package com.warumono.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class AuditingEntity extends AuditingWithoutIdentityEntity
{
	@Column(updatable = false, nullable = false, length = 50, columnDefinition = "VARCHAR(50) COMMENT '클라이언트와 서버간의 고유 식별 키'")
	private String identity = UUID.randomUUID().toString();
}
