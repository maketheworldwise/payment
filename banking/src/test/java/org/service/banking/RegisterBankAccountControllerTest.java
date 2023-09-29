package org.service.banking;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.service.banking.adapter.in.web.RegisterBankAccountRequest;
import org.service.banking.domain.RegisterBankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RegisterBankAccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void registerBankAccount() throws Exception {
		RegisterBankAccountRequest request = new RegisterBankAccountRequest("1", "name", "1234", true);

		RegisterBankAccount expect = RegisterBankAccount.generateRegisterBankAccount(
			new RegisterBankAccount.RegisterBankAccountId("1"),
			new RegisterBankAccount.MembershipId("1"),
			new RegisterBankAccount.BankName("name"),
			new RegisterBankAccount.BankAccountNumber("1234"),
			new RegisterBankAccount.LinkedStatusIsValid(true)
		);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/banking/account/register")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expect)));
	}
}