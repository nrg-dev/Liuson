package com.nrg.liusen.managedBean;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.StockDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.util.CommonValidate;

public class StockDamageMB {

	StockDataBean stockDataBean = new StockDataBean();
	private boolean flag = true;
	private List<String> productNameList=null;
	private boolean valid = false;
	private String message;
	private List<String> damageList=null;
	public List<String> getDamageList() {
		return damageList;
	}

	public void setDamageList(List<String> damageList) {
		this.damageList = damageList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return the productNameList
	 */
	public List<String> getProductNameList() {
		return productNameList;
	}

	/**
	 * @param productNameList the productNameList to set
	 */
	public void setProductNameList(List<String> productNameList) {
		this.productNameList = productNameList;
	}

	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * @return the stockDataBean
	 */
	public StockDataBean getStockDataBean() {
		return stockDataBean;
	}

	/**
	 * @param stockDataBean
	 *            the stockDataBean to set
	 */
	public void setStockDataBean(StockDataBean stockDataBean) {
		this.stockDataBean = stockDataBean;
	}

	public String stockDamagePageLoad() {

		System.out.println("Inside Load the stockDamagePageLoad Page");
		setFlag(true);
		valid = false;
		stockDataBean.setStockCategory("");
		stockDataBean.setStockDamageQty("");
		stockDataBean.setStockProductName("");
		setMessage("");
		LiusenController controller = null;
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			productNameList=controller.getStockProductName();
			System.out.println(productNameList);
			damageList=controller.stockDamgeProducts();
			System.out.println("damage products -- > "+damageList);
			Collections.sort(productNameList);
			Collections.sort(damageList);
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		return "stockDamageLoad";

	}

	/**
	 * This method is used for show data tables depends on Product Name
	 * 
	 * @return
	 */
	public String searchProduct()
	{
		System.out.println("Inside the searchProduct Method Calling");
		LiusenController controller = null;
		stockDataBean.setStockDamageQty("");
		stockDataBean.setStockProductName("");
		try 
		{
			if (validate(true)) 
			{
				System.out.println("After Validate inside  searchProduct method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				controller.damageproductsView(stockDataBean);
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
		if (stockDataBean.getStockproduct().equalsIgnoreCase("DAM1000")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("stockDamCat")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Product Name."));
			}
			valid = false;
		}
		return valid;
	}

	public String addDamage() {

		System.out.println("Inside the addDamage Method Calling"+stockDataBean.getStockProductName());
		LiusenController controller = null;
		stockDataBean.setStockproduct("");
		String status="Fail";
		try {
			setFlag(true);
			System.out.println("product name - > "+stockDataBean.getStockProductName());
			if (validate1(true)) {
				System.out.println("After Validate inside  addDamage method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				status=controller.addDamage(stockDataBean);
				valid = true;
				setMessage("");
			} else {

			}
		}
		catch (LiusenException e) 
		{
			e.printStackTrace();
			setMessage(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();			
		}
		return "";
	}

	private boolean validate1(boolean flag2) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (stockDataBean.getStockProductName().equalsIgnoreCase("DAM00")) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("stockDamName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Product Name."));
			}
			valid = false;
		}
		if (StringUtils.isEmpty(stockDataBean.getStockDamageQty())) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("stockDamQty")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Quantity."));
			}
			valid = false;
		} else if (!StringUtils.isEmpty(stockDataBean.getStockDamageQty())) {
			if (!CommonValidate.isNumeric(stockDataBean.getStockDamageQty())) {
				if (flag2) {
					fieldName = CommonValidate.findComponentInRoot(
							"stockDamQty").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Enter the valid Damage Quantity."));
				}
				valid = false;
			}
		}
		return valid;
	}
	
	
}

