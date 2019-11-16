package com.nrg.liusen.managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="purchaseViewMB")
@RequestScoped
public class PurchaseViewMB {

	PurchaseOrederDataBean purchaseOrederDataBean=new PurchaseOrederDataBean();
	private boolean flag=true;
	public String validate;
	private boolean deleteflag=false;
	private boolean editflag=false;
	public boolean isDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(boolean deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
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
	
	public String purchaseViewLoad() {

		System.out.println("Inside Load the purchase View Load Page");
		purchaseOrederDataBean.setPurFromDate(null);
		purchaseOrederDataBean.setPurToDate(null);
		purchaseOrederDataBean.setPurVendor("");
		purchaseOrederDataBean.setPurCategory("");
		myValid = true;
		setFlag(true);
		return "purchaseViewLoad";

	}

	/**
	 * This method is used for show data tables depends on Date
	 * 
	 * @return
	 */
	public String searchDate() {
		
		System.out.println("Inside the searchDate Method Calling");
		try {
			if (validate(true)) {
				System.out.println("After Validate inside  searchDate method");
				setFlag(false);
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
		if (purchaseOrederDataBean.getPurFromDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("purViewFdate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the From Date."));
			}
			valid = false;
		}  if (purchaseOrederDataBean.getPurToDate()==null) {

			
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot("purViewTdate").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the  To Date."));
				}
				valid = false;
			
		}
		return valid;
	}
	
	

	/**
	 * This method is used for show data tables depends on Category name
	 * 
	 * @return
	 */

