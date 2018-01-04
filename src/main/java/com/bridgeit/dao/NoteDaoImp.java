package com.bridgeit.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgeit.model.Note;
import com.bridgeit.model.User;

@Component
public class NoteDaoImp implements NoteDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	

	public void create(Note note) {
		
		getSession().save(note);
	}



	public void delete(Note note) {
		Query query=getSession().createQuery("delete Note where noteId='"+note.getNoteId()+"'");
		query.executeUpdate();		
	}

	
	

	public void update(Note note) {
		getSession().update(note);
		
	}

	
	public Note read(Note note) {
		Query query=getSession().createQuery("from Note where noteId='"+note.getNoteId()+"'");
		Note note1=(Note) query.uniqueResult();
		return note1;
		
		// TODO Auto-generated method stub
		
	}

	public List<Note> read(User user) {
		Query query=getSession().createQuery("from Note where userId='"+user.getUserId()+"'");
		List<Note> notes= query.list();
		
		Criteria criteria=getSession().createCriteria(Note.class);
		criteria.createAlias("sharedUser", "user");
		criteria.add(Restrictions.eq("user.userId", user.getUserId()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Note> notes1 = criteria.list();
		notes.addAll(notes1);
		
		return notes;
		
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	public void archive(Note note, int userId) {
	Query query=getSession().createQuery("select isArchived from Note where noteId='"+note.getNoteId()+"' and userId='"+userId+"' ");
	Boolean status=(Boolean) query.uniqueResult();
		if(status) {
			Query query2=getSession().createQuery("update Note set isArchived=false where noteId='"+note.getNoteId()+"' and userId='"+userId+"'");
			query2.executeUpdate();
		}else {
			Query query2=getSession().createQuery("update Note set isArchived=true where noteId='"+note.getNoteId()+"' and userId='"+userId+"'");
			query2.executeUpdate();
		}
	}


	@Override
	public void trash(Note note, int userId) {
		
		Query query=getSession().createQuery("select inTrash from Note where noteId='"+note.getNoteId()+"' and userId='"+userId+"' ");
		Boolean status=(Boolean) query.uniqueResult();
			if(status) {
				Query query2=getSession().createQuery("update Note set inTrash=false where noteId='"+note.getNoteId()+"' and userId='"+userId+"'");
				query2.executeUpdate();
			}else {
				Query query2=getSession().createQuery("update Note set inTrash=true where noteId='"+note.getNoteId()+"' and userId='"+userId+"'");
				query2.executeUpdate();
			}
	}


	@Override
	public void pin(Note note, int userId) {
		Query query=getSession().createQuery("select isPinned from Note where noteId='"+note.getNoteId()+"' and userId='"+userId+"' ");
		Boolean status=(Boolean) query.uniqueResult();
		if(status) {
			Query query2=getSession().createQuery("update Note set isPinned=false where noteId='"+note.getNoteId()+"' and userId='"+userId+"'");
			query2.executeUpdate();
		}else {
			Query query2=getSession().createQuery("update Note set isPinned=true where noteId='"+note.getNoteId()+"' and userId='"+userId+"'");
			query2.executeUpdate();
		}
	}


	@Override
	public void color(Note note, int userId) {
		Query query=getSession().createQuery("update Note set color='"+note.getColor()+"' where noteId='"+note.getNoteId()+"' and userId='"+userId+"'");
		query.executeUpdate();
	}


	@Override
	public void remind(Note note, int userId) {
		Query query=getSession().createQuery("update Note set reminder='"+note.getReminder()+"' where noteId='"+note.getNoteId()+"' and userId='"+userId+"'");
		query.executeUpdate();
	}


	@Override
	public Note read(int noteId) {
		Query query=getSession().createQuery("from Note where noteId='"+noteId+"'");
		Note note1=(Note) query.uniqueResult();
		return note1;
	
	}

}
