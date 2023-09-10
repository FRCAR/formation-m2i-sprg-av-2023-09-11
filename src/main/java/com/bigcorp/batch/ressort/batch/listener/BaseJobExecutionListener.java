package com.bigcorp.batch.ressort.batch.listener;

import java.time.LocalDateTime;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class BaseJobExecutionListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("before base job " + jobExecution);
		jobExecution.getExecutionContext().put("base-job-debut", LocalDateTime.now().toString());

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("after base job " + jobExecution);
		jobExecution.getExecutionContext().put("base-job-fin", LocalDateTime.now().toString());
	}

}
