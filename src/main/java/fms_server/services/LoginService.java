package fms_server.services;

import fms_server.dao.IDatabaseAccessObject;
import fms_server.results.LoginResult;

/**
 * Service class to do with logging in
 */
public class LoginService extends Service {

    /**
     * Constructor for the login service
     * @param dao UserDAO
     */
    public LoginService(IDatabaseAccessObject dao) {
        super(dao);
    }

    /**
     * Attempts to login the user.
     * @param request LoginRequest contains username, email, password
     * @return LoginResult holds the information about the attempt. If it was unsuccessful will may return a null object
     */
    public LoginResult login(LoginResult request) {
        return null;
    }
}
