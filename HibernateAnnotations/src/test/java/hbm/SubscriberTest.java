package hbm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 2, 2010
 * Time: 12:45:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubscriberTest {


    Logger log = LoggerFactory.getLogger(OrganizationTests.class);

    @Test
    public void testBuildSessionFactory() {
        HibernateUtil.getSessionFactory();
    }

    @Test
    public void selectSubscribers() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        OceanSubscriber oceanSubscriber = (OceanSubscriber) session.get(OceanSubscriber.class, 11L);
        log.info("Retrieved Subscribers - {}", oceanSubscriber);
        oceanSubscriber.setActive(!oceanSubscriber.isActive());
        session.save(oceanSubscriber);
        transaction.commit();
        session.close();
    }

}