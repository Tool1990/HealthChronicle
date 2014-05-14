package fhws.healthchronicle.managedbeans;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fhws.healthchronicle.entities.User;

public class EmailRestoreBean {
	public static void main(String[] args) throws Exception {
		
		
		//private static final long serialVersionUID = 1L;
		final String PERSISTENCE_UNIT_NAME = "common-entities";	
		
		String usermail = "katerok_de@mail.ru";
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT x FROM User x WHERE x.name ='"	+ usermail + "'");
		
		List<User> usersList = new ArrayList<User>();
		usersList = (List<User>) q.getResultList();
		
		User tUser = usersList.get(0);
		Long tId = usersList.get(0).getId();
		String tName = usersList.get(0).getName();
		String tPassword = usersList.get(0).getPassword();
		String tRole = usersList.get(0).getRole();
		System.out.println("user:"+tUser);
		System.out.println("id:"+tId+" name:"+tName+" password:"+tPassword+" role:"+tRole);
		
		
			System.out.println("Senden email");
			// Google-Mail mit TLSv1 und Auth
			String mhost="smtp.gmail.com";
			int mport=587;
			String muser="mediplatt@gmail.com";
			String mpass="mediplatt2014";
			Properties props=new Properties();
			// Authentifizierung aktivieren
			props.put("mail.smtp.auth", "true");
			// SSLv3/TLSv1
			props.put("mail.smtp.starttls.enable", "true");
			Session session=Session.getDefaultInstance(props);
			Transport transport=session.getTransport("smtp");
			transport.connect(mhost, mport, muser, mpass);
			Address[] addresses=InternetAddress.parse(tName);
			Message message=new MimeMessage(session);
			message.setFrom(new InternetAddress(muser));
			message.setRecipients(Message.RecipientType.TO, addresses);
			message.setSubject("MediPlatt: Passwordwiederherstellung");
			
			String neuPassword = generatePass();
			System.out.println("pass:"+neuPassword);
			
			message.setText("Ihr neues Password lautet: "+neuPassword);
			transport.sendMessage(message, addresses);
			System.out.println("E-Mail gesendet");
			transport.close();
			
			//**********
			
			em.getTransaction().begin();
			User toBeUpdated = em.merge(tUser);
			toBeUpdated.setPassword(neuPassword);
			em.getTransaction().commit();
			System.out.println("updateCount");
		

	}
	
		  public static String generatePass() {
			  String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			  Random rnd = new Random();
		     StringBuilder sb = new StringBuilder(8);
			     for( int i = 0; i < 8; i++ ) 
			        sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
			     return sb.toString();
		  }
		  
}

///////////////////////////////////////////////////////////////
/*package de.medizinplattform.managedbeans;

import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class EmailRestoreBean {
	String email;
	
	public EmailRestoreBean() {
		this.email=null;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}
	public String getEmail() {
		return email;
	}
	
	public void param() {
		// +[a-zA-Z0-9]*)@([a-zA-Z]+[a-zA-Z0-9]*)(\\.[a-zA-Z]{2,6})
		//String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$
		
		final long serialVersionUID = 1L;
				final String PERSISTENCE_UNIT_NAME = "common-entities";	
				
				String usermail = "katerok_de@mail.ru";
				
					System.out.println("Senden email");
					// Google-Mail mit TLSv1 und Auth
					String mhost="smtp.gmail.com";
					int mport=587;
					String muser="mediplatt@gmail.com";
					String mpass="mediplatt2014";
					
					Properties props=new Properties();
					// Authentifizierung aktivieren
					props.put("mail.smtp.auth", "true");
					// SSLv3/TLSv1
					props.put("mail.smtp.starttls.enable", "true");
					
					Session session=Session.getDefaultInstance(props);
					System.out.println("session: "+session.getDebugOut());
					Transport transport = null;
					System.out.println("1");
					
					// fehler
					 try {
						transport = session.getTransport("smtp");
						System.out.println("2"); //nein
					} catch (NoSuchProviderException e) {
						System.out.println("Fehler gefunden:"); //ja
						System.out.println(e); //ja
						e.printStackTrace();
					}
					 
					 
					 
					 
					 
					 
					/*
					try {
						transport.connect(mhost, mport, muser, mpass);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Address[] addresses = null;
					try {
						addresses = InternetAddress.parse(usermail);
					} catch (AddressException e) {
						System.out.println("***"+e); //ja
						e.printStackTrace();
					}
					Message message=new MimeMessage(session);
					try {
						message.setFrom(new InternetAddress(muser));
					} catch (MessagingException e) {
						System.out.println("***"+e); //ja
						e.printStackTrace();
					}
					try {
						message.setRecipients(Message.RecipientType.TO, addresses);
					} catch (MessagingException e) {
						System.out.println("***"+e); //ja
						e.printStackTrace();
					}
					try {
						message.setSubject("MediPlatt: Passwordwiederherstellung");
					} catch (MessagingException e) {
						System.out.println("***"+e); //ja
						e.printStackTrace();
					}
					
					try {
						message.setText("Ihr neues Password lautet: ");
					} catch (MessagingException e) {
						System.out.println("***"+e); //ja
						e.printStackTrace();
					}
					try {
						transport.sendMessage(message, addresses);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("E-Mail gesendet");
					/*try {
						transport.close();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		//--------
		
		this.email += "?";
	}
	
}*/

