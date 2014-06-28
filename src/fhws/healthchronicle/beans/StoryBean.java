package fhws.healthchronicle.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

import fhws.healthchronicle.entities.Story;

@RequestScoped
@ManagedBean
public class StoryBean implements Serializable
{
	private static final long serialVersionUID = 1261120717489692759L;

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean session;

	private Story story;

	public StoryBean()
	{
		System.out.println("new Story");
		story = new Story();
	}

	public String createStory()
	{
		if (!session.isLoggedIn())
		{
			System.out.println("Not logged in");
			return "index";
		}

		story.setPlatformUser(session.getPlatformUser());

		EntityManager em = session.getEm();
		em.getTransaction().begin();
		em.persist(story);
		em.getTransaction().commit();

		session.getPlatformUser().addStory(story);

		System.out.println("New story ok");
		return "index";
	}
	
	public String selectStory(Story activeStory)
	{
		System.out.println("selectStory()");
		session.setActiveStory(activeStory);
		
		return "show-events?faces-redirect=true";
	}

	public SessionBean getSession()
	{
		return session;
	}

	public void setSession(SessionBean session)
	{
		this.session = session;
	}

	public Story getStory()
	{
		return story;
	}

	public void setStory(Story story)
	{
		this.story = story;
	}
}
