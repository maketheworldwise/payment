package org.service.money.adapter.out.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_money")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberMoneyJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberMoneyId;

	private String membershipId;

	private int balance;

	public MemberMoneyJpaEntity(
		String membershipId,
		int balance
	) {
		this.membershipId = membershipId;
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "MemberMoneyJpaEntity{" +
			"memberMoneyId=" + memberMoneyId +
			", membershipId='" + membershipId + '\'' +
			", balance=" + balance +
			'}';
	}
}
