package fhws.healthchronicle.entities;

import java.io.Serializable;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "getPlatformUser", query = "SELECT u FROM PlatformUser u WHERE email = :email")
public class PlatformUser implements Serializable
{
	@Id
	@GeneratedValue
	private Long id;
	// @Column(nullable = false)
	private String email;
	private String password;
	private Character gender = 'm';
	private Long weight;
	private Long height;
	private Integer birthyear;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "platformUser")
	private List<Story> stories = new ArrayList<Story>();
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Character getGender()
	{
		return gender;
	}

	public void setGender(Character gender)
	{
		this.gender = gender;
	}

	public Long getWeight()
	{
		return weight;
	}

	public void setWeight(Long weight)
	{
		this.weight = weight;
	}

	public Long getHeight()
	{
		return height;
	}

	public void setHeight(Long height)
	{
		this.height = height;
	}

	public Integer getBirthyear()
	{
		return birthyear;
	}

	public void setBirthyear(Integer birthyear)
	{
		this.birthyear = birthyear;
	}

	public List<Story> getStories()
	{
		System.out.println("getStories()");
		return stories;
	}

	public void setStories(List<Story> stories)
	{
		this.stories = stories;
	}

	public void addStory(Story story)
	{
		stories.add(story);
	}
}
