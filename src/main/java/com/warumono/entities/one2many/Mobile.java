package com.warumono.entities.one2many;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.warumono.entities.AuditingEntity;
import com.warumono.entities.one2one.Profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor(staticName = "on")
@AllArgsConstructor(staticName = "of")
@Setter
@Getter
@Builder
@Table
(
	name = "MOBILE", 
	uniqueConstraints = 
	{
		@UniqueConstraint(name = "UNIQ_IDENTITY_IN_MOBILE", columnNames = { "identity" }), 
		@UniqueConstraint(name = "UNIQ_NUMBER_IN_MOBILE", columnNames = { "number" })
	}
)
//@AttributeOverride(name = "seq", column = @Column(name = "mobile_seq"))
@Entity
public class Mobile extends AuditingEntity
{
	@NonNull
	@Column(updatable = true, nullable = false, length = 12, columnDefinition = "VARCHAR(12) COMMENT '전화번호'")
	private String number;
	
	// ----- Relationships -----

	@JsonBackReference
	@ManyToOne
	@JoinColumn(updatable = false, nullable = false, name = "profile_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_PROFILE_SEQ_IN_MOBILE"))
	private Profile holder;
}
