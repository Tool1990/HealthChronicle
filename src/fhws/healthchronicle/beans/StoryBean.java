package fhws.healthchronicle.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;

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
		story = new Story();
	}

	public String newStory()
	{
		session.setActiveStory(null);
		return "new-story?faces-redirect=true";
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

			deleteStoryFromSession(storyId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		return "show-stories?faces-redirect=true";
	}

	public void deleteStoryFromSession(Long storyId)
	{
		int index = 0;

		for (Story s : session.getPlatformUser().getStories())
		{
			if (s.getId() == storyId)
			{
				session.getPlatformUser().getStories().remove(index);
				return;
			}

			index++;
		}
	}

	public String selectStory(Story s)
	{
		System.out.println("selectStory()");
		session.setActiveStory(s);

		return "show-events?faces-redirect=true";
	}
	
	public void switchCureState(ValueChangeEvent event)
	{
		if (!session.isLoggedIn())
		{
			System.out.println("Not logged in");
			return;
		}
		
		EntityManager em = session.getEm();
		Long storyId = session.getActiveStory().getId();
		Story s = em.find(Story.class, storyId);

		try
		{
			em.getTransaction().begin();
			
			if ((Boolean) event.getNewValue() == true)
			{
				s.setCured(true);
				session.getActiveStory().setCured(true);
			}
			else
			{
				s.setCured(false);
				session.getActiveStory().setCured(false);
			}
			
			em.getTransaction().commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			em.getTransaction().rollback();
		}
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
