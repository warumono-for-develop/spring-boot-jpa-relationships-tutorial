package com.warumono.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.warumono.enums.AcceptanceType;

@Converter
public class AcceptanceTypeConverter extends AbstractEnumConverter<AcceptanceType> implements AttributeConverter<AcceptanceType, String>
{
	@Override
	public String convertToDatabaseColumn(AcceptanceType attribute)
	{
		return toDatabaseColumn(attribute);
	}

	@Override
	public AcceptanceType convertToEntityAttribute(String dbData)
	{
		return toEntityAttribute(AcceptanceType.TERMS_OF_POLICY, dbData);
	}
}
