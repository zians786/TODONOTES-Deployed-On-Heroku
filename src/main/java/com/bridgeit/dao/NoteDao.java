package com.bridgeit.dao;

import java.util.List;

import com.bridgeit.model.Note;
import com.bridgeit.model.User;

public interface NoteDao {
	
 void create(Note note);
 void update(Note note);
 Note read(Note note);
 Note read(int noteId);
 List<Note> read(User user);
 void delete(Note note);
 void archive(Note note,int userId);
 void trash(Note note,int userId);
 void pin(Note note,int userId);
 void color(Note note,int userId);
 void remind(Note note,int userId);
 
}
