package com.nrg.liusen.managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;







import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="purchasePaymentStatusMB")
@RequestScoped
public class PurchasePaymentStatusMB {
	PurchaseOrederDataBean purchaseOrederDataBean = new PurchaseOrederDataBean();
	private boolean flag=true;
	

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
	
	public String purchasePaymentViewLoad() {

		System.out.println("Inside Load the purchasePaymentViewLoad Page");
		setFlag(true);
		purchaseOrederDataBean.setPurToDate(null);
		purchaseOrederDataBean.setPurFromDate(null);
		return "purchasePayLoad";

	}
	/**
	 * This method is used for show data tables depends on Date
	 * 
	 * @return
	 */
	public String searchDate()
	{		
		System.out.println("Inside the searchDate Method Calling");
		try 
		{
			if (validate(true))
			{
				System.out.println("After Validate  - ");
				ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController controller=(LiusenController) ctx.getBean("controller");
				controller.paymentStatusView(purchaseOrederDataBean);
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
		if (purchaseOrederDataBean.getPurFromDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("purchaseFrom").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the From Date."));
			}
			valid = false;
		}  if (purchaseOrederDataBean.getPurToDate()==null) {

			
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"purchaseTo").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Choose the  To Date."));
				}
				valid = false;
			
		}
		return valid;
	}
	
	public String purcpayview()
	{
		ApplicationContext ctx=null;
		LiusenController controller=null;
		
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			controller.purcPaymentView(purchaseOrederDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "purchasePaymentStatusView";
	}
	
	public String purpaymnetBack()
	{
		return "purpaymnetBacks"; 
	}
}
