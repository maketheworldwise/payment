package org.service.banking.application.port.out;

public interface GetMembershipPort {

	MembershipStatus getMembership(String membershipId);
}
