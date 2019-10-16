package com.hiddenma.mybatisFramework.handler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import java.lang.reflect.Parameter;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hiddenma.mybatisFramework.utils.JDBCUtils;
import com.hiddenma.mybatisFramework.utils.SQLUtils;
import com.hiddenma.mybatisFramework.annotation.SelfInsert;
import com.hiddenma.mybatisFramework.annotation.SelfParam;
import com.hiddenma.mybatisFramework.annotation.SelfSelect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 使用动态代理拦截接口上的sql语句<br>
 * 根据不同的sql语句调用<br>
 * 
 * @author Ma.jian
 *
 */
public class SelfInvocationHandler implements InvocationHandler {

	// 代理的真实对象
	private Object object;

	// 构造方法，给要代理的真实对象赋值
	public SelfInvocationHandler(Object object) {
		this.object = object;
	}

	/**
	 * @Param proxy 代理实例
	 * @param method 被调用的方法对象
	 * @param args   调用参数
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 判断方法上是否有SelfInsert注解
		SelfInsert selfInsert = method.getAnnotation(SelfInsert.class);
		if (selfInsert != null) {
			return insertSQL(selfInsert, method, args);
		}
		// 判断方法上是否有SelfSelect注解
		SelfSelect selfSelect = method.getAnnotation(SelfSelect.class);
		if (selfSelect != null) {
			return selectSQL(selfSelect, method, args);
		}
		return null;
	}

	// 执行插入语句
	private int insertSQL(SelfInsert selfInsert, Method method, Object[] args) {
		// 获取注解上的sql
		String insertSQL = selfInsert.value();
		// 获取方法上的参数
		Parameter[] parameters = method.getParameters();
		// 将方法上的参数放在map集合中
		ConcurrentHashMap<String, Object> parameterMap = getParameters(parameters, args);
		// 获取SQL语句上需要传递的参数
		String[] sqlParams = SQLUtils.sqlInsertParameter(insertSQL);
		List<Object> paramValues = new ArrayList<>();
		for (int i = 0; i < sqlParams.length; i++) {
			String str = sqlParams[i];
			Object object = parameterMap.get(str);
			paramValues.add(object);
		}
		// 将SQL语句替换成?
		String newSql = SQLUtils.parameQuestion(insertSQL, sqlParams);
		// 调用jdbc代码执行
		int insertResult = JDBCUtils.insert(newSql, false, paramValues);
		System.out.println("insertResult:" + insertResult);
		return insertResult;
	}

	private Object selectSQL(SelfSelect selfSelect, Method method, Object[] args) {
		try {
			// 获取查询SQL语句
			String selectSQL = selfSelect.value();
			// 将方法上的参数存放在map集合中
			Parameter[] parameters = method.getParameters();
			// 获取方法上的参数集合
			ConcurrentHashMap<String, Object> parameterMap = getParameters(parameters, args);
			// 获取SQL传递参数
			List<String> selectParams = SQLUtils.sqlSelectParameter(selectSQL);
			// 排序参数
			List<Object> paramValues = new ArrayList<>();
			for (int i = 0; i < parameters.length; i++) {
				String paramName = selectParams.get(i);
				Object object = parameterMap.get(paramName);
				paramValues.add(object);
			}
			// 将SQL语句替换成?
			String newSql = SQLUtils.parameQuestion(selectSQL, selectParams);
			// 调用jdbc代码查询
			ResultSet rs = JDBCUtils.query(newSql, paramValues);
			// 获取返回类型
			Class<?> returnType = method.getReturnType();
			if (!rs.next()) {
				return null;
			}
			// 指针向上移动
			rs.previous();
			// 实例化对象
			Object newInstance = returnType.newInstance();
			while (rs.next()) {
				for (String paramName : selectParams) {
					// 获取集合中的数据
					Object value = rs.getObject(paramName);
					// 查找对应属性
					Field field = returnType.getDeclaredField(paramName);
					// 设置允许私有访问
					field.setAccessible(true);
					// 赋值参数
					field.set(newInstance, value);
				}
			}
			return newInstance;
		} catch (InstantiationException | IllegalAccessException | SQLException | NoSuchFieldException
				| SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ConcurrentHashMap<String, Object> getParameters(Parameter[] parameters, Object[] args) {
		ConcurrentHashMap<String, Object> parameterMap = new ConcurrentHashMap<>();
		for (int i = 0; i < parameters.length; i++) {
			// 参数信息
			Parameter parameter = parameters[i];
			SelfParam selfParam = parameter.getAnnotation(SelfParam.class);
			// 参数名称
			String paramValue = selfParam.value();
			// 参数值
			Object object = args[i];
			parameterMap.put(paramValue, object);
		}
		return parameterMap;
	}
}
