package com.bigcorp.batch;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.bigcorp.batch.correction.LivraisonBatchConfigurationCorrection;

@SpringBatchTest
@SpringJUnitConfig(LivraisonBatchConfigurationCorrection.class)
public class LivraisonBatchJacquesJavaTest {
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void testJob(@Autowired Job job) throws Exception {
		this.jobLauncherTestUtils.setJob(job);
		File outputFile = new File("target/output/livraison-null.csv");
		System.out.println(
				new FileSystemResource("input/stock.csv").exists());
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
		String expectedFilePath = this.getClass().getClassLoader().getResource(
				"expected/expected-commande.csv").getFile();
		File expectedFile = new File(expectedFilePath);
		Assertions.assertTrue(expectedFile.exists());
		Assertions.assertTrue(outputFile.exists());
		Assertions.assertTrue(FileUtils.contentEquals(expectedFile, outputFile));
	}
}