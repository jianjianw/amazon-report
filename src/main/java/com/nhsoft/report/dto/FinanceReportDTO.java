package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class FinanceReportDTO implements Serializable {

	private static final long serialVersionUID = -5433565160026669995L;
	private String financeSubjectSummary; // 摘要
	private String subjectCode; // 科目编码
	private BigDecimal originalDebtorMoney; // 原币借方金额
	private BigDecimal localDebtorMoney; // 本币借方金额
	private BigDecimal originalLenderMoney; // 原币贷方金额
	private BigDecimal localLenderMoney; // 本币贷方金额
	private String subjectAssist; // 辅助核算

	public String getSubjectAssist() {
		return subjectAssist;
	}

	public void setSubjectAssist(String subjectAssist) {
		this.subjectAssist = subjectAssist;
	}

	public String getFinanceSubjectSummary() {
		return financeSubjectSummary;
	}

	public void setFinanceSubjectSummary(String financeSubjectSummary) {
		this.financeSubjectSummary = financeSubjectSummary;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public BigDecimal getOriginalDebtorMoney() {
		return originalDebtorMoney;
	}

	public void setOriginalDebtorMoney(BigDecimal originalDebtorMoney) {
		this.originalDebtorMoney = originalDebtorMoney;
	}

	public BigDecimal getLocalDebtorMoney() {
		return localDebtorMoney;
	}

	public void setLocalDebtorMoney(BigDecimal localDebtorMoney) {
		this.localDebtorMoney = localDebtorMoney;
	}

	public BigDecimal getOriginalLenderMoney() {
		return originalLenderMoney;
	}

	public void setOriginalLenderMoney(BigDecimal originalLenderMoney) {
		this.originalLenderMoney = originalLenderMoney;
	}

	public BigDecimal getLocalLenderMoney() {
		return localLenderMoney;
	}

	public void setLocalLenderMoney(BigDecimal localLenderMoney) {
		this.localLenderMoney = localLenderMoney;
	}

}
