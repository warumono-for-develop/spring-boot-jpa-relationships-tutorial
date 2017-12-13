package com.warumono.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AcceptanceType implements AbstractEnumeratable<AcceptanceType>
{
	TERMS_OF_POLICY("TOP"), 
	PRIVACY_OF_POLICY("POP");
	
	private String dbData;

	@Override
	public String getToDatabaseColumn(AcceptanceType attribute)
	{
		return dbData;
	}

	@Override
	public AcceptanceType getToEntityAttribute(String dbData)
	{
		return Arrays.stream(AcceptanceType.values()).filter(e -> e.getDbData().equals(dbData)).findFirst().orElseThrow(null);
	}
}
