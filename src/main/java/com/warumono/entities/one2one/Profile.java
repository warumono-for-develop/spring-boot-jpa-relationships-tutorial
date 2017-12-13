package com.warumono.entities.one2one;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.warumono.entities.AuditingEntity;
import com.warumono.entities.User;
import com.warumono.entities.many2many.embeddables.ProfileAcceptance;
import com.warumono.entities.one2many.Mobile;

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
	name = "PROFILE", 
	uniqueConstraints = 
	{
		@UniqueConstraint(name = "UNIQ_IDENTITY_IN_PROFILE", columnNames = { "identity" })
	}
)
@Entity
public class Profile extends AuditingEntity
{
	@NonNull
	@Column(updatable = true, nullable = false, length = 20, columnDefinition = "VARCHAR(20) COMMENT '이름'")
	private String name;
	
	@Temporal(value = TemporalType.DATE)
	@Column(updatable = false, nullable = true, columnDefinition = "DATE COMMENT '생년월일'")
	private Date birthday;
	
	// ----- Relationships -----
	
	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(updatable = false, nullable = false, name = "user_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_USER_SEQ_IN_PROFILE"))
	private User host;

	@JsonManagedReference
	@Default
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "holder")
	private Collection<Mobile> mobiles = Sets.newHashSet();
	
	public void bind(Mobile mobile)
	{
		mobiles.add(mobile);
		mobile.setHolder(this);
	}
	
	@JsonProperty(value = "acceptances")
	@Default
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "primaryKey.profile")
	private Collection<ProfileAcceptance> profileAcceptances = Sets.newHashSet();
	
	public void bind(ProfileAcceptance profileAcceptance)
	{
		profileAcceptances.add(profileAcceptance);
	}
	
	public void unbind(ProfileAcceptance profileAcceptance)
	{
		profileAcceptances.remove(profileAcceptance);
	}
}
