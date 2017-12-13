package com.warumono.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.warumono.constants.AppConstant.RestfulApi.Prefix;
import com.warumono.helpers.SwaggerParameter;

import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration
{
	@Autowired
	private SwaggerParameter swaggerParameter;
	
	@Bean
	public Docket testAPI()
	{
		return swaggerParameter.docket(Boolean.TRUE, Prefix.TEST, new Tag("TEST", "Test API"), new Tag("V1", "Version 1.0"));
	}
	
	@Bean
	public Docket appAPI()
	{
		return swaggerParameter.docket(Boolean.TRUE, Prefix.APP, new Tag("APP", "Application API"), new Tag("V1", "Version 1.0"));
	}
}
