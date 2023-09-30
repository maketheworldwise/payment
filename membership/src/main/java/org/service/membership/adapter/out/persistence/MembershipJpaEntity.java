package org.service.membership.adapter.out.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "membership")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long membershipId;

	private String name;

	private String email;

	private String address;

	private boolean isValid;

	private boolean isCorp;

	public MembershipJpaEntity(String name, String email, String address, boolean isValid, boolean isCorp) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.isValid = isValid;
		this.isCorp = isCorp;
	}

	@Override
	public String toString() {
		return "MembershipJpaEntity{" +
			"membershipId=" + membershipId +
			", name='" + name + '\'' +
			", address='" + address + '\'' +
			", email='" + email + '\'' +
			", isValid=" + isValid +
			", isCorp=" + isCorp +
			'}';
	}
}
