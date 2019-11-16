package com.nrg.liusen.managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="salesReportMB")
@RequestScoped
public class SalesReportMB {

	SalesOrderDataBean salesOrderDataBean=new SalesOrderDataBean();
	private boolean flag=true;
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
	public String salesReportLoad() {

		System.out.println("Inside Load the salesReportLoad Page");
		setFlag(true);
		salesOrderDataBean.setSalFromDate(null);
		salesOrderDataBean.setSaleToDate(null);
		return "saleReportPageLoad";

	}
	
	/**
	 * This method is used for show data tables depends on Date
	 * 
	 * @return
	 */
	public String searchDate() 
	{		
		System.out.println("Inside the searchDate Method Calling");
		salesOrderDataBean.setSalist(null);
		try
		{
			if (validate(true))
			{
				System.out.println("After Validate inside  searchDate method");
				ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController controller=(LiusenController)ctx.getBean("controller");
				controller.salesReport(salesOrderDataBean);
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
				fieldName = CommonValidate.findComponentInRoot("sReportFrom").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the From Date."));
			}
			valid = false;
		} 
	
		if (salesOrderDataBean.getSaleToDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("sReportTo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the To Date."));
			}
			valid = false;
		} 
		return valid;
	}
}
