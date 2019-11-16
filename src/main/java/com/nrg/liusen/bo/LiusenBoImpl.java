package com.nrg.liusen.bo;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrg.liusen.dao.EmployeeDao;
import com.nrg.liusen.dao.LiusenDao;
import com.nrg.liusen.dao.PurchaseDao;
import com.nrg.liusen.dao.SalesDao;
import com.nrg.liusen.dao.StockDao;
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

@Service("bo")
public   class LiusenBoImpl implements LiusenBo
{
	@Autowired
	LiusenDao dao;	
	
	@Autowired
	EmployeeDao empDao;
    
	@Autowired
	StockDao sDao;
	
	@Autowired
	PurchaseDao pDao;
	
	@Autowired
	SalesDao salesDao;
	
	@Override
	public String loginBo(LoginAccess loginaccess) throws LiusenException 
	{
		System.out.println("inside bo");
		System.out.println(loginaccess.getUsername());
		dao.loginDao(loginaccess);		
		return null;
	}


	@Override
	public String insertVendor(VendorDataBean vendorDataBean) {
		
		System.out.println("Calling insertVendor method in bo");
			
		return dao.insertVendor(vendorDataBean);
		
		
	}


	@Override
	public List<String> getCountryList() {
		
		return dao.getCountryList();
	}


	@Override
	public List<VendorDataBean> searchByVendorName(VendorDataBean vendorDataBean) {
		
		return dao.searchByVendorName(vendorDataBean);
	}


	@Override
	public List<Vendor> getVendorInfo(String venFirmName) {
		
		return dao.getVendorInfo(venFirmName);
	}


	@Override
	public String editVendor(VendorDataBean vendorDataBean) {
		String status=dao.editVendor(vendorDataBean);
		return status;
	}


	@Override
	public String deleteVendor(VendorDataBean vendorDataBean) {
	
		return dao.deleteVendor(vendorDataBean);
	}


	@Override
	public List<VendorDataBean> searchByCityName(VendorDataBean vendorDataBean) {
		
		return dao.searchByCityName(vendorDataBean);
	}


	@Override
	public List<String> getVendorList() {
		
		return dao.getVendorList();
	}


	@Override
	public String insertProduct(ProductDataBean productDataBean) {
		
		return dao.insertProduct(productDataBean);
	}


	@Override
	public List<ProductDataBean> getProductList(ProductDataBean productDataBean) {
		
		return dao.getProductList(productDataBean);
	}


	@Override
	public List<Product> getProductListSingle(String prodProductName) {
		
		return dao.getProductListSingle(prodProductName);
	}

	@Override
	public List<Product> getProductInfo(ProductDataBean productDataBean) {
		
		return dao.getProductInfo(productDataBean);
	}
	

	@Override
	public String editProduct(ProductDataBean productDataBean) {
		
		return dao.editProduct(productDataBean);
	}


	@Override
	public String productDelete(ProductDataBean productDataBean) {
		
		return dao.productDelete(productDataBean);
	}


	@Override
	public List<ProductDataBean> getProductListByCategory(
			ProductDataBean productDataBean) {
		
		return dao.getProductListByCategory(productDataBean);
	}


	@Override
	public List<String> productNameList(String category) {
		
		return dao.productNameList(category);
	}


	@Override
	public String insertLimit(LimitDataBean limitDataBean) {
		
		return dao.insertLimit(limitDataBean);
	}


	@Override
	public List<LimitDataBean> getLimit(String limitProductName1) {
		
		return dao.getLimit(limitProductName1);
	}


	@Override
	public List<String> getQualification() {
		
		return empDao.getQualification();
	}


	@Override
	public List<String> getDesignation() {
	
		return empDao.getDesignation();
	}


	@Override
	public String inserEmployee(EmployeeDataBean employeeDataBean) {
		
		return empDao.inserEmployee(employeeDataBean);
	}


	@Override
	public List<String> getEmployeeID() {
		
		return empDao.getEmployeeID();
	}


	@Override
	public List<RawMaterial> getProductListSingle1(String prodProductName) {
		
		return dao.getProductListSingle1(prodProductName);
	}


	@Override
	public List<RawMaterial> getRawCategoryEdit(ProductDataBean productDataBean) {
		
		return dao.getRawCategoryEdit(productDataBean);
	}


