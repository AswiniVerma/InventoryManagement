package com.inv.inventory.entity;


import org.springframework.data.mongodb.core.mapping.Document;
//
//import com.inv.inventory.service.SequenceGeneratorService;

import jakarta.persistence.Id;

import lombok.Data;

@Data
@Document(collection = "tools")
public class Tool {
	 	@Id
	    private int id;


	private String name;
	private String originalquantity;
	private String curquantity;
}
