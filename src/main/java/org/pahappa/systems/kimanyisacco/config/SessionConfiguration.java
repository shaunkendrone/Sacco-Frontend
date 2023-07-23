package org.pahappa.systems.kimanyisacco.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.pahappa.systems.kimanyisacco.models.Account;
// import org.pahappa.systems.kimanyisacco.User.User;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.models.Transactions;

// import javassist.compiler.ast.Member;

public class SessionConfiguration {
    private final static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            AnnotationConfiguration configuration = new AnnotationConfiguration();
            configuration.configure(); // Loads hibernate.cfg.xml configuration

            // Add annotated classes here
            configuration.addAnnotatedClass(Members.class);
            configuration.addAnnotatedClass(Account.class);
            configuration.addAnnotatedClass(Transactions.class);
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
