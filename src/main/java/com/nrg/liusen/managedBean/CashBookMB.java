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


@ManagedBean(name ="cashBookMB")
@RequestScoped
public class CashBookMB 
{
PurchaseOrederDataBean purchaseOrederDataBean=new PurchaseOrederDataBean();
private boolean flag=false;
private boolean flag1=true;

	public boolean isFlag() {
	return flag;
}
public void setFlag(boolean flag) {
	this.flag = flag;
}
public boolean isFlag1() {
	return flag1;
}
public void setFlag1(boolean flag1) {
	this.flag1 = flag1;
}
	public PurchaseOrederDataBean getPurchaseOrederDataBean() {
		return purchaseOrederDataBean;
	}
	public void setPurchaseOrederDataBean(
			PurchaseOrederDataBean purchaseOrederDataBean) {
		this.purchaseOrederDataBean = purchaseOrederDataBean;
	}
	
	
	
public String redirectCashBook()
{
		System.out.println("----------------inside redirectbook------------");
		purchaseOrederDataBean.setFromDate(null);
		purchaseOrederDataBean.setToDate(null);
		setFlag(false);
		flag1=true;
		return "cashBook";
}

public String findcashbook()
{
	try
	{
		setFlag(true);
		if(validate(true))
		{
			System.out.println("Inside findcashbook method");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			LiusenController controller = (LiusenController) ctx.getBean("controller");
			controller.findcashbook(purchaseOrederDataBean);
			setFlag(true);
		}
	}
	catch(Exception p)
	{
		p.printStackTrace();
	}
	return null;
	
}

private boolean validate(boolean flag)
{
	boolean valid = true;
	String fieldName;
	FacesContext fc = FacesContext.getCurrentInstance();
	
	if(purchaseOrederDataBean.getFromDate()==null){
		if(flag){
			fieldName = CommonValidate.findComponentInRoot("Fdate").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the from date"));
			}
			valid = false;
	}
	if(purchaseOrederDataBean.getToDate()==null){
		if(flag){
			fieldName = CommonValidate.findComponentInRoot("Tdate").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the to date"));
			}
			valid = false;
	}
	return valid;
}

	
}

