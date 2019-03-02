package fms_server.services;

import fms_server.dao.DataBaseException;
import fms_server.dao.IDatabaseAccessObject;
import fms_server.logging.Logger;
import fms_server.requests.Request;
import fms_server.results.ClearResult;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Clear service class
 */
public class ClearService extends Service {
    private List<IDatabaseAccessObject> daos;
    /**
     * Constructor for the clear service
     * @param dao IDatabaseAccessObject
     */
    public ClearService(IDatabaseAccessObject...dao) {
        super(dao[0]);
        this.daos = Arrays.asList(dao);
        registerServiceCall(ClearResult.class, this::clear);
    }

    /**
     * Clears(truncates) everything in the database
     * @return ClearResult
     */
    public ClearResult clear(Request request) {
        AtomicBoolean cleared = new AtomicBoolean(true);
        daos.forEach((item) -> {
            try {
                item.clear();
            } catch (DataBaseException e) {
                Logger.error("Unable to clear all the tables", e);
                cleared.set(false);
            }
        });
        return new ClearResult(cleared.get(), (cleared.get()) ? "Success" : "Failed to clear all the databases");
    }
}
