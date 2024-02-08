package com.inv.inventory.requests;

import lombok.Data;

@Data
public class AddExtraTool {
	public String siteName;
	public String toolName;
	public String toolQuantity;
}
