package com.nrg.liusen.controler;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
import com.nrg.liusen.service.LiusenService;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("controller")
public   class LiusenControllerImpl implements LiusenController{
	
	
	final Logger logger = LoggerFactory.getLogger(LiusenControllerImpl.class);
	
	@Autowired
	LiusenService service;

	
	public LiusenService getService() {
		return service;
	}


	public void setService(LiusenService service) {
		this.service = service;
	}


	@Override
	public String userLogin(LoginAccess loginaccess)  throws LiusenException
	{
	
		System.out.println("inside controller");
		System.out.println(loginaccess.getUsername());
		System.out.println(" controller");
		service.loginService(loginaccess);
		System.out.println("outside controller");
		return "";
	}


	@Override
	public String insertVendor(VendorDataBean vendorDataBean) {
		
		System.out.println("Calling insertVendor method in Controller");
		System.out.println("Firm Name"+vendorDataBean.getVenFirmName());
		
		return service.insertVendor(vendorDataBean);
	
	}


	@Override
	public List<String> getCountryList() {
		
		return service.getCountryList();
	}


	@Override
	public List<VendorDataBean> searchByVendorName(VendorDataBean vendorDataBean) {
		
		return service.searchByVendorName(vendorDataBean);
	}


	@Override
	public List<Vendor> getVendorInfo(String venFirmName) {
		
		return service.getVendorInfo(venFirmName);
	}


	@Override
	public String editVendor(VendorDataBean vendorDataBean) {
		String status=service.editVendor(vendorDataBean);
		return status;
	}


	@Override
	public String deleteVendor(VendorDataBean vendorDataBean) {
		
		return service.deleteVendor(vendorDataBean);
	}


	@Override
	public List<VendorDataBean> searchByCityName(VendorDataBean vendorDataBean) {
		
		return service.searchByCityName(vendorDataBean);
	}


	@Override
	public List<String> getVendorList() {
		
		return service.getVendorList();
	}


	@Override
	public String insertProduct(ProductDataBean productDataBean) {
		
		return service.insertProduct(productDataBean);
	}


	@Override
	public List<ProductDataBean> getProductList(ProductDataBean productDataBean) {
		
		return service.getProductList(productDataBean);
	}


	@Override
	public List<Product> getProductListSingle(String prodProductName) {
		
		return service.getProductListSingle(prodProductName);
	}


	@Override
	public List<Product> getProductInfo(ProductDataBean productDataBean) {
		
		return service.getProductInfo(productDataBean);
	}
	
	@Override
	public String editProduct(ProductDataBean productDataBean) {
		
		return service.editProduct(productDataBean);
	}


	@Override
	public String productDelete(ProductDataBean productDataBean) {
		
		return service.productDelete(productDataBean);
	}


	@Override
	public List<ProductDataBean> getProductListByCategory(ProductDataBean productDataBean) {
		
		return service.getProductListByCategory(productDataBean);
	}


	@Override
	public List<String> productNameList(String category) {
		
		return service.productNameList(category);
	}


	@Override
	public String insertLimit(LimitDataBean limitDataBean) {
		
		return service.insertLimit(limitDataBean);
	}


	@Override
	public List<LimitDataBean> getLimit(String limitProductName1) {
	
		return service.getLimit(limitProductName1);
	}


	@Override
	public List<String> getQualification() {
		
		return service.getQualification();
	}


	@Override
	public List<String> getDesignation() {
	
		return service.getDesignation();
	}


	@Override
	public String inserEmployee(EmployeeDataBean employeeDataBean) {
		
		return service.inserEmployee(employeeDataBean);
	}


	@Override
	public List<String> getEmployeeID() {
		
		return service.getEmployeeID();
	}


	@Override
	public List<RawMaterial> getProductListSingle1(String prodProductName) {
		
		return service.getProductListSingle1(prodProductName);
	}


	@Override
	public List<RawMaterial> getRawCategoryEdit(ProductDataBean productDataBean) {
		
		return service.getRawCategoryEdit(productDataBean);
	}


