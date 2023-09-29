package org.service.banking.adapter.in.web;

import org.service.banking.application.port.in.RegisterBankAccountCommand;
import org.service.banking.application.port.in.RegisterBankAccountUsecase;
import org.service.banking.domain.RegisterBankAccount;
import org.service.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

	private final RegisterBankAccountUsecase registerBankAccountUsecase;

	@PostMapping("banking/account/register")
	public RegisterBankAccount registerBankAccount(@RequestBody RegisterBankAccountRequest request) {

		RegisterBankAccountCommand command = new RegisterBankAccountCommand(
			request.getMembershipId(),
			request.getBankName(),
			request.getBankAccountNumber(),
			request.isLinkedStatusIsValid()
		);

		return registerBankAccountUsecase.registerBankAccount(command);
	}
}
