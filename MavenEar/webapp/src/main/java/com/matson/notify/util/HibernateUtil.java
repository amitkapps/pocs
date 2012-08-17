package com.matson.notify.util;

import org.hibernate.HibernateException;
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
public class HibernateUtil {

    private static SessionFactory sessionFactory = null;
    private static final String HIBERNATE_CONFIG_PATH = "config/hibernate/hibernate.cfg.xml";
    static{
        try {
            Configuration cfg = new AnnotationConfiguration();
            sessionFactory = cfg.configure(HIBERNATE_CONFIG_PATH).buildSessionFactory();
        } catch (HibernateException e) {
            throw new ExceptionInInitializerError(e);
        }
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}