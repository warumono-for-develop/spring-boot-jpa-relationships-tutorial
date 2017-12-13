package com.warumono.converters;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.util.ObjectUtils;

import com.google.common.collect.Sets;
import com.warumono.enums.AbstractEnumeratable;

public abstract class AbstractEnumConverter<E extends AbstractEnumeratable<E>>
{
	protected String toDatabaseColumn(E attribute)
	{
		return ObjectUtils.isEmpty(attribute) ? null : attribute.getToDatabaseColumn(attribute);
	}
	
	@SuppressWarnings("hiding")
	protected <E extends AbstractEnumeratable<E>> E toEntityAttribute(E e, String dbData)
	{
		return ObjectUtils.isEmpty(dbData) ? null : e.getToEntityAttribute(dbData);
	}
	
	@Deprecated
	@SuppressWarnings("unchecked")
	protected String toDatabaseColumn(E ... attributes)
	{
		if(ObjectUtils.isEmpty(attributes))
		{
			return null;
		}
		
		Collection<String> attributeses = Sets.newHashSet();
		
		Arrays.stream(attributes).forEach(a -> attributeses.add(a.getToDatabaseColumn(a)));
		
		return String.join(",", attributeses);
	}
}
