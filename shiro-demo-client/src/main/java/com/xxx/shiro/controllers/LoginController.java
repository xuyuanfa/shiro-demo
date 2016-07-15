package com.xxx.shiro.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/login")
public class LoginController {
	
	@RequestMapping(value = "/login.do")//, method = RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response , Model model) throws ServletException, IOException {
		String result;
		result = login(request);
//		PrintWriter out = response.getWriter();
//		out.print(result);
//		out.flush();
//		out.close();
//		return "index";
		return result;
	}

	private String login(HttpServletRequest request) {
		String result = "please login";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if("true".equals(request.getSession().getAttribute("hasLogined"))){
			result = "already login";
			return result;
		}
		if("admin".equals(username) && "password".equals(password)){
			request.getSession().setAttribute("hasLogined", "true");
			result = "login successed";
		}
		return result;
	}
	


	@RequestMapping(value = "/loginOut.do")//, method = RequestMethod.POST)
	@ResponseBody
	public String loginOut(HttpServletRequest request, HttpServletResponse response , Model model) throws ServletException, IOException {
		String result;
		result = loginOut(request);
		return result;
	}

	private String loginOut(HttpServletRequest request) {
		String result;
		String username = request.getParameter("username");
		if(request.getSession().getAttribute("hasLogined") != null){
			request.getSession().removeAttribute("hasLogined");
			result = "logout successed";
		}else {
			result = "please login";
		}
		return result;
	}
	
	
	@RequestMapping(value = "/verify.do", method = RequestMethod.GET)
	@ResponseBody
	public String verify(HttpServletRequest request, HttpServletResponse response , Model model) throws ServletException, IOException {
		String result;
//		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		if("true".equals(request.getSession().getAttribute("hasLogined"))){
//			out.print("already logined");
			result = "already logined";
		}else {
//			out.print("please login");
			result = "please login";
		}
//		out.flush();
//		out.close();

		return result;
	}
	
}