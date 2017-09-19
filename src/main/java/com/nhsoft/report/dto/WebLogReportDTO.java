package com.nhsoft.report.dto;

import java.io.Serializable;

public class WebLogReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8745748613216729585L;
	private String webLogItem;
	private Integer webLogItemCurrentCount;
	private Integer webLogItemLastCount;
	private Integer webLogItemSubCount;

	public WebLogReportDTO(){
		setWebLogItemCurrentCount(0);
		setWebLogItemLastCount(0);
		setWebLogItemSubCount(0);
	}
	
	public String getWebLogItem() {
		return webLogItem;
	}

	public void setWebLogItem(String webLogItem) {
		this.webLogItem = webLogItem;
	}

	public Integer getWebLogItemCurrentCount() {
		return webLogItemCurrentCount;
	}

	public void setWebLogItemCurrentCount(Integer webLogItemCurrentCount) {
		this.webLogItemCurrentCount = webLogItemCurrentCount;
	}

	public Integer getWebLogItemLastCount() {
		return webLogItemLastCount;
	}

	public void setWebLogItemLastCount(Integer webLogItemLastCount) {
		this.webLogItemLastCount = webLogItemLastCount;
	}

	public Integer getWebLogItemSubCount() {
		return webLogItemSubCount;
	}

	public void setWebLogItemSubCount(Integer webLogItemSubCount) {
		this.webLogItemSubCount = webLogItemSubCount;
	}

}
