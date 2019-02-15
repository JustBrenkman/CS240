package fms_server.services;

import fms_server.dao.IDatabaseAccessObject;
import fms_server.models.Person;
import fms_server.requests.AuthenticatedRequest;
import fms_server.requests.PersonRequest;

import java.util.List;

/**
 * Person Service class
 */
public class PersonService extends Service {

    /**
     * Person Service constructor
     * @param dao PersonDAO
     */
    public PersonService(IDatabaseAccessObject dao) {
        super(dao);
    }

    /**
     * Get a list of all Persons
     * @param request This request must be authenticated
     * @return return a list of all person objects in the database
     */
    public List<Person> getAllPersons(AuthenticatedRequest request) {
        return null;
    }

    /**
     * Gets a single person based on an id
     * @param request contains id information, and authentication
     * @return a person object
     */
    public Person getPerson(PersonRequest request) {
        return null;
    }
}
