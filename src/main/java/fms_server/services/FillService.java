package fms_server.services;

import fms_server.dao.*;
import fms_server.requests.FillRequest;
import fms_server.results.FillResult;

/**
 * Fill service class
 */
public class FillService extends Service {
    private EventDAO eventDAO;
    private UserDAO userDAO;
    private PersonDAO personDAO;
    /**
     * Fill service constructor
     * @param dao DataAccessObject for database access
     */
    public FillService(IDatabaseAccessObject dao) {
        super(dao);
    }

    public FillService(EventDAO eventDAO, UserDAO userDAO, PersonDAO personDAO) {
        super(eventDAO);
        this.personDAO = personDAO;
        this.eventDAO = eventDAO;
        this.userDAO = userDAO;
    }

    /**
     * Will generate and fill database with random information
     * @param request FillRequest
     * @return whether fill was successful
     */
    public FillResult fill(FillRequest request) throws DataBaseException {
        return new FillResult(true, "Generations are filled");
    }
}
