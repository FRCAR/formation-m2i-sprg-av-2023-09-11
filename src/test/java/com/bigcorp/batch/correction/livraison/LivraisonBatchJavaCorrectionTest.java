package com.bigcorp.batch.correction.livraison;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.bigcorp.batch.correction.LivraisonBatchConfigurationCorrection;

@SpringBatchTest
@SpringJUnitConfig(LivraisonBatchConfigurationCorrection.class)
public class LivraisonBatchJavaCorrectionTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

	private String version = "10-test";

    @Test
	public void testJob(@Autowired Job job) throws Exception {
        this.jobLauncherTestUtils.setJob(job);

		Map<String, JobParameter<?>> parametersMap = new HashMap<>();

		File outputFile = new File("target/output/livraison-" + this.version + ".csv");

		parametersMap.put("version", new JobParameter<String>(
				this.version, String.class));

		JobParameters jobParameters = new JobParameters(parametersMap);

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

		Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());

		String expectedFilePath = this.getClass().getClassLoader().getResource(
				"expected/expected-commande.csv").getFile();
		File expectedFile = new File(expectedFilePath);

		Assertions.assertTrue(expectedFile.exists());
		Assertions.assertTrue(outputFile.exists());

		Assertions.assertTrue(FileUtils.contentEquals(expectedFile, outputFile));
    }


}