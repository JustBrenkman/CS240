package fms_server.models;

import fms_server.logging.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Basic model class
 * @param <T> Type of id
 */
public abstract class AbstractModel<T> {
    protected T id;

    /**
     * Getter for the models id
     * @return id
     */
    public T getId() {
        return id;
    }

    /**
     * Setter of the models id
     * @param id id
     */
    public void setId(T id) {
        this.id = id;
    }

    /**
     * Takes any arbitrary class that inherits AbstractModel and a SQL result set and converts into instance of model
     * NOTE: Will only set variables that can be found in the result set, if they do not exist will end up being null
     * Will only through warning if not found, note that this may cause unknown consequences in your code
     * @param tClass Class to convert into
     * @param resultSet SQL Result Set, contains information we want to cast into the model
     * @param <T> Type of class (model class)
     * @return an instance of the convert result set
     */
    public static <T extends AbstractModel> T castToModel(Class<T> tClass, ResultSet resultSet) {
        T model = null;
        try {
            Constructor<T> constructor = tClass.getDeclaredConstructor(); // Gets the protected constructor
            model = constructor.newInstance((Object[]) null);
            Field[] fields = tClass.getDeclaredFields();
            Field[] superFields = tClass.getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.set(model, resultSet.getObject(field.getName()));
                } catch (IllegalAccessException | SQLException e) {
                    Logger.warn("[CASTING ERROR]: Class-> " + tClass.getName() + ", Variable not found: " + field.getName() + ", type: " + field.getType().getName(), e);
                }
            }
            for (Field field : superFields) {
                try {
                    field.set(model, resultSet.getObject(field.getName()));
                } catch (IllegalAccessException | SQLException e) {
                    Logger.warn("[CASTING ERROR]: Class-> " + tClass.getName() + ", Variable not found: " + field.getName() + ", type: " + field.getType().getName(), e);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
//            e.printStackTrace();
            Logger.setShouldPrintStackTrace(true);
            Logger.error("Unable to cast, something went wrong with generating the model. Try adding an empty public constructor", e);
            Logger.setShouldPrintStackTrace(false);
            return null;
        }
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractModel)) return false;
        AbstractModel<?> that = (AbstractModel<?>) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
