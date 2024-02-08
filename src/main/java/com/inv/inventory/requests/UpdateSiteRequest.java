package com.inv.inventory.requests;

import lombok.Data;

@Data
public class UpdateSiteRequest {
	int id;
	public String siteName;
	public String address;
	public String supervisor;
}
