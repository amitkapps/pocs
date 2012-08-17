package hbm;

import org.hamcrest.Matchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//Example of hamcrest matchers http://code.google.com/p/hamcrest/wiki/Tutorial
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jun 17, 2010
 * Time: 9:22:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class SubscriptionTest {

    Logger log = LoggerFactory.getLogger(SubscriptionTest.class);

    @Test
    public void testBuildSessionFactory() {
        assertThat(HibernateUtil.getSessionFactory(), Matchers.<SessionFactory>notNullValue());
    }

    @Test
    public void selectSubscriptions() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Collection<Subscription> subscriptions = session.createQuery("from Subscription ").list();

        log.info("Retrieved Subscriptions: {}", subscriptions);

        transaction.commit();
        session.close();
    }


    @Test
    public void saveOrUpdateSubscription(){
        boolean deleteAndInsert = true;

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession(new AuditTrailManagerInterceptor());
        Transaction transaction = session.beginTransaction();

        Notification notification = (Notification)session.load(Notification.class, 1L);
        log.info("Loaded notif: {}", notification);
        OceanSubscriber subscriber = (OceanSubscriber) session.load(OceanSubscriber.class, 11L);
        log.info("Loaded subscriber: {}", subscriber);

        //remove existing subscription
        OceanSubscription oSubscription = (OceanSubscription)session.createCriteria(OceanSubscription.class)
                                                    .add(Restrictions.eq("notification.id", notification.getId()))
                                                    .add(Restrictions.eq("subscriber.id", subscriber.getId()))
                                                    .uniqueResult();
        log.info("Loaded Ocean Subscription: {}", oSubscription);
        if(null != oSubscription && deleteAndInsert){
            session.delete(oSubscription);
            session.flush();
        }
        OceanSubscriptionConfiguration osc
                = new OceanSubscriptionConfiguration()
                        .setCustomerArolAuthorization(
                                subscriber.getCustomer().getCustomerArolAuthorizations()
                                        .iterator().next()
                        );
        List<OceanSubscriptionConfiguration> oscs = new ArrayList<OceanSubscriptionConfiguration>();
        oscs.add(osc);


        Subscription subscription
                = new OceanSubscription()
                        .setOceanSubscriptionConfigurations(oscs)
                        .setEmailDelivery(true)
                        .setFaxDelivery(false)
                        .setSubscriber(subscriber)
                        .setNotification(notification);


        session.saveOrUpdate(subscription);

        transaction.commit();
        session.close();

    }
}