	@Override
	public List<String> getProjectServiceList(String category) {
		
		return dao.getProjectServiceList(category);
	}


	@Override
	public List<ProductLimit> getLimitListByProject(LimitDataBean limitDataBean) {
		
		return dao.getLimitListByProject(limitDataBean);
	}


	@Override
	public List<ProductLimit> limitListEditByProject(LimitDataBean limitDataBean) {
		return dao.limitListEditByProject(limitDataBean);
	}


	@Override
	public String EditLimit(LimitDataBean limitDataBean) {
		return dao.EditLimit(limitDataBean);
	}


	@Override
	public String limitDelete(LimitDataBean limitDataBean) {
		
		return dao.limitDelete(limitDataBean);
	}


	@Override
	public List<LimitDataBean> getLimitByCategory(String limProductCategory1) {
		
		return dao.getLimitByCategory(limProductCategory1);
	}


	@Override
	public List<String> getPurchaseOrderNo() {
		System.out.println("Out");
		return sDao.getPurchaseOrderNo();
	}


	@Override
	public List<Purchase> getPurchaseOrderDetails(StockDataBean stockDataBean) {
		
		return sDao.getPurchaseOrderDetails(stockDataBean);
	}


	@Override
	public String insertStock(StockDataBean stockDataBean) {
		
		return sDao.insertStock(stockDataBean);
	}


	@Override
	public List<String> getStockProductName() {
		return sDao.getStockProductName();
	}


	@Override
	public List<String> getvendorName() {
		
		return sDao.getvendorName();
	}


	@Override
	public List<StockDataBean> getStockInList(StockDataBean stockDataBean) {
		
		return sDao.getStockInList(stockDataBean);
	}


	@Override
	public List<StockDataBean> getStockInListByVendor(
			StockDataBean stockDataBean) {
	
		return sDao.getStockInListByVendor(stockDataBean);
	}

	@Override
	public List<String> getstaffid() {
		
		return empDao.getEmployeeID();
	}


	@Override
	public String insertCustomer(CustomerDataBean customerDataBean) {
		
		return dao.insertCustomer(customerDataBean);
	}


	@Override
	public List<CustomerDataBean> getCustList(CustomerDataBean customerDataBean) {
		
		return dao.getCustList(customerDataBean);
	}


	@Override
	public String customerDelete(CustomerDataBean customerDataBean) {
		
		return dao.customerDelete(customerDataBean);
	}


	@Override
	public List<Customer> getCustomerInfo(String custCustomerName) {
	
		return dao.getCustomerInfo(custCustomerName);
	}


	@Override
	public String editCustomer(CustomerDataBean customerDataBean) {
	
		return dao.editCustomer(customerDataBean);
	}


	@Override
	public List<CustomerDataBean> searchByCustomerName(
			CustomerDataBean customerDataBean) {
		
		return dao.searchByCustomerName(customerDataBean);
	}


	@Override
	public List<CustomerDataBean> searchByCityName(
			CustomerDataBean customerDataBean) {
		
		return dao.searchByCustomerCityName(customerDataBean);
	}


	@Override
	public List<Employee> getemp(EmployeeDataBean employeeDataBean) {
	
		return empDao.getemp(employeeDataBean);
	}


	@Override
	public List<EmployeeDataBean> searchname(EmployeeDataBean employeeDataBean) {
		
		return empDao.searchname(employeeDataBean);
	}


	@Override
	public List<Employee> getempedit(String empEmployeeName,EmployeeDataBean employeeDataBean) {
		
		return empDao.getempedit(empEmployeeName,employeeDataBean);
	}


	@Override
	public String getupdate(EmployeeDataBean employeeDataBean) {
		
		String status=empDao.getupdate(employeeDataBean);

		return status;
	}


	@Override
	public String deleteemployee(EmployeeDataBean employeeDataBean) {
	
		return empDao.deleteemployee(employeeDataBean);
	}


	@Override
	public List<String> empid() {
	
		return empDao.empid();
	}


	@Override
	public List<EmployeeDataBean> searchid(EmployeeDataBean employeeDataBean) {
	
		return empDao.searchid(employeeDataBean);
	}


	@Override
	public String addDamage(StockDataBean stockDataBean) throws LiusenException {
		
		return sDao.addDamage(stockDataBean);
	}

