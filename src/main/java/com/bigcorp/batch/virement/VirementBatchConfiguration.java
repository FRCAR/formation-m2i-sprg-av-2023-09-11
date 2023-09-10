package com.bigcorp.batch.virement;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.bigcorp.batch.virement.bean.DemandeVirement;
import com.bigcorp.batch.virement.bean.Virement;
import com.bigcorp.batch.virement.mapper.DemandeVirementFieldSetMapper;
import com.bigcorp.batch.virement.processor.VirementProcessor;

/**
 * Configuration de l'application Spring Batch
 */
@Configuration
@EnableBatchProcessing
@PropertySource("classpath:/configuration-batch.properties")
public class VirementBatchConfiguration {

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
	public Job virementJob(JobRepository jobRepository, Step transformationVirementStep) {
		return new JobBuilder("virementJob", jobRepository)
				.start(transformationVirementStep)
				.build();
	}

	/**
	 * Crée un Step
	 * 
	 * @param jobRepository
	 * @param transactionManager
	 * @param demandeVirementItemReader
	 * @param demandeVirementProcessor
	 * @param demandeVirementItemWriter
	 * @return
	 */
	@Bean
	public Step transformationVirementStep(JobRepository jobRepository,
			PlatformTransactionManager transactionManager,
			ItemReader<DemandeVirement> demandeVirementItemReader,
			ItemProcessor<DemandeVirement, Virement> demandeVirementProcessor,
			ItemWriter<Virement> demandeVirementItemWriter) {
		// StepBuilder construit des steps en enchaînant les appels de méthode
		return new StepBuilder("sampleStep", jobRepository)
				// chunk définit un "morceau" : la taille minimale de travail du batch
				.<DemandeVirement, Virement>chunk(10, transactionManager)
				// définition des reader, processor et writer
				.reader(demandeVirementItemReader)
				.processor(demandeVirementProcessor)
				.writer(demandeVirementItemWriter)
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

	/**
	 * Crée l'itemReader. Celui-ci a besoin d'un FieldSetMapper et d'un Tokenizer
	 * 
	 * @return
	 */
	@Bean
	public ItemReader<DemandeVirement> demandeVirementItemReader() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setNames("label", "accountFrom", "accountTo", "date", "amount");
		return new FlatFileItemReaderBuilder<DemandeVirement>()
				.resource(new FileSystemResource("input/demande-virement.csv"))
				.lineTokenizer(tokenizer)
				.fieldSetMapper(new DemandeVirementFieldSetMapper())
				.linesToSkip(1)
				.name("demandeVirementItemReader")
				.build();
	}

	/**
	 * Crée l'itemWriter via FlatFileItemWriter
	 * 
	 * @return
	 */
	@Bean
	@StepScope
	public ItemWriter<Virement> itemWriterViaFlatFileItemWriter(
			@Value("#{jobParameters['output.resource.name']}") String outputFileName) {
		DelimitedLineAggregator<Virement> lineAggregator = new DelimitedLineAggregator<Virement>();
		lineAggregator.setDelimiter(",");
		BeanWrapperFieldExtractor<Virement> fieldExtractor = new BeanWrapperFieldExtractor<Virement>();
		fieldExtractor.setNames(new String[] { "name", "from", "to", "date", "time", "amount" });
		lineAggregator.setFieldExtractor(fieldExtractor);
		FlatFileItemWriter<Virement> writer = new FlatFileItemWriterBuilder<Virement>()
				.resource(new FileSystemResource(outputFileName))
				.lineAggregator(lineAggregator)
				.append(true)
				.name("virementItemWriter")
				.shouldDeleteIfExists(true)
				.build();
		writer.open(new ExecutionContext());
		return writer;
	}

	/**
	 * Crée le processor
	 * 
	 * @return
	 */
	@Bean
	public ItemProcessor<DemandeVirement, Virement> virementProcessor() {
		return new VirementProcessor();
	}

}
