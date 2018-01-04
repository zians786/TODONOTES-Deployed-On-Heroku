package com.bridgeit.dao;

import com.bridgeit.model.User;

public interface UserDao {

	Boolean loginValidate(User user);
	Boolean emailValidaton(String email);
	void registerUser(User user);
	int activateUser(int userId);
	String getPassword(String email);
	void resetPassword(User user);
	int getUserId(String userName);
	User getUserByEmailId(String email);
	User getUserByUserName(String userName);
	User getUserByUserId(int userId);
}
