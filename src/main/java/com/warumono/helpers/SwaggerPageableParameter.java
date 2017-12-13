package com.warumono.helpers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.warumono.constants.AppConstant.Swagger.Pagination;
import com.warumono.constants.AppConstant.Swagger.ParamType;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.ResolvedTypes;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

@Component
@Order(value = SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class SwaggerPageableParameter implements OperationBuilderPlugin
{
	private final TypeNameExtractor nameExtractor;
	private final TypeResolver resolver;
	private final ResolvedType pageableType;

	private final String defaultPage = "0";
	private final String defaultSize = "10";
	private final String defaultSort = Direction.ASC.name().toLowerCase();
	private final List<String> sizes = Lists.newArrayList("1", "5", defaultSize, "20", "50");

	@Autowired
	public SwaggerPageableParameter(TypeNameExtractor nameExtractor, TypeResolver resolver)
	{
		this.nameExtractor = nameExtractor;
		this.resolver = resolver;
		this.pageableType = resolver.resolve(Pageable.class);
	}

	@Override
	public void apply(OperationContext context)
	{
		List<ResolvedMethodParameter> methodParameters = context.getParameters();

		for(ResolvedMethodParameter methodParameter : methodParameters)
		{
			if(pageableType.equals(methodParameter.getParameterType()))
			{
				List<Parameter> parameters = Lists.newArrayList();
				
				ParameterContext parameterContext = new ParameterContext(methodParameter, new ParameterBuilder(), context.getDocumentationContext(), context.getGenericsNamingStrategy(), context);
				ModelContext modelContext = ModelContext.inputParam(parameterContext.getGroupName(), parameterContext.resolvedMethodParameter().getParameterType(), parameterContext.getDocumentationType(), parameterContext.getAlternateTypeProvider(), parameterContext.getGenericNamingStrategy(), parameterContext.getIgnorableParameterTypes());
				Function<ResolvedType, ? extends ModelReference> factory = ResolvedTypes.modelRefFactory(modelContext, nameExtractor);

				ModelReference integer = factory.apply(resolver.resolve(Integer.TYPE));
				ModelReference string = factory.apply(resolver.resolve(List.class, String.class));

				parameters.add(pageParameter(integer));
				parameters.add(sizeParameter(integer));
				parameters.add(sortParameter(string));
				
				context.operationBuilder().parameters(parameters);
			}
		}
	}

	@Override
	public boolean supports(DocumentationType delimiter)
	{
		return Boolean.TRUE;
	}
	
	private Parameter pageParameter(ModelReference integer)
	{
		return new ParameterBuilder()
				.name(Pagination.PAGE)
				.description("페이지 번호 (0..N). default '" + defaultPage + "'.")
				.parameterType(ParamType.QUERY)
				.modelRef(integer)
				.required(Boolean.FALSE)
				.defaultValue(defaultPage)
				.build();
	}
	
	private Parameter sizeParameter(ModelReference integer)
	{
		return new ParameterBuilder()
				.name(Pagination.SIZE)
				.description("페이지별 항목 수 (1..N). default '" + defaultSize + "'.")
				.parameterType(ParamType.QUERY)
				.modelRef(integer)
				.required(Boolean.FALSE)
				.allowableValues(new AllowableListValues(sizes, resolver.resolve(Integer.TYPE).getTypeName()))
				.defaultValue(defaultSize)
				.build();
	}
	
	private Parameter sortParameter(ModelReference string)
	{
		return new ParameterBuilder()
				.name(Pagination.SORT)
				.description("정렬방식 (,asc|desc). default '" + defaultSort + "'.")
				.parameterType(ParamType.QUERY)
				.modelRef(string)
				.required(Boolean.FALSE)
				.defaultValue(defaultSort)
				.allowMultiple(Boolean.TRUE)
				.build();
	}
}
