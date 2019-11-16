package com.nrg.liusen.dao;

import java.util.List;

import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.domain.StockOutManualDataBean;
import com.nrg.liusen.exception.LiusenException;

/**
 * This Java Class will communicate with Database
 * @author Udhaya
 * @date 30-11-2015
 * @copyright NRG      
 */
public interface SalesDao
{
	public void dropCustomer(SalesOrderDataBean salesOrderDataBean);
	
	public void categoryChange(SalesOrderDataBean salesOrderDataBean);
	
	public void productChange(SalesOrderDataBean salesOrderDataBean);
	
	public void quantityChange(SalesOrderDataBean salesOrderDataBean)throws LiusenException;
	
	public String salesInsertion(SalesOrderDataBean salesOrderDataBean);

	public String salesRecordInsert(SalesOrderDataBean salesOrderDataBean, int i);

	public void dateSearchSales(SalesOrderDataBean salesOrderDataBean);
	
	public void categorySearchSales(SalesOrderDataBean salesOrderDataBean);

	public void viewEachSales(SalesOrderDataBean salesOrderDataBean);
	
	public void editSalesOrder(SalesOrderDataBean salesOrderDataBean);
	
	public void updatequantitycheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException;

	public void updateSales(SalesOrderDataBean salesOrderDataBean);
	
	public void deleteSales(SalesOrderDataBean salesOrderDataBean)throws LiusenException;
	
	public int remSalesApprovalGM();

	public int remSalesApprovalMM();

	public void updateStock(SalesOrderDataBean salesOrderDataBean, int i);
	
	public String approveMM(SalesOrderDataBean salesOrderDataBean);
	
	public String approveGM(SalesOrderDataBean salesOrderDataBean);
	
	public String approvedSalesMM(SalesOrderDataBean salesOrderDataBean);
	
	public String approvedSalesGM(SalesOrderDataBean salesOrderDataBean);

	public int remSalesDeliveryGM();

	public int remSalesDeliveryPM();
	
	public String deliveryGM(SalesOrderDataBean salesOrderDataBean);

	public String deliveryPM(SalesOrderDataBean salesOrderDataBean);
	
	public String deliverdSalesPM(SalesOrderDataBean salesOrderDataBean);

	public String deliverdSalesGM(SalesOrderDataBean salesOrderDataBean);

	public void deleteCheck(SalesOrderDataBean salesOrderDataBean);
	
	public List<String> getinvoiceSales(SalesOrderDataBean salesOrderDataBean);
	
	public String invoiceViewSales(SalesOrderDataBean salesOrderDataBean);

	public String invoiceViewForm(SalesOrderDataBean salesOrderDataBean);

	public String invoiceinsert(SalesOrderDataBean salesOrderDataBean);
	
	public List<String> getpaymentSales(SalesOrderDataBean salesOrderDataBean);
	
	public String paymentViewSales(SalesOrderDataBean salesOrderDataBean);
	
	public String payForm(SalesOrderDataBean salesOrderDataBean);

	public String paymentinSales(SalesOrderDataBean salesOrderDataBean);
	
	public String paysales(SalesOrderDataBean salesOrderDataBean);
	
	public int remSalesPaymnetdirector();

	public int remSalesPaymentFM();
	
	public String paymentFM(SalesOrderDataBean salesOrderDataBean);
	
	public String paymentDirector(SalesOrderDataBean salesOrderDataBean);
	
	public String paymentApproveFM(SalesOrderDataBean salesOrderDataBean);

	public String paymentApproveDirector(SalesOrderDataBean salesOrderDataBean);
	
	public List<String> paymentCategory(SalesOrderDataBean salesOrderDataBean);
	
	public String paymentStatusView1(SalesOrderDataBean salesOrderDataBean);
	
	public String salesPaymentView(SalesOrderDataBean salesOrderDataBean);
	
	public String salesReport(SalesOrderDataBean salesOrderDataBean);
	
	public String salesReturn(SalesOrderDataBean salesOrderDataBean);
	
	public List<String> returnSales(SalesOrderDataBean salesOrderDataBean);
	
	public String returnSubmit(SalesOrderDataBean salesOrderDataBean);
	
	public String salesReturnSearch(SalesOrderDataBean salesOrderDataBean);

	public String returnSubmit1(SalesOrderDataBean salesOrderDataBean, int j);
	
	public String quantityCheckReturn(SalesOrderDataBean salesOrderDataBean) throws LiusenException;
	
	public String returnCheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException;

	public String salesUpdateReturn(SalesOrderDataBean salesOrderDataBean);
	
	public String viewEachSalesRecord(SalesOrderDataBean salesOrderDataBean);

	public String stockUpdateReturnNr(SalesOrderDataBean salesOrderDataBean, int j);

	public String stockUpdateReturnDr(SalesOrderDataBean salesOrderDataBean, int j);

	public String paymentCheckReturn(SalesOrderDataBean salesOrderDataBean);
	
	public String salesReturnView(SalesOrderDataBean salesOrderDataBean);
	
	public String returnSalesView(SalesOrderDataBean salesOrderDataBean);

	public List<String> getProjectNames();
}