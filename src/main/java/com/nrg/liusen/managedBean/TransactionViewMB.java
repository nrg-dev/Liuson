package com.nrg.liusen.managedBean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.TransactionDataBean;
import com.nrg.liusen.shared.Transaction;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="transactionViewMB")
@RequestScoped
public class TransactionViewMB {

	TransactionDataBean TransactionDataBean=new TransactionDataBean();
	private boolean flag=true;
	private boolean  delflag=false;
	private boolean  editflag=false;
	public boolean isDelflag() {
		return delflag;
	}
	public void setDelflag(boolean delflag) {
		this.delflag = delflag;
	}
	public boolean isEditflag() {
		return editflag;
	}
	public void setEditflag(boolean editflag) {
		this.editflag = editflag;
	}
	public String flag0="";

	
	public String getFlag0() {
		return flag0;
	}
	public void setFlag0(String flag0) {
		this.flag0 = flag0;
	}
	public List<Transaction>translist=null;

	public List<Transaction> getTranslist() {
		return translist;
	}
	public void setTranslist(List<Transaction> translist) {
		this.translist = translist;
	}
	
	
	/**
	 * @return the transactionDataBean
	 */
	public TransactionDataBean getTransactionDataBean() {
		return TransactionDataBean;
	}
	/**
	 * @param transactionDataBean the transactionDataBean to set
	 */
	public void setTransactionDataBean(TransactionDataBean transactionDataBean) {
		TransactionDataBean = transactionDataBean;
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
	public String TransactionViewLoad() {

		System.out.println("Inside Load the TransactionViewLoad Page");
		setFlag(true);
		setValid(true);
		TransactionDataBean.setTransFromDate(null);
		TransactionDataBean.setTransToDate(null);
		return "transViewLoad";

	}
	/**
	 * This method is used for show data tables depends on Date
	 * 
	 * @return
	 */
	public String cancel()
	{
		return "";
		
	}
	public String searchDate() {
		
		System.out.println("Inside the searchDate Method Calling");
		try {
			if (validate(true)) {
				System.out.println("After Validate inside  searchDate method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager")){
					setDelflag(true);
					setEditflag(true);
				}else{
					setDelflag(false);
					setEditflag(false);	
				}
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController	controller = (LiusenController) ctx.getBean("controller");
			    controller.traview(TransactionDataBean);
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
		if (TransactionDataBean.getTransFromDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("tran_From").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the From Date."));
			}
			valid = false;
		}  if (TransactionDataBean.getTransToDate()==null) {

			
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"tran_To").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Choose the  To Date."));
				}
				valid = false;
				}
		return valid;

	}
	public void transview()
	{
System.out.println("inside transview"+TransactionDataBean.getTransno());	
LiusenController controller = null;
			 try{
				 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				 controller = (LiusenController) ctx.getBean("controller");
				 translist=controller.trans(TransactionDataBean.getTransno());
				 System.out.println("ttttranslist>>>>>"+translist.size());
				 if(translist.size()>0)
				 {
					 for(Transaction trans:translist)
					 {
						 TransactionDataBean.setTransDate(trans.getTransDate());
						 TransactionDataBean.setTransName(trans.getTransactionName());
						 TransactionDataBean.setTransPaymentType(trans.getPaymentMode());
						 TransactionDataBean.setTransTransactionType(trans.getTransactionType());
						 TransactionDataBean.setTransAmmount(trans.getTransactionAmount());
						 TransactionDataBean.setTransno(trans.getTransactionNo());
						 TransactionDataBean.setTransstatus(trans.getStatus());
						 
					 }
				 }
	}
			 catch(Exception e)
			 {
		e.printStackTrace();
			 }
	}
	private boolean valid =true;

	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String transedit()
	{
		System.out.println("Inside Edit Method");
		LiusenController controller=null;
		 try{
			 valid = true; 
			 flag0="1";
			 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			 controller = (LiusenController) ctx.getBean("controller");
			 System.out.println("orderno====="+TransactionDataBean.getTransno());
	         translist=controller.traedit(TransactionDataBean);
	         System.out.println("pay.,.,.,,.,.,<><><>"+TransactionDataBean.getTransPaymentType());
	         System.out.println("transaction[][][]][]"+TransactionDataBean.getTransTransactionType());
	         
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		return "cancel";
	}
	     
	private String status;

public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
public String save()
{
	System.out.println("Inside Save method Calling");
	LiusenController controller = null;
	if (validedit(true)) 
	{
		try {
			System.out.println("After Validation inside Save method");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			status=controller.update(TransactionDataBean);
			valid =false;

		}
		catch(Exception e)
		{
			System.out.println("inside exception");
		}
	}
	return "";
	
}

private boolean validedit(boolean flag1)
{
	boolean valid=true;
	String fieldName;
	FacesContext fc = FacesContext.getCurrentInstance();
	if (TransactionDataBean.getTransDate()==null) {
		if(flag1){
		fieldName = CommonValidate.findComponentInRoot("tran_date").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
		}
	valid= false;
	}
	if(TransactionDataBean.getTransPaymentType().equalsIgnoreCase("TRAN000"))
	{
		if(flag1)
		{
			fieldName=CommonValidate.findComponentInRoot("trans_Mode").getClientId(fc);
			fc.addMessage(fieldName ,new FacesMessage("Please Choose the Payment Mode"));
		}
		valid=false;
		}
	if(TransactionDataBean.getTransPaymentType().equalsIgnoreCase("Card")){
			if(StringUtils.isBlank(TransactionDataBean.getTransCardBankName())){
				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("tran_cardB").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Bank Name."));
					}
					valid = false;
			}else if(!StringUtils.isBlank(TransactionDataBean.getTransCardBankName())){
				if(!CommonValidate.validateName(TransactionDataBean.getTransCardBankName())){
				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("tran_cardB").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Bank Name."));
					}
					valid = false;
				}
			}
			if(StringUtils.isBlank(TransactionDataBean.getTransCardAccountNo())){
				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("trans_cardN").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Account Number."));
					}
					valid = false;
			}else if(!StringUtils.isBlank(TransactionDataBean.getTransCardAccountNo())){
				if(!CommonValidate.validateNumber(TransactionDataBean.getTransCardAccountNo())){
				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("trans_cardN").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Account Number."));
					}
					valid = false;
			}
			}
		}else if(TransactionDataBean.getTransPaymentType().equalsIgnoreCase("Cheque")){
			if(StringUtils.isBlank(TransactionDataBean.getTransChequeBName())){
				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("tran_ChequeB").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Bank Name."));
					}
					valid = false;
			}else if(!StringUtils.isBlank(TransactionDataBean.getTransChequeBName())){
				if(!CommonValidate.validateName(TransactionDataBean.getTransChequeBName())){

				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("tran_ChequeB").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Bank Name."));
					}
					valid = false;
			}
			}
			if(StringUtils.isBlank(TransactionDataBean.getTransChequeNo())){
				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("tran_ChequeN").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Cheque Number."));
					}
					valid = false;
			}else if(!StringUtils.isBlank(TransactionDataBean.getTransChequeNo())){
				if(!CommonValidate.validateNumber(TransactionDataBean.getTransChequeNo())){
					if(flag1){
						fieldName = CommonValidate.findComponentInRoot("tran_ChequeN").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Cheque Number."));
						}
						valid = false;
				}
			}
			if(TransactionDataBean.getTransChequeDate()==null){
				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("tran_ChequeDate").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Cheque Date."));
					}
					valid = false;
			}
		}else if(TransactionDataBean.getTransPaymentType().equalsIgnoreCase("Transfer")){
			if(StringUtils.isBlank(TransactionDataBean.getTransTransferBName())){
				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("tran_TransB").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Bank Name."));
					}
					valid = false;
			}else if(!StringUtils.isBlank(TransactionDataBean.getTransTransferBName())){
				if(!CommonValidate.validateName(TransactionDataBean.getTransTransferBName())){
				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("tran_TransB").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Bank Name."));
					}
					valid = false;
				}
			}
			if(StringUtils.isBlank(TransactionDataBean.getTranstranferAccontNo())){
				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("trans_TransN").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Account Number."));
					}
					valid = false;
			}
			else if(!StringUtils.isBlank(TransactionDataBean.getTranstranferAccontNo())){
				if(!CommonValidate.validateNumber(TransactionDataBean.getTranstranferAccontNo())){
				if(flag1){
					fieldName = CommonValidate.findComponentInRoot("trans_TransN").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Account Number."));
					}
					valid = false;
				}
			}
		}

	
	if(TransactionDataBean.getTransTransactionType().equalsIgnoreCase("TRANT00"))
	{
		if(flag1)
		{
			fieldName=CommonValidate.findComponentInRoot("tran_Type").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Transaction Type"));
			}
		valid=false;
	}
	
	if(StringUtils.isEmpty(TransactionDataBean.getTransName()))
	{
		if(flag1)
		{
			fieldName=CommonValidate.findComponentInRoot("tran_Name").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("lease Enter the Name"));
			}
		valid=false;
	}
	else if(StringUtils.isNotEmpty(TransactionDataBean.getTransName()))
	{
		if(!CommonValidate.validateName(TransactionDataBean.getTransName()))
		{
			if(flag1)
			{
				fieldName=CommonValidate.findComponentInRoot("tran_Name").getClientId(fc);
				fc.addMessage(fieldName ,new FacesMessage("Please Enter the valid Name"));
			}
			valid=false;
			}
		}
	if(StringUtils.isEmpty(TransactionDataBean.getTransAmmount()))
	{
		if(flag1)
		{
			fieldName=CommonValidate.findComponentInRoot("trans_Amnt").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Amount"));
			}
		valid=false;
	}
	else if(StringUtils.isNotEmpty(TransactionDataBean.getTransAmmount()))
	{
		if(!CommonValidate.validateNumber(TransactionDataBean.getTransAmmount()))
		{
			if(flag1)
			{
				fieldName=CommonValidate.findComponentInRoot("trans_Amnt").getClientId(fc);
				fc.addMessage(fieldName ,new FacesMessage("Please Enter the valid Amount"));
			}
			valid=false;
			}
		}
	return valid;
}
public String delete()
{
	System.out.println("inside delete method calling");
	System.out.println(TransactionDataBean.getTransno());
	
	LiusenController controller = null;
	if(TransactionDataBean.getTransno() != null)
	{
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (LiusenController) ctx.getBean("controller");
		status=controller.delettrans(TransactionDataBean);
		setValid(false);
	}
		return status;
	

}
}