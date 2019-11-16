package com.nrg.liusen.managedBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="purchaseReportMB")
@RequestScoped
public class PurchaseReportMB {
	
	PurchaseOrederDataBean purchaseOrederDataBean=new PurchaseOrederDataBean();
	
	
	/**
	 * @return the purchaseOrederDataBean
	 */
	public PurchaseOrederDataBean getPurchaseOrederDataBean() {
		return purchaseOrederDataBean;
	}
	/**
	 * @param purchaseOrederDataBean the purchaseOrederDataBean to set
	 */
	public void setPurchaseOrederDataBean(
			PurchaseOrederDataBean purchaseOrederDataBean) {
		this.purchaseOrederDataBean = purchaseOrederDataBean;
	}
	private boolean flag=true;
	
	/**
	 * @return the flag
	 */
	public String total;

	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String fromdate1;
	public String toDate1;

	public String getFromdate1() {
		return fromdate1;
	}
	public void setFromdate1(String fromdate1) {
		this.fromdate1 = fromdate1;
	}
	public String getToDate1() {
		return toDate1;
	}
	public void setToDate1(String toDate1) {
		this.toDate1 = toDate1;
	}
	public boolean isFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public String purReportLoad() {

		System.out.println("Inside Load the purReportLoad Page");
		setFlag(true);
		purchaseOrederDataBean.setPurFromDate(null);
		purchaseOrederDataBean.setPurToDate(null);
		return "purReportPageLoad";

	}
	
	/**
	 * This method is used for show data tables depends on Date
	 * 
	 * @return
	 */
	public String searchDate() {
		
		System.out.println("Inside the searchDate Method Calling");
		purchaseOrederDataBean.setPurchaseList(null);
		try {
			total="0";
			if (validate(true)) {
				System.out.println("After Validate inside  searchDate method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController	controller = (LiusenController) ctx.getBean("controller");
				controller.purchasedate(purchaseOrederDataBean);
				setFlag(false);
				
			   
			}else{
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
		if (purchaseOrederDataBean.getPurFromDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("pReportFrom").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the From Date."));
			}
			valid = false;
		} 
	
		if (purchaseOrederDataBean.getPurToDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("pReportTo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the To Date."));
			}
			valid = false;
		} 
		return valid;
	}
}
