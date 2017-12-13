package com.warumono.helpers;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageableHandler implements HandlerMethodArgumentResolver
{
	@Override
	public boolean supportsParameter(MethodParameter methodParameter)
	{
		return methodParameter.getParameterType().equals(Pageable.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception
	{
		String limit = nativeWebRequest.getParameter("limit");
		int size = toInt(limit, 100);

		String offset = nativeWebRequest.getParameter("offset");
		int page = (toInt(offset, size) / size);
		
		String sortStr = nativeWebRequest.getParameter("sort");
		String orderStr = nativeWebRequest.getParameter("order");
		Sort sort = null;
		
		if(Boolean.logicalAnd(Objects.nonNull(sortStr), Objects.nonNull(orderStr)))
		{
			Direction direction = Direction.ASC.name().equalsIgnoreCase(orderStr) ? Direction.ASC : Direction.DESC;
			sort = new Sort(direction, sortStr);
		}
		
		return new PageRequest(page, size, sort);
	}

	private int toInt(String value, int defaultValue)
	{
		int resultValue = defaultValue;
		
		if(Objects.nonNull(value))
		{
			try
			{
				resultValue = Integer.parseInt(value);
			}
			catch(NumberFormatException ignore)
			{
				ignore.printStackTrace();
			}
		}
		
		return resultValue;
	}
}
