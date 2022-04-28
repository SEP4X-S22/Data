package Model;

public class User
{
	private int id;
	private String name;
	private boolean admin;
	
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
}
