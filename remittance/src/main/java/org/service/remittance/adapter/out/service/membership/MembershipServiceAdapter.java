package org.service.remittance.adapter.out.service.membership;

import org.service.common.CommonHttpClient;
import org.service.common.ExternalAdapter;
import org.service.remittance.application.port.out.membership.MembershipPort;
import org.service.remittance.application.port.out.membership.MembershipStatus;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@ExternalAdapter
@RequiredArgsConstructor
public class MembershipServiceAdapter implements MembershipPort {

	private final CommonHttpClient membershipServiceHttpClient;

	@Value("${service.membership.url}")
	private String membershipServiceEndpoint;

	@Override
	public MembershipStatus getMembershipStatus(String membershipId) {
		String buildUrl = String.join("/", this.membershipServiceEndpoint, "membership", membershipId);
		try {
			String jsonResponse = membershipServiceHttpClient.sendGetRequest(buildUrl).body();

			ObjectMapper mapper = new ObjectMapper();
			Membership membership = mapper.readValue(jsonResponse, Membership.class);

			return new MembershipStatus(membership.getMembershipId(), membership.isValid());

		} catch (Exception e) {
			throw new RuntimeException("membership request failed");
		}
	}
}
