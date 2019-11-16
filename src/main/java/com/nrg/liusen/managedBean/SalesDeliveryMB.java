package com.nrg.liusen.managedBean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;




import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.util.CommonValidate;
@ManagedBean(name="salesDeliveryMB")
@RequestScoped
public class SalesDeliveryMB {

	SalesOrderDataBean salesOrderDataBean=new SalesOrderDataBean();
	private  boolean flag=true;
	private List<String> saleslist=null;
	public List<String> getSaleslist() {
		return saleslist;
	}

	public void setSaleslist(List<String> saleslist) {
		this.saleslist = saleslist;
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
	public String salesDeliveryLoad() {

		System.out.println("Inside Load the salesDeliveryLoad Page");
		setFlag(true);
		salesOrderDataBean.setSaleSalesOrderNo("");
		return "salesDelLoad";

	}
	
	/**
	 * This method is used for show data tables depends on Date
	 * 
	 * @return
	 */
	public String searchOrder() {
		
		System.out.println("Inside the searchOrder Method Calling");
		try {
			if (validate(true)) {
				System.out.println("After Validate inside  searchOrder method");
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
		if (salesOrderDataBean.getSaleSalesOrderNo().equalsIgnoreCase("DEL00")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("saleNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Sales Order Number."));
			}
			valid = false;
		} 
		return valid;
	}
	
	@PostConstruct
	public void init()
	{
		System.out.println("inside constructor delivery sales -- ");
		LiusenController controller = null;
		ApplicationContext ctx =null;
		try
		{
			ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
