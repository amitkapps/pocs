package hbm;

import org.hibernate.EmptyInterceptor;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jul 14, 2010
 * Time: 12:55:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuditTrailManagerInterceptor extends EmptyInterceptor {
    Logger log = LoggerFactory.getLogger(AuditTrailManagerInterceptor.class);
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
                                String[] propertyNames, org.hibernate.type.Type[] types) {
        if(entity instanceof Auditable){
            log.trace("***ON FLUSH DIRTY");
            setValue(currentState, propertyNames, "updateUser", "akapoor");
//            setValue(currentState, propertyNames, "updateDate", new Date());
        }

        return true;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, org.hibernate.type.Type[] types) {
        if (entity instanceof Auditable) {
            log.trace("***ON SAVE");
            setValue(state, propertyNames, "createUser", "akapoor");
            setValue(state, propertyNames, "createDate", new Date());
            setValue(state, propertyNames, "updateUser", "akapoor");
//            setValue(state, propertyNames, "updateDate", new Date());
        }
        return true;
    }

    private void setValue(Object[] currentState, String[] propertyNames,
                          String propertyToSet, Object value) {
        int index = Arrays.asList(propertyNames).indexOf(propertyToSet);
        if (index >= 0) {
            currentState[index] = value;
        }
    }
}