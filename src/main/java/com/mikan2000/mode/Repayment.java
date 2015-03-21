package com.mikan2000.mode;

import java.math.BigDecimal;
/**
 * 不同还款方式计算出的结果
 * @author mikan
 *
 */
public final class Repayment {
	/**
	 * 本金
	 */
	private BigDecimal principle;
	/**
	 * 利息
	 */
	private BigDecimal interest;
	/**
	 * 还款总额
	 */
	private BigDecimal totalRepay;
	/**
	 * 实际利率
	 */
	private BigDecimal rate;
	/**
	 * 第几期
	 */
	private int sequence;
	
	public BigDecimal getPrinciple() {
		return principle;
	}
	public void setPrinciple(BigDecimal principle) {
		this.principle = principle;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public BigDecimal getTotalRepay() {
		return totalRepay;
	}
	public void setTotalRepay(BigDecimal totalRepay) {
		this.totalRepay = totalRepay;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
