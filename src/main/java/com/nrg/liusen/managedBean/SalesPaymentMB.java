package com.nrg.liusen.managedBean;

import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="salesPaymentMB")
@RequestScoped
public class SalesPaymentMB {

	SalesOrderDataBean salesOrderDataBean=new SalesOrderDataBean();
	private boolean flag=true;
	private List<String> paymentList=null;
	private boolean valid=false;
	private String valid1;
	private String valid2="none";
	private String card;
	private String cheque; 
	private boolean actionflag=false;
	
	public boolean isActionflag() {
		return actionflag;
	}
	public void setActionflag(boolean actionflag) {
		this.actionflag = actionflag;
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
	public String getValid2() {
		return valid2;
	}
	public void setValid2(String valid2) {
		this.valid2 = valid2;
	}
	public String getValid1() {
		return valid1;
	}
	public void setValid1(String valid1) {
		this.valid1 = valid1;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public List<String> getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(List<String> paymentList) {
		this.paymentList = paymentList;
	}
	/**
	 * @return the salesOrderDataBean
	 */
	public SalesOrderDataBean getSalesOrderDataBean() {
		return salesOrderDataBean;
	}
	/**
	 * @param salesOrderDataBean the salesOrderDataBean to set
	 */
	public void setSalesOrderDataBean(SalesOrderDataBean salesOrderDataBean) {
		this.salesOrderDataBean = salesOrderDataBean;
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
	public String salesPayPageLoad() {

		System.out.println("Inside Load the salesPayPageLoad Page");
		setFlag(true);
		salesOrderDataBean.setSaleSalesOrderNo("");
		salesOrderDataBean.setSaleCategory("");
		salesOrderDataBean.setPaymenttype("");
		salesOrderDataBean.setSalePrice("");
		salesOrderDataBean.setBankname("");
		salesOrderDataBean.setAccountno("");
		salesOrderDataBean.setChequedate(null);
		salesOrderDataBean.setChequeno("");
		setValid(false);
		LiusenController controller=null;		
		ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (LiusenController) ctx.getBean("controller");		
		paymentList=controller.getpaymentSales(salesOrderDataBean);
		Collections.sort(paymentList);
		return "salesPayLoadPage";

	}
	
	/**
	 * This method is used for show data tables depends on Date
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
				System.out.println("After Validate ");
				ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller=(LiusenController) ctx.getBean("controller");
				controller.paymentViewSales(salesOrderDataBean);
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
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
		/*if (salesOrderDataBean.getSaleCategory().equalsIgnoreCase("select")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("category").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Category."));
			}
			valid = false;
		} */
		if (salesOrderDataBean.getSaleSalesOrderNo().equalsIgnoreCase("SALPAY00")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("salepaymentNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Order Number."));
			}
			valid = false;
		} 
		return valid;
	}
	
	public void categoryChange(ValueChangeEvent v)
	{
		System.out.println("category change - > "+v.getNewValue());		
		LiusenController controller=null;		
		ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (LiusenController) ctx.getBean("controller");
		salesOrderDataBean.setSaleCategory(v.getNewValue().toString());
		paymentList=controller.getpaymentSales(salesOrderDataBean);
		Collections.sort(paymentList);
		System.out.println("payment -- > "+paymentList.size());
	}	
	
	public String paymentView() 
	{		
		System.out.println("view invoice records - order no - "+salesOrderDataBean.getSaleSalesOrderNo());
		ApplicationContext ctx=null;
		LiusenController controller=null;
		setValid1("none");
		try
		{			
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			controller.payForm(salesOrderDataBean);
			if(Integer.parseInt(salesOrderDataBean.getPaidamount())>0)
			{
				System.out.println("greater 0 ");
				valid1="0";
				card="0";
				cheque="0";
			}
			else
			{
				System.out.println("not 0 ");
				salesOrderDataBean.setPaymenttype("");
				valid1="1";
				card="0";
				cheque="0";
				salesOrderDataBean.setBankname("");
				salesOrderDataBean.setAccountno("");
				salesOrderDataBean.setChequedate(null);
				salesOrderDataBean.setChequeno("");
			}
			System.out.println("valid - > "+valid1);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "salesPaymentReceive";
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
				controller.paysales(salesOrderDataBean);
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
		if(StringUtils.isBlank(salesOrderDataBean.getSalePrice()))
		{
			System.out.println(" - valid 11 ");
			if (payy)
			{
				fieldName = CommonValidate.findComponentInRoot("amount").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Amount."));
			}
			valid = false;
		} 
		else if(StringUtils.isNotBlank(salesOrderDataBean.getSalePrice()))
		{
			System.out.println(" - valid ");
			if(!StringUtils.isNumeric(salesOrderDataBean.getSalePrice()))
			{
				System.out.println(" - valid 1");
				if (payy)
				{
					fieldName = CommonValidate.findComponentInRoot("amount").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Amount should be Number."));
				}
				valid = false;
			}
			if(Integer.parseInt(salesOrderDataBean.getSalePrice())>Integer.parseInt(salesOrderDataBean.getBalanceamount()))
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
		if(Integer.parseInt(salesOrderDataBean.getPaidamount())==0)
		{
			if(salesOrderDataBean.getPaymenttype().equals("select"))
			{
				if (payy)
				{
					fieldName = CommonValidate.findComponentInRoot("type").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Payment Mode."));
				}
				valid = false;
			}
			else if(!salesOrderDataBean.getPaymenttype().equals("select"))
			{
				if(salesOrderDataBean.getPaymenttype().equals("Card") || salesOrderDataBean.getPaymenttype().equals("Transfer"))
				{
					if(StringUtils.isBlank(salesOrderDataBean.getBankname()))
					{
						if (payy)
						{
							fieldName = CommonValidate.findComponentInRoot("tran_cardB").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Bank Name"));
						}
						valid = false;
					}
					if(StringUtils.isBlank(salesOrderDataBean.getAccountno()))
					{
						if (payy)
						{
							fieldName = CommonValidate.findComponentInRoot("trans_cardN").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Account Number"));
						}
						valid = false;
					}
				}
				if(salesOrderDataBean.getPaymenttype().equals("Cheque"))
				{
					if(StringUtils.isBlank(salesOrderDataBean.getBankname()))
					{
						if (payy)
						{
							fieldName = CommonValidate.findComponentInRoot("tran_ChequeB").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Bank Name"));
						}
						valid = false;
					}
					if(StringUtils.isBlank(salesOrderDataBean.getChequeno()))
					{
						if (payy)
						{
							fieldName = CommonValidate.findComponentInRoot("tran_ChequeN").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Cheque Number"));
						}
						valid = false;
					}
					if(salesOrderDataBean.getChequedate()==null)
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