	@Override
	public List<String> getProjectServiceList(String category) {
		
		return service.getProjectServiceList(category);
	}


	@Override
	public List<ProductLimit> getLimitListByProject(LimitDataBean limitDataBean) {
	
		return service.getLimitListByProject(limitDataBean);
	}


	@Override
	public List<ProductLimit> limitListEditByProject(LimitDataBean limitDataBean) {
		return service.limitListEditByProject(limitDataBean);
	}


	@Override
	public String EditLimit(LimitDataBean limitDataBean) {
		
		return service.EditLimit(limitDataBean);
	}


	@Override
	public String limitDelete(LimitDataBean limitDataBean) {
		
		return service.limitDelete(limitDataBean);
	}


	@Override
	public List<LimitDataBean> getLimitByCategory(String limProductCategory1) {
		return service.getLimitByCategory(limProductCategory1);
	}


	@Override
	public List<String> getPurchaseOrderNo() {
		
		return service.getPurchaseOrderNo();
	}


	@Override
	public List<Purchase> getPurchaseOrderDetails(StockDataBean stockDataBean) {

		return service.getPurchaseOrderDetails(stockDataBean);
	}


	@Override
	public String insertStock(StockDataBean stockDataBean) {
		
		return service.insertStock(stockDataBean);
	}


	@Override
	public List<String> getStockProductName() {
		
		return service.getStockProductName();
	}


	@Override
	public List<String> getvendorName() {
		
		return service.getvendorName();
	}


	@Override
	public List<StockDataBean> getStockInList(StockDataBean stockDataBean) {
		
		return service.getStockInList(stockDataBean);
	}


	@Override
	public List<StockDataBean> getStockInListByVendor(
			StockDataBean stockDataBean) {
		
		return service.getStockInListByVendor(stockDataBean);
	}


	@Override
	public List<String> getstaffid() {
		
		return service.getstaffid();
	}


	@Override
	public String insertCustomer(CustomerDataBean customerDataBean) {
	
		return service.insertCustomer(customerDataBean);
	}


	@Override
	public List<CustomerDataBean> getCustList(CustomerDataBean customerDataBean) {
		
		return service.getCustList(customerDataBean);
	}


	@Override
	public String customerDelete(CustomerDataBean customerDataBean) {
		
		return service.customerDelete(customerDataBean);
	}


	@Override
	public List<Customer> getCustomerInfo(String custCustomerName) {
		
		return service.getCustomerInfo(custCustomerName);
	}


	@Override
	public String editCustomer(CustomerDataBean customerDataBean) {
		
		return service.editCustomer(customerDataBean);
	}


	@Override
	public List<CustomerDataBean> searchByCustomerName(
			CustomerDataBean customerDataBean) {
		
		return service.searchByCustomerName(customerDataBean);
	}


	@Override
	public List<CustomerDataBean> searchByCityName(
			CustomerDataBean customerDataBean) {
		
		return service.searchByCityName(customerDataBean);
	}

	@Override
	public List<Employee> getemp(EmployeeDataBean employeeDataBean) {
		
		return service.getemp(employeeDataBean);
	}


	@Override
	public List<EmployeeDataBean> searchname(EmployeeDataBean employeeDataBean) {
		
		return service.searchname(employeeDataBean);
	}


	@Override
	public List<Employee> getempedit(String empEmployeeName, EmployeeDataBean employeeDataBean) {
		
		return service.getempedit(empEmployeeName,employeeDataBean);
	}


	@Override
	public String getupdate(EmployeeDataBean employeeDataBean) {
		
		String status=service.getupdate(employeeDataBean);
		return status;
	}


	@Override
	public String deleteemployee(EmployeeDataBean employeeDataBean) {
		
		return service.deleteemployee(employeeDataBean);
	}


	@Override
	public List<String> empid() {
		
		return service.empid();
	}


	@Override
	public List<EmployeeDataBean> searchid(EmployeeDataBean employeeDataBean) {
	
		return service.searchid(employeeDataBean);
	}


