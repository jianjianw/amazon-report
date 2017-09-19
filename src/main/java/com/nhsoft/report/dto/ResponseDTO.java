package com.nhsoft.report.dto;

public class ResponseDTO extends BaseResponseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7203189158375393721L;
	private Object data;
	
	public ResponseDTO(){
		super();
	}
	
	public ResponseDTO(String constants){
		super(constants);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
