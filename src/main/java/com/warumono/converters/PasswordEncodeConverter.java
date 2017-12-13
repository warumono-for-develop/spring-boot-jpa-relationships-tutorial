package com.warumono.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Converter
public class PasswordEncodeConverter implements AttributeConverter<String, String>
{
	@Override
	public String convertToDatabaseColumn(String attribute)
	{
		return StringUtils.isEmpty(attribute) ? null : new BCryptPasswordEncoder().encode(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData)
	{
		return dbData;
	}
}
