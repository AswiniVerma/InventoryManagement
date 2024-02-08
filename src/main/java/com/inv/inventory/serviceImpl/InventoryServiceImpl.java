package com.inv.inventory.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.inv.inventory.entity.SentToolDetails;
import com.inv.inventory.entity.Site;
import com.inv.inventory.entity.SiteHistory;
import com.inv.inventory.entity.Tool;
import com.inv.inventory.repository.SiteHistoryRepository;
import com.inv.inventory.repository.SiteRepository;
import com.inv.inventory.repository.ToolRepository;
import com.inv.inventory.requests.AddExtraTool;
import com.inv.inventory.requests.ReceiveToolsRequest;
import com.inv.inventory.requests.RemoveTool;
import com.inv.inventory.requests.SendToolsRequest;
import com.inv.inventory.requests.UpdateSiteRequest;
import com.inv.inventory.service.InventoryService;
import com.inv.inventory.validator.RequestValidator;

@Service
public class InventoryServiceImpl implements InventoryService{
	
	private final ToolRepository toolRepository;
	private final SiteRepository siteRepository;
	private final SiteHistoryRepository siteHistoryRepository;
	
	@Autowired
    private MongoTemplate mongoTemplate;
	@Autowired
	private RequestValidator requestValidotor;
	
    public InventoryServiceImpl(ToolRepository toolRepository, SiteRepository siteRepository, SiteHistoryRepository siteHistoryRepository) {
        this.toolRepository = toolRepository;
        this.siteRepository = siteRepository;
        this.siteHistoryRepository = siteHistoryRepository;
    }
    
	public String addTool(Tool tool) {
		
		List<Tool> toolList = getAllTools();
		Tool oldTool = toolRepository.findByName(tool.getName());
		boolean validreq = requestValidotor.addTool(toolList, tool);
		if(validreq) {
			String newq = Integer.toString(
					Integer.parseInt(oldTool.getOriginalquantity())+
					Integer.parseInt(tool.getOriginalquantity())
					);
			String newcq = Integer.toString(
					Integer.parseInt(oldTool.getCurquantity())+
					Integer.parseInt(tool.getOriginalquantity())
					);
			Query query2 = new Query(Criteria.where("name").is(tool.getName()));
			Update update2 = new Update().set("originalquantity", newq);
			mongoTemplate.updateFirst(query2, update2, Tool.class);
			Query query3 = new Query(Criteria.where("name").is(tool.getName()));
			Update update3 = new Update().set("curquantity", newcq);
			mongoTemplate.updateFirst(query3, update3, Tool.class);
			
			return "Tool saved";
		}
		if(toolList.size()==0) {
			tool.setId(0);
		}
		else {
			int toolListSize = toolList.size();
			Tool lastElement = toolList.get(toolListSize-1);
			tool.setId(lastElement.getId()+1);
		}
		tool.setName(tool.getName().toLowerCase());
		tool.setCurquantity(tool.getOriginalquantity());
		toolRepository.save(tool);
		return "Tool saved";
	}
	@Override
	public List<Tool> getAllTools() {
		List<Tool> toolList = toolRepository.findAll();
		return toolList;
	}
	
	public String addSite(Site site) {
		List<SentToolDetails> siteToolDetails = new ArrayList<>();
		for(SentToolDetails x : site.getToolDetails()) {
			String lowerCaseName = x.getToolName().toLowerCase();
			SentToolDetails obj = new SentToolDetails();
			obj.setToolName(lowerCaseName);
			obj.setToolQuantity(x.getToolQuantity());
			siteToolDetails.add(obj);
		}
		site.setToolDetails(siteToolDetails);
		List<Tool> toolList = getAllTools();
		boolean validReq = requestValidotor.addSite(site, toolList);
		if(validReq) {
			List<Site> siteList = getAllSites();
			if(siteList.size()==0) {
				site.setId(0);
			}
			else {
				int siteListSize = siteList.size();
				Site lastElement = siteList.get(siteListSize-1);
				site.setId(lastElement.getId()+1);
			}
			siteRepository.save(site);
			SendToolsRequest req = new SendToolsRequest();
			req.setSiteName(site.getName());
			req.setSentToolDetails(site.getToolDetails());
			String sendTools = sendTools(req);
			return sendTools;
		}
		else {
			return "not a valid req";
		}
		
	}

	@Override
	public List<Site> getAllSites() {
		List<Site> siteList = siteRepository.findAll();
		return siteList;
	}



	public String sendTools(SendToolsRequest request) {

		Query query = new Query(Criteria.where("name").is(request.getSiteName()));
		List<SentToolDetails> newList = request.getSentToolDetails();
		Update update = new Update().set("toolDetails", newList);
		mongoTemplate.updateFirst(query, update, Site.class);
		
		for(SentToolDetails x : newList) {
			Tool tool = toolRepository.findByName(x.getToolName());
			int curQuantity = Integer.parseInt(tool.getCurquantity());
			String newQuantity = Integer.toString(curQuantity -  Integer.parseInt(x.getToolQuantity()));
			
			Query query2 = new Query(Criteria.where("name").is(x.getToolName()));
			Update update2 = new Update().set("curquantity", newQuantity);
			mongoTemplate.updateFirst(query2, update2, Tool.class);
			
		}
		
		
		
		return null;
	}

