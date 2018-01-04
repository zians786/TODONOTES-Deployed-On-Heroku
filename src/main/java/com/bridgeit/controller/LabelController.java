package com.bridgeit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.model.Label;
import com.bridgeit.model.Note;
import com.bridgeit.model.UserResponse;
import com.bridgeit.service.LabelService;
import com.bridgeit.service.NoteService;
import com.bridgeit.utility.JWT;

@RestController
public class LabelController {
	
	@Autowired
	LabelService labelService;

	@Autowired
	JWT jwtObject;
	
	@RequestMapping(value = "label/create", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> create(@RequestBody Label label, HttpServletRequest request) {
		String token = jwtObject.jwtVerify(request.getHeader("accToken"));
		
		UserResponse message=new UserResponse();
		
		if (token != null) {
			labelService.createLabel(label, token);
			message.setMessage("Label created Successfully.");
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);

		} else {
			message.setMessage("Invalid Token");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value = "label/delete", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> delete(@RequestBody Label label, HttpServletRequest request) {

		String token = jwtObject.jwtVerify(request.getHeader("accToken"));
		UserResponse message=new UserResponse();
		if (token != null) {
			labelService.deletelabel(label);
			message.setMessage("Label Deleted Successfully.");
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);

		} else {
			message.setMessage("Invalid Token");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}

	}
	
	

	@RequestMapping(value = "label/update", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> update(@RequestBody Label label, HttpServletRequest request) {

		String token = jwtObject.jwtVerify(request.getHeader("accToken"));
		UserResponse message=new UserResponse();
		if (token != null) {
			labelService.updateLabel(label, token);
			message.setMessage("Label Updated Successfully.");
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);

		} else {
			message.setMessage("Invalid Token");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}

	}
	
	
	@RequestMapping(value = "label/read", method = RequestMethod.POST)
	public ResponseEntity<List<Label>> read(HttpServletRequest request) {

		String token = jwtObject.jwtVerify(request.getHeader("accToken"));

		if (token != null) {
			List<Label> resultNote = labelService.readLabel(token);
			return new ResponseEntity<List<Label>>(resultNote, HttpStatus.OK);

		} else {
			List<Label> resultNote =null;
			return new ResponseEntity<List<Label>>(resultNote, HttpStatus.CONFLICT);
		}

	}


}
