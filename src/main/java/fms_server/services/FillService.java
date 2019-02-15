package fms_server.services;

import fms_server.dao.IDatabaseAccessObject;
import fms_server.requests.FillRequest;

/**
 * Fill service class
 */
public class FillService extends Service {
    /**
     * Fill service constructor
     * @param dao DataAccessObject for database access
     */
    public FillService(IDatabaseAccessObject dao) {
        super(dao);
    }

    /**
     * Will generate and fill database with random information
     * @param request FillRequest
     * @return whether fill was successful
     */
    public boolean fill(FillRequest request) {
        return true;
    }
}
