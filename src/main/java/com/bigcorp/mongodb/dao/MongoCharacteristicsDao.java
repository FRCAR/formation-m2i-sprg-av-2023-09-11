package com.bigcorp.mongodb.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bigcorp.mongodb.model.Characteristics;

@Repository
public interface MongoCharacteristicsDao extends CrudRepository<Characteristics, String> {

}
