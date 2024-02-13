package com.inv.inventory.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "permanentdamagedtool")
public class PermanentDamagedTool {
	@Id
	public int id;
	public String name;
	public String quantity;
	public String siteName;
	public String reason;
}
