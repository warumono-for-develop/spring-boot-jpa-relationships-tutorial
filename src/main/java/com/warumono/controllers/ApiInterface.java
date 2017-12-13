package com.warumono.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.warumono.constants.AppConstant.RestfulApi.Prefix;
import com.warumono.constants.AppConstant.Swagger.DataType;
import com.warumono.constants.AppConstant.Swagger.ParamType;
import com.warumono.entities.User;
import com.warumono.entities.one2one.Profile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/")
@Api(value = "/", tags = { "ApiController" })
public interface ApiInterface
{
	@ApiOperation(value = "테스트", notes = "클라이언트와 서버 간의 통신을 확인한다.<br/>")
	@ApiImplicitParams
	({
		@ApiImplicitParam(name = "path", value = "path variable", dataType = DataType.STRING, paramType = ParamType.PATH, required = true), 
		@ApiImplicitParam(name = "param", value = "param variable", dataType = DataType.STRING, paramType = ParamType.QUERY, required = false)
	})
	@GetMapping(value = Prefix.TEST + "/{path}")
	ResponseEntity<Map<String, Object>> pingpong(@PathVariable(value = "path") String path, @RequestParam(value = "param", required = false) String param);
	
	@ApiOperation(value = "사용자 전체 목록", notes = "사용자 전체 목록을 조회한다.<br/>")
	@ApiImplicitParams({})
	@GetMapping(value = Prefix.APP + "/user")
	ResponseEntity<Collection<User>> all();

	@ApiOperation(value = "사용자 정보", notes = "사용자 정보를 조회한다.<br/>")
	@ApiImplicitParams({ @ApiImplicitParam(name = "identity", value = "identity", dataType = DataType.STRING, paramType = ParamType.PATH, required = true) })
	@GetMapping(value = Prefix.APP + "/user/{identity}")
	ResponseEntity<User> user(@PathVariable(value = "identity") String identity);
	
	@ApiOperation(value = "사용자 프로필 정보", notes = "사용자 프로필 정보를 조회한다.<br/>")
	@ApiImplicitParams({ @ApiImplicitParam(name = "identity", value = "identity", dataType = DataType.STRING, paramType = ParamType.PATH, required = true) })
	@GetMapping(value = Prefix.APP + "/user/{identity}/profile")
	ResponseEntity<Profile> profile(@PathVariable(value = "identity") String identity);
}
