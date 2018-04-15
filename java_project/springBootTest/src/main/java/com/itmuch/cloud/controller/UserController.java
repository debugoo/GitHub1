package com.itmuch.cloud.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.Dao.IUserDao;
import com.itmuch.cloud.entity.User;
import com.itmuch.cloud.ueditor.ActionEnter;

@RestController
public class UserController {

	@Autowired
	private IUserDao iUserDao;

	@GetMapping("/simple/{id}")
	public User finById(@PathVariable long id) {
		System.out.println("udss+" + id);
		return this.iUserDao.findOne(id);

	}
	 
	  @RequestMapping(value = "/ueditor/upload",method= {RequestMethod.GET,RequestMethod.POST})  
	    public void config(HttpServletRequest request, HttpServletResponse response) {  
		    response.setHeader("Content-Type" , "text/html");
	        String rootPath = request.getSession().getServletContext()  
	                .getRealPath("/");  
	        try {  
	            String exec = new ActionEnter(request, rootPath).exec();  
	            PrintWriter writer = response.getWriter();  
	            writer.write(exec);  
	            writer.flush();  
	            writer.close();  
	        } catch (IOException | JSONException e) {  
	            e.printStackTrace();  
	        }  
	    }  

}
