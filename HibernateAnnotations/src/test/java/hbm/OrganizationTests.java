package hbm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 */
public class OrganizationTests {

    Logger log = LoggerFactory.getLogger(OrganizationTests.class);

    @Test
    public void testBuildSessionFactory(){
        HibernateUtil.getSessionFactory();
    }
    
    @Test
    public void selectOrganizations(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

//        Organization org = (Organization)session.get(Organization.class, 1L);
        Organization org = new Organization()
                            .setId(1000L)
                            .setOrganizationName("TEST ORG");
        log.info("Retrieved Organization - {}", org);

        org.setStatus("I");
        session.save(org);
        transaction.commit();
        session.close();

        log.info("saved org; {}", org);

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(org);
        transaction.commit();
        session.close();
        log.info("deleted org; {}", org);

    }

}
