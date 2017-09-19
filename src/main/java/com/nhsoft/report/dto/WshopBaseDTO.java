package com.nhsoft.report.dto;

import com.nhsoft.pos3.server.model.AlipayAccount;
import com.nhsoft.pos3.server.model.SystemBook;
import com.nhsoft.pos3.server.param.ChainDeliveryParam;
import com.nhsoft.pos3.server.param.KDTParam;

import java.io.Serializable;

public class WshopBaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3396677629143645661L;
	private SystemBook systemBook;
	private AlipayAccount alipayAccount;
	private ChainDeliveryParam chainDeliveryParam;
	private KDTParam kdtParam;

	public SystemBook getSystemBook() {
		return systemBook;
	}

	public void setSystemBook(SystemBook systemBook) {
		this.systemBook = systemBook;
	}

	public AlipayAccount getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(AlipayAccount alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public ChainDeliveryParam getChainDeliveryParam() {
		return chainDeliveryParam;
	}

	public void setChainDeliveryParam(ChainDeliveryParam chainDeliveryParam) {
		this.chainDeliveryParam = chainDeliveryParam;
	}

	public KDTParam getKdtParam() {
		return kdtParam;
	}

	public void setKdtParam(KDTParam kdtParam) {
		this.kdtParam = kdtParam;
	}

}
