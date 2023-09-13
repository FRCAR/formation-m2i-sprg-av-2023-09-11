package com.bigcorp.mongodb;

import java.time.LocalDate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bigcorp.mongodb.configuration.SpringMongoConfiguration;
import com.bigcorp.mongodb.dao.MongoCharacteristicsDao;
import com.bigcorp.mongodb.model.Characteristics;

public class MongoDbRepositoryLauncher {

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
				SpringMongoConfiguration.class)) {

			MongoCharacteristicsDao characteristicsDao = appContext.getBean(MongoCharacteristicsDao.class);

			Characteristics characteristics = new Characteristics();
			characteristics.setCreationDate(LocalDate.of(2023, 1, 1));
			characteristics.setName("Super objet");
			characteristics.setWeight(2_000_000);
			Characteristics savedCharacteristics = characteristicsDao.save(characteristics);

			System.out.println("Sauvegarde faite ! ");
		}

	}

}
