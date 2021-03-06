package com.cog.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.cog.entity.User;

public class UserDAO {
	
//	1. Create a Session Factory
//	SessionFactory  sessionFactory = new Configuration().configure().buildSessionFactory();
	
	SessionFactory sessionFactory = new AnnotationConfiguration().
            configure().
            addAnnotatedClass(User.class).
//            addAnnotatedClass(Address.class).
            buildSessionFactory();
	
	public boolean addUser(User user){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.save(user);
		
		tx.commit();
		
		return true;
	}
	
	public User getUser(int id){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		User user1 = (User) session.get(User.class, id);
		
		tx.commit();
		
		return user1;
	}
	
	public User changeUserName(int id, String newName){
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		User user1 = (User) session.get(User.class, id);
		user1.setName("Ravi");
		user1.setName("Sonia");
		
		tx.commit();
		session.close();
		
		return user1;
	}

}
