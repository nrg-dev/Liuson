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

@ManagedBean(name="purchaseOrderCloseMB")
@RequestScoped
public class PurchaseOrderCloseMB {

	PurchaseOrederDataBean purchaseOrederDataBean=new PurchaseOrederDataBean();
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
	
	public String purchaseCloseLoad() {

		System.out.println("Inside Load the purchasePaymentViewLoad Page");
		setFlag(true);
		purchaseOrederDataBean.setPurToDate(null);
		purchaseOrederDataBean.setPurFromDate(null);
		return "purchaseCloseLoad";

	}
	/**
	 * This method is used for show data tables depends on Date
	 * 
	 * @return
	 */
	public String searchDate() 
	{		
		System.out.println("Inside the searchDate Method Calling");
		purchaseOrederDataBean.setPurchaseList(null);
		try
		{
			if (validate(true)) 
			{
				System.out.println("After Validate inside  searchDate method");
				ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController controller=(LiusenController) ctx.getBean("controller");
				controller.purchaseOrderClose(purchaseOrederDataBean);
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
				fieldName = CommonValidate.findComponentInRoot("purchaseCloseFrom").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the From Date."));
			}
			valid = false;
		}  if (purchaseOrederDataBean.getPurToDate()==null) {

			
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"purchaseCloseTo").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Choose the  To Date."));
				}
				valid = false;
			
		}
		return valid;
	}
}
