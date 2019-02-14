package fms_server.services;

import fms_server.dao.IDatabaseAccessObject;
import fms_server.models.User;
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
     * @param user
     * @return
     */
    public RegisterResult register(User user) {
        return null;
    }
}
