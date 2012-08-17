package hbm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for simple App.
 */
public class AddressTests {

    Logger log = LoggerFactory.getLogger(AddressTests.class);

    @Test
    public void testBuildSessionFactory(){
        HibernateUtilCP.getOceanCPSessionFactory();
    }

    @Test
    public void selectOrganizationAddress(){
        SessionFactory sessionFactory = HibernateUtilCP.getOceanCPSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Address orgAddr = (Address)session.createCriteria(Address.class)
                                    .add(Restrictions.eq("organizationId", 12146L))
                                    .add(Restrictions.eq("addressSequence", 1))
                                    .uniqueResult();
        log.info("Retrieved OrganizationAddress - {}", orgAddr);

        transaction.commit();
        session.close();
    }

}
