package com.warumono.helpers;

import java.lang.reflect.WildcardType;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Component
public class SwaggerParameter
{
	@Autowired
	private TypeResolver typeResolver;
	
	public Docket docket(Boolean enabled, String prefix, Tag defaultTag, Tag ... otherTags)
	{
		String groupName = prefix.toString();
		String path = "/".concat(prefix).concat("/**");
		
		return docket(enabled, groupName, path, defaultTag, otherTags);
	}
	
	public Docket docket(Boolean enabled, String groupName, String path, Tag defaultTag, Tag ... otherTags)
	{
		List<ResponseMessage> responseMessages = responseMessages();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(enabled)
				.groupName(groupName)
				.apiInfo(apiInfo())
				.produces(produces())
				.select()
				//*
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.ant(path))
				/*/
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				//*/
				.build()
				.pathMapping("/")
				.useDefaultResponseMessages(Boolean.TRUE)
				.globalResponseMessage(RequestMethod.GET, responseMessages)
				.globalResponseMessage(RequestMethod.POST, responseMessages)
				.globalResponseMessage(RequestMethod.PUT, responseMessages)
				.globalResponseMessage(RequestMethod.DELETE, responseMessages)
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.alternateTypeRules(new AlternateTypeRule(typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)), typeResolver.resolve(WildcardType.class)))
				.tags(defaultTag, otherTags)
//				.globalOperationParameters(defaultParameters())
				
				.ignoredParameterTypes
				(
					Pageable.class
				);
	}
	
	private ApiInfo apiInfo()
	{
		return new ApiInfoBuilder()
				.title("API")
				.description("--------------------------------------------------<br/>API documents for <strong>JPA Relationship</strong>.")
				.contact(new Contact("warumono", "https://github.com/warumono-for-develop", "warumono.for.develop@gmail.com"))
				.version("1.0.0")
				.build();
	}
	
	private Set<String> produces()
	{
		return Sets.newHashSet
				(
//					MediaType.APPLICATION_XML_VALUE, 
					MediaType.APPLICATION_JSON_VALUE
				);
	}
	/* // for authorization
	private List<Parameter> defaultParameters()
	{
		return Lists.newArrayList
				(
					new ParameterBuilder()
						.name("Authorization")
						.description("Access Token")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.defaultValue("Bearer <access_token>")
						.required(Boolean.TRUE)
						.build()
				);
	}
	*/
	private List<ResponseMessage> responseMessages()
	{
		/*
		NoSuchRequestHandlingMethodException		404 (Not Found)
		HttpRequestMethodNotSupportedException		405 (Method Not Allowed)
		HttpMediaTypeNotSupportedException			415 (Unsupported Media Type)
		MissingServletRequestParameterException		400 (Bad Request)
		ServletRequestBindingException				400 (Bad Request)
		ConversionNotSupportedException				500 (Internal Server Error)
		TypeMismatchException						400 (Bad Request)
		HttpMessageNotReadableException				400 (Bad Request)
		HttpMessageNotWritableException				500 (Internal Server Error)
		MethodArgumentNotValidException				400 (Bad Request)
		MissingServletRequestPartException			400 (Bad Request)
		*/
		
		return Lists.newArrayList
		(
			// 200 클라이언트의 요청을 정상적으로 수행함
			new ResponseMessageBuilder()
				.code(HttpURLConnection.HTTP_OK)
				.message("SUCCESS")
				.responseModel(new ModelRef("Success"))
				.build(), 
			// 400  클라이언트의 요청이 부적절 할 경우 사용하는 응답 코드
			new ResponseMessageBuilder()
				.code(HttpURLConnection.HTTP_BAD_REQUEST)
				.message("BAD_REQUEST")
				.responseModel(new ModelRef("Error"))
				.build(), 
			// 401 클라이언트가 인증되지 않은 상태에서 보호된 리소스를 요청했을 때 사용하는 응답 코드
			//    (로그인 하지 않은 유저가 로그인 했을 때, 요청 가능한 리소스를 요청했을 때)
			new ResponseMessageBuilder()
				.code(HttpURLConnection.HTTP_FORBIDDEN)
				.message("FORBIDDEN")
				.responseModel(new ModelRef("Error"))
				.build(), 
			// 403 유저 인증상태와 관계 없이 응답하고 싶지 않은 리소스를 클라이언트가 요청했을 때 사용하는 응답 코드
			//     (403 보다는 400이나 404를 사용할 것을 권고. 403 자체가 리소스가 존재한다는 뜻이기 때문에)
			new ResponseMessageBuilder()
				.code(HttpURLConnection.HTTP_UNAUTHORIZED)
				.message("UNAUTHORIZED")
				.responseModel(new ModelRef("Error"))
				.build(), 
			// 405 클라이언트가 요청한 리소스에서는 사용 불가능한 Method를 이용했을 경우 사용하는 응답 코드
			new ResponseMessageBuilder()
				.code(HttpURLConnection.HTTP_NOT_ACCEPTABLE)
				.message("NOT_ACCEPTABLE")
				.responseModel(new ModelRef("Error"))
				.build(), 
			// 500 서버에 문제가 있을 경우 사용하는 응답 코드
			new ResponseMessageBuilder()
				.code(HttpURLConnection.HTTP_INTERNAL_ERROR)
				.message("INTERNAL_SERVER_ERROR")
				.responseModel(new ModelRef("Error"))
				.build()
		);
	}
}
