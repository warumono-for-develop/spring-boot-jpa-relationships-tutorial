package com.warumono.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "seq")
@Getter
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class AuditingWithoutIdentityEntity
{
	@Id
	//* // for h2
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(updatable = false, nullable = false, columnDefinition = "LONG COMMENT '서버내의 고유 식별 키'")
	/*/ // for mysql
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false, columnDefinition = "BIGINT(10) UNSIGNED COMMENT '서버내의 고유 식별 키'")
	//*/
	private Long seq;

	@LastModifiedDate
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(updatable = true, nullable = true, columnDefinition = "DATETIME COMMENT '수정일시'")
	private Date updatedAt;

	@CreatedDate
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(updatable = false, nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'")
	private Date registedAt;
	
	@Override
	public String toString()
	{
		//*
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).toString();
		/*/
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendSuper(super.toString()).toString();
		//*/
	}
}