	@Override
	public List<String> productNameList(PurchaseOrederDataBean o)
	{
		return pDao.productNameList(o);
	}
	@Override
	public List<String> vendorNameList(PurchaseOrederDataBean o)
	{
		return pDao.vendorNameList(o);
	}
	@Override
	public String productPrice(PurchaseOrederDataBean p)
	{
		return pDao.productPrice(p);
	}
	@Override
	public String purchaseInsertion1(PurchaseOrederDataBean p) throws LiusenException
	{
		return pDao.purchaseInsertion1(p);
	}
	@Override
	public String purchaseInsertion2(PurchaseOrederDataBean p) throws LiusenException
	{
		System.out.println("-->>list size "+p.getPurchaseList().size());
		for(int i=0;i<p.getPurchaseList().size();i++)
		{
			if(!p.getPurchaseList().get(i).getPurProductName().equalsIgnoreCase(""))
			{
			p.setPurProductName(p.getPurchaseList().get(i).getPurProductName());
			p.setPurQuantity(p.getPurchaseList().get(i).getPurQuantity());
			p.setPurprice(p.getPurchaseList().get(i).getPurprice());
			pDao.purchaseInsertion2(p);
			}
		}
		
		return "";
	}
	@Override
	public String purchaseView(PurchaseOrederDataBean p) throws LiusenException
	{
		return pDao.purchaseView(p);
	}
	@Override
	public String purchaseDelete(PurchaseOrederDataBean p) throws LiusenException
	{
		return pDao.purchaseDelete(p);
	}
	@Override
	public String purchaseEditConfirm(PurchaseOrederDataBean p) throws LiusenException
	{
		pDao.productPrice(p);
		return pDao.purchaseEditConfirm(p);
	}
	@Override
	public String purchaseApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		pDao.purchaseApproval(p);
		pDao.purchasePayment(p);
		return "";
	}
	@Override
	public String purchaseOrderDetailedView(PurchaseOrederDataBean p) throws LiusenException
	{
		return pDao.purchaseOrderDetailedView(p);
	}
	@Override
	public String purchaseEditDeleteCheck(PurchaseOrederDataBean p) throws LiusenException
	{
		return pDao.purchaseEditDeleteCheck(p);
	}
	@Override
	public String poDetailsForDeliveryApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return pDao.poDetailsForDeliveryApproval(p);
	}
	@Override
	public String poDetailsForApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return pDao.poDetailsForApproval(p);
	}
	@Override
	public String poGMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return pDao.poGMApproval(p);
	}
	@Override
	public String poMMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return pDao.poMMApproval(p);
	}
	@Override
	public String poDelGMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return pDao.poDelGMApproval(p);
	}
	@Override
	public String poDelPMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		return pDao.poDelPMApproval(p);
	}	

/*udhaya*/
	

	
	public void dropCustomer(SalesOrderDataBean salesOrderDataBean)
	{
		salesDao.dropCustomer(salesOrderDataBean);
	}
	
	public void categoryChange(SalesOrderDataBean salesOrderDataBean)
	{
		salesDao.categoryChange(salesOrderDataBean);
	}

	public void productChange(SalesOrderDataBean salesOrderDataBean)
	{
		salesDao.productChange(salesOrderDataBean);
	}
	
	public void quantityChange(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		salesDao.quantityChange(salesOrderDataBean);
	}
	
	/*public String salesInsertion(SalesOrderDataBean salesOrderDataBean)
	{
		salesDao.salesInsertion(salesOrderDataBean);
		System.out.println("list size -- > "+salesOrderDataBean.getSalesList().size());
		for (int i = 0; i <salesOrderDataBean.getSalesList().size(); i++) 
		{
			if(!salesOrderDataBean.getSalesList().get(i).getSaleProductName().equals(""))
			{
				salesDao.salesRecordInsert(salesOrderDataBean,i);
			}
		}
		return "";
	}*/
	
	public void dateSearchSales(SalesOrderDataBean salesOrderDataBean)
	{
		salesDao.dateSearchSales(salesOrderDataBean);
	}
	
	public void categorySearchSales(SalesOrderDataBean salesOrderDataBean)
	{
		salesDao.categorySearchSales(salesOrderDataBean);
	}
	
	public void viewEachSales(SalesOrderDataBean salesOrderDataBean)
	{
		salesDao.viewEachSales(salesOrderDataBean);
	}
	
	public void editSalesOrder(SalesOrderDataBean salesOrderDataBean)
	{
		salesDao.editSalesOrder(salesOrderDataBean);
	}
	
	public void updatequantitycheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		salesDao.updatequantitycheck(salesOrderDataBean);
	}
	
	public void updateSales(SalesOrderDataBean salesOrderDataBean)
	{
		salesDao.updateSales(salesOrderDataBean);
	}
	
	public void deleteSales(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		salesDao.deleteSales(salesOrderDataBean);
	}
	
	public int remSalesApprovalGM()
	{
		return salesDao.remSalesApprovalGM();
	}

	public int remSalesApprovalMM()
	{
		return salesDao.remSalesApprovalMM();
	}


	@Override
