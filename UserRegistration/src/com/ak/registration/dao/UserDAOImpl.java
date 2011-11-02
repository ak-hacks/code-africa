/**
 * 
 */
package com.ak.registration.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ak.registration.form.User;

/**
 * @author anuragkapur
 * 
 */
public class UserDAOImpl implements UserDAO {

	private HibernateTemplate hibernateTemplate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public void addUser(User user) {
		hibernateTemplate.saveOrUpdate(user);
	}

	public List<User> listUser() {
		return hibernateTemplate.find("from User");
	}
}
