package org.service.membership.application.port.in;

import org.service.membership.application.port.in.FindMembershipCommand;
import org.service.membership.domain.Membership;

public interface FindMembershipUsecase {

	Membership findMembership(FindMembershipCommand command);
}
