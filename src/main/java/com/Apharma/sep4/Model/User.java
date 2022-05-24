package com.Apharma.sep4.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

	@Override public String toString()
	{
		return "User{" + "id = " + id + ", name = '" + name + '\'' + ", admin = " + admin + '}';
	}
}
