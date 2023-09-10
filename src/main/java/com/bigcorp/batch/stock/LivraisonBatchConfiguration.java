package com.bigcorp.batch.stock;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;

/**
 * Configuration d'une application Spring Batch avec un flux parallèle et des
 * tâches simplistes
 */
@Configuration
@EnableBatchProcessing
@PropertySource("classpath:/configuration-batch.properties")
public class LivraisonBatchConfiguration {

	@Value("${datasource.url}")
	private String dataSourceUrl;
	@Value("${datasource.driverClassName}")
	private String dataSourceDriverClassName;
	@Value("${datasource.username}")
	private String dataSourceUserName;
	@Value("${datasource.password}")
	private String dataSourcePassword;
	



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