	public String searchCategory() {
		System.out.println("Inside the searchCategory Method Calling");
		try{
			if (validate1(true)) {
				System.out.println("After Validate1 inside  searchCategory method");
				setFlag(false);
			}else{
				setFlag(true);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";

	}

	private boolean validate1(boolean flag2) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if ((purchaseOrederDataBean.getPurCategory().equalsIgnoreCase(""))) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("purViewCate")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Select the Category Name."));
			}
			valid = false;
		} else if (!StringUtils.isBlank(purchaseOrederDataBean.getPurCategory())) {

			if (!CommonValidate.validateName(purchaseOrederDataBean.getPurCategory())) {
				if (flag2) {
					fieldName = CommonValidate.findComponentInRoot(
							"purViewCate").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Select the Category Name."));
				}
				valid = false;
			}
		}
		return valid;
	}
	
	
	private boolean validate2(boolean flag3) 
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if ((purchaseOrederDataBean.getPurQuantity1().equalsIgnoreCase("")))
		{
			if (flag3) 
			{
				fieldName = CommonValidate.findComponentInRoot("quan")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						" "+" Please Enter the Quantity."));
			}
			valid = false;
		}
		if(!purchaseOrederDataBean.getPurQuantity1().matches("^\\d+(\\.\\d+)*$"))
		{
			if(flag3)
			{
			fieldName = CommonValidate.findComponentInRoot("quan").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage(" "+" Quantity should be in Numbers. "));
			}
			valid = false;
		}
		return valid;
	}
	
	
	
	
	
	
	LiusenController controller = null;
	/*kasthuri 02-12-15 @@ purchase order search by date*/
	public String viewByDate()
	{
		purchaseOrederDataBean.setPurCategory("");
		try
		{
		if (validate(true)) 
		{
			System.out.println("-->> if");
			System.out.println("-->> fdate"+purchaseOrederDataBean.purFromDate);	
			System.out.println("-->> tdate"+purchaseOrederDataBean.purToDate);
			String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
			if(rolles.equalsIgnoreCase("Project Manager") || rolles.equalsIgnoreCase("Finance Manager")){
				setDeleteflag(true);
				setEditflag(false);
			}else if(rolles.equalsIgnoreCase("Chief Designer") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Accounting Staff")){
				setDeleteflag(true);
				setEditflag(true);
			}else{
				setDeleteflag(false);
				setEditflag(false);
				
			}
			myValid=true;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			purchaseOrederDataBean.setSearchType("viewByDate");
			controller = (LiusenController) ctx.getBean("controller");
			controller.purchaseView(purchaseOrederDataBean);
			setFlag(false);
		}
		else
		{
			System.out.println("-->> else");
			setFlag(true);
		}
		}
		catch(Exception e)
		{
			
		}
		return "";
		
	}
	
	/*kasthuri 02-12-15 @@ purchase order search by category*/
	public String viewByCategory()
	{
		purchaseOrederDataBean.setPurFromDate(null);
		purchaseOrederDataBean.setPurToDate(null);
		try
		{
		if (validate1(true)) 
		{
			System.out.println("-->> if");
			System.out.println("-->> cat type"+purchaseOrederDataBean.purCategory);
			String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
			if(rolles.equalsIgnoreCase("Project Manager") || rolles.equalsIgnoreCase("Finance Manager")){
				setDeleteflag(true);
				setEditflag(false);
			}else if(rolles.equalsIgnoreCase("Chief Designer") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Accounting Staff")){
				setDeleteflag(true);
				setEditflag(true);
			}else{
				setDeleteflag(false);
				setEditflag(false);
				
			}
			myValid=true;
			purchaseOrederDataBean.setSearchType("viewByCategory");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.purchaseView(purchaseOrederDataBean);
			setFlag(false);
		}
		else
		{
			System.out.println("-->> else");
			setFlag(true);
		}
		}
		catch(Exception e)
		{
			
		}
		return "";
		
	}
	
	/*kasthuri 02-12-15 @@ purchase order detailed view*/
	public String purchaseOrderDetailedView()
	{
	
		try
		{
			System.out.println("-->> mb po no "+purchaseOrederDataBean.purOrderNo);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.purchaseOrderDetailedView(purchaseOrederDataBean);
			if(purchaseOrederDataBean.getStatus().equalsIgnoreCase("success"))
			{
				System.out.println("-->>if status : "+purchaseOrederDataBean.getStatus());
				return "purchaseOrderView";
			}
			else
			{
				System.out.println("-->>else status : "+purchaseOrederDataBean.getStatus());
			}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	private boolean validate3(boolean flag4) 
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if(purchaseOrederDataBean.getPoApprovalStatus().equalsIgnoreCase("Approve"))
		{
			if (flag4) 
			{
				fieldName = CommonValidate.findComponentInRoot("purView").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(purchaseOrederDataBean.getPurOrderNo()+" is Already Approved"));

			}
			valid = false;
		}
		else if(purchaseOrederDataBean.getPoApprovalStatus().equalsIgnoreCase("Reject"))
		{
			if (flag4) 
			{
				fieldName = CommonValidate.findComponentInRoot("purView").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(purchaseOrederDataBean.getPurOrderNo()+" is Already Rejected"));
			}
			valid = false;
			
		}
		return valid;
	}
	
	/*kasthuri 02-12-15 @@ purchase order edit*/
	public String purchaseOrderEdit()
	{
	
		try
		{
			
			System.out.println("-->> mb po no "+purchaseOrederDataBean.purOrderNo);
			purchaseOrederDataBean.setPoApprovalStatus("");
			purchaseOrederDataBean.setPoStatus("");
			purchaseOrederDataBean.setStatus("");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.purchaseEditDeleteCheck(purchaseOrederDataBean);
			System.out.println("-->>postatus  : "+purchaseOrederDataBean.getPoStatus());
			System.out.println("-->>poapprovalstatus  : "+purchaseOrederDataBean.getPoApprovalStatus());
			if(validate3(true))
			{
				System.out.println("-->>true : ");
				if(purchaseOrederDataBean.getPoApprovalStatus().equalsIgnoreCase("pending") && purchaseOrederDataBean.getPoStatus().equalsIgnoreCase("ordered") )
				{
					controller.purchaseOrderDetailedView(purchaseOrederDataBean);
					if(purchaseOrederDataBean.getStatus().equalsIgnoreCase("success"))
					{
						System.out.println("-->>if  : ");
						return "purchaseOrderEdit";
					}
					else
					{
						System.out.println("-->>else : ");
					}	
				}
			}
			else
			{
				System.out.println("-->>fails: ");
			}
			
		}
		catch(Exception e)
		{
			setValidate(e.getMessage());
			System.out.println("-->>validation :"+getValidate());
			e.printStackTrace();
		}
		return "";
		
	}
	
	private boolean myValid = true;
	public boolean isMyValid() {
		return myValid;
	}
	public void setMyValid(boolean myValid) {
		this.myValid = myValid;
	}

	/*kasthuri 04-12-15 @@ purchase order delete*/
	public String purchaseOrderDelete()
	{
	
		try
		{
			System.out.println("-->> mb po no "+purchaseOrederDataBean.purOrderNo);
			purchaseOrederDataBean.setPoApprovalStatus("");
			purchaseOrederDataBean.setPoStatus("");
			purchaseOrederDataBean.setStatus("");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.purchaseEditDeleteCheck(purchaseOrederDataBean);
			System.out.println("-->>postatus  : "+purchaseOrederDataBean.getPoStatus());
			System.out.println("-->>poapprovalstatus  : "+purchaseOrederDataBean.getPoApprovalStatus());
			if(validate3(true))
			{
				if(purchaseOrederDataBean.getPoApprovalStatus().equalsIgnoreCase("pending") && purchaseOrederDataBean.getPoStatus().equalsIgnoreCase("ordered") )
				{
					controller.purchaseDelete(purchaseOrederDataBean);
					if(purchaseOrederDataBean.getStatus().equalsIgnoreCase("deleted"))
					{
						System.out.println("-->>if  : ");
						myValid=false;
					}
					else
					{
						System.out.println("-->>else : ");
						myValid=true; 
					}	
				}
					
			}
			else
			{
				System.out.println("-->>validation :fails");
			}
			
		}
		catch(Exception e)
		{
			myValid=true;
			setValidate(e.getMessage());
			System.out.println("-->>validation :"+getValidate());
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri 02-12-15 @@ purchase order edit*/
	public String poEdit()
	{
		System.out.println("-->> po no "+purchaseOrederDataBean.purOrderNo);
		System.out.println("-->> prod "+purchaseOrederDataBean.purProductName);
		System.out.println("-->> old quan"+purchaseOrederDataBean.purQuantity);
		purchaseOrederDataBean.setPurQuantity1("");
		myValid=true;
		return "purchaseOderEditConfirm";
	}
	/*kasthuri 02-12-15 @@ purchase order edit*/
	public String purchaseEditConfirm()
	{
	
		try
		{
			
			System.out.println("-->> new quan"+purchaseOrederDataBean.purQuantity1);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			if (validate2(true)) 
			{
				System.out.println("-->>validation true: ");
				controller.purchaseEditConfirm(purchaseOrederDataBean);
				if(purchaseOrederDataBean.getStatus().equalsIgnoreCase("success"))
				{
					System.out.println("-->>if status : "+purchaseOrederDataBean.getStatus());
					myValid=false;
				}
				else
				{
					System.out.println("-->>else status : "+purchaseOrederDataBean.getStatus());
					myValid=true;
				}
			}
			else
			{
				System.out.println("-->>validation fails: ");
			}
			
		}
		catch(Exception e)
		{
			
		}
		return "";
		
	}
	
	
	/*kasthuri 04-12-15 @@ return to back page*/
	public String back()
	{
		setValidate("");
		myValid=true;
		flag=true;
		purchaseOrederDataBean.setPurCrossTotal("");
		purchaseOrederDataBean.setPurchaseList(null);
		return "purchaseOrder";
	}

	/**
	 * @return the editflag
	 */
	public boolean isEditflag() {
		return editflag;
	}

	/**
	 * @param editflag the editflag to set
	 */
	public void setEditflag(boolean editflag) {
		this.editflag = editflag;
	}
	
}
