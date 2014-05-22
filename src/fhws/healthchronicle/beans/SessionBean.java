package fhws.healthchronicle.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fhws.healthchronicle.dao.EntityManagerFactoryBean;
import fhws.healthchronicle.entities.PlatformUser;

@SessionScoped
@ManagedBean
public class SessionBean implements Serializable
{
	private PlatformUser platformUser;
	private boolean loggedIn = false;
	
	//@PersistenceContext
	private EntityManager em;

	public EntityManager getEm()
	{
		if (em == null)
		{
			em = new EntityManagerFactoryBean().getEntityManager();
			System.out.println("new entity manager");
		}
		
		return em;
	}

	public void setEm(EntityManager em)
	{
		this.em = em;
	}

	public SessionBean()
	{
	}

	public PlatformUser getPlatformUser()
	{
		if (platformUser == null)
		{
			System.out.println("new PlatformUser");
			platformUser = new PlatformUser();
		}

		return platformUser;
	}

	public void setPlatformUser(PlatformUser platformUser)
	{
		this.platformUser = platformUser;
	}

	public boolean isLoggedIn()
	{
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn)
	{
		this.loggedIn = loggedIn;
	}
}