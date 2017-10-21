package com.nhsoft.module.report.query;

import com.nhsoft.module.report.dto.QueryBuilder;

import java.util.Date;
import java.util.List;

public class ABCListQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 256504585725488985L;
	private String systemBookCode;
	private Integer branchNum;           		//当前店
	private List<Integer> branchNums;			//分店List
    private Date dateFrom;							//开始时间
    private Date dateTo; 								//结束时间
    private List<String> categoryCodeList; 	//商品类别
    private List<String> types;            			//【调出，批发， 销售】

	@Override
	public boolean checkQueryBuild(){
		 if (dateFrom != null && dateTo != null) {
            if (dateFrom.after(dateTo)) {
                return false;
            }
        }
        if (systemBookCode == null) {
            return false;
        }
        return true;
	}
	
	@SuppressWarnings("deprecation")
	public boolean isThrowTowYears(){
		if(dateFrom.getYear() != dateTo.getYear()){
		    return false;
		}
		return true;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
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

	public List<String> getCategoryCodeList() {
		return categoryCodeList;
	}

	public void setCategoryCodeList(List<String> categoryCodeList) {
		this.categoryCodeList = categoryCodeList;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
	
	public String getKey() {
		StringBuffer sb = new StringBuffer();
		sb.append(systemBookCode);
		sb.append(dateFrom.toString());
		sb.append(dateTo.toString());

		if(branchNums != null){
			sb.append("branchNums:");
			for(int i = 0;i < branchNums.size();i++){
				sb.append(branchNums.get(i));
			}
		}
		if(categoryCodeList != null){
			sb.append("categoryCodeList:");
			for(int i = 0;i < categoryCodeList.size();i++){
				sb.append(categoryCodeList.get(i));
			}
		}
		if(types != null){
			sb.append("types:");
			for(int i = 0;i < types.size();i++){
				sb.append(types.get(i));
			}
		}
		if(branchNum != null){
			sb.append("branchNum:");
			sb.append(branchNum);
		}
		return sb.toString();
	}
	
}
