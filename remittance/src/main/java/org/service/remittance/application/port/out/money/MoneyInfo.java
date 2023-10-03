package org.service.remittance.application.port.out.money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyInfo {

	private String membershipId;

	private int balance;
}
