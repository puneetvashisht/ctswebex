package com.cog;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cog.dao.UserDAO;
import com.cog.entity.User;

public class TestUser {
	
	UserDAO dao = new UserDAO();

//	@Test
	public void test() {
		
		User user = new User("Amit");
		boolean result = dao.addUser(user);
		assertTrue(result);
	}
	
//	@Test
	public void findUser() {	
		User user = dao.getUser(2);
		System.out.println(user);
		assertEquals("Same objects", "Amit", user.getName());
	}
	@Test
	public void changeUserName() {	
		User user = dao.changeUserName(2, "Sonia");
		System.out.println(user);
		assertEquals("Same objects", "Sonia", user.getName());
	}
	
	

}
