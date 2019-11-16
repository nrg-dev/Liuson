package com.nrg.liusen.managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.TransactionDataBean;
import com.nrg.liusen.util.CommonValidate;


@ManagedBean(name="transactionMB")
@RequestScoped
public class TransactionMB {
	private boolean flag=true;

	TransactionDataBean transactionDataBean=new TransactionDataBean();

	/**
	 * @return the transactionDataBean
	 * 
	 */
	
	public TransactionDataBean getTransactionDataBean() {
		return transactionDataBean;
	}

	/**
	 * @param transactionDataBean the transactionDataBean to set
	 */
	public void setTransactionDataBean(TransactionDataBean transactionDataBean) {
		this.transactionDataBean = transactionDataBean;
	}
	
	public String TransLoad() {

		System.out.println("Inside Load the TransLoad Page");
       setValid(false);
        setFlag(true);
		transactionDataBean.setTransAmmount("");
		transactionDataBean.setTransCardAccountNo("");
		transactionDataBean.setTransCardBankName("");
		transactionDataBean.setTransChequeBName("");
		transactionDataBean.setTransChequeDate(null);
		transactionDataBean.setTransChequeNo("");
		transactionDataBean.setTransDate(null);
		transactionDataBean.setTransName("");
		transactionDataBean.setTransNote("");
		transactionDataBean.setTransPaymentType("");
		transactionDataBean.setTransTransferBName("");
		transactionDataBean.setTransTransactionType("");
		transactionDataBean.setTransTransferBName("");
		return "transRegisterPage";
	}
	public String submit()
	{
		LiusenController controller=null;
		try
		{
			System.out.println("----------Inside submit Method Calling-----");
			if (validate(true))
			{
				System.out.println("After Validation inside Submit method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				String status=controller.inserttrans(transactionDataBean);			 
				 valid =true;	
				 return "";				
			 }		
		}
		catch (Exception e)
		{
		System.out.println("----------Inside submit Method Exception Calling-----");
		e.printStackTrace();	
		}
		return "";
	}
	
	public boolean isFlag()
	{
		return flag;
	
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	private boolean valid = true; 
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	private boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (transactionDataBean.getTransDate()==null) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("tran_date").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
			}
			valid = false;
		}
		if(transactionDataBean.getTransPaymentType().equalsIgnoreCase("TRAN000")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("trans_Mode").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Payment Mode."));
				}
				valid = false;
		}else if(!transactionDataBean.getTransPaymentType().equalsIgnoreCase("TRAN001")){
			if(transactionDataBean.getTransPaymentType().equalsIgnoreCase("TRAN002")){
				if(StringUtils.isBlank(transactionDataBean.getTransCardBankName())){
					if(flag){
						fieldName = CommonValidate.findComponentInRoot("tran_cardB").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the Bank Name."));
						}
						valid = false;
				}else if(StringUtils.isNotEmpty(transactionDataBean.getTransCardBankName())){
					if(!CommonValidate.validateName(transactionDataBean.getTransCardBankName())){
					if(flag){
						fieldName = CommonValidate.findComponentInRoot("tran_cardB").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Bank Name."));
						}
						valid = false;
					}
				}
				if(StringUtils.isBlank(transactionDataBean.getTransCardAccountNo())){
					if(flag){
						fieldName = CommonValidate.findComponentInRoot("trans_cardN").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the Account Number."));
						}
						valid = false;
				}else if(StringUtils.isNotEmpty(transactionDataBean.getTransCardAccountNo())){
					if(!CommonValidate.validateNumber(transactionDataBean.getTransCardAccountNo())){
					if(flag){
						fieldName = CommonValidate.findComponentInRoot("trans_cardN").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Account Number."));
						}
						valid = false;
				}
				}
			}else if(transactionDataBean.getTransPaymentType().equalsIgnoreCase("TRAN003")){
				if(StringUtils.isBlank(transactionDataBean.getTransChequeBName())){
					if(flag){
						fieldName = CommonValidate.findComponentInRoot("tran_ChequeB").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the Bank Name."));
						}
						valid = false;
				}else if(StringUtils.isNotEmpty(transactionDataBean.getTransChequeBName())){
					if(!CommonValidate.validateName(transactionDataBean.getTransChequeBName())){

					if(flag){
						fieldName = CommonValidate.findComponentInRoot("tran_ChequeB").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Bank Name."));
						}
						valid = false;
				}
				}
				if(StringUtils.isBlank(transactionDataBean.getTransChequeNo())){
					if(flag){
						fieldName = CommonValidate.findComponentInRoot("tran_ChequeN").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the Cheque Number."));
						}
						valid = false;
				}else if(StringUtils.isNotEmpty(transactionDataBean.getTransChequeNo())){
					if(!CommonValidate.validateNumber(transactionDataBean.getTransChequeNo())){
						if(flag){
							fieldName = CommonValidate.findComponentInRoot("tran_ChequeN").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Cheque Number."));
							}
							valid = false;
					}
				}
				if(transactionDataBean.getTransChequeDate()==null){
					if(flag){
						fieldName = CommonValidate.findComponentInRoot("tran_ChequeDate").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Choose the Cheque Date."));
						}
						valid = false;
				}
			}else if(transactionDataBean.getTransPaymentType().equalsIgnoreCase("TRAN004")){
				if(StringUtils.isBlank(transactionDataBean.getTransTransferBName())){
					if(flag){
						fieldName = CommonValidate.findComponentInRoot("tran_TransB").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the Bank Name."));
						}
						valid = false;
				}else if(StringUtils.isNotEmpty(transactionDataBean.getTransTransferBName())){
					if(!CommonValidate.validateName(transactionDataBean.getTransTransferBName())){
					if(flag){
						fieldName = CommonValidate.findComponentInRoot("tran_TransB").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Bank Name."));
						}
						valid = false;
					}
				}
				if(StringUtils.isBlank(transactionDataBean.getTranstranferAccontNo())){
					if(flag){
						fieldName = CommonValidate.findComponentInRoot("trans_TransN").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the Account Number."));
						}
						valid = false;
				}else if(StringUtils.isNotEmpty(transactionDataBean.getTranstranferAccontNo())){
					if(!CommonValidate.validateNumber(transactionDataBean.getTranstranferAccontNo())){
					if(flag){
						fieldName = CommonValidate.findComponentInRoot("trans_TransN").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Account Number."));
						}
						valid = false;
					}
				}
			}
		}
		if(transactionDataBean.getTransTransactionType().equalsIgnoreCase("TRANT00"))
		{
			if(flag)
			{
				fieldName=CommonValidate.findComponentInRoot("tran_Type").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Transaction Type"));
				}
			valid=false;
		}
		if(StringUtils.isBlank(transactionDataBean.getTransName())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("tran_Name").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Name."));
				}
				valid = false;
		}else if(StringUtils.isNotEmpty(transactionDataBean.getTransName())){
			if(!CommonValidate.validateName(transactionDataBean.getTransName())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("tran_Name").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Name."));
				}
				valid = false;
		}
		}
		if(StringUtils.isBlank(transactionDataBean.getTransAmmount())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("trans_Amnt").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Amount."));
				}
				valid = false;
		}else if(StringUtils.isNotEmpty(transactionDataBean.getTransAmmount())){
			if(!CommonValidate.validateNumber(transactionDataBean.getTransAmmount())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("trans_Amnt").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Amount."));
				}
				valid = false;
		}
		}
		return valid;
	}
	public String reset() {
		System.out.println("Inside Rest Method Calling");
		transactionDataBean.setTransAmmount("");
		transactionDataBean.setTransCardAccountNo("");
		transactionDataBean.setTransCardBankName("");
		transactionDataBean.setTransChequeBName("");
		transactionDataBean.setTransChequeDate(null);
		transactionDataBean.setTransChequeNo("");
		transactionDataBean.setTransDate(null);
		transactionDataBean.setTransName("");
		transactionDataBean.setTransNote("");
		transactionDataBean.setTransPaymentType("");
		transactionDataBean.setTransTransferBName("");
		transactionDataBean.setTransTransactionType("");
		transactionDataBean.setTransTransferBName("");
		return "";
	}
	public String cancel() {
		System.out.println("=======************ Inside Cancel Method Calling***********------");
		return "canSuccess";

	}
}