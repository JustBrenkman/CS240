package fms_server.services;

import fms_server.dao.IDatabaseAccessObject;

/**
 * Fill service class
 */
public class FillService extends Service {

    /**
     * Fill service constructor
     * @param dao IDatabaseObject
     */
    public FillService(IDatabaseAccessObject dao) {
        super(dao);
    }
}
