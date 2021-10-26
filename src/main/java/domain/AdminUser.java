package domain;

import javax.persistence.Entity;

@Entity
public class AdminUser extends User {

	public AdminUser(String userName, String userPass, String firstName, String lastName) {

		super(userName, userPass, firstName, lastName);

	}

}
