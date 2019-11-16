package com.nrg.liusen.managedBean;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;





import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.util.CommonValidate;


@ManagedBean(name="accountsPayableLiablityMB")
public class accountsPayableLiablityMB
{
	private Date fromDate;
	private Date toDate;
	public String flag="none";
	public String flag1="none";
	private String validate;
	public boolean flags=false;
	public boolean isFlags() {
		return flags;
	}

	public void setFlags(boolean flags) {
		this.flags = flags;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}
	List<PurchaseOrederDataBean> accountList=null;
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

	public List<PurchaseOrederDataBean> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<PurchaseOrederDataBean> accountList) {
		this.accountList = accountList;
	}

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
	PurchaseOrederDataBean purchaseOrder;
	public String viewAccountPayable()
	{
		LiusenController controller=null;
		flags=false;
		try
		{
			if (validate(true)) 
			{
				System.out.println("After Validate inside  searchDate method");
				purchaseOrder=new PurchaseOrederDataBean();
				System.out.println("---------------inside mb------------------");
				System.out.println("from date------------->"+fromDate);
				System.out.println("to date----------->"+toDate);
				purchaseOrder.setPurFromDate(fromDate);
				System.out.println("CHECK...........1");
				purchaseOrder.setPurToDate(toDate);
				System.out.println("CHECK...........2");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				controller.viewAccountPayable(purchaseOrder);
				accountList=purchaseOrder.getResulfinal();
				System.out.println("CHECK...3"+purchaseOrder.getResulfinal());
				System.out.println("LIST SIZE"+accountList);			
				flags=true;
			}			
			else
			{
				
			}
		}
		catch(Exception e)
		{
			setValidate(e.getMessage());
			System.out.println("--------------inside exception--------------");
			e.printStackTrace();
		}
		return null;
		
	}
	public String cancel()
	{
		
		flag="none";
		flag1="none";
		fromDate=null;
		toDate=null;
		flags=false;
		return "aAccountReceivableAsset.xhtml";
		
	}
	
	private boolean validate(boolean flag1) 
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fromDate==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("profitFrom").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the From Date."));
			}
			valid = false;
		} 
		if (toDate==null) {
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
                   