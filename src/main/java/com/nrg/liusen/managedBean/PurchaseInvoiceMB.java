package com.nrg.liusen.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="purchaseInvoiceMB")
@RequestScoped
public class PurchaseInvoiceMB {

	PurchaseOrederDataBean purchaseOrederDataBean=new PurchaseOrederDataBean();
	private boolean flag=true;
	private List<String> invoiceList=null;
	public List<String> getInvoiceList() {
		return invoiceList;
	}
	public void setInvoiceList(List<String> invoiceList) {
		this.invoiceList = invoiceList;
	}
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
	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String purchaseINPageLoad() {

		System.out.println("Inside Load the purchaseINPageLoad Page");
		purchaseOrederDataBean.setPurOrderNo("");
		LiusenController controller=null;		
		ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (LiusenController) ctx.getBean("controller");
		invoiceList=new ArrayList<String>();
		invoiceList=controller.getinvoicePurchase();
		System.out.println("invoice -- > "+invoiceList.size()+" - " +invoiceList);
		setFlag(true);
		
		return "purchaseINLoadPage";

	}
	
	/**
	 * This method is used for show data tables depends on Order Number
	 * 
	 * @return
	 */
	public String searchOrder() 
	{		
		System.out.println("Inside the searchOrder Method Calling");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			if (validate(true))
			{
				System.out.println("after Validate");
				ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller=(LiusenController) ctx.getBean("controller");
				controller.invoiceView(purchaseOrederDataBean);
				setFlag(false);
			}
			else
			{
				setFlag(true);
			}
		} 
		catch (Exception e)
		{
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
		if (purchaseOrederDataBean.getPurOrderNo().equalsIgnoreCase("PURPAYIN00")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("purInNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Order Number."));
			}
			valid = false;
		} 
		return valid;
	}
	
	public String invoiceView() 
	{		
		System.out.println("view invoice records - order no - "+purchaseOrederDataBean.getPurOrderNo());
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{			
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			controller.invoiceViewForm(purchaseOrederDataBean);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "purchaseInvoiceForm";
	}	
}
