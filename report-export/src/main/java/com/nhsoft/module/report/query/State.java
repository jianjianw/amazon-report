package com.nhsoft.module.report.query;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class State implements java.io.Serializable {

	private static final long serialVersionUID = 689626232410514464L;

	private Integer stateCode;

	private String stateName;

	public State() {
	}

	public State(final State state) {
		this.stateCode = state.getStateCode();
		this.stateName = state.getStateName();
	}

	public State(Integer stateCode, String stateName) {
		this.stateCode = stateCode;
		this.stateName = stateName;
	}

	public State(int stateCode, String stateName) {
		this(new Integer(stateCode), stateName);
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	@JsonIgnore
	public String getLastStateName(){
		int index = stateName.lastIndexOf("|");
		if(index <= 0){
			return stateName;
		} else {
			return stateName.substring(index + 1);
		}
	}
	
	@JsonIgnore
	public static String getLastStateName(String stateName){
		int index = stateName.lastIndexOf("|");
		if(index <= 0){
			return stateName;
		} else {
			return stateName.substring(index + 1);
		}
	}

	public static boolean checkState(final List<State> states,
			final State comparedState) {
		for (int i = 0; i < states.size(); i++) {
			State state = (State) states.get(i);
			if (comparedState.check(state) == true) {
				return true;
			}
		}
		return false;
	}

	public boolean check(final State constant) {
		int stateVal = constant.stateCode.intValue();
		int thisStateVal = this.stateCode.intValue();
		if (((stateVal & thisStateVal) ^ stateVal) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void merge(final State constant) {
		if (check(constant)) {
			return;
		}
		this.stateCode = new Integer(this.stateCode.intValue()
				| constant.stateCode.intValue());
		if (this.stateName.equals("")) {
			this.stateName = constant.stateName;
		} else {
			this.stateName = this.stateName + "|" + constant.stateName;
		}
	}

	public void delete(final State constant) {
		int stateVal1 = this.stateCode.intValue();
		int stateVal2 = ~constant.stateCode.intValue();

		this.stateCode = new Integer(stateVal1 & stateVal2);
		int pos = this.stateName.indexOf(constant.stateName);
		if (pos != -1) {
			String delStateName = this.stateName.substring(0, pos - 1);
			int len1 = constant.stateName.length();
			int len2 = this.stateName.length();
			String subStr = this.stateName.substring(pos + len1, len2);
			delStateName = delStateName + subStr;
			this.stateName = delStateName;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((stateCode == null) ? 0 : stateCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		return true;
	}

	
}
