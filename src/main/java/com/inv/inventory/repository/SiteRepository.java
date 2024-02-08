package com.inv.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.inv.inventory.entity.SentToolDetails;
import com.inv.inventory.entity.Site;

@Repository
public interface SiteRepository extends MongoRepository<Site, Integer> {
	Site findByName(String name);
	
	 
	   
	
}