public String salesInsertion(SalesOrderDataBean salesOrderDataBean)
{
salesDao.salesInsertion(salesOrderDataBean);
System.out.println("list size -- > "+salesOrderDataBean.getSalesList().size());
for (int i = 0; i <salesOrderDataBean.getSalesList().size(); i++) 
{
	if(!salesOrderDataBean.getSalesList().get(i).getSaleProductName().equals(""))
	{
		salesDao.salesRecordInsert(salesOrderDataBean,i);
		if(salesOrderDataBean.getSaleCategory().equals("Product Trading"))
		{
			System.out.println("category bo  -- > "+salesOrderDataBean.getSaleCategory());
			salesDao.updateStock(salesOrderDataBean,i);
		}				
	}
}
return "";
}
	
		@Override
	public String approveMM(SalesOrderDataBean salesOrderDataBean)
	{
	return salesDao.approveMM(salesOrderDataBean);
	}
	
		@Override
	public String approveGM(SalesOrderDataBean salesOrderDataBean)
	{
	return salesDao.approveGM(salesOrderDataBean);
	}
	
		@Override
	public String approvedSalesMM(SalesOrderDataBean salesOrderDataBean)
	{
	return salesDao.approvedSalesMM(salesOrderDataBean);
	}
	
		@Override
	public String approvedSalesGM(SalesOrderDataBean salesOrderDataBean)
	{
	return salesDao.approvedSalesGM(salesOrderDataBean);
	}


		@Override
		public List<String> getSalesOrderList(
				StockOutManualDataBean stockOutManualDataBean) {
			
			return sDao.getSalesOrderList(stockOutManualDataBean);
		}


		@Override
		public List<String> getProjectDetails(String orderNo,StockOutManualDataBean stockOutManualDataBean) {
			
			return sDao.getProjectDetails(orderNo,stockOutManualDataBean);
		}


		@Override
		public List<String> getProjectList() {
			
			return dao.getProjectList();
		}


		@Override
		public List<RawMaterial> getSubCategoty(String category) {
			
			return dao.getSubCategoty(category);
		}
		
		
		public int remSalesDeliveryGM() 
		{
			return salesDao.remSalesDeliveryGM();
		}
		
		public int remSalesDeliveryPM()
		{
			return salesDao.remSalesDeliveryPM();
		}
		
		public String deliveryGM(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.deliveryGM(salesOrderDataBean);
		}

		public String deliveryPM(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.deliveryPM(salesOrderDataBean);
		}
		
		public String deliverdSalesGM(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.deliverdSalesGM(salesOrderDataBean);
		}
		
		public String deliverdSalesPM(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.deliverdSalesPM(salesOrderDataBean);
		}
		
		public void deleteCheck(SalesOrderDataBean salesOrderDataBean)
		{
			salesDao.deleteCheck(salesOrderDataBean);
		}
		
		/*udhaya 19/12*/
		public String getProjectLists(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.getProjectLists(stockOutManualDataBean);
		}

		public String customerList(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.customerList(stockOutManualDataBean);
		}
		
		public String employeeList(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.employeeList(stockOutManualDataBean);
		}
		
		public String getproductLimit(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.getproductLimit(stockOutManualDataBean);
		}
		
		public String quantityCheckStockOut(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.quantityCheckStockOut(stockOutManualDataBean);
		}
		
		public String stockoutManual(StockOutManualDataBean stockOutManualDataBean)
		{
			int c=0;
			sDao.stockoutManual(stockOutManualDataBean);
			for (int i = 0; i < stockOutManualDataBean.getStockOutList().size(); i++) 
			{
				sDao.stockoutRecord(stockOutManualDataBean , i);
				if(!stockOutManualDataBean.getStockOutList().get(i).getSoutProductName1().equals(""))
				{
					if(Integer.parseInt(stockOutManualDataBean.getStockOutList().get(i).getSoutLimit())<Integer.parseInt(stockOutManualDataBean.getStockOutList().get(i).getSoutQty()))
					{
						c++;
					}	
				}		
			}	
			if(c>0)
			{
				System.out.println("quantity is greater than limit");
				sDao.stockOutupdate(stockOutManualDataBean);
			}
			else
			{
				System.out.println("quantity is less than limit");
				for (int i = 0; i < stockOutManualDataBean.getStockOutList().size(); i++) 
				{
					if(!stockOutManualDataBean.getStockOutList().get(i).getSoutProductName1().equals(""))
					{
						sDao.stockoutBarcode(stockOutManualDataBean , i);	
					}											
				}	
				sDao.stockOutupdate1(stockOutManualDataBean);
			}
			return "";
		}
		
		public int remStockOutManual()
		{
			return sDao.remStockOutManual();
		}
		
		public String stockOutWaitingList(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.stockOutWaitingList(stockOutManualDataBean);
		}
		
		public String stockoutapproval(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.stockoutapproval(stockOutManualDataBean);
		}
		
		public String stockOutManualView(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.stockOutManualView(stockOutManualDataBean);
		}
		
		public String dateSearchView(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.dateSearchView(stockOutManualDataBean);
		}
		
		public String viewOutRecords(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.viewOutRecords(stockOutManualDataBean);
		}
		
		@Override
		public String inserttrans(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			return dao.inserttrans(transactionDataBean);
		}


		@Override
		public String traview(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			return dao.traview(transactionDataBean);
		}


		@Override
		public List<Transaction> trans(String transno) {
			// TODO Auto-generated method stub
			return dao.trans(transno);
		}


		@Override
		public List<Transaction> traedit(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			return dao.traedit(transactionDataBean);
		}


		@Override
		public String update(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			String status=dao.update(transactionDataBean);
			return status;
		}


		@Override
		public String delettrans(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			return dao.delettrans(transactionDataBean);
		}
		
		public String categoryOut(StockDataBean stockDataBean)
		{
			return sDao.categoryOut(stockDataBean);
		}

		public String stockoutview(StockDataBean stockDataBean)
		{
			return sDao.stockoutview(stockDataBean);
		}
		
		public String categorySearchView(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.categorySearchView(stockOutManualDataBean);
		}
		
		@Override
		public String payroll(EmployeeDataBean employeeDataBean)
				throws LiusenException {
			// TODO Auto-generated method stub
			return empDao.payroll(employeeDataBean);
		}
		
		@Override
		public String insertpayroll(EmployeeDataBean employeeDataBean) {
			
			return empDao.insertpayroll(employeeDataBean);
		}

		@Override
		public String payroll1(EmployeeDataBean employeeDataBean) {
			
			return empDao.payroll1(employeeDataBean);
		}

		@Override
		public List<String> getEmployeeName() {
			
			return empDao.getEmployeeName();
		}

		@Override
		public List<EmployeeDataBean> getPayrollList(
				EmployeeDataBean employeeDataBean) {
			return empDao.getPayrollList(employeeDataBean);
		}

		@Override
		public List<EmployeeDataBean> getPayrollList1(
				EmployeeDataBean employeeDataBean) {
			
			return empDao.getPayrollList1(employeeDataBean);
		}

		@Override
		public List<Payroll> getPayrollListSingle(EmployeeDataBean employeeDataBean) {
			
			return empDao.getPayrollListSingle(employeeDataBean);
		}

		@Override
		public String payrollDelete(EmployeeDataBean employeeDataBean) {
			
			return empDao.payrollDelete(employeeDataBean);
		}

		@Override
		public List<Payroll> getPayrollInfo(EmployeeDataBean employeeDataBean)
		{		
			return empDao.getPayrollInfo(employeeDataBean);
		}

		@Override
		public String editProduct(EmployeeDataBean employeeDataBean) {
			
			return empDao.editProduct(employeeDataBean);
		}
		
		public List<String> getinvoicePurchase()
		{
			return pDao.getinvoicePurchase();
		}
		
		public String invoiceView(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.invoiceView(purchaseOrederDataBean);
		}
		
		public String invoiceViewForm(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			pDao.invoiceViewForm(purchaseOrederDataBean);
			pDao.invoiceinsertpur(purchaseOrederDataBean);
			return "";
		}
		
		public List<String> getinvoiceSales(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.getinvoiceSales(salesOrderDataBean);
		}
		
		public String invoiceViewSales(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.invoiceViewSales(salesOrderDataBean);
		}
		
		public String invoiceViewForm(SalesOrderDataBean salesOrderDataBean)
		{
			salesDao.invoiceViewForm(salesOrderDataBean);
			salesDao.invoiceinsert(salesOrderDataBean);
			return "";
		}
		
		public List<String> getpaymentSales(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.getpaymentSales(salesOrderDataBean);
		}
		
		public String paymentViewSales(SalesOrderDataBean salesOrderDataBean)
		{
			salesDao.paymentViewSales(salesOrderDataBean);
			salesDao.paymentinSales(salesOrderDataBean);
			return "";
		}
		
		public String payForm(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.payForm(salesOrderDataBean);
		}
		
		public String paysales(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.paysales(salesOrderDataBean);
		}
		
		public String designRegSubmit(ProductDataBean productDataBean) throws LiusenException, ParseException
		{
			return dao.designRegSubmit(productDataBean);
		}
		
		public int remSalesPaymnetdirector()
		{
			return salesDao.remSalesPaymnetdirector();
		}

		public int remSalesPaymentFM()
		{
			return salesDao.remSalesPaymentFM();
		}
		
		public String paymentFM(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.paymentFM(salesOrderDataBean);
		}
		
		public String paymentDirector(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.paymentDirector(salesOrderDataBean);
		}
		
		public String paymentApproveFM(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.paymentApproveFM(salesOrderDataBean);
		}

		public String paymentApproveDirector(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.paymentApproveDirector(salesOrderDataBean);
		}
		
		public List<String> purchasepayorders()
		{
			return pDao.purchasepayorders();
		}
		
		public String paymentViewpurchase(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			pDao.paymentViewpurchase(purchaseOrederDataBean);
			pDao.paymentinpur(purchaseOrederDataBean);
			return "";
		}
		
		public String payPurchase(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.payPurchase(purchaseOrederDataBean);
		}
		
		public String payPurchaseAmount(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.payPurchaseAmount(purchaseOrederDataBean);
		}
		
		public String paymentFM(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.paymentFM(purchaseOrederDataBean);
		}

		public String paymentDirector(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.paymentDirector(purchaseOrederDataBean);
		}
		
		public String paymentApproveFM(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.paymentApproveFM(purchaseOrederDataBean);
		}

		public String paymentApproveDirector(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.paymentApproveDirector(purchaseOrederDataBean);
		}
		
		public String paymentStatusView(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.paymentStatusView(purchaseOrederDataBean);
		}
		
		public String purcPaymentView(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.purcPaymentView(purchaseOrederDataBean);
		}
		
		public List<ProfitLossDataBean> searchProfitLoss(ProfitLossDataBean profitLossDataBean) 
		{			
			return pDao.searchProfitLoss(profitLossDataBean);
		}
		
		public String purchaseOrderClose(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.purchaseOrderClose(purchaseOrederDataBean);
		}
		
		public List<String> paymentCategory(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.paymentCategory(salesOrderDataBean);
		}
		
		public String paymentStatusView1(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.paymentStatusView1(salesOrderDataBean);
		}
		
		public String salesPaymentView(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.salesPaymentView(salesOrderDataBean);
		}
		
		public List<String> stockDamgeProducts()
		{
			return sDao.stockDamgeProducts();
		}
		
		public String damageproductsView(StockDataBean stockDataBean)
		{
			return sDao.damageproductsView(stockDataBean);
		}
		
		public String dropprojectValues(ProjectDataBean projectDataBean)
		{
			return dao.dropprojectValues(projectDataBean);
		}
		
		public String insertProjectValues(ProjectDataBean projectDataBean)throws LiusenException,IOException
		{
			dao.insertProjectValues(projectDataBean);
			dao.insertProjectEmployee(projectDataBean);
			dao.insertPmarketingEmployee(projectDataBean);
			dao.insertPadminEmployee(projectDataBean);
			return "";
		}
		
		public String projectImage(ProjectDataBean projectDataBean)
		{
			return dao.projectImage(projectDataBean);
		}
		
		public String findcashbook(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return dao.findcashbook(purchaseOrederDataBean);
		}
		
		public String searchdate(PurchaseDataBean purchaseDataBean)
		{
			return dao.searchdate(purchaseDataBean);
		}@Override
		public List<ProductDataBean> getDesignList(
				ProductDataBean productDataBean) {
			
			return dao.getDesignList(productDataBean);
		}


		@Override
		public List<String> getDesignProjectList() {
		
			return dao.getDesignProjectList();
		}


		@Override
		public List<ImagePath> getDesignListView(
				ProductDataBean productDataBean) {
			
			return dao.getDesignListView(productDataBean);
		}


		@Override
		public List<String> getDesignProList() {
			
			return dao.getDesignProList();
		}


		@Override
		public String designEditSubmit(ProductDataBean productDataBean) {
			
			return dao.designEditSubmit(productDataBean);
		}


		@Override
		public String designDelete(ProductDataBean productDataBean) {
			
			return dao.designDelete(productDataBean);
		}
		
		public String salesReport(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.salesReport(salesOrderDataBean);
		}
		
		public String payrollremainder(EmployeeDataBean employeeDataBean)
		{
			return empDao.payrollremainder(employeeDataBean);
		}
		
		public String viewAccountPayable(PurchaseOrederDataBean purchaseOrder) throws LiusenException
		{
			return dao.viewAccountPayable(purchaseOrder);
		}
		
		public String purchasedate(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return dao.purchasedate(purchaseOrederDataBean);
		}
		
		public String salesReturn(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.salesReturn(salesOrderDataBean);
		}
		
		public List<String> returnSales(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.returnSales(salesOrderDataBean);
		}
		
		public String returnSubmit(SalesOrderDataBean salesOrderDataBean)
		{
			salesDao.returnSubmit(salesOrderDataBean);
			for (int j = 0; j < salesOrderDataBean.getSalesList().size(); j++)
			{
				if(salesOrderDataBean.getSalesList().get(j).getTick().equals("true"))
				{
					if(salesOrderDataBean.getSalesList().get(j).getText().equals("true"))
					{
						salesDao.returnSubmit1(salesOrderDataBean,j);
					}					
				}
			}
			salesDao.salesUpdateReturn(salesOrderDataBean);
			for (int j = 0; j < salesOrderDataBean.getSalesList().size(); j++)
			{
				if(salesOrderDataBean.getSalesList().get(j).getTick().equals("true"))
				{
					if(salesOrderDataBean.getSalesList().get(j).getText().equals("true"))
					{
						if(!salesOrderDataBean.getSalesList().get(j).getReturnqauntity().equals(""))
						{
							salesDao.stockUpdateReturnNr(salesOrderDataBean,j);
						}
						if(!salesOrderDataBean.getSalesList().get(j).getDr().equals(""))
						{
							salesDao.stockUpdateReturnDr(salesOrderDataBean,j);
						}						
					}					
				}
			}
			salesDao.paymentCheckReturn(salesOrderDataBean);
			return "";
		}
		
		public String salesReturnSearch(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.salesReturnSearch(salesOrderDataBean);
		}
		
		@Override
		public List<String> dropProject(ProjectDataBean projectDataBean) 
		{		
			return dao.dropProject(projectDataBean);
		}

		@Override
		public List<ProjectDataBean> getProjectView(ProjectDataBean projectDataBean) 
		{			
			return dao.getProjectView(projectDataBean);
		}

		@Override
		public List<Projet> getProjectListView(ProjectDataBean projectDataBean,List<ProjectDataBean> projectViewList) 
		{			
			return dao.getProjectListView(projectDataBean,projectViewList);
		}

		@Override
		public String updateProject(ProjectDataBean projectDataBean) 
		{			
			return dao.updateProject(projectDataBean);
		}

		@Override
		public String deleteProject(ProjectDataBean projectDataBean) 
		{			
			return dao.deleteProject(projectDataBean);
		}

		@Override
		public List<ProjectDataBean> getProjectViewbyClient(ProjectDataBean projectDataBean) 
		{			
			return dao.getProjectViewbyClient(projectDataBean);
		}
		
		public String searchpayroll(EmployeeDataBean employeeDataBean)
		{
			return dao.searchpayroll(employeeDataBean);
		}
		
		public String quantityCheckReturn(SalesOrderDataBean salesOrderDataBean)throws LiusenException
		{
			return salesDao.quantityCheckReturn(salesOrderDataBean);
		}
		
		public String returnCheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException
		{
			return salesDao.returnCheck(salesOrderDataBean);
		}
		
		public String viewEachSalesRecord(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.viewEachSalesRecord(salesOrderDataBean);
		}
		
		public String salesReturnView(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.salesReturnView(salesOrderDataBean);
		}
		
		public String returnSalesView(SalesOrderDataBean salesOrderDataBean)
		{
			return salesDao.returnSalesView(salesOrderDataBean);
		}
		
		public String purchaseReturn(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.purchaseReturn(purchaseOrederDataBean);
		}
		
		public String purchaseReturnsearch(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.purchaseReturnsearch(purchaseOrederDataBean);
		}
		
		public String returnCheckpur(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException
		{
			return pDao.returnCheckpur(purchaseOrederDataBean);
		}
		
		public String quantityCheckReturn(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException
		{
			return pDao.quantityCheckReturn(purchaseOrederDataBean);
		}
		
		public String returnSubmitPurcahse(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			pDao.returnSubmitPurcahse(purchaseOrederDataBean);
			for (int j = 0; j < purchaseOrederDataBean.getPurchaseViewList().size(); j++)
			{
				if(purchaseOrederDataBean.getPurchaseViewList().get(j).getTick().equals("true"))
				{
					if(purchaseOrederDataBean.getPurchaseViewList().get(j).getText().equals("true"))
					{
						pDao.returnSubmitPurcahse1(purchaseOrederDataBean,j);
					}					
				}
			}
			pDao.purchaseUpdateReturn(purchaseOrederDataBean);
			for (int j = 0; j < purchaseOrederDataBean.getPurchaseViewList().size(); j++)
			{
				if(purchaseOrederDataBean.getPurchaseViewList().get(j).getTick().equals("true"))
				{
					if(purchaseOrederDataBean.getPurchaseViewList().get(j).getText().equals("true"))
					{
						if(!purchaseOrederDataBean.getPurchaseViewList().get(j).getReturnqauntity().equals(""))
						{
							pDao.stockUpdateReturnNr(purchaseOrederDataBean,j);
						}
						if(!purchaseOrederDataBean.getPurchaseViewList().get(j).getDr().equals(""))
						{
							pDao.stockUpdateReturnDr(purchaseOrederDataBean,j);
						}						
					}					
				}
			}
			pDao.paymentCheckReturn(purchaseOrederDataBean);
			return "";
		}
		
		public String openingStock(ProductDataBean productDataBean)throws LiusenException
		{
			dao.openingStock(productDataBean);
			dao.purchasetable(productDataBean);
			dao.stocktable(productDataBean);
			dao.batchtable(productDataBean);
			dao.barcodetable(productDataBean);
			return "";
		}
		
		public String purchaseReturnView(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.purchaseReturnView(purchaseOrederDataBean);
		}
		
		public String purchaseReturnViewRecord(PurchaseOrederDataBean purchaseOrederDataBean)
		{
			return pDao.purchaseReturnViewRecord(purchaseOrederDataBean);
		}


		@Override
		public List<String> getProjectNames() {
			
			return salesDao.getProjectNames();
		}
		
		public String projectNames(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.projectNames(stockOutManualDataBean);
		}
		
		public int remStockOutManualPM()
		{
			return sDao.remStockOutManualPM();		
		}
		
		public String stockOutWaitingListPM(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.stockOutWaitingListPM(stockOutManualDataBean);		
		}
		
		public String stockoutapprovalPM(StockOutManualDataBean stockOutManualDataBean)
		{
			return sDao.stockoutapprovalPM(stockOutManualDataBean);	
		}
}