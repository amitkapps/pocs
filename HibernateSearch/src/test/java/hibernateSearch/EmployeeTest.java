package hibernateSearch;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class EmployeeTest{
    public static Logger log = LoggerFactory.getLogger(EmployeeTest.class);

    @Test
    public void testLoadEmployee(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List list = session.createCriteria(Employee.class).list();
        log.info("Employees: {}", list);

        session.close();
    }


}
