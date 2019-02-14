package fms_server.models;

/**
 * Basic model class
 * @param <T> Type of id
 */
public abstract class Model<T> {
    private T id;

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
}
