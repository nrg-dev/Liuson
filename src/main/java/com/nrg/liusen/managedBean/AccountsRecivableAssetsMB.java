package com.nrg.liusen.managedBean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.domain.TransactionDataBean;
import com.nrg.liusen.util.CommonValidate;


@ManagedBean(name="accountsReceivableAssetsMB")
@RequestScoped

public class AccountsRecivableAssetsMB
{
	com.nrg.liusen.domain.PurchaseDataBean PurchaseDataBean=new com.nrg.liusen.domain.PurchaseDataBean(); 
	private boolean flag0=true;

	
	public com.nrg.liusen.domain.PurchaseDataBean getPurchaseDataBean() {
		return PurchaseDataBean;
	}
	public void setPurchaseDataBean(
			com.nrg.liusen.domain.PurchaseDataBean purchaseDataBean) {
		PurchaseDataBean = purchaseDataBean;
	}
	public boolean isFlag0() {
		return flag0;
	}
	public void setFlag0(boolean flag0) {
		this.flag0 = flag0;
	}


	private Date fromDate;
	private Date toDate;
	public String flag="none";
	public String flag1="none";
	private String validate;
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlag1() {
		return flag1;
	}
	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	
	private boolean valid =true;

	
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String Accountsviewload() {

		System.out.println("Inside Load the Accountsview Page");
		setFlag0(false);
        setValid(true);
		PurchaseDataBean.setFromDate(fromDate);
		PurchaseDataBean.setToDate(toDate);
		return "winner";

	}	
	
	public String search()
	{
		System.out.println("Inside the searchDate Method Calling");
		try {
			if (validate(true)) {
				System.out.println("After Validate inside  searchDate method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController	controller = (LiusenController) ctx.getBean("controller");
				controller.searchdate(PurchaseDataBean);
				setFlag0(true);
			}else{
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
		
	}
	
	
	private boolean validate(boolean flag1) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (PurchaseDataBean.getFromDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("acc_From").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the From Date."));
			}
			valid = false;
		}  if (PurchaseDataBean.getToDate()==null) {

			
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"acc_To").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Choose the  To Date."));
				}
				valid = false;
				}
		return valid;

	}
	
}