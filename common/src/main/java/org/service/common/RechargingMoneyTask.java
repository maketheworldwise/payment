package org.service.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RechargingMoneyTask { // increase money

	private String taskId;

	private String taskName;

	private String membershipId;

	private List<SubTask> subTaskList;

	private String toBankName; // corporate

	private String toBankAccountNumber; // corporate

	private int moneyAmount; // won
}
