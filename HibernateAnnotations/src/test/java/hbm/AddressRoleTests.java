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
 * Date: May 27, 2010
 * Time: 4:51:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddressRoleTests {
    
    Logger log = LoggerFactory.getLogger(AddressRoleTests.class);

    @Test
    public void selectAddressRole(){
        SessionFactory sessionFactory = HibernateUtilCP.getOceanCPSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<AddressRole> arolList = session.createCriteria(AddressRole.class)
                                                .add(Restrictions.eq("organization.id", 12146L))
                                                .list();
        log.info("Retrieved {} AddressRoles - {}", arolList.size(), arolList);

        transaction.commit();
        session.close();

    }
}
