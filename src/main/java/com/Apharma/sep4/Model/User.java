package com.Apharma.sep4.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 Model class for a User object. Used as an entity in JPA under the name of 'users'.
 
 @author 4X Data team
 @version 1.1 - 28.04.2022
 */
@Entity
@Table(name = "users")
public class User
{
	private @Id @GeneratedValue int id;
	private String name;
	private boolean admin;

	public User()
	{
	}

	public User(int id, String name, boolean admin)
	{
		this.id = id;
		setName(name);
		this.admin = admin;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public boolean isAdmin()
	{
		return admin;
	}

	public void setName(String name)
	{
		if (name != null && !name.isEmpty())
		{
			this.name = name;
		}
	}

	public void toggleAdmin()
	{
		admin = !admin;
	}
	
	/**
	 Overridden toString method to alter the default String representation of this class' objects.
	 */
	@Override public String toString()
	{
		return "User {" + "ID = " + id + ", name = '" + name + '\'' + ", admin = " + admin + '}';
	}
}
