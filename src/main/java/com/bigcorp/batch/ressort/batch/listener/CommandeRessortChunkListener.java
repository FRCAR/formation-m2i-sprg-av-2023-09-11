package com.bigcorp.batch.ressort.batch.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class CommandeRessortChunkListener implements ChunkListener {

	@Override
	public void beforeChunk(ChunkContext context) {
		System.out.println("Before chunk");

	}

	@Override
	public void afterChunk(ChunkContext context) {
		System.out.println("After chunk");

	}

	@Override
	public void afterChunkError(ChunkContext context) {
		System.out.println("After chunk error");
	}

}
