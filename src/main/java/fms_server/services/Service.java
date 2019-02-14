package fms_server.services;

import fms_server.dao.IDatabaseAccessObject;

/**
 * Base class for all services
 */
public class Service {
    private IDatabaseAccessObject dao;

    public Service(IDatabaseAccessObject dao) {
        this.dao = dao;
    }

    /**
     * Gets the IDatabaseAccessObject associated with the class
     * @return IDatabaseObject instance
     */
    public IDatabaseAccessObject getDao() {
        return dao;
    }
}
