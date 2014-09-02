package fhws.healthchronicle.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fhws.healthchronicle.entities.PlatformUser;
import fhws.healthchronicle.entities.Story;

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
			session.setPlatformUser(resultList.get(0));
			session.setLoggedIn(true);

			System.out.println("login ok");
			return "index";
		}
		
		System.out.println("login fail");
		throw new ValidatorException(new FacesMessage("Wrong password or E-Mail"));
	}

	public String logout()
	{
		session.destroy();
		System.out.println("logout ok");
		return "index";
	}

	public String register()
	{
		// new user to refresh id
		PlatformUser p = new PlatformUser();
		p.setEmail(session.getPlatformUser().getEmail());
		p.setPassword(session.getPlatformUser().getPassword());
		p.setGender(session.getPlatformUser().getGender());
		p.setWeight(session.getPlatformUser().getWeight());
		p.setHeight(session.getPlatformUser().getHeight());
		p.setBirthyear(session.getPlatformUser().getBirthyear());
		p.setStories(new ArrayList<Story>());

		EntityManager em = session.getEm();

		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();

		System.out.println("registration ok");
		return "registration-success";
	}

	public void validateEmail(FacesContext context, UIComponent component, Object value) throws ValidatorException
	{
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(value.toString());
		
		if (!matcher.matches())
		{
			throw new ValidatorException(new FacesMessage("Enter valid E-Mail format"));
		}

		TypedQuery<PlatformUser> query = session.getEm().createNamedQuery("getPlatformUser", PlatformUser.class);
		query.setParameter("email", value.toString());
		List<PlatformUser> resultList = query.getResultList();

		if (resultList.size() != 0)
		{
			throw new ValidatorException(new FacesMessage("Email already exists"));
		}
	}
	
	public void validatePassword(FacesContext context, UIComponent component, Object value) throws ValidatorException
	{
		if (value.toString().length() < 3)
		{
			throw new ValidatorException(new FacesMessage("Password too short"));
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
}
