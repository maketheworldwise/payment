package org.service.banking.application.port.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipStatus { // for only banking
	private String membershipId;
	private boolean isValid;
}
