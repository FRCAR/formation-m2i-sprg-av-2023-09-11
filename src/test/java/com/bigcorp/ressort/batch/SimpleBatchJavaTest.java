package com.bigcorp.ressort.batch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.bigcorp.batch.flow.FlowBatchConfiguration;

@SpringBatchTest
@SpringJUnitConfig(FlowBatchConfiguration.class)
public class SimpleBatchJavaTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void testStep4(@Autowired Job job) throws Exception {
		this.jobLauncherTestUtils.setJob(job);
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("step4");
		Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
	}
}