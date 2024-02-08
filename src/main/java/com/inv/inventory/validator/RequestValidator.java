package com.inv.inventory.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.inv.inventory.entity.SentToolDetails;
import com.inv.inventory.entity.Site;
import com.inv.inventory.entity.Tool;

@Component
public class RequestValidator {
	
	

   
	public boolean addSite(Site site, List<Tool> toolList) {

		List<SentToolDetails> sentToolDetails = site.getToolDetails();
		List<String> toolNameList = new ArrayList<>();
		for (int i = 0; i < toolList.size(); i++) {
			toolNameList.add(toolList.get(i).getName());
		}
		List<String> sentToolNameList = new ArrayList<>();
		for (int i = 0; i < sentToolDetails.size(); i++) {
			sentToolNameList.add(sentToolDetails.get(i).getToolName());
		}
		boolean allToolsPresent = toolNameList.containsAll(sentToolNameList);
		return allToolsPresent;
	}
	
	public boolean modifyToolList(String toolName, List<Tool> toolList) {
		List<String> toolNameList = new ArrayList<>();
		for(int i =0; i<toolList.size(); i++) {
			toolNameList.add(toolList.get(i).getName());
		}
		if(toolNameList.contains(toolName)) {
			return true;
		}
		return false;
	}
	
	public boolean addTool(List<Tool> toolList, Tool tool) {
		List<String> nameList = new ArrayList<>();
		for(Tool x : toolList) {
			nameList.add(x.getName());
		}
		if(nameList.contains(tool.getName())) return true;
		else return false;
	}
}
