package com.bigcorp.batch.flow;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;

/**
 * Configuration d'une application Spring Batch avec un flux parallèle et des
 * tâches simplistes
 */
@Configuration
@EnableBatchProcessing
@PropertySource("classpath:/configuration-batch.properties")
public class FlowBatchConfiguration {

	@Value("${datasource.url}")
	private String dataSourceUrl;
	@Value("${datasource.driverClassName}")
	private String dataSourceDriverClassName;
	@Value("${datasource.username}")
	private String dataSourceUserName;
	@Value("${datasource.password}")
	private String dataSourcePassword;
	

	/**
	 * Crée un Job
	 * 
	 * @param jobRepository
	 * @return
	 */
	@Bean
	public Job simpleJob(JobRepository jobRepository) {
		String[] paramObligatoires = null;
		String[] paramFacultatifs = null;

		Step step1 = new DummyStep("step1");
		Step step2 = new DummyStep("step2");
		Step step3 = new DummyStep("step3");
		Step step4 = new DummyStep("step4");

		Flow flow1 = new FlowBuilder<SimpleFlow>("flow1")
				.start(step1)
				.next(step2)
				.build();

		Flow flow2 = new FlowBuilder<SimpleFlow>("flow2")
				.start(step3)
				.build();

		return new JobBuilder("simpleJob", jobRepository)
				.start(flow1)
				.split(new SimpleAsyncTaskExecutor())
				.add(flow2)
				.next(step4)
				.end()
				.build();
	}



	/**
	 * Crée une DataSource vers la base de données qui stocke les données de batch.
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(this.dataSourceDriverClassName);
		driverManagerDataSource.setUrl(this.dataSourceUrl);
		driverManagerDataSource.setUsername(this.dataSourceUserName);
		driverManagerDataSource.setPassword(this.dataSourcePassword);
		return driverManagerDataSource;
	}

	/**
	 * Gère les transactions
	 * 
	 * @param dataSource
	 * @return
	 */
	@Bean
	public JdbcTransactionManager transactionManager(DataSource dataSource) {
		return new JdbcTransactionManager(dataSource);
	}


}
