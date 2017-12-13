package com.warumono.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface AbstractRepository<T> extends JpaRepository<T, Long>
{
	T findByIdentity(@Param(value = "identity") String identity);
}
