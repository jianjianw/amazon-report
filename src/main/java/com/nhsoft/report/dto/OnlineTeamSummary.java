package com.nhsoft.report.dto;


import com.nhsoft.report.model.OnlineTeam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OnlineTeamSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5688954407220178670L;
	private List<OnlineTeam> onlineTeams = new ArrayList<OnlineTeam>();
	private int count = 0;

	public List<OnlineTeam> getOnlineTeams() {
		return onlineTeams;
	}

	public void setOnlineTeams(List<OnlineTeam> onlineTeams) {
		this.onlineTeams = onlineTeams;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
