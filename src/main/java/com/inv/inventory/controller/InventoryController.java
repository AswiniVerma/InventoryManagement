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

import com.inv.inventory.entity.Site;
import com.inv.inventory.entity.SiteHistory;
import com.inv.inventory.entity.Tool;
import com.inv.inventory.requests.AddExtraTool;
import com.inv.inventory.requests.ReceiveToolsRequest;
import com.inv.inventory.requests.RemoveTool;
import com.inv.inventory.requests.SendToolsRequest;
import com.inv.inventory.requests.UpdateSiteRequest;
import com.inv.inventory.service.InventoryService;


@RestController
public class InventoryController {
	
	@Autowired
	InventoryService inventoryService;
	
	@PostMapping("/addTool")
	public String addTool(@RequestBody Tool tool) {
		inventoryService.addTool(tool);
		return "Tool added";
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
		inventoryService.deleteTool(id);
		return "Tool deleted";
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
	
}
