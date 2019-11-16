package com.nrg.liusen.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
















































































































































import com.nrg.liusen.bo.LiusenBo;
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
 * This Java Class will communicate with MentariBo.java
 * @author Robert Arjun 
 * @date 13-11-2015
 * @copyright NRG    
 */
@Service("service")
public class LiusenServiceImpl   implements LiusenService{

	
	
	@Autowired
	@Qualifier("bo")
	LiusenBo bo;

	@Override
	public String loginService(LoginAccess loginaccess)throws LiusenException
	{
		System.out.println("inside service ::::::::::::::::::");
		System.out.println(loginaccess.getUsername());
		
		bo.loginBo(loginaccess);
		return null;
	}

	@Override
	public String insertVendor(VendorDataBean vendorDataBean){
		
		return bo.insertVendor(vendorDataBean);
		
	}

	@Override
	public List<String> getCountryList() {
		
		return bo.getCountryList();
	}

	@Override
	public List<VendorDataBean> searchByVendorName(VendorDataBean vendorDataBean) {
		
		return bo.searchByVendorName(vendorDataBean);
	}

	@Override
	public List<Vendor> getVendorInfo(String venFirmName) {
		
		return  bo.getVendorInfo(venFirmName);
	}

	@Override
	public String editVendor(VendorDataBean vendorDataBean) {
		String status=bo.editVendor(vendorDataBean);
		return status;
	}

	@Override
	public String deleteVendor(VendorDataBean vendorDataBean) {
		
		return bo.deleteVendor(vendorDataBean);
	}

	@Override
	public List<VendorDataBean> searchByCityName(VendorDataBean vendorDataBean) {
		
		return bo.searchByCityName(vendorDataBean);
	}

	@Override
	public List<String> getVendorList() {
	
		return bo.getVendorList();
	}

	@Override
	public String insertProduct(ProductDataBean productDataBean) {
	
		return bo.insertProduct(productDataBean);
	}

	@Override
	public List<ProductDataBean> getProductList(ProductDataBean productDataBean) {
		
		return bo.getProductList(productDataBean);
	}

	@Override
	public List<Product> getProductListSingle(String prodProductName) {
		
		return bo.getProductListSingle(prodProductName);
	}

	
	@Override
	public List<Product> getProductInfo(ProductDataBean productDataBean) {
		
		return bo.getProductInfo(productDataBean);
	}
	
	@Override
	public String editProduct(ProductDataBean productDataBean) {
		
		return bo.editProduct(productDataBean);
	}

	@Override
	public String productDelete(ProductDataBean productDataBean) {
	
		return bo.productDelete(productDataBean);
	}

	@Override
	public List<ProductDataBean> getProductListByCategory(ProductDataBean productDataBean) {
		
		return bo.getProductListByCategory(productDataBean);
	}

	@Override
	public List<String> productNameList(String category) {
	
		return bo.productNameList(category);
	}

	@Override
	public String insertLimit(LimitDataBean limitDataBean) {
		
		return bo.insertLimit(limitDataBean);
	}

	@Override
	public List<LimitDataBean> getLimit(String limitProductName1) {
		
		return bo.getLimit(limitProductName1);
	}

	@Override
	public List<String> getQualification() {
		
		return bo.getQualification();
	}

	@Override
	public List<String> getDesignation() {
		
		return bo.getDesignation();
	}

	@Override
	public String inserEmployee(EmployeeDataBean employeeDataBean) {
		
		return  bo.inserEmployee(employeeDataBean);
	}

	@Override
	public List<String> getEmployeeID() {
		
		return bo.getEmployeeID();
	}

	@Override
	public List<RawMaterial> getProductListSingle1(String prodProductName) {
	
		return bo.getProductListSingle1(prodProductName);
	}

	@Override
	public List<RawMaterial> getRawCategoryEdit(ProductDataBean productDataBean) {
		
		return bo.getRawCategoryEdit(productDataBean);
	}

	@Override
	public List<String> getProjectServiceList(String category) {
		
		return bo.getProjectServiceList(category);
	}

	@Override
	public List<ProductLimit> getLimitListByProject(LimitDataBean limitDataBean) {
		
		return bo.getLimitListByProject(limitDataBean);
	}

	@Override
	public List<ProductLimit> limitListEditByProject(LimitDataBean limitDataBean) {
		return bo.limitListEditByProject(limitDataBean);
	}

