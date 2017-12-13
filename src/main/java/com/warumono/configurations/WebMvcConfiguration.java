package com.warumono.configurations;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.warumono.handlers.RestHandlerInterceptor;
import com.warumono.helpers.Jackson2HttpMessageConverter;
import com.warumono.helpers.PageableHandler;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport
{
	@Autowired
	private RestHandlerInterceptor restHandlerInterceptor;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(restHandlerInterceptor);
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
	{
		argumentResolvers.add(new PageableHandler());
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
	{
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new Jackson2HttpMessageConverter(jsonObjectMapper()).getJackson2HttpMessageConverter();
		
		converters.add(jackson2HttpMessageConverter);
	}
	
	@Bean
	public ObjectMapper jsonObjectMapper()
	{
		Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = Jackson2ObjectMapperBuilder.json();
		jackson2ObjectMapperBuilder.serializationInclusion(Include.USE_DEFAULTS);
		jackson2ObjectMapperBuilder.serializationInclusion(Include.NON_NULL);
		jackson2ObjectMapperBuilder.serializationInclusion(Include.NON_EMPTY);
		jackson2ObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// disabled
		jackson2ObjectMapperBuilder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		jackson2ObjectMapperBuilder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		jackson2ObjectMapperBuilder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// enabled
		jackson2ObjectMapperBuilder.featuresToEnable(SerializationFeature.INDENT_OUTPUT);
		jackson2ObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		jackson2ObjectMapperBuilder.featuresToEnable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		
		return jackson2ObjectMapperBuilder.build().registerModule(new Hibernate5Module());
	}
	
	@Bean
	public DefaultErrorAttributes errorAttributes()
	{
		return new DefaultErrorAttributes()
		{
			@Override
			public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace)
			{
				//*
				Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
				/*/
				Map<String, Object> errorAttributes = Maps.newHashMap();
				errorAttributes.put("ERROR_", requestAttributes.getClass().getName());
				//*/
				return errorAttributes;
			}
		};
	}
}
