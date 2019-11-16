package com.nrg.liusen.domain;

import java.util.Date;
import java.util.List;

public class StockDataBean {

	private String stcokOrderNumber;
	private Date stockOrderDate;
	private String stockFirmName;
	private String stockVendorPhoneNo;
	private Date stockDeliveryDate;
	private Date stockEstDate;
	private String stockDelayReason;
	private String stcokOrderNumber1;
	private String stockProductName;
	private String stockDamageQty;
	private String stockCategory;
	private Date stockInDate;
	private String stockinQty;
	private String stockOutQty;
	private String stockUnitPrice;
	private String stockUnit;
	private List<String> products=null;	
	private List<StockDataBean> viewlist=null;
	private String limit;
	private String status;
	private String stockproduct;
	public String getStockproduct() {
		return stockproduct;
	}
	public void setStockproduct(String stockproduct) {
		this.stockproduct = stockproduct;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public List<StockDataBean> getViewlist() {
		return viewlist;
	}
	public void setViewlist(List<StockDataBean> viewlist) {
		this.viewlist = viewlist;
	}
	public List<String> getProducts() {
		return products;
	}
	public void setProducts(List<String> products) {
		this.products = products;
	}
	/**
	 * @return the stockUnit
	 */
	public String getStockUnit() {
		return stockUnit;
	}
	/**
	 * @param stockUnit the stockUnit to set
	 */
	public void setStockUnit(String stockUnit) {
		this.stockUnit = stockUnit;
	}
	/**
	 * @return the stockUnitPrice
	 */
	public String getStockUnitPrice() {
		return stockUnitPrice;
	}
	/**
	 * @param stockUnitPrice the stockUnitPrice to set
	 */
	public void setStockUnitPrice(String stockUnitPrice) {
		this.stockUnitPrice = stockUnitPrice;
	}
	/**
	 * @return the stockinQty
	 */
	public String getStockinQty() {
		return stockinQty;
	}
	/**
	 * @param stockinQty the stockinQty to set
	 */
	public void setStockinQty(String stockinQty) {
		this.stockinQty = stockinQty;
	}
	/**
	 * @return the stockOutQty
	 */
	public String getStockOutQty() {
		return stockOutQty;
	}
	/**
	 * @param stockOutQty the stockOutQty to set
	 */
	public void setStockOutQty(String stockOutQty) {
		this.stockOutQty = stockOutQty;
	}
	/**
	 * @return the stockInDate
	 */
	public Date getStockInDate() {
		return stockInDate;
	}
	/**
	 * @param stockInDate the stockInDate to set
	 */
	public void setStockInDate(Date stockInDate) {
		this.stockInDate = stockInDate;
	}
	/**
	 * @return the stockCategory
	 */
	public String getStockCategory() {
		return stockCategory;
	}
	/**
	 * @param stockCategory the stockCategory to set
	 */
	public void setStockCategory(String stockCategory) {
		this.stockCategory = stockCategory;
	}
	/**
	 * @return the stockDamageQty
	 */
	public String getStockDamageQty() {
		return stockDamageQty;
	}
	/**
	 * @param stockDamageQty the stockDamageQty to set
	 */
	public void setStockDamageQty(String stockDamageQty) {
		this.stockDamageQty = stockDamageQty;
	}
	/**
	 * @return the stockProductName
	 */
	public String getStockProductName() {
		return stockProductName;
	}
	/**
	 * @param stockProductName the stockProductName to set
	 */
	public void setStockProductName(String stockProductName) {
		this.stockProductName = stockProductName;
	}
	/**
	 * @return the stcokOrderNumber1
	 */
	public String getStcokOrderNumber1() {
		return stcokOrderNumber1;
	}
	/**
	 * @param stcokOrderNumber1 the stcokOrderNumber1 to set
	 */
	public void setStcokOrderNumber1(String stcokOrderNumber1) {
		this.stcokOrderNumber1 = stcokOrderNumber1;
	}
	/**
	 * @return the stcokOrderNumber
	 */
	public String getStcokOrderNumber() {
		return stcokOrderNumber;
	}
	/**
	 * @param stcokOrderNumber the stcokOrderNumber to set
	 */
	public void setStcokOrderNumber(String stcokOrderNumber) {
		this.stcokOrderNumber = stcokOrderNumber;
	}
	/**
	 * @return the stockOrderDate
	 */
	public Date getStockOrderDate() {
		return stockOrderDate;
	}
	/**
	 * @param stockOrderDate the stockOrderDate to set
	 */
	public void setStockOrderDate(Date stockOrderDate) {
		this.stockOrderDate = stockOrderDate;
	}
	/**
	 * @return the stockFirmName
	 */
	public String getStockFirmName() {
		return stockFirmName;
	}
	/**
	 * @param stockFirmName the stockFirmName to set
	 */
	public void setStockFirmName(String stockFirmName) {
		this.stockFirmName = stockFirmName;
	}
	/**
	 * @return the stockVendorPhoneNo
	 */
	public String getStockVendorPhoneNo() {
		return stockVendorPhoneNo;
	}
	/**
	 * @param stockVendorPhoneNo the stockVendorPhoneNo to set
	 */
	public void setStockVendorPhoneNo(String stockVendorPhoneNo) {
		this.stockVendorPhoneNo = stockVendorPhoneNo;
	}
	/**
	 * @return the stockDeliveryDate
	 */
	public Date getStockDeliveryDate() {
		return stockDeliveryDate;
	}
	/**
	 * @param stockDeliveryDate the stockDeliveryDate to set
	 */
	public void setStockDeliveryDate(Date stockDeliveryDate) {
		this.stockDeliveryDate = stockDeliveryDate;
	}
	/**
	 * @return the stockEstDate
	 */
	public Date getStockEstDate() {
		return stockEstDate;
	}
	/**
	 * @param stockEstDate the stockEstDate to set
	 */
	public void setStockEstDate(Date stockEstDate) {
		this.stockEstDate = stockEstDate;
	}
	/**
	 * @return the stockDelayReason
	 */
	public String getStockDelayReason() {
		return stockDelayReason;
	}
	/**
	 * @param stockDelayReason the stockDelayReason to set
	 */
	public void setStockDelayReason(String stockDelayReason) {
		this.stockDelayReason = stockDelayReason;
	}
	
	
}
