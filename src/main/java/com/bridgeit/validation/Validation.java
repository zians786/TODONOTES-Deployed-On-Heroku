package com.bridgeit.validation;

import org.springframework.stereotype.Component;

import com.bridgeit.model.User;

@Component
public class Validation {

	
//	
//public boolean userValidate(User user) {
//	boolean status=true;
//	String email="/^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$/;";
//	String number="^[0-9]{10}$";
//	String username="^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$";
//	String userNumber=Long.toString(user.getNumber());
//	
//	
//	if(!(user.getUserName()).matches(username)) {
//		System.out.println("user");
//		return false;
//	}
//	if(!(user.getEmail()).matches(email)) {
//		System.out.println("email");
//		return false;
//	}
//	if(!userNumber.matches(number)) {
//		System.out.println("number");
//		return false;
//	}
//	return status;
//}

public  boolean userValidate(User user) {
	String userNumber=user.getNumber();
	boolean valid = true;
	String phoneNumRegx = "^[0-9]{10}$";
	String nameRegx = "^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$";
	String emailRegx =   "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	if (!user.getUserName().matches(nameRegx)) {
		System.out.println("user");
		valid = false;
	} else

	if (!userNumber.matches(phoneNumRegx)) {
		System.out.println("number");
		valid = false;
	} else

	if (!user.getEmail().matches(emailRegx)) {
		System.out.println("email");
		valid = false;
	} else if (user.getPassword().length() < 7 || user.getPassword().length() > 16) {
		System.out.println("length");
		valid = false;
	} else
	{
		valid = true;
	}
	return valid;
}


	
}
