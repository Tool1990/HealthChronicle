package fhws.healthchronicle.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.primefaces.event.CellEditEvent;

import fhws.healthchronicle.entities.User;

@ManagedBean(name = "adminBean")
@ViewScoped
public class AdminBean {

	// ...?
	private final String PERSISTENCE_UNIT_NAME = "common-entities";

	// Injecting sessionBean
	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean session;

	public SessionBean getSession() {
		return session;
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}
    
	private final static String[] roles;  
	
	static {  
        roles = new String[3];  
        roles[0] = "admin";  
        roles[1] = "user"; 
        roles[2] = "moderator"; 
    }
	
	private SelectItem[] roleOptions;

	// Constructor
	public AdminBean() {
		roleOptions = createFilterOptions(roles); 
		
		users = new ArrayList<User>();
		
		// Get list of all users
		// get entitymanager
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		// Create a query
		Query q = em.createQuery("SELECT x FROM User x");
		users = (List<User>) q.getResultList();
	}
	
	private String newEmail;
	private String newPassword;
	private String newRole;	
	
	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewRole() {
		return newRole;
	}

	public void setNewRole(String newRole) {
		this.newRole = newRole;
	}

	private List<User> filteredUsers;
	
	public List<User> getFilteredUsers() {  
        return filteredUsers;  
    }  
  
    public void setFilteredUsers(List<User> filteredUsers) {  
        this.filteredUsers = filteredUsers;  
    }
    
    private SelectItem[] createFilterOptions(String[] data)  {  
        SelectItem[] options = new SelectItem[data.length + 1];  
  
        options[0] = new SelectItem("", "Auswählen");  
        for(int i = 0; i < data.length; i++) {  
            options[i + 1] = new SelectItem(data[i], data[i]);  
        }  
  
        return options;  
    }  
  
    public SelectItem[] getRoleOptions() {  
        return roleOptions;  
    }
    
    public String[] getRoles(){
		return roles;
	}

    public void onCellEdit(CellEditEvent event) {  
        Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue();  
        User user = users.get(event.getRowIndex());
        String columnKey = event.getColumn().getColumnKey();
          
        if(newValue != null && !newValue.equals(oldValue)) {  
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getColumn().getHeaderText()+" geändert", "von  " + oldValue + " auf " + newValue);  
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
          	EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    		EntityManager em = emf.createEntityManager();
    				
    		em.getTransaction().begin();
    		User toBeUpdated = em.merge(user);
    		
    		if(columnKey.equals("nameColumn")){
    			toBeUpdated.setName(String.valueOf(newValue));
    		}
    		if(columnKey.equals("passwordColumn")){
    			toBeUpdated.setPassword(String.valueOf(newValue));
    		}
    		if(columnKey.equals("roleColumn")){
    			toBeUpdated.setRole(String.valueOf(newValue));
    		}
    		em.getTransaction().commit();    		
    		
    	}  
    } 
    
    List<User> users;
	public List<User> getUsers() {
		return users;
	}
	
	public void deleteSelected(ActionEvent actionEvent) {  
		
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
        for(User user : selectedUsers){
    		em.getTransaction().begin();
    		User toBeRemoved = em.merge(user);
    		em.remove(toBeRemoved);
    		em.getTransaction().commit();
    		users.remove(user);
        }
        int length = selectedUsers.length;
        FacesMessage msg;
        if(length==1){
        	msg = new FacesMessage(FacesMessage.SEVERITY_INFO, length+" Eintrag gelöscht", "");  
        }
        else{
        	msg = new FacesMessage(FacesMessage.SEVERITY_INFO, length+" Einträge gelöscht", "");  
        }
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
	
	public void saveUser(ActionEvent actionEvent) {  		
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
    	User toBeSaved = new User();
    	toBeSaved.setName(this.newEmail);
    	toBeSaved.setPassword(this.newPassword);
    	toBeSaved.setRole(this.newRole);
    	em.persist(toBeSaved);
    	em.getTransaction().commit();
    	users.add(toBeSaved);
    	this.newEmail="";
    	this.newPassword="";
    	this.newRole="";
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "User gespeichert", toBeSaved.getName());        
        FacesContext.getCurrentInstance().addMessage(null, msg);        
    }
	
	private User[] selectedUsers;

	public User[] getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(User[] selectedUsers) {
		this.selectedUsers = selectedUsers;
	}  
		
}
