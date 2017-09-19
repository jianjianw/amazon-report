package com.nhsoft.report.model;



/**
 * BookResourceId entity. @author MyEclipse Persistence Tools
 */

public class BookResourceId  implements java.io.Serializable {


	private static final long serialVersionUID = 7308914018602314185L;
	private String systemBookCode;
    private String bookResourceName;
    
    public BookResourceId(){
    	
    }
    
    public BookResourceId(String systemBookCode, String bookResourceName){
    	this.systemBookCode = systemBookCode;
    	this.bookResourceName = bookResourceName;
    }

    public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getBookResourceName() {
        return this.bookResourceName;
    }
    
    public void setBookResourceName(String bookResourceName) {
        this.bookResourceName = bookResourceName;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((bookResourceName == null) ? 0 : bookResourceName.hashCode());
		result = prime * result
				+ ((systemBookCode == null) ? 0 : systemBookCode.hashCode());
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
		BookResourceId other = (BookResourceId) obj;
		if (bookResourceName == null) {
			if (other.bookResourceName != null)
				return false;
		} else if (!bookResourceName.equals(other.bookResourceName))
			return false;
		if (systemBookCode == null) {
			if (other.systemBookCode != null)
				return false;
		} else if (!systemBookCode.equals(other.systemBookCode))
			return false;
		return true;
	}
   

}