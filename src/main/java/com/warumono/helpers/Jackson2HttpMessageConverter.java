package com.warumono.helpers;

import java.nio.charset.StandardCharsets;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Jackson2HttpMessageConverter
{
	private MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;
	
	public Jackson2HttpMessageConverter(ObjectMapper jsonObjectMapper)
	{
		this.jackson2HttpMessageConverter = jackson2HttpMessageConverter(jsonObjectMapper);
	}
	
	private MappingJackson2HttpMessageConverter jackson2HttpMessageConverter(ObjectMapper jsonObjectMapper)
	{
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jackson2HttpMessageConverter.setObjectMapper(jsonObjectMapper);
		jackson2HttpMessageConverter.setPrettyPrint(Boolean.TRUE);
		jackson2HttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
		
		return jackson2HttpMessageConverter;
	}
}