	@Override
	public String addDamage(StockDataBean stockDataBean) throws LiusenException {
		
		return service.addDamage(stockDataBean);
	}

	
	public List<String> productNameList(PurchaseOrederDataBean o)
	{
		return service.productNameList(o);
	}
	public List<String> vendorNameList(PurchaseOrederDataBean o)
	{
		return service.vendorNameList(o);
	}
	@Override
	public String productPrice(PurchaseOrederDataBean p)
	{
		return service.productPrice(p);
	}
	@Override
	public String purchaseInsertion1(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.purchaseInsertion1(p);
	}
	@Override
	public String purchaseInsertion2(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.purchaseInsertion2(p);
	}
	@Override
	public String purchaseView(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.purchaseView(p);
	}
	@Override
	public String purchaseDelete(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.purchaseDelete(p);
	}
	@Override
	public String purchaseEditConfirm(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.purchaseEditConfirm(p);
	}
	@Override
	public String purchaseApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.purchaseApproval(p);
	}
	@Override
	public String purchaseOrderDetailedView(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.purchaseOrderDetailedView(p);
	}
	@Override
	public String purchaseEditDeleteCheck(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.purchaseEditDeleteCheck(p);
	}
	@Override
	public String poDetailsForDeliveryApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.poDetailsForDeliveryApproval(p);
	}
	@Override
	public String poDetailsForApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.poDetailsForApproval(p);
	}
	@Override
	public String poGMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.poGMApproval(p);
	}
	@Override
	public String poMMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.poMMApproval(p);
	}
	@Override
	public String poDelGMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.poDelGMApproval(p);
	}
	@Override
	public String poDelPMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return service.poDelPMApproval(p);
	}	

	
	/*udhaya*/
public void dropCustomer(SalesOrderDataBean salesOrderDataBean)
{
	service.dropCustomer(salesOrderDataBean);
}

public void categoryChange(SalesOrderDataBean salesOrderDataBean)
{
	service.categoryChange(salesOrderDataBean);
}

public void productChange(SalesOrderDataBean salesOrderDataBean)
{
	service.productChange(salesOrderDataBean);
}

public void quantityChange(SalesOrderDataBean salesOrderDataBean)throws LiusenException
{
	service.quantityChange(salesOrderDataBean);
}

public String salesInsertion(SalesOrderDataBean salesOrderDataBean)
{
	return service.salesInsertion(salesOrderDataBean);
}

public void dateSearchSales(SalesOrderDataBean salesOrderDataBean)
{
	service.dateSearchSales(salesOrderDataBean);
}

public void categorySearchSales(SalesOrderDataBean salesOrderDataBean)
{
	service.categorySearchSales(salesOrderDataBean);
}

public void viewEachSales(SalesOrderDataBean salesOrderDataBean)
{
	service.viewEachSales(salesOrderDataBean);
}

public void editSalesOrder(SalesOrderDataBean salesOrderDataBean)
{
	service.editSalesOrder(salesOrderDataBean);
}

public void updatequantitycheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException
{
	service.updatequantitycheck(salesOrderDataBean);
}

public void updateSales(SalesOrderDataBean salesOrderDataBean)
{
	service.updateSales(salesOrderDataBean);
}

public void deleteSales(SalesOrderDataBean salesOrderDataBean)throws LiusenException
{
	service.deleteSales(salesOrderDataBean);
}

public int remSalesApprovalGM()
{
	return service.remSalesApprovalGM();
}

