package com.fi9e.rest.daotests;

import com.fi9e.rest.dao.UserDao;
import com.fi9e.rest.entity.Role;
import com.fi9e.rest.entity.User;

public class UserDaoTest {
	public static void main(String[] args) {
		UserDao ud = new UserDao();
		
		Role tempRole = new Role();

		ud.createUser("Jackaf Sparrow", "john.smith@test.com", "password", tempRole);
		ud.createUser("Katewr Johnson", "kate.johnson@test.com", "waefsd", tempRole);

		User u1 = ud.getUserById(15);

		System.out.println(u1);

		u1.setEmail("new_email@tet.com");

		ud.updateUser(u1);
	}

}
