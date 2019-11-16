package com.nrg.liusen.dao;

import java.util.List;

import com.nrg.liusen.domain.StockDataBean;
import com.nrg.liusen.domain.StockOutManualDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.shared.Purchase;

public interface StockDao {

	List<String> getPurchaseOrderNo();

	List<Purchase> getPurchaseOrderDetails(StockDataBean stockDataBean);

	String insertStock(StockDataBean stockDataBean);

	List<String> getStockProductName();

	List<String> getvendorName();

	List<StockDataBean> getStockInList(StockDataBean stockDataBean);

	List<StockDataBean> getStockInListByVendor(StockDataBean stockDataBean);

	String addDamage(StockDataBean stockDataBean) throws LiusenException;

	List<String> getSalesOrderList(StockOutManualDataBean stockOutManualDataBean);

	List<String> getProjectDetails(String orderNo, StockOutManualDataBean stockOutManualDataBean);

	/*udhaya 19/12*/
	public String getProjectLists(StockOutManualDataBean stockOutManualDataBean);

	public String customerList(StockOutManualDataBean stockOutManualDataBean);

	public String employeeList(StockOutManualDataBean stockOutManualDataBean);
	
	public String getproductLimit(StockOutManualDataBean stockOutManualDataBean);
	
	public String quantityCheckStockOut(StockOutManualDataBean stockOutManualDataBean);
	
	public String stockoutManual(StockOutManualDataBean stockOutManualDataBean);

	public String stockoutRecord(StockOutManualDataBean stockOutManualDataBean, int i);

	public String stockOutupdate(StockOutManualDataBean stockOutManualDataBean);

	public String stockoutBarcode(StockOutManualDataBean stockOutManualDataBean, int i);

	public String stockOutupdate1(StockOutManualDataBean stockOutManualDataBean);
	
	public int remStockOutManual();
	
	public String stockOutWaitingList(StockOutManualDataBean stockOutManualDataBean);

	public String stockoutapproval(StockOutManualDataBean stockOutManualDataBean);
	
	public String stockOutManualView(StockOutManualDataBean stockOutManualDataBean);
	
	public String dateSearchView(StockOutManualDataBean stockOutManualDataBean);
	
	public String viewOutRecords(StockOutManualDataBean stockOutManualDataBean);
	
	public String categoryOut(StockDataBean stockDataBean);
	
	public String stockoutview(StockDataBean stockDataBean);
	
	public String categorySearchView(StockOutManualDataBean stockOutManualDataBean);
	
	public List<String> stockDamgeProducts();
	
	public String damageproductsView(StockDataBean stockDataBean);
	
	public String projectNames(StockOutManualDataBean stockOutManualDataBean);
	
	public int remStockOutManualPM();
	
	public String stockOutWaitingListPM(StockOutManualDataBean stockOutManualDataBean);
	
	public String stockoutapprovalPM(StockOutManualDataBean stockOutManualDataBean);
}