	@Override
	public String EditLimit(LimitDataBean limitDataBean) {
		return bo.EditLimit(limitDataBean);
	}

	@Override
	public String limitDelete(LimitDataBean limitDataBean) {
		return bo.limitDelete(limitDataBean);
	}

	@Override
	public List<LimitDataBean> getLimitByCategory(String limProductCategory1) {
		
		return bo.getLimitByCategory(limProductCategory1);
	}

	@Override
	public List<String> getPurchaseOrderNo() {
		return bo.getPurchaseOrderNo();
	}

	@Override
	public List<Purchase> getPurchaseOrderDetails(StockDataBean stockDataBean) {
		return bo.getPurchaseOrderDetails(stockDataBean);
	}

	@Override
	public String insertStock(StockDataBean stockDataBean) {
		
		return bo.insertStock(stockDataBean);
	}

	@Override
	public List<String> getStockProductName() {
		
		return bo.getStockProductName();
	}

	@Override
	public List<String> getvendorName() {
		return bo.getvendorName();
	}

	@Override
	public List<StockDataBean> getStockInList(StockDataBean stockDataBean) {
		
		return bo.getStockInList(stockDataBean);
	}

	@Override
	public List<StockDataBean> getStockInListByVendor(
			StockDataBean stockDataBean) {
		
		return bo.getStockInListByVendor(stockDataBean);
	}
	@Override
	public List<String> getstaffid() {
		
		return bo.getstaffid();
	}

	@Override
	public String insertCustomer(CustomerDataBean customerDataBean) {
	
		return bo.insertCustomer(customerDataBean);
	}

	@Override
	public List<CustomerDataBean> getCustList(CustomerDataBean customerDataBean) {
	
		return bo.getCustList(customerDataBean);
	}

	@Override
	public String customerDelete(CustomerDataBean customerDataBean) {
		
		return bo.customerDelete(customerDataBean);
	}

	@Override
	public List<Customer> getCustomerInfo(String custCustomerName) {
	
		return bo.getCustomerInfo(custCustomerName);
	}

	@Override
	public String editCustomer(CustomerDataBean customerDataBean) {
		
		return bo.editCustomer(customerDataBean);
	}

	@Override
	public List<CustomerDataBean> searchByCustomerName(
			CustomerDataBean customerDataBean) {
		
		return  bo.searchByCustomerName(customerDataBean);
	}

	@Override
	public List<CustomerDataBean> searchByCityName(
			CustomerDataBean customerDataBean) {
	
		return  bo.searchByCityName(customerDataBean);
	}
	@Override
	public List<Employee> getemp(EmployeeDataBean employeeDataBean) {
		
		return bo.getemp(employeeDataBean);
	}


	@Override
	public List<EmployeeDataBean> searchname(EmployeeDataBean employeeDataBean) {
		
		return bo.searchname(employeeDataBean);
	}


	@Override
	public List<Employee> getempedit(String empEmployeeName, EmployeeDataBean employeeDataBean) {
	
		return bo.getempedit(empEmployeeName,employeeDataBean);
	}


	@Override
	public String getupdate(EmployeeDataBean employeeDataBean) {
		
		String status=bo.getupdate(employeeDataBean);
		return status;
	}


	@Override
	public String deleteemployee(EmployeeDataBean employeeDataBean) {
		
		return bo.deleteemployee(employeeDataBean);
	}


	@Override
	public List<String> empid() {
		
		return bo.empid();
	}


	@Override
	public List<EmployeeDataBean> searchid(EmployeeDataBean employeeDataBean) {
		
		return bo.searchid(employeeDataBean);
	}

