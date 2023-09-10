package com.bigcorp.mongodb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


@Configuration
//Active les repositories 
@EnableMongoRepositories("com.bigcorp.mongodb.dao")
public class SpringMongoConfiguration extends AbstractMongoClientConfiguration {

	/**
	 * Permet de configurer la connexion à la base de données MongoDB
	 */
	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create("mongodb://localhost:27017/supermarche");
	}

	/**
	 * Permet de configurer le nom de la base de données
	 */
	@Override
	protected String getDatabaseName() {
		return "supermarche";
	}

}
