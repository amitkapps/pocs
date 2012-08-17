package hbm;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.event.PostInsertEventListener;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: May 25, 2010
 * Time: 11:31:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUtil {

    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static SessionFactory sessionFactory;

    static{
            Configuration cfg = new AnnotationConfiguration();
        cfg.getEventListeners().setPostInsertEventListeners(new PostInsertEventListener[]{new MyPostInsertEventListener()});
            sessionFactory = cfg.configure(HIBERNATE_CFG_FILE)
//                                .setNamingStrategy(ImprovedNamingStrategy.INSTANCE)
                                .setInterceptor(new AuditTrailManagerInterceptor())
                                .buildSessionFactory();
        }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

