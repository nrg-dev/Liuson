package com.nrg.liusen.dao;
import java.io.IOException;
import java.util.List;

import com.nrg.liusen.domain.CustomerDataBean;
import com.nrg.liusen.domain.EmployeeDataBean;
import com.nrg.liusen.domain.LimitDataBean;
import com.nrg.liusen.domain.LoginAccess;
import com.nrg.liusen.domain.ProductDataBean;
import com.nrg.liusen.domain.ProjectDataBean;
import com.nrg.liusen.domain.PurchaseDataBean;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.domain.TransactionDataBean;
import com.nrg.liusen.domain.VendorDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.shared.Customer;
import com.nrg.liusen.shared.Employee;
import com.nrg.liusen.shared.ImagePath;
import com.nrg.liusen.shared.Product;
import com.nrg.liusen.shared.ProductLimit;
import com.nrg.liusen.shared.Projet;
import com.nrg.liusen.shared.RawMaterial;
import com.nrg.liusen.shared.Transaction;
import com.nrg.liusen.shared.Vendor;
import com.steadystate.css.parser.ParseException;
/**
 * This Java Class will communicate with Database
 * @author Robert Arjun 
 * @date 13-11-2015
 * @copyright NRG      
 */
public interface LiusenDao
{
	public String loginDao(LoginAccess loginaccess) throws LiusenException;

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

	public List<RawMaterial> getProductListSingle1(String prodProductName);

	public List<RawMaterial> getRawCategoryEdit(ProductDataBean productDataBean);

	public List<String> getProjectServiceList(String category);

	public List<ProductLimit> getLimitListByProject(LimitDataBean limitDataBean);

	public List<ProductLimit> limitListEditByProject(LimitDataBean limitDataBean);

	public String EditLimit(LimitDataBean limitDataBean);

	public String limitDelete(LimitDataBean limitDataBean);

	public List<LimitDataBean> getLimitByCategory(String limProductCategory1);

	public String insertCustomer(CustomerDataBean customerDataBean);

	public List<CustomerDataBean> getCustList(CustomerDataBean customerDataBean);

	public String customerDelete(CustomerDataBean customerDataBean);

	public List<Customer> getCustomerInfo(String custCustomerName);

	public String editCustomer(CustomerDataBean customerDataBean);

	public List<CustomerDataBean> searchByCustomerName(
			CustomerDataBean customerDataBean);

	public List<CustomerDataBean> searchByCustomerCityName(
			CustomerDataBean customerDataBean);

	public List<String> getProjectList();

	public List<RawMaterial> getSubCategoty(String category);

	public String inserttrans(TransactionDataBean transactionDataBean);
	
	public String traview(com.nrg.liusen.domain.TransactionDataBean transactionDataBean);

	public List<Transaction> trans(String transno);

	public List<Transaction> traedit(TransactionDataBean transactionDataBean);

	public String update(TransactionDataBean transactionDataBean);

	public String delettrans(TransactionDataBean transactionDataBean);
	
	public List<Employee> getempedit(String empEmployeeName,
			EmployeeDataBean employeeDataBean);

	public String getupdate(EmployeeDataBean employeeDataBean);

	public String deleteemployee(EmployeeDataBean employeeDataBean);
	
	public String designRegSubmit(ProductDataBean productDataBean) throws LiusenException, ParseException;
	
	public String dropprojectValues(ProjectDataBean projectDataBean);
	
	public String insertProjectValues(ProjectDataBean projectDataBean) throws LiusenException, IOException;
	
	public String projectImage(ProjectDataBean projectDataBean);

	public String insertProjectEmployee(ProjectDataBean projectDataBean);

	public String insertPmarketingEmployee(ProjectDataBean projectDataBean);

	public String insertPadminEmployee(ProjectDataBean projectDataBean);
	
	public String findcashbook(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public String searchdate(PurchaseDataBean purchaseDataBean);
	
	public String designDelete(ProductDataBean productDataBean);

	public List<ImagePath> getDesignListView(ProductDataBean productDataBean);

	public List<String> getDesignProjectList();

	public String designEditSubmit(ProductDataBean productDataBean);

	public List<String> getDesignProList();

	public List<ProductDataBean> getDesignList(ProductDataBean productDataBean);
	
	public String viewAccountPayable(PurchaseOrederDataBean purchaseOrder) throws LiusenException;
	
	public String purchasedate(PurchaseOrederDataBean purchaseOrederDataBean);
	
	public List<String> dropProject(ProjectDataBean projectDataBean);

	public List<ProjectDataBean> getProjectView(ProjectDataBean projectDataBean);

	public List<Projet> getProjectListView(ProjectDataBean projectDataBean,
			List<ProjectDataBean> projectViewList);

	public String updateProject(ProjectDataBean projectDataBean);

	public String deleteProject(ProjectDataBean projectDataBean);

	public List<ProjectDataBean> getProjectViewbyClient(
			ProjectDataBean projectDataBean);
	
	public String searchpayroll(EmployeeDataBean employeeDataBean);
	
	public String openingStock(ProductDataBean productDataBean)throws LiusenException;

	public String purchasetable(ProductDataBean productDataBean);

	public String stocktable(ProductDataBean productDataBean);

	public String batchtable(ProductDataBean productDataBean);

	public String barcodetable(ProductDataBean productDataBean);
}
