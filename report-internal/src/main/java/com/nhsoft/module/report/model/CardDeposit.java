package com.nhsoft.module.report.model;

import com.nhsoft.report.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CardDeposit generated by hbm2java
 */
@SuppressWarnings("rawtypes")
@Entity
public class CardDeposit implements java.io.Serializable {

	private static final Logger logger = LoggerFactory.getLogger(CardDeposit.class);
	private static final long serialVersionUID = 2765947710410271382L;
	@Id
	private String depositFid;
	private String systemBookCode;
	private Integer branchNum;
	private Integer shiftTableNum;
	private String shiftTableBizday;
	private Integer depositCustNum;
	private Integer depositCardType;
	private String depositPrintedNum;
	private Integer depositCategory;
	private String depositType;
	private BigDecimal depositBalance;
	private BigDecimal depositCash;
	private BigDecimal depositMoney;
	private BigDecimal depositPoint;
	private BigDecimal depositInvoice;
	private Date depositDate;
	private String depositOperator;
	private Integer depositCount;
	private Integer depositPaymentType;
	private String depositPaymentTypeName;
	private String depositBankName;
	private String depositBillref;
	private String depositMemo;
	private Date depositStartDate;
	private Date depositDeadline;
	private String depositPhysicalNum;
	private String depositCustName;
	private String depositBranchName;
	private Integer depositBalanceDetailNum;
	private Integer accountBankNum;
	private String depositMachine;
	private Boolean depositSettlementFlag;
	private Boolean depositRepealed;
	private Boolean depositNoticeFlag;
	private String depositOpenId;
	private String clientFid;
	private String depositAntiFid;
	private String depositSeller;
	private Date depositSynchDate;
	
	@Transient
	private CardUser cardUser;
	
	//临时属性
	@Transient
	private String depositCrypt;
	@Transient
	private Boolean fromPos = false;
	@Transient
	private String reSendFid;
	
    public String getReSendFid() {
		return reSendFid;
	}

	public void setReSendFid(String reSendFid) {
		this.reSendFid = reSendFid;
	}

	public Date getDepositSynchDate() {
		return depositSynchDate;
	}

	public void setDepositSynchDate(Date depositSynchDate) {
		this.depositSynchDate = depositSynchDate;
	}

	public Boolean getFromPos() {
		return fromPos;
	}

	public void setFromPos(Boolean fromPos) {
		this.fromPos = fromPos;
	}

	public String getDepositCrypt() {
		return depositCrypt;
	}

	public void setDepositCrypt(String depositCrypt) {
		this.depositCrypt = depositCrypt;
	}

	public CardUser getCardUser() {
		return cardUser;
	}

	public void setCardUser(CardUser cardUser) {
		this.cardUser = cardUser;
	}

	public CardDeposit() {
    	setDepositSettlementFlag(false);
	}

    public String getDepositSeller() {
		return depositSeller;
	}

	public void setDepositSeller(String depositSeller) {
		this.depositSeller = depositSeller;
	}

	public String getDepositAntiFid() {
		return depositAntiFid;
	}

	public void setDepositAntiFid(String depositAntiFid) {
		this.depositAntiFid = depositAntiFid;
	}

	public String getClientFid() {
        return clientFid;
    }

    public void setClientFid(String clientFid) {
        this.clientFid = clientFid;
    }
    
	public Boolean getDepositRepealed() {
		return depositRepealed;
	}

	public void setDepositRepealed(Boolean depositRepealed) {
		this.depositRepealed = depositRepealed;
	}

	public String getDepositFid() {
		return this.depositFid;
	}

	public void setDepositFid(String depositFid) {
		this.depositFid = depositFid;
	}

