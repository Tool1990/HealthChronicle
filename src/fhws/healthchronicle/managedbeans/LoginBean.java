package fhws.healthchronicle.managedbeans;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fhws.healthchronicle.entities.User;

@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean {

	// Injecting sessionBean
	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean session;

	public SessionBean getSession() {
		return session;
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}

	// Constants
	private final String PERSISTENCE_UNIT_NAME = "common-entities";

	// Variables
	private String name;

	private String password;

	private boolean loginFormVisible = false;

	public LoginBean() {
		loginFormVisible = false;
	}

	// Getters-Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoginFormVisible() {
		return loginFormVisible;
	}

	public void setLoginFormVisible(boolean loginFormVisible) {
		this.loginFormVisible = loginFormVisible;
	}

	public String makeLoginFormVisible() {
		loginFormVisible = true;
		return null;
	}

	public void login(ActionEvent actionEvent) {
		//if (name.length() > 0 && password.length() > 0) {
			EntityManagerFactory emf = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q = em.createQuery("SELECT x FROM User x WHERE x.name ='"
					+ name + "'");

			List<User> usersList = (List<User>) q.getResultList();

			if (usersList.size() > 1) {
				// Error: Should throw an Exception, because there cannot be
				// many users with same name
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fehler: Diese E-Mail Adresse ist schon vergeben!")); 
				return;
			} else if (usersList.size() == 0) {
				// None User with that name found, maybe there was a mistake in
				// the spelling, or the user is not registered
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Kein Account mit dieser E-mail registriert!", "wrongemail")); 
				return;
			} else {
				// One User found, now check if password is correct and if, then
				// set session data
				if (usersList.get(0).getPassword().equals(password)) {
					session.setUsersName(usersList.get(0).getName());
					session.setCanSeeChronicleOf(usersList.get(0).getName());
					session.setGuest(false);

					if (usersList.get(0).isAdmin()) {
						session.setAdmin(true);
						session.setUser(false);
					} else {
						session.setAdmin(false);
						session.setUser(true);
					}
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("MainMenu.xhtml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falsches Passwort", "wrongpass"));
				return;
			}
	}

	public String logout() {
		// debug
		session.setGuest(true);
		session.setAdmin(false);
		session.setUser(false);
		session.setUsersName(null);

		return "index.xhtml?faces-redirect=true";
	}
}