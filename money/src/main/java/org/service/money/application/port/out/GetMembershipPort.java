package org.service.money.application.port.out;

public interface GetMembershipPort {

	MembershipStatus getMembership(String membershipId);
}
