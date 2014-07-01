package fhws.healthchronicle.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;

import fhws.healthchronicle.entities.Story;

@RequestScoped
@ManagedBean
@NamedQuery(name = "deleteStory", query = "DELETE FROM Story s WHERE s.id = :storyId")
public class StoryBean implements Serializable
{
	private static final long serialVersionUID = 1L;

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
			return "index?faces-redirect=true";
		}

		story.setPlatformUser(session.getPlatformUser());

		EntityManager em = session.getEm();
		em.getTransaction().begin();
		em.persist(story);
		em.getTransaction().commit();

		session.getPlatformUser().addStory(story);

		System.out.println("New story ok");
		return "show-stories?faces-redirect=true";
	}

	public static void main(String[] args)
	{
		EntityManager em = Persistence.createEntityManagerFactory("common-entities").createEntityManager();

		em.getTransaction().begin();
		Story s = em.find(Story.class, 2l);
		em.remove(s);
		em.getTransaction().commit();
	}

	public String deleteStory(Long storyId)
	{
		System.out.println("deleteStory()");
		
		if (!session.isLoggedIn())
		{
			System.out.println("Not logged in");
			return "index?faces-redirect=true";
		}

		EntityManager em = session.getEm();

		try
		{
			em.getTransaction().begin();
			Story s = em.find(Story.class, storyId);
			em.remove(s);
			em.getTransaction().commit();

			deleteStoryById(storyId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			em.getTransaction().rollback();
		}

		return "show-stories?faces-redirect=true";
	}
	
	public void deleteStoryById(Long storyId)
	{
		int index = 0;
		
		for(Story s : session.getPlatformUser().getStories())
		{
			if (s.getId() == storyId)
			{
				session.getPlatformUser().getStories().remove(index);
				return;
			}
			
			index++;
		}
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
