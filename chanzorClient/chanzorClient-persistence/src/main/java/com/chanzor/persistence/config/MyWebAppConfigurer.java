package com.chanzor.persistence.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.chanzor.util.PropertiesConfig;

@Configuration
public class MyWebAppConfigurer 
        extends WebMvcConfigurerAdapter {
	
	@Autowired
	public PropertiesConfig properties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/httpmapping/**").addResourceLocations("file:"+properties.getService_nginx_url());
        registry.addResourceHandler("/filemapping/**").addResourceLocations("file:"+properties.getFile_loc());
        super.addResourceHandlers(registry);
    }

}