	@Override
	public String addDamage(StockDataBean stockDataBean)  throws LiusenException{
		
		return bo.addDamage(stockDataBean);
	}@Override
	public List<String> productNameList(PurchaseOrederDataBean o)
	{
		return bo.productNameList(o);
	}
	@Override
	public List<String> vendorNameList(PurchaseOrederDataBean o)
	{
		return bo.vendorNameList(o);
	}
	@Override
	public String productPrice(PurchaseOrederDataBean p)
	{
		return bo.productPrice(p);
	}
	@Override
	public String purchaseInsertion1(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.purchaseInsertion1(p);
	}
	@Override
	public String purchaseInsertion2(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.purchaseInsertion2(p);
	}
	@Override
	public String purchaseView(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.purchaseView(p);
	}
	@Override
	public String purchaseDelete(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.purchaseDelete(p);
	}
	@Override
	public String purchaseEditConfirm(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.purchaseEditConfirm(p);
	}
	@Override
	public String purchaseApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.purchaseApproval(p);
	}
	@Override
	public String purchaseOrderDetailedView(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.purchaseOrderDetailedView(p);
	}
	@Override
	public String purchaseEditDeleteCheck(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.purchaseEditDeleteCheck(p);
	}
	@Override
	public String poDetailsForDeliveryApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.poDetailsForDeliveryApproval(p);
	}
	@Override
	public String poDetailsForApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.poDetailsForApproval(p);
	}
	@Override
	public String poGMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.poGMApproval(p);
	}
	@Override
	public String poMMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.poMMApproval(p);
	}
	@Override
	public String poDelGMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.poDelGMApproval(p);
	}
	@Override
	public String poDelPMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return bo.poDelPMApproval(p);
	}	
	
	/*udhaya*/
	public void dropCustomer(SalesOrderDataBean salesOrderDataBean)
	{
		bo.dropCustomer(salesOrderDataBean);
	}	
	
	public void categoryChange(SalesOrderDataBean salesOrderDataBean)
	{
		bo.categoryChange(salesOrderDataBean);
	}
	
	public void productChange(SalesOrderDataBean salesOrderDataBean)
	{
		bo.productChange(salesOrderDataBean);
	}
	
	public void quantityChange(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		bo.quantityChange(salesOrderDataBean);
	}
	
	public String salesInsertion(SalesOrderDataBean salesOrderDataBean)
	{
		return bo.salesInsertion(salesOrderDataBean);
	}
	
	public void dateSearchSales(SalesOrderDataBean salesOrderDataBean)
	{
		bo.dateSearchSales(salesOrderDataBean);
	}
	
	public void categorySearchSales(SalesOrderDataBean salesOrderDataBean)
	{
		bo.categorySearchSales(salesOrderDataBean);
	}
	
	public void viewEachSales(SalesOrderDataBean salesOrderDataBean)
	{
		bo.viewEachSales(salesOrderDataBean);
	}
	
	public void editSalesOrder(SalesOrderDataBean salesOrderDataBean)
	{
		bo.editSalesOrder(salesOrderDataBean);
	}
	
	public void updatequantitycheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		bo.updatequantitycheck(salesOrderDataBean);
	}
	
	public void updateSales(SalesOrderDataBean salesOrderDataBean)
	{
		bo.updateSales(salesOrderDataBean);
	}
	
	public void deleteSales(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		bo.deleteSales(salesOrderDataBean);
	}
	
	public int remSalesApprovalGM()
	{
		return bo.remSalesApprovalGM();
	}

	public int remSalesApprovalMM()
	{
		return bo.remSalesApprovalMM();
	}


		@Override
		public String approveMM(SalesOrderDataBean salesOrderDataBean)
	{
		return bo.approveMM(salesOrderDataBean);
	}
	
		@Override
	public String approveGM(SalesOrderDataBean salesOrderDataBean)
	{
		return bo.approveGM(salesOrderDataBean);
	}
	
		@Override
	public String approvedSalesMM(SalesOrderDataBean salesOrderDataBean)
	{
		return bo.approvedSalesMM(salesOrderDataBean);
	}
	
		@Override
	public String approvedSalesGM(SalesOrderDataBean salesOrderDataBean)
	{
		return bo.approvedSalesGM(salesOrderDataBean);
	}

		@Override
		public List<String> getSalesOrderList(
				StockOutManualDataBean stockOutManualDataBean) {
			
			return bo.getSalesOrderList(stockOutManualDataBean);
		}

		@Override
		public List<String> getProjectDetails(String orderNo,StockOutManualDataBean stockOutManualDataBean) {
		
			return bo.getProjectDetails(orderNo,stockOutManualDataBean);
		}

		@Override
		public List<String> getProjectList() {
		
			return bo.getProjectList();
		}

		@Override
		public List<RawMaterial> getSubCategoty(String category) {
			
			return bo.getSubCategoty(category);
		}

		public int remSalesDeliveryGM() 
		{
			return bo.remSalesDeliveryGM();
		}
		
		public int remSalesDeliveryPM()
		{
			return bo.remSalesDeliveryPM();
		}
		
		public String deliveryGM(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.deliveryGM(salesOrderDataBean);
		}

		public String deliveryPM(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.deliveryPM(salesOrderDataBean);
		}
		
		public String deliverdSalesGM(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.deliverdSalesGM(salesOrderDataBean);
		}
		
		public String deliverdSalesPM(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.deliverdSalesPM(salesOrderDataBean);
		}

		public void deleteCheck(SalesOrderDataBean salesOrderDataBean)
		{
			bo.deleteCheck(salesOrderDataBean);
		}
		
		/*udhaya 19/12*/
		public String getProjectLists(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.getProjectLists(stockOutManualDataBean);
		}

		public String customerList(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.customerList(stockOutManualDataBean);
		}
		
		public String employeeList(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.employeeList(stockOutManualDataBean);
		}
		
		public String getproductLimit(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.getproductLimit(stockOutManualDataBean);
		}
		
		public String quantityCheckStockOut(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.quantityCheckStockOut(stockOutManualDataBean);
		}
		
		public String stockoutManual(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.stockoutManual(stockOutManualDataBean);
		}
		
		public int remStockOutManual()
		{
			return bo.remStockOutManual();
		}
		
		public String stockOutWaitingList(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.stockOutWaitingList(stockOutManualDataBean);
		}
		
		public String stockoutapproval(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.stockoutapproval(stockOutManualDataBean);
		}
		
		public String stockOutManualView(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.stockOutManualView(stockOutManualDataBean);
		}
		
		public String dateSearchView(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.dateSearchView(stockOutManualDataBean);
		}
		
		public String viewOutRecords(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.viewOutRecords(stockOutManualDataBean);
		}
		
		@Override
		public String inserttrans(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			return bo.inserttrans(transactionDataBean);
		}


		@Override
		public String traview(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			return bo.traview(transactionDataBean);
		}


		@Override
		public List<Transaction> trans(String transno) {
			// TODO Auto-generated method stub
			return bo.trans(transno);
		}


		@Override
		public List<Transaction> traedit(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			return bo.traedit(transactionDataBean);
		}


		@Override
		public String update(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			String status=bo.update(transactionDataBean);
			return status;
		}


		@Override
		public String delettrans(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			return bo.delettrans(transactionDataBean);
		}
		
		public String categoryOut(StockDataBean stockDataBean)
		{
			return bo.categoryOut(stockDataBean);
		}

		public String stockoutview(StockDataBean stockDataBean)
		{
			return bo.stockoutview(stockDataBean);
		}
		
		public String categorySearchView(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.categorySearchView(stockOutManualDataBean);
		}
		
		@Override
		public String payroll(EmployeeDataBean employeeDataBean)
				throws LiusenException {
			// TODO Auto-generated method stub
			return bo.payroll(employeeDataBean);
		}
		
		@Override
		public String insertpayroll(EmployeeDataBean employeeDataBean) {
			
			return bo.insertpayroll(employeeDataBean);
		}

		@Override
		public String payroll1(EmployeeDataBean employeeDataBean) {
			
			return bo.payroll1(employeeDataBean);
		}

		@Override
		public List<String> getEmployeeName() {
			
			return bo.getEmployeeName();
		}

		@Override
		public List<EmployeeDataBean> getPayrollList(
				EmployeeDataBean employeeDataBean) {
			return bo.getPayrollList(employeeDataBean);
		}

		@Override
		public List<EmployeeDataBean> getPayrollList1(
				EmployeeDataBean employeeDataBean) {
			
			return bo.getPayrollList1(employeeDataBean);
		}

		@Override
		public List<Payroll> getPayrollListSingle(EmployeeDataBean employeeDataBean) {
			
			return bo.getPayrollListSingle(employeeDataBean);
		}

		@Override
		public String payrollDelete(EmployeeDataBean employeeDataBean) {
			
			return bo.payrollDelete(employeeDataBean);
		}
		@Override
		public List<Payroll> getPayrollInfo(EmployeeDataBean employeeDataBean)
		{		
			return bo.getPayrollInfo(employeeDataBean);
		}

		@Override
		public String editProduct(EmployeeDataBean employeeDataBean) {
			
			return bo.editProduct(employeeDataBean);
		}
		public List<String> getinvoicePurchase()
		{
			return bo.getinvoicePurchase();
		}
		
		public String invoiceView(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.invoiceView(purchaseOrederDataBean);
		}
		
		public String invoiceViewForm(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.invoiceViewForm(purchaseOrederDataBean);
		}
		
		public List<String> getinvoiceSales(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.getinvoiceSales(salesOrderDataBean);
		}
		
		public String invoiceViewSales(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.invoiceViewSales(salesOrderDataBean);
		}
		
		public String invoiceViewForm(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.invoiceViewForm(salesOrderDataBean);
		}
		
		public List<String> getpaymentSales(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.getpaymentSales(salesOrderDataBean);
		}
		
		public String paymentViewSales(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.paymentViewSales(salesOrderDataBean);
		}
		
		public String payForm(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.payForm(salesOrderDataBean);
		}
		
		public String paysales(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.paysales(salesOrderDataBean);
		}
		
		public String designRegSubmit(ProductDataBean productDataBean) throws LiusenException, ParseException
		{
			return bo.designRegSubmit(productDataBean);
		}
		
		public int remSalesPaymnetdirector()
		{
			return bo.remSalesPaymnetdirector();
		}

		public int remSalesPaymentFM()
		{
			return bo.remSalesPaymentFM();
		}
		
		public String paymentFM(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.paymentFM(salesOrderDataBean);
		}
		
		public String paymentDirector(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.paymentDirector(salesOrderDataBean);
		}
		
		public String paymentApproveFM(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.paymentApproveFM(salesOrderDataBean);
		}

		public String paymentApproveDirector(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.paymentApproveDirector(salesOrderDataBean);
		}
		
		public List<String> purchasepayorders()
		{
			return bo.purchasepayorders();
		}
		
		public String paymentViewpurchase(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.paymentViewpurchase(purchaseOrederDataBean);
		}
		
		public String payPurchase(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.payPurchase(purchaseOrederDataBean);
		}
		
		public String payPurchaseAmount(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.payPurchaseAmount(purchaseOrederDataBean);
		}
		
		public String paymentFM(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.paymentFM(purchaseOrederDataBean);
		}

		public String paymentDirector(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.paymentDirector(purchaseOrederDataBean);
		}
		
		public String paymentApproveFM(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.paymentApproveFM(purchaseOrederDataBean);
		}

		public String paymentApproveDirector(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.paymentApproveDirector(purchaseOrederDataBean);
		}
		
		public String paymentStatusView(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.paymentStatusView(purchaseOrederDataBean);
		}
		
		public String purcPaymentView(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.purcPaymentView(purchaseOrederDataBean);
		}
		
		public List<ProfitLossDataBean> searchProfitLoss(ProfitLossDataBean profitLossDataBean)
		{			
			return bo.searchProfitLoss(profitLossDataBean);
		}
		
		public String purchaseOrderClose(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.purchaseOrderClose(purchaseOrederDataBean);
		}
		
		public List<String> paymentCategory(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.paymentCategory(salesOrderDataBean);
		}
		
		public String paymentStatusView1(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.paymentStatusView1(salesOrderDataBean);
		}
		
		public String salesPaymentView(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.salesPaymentView(salesOrderDataBean);
		}
		
		public List<String> stockDamgeProducts()
		{
			return bo.stockDamgeProducts();
		}
		
		public String damageproductsView(StockDataBean stockDataBean)
		{
			return bo.damageproductsView(stockDataBean);
		}
		
		public String dropprojectValues(ProjectDataBean projectDataBean)
		{
			return bo.dropprojectValues(projectDataBean);
		}
		
		public String insertProjectValues(ProjectDataBean projectDataBean)throws LiusenException,IOException
		{
			return bo.insertProjectValues(projectDataBean);
		}
		
		public String projectImage(ProjectDataBean projectDataBean)
		{
			return bo.projectImage(projectDataBean);
		}
		public String findcashbook(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.findcashbook(purchaseOrederDataBean);
		}
		
		public String searchdate(PurchaseDataBean purchaseDataBean)
		{
			return bo.searchdate(purchaseDataBean);
		}
		
		@Override
		public List<ProductDataBean> getDesignList(
				ProductDataBean productDataBean) {
			
			return bo.getDesignList(productDataBean);
		}


		@Override
		public List<String> getDesignProjectList() {
		
			return bo.getDesignProjectList();
		}


		@Override
		public List<ImagePath> getDesignListView(
				ProductDataBean productDataBean) {
			
			return bo.getDesignListView(productDataBean);
		}


		@Override
		public List<String> getDesignProList() {
			
			return bo.getDesignProList();
		}


		@Override
		public String designEditSubmit(ProductDataBean productDataBean) {
			
			return bo.designEditSubmit(productDataBean);
		}


		@Override
		public String designDelete(ProductDataBean productDataBean) {
			
			return bo.designDelete(productDataBean);
		}
		
		public String salesReport(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.salesReport(salesOrderDataBean);
		}
		
		public String payrollremainder(EmployeeDataBean employeeDataBean)
		{
			return bo.payrollremainder(employeeDataBean);
		}
		
		public String viewAccountPayable(PurchaseOrederDataBean purchaseOrder) throws LiusenException
		{
			return bo.viewAccountPayable(purchaseOrder);
		}
		
		public String purchasedate(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.purchasedate(purchaseOrederDataBean);
		}
		
		public String salesReturn(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.salesReturn(salesOrderDataBean);
		}
		
		public List<String> returnSales(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.returnSales(salesOrderDataBean);
		}
		
		public String returnSubmit(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.returnSubmit(salesOrderDataBean);
		}
		
		public String salesReturnSearch(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.salesReturnSearch(salesOrderDataBean);
		}
		
		@Override
		public List<String> dropProject(ProjectDataBean projectDataBean) 
		{		
			return bo.dropProject(projectDataBean);
		}

		@Override
		public List<ProjectDataBean> getProjectView(ProjectDataBean projectDataBean) 
		{			
			return bo.getProjectView(projectDataBean);
		}

		@Override
		public List<Projet> getProjectListView(ProjectDataBean projectDataBean,List<ProjectDataBean> projectViewList) 
		{			
			return bo.getProjectListView(projectDataBean,projectViewList);
		}

		@Override
		public String updateProject(ProjectDataBean projectDataBean) 
		{			
			return bo.updateProject(projectDataBean);
		}

		@Override
		public String deleteProject(ProjectDataBean projectDataBean) 
		{			
			return bo.deleteProject(projectDataBean);
		}

		@Override
		public List<ProjectDataBean> getProjectViewbyClient(ProjectDataBean projectDataBean) 
		{			
			return bo.getProjectViewbyClient(projectDataBean);
		}
		
		public String searchpayroll(EmployeeDataBean employeeDataBean)
		{
			return bo.searchpayroll(employeeDataBean);
		}
		
		public String quantityCheckReturn(SalesOrderDataBean salesOrderDataBean)throws LiusenException
		{
			return bo.quantityCheckReturn(salesOrderDataBean);
		}
		
		public String returnCheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException
		{
			return bo.returnCheck(salesOrderDataBean);
		}
		
		public String viewEachSalesRecord(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.viewEachSalesRecord(salesOrderDataBean);
		}
		
		public String salesReturnView(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.salesReturnView(salesOrderDataBean);
		}
		
		public String returnSalesView(SalesOrderDataBean salesOrderDataBean)
		{
			return bo.returnSalesView(salesOrderDataBean);
		}
		
		public String purchaseReturn(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.purchaseReturn(purchaseOrederDataBean);
		}
		
		public String purchaseReturnsearch(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.purchaseReturnsearch(purchaseOrederDataBean);
		}
		
		public String returnCheckpur(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException
		{
			return bo.returnCheckpur(purchaseOrederDataBean);
		}
		
		public String quantityCheckReturn(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException
		{
			return bo.quantityCheckReturn(purchaseOrederDataBean);
		}
		
		public String returnSubmitPurcahse(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.returnSubmitPurcahse(purchaseOrederDataBean);
		}
		
		public String openingStock(ProductDataBean productDataBean)throws LiusenException
		{
			return bo.openingStock(productDataBean);
		}
		
		public String purchaseReturnView(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.purchaseReturnView(purchaseOrederDataBean);
		}
		
		public String purchaseReturnViewRecord(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return bo.purchaseReturnViewRecord(purchaseOrederDataBean);
		}

		@Override
		public List<String> getProjectNames() {
			
			return bo.getProjectNames();
		}
		
		public String projectNames(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.projectNames(stockOutManualDataBean);
		}
		
		public int remStockOutManualPM()
		{
			return bo.remStockOutManualPM();		
		}
		
		public String stockOutWaitingListPM(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.stockOutWaitingListPM(stockOutManualDataBean);		
		}
		
		public String stockoutapprovalPM(StockOutManualDataBean stockOutManualDataBean)
		{
			return bo.stockoutapprovalPM(stockOutManualDataBean);	
		}
	}