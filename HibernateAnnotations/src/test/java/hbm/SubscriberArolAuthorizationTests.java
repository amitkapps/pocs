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
 * Time: 4:48:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubscriberArolAuthorizationTests {
    Logger log = LoggerFactory.getLogger(OrganizationTests.class);

    @Test
    public void testBuildSessionFactory(){
        HibernateUtilCP.getOceanCPSessionFactory();
    }

    @Test
    public void selectAuthorizations(){
        SessionFactory sessionFactory = HibernateUtilCP.getOceanCPSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<CustomerArolAuthorization> auths = session.createCriteria(CustomerArolAuthorization.class)
                .createCriteria("addressRole.organization")
                .add(Restrictions.eq("id", 8906L))
                .list();
        log.info("Retrieved Authorizations - {}", auths);

        transaction.commit();
        session.close();
    }

}
