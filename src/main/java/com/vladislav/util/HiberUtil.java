package com.vladislav.util;

import com.vladislav.model.Event;
import com.vladislav.model.File;
import com.vladislav.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HiberUtil {
    private static SessionFactory sessionFactory;

    static {
        try{
            sessionFactory = new Configuration().configure()
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(File.class)
                    .addAnnotatedClass(Event.class)
                    .buildSessionFactory();
        } catch (Throwable th) {
            System.out.println("Unable to create session factory");
            th.printStackTrace();
        }
    }

    public static Session getOpenedSession() {
        return sessionFactory.openSession();
    }
}
