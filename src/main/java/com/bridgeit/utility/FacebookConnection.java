package com.bridgeit.utility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FacebookConnection {

	private final String faceBookClientId = "1585966938157751";
	private final String faceBookSecretId = "26c734e34250f8aa7372f477c893e445";
	private final String redirect_URI = "https://bridgeit-todonotes.herokuapp.com/social/fbConnect";

	public String getFacebookURL(String uId) throws Exception {
		String facebookLoginURL = "";

		facebookLoginURL = "http://www.facebook.com/dialog/oauth?" + "client_id=" + faceBookClientId + "&redirect_uri="
				+ URLEncoder.encode(redirect_URI, "UTF-8") + "&state=" + uId + "&response_type=code"
				+ "&scope=public_profile,email";

		return facebookLoginURL;
	}

	public String getfbAccessToken(String authCode) throws Exception {

		String fbAccTokenURL = "";
		try {
			fbAccTokenURL = "https://graph.facebook.com/v2.9/oauth/access_token?" + "client_id=" + faceBookClientId
					+ "&redirect_uri=" + URLEncoder.encode(redirect_URI, "UTF-8") + "&client_secret=" + faceBookSecretId
					+ "&code=" + authCode;
		} catch (Exception e) {

			e.printStackTrace();
		}

		ResteasyClient restCall = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = restCall.target(fbAccTokenURL);

		Form form = new Form();
		form.param("client_id", faceBookClientId);
		form.param("client_secret", faceBookSecretId);
		form.param("redirect_uri", redirect_URI);
		form.param("code", authCode);
		form.param("grant_type", "authorization_code");

		// using post request we are sending an data to web service and getting
		// json response back

		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(form));
		String facebookAccessToken = response.readEntity(String.class);

		ObjectMapper mapper = new ObjectMapper();

		String acc_token = null;

		try {

			acc_token = mapper.readTree(facebookAccessToken).get("access_token").asText();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return acc_token;
	}
	
	
public JsonNode getUserProfile(String fbaccessToken) {
		
		String fbgetUserURL = "https://graph.facebook.com/v2.9/me?access_token=" + fbaccessToken
				+ "&fields=id,name,email,picture";
		
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = restCall.target(fbgetUserURL);

		String headerAuth = "Bearer " + fbaccessToken;
		Response response = target.request()
				.header("Authorization", headerAuth)
				.accept(MediaType.APPLICATION_JSON)
				.get();
		
		String profile =  response.readEntity(String.class);
		
		ObjectMapper mapper=new ObjectMapper();
		
		JsonNode FBprofile = null;
		
		try {
			
			FBprofile = mapper.readTree(profile);
		
		} catch (IOException e) {
			
			e.printStackTrace();
	
		}
		
		restCall.close();
		
		return FBprofile;

}


	public String getSocialShareUrl(String title,String data) {
		String facebookSocialShareURL = "";

		facebookSocialShareURL = "https://www.facebook.com/dialog/feed?"  +"client_id=" + faceBookClientId +" &display=popup&amp;caption=An%20example%20caption"+"&link=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2F"+"&quote=Title: "+title+", Note: "+data+"&redirect_uri=https://bridgeit-todonotes.herokuapp.com/gohome";

		return facebookSocialShareURL;
	}
	

}
