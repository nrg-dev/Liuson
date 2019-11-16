package com.nrg.liusen.managedBean;

import java.util.List;

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

@ManagedBean(name="salesPaymentStatusMB")
@RequestScoped
public class SalesPaymentStatusMB {

	SalesOrderDataBean salesOrderDataBean=new SalesOrderDataBean();
	private boolean flag=true;
	private List<String> paymentlist=null;
	public List<String> getPaymentlist() {
		return paymentlist;
	}
	public void setPaymentlist(List<String> paymentlist) {
		this.paymentlist = paymentlist;
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
	public String salesPayLoad() {

		System.out.println("Inside Load the salesPayLoad Page");
		setFlag(true);
		salesOrderDataBean.setSaleSalesOrderNo("");
		salesOrderDataBean.setSaleCategory("");
		salesOrderDataBean.setSalFromDate(null);
		salesOrderDataBean.setSaleToDate(null);
		return "salesPayPageLoad";

	}
	
	/**
	 * This method is used for show data tables depends on Date
	 * 
	 * @return
	 */
	public String searchPayOrder()
	{		
		System.out.println("Inside the searchPayOrder Method Calling");
		try 
		{
			if (validate(true))
			{
				System.out.println("After Validate inside  searchPayOrder method");
				ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController controller=(LiusenController) ctx.getBean("controller");
				controller.paymentStatusView1(salesOrderDataBean);				
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
		if (salesOrderDataBean.getSalFromDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("salefrom").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the From Date."));
			}
			valid = false;
		}  if (salesOrderDataBean.getSaleToDate()==null){
			
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"saleto").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Choose the  To Date."));
				}
				valid = false;
			
		}
		/*if (salesOrderDataBean.getSaleCategory().equalsIgnoreCase("SPAY00")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("salPayCate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Category."));
			}
			valid = false;
		} 
	*/
		return valid;
	}
	
	public void categoryChange(ValueChangeEvent v)
	{
		System.out.println("category change - > "+v.getNewValue());		
		LiusenController controller=null;		
		ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (LiusenController) ctx.getBean("controller");
		salesOrderDataBean.setSaleCategory(v.getNewValue().toString());
		paymentlist=controller.paymentCategory(salesOrderDataBean);
		System.out.println("payment -- > "+paymentlist.size()+" - " +paymentlist);
	}
	
	public String salepayview()
	{
		ApplicationContext ctx=null;
		LiusenController controller=null;
		
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			controller.salesPaymentView(salesOrderDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "salesPaymentView";
	}
	
	public String salpaymnetBack()
	{
		return "salPaymentbacks"; 
	}
}
