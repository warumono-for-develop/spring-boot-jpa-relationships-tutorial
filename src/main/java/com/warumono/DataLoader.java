package com.warumono;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.warumono.entities.User;
import com.warumono.entities.many2many.Role;
import com.warumono.entities.many2many.embeddables.Acceptance;
import com.warumono.entities.many2many.embeddables.ProfileAcceptance;
import com.warumono.entities.many2many.embeddables.ProfileAcceptanceId;
import com.warumono.entities.one2many.Mobile;
import com.warumono.entities.one2one.Profile;
import com.warumono.enums.AcceptanceType;
import com.warumono.enums.Authority;
import com.warumono.enums.Provider;
import com.warumono.repositories.AcceptanceRepository;
import com.warumono.repositories.RoleRepository;
import com.warumono.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner
{
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AcceptanceRepository acceptanceRepository;
	
	@Autowired
	private UserRepository userRepository;

	private Collection<Role> roles(Authority ... authorities)
	{
		Collection<Role> roles = Sets.newHashSet();
		Arrays.asList(Authority.values()).forEach(a -> roles.add(Role.builder().authority(a).build()));
		
		return roles;
	}

	private Collection<Acceptance> acceptances()
	{
		Collection<Acceptance> acceptances = Sets.newHashSet();
		Arrays.asList(AcceptanceType.values()).forEach(a -> acceptances.add(Acceptance.builder().type(a).build()));
		
		return acceptances;
	}
	
	private User user(String username, String password, Provider provider)
	{
		return User.builder()
				.username(username)
				.password(password)
				.provider(provider)
				.build();
	}
	
	private Profile profile(String name)
	{
		return Profile.builder()
				.name(name)
				.build();
	}
	
	private Mobile mobile(String number)
	{
		return Mobile.builder()
				.number(number)
				.build();
	}
	
	private ProfileAcceptanceId profileAcceptanceId(Profile profile, Acceptance acceptance)
	{
		return ProfileAcceptanceId.builder()
				.profile(profile)
				.acceptance(acceptance)
				.build();
	}
	
	private ProfileAcceptance profileAcceptance(ProfileAcceptanceId profileAcceptanceId, Boolean accepted)
	{
		return ProfileAcceptance.builder()
				.primaryKey(profileAcceptanceId)
				.accepted(accepted)
				.build();
	}
	
	@Transactional
	@Override
	public void run(String... args) throws Exception
	{
		Collection<Role> roles = roles();
		roles = roleRepository.save(roles);
		
		Collection<Acceptance> acceptances = acceptances();
		acceptances = acceptanceRepository.save(acceptances);
		
		
		
		Profile profile = profile("warumono");
		
		Mobile mobile = mobile("01012345678");
		profile.bind(mobile);
		
		Acceptance acceptance = Iterables.getFirst(acceptances, null);
		
		ProfileAcceptanceId profileAcceptanceId = profileAcceptanceId(profile, acceptance);
		
		ProfileAcceptance profileAcceptance = profileAcceptance(profileAcceptanceId, Boolean.TRUE);
		profile.bind(profileAcceptance);
		
		Role role = Iterables.getFirst(roles, null);
		
		User user = user("user@me.com", "userpassword", Provider.APP);
		user.bind(role);
		user.bind(profile);
		
		try
		{
			user = userRepository.save(user);
			
			log.debug("{}", user);
		}
		catch(DataIntegrityViolationException ignore)
		{
			ignore.printStackTrace();
		}
	}
}
