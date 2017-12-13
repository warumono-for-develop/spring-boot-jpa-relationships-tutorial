package com.warumono.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Provider implements AbstractEnumeratable<Provider>
{
	APP("APP"), 
	FACEBOOK("FBK"), 
	KAKAO("KKO");
	
	private String dbData;

	@Override
	public String getToDatabaseColumn(Provider attribute)
	{
		return dbData;
	}

	@Override
	public Provider getToEntityAttribute(String dbData)
	{
		return Arrays.stream(Provider.values()).filter(e -> e.getDbData().equals(dbData)).findFirst().orElseThrow(null);
	}
}
