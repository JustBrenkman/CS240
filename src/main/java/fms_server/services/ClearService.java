package fms_server.services;

import fms_server.dao.IDatabaseAccessObject;
import fms_server.results.ClearResult;

/**
 * Clear service class
 */
public class ClearService extends Service {
    /**
     * Constructor for the clear service
     * @param dao IDatabaseAccessObject
     */
    public ClearService(IDatabaseAccessObject dao) {
        super(dao);
    }


    /**
     * Clears(truncates) everything in the database
     * @return ClearResult
     */
    public ClearResult clear() {
        return null;
    }
}
