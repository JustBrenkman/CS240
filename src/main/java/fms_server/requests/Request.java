package fms_server.requests;

import fms_server.logging.Logger;

import java.lang.reflect.Field;

public class Request {
    /**
     * Converts any arbitrary object to string.
     * @return String representation of the object
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append( this.getClass().getSimpleName() );
        Field[] fields = this.getClass().getDeclaredFields();
        for ( Field field : fields  ) {
            result.append(", ");
            try {
                result.append( field.getName() );
                result.append(": ");
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                Logger.warn("Unable to access field: " + field.getName());
            }
        }
        return result.toString();
    }
}
