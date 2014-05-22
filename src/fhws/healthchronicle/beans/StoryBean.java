package fhws.healthchronicle.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

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
		
//		story.setPlatformUser(session.getPlatformUser());
//		session.getEm().getTransaction().begin();
//		session.getEm().persist(story);
//		session.getEm().getTransaction().commit();

		List<Story> stories = session.getPlatformUser().getStories();
		stories.add(story);
		session.getPlatformUser().setStories(stories);
		session.getEm().getTransaction().begin();
		session.getEm().persist(session.getPlatformUser());
		session.getEm().getTransaction().commit();

		System.out.println("New story ok");
		return "index";
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
