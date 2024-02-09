package com.inv.inventory.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "login")
public class Login {
	@Id
	private int id;
	private String username;
	private String password;
}
