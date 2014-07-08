package fhws.healthchronicle.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import com.google.gson.Gson;

import fhws.healthchronicle.dao.EntityManagerFactoryBean;
import fhws.healthchronicle.entities.Event;
import fhws.healthchronicle.entities.PlatformUser;
import fhws.healthchronicle.entities.Story;

@SessionScoped
@ManagedBean(name = "sessionBean")
public class SessionBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	private PlatformUser platformUser;
	private boolean loggedIn = false;
	private Story activeStory;
	private String localeCode = "en";

	@ManagedProperty(value = "#{entityManagerFactoryBean}")
	private EntityManagerFactoryBean emfBean;
	private EntityManager em;

	@PostConstruct
	public void init()
	{
		Locale lang = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
		FacesContext.getCurrentInstance().getViewRoot().setLocale(lang);
		setLocaleCode(lang.getLanguage());
	}

	public EntityManager getEm()
	{
		em = emfBean.getEntityManager();
		System.out.println("new entity manager");
		return em;
	}

	public void setEm(EntityManager em)
	{
		this.em = em;
	}

	public void destroy()
	{
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		// setLoggedIn(false);
		System.out.println("session destroyed");
	}

	public String jsonEvents()
	{
		List<Event> events = getActiveStory().getEvents();
		return (events == null) ? "" : new Gson().toJson(events);
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

	public Story getActiveStory()
	{
		return activeStory;
	}

	public void setActiveStory(Story activeStory)
	{
		this.activeStory = activeStory;
	}

	public EntityManagerFactoryBean getEmfBean()
	{
		return emfBean;
	}

	public void setEmfBean(EntityManagerFactoryBean emfBean)
	{
		this.emfBean = emfBean;
	}

	public String getLocaleCode()
	{
		return localeCode;
	}

	public void setLocaleCode(String localeCode)
	{
		this.localeCode = localeCode;
	}
}