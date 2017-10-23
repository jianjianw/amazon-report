package com.nhsoft.module.report.model;

/**
 * PrivilegeResource entity. @author MyEclipse Persistence Tools
 */

public class PrivilegeResource implements java.io.Serializable {

	private static final long serialVersionUID = -664261981733010243L;
	private String privilegeResourceKey;
	private String privilegeResourceName;
	private String privilegeResourceCategory;
	private String privilegeResourceType;
	private String privilegeResourceMode;
	private String privilegeResourceAssignedBook;
	private String privilegeResourceProduct;
	
	public PrivilegeResource(){
		
	}
	
	public String getPrivilegeResourceProduct() {
		return privilegeResourceProduct;
	}

	public void setPrivilegeResourceProduct(String privilegeResourceProduct) {
		this.privilegeResourceProduct = privilegeResourceProduct;
	}

	public String getPrivilegeResourceAssignedBook() {
		return privilegeResourceAssignedBook;
	}

	public void setPrivilegeResourceAssignedBook(String privilegeResourceAssignedBook) {
		this.privilegeResourceAssignedBook = privilegeResourceAssignedBook;
	}

	public PrivilegeResource(String privilegeResourceName, String privilegeResourceCategory, String privilegeResourceType, String privilegeResourceMode){
		this.privilegeResourceName = privilegeResourceName;
		this.privilegeResourceCategory = privilegeResourceCategory;
		this.privilegeResourceType = privilegeResourceType;
		this.privilegeResourceMode = privilegeResourceMode;
	}

	public String getPrivilegeResourceKey() {
		return privilegeResourceKey;
	}

	public void setPrivilegeResourceKey(String privilegeResourceKey) {
		this.privilegeResourceKey = privilegeResourceKey;
	}

	public String getPrivilegeResourceName() {
		return privilegeResourceName;
	}

	public void setPrivilegeResourceName(String privilegeResourceName) {
		this.privilegeResourceName = privilegeResourceName;
	}

	public String getPrivilegeResourceCategory() {
		return privilegeResourceCategory;
	}

	public void setPrivilegeResourceCategory(String privilegeResourceCategory) {
		this.privilegeResourceCategory = privilegeResourceCategory;
	}

	public String getPrivilegeResourceType() {
		return privilegeResourceType;
	}

	public void setPrivilegeResourceType(String privilegeResourceType) {
		this.privilegeResourceType = privilegeResourceType;
	}

	public String getPrivilegeResourceMode() {
		return privilegeResourceMode;
	}

	public void setPrivilegeResourceMode(String privilegeResourceMode) {
		this.privilegeResourceMode = privilegeResourceMode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((privilegeResourceKey == null) ? 0 : privilegeResourceKey.hashCode());
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
		PrivilegeResource other = (PrivilegeResource) obj;
		if (privilegeResourceKey == null) {
			if (other.privilegeResourceKey != null)
				return false;
		} else if (!privilegeResourceKey.equals(other.privilegeResourceKey))
			return false;
		return true;
	}

	
}