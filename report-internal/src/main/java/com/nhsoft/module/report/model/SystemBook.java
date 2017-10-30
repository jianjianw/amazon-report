package com.nhsoft.module.report.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
public class SystemBook implements java.io.Serializable {

	private static final long serialVersionUID = -8604474107038799163L;
	@Id
	private String systemBookCode;
	private String bookName;
	private String bookKey;
	private Boolean bookActived;
	private Boolean bookWebKeyActived;
	private String bookMerchantId;
	private String bookMerchantName;
	private Boolean bookMerchantActived;
	private String bookScopeId;
	private Boolean bookMerchantDpcDisabled;//是否禁用大中心同步
	private Date bookWshopExpiration;
	private String bookWshopModule;
	private String bookDeleteData;
	private Boolean bookReadDpc = false;
	private String parentBookCode;
	private Boolean bookChainActived;
	
	//临时属性
	@Transient
	private Boolean fromLicenseRegist = false; //是否从通过证书注册导入
	@Transient
	private Boolean bookEnableWholesaler; // 批发功能
	@Transient
	private Boolean bookEnableDealer; // 经销商在线订货功能

	public Boolean getFromLicenseRegist() {
		return fromLicenseRegist;
	}

	public void setFromLicenseRegist(Boolean fromLicenseRegist) {
		this.fromLicenseRegist = fromLicenseRegist;
	}

	public Boolean getBookChainActived() {
		return bookChainActived;
	}

	public void setBookChainActived(Boolean bookChainActived) {
		this.bookChainActived = bookChainActived;
	}

	public String getParentBookCode() {
		return parentBookCode;
	}

	public void setParentBookCode(String parentBookCode) {
		this.parentBookCode = parentBookCode;
	}

	public Boolean getBookReadDpc() {
		return bookReadDpc;
	}

	public void setBookReadDpc(Boolean bookReadDpc) {
		this.bookReadDpc = bookReadDpc;
	}

	public String getBookDeleteData() {
		return bookDeleteData;
	}

	public void setBookDeleteData(String bookDeleteData) {
		this.bookDeleteData = bookDeleteData;
	}

	public Date getBookWshopExpiration() {
		return bookWshopExpiration;
	}

	public void setBookWshopExpiration(Date bookWshopExpiration) {
		this.bookWshopExpiration = bookWshopExpiration;
	}

	public String getBookWshopModule() {
		return bookWshopModule;
	}

	public void setBookWshopModule(String bookWshopModule) {
		this.bookWshopModule = bookWshopModule;
	}

	public Boolean getBookMerchantDpcDisabled() {
		return bookMerchantDpcDisabled;
	}

	public void setBookMerchantDpcDisabled(Boolean bookMerchantDpcDisabled) {
		this.bookMerchantDpcDisabled = bookMerchantDpcDisabled;
	}

	public String getSystemBookCode() {
		return this.systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getBookName() {
		return this.bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookKey() {
		return this.bookKey;
	}

	public void setBookKey(String bookKey) {
		this.bookKey = bookKey;
	}

	public Boolean getBookActived() {
		return this.bookActived;
	}

	public void setBookActived(Boolean bookActived) {
		this.bookActived = bookActived;
	}

	public Boolean getBookWebKeyActived() {
		return bookWebKeyActived;
	}

	public void setBookWebKeyActived(Boolean bookWebKeyActived) {
		this.bookWebKeyActived = bookWebKeyActived;
	}

	public String getBookMerchantId() {
		return bookMerchantId;
	}

	public void setBookMerchantId(String bookMerchantId) {
		this.bookMerchantId = bookMerchantId;
	}

	public String getBookMerchantName() {
		return bookMerchantName;
	}

	public void setBookMerchantName(String bookMerchantName) {
		this.bookMerchantName = bookMerchantName;
	}

	public Boolean getBookMerchantActived() {
		return bookMerchantActived;
	}

	public void setBookMerchantActived(Boolean bookMerchantActived) {
		this.bookMerchantActived = bookMerchantActived;
	}

	public Boolean getBookEnableWholesaler() {
		return bookEnableWholesaler;
	}

	public void setBookEnableWholesaler(Boolean bookEnableWholesaler) {
		this.bookEnableWholesaler = bookEnableWholesaler;
	}

	public Boolean getBookEnableDealer() {
		return bookEnableDealer;
	}

	public void setBookEnableDealer(Boolean bookEnableDealer) {
		this.bookEnableDealer = bookEnableDealer;
	}

	public String getBookScopeId() {
		return bookScopeId;
	}

	public void setBookScopeId(String bookScopeId) {
		this.bookScopeId = bookScopeId;
	}
	
	public static void main(String[] args) {
		String a = "pos_order:20150101,pos_item_log:20160101";
		String [] array = a.split(",");
		System.out.println(array[0].split(":")[1]);
	}
	
	/**
	 * 业务库数据删除截止日期
	 * @return
	 */
	@JsonIgnore
	public Date getDeleteDate(){
		
		if(StringUtils.isEmpty(bookDeleteData)){
			bookDeleteData = "20150101";
		}		
		return DateUtil.getDateStr(bookDeleteData);
	}

	public static SystemBook get(List<SystemBook> systemBookList, String systemBookCode) {
		if(systemBookList == null) {
			return null;
		}
		return systemBookList.stream().filter(s -> s.getSystemBookCode().equals(systemBookCode)).findAny().orElse(null);
	}

}