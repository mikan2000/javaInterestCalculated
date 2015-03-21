package com.mikan2000.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mikan2000.mode.CalculattorType;
import com.mikan2000.mode.Repayment;
import com.mikan2000.mode.RepaymentType;

/**
 * 单例模式
 * 利息计算器
 * @author mikan
 *
 */
public final class RepaymentCalculator {
	private BigDecimal totalRate;
	
	private static class RepaymentCalculatorInstance{
		private static final RepaymentCalculator instance = new RepaymentCalculator();
	}
	
	public static RepaymentCalculator getInstance(){
		
		return RepaymentCalculatorInstance.instance;
	}

	private RepaymentCalculator(){
		
	}
	
	/**
	 * 计算每期还款金额
	 * @param amount 本金
	 * @param period 借款期限
	 * @param rate 年化收益率
	 * @param repaymentType 还款方式
	 * @param calculattorType 计息方式
	 * @return
	 */
	public List<Repayment> calculatePeriodRepayment(BigDecimal amount, int period, BigDecimal rate, RepaymentType repaymentType, CalculattorType calculattorType){
		List<Repayment> repayments = new ArrayList<Repayment>();
		BigDecimal rateBase = new BigDecimal(100);// 利率基数
		
		if(amount != null){
			totalRate = rate;
			BigDecimal computeRate = rate.divide(rateBase, 6, BigDecimal.ROUND_HALF_UP);
			
			BigDecimal oncePeriodRate;
			switch(calculattorType){
			
			case DAY:
				oncePeriodRate = computeRate.divide(new BigDecimal(365), 6, BigDecimal.ROUND_HALF_UP);
				break;
				
			case MONTH:
			default:
				oncePeriodRate = computeRate.divide(new BigDecimal(12), 6, BigDecimal.ROUND_HALF_UP);
				break;
			}
			
			switch(repaymentType){
			
			case MONTH_PRINCIPLE:
				repayments = this.calculateMonthPrinciple(amount, period, oncePeriodRate);
				break;
				
			case MONTH_INTEREST:
				repayments = this.calculateMonthInterest(amount, period, oncePeriodRate);
				break;
				
			case ONCE_ONTIME:
			default:
				repayments.add(this.calculateOnceOnTime(amount, period, oncePeriodRate));
				break;
			}
		}
		
		return repayments;
	}
	
	/**
	 * 计算总还款金额
	 * @param amount 本金
	 * @param period 借款期限
	 * @param rate 年化收益率
	 * @param repaymentType 还款方式
	 * @param calculattorType 计息方式
	 * @return
	 */
	public BigDecimal calculateTotalRepayment(BigDecimal amount, int period, BigDecimal rate, RepaymentType repaymentType, CalculattorType calculattorType){
		
		BigDecimal totalRepayment = new BigDecimal("0");
		List<Repayment> repayments = this.calculatePeriodRepayment(amount, period, rate, repaymentType, calculattorType);
		
		for(Repayment repayment: repayments){
			
			totalRepayment = totalRepayment.add(repayment.getTotalRepay());
		}
		
		return totalRepayment.setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 利息总额
	 * @param amount 本金
	 * @param period 借款期限
	 * @param rate 年化收益率
	 * @param repaymentType 还款方式
	 * @param calculattorType 计息方式
	 * @return
	 */
	public BigDecimal calculateTotalInterest(BigDecimal amount, int period, BigDecimal rate, RepaymentType repaymentType, CalculattorType calculattorType){
		BigDecimal totalInterest = new BigDecimal("0");
		List<Repayment> repayments = this.calculatePeriodRepayment(amount, period, rate, repaymentType, calculattorType);
		
		for(Repayment repayment: repayments){
			
			totalInterest = totalInterest.add(repayment.getInterest());
		}
		
		return totalInterest.setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 按月等额本金
	 * @param amount
	 * @param period
	 * @param rate
	 * @param repaymentType
	 * @return
	 */
	private List<Repayment> calculateMonthPrinciple(BigDecimal amount, int period, BigDecimal rate){
	
		return null;
	}
	
	/**
	 * 按月等额本息
	 * @param amount
	 * @param period
	 * @param rate
	 * @param repaymentType
	 * @return
	 */
	private List<Repayment> calculateMonthInterest(BigDecimal amount, int period, BigDecimal rate){
		
		return null;
	}
	
	/**
	 * 到期一次性还款
	 * @param amount
	 * @param period
	 * @param rate
	 * @param repaymentType
	 * @return
	 */
	private Repayment calculateOnceOnTime(BigDecimal amount, int period, BigDecimal rate){
		
		BigDecimal periodTime = new BigDecimal(period);
		BigDecimal totalMoney = amount.multiply(rate).multiply(periodTime).add(amount);//本息
		BigDecimal interest = amount.multiply(rate).multiply(periodTime);// 利息
		
		Repayment repayment = new Repayment();
		repayment.setSequence(1);
		repayment.setPrinciple(amount.setScale(2,BigDecimal.ROUND_HALF_UP));
		repayment.setRate(totalRate.setScale(2,BigDecimal.ROUND_HALF_UP));
		repayment.setInterest(interest.setScale(2,BigDecimal.ROUND_HALF_UP));
		repayment.setTotalRepay(totalMoney.setScale(2,BigDecimal.ROUND_HALF_UP));
		
		return repayment;
	}
}
