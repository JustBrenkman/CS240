package fms_server.requests;

import fms_server.logging.Logger;
import fms_server.models.AbstractModel;

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

    /**
     * Checks to see if the class has any null variables
     */
    public void checkForProperInstantiation() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.get(this) == null)
                    Logger.warn("Could not find value to instantiate: " + field.getName() + ", on class: " + this.getClass().getSimpleName() + "; this could result in failure to complete request");
                if (field.get(this) instanceof AbstractModel)
                    ((AbstractModel) field.get(this)).checkForProperInstantiation();
                if (field.get(this) instanceof AbstractModel[])
                    for (AbstractModel entry : (AbstractModel[]) field.get(this))
                        entry.checkForProperInstantiation();
            } catch (IllegalAccessException e) {
                Logger.warn("Unable to read request field, please make sure that it is protected", e);
            }
        }
    }
}
