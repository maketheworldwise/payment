package org.service.banking.application.port.in;

import org.service.banking.domain.RegisterBankAccount;

public interface RegisterBankAccountUsecase {

	RegisterBankAccount registerBankAccount(RegisterBankAccountCommand command);
}
