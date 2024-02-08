package com.inv.inventory.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "siteHistory")
public class SiteHistory {
	@Id
	private int id;
	private String name;
	private String address;
	private String supervisor;
	private List<SentToolDetails> toolDetails;
	

}
