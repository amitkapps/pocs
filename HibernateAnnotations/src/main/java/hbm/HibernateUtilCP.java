package hbm;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: May 25, 2010
 * Time: 11:31:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUtilCP {


    private static SessionFactory sessionFactory = null;

    private static enum SessionFactoryType{
        OCEAN_CP("hibernate.cp.cfg.xml"),
        MNS("hibernate.mns.cfg.xml");

        private String hibernateMappingFileLocation;
        private SessionFactory sessionFactory;

        SessionFactoryType(String hibernateMappingFileLocation){
            this.hibernateMappingFileLocation = hibernateMappingFileLocation;
            buildSessionFactory();
        }

        private String getHibernateMappingFileLocation() {
            return hibernateMappingFileLocation;
        }

        public SessionFactory getSessionFactory() {
            return sessionFactory;
        }

        private static final Object SESSION_FACTORY_BUILD_LOCK = new Object();

        private void buildSessionFactory(){
            Configuration cfg = new AnnotationConfiguration();
            sessionFactory = cfg.configure(hibernateMappingFileLocation).buildSessionFactory();
        }
    }


    public static SessionFactory getOceanCPSessionFactory(){
        return SessionFactoryType.OCEAN_CP.getSessionFactory();
    }

    public static SessionFactory getMNSSessionFactory(){
        return SessionFactoryType.MNS.getSessionFactory();
    }
}
