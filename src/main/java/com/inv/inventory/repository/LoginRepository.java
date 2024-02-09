package com.inv.inventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inv.inventory.entity.Login;

public interface LoginRepository  extends MongoRepository<Login, Integer>{

	Login findByUsername(String username);

}
