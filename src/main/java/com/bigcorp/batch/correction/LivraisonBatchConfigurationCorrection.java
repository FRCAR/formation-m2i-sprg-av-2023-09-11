package com.bigcorp.batch.correction;

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

import com.bigcorp.batch.correction.bean.Livraison;
import com.bigcorp.batch.correction.bean.ProduitEnStock;
import com.bigcorp.batch.correction.mapper.ProduitEnStockFieldSetMapper;
import com.bigcorp.batch.correction.steppart.ProduitEnStockProcessor;

/**
 * Configuration d'une application Spring Batch avec un flux parallèle et des
 * tâches simplistes
 */
@Configuration
@EnableBatchProcessing
@PropertySource("classpath:/configuration-batch.properties")
public class LivraisonBatchConfigurationCorrection {

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
	public Job livraisonJob(JobRepository jobRepository,
			Step creationFichierLivraisonStep,
			Step miseAJourFichierLivraisonStep) {

		return new JobBuilder("livraisonJob", jobRepository)
				.start(creationFichierLivraisonStep)
				.next(miseAJourFichierLivraisonStep)
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

	@Bean
	public Step creationFichierLivraisonStep(JobRepository jobRepository,
			PlatformTransactionManager transactionManager,
			ItemReader<ProduitEnStock> produitEnStockItemReader,
			ItemProcessor<ProduitEnStock, Livraison> produitEnStockProcessor,
			ItemWriter<Livraison> livraisonItemWriter) {
		return new StepBuilder("creationFichierLivraison", jobRepository)
				.<ProduitEnStock, Livraison>chunk(10, transactionManager)
				.reader(produitEnStockItemReader)
				.processor(produitEnStockProcessor)
				.writer(livraisonItemWriter)
				.build();
	}

	@Bean
	public Step miseAJourFichierLivraisonStep(JobRepository jobRepository,
			PlatformTransactionManager transactionManager,
			ItemReader<ProduitEnStock> produitExterneItemReader,
			ItemProcessor<ProduitEnStock, Livraison> produitEnStockProcessor,
			ItemWriter<Livraison> livraisonItemWriter) {
		return new StepBuilder("miseAJourFichierLivraisonStep", jobRepository)
				.<ProduitEnStock, Livraison>chunk(10, transactionManager)
				.reader(produitExterneItemReader)
				.processor(produitEnStockProcessor)
				.writer(livraisonItemWriter)
				.build();
	}

	@Bean
	public ItemReader<ProduitEnStock> produitEnStockItemReader() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setNames("designation", "masse", "commande");
		return new FlatFileItemReaderBuilder<ProduitEnStock>()
				.resource(new FileSystemResource("input/stock.csv"))
				.lineTokenizer(tokenizer)
				.fieldSetMapper(new ProduitEnStockFieldSetMapper())
				.linesToSkip(1)
				.name("produitEnStockItemReader")
				.build();
	}

	@Bean
	public ItemProcessor<ProduitEnStock, Livraison> produitEnStockProcessor() {
		return new ProduitEnStockProcessor();
	}

	@Bean
	@StepScope
	public ItemWriter<Livraison> livraisonItemWriter(
			@Value("#{jobParameters['version']}") String version) {
		DelimitedLineAggregator<Livraison> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(",");
		BeanWrapperFieldExtractor<Livraison> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] { "nomProduit", "commande", "masse" });
		lineAggregator.setFieldExtractor(fieldExtractor);
		FlatFileItemWriter<Livraison> writer = new FlatFileItemWriterBuilder<Livraison>()
				.resource(new FileSystemResource("target/output/livraison-" + version + ".csv"))
				.lineAggregator(lineAggregator)
				.append(true)
				.name("livraisonItemWriter")
				.build();
		writer.open(new ExecutionContext());
		return writer;
	}

	@Bean
	public ItemReader<ProduitEnStock> produitExterneItemReader() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setNames("designation", "referenceInterne", "masse", "commande");
		return new FlatFileItemReaderBuilder<ProduitEnStock>()
				.resource(new FileSystemResource("input/stock-externe.csv"))
				.lineTokenizer(tokenizer)
				.fieldSetMapper(new ProduitEnStockFieldSetMapper())
				.linesToSkip(1)
				.name("produitExterneItemReader")
				.build();

	}

}
