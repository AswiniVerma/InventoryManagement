package com.inv.inventory.requests;

import lombok.Data;

@Data
public class SearchToolRequest {
	public String name;
	public int quantity;
}
