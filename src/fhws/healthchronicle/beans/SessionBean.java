package fhws.healthchronicle.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fhws.healthchronicle.entities.PlatformUser;

@SessionScoped
@ManagedBean
public class SessionBean implements Serializable
{
	private static final long	serialVersionUID	= -5907159821411980871L;
	private PlatformUser		user;
	private boolean				loggedIn			= false;

	public SessionBean()
	{
	}

	public PlatformUser getUser()
	{
		if (user == null)
			user = new PlatformUser();

		return user;
	}

	public void setUser(PlatformUser user)
	{
		this.user = user;
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