package com.nhsoft.report.dto;

import com.nhsoft.pos3.server.model.WeixinPosItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeixinPosItemSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5531415726135471721L;
	private List<WeixinPosItem> weixinPosItems = new ArrayList<WeixinPosItem>();
	private int count = 0;

	public List<WeixinPosItem> getWeixinPosItems() {
		return weixinPosItems;
	}

	public void setWeixinPosItems(List<WeixinPosItem> weixinPosItems) {
		this.weixinPosItems = weixinPosItems;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
