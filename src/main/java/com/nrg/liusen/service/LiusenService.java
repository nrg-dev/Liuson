package com.nrg.liusen.service;

import java.io.IOException;
import java.util.List;

import com.nrg.liusen.domain.CustomerDataBean;
import com.nrg.liusen.domain.EmployeeDataBean;
import com.nrg.liusen.domain.LimitDataBean;
import com.nrg.liusen.domain.LoginAccess;
import com.nrg.liusen.domain.ProductDataBean;
import com.nrg.liusen.domain.ProfitLossDataBean;
import com.nrg.liusen.domain.ProjectDataBean;
import com.nrg.liusen.domain.PurchaseDataBean;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.domain.StockDataBean;
import com.nrg.liusen.domain.StockOutManualDataBean;
import com.nrg.liusen.domain.TransactionDataBean;
import com.nrg.liusen.domain.VendorDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.shared.Customer;
import com.nrg.liusen.shared.Employee;
import com.nrg.liusen.shared.ImagePath;
import com.nrg.liusen.shared.Payroll;
import com.nrg.liusen.shared.Product;
import com.nrg.liusen.shared.ProductLimit;
import com.nrg.liusen.shared.Projet;
import com.nrg.liusen.shared.Purchase;
import com.nrg.liusen.shared.RawMaterial;
import com.nrg.liusen.shared.Transaction;
import com.nrg.liusen.shared.Vendor;
import com.steadystate.css.parser.ParseException;

/**
 * This Java Class will communicate with mentariBo.java
 * @author Robert Arjun 
 * @date 13-11-2015
 * @copyright NRG
 * 
 *       
 */

public interface LiusenService {
	
	public String loginService(LoginAccess loginaccess) throws LiusenException;

	public String insertVendor(VendorDataBean vendorDataBean);

	public List<String> getCountryList();

	public List<VendorDataBean> searchByVendorName(VendorDataBean vendorDataBean);

	public List<Vendor> getVendorInfo(String venFirmName);

	public String editVendor(VendorDataBean vendorDataBean);

	public String deleteVendor(VendorDataBean vendorDataBean);

	public List<VendorDataBean> searchByCityName(VendorDataBean vendorDataBean);

	public List<String> getVendorList();

	public String insertProduct(ProductDataBean productDataBean);

	public List<ProductDataBean> getProductList(ProductDataBean productDataBean);

	public List<Product> getProductListSingle(String prodProductName);

	public List<Product> getProductInfo(ProductDataBean productDataBean);

	public String editProduct(ProductDataBean productDataBean);

	public String productDelete(ProductDataBean productDataBean);

	public List<ProductDataBean> getProductListByCategory(ProductDataBean productDataBean);

	public List<String> productNameList(String category);

	public String insertLimit(LimitDataBean limitDataBean);

	public List<LimitDataBean> getLimit(String limitProductName1);

	public List<String> getQualification();

	public List<String> getDesignation();

	public String inserEmployee(EmployeeDataBean employeeDataBean);

	public List<String> getEmployeeID();

	public List<RawMaterial> getProductListSingle1(String prodProductName);

	public List<RawMaterial> getRawCategoryEdit(ProductDataBean productDataBean);

	public List<String> getProjectServiceList(String category);

	public List<ProductLimit> getLimitListByProject(LimitDataBean limitDataBean);

	public List<ProductLimit> limitListEditByProject(LimitDataBean limitDataBean);

	public String EditLimit(LimitDataBean limitDataBean);

	public String limitDelete(LimitDataBean limitDataBean);

	public List<LimitDataBean> getLimitByCategory(String limProductCategory1);

	public List<String> getPurchaseOrderNo();

	public List<Purchase> getPurchaseOrderDetails(StockDataBean stockDataBean);

	public String insertStock(StockDataBean stockDataBean);

	public List<String> getStockProductName();

	public List<String> getvendorName();

	public List<StockDataBean> getStockInList(StockDataBean stockDataBean);

