package com.nrg.liusen.dao;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.faces.context.FacesContext;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nrg.liusen.domain.CustomerDataBean;
import com.nrg.liusen.domain.EmployeeDataBean;
import com.nrg.liusen.domain.LimitDataBean;
import com.nrg.liusen.domain.LoginAccess;
import com.nrg.liusen.domain.ProductDataBean;
import com.nrg.liusen.domain.ProjectDataBean;
import com.nrg.liusen.domain.PurchaseDataBean;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.domain.TransactionDataBean;
import com.nrg.liusen.domain.VendorDataBean;
import com.nrg.liusen.exception.LiusenException;


import com.nrg.liusen.shared.Barcode;
import com.nrg.liusen.shared.Batch;
import com.nrg.liusen.shared.Country;
import com.nrg.liusen.shared.Customer;
import com.nrg.liusen.shared.Designation;
import com.nrg.liusen.shared.Employee;
import com.nrg.liusen.shared.ImagePath;
import com.nrg.liusen.shared.Invoice;
import com.nrg.liusen.shared.OpeningStock;
import com.nrg.liusen.shared.Payment;
import com.nrg.liusen.shared.Payroll;
import com.nrg.liusen.shared.Product;
import com.nrg.liusen.shared.ProductLimit;
import com.nrg.liusen.shared.ProjectEmployee;
import com.nrg.liusen.shared.Projet;
import com.nrg.liusen.shared.Purchase;
import com.nrg.liusen.shared.PurchaseRecord;
import com.nrg.liusen.shared.PurchaseReturn;
import com.nrg.liusen.shared.Qualification;
import com.nrg.liusen.shared.RawMaterial;
import com.nrg.liusen.shared.Sale;
import com.nrg.liusen.shared.SalesRecord;
import com.nrg.liusen.shared.SalesReturn;
import com.nrg.liusen.shared.Stock;
import com.nrg.liusen.shared.StockoutManual;
import com.nrg.liusen.shared.Transaction;
import com.nrg.liusen.shared.User;
import com.nrg.liusen.shared.UserRoll;
import com.nrg.liusen.shared.Vendor;
import com.steadystate.css.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
@Singleton
public class LiusenDaoImpl implements LiusenDao
{
	
	final Logger logger = LoggerFactory.getLogger(LiusenDaoImpl.class);

	@PersistenceContext(unitName="liusen-pu")
	private EntityManager entitymanager;
	
	@PersistenceContext(unitName="liusen-pu")
	private EntityManager entitymanager1;	
	
	SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
	
	
	/**
	 * This loginDao Method is Used for Authenticate the User 
	 */
	@Override
	@Transactional(value="transactionManager")
	public String loginDao(LoginAccess loginaccess) throws LiusenException {
		
		Query q=null;
		String pwd=null;
		String rollName=null;
		String login_user=null;
		Query q1=null;
		q=entitymanager.createQuery("from User where user_Name=? ");
		q.setParameter(1, loginaccess.getUsername());
		//p=loginaccess.getUsername();
		if(loginaccess.getUsername().length()==0)
		{
			throw new LiusenException("Enter UserName And Password");
		}
		@SuppressWarnings("unchecked")
		List<User> result=(List<User>)q.getResultList();
		System.out.println(result);
		
		if(result.size()==0)
		{
			
			throw new LiusenException("Invalid UserName"); 
			
		}
		else{
			
			
			int out=0;
			pwd=result.get(out).getUser_Password();

			System.out.println("pwd");
			System.out.println(pwd);
		}
		if(!pwd.equalsIgnoreCase(loginaccess.getUserpassword()))
		{
			throw new LiusenException("Invalid Password");  
		}else{
			login_user=result.get(0).getLogin_UserName();
			loginaccess.setLogin_user(login_user);
			q1=entitymanager.createQuery("from UserRoll where (user_ID=? and user_roll_Status='Active')");// Checking for User Role
			q1.setParameter(1, result.get(0).getUser_ID());
			List<UserRoll> res=(List<UserRoll>)q1.getResultList();
			if(res.size()>0){
				System.out.println("Roll Name"+res.get(0).getRoll().getRoll_Name());
				rollName=res.get(0).getRoll().getRoll_Name();
				loginaccess.setUser_rolles(rollName);
			}else{
				throw new LiusenException("Network Problem. Please try again...");  
			}
		}
		
		return null;
	}
	
	/**
	 * This insertVendor method is used for insert the values to DB
	 * @author Robert Arjun
	 * @date 16-11-2015
	 * @copyright NRG
	 */
	@Override
	@Transactional(value="transactionManager")
	public String insertVendor(VendorDataBean vendorDataBean) {
		
		String inserDaoStatus="insertFali";
		System.out.println("Calling insertVendor method in DAO");
		System.out.println("Firm Name"+vendorDataBean.getVenFirmName());
		System.out.println("Login User Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");// get the user roll
		Date date=new Date();
		Timestamp timestamp=new Timestamp(date.getTime());
		System.out.println(date);
		System.out.println(timestamp);
		if(login == null){
			System.out.println("Inside Login name is Null");
			return inserDaoStatus; // this return status is go to redirect the login page( session is null )
		}else {
			try{
				System.out.println("Inside Try Method calling in DAO");
				int Country_ID=0;
				Query q=entitymanager.createQuery("from Country where country_Name=?");
				q.setParameter(1, vendorDataBean.getVenCountry());
				List<Country> countryRes=(List<Country>)q.getResultList();
				System.out.println("Country Size"+countryRes.size());
				if(countryRes.size()>0){
					Country_ID=countryRes.get(0).getCountry_ID();
					System.out.println("Country ID"+Country_ID);
					Query q1=entitymanager.createQuery("from Vendor where ( firmName=? and status=? )"); // checking vendor name exsist or not
					q1.setParameter(1, vendorDataBean.getVenFirmName());
					q1.setParameter(2, "Active");
					List<Vendor> venList=(List<Vendor>)q1.getResultList();
					if(venList.size()>0){
						//throw error message to JSF Page
						System.out.println("Vender Name already registered...");
						inserDaoStatus="Exsist";
						System.out.println(inserDaoStatus);
					}else{
						// insert to DB
						Vendor vendor=new Vendor();
						vendor.setFirmName(vendorDataBean.getVenFirmName());
						vendor.setAddress(vendorDataBean.getVenAddress());
						vendor.setCity(vendorDataBean.getVenCity());
						vendor.setCountry(entitymanager.find(Country.class, Country_ID)); // Foreign Key
						vendor.setEmail(vendorDataBean.getVenEmail());
						vendor.setFaxNumber(vendorDataBean.getVenFaxNO());
						vendor.setFirmType(vendorDataBean.getVenFirmType());
						vendor.setLoginStatus(login);
						vendor.setNatureBusiness(vendorDataBean.getVenNature());
						vendor.setNote(vendorDataBean.getVenNote());
						vendor.setPersonIncharge(vendorDataBean.getVenPerson());
						vendor.setPhoneNumber(vendorDataBean.getVenPhone());
						vendor.setStandards(vendorDataBean.getVenStandard());
						vendor.setVenRegDate(date);
						vendor.setVenRegTime(timestamp);
						vendor.setTelephoneNumber(vendorDataBean.getVenTelephone());
						vendor.setTaxNumber(vendorDataBean.getVenTaxNO());
						vendor.setEditStatus("Not Edited");
						vendor.setStatus("Active");
						vendor.setState(vendorDataBean.getVenState());
						vendor.setWebAddress(vendorDataBean.getVenWeb());
						vendor.setTelephoneNo2(vendorDataBean.getVenTelephone1());
						vendor.setAccountNo(vendorDataBean.getVenAccountNo());
						vendor.setBankName(vendorDataBean.getVenBankName());
						vendor.setPhoneNumber2(vendorDataBean.getVenPhone1());
						vendor.setTelephoneNo2(vendorDataBean.getVenTelephone1());
						vendor.setPersonIncharge2(vendorDataBean.getVenPhone1());
						entitymanager.persist(vendor);
						inserDaoStatus="insertSuccess";
						
					}
					System.out.println(inserDaoStatus);
				}
			return inserDaoStatus;
			}catch(Exception e){
				e.printStackTrace();
				return inserDaoStatus;
			}
		}
	}

	@Override
	/**
	 * This getCountryList method is used for get the Country list  DB to JSF
	 * @author Robert Arjun
	 * @date 16-11-2015
	 * @copyright NRG
	 */
	public List<String> getCountryList() {
		List<String> country=null;
		try{
			Query q=null;
			q=entitymanager.createQuery("select country_Name from Country");
			country=(List<String>)q.getResultList();
			System.out.println("Country List"+country.size());
			return country;
		}catch(Exception e){
			return country;
		}
	}
	
