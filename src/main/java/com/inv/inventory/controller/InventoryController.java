package com.inv.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
import com.inv.inventory.service.InventoryService;


@RestController
public class InventoryController {
	
	@Autowired
	InventoryService inventoryService;
	
	@PostMapping("/addTool")
	public String addTool(@RequestBody Tool tool) {
		return inventoryService.addTool(tool);
		
	}
	
	@GetMapping("/allTools")
	public List<Tool> getAllTools(){
		return inventoryService.getAllTools();
	}
	
	@PostMapping("/addSite")
	public String addSite(@RequestBody Site site) {
		return inventoryService.addSite(site);
	}
	@GetMapping("/allSites")
	public List<Site> getAllSites(){
		return inventoryService.getAllSites();
	}
	@PutMapping("/sendTools")
	public String sendTools(@RequestBody SendToolsRequest request) {
		inventoryService.sendTools(request);
		return "Updated";
	}
	@PostMapping("/addExtraTool")
	public String addExtraTool(@RequestBody AddExtraTool request) {
		
		return inventoryService.addExtraTool(request);
	}
	@PostMapping("/decreaseToolNumber")
	public String decreaseToolNumber(@RequestBody AddExtraTool request) {
		
		return inventoryService.decreaseNumberOfTool(request);
	}
	
	@PostMapping("/removeTool")
	public String removeTool(@RequestBody RemoveTool request) {
		inventoryService.removeTool(request);
		return "tool removed";
	}
	
	@DeleteMapping("/deleteTool/{id}")
	public String deleteTool(@PathVariable int id) {
		
		return inventoryService.deleteTool(id);
	}
	
	@DeleteMapping("/deletePermanentDamagedTool/{id}")
	public String deletePermanentDamagedTool(@PathVariable int id) {
		
		return inventoryService.deletePermanentDamagedTool(id);
	}
	
	@PostMapping("/moveToHistory")
	public String moveToHistory(@RequestBody SiteHistory site) {
		return inventoryService.moveToHistory(site);
	}
	
	@GetMapping("/getHistory")
	public List<SiteHistory> getHistory(){
		return inventoryService.getHistoryList();
	}
	
	@GetMapping("/siteDetails/{id}")
	public Optional<Site> getSiteDetails (@PathVariable int id){
		return inventoryService.siteDetails(id);
	}
	
	@DeleteMapping("/deleteSite/{id}")
	public String deleteSite(@PathVariable int id) {
		inventoryService.deleteSite(id);
		return "Site deleted";
	} 
	
	@PutMapping("/updateSite")
	public String updateSite(@RequestBody UpdateSiteRequest request) {
		return inventoryService.updateSite(request);
	}
	
	@PutMapping("/receiveTools")
	public String receiveTools(@RequestBody ReceiveToolsRequest request) {
		return inventoryService.receiveTools(request);
	}
	
	@DeleteMapping("/deleteSiteHistory/{id}")
	public String deleteSiteHistory(@PathVariable int id) {
		inventoryService.deleteSiteHistory(id);
		return "Site History deleted";
	}
	
	@PutMapping("/updateToolList")
	public String updateToolList(@RequestBody Tool tool) {
		return inventoryService.updateToolQuantity(tool);
	}
	
	@PostMapping("/login")
	public String login (@RequestBody Login login) {
		return inventoryService.login(login);
	}
	
	@PostMapping("/lostTool")
	public String addLostTool(@RequestBody LostTool tool) {
		return inventoryService.addLostTool(tool);
	}
	
	@GetMapping("/getAllLostTool")
	public List<LostTool> getAllLostTool(){
		return inventoryService.getAllLostTool();
	}
	
	@PostMapping("/damagedTool")
	public String addDamagedTool(@RequestBody DamagedTool tool) {
		return inventoryService.addDamagedTool(tool);
	}
	
	@GetMapping("/getAllDamagedTool")
	public List<DamagedTool> getAllDamagedTool(){
		return inventoryService.getAllDamagedTool();
	}
	
	@PostMapping("/permanentDamagedTool")
	public String addPermanentDamagedTool(@RequestBody PermanentDamagedTool tool) {
		return inventoryService.addPermanentDamagedTool(tool);
	}
	
	@GetMapping("/getAllPermanentDamagedTool")
	public List<PermanentDamagedTool> getAllPermanentDamagedTool(){
		return inventoryService.getAllPermanentDamagedTool();
	}
	
	@GetMapping("/toolDetails/{toolName}")
	public ToolDetailsResponse toolDetails(@PathVariable String toolName) {
		return inventoryService.toolDetails(toolName);
	}
	
	@PutMapping("/toolFound")
	public String lostToolFound(@RequestBody ToolFound req) {
		return inventoryService.lostToolFound(req);                                                                                                    
	}
	
	@PutMapping("/toolRepaired")
	public String damagedToolRepaired(@RequestBody ToolFound req) {
		return inventoryService.damagedToolRepaired(req);                                                                                                    
	}
	
	@PostMapping("/searchTool")
	public Tool searchToolList(@RequestBody SearchToolRequest req) {
		return inventoryService.searchTool(req);
	}
}
