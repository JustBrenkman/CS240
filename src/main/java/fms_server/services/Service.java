package fms_server.services;

import com.google.common.annotations.Beta;
import com.google.gson.Gson;
import fms_server.FMSServer;
import fms_server.dao.IDatabaseAccessObject;
import fms_server.logging.Logger;
import fms_server.models.AuthToken;
import fms_server.models.User;
import fms_server.requests.Request;
import fms_server.results.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.util.Calendar;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Base class for all services
 */
public class Service {
    private IDatabaseAccessObject dao;
    private Gson gson;
    private HashMap<Class<?>, Function<Request, Result>> serviceCalls; // This is an experimental feature

    public Service(IDatabaseAccessObject dao) {
        this.dao = dao;
        this.gson = new Gson();
        serviceCalls = new HashMap<>();
    }

    /**
     * Gets the IDatabaseAccessObject associated with the class
     * @return IDatabaseObject instance
     */
    public IDatabaseAccessObject getDao() {
        return dao;
    }

    /**
     * Register a service call
     *
     * @param tClass   return type of function
     * @param function function
     * @param <T>      return type
     */
    @Beta
    <T extends Result> void registerServiceCall(Class<T> tClass, Function<Request, Result> function) {
        if (serviceCalls.containsKey(tClass))
            Logger.warn("Already set that service call, rewriting the old call");
        serviceCalls.put(tClass, function);
    }

    /**
     * Call function to call a service function
     * @param tClass return type
     * @param request request
     * @param <T> type of return
     * @param <U> type of request
     * @return result
     * @throws NoSuchMethodException if it doesn't exist
     */
    @Beta
    public <T extends Result, U extends Request> T call(Class<T> tClass, U request) throws NoSuchMethodException {
        try {
            if (serviceCalls.containsKey(tClass))
                return (T) serviceCalls.get(tClass).apply(request);
            throw new NoSuchMethodException("There is no service call that is defined by that class");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Creates a AuthToken
     * @param user
     * @return
     */
    public AuthToken generateAuthToken(User user) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 1);
        String jws = Jwts.builder().setSubject(user.getUsername()).setExpiration(cal.getTime()).signWith(FMSServer.getKey()).compact();
        return new AuthToken(jws, user.getUsername());
    }

    /**
     * Authenticates an authtoken
     * @param token token string
     * @return if token is authenticated
     */
    boolean authenticateToken(String token) {
        Jws<Claims> jws;
        try {
            jws = Jwts.parser().setSigningKey(FMSServer.getKey()).parseClaimsJws(token);
        } catch (JwtException e) {
            return true;
        }
        return false;
    }
}
