package com.nrg.liusen.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="salesInvoiceMB")
@RequestScoped
public class SalesInvoiceMB {

	SalesOrderDataBean salesOrderDataBean=new SalesOrderDataBean();
	private boolean flag=true;
	private List<String> invoiceList=null;
	public List<String> getInvoiceList() {
		return invoiceList;
	}
	public void setInvoiceList(List<String> invoiceList) {
		this.invoiceList = invoiceList;
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
	public String salesInvoiceLoad() {

		System.out.println("Inside Load the salesInvoiceLoad Page");
		setFlag(true);
		salesOrderDataBean.setSaleCategory("");
		salesOrderDataBean.setSaleSalesOrderNo("");
		return "salesInLoad";

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
				System.out.println("after validate -- ");
				ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller=(LiusenController) ctx.getBean("controller");
				controller.invoiceViewSales(salesOrderDataBean);
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
		if (salesOrderDataBean.getSaleCategory().equalsIgnoreCase("select")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("category").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Category."));
			}
			valid = false;
		} 
		if (salesOrderDataBean.getSaleSalesOrderNo().equalsIgnoreCase("SALIN00")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("saleInNo").getClientId(fc);
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
		invoiceList=controller.getinvoiceSales(salesOrderDataBean);
		System.out.println("invoice -- > "+invoiceList.size()+" - " +invoiceList);
	}
	
	public String invoiceView() 
	{		
		System.out.println("view invoice records - order no - "+salesOrderDataBean.getSaleSalesOrderNo());
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{			
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			controller.invoiceViewForm(salesOrderDataBean);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "salesInvoiceViewForm";
	}	
}
