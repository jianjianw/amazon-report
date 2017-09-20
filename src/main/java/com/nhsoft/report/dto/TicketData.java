package com.nhsoft.report.dto;


import com.nhsoft.report.model.Branch;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TicketData implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8949825369977328154L;
	private String ticketSendFid;
	private String actionId;
	private Integer ticketSendDetailNum;
	private String ticketSendDetailPrintNum;
	private BigDecimal ticketSendDetailValue;
	private Date ticketSendDetailValidDate;
	private String ticketSendDetailType;
	private String ticketSendBarCode;
	private String ticketSendDetailMemo;
	private String ticketQrCodeUrl;
	private Date ticketSendDetailValidStart;
	private String ticketSendDetailSendBranchName;
	private BigDecimal ticketLimitMoney;
	private Integer state;

	//微信朋友圈广告
	private boolean friendsTicket = false; //是朋友圈广告，可分享
	private String marketActImg; //营销活动图片
	private List<Branch> branches;//券应用门店
	private String shareAppMessagetitle;       //微信分享标题
	private String shareAppMessageDesc;        //微信分享描述
    private String shareAppMessageLink;        //微信分享链接
	private String shareAppMessageImgUrl;      //微信分享封面图片
	private Integer actionSceneId;
	
	public Integer getActionSceneId() {
		return actionSceneId;
	}
	
	public void setActionSceneId(Integer actionSceneId) {
		this.actionSceneId = actionSceneId;
	}
	
	public String getTicketSendFid() {
		return ticketSendFid;
	}
	public String getActionId() {
		return actionId;
	}
	public Integer getTicketSendDetailNum() {
		return ticketSendDetailNum;
	}
	public String getTicketSendDetailPrintNum() {
		return ticketSendDetailPrintNum;
	}
	public BigDecimal getTicketSendDetailValue() {
		return ticketSendDetailValue;
	}
	public Date getTicketSendDetailValidDate() {
		return ticketSendDetailValidDate;
	}
	public String getTicketSendDetailType() {
		return ticketSendDetailType;
	}
	public String getTicketSendBarCode() {
		return ticketSendBarCode;
	}
	public String getTicketSendDetailMemo() {
		return ticketSendDetailMemo;
	}
	public String getTicketQrCodeUrl() {
		return ticketQrCodeUrl;
	}
	public Date getTicketSendDetailValidStart() {
		return ticketSendDetailValidStart;
	}
	public String getTicketSendDetailSendBranchName() {
		return ticketSendDetailSendBranchName;
	}
	public BigDecimal getTicketLimitMoney() {
		return ticketLimitMoney;
	}
	public boolean isFriendsTicket() {
		return friendsTicket;
	}
	public String getMarketActImg() {
		return marketActImg;
	}
	public List<Branch> getBranches() {
		return branches;
	}
	public void setTicketSendFid(String ticketSendFid) {
		this.ticketSendFid = ticketSendFid;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public void setTicketSendDetailNum(Integer ticketSendDetailNum) {
		this.ticketSendDetailNum = ticketSendDetailNum;
	}
	public void setTicketSendDetailPrintNum(String ticketSendDetailPrintNum) {
		this.ticketSendDetailPrintNum = ticketSendDetailPrintNum;
	}
	public void setTicketSendDetailValue(BigDecimal ticketSendDetailValue) {
		this.ticketSendDetailValue = ticketSendDetailValue;
	}
	public void setTicketSendDetailValidDate(Date ticketSendDetailValidDate) {
		this.ticketSendDetailValidDate = ticketSendDetailValidDate;
	}
	public void setTicketSendDetailType(String ticketSendDetailType) {
		this.ticketSendDetailType = ticketSendDetailType;
	}
	public void setTicketSendBarCode(String ticketSendBarCode) {
		this.ticketSendBarCode = ticketSendBarCode;
	}
	public void setTicketSendDetailMemo(String ticketSendDetailMemo) {
		this.ticketSendDetailMemo = ticketSendDetailMemo;
	}
	public void setTicketQrCodeUrl(String ticketQrCodeUrl) {
		this.ticketQrCodeUrl = ticketQrCodeUrl;
	}
	public void setTicketSendDetailValidStart(Date ticketSendDetailValidStart) {
		this.ticketSendDetailValidStart = ticketSendDetailValidStart;
	}
	public void setTicketSendDetailSendBranchName(String ticketSendDetailSendBranchName) {
		this.ticketSendDetailSendBranchName = ticketSendDetailSendBranchName;
	}
	public void setTicketLimitMoney(BigDecimal ticketLimitMoney) {
		this.ticketLimitMoney = ticketLimitMoney;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getShareAppMessagetitle() {
		return shareAppMessagetitle;
	}
	public String getShareAppMessageDesc() {
		return shareAppMessageDesc;
	}
	public String getShareAppMessageLink() {
		return shareAppMessageLink;
	}
	public String getShareAppMessageImgUrl() {
		return shareAppMessageImgUrl;
	}
	public void setShareAppMessagetitle(String shareAppMessagetitle) {
		this.shareAppMessagetitle = shareAppMessagetitle;
	}
	public void setShareAppMessageDesc(String shareAppMessageDesc) {
		this.shareAppMessageDesc = shareAppMessageDesc;
	}
	public void setShareAppMessageLink(String shareAppMessageLink) {
		this.shareAppMessageLink = shareAppMessageLink;
	}
	public void setShareAppMessageImgUrl(String shareAppMessageImgUrl) {
		this.shareAppMessageImgUrl = shareAppMessageImgUrl;
	}
	public void setFriendsTicket(boolean friendsTicket) {
		this.friendsTicket = friendsTicket;
	}
	public void setMarketActImg(String marketActImg) {
		this.marketActImg = marketActImg;
	}
	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}
	
}
