package com.chanzor.persistence.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamValidate {
	/**
	 * json格式字符串数组
	 * 默认参数
	 * isNum 是否为数字 (true or false)
	 * minLength 参数最小长度
	 * maxLength 参数最大长度
	 * name 参数名
	 * errorMsg 返回参数名称
	 * isEmail 是否邮箱 (true or false )
	 * isMobile 是否手机号 (true or false )
	 * 目前加入上述验证,可修改此验证,增加验证限制,配合springMVC 拦截器使用,
	 * 本例拦截器为 com.chanzor.interceptor.ParamValidateIntecepter
	 * @return
	 */
	String[] validateParam() default {};
	
}