	/**
	 * This searchByVendorName method is used for get the vendor details depending on Firmname set to Data table
	 * @author Robert Arjun
	 * @date 18-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<VendorDataBean> searchByVendorName(VendorDataBean vendorDataBean) {
		
		List<VendorDataBean> vendorList=null;
		System.out.println("Inside searchByVendorName in DAO method calling");
		System.out.println("Firm Name"+vendorDataBean.getVenFirmName1());
		if(vendorDataBean.getVenFirmName() !=null){
			try{
			Query ven=null;
			vendorList=new ArrayList<VendorDataBean>();
			ven=entitymanager.createQuery("from Vendor where (firmName Like ?  and status=?)"); //Like Search
			ven.setParameter(1, "%"+vendorDataBean.getVenFirmName1()+"%");
			ven.setParameter(2, "Active");
			List<Vendor> vendorResult=(List<Vendor>)ven.getResultList();
			System.out.println("Size"+vendorResult.size());
			if(vendorResult.size()>0){
				System.out.println(vendorResult);
				//Adding Values to Data Table
				for(Vendor itr : vendorResult){
					VendorDataBean venObj=new VendorDataBean();
					System.out.println("name"+itr.getAddress());
					venObj.setVenCity(itr.getCity());
					venObj.setVenFirmName(itr.getFirmName());
					venObj.setVenFirmType(itr.getFirmType());
					venObj.setVenPhone(itr.getPhoneNumber());
					venObj.setVenCountry(itr.getCountry().getCountry_Name());
					venObj.setVenAddress(itr.getAddress());
					venObj.setVenPerson(itr.getPersonIncharge());
					vendorList.add(venObj);
				}
			}
			System.out.println("Size"+vendorList.size());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return vendorList;
	}

	/**
	 * This getVendorInfo method is used for get the selected vendor details depending on Firmname view to Data table
	 * @author Robert Arjun
	 * @date 18-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<Vendor> getVendorInfo(String venFirmName) {
		System.out.println("Inside getVendorInfo in DAO"+venFirmName);
		List<Vendor> venList=null;
		if(venFirmName != null){
			try{
			Query q=null;
			q=entitymanager.createQuery("from Vendor where (firmName=? and status=?)");
			q.setParameter(1, venFirmName);
			q.setParameter(2, "Active");
			venList=(List<Vendor>)q.getResultList();
			System.out.println("venList Size"+venList.size());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return venList;
	}
	
	/**
	 * This editVendor method is used for get the selected vendor details depending on Firmname 
	 * Edit the values
	 * @author Robert Arjun
	 * @date 18-11-2015
	 * @copyright NRG
	 */
	@Override
	@Transactional(value="transactionManager")
	public String editVendor(VendorDataBean vendorDataBean) {
		System.out.println("Inside EditVendor method calling in DAO..."+vendorDataBean.getVenFirmName());
		String status="Fail";
		Query findVenID= null;
		int ven_ID=0;
		System.out.println("Roll Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll"));
		System.out.println("Login User Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"); // checking login roll
		Date date=new Date();
		Timestamp timestamp=new Timestamp(date.getTime());
		try{
			if(vendorDataBean.getVenFirmName() != null){
				findVenID=entitymanager.createQuery("from Vendor where (firmName=? and status=? and phoneNumber=?)");
				findVenID.setParameter(1, vendorDataBean.getFirmName());
				findVenID.setParameter(2, "Active");
				findVenID.setParameter(3, vendorDataBean.getPhone());
				List<Vendor> vendorList=(List<Vendor>)findVenID.getResultList();
				System.out.println("vendorList Size"+vendorList.size());
				int Country_ID=0;
				if(vendorList.size() > 0){
					
					ven_ID=vendorList.get(0).getVendor_ID();
					System.out.println("ID "+ven_ID);
					
					Query q=entitymanager.createQuery("from Country where country_Name=?");
					q.setParameter(1, vendorDataBean.getVenCountry());
					List<Country> countryRes=(List<Country>)q.getResultList();
					System.out.println("Country Size"+countryRes.size());
					if(countryRes.size()>0){
					Country_ID=countryRes.get(0).getCountry_ID();
					Vendor vendor=entitymanager.find(Vendor.class, ven_ID);
					vendor.setAddress(vendorDataBean.getVenAddress());
					vendor.setCity(vendorDataBean.getVenCity());
					vendor.setCountry(entitymanager.find(Country.class, Country_ID));// Foreign Key
					vendor.setEmail(vendorDataBean.getVenEmail());
					vendor.setFaxNumber(vendorDataBean.getVenFaxNO());
					vendor.setFirmType(vendorDataBean.getVenFirmType());
					vendor.setEditLoginStatus(login);
					vendor.setNatureBusiness(vendorDataBean.getVenNature());
					vendor.setNote(vendorDataBean.getVenNote());
					vendor.setPersonIncharge(vendorDataBean.getVenPerson());
					vendor.setPhoneNumber(vendorDataBean.getVenPhone());
					vendor.setStandards(vendorDataBean.getVenStandard());
					vendor.setEditDate(date);
					vendor.setTelephoneNumber(vendorDataBean.getVenTelephone());
					vendor.setTaxNumber(vendorDataBean.getVenTaxNO());
					vendor.setEditStatus("Edited");
					vendor.setTelephoneNo2(vendorDataBean.getVenTelephone1());
					vendor.setAccountNo(vendorDataBean.getVenAccountNo());
					vendor.setBankName(vendorDataBean.getVenBankName());
					vendor.setPhoneNumber2(vendorDataBean.getVenPhone1());
					vendor.setTelephoneNo2(vendorDataBean.getVenTelephone1());
					vendor.setPersonIncharge2(vendorDataBean.getVenPerson1());
					vendor.setState(vendorDataBean.getVenState());
					vendor.setWebAddress(vendorDataBean.getVenWeb());
					vendor.setFirmName(vendorDataBean.getVenFirmName());
					entitymanager.merge(vendor); // Merge the values
				}
				}
			}
			status="Success";
		}catch(Exception e){
			
		}
		System.out.println(status);
		return status;
	}

	/**
	 * This deleteVendor method is used for get the selected vendor details depending on Firmname 
	 * Delete  the values - Only change the status
	 * @author Robert Arjun
	 * @date 18-11-2015
	 * @copyright NRG
	 */
	@Override
	@Transactional(value="transactionManager")
	public String deleteVendor(VendorDataBean vendorDataBean) {
		String status="Fail";
		Query findVenID= null;
		int ven_ID=0;
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
		Date date=new Date();
		try{
		System.out.println("Inside deleteVendor methood calling in DAO");
		System.out.println(vendorDataBean.getVenFirmName1());
		System.out.println(vendorDataBean.getVenFirmName());
		if(vendorDataBean.getVenFirmName() != null){
			findVenID=entitymanager.createQuery("from Vendor where (firmName=? and status=? and phoneNumber=?)");
			findVenID.setParameter(1, vendorDataBean.getVenFirmName());
			findVenID.setParameter(2, "Active");
			findVenID.setParameter(3, vendorDataBean.getVenPhone());
			List<Vendor> venList=(List<Vendor>)findVenID.getResultList();
			System.out.println("vendorList Size"+venList.size());
			int Country_ID=0;
			if(venList.size() > 0){
				
				ven_ID=venList.get(0).getVendor_ID();
				System.out.println("ID "+ven_ID);
				Vendor vendor=entitymanager.find(Vendor.class, ven_ID);// Primary Key
				vendor.setStatus("De-Active"); // Status Changed 
				vendor.setEditStatus("Removed");
				vendor.setEditLoginStatus(login);
				vendor.setEditDate(date);
				entitymanager.merge(vendor);
			}
			status="Success";
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
		
	}

	/**
	 * This searchByCityName method is used for get the selected vendor details depending on CityName view to Data table
	 * @author Robert Arjun
	 * @date 18-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<VendorDataBean> searchByCityName(VendorDataBean vendorDataBean) {
		
		List<VendorDataBean> vendorList=null;
		System.out.println("Inside searchByCityName in DAO method calling");
		System.out.println("Firm Name"+vendorDataBean.getVenCity());
		if(vendorDataBean.getVenFirmName() !=null){
			try{
			Query ven=null;
			vendorList=new ArrayList<VendorDataBean>();
			ven=entitymanager.createQuery("from Vendor where (city Like ?  and status=?)");
			ven.setParameter(1, "%"+vendorDataBean.getVenCity()+"%"); // Like Search
			ven.setParameter(2, "Active");
			List<Vendor> vendorResult=(List<Vendor>)ven.getResultList();
			System.out.println("Size"+vendorResult.size());
			if(vendorResult.size()>0){
				System.out.println(vendorResult);
				
				//Adding Values to Data Table 
				
				for(Vendor itr : vendorResult){
					VendorDataBean venObj=new VendorDataBean();
					System.out.println("name"+itr.getAddress());
					venObj.setVenCity(itr.getCity());
					venObj.setVenFirmName(itr.getFirmName());
					venObj.setVenFirmType(itr.getFirmType());
					venObj.setVenPhone(itr.getPhoneNumber());
					venObj.setVenCountry(itr.getCountry().getCountry_Name());
					venObj.setVenAddress(itr.getAddress());
					venObj.setVenPerson(itr.getPersonIncharge());
					vendorList.add(venObj);
				}
			}
			System.out.println("Size"+vendorList.size());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return vendorList;
	}

	/**
	 * This getCountryList method is used for get the Vendor list  DB to JSF
	 * @author Robert Arjun
	 * @date 19-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<String> getVendorList() {
		List<String>venList=null;
		try{
			System.out.println("Inside getVendorList in DAO");
			Query ven=null;
			ven=entitymanager.createQuery("select firmName from Vendor where status='Active'");
			venList=(List<String>)ven.getResultList();
			System.out.println("List Size"+venList.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		return venList;
	}
	
	/**
	 * This insertProduct method is used for insert the values to DB
	 * @author Robert Arjun
	 * @date 19-11-2015
	 * @copyright NRG
	 */
	
	@Override
	@Transactional(value="transactionManager")
	public String insertProduct(ProductDataBean productDataBean) {
		
		System.out.println("Inside insertProduct method calling...");
		String status="Fail";
		Query getVendorQuery=null;
		
		Date d= new Date();
		Timestamp t=new Timestamp(d.getTime());
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
		int venID=0;
		int pName = 0; int pCode = 0;int pName1 = 0; int pCode1 = 0;
		try{
			// Check Product Name Exsist or Not
			if(productDataBean.getProdProductName() != null && productDataBean.getProdProductCode() != null){
				
				getVendorQuery=entitymanager.createQuery("from Vendor where firmName=?");
				getVendorQuery.setParameter(1, productDataBean.getProdVendor());
				List<Vendor> venList=(List<Vendor>)getVendorQuery.getResultList();
				if (venList.size()>0) {
					venID=venList.get(0).getVendor_ID();
				}
				if(productDataBean.getProdCategory() != null){
					if(productDataBean.getProdCategory().equalsIgnoreCase("Raw Material")){
						System.out.println("Inside Raw Material Conditions");
						if(venID > 0)
						{
							pName=searchProdectName1(productDataBean.getProdProductName());
							pCode=searchProductCode1(productDataBean.getProdProductCode());
							pName1=searchProdectName(productDataBean.getProdProductName());
							pCode1=searchProductCode(productDataBean.getProdProductCode());
							if(pName > 0 || pCode > 0 || pName1 > 0 || pCode1 > 0){
								if(pName > 0 ){
								status="ExsistName"; // Set the Error Message to JSF 
								}else if(pCode > 0){
									status="ExsistCode"; // Set the Error Message to JSF 
								}else if(pName1 > 0){
									status="ExsistName"; // Set the Error Message to JSF 
								}else{
									status="ExsistCode"; // Set the Error Message to JSF 
								}
							}else{
									RawMaterial raw = new  RawMaterial();
									raw.setActualPrice(productDataBean.getProdActualPrice());
									raw.setBrand(productDataBean.getProdBrand());
									raw.setCategory(productDataBean.getProdCategory());
									raw.setColor(productDataBean.getProdColor());
									raw.setDescription(productDataBean.getProdDescription());
									raw.setEditStatus("Not Editted");
									raw.setMarketPrice(productDataBean.getProdMarketPrice());
									raw.setProductCode(productDataBean.getProdProductCode());
									raw.setProductName(productDataBean.getProdProductName());
									raw.setProductRegDate(d);
									raw.setProductRegTime(t);
									raw.setSize(productDataBean.getProdSize());
									raw.setStatus("Active");
									raw.setSubcategory1_ID(productDataBean.getProRawCategory());
									raw.setSubcategory2_ID(productDataBean.getProRawCategory1());
									raw.setLoginStatus(login);
									raw.setUnit(productDataBean.getProdUnit());
									raw.setVendor(entitymanager.find(Vendor.class, venID)); //foreign Key 
									raw.setCategoryStatus("RawType");
									entitymanager.persist(raw);
									status="Inserted";
							}
						}
					}else if(productDataBean.getProdCategory().equalsIgnoreCase("Product Trading")){
						System.out.println("Inside Product Trading Conditions");

						if(venID > 0){
						// if Category is not a Raw Material insert CategoryStatus=No RawType to product Table
							pName=searchProdectName1(productDataBean.getProdProductName());
							pCode=searchProductCode1(productDataBean.getProdProductCode());
							pName1=searchProdectName(productDataBean.getProdProductName());
							pCode1=searchProductCode(productDataBean.getProdProductCode());
							if(pName > 0 || pCode > 0 || pName1 > 0 || pCode1 > 0){
								if(pName > 0 ){
								status="ExsistName"; // Set the Error Message to JSF 
								}else if(pCode > 0){
									status="ExsistCode"; // Set the Error Message to JSF 
								}else if(pName1 > 0){
									status="ExsistName"; // Set the Error Message to JSF 
								}else{
									status="ExsistCode"; // Set the Error Message to JSF 
								}
							}else{
									Product product = new Product();
									product.setActualPrice(productDataBean.getProdActualPrice());
									product.setBrand(productDataBean.getProdBrand());
									product.setCategory(productDataBean.getProdCategory());
									product.setColor(productDataBean.getProdColor());
									product.setDescription(productDataBean.getProdDescription());
									product.setEditStatus("Not Editted");
									product.setMarketPrice(productDataBean.getProdMarketPrice());
									product.setProductCode(productDataBean.getProdProductCode());
									product.setProductName(productDataBean.getProdProductName());
									product.setProductRegDate(d);
									product.setProductRegTime(t);
									product.setSize(productDataBean.getProdSize());
									product.setStatus("Active");
									product.setLoginStatus(login);
									product.setUnit(productDataBean.getProdUnit());
									product.setVendor(entitymanager.find(Vendor.class, venID));
									product.setCategoryStatus("TradingType");
									entitymanager.persist(product);
									status="Inserted";
							}
						}
					}else if(productDataBean.getProdCategory().equalsIgnoreCase("Project")){
						System.out.println("Inside Project Conditions");
						pName=searchProdectName1(productDataBean.getProdProductName());
						pCode=searchProductCode1(productDataBean.getProdProductCode());
						pName1=searchProdectName(productDataBean.getProdProductName());
						pCode1=searchProductCode(productDataBean.getProdProductCode());
						if(pName > 0 || pCode > 0 || pName1 > 0 || pCode1 > 0){
							if(pName > 0 ){
							status="ExsistName"; // Set the Error Message to JSF 
							}else if(pCode > 0){
								status="ExsistCode"; // Set the Error Message to JSF 
							}else if(pName1 > 0){
								status="ExsistName"; // Set the Error Message to JSF 
							}else{
								status="ExsistCode"; // Set the Error Message to JSF 
							}
						}else{
								Product product = new Product();
								product.setActualPrice(productDataBean.getProdActualPrice());
								product.setBrand(productDataBean.getProdBrand());
								product.setCategory(productDataBean.getProdCategory());
								product.setColor(productDataBean.getProdColor());
								product.setDescription(productDataBean.getProdDescription());
								product.setEditStatus("Not Editted");
								product.setMarketPrice(productDataBean.getProdMarketPrice());
								product.setProductCode(productDataBean.getProdProductCode());
								product.setProductName(productDataBean.getProdProductName());
								product.setProductRegDate(d);
								product.setProductRegTime(t);
								product.setSize(productDataBean.getProdSize());
								product.setStatus("Active");
								product.setLoginStatus(login);
								product.setUnit(productDataBean.getProdUnit());
								product.setCategoryStatus("ProjectType");
								entitymanager.persist(product);
								status="Inserted";
						}
					}else{
						System.out.println("Inside Project Conditions");
						if(venID > 0){
						pName=searchProdectName1(productDataBean.getProdProductName());
						pCode=searchProductCode1(productDataBean.getProdProductCode());
						pName1=searchProdectName(productDataBean.getProdProductName());
						pCode1=searchProductCode(productDataBean.getProdProductCode());
						if(pName > 0 || pCode > 0 || pName1 > 0 || pCode1 > 0){
							if(pName > 0 ){
							status="ExsistName"; // Set the Error Message to JSF 
							}else if(pCode > 0){
								status="ExsistCode"; // Set the Error Message to JSF 
							}else if(pName1 > 0){
								status="ExsistName"; // Set the Error Message to JSF 
							}else{
								status="ExsistCode"; // Set the Error Message to JSF 
							}
						}else{
								RawMaterial raw = new  RawMaterial();
								raw.setActualPrice(productDataBean.getProdActualPrice());
								raw.setBrand(productDataBean.getProdBrand());
								raw.setCategory(productDataBean.getProdCategory());
								raw.setColor(productDataBean.getProdColor());
								raw.setDescription(productDataBean.getProdDescription());
								raw.setEditStatus("Not Editted");
								raw.setMarketPrice(productDataBean.getProdMarketPrice());
								raw.setProductCode(productDataBean.getProdProductCode());
								raw.setProductName(productDataBean.getProdProductName());
								raw.setProductRegDate(d);
								raw.setProductRegTime(t);
								raw.setSubcategory1_ID(productDataBean.getProdSerCategory1());
								raw.setSubcategory2_ID(productDataBean.getProdSerCategory2());
								raw.setSize(productDataBean.getProdSize());
								raw.setStatus("Active");
								raw.setLoginStatus(login);
								raw.setUnit(productDataBean.getProdUnit());
								raw.setVendor(entitymanager.find(Vendor.class, venID)); //foreign Key 
								raw.setCategoryStatus("ServiceType");
								entitymanager.persist(raw);
								status="Inserted";
						}
						}
					}
					
				}
					
				}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}

	private int searchProductCode1(String prodProductCode) {
		int res = 0;
		Query findQuery1=null;
		try{
			if(prodProductCode != null){
				findQuery1=entitymanager.createQuery("from RawMaterial where (productCode=? and status=?)");
				findQuery1.setParameter(1, prodProductCode);
				findQuery1.setParameter(2, "Active");
				List<Product> codeList=(List<Product>)findQuery1.getResultList();
				res=codeList.size();
				System.out.println("Result Size"+res);
			}
		}catch (Exception e) {
				e.printStackTrace();
			}
		return res;
	}

	private int searchProdectName1(String prodProductName) {
		int res = 0;
		Query findQuery2=null;
		try{
			if(prodProductName != null){
				findQuery2=entitymanager.createQuery("from RawMaterial where (productName=? and status=?)");
				findQuery2.setParameter(1, prodProductName);
				findQuery2.setParameter(2, "Active");
				List<Product> codeList=(List<Product>)findQuery2.getResultList();
				res=codeList.size();
				System.out.println("Result1 Size"+res);
			}
		}catch (Exception e) {
				e.printStackTrace();
			}
		return res;
	}

	private int searchProductCode(String prodProductCode) {
		int res = 0;
		Query findQuery1=null;
		try{
			if(prodProductCode != null){
				// Check Product Code Exsist or Not
			findQuery1=entitymanager.createQuery("from Product where (productCode=? and status=?)");
			findQuery1.setParameter(1, prodProductCode);
			findQuery1.setParameter(2, "Active");
			List<Product> codeList=(List<Product>)findQuery1.getResultList();
			res=codeList.size();
			System.out.println("Result Size"+res);
			}
			
		}catch(Exception e){
			
		}
		return res;
	}

	private int searchProdectName(String prodProductName) {
		Query findQuery2=null;
		int res1 = 0;
		try{
			if(prodProductName != null){
				findQuery2=entitymanager.createQuery("from Product where (productName=? and status=?)");
				findQuery2.setParameter(1, prodProductName);
				findQuery2.setParameter(2, "Active");
				List<Product> productList=(List<Product>)findQuery2.getResultList();
				res1=productList.size();
				System.out.println("Result1 Size"+res1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return res1;
	}

	/**
	 * This method used for get the values from DB set the values to JSF
	 * @author Robert Arjun
	 * @date 20-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<ProductDataBean> getProductList(ProductDataBean productDataBean) {
		List<ProductDataBean> productList=null;
		List<ProductDataBean> productList1= null;
		List<ProductDataBean> prodAll =null;
		Query proList=null; Query proList1=null;
		try{
			System.out.println("Inside getProductList method is calling");
			System.out.println("Product Name"+productDataBean.getProdProductName1());
			if(productDataBean.getProdProductName1() != null){
			proList=entitymanager.createQuery("from Product where (productName Like ? and status=?)");
			proList.setParameter(1, "%"+productDataBean.getProdProductName1()+"%");
			proList.setParameter(2, "Active");
			List<Product> result=(List<Product>)proList.getResultList();
			System.out.println("Checking Size "+result.size());
			productList= new ArrayList<ProductDataBean>();
			if(result.size()>0){
				
				for(Product product : result){
					ProductDataBean pro = new ProductDataBean();
					pro.setProdCategory(product.getCategory());
					pro.setProdMarketPrice(product.getMarketPrice());
					pro.setProdProductCode(product.getProductCode());
					pro.setProdProductName(product.getProductName());
					pro.setProdActualPrice(product.getActualPrice());
					pro.setProdBrand(product.getBrand());
					productList.add(pro);
				}
			}
			proList1=entitymanager.createQuery("from RawMaterial where (productName Like ? and status=?)");
			proList1.setParameter(1, "%"+productDataBean.getProdProductName1()+"%");
			proList1.setParameter(2, "Active");
			List<RawMaterial> res = (List<RawMaterial>)proList1.getResultList();
			System.out.println("Checking Size1 "+res.size());
			if(res.size() > 0){
				productList1=new ArrayList<ProductDataBean>();
				for(RawMaterial raw : res){
					ProductDataBean pro1 = new ProductDataBean();
					pro1.setProdCategory(raw.getCategory());
					pro1.setProdMarketPrice(raw.getMarketPrice());
					pro1.setProdProductCode(raw.getProductCode());
					pro1.setProdProductName(raw.getProductName());
					pro1.setProdActualPrice(raw.getActualPrice());
					pro1.setProdBrand(raw.getBrand());
					productList1.add(pro1);
				}
			}
			prodAll = new ArrayList<ProductDataBean>();
			if(productList != null){
			prodAll.addAll(productList);
			}if(productList1 != null){
			prodAll.addAll(productList1);
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return prodAll;
	}

	/**
	 * This method is used get product List depending on product name
	 * @author Robert Arjun
	 * @date 20-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<Product> getProductListSingle(String prodProductName) {
		System.out.println("Inside getProductListSingle method calling...");
		
		List<Product> productList=null;
		try{
			if(prodProductName != null){
				
				Query q=entitymanager.createQuery("from Product where (productName=? and status=?)");
				q.setParameter(1, prodProductName);
				q.setParameter(2, "Active");
				productList=(List<Product>)q.getResultList();
				System.out.println("productList Size"+productList.size());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return productList;
	}

	@Override
	public List<RawMaterial> getProductListSingle1(String prodProductName) {
		System.out.println("Inside getProductListSingle method calling...");
		List<RawMaterial> rawList=null;
		try{
			if(prodProductName != null){
				
				Query q=entitymanager.createQuery("from RawMaterial where (productName=? and status=?)");
				q.setParameter(1, prodProductName);
				q.setParameter(2, "Active");
				rawList=(List<RawMaterial>)q.getResultList();
				System.out.println("productList Size"+rawList.size());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return rawList;
	}

	
	/**
	 * This method is used get product List depending on product name when we click view button
	 * @author Robert Arjun
	 * @date 20-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<Product> getProductInfo(ProductDataBean productDataBean) {
System.out.println("Inside getProductInfo method calling...");
		
		List<Product> productList=null;
		try{
			
			if(productDataBean.getProdCategory() != null){
				
					Query q=entitymanager.createQuery("from Product where (productName=? and status=?)");
					q.setParameter(1, productDataBean.getProdProductName());
					q.setParameter(2, "Active");
					productList=(List<Product>)q.getResultList();
				}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return productList;
	}
	
	/**
	 * This method is used Edit product depending on product name when we click edit button
	 * @author Robert Arjun
	 * @date 20-11-2015
	 * @copyright NRG
	 */
	@Override
	@Transactional(value="transactionManager")
	public String editProduct(ProductDataBean productDataBean) {
		String status="Failed";
		
		try{
			System.out.println("Inside editProduct method calling...");
			int prodID=0;
			int venID=0;
			Date date=new Date();int raw_ID=0; 
			String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
			if(productDataBean.getProdProductName() != null && productDataBean.getProdCategory() != null){
				if(productDataBean.getProdCategory().equalsIgnoreCase("Raw Material")){
					raw_ID=getRawMeterialID(productDataBean.getProdProductName());
					venID=getVendorID(productDataBean.getProdVendor());
					if(raw_ID > 0 && venID > 0){
					RawMaterial raw = entitymanager.find(RawMaterial.class, raw_ID);
					raw.setActualPrice(productDataBean.getProdActualPrice());
					raw.setBrand(productDataBean.getProdBrand());
					raw.setCategory(productDataBean.getProdCategory());
					raw.setColor(productDataBean.getProdColor());
					raw.setDescription(productDataBean.getProdDescription());
					raw.setEditStatus("Edited");
					raw.setMarketPrice(productDataBean.getProdMarketPrice());
					raw.setSize(productDataBean.getProdSize());
					raw.setSubcategory1_ID(productDataBean.getProRawCategory());
					raw.setSubcategory2_ID(productDataBean.getProRawCategory1());
					raw.setUnit(productDataBean.getProdUnit());
					raw.setVendor(entitymanager.find(Vendor.class, venID)); //foreign Key 
					raw.setCategoryStatus("RawType");
					raw.setEditDate(date);
					raw.setEditLoginStatus(login);
					entitymanager.merge(raw);
					status="Merged";
					}
				}else if(productDataBean.getProdCategory().equalsIgnoreCase("Product Trading")){
					prodID=getProductID(productDataBean.getProdProductName());
					venID=getVendorID(productDataBean.getProdVendor());
					if(prodID > 0 && venID > 0){
						Product product = entitymanager.find(Product.class, prodID);
						product.setActualPrice(productDataBean.getProdActualPrice());
						product.setBrand(productDataBean.getProdBrand());
						product.setCategory(productDataBean.getProdCategory());
						product.setColor(productDataBean.getProdColor());
						product.setDescription(productDataBean.getProdDescription());
						product.setEditStatus("Editted");
						product.setMarketPrice(productDataBean.getProdMarketPrice());
						product.setSize(productDataBean.getProdSize());
						product.setSubcategory1_ID("");
						product.setSubcategory2_ID("");
						product.setEditLoginStatus(login);
						product.setUnit(productDataBean.getProdUnit());
						product.setVendor(entitymanager.find(Vendor.class, venID));
						product.setCategoryStatus("No RawType");
						product.setEditDate(date);
						entitymanager.merge(product);
						status="Merged";
					}
				}else if(productDataBean.getProdCategory().equalsIgnoreCase("Project")){
					prodID=getProductID(productDataBean.getProdProductName());
					if(prodID > 0){
						Product product = entitymanager.find(Product.class, prodID);
						product.setActualPrice(productDataBean.getProdActualPrice());
						product.setBrand(productDataBean.getProdBrand());
						product.setCategory(productDataBean.getProdCategory());
						product.setColor(productDataBean.getProdColor());
						product.setDescription(productDataBean.getProdDescription());
						product.setEditStatus("Editted");
						product.setMarketPrice(productDataBean.getProdMarketPrice());
						product.setSize(productDataBean.getProdSize());
						product.setSubcategory1_ID("");
						product.setSubcategory2_ID("");
						product.setEditLoginStatus(login);
						product.setUnit(productDataBean.getProdUnit());
						product.setVendor(null);
						product.setCategoryStatus("ProjectType");
						product.setEditDate(date);
						entitymanager.merge(product);
						status="Merged";
					}
				}else{
					raw_ID=getRawMeterialID(productDataBean.getProdProductName());
					venID=getVendorID(productDataBean.getProdVendor());
					if(raw_ID > 0 && venID > 0){
						RawMaterial raw = entitymanager.find(RawMaterial.class, raw_ID);
						raw.setActualPrice(productDataBean.getProdActualPrice());
						raw.setBrand(productDataBean.getProdBrand());
						raw.setCategory(productDataBean.getProdCategory());
						raw.setColor(productDataBean.getProdColor());
						raw.setDescription(productDataBean.getProdDescription());
						raw.setEditStatus("Editted");
						raw.setMarketPrice(productDataBean.getProdMarketPrice());
						raw.setSize(productDataBean.getProdSize());
						raw.setSubcategory1_ID(productDataBean.getProdSerCategory1());
						raw.setSubcategory2_ID(productDataBean.getProdSerCategory2());
						raw.setEditLoginStatus(login);
						raw.setUnit(productDataBean.getProdUnit());
						raw.setVendor(entitymanager.find(Vendor.class, venID)); //foreign Key 
						raw.setCategoryStatus("ServiceType");
						raw.setEditDate(date);
						entitymanager.merge(raw);
						status="Merged";
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * This method is used return Product_ID
	 * @author Robert Arjun
	 * @date 26-11-2015
	 * @copyright NRG
	 */
	private int getProductID(String prodProductName) {
		int result=0;Query q=null;
		q=entitymanager.createQuery("from Product where (productName=? and status=?)");
		q.setParameter(1, prodProductName);
		q.setParameter(2, "Active");
		List<Product> res=(List<Product>)q.getResultList();
		if (res.size() > 0) {
			result=res.get(0).getProduct_ID();
		}
		return result;
	}
	/**
	 * This method is used return Vendor_ID
	 * @author Robert Arjun
	 * @date 26-11-2015
	 * @copyright NRG
	 */
	private int getVendorID(String prodVendor) {
		int result=0;Query q = null;
		try{
			q=entitymanager.createQuery("from Vendor where firmName=?");
			q.setParameter(1, prodVendor);
			List<Vendor> venList=(List<Vendor>)q.getResultList();
			if (venList.size()>0) {
				result=venList.get(0).getVendor_ID();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * This method is used return RawMeterialID
	 * @author Robert Arjun
	 * @date 26-11-2015
	 * @copyright NRG
	 */
	private int getRawMeterialID(String prodProductName) {
		int result=0;
		Query rawQuery=null;
		try{
			rawQuery=entitymanager.createQuery("from RawMaterial where (productName=? and status=?)");
			rawQuery.setParameter(1, prodProductName);
			rawQuery.setParameter(2, "Active");
			List<RawMaterial> resList=(List<RawMaterial>)rawQuery.getResultList();
			if(resList.size() > 0){
				result=resList.get(0).getRaw_ID();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method is used delete product depending on product name when we click delete button
	 * Only change the product status
	 * @author Robert Arjun
	 * @date 20-11-2015
	 * @copyright NRG
	 */
	@Override
	@Transactional(value="transactionManager")
	public String productDelete(ProductDataBean productDataBean) {
		System.out.println("Inside productDelete method calling in DAO");
		String status="Fail";
		int prodID=0;
		Date date=new Date();
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
		int raw_ID=0;
		try{
			if(productDataBean.getProdProductName() != null){
				if(productDataBean.getProdCategory().equalsIgnoreCase("Raw Material")||productDataBean.getProdCategory().equalsIgnoreCase("Service")){
				raw_ID=getRawMeterialID(productDataBean.getProdProductName());
				if(raw_ID > 0){
				RawMaterial raw = entitymanager.find(RawMaterial.class, raw_ID);
				raw.setEditDate(date);
				raw.setEditLoginStatus(login);
				raw.setEditStatus("Removed");
				raw.setStatus("De-Activate");
				entitymanager.merge(raw);
				status="Success";
				}
				}else{
					prodID=getProductID(productDataBean.getProdProductName());
					if(prodID > 0){
					Product product = entitymanager.find(Product.class, prodID);
					product.setEditDate(date);
					product.setEditLoginStatus(login);
					product.setEditStatus("Removed");
					product.setStatus("De-Activate");
					entitymanager.merge(product);
					status="Success";
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	/**
	 * This method is used view product depending on Category
	 * @author Robert Arjun
	 * @date 20-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<ProductDataBean> getProductListByCategory(ProductDataBean productDataBean) {
		List<ProductDataBean> productList=null;
		List<ProductDataBean> productList1=null;
		List<ProductDataBean> prodAll=null;
		Query proList=null;Query proList1=null;
		try{
			System.out.println("Inside getProductListByCategory method is calling");
			System.out.println("getProdCategory1 Name"+productDataBean.getProdCategory1());
			if(productDataBean.getProdCategory1() != null){
				if(productDataBean.getProdCategory1().equalsIgnoreCase("Raw Material") || productDataBean.getProdCategory1().equalsIgnoreCase("Service")){
					proList1=entitymanager.createQuery("from RawMaterial where (category=? and status=?)");
					proList1.setParameter(1, productDataBean.getProdCategory1());
					proList1.setParameter(2, "Active");
					List<RawMaterial> result1=(List<RawMaterial>)proList1.getResultList();
					System.out.println("Checking1 Size "+result1.size());
					productList1= new ArrayList<ProductDataBean>();
					if(result1.size() > 0){
						for(RawMaterial raw : result1){
							ProductDataBean pro1 = new ProductDataBean();
							pro1.setProdCategory(raw.getCategory());
							pro1.setProdMarketPrice(raw.getMarketPrice());
							pro1.setProdProductCode(raw.getProductCode());
							pro1.setProdProductName(raw.getProductName());
							pro1.setProdActualPrice(raw.getActualPrice());
							pro1.setProdBrand(raw.getBrand());
							productList1.add(pro1);
						}
					}
				}else
				{
			proList=entitymanager.createQuery("from Product where (category=? and status=?)");
			proList.setParameter(1, productDataBean.getProdCategory1());
			proList.setParameter(2, "Active");
			List<Product> result=(List<Product>)proList.getResultList();
			System.out.println("Checking Size "+result.size());
			productList= new ArrayList<ProductDataBean>();
			if(result.size()>0){
				
				for(Product product : result){
					ProductDataBean pro = new ProductDataBean();
					pro.setProdCategory(product.getCategory());
					pro.setProdMarketPrice(product.getMarketPrice());
					pro.setProdProductCode(product.getProductCode());
					pro.setProdProductName(product.getProductName());
					pro.setProdActualPrice(product.getActualPrice());
					pro.setProdBrand(product.getBrand());
					productList.add(pro);
				}
			}
			}
				prodAll = new ArrayList<ProductDataBean>();
				if(productList1 != null){
				prodAll.addAll(productList1);
				}
				if(productList != null){
				prodAll.addAll(productList);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return prodAll;
	}
	/**
	 * This method is used get Raw material name 
	 * @author Robert Arjun
	 * @date 28-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<String> productNameList(String category) {
		
		List<String> productname=null;
		try{
		Query q=null;
		q=entitymanager.createQuery("select productName from RawMaterial where (category=? and status=?)");
		q.setParameter(1, category);
		q.setParameter(2, "Active");
		productname=(List<String>)q.getResultList();
		System.out.println("Checking Size "+productname.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return productname;
	}
	/**
	 * This method is used insert Product Limit
	 * @author Robert Arjun
	 * @date 28-11-2015
	 * @copyright NRG
	 */
	@Override
	@Transactional(value="transactionManager")
	public String insertLimit(LimitDataBean limitDataBean) {
		System.out.println("insertLimit method calling in DAO"+limitDataBean.getLimProductCategory());
		String status="Fail";
		Date date=new Date();
		Timestamp timestamp=new Timestamp(date.getTime());
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");

		int raw_ID=0; int prodID=0;int limit_ID=0;
		Query q=null;
		try{
			if(limitDataBean.getLimProductCategory() != null){
				
				prodID=getProductID(limitDataBean.getLimitProjectName());
				if(limitDataBean.getLimProductCategory().equalsIgnoreCase("Raw Material")){
					raw_ID=getRawMeterialID(limitDataBean.getLimitProductName());
				}else{
					raw_ID=getRawMeterialID(limitDataBean.getLimitServiceName());
				}
				limit_ID=getLimitID(limitDataBean.getLimitProjectName(),raw_ID);
				if(limit_ID > 0){
					status="Exsist";
				}else{
					if(raw_ID > 0 && prodID > 0){
						ProductLimit limit=new ProductLimit();
						limit.setDescription(limitDataBean.getLimitDescription());
						limit.setLimitSize(limitDataBean.getLimitLimitSize());
						limit.setProduct(entitymanager.find(Product.class, prodID));
						limit.setRawMaterial(entitymanager.find(RawMaterial.class, raw_ID));
						limit.setRegDate(date);
						limit.setRegTime(timestamp);
						limit.setEditStatus("Not Edited");
						limit.setStatus("Active");
						limit.setLoginStatus(login);
						entitymanager.persist(limit);
						status="Inserted";
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	/**
	 * This method is used return the product name is present or not 
	 * @author Robert Arjun
	 * @date 30-11-2015
	 * @copyright NRG
	 */
	private int getLimitID(String limitProductName,int raw_ID) {
		int result=0;int result1=0;
		
		Query prodCheck=null;
		Query productLimit=null;
		try{
			if(limitProductName != null){
			prodCheck=entitymanager.createQuery("from Product where (productName=? and status='Active')");
			prodCheck.setParameter(1, limitProductName);
			List<Product> prod=(List<Product>)prodCheck.getResultList();
			if(prod.size() > 0){
				result1=prod.get(0).getProduct_ID();
				productLimit=entitymanager.createQuery("from ProductLimit where product_ID=? and raw_id=? and status='Active'");
				productLimit.setParameter(1, result1);
				productLimit.setParameter(2, raw_ID);
				List<ProductLimit> lim=(List<ProductLimit>)productLimit.getResultList();
				result=lim.size();
			}
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * This method is used set the value in datatable
	 * @author Robert Arjun
	 * @date 30-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<LimitDataBean> getLimit(String limitProductName1) {
		
		System.out.println("Inside getLimit Method Calling...");
		List<LimitDataBean> viewLimit=null;
		Query productQry=null;
		int prod_ID=0;
		Query limit=null;
		try{
			if(limitProductName1 !=null){
				productQry=entitymanager.createQuery("from Product where (productName Like ? and status='Active')");
				productQry.setParameter(1, "%"+limitProductName1+"%");
				List<Product> res=(List<Product>)productQry.getResultList();
				System.out.println("Result "+res.size());
				if(res.size() > 0){
					viewLimit=new ArrayList<LimitDataBean>();
					for(int i=0;i<res.size();i++){
						prod_ID=res.get(i).getProduct_ID();
						limit=entitymanager.createQuery("from ProductLimit where (product_ID=? and status='Active')");
						limit.setParameter(1, prod_ID);
						List<ProductLimit> res1=(List<ProductLimit>)limit.getResultList();
						if(res1.size() > 0){
							for(ProductLimit lim : res1){
								LimitDataBean limitObject= new LimitDataBean();
								limitObject.setLimitDescription(lim.getDescription());
								limitObject.setLimitProjectName(lim.getProduct().getProductName());
								limitObject.setLimitProductName(lim.getRawMaterial().getProductName());
								limitObject.setLimitRawCategory1(lim.getRawMaterial().getSubcategory1_ID());
								limitObject.setLimitRawCategoory2(lim.getRawMaterial().getSubcategory2_ID());
								limitObject.setLimitLimitSize(lim.getLimitSize());
								viewLimit.add(limitObject);
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return viewLimit;
	}

	/**
	 * This method is used set the value in datatable
	 * Only change the product status
	 * @author Robert Arjun
	 * @date 30-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<RawMaterial> getRawCategoryEdit(ProductDataBean productDataBean) {
		List<RawMaterial> rawList=null;
		try{
			
			if(productDataBean.getProdCategory() != null){
				
					Query q=entitymanager.createQuery("from RawMaterial where (productName=? and status=?)");
					q.setParameter(1, productDataBean.getProdProductName());
					q.setParameter(2, "Active");
					rawList=(List<RawMaterial>)q.getResultList();
				}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rawList;
	}
	/**
	 * This method is used set dropdown values list
	 * Only change the product status
	 * @author Robert Arjun
	 * @date 30-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<String> getProjectServiceList(String category) {
		
		System.out.println("Inside getProjectServiceList method calling...");
		Query projectQueery=null;
		Query serviceQuery=null;
		List<String> PoductList=null;
		try{
			if(category != null){
				if(category.equalsIgnoreCase("Project")){
					System.out.println("Inside Project");
					projectQueery=entitymanager.createQuery("select productName from Product where (category = ? and status= ? and categoryStatus='ProjectType')");
					projectQueery.setParameter(1, category);
					projectQueery.setParameter(2, "Active");
					PoductList=(List<String>)projectQueery.getResultList();
					System.out.println("names"+PoductList);
				}else{
					System.out.println("Inside Service");
					projectQueery=entitymanager.createQuery("select productName from Product where (category = ? and status= ? and categoryStatus='ServiceType')");
					projectQueery.setParameter(1, category);
					projectQueery.setParameter(2, "Active");
					PoductList=(List<String>)projectQueery.getResultList();
					System.out.println("names"+PoductList);
			}
			
		}
		}catch(Exception e){
			
		}
		
		return PoductList;
	}

	
	/**
	 * This method is used set output values depending on project name 
	 * Only change the product status
	 * @author Robert Arjun
	 * @date 30-11-2015
	 * @copyright NRG
	 */
	@Override
	public List<ProductLimit> getLimitListByProject(LimitDataBean limitDataBean) {
		
		System.out.println("Inside getLimitListByProject method calling....");
		int result1=0;
		Query productLim=null;Query produ=null;int rawid=0;
		List<ProductLimit> productLimitList= null;
		try{
			if(limitDataBean.getLimitProjectName() != null){
				produ=entitymanager.createQuery("from Product where (productName=? and status='Active')");
				produ.setParameter(1, limitDataBean.getLimitProjectName());
			List<Product> prod=(List<Product>)produ.getResultList();
			if(prod.size() > 0){
				result1=prod.get(0).getProduct_ID();
				productLim=null;
				productLim=entitymanager.createQuery("from RawMaterial where productName=? and status='Active'");
				productLim.setParameter(1, limitDataBean.getLimitProductName());
				List<RawMaterial> rr=(List<RawMaterial>)productLim.getResultList();
				rawid=rr.get(0).getRaw_ID();
				 productLim=entitymanager.createQuery("from ProductLimit where (product_ID=? and status='Active' and raw_id=?)");
				 productLim.setParameter(1, result1);
				 productLim.setParameter(2, rawid);
				 productLimitList=(List<ProductLimit>)productLim.getResultList();
				
			}
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return productLimitList;
	}

	@Override
	public List<ProductLimit> limitListEditByProject(LimitDataBean limitDataBean) {
		System.out.println("Inside getLimitListByProject method calling...."+limitDataBean.getLimitProjectName());
		int result1=0;
		Query productLim=null;Query produ=null;
		List<ProductLimit> productLimitList= null;int rawid=0;
		try{
			if(limitDataBean.getLimitProjectName() != null){
				produ=entitymanager.createQuery("from Product where (productName=? and status='Active')");
				produ.setParameter(1, limitDataBean.getLimitProjectName());
			List<Product> prod=(List<Product>)produ.getResultList();
			if(prod.size() > 0){
				result1=prod.get(0).getProduct_ID();
				productLim=null;
				productLim=entitymanager.createQuery("from RawMaterial where productName=? and status='Active'");
				productLim.setParameter(1, limitDataBean.getLimitProductName());
				List<RawMaterial> rr=(List<RawMaterial>)productLim.getResultList();
				rawid=rr.get(0).getRaw_ID();				
				 productLim=entitymanager.createQuery("from ProductLimit where (product_ID=? and status='Active'  and raw_id=?)");
				 productLim.setParameter(1, result1);
				 productLim.setParameter(2, rawid);
				 productLimitList=(List<ProductLimit>)productLim.getResultList();
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return productLimitList;
	}

	@Override
	@Transactional(value="transactionManager")
	public String EditLimit(LimitDataBean limitDataBean) {
		String status="Fail";
		int limitID=0; int raw_ID=0;
		Date date=new Date();
		Timestamp timestamp=new Timestamp(date.getTime());
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");

		
		try{
			limitID=findLimitID(limitDataBean);
			if(limitDataBean.getLimProductCategory().equalsIgnoreCase("Raw Material")){
			raw_ID=getRawMeterialID(limitDataBean.getLimitProductName());
			}else{
			raw_ID=getRawMeterialID(limitDataBean.getLimitServiceName());
			}
			if(limitID > 0 && raw_ID > 0){
				ProductLimit prpdLimit=entitymanager.find(ProductLimit.class, limitID);
				prpdLimit.setRawMaterial(entitymanager.find(RawMaterial.class, raw_ID));
				prpdLimit.setEditLogin(login);
				prpdLimit.setEditTime(timestamp);
				prpdLimit.setEditDate(date);
				prpdLimit.setDescription(limitDataBean.getLimitDescription());
				prpdLimit.setLimitSize(limitDataBean.getLimitLimitSize());
				prpdLimit.setEditStatus("Edited");
				entitymanager.merge(prpdLimit);
				 status="Pass";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}

	private int findLimitID(LimitDataBean limitDataBean) {
		int result=0;
		int prod_ID=0;
		Query q= null;Query q1= null;int rawid=0;
		try{
			q=entitymanager.createQuery("from Product where (productName=? and status='Active')");
			q.setParameter(1, limitDataBean.getLimitProjectName());
			List<Product> product=(List<Product>)q.getResultList();
			if(product.size() > 0){
				prod_ID=product.get(0).getProduct_ID();
			}
			if(prod_ID>0){
				q=null;
				if(limitDataBean.getStatus().equals("edit"))
				{
					q=entitymanager.createQuery("from RawMaterial where productName=? and status='Active'");
					if(limitDataBean.getLimProductCategory().equalsIgnoreCase("Raw Material")) 
					{
						q.setParameter(1, limitDataBean.getLimitProductName());
						System.out.println("raw name - > "+limitDataBean.getLimitProductName());
					}
					else
					{
						q.setParameter(1, limitDataBean.getLimitServiceName());
						System.out.println("service name - > "+limitDataBean.getLimitServiceName());
					}
				}
				else if(limitDataBean.getStatus().equals("delete"))
				{
					q=entitymanager.createQuery("from RawMaterial where productName=? and status='Active'");
					q.setParameter(1, limitDataBean.getLimitProductName());						
				}				
				List<RawMaterial> rr=(List<RawMaterial>)q.getResultList();
				rawid=rr.get(0).getRaw_ID();
				System.out.println("raw id -- > "+rawid);
				q1=entitymanager.createQuery("from ProductLimit where (product_ID=? and status='Active' and raw_id=?)");
				q1.setParameter(1, prod_ID);
				System.out.println("product id -- . "+prod_ID);
				q1.setParameter(2, rawid);
				List<ProductLimit> limit=(List<ProductLimit>)q1.getResultList();
				if(limit.size() > 0){
					result=limit.get(0).getLimit_ID();
				}
				System.out.println("limit id - . "+result);
			}
			
		}catch(Exception e){
			
		}
		return result;
	}

	@Override
	@Transactional(value="transactionManager")
	public String limitDelete(LimitDataBean limitDataBean) {
		String status="Fail";
		int limitID=0; int raw_ID=0;
		Date date=new Date();
		Timestamp timestamp=new Timestamp(date.getTime());
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");

		
		try{
			limitID=findLimitID(limitDataBean);

			if(limitID > 0){
				ProductLimit prpdLimit=entitymanager.find(ProductLimit.class, limitID);
				prpdLimit.setEditLogin(login);
				prpdLimit.setEditTime(timestamp);
				prpdLimit.setEditDate(date);
				prpdLimit.setStatus("De-Active");
				prpdLimit.setEditStatus("Removed");
				entitymanager.merge(prpdLimit);
				 status="Pass";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public List<LimitDataBean> getLimitByCategory(String limProductCategory1) {
		System.out.println("Inside getLimit Method Calling...");
		List<LimitDataBean> viewLimit=null;
		Query productQry=null;
		int prod_ID=0;
		Query limit=null;
		try{
			if(limProductCategory1 !=null){
				productQry=entitymanager.createQuery("from RawMaterial where (category=? and status='Active')");
				productQry.setParameter(1, limProductCategory1);
				List<RawMaterial> res=(List<RawMaterial>)productQry.getResultList();
				System.out.println("Result "+res.size());
				if(res.size() > 0){
					viewLimit=new ArrayList<LimitDataBean>();
					for(int i=0;i<res.size();i++){
						prod_ID=res.get(i).getRaw_ID();
						limit=entitymanager.createQuery("from ProductLimit where (raw_id=? and status='Active')");
						limit.setParameter(1, prod_ID);
						List<ProductLimit> res1=(List<ProductLimit>)limit.getResultList();
						if(res1.size() > 0){
							for(ProductLimit lim : res1){
								LimitDataBean limitObject= new LimitDataBean();
								limitObject.setLimitDescription(lim.getDescription());
								limitObject.setLimitProjectName(lim.getProduct().getProductName());
								limitObject.setLimitProductName(lim.getRawMaterial().getProductName());
								limitObject.setLimitRawCategory1(lim.getRawMaterial().getSubcategory1_ID());
								limitObject.setLimitRawCategoory2(lim.getRawMaterial().getSubcategory2_ID());
								limitObject.setLimitLimitSize(lim.getLimitSize());
								viewLimit.add(limitObject);
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return viewLimit;
}

	@Transactional(value="transactionManager")
	@Override
	public String insertCustomer(CustomerDataBean customerDataBean) {
		
		System.out.println("Country id"+customerDataBean.getCustCountry()+"Staff "+customerDataBean.getCustStaffId());
		
		String status="fail";
		Date date= new Date();
		Timestamp timestamp=new Timestamp(date.getTime());
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");

		try
		{
			
			Query q=null;
			System.out.println("2");
			q=entitymanager.createQuery("from Country where country_Name=? ");
			System.out.println("3");
			q.setParameter(1, customerDataBean.getCustCountry());
			List<Country> result=new ArrayList<Country>();
			result=q.getResultList();	
			System.out.println("4");
			int countryid=0;
			if(result.size()>0)
			{
				int i=0;
				countryid= result.get(i).getCountry_ID();
				System.out.println("4");
			}
			
			Query qq=null;
			qq=entitymanager.createQuery("from Employee where employee_details_ID=? and status='Active'");
			qq.setParameter(1, customerDataBean.getCustStaffId());
			List<Employee> emp=new ArrayList<Employee>();
			emp=qq.getResultList();
			int emp_id=0;
			if(emp.size()>0)
			{
				emp_id=emp.get(0).getEmployee_ID();
			}
			
			
			
			if(emp_id>0 && countryid>0)
			{
			
			

			Query q1=null;
			q1=entitymanager.createQuery("from Customer where customerPhoneNumber=?");
			q1.setParameter(1, customerDataBean.getCustPhoneNumber());
			List<Customer> res=(List<Customer>) q1.getResultList();
			res=q1.getResultList();
			customerDataBean.setCusphn(res);
			if(res.size()>0)
			{
				status="exist";
				//throw new Exception("Phone Number is already exist");
			}
			else
			{
				Customer customer=new Customer();
				customer.setCustomerName(customerDataBean.getCustCustomerName());
				customer.setCustomerAddress(customerDataBean.getCustShipingAddress());
				customer.setCustomerCity(customerDataBean.getCustCity());
				customer.setCustomerDescription(customerDataBean.getCustNote());
				customer.setCustomerState(customerDataBean.getCustState());
				customer.setCustomerEmail(customerDataBean.getCustEmail());
				customer.setCustomerPhoneNumber(customerDataBean.getCustPhoneNumber());
				System.out.println("=========="+customerDataBean.getCustPhoneNumber());
				customer.setCustomerRegDate(customerDataBean.getCustDate());
				customer.setCustomerTaxNumber(customerDataBean.getCustTaxNO());
				customer.setEditStatus("Not Edited");
				customer.setStatus("Active");
				customer.setCustomerRegDate(date);
				customer.setCustomerRegTime(timestamp);
				customer.setLoginStatus(login);
				/*customer.setEmployee_ID(customerDataBean.getCustStaffId());*/
				customer.setCountry(entitymanager.find(Country.class, countryid));
				customer.setEmployee(entitymanager.find(Employee.class, emp_id));
				
				entitymanager.persist(customer);
				status="inserted";
			}
			
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public List<CustomerDataBean> getCustList(CustomerDataBean customerDataBean) {
		List<CustomerDataBean> custList=null;
		List<CustomerDataBean> custList1=null;
		List<CustomerDataBean> custAll=null;
		Query q1=null;
		Query q2=null;
		Query q3=null;
		
		custAll= new ArrayList<CustomerDataBean>();
		try
		{
			System.out.println("Inside getProductList method is calling");
			if(customerDataBean.getCustCustomerName() != null)
			{
			q1=entitymanager.createQuery("from Customer where (customerName Like ? and status=?)");
			q1.setParameter(1, "%"+customerDataBean.getCustCustomerName()+"%");
			q1.setParameter(2, "Active");
			List<Customer> result=(List<Customer>)q1.getResultList();
			System.out.println("check size=>>>>>>>"+result.size());
			System.out.println("check size=>>>>>>>"+result);
			/*customerDataBean.setCuslist(result);
			System.out.println("customer list -- >"+customerDataBean.getCuslist());
			*/custList= new ArrayList<CustomerDataBean>();
			if(result.size()>0)
				
			{
				for(Customer customer : result)
				{
					CustomerDataBean cust=new CustomerDataBean();
					
					cust.setCustCustomerName(customer.getCustomerName());
					System.out.println("name========"+cust.getCustCustomerName());
					
					cust.setCustPhoneNumber(customer.getCustomerPhoneNumber());
					System.out.println("phno====="+customer.getCustomerPhoneNumber());
					
					cust.setCustCity(customer.getCustomerCity());
					System.out.println("city======="+customer.getCustomerCity());
					
					cust.setCustCountry(result.get(0).getCountry().getCountry_Name());
					System.out.println("country=====>>>>>"+result.get(0).getCountry().getCountry_Name());
				    custList.add(cust);
				}
			}
			
			System.out.println("sizeeeeeeeeeeee"+custList.size());
			if(custList != null)
			{
			custAll.addAll(custList);
			}
			System.out.println("sizeeeeeeeeeeee"+custAll.size());
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return custAll;
	}

	
	
	
	@Override
	public List<Customer> getCustomerInfo(String custCustomerName) {
		// TODO Auto-generated method stub
		System.out.println("Inside getCustomerInfo in DAO"+custCustomerName);
		List<Customer> cusList=null;
		if(custCustomerName != null){
			try{
			Query q=null;
			q=entitymanager.createQuery("from Customer where (customerName=? and status=?)");
			q.setParameter(1, custCustomerName);
			q.setParameter(2, "Active");
			cusList=(List<Customer>)q.getResultList();
			System.out.println("venList Size"+cusList.size());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return cusList;
		
		}

	@Override
	@Transactional(value="transactionManager")
	public String editCustomer(CustomerDataBean customerDataBean) {
	System.out.println("---------------------------------------------");	
System.out.println("Country id"+customerDataBean.getCustCountry()+"Staff "+customerDataBean.getCustStaffId());
		
		String status="fail";
		Date date= new Date();
		Timestamp timestamp=new Timestamp(date.getTime());
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");

		try
		{
			
			Query q=null;
			System.out.println("2");
			q=entitymanager.createQuery("from Country where country_Name=? ");
			System.out.println("3");
			q.setParameter(1, customerDataBean.getCustCountry());
			List<Country> result=new ArrayList<Country>();
			result=q.getResultList();	
			System.out.println("4");
			int countryid=0;
			if(result.size()>0)
			{
				int i=0;
				countryid= result.get(i).getCountry_ID();
				System.out.println("4");
			}
			
			Query qq=null;
			qq=entitymanager.createQuery("from Employee where employee_details_ID=? and status='Active'");
			qq.setParameter(1, customerDataBean.getCustStaffId());
			List<Employee> emp=new ArrayList<Employee>();
			emp=qq.getResultList();
			int emp_id=0;
			if(emp.size()>0)
			{
				emp_id=emp.get(0).getEmployee_ID();
			}
			
			
			
			if(emp_id>0 && countryid>0)
			{
			
			

			Query q1=null;
			q1=entitymanager.createQuery("from Customer where customerPhoneNumber=?");
			q1.setParameter(1, customerDataBean.getCustPhoneNumber());
			List<Customer> res=(List<Customer>) q1.getResultList();
			res=q1.getResultList();
			customerDataBean.setCusphn(res);
			int res_id=0;
			if(res.size()>0)
			{
				res_id=res.get(0).getCustomer_ID();
				status="exist";
				//throw new Exception("Phone Number is already exist");

				Customer customer=entitymanager.find(Customer.class, res_id);
				customer.setCustomerName(customerDataBean.getCustCustomerName());
				customer.setCustomerAddress(customerDataBean.getCustShipingAddress());
				customer.setCustomerCity(customerDataBean.getCustCity());
				customer.setCustomerDescription(customerDataBean.getCustNote());
				customer.setCustomerState(customerDataBean.getCustState());
				customer.setCustomerEmail(customerDataBean.getCustEmail());
				customer.setCustomerPhoneNumber(customerDataBean.getCustPhoneNumber());
				System.out.println("=========="+customerDataBean.getCustPhoneNumber());
				customer.setCustomerRegDate(customerDataBean.getCustDate());
				customer.setCustomerTaxNumber(customerDataBean.getCustTaxNO());
				customer.setEditStatus("Edited");
				
				customer.setCustomerRegDate(date);
				customer.setCustomerRegTime(timestamp);
				customer.setLoginStatus(login);
				/*customer.setEmployee_ID(customerDataBean.getCustStaffId());*/
				customer.setCountry(entitymanager.find(Country.class, countryid));
				customer.setEmployee(entitymanager.find(Employee.class, emp_id));
				
				entitymanager.merge(customer);
				System.out.println("+++++++++++++++++++");
				status="Edited";
			
			}
			else
			{
				
			}
			
		}status="success";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	


	}


	
	@Override
	@Transactional(value="transactionManager")
	public String customerDelete(CustomerDataBean customerDataBean) {
		String status="Fail";
		Query findCusID= null;
		int cus_ID=0;
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
		Date date=new Date();
		try{
		System.out.println("Inside deleteCustomer methood calling in DAO");
		
		if(customerDataBean.getCustCustomerName() != null){
			findCusID=entitymanager.createQuery("from Customer where (customerName=? and status=?)");
			findCusID.setParameter(1, customerDataBean.getCustCustomerName());
			findCusID.setParameter(2, "Active");
			List<Customer> cusList=(List<Customer>)findCusID.getResultList();
			System.out.println("Customer List Size"+cusList.size());
			int Country_ID=0;
			if(cusList.size() > 0){
				
				cus_ID=cusList.get(0).getCustomer_ID();
				System.out.println("ID "+cus_ID);
				Customer customer=entitymanager.find(Customer.class, cus_ID);// Primary Key
				customer.setStatus("De-Active"); // Status Changed 
				customer.setEditStatus("Removed");
				customer.setEditLoginStatus(login);
				customer.setEditDate(date);
				entitymanager.merge(customer);
				System.out.println("+++++++++");
			}
			else
			{
				System.out.println("inside else");
			}
			status="Success";
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
		
		
	}

	@Override
	public List<CustomerDataBean> searchByCustomerName(
			CustomerDataBean customerDataBean) {
		List<CustomerDataBean> customerList=null;
		System.out.println("Inside searchByCustomerName in DAO method calling");
		System.out.println("Customer Name"+customerDataBean.getCustCustomerName());
		if(customerDataBean.getCustCustomerName() !=null){
			try{
			Query q=null;
			customerList=new ArrayList<CustomerDataBean>();
			q=entitymanager.createQuery("from Customer where (customerName=? and status=?)");
			q.setParameter(1, "%"+customerDataBean.getCustCustomerName()+"%");
			q.setParameter(2, "Active");
			List<Customer> custResult=(List<Customer>)q.getResultList();
			System.out.println("Size"+custResult.size());
			if(custResult.size()>0){
				System.out.println(custResult);
				//Adding Values to Data Table
				for(Customer cus : custResult){
					CustomerDataBean customerdb=new CustomerDataBean();
					customerdb.setCustCustomerName(cus.getCustomerName());
					customerdb.setCustPhoneNumber(cus.getCustomerPhoneNumber());
					customerdb.setCustCity(cus.getCustomerCity());
					customerdb.setCustCountry(cus.getCountry().getCountry_Name());
					customerList.add(customerdb);
				}
			}
			System.out.println("Size"+customerList.size());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return customerList;
		}

	@Override
	public List<CustomerDataBean> searchByCustomerCityName(
			CustomerDataBean customerDataBean) {
		List<Customer> custResult=null;
		List<CustomerDataBean> custList=null;
		System.out.println("Inside searchByCityName in DAO method calling");
		System.out.println("Cityname"+customerDataBean.getCustCity());
		if(customerDataBean.getCustCustomerName() !=null){
			try{
			Query cus=null;
			custList=new ArrayList<CustomerDataBean>();
			cus=entitymanager.createQuery("from Customer where (customerCity Like ?  and status=?)");
			cus.setParameter(1, "%"+customerDataBean.getCustCity()+"%"); // Like Search
			cus.setParameter(2, "Active");
			custResult=(List<Customer>)cus.getResultList();
			System.out.println("Size"+custResult.size());
			if(custResult.size()>0){
				System.out.println(custResult);
				
				//Adding Values to Data Table 
				
				for(Customer customer: custResult){
					CustomerDataBean cusObj=new CustomerDataBean();
					System.out.println("name"+customer.getCustomerCity());
					cusObj.setCustCity(customer.getCustomerCity());
					cusObj.setCustCountry(customer.getCountry().getCountry_Name());
					cusObj.setCustCustomerName(customer.getCustomerName());
					cusObj.setCustPhoneNumber(customer.getCustomerPhoneNumber());
					
					custList.add(cusObj);
				}
			}
			System.out.println("Size"+custList.size());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return custList;
	}

	@Override
	public List<String> getProjectList() {
		Query projectQueery=null;
		
		List<String> PoductList=null;
		try{
		projectQueery=entitymanager.createQuery("select productName from Product where (status= ? and categoryStatus='ProjectType')");
		projectQueery.setParameter(1, "Active");
		PoductList=(List<String>)projectQueery.getResultList();
		System.out.println("names"+PoductList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return PoductList;
	}

	@Override
	public List<RawMaterial> getSubCategoty(String category) {
		Query qry=null;
		
		List<RawMaterial> PoductList=null;
		try{
			qry=entitymanager.createQuery("from RawMaterial where (productName= ? and status= ?)");
			qry.setParameter(1, category);
			qry.setParameter(2, "Active");
			PoductList=(List<RawMaterial>)qry.getResultList();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return PoductList;
	}
		
	@Override
		@Transactional(value="transactionManager")
		public String inserttrans(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
		System.out.println("Calling inserttransaction  method ...");
			String status="Fail";
			String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
			Date date=new Date();
			Timestamp timestamp=new Timestamp(date.getTime());
          Query a=null;int b=0;
			try
			{
				a=entitymanager.createQuery("from Transaction ");
				List<Transaction>tlist=(List<Transaction>)a.getResultList();
				System.out.println("transaction size,<><><><><><>"+tlist.size());
				String orderno="";
				if(tlist.size()>0)
				{
					b=tlist.size()+1;
					System.out.println("bsize<,,,.<><><>,.,.,.,>"+b);	
			     if(b<=9)
				{
				    orderno="TN000"+b;
				}
				else if(b>9 &&b<99)
				{
					orderno="TN00"+b;
				}
				else if(b>99)
				{
					orderno="TN0"+b;
				}
				}
				else 
				{
					b=1;
					orderno="TN0001";
					System.out.println("orderno<><<>"+orderno);
				}
	            
		    Transaction transaction=new Transaction();
		    transaction.setTransactionNo(orderno);
		    transaction.setTransactionName(transactionDataBean.getTransName());
		    System.out.println("Name>>>>>>"+transactionDataBean.getTransName());
		    if(transactionDataBean.getTransPaymentType().equals("TRAN001"))
            {
            	 transaction.setPaymentMode("Cash");
                 System.out.println("transtype"+transaction.getPaymentMode());

            }
		    else if(transactionDataBean.getTransPaymentType().equals("TRAN002"))
            {
            	transaction.setBankName(transactionDataBean.getTransCardBankName());
    		    System.out.println("bank>>>"+transactionDataBean.getTransCardBankName());
            
    		    transaction.setAccountNumber(transactionDataBean.getTransCardAccountNo());
    		    System.out.println("acc2>>"+transactionDataBean.getTransCardAccountNo());
    		  transaction.setPaymentMode("Card");
              System.out.println("transtype"+transaction.getPaymentMode());

            }
		   
            else if(transactionDataBean.getTransPaymentType().equals("TRAN003"))
            {
            	 transaction.setChequeNo(transactionDataBean.getTransChequeNo());
     		    System.out.println("chekno>>"+transactionDataBean.getTransChequeNo());
     		    transaction.setBankName(transactionDataBean.getTransChequeBName());
     		    System.out.println("bank name>>>>>"+transactionDataBean.getTransChequeNo());
     		    transaction.setChequeDate(transactionDataBean.getTransChequeDate());
     		    System.out.println("chk date>>>"+transactionDataBean.getTransChequeDate());
     		   transaction.setPaymentMode("Cheque");
               System.out.println("transtype"+transaction.getPaymentMode());

            }
            else if(transactionDataBean.getTransPaymentType().equals("TRAN004"))
            {
            	  transaction.setBankName(transactionDataBean.getTransTransferBName());
      		    System.out.println("bank1>>>"+transactionDataBean.getTransTransferBName());
      		    transaction.setAccountNumber(transactionDataBean.getTranstranferAccontNo());
    		    System.out.println("acc3>>>"+transactionDataBean.getTranstranferAccontNo());
      		   transaction.setPaymentMode("Transfer");
               System.out.println("transtype"+transaction.getPaymentMode());


            }

           transaction.setTransactionNote(transactionDataBean.getTransNote());
		    System.out.println("note>>>>"+transactionDataBean.getTransNote());

            transaction.setTransactionAmount(transactionDataBean.getTransAmmount());
		    System.out.println("ammount>>>>"+transactionDataBean.getTransAmmount());
		   if(transactionDataBean.getTransTransactionType().equals("TRANT01"))
		   {
			 transaction.setTransactionType("Income");  
             System.out.println("transtype"+transaction.getTransactionType());

		   }
		   
		   else if(transactionDataBean.getTransTransactionType().equals("TRANT02"))
		   {
				 transaction.setTransactionType("Expenses");  
                 System.out.println("transtype"+transaction.getTransactionType());
		   }
		   
		    transaction.setTransactionDate(transactionDataBean.getTransDate());
		    System.out.println("transdate>>>>"+transactionDataBean.getTransDate());
		    transaction.setTransDate(date);
             transaction.setEditDate(date);
             transaction.setEditLoginStatus(login);
             transaction.setLoginStatus(login);
             transaction.setStatus("Active");
             transaction.setEditStatus("not edited");
             transaction.setEditTime(timestamp);
             entitymanager.persist(transaction);
             
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return status;
			}
			return null;
		}

	
		@Override
		@Transactional(value="transactionManager")

		public String traview(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			try
			{
				Query q=null;
				q=entitymanager.createQuery("from Transaction where transactionDate between ? and ? and status='Active' ");
				q.setParameter(1, transactionDataBean.getTransFromDate());
				q.setParameter(2, transactionDataBean.getTransToDate());
				List<Transaction>view=(List<Transaction>)q.getResultList();
				System.out.println("transactionlist view size....>>>>>>>"+view);
				transactionDataBean.setTranslist(view);
				
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			return null;
		}

		@Override
		public List<Transaction> trans(String transno) {
			// TODO Auto-generated method stub
			System.out.println("inside trans dao"+transno);
			List<Transaction>tralist=null;
			if(transno!=null)
			{
			try
			{
				Query q=null;
				q=entitymanager.createQuery("from Transaction where (transactionNo=? and status=?) ");
				q.setParameter(1, transno);
				q.setParameter(2, "Active");
				tralist=q.getResultList();
				System.out.println("tralist size,.,.,.,"+tralist.size());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
			return tralist;
		}

		@Override
		public List<Transaction> traedit(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
		
			System.out.println("inside tra edit ");
			try
			{
				Query q=null;
				q=entitymanager.createQuery("from Transaction where (transactionNo=? and status=?) ");
				q.setParameter(1, transactionDataBean.getTransno());
				System.out.println("gyeegrygr"+transactionDataBean.getTransno());
				q.setParameter(2, "Active");
				List<Transaction>tlist=(List<Transaction>)q.getResultList();
				System.out.println("tlist size"+tlist.size());
				if(tlist.size()>0)
				{
			transactionDataBean.setTransDate(tlist.get(0).getTransactionDate());
			System.out.println("date...."+transactionDataBean.getTransDate());
			transactionDataBean.setTransName(tlist.get(0).getTransactionName());
			System.out.println("name...."+transactionDataBean.getTransName());
			

			if(tlist.get(0).getPaymentMode().equals("Card"))
			{
				transactionDataBean.setTransCardBankName(tlist.get(0).getBankName());	
				transactionDataBean.setTransCardAccountNo(tlist.get(0).getAccountNumber());
			    transactionDataBean.setTransPaymentType(tlist.get(0).getPaymentMode());
			    System.out.println("payment[][][][][]"+transactionDataBean.getTransPaymentType());  

			     
			}
			
			
			else if(tlist.get(0).getPaymentMode().equals("Cheque"))
			{
				transactionDataBean.setTransChequeBName(tlist.get(0).getBankName());
				transactionDataBean.setTransChequeNo(tlist.get(0).getChequeNo());
				transactionDataBean.setTransChequeDate(tlist.get(0).getChequeDate());
			    transactionDataBean.setTransPaymentType(tlist.get(0).getPaymentMode());
			    System.out.println("payment[][][][][]"+transactionDataBean.getTransPaymentType());  


			}
			else if(tlist.get(0).getPaymentMode().equals("Transfer"))
				
			{
				transactionDataBean.setTransTransferBName(tlist.get(0).getBankName());
				transactionDataBean.setTranstranferAccontNo(tlist.get(0).getAccountNumber());
				transactionDataBean.setTransPaymentType(tlist.get(0).getPaymentMode());

	            System.out.println("payment[][][][][]"+transactionDataBean.getTransPaymentType());  

			}
			else if(tlist.get(0).getPaymentMode().equals("Cash"))
			{
				transactionDataBean.setTransPaymentType(tlist.get(0).getPaymentMode());
	            System.out.println("payment[][][][][]"+transactionDataBean.getTransPaymentType());  

				
			}
         
			transactionDataBean.setTransTransactionType(tlist.get(0).getTransactionType());
			System.out.println("transaction...."+transactionDataBean.getTransTransactionType());

			transactionDataBean.setTransstatus(tlist.get(0).getStatus());
			System.out.println("status...."+transactionDataBean.getTransstatus());

			transactionDataBean.setTransno(tlist.get(0).getTransactionNo());
			System.out.println("transno...."+transactionDataBean.getTransno());

			transactionDataBean.setTransAmmount(tlist.get(0).getTransactionAmount());
			System.out.println("amount..."+transactionDataBean.getTransAmmount());

			transactionDataBean.setTransNote(tlist.get(0).getTransactionNote());
			System.out.println("note..."+transactionDataBean.getTransNote());

			
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		@Transactional(value="transactionManager")
		public String update(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			System.out.println("inside dao mathod"+transactionDataBean.getTransno());
			String status="fail";
			Query q= null;
			int transid;
			Date date=new Date();
			Timestamp timestamp=new Timestamp(date.getTime());
			System.out.println("Roll Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll"));
			System.out.println("Login User Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"); // checking login roll
			try
			{
				if(transactionDataBean.getTransno()!=null)
				{
					q=entitymanager.createQuery("from Transaction where (transactionNo=? and status=?) ");
					q.setParameter(1,transactionDataBean.getTransno());
					q.setParameter(2, "Active");
					List<Transaction>trlist=(List<Transaction>)q.getResultList();
					System.out.println("trlist size"+trlist.size());
					if(trlist.size()>0)
					{
						transid=trlist.get(0).getTransaction_ID();
						System.out.println("transid<><><><>"+transid);
						Transaction trans=entitymanager.find(Transaction.class,transid);
						trans.setTransDate(transactionDataBean.getTransDate());
						if(trans.getPaymentMode().equals("Card"))
						{
							
							trans.setBankName(transactionDataBean.getTransCardBankName());
							trans.setAccountNumber(transactionDataBean.getTransCardAccountNo());
							
							trans.setPaymentMode(transactionDataBean.getTransPaymentType());
							System.out.println("card"+transactionDataBean.getTransPaymentType());
							
						}
						else if(trans.getPaymentMode().equals("Cheque"))
						{

							trans.setBankName(transactionDataBean.getTransChequeBName());
							trans.setChequeNo(transactionDataBean.getTransChequeNo());
							trans.setChequeDate(transactionDataBean.getTransChequeDate());

							
							trans.setPaymentMode(transactionDataBean.getTransPaymentType());
							System.out.println("chk"+transactionDataBean.getTransPaymentType());
							
						}
						else if(trans.getPaymentMode().equals("Transfer"))
						{
							trans.setAccountNumber(transactionDataBean.getTranstranferAccontNo());
							trans.setBankName(transactionDataBean.getTransTransferBName());

							trans.setPaymentMode(transactionDataBean.getTransPaymentType());
							System.out.println("trans"+transactionDataBean.getTransPaymentType());
							
						}
						if(trans.getTransactionType().equals("Income"))
						{
							trans.setTransactionType(transactionDataBean.getTransTransactionType());
							System.out.println("trans"+transactionDataBean.getTransTransactionType());
						}
						else if(trans.getTransactionType().equals("Expenses"))
						{
							trans.setTransactionType(transactionDataBean.getTransTransactionType());
							System.out.println("trans"+transactionDataBean.getTransTransactionType());
						}
						trans.setTransactionName(transactionDataBean.getTransName());
					    trans.setTransactionAmount(transactionDataBean.getTransAmmount());
					    trans.setTransactionNote(transactionDataBean.getTransNote());
					    trans.setEditStatus("edited");
					    trans.setEditLoginStatus(login);
					    trans.setEditDate(date);
					    trans.setEditTime(timestamp);
					    entitymanager.merge(trans);
					}
				}
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return status;
		}

		@Override
		@Transactional(value="transactionManager")

		public String delettrans(TransactionDataBean transactionDataBean) {
			// TODO Auto-generated method stub
			String status="Fail";
			Query q= null;
			int transid=0;
			String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
			Date date=new Date();
			Timestamp timestamp=new Timestamp(date.getTime());
			try
			{
				System.out.println(transactionDataBean.getTransno());
				if(transactionDataBean.getTransno()!=null)
				{
					q=entitymanager.createQuery("from Transaction where (transactionNo=? and status=?) ");
					q.setParameter(1,transactionDataBean.getTransno());
					q.setParameter(2, "Active");
					List<Transaction>tlist=(List<Transaction>)q.getResultList();
					System.out.println("tlist size?????????????"+tlist.size());
					if(tlist.size()>0)
					{
						transid=tlist.get(0).getTransaction_ID();
						System.out.println("transid[][][][][][][]"+transid);
						Transaction tr=entitymanager.find(Transaction.class,transid);
				         tr.setStatus("De-Active");
					     tr.setEditLoginStatus(login);
						 tr.setEditStatus("Removed");
						 tr.setEditDate(date);
						 tr.setEditTime(timestamp);
						 entitymanager.merge(tr);
					
					}
				}
				
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return status;
		}
		@Override
		public List<Employee> getempedit(String empEmployeeName,EmployeeDataBean employeeDataBean) {
			// TODO Auto-generated method stub
			System.out.println("inside get emp edit ");
			try
			{
				Query q=null;
				q=entitymanager.createQuery("from Employee where(employee_details_ID=? and status=?)");
				q.setParameter(1,empEmployeeName);
				System.out.println("employyyyyyeee emp edit name"+empEmployeeName);
				q.setParameter(2, "Active");
				List<Employee>empid1=(List<Employee>)q.getResultList();
				System.out.println("empidddddddd size1111111111"+empid1.size());
				if(empid1.size()>0)
				{
				
				employeeDataBean.setEmpEmployeeName(empid1.get(0).getEmployeeName());
				System.out.println("empppname"+employeeDataBean.getEmpEmployeeName());
				
				employeeDataBean.setEmpEmployeeId(empid1.get(0).getEmployee_details_ID());
				System.out.println("empid"+employeeDataBean.getEmpEmployeeId());
				
				employeeDataBean.setEmpAddress(empid1.get(0).getEmployeeAddress());
				System.out.println("empppname"+employeeDataBean.getEmpAddress());

				employeeDataBean.setEmpBasicSalary(empid1.get(0).getEmployeeBasicSalary());
				System.out.println("empadd"+employeeDataBean.getEmpBasicSalary());

				employeeDataBean.setEmpDescription(empid1.get(0).getEmployeeDescription());
				System.out.println("empdesc"+employeeDataBean.getEmpDescription());

				employeeDataBean.setEmpDob(empid1.get(0).getEmployeeDob());
				System.out.println("empdob"+employeeDataBean.getEmpDob());
	            
				employeeDataBean.setEmpEntryDate(empid1.get(0).getEmployeeEntryDate());
				System.out.println("empdate"+employeeDataBean.getEmpEntryDate());
				
				employeeDataBean.setEmpGender(empid1.get(0).getEmployeeGender());
				System.out.println("empgender"+employeeDataBean.getEmpGender());

				employeeDataBean.setEmpMaildId(empid1.get(0).getEmployeeEmail());
				System.out.println("empmailid"+employeeDataBean.getEmpMaildId());
	            
				employeeDataBean.setEmpDesignation(empid1.get(0).getDesignation().getDesignation_Name());
				System.out.println("empdesign"+employeeDataBean.getEmpDesignation());
				
				employeeDataBean.setEmpQualification(empid1.get(0).getQualification().getQualification_Name());
				System.out.println("empquali"+employeeDataBean.getEmpQualification());
	            employeeDataBean.setEmpJoinDate(empid1.get(0).getEmployeeJointDate());
				System.out.println("empjoin"+employeeDataBean.getEmpJoinDate());

	            employeeDataBean.setEmpPhoneNo(empid1.get(0).getEmpPhone());
				System.out.println("empphone"+employeeDataBean.getEmpPhoneNo());

	            
				}
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return null;
		}
		@Override
		@Transactional(value="transactionManager")
		public String getupdate(EmployeeDataBean employeeDataBean) {
			// TODO Auto-generated method stub
			
			System.out.println("Inside EditVendor method calling in DAO..."+employeeDataBean.getEmpEmployeeId());
			String status="Fail";
			Query q= null;
			int empid=0;
			System.out.println("Roll Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll"));
			System.out.println("Login User Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"); // checking login roll
			
			try{
				if(employeeDataBean.getEmpEmployeeId() != null){
					q=entitymanager.createQuery("from  Employee where (employee_details_ID=? and status=?)");
					q.setParameter(1, employeeDataBean.getEmpEmployeeId());
					q.setParameter(2, "Active");
					List<Employee> emplist=(List<Employee>)q.getResultList();
					System.out.println("emplist Size"+emplist.size());
	                int designid=0;
	                int qualiid=0;
					
					if(emplist.size() > 0){
						
	                	  empid=emplist.get(0).getEmployee_ID();
						 System.out.println("ID "+empid);
						 Query de=null;
	                     de=entitymanager.createQuery("from Designation where designation_Name=? and designation_Status=?");
					     de.setParameter(1, employeeDataBean.getEmpDesignation());					
						 de.setParameter(2, "Active");
						 List<Designation>des=(List<Designation>)de.getResultList();
						 System.out.println("des Size"+des.size());
						if(des.size() > 0){
							
							designid=des.get(0).getDesignationID();
	                         Query qu=null;
	                         qu=entitymanager.createQuery("from  Qualification where qualification_Name=? and qualification_Status=?");
						qu.setParameter(1, employeeDataBean.getEmpQualification());
						qu.setParameter(2, "Active");
						List<Qualification>qua=(List<Qualification>)qu.getResultList();
						System.out.println("qua Size"+qua.size());
						if(qua.size() > 0){
						qualiid=qua.get(0).getQualificationID();
						Employee employee=entitymanager.find(Employee.class, empid);
						
						employee.setEmployeeName(employeeDataBean.getEmpEmployeeName());
						employee.setEmployee_details_ID(employeeDataBean.getEmpEmployeeId());
						employee.setEmpPhone(employeeDataBean.getEmpPhoneNo());
						employee.setEmployeeDob(employeeDataBean.getEmpDob());
						employee.setEmployeeAddress(employeeDataBean.getEmpAddress());
						employee.setEmployeeDescription(employeeDataBean.getEmpDescription());
						employee.setEmployeeBasicSalary(employeeDataBean.getEmpBasicSalary());
						employee.setEmployeeGender(employeeDataBean.getEmpGender());
						employee.setEmployeeEmail(employeeDataBean.getEmpMaildId());
						employee.setEmployeeEntryDate(employeeDataBean.getEmpEntryDate());
						employee.setEmployeeJointDate(employeeDataBean.getEmpJoinDate());
						employee.setDesignation(entitymanager.find(Designation.class,designid));
						employee.setQualification(entitymanager.find(Qualification.class,qualiid));
						entitymanager.merge(employee);
						
						}
						

						}
	                  }
				
				}
				}catch(Exception e)
				{
					
				}
			
			
			return status;
		}


			@Override
			@Transactional(value="transactionManager")
		public String deleteemployee(EmployeeDataBean employeeDataBean) {
			// TODO Auto-generated method stub
				String status="Fail";
				Query q= null;
				int empid=0;
				String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
				Date date=new Date();
				try
				{
					System.out.println(employeeDataBean.getEmpEmployeeName());
					if(employeeDataBean.getEmpEmployeeName()!=null)
					{
						q=entitymanager.createQuery("from  Employee where (employee_details_ID=? and status=?)");
						q.setParameter(1,employeeDataBean.getEmpEmployeeId());
						q.setParameter(2, "Active");
						List<Employee>emplist=(List<Employee>)q.getResultList();
						System.out.println("emp list size"+emplist.size());
						if(emplist.size()>0)
						{
							empid=emplist.get(0).getEmployee_ID();	
							System.out.println("empid size"+empid);}
					    Employee employee=entitymanager.find(Employee.class,empid);
						employee.setStatus("De-Active");
						employee.setEmployeeLoginStatus(login);
						employee.setEmployeeEditStatus("Removed");
						employee.setEmployeeEditDate(date);
						entitymanager.merge(employee);
						
					}
		
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
			return status;
		}
			
			UploadedFile file;
			public String filepath;  
			public String getFilepath() {
				return filepath;
			}
			public void setFilepath(String filepath) {
				this.filepath = filepath;
			}
			public UploadedFile getFile() {
				return file;
			}
			public void setFile(UploadedFile file) {
				this.file = file;
			}
			public String TransferFile(String fileName, java.io.InputStream inputStream,String temp) throws LiusenException
			{ 
			    try 
			    {  
			    	
					filepath="C://product//";  
					System.out.println("managed bean file path---------->"+filepath);
					OutputStream out = new FileOutputStream(new File(filepath + temp+""+fileName));
			        int reader = 0;  
			        System.out.println(">>-->>file size "+getFile().getSize());
			        byte[] bytes = new byte[(int) getFile().getSize()];  
			        while ((reader = inputStream.read(bytes)) != -1)
			        {  
			            out.write(bytes, 0, reader);     	            
			        }  
			        inputStream.close();  
			        out.flush();  
			        out.close();  
			    } 
			    catch (IOException e) 
			    {  
			    	System.out.println(e.getMessage());  
			    } 
			    return temp+""+fileName;
			}
			
			@Transactional(value="transactionManager")
			public String designRegSubmit(ProductDataBean productDataBean) throws LiusenException, ParseException
			{
				try
				{
				Query q1=null,q2=null;
				q1=entitymanager.createQuery("from Product where productName=? ");
				q1.setParameter(1,productDataBean.getProdProductName());
				List<Product> Product1=(List<Product>)q1.getResultList();
				int pid=0;
				if(Product1.size()>0)
				{
				pid=Product1.get(0).getProduct_ID();
				q2=entitymanager.createQuery("from ImagePath where product_id=? and status='inserted'");
				q2.setParameter(1,pid);
				List<ImagePath> list1=(List<ImagePath>)q2.getResultList();
				int id=0;
				if(list1.size()>0)
				{
					throw new LiusenException("*Already an Image has been Selected for this Product");
				}
				else
				{
				setFile(productDataBean.getFile());
				ImagePath d=new ImagePath();
				d.setDate(productDataBean.getDate());
				d.setProduct(entitymanager.find(Product.class,pid));
				d.setDescription(productDataBean.getProdDescription());
				try
				{
					System.out.println("-->>1 "+productDataBean.getFile().getName());
					System.out.println("-->>2 "+productDataBean.getFile().getInputStream());
					d.setFilePath(TransferFile(productDataBean.getFile().getName(), productDataBean.getFile().getInputStream(),productDataBean.getProdProductName()));
				}
				catch(Exception ee)
				{
					d.setFilePath("");
				}
				d.setStatus("inserted");
				entitymanager.persist(d);
				System.out.println("-->> completed");
				}
				}
				else
				{
					System.out.println("no prod");
				}
				}
				finally
				{
					
				}
				return null;
			}
			
	public String dropprojectValues(ProjectDataBean projectDataBean)
	{
		System.out.println("drop vales -- project -- dao  - ");
		Query v=null;
		List<String> projectman=new ArrayList<String>();
		List<String> marketman=new ArrayList<String>();
		List<String> adminproj=new ArrayList<String>();
		List<String> projects=new ArrayList<String>();
		try
		{
			v=entitymanager.createQuery("from Employee where status='Active'");
			List<Employee> employee=(List<Employee>) v.getResultList();
			System.out.println("employee size - > "+employee.size());
			if(employee.size()>0)
			{
				for (int i = 0; i < employee.size(); i++) 
				{
					String proman="";String marman="";String adproj="";
					if(employee.get(i).getDesignation().getDesignation_Name().equals("Project Manager"))
					{
						proman=employee.get(i).getEmployee_details_ID();
						projectman.add(proman);
					}
					else if(employee.get(i).getDesignation().getDesignation_Name().equals("Marketing Manager"))
					{
						marman=employee.get(i).getEmployee_details_ID();
						marketman.add(marman);
					}
					else if(employee.get(i).getDesignation().getDesignation_Name().equals("Admin Project"))
					{
						adproj=employee.get(i).getEmployee_details_ID();
						adminproj.add(adproj);
					}
				}
				System.out.println("project man - > "+projectman.size()+" market man - > "+marketman.size()+" admin - > "+adminproj.size());
				projectDataBean.setProjectmanager(projectman);
				projectDataBean.setMarketingmanager(marketman);
				projectDataBean.setAdminproject(adminproj);
			}
			v=null;
			v=entitymanager.createQuery("from Product where category='Project' and status='Active' ");
			List<Product> product=(List<Product>) v.getResultList();
			System.out.println("project size - > "+product.size());
			if(product.size()>0)
			{
				for (int i = 0; i < product.size(); i++) 
				{
					String project="";
					project=product.get(i).getProductName();
					projects.add(project);
				}
				System.out.println("project name size -- > "+projects.size());
				projectDataBean.setProjects(projects);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String insertProjectValues(ProjectDataBean projectDataBean)throws LiusenException, IOException
	{
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		try
		{
			v=entitymanager.createQuery("from Projet where projectName=? and status='Active'");
			v.setParameter(1, projectDataBean.getProjProjectName());
			List<Projet> projet1=(List<Projet>) v.getResultList();
			if(projet1.size()>0)
			{
				throw new LiusenException("This Project Name is Already Exist");
			}
			else
			{
				Projet insert=new Projet();
				insert.setProjectName(projectDataBean.getProjProjectName());
				insert.setProjectValue(projectDataBean.getProjProjectValue());
				insert.setProjectDate1(projectDataBean.getProjDate());
				insert.setProjectDate2(projectDataBean.getProjDate1());
				insert.setProjectDate3(projectDataBean.getProjDate2());
				insert.setProjectRegDate(date);
				insert.setAddress(projectDataBean.getProjAddress());
				insert.setClientName(projectDataBean.getProjClientName());
				insert.setContactNumber(projectDataBean.getProjContactNO());
				insert.setDescription(projectDataBean.getProjDescription());
				insert.setEditDate(date);
				insert.setEditLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				insert.setEditStatus("not edited");
				insert.setLocation(projectDataBean.getProjLocation());
				insert.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				insert.setProjectRegTime(time);
				insert.setState(projectDataBean.getProjLocation());
				insert.setStatus("Active");
				setFile(projectDataBean.getFile());
				v=null;
				v=entitymanager.createQuery("from Projet");
				List<Projet> projt1=(List<Projet>) v.getResultList();
				try
				{
					if(projt1.size()>0)
					{
						System.out.println("inside if loop in dao -- >>");
						insert.setFilePath(TransferFile(projectDataBean.getFile().getName(), projectDataBean.getFile().getInputStream(),""+(projt1.get(projt1.size()-1).getProject_ID()+1))	);
					}
					else
					{
						System.out.println("inside else loop in dao -- >>");
						insert.setFilePath(TransferFile(projectDataBean.getFile().getName(), projectDataBean.getFile().getInputStream(),""+0)	);
					}
				}
				catch(Exception e)
				{
					insert.setFilePath("");
				}				
				entitymanager.persist(insert);
				v=entitymanager.createQuery("from Projet");
				List<Projet> projet=(List<Projet>) v.getResultList();
				int projetid=0;
				if(projet.size()>0)
				{
					projetid=projet.get(projet.size()-1).getProject_ID();
					System.out.println("projet id -- > "+projetid);
					projectDataBean.setProjetid(projetid);
				}
			}			
		}
		finally
		{
			
		}
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String insertProjectEmployee(ProjectDataBean projectDataBean)
	{
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from Employee where employee_details_ID=? and status='Active'");
			v.setParameter(1, projectDataBean.getProjProjectManager());
			List<Employee> employee=(List<Employee>) v.getResultList();
			int empid=0;
			if(employee.size()>0)
			{
				empid=employee.get(0).getEmployee_ID();
				System.out.println("emp id - > "+empid);
				ProjectEmployee proemp=new ProjectEmployee();
				proemp.setProjet(entitymanager.find(Projet.class, projectDataBean.getProjetid()));
				proemp.setEmployee(entitymanager.find(Employee.class, empid));
				proemp.setStatus("Project Manager");
				entitymanager.persist(proemp);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String insertPmarketingEmployee(ProjectDataBean projectDataBean)
	{
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from Employee where employee_details_ID=? and status='Active'");
			v.setParameter(1, projectDataBean.getProjMarketing());
			List<Employee> employee1=(List<Employee>) v.getResultList();
			int empid1=0;
			if(employee1.size()>0)
			{
				empid1=employee1.get(0).getEmployee_ID();
				System.out.println("emp id -1 > "+empid1);
				ProjectEmployee proemp=new ProjectEmployee();
				proemp.setProjet(entitymanager.find(Projet.class, projectDataBean.getProjetid()));
				proemp.setEmployee(entitymanager.find(Employee.class, empid1));
				proemp.setStatus("Marketing Manager");
				entitymanager.persist(proemp);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	@Transactional(value="transactionManager")
	public String insertPadminEmployee(ProjectDataBean projectDataBean)
	{
		Query v=null;
		try
		{			
			v=entitymanager.createQuery("from Employee where employee_details_ID=? and status='Active'");
			v.setParameter(1, projectDataBean.getProjAdminProject());
			List<Employee> employee2=(List<Employee>) v.getResultList();
			int empid2=0;
			if(employee2.size()>0)
			{
				empid2=employee2.get(0).getEmployee_ID();
				System.out.println("emp id -2 > "+empid2);
				ProjectEmployee proemp=new ProjectEmployee();
				proemp.setProjet(entitymanager.find(Projet.class, projectDataBean.getProjetid()));
				proemp.setEmployee(entitymanager.find(Employee.class, empid2));
				proemp.setStatus("Admin Project");
				entitymanager.persist(proemp);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	public String projectImage(ProjectDataBean projectDataBean)
	{
		System.out.println("project photo view dao  -- ");
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from Product where productName=? and status='Active'");
			v.setParameter(1, projectDataBean.getProjProjectName());
			List<Product> product=(List<Product>)v.getResultList();
			int productid=0;
			if(product.size()>0)
			{
				productid=product.get(0).getProduct_ID();
				System.out.println("product id - > "+productid);
				v=null;
				v=entitymanager.createQuery("from ImagePath where product_id=? and status='inserted'");
				v.setParameter(1, productid);
				List<ImagePath> image=(List<ImagePath>) v.getResultList();
				if(image.size()>0)
				{
					projectDataBean.setIamgepath(image.get(0).getFilePath());
					System.out.println("iamge path -- > "+projectDataBean.getIamgepath());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String findcashbook(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query q=null;
		Query qq=null;
		Query q1=null;
		ArrayList<Invoice> invoice=null;
		ArrayList<Payment> payment=null;
		List<Transaction> trans=null;
		ArrayList<Purchase> purchaseList=null;
		ArrayList<Sale> salesList=null;
		ArrayList<StockoutManual> stockout=null;
		ArrayList<PurchaseOrederDataBean> purchaselist=null;
		System.out.println("---Inside purchasecash---");
		BigDecimal debit=BigDecimal.valueOf(0);
		BigDecimal credit=BigDecimal.valueOf(0);
		int count=1;
		try
		{
			purchaseOrederDataBean.setCashList(null);
			purchaselist=new ArrayList<PurchaseOrederDataBean>();
			qq=entitymanager.createQuery("from Purchase where purchaseOrderDate between ? and ? and paymentStatus='paid' and status='ordered'");
			qq.setParameter(1, purchaseOrederDataBean.getFromDate());
			qq.setParameter(2, purchaseOrederDataBean.getToDate());
			purchaseList=(ArrayList<Purchase>)qq.getResultList();
			System.out.println("size of purchaseList"+purchaseList.size());
			if(purchaseList.size() >0)
			{
				int id=0;
				for(int i=0; i<purchaseList.size(); i++)
				{
					q=entitymanager.createQuery("from Invoice where purchase_ID=? and invoiceStatus='Purchase Invoice' ");
					q.setParameter(1, purchaseList.get(i).getPurchase_ID());							
					invoice=(ArrayList<Invoice>) q.getResultList();
					System.out.println("id--->> "+purchaseList.get(i).getPurchase_ID());
					System.out.println("size of invoicelist--->>"+invoice.size());
					if(invoice.size() >0)
					{
					id=invoice.get(0).getInvoice_ID();
					System.out.println("id------>>>>"+id);
					q1=entitymanager.createQuery("from Payment where invoice_ID=? and paymentType='Cash' and status='Approved'");
					q1.setParameter(1, id);
					payment=(ArrayList<Payment>)q1.getResultList();
					System.out.println("size of payment"+payment.size());
					if(payment.size() > 0)
					{
						
								PurchaseOrederDataBean purchase=new PurchaseOrederDataBean();
								purchase.setsNo(""+count);
								/*purchaseOrederDataBean.setCount1(""+(count+1));
								*/purchase.setParticulars("Purchase");
								purchase.setDebit("");
								purchase.setDate(payment.get(0).getInvoice().getPurchase().getPurchaseOrderDate());
								purchase.setClientName(payment.get(0).getInvoice().getPurchase().getVendor().getFirmName());
								purchase.setCredit(payment.get(0).getTotalAmount());
								purchaselist.add(purchase);
								purchaseOrederDataBean.setCashList(purchaselist);
						System.out.println("list size"+purchaseOrederDataBean.getPurchaseList().size());
						credit=credit.add(new BigDecimal(payment.get(0).getTotalAmount()));
						count++;
					}
					}
				}
			}
			
			
			
			
			q=entitymanager.createQuery("from Transaction where transactionDate between ? and ? and paymentMode='Cash' and status='Active' ");
			q.setParameter(1, purchaseOrederDataBean.getFromDate());
			q.setParameter(2, purchaseOrederDataBean.getToDate());
			trans=(List<Transaction>) q.getResultList();
			if(trans.size()>0)
			{
				for (int i = 0; i < trans.size(); i++) 
				{
					PurchaseOrederDataBean purchase=new PurchaseOrederDataBean();
					purchase.setsNo(""+count);
					/*purchaseOrederDataBean.setCount1(""+(count+1));
					*/purchase.setParticulars("Transaction");
					purchase.setDate(trans.get(i).getTransactionDate());
					purchase.setClientName(trans.get(i).getTransactionName());
					if(trans.get(i).getTransactionType().equals("Income"))
					{
						purchase.setCredit("");
						purchase.setDebit(trans.get(i).getTransactionAmount());
						debit=debit.add(new BigDecimal(trans.get(i).getTransactionAmount()));
					}
					else if(trans.get(i).getTransactionType().equals("Expenses"))
					{
						purchase.setDebit("");
						purchase.setCredit(trans.get(i).getTransactionAmount());
						credit=credit.add(new BigDecimal(trans.get(i).getTransactionAmount()));
					}
					purchaselist.add(purchase);
					purchaseOrederDataBean.setCashList(purchaselist);
			System.out.println("list size trans"+purchaseOrederDataBean.getPurchaseList().size());
			count++;
				}
			}
			
			
			
			
			qq=entitymanager.createQuery("from Sale where salesOrderDate between ? and ? and paymentStatus='paid'");
			qq.setParameter(1, purchaseOrederDataBean.getFromDate());
			qq.setParameter(2, purchaseOrederDataBean.getToDate());
			salesList=(ArrayList<Sale>)qq.getResultList();
			System.out.println("size of purchaseList"+salesList.size());
			if(salesList.size() >0)
			{
				int id=0;
				for(int i=0; i<salesList.size(); i++)
				{
					q=entitymanager.createQuery("from Invoice where sales_ID=? and invoiceStatus='Sales Invoice' ");
					q.setParameter(1, salesList.get(i).getSales_ID());							
					invoice=(ArrayList<Invoice>) q.getResultList();
					System.out.println("id--->> "+salesList.get(i).getSales_ID());
					System.out.println("size of invoicelist--->>"+invoice.size());
					if(invoice.size() >0)
					{
					id=invoice.get(0).getInvoice_ID();
					System.out.println("id------>>>>"+id);
					q1=entitymanager.createQuery("from Payment where invoice_ID=? and paymentType='Cash' and status='Approved'");
					q1.setParameter(1, id);
					payment=(ArrayList<Payment>)q1.getResultList();
					System.out.println("size of payment"+payment.size());
					if(payment.size() > 0)
					{						
						PurchaseOrederDataBean purchase=new PurchaseOrederDataBean();
						purchase.setsNo(""+count);
						purchaseOrederDataBean.setCount1(""+(count+1));
						purchase.setParticulars("Sales");
						purchase.setCredit("");
						purchase.setDate(payment.get(0).getInvoice().getSale().getSalesDate());
						purchase.setClientName(payment.get(0).getInvoice().getSale().getProjet().getProjectName());
						purchase.setDebit(payment.get(0).getTotalAmount());
						purchaselist.add(purchase);
						purchaseOrederDataBean.setCashList(purchaselist);
						System.out.println("list size"+purchaseOrederDataBean.getPurchaseList().size());
						debit=debit.add(new BigDecimal(payment.get(0).getTotalAmount()));
						count++;
					}
				}
			}
		}			
		purchaseOrederDataBean.setTotalcredit(""+credit);
		purchaseOrederDataBean.setTotaldebit(""+debit);
		purchaseOrederDataBean.setCashList(purchaselist);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return null;
	}
	
	@Override
	public String searchdate(PurchaseDataBean purchaseDataBean)
	{	
		ArrayList<Sale>salelist=null;
		ArrayList<StockoutManual>stoklist=null;
		ArrayList<Invoice>inlist=null;
		ArrayList<Payment>paylist=null;
		ArrayList<PurchaseDataBean>pulist=null;
		Query q=null;
		int i=1;
		try
		{			
		purchaseDataBean.setRes(null);
		pulist=new ArrayList<PurchaseDataBean>();
		q=entitymanager.createQuery("from Sale where salesOrderDate between ? and ? and (paymentStatus='pending' and salesDeliveryStatus='Delivered')");
		q.setParameter(1, purchaseDataBean.getFromDate());
		q.setParameter(2, purchaseDataBean.getToDate());
		salelist=(ArrayList<Sale>)q.getResultList();
        if(salelist.size()>0)
		{
			System.out.println("------------inside sales list----------------"+salelist.size());
			for (Sale salist:salelist)
            {
        	   q=null;
        	   q=entitymanager.createQuery("from Invoice where sales_ID=?");
        	   q.setParameter(1, salist.getSales_ID());
        	   inlist=(ArrayList<Invoice>)q.getResultList();
        	   System.out.println("invoice listttt"+inlist.size());
        	   if(inlist.size()>0)
        	   {
					System.out.println("------------invoice generated----------------"+salist.getTotalAmount());
					q=null;
					q=entitymanager.createQuery("from Payment where invoice_ID=? and paymentStatus='pending'");
					q.setParameter(1,inlist.get(0).getInvoice_ID());
					paylist=(ArrayList<Payment>)q.getResultList();
					System.out.println("payment size"+paylist.size());
					if(paylist.size()>0)
					{
						System.out.println("-----------inside payment pending----------");
						PurchaseDataBean purchasedatabean1=new PurchaseDataBean();
						purchasedatabean1.setSerialNo(i);
						purchasedatabean1.setReason("Sale");
						purchasedatabean1.setCustomerName(paylist.get(0).getInvoice().getSale().getProjet().getProjectName());
						System.out.println("ccccccustomername1111111>>>>>>++++++++"+purchasedatabean1.getCustomerName());
	
						purchasedatabean1.setOrderDate(paylist.get(0).getInvoice().getSale().getSalesOrderDate());
						purchasedatabean1.setOrderNumber(paylist.get(0).getInvoice().getSale().getSalesOrderNumber());
						purchasedatabean1.setTotalPrice(paylist.get(0).getTotalAmount());
						purchasedatabean1.setBalanceammount(""+(new java.math.BigDecimal(paylist.get(0).getTotalAmount()).subtract(new java.math.BigDecimal(paylist.get(0).getPaidAmount()))));
					    pulist.add(purchasedatabean1);
		                purchaseDataBean.setRes(pulist);
		                i++;
					}
					else
					{
						System.out.println("-----------inside not payment ---------");
						BigDecimal nr=BigDecimal.valueOf(0),price=BigDecimal.valueOf(0),price2=BigDecimal.valueOf(0),pric3e=BigDecimal.valueOf(0);
						BigDecimal total=BigDecimal.valueOf(0),total1=BigDecimal.valueOf(0);
						q=null;
						q=entitymanager.createQuery("from SalesRecord where sales_ID=?");
						q.setParameter(1, salist.getSales_ID());
						List<SalesRecord> rec=(List<SalesRecord>)q.getResultList();
						if(rec.size()>0)
						{						
							for (int j = 0; j < rec.size(); j++) 
							{
								q=null;
								q=entitymanager.createQuery("from SalesReturn where sales_record_ID=?");
								q.setParameter(1, rec.get(j).getSales_record_ID());
								List<SalesReturn> ret=(List<SalesReturn>)q.getResultList();
								if(ret.size()>0)
								{
									nr=new BigDecimal(ret.get(0).getNormalReturn()).add(new BigDecimal(ret.get(0).getDamageReturn()));
								}
								else
								{
									nr=BigDecimal.valueOf(0);
								}
								price=price.add(nr.multiply(new BigDecimal(rec.get(j).getProduct().getActualPrice())));
							}
							price2=new BigDecimal(salist.getCrossTotal());
						}
						pric3e=price2.subtract(price);
						if(salist.getTaxType().equals("NO Tax"))
						{
							total=pric3e;
						}
						else if(salist.getTaxType().equals("10% Tax"))
						{
							total=pric3e.add(pric3e.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
						}
						if(salist.getDiscountType().equals(""))
						{
							total1=total.setScale(0,BigDecimal.ROUND_HALF_UP);
						}
						else if(salist.getDiscountType().equals("IDR"))
						{
							total1=total.subtract(new BigDecimal(salist.getDiscountAmount())).setScale(0,BigDecimal.ROUND_HALF_UP);
						}
						else if(salist.getDiscountType().equals("%"))
						{
							total1=total.subtract(total.multiply(new BigDecimal(salist.getDiscountAmount())).divide(BigDecimal.valueOf(100))).setScale(0,BigDecimal.ROUND_HALF_UP);
						}	
						PurchaseDataBean purchasedatabean1=new PurchaseDataBean();
						purchasedatabean1.setSerialNo(i);
						purchasedatabean1.setReason("Sale");
						purchasedatabean1.setCustomerName(inlist.get(0).getSale().getProjet().getProjectName());
						System.out.println("ccccccustomername1111111>>>>>>++++++++"+purchasedatabean1.getCustomerName());

						purchasedatabean1.setOrderDate(inlist.get(0).getSale().getSalesOrderDate());
						purchasedatabean1.setOrderNumber(inlist.get(0).getSale().getSalesOrderNumber());
						purchasedatabean1.setTotalPrice(""+total1);
						purchasedatabean1.setBalanceammount(""+total1);
					    pulist.add(purchasedatabean1);
		                purchaseDataBean.setRes(pulist);
		                i++;
					}
    	   		}
				else
				{
					System.out.println("====invoice not generated===="+salist.getSales_ID());
					BigDecimal nr=BigDecimal.valueOf(0),price=BigDecimal.valueOf(0),price2=BigDecimal.valueOf(0),pric3e=BigDecimal.valueOf(0);
					BigDecimal total=BigDecimal.valueOf(0),total1=BigDecimal.valueOf(0);
					q=null;
					q=entitymanager.createQuery("from SalesRecord where sales_ID=?");
					q.setParameter(1, salist.getSales_ID());
					List<SalesRecord> rec=(List<SalesRecord>)q.getResultList();
					if(rec.size()>0)
					{						
						for (int j = 0; j < rec.size(); j++) 
						{
							q=null;
							q=entitymanager.createQuery("from SalesReturn where sales_record_ID=?");
							q.setParameter(1, rec.get(j).getSales_record_ID());
							List<SalesReturn> ret=(List<SalesReturn>)q.getResultList();
							if(ret.size()>0)
							{
								nr=new BigDecimal(ret.get(0).getNormalReturn()).add(new BigDecimal(ret.get(0).getDamageReturn()));
							}
							else
							{
								nr=BigDecimal.valueOf(0);
							}
							price=price.add(nr.multiply(new BigDecimal(rec.get(j).getProduct().getActualPrice())));
						}
						price2=new BigDecimal(salist.getCrossTotal());
					}
					pric3e=price2.subtract(price);
					if(salist.getTaxType().equals("NO Tax"))
					{
						total=pric3e;
					}
					else if(salist.getTaxType().equals("10% Tax"))
					{
						total=pric3e.add(pric3e.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
					}
					if(salist.getDiscountType().equals(""))
					{
						total1=total.setScale(0,BigDecimal.ROUND_HALF_UP);
					}
					else if(salist.getDiscountType().equals("IDR"))
					{
						total1=total.subtract(new BigDecimal(salist.getDiscountAmount())).setScale(0,BigDecimal.ROUND_HALF_UP);
					}
					else if(salist.getDiscountType().equals("%"))
					{
						total1=total.subtract(total.multiply(new BigDecimal(salist.getDiscountAmount())).divide(BigDecimal.valueOf(100))).setScale(0,BigDecimal.ROUND_HALF_UP);
					}					
					PurchaseDataBean purchasedatabean1=new PurchaseDataBean();
					purchasedatabean1.setSerialNo(i);
					purchasedatabean1.setReason("Sale");
					purchasedatabean1.setCustomerName(salist.getProjet().getProjectName());
					System.out.println("ccccccustomername1111<<<><<><><>"+purchasedatabean1.getCustomerName());
					purchasedatabean1.setOrderDate(salist.getSalesOrderDate());
					purchasedatabean1.setOrderNumber(salist.getSalesOrderNumber());
					purchasedatabean1.setTotalPrice(""+total1);
					purchasedatabean1.setBalanceammount(""+total1);
					pulist.add(purchasedatabean1);
		            purchaseDataBean.setRes(pulist);
		            i++;
				}
			}
    	}
		System.out.println("size===="+pulist.size());
        purchaseDataBean.setRes(pulist);
        System.out.println("size===++++++++===="+pulist.size());        
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}
	
	@Override
	public List<ProductDataBean> getDesignList(
			ProductDataBean productDataBean) {
		System.out.println("Inside getDesignList method calling in DAO"+productDataBean.getProdProductName1());
		Query q=null;
		int prodID=0;
		List<ProductDataBean> finalres=null;
		if(productDataBean.getProdProductName1() != null){
			prodID=getProductID(productDataBean.getProdProductName1());
			if(prodID >0){
			q=entitymanager.createQuery("from ImagePath where product_id=? and status='inserted'");
			q.setParameter(1, prodID);
			List<ImagePath> result=(List<ImagePath>)q.getResultList();
			if(result.size() > 0){
				finalres=new ArrayList<ProductDataBean>();
				for(int i=0;i<result.size();i++){
					ProductDataBean data=new ProductDataBean();
					data.setDesignDate(result.get(i).getDate());
					data.setDesignDesc(result.get(i).getDescription());
					data.setDesignProject(result.get(i).getProduct().getProductName());
					finalres.add(data);
				}
			}
			}
		}
		
		return finalres;
		
	}

	@Override
	public List<String> getDesignProjectList() {
		List<String> nameList=null;
		Query q=null;
		q=entitymanager.createQuery("from ImagePath where status='inserted'");
		List<ImagePath> result=(List<ImagePath>)q.getResultList();
		if(result.size() > 0){
			nameList=new ArrayList<String>();
			for(int i=0;i<result.size();i++){
				String name=result.get(i).getProduct().getProductName();
				nameList.add(name);
			}
		}
		return nameList;
	}

	@Override
	public List<ImagePath> getDesignListView(
			ProductDataBean productDataBean) {
		List<ImagePath> designList=null;
		int prodID=0;
		Query q=null;
		System.out.println("Inside DAO getDesignListView method calling");
		if(productDataBean.getDesignProject() != null){
			prodID=getProductID(productDataBean.getProdProductName1());
			if(prodID >0){
			q=entitymanager.createQuery("from ImagePath where product_id=? and status='inserted'");
			q.setParameter(1, prodID);
			designList=(List<ImagePath>)q.getResultList();
		}
		
	}
		return designList;
}

	@Override
	public List<String> getDesignProList() {
		List<String> projectname=null;
		Query q=null;
		try{
			q=entitymanager.createQuery("select productName from  Product where status=? and category='Project')");
			q.setParameter(1, "Active");
			projectname=(List<String>)q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return projectname;
	}

	@Override
	@Transactional(value="transactionManager")
	public String designEditSubmit(ProductDataBean productDataBean) {
String status = "Fail";
int prodID = 0;int imagepathID=0;
Query q = null;
try {
	if (productDataBean.getDesignProject() != null) {
		prodID = getProductID(productDataBean.getDesignProject());
		if (prodID > 0) {
			q = entitymanager
					.createQuery("from ImagePath where product_id=? and status='inserted'");
			q.setParameter(1, prodID);
			List<ImagePath> result = (List<ImagePath>) q.getResultList();
			if (result.size() > 0) {
				imagepathID=result.get(0).getPId();
				if (productDataBean.getFile() != null) {
					setFile(productDataBean.getFile());
					System.out.println("File List New"+imagepathID);
					ImagePath image=entitymanager.find(ImagePath.class, imagepathID);
					image.setDate(productDataBean.getDesignDate());
					image.setDescription(productDataBean.getDesignDesc());
					image.setFilePath(TransferFile(productDataBean.getFile().getName(), productDataBean.getFile().getInputStream(),productDataBean.getDesignProject()));
					entitymanager.merge(image);
					status = "Success";
				} else {
					System.out.println("File List Old"+imagepathID);
					ImagePath image=entitymanager.find(ImagePath.class, imagepathID);
					image.setDate(productDataBean.getDesignDate());
					image.setDescription(productDataBean.getDesignDesc());
					entitymanager.merge(image);
					status = "Success";
				}
			}
		}
	}
} catch (Exception e) {
	e.printStackTrace();
}
System.out.println(status);
return status;
}

	@Override
	@Transactional(value="transactionManager")
	public String designDelete(ProductDataBean productDataBean) {
		String status = "Fail";
		int prodID = 0;int imagepathID=0;
		Query q = null;
		try {
			if (productDataBean.getDesignProject() != null) {
				prodID = getProductID(productDataBean.getDesignProject());
				if (prodID > 0) {
					q = entitymanager
							.createQuery("from ImagePath where product_id=? and status='inserted'");
					q.setParameter(1, prodID);
					List<ImagePath> result = (List<ImagePath>) q.getResultList();
					if (result.size() > 0) {
						imagepathID=result.get(0).getPId();
						ImagePath image=entitymanager.find(ImagePath.class, imagepathID);
						image.setStatus("Removed");
						entitymanager.merge(image);
						status = "Success";
					}
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	
	@Override	
	public String viewAccountPayable(PurchaseOrederDataBean  purchaseOrder)throws  LiusenException 
	{
		System.out.println("CHECK <<<<<<<!!!>>>");
		Query q=null;
		ArrayList<Invoice> invoiceList=null;
		ArrayList<Payment> payamentList=null;
		ArrayList<PurchaseOrederDataBean> accountList=null;
		int i=0;
		try
		{
			System.out.println("CHECK <<<<<<<222>>>");
			purchaseOrder.setResulfinal(null);
			accountList=new ArrayList<PurchaseOrederDataBean>();
			System.out.println("---------------inside viewaccountpayable------------");
			System.out.println("from dateeeeee---------------->"+purchaseOrder.getPurFromDate());
			System.out.println("to dateeeeee---------------->"+purchaseOrder.getPurToDate());
			q=entitymanager.createQuery("from Purchase where purchaseOrderDate between ? and ? and status='ordered' and paymentStatus='pending' and deliveryStatus='Approve'");
			q.setParameter(1, purchaseOrder.getPurFromDate());
			q.setParameter(2, purchaseOrder.getPurToDate());
			System.out.println("checkkkkkkk");
			List<Purchase> purchaseList=(ArrayList<Purchase>)q.getResultList();
			System.out.println("CHECK <<<<<<3333>>>"+purchaseList.size());
			System.out.println("checkkkk1");
			
			if(purchaseList.size()>0)
			{
				System.out.println("------------inside purchase list----------------"+purchaseList.size());
	
				for (Purchase purchaseList1 : purchaseList) 
					
				{
					
					
					q=null;
					q=entitymanager.createQuery("from Invoice where purchase_ID=?");
					q.setParameter(1, purchaseList1.getPurchase_ID());
					System.out.println("CHECK PURCHASE ID"+purchaseList1.getPurchase_ID());
					
					invoiceList=(ArrayList<Invoice>)q.getResultList();
					System.out.println("CHECK INVOICE"+invoiceList.size());
					if(invoiceList.size()>0)
					{
					
						System.out.println("-----------invoice generated-------------"+purchaseList1.getPurchase_ID());
						q=null;
						q=entitymanager.createQuery("from Payment where invoice_ID=? and paymentStatus='pending'");
						q.setParameter(1, invoiceList.get(0).getInvoice_ID());
						System.out.println("CHECK INVOICE ID"+invoiceList.get(0).getInvoice_ID());
						payamentList=(ArrayList<Payment>)q.getResultList();
						System.out.println("CHECK PAYMENT"+payamentList.size());
						if(payamentList.size()>0)
						{
							i++;
							System.out.println("-----------inside payment pending----------");
							PurchaseOrederDataBean  purchaseOrder2=new PurchaseOrederDataBean ();
							purchaseOrder2.setSerialNo(i);
							purchaseOrder2.setReason("Purchase");
							purchaseOrder2.setPurFromDate(purchaseList1.getPurchaseOrderDate());
							purchaseOrder2.setPurOrderNo(purchaseList1.getPurchaseOrderNumber());
							purchaseOrder2.setPurTotalPrice(payamentList.get(0).getTotalAmount());
							purchaseOrder2.setPurCrossTotal(""+(new BigDecimal(payamentList.get(0).getTotalAmount()).subtract(new BigDecimal(payamentList.get(0).getPaidAmount()))));
							accountList.add(purchaseOrder2);
							
						}
						else 
						{				

							i++;
							System.out.println("-----------invoice  generated---- not paymnet---------"+purchaseList1.getPurchase_ID());
							BigDecimal nr=BigDecimal.valueOf(0),price=BigDecimal.valueOf(0),price2=BigDecimal.valueOf(0),pric3e=BigDecimal.valueOf(0);
							BigDecimal total=BigDecimal.valueOf(0),total1=BigDecimal.valueOf(0);
							q=null;
							q=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
							q.setParameter(1, purchaseList1.getPurchase_ID());
							List<PurchaseRecord> rec=(List<PurchaseRecord>)q.getResultList();
							if(rec.size()>0)
							{						
								for (int j = 0; j < rec.size(); j++) 
								{
									q=null;
									q=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
									q.setParameter(1, rec.get(j).getPurchase_record_ID());
									List<PurchaseReturn> ret=(List<PurchaseReturn>)q.getResultList();
									if(ret.size()>0)
									{
										nr=new BigDecimal(ret.get(0).getNormalReturn()).add(new BigDecimal(ret.get(0).getDamageReturn()));
									}
									else
									{
										nr=BigDecimal.valueOf(0);
									}
									price=price.add(nr.multiply(new BigDecimal(rec.get(j).getUnitPrice())));
								}
								price2=new BigDecimal(purchaseList1.getCrossTotal());
							}
							pric3e=price2.subtract(price);
							if(purchaseList1.getTypeofProject().equals("No Tax"))
							{
								total=pric3e;
							}
							else if(purchaseList1.getTypeofProject().equals("10% Tax"))
							{
								total=pric3e.add(pric3e.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
							}
							if(purchaseList1.getDiscountType().equals(""))
							{
								total1=total.setScale(0,BigDecimal.ROUND_HALF_UP);
							}
							else if(purchaseList1.getDiscountType().equals("IDR"))
							{
								total1=total.subtract(new BigDecimal(purchaseList1.getDiscountAmount())).setScale(0,BigDecimal.ROUND_HALF_UP);
							}
							else if(purchaseList1.getDiscountType().equals("%"))
							{
								total1=total.subtract(total.multiply(new BigDecimal(purchaseList1.getDiscountAmount())).divide(BigDecimal.valueOf(100))).setScale(0,BigDecimal.ROUND_HALF_UP);
							}
							PurchaseOrederDataBean  purchaseOrder2=new PurchaseOrederDataBean();
							purchaseOrder2.setSerialNo(i);
							purchaseOrder2.setReason("Purchase");
							purchaseOrder2.setPurFromDate(invoiceList.get(0).getPurchase().getPurchaseOrderDate());
							purchaseOrder2.setPurOrderNo(invoiceList.get(0).getPurchase().getPurchaseOrderNumber());
							purchaseOrder2.setPurTotalPrice(""+total1);
							purchaseOrder2.setPurCrossTotal(""+total1);
							accountList.add(purchaseOrder2);
						}
					}
					else
					{
						i++;
						System.out.println("-----------invoice not generated-------------"+purchaseList1.getPurchase_ID());
						BigDecimal nr=BigDecimal.valueOf(0),price=BigDecimal.valueOf(0),price2=BigDecimal.valueOf(0),pric3e=BigDecimal.valueOf(0);
						BigDecimal total=BigDecimal.valueOf(0),total1=BigDecimal.valueOf(0);
						q=null;
						q=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
						q.setParameter(1, purchaseList1.getPurchase_ID());
						List<PurchaseRecord> rec=(List<PurchaseRecord>)q.getResultList();
						System.out.println("pur rec size  -- > "+rec.size());						
						if(rec.size()>0)
						{		
							for (int j = 0; j < rec.size(); j++) 
							{
								System.out.println("pur rec id  -- > "+rec.get(j).getPurchase_record_ID());
								q=null;
								q=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
								q.setParameter(1, rec.get(j).getPurchase_record_ID());
								List<PurchaseReturn> ret=(List<PurchaseReturn>)q.getResultList();
								if(ret.size()>0)
								{
									nr=new BigDecimal(ret.get(0).getNormalReturn()).add(new BigDecimal(ret.get(0).getDamageReturn()));
								}
								else
								{
									nr=BigDecimal.valueOf(0);
								}
								price=price.add(nr.multiply(new BigDecimal(rec.get(j).getUnitPrice())));
							}
							System.out.println("return amnt  -- > "+price);
							price2=new BigDecimal(purchaseList1.getCrossTotal());
							System.out.println("total -- > "+price2);
						}
						pric3e=price2.subtract(price);
						System.out.println("amnt -- > "+pric3e);
						if(purchaseList1.getTypeofProject().equals("No Tax"))
						{
							total=pric3e;
							System.out.println("no tax - > "+total +" - "+pric3e);
						}
						else if(purchaseList1.getTypeofProject().equals("10% Tax"))
						{
							total=pric3e.add(pric3e.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
							System.out.println("tax - > "+total +" - "+pric3e);
						}
						System.out.println("after tax  -- > "+total);
						if(purchaseList1.getDiscountType().equals(""))
						{
							total1=total.setScale(0,BigDecimal.ROUND_HALF_UP);
							System.out.println("no dis - > "+total +" - "+total1);
						}					
						else if(purchaseList1.getDiscountType().equals("IDR"))
						{
							total1=total.subtract(new BigDecimal(purchaseList1.getDiscountAmount())).setScale(0,BigDecimal.ROUND_HALF_UP);
							System.out.println("idr - > "+total +" - "+total1);
						}
						else if(purchaseList1.getDiscountType().equals("%"))
						{
							total1=total.subtract(total.multiply(new BigDecimal(purchaseList1.getDiscountAmount())).divide(BigDecimal.valueOf(100))).setScale(0,BigDecimal.ROUND_HALF_UP);
							System.out.println("% - > "+total +" - "+total1);
						}
						System.out.println("after discount  -- > "+total1);
						PurchaseOrederDataBean  purchaseOrder2=new PurchaseOrederDataBean();
						purchaseOrder2.setSerialNo(i);
						purchaseOrder2.setReason("Purchase");
						purchaseOrder2.setPurFromDate(purchaseList1.getPurchaseOrderDate());
						purchaseOrder2.setPurOrderNo(purchaseList1.getPurchaseOrderNumber());
						purchaseOrder2.setPurTotalPrice(""+total1);
						purchaseOrder2.setPurCrossTotal(""+total1);
						accountList.add(purchaseOrder2);
						
					}
					
				}
				
			}
			System.out.println("<<<<<<<<<<<444444444444>>>>>>>>>>>");
			System.out.println("sizeeeeee-------------NNNNNNNNN->");
			System.out.println("sizeeeeee-------------1->"+accountList.size());
			purchaseOrder.setResulfinal(accountList);
			System.out.println("size>>>>>>>>>>>2>>>>>>>"+purchaseOrder.getResulfinal().size());
			
		}
		catch(Exception e)
		{
			System.out.println("-----------inside exception----------");
		}
		return null;
	}
	
	@Override
	public String purchasedate(PurchaseOrederDataBean purchaseOrederDataBean) {
		// TODO Auto-generated method stub
		java.math.BigDecimal total=java.math.BigDecimal.valueOf(0);
		System.out.println("==-=-=-=,.,.,.,.,.inside purchase date <><><<-=-=-=-=");
		Query q=null;Query v=null;
		ArrayList<Purchase>purchase=null;
		ArrayList<PurchaseOrederDataBean>purchasedomain=new ArrayList<PurchaseOrederDataBean>();
		ArrayList<PurchaseOrederDataBean>report=new ArrayList<PurchaseOrederDataBean>();
		BigDecimal total2=BigDecimal.valueOf(0),total1=BigDecimal.valueOf(0);
		try
		{
			System.out.println("<>>>>>>>>>>>>purchase date><<<<<<<<<<<<<<<<");
			System.out.println("TO DATE"+purchaseOrederDataBean.getPurToDate());
			System.out.println("FROM DATE"+purchaseOrederDataBean.getPurFromDate());
			q=entitymanager.createQuery("from Purchase where purchaseOrderDate between ? and ? and (paymentStatus='paid' and status='ordered')");
			q.setParameter(1, purchaseOrederDataBean.getPurFromDate());
			q.setParameter(2, purchaseOrederDataBean.getPurToDate());
			List<Purchase>purlist=(ArrayList<Purchase>)q.getResultList();
			System.out.println("SIZE OF PURLIST"+purlist.size());
			if(purlist.size()>0)
			{
			
				for (int i = 0; i < purlist.size(); i++)
				{
					int  c=0;					
					v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
					v.setParameter(1, purlist.get(i).getPurchase_ID());
					List<PurchaseRecord> salrec=(List<PurchaseRecord>) v.getResultList();
					System.out.println("purchase record size -- > "+salrec.size());
					if(salrec.size()>0)
					{						
						for (int j = 0; j < salrec.size(); j++)
						{						
							v=null;
							v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
							v.setParameter(1, salrec.get(j).getPurchase_record_ID());
							List<PurchaseReturn> salret=(List<PurchaseReturn>)v.getResultList();
							if(salret.size()>0)
							{
								c++;
							}
						}
					}
					PurchaseOrederDataBean doobj=new PurchaseOrederDataBean();
					doobj.setPurVendor(purlist.get(i).getVendor().getFirmName());
					doobj.setPurdate(purlist.get(i).getPurchaseOrderDate());
					doobj.setPurOrderNo(purlist.get(i).getPurchaseOrderNumber());
					if(c>0)
					{
						v=null;
						v=entitymanager.createQuery("from Invoice where purchase_ID=?");
						v.setParameter(1, purlist.get(i).getPurchase_ID());
						List<Invoice> inv=(List<Invoice>) v.getResultList();
						int invid=0;
						if(inv.size()>0)
						{
							invid=inv.get(0).getInvoice_ID();
							System.out.println("invoice id - > "+invid);
							v=null;
							v=entitymanager.createQuery("from Payment where invoice_ID=?");
							v.setParameter(1, invid);
							List<Payment> payy=(List<Payment>) v.getResultList();
							if(payy.size()>0)
							{
								doobj.setPurTotalPrice(payy.get(0).getTotalAmount());
								total1=new BigDecimal(payy.get(0).getTotalAmount());
							}
						}
					}
					else
					{
						doobj.setPurTotalPrice(purlist.get(i).getTotalAmount());
						total1=new BigDecimal(purlist.get(i).getTotalAmount());
					}					
					purchasedomain.add(doobj);
					purchaseOrederDataBean.setPurchaseList(purchasedomain);			
					total=total.add(total1);
				}				
				purchaseOrederDataBean.setPurprice(""+total);
			}
			else
			{
				System.out.println("No value");
				purchaseOrederDataBean.setPurprice(""+total);
			}			
		}		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<String> dropProject(ProjectDataBean projectDataBean) {
		
		List<String> projectList=null;
		Query q=null;
		try{
			q=entitymanager.createQuery("select projectName from Projet where status='Active'");
			projectList=(List<String>)q.getResultList();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return projectList;
	}

	@Override
	public List<ProjectDataBean> getProjectView(ProjectDataBean projectDataBean) {
		System.out.println("Inside getProjectView method calling in DAO");
		Query q=null;Query q1=null;Query q2=null;Query q3=null;
		int projectID=0;
		List<ProjectDataBean> ProjectViewList=null;
		try{
			if(projectDataBean.getProjProjectName() !=null){
				/*
				projectID=getProductID(projectDataBean.getProjProjectName());*/
				q=entitymanager.createQuery("from Projet where projectName=? and status='Active'");
				q.setParameter(1, projectDataBean.getProjProjectName());
				List<Projet> result1=(List<Projet>)q.getResultList();
				if(result1.size() > 0){
					projectID=result1.get(0).getProject_ID();
					if(projectID >0){
						ProjectDataBean DataBean = new ProjectDataBean();
						ProjectViewList=new ArrayList<ProjectDataBean>();
						q1=entitymanager.createQuery("from ProjectEmployee where project_ID=? and status='Project Manager'");
					q1.setParameter(1, projectID);
					List<ProjectEmployee> result2=(List<ProjectEmployee>)q1.getResultList();
					if(result2.size() >0){
						DataBean.setProjProjectManager(result2.get(0).getEmployee().getEmployee_details_ID());
					}
					q2=entitymanager.createQuery("from ProjectEmployee where project_ID=? and status='Marketing Manager'");
					q2.setParameter(1, projectID);
					List<ProjectEmployee> result3=(List<ProjectEmployee>)q2.getResultList();
					if(result3.size() >0){
						DataBean.setProjMarketing(result3.get(0).getEmployee().getEmployee_details_ID());
					}
					q3=entitymanager.createQuery("from ProjectEmployee where project_ID=? and status='Admin Project'");
					q3.setParameter(1, projectID);
					List<ProjectEmployee> result4=(List<ProjectEmployee>)q3.getResultList();
					if(result4.size() >0){
						DataBean.setProjAdminProject(result4.get(0).getEmployee().getEmployee_details_ID());	
					}
					DataBean.setProjProjectName(result1.get(0).getProjectName());
					DataBean.setProjClientName(result1.get(0).getClientName());
					DataBean.setProjProjectValue(result1.get(0).getProjectValue());
					ProjectViewList.add(DataBean);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ProjectViewList;
	}

	@Override
	public List<Projet> getProjectListView(ProjectDataBean projectDataBean,List<ProjectDataBean> projectViewList) {
		List<Projet> projectList=null;
		projectViewList=null;
		Query q=null;Query q1=null; int projectID=0;int prodID=0;Query q2=null;
		if(projectDataBean.getProjProjectName() !=null){
			try{
				q=entitymanager.createQuery("from Projet where projectName=? and status='Active'");
				q.setParameter(1, projectDataBean.getProjProjectName());
				projectList=(List<Projet>)q.getResultList();
				if(projectList.size() >0){
					projectID=projectList.get(0).getProject_ID();
							
					if(projectID > 0){
					q1=entitymanager.createQuery("from ProjectEmployee where project_ID=?");
					q1.setParameter(1, projectID);
					List<ProjectEmployee> result2=(List<ProjectEmployee>)q1.getResultList();
					if(result2.size() > 0){
						projectViewList=new ArrayList<ProjectDataBean>();
						System.out.println(result2.get(1).getEmployee().getEmployee_details_ID());
						System.out.println(result2.get(0).getEmployee().getEmployee_details_ID());
						System.out.println(result2.get(2).getEmployee().getEmployee_details_ID());
						prodID=getProductID(projectDataBean.getProjProjectName());
						projectDataBean.setFileName(projectList.get(0).getFilePath());						
						projectDataBean.setProjMarketing(result2.get(1).getEmployee().getEmployee_details_ID());
						projectDataBean.setProjAdminProject(result2.get(2).getEmployee().getEmployee_details_ID());
						projectDataBean.setProjProjectManager(result2.get(0).getEmployee().getEmployee_details_ID());
						projectViewList.add(projectDataBean);
					}
				}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println(projectList.size());
		return projectList;
	}

	@Override
	@Transactional(value="transactionManager")
	public String updateProject(ProjectDataBean projectDataBean) {
		String status="fail";
		int ProjID=0; int adminID=0;int projEmpID=0;int marketEMpID=0;
		int hasPmId=0;int hasAmId=0;int hasMmId=0;
		try{
		if(projectDataBean.getProjProjectName() != null){
			Query q=null;
			Date date=new Date();
			q=entitymanager.createQuery("from Projet where projectName=? and status='Active'");
			q.setParameter(1, projectDataBean.getProjProjectName());
			List<Projet> projet1=(List<Projet>) q.getResultList();
			
			if(projet1.size() >0){
				ProjID=projet1.get(0).getProject_ID();
			Projet pro=entitymanager.find(Projet.class, ProjID);
			pro.setProjectValue(projectDataBean.getProjProjectValue());
			pro.setProjectDate1(projectDataBean.getProjDate());
			pro.setProjectDate2(projectDataBean.getProjDate1());
			pro.setProjectDate3(projectDataBean.getProjDate2());
			pro.setAddress(projectDataBean.getProjAddress());
			pro.setClientName(projectDataBean.getProjClientName());
			pro.setContactNumber(projectDataBean.getProjContactNO());
			pro.setDescription(projectDataBean.getProjDescription());
			pro.setEditDate(date);
			pro.setEditLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			pro.setEditStatus("Edited");
			pro.setLocation(projectDataBean.getProjLocation());
			pro.setState(projectDataBean.getProjLocation());
			entitymanager.merge(pro);
			
			projEmpID=getMarkEmpId(projectDataBean.getProjProjectManager());
			if(projEmpID > 0){
				hasPmId=getPMEmpID(ProjID);
				if(hasPmId > 0){
					ProjectEmployee proemp=entitymanager.find(ProjectEmployee.class, hasPmId);
					proemp.setEmployee(entitymanager.find(Employee.class, projEmpID));
					entitymanager.merge(proemp);
				}
			}
			marketEMpID=getMarkEmpId(projectDataBean.getProjMarketing());
			if(marketEMpID > 0){
				hasMmId=getMMEmpID(ProjID);
				if(hasMmId > 0){
					ProjectEmployee proemp1=entitymanager.find(ProjectEmployee.class, hasMmId);
					proemp1.setEmployee(entitymanager.find(Employee.class, marketEMpID));
					entitymanager.merge(proemp1);
				}
			}
			adminID=getMarkEmpId(projectDataBean.getProjAdminProject());
			if(adminID > 0){
				hasAmId=getAMEmpID(ProjID);
				if(hasAmId > 0){
					ProjectEmployee proemp2=entitymanager.find(ProjectEmployee.class, hasAmId);
					proemp2.setEmployee(entitymanager.find(Employee.class, adminID));
					entitymanager.merge(proemp2);
					status="Sucesss";
				}
			}
			
			
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}

	private int getMarkEmpId(String projMarketing) {
		int empID=0;
		Query v=null;
		v=entitymanager.createQuery("from Employee where employee_details_ID=? and status='Active'");
		v.setParameter(1, projMarketing);
		List<Employee> employee=(List<Employee>) v.getResultList();
		if(employee.size() > 0){
			empID=employee.get(0).getEmployee_ID();
		}
		return empID;
	}

	private int getPMEmpID(int projID) {
		int hasPM=0;
		Query v=null;
		v=entitymanager.createQuery("from ProjectEmployee where project_ID=? and status='Project Manager'");
		v.setParameter(1, projID);
		List<ProjectEmployee> employee=(List<ProjectEmployee>) v.getResultList();
		if(employee.size() > 0){
			hasPM=employee.get(0).getHas_project_ID();
			System.out.println();
		}
		return hasPM;
	}
	private int getMMEmpID(int projID) {
		int hasMM=0;
		Query v=null;
		v=entitymanager.createQuery("from ProjectEmployee where project_ID=? and status='Marketing Manager'");
		v.setParameter(1, projID);
		List<ProjectEmployee> employee=(List<ProjectEmployee>) v.getResultList();
		if(employee.size() > 0){
			hasMM=employee.get(0).getHas_project_ID();
		}
		return hasMM;
	}
	private int getAMEmpID(int projID) {
		int hasAM=0;
		Query v=null;
		v=entitymanager.createQuery("from ProjectEmployee where project_ID=? and status='Admin Project'");
		v.setParameter(1, projID);
		List<ProjectEmployee> employee=(List<ProjectEmployee>) v.getResultList();
		if(employee.size() > 0){
			hasAM=employee.get(0).getHas_project_ID();
		}
		return hasAM;
	}

	@Override
	@Transactional(value="transactionManager")
	public String deleteProject(ProjectDataBean projectDataBean) {
		String status="fail";
		int ProjID=0;
		if(projectDataBean.getProjProjectName() != null){
			Query q=null;
			Date date=new Date();
			q=entitymanager.createQuery("from Projet where projectName=? and status='Active'");
			q.setParameter(1, projectDataBean.getProjProjectName());
			List<Projet> projet1=(List<Projet>) q.getResultList();
			
			if(projet1.size() >0){
				ProjID=projet1.get(0).getProject_ID();
			Projet pro=entitymanager.find(Projet.class, ProjID);
			pro.setEditDate(date);
			pro.setEditLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			pro.setEditStatus("Removed");
			pro.setStatus("De-Active");
			entitymanager.merge(pro);
			status="Sucess";
		}
	
	}
		return status;
	}

	@Override
	public List<ProjectDataBean> getProjectViewbyClient(
			ProjectDataBean projectDataBean) {
		System.out.println("Inside getProjectView method calling in DAO");
		Query q=null;Query q1=null;Query q2=null;Query q3=null;
		int projectID=0;
		List<ProjectDataBean> ProjectViewList=null;
		try{
			if(projectDataBean.getProjProjectName() !=null){
				/*
				projectID=getProductID(projectDataBean.getProjProjectName());*/
				q=entitymanager.createQuery("from Projet where clientName Like ?  and status='Active'");
				q.setParameter(1, "%"+projectDataBean.getProjClientName()+"%");
				List<Projet> result1=(List<Projet>)q.getResultList();
				if(result1.size() > 0){
						for(int i=0;i<result1.size();i++){
							projectID=result1.get(i).getProject_ID();
						ProjectDataBean DataBean = new ProjectDataBean();
						ProjectViewList=new ArrayList<ProjectDataBean>();
						q1=entitymanager.createQuery("from ProjectEmployee where project_ID=? and status='Project Manager'");
					q1.setParameter(1, projectID);
					List<ProjectEmployee> result2=(List<ProjectEmployee>)q1.getResultList();
					if(result2.size() >0){
						DataBean.setProjProjectManager(result2.get(0).getEmployee().getEmployee_details_ID());
					}
					q2=entitymanager.createQuery("from ProjectEmployee where project_ID=? and status='Marketing Manager'");
					q2.setParameter(1, projectID);
					List<ProjectEmployee> result3=(List<ProjectEmployee>)q2.getResultList();
					if(result3.size() >0){
						DataBean.setProjMarketing(result3.get(0).getEmployee().getEmployee_details_ID());
					}
					q3=entitymanager.createQuery("from ProjectEmployee where project_ID=? and status='Admin Project'");
					q3.setParameter(1, projectID);
					List<ProjectEmployee> result4=(List<ProjectEmployee>)q3.getResultList();
					if(result4.size() >0){
						DataBean.setProjAdminProject(result4.get(0).getEmployee().getEmployee_details_ID());	
					}
					DataBean.setProjProjectName(result1.get(0).getProjectName());
					DataBean.setProjClientName(result1.get(0).getClientName());
					DataBean.setProjProjectValue(result1.get(0).getProjectValue());
					ProjectViewList.add(DataBean);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ProjectViewList;
	}
	
	@Override
	public String searchpayroll(EmployeeDataBean employeeDataBean) {
		// TODO Auto-generated method stub
		ArrayList<Payroll>paylist=null;
		ArrayList<EmployeeDataBean>emplist=null;
		Query q=null;
		try
		{
			emplist=new ArrayList<EmployeeDataBean>();
			q=entitymanager.createQuery("from Payroll where  payDate between ? and ? and status='Active'");
			q.setParameter(1, employeeDataBean.getFromDate());
			q.setParameter(2, employeeDataBean.getToDate());
			paylist=(ArrayList<Payroll>)q.getResultList();
			System.out.println("palylist sise<<<>>>><<<<<>>>"+paylist.size());
			if(paylist.size()>0)
			{
				System.out.println("())(()()()()()()paylist)(()(()()()()"+paylist.size());
				for(Payroll pay:paylist)
				{
					EmployeeDataBean emp=new EmployeeDataBean();
					emp.setEmpPayTodayDate(pay.getPayDate());
					emp.setEmpPayempId(pay.getEmployee().getEmployee_details_ID());
					emp.setEmpEmployeeName(pay.getEmployee().getEmployeeName());
					emp.setEmpPayTotalSalary(pay.getTotalSalary());
					emplist.add(emp);
					employeeDataBean.setPayrollList(emplist);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional(value="transactionManager")
	public String openingStock(ProductDataBean productDataBean)throws LiusenException
	{
		Query v=null;
		Date dd=new Date();
		try
		{
			v=entitymanager.createQuery("from OpeningStock where productName=? and category=? and status='add'");
			v.setParameter(1, productDataBean.getProdProductName());
			v.setParameter(2, productDataBean.getProdCategory());
			List<OpeningStock> opstk=(List<OpeningStock>) v.getResultList();
			if(opstk.size()>0)
			{
				throw new LiusenException("Already have Opening Stock for "+productDataBean.getProdProductName());
			}
			else
			{
				 if(productDataBean.getOpeningStock().equals(""))
				 {
					throw new LiusenException("Enter the Opening Stock");
				 }
				OpeningStock stkadd=new OpeningStock();
				stkadd.setQuantity(productDataBean.getOpeningStock());
				stkadd.setProductName(productDataBean.getProdProductName());
				stkadd.setProductCode(productDataBean.getProdProductCode());
				stkadd.setCategory(productDataBean.getProdCategory());
				stkadd.setStatus("Add");
				stkadd.setStockDate(dd);
				entitymanager.persist(stkadd);
			}
		}
		finally
		{
			
		}
		return "";
	}

	@Transactional(value="transactionManager")
	public String purchasetable(ProductDataBean productDataBean)
	{
		Date d= new Date();
		Timestamp t=new Timestamp(d.getTime());
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
		Purchase po=new Purchase();
		po.setCrossTotal("0");
		po.setDiscountAmount("0");
		po.setDiscountType("0");
		po.setPurchaseDate(d);
		po.setPurchaseEstimatedDate(d);
		po.setPurchaseOrderDate(d);
		po.setStatus("add");
		po.setTotalAmount("0");
		po.setPurchaseOrderNumber("00");
		po.setEditStatus("none");
		po.setEditLoginStatus("none");
		po.setLoginStatus(login);
		po.setPurchaseTime(t);
		po.setPurchaseApprovalStatus("add");
		po.setCategory(productDataBean.getProdCategory());
		po.setTypeofProject("0");
		po.setPaymentStatus("add");
		po.setDeliveryStatus("add");
		po.setStatus2("add");
		entitymanager.persist(po);
		Query v=null;
		v=entitymanager.createQuery("from Purchase");
		List<Purchase> pur=(List<Purchase>) v.getResultList();
		if(pur.size()>0)
		{
			productDataBean.setPurchaseid(pur.get(pur.size()-1).getPurchase_ID());
		}
		return null;
	}

	@Transactional(value="transactionManager")
	public String stocktable(ProductDataBean productDataBean) 
	{	
		Date d= new Date();
		Timestamp t=new Timestamp(d.getTime());
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
		Stock stk=new Stock();
		stk.setStatus("Active");
		stk.setLoginStatus(login);
		stk.setOrderDate(d);
		stk.setPurchase(entitymanager.find(Purchase.class,productDataBean.getPurchaseid()));
		stk.setStockInDate(d);
		stk.setStockInTime(t);
		entitymanager.persist(stk);
		Query v=null;
		v=entitymanager.createQuery("from Stock");
		List<Stock> pur1=(List<Stock>) v.getResultList();
		if(pur1.size()>0)
		{
			productDataBean.setStockid(pur1.get(pur1.size()-1).getStock_ID());
		}
		return null;
	}

	@Transactional(value="transactionManager")
	public String batchtable(ProductDataBean productDataBean)
	{	
		Date d= new Date();
		Timestamp t=new Timestamp(d.getTime());
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");		
		Query v=null;
		v=entitymanager.createQuery("from Batch where productName=? and category=? and status='Active'");
		v.setParameter(1, productDataBean.getProdProductName());
		v.setParameter(2, productDataBean.getProdCategory());
		List<Batch> batch=(List<Batch>) v.getResultList();
		if(batch.size()>0)
		{
			Batch btt=entitymanager.find(Batch.class, batch.get(0).getBatch_ID());
			btt.setLoginStatus(login);
			btt.setStatus("Active");
			btt.setStock(entitymanager.find(Stock.class, productDataBean.getStockid()));
			entitymanager.merge(btt);
			productDataBean.setBatchid(batch.get(0).getBatch_ID());
		}
		else
		{
			v=null;
			Batch btt=new Batch();
			if(productDataBean.getProdCategory().equals("Product Trading"))
			{
				v=entitymanager.createQuery("from Product where category='Product Trading' and productName=? and status='Active'");
				v.setParameter(1, productDataBean.getProdProductName());
				List<Product> product=(List<Product>)v.getResultList();
				btt.setUnit(product.get(0).getUnit());
				btt.setUnitPrice(product.get(0).getActualPrice());
				btt.setVendor(entitymanager.find(Vendor.class, product.get(0).getVendor().getVendor_ID()));
			}
			else if(!productDataBean.getProdCategory().equals("Product Trading"))
			{
				v=entitymanager.createQuery("from RawMaterial where category=? and productName=? and status='Active'");
				v.setParameter(1, productDataBean.getProdCategory());
				v.setParameter(2, productDataBean.getProdProductName());
				List<RawMaterial> product=(List<RawMaterial>)v.getResultList();
				btt.setUnit(product.get(0).getUnit());
				btt.setUnitPrice(product.get(0).getActualPrice());
				btt.setVendor(entitymanager.find(Vendor.class, product.get(0).getVendor().getVendor_ID()));
			}	
			btt.setCategory(productDataBean.getProdCategory());
			btt.setLoginStatus(login);
			btt.setStatus("Active");
			btt.setStock(entitymanager.find(Stock.class, productDataBean.getStockid()));
			btt.setProductName(productDataBean.getProdProductName());
			entitymanager.persist(btt);
			v=null;
			v=entitymanager.createQuery("from Batch");
			List<Batch> batch1=(List<Batch>) v.getResultList();
			if(batch1.size()>0)
			{
				productDataBean.setBatchid(batch1.get(batch1.size()-1).getBatch_ID());
			}
		}
		return null;
	}

	@Transactional(value="transactionManager")
	public String barcodetable(ProductDataBean productDataBean) 
	{	
		Query v=null;		
		v=entitymanager.createQuery("from Barcode where batch_ID=? and status='Barcode Generated'");
		v.setParameter(1, productDataBean.getBatchid());
		List<Barcode> barcode=(List<Barcode>)v.getResultList();
		if(barcode.size()>0)
		{
			Barcode bar=entitymanager.find(Barcode.class, barcode.get(0).getBarcode_ID());
			bar.setStockIn(""+(new BigDecimal(productDataBean.getOpeningStock()).add(new BigDecimal(barcode.get(0).getStockIn()))));
			entitymanager.persist(bar);
		}
		else
		{
			Barcode bar=new Barcode();
			bar.setBatch(entitymanager.find(Batch.class, productDataBean.getBatchid()));
			bar.setStatus("Barcode Generated");
			bar.setStockIn(productDataBean.getOpeningStock());
			bar.setStockOut("0");
			bar.setStatus2("");
			entitymanager.persist(bar);
		}
		return null;
	}
}