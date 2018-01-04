package com.bridgeit.dao;

import java.util.List;

import com.bridgeit.model.Label;
import com.bridgeit.model.Note;
import com.bridgeit.model.User;

public interface LabelDao {

	 void create(Label label);
	 void update(Label label);
	 Label read(int labelId);
	 List<Label> read(User user);
	 void delete(Label label);
}
