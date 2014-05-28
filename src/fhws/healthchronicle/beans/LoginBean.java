package fhws.healthchronicle.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import fhws.healthchronicle.entities.PlatformUser;

@RequestScoped
@ManagedBean
public class LoginBean implements Serializable
{
	private static final long serialVersionUID = 1261120717489692759L;

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean session;

	public LoginBean()
	{
	}

	public String login()
	{
		if (session.isLoggedIn())
			return "index";

		TypedQuery<PlatformUser> query = session.getEm().createNamedQuery("getPlatformUser", PlatformUser.class);
		query.setParameter("email", session.getPlatformUser().getEmail());
		List<PlatformUser> resultList = query.getResultList();
		
		if (resultList != null && resultList.size() == 1 && resultList.get(0).getPassword().equals(session.getPlatformUser().getPassword()))
		{
			session.setLoggedIn(true);
			System.out.println("login ok");
			return "index";
		}

		System.out.println("login fail");
		return "login-failed";
	}

	public String logout()
	{
		session.setLoggedIn(false);
		System.out.println("logout ok");
		return "index";
	}

	public String register()
	{
		TypedQuery<PlatformUser> query = session.getEm().createNamedQuery("getPlatformUser", PlatformUser.class);
		query.setParameter("email", session.getPlatformUser().getEmail());
		List<PlatformUser> resultList = query.getResultList();

		if (resultList.size() != 0)
		{
			System.out.println("registration failed");
			return "";
		}

		session.getEm().getTransaction().begin();
		session.getEm().persist(session.getPlatformUser());
		session.getEm().getTransaction().commit();

		System.out.println("registration ok");
		return "registration-success";
	}

	public SessionBean getSession()
	{
		return session;
	}

	public void setSession(SessionBean session)
	{
		this.session = session;
	}
}
