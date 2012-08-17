package hibernateSearch;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 4/30/11
 * Time: 2:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUtil {
    public static Logger log = LoggerFactory.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory;
    static{
        sessionFactory = new Configuration().configure("mns.hibernate.cfg.xml").buildSessionFactory();
        log.info("Built Session Factory");
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
