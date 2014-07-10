package com.test.many2many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.BeforeClass;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import java.io.File;

/**
 * Created by hejian on 2014/7/10.
 */
public class App {

    private static SessionFactory sf = null;
    private Session session = null;
    @BeforeClass
    public static void initSessionFactory(){
        Configuration cf = new Configuration().configure(new File("F:\\IdeaProjects\\SmileTvPlatform\\src\\test\\java\\com\\test\\many2many\\hibernate.cfg.xml"));
        ServiceRegistry sg = new StandardServiceRegistryBuilder().applySettings(cf.getProperties()).build();
        sf = cf.buildSessionFactory(sg);
    }

    @Test
    public void testMany2Many(){
        session = sf.openSession();
        System.out.println(session+"---------------------");
    }


}
