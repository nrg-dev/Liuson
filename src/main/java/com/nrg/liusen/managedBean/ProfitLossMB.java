package com.nrg.liusen.managedBean;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.ProfitLossDataBean;
import com.nrg.liusen.util.CommaFormat;
import com.nrg.liusen.util.CommonValidate;

public class ProfitLossMB {

	ProfitLossDataBean profitLossDataBean = new ProfitLossDataBean();
	private Boolean flag=true;
	
	private List<ProfitLossDataBean> profitLossList=null;
	private List<ProfitLossDataBean> profitLossList1=null;
	private String totalAmount;

	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the profitLossList1
	 */
	public List<ProfitLossDataBean> getProfitLossList1() {
		return profitLossList1;
	}

	/**
	 * @param profitLossList1 the profitLossList1 to set
	 */
	public void setProfitLossList1(List<ProfitLossDataBean> profitLossList1) {
		this.profitLossList1 = profitLossList1;
	}

	/**
	 * @return the profitLossList
	 */
	public List<ProfitLossDataBean> getProfitLossList() {
		return profitLossList;
	}

	/**
	 * @param profitLossList the profitLossList to set
	 */
	public void setProfitLossList(List<ProfitLossDataBean> profitLossList) {
		this.profitLossList = profitLossList;
	}

	/**
	 * @return the flag
	 */
	public Boolean getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	/**
	 * @return the profitLossDataBean
	 */
	public ProfitLossDataBean getProfitLossDataBean() {
		return profitLossDataBean;
	}

	/**
	 * @param profitLossDataBean the profitLossDataBean to set
	 */
	public void setProfitLossDataBean(ProfitLossDataBean profitLossDataBean) {
		this.profitLossDataBean = profitLossDataBean;
	}
	
	public String profitPageLoad() {
		System.out.println("Inside profit and loss method calling");
		setFlag(true);
		profitLossDataBean.setFromDate(null);
		profitLossDataBean.setToDate(null);
		return "profitLossPage";
		
	}
	/**
	 * This method is used for show data tables depends on Order Number
	 * 
	 * @return
	 */
	public String searchDate() {
		
		System.out.println("Inside the searchDate Method Calling");
		try {
			if (validate(true)) {
				System.out.println("After Validate inside  searchDate method");
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController	controller = (LiusenController) ctx.getBean("controller");
				profitLossList=controller.searchProfitLoss(profitLossDataBean);
			if(profitLossDataBean.getSalesList() !=null){
				System.out.println("getSalesList in MB"+profitLossDataBean.getSalesList().size());
			}
			if(profitLossDataBean.getPurchaseList() != null){
				System.out.println("getPurchaseList in MB"+profitLossDataBean.getPurchaseList().size());
			}
			if(profitLossDataBean.getPayList() != null){
				System.out.println("getPayList in MB"+profitLossDataBean.getPayList().size());

			}
			if(profitLossDataBean.getTransactionList() != null){
				System.out.println("getTransactionList in MB"+profitLossDataBean.getTransactionList().size());

			}
			if(profitLossDataBean.getTransactionList1() != null){
				System.out.println("getTransactionList in MB"+profitLossDataBean.getTransactionList1().size());

			}
			if(profitLossDataBean.getStockoutManualList() != null){
				System.out.println("getStockoutManualList in MB"+profitLossDataBean.getStockoutManualList().size());

			}
			System.out.println("temp in MB"+profitLossList.size()+profitLossDataBean.getTotalLoss());
			System.out.println("temp in MB"+profitLossList.size()+profitLossDataBean.getTptalProfit());
			BigDecimal big=BigDecimal.ZERO;
			if(new BigDecimal(profitLossDataBean.getTotalLoss()).compareTo(new BigDecimal(profitLossDataBean.getTptalProfit()))==1)
			{
				big=new BigDecimal(profitLossDataBean.getTotalLoss()).subtract(new BigDecimal(profitLossDataBean.getTptalProfit()));
				setTotalAmount("-"+CommaFormat.numberFormat.format(big));
			}
			else if(new BigDecimal(profitLossDataBean.getTptalProfit()).compareTo(new BigDecimal(profitLossDataBean.getTotalLoss()))==1)
			{
				big=new BigDecimal(profitLossDataBean.getTptalProfit()).subtract(new BigDecimal(profitLossDataBean.getTotalLoss()));
				setTotalAmount(""+CommaFormat.numberFormat.format(big));
			}
			profitLossDataBean.setTotalLoss(""+CommaFormat.numberFormat.format(new BigDecimal(profitLossDataBean.getTotalLoss())));
			profitLossDataBean.setTptalProfit(""+CommaFormat.numberFormat.format(new BigDecimal(profitLossDataBean.getTptalProfit())));
			}else{
				setFlag(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * This method is used to validate data
	 * 
	 * @param valid
	 * @return false show error message
	 * @return true not showing error message
	 */
	private boolean validate(boolean flag1) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (profitLossDataBean.getFromDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("profitFrom").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the From Date."));
			}
			valid = false;
		} 
		if (profitLossDataBean.getToDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("profitTo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the To Date."));
			}
			valid = false;
		} 
		return valid;
	}
	
	
}
