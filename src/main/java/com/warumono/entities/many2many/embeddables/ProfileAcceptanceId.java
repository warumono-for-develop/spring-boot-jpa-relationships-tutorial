package com.warumono.entities.many2many.embeddables;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.warumono.entities.one2one.Profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Setter
@Getter
@Builder
@EqualsAndHashCode
@ToString
@Embeddable
public class ProfileAcceptanceId implements Serializable
{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(updatable = false, nullable = false, name = "profile_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_PROFILE_SEQ_IN_PROFILE"))
	private Profile profile;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(updatable = false, nullable = false, name = "acceptance_seq", referencedColumnName = "seq", foreignKey = @ForeignKey(name = "FKEY_ACCEPTANCE_SEQ_IN_ACCEPTANCE"))
	private Acceptance acceptance;
}
