package com.chanzor.persistence.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.chanzor.persistence.interceptor.LoginHandlerInterceptor;
import com.chanzor.persistence.interceptor.ParamValidateIntecepter;


@Configuration
public class Config extends WebMvcConfigurerAdapter {

	/**
	 * 拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new ParamValidateIntecepter()).addPathPatterns("/**");
	}

	/**
	 * 静态资源
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("*.js");
	}
	/**
	 * 文件上传
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("12800kb");
		factory.setMaxRequestSize("12800kb");
		return factory.createMultipartConfig();
	}
	/**
	 * 异常处理
	 * @return
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer () {
		return new EmbeddedServletContainerCustomizer() {
			
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/jsp/404.jsp"));
				container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/WEB-INF/jsp/500.jsp"));
			}
		};
	}
}
