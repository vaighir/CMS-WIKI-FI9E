package com.fi9e.rest.daotests;

import com.fi9e.rest.dao.UserDao;
import com.fi9e.rest.entity.Role;
import com.fi9e.rest.entity.User;

public class UserDaoTest {
	public static void main(String[] args) {
		UserDao ud = new UserDao();
		
		// 0. getUserById functional tests are part of functional tests nr. 1-3
		
		// 1. createUser functional test

		ud.createUser("Jackaf Sparrow", "john.smith@test.com", "password");
		ud.createUser("Katewr Johnson", "kate.johnson@test.com", "waefsd");

		User u1 = ud.getUserById(2);

		System.out.println("got user by id: " + u1);
		
		// 2. updateUser functional test

		u1.setEmail("new_email@tet.com");

		ud.updateUser(u1);
		
		User u2 = ud.getUserById(2);
		
		System.out.println("updated user = " + u2);
		
		// 3. deleteUserById functional test
		
		ud.deleteUserById(2);
		
		u2 = ud.getUserById(2);
		
		System.out.println("deleted user = " + u2);
	}

}

