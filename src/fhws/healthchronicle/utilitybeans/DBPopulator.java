package fhws.healthchronicle.utilitybeans;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fhws.healthchronicle.entities.ActionCollection;
import fhws.healthchronicle.entities.Entry;

public class DBPopulator {
	
	private static final String PERSISTENCE_UNIT_NAME = "common-entities";
	
	public static void main(String[] args) {
		List<Entry> allEntries;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
	
		em.getTransaction().begin();
		ActionCollection sc = new ActionCollection();
		sc.setAction("Schlafen");
		em.persist(sc);
		em.getTransaction().commit();
	}
}


