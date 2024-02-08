package com.inv.inventory.repository;




import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.inv.inventory.entity.Tool;

@Repository
public interface ToolRepository extends MongoRepository<Tool, Integer> {
	Tool findByName(String name);
	
}	
