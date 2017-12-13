package com.warumono.entities.many2many.embeddables;

import java.util.Collection;

import javax.persistence.CascadeType;
//*
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.common.collect.Sets;
import com.warumono.converters.AcceptanceTypeConverter;
import com.warumono.entities.AuditingEntity;
import com.warumono.enums.AcceptanceType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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
	name = "ACCEPTANCE", 
	uniqueConstraints = 
	{
		@UniqueConstraint(name = "UNIQ_IDENTITY_IN_ACCEPTANCE", columnNames = { "identity" }), 
		@UniqueConstraint(name = "UNIQ_TYPE_IN_ACCEPTANCE", columnNames = { "type" })
	}
)
//@AttributeOverride(name = "seq", column = @Column(name = "acceptance_seq"))
@Entity
public class Acceptance extends AuditingEntity
{
	@NonNull
	@Convert(converter = AcceptanceTypeConverter.class)
	@Column(updatable = false, nullable = false, length = 5, columnDefinition = "VARCHAR(5) COMMENT '수신동의 종류'")
	private AcceptanceType type;
	
	@Default
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "primaryKey.acceptance")
	private Collection<ProfileAcceptance> profileAcceptances = Sets.newHashSet();
	
	public void bind(ProfileAcceptance profileAcceptance)
	{
		profileAcceptances.add(profileAcceptance);
	}
}
