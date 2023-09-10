package com.bigcorp.batch.ressort.batch;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SpringBatchSimpleApplicationWithXmlConfiguration {
	public static void main(String[] args) {
		// Spring Java config
		try (GenericXmlApplicationContext context = new GenericXmlApplicationContext(
				"spring-batch-beans-simple.xml")) {

			JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
			Job job = (Job) context.getBean("monPremierJob");
			System.out.println("Starting the batch job");
			try {
				Map<String, JobParameter<?>> parametersMap = new HashMap<>();
				long version = 4l;
				parametersMap.put("output.resource.name", new JobParameter<String>(
						"file:target/output/simple/simple-output-"
						+ LocalDate
								.now()
								.toString() + "-"
						+ version + ".csv", String.class));

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
