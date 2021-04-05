package com.fi9e.rest.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateTest {

public static void main(String[] args) {
		
		// create session factory
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.buildSessionFactory();
		
		// create session 

		Session session = factory.getCurrentSession();
		
		try {
			// use the session object to save Java object
			
			
			// create a real message object
			System.out.println("Creating a new RealMessage object");
			User tempUser = new User("Tom Riddle", "tom.riddle@test.com", "oasswrod"); 
			
			// start a transaction
			session.beginTransaction();
			
			// save the User object
			System.out.println("Saving the User into the database...");
			session.save(tempUser);
			
			// commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
			
		    // Code for UPDATE:
			
			// find out the id (primary key) of the RealMessage
			System.out.println("Saved user. Generated id: " + tempUser.getId());
			
			// not get a new session and start transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			//retrieve RealMessage based on the id: primary key
			System.out.println("\nGetting user with id: " + tempUser.getId());
			
			User myRealUser = session.get(User.class, tempUser.getId());
			
			System.out.println("Get complete: " + myRealUser);
			
			// commit the transaction
			session.getTransaction().commit();
		}
		
		finally {
			factory.close();
		}
		
	}

}
