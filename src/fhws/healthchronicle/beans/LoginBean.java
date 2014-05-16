package fhws.healthchronicle.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fhws.healthchronicle.dao.EntityManagerFactoryBean;
import fhws.healthchronicle.entities.PlatformUser;

@RequestScoped
@ManagedBean
public class LoginBean implements Serializable
{
	private static final long	serialVersionUID	= 1261120717489692759L;

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean			session;

	@PersistenceContext
	EntityManager				em;

	public LoginBean()
	{
	}

	public String login()
	{
		if (em == null)
			em = new EntityManagerFactoryBean().getEntityManager();

		TypedQuery<PlatformUser> query = em.createNamedQuery("getPlatformUser", PlatformUser.class);
		query.setParameter("email", session.getUser().getEmail());
		List<PlatformUser> resultList = query.getResultList();

		if (resultList != null && resultList.size() == 1 && resultList.get(0).getPassword().equals(session.getUser().getPassword()))
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
		if (em == null)
		{
			em = new EntityManagerFactoryBean().getEntityManager();
			System.out.println("new entity manager");
		}

		TypedQuery<PlatformUser> query = em.createNamedQuery("getPlatformUser", PlatformUser.class);
		query.setParameter("email", session.getUser().getEmail());
		List<PlatformUser> resultList = query.getResultList();

		if (resultList.size() != 0)
			return "Email ist bereits registriert";

		em.getTransaction().begin();
		em.persist(session.getUser());
		em.getTransaction().commit();

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

	public PlatformUser getUser()
	{
		return session.getUser();
	}

	public void setUser(PlatformUser user)
	{
		session.setUser(user);
	}

	public boolean isLoggedIn()
	{
		return session.isLoggedIn();
	}

	public void setLoggedIn(boolean loggedIn)
	{
		session.setLoggedIn(loggedIn);
	}

	public EntityManager getEm()
	{
		return em;
	}

	public void setEm(EntityManager em)
	{
		this.em = em;
	}
}
