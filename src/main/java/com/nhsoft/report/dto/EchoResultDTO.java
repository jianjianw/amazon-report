package com.nhsoft.report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EchoResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3752247953975700975L;
	private Integer messageCount; //未读留言数
	private List<String> commands = new ArrayList<String>(); //广播命令
	
	public Integer getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
}