	@Override
	public String addExtraTool(AddExtraTool extraTool) {
		Tool tool = toolRepository.findByName(extraTool.getToolName());
		if(Integer.parseInt(tool.getCurquantity())<Integer.parseInt(extraTool.getToolQuantity()))
		{
			return extraTool.getToolName()+ " quantity is more than the present tool in shop ";
		}
		Site site = siteRepository.findByName(extraTool.getSiteName());
		String toolName = extraTool.getToolName().toLowerCase();
		extraTool.setToolName(toolName);
		boolean validReq = requestValidotor.modifyToolList(toolName, getAllTools());
		
		if(validReq) {
			List<SentToolDetails> oldList = site.getToolDetails();
			
			for(int i = 0; i<oldList.size(); i++) {
				if(oldList.get(i).getToolName().equalsIgnoreCase(extraTool.getToolName())) {
					oldList.get(i).setToolQuantity(
							
							Integer.toString(
									Integer.parseInt(oldList.get(i).getToolQuantity())
											+Integer.parseInt(extraTool.getToolQuantity())));
					
					Query query = new Query(Criteria.where("name").is(extraTool.getSiteName()));
					
					Update update = new Update().set("toolDetails", oldList);
					mongoTemplate.updateFirst(query, update, Site.class);
					String msg = updateToolQuantity(extraTool.getToolName(), extraTool.getToolQuantity());
					return msg;
					
				}
				
			}
			
			SentToolDetails newTool = new SentToolDetails();
			newTool.setToolName(extraTool.getToolName());
			newTool.setToolQuantity(extraTool.getToolQuantity());
			oldList.add(newTool);
			
			Query query = new Query(Criteria.where("name").is(extraTool.getSiteName()));
			
			Update update = new Update().set("toolDetails", oldList);
			mongoTemplate.updateFirst(query, update, Site.class);
			String msg = updateToolQuantity(extraTool.getToolName(), extraTool.getToolQuantity());
			return "Extra Tool Added/ Tool Value increased" +msg;

			
		}
		
		return "No tool found";
	}

	@Override
	public String removeTool(RemoveTool removeToolRequest) {
		Site site = siteRepository.findByName(removeToolRequest.getSiteName());
		String toolName = removeToolRequest.getToolName().toLowerCase();
		removeToolRequest.setToolName(toolName);
		List<SentToolDetails> oldList = site.getToolDetails();
		int i=0;
		String toolQuantityOfRemovedTool;
		for( i = 0; i<oldList.size(); i++) {
			if(oldList.get(i).getToolName().equalsIgnoreCase(removeToolRequest.getToolName()))
			break;
		}
		toolQuantityOfRemovedTool = oldList.get(i).getToolQuantity();
		String msg = updateToolQuantityNegative(removeToolRequest.getToolName(), toolQuantityOfRemovedTool);
		oldList.remove(i);
		
		Query query = new Query(Criteria.where("name").is(removeToolRequest.getSiteName()));
		
		Update update = new Update().set("toolDetails", oldList);
		mongoTemplate.updateFirst(query, update, Site.class);
		return "Tool removed"+msg;
	}

	@Override
	public String deleteTool(int id) {
		toolRepository.deleteById(id);
		return "tool deleted";
	}

	@Override
	public String decreaseNumberOfTool(AddExtraTool extraTool) {
		Tool tool = toolRepository.findByName(extraTool.getToolName());
		if(Integer.parseInt(tool.getCurquantity())- Integer.parseInt(extraTool.getToolQuantity()) < 0)
		{
			return "Amount of: "+extraTool.getToolName()+ " you are trying to return in not valid ";
		}
		Site site = siteRepository.findByName(extraTool.getSiteName());
		
		List<SentToolDetails> oldList = site.getToolDetails();
		
		for(int i = 0; i<oldList.size(); i++) {
			if(oldList.get(i).getToolName().equalsIgnoreCase(extraTool.getToolName())) {
				oldList.get(i).setToolQuantity(
						
						Integer.toString(
								Integer.parseInt(oldList.get(i).getToolQuantity())
										-Integer.parseInt(extraTool.getToolQuantity())));
				
				Query query = new Query(Criteria.where("name").is(extraTool.getSiteName()));
				
				Update update = new Update().set("toolDetails", oldList);
				mongoTemplate.updateFirst(query, update, Site.class);
				String msg = updateToolQuantityNegative(extraTool.getToolName(),extraTool.getToolQuantity());
				return msg;
				
			}
			
		}
		
		return "No tool found";
	}
	//internal functions
	public String updateToolQuantity(String toolName, String quantity) {
		Tool tool = toolRepository.findByName(toolName);
		int toolCurrentQuantity = Integer.parseInt(tool.getCurquantity());
		int newQuantity = toolCurrentQuantity-Integer.parseInt(quantity);
		String newToolQuantity = Integer.toString(newQuantity);
		Query query2 = new Query(Criteria.where("name").is(toolName));
		Update update2 = new Update().set("curquantity", newToolQuantity);
		mongoTemplate.updateFirst(query2, update2, Tool.class);
		
		return "Tool Quantity updated";
	}
	public String updateToolQuantityNegative(String toolName, String quantity) {
		Tool tool = toolRepository.findByName(toolName);
		int toolCurrentQuantity = Integer.parseInt(tool.getCurquantity());
		int newQuantity = toolCurrentQuantity+Integer.parseInt(quantity);
		String newToolQuantity = Integer.toString(newQuantity);
		Query query2 = new Query(Criteria.where("name").is(toolName));
		Update update2 = new Update().set("curquantity", newToolQuantity);
		mongoTemplate.updateFirst(query2, update2, Tool.class);
		
		return "Tool Quantity updated";
	}

