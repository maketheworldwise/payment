package org.service.banking.application.port.out;

import org.service.banking.adapter.out.external.bank.BankAccount;
import org.service.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {
	BankAccount getBankAccountInfo(GetBankAccountRequest request);
}
