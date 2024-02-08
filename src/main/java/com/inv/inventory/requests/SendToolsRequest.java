package com.inv.inventory.requests;

import java.util.List;

import com.inv.inventory.entity.SentToolDetails;

import lombok.Data;

@Data
public class SendToolsRequest {
	private String siteName;
	private List<SentToolDetails> sentToolDetails;
}
