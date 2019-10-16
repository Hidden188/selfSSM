package com.hiddenma.mvcFramework.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hiddenma.mvcFramework.annotation.SelfController;
import com.hiddenma.mvcFramework.annotation.SelfRequestMapping;

@SelfController
@SelfRequestMapping("/self")
public class SelfUserController {

	@SelfRequestMapping("/test")
	public String test() {
		return "index";
	}

	@SelfRequestMapping("/test2")
	public void test2(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.getWriter().println("test2 method success!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
