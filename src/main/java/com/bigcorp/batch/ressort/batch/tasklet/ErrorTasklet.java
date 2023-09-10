package com.bigcorp.batch.ressort.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

public class ErrorTasklet extends PurgeTasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		System.out.println("Erreur grave.");
		return super.execute(contribution, chunkContext);

	}

}
