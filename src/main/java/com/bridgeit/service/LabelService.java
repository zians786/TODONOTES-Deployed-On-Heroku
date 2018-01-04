package com.bridgeit.service;

import java.util.List;

import com.bridgeit.model.Label;

public interface LabelService {
	

	void createLabel(Label label,String token);
	List<Label> readLabel(String token);
	void updateLabel(Label label,String token);
	void deletelabel(Label label);

}
