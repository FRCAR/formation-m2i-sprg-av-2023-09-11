package com.bigcorp.batch.virement;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class MyJobExecutionListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Démarrage du job...");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			System.out.println("Job exécuté ! ");
		} else if (jobExecution.getStatus() == BatchStatus.FAILED) {
			System.out.println("Job échoué ! ");
		}
	}
}
