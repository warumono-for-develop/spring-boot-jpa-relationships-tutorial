package com.warumono.entities.many2many.embeddables;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@RequiredArgsConstructor(staticName = "on")
@AllArgsConstructor(staticName = "of")
@Setter
@Getter
@Builder
@ToString
@Table
(
	name = "PROFILE_ACCEPTANCE"
)
@EntityListeners(value = AuditingEntityListener.class)
@Entity
public class ProfileAcceptance
{
	@NonNull
	@EmbeddedId
	private ProfileAcceptanceId primaryKey;

	@Default
	@Column(updatable = true, nullable = false, columnDefinition = "BOOLEAN DEFAULT 0 COMMENT '수신동의 여부'")
	private Boolean accepted = Boolean.FALSE;

	@LastModifiedDate
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(updatable = true, nullable = true, columnDefinition = "DATETIME COMMENT '수정일시'")
	private Date updatedAt;

	@CreatedDate
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(updatable = false, nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시'")
	private Date registedAt;
}
