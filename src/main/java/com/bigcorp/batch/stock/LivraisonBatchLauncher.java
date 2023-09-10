package com.bigcorp.batch.stock;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LivraisonBatchLauncher {
	public static void main(String[] args) {
		// Spring Java config
		try (AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
				LivraisonBatchConfiguration.class)) {

			JobLauncher jobLauncher = appContext.getBean(JobLauncher.class);
			Job job = (Job) appContext.getBean("livraisonJob");
			System.out.println("Démarrage du batch");
			try {
				Map<String, JobParameter<?>> parametersMap = new HashMap<>();
				String version = "6";
				parametersMap.put("version", new JobParameter<String>(
						version, String.class));

				JobParameters jobParameters = new JobParameters(parametersMap);
				JobExecution execution = jobLauncher.run(job, jobParameters);
				System.out.println("Job Status : " + execution.getStatus());
				System.out.println("Fin du batch");
				if (execution.getStatus() == BatchStatus.COMPLETED) {
					System.exit(0);
				}
			} catch (Exception e) {
				System.out.println("Echec du job");
				e.printStackTrace();
			}
			System.exit(1);
		}
	}
}
