package model;

public class User {
		
	protected int UserId;
	protected String FirstName;
	protected String LastName;
	protected String Email;
	
	public User(int userId, String firstName, String lastName, String email) {
		UserId = userId;
		FirstName = firstName;
		LastName = lastName;
		Email = email;
	}

	public User(int userId) {
		super();
		UserId = userId;
	}
	
	

	public User(String firstName, String lastName, String email) {
		super();
		FirstName = firstName;
		LastName = lastName;
		Email = email;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Email == null) ? 0 : Email.hashCode());
		result = prime * result + ((FirstName == null) ? 0 : FirstName.hashCode());
		result = prime * result + ((LastName == null) ? 0 : LastName.hashCode());
		result = prime * result + UserId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Email == null) {
			if (other.Email != null)
				return false;
		} else if (!Email.equals(other.Email))
			return false;
		if (FirstName == null) {
			if (other.FirstName != null)
				return false;
		} else if (!FirstName.equals(other.FirstName))
			return false;
		if (LastName == null) {
			if (other.LastName != null)
				return false;
		} else if (!LastName.equals(other.LastName))
			return false;
		if (UserId != other.UserId)
			return false;
		return true;
	}
	
	

}
