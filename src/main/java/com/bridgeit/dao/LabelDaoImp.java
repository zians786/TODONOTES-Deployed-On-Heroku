package com.bridgeit.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgeit.model.Label;
import com.bridgeit.model.Note;
import com.bridgeit.model.User;

@Component
public class LabelDaoImp implements LabelDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	

	@Override
	public void create(Label label) {
	getSession().save(label);	
	}

	@Override
	public void update(Label label) {
		getSession().update(label);
	}

	@Override
	public Label read(int labelId) {
		Query query=getSession().createQuery("from Label where labelId='"+labelId+"'");
		Label label=(Label) query.uniqueResult();
		return label;
	}

	@Override
	public List<Label> read(User user) {
	
/*		Criteria criteria=getSession().createCriteria(Label.class);
		criteria.add(Restrictions.eq("userId",user.getUserId()));
		List<Label> labels=criteria.list();
*/
		Query query=getSession().createQuery("from Label where userId='"+user.getUserId()+"'");
		List<Label> labels=(List<Label>) query.list();
		return labels;
	}

	@Override
	public void delete(Label label) {
		getSession().delete(label);
		

	}

}
