package fhws.healthchronicle.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import fhws.healthchronicle.dao.EntityManagerFactoryBean;
import fhws.healthchronicle.entities.PlatformUser;

@SessionScoped
@ManagedBean
public class SessionBean implements Serializable
{
	private static final long serialVersionUID = 6777774608213533082L;
	
	private PlatformUser platformUser;
	private boolean loggedIn = false;

	@ManagedProperty(value = "#{entityManagerFactoryBean}")
	private EntityManagerFactoryBean emfBean;
	private EntityManager em;

	public EntityManager getEm()
	{
		if (em == null)
		{
			em = emfBean.getEntityManager();
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
		
		System.out.println("getPlatformUser()");
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

	public EntityManagerFactoryBean getEmfBean()
	{
		return emfBean;
	}

	public void setEmfBean(EntityManagerFactoryBean emfBean)
	{
		this.emfBean = emfBean;
	}
}