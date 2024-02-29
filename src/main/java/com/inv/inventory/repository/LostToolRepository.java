package com.inv.inventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inv.inventory.entity.LostTool;

public interface LostToolRepository extends MongoRepository<LostTool, Integer>{
	LostTool findByName(String name);
}
