package com.inv.inventory.service;

import java.util.List;
import java.util.Optional;

import com.inv.inventory.entity.DamagedTool;
import com.inv.inventory.entity.Login;
import com.inv.inventory.entity.LostTool;
import com.inv.inventory.entity.PermanentDamagedTool;
import com.inv.inventory.entity.Site;
import com.inv.inventory.entity.SiteHistory;
import com.inv.inventory.entity.Tool;
import com.inv.inventory.requests.AddExtraTool;
import com.inv.inventory.requests.ReceiveToolsRequest;
import com.inv.inventory.requests.RemoveTool;
import com.inv.inventory.requests.SearchToolRequest;
import com.inv.inventory.requests.SendToolsRequest;
import com.inv.inventory.requests.ToolFound;
import com.inv.inventory.requests.UpdateSiteRequest;
import com.inv.inventory.response.ToolDetailsResponse;

public interface InventoryService {
	//Tool
	
	public String addTool(Tool tool);
	public List<Tool> getAllTools();
	public String deleteTool(int id);
	public String deletePermanentDamagedTool(int id);
	public String addLostTool(LostTool tool);
	public List<LostTool> getAllLostTool();
	public String addDamagedTool(DamagedTool tool);
	public List<DamagedTool> getAllDamagedTool();
	public String addPermanentDamagedTool(PermanentDamagedTool tool);
	public List<PermanentDamagedTool> getAllPermanentDamagedTool();
	public ToolDetailsResponse toolDetails(String toolName);
	public String lostToolFound (ToolFound req);
	public String damagedToolRepaired (ToolFound req);
	public Tool searchTool(SearchToolRequest req);
	
	
	//Site
	public String updateToolQuantity(Tool newToolQuantity);
	public List<Site> getAllSites();
	public String addSite(Site site); 
	public String sendTools(SendToolsRequest sendToolsRequest);
	public String addExtraTool(AddExtraTool addExtraTool);
	public String removeTool(RemoveTool removeToolRequest);
	public String decreaseNumberOfTool(AddExtraTool extraTool);
	public String moveToHistory (SiteHistory site);
	public List<SiteHistory> getHistoryList();
	public Optional<Site> siteDetails(int id);
	public String deleteSite(int id);
	public String deleteSiteHistory(int id);
	public String updateSite(UpdateSiteRequest request);
	public String receiveTools(ReceiveToolsRequest request);
	
	//login service
	public String login (Login login);
}
