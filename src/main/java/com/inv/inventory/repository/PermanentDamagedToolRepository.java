package com.inv.inventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inv.inventory.entity.PermanentDamagedTool;

public interface PermanentDamagedToolRepository extends MongoRepository<PermanentDamagedTool, Integer> {

}
