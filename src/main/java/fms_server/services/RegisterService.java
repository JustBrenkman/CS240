package fms_server.services;

import fms_server.dao.*;
import fms_server.logging.Logger;
import fms_server.models.Person;
import fms_server.models.User;
import fms_server.requests.RegisterRequest;
import fms_server.results.RegisterResult;

import java.util.Random;

public class RegisterService extends Service {
    private IDatabaseAccessObject<Person, String> personDAO;

    /**
     * Constructor for the register service
     * @param userDAO UserDAO
     */
    public RegisterService(IDatabaseAccessObject<User, String> userDAO, IDatabaseAccessObject<Person, String> personDAO) {
        super(userDAO);
        this.personDAO = personDAO;
    }

    /**
     * Registers user, creates a new person and user model to add to the database
     * @param user RegisterRequest
     * @return A RegisterResult that contains the information about the result of the request
     */
    public RegisterResult register(RegisterRequest user) {
        Person person = new Person(user);
        User userToAdd = new User(person.getId(), user);
        try {
            personDAO.add(person);
            ((UserDAO) getDao()).add(userToAdd);
        } catch (DataBaseException e) {
            Logger.error("Unable to add user", e);
            return new RegisterResult(false, "Already a user", null);
        }
        return new RegisterResult(true, "Successfully register user", generateAuthToken(userToAdd));
    }
}
