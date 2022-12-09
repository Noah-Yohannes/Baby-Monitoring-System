package net.codejava.swing;

public abstract class User {
	public String Name, lname,Password;

	public User(String Fname, String Lname, String password) {
		Name = Fname;
		lname=Lname;
		Password = password;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	
	
}