	@Override
	public String moveToHistory(SiteHistory site) {
		siteHistoryRepository.save(site);
		return site.getName()+"Moved to History";
	}

	@Override
	public List<SiteHistory> getHistoryList() {
		return siteHistoryRepository.findAll();
		
	}

	@Override
	public Optional<Site> siteDetails(int id) {
		
		return siteRepository.findById(id);
	}

	@Override
	public String deleteSite(int id) {
		Optional<Site> site = siteRepository.findById(id);
		List<SentToolDetails> toolDetails = site.get().getToolDetails();
		for(SentToolDetails x : toolDetails) {
			updateToolQuantityNegative(x.getToolName(), x.getToolQuantity());
		}
		
		siteRepository.deleteById(id);
		return "Site deleted";
	}

	@Override
	public String updateSite(UpdateSiteRequest request) {
		Optional<Site> site = siteRepository.findById(request.getId());
		Query query = new Query(Criteria.where("name").is(site.get().getName()));
		Update update = new Update().set("name", request.getSiteName());
		mongoTemplate.updateFirst(query, update, Site.class);
		
		Query query2 = new Query(Criteria.where("address").is(site.get().getAddress()));
		Update update2 = new Update().set("address", request.getAddress());
		mongoTemplate.updateFirst(query2, update2, Site.class);
		
		Query query3 = new Query(Criteria.where("supervisor").is(site.get().getSupervisor()));
		Update update3 = new Update().set("supervisor", request.getSupervisor());
		mongoTemplate.updateFirst(query3, update3, Site.class);
		
		return "Site Updated";
	}

	@Override
	public String receiveTools(ReceiveToolsRequest request) {
		Optional<Site> site = siteRepository.findById(request.getId());
		List<SentToolDetails> oldList = site.get().getToolDetails();
		List<SentToolDetails> newList = request.getToolDetails();
		
		for(int i =0; i<oldList.size(); i++) {
			for(int j =0; j<newList.size(); j++) {
				if(newList.get(j).getToolName().equalsIgnoreCase(oldList.get(i).getToolName())) {
					if(Integer.parseInt(newList.get(j).getToolQuantity())>Integer.parseInt(oldList.get(i).getToolQuantity())) {
						return "Value of the tool:"+ oldList.get(i).getToolName()+" entered is not correct";
					}
					oldList.get(i).setToolQuantity(
							Integer.toString(
									Integer.parseInt(oldList.get(i).getToolQuantity())-
									Integer.parseInt(newList.get(i).getToolQuantity())));
					updateToolQuantityNegative(oldList.get(i).getToolName(),newList.get(i).getToolQuantity());
				}
			}
		}
		Query query = new Query(Criteria.where("name").is(site.get().getName()));
		
		Update update = new Update().set("toolDetails", oldList);
		mongoTemplate.updateFirst(query, update, Site.class);
		
		
				
			
			
		
		return "tools received";
	}

	@Override
	public String deleteSiteHistory(int id) {
		
		
		siteHistoryRepository.deleteById(id);
		return "Site History deleted";
	}

	@Override
	public String updateToolQuantity(Tool newToolQuantity) {
		Tool tool = toolRepository.findByName(newToolQuantity.getName());
		Query query = new Query(Criteria.where("name").is(tool.getName()));
		Update update = new Update().set("originalquantity", newToolQuantity.getOriginalquantity());
		mongoTemplate.updateFirst(query, update, Tool.class);
		
		Query query2 = new Query(Criteria.where("name").is(tool.getName()));
		Update update2 = new Update().set("curquantity", newToolQuantity.getCurquantity());
		mongoTemplate.updateFirst(query2, update2, Tool.class);
		
		Query query3 = new Query(Criteria.where("name").is(tool.getName()));
		Update update3 = new Update().set("name", newToolQuantity.getName());
		mongoTemplate.updateFirst(query3, update3, Tool.class);
		
		return "Tool quantity updated";
	}
}
