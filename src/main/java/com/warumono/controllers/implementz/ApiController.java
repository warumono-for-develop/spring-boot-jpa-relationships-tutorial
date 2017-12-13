package com.warumono.controllers.implementz;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.warumono.controllers.ApiInterface;
import com.warumono.entities.User;
import com.warumono.entities.one2one.Profile;
import com.warumono.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ApiController implements ApiInterface
{
	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<Map<String, Object>> pingpong(@PathVariable String path, String param)
	{
		log.debug("pinpong");
		log.debug("path: {}", path);
		log.debug("param: {}", param);
		
		Map<String, Object> response = Maps.newHashMap();
		response.put("path", path);
		response.put("param", param);
		response.put("timestamp", new Date());
		
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<Collection<User>> all()
	{
		return ResponseEntity.ok(userRepository.findAll());
	}

	@Override
	public ResponseEntity<User> user(@PathVariable String identity)
	{
		return ResponseEntity.ok(userRepository.findByIdentity(identity));
	}

	@Override
	public ResponseEntity<Profile> profile(@PathVariable String identity)
	{
		User user = userRepository.findByIdentity(identity);
		Profile profile = null;
		
		if(Objects.nonNull(user))
		{
			profile = user.getProfile();
		}
		
		return ResponseEntity.ok(profile);
	}
}
