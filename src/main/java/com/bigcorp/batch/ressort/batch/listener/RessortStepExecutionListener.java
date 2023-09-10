package com.bigcorp.batch.ressort.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class RessortStepExecutionListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("Avant le Step");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("StepExecution : " + stepExecution);
		long filterCount = stepExecution.getFilterCount();
		System.out.println("Items filtrés : " + filterCount);
		long skipCount = stepExecution.getSkipCount();
		System.out.println("Items passés : " + skipCount);
		return stepExecution.getExitStatus();

	}

}
