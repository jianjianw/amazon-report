package com.nhsoft.report.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Storehouse entity. @author MyEclipse Persistence Tools
 */

public class Storehouse implements java.io.Serializable {

	 /**
	 * 
	 */
	 private static final long serialVersionUID = 6845344997127599076L;
	 private Integer storehouseNum;
	 private String systemBookCode;
	 private String storehouseCode;
	 private String storehouseName;
	 private String storehouseLocation;
	 private Boolean storehouseActived;
	 private String storehouseMemo;
	 private String storehouseLinkman;
	 private String storehouseLinktel;
	 private String storehouseAddr;
	 private String storehousePostcode;
	 private String storehouseEmail;
	 private Boolean storehouseDelTag;
	 private Boolean storehouseCenterTag;
	 private Boolean storehouseOverflow;
	 private List<Branch> branchs = new ArrayList<Branch>();

    public Integer getStorehouseNum() {
        return this.storehouseNum;
    }
    
    public void setStorehouseNum(Integer storehouseNum) {
        this.storehouseNum = storehouseNum;
    }

    public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getStorehouseCode() {
        return this.storehouseCode;
    }
    
    public void setStorehouseCode(String storehouseCode) {
        this.storehouseCode = storehouseCode;
    }

    public String getStorehouseName() {
        return this.storehouseName;
    }
    
    public void setStorehouseName(String storehouseName) {
        this.storehouseName = storehouseName;
    }

    public String getStorehouseLocation() {
        return this.storehouseLocation;
    }
    
    public void setStorehouseLocation(String storehouseLocation) {
        this.storehouseLocation = storehouseLocation;
    }

    public Boolean getStorehouseActived() {
        return this.storehouseActived;
    }
    
    public void setStorehouseActived(Boolean storehouseActived) {
        this.storehouseActived = storehouseActived;
    }

    public String getStorehouseMemo() {
        return this.storehouseMemo;
    }
    
    public void setStorehouseMemo(String storehouseMemo) {
        this.storehouseMemo = storehouseMemo;
    }

    public String getStorehouseLinkman() {
        return this.storehouseLinkman;
    }
    
    public void setStorehouseLinkman(String storehouseLinkman) {
        this.storehouseLinkman = storehouseLinkman;
    }

    public String getStorehouseLinktel() {
        return this.storehouseLinktel;
    }
    
    public void setStorehouseLinktel(String storehouseLinktel) {
        this.storehouseLinktel = storehouseLinktel;
    }

    public String getStorehouseAddr() {
        return this.storehouseAddr;
    }
    
    public void setStorehouseAddr(String storehouseAddr) {
        this.storehouseAddr = storehouseAddr;
    }

    public String getStorehousePostcode() {
        return this.storehousePostcode;
    }
    
    public void setStorehousePostcode(String storehousePostcode) {
        this.storehousePostcode = storehousePostcode;
    }

    public String getStorehouseEmail() {
        return this.storehouseEmail;
    }
    
    public void setStorehouseEmail(String storehouseEmail) {
        this.storehouseEmail = storehouseEmail;
    }

    public Boolean getStorehouseDelTag() {
        return this.storehouseDelTag;
    }
    
    public void setStorehouseDelTag(Boolean storehouseDelTag) {
        this.storehouseDelTag = storehouseDelTag;
    }

    public Boolean getStorehouseCenterTag() {
        return this.storehouseCenterTag;
    }
    
    public void setStorehouseCenterTag(Boolean storehouseCenterTag) {
        this.storehouseCenterTag = storehouseCenterTag;
    }

	public List<Branch> getBranchs() {
		return branchs;
	}

	public void setBranchs(List<Branch> branchs) {
		this.branchs = branchs;
	}

	public Boolean getStorehouseOverflow() {
		return storehouseOverflow;
	}

	public void setStorehouseOverflow(Boolean storehouseOverflow) {
		this.storehouseOverflow = storehouseOverflow;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((storehouseNum == null) ? 0 : storehouseNum.hashCode());
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
		Storehouse other = (Storehouse) obj;
		if (storehouseNum == null) {
			if (other.storehouseNum != null)
				return false;
		} else if (!storehouseNum.equals(other.storehouseNum))
			return false;
		return true;
	}


}