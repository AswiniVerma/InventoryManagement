package com.inv.inventory.repository;




import org.springframework.data.mongodb.repository.MongoRepository;

import com.inv.inventory.entity.Tool;


public interface ToolRepository extends MongoRepository<Tool, Integer> {
	Tool findByName(String name);
	
}	
