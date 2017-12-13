package com.warumono.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.warumono.enums.Provider;

@Converter
public class ProviderConverter extends AbstractEnumConverter<Provider> implements AttributeConverter<Provider, String>
{
	@Override
	public String convertToDatabaseColumn(Provider attribute)
	{
		return toDatabaseColumn(attribute);
	}

	@Override
	public Provider convertToEntityAttribute(String dbData)
	{
		return toEntityAttribute(Provider.APP, dbData);
	}
}
