package com.inv.inventory.response;

import lombok.Data;

@Data
public class ToolDetailsResponse {
	public String name;
	public String originalQuantity;
	public String curQuantity;
	public String sitetoolCount;
	public String lost;
	public String damaged;
	public String unrepair;
}
