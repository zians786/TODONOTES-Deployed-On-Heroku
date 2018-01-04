package com.bridgeit.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.model.User;
import com.bridgeit.model.UserResponse;
import com.bridgeit.service.Service;
import com.bridgeit.utility.FacebookConnection;
import com.bridgeit.utility.GoogleConnection;
import com.bridgeit.utility.JWT;
import com.fasterxml.jackson.databind.JsonNode;



@RestController
public class SocialController {
	
	@Autowired
	FacebookConnection fbconnection;
	@Autowired
	GoogleConnection googleConnection;

	@Autowired
	Service userService;
	
	JWT jwtObject;
	
	
	@RequestMapping(value="social/fbLogin",method=RequestMethod.GET)
	public void facebookLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String uId=UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE",uId);
		String fbLoginURL=fbconnection.getFacebookURL(uId);
		response.sendRedirect(fbLoginURL);
		
	}
	
	
	@RequestMapping(value="social/fbConnect",method=RequestMethod.GET)
	public ResponseEntity<UserResponse> facebookConnect(HttpServletRequest request,HttpServletResponse response,HttpSession httpSession) throws Exception {
		String userSessionState=(String) request.getSession().getAttribute("STATE");
		String fbState=request.getParameter("state");
		
		if(userSessionState==null || !userSessionState.equals(fbState)){
			response.sendRedirect("socail/fbLogin");
		}
		UserResponse message=new UserResponse();
		String authCode=request.getParameter("code");
		String fbAccessToken=fbconnection.getfbAccessToken(authCode);
		
		JsonNode profile = fbconnection.getUserProfile(fbAccessToken);
		String token=userService.registerSocialAccountUser(profile);
		
		httpSession.setAttribute("accToken", token);
		response.sendRedirect("https://bridgeit-todonotes.herokuapp.com/#!/dummy");
	
		
		return new ResponseEntity<UserResponse>(message, HttpStatus.OK);
		}
	
	
	
	
	@RequestMapping(value="social/googleLogin",method=RequestMethod.GET)
	public void googleConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String unid=UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);

		String googleLogInURL=googleConnection.getGoogleURL(unid);


		response.sendRedirect(googleLogInURL);
		return;
	}

	@RequestMapping(value="social/connectGoogle",method=RequestMethod.GET)
	public String redirectFromGoogle(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) throws IOException {
		String sessionState = (String) request.getSession().getAttribute("STATE");
		String googlestate = request.getParameter("state");

		if (sessionState == null || !sessionState.equals(googlestate)) {

			response.sendRedirect("social/googleLogin");

		}
		String error = request.getParameter("error");

		// change this to the front end homepage address

		if (error != null && error.trim().isEmpty()) {

			response.sendRedirect("userlogin");

		}

		String authCode=request.getParameter("code");
	
		String googleaccessToken = googleConnection.getAccessToken(authCode);
	
		JsonNode profile = googleConnection.getUserProfile(googleaccessToken);


		String token=userService.registerSocialAccountUser(profile);
		
		httpSession.setAttribute("accToken", token);
		response.sendRedirect("https://bridgeit-todonotes.herokuapp.com/#!/dummy");

	
		return null;

	}
	
	@RequestMapping(value="/getToken",method=RequestMethod.POST)
	public ResponseEntity<UserResponse> getTokenAsResponse(HttpSession httpSession) {
		UserResponse message=new UserResponse();
		String accToken=(String) httpSession.getAttribute("accToken");
		httpSession.invalidate();
			message.setMessage(accToken);
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);
		}
	
	@RequestMapping(value="/socialShare/title/{title}/data/{data}",method=RequestMethod.GET)
	public void socialShare(HttpServletResponse response,@PathVariable String title,@PathVariable String data) throws Exception {
		response.sendRedirect(fbconnection.getSocialShareUrl(title,data));
	}
	
	@RequestMapping(value="/gohome",method=RequestMethod.GET)
	public void redirectToHome(HttpServletResponse response) throws IOException {
		response.sendRedirect("https://bridgeit-todonotes.herokuapp.com/#!/home");
	}
	
	
}


