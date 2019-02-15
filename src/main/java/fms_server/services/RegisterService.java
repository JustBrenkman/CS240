package fms_server.services;

import fms_server.dao.IDatabaseAccessObject;
import fms_server.models.User;
import fms_server.requests.RegisterRequest;
import fms_server.results.RegisterResult;

public class RegisterService extends Service {

    /**
     * Constructor for the register service
     * @param dao UserDAO
     */
    public RegisterService(IDatabaseAccessObject dao) {
        super(dao);
    }

    /**
     * Registers user
     * @param user RegisterRequest
     * @return A RegisterResult that contains the information about the result of the request
     */
    public RegisterResult register(RegisterRequest user) {
        return null;
    }
}
