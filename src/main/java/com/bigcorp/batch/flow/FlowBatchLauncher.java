package com.bigcorp.batch.flow;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FlowBatchLauncher {
	public static void main(String[] args) {
		// Spring Java config
		try (AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
				FlowBatchConfiguration.class)) {

			JobLauncher jobLauncher = appContext.getBean(JobLauncher.class);
			Job job = (Job) appContext.getBean("simpleJob");
			System.out.println("Starting the batch job");
			try {
				Map<String, JobParameter<?>> parametersMap = new HashMap<>();
				String version = "2";
				parametersMap.put("version", new JobParameter<String>(
						version, String.class));

				JobParameters jobParameters = new JobParameters(parametersMap);
				JobExecution execution = jobLauncher.run(job, jobParameters);
				System.out.println("Job Status : " + execution.getStatus());
				System.out.println("Fin du main");
				if (execution.getStatus() == BatchStatus.COMPLETED) {
					System.exit(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Job failed");
			}
			System.exit(1);
		}
	}
}
