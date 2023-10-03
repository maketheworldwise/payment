package org.service.remittance.adapter.out.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "remittance_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemittanceRequestJpaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long remittanceRequestId;

	private String fromMembershipId;

	private String toMembershipId;

	private String toBankName;

	private String toBankAccountNumber;

	private int remittanceType; // 0: membership (internal), 1: bank (external)

	private int moneyAmount;

	private String status;

	public RemittanceRequestJpaEntity(
		String fromMembershipId,
		String toMembershipId,
		String toBankName,
		String toBankAccountNumber,
		int remittanceType,
		int moneyAmount,
		String status
	) {
		this.fromMembershipId = fromMembershipId;
		this.toMembershipId = toMembershipId;
		this.toBankName = toBankName;
		this.toBankAccountNumber = toBankAccountNumber;
		this.remittanceType = remittanceType;
		this.moneyAmount = moneyAmount;
		this.status = status;
	}
}