	public String getSystemBookCode() {
		return this.systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return this.branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Integer getShiftTableNum() {
		return this.shiftTableNum;
	}

	public void setShiftTableNum(Integer shiftTableNum) {
		this.shiftTableNum = shiftTableNum;
	}

	public String getShiftTableBizday() {
		return this.shiftTableBizday;
	}

	public void setShiftTableBizday(String shiftTableBizday) {
		this.shiftTableBizday = shiftTableBizday;
	}

	public Integer getDepositCustNum() {
		return this.depositCustNum;
	}

	public void setDepositCustNum(Integer depositCustNum) {
		this.depositCustNum = depositCustNum;
	}

	public Integer getDepositCardType() {
		return this.depositCardType;
	}

	public void setDepositCardType(Integer depositCardType) {
		this.depositCardType = depositCardType;
	}

	public String getDepositPrintedNum() {
		return this.depositPrintedNum;
	}

	public void setDepositPrintedNum(String depositPrintedNum) {
		this.depositPrintedNum = depositPrintedNum;
	}

	public Integer getDepositCategory() {
		return this.depositCategory;
	}

	public void setDepositCategory(Integer depositCategory) {
		this.depositCategory = depositCategory;
	}

	public String getDepositType() {
		return this.depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public BigDecimal getDepositBalance() {
		return this.depositBalance;
	}

	public void setDepositBalance(BigDecimal depositBalance) {
		this.depositBalance = depositBalance;
	}

	public BigDecimal getDepositCash() {
		return this.depositCash;
	}

	public void setDepositCash(BigDecimal depositCash) {
		this.depositCash = depositCash;
	}

	public BigDecimal getDepositMoney() {
		return this.depositMoney;
	}

	public void setDepositMoney(BigDecimal depositMoney) {
		this.depositMoney = depositMoney;
	}

	public BigDecimal getDepositPoint() {
		return this.depositPoint;
	}

	public void setDepositPoint(BigDecimal depositPoint) {
		this.depositPoint = depositPoint;
	}

	public BigDecimal getDepositInvoice() {
		return this.depositInvoice;
	}

	public void setDepositInvoice(BigDecimal depositInvoice) {
		this.depositInvoice = depositInvoice;
	}

	public Date getDepositDate() {
		return this.depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public String getDepositOperator() {
		return this.depositOperator;
	}

	public void setDepositOperator(String depositOperator) {
		this.depositOperator = depositOperator;
	}

	public Integer getDepositCount() {
		return this.depositCount;
	}

	public void setDepositCount(Integer depositCount) {
		this.depositCount = depositCount;
	}

	public Integer getDepositPaymentType() {
		return this.depositPaymentType;
	}

	public void setDepositPaymentType(Integer depositPaymentType) {
		this.depositPaymentType = depositPaymentType;
	}

	public String getDepositPaymentTypeName() {
		return this.depositPaymentTypeName;
	}

	public void setDepositPaymentTypeName(String depositPaymentTypeName) {
		this.depositPaymentTypeName = depositPaymentTypeName;
	}

	public String getDepositBankName() {
		return this.depositBankName;
	}

	public void setDepositBankName(String depositBankName) {
		this.depositBankName = depositBankName;
	}

	public String getDepositBillref() {
		return this.depositBillref;
	}

	public void setDepositBillref(String depositBillref) {
		this.depositBillref = depositBillref;
	}

	public String getDepositMemo() {
		return this.depositMemo;
	}

	public void setDepositMemo(String depositMemo) {
		this.depositMemo = depositMemo;
	}

	public Date getDepositStartDate() {
		return this.depositStartDate;
	}

	public void setDepositStartDate(Date depositStartDate) {
		this.depositStartDate = depositStartDate;
	}

	public Date getDepositDeadline() {
		return this.depositDeadline;
	}

	public void setDepositDeadline(Date depositDeadline) {
		this.depositDeadline = depositDeadline;
	}

	public String getDepositPhysicalNum() {
		return this.depositPhysicalNum;
	}

	public void setDepositPhysicalNum(String depositPhysicalNum) {
		this.depositPhysicalNum = depositPhysicalNum;
	}

	public String getDepositCustName() {
		return this.depositCustName;
	}

	public void setDepositCustName(String depositCustName) {
		this.depositCustName = depositCustName;
	}

	public String getDepositBranchName() {
		return this.depositBranchName;
	}

	public void setDepositBranchName(String depositBranchName) {
		this.depositBranchName = depositBranchName;
	}

	public Integer getDepositBalanceDetailNum() {
		return this.depositBalanceDetailNum;
	}

	public void setDepositBalanceDetailNum(Integer depositBalanceDetailNum) {
		this.depositBalanceDetailNum = depositBalanceDetailNum;
	}

	public Integer getAccountBankNum() {
		return this.accountBankNum;
	}

	public void setAccountBankNum(Integer accountBankNum) {
		this.accountBankNum = accountBankNum;
	}

	public String getDepositMachine() {
		return this.depositMachine;
	}

	public void setDepositMachine(String depositMachine) {
		this.depositMachine = depositMachine;
	}

	public Boolean getDepositSettlementFlag() {
		return this.depositSettlementFlag;
	}

	public void setDepositSettlementFlag(Boolean depositSettlementFlag) {
		this.depositSettlementFlag = depositSettlementFlag;
	}

	public Boolean getDepositNoticeFlag() {
		return depositNoticeFlag;
	}

	public void setDepositNoticeFlag(Boolean depositNoticeFlag) {
		this.depositNoticeFlag = depositNoticeFlag;
	}

	public String getDepositOpenId() {
		return depositOpenId;
	}

	public void setDepositOpenId(String depositOpenId) {
		this.depositOpenId = depositOpenId;
	}

	public void toXml(Element cardDepositElement) {
        Element child;
        if (depositFid != null) {
            child = cardDepositElement.addElement("deposit_fid");
            child.setText(depositFid.toString());
        }
        if (systemBookCode != null) {
            child = cardDepositElement.addElement("system_book_code");
            child.setText(systemBookCode.toString());
        }
        if (branchNum != null) {
            child = cardDepositElement.addElement("branch_num");
            child.setText(branchNum.toString());
        }
        if (shiftTableNum != null) {
            child = cardDepositElement.addElement("shift_table_num");
            child.setText(shiftTableNum.toString());
        }
        if (shiftTableBizday != null) {
            child = cardDepositElement.addElement("shift_table_bizday");
            child.setText(shiftTableBizday.toString());
        }
        if (depositCustNum != null) {
            child = cardDepositElement.addElement("deposit_cust_num");
            child.setText(depositCustNum.toString());
        }
        if (depositCardType != null) {
            child = cardDepositElement.addElement("deposit_card_type");
            child.setText(depositCardType.toString());
        }
        if (depositPrintedNum != null) {
            child = cardDepositElement.addElement("deposit_printed_num");
            child.setText(depositPrintedNum.toString());
        }
        if (depositCategory != null) {
            child = cardDepositElement.addElement("deposit_category");
            child.setText(depositCategory.toString());
        }
        if (depositType != null) {
            child = cardDepositElement.addElement("deposit_type");
            child.setText(depositType.toString());
        }
        if (depositBalance != null) {
            child = cardDepositElement.addElement("deposit_balance");
            child.setText(depositBalance.toString());
        }
        if (depositCash != null) {
            child = cardDepositElement.addElement("deposit_cash");
            child.setText(depositCash.toString());
        }
        if (depositMoney != null) {
            child = cardDepositElement.addElement("deposit_money");
            child.setText(depositMoney.toString());
        }
        if (depositPoint != null) {
            child = cardDepositElement.addElement("deposit_point");
            child.setText(depositPoint.toString());
        }
        if (depositInvoice != null) {
            child = cardDepositElement.addElement("deposit_invoice");
            child.setText(depositInvoice.toString());
        }
        if (depositDate != null) {
            child = cardDepositElement.addElement("deposit_date");
            child.setText(DateUtil.getXmlTString(depositDate));
        }
        if (depositOperator != null) {
            child = cardDepositElement.addElement("deposit_operator");
            child.setText(depositOperator.toString());
        }
        if (depositCount != null) {
            child = cardDepositElement.addElement("deposit_count");
            child.setText(depositCount.toString());
        }
        if (depositPaymentType != null) {
            child = cardDepositElement.addElement("deposit_payment_type");
            child.setText(depositPaymentType.toString());
        }
        if (depositPaymentTypeName != null) {
            child = cardDepositElement.addElement("deposit_payment_type_name");
            child.setText(depositPaymentTypeName.toString());
        }
        if (depositBankName != null) {
            child = cardDepositElement.addElement("deposit_bank_name");
            child.setText(depositBankName.toString());
        }
        if (depositBillref != null) {
            child = cardDepositElement.addElement("deposit_billref");
            child.setText(depositBillref.toString());
        }
        if (depositMemo != null) {
            child = cardDepositElement.addElement("deposit_memo");
            child.setText(depositMemo.toString());
        }
        if (depositStartDate != null) {
            child = cardDepositElement.addElement("deposit_start_date");
            child.setText(DateUtil.getXmlTString(depositStartDate));
        }
        if (depositDeadline != null) {
            child = cardDepositElement.addElement("deposit_deadline");
            child.setText(DateUtil.getXmlTString(depositDeadline));
        }
        if (depositPhysicalNum != null) {
            child = cardDepositElement.addElement("deposit_physical_num");
            child.setText(depositPhysicalNum.toString());
        }
        if (depositCustName != null) {
            child = cardDepositElement.addElement("deposit_cust_name");
            child.setText(depositCustName.toString());
        }
        if (depositBranchName != null) {
            child = cardDepositElement.addElement("deposit_branch_name");
            child.setText(depositBranchName.toString());
        }
        if (depositBalanceDetailNum != null) {
            child = cardDepositElement.addElement("deposit_balance_detail_num");
            child.setText(depositBalanceDetailNum.toString());
        }
        if (accountBankNum != null) {
            child = cardDepositElement.addElement("account_bank_num");
            child.setText(accountBankNum.toString());
        }
        if (depositMachine != null) {
            child = cardDepositElement.addElement("DEPOSIT_MACHINE");
            child.setText(depositMachine.toString());
        }
        if (depositSettlementFlag != null) {
            child = cardDepositElement.addElement("DEPOSIT_SETTLEMENT_FLAG");
            child.setText(depositSettlementFlag.toString());
        }
    }

    public static CardDeposit fromXml(Element cardDepositElement) {
        Element child;
        CardDeposit cardDeposit = new CardDeposit();
        child = (Element) cardDepositElement.selectSingleNode("deposit_fid");
        if (child != null) {
            cardDeposit.setDepositFid(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("system_book_code");
        if (child != null) {
            cardDeposit.setSystemBookCode(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("branch_num");
        if (child != null) {
            cardDeposit.setBranchNum(new Integer(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("shift_table_num");
        if (child != null) {
            cardDeposit.setShiftTableNum(new Integer(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("shift_table_bizday");
        if (child != null) {
            cardDeposit.setShiftTableBizday(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_cust_num");
        if (child != null) {
            cardDeposit.setDepositCustNum(new Integer(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_card_type");
        if (child != null) {
            cardDeposit.setDepositCardType(new Integer(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_printed_num");
        if (child != null) {
            cardDeposit.setDepositPrintedNum(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_category");
        if (child != null) {
            cardDeposit.setDepositCategory(new Integer(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_type");
        if (child != null) {
            cardDeposit.setDepositType(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_balance");
        if (child != null) {
            cardDeposit.setDepositBalance(new BigDecimal(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_cash");
        if (child != null) {
            cardDeposit.setDepositCash(new BigDecimal(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_money");
        if (child != null) {
            cardDeposit.setDepositMoney(new BigDecimal(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_point");
        if (child != null) {
            cardDeposit.setDepositPoint(new BigDecimal(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_invoice");
        if (child != null) {
            cardDeposit.setDepositInvoice(new BigDecimal(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_date");
        if (child != null) {
            cardDeposit.setDepositDate(DateUtil.getXmlTDate(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_operator");
        if (child != null) {
            cardDeposit.setDepositOperator(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_count");
        if (child != null) {
            cardDeposit.setDepositCount(new Integer(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_payment_type");
        if (child != null) {
            cardDeposit.setDepositPaymentType(new Integer(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_payment_type_name");
        if (child != null) {
            cardDeposit.setDepositPaymentTypeName(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_bank_name");
        if (child != null) {
            cardDeposit.setDepositBankName(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_billref");
        if (child != null) {
            cardDeposit.setDepositBillref(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_memo");
        if (child != null) {
            cardDeposit.setDepositMemo(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_start_date");
        if (child != null) {
            cardDeposit.setDepositStartDate(DateUtil.getXmlTDate(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_deadline");
        if (child != null) {
            cardDeposit.setDepositDeadline(DateUtil.getXmlTDate(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_physical_num");
        if (child != null) {
            cardDeposit.setDepositPhysicalNum(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_cust_name");
        if (child != null) {
            cardDeposit.setDepositCustName(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_branch_name");
        if (child != null) {
            cardDeposit.setDepositBranchName(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("deposit_balance_detail_num");
        if (child != null) {
            cardDeposit.setDepositBalanceDetailNum(new Integer(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("account_bank_num");
        if (child != null) {
            cardDeposit.setAccountBankNum(new Integer(child.getText()));
        }
        child = (Element) cardDepositElement.selectSingleNode("DEPOSIT_MACHINE");
        if (child != null) {
            cardDeposit.setDepositMachine(child.getText());
        }
        child = (Element) cardDepositElement.selectSingleNode("DEPOSIT_SETTLEMENT_FLAG");
        if (child != null) {
            cardDeposit.setDepositSettlementFlag(new Boolean(child.getText()));
        }
        return cardDeposit;
    }

    public static String toXml(List<CardDeposit> cardDepositList) {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("GBK");
        Element root = document.addElement("CARD_DEPOSIT_LIST");
        for (int i = 0; i < cardDepositList.size(); i++) {
            CardDeposit cardDeposit = (CardDeposit) cardDepositList.get(i);
            Element cardDepositElement = root.addElement("card_deposit");
            cardDeposit.toXml(cardDepositElement);
        }
        return document.asXML();
    }

    public static List<CardDeposit> fromXml(String xmlStream) throws Exception {
        List<CardDeposit> cardDepositList = new ArrayList<CardDeposit>();
        Document document = DocumentHelper.parseText(xmlStream);

		List elementList = document.selectNodes("//card_deposit");
        for (int i = 0; i < elementList.size(); i++) {
            Element cardDepositElement = (Element) elementList.get(i);
            CardDeposit cardDeposit = fromXml(cardDepositElement);
            cardDepositList.add(cardDeposit);
        }
        return cardDepositList;
    }

    public static CardDeposit objectFromXml(String xmlStream) {
    	if(StringUtils.isEmpty(xmlStream)){
    		return null;
    	}
    	try {
    		 return fromXml(xmlStream).get(0);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
       
    }

    public static String objectToXml(CardDeposit cardDeposit) {
        List<CardDeposit> cardDepositList = new ArrayList<CardDeposit>();
        cardDepositList.add(cardDeposit);
        return toXml(cardDepositList);
    }
	
}
