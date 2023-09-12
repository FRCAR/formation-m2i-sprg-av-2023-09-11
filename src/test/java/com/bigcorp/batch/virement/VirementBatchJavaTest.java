package com.bigcorp.batch.virement;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBatchTest
@SpringJUnitConfig(VirementBatchConfiguration.class)
public class VirementBatchJavaTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
	public void testJob(@Autowired Job job) throws Exception {
        this.jobLauncherTestUtils.setJob(job);

		Map<String, JobParameter<?>> parametersMap = new HashMap<>();
		String fileName = "target/" + LocalDate.now() + "-" + UUID.randomUUID();

		File outputFile = new File(fileName + ".csv");

		parametersMap.put("output.resource.name", new JobParameter<String>(
				outputFile.getAbsolutePath(), String.class));

		System.out.println(
				new FileSystemResource("input/demande-virement.csv").exists());

		JobParameters jobParameters = new JobParameters(parametersMap);

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

		Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());

		String expectedFilePath = this.getClass().getClassLoader().getResource(
				"expected/expected-output.csv").getFile();
		File expectedFile = new File(expectedFilePath);

		Assertions.assertTrue(expectedFile.exists());
		Assertions.assertTrue(outputFile.exists());

		Assertions.assertTrue(FileUtils.contentEquals(expectedFile, outputFile));
    }


}