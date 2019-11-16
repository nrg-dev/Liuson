package com.nrg.liusen.managedBean;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;








import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.StockDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="")
@RequestScoped
public class StockInViewMB {

	StockDataBean stockDataBean=new StockDataBean();
	
	private boolean flag=true;
	
	private List<String> productNameList=null;
	
	private List<String> vendornameList=null;
	
	private List<StockDataBean> StockinViewList = null;
	
	
	
	
	/**
	 * @return the stockinViewList
	 */
	public List<StockDataBean> getStockinViewList() {
		return StockinViewList;
	}

	/**
	 * @param stockinViewList the stockinViewList to set
	 */
	public void setStockinViewList(List<StockDataBean> stockinViewList) {
		StockinViewList = stockinViewList;
	}

	/**
	 * @return the vendornameList
	 */
	public List<String> getVendornameList() {
		return vendornameList;
	}

	/**
	 * @param vendornameList the vendornameList to set
	 */
	public void setVendornameList(List<String> vendornameList) {
		this.vendornameList = vendornameList;
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
	 * @param flag the flag to set
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
	 * @param stockDataBean the stockDataBean to set
	 */
	public void setStockDataBean(StockDataBean stockDataBean) {
		this.stockDataBean = stockDataBean;
	}
	public String stockViewLoad() {

		System.out.println("Inside Load the Stock View Load Page");
		setFlag(true);
		stockDataBean.setStockProductName("");
		stockDataBean.setStockFirmName("");
		LiusenController controller = null;
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (LiusenController) ctx.getBean("controller");
		productNameList=controller.getStockProductName();
		System.out.println(productNameList);
		vendornameList=controller.getvendorName();
		System.out.println(vendornameList);
		Collections.sort(productNameList);
		Collections.sort(vendornameList);
		return "stockInViewLoad";

	}

	/**
	 * This method is used for show data tables depends on firm name
	 * 
	 * @return
	 */
	public String searchFirmName() {
		
		System.out.println("Inside the searchFirmName Method Calling");
		LiusenController controller = null;
		stockDataBean.setStockProductName("");
		try {
			if (validate(true)) {
				System.out.println("After Validate inside  searchFirmName method");
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				StockinViewList=controller.getStockInListByVendor(stockDataBean);
				
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
		if (stockDataBean.getStockFirmName().equalsIgnoreCase("")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("stockViewVName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Vendor Name."));
			}
			valid = false;
		} 
		
		return valid;
	}

	/**
	 * This method is used for show data tables depends on Product name
	 * 
	 * @return
	 */

	public String searchProduct() {
		System.out.println("Inside the searchProduct Method Calling");
		LiusenController controller = null;
		stockDataBean.setStockFirmName("");
		try{
			if (validate1(true)) {
				System.out.println("After Validate1 inside  searchProduct method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				StockinViewList=controller.getStockInList(stockDataBean);
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
		if (stockDataBean.getStockProductName().equalsIgnoreCase("")) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("stockViewPName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Product Name."));
			}
			valid = false;
		}
		return valid;
	}
	
}
