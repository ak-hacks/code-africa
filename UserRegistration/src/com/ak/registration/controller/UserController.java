package com.ak.registration.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.ak.registration.dao.UserDAO;
import com.ak.registration.form.User;
import com.ak.registration.jobpool.DataPersister;

/**
 * 
 * @author anuragkapur
 */
public class UserController extends MultiActionController {

	private UserDAO userDAO;
	private DataPersister dataPersister;
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public void setDataPersister(DataPersister dataPersister) {
		this.dataPersister = dataPersister;
	}

	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response, User user) throws Exception {
		dataPersister.persist(user);
		//TODO: Use logger
		System.out.println("Data submitted to task executor for async persistence");
		return new ModelAndView("redirect:list.htm");
	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//TODO: Use logger		
		System.out.println(Thread.currentThread().getId());
		System.out.println(Thread.currentThread().getName());
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("userList", userDAO.listUser());
		modelMap.addAttribute("user", new User());
		return new ModelAndView("form", modelMap);
	}
}
