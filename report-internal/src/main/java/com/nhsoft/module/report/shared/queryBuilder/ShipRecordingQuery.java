import com.nhsoft.module.report.query.QueryBuilder;

import java.util.Date;
import java.util.List;

public class ShipRecordingQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3025237721595858608L;
	private Integer centerBranchNum;
	private Integer branchNum;
	private List<Integer> branchNums;//收货分店
	private List<String> clientFids;//收货客户
	private List<Integer> transferLineNums;//路线代码
	private String expressCompany;//货运公司
	private String state;//状态
	private Date dateFrom;
	private Date dateTo;
	private Boolean isPasing = false;
	//排序
	private String sortField;
	private String sortType;
	
	public List<Integer> getTransferLineNums() {
		return transferLineNums;
	}

	public void setTransferLineNums(List<Integer> transferLineNums) {
		this.transferLineNums = transferLineNums;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	public Boolean getIsPasing() {
		return isPasing;
	}

	public void setIsPasing(Boolean isPasing) {
		this.isPasing = isPasing;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public List<String> getClientFids() {
		return clientFids;
	}

	public void setClientFids(List<String> clientFids) {
		this.clientFids = clientFids;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getCenterBranchNum() {
		return centerBranchNum;
	}

	public void setCenterBranchNum(Integer centerBranchNum) {
		this.centerBranchNum = centerBranchNum;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	
	
}
