package com.bigcorp.batch.ressort.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;

public class PurgeTasklet implements Tasklet {

	private Resource jsonOutput;

	public Resource getJsonOutput() {
		return jsonOutput;
	}

	public void setJsonOutput(Resource jsonOutput) {
		this.jsonOutput = jsonOutput;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		if (this.jsonOutput.exists()) {
			System.out.println("Suppression des documents.");
			this.jsonOutput.getFile().delete();
		}
		return RepeatStatus.FINISHED;

	}

}
