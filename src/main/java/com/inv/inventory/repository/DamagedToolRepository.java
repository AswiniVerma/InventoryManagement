package com.inv.inventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inv.inventory.entity.DamagedTool;

public interface DamagedToolRepository extends MongoRepository<DamagedTool, Integer> {

}
