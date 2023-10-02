package org.service.money.application.port.out;

import org.service.common.RechargingMoneyTask;

public interface SendRechargingMoneyTaskPort {
	void sendRechargingMoneyTask(RechargingMoneyTask task);
}