public int remSalesApprovalMM()
{
	return service.remSalesApprovalMM();
}
	
	@Override
	public String approveMM(SalesOrderDataBean salesOrderDataBean)
	{
	return service.approveMM(salesOrderDataBean);
	}	
	
	@Override
	public String approveGM(SalesOrderDataBean salesOrderDataBean)
	{
	return service.approveGM(salesOrderDataBean);
	}
	
	@Override
	public String approvedSalesMM(SalesOrderDataBean salesOrderDataBean)
	{
	return service.approvedSalesMM(salesOrderDataBean);
	}
	
	@Override
	public String approvedSalesGM(SalesOrderDataBean salesOrderDataBean)
	{
	return service.approvedSalesGM(salesOrderDataBean);
	}


	@Override
	public List<String> getSalesOrderList(
			StockOutManualDataBean stockOutManualDataBean) {
		
		return service.getSalesOrderList(stockOutManualDataBean);
	}


	@Override
	public List<String> getProjectDetails(String orderNo,StockOutManualDataBean stockOutManualDataBean) {
		
		return service.getProjectDetails(orderNo,stockOutManualDataBean);
	}


	@Override
	public List<String> getProjectList() {
		
		return service.getProjectList();
	}


	@Override
	public List<RawMaterial> getSubCategoty(String category) {
		
		return service.getSubCategoty(category);
	}

	public int remSalesDeliveryGM() 
	{
		return service.remSalesDeliveryGM();
	}
	
	public int remSalesDeliveryPM()
	{
		return service.remSalesDeliveryPM();
	}

	public String deliveryGM(SalesOrderDataBean salesOrderDataBean)
	{
		return service.deliveryGM(salesOrderDataBean);
	}

	public String deliveryPM(SalesOrderDataBean salesOrderDataBean)
	{
		return service.deliveryPM(salesOrderDataBean);
	}

	public String deliverdSalesGM(SalesOrderDataBean salesOrderDataBean)
	{
		return service.deliverdSalesGM(salesOrderDataBean);
	}
	
	public String deliverdSalesPM(SalesOrderDataBean salesOrderDataBean)
	{
		return service.deliverdSalesPM(salesOrderDataBean);
	}
	
	public void deleteCheck(SalesOrderDataBean salesOrderDataBean)
	{
		service.deleteCheck(salesOrderDataBean);
	}

	/*udhaya 19/12*/
	public String getProjectLists(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.getProjectLists(stockOutManualDataBean);
	}

	public String customerList(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.customerList(stockOutManualDataBean);
	}
	
	public String employeeList(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.employeeList(stockOutManualDataBean);
	}

	public String getproductLimit(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.getproductLimit(stockOutManualDataBean);
	}
	
	public String quantityCheckStockOut(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.quantityCheckStockOut(stockOutManualDataBean);
	}
	
	public String stockoutManual(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.stockoutManual(stockOutManualDataBean);
	}
	
	public int remStockOutManual()
	{
		return service.remStockOutManual();
	}
	
	public String stockOutWaitingList(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.stockOutWaitingList(stockOutManualDataBean);
	}
	
	public String stockoutapproval(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.stockoutapproval(stockOutManualDataBean);
	}
	
	public String stockOutManualView(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.stockOutManualView(stockOutManualDataBean);
	}
	
	public String dateSearchView(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.dateSearchView(stockOutManualDataBean);
	}
	
	public String viewOutRecords(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.viewOutRecords(stockOutManualDataBean);
	}

	@Override
	public String inserttrans(TransactionDataBean transactionDataBean) {
		// TODO Auto-generated method stub
		return service.inserttrans(transactionDataBean);
	}


	@Override
	public String traview(TransactionDataBean transactionDataBean) {
		// TODO Auto-generated method stub
		return service.traview(transactionDataBean);
	}


	@Override
	public List<Transaction> trans(String transno) {
		// TODO Auto-generated method stub
		return service.trans(transno);
	}


	@Override
	public List<Transaction> traedit(TransactionDataBean transactionDataBean) {
		// TODO Auto-generated method stub
		return service.traedit(transactionDataBean);
	}


	@Override
	public String update(TransactionDataBean transactionDataBean) {
		// TODO Auto-generated method stub
		String status=service.update(transactionDataBean);
		return status;
	}


	@Override
	public String delettrans(TransactionDataBean transactionDataBean) {
		// TODO Auto-generated method stub
		return service.delettrans(transactionDataBean);
	}
	
	public String categoryOut(StockDataBean stockDataBean)
	{
		return service.categoryOut(stockDataBean);
	}
	
	public String stockoutview(StockDataBean stockDataBean)
	{
		return service.stockoutview(stockDataBean);
	}

	public String categorySearchView(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.categorySearchView(stockOutManualDataBean);
	}

	@Override
	public String payroll(EmployeeDataBean employeeDataBean)
			throws LiusenException {
		// TODO Auto-generated method stub
		return service.payroll(employeeDataBean);
	}
	
	@Override
	public String insertpayroll(EmployeeDataBean employeeDataBean) {
		
		return service.insertpayroll(employeeDataBean);
	}

	@Override
	public String payroll1(EmployeeDataBean employeeDataBean) {
		
		return service.payroll1(employeeDataBean);
	}

	@Override
	public List<String> getEmployeeName() {
		
		return service.getEmployeeName();
	}

	@Override
	public List<EmployeeDataBean> getPayrollList(
			EmployeeDataBean employeeDataBean) {
		return service.getPayrollList(employeeDataBean);
	}

	@Override
	public List<EmployeeDataBean> getPayrollList1(
			EmployeeDataBean employeeDataBean) {
		
		return service.getPayrollList1(employeeDataBean);
	}

	@Override
	public List<Payroll> getPayrollListSingle(EmployeeDataBean employeeDataBean) {
		
		return service.getPayrollListSingle(employeeDataBean);
	}

	@Override
	public String payrollDelete(EmployeeDataBean employeeDataBean) {
		
		return service.payrollDelete(employeeDataBean);
	}

	@Override
	public List<Payroll> getPayrollInfo(EmployeeDataBean employeeDataBean)
	{		
		return service.getPayrollInfo(employeeDataBean);
	}

	@Override
	public String editProduct(EmployeeDataBean employeeDataBean) {
		
		return service.editProduct(employeeDataBean);
	}
	
	public List<String> getinvoicePurchase()
	{
		return service.getinvoicePurchase();
	}
	
	public String invoiceView(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.invoiceView(purchaseOrederDataBean);
	}
	
	public String invoiceViewForm(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.invoiceViewForm(purchaseOrederDataBean);
	}

	public List<String> getinvoiceSales(SalesOrderDataBean salesOrderDataBean)
	{
		return service.getinvoiceSales(salesOrderDataBean);
	}
	
	public String invoiceViewSales(SalesOrderDataBean salesOrderDataBean)
	{
		return service.invoiceViewSales(salesOrderDataBean);
	}
	
	public String invoiceViewForm(SalesOrderDataBean salesOrderDataBean)
	{
		return service.invoiceViewForm(salesOrderDataBean);
	}
	
	public List<String> getpaymentSales(SalesOrderDataBean salesOrderDataBean)
	{
		return service.getpaymentSales(salesOrderDataBean);
	}
	
	public String paymentViewSales(SalesOrderDataBean salesOrderDataBean)
	{
		return service.paymentViewSales(salesOrderDataBean);
	}
	
	public String payForm(SalesOrderDataBean salesOrderDataBean)
	{
		return service.payForm(salesOrderDataBean);
	}
	
	public String paysales(SalesOrderDataBean salesOrderDataBean)
	{
		return service.paysales(salesOrderDataBean);
	}
	
	public String designRegSubmit(ProductDataBean productDataBean) throws LiusenException, ParseException
	{
		return service.designRegSubmit(productDataBean);
	}
	
	public int remSalesPaymnetdirector()
	{
		return service.remSalesPaymnetdirector();
	}

	public int remSalesPaymentFM()
	{
		return service.remSalesPaymentFM();
	}
	
	public String paymentFM(SalesOrderDataBean salesOrderDataBean)
	{
		return service.paymentFM(salesOrderDataBean);
	}
	
	public String paymentDirector(SalesOrderDataBean salesOrderDataBean)
	{
		return service.paymentDirector(salesOrderDataBean);
	}
	
	public String paymentApproveFM(SalesOrderDataBean salesOrderDataBean)
	{
		return service.paymentApproveFM(salesOrderDataBean);
	}

	public String paymentApproveDirector(SalesOrderDataBean salesOrderDataBean)
	{
		return service.paymentApproveDirector(salesOrderDataBean);
	}
	
	public List<String> purchasepayorders()
	{
		return service.purchasepayorders();
	}
	
	public String paymentViewpurchase(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.paymentViewpurchase(purchaseOrederDataBean);
	}
	
	public String payPurchase(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.payPurchase(purchaseOrederDataBean);
	}
	
	public String payPurchaseAmount(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.payPurchaseAmount(purchaseOrederDataBean);
	}
	
	public String paymentFM(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.paymentFM(purchaseOrederDataBean);
	}

	public String paymentDirector(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.paymentDirector(purchaseOrederDataBean);
	}
	
	public String paymentApproveFM(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.paymentApproveFM(purchaseOrederDataBean);
	}

	public String paymentApproveDirector(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.paymentApproveDirector(purchaseOrederDataBean);
	}
	
	public String paymentStatusView(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.paymentStatusView(purchaseOrederDataBean);
	}
	
	public String purcPaymentView(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.purcPaymentView(purchaseOrederDataBean);
	}
	
	public List<ProfitLossDataBean> searchProfitLoss(ProfitLossDataBean profitLossDataBean)
	{		
		return service.searchProfitLoss(profitLossDataBean);
	}
	
	public String purchaseOrderClose(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.purchaseOrderClose(purchaseOrederDataBean);
	}
	
	public List<String> paymentCategory(SalesOrderDataBean salesOrderDataBean)
	{
		return service.paymentCategory(salesOrderDataBean);
	}
	
	public String paymentStatusView1(SalesOrderDataBean salesOrderDataBean)
	{
		return service.paymentStatusView1(salesOrderDataBean);
	}
	
	public String salesPaymentView(SalesOrderDataBean salesOrderDataBean)
	{
		return service.salesPaymentView(salesOrderDataBean);
	}
	
	public List<String> stockDamgeProducts()
	{
		return service.stockDamgeProducts();
	}
	
	public String damageproductsView(StockDataBean stockDataBean)
	{
		return service.damageproductsView(stockDataBean);
	}
	
	public String dropprojectValues(ProjectDataBean projectDataBean)
	{
		return service.dropprojectValues(projectDataBean);
	}
	
	public String insertProjectValues(ProjectDataBean projectDataBean)throws LiusenException,IOException
	{
		return service.insertProjectValues(projectDataBean);
	}
	
	public String projectImage(ProjectDataBean projectDataBean)
	{
		return service.projectImage(projectDataBean);
	}
	
	public String findcashbook(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.findcashbook(purchaseOrederDataBean);
	}
	
	public String searchdate(PurchaseDataBean purchaseDataBean)
	{
		return service.searchdate(purchaseDataBean);
	}
	
	@Override
	public List<ProductDataBean> getDesignList(
			ProductDataBean productDataBean) {
		
		return service.getDesignList(productDataBean);
	}


	@Override
	public List<String> getDesignProjectList() {
	
		return service.getDesignProjectList();
	}


	@Override
	public List<ImagePath> getDesignListView(
			ProductDataBean productDataBean) {
		
		return service.getDesignListView(productDataBean);
	}


	@Override
	public List<String> getDesignProList() {
		
		return service.getDesignProList();
	}


	@Override
	public String designEditSubmit(ProductDataBean productDataBean) {
		
		return service.designEditSubmit(productDataBean);
	}


	@Override
	public String designDelete(ProductDataBean productDataBean) {
		
		return service.designDelete(productDataBean);
	}

	public String salesReport(SalesOrderDataBean salesOrderDataBean)
	{
		return service.salesReport(salesOrderDataBean);
	}
	
	public String payrollremainder(EmployeeDataBean employeeDataBean)
	{
		return service.payrollremainder(employeeDataBean);
	}
	
	public String viewAccountPayable(PurchaseOrederDataBean purchaseOrder) throws LiusenException
	{
		return service.viewAccountPayable(purchaseOrder);
	}
	
	public String purchasedate(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.purchasedate(purchaseOrederDataBean);
	}

	public String salesReturn(SalesOrderDataBean salesOrderDataBean)
	{
		return service.salesReturn(salesOrderDataBean);
	}
	
	public List<String> returnSales(SalesOrderDataBean salesOrderDataBean)
	{
		return service.returnSales(salesOrderDataBean);
	}
	
	public String returnSubmit(SalesOrderDataBean salesOrderDataBean)
	{
		return service.returnSubmit(salesOrderDataBean);
	}
	
	public String salesReturnSearch(SalesOrderDataBean salesOrderDataBean)
	{
		return service.salesReturnSearch(salesOrderDataBean);
	}
	
	@Override
	public List<String> dropProject(ProjectDataBean projectDataBean) 
	{	
		return service.dropProject(projectDataBean);
	}

	@Override
	public List<ProjectDataBean> getProjectView(ProjectDataBean projectDataBean)
	{		
		return service.getProjectView(projectDataBean);
	}

	@Override
	public List<Projet> getProjectListView(ProjectDataBean projectDataBean,List<ProjectDataBean> projectViewList) 
	{		
		return service.getProjectListView(projectDataBean,projectViewList);
	}

	@Override
	public String updateProject(ProjectDataBean projectDataBean) 
	{		
		return service.updateProject(projectDataBean);
	}

	@Override
	public String deleteProject(ProjectDataBean projectDataBean)
	{		
		return service.deleteProject(projectDataBean);
	}

	@Override
	public List<ProjectDataBean> getProjectViewbyClient(ProjectDataBean projectDataBean) 
	{		
		return service.getProjectViewbyClient(projectDataBean);
	}
	
	public String searchpayroll(EmployeeDataBean employeeDataBean)
	{
		return service.searchpayroll(employeeDataBean);
	}
	
	public String quantityCheckReturn(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		return service.quantityCheckReturn(salesOrderDataBean);
	}
	
	public String returnCheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		return service.returnCheck(salesOrderDataBean);
	}
	
	public String viewEachSalesRecord(SalesOrderDataBean salesOrderDataBean)
	{
		return service.viewEachSalesRecord(salesOrderDataBean);
	}
	
	public String salesReturnView(SalesOrderDataBean salesOrderDataBean)
	{
		return service.salesReturnView(salesOrderDataBean);
	}
	
	public String returnSalesView(SalesOrderDataBean salesOrderDataBean)
	{
		return service.returnSalesView(salesOrderDataBean);
	}
	
	public String purchaseReturn(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.purchaseReturn(purchaseOrederDataBean);
	}
	
	public String purchaseReturnsearch(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.purchaseReturnsearch(purchaseOrederDataBean);
	}
	
	public String returnCheckpur(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException
	{
		return service.returnCheckpur(purchaseOrederDataBean);
	}
	
	public String quantityCheckReturn(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException
	{
		return service.quantityCheckReturn(purchaseOrederDataBean);
	}
	
	public String returnSubmitPurcahse(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.returnSubmitPurcahse(purchaseOrederDataBean);
	}
	
	public String openingStock(ProductDataBean productDataBean)throws LiusenException
	{
		return service.openingStock(productDataBean);
	}
	
	public String purchaseReturnView(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.purchaseReturnView(purchaseOrederDataBean);
	}
	
	public String purchaseReturnViewRecord(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		return service.purchaseReturnViewRecord(purchaseOrederDataBean);
	}


	@Override
	public List<String> getProjectNames() {
		
		return service.getProjectNames();
	}
	
	public String projectNames(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.projectNames(stockOutManualDataBean);
	}
	
	public int remStockOutManualPM()
	{
		return service.remStockOutManualPM();		
	}
	
	public String stockOutWaitingListPM(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.stockOutWaitingListPM(stockOutManualDataBean);		
	}
	
	public String stockoutapprovalPM(StockOutManualDataBean stockOutManualDataBean)
	{
		return service.stockoutapprovalPM(stockOutManualDataBean);	
	}
}