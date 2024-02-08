package com.inv.inventory.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.inv.inventory.entity.SiteHistory;

@Repository
public interface SiteHistoryRepository extends MongoRepository<SiteHistory, Integer> {
	SiteHistory findByName(String name);
	
	 
	   
	
}
