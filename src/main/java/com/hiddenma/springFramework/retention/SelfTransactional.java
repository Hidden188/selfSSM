package com.hiddenma.springFramework.retention;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
// @interface定义注解
public @interface SelfTransactional {
	
	int userId() default 0;
	
	String name() default "小明";
}
