package com.inv.inventory.requests;

import java.util.List;

import com.inv.inventory.entity.SentToolDetails;

import lombok.Data;

@Data
public class ReceiveToolsRequest {
	
	private int id;
	private String name;
	private String address;
	private String supervisor;
	private List<SentToolDetails> toolDetails;
}

	


