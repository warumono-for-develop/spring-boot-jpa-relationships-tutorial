package com.warumono.configurations;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.warumono.SpringBootJpaRelationshipsTutorialApplication;
import com.warumono.constants.AppConstant.Package;

@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { Package.REPOSITORIES })
@EntityScan
(
	basePackageClasses = { SpringBootJpaRelationshipsTutorialApplication.class, Jsr310JpaConverters.class }, 
	basePackages = { Package.ENTITIES }
)
@Configuration
public class JpaConfiguration
{
	/* Empty code */
}
