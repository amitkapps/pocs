package hbm;

import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jul 14, 2010
 * Time: 2:11:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyPostInsertEventListener implements PostInsertEventListener{
    Logger log = LoggerFactory.getLogger(MyPostInsertEventListener.class);
    public void onPostInsert(PostInsertEvent event) {
        log.info("INSERTED ENTITY {}", event.getEntity());
    }
}
