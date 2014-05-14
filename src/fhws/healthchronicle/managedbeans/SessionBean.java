package fhws.healthchronicle.managedbeans;

import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class SessionBean
{
	public String	usersName;
	public String	canSeeChronicleOf;
	private boolean	guest	= true;
	private boolean	user	= false;
	private boolean	admin	= false;

	public SessionBean()
	{
		System.out.println("SessionBean started");
	}

	public String getUsersName()
	{
		return usersName;
	}

	public void setUsersName(String usersName)
	{
		this.usersName = usersName;
	}

	public String getCanSeeChronicleOf()
	{
		return canSeeChronicleOf;
	}

	public void setCanSeeChronicleOf(String canSeeChronicleOf)
	{
		this.canSeeChronicleOf = canSeeChronicleOf;
	}

	public boolean isGuest()
	{
		return guest;
	}

	public void setGuest(boolean guest)
	{
		this.guest = guest;
	}

	public boolean isUser()
	{
		return user;
	}

	public void setUser(boolean user)
	{
		this.user = user;
	}

	public boolean isAdmin()
	{
		return admin;
	}

	public void setAdmin(boolean admin)
	{
		this.admin = admin;
	}

	public boolean isLoggedIn()
	{
		return admin || user;
	}

	@PreDestroy
	public void cry()
	{
		System.out.println("SessionBean is about to be destroyed");
	}
}
