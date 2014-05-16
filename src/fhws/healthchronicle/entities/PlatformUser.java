package fhws.healthchronicle.entities;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "getPlatformUser", query = "SELECT u FROM PlatformUser u WHERE email = :email")
public class PlatformUser
{
	@Id
	@GeneratedValue
	private Long		id;
	@Column(nullable = false)
	private String		email;
	private String		password;
	private char		gender		= 'm';
	private double		weight;
	private double		height;
	private int			birthyear	= 1990;
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "user")
	private List<Story>	stories		= new ArrayList<Story>();

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

	public char getGender()
	{
		return gender;
	}

	public void setGender(char gender)
	{
		this.gender = gender;
	}

	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public int getBirthyear()
	{
		return birthyear;
	}

	public void setBirthyear(int birthyear)
	{
		this.birthyear = birthyear;
	}

	public List<Story> getStories()
	{
		return stories;
	}

	public void setStories(List<Story> stories)
	{
		this.stories = stories;
	}
}
