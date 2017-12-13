package com.warumono.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;
import com.warumono.converters.PasswordEncodeConverter;
import com.warumono.converters.ProviderConverter;
import com.warumono.entities.many2many.Role;
import com.warumono.entities.one2one.Profile;
import com.warumono.enums.Provider;

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
	name = "USER", 
	uniqueConstraints = 
	{
		@UniqueConstraint(name = "UNIQ_IDENTITY_IN_USER", columnNames = { "identity" }), 
		@UniqueConstraint(name = "UNIQ_USERNAME__PROVIDER_IN_USER", columnNames = { "username", "provider" })
	}
)
//@AttributeOverride(name = "seq", column = @Column(name = "user_seq"))
@Entity
public class User extends AuditingEntity
{
	@NonNull
	@Column(updatable = true, nullable = false, length = 30, columnDefinition = "VARCHAR(30) COMMENT '아이디'")
	private String username;

	@Convert(converter = PasswordEncodeConverter.class)
	@Column(updatable = true, nullable = true, length = 255, columnDefinition = "VARCHAR(255) COMMENT '비밀번호'")
	private String password;

	@NonNull
	@Convert(converter = ProviderConverter.class)
	@Column(updatable = false, nullable = false, length = 5, columnDefinition = "VARCHAR(5) COMMENT '정보 제공처'")
	private Provider provider;
	
	// ----- Relationships -----

	@Default
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable
	(
		name = "USER_ROLE", 
		joinColumns = @JoinColumn(name = "user_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_USER_SEQ_IN_USER")), 
		inverseJoinColumns = @JoinColumn(name = "role_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_ROLE_SEQ_IN_ROLE")), 
		foreignKey = @ForeignKey(name = "FKEY_USER_SEQ__ROLE_SEQ_IN_USER_ROLE")
	)
	private Collection<Role> roles = Sets.newHashSet();
	
	public void bind(Role role)
	{
		roles.add(role);
	}
	
	public void binds(Collection<Role> roles)
	{
		roles.addAll(roles);
	}

	@JsonManagedReference
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "host")
	@JoinColumn(name = "user_seq", referencedColumnName = "seq")
	private Profile profile;
	
	public void bind(Profile profile)
	{
		setProfile(profile);
		profile.setHost(this);
	}
}
