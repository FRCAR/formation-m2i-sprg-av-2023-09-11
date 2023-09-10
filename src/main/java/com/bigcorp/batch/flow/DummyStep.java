package com.bigcorp.batch.flow;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;

public class DummyStep implements Step {

	private String name;

	public DummyStep(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void execute(StepExecution stepExecution) throws JobInterruptedException {
		System.out.println(this.name + " commence ! ");
		try {
			Thread.sleep(4_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(this.name + " a terminé ! ");

		stepExecution.setExitStatus(ExitStatus.COMPLETED);
	}

}
