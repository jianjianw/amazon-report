package com.nhsoft.module.report.dto;


import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@SuppressWarnings("unchecked")
public class CustomerModelParam implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(CustomerModelParam.class);
    
	private static final long serialVersionUID = 6830394379749531809L;
	private String modelName;
	private String modelType;
	private List<GroupCustomerDTO> groupCustomers = new ArrayList<GroupCustomerDTO>();

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public List<GroupCustomerDTO> getGroupCustomers() {
		return groupCustomers;
	}

	public void setGroupCustomers(List<GroupCustomerDTO> groupCustomers) {
		this.groupCustomers = groupCustomers;
	}

	public static CustomerModelParam fromXml(Element element){
		CustomerModelParam param = new CustomerModelParam();
		List<GroupCustomerDTO> groupCustomers = new ArrayList<GroupCustomerDTO>();
		Attribute attribute = element.attribute("modelName");
		if(attribute != null){
			param.setModelName(attribute.getText());
		}
		attribute = element.attribute("modelType");
        if(attribute != null){
            param.setModelType(attribute.getText());
        }
		Element groupList = (Element)element.selectSingleNode("groupCustomers");
		if(groupList != null){
			Iterator<Element> iterator = groupList.elementIterator();
			while(iterator.hasNext()){
				GroupCustomerDTO groupCustomer = new GroupCustomerDTO();
				Element group = iterator.next();
				attribute = group.attribute("groupCustomerId");
				if(attribute != null){
					groupCustomer.setGroupCustomerId(attribute.getText());
				}
				attribute = group.attribute("groupCustomerName");
				if(attribute != null){
					groupCustomer.setGroupCustomerName(attribute.getText());
				}
				groupCustomers.add(groupCustomer);			
			}
		}
		param.setGroupCustomers(groupCustomers);
		return param;
	
	}
	
	public void toXml(Element root){
		Element model = root.addElement("CustomerModelParam");
		if(modelName != null){
			model.addAttribute("modelName", modelName);
		}
		if(modelType != null){
            model.addAttribute("modelType", modelType);
        }
		if(groupCustomers != null){
			Element element = model.addElement("groupCustomers");
			for(int i = 0;i < groupCustomers.size();i++){
				GroupCustomerDTO groupCustomer = groupCustomers.get(i);
				Element child = element.addElement("groupCustomer");
				if(groupCustomer.getGroupCustomerId() != null){
					child.addAttribute("groupCustomerId", groupCustomer.getGroupCustomerId());
				}
				if(groupCustomer.getGroupCustomerName() != null){
					child.addAttribute("groupCustomerName", groupCustomer.getGroupCustomerName());
				}
			}
		}
	}
	
	public static List<CustomerModelParam> fromXml(String text){
	    List<CustomerModelParam> list = new ArrayList<CustomerModelParam>();
		Document document;
        try {
            document = DocumentHelper.parseText(text);          		
    		Element root = document.getRootElement();
    		Iterator<Element> iterator = root.elementIterator();
    		while(iterator.hasNext()){
    			Element child = iterator.next();
    			CustomerModelParam param = fromXml(child);
    			list.add(param);
    		}
    		
        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }
        
        return list;
	}
	
	public static String toXml(List<CustomerModelParam> list){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("CustomerModelParam");
		for(int i = 0;i < list.size();i++){
			CustomerModelParam param = list.get(i);
			param.toXml(root);
		}
		return document.asXML();
	}
	
}
