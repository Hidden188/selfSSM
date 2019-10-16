package com.hiddenma.mvcFramework.servlet;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hiddenma.mvcFramework.annotation.SelfController;
import com.hiddenma.mvcFramework.annotation.SelfRequestMapping;
import com.hiddenma.mvcFramework.utils.ClassUtil;

/**
 * Servlet implementation class SelfDispatcherServlets
 */
@WebServlet("/selfDispatcherServlets")
public class SelfDispatcherServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private List<Class<?>> classNames = null;

	private ConcurrentHashMap<String, Object> mvcBeans = new ConcurrentHashMap<>();

	private ConcurrentHashMap<String, Object> urlBeans = new ConcurrentHashMap<>();

	private ConcurrentHashMap<String, Object> urlMethods = new ConcurrentHashMap<>();

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init===selfDispatcherServlet");
		// 获取当前包下的所有类
		classNames = ClassUtil.getClasses("com.hiddenma.mvcFramework.controller");
		// 将扫包范围所有的类，注入到springmvc容器里，存放在map集合里面，key默认为首字母小写
		doInstance();
		// 将url映射到方法进行关联
		handleMapping();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post===selfDispatcherServlet");
		// 获取请求url地址
		String requestUrl = req.getRequestURI();
		// 从map集合获取
		Object object = urlBeans.get(requestUrl);
		if (object == null) {
			resp.getWriter().println("Not found bean 404");
			return;
		}
		// 使用url地址获取方法
		String methodName = (String) urlMethods.get(requestUrl);
		if (methodName == null) {
			resp.getWriter().println("Not found method 404");
			return;
		}
		// 使用java反射机制调用方法
		String resultPage = (String) getResultPage(object, methodName);
		// 视图转换器渲染给页面
		viewDisplay(resultPage, req, resp);
	}

	// 视图展示
	private void viewDisplay(String resultPage, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String suffix = ".html";
		String prefix = "/";
		req.getRequestDispatcher(prefix + resultPage + suffix).forward(req, resp);
	}

	// 使用反射获取调用结果
	private Object getResultPage(Object object, String methodName) {
		Object result = null;
		try {
			Class<?> classInfo = object.getClass();
			Method method = classInfo.getMethod(methodName);
			result = method.invoke(object);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 扫包，将有controller注解的对象放入集合中
	private void doInstance() {
		try {
			for (Class<?> classInfo : classNames) {
				Annotation annotation = classInfo.getAnnotation(SelfController.class);
				if (annotation != null) {
					String className = ClassUtil.toLowerCaseFirstOne(classInfo.getName());
					Object obj = classInfo.newInstance();
					mvcBeans.put(className, obj);
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	// 将方法地址放入集合中
	private void handleMapping() {
		for (Entry<String, Object> bean : mvcBeans.entrySet()) {
			// bean的对象
			Object obj = bean.getValue();
			Class<?> classInfo = obj.getClass();
			SelfRequestMapping selfRequestMapping = classInfo.getAnnotation(SelfRequestMapping.class);
			String requestUrl = "";
			if (selfRequestMapping != null) {
				// 获取url的映射地址
				requestUrl = selfRequestMapping.value();
			}
			// 判断方法是否有url映射地址
			Method[] methods = classInfo.getDeclaredMethods();
			for (Method method : methods) {
				SelfRequestMapping methodMapping = method.getDeclaredAnnotation(SelfRequestMapping.class);
				if (methodMapping != null) {
					String methodUrl = requestUrl + methodMapping.value();
					urlBeans.put(methodUrl, obj);
					urlMethods.put(methodUrl, method.getName());
				}
			}
		}
	}
}
