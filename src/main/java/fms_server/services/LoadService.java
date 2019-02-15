package fms_server.services;

import fms_server.dao.IDatabaseAccessObject;
import fms_server.requests.LoadRequest;

/**
 * Load service class
 */
public class LoadService extends Service {

    /**
     * Load service constructor
     * @param dao IDatabaseObject
     */
    public LoadService(IDatabaseAccessObject dao) {
        super(dao);
    }

    /**
     * Loads database with user, person, and event objects
     * @param request contains list of users, events, and persons
     * @return if fill was successful or not
     */
    public boolean load(LoadRequest request) {
        return true;
    }
}
