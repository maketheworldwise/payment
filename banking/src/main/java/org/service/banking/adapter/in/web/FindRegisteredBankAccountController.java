package org.service.banking.adapter.in.web;

import org.service.banking.application.query.FindRegisteredBankAccountQuery;
import org.service.banking.application.query.ro.RegisteredBankAccountRo;
import org.service.common.WebAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindRegisteredBankAccountController {

	private final FindRegisteredBankAccountQuery findRegisteredBankAccountQuery;

	@GetMapping(path = "/banking/account/{registeredBankAccountId}")
	public ResponseEntity<RegisteredBankAccountRo> getBankAccount(@PathVariable String registeredBankAccountId) {
		return ResponseEntity.ok(findRegisteredBankAccountQuery.getRegisteredBankAccount(registeredBankAccountId));
	}
}
