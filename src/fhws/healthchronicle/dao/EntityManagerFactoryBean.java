package fhws.healthchronicle.dao;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
@ManagedBean(eager = true)
public class EntityManagerFactoryBean implements Serializable
{
	private static final long	serialVersionUID	= -5567157647750357829L;
	private static final String	PERSISTENCE_UNIT_NAME	= "common-entities";
	EntityManagerFactory		emf;

	public EntityManagerFactoryBean()
	{
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}
}
