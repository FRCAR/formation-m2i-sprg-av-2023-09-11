package com.bigcorp.mongodb;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

public class MongoClientConnectionExample {

	public static void main(String[] args) {
		String uri = "mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=java";
		// Création des paramètres de connexion
		ServerApi serverApi = ServerApi.builder()
				.version(ServerApiVersion.V1)
				.build();
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(uri))
				.serverApi(serverApi)
				.build();
		// Connexion à la base de données
		try (MongoClient mongoClient = MongoClients.create(settings)) {
			MongoDatabase database = mongoClient.getDatabase("test");
			// Création d'un document à partir d'une Map
			Map<String, Object> map = new HashMap<>();
			map.put("nom", "Roger");
			map.put("age", 90);
			Document document = new Document(map);

			// Insertion dans la collection
			MongoCollection<Document> collection = database.getCollection("javacollection");
			InsertOneResult result = collection.insertOne(document);
		} catch (MongoException me) {
			System.err.println(me);
		}

	}

}