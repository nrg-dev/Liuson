package com.nrg.liusen.managedBean;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="purchasePaymentMB")
@RequestScoped
public class PurchasePaymentMB {

	PurchaseOrederDataBean purchaseOrederDataBean=new PurchaseOrederDataBean();
	private boolean flag=true;
	
	private boolean valid=false;
	private List<String> payorders=null;
	private String valid1;
	private String card;
	private String cheque;  
	private boolean actionflag=true;
	public boolean isActionflag() {
		return actionflag;
	}
	public void setActionflag(boolean actionflag) {
		this.actionflag = actionflag;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getValid1() {
		return valid1;
	}
	public void setValid1(String valid1) {
		this.valid1 = valid1;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getCheque() {
		return cheque;
	}
	public void setCheque(String cheque) {
		this.cheque = cheque;
	}
	/**
	 * @return the purchaseOrederDataBean
	 */
	public PurchaseOrederDataBean getPurchaseOrederDataBean() {
		return purchaseOrederDataBean;
	}
	public List<String> getPayorders() {
		return payorders;
	}
	public void setPayorders(List<String> payorders) {
		this.payorders = payorders;
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
	
	public String purchasePayPageLoad() {

		System.out.println("Inside Load the purchasePayPageLoad Page");
		setFlag(true);
		purchaseOrederDataBean.setPurOrderNo("");
		ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		LiusenController controller=(LiusenController) ctx.getBean("controller");
		payorders=controller.purchasepayorders();
		System.out.println("purhase payment order nos -> "+payorders);
		Collections.sort(payorders);
		return "purchasePaymentLoadPage";

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
				System.out.println("After Validate inside  searchOrder method");
				ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller=(LiusenController) ctx.getBean("controller");
				controller.paymentViewpurchase(purchaseOrederDataBean);
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
				if(rolles.equalsIgnoreCase("Project Manager")){
					setActionflag(true);
					
				}else{
					setActionflag(false);
					
				}
				setFlag(false);
			}
			else
			{
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
		if (purchaseOrederDataBean.getPurOrderNo().equalsIgnoreCase("PURPAY10")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("purPAYNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Order Number."));
			}
			valid = false;
		} 
		return valid;
	}
	
	public String purPayment()
	{
		ApplicationContext ctx=null;
		LiusenController controller=null;
		setValid(false);
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			controller.payPurchase(purchaseOrederDataBean);
			if(Integer.parseInt(purchaseOrederDataBean.getPaidamount())>0)
			{
				System.out.println("paid amount greater -- .");
				valid1="0";
				card="0";
				cheque="0";
				purchaseOrederDataBean.setPurprice("");
			}
			else
			{
				System.out.println("paid amount 0 --");
				valid1="1";
				card="0";
				cheque="0";
				purchaseOrederDataBean.setPaymenttype("");
				purchaseOrederDataBean.setBankname("");
				purchaseOrederDataBean.setChequedate(null);
				purchaseOrederDataBean.setChequeno("");
				purchaseOrederDataBean.setAccno("");
				purchaseOrederDataBean.setPurprice("");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "purchasePaymentForm";
	}
	
	public void paymentType(ValueChangeEvent v)
	{
		System.out.println("payment type - >"+v.getNewValue());
		if(v.getNewValue().toString().equals("Cash"))
		{
			card="0";
			cheque="0";
		}
		else if(v.getNewValue().toString().equals("Card") || v.getNewValue().toString().equals("Transfer"))
		{
			card="1";
			cheque="0";
		}
		else if(v.getNewValue().toString().equals("Cheque"))
		{
			card="0";
			cheque="1";
		}
	}
	
	public String pay()
	{
		System.out.println("pay for sales");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		setValid(false);
		try
		{	
			if(valipay(true))
			{
				ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller=(LiusenController) ctx.getBean("controller");
				controller.payPurchaseAmount(purchaseOrederDataBean);
				setValid(true);
			}
			else
			{
				System.out.println("vaidation -- ");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	private boolean valipay(boolean payy)
	{
		System.out.println(" - valids ");
		boolean valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(StringUtils.isBlank(purchaseOrederDataBean.getPurprice()))
		{
			System.out.println(" - valid 11 ");
			if (payy)
			{
				fieldName = CommonValidate.findComponentInRoot("amount").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Amount."));
			}
			valid = false;
		} 
		else if(StringUtils.isNotBlank(purchaseOrederDataBean.getPurprice()))
		{
			System.out.println(" - valid ");
			if(!StringUtils.isNumeric(purchaseOrederDataBean.getPurprice()))
			{
				System.out.println(" - valid 1");
				if (payy)
				{
					fieldName = CommonValidate.findComponentInRoot("amount").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Amount should be Number."));
				}
				valid = false;
			}
			if(Integer.parseInt(purchaseOrederDataBean.getPurprice())>Integer.parseInt(purchaseOrederDataBean.getBalamount()))
			{
				System.out.println(" - valid 2");
				if (payy)
				{
					fieldName = CommonValidate.findComponentInRoot("amount").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Correct Amount."));
				}
				valid = false;
			}
		}
		if(Integer.parseInt(purchaseOrederDataBean.getPaidamount())==0)
		{
			if(purchaseOrederDataBean.getPaymenttype().equals("select"))
			{
				if (payy)
				{
					fieldName = CommonValidate.findComponentInRoot("type").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Payment Mode."));
				}
				valid = false;
			}
			else if(!purchaseOrederDataBean.getPaymenttype().equals("select"))
			{
				if(purchaseOrederDataBean.getPaymenttype().equals("Card") || purchaseOrederDataBean.getPaymenttype().equals("Transfer"))
				{
					if(StringUtils.isBlank(purchaseOrederDataBean.getBankname()))
					{
						if (payy)
						{
							fieldName = CommonValidate.findComponentInRoot("tran_cardB").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Bank Name"));
						}
						valid = false;
					}
					if(StringUtils.isBlank(purchaseOrederDataBean.getAccno()))
					{
						if (payy)
						{
							fieldName = CommonValidate.findComponentInRoot("trans_cardN").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Account Number"));
						}
						valid = false;
					}
				}
				if(purchaseOrederDataBean.getPaymenttype().equals("Cheque"))
				{
					if(StringUtils.isBlank(purchaseOrederDataBean.getBankname()))
					{
						if (payy)
						{
							fieldName = CommonValidate.findComponentInRoot("tran_ChequeB").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Bank Name"));
						}
						valid = false;
					}
					if(StringUtils.isBlank(purchaseOrederDataBean.getChequeno()))
					{
						if (payy)
						{
							fieldName = CommonValidate.findComponentInRoot("tran_ChequeN").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Cheque Number"));
						}
						valid = false;
					}
					if(purchaseOrederDataBean.getChequedate()==null)
					{
						if (payy)
						{
							fieldName = CommonValidate.findComponentInRoot("tran_ChequeDate").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Cheque Date"));
						}
						valid = false;
					}
				}
			} 
		}		
		return valid;
	}
}
