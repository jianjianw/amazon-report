package com.nhsoft.report.dto;

import com.nhsoft.pos3.server.model.WebLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WebLogPageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8288722579796132024L;
	private List<WebLog> webLogs = new ArrayList<WebLog>();
	private int count;

	public List<WebLog> getWebLogs() {
		return webLogs;
	}

	public void setWebLogs(List<WebLog> webLogs) {
		this.webLogs = webLogs;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