	public List<StockDataBean> getStockInListByVendor(
			StockDataBean stockDataBean);
	public List<String> getstaffid();

	public String insertCustomer(CustomerDataBean customerDataBean);

	public List<CustomerDataBean> getCustList(CustomerDataBean customerDataBean);

	public String customerDelete(CustomerDataBean customerDataBean);

	public List<Customer> getCustomerInfo(String custCustomerName);

	public String editCustomer(CustomerDataBean customerDataBean);

	public List<CustomerDataBean> searchByCustomerName(
			CustomerDataBean customerDataBean);

	public List<CustomerDataBean> searchByCityName(
			CustomerDataBean customerDataBean);
	
	public List<Employee> getemp(EmployeeDataBean employeeDataBean);

	public List<EmployeeDataBean> searchname(EmployeeDataBean employeeDataBean);

	public List<Employee> getempedit(String empEmployeeName,EmployeeDataBean employeeDataBean);

	public String getupdate(EmployeeDataBean employeeDataBean);

	public String deleteemployee(EmployeeDataBean employeeDataBean);

	public List<String> empid();

	public List<EmployeeDataBean> searchid(EmployeeDataBean employeeDataBean);

	public String addDamage(StockDataBean stockDataBean) throws LiusenException;


	public List<String> vendorNameList(PurchaseOrederDataBean o);
	public List<String> productNameList(PurchaseOrederDataBean o);
	public String productPrice(PurchaseOrederDataBean p);
	public String purchaseInsertion1(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseInsertion2(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseView(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseDelete(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseEditConfirm(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseOrderDetailedView(PurchaseOrederDataBean p) throws LiusenException;	
	public String purchaseEditDeleteCheck(PurchaseOrederDataBean p) throws LiusenException;
	public String poDetailsForApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String poDetailsForDeliveryApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String poGMApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String poMMApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String poDelGMApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String poDelPMApproval(PurchaseOrederDataBean p) throws LiusenException;
	
	/*udhaya*/
	public void dropCustomer(SalesOrderDataBean salesOrderDataBean);

	public void categoryChange(SalesOrderDataBean salesOrderDataBean);

	public void productChange(SalesOrderDataBean salesOrderDataBean);

	public void quantityChange(SalesOrderDataBean salesOrderDataBean)throws LiusenException;

	public String salesInsertion(SalesOrderDataBean salesOrderDataBean);

	public void dateSearchSales(SalesOrderDataBean salesOrderDataBean);

	public void categorySearchSales(SalesOrderDataBean salesOrderDataBean);

	public void viewEachSales(SalesOrderDataBean salesOrderDataBean);

	public void editSalesOrder(SalesOrderDataBean salesOrderDataBean);

	public void updatequantitycheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException;

	public void updateSales(SalesOrderDataBean salesOrderDataBean);

	public void deleteSales(SalesOrderDataBean salesOrderDataBean)throws LiusenException;
	
	public int remSalesApprovalGM();

	public int remSalesApprovalMM();
	
	public String approveMM(SalesOrderDataBean salesOrderDataBean);

	public String approveGM(SalesOrderDataBean salesOrderDataBean);

	public String approvedSalesMM(SalesOrderDataBean salesOrderDataBean);

	public String approvedSalesGM(SalesOrderDataBean salesOrderDataBean);

	public List<String> getSalesOrderList(
			StockOutManualDataBean stockOutManualDataBean);

	public List<String> getProjectDetails(String orderNo, StockOutManualDataBean stockOutManualDataBean);

	public List<String> getProjectList();

	public List<RawMaterial> getSubCategoty(String category);
	
	public int remSalesDeliveryGM();

	public int remSalesDeliveryPM();

	public String deliveryGM(SalesOrderDataBean salesOrderDataBean);

	public String deliveryPM(SalesOrderDataBean salesOrderDataBean);

	public String deliverdSalesPM(SalesOrderDataBean salesOrderDataBean);

	public String deliverdSalesGM(SalesOrderDataBean salesOrderDataBean);

	public void deleteCheck(SalesOrderDataBean salesOrderDataBean);
	
	/*udhaya 19/12*/
	public String getProjectLists(StockOutManualDataBean stockOutManualDataBean);

	public String customerList(StockOutManualDataBean stockOutManualDataBean);

	public String employeeList(StockOutManualDataBean stockOutManualDataBean);
	
	public String getproductLimit(StockOutManualDataBean stockOutManualDataBean);
	
	public String quantityCheckStockOut(StockOutManualDataBean stockOutManualDataBean);
	
	public String stockoutManual(StockOutManualDataBean stockOutManualDataBean);
	
	public int remStockOutManual();
	
	public String stockOutWaitingList(StockOutManualDataBean stockOutManualDataBean);
	
	public String stockoutapproval(StockOutManualDataBean stockOutManualDataBean);
	
	public String stockOutManualView(StockOutManualDataBean stockOutManualDataBean);
	
	public String dateSearchView(StockOutManualDataBean stockOutManualDataBean);
	
	public String viewOutRecords(StockOutManualDataBean stockOutManualDataBean);
	
	public String inserttrans(TransactionDataBean transactionDataBean);
	
	public String traview(com.nrg.liusen.domain.TransactionDataBean transactionDataBean);

	public List<Transaction> trans(String transno);

	public List<Transaction> traedit(TransactionDataBean transactionDataBean);

	public String update(TransactionDataBean transactionDataBean);

	public String delettrans(TransactionDataBean transactionDataBean);
	
	public String categoryOut(StockDataBean stockDataBean);
	
	public String stockoutview(StockDataBean stockDataBean);
	
	public String categorySearchView(StockOutManualDataBean stockOutManualDataBean);
	
	public String insertpayroll(EmployeeDataBean employeeDataBean);

	public String payroll(EmployeeDataBean employeeDataBean)throws LiusenException;

	public String payroll1(EmployeeDataBean employeeDataBean);

	public List<String> getEmployeeName();

	public List<com.nrg.liusen.domain.EmployeeDataBean> getPayrollList(com.nrg.liusen.domain.EmployeeDataBean employeeDataBean);

	public List<com.nrg.liusen.domain.EmployeeDataBean> getPayrollList1(com.nrg.liusen.domain.EmployeeDataBean employeeDataBean);

	public List<Payroll> getPayrollListSingle(EmployeeDataBean employeeDataBean);

	public String payrollDelete(EmployeeDataBean employeeDataBean);

	public List<Payroll> getPayrollInfo(EmployeeDataBean employeeDataBean);

	public String editProduct(com.nrg.liusen.domain.EmployeeDataBean employeeDataBean);
	
	public List<String> getinvoicePurchase();
	
	public String invoiceView(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String invoiceViewForm(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public List<String> getinvoiceSales(SalesOrderDataBean salesOrderDataBean);
	
	public String invoiceViewSales(SalesOrderDataBean salesOrderDataBean);
	
	public String invoiceViewForm(SalesOrderDataBean salesOrderDataBean);
	
	public List<String> getpaymentSales(SalesOrderDataBean salesOrderDataBean);
	
	public String paymentViewSales(SalesOrderDataBean salesOrderDataBean);
	
	public String payForm(SalesOrderDataBean salesOrderDataBean);
	
	public String paysales(SalesOrderDataBean salesOrderDataBean);
	
	public String designRegSubmit(ProductDataBean productDataBean) throws LiusenException, ParseException;
	
	public int remSalesPaymnetdirector();

	public int remSalesPaymentFM();
	
	public String paymentFM(SalesOrderDataBean salesOrderDataBean);
	
	public String paymentDirector(SalesOrderDataBean salesOrderDataBean);
	
	public String paymentApproveFM(SalesOrderDataBean salesOrderDataBean);

	public String paymentApproveDirector(SalesOrderDataBean salesOrderDataBean);
	
	public List<String> purchasepayorders();
	
	public String paymentViewpurchase(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String payPurchase(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String payPurchaseAmount(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String paymentFM(PurchaseOrederDataBean purchaseOrederDataBean);

	public String paymentDirector(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String paymentApproveFM(PurchaseOrederDataBean purchaseOrederDataBean);

	public String paymentApproveDirector(PurchaseOrederDataBean purchaseOrederDataBean);

	public String paymentStatusView(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String purcPaymentView(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public List<ProfitLossDataBean> searchProfitLoss(ProfitLossDataBean profitLossDataBean);
	
	public String purchaseOrderClose(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public List<String> paymentCategory(SalesOrderDataBean salesOrderDataBean);
	
	public String paymentStatusView1(SalesOrderDataBean salesOrderDataBean);
	
	public String salesPaymentView(SalesOrderDataBean salesOrderDataBean);
	
	public List<String> stockDamgeProducts();
	
	public String damageproductsView(StockDataBean stockDataBean);
	
	public String dropprojectValues(ProjectDataBean projectDataBean);
	
	public String insertProjectValues(ProjectDataBean projectDataBean)throws LiusenException, IOException;
	
	public String projectImage(ProjectDataBean projectDataBean);
	
	public String findcashbook(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String searchdate(PurchaseDataBean purchaseDataBean);
	
	public String designDelete(ProductDataBean productDataBean);

	public List<ImagePath> getDesignListView(ProductDataBean productDataBean);

	public List<String> getDesignProjectList();

	public String designEditSubmit(ProductDataBean productDataBean);

	public List<String> getDesignProList();

	public List<ProductDataBean> getDesignList(ProductDataBean productDataBean);
	
	public String salesReport(SalesOrderDataBean salesOrderDataBean);
	
	public String payrollremainder(EmployeeDataBean employeeDataBean);
	
	public String viewAccountPayable(PurchaseOrederDataBean purchaseOrder) throws LiusenException;
	
	public String purchasedate(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String salesReturn(SalesOrderDataBean salesOrderDataBean);
	
	public List<String> returnSales(SalesOrderDataBean salesOrderDataBean);
	
	public String returnSubmit(SalesOrderDataBean salesOrderDataBean);
	
	public String salesReturnSearch(SalesOrderDataBean salesOrderDataBean);
	
	public List<String> dropProject(ProjectDataBean projectDataBean);

	public List<ProjectDataBean> getProjectView(ProjectDataBean projectDataBean);	

	public List<Projet> getProjectListView(ProjectDataBean projectDataBean,List<ProjectDataBean> projectViewList);

	public String updateProject(ProjectDataBean projectDataBean);

	public String deleteProject(ProjectDataBean projectDataBean);

	public List<ProjectDataBean> getProjectViewbyClient(ProjectDataBean projectDataBean);
	
	public String searchpayroll(EmployeeDataBean employeeDataBean);
	
	public String quantityCheckReturn(SalesOrderDataBean salesOrderDataBean) throws LiusenException;
	
	public String returnCheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException;
	
	public String viewEachSalesRecord(SalesOrderDataBean salesOrderDataBean);
	
	public String salesReturnView(SalesOrderDataBean salesOrderDataBean);
	
	public String returnSalesView(SalesOrderDataBean salesOrderDataBean);
	
	public String purchaseReturn(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String purchaseReturnsearch(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String returnCheckpur(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException;
	
	public String quantityCheckReturn(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException;
	
	public String returnSubmitPurcahse(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String openingStock(ProductDataBean productDataBean)throws LiusenException;
	
	public String purchaseReturnView(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String purchaseReturnViewRecord(PurchaseOrederDataBean purchaseOrederDataBean);

	public List<String> getProjectNames();

	public String projectNames(StockOutManualDataBean stockOutManualDataBean);

	public int remStockOutManualPM();
	
	public String stockOutWaitingListPM(StockOutManualDataBean stockOutManualDataBean);
	
	public String stockoutapprovalPM(StockOutManualDataBean stockOutManualDataBean);
}