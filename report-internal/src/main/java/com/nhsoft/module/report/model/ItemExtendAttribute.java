package com.nhsoft.module.report.model;


import com.nhsoft.module.report.query.TwoStringValueData;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ItemExtendAttribute implements Serializable {

	/**
	 * 
	 */
	@Embeddable
	public static class ItemExtendAttributeId implements Serializable {

		private static final long serialVersionUID = -4303495322140820269L;
		private Integer itemNum;
		private String attributeName;

		public Integer getItemNum() {
			return itemNum;
		}

		public void setItemNum(Integer itemNum) {
			this.itemNum = itemNum;
		}

		public String getAttributeName() {
			return attributeName;
		}

		public void setAttributeName(String attributeName) {
			this.attributeName = attributeName;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			ItemExtendAttributeId that = (ItemExtendAttributeId) o;

			if (itemNum != null ? !itemNum.equals(that.itemNum) : that.itemNum != null) return false;
			return attributeName != null ? attributeName.equals(that.attributeName) : that.attributeName == null;
		}

		@Override
		public int hashCode() {
			int result = itemNum != null ? itemNum.hashCode() : 0;
			result = 31 * result + (attributeName != null ? attributeName.hashCode() : 0);
			return result;
		}
	}

	private static final long serialVersionUID = 1433828423909546162L;
	@EmbeddedId
	private ItemExtendAttributeId id;
	private String systemBookCode;
	private String attributeValue;

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public ItemExtendAttributeId getId() {
		return id;
	}

	public void setId(ItemExtendAttributeId id) {
		this.id = id;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	public static List<ItemExtendAttribute> find(List<ItemExtendAttribute> itemExtendAttributes, Integer itemNum){
		List<ItemExtendAttribute> list = new ArrayList<ItemExtendAttribute>();
		for(int i = 0;i < itemExtendAttributes.size();i++){
			ItemExtendAttribute itemExtendAttribute = itemExtendAttributes.get(i);
			if(itemExtendAttribute.getId().getItemNum().equals(itemNum)){
				list.add(itemExtendAttribute);
			}
		}
		return list;
	}
	
	public static boolean exists(List<ItemExtendAttribute> itemExtendAttributes, List<TwoStringValueData> twoStringValueDatas){
		TwoStringValueData param  = null;
		ItemExtendAttribute itemExtendAttribute = null;
		boolean find = false;
		for(int i = 0; i < twoStringValueDatas.size();i++){
			param = twoStringValueDatas.get(i);
			
			for(int j = 0; j < itemExtendAttributes.size();j++){
				itemExtendAttribute = itemExtendAttributes.get(j);
				if(param.getKey().equals(itemExtendAttribute.getId().getAttributeName())){
					if(itemExtendAttribute.getAttributeValue().contains(param.getValue())){
						return true;
					}
				}
			}
		}
		return find;
	}

}
