package com.nrg.liusen.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.StockDataBean;
import com.nrg.liusen.shared.Purchase;
import com.nrg.liusen.util.CommonValidate;

public class StockInMB {

	StockDataBean stockDataBean=new StockDataBean();

	private boolean flag=true;
	
	private boolean flag1=true;
	
	
	/**
	 * @return the flag1
	 */
	public boolean isFlag1() {
		return flag1;
	}

	/**
	 * @param flag1 the flag1 to set
	 */
	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	/**
	 * @return the orderNoList
	 */
	public List<String> getOrderNoList() {
		return orderNoList;
	}

	/**
	 * @param orderNoList the orderNoList to set
	 */
	public void setOrderNoList(List<String> orderNoList) {
		this.orderNoList = orderNoList;
	}
	private boolean spacerflag=true;
	
	private List<String> orderNoList=null;
	
	private List<Purchase> purchaseDetails = null;
	
	
	/**
	 * @return the spacerflag
	 */
	public boolean isSpacerflag() {
		return spacerflag;
	}

	/**
	 * @param spacerflag the spacerflag to set
	 */
	public void setSpacerflag(boolean spacerflag) {
		this.spacerflag = spacerflag;
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
	
	public String stockLoad() {

		System.out.println("Inside Load the stockLoad Page");
		stockDataBean.setStcokOrderNumber("");
		stockDataBean.setStcokOrderNumber1("");
		stockDataBean.setStockDelayReason("");
		stockDataBean.setStockDeliveryDate(null);
		stockDataBean.setStockEstDate(null);
		stockDataBean.setStockFirmName("");
		stockDataBean.setStockOrderDate(null);
		stockDataBean.setStockVendorPhoneNo("");		
		setFlag(true);
		setSpacerflag(false);
		setFlag1(true);
		LiusenController controller=null;		
		ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (LiusenController) ctx.getBean("controller");
		orderNoList=new ArrayList<String>();
		orderNoList=controller.getPurchaseOrderNo();
		return "stockPageLoad";

	}
	
	public String orderSubmit(){
		System.out.println("Inside Order Submit Method Calling");
		LiusenController controller=null;
		if(validate(true)){
			System.out.println("Inside Order Submit method Calling...");
			setFlag(false);
			setSpacerflag(true);
			if(stockDataBean.getStcokOrderNumber1() != null){
				ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				purchaseDetails=controller.getPurchaseOrderDetails(stockDataBean);
				System.out.println("Size "+purchaseDetails.size());
				if(purchaseDetails.size() > 0){
					stockDataBean.setStcokOrderNumber(purchaseDetails.get(0).getPurchaseOrderNumber());
					stockDataBean.setStockOrderDate(purchaseDetails.get(0).getPurchaseOrderDate());
					stockDataBean.setStockFirmName(purchaseDetails.get(0).getVendor().getFirmName());
					stockDataBean.setStockVendorPhoneNo(purchaseDetails.get(0).getVendor().getPhoneNumber());
					stockDataBean.setStockEstDate(purchaseDetails.get(0).getPurchaseEstimatedDate());
				}else{
					stockDataBean.setStcokOrderNumber("");
					stockDataBean.setStockOrderDate(null);
					stockDataBean.setStockFirmName("");
					stockDataBean.setStockVendorPhoneNo("");
					stockDataBean.setStockEstDate(null);
				}
			}
		}else{
			setFlag(true);
			setSpacerflag(false);
		}
		
		
		return "";
		
	}

	private boolean validate(boolean flag) {
		boolean valid=true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if(stockDataBean.getStcokOrderNumber1().equalsIgnoreCase("")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("stOrderNumber").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Order Number."));
			}
			valid=false;
		}
		return valid;
	}
	
	public String submit() {
		String status="Fail";
		
		LiusenController controller=null;
		ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (LiusenController) ctx.getBean("controller");
		
		try{
			System.out.println("Inside Submit Method Calling");
		if(validate1(true)){
			System.out.println("Inside Submit method Calling...");
			status=controller.insertStock(stockDataBean);
			/*if(status.equalsIgnoreCase("Inserted")){*/
				setFlag1(false);
			/*}*/
		}
		return "";
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	private boolean validate1(boolean flag2) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (stockDataBean.getStcokOrderNumber1().equalsIgnoreCase("STOCK00")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("stOrderNumber")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Order Number."));
			}
			valid = false;
		}

		if (StringUtils.isBlank(stockDataBean.getStcokOrderNumber())) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("stOrder")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Order Number."));
			}
			valid = false;
		}
		if (stockDataBean.getStockOrderDate() == null) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("stOrderDate")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Order Date."));
			}
			valid = false;
		}
		if (StringUtils.isBlank(stockDataBean.getStockFirmName())) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("stFirmName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Firm Name."));
			}
			valid = false;
		}
		if (StringUtils.isBlank(stockDataBean.getStockVendorPhoneNo())) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("stPhoneNo")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Phone Number."));
			}
			valid = false;
		}
		if (stockDataBean.getStockEstDate() == null) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("stEstDate")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Estimate Delivery Date."));
			}
			valid = false;
		}
		if (stockDataBean.getStockDeliveryDate() == null) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("stDelDate")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Delivered Date."));
			}
			valid = false;
		}

		return valid;
	}
	
	public String reset() {
		System.out.println("Inside Reset Method Calling..");
		stockDataBean.setStcokOrderNumber("");
		stockDataBean.setStcokOrderNumber1("");
		stockDataBean.setStockDelayReason("");
		stockDataBean.setStockDeliveryDate(null);
		stockDataBean.setStockEstDate(null);
		stockDataBean.setStockFirmName("");
		stockDataBean.setStockOrderDate(null);
		stockDataBean.setStockVendorPhoneNo("");
		setFlag(true);
		setSpacerflag(false);
		return "";
		
	}
	public String cancel() {
		System.out.println("Inside Cancel Method Calling...");
		return "cancellSucess";
		
		
	}
	/*@PostConstruct
	public void init(){
		System.out.println("Inside Constructor calling..");
		
		LiusenController controller=null;
		
		ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (LiusenController) ctx.getBean("controller");
		orderNoList=new ArrayList<String>();
		orderNoList=controller.getPurchaseOrderNo();
		System.out.println("orderNoList"+orderNoList.size()+orderNoList);
	}*/
}
