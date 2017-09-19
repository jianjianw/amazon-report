package com.nhsoft.report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonData implements Serializable {

	/**
	 * 
	 */
	public static class JsonDetailData implements Serializable {

		/**
		 *
		 */
		private static final long serialVersionUID = -5062432840370353536L;
		private int dataNum;
		private int dataDetailNum;

		public JsonDetailData(){
			this.dataNum = 1;
			this.dataDetailNum = 1;
		}

		public int getDataNum() {
			return dataNum;
		}

		public void setDataNum(int dataNum) {
			this.dataNum = dataNum;
		}

		public int getDataDetailNum() {
			return dataDetailNum;
		}

		public void setDataDetailNum(int dataDetailNum) {
			this.dataDetailNum = dataDetailNum;
		}

	}

	private static final long serialVersionUID = 3651589668860324893L;
	private int dataNum;
	private boolean dataFlag;
	private String dataStr;
	private List<JsonDetailData> jsonDetailDatas = new ArrayList<JsonDetailData>();

	public JsonData() {
		this.dataNum = 1;
		this.dataFlag = false;
		this.dataStr = "123";
		JsonDetailData jsonDetailData = new JsonDetailData();
		this.jsonDetailDatas.add(jsonDetailData);

	}

	public int getDataNum() {
		return dataNum;
	}

	public void setDataNum(int dataNum) {
		this.dataNum = dataNum;
	}

	public boolean isDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(boolean dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getDataStr() {
		return dataStr;
	}

	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}

	public List<JsonDetailData> getJsonDetailDatas() {
		return jsonDetailDatas;
	}

	public void setJsonDetailDatas(List<JsonDetailData> jsonDetailDatas) {
		this.jsonDetailDatas = jsonDetailDatas;
	}

}
