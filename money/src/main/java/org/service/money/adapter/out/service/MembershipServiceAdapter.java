package org.service.money.adapter.out.service;

import org.service.common.CommonHttpClient;
import org.service.money.application.port.out.GetMembershipPort;
import org.service.money.application.port.out.MembershipStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {

	private final CommonHttpClient commonHttpClient;

	private final String membershipServiceUrl;

	public MembershipServiceAdapter(
		CommonHttpClient commonHttpClient,
		@Value("${service.membership.url}") String membershipServiceUrl
	) {
		this.commonHttpClient = commonHttpClient;
		this.membershipServiceUrl = membershipServiceUrl;
	}

	@Override
	public MembershipStatus getMembership(String membershipId) {
		String url = String.join("/", membershipServiceUrl, "membership", membershipId);

		try {
			String jsonResponse = commonHttpClient.sendGetRequest(url).body();
			ObjectMapper mapper = new ObjectMapper();

			Membership membership = mapper.readValue(jsonResponse, Membership.class);

			if (!membership.isValid()) {
				return new MembershipStatus(membership.getMembershipId(), false);
			}

			return new MembershipStatus(membership.getMembershipId(), true);

		} catch (Exception e) {
			throw new RuntimeException("membership request failed");
		}
	}
}
