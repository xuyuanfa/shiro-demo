package com.xxx.shiro.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/admin")
public class ShiroController {
	
	@RequestMapping(value = "/testroles")//, method = RequestMethod.POST)
	@RequiresRoles("admin")
	@ResponseBody
	public void testroles(HttpServletRequest request, HttpServletResponse response , Model model) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("Admin Role");
		out.flush();
		out.close();
	}
	
}