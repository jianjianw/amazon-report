package com.nhsoft.module.report.model;

import javax.persistence.*;


/**
 * ItemFlagDetail generated by hbm2java
 */
@Entity
public class ItemFlagDetail implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6654414629373195619L;

	@Embeddable
	public static class ItemFlagDetailId implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4763116461155874973L;
		private int itemFlagNum;
		private int itemNum;


		public ItemFlagDetailId() {
		}

		public ItemFlagDetailId(int itemFlagNum, int itemNum) {
			this.itemFlagNum = itemFlagNum;
			this.itemNum = itemNum;
		}


		public int getItemFlagNum() {
			return this.itemFlagNum;
		}

		public void setItemFlagNum(int itemFlagNum) {
			this.itemFlagNum = itemFlagNum;
		}

		public int getItemNum() {
			return this.itemNum;
		}

		public void setItemNum(int itemNum) {
			this.itemNum = itemNum;
		}

		public boolean equals(Object other) {
			if ((this == other))
				return true;
			if ((other == null))
				return false;
			if (!(other instanceof ItemFlagDetailId))
				return false;
			ItemFlagDetailId castOther = (ItemFlagDetailId) other;

			return (this.getItemFlagNum() == castOther.getItemFlagNum()) && (this.getItemNum() == castOther.getItemNum());
		}

		public int hashCode() {
			int result = 17;

			result = 37 * result + this.getItemFlagNum();
			result = 37 * result + this.getItemNum();
			return result;
		}

	}

	@EmbeddedId
	private ItemFlagDetailId id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="itemFlagNum", insertable=false, updatable=false)
	private PosItemFlag posItemFlag;
	@ManyToOne
	@JoinColumn(name="itemNum", insertable=false, updatable=false)
	private PosItem posItem;

	private String systemBookCode;

	public ItemFlagDetail() {
	}

	public ItemFlagDetail(ItemFlagDetailId id, PosItemFlag posItemFlag) {
		this.id = id;
		this.posItemFlag = posItemFlag;
	}

	public ItemFlagDetailId getId() {
		return this.id;
	}

	public void setId(ItemFlagDetailId id) {
		this.id = id;
	}

	public PosItemFlag getPosItemFlag() {
		return this.posItemFlag;
	}

	public void setPosItemFlag(PosItemFlag posItemFlag) {
		this.posItemFlag = posItemFlag;
	}

	public PosItem getPosItem() {
		return posItem;
	}

	public void setPosItem(PosItem posItem) {
		this.posItem = posItem;
	}


	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

}
