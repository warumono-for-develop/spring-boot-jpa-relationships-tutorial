package com.warumono.enums;

public interface AbstractEnumeratable<E>
{
	String getToDatabaseColumn(E attribute);
	E getToEntityAttribute(String dbData);
}
