package com.warumono.entities.many2many;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.warumono.entities.AuditingEntity;
import com.warumono.enums.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Setter
@Getter
@Builder
@Table
(
	name = "ROLE", 
	uniqueConstraints = 
	{
		@UniqueConstraint(name = "UNIQ_IDENTITY_IN_ROLE", columnNames = { "identity" }), 
		@UniqueConstraint(name = "UNIQ_AUTHORITY_IN_ROLE", columnNames = { "authority" })
	}
)
//@AttributeOverride(name = "seq", column = @Column(name = "role_seq"))
@Entity
public class Role extends AuditingEntity
{
	@NonNull
	@Enumerated(value = EnumType.STRING)
	@Column(updatable = true, nullable = false, length = 5, columnDefinition = "VARCHAR(5) COMMENT '권한'")
	private Authority authority;
}
