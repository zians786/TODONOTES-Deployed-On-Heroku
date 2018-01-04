
package com.bridgeit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.bridgeit.dao.UserDao;
import com.bridgeit.model.User;
import com.bridgeit.utility.Email;
import com.bridgeit.utility.JWT;
import com.bridgeit.validation.Encryption;
import com.fasterxml.jackson.databind.JsonNode;

@Component
@Transactional
public class ServiceImp implements Service {

	@Autowired
	UserDao userDao;

	@Autowired
	JWT jwt;

	@Autowired
	Encryption encrypt;

	@Autowired
	Email email;

	public String registrationValidate(User user) {
		user.setPassword(encrypt.encryptPassword(user.getPassword()));

		if (userDao.emailValidaton(user.getEmail())) {
			return "User Already Exist with this Email...";
		} else {
			userDao.registerUser(user);
			String id = Integer.toString(user.getUserId());
			String jwToken = jwt.jwtGenerator(id);

			email.registrationEmail(user.getEmail(), jwToken);

			return "Registration Successfull, Please check confirmation mail to Activate your Account";

		}

	}

	public String loginValidate(User user) {
		String jwToken;
		user.setPassword(encrypt.encryptPassword(user.getPassword()));
		if (userDao.loginValidate(user)) {
			int id = userDao.getUserId(user.getUserName());
			String uid = Integer.toString(id);
			jwToken = jwt.jwtGenerator(uid);
		} else {
			jwToken = null;
		}

		return jwToken;

	}

	public String verifyToken(String token) {
		String uid = jwt.jwtVerify(token);
		int userId = Integer.parseInt(uid);

		String result = null;

		if (userDao.activateUser(userId) > 0) {
			result = "Your Account has been Activated...\n login: "+"<a href='https://bridgeit-todonotes.herokuapp.com'>click here</a>\"";
		} else {
			result = "Invalid Token or User may not Registered with us..";
		}
		return result;
	}

	public int verifyTokenReset(String token) {
		return jwt.jwtVerifyToken(token);
	}

	
	
	public String forgetPassword(String userEmail) {
		String result = null;
		if (userDao.emailValidaton(userEmail)) {
			User user = userDao.getUserByEmailId(userEmail);
			String token=jwt.jwtGenerator(user.getUserId());
			email.forgetEmail(userEmail, token);
			result = "Password has been Sent to your email.";
		} else {
			result = "This email is not Register with us.";
		}
		return result;
	}


	@Override
	public String resetPassword(int userId,String password) {
		String result;
		User user=userDao.getUserByUserId(userId);
		
		if (user!=null) 
		{
			user.setPassword(encrypt.encryptPassword(password));
			
			userDao.resetPassword(user);
			result = "Your Password Reset Successull";
		} else {
			result = "Invalid Token or User may not Registered with us..";
		}
		return result;

	}
	
	
	@Override
	public String registerSocialAccountUser(JsonNode profile) {

		User user = userDao.getUserByEmailId((profile.get("email").asText()));

		if (user == null) {

			User user2 = new User();
			System.out.println(profile.get("picture").get("data").get("url"));
			user2.setUserName(profile.get("name").asText());
			user2.setEmail(profile.get("email").asText());
			user2.setProfilePicture(profile.get("picture").get("data").get("url").asText());
			user2.setActive(true);
			userDao.registerUser(user2);
			int userId = userDao.getUserId(user2.getUserName());
			String token = jwt.jwtGenerator(Integer.toString(userId));
			return token;
		} else {
			String userId = Integer.toString(user.getUserId());
			String token = jwt.jwtGenerator(userId);
			return token;
		}

	}

	@Override
	public User getUser(String userName) {
		
		return userDao.getUserByUserName(userName);
	}

	@Override
	public User getUserInfo(String token) {
		int userId=jwt.jwtVerifyToken(token);
		return userDao.getUserByUserId(userId);
	}



}
