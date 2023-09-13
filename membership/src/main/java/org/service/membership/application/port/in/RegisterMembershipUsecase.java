package org.service.membership.application.port.in;

import org.service.membership.domain.Membership;

public interface RegisterMembershipUsecase {

	Membership registerMembership(RegisterMembershipCommand command);
}
