package fms_server.services;

import com.google.gson.Gson;
import fms_server.FMSServer;
import fms_server.dao.IDatabaseAccessObject;
import fms_server.models.AuthToken;
import fms_server.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.util.Calendar;

/**
 * Base class for all services
 */
public class Service {
    private IDatabaseAccessObject dao;
    private Gson gson;

    public Service(IDatabaseAccessObject dao) {
        this.dao = dao;
        this.gson = new Gson();
    }

    /**
     * Gets the IDatabaseAccessObject associated with the class
     * @return IDatabaseObject instance
     */
    public IDatabaseAccessObject getDao() {
        return dao;
    }

    public AuthToken generateAuthToken(User user) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 1);
        String jws = Jwts.builder().setSubject(user.getUsername()).setExpiration(cal.getTime()).signWith(FMSServer.getKey()).compact();
        return new AuthToken(jws, user.getUsername());
    }

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
