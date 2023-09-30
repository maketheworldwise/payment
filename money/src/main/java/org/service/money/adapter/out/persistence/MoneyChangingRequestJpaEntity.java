package org.service.money.adapter.out.persistence;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "money_changing_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingRequestJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long moneyChangingRequestId;

	private String targetMembershipId;

	private int changingType; // 0: increase, 1: decrease

	private int changingMoneyAmount;

	private int changingMoneyStatus; // 0: requested, 1: succeeded, 2: failed, 3: canceled

	private UUID uuid;

	private Date createdAt;

	public MoneyChangingRequestJpaEntity(
		String targetMembershipId,
		int changingType,
		int changingMoneyAmount,
		int changingMoneyStatus,
		UUID uuid,
		Date createdAt
	) {
		this.targetMembershipId = targetMembershipId;
		this.changingType = changingType;
		this.changingMoneyAmount = changingMoneyAmount;
		this.changingMoneyStatus = changingMoneyStatus;
		this.uuid = uuid;
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "MoneyChangingRequestJpaEntity{" +
			"moneyChangingRequestId=" + moneyChangingRequestId +
			", targetMembershipId='" + targetMembershipId + '\'' +
			", changingType=" + changingType +
			", changingMoneyAmount=" + changingMoneyAmount +
			", changingMoneyStatus=" + changingMoneyStatus +
			", uuid=" + uuid +
			", createdAt=" + createdAt +
			'}';
	}
}
