package com.hiddenma.mvcFramework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/testServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String message = "Hello world I am servlet";
	int count = 0;
	List<Integer> intList = new ArrayList<>();

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init===testServlet");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(message + ", doGet: " + ++count);
		resp.setContentType("text/html"); // 设置响应内容类型
		PrintWriter out = resp.getWriter(); // 设置逻辑实现
		out.println("<h1>" + message + "</h1>");
		out.print(count + "------" + intList.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
