package com.nrg.liusen.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.CustomerDataBean;
import com.nrg.liusen.util.CommonValidate;
/**
 * 
 * @author Robert Arjun
 * @date 26-10-2015
 * @copyright NRG 
 * This class works as a ManagedBean
 *
 */
@ManagedBean(name ="customerMB")
@RequestScoped
public class CustomerMB {
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	CustomerDataBean customerDataBean= new CustomerDataBean();
	private List<String> idlist = null;

	public List<String> getIdlist() {
		return idlist;
	}

	public void setIdlist(List<String> idlist) {
		this.idlist = idlist;
	}

	public List<String> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<String> countryList) {
		this.countryList = countryList;
	}

	/**
	 * @return the customerDataBean
	 */
	public CustomerDataBean getCustomerDataBean() {
		return customerDataBean;
	}

	/**
	 * @param customerDataBean the customerDataBean to set
	 */
	public void setCustomerDataBean(CustomerDataBean customerDataBean) {
		this.customerDataBean = customerDataBean;
	}
	
	private boolean valid = true; 
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	private List<String> countryList = null;
	/**
	 * customerPageLoad Method is used for redirect to vendor registration form
	 * page
	 * 
	 * @return success go to Customer registration form page
	 */
	public String customerPageLoad() {
		valid=true;
		System.out.println("Inside customerPageLoad Method Calling ");
		customerDataBean.setCustCity("");
		customerDataBean.setCustCountry("");
		customerDataBean.setCustCustomerName("");
		customerDataBean.setCustDate(null);
		customerDataBean.setCustEmail("");
		customerDataBean.setCustNote("");
		customerDataBean.setCustPhoneNumber("");
		customerDataBean.setCustShipingAddress("");
		customerDataBean.setCustStaffName("");
		customerDataBean.setCustStaffId("");
		customerDataBean.setCustState("");
		customerDataBean.setCustTaxNO("");
		return "customerLoadPage";
		
	}
	/**
	 * submit Method is used to get the values from UI to Controller
	 * 
	 * @return submitSuccess go to controller
	 * @return submitFailure redirect same page
	 */
	public String submit() {
		LiusenController controller = null;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			System.out.println("----------Inside submit Method Calling-----"+customerDataBean.getCustDate());
			if(validate(true)){
				System.out.println("After Validation inside Submit method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				status = controller.insertCustomer(customerDataBean);
			System.out.println("status===="+status);
			if(status.equalsIgnoreCase("exist"))
			{
				fieldName = CommonValidate.findComponentInRoot("cus_phno").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Phone number already exist"));
			}
			else if(status.equalsIgnoreCase("fail"))
			{
				System.out.println("");
			}
			else{
				valid=false;
				return "";
				
			}
			}

			return "";
		} catch (Exception e) {
			System.out
					.println("----------Inside submit Method Exception Calling-----");

			e.printStackTrace();
			return "";
		}
	}
	/**
	 * This method is used to validate data
	 * @param valid
	 * @return false show error message
	 * @return true not showing error message
	 */
	private boolean validate(boolean flag) {
		boolean valid=true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isEmpty(customerDataBean.getCustCustomerName())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("cus_name").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Customer Name."));
			}
			valid = false;
		}else if(!StringUtils.isBlank(customerDataBean.getCustCustomerName())){
			if(!CommonValidate.validateName(customerDataBean.getCustCustomerName())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("cus_name").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Customer Name."));
					}
					valid = false;	
			}
		}
		if(customerDataBean.getCustDate()==null){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_date").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
				}
				valid = false;	
		}
		
		if(StringUtils.isBlank(customerDataBean.getCustShipingAddress())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_saddr").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Billing Address."));
				}
				valid = false;	
		}
		if(StringUtils.isBlank(customerDataBean.getCustCity())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_city").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the City Name."));
				}
				valid = false;	
		}else if(StringUtils.isBlank(customerDataBean.getCustCity())){
			if(!CommonValidate.validateName(customerDataBean.getCustCity())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_city").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the City Name."));
				}
				valid = false;	
			}
		}
		if(StringUtils.isBlank(customerDataBean.getCustState())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_state").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the State Name."));
				}
				valid = false;	
		}else if(StringUtils.isBlank(customerDataBean.getCustState())){
			if(!CommonValidate.validateName(customerDataBean.getCustState())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_state").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the State Name."));
				}
				valid = false;	
			}
		}
		if(customerDataBean.getCustCountry().equalsIgnoreCase("00110")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_country").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Country."));
				}
				valid = false;	
			}
		
		if(StringUtils.isBlank(customerDataBean.getCustPhoneNumber())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_phno").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Phone Number."));
				}
				valid = false;	
		}else if(!StringUtils.isBlank(customerDataBean.getCustPhoneNumber())){
			if(!CommonValidate.validateNumber(customerDataBean.getCustPhoneNumber())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_phno").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Phone Number."));
				}
				valid = false;	
			}
		}
	/*	if(StringUtils.isBlank(customerDataBean.getCustEmail())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_email").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Email ID."));
				}
				valid = false;	
		}else if(!StringUtils.isBlank(customerDataBean.getCustEmail())){
			if(!CommonValidate.validateEmail(customerDataBean.getCustEmail())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_email").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Email ID."));
				}
				valid = false;	
			}
		}*/
		if(StringUtils.isBlank(customerDataBean.getCustTaxNO())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_taxnum").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Tax Number."));
				}
				valid = false;	
		}else if(!StringUtils.isBlank(customerDataBean.getCustTaxNO())){
			if(!CommonValidate.validateNumber(customerDataBean.getCustTaxNO())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_taxnum").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Tax Number."));
				}
				valid = false;	
			}
		}
		if(customerDataBean.getCustStaffId().equalsIgnoreCase("000")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("staf_id").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Staff ID."));
				}
				valid = false;	
			}

		
		return valid;
	}
	/**
	 * reset Method is used to reset the input text box values
	 * 
	 * @return
	 */
	public String reset() {
		System.out.println("Inside Reset method calling");
		customerDataBean.setCustCity("");
		customerDataBean.setCustCountry("");
		customerDataBean.setCustCustomerName("");
		customerDataBean.setCustDate(null);
		customerDataBean.setCustEmail("");
		customerDataBean.setCustNote("");
		customerDataBean.setCustPhoneNumber("");
		customerDataBean.setCustShipingAddress("");
		customerDataBean.setCustStaffName("");
		customerDataBean.setCustState("");
		customerDataBean.setCustTaxNO("");
		customerDataBean.setCustStaffId("");
		return "";
	}

	/**
	 * cancel Method is used for redirect to home page
	 * 
	 * @return
	 */

	public String cancel() {
		System.out.println("---- Inside Cancel Method Calling------");
		return "cancelSuccess";
		
	}

	
	
	
	@PostConstruct
	public void init(){
		LiusenController controller = null;
		try{
		System.out.println("Inside Construtor Calling");
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (LiusenController) ctx.getBean("controller");
		List<String> countryName=controller.getCountryList();
		System.out.println("name"+countryName);
		if(countryName.size()>0){
			countryList=new ArrayList<String>();
			/*setCountryList(countryName);*/
			countryList.addAll(countryName);
			/*TestCaseCheck.testing();*/
			System.out.println("=====ggj===="+countryList);
		}
		else
		{
			System.out.println("inside else");
		}
		
		
		List<String> sid=controller.getstaffid();
		if(sid.size()>0)
		{
			idlist=new ArrayList<String>();
			
			idlist.addAll(sid);
		}
		else
		{
			System.out.println("inside else");
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*@PostConstruct
	public void stafflist()
	{
		LiusenController controller = null;
		try 
		{
			System.out.println("Inside Construtor Calling11");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			List<String> sid=controller.getstaffid();
			if(idlist.size()>0)
			{
				idlist=new ArrayList<String>();
				
				idlist.addAll(sid);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}*/
	
	
	}

