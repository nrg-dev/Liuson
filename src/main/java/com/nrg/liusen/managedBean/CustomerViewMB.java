package com.nrg.liusen.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.CustomerDataBean;
import com.nrg.liusen.shared.Country;
import com.nrg.liusen.shared.Customer;
import com.nrg.liusen.shared.Vendor;
import com.nrg.liusen.util.CommonValidate;
/**
 * 
 * @author Robert Arjun
 * @date 27-10-2015
 * @copyright NRG 
 * This class works as a ManagedBean
 *
 */
@ManagedBean(name="customerViewMB")
@RequestScoped
public class CustomerViewMB {

	private boolean flag=true;
	private CustomerDataBean customerDataBean = new CustomerDataBean();
	private List<CustomerDataBean> custViewList=null;
	private List<Customer> custList=null;
	private List<Customer> customerInfoList=null;
	private List<String> countryList = null;
	private boolean deleteflag=false;
	private boolean editflag=false;
	public boolean isDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(boolean deleteflag) {
		this.deleteflag = deleteflag;
	}


	private String status;
	
	
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Customer> getCustomerInfoList() {
		return customerInfoList;
	}

	public void setCustomerInfoList(List<Customer> customerInfoList) {
		this.customerInfoList = customerInfoList;
	}

	
	public List<String> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<String> countryList) {
		this.countryList = countryList;
	}


	private boolean myValid = false;
	private boolean dialog = false;
	public boolean isDialog() {
		return dialog;
	}

	public void setDialog(boolean dialog) {
		this.dialog = dialog;
	}

	public boolean isMyValid() {
		return myValid;
	}

	public void setMyValid(boolean myValid) {
		this.myValid = myValid;
	}
	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
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
	
	
	
	
	public List<CustomerDataBean> getCustViewList() {
		return custViewList;
	}

	public void setCustViewList(List<CustomerDataBean> custViewList) {
		this.custViewList = custViewList;
	}

	public List<Customer> getCustList() {
		return custList;
	}

	public void setCustList(List<Customer> custList) {
		this.custList = custList;
	}

	public String customerViewLoad() {

		System.out.println("Inside  Customer View Load Page");
		customerDataBean.setCustCustomerName("");
		customerDataBean.setCustCity("");
		setFlag(true);
		valid=false;
		System.out.println("===fag=="+flag);
		return "customerViewLoadPage";

	}

	/**
	 * This method is used for show data tables depends on firm name
	 * 
	 * @return
	 */
	public String searchCustomerName() {
		
		System.out.println("Inside the searchCustomerName Method Calling");
		LiusenController controller = null;
		customerDataBean.setCustCity("");
		try {
			if (validate(true)) {
				System.out.println("After Validate inside  searchCustomerName method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager")  || rolles.equalsIgnoreCase("Finance Manager") || rolles.equalsIgnoreCase("Marketing Manager") ){
					setDeleteflag(true);
					setEditflag(false);
				}else if(rolles.equalsIgnoreCase("Chief Designer") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Designer") || rolles.equalsIgnoreCase("Marketing Staff")){
					setDeleteflag(true);
					setEditflag(true);
				}else{
					setDeleteflag(false);
					setEditflag(false);
					
				}
				
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				custViewList=controller.getCustList(customerDataBean);
				setFlag(false);
			}else{
				setFlag(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	private boolean valid = true; 
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * This method is used to validate data
	 * 
	 * @param valid
	 * @return false show error message
	 * @return true not showing error message
	 */
	private boolean validate(boolean flag1) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isBlank(customerDataBean.getCustCustomerName())) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("cusViewName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Customer Name."));
			}
			valid = false;
		} else if (!StringUtils.isBlank(customerDataBean.getCustCustomerName())) {

			if (!CommonValidate.validateName(customerDataBean.getCustCustomerName())) {
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"cusViewName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Enter the valid Customer Name."));
				}
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * This method is used for show data tables depends on City name
	 * 
	 * @return
	 */

	public String searchCity() {
		System.out.println("Inside the searchCity Method Calling");
		String outputStatus="Fail";
		LiusenController controller = null;
		customerDataBean.setCustCustomerName("");
		try{
			if (validate1(true)) {
				System.out.println("After Validate1 inside  searchCity method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager")  || rolles.equalsIgnoreCase("Finance Manager") || rolles.equalsIgnoreCase("Marketing Manager")){
					setDeleteflag(true);
					setEditflag(false);
				}else if(rolles.equalsIgnoreCase("Chief Designer") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Marketing Staff") || rolles.equalsIgnoreCase("Designer")){
					setDeleteflag(true);
					setEditflag(true);
				}else{
					setDeleteflag(false);
					setEditflag(false);
					
				}
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				custViewList = controller.searchByCityName(customerDataBean);
				System.out.println("Size in MB"+custViewList.size());
				outputStatus="Success";
			}else{
				setFlag(true);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";

	}

	private boolean validate1(boolean flag2) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (StringUtils.isBlank(customerDataBean.getCustCity())) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("CusCityName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the City Name."));
			}
			valid = false;
		} else if (!StringUtils.isBlank(customerDataBean.getCustCity())) {

			if (!CommonValidate.validateName(customerDataBean.getCustCity())) {
				if (flag2) {
					fieldName = CommonValidate.findComponentInRoot(
							"CusCityName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Enter the valid City Name."));
				}
				valid = false;
			}
		}
		return valid;
	}
	
	

	
	
	
	 public void customerViewByName() {
		 System.out.println("Inside"+customerDataBean.getCustCustomerName());
		 LiusenController controller = null;
		 if(!customerDataBean.getCustCustomerName().equalsIgnoreCase("")){
			 try{
				 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				 controller = (LiusenController) ctx.getBean("controller");
				 customerInfoList=controller.getCustomerInfo(customerDataBean.getCustCustomerName());
				 System.out.println("vendorInfoList Size"+customerInfoList.size());
				 if(customerInfoList.size()>0){
				 for(Customer cus:customerInfoList){
					 customerDataBean.setCustCustomerName(cus.getCustomerName());;
					 customerDataBean.setCustDate(cus.getCustomerRegDate());
					 customerDataBean.setCustShipingAddress(cus.getCustomerAddress());
					 customerDataBean.setCustCity(cus.getCustomerCity());
					 customerDataBean.setCustState(cus.getCustomerState());
					 customerDataBean.setCustCountry(cus.getCountry().getCountry_Name());
					 customerDataBean.setCustPhoneNumber(cus.getCustomerPhoneNumber());
					 customerDataBean.setCustEmail(cus.getCustomerEmail());
					 customerDataBean.setCustTaxNO(cus.getCustomerTaxNumber());
					 customerDataBean.setCustNote(cus.getCustomerDescription());
					 customerDataBean.setCustStaffId(cus.getEmployee().getEmployee_details_ID());
				 }
				 }
			 }catch(NullPointerException e1){
				System.out.println("Inside Null Pointer Exception");
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
	 }
	
	
	 public String customerEdit() {
			String status="getCustomerListNone";
			System.out.println("Inside Edit Method");
			 LiusenController controller = null;
			 valid=false;
			 if(!customerDataBean.getCustCustomerName().equalsIgnoreCase("")){
				 try{
					
					 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					 controller = (LiusenController) ctx.getBean("controller");
					 customerInfoList=controller.getCustomerInfo(customerDataBean.getCustCustomerName());
					 System.out.println("vendorInfoList Size"+customerInfoList.size());
					 if(customerInfoList.size()>0){
					 for(Customer cus:customerInfoList){
						 
						 customerDataBean.setCustCustomerName(cus.getCustomerName());;
						 customerDataBean.setCustDate(cus.getCustomerRegDate());
						 customerDataBean.setCustShipingAddress(cus.getCustomerAddress());
						 customerDataBean.setCustCity(cus.getCustomerCity());
						 customerDataBean.setCustState(cus.getCustomerState());
						 customerDataBean.setCustCountry(cus.getCountry().getCountry_Name());
						 customerDataBean.setCustPhoneNumber(cus.getCustomerPhoneNumber());
						 customerDataBean.setCustEmail(cus.getCustomerEmail());
						 customerDataBean.setCustTaxNO(cus.getCustomerTaxNumber());
						 customerDataBean.setCustNote(cus.getCustomerDescription());
						 customerDataBean.setCustStaffId(cus.getEmployee().getEmployee_details_ID());
						 System.out.println("===----------======");
					 }
					
						List<String> countryName=controller.getCountryList();
						System.out.println("name"+countryName);
						if(countryName.size()>0){
							countryList=new ArrayList<String>();
							/*setCountryList(countryName);*/
							countryList.addAll(countryName);
							/*TestCaseCheck.testing();*/
						}
						
					 status="customerListNotEmpty";
					 }
				 }catch(NullPointerException e1){
					System.out.println("Inside Null Pointer Exception");
				 }catch(Exception e){
					 e.printStackTrace();
				 }
			 }
			 System.out.println("Status"+status);
			return status;
			
		}
	
	 /*private boolean valid1 = true; 
		
		public boolean isValid1() {
		return valid1;
	}

	public void setValid1(boolean valid1) {
		this.valid1 = valid1;
	}*/

		public String save() {
			System.out.println("Inside Save method Calling");
			LiusenController controller = null;
		System.out.println("======="+valid);
			if (validateEdit(true)) {
				try {
					System.out.println("After Validation inside Save method");
					ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (LiusenController) ctx.getBean("controller");
					status = controller.editCustomer(customerDataBean);
					System.out.println("Status" + status);
					valid=true;

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Inside Exception");
				}
				
			}
			return "";
		}
		
		
		private boolean validateEdit(boolean flag) {
			boolean valid=true;
			String fieldName;
			FacesContext fc = FacesContext.getCurrentInstance();

			if (StringUtils.isEmpty(customerDataBean.getCustCustomerName())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_name").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Customer Name."));
				}
				valid = false;
			} else if (StringUtils.isNotEmpty(customerDataBean.getCustCustomerName())) {
				if (!CommonValidate.validateName(customerDataBean.getCustCustomerName())) {
					if(flag){
					fieldName = CommonValidate.findComponentInRoot("cus_name").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Customer Name."));
					}
					valid = false;
				}
			}
			
			if(StringUtils.isBlank(customerDataBean.getCustShipingAddress())){
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_saddr").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Billing Address."));
				}
				valid = false;
			} else if (StringUtils.isNotEmpty(customerDataBean.getCustShipingAddress())) {
				if (!CommonValidate.validateName(customerDataBean.getCustShipingAddress())) {
					if(flag){
					fieldName = CommonValidate.findComponentInRoot("cus_saddr").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Billing Address."));
					}
					valid = false;
				}
			}
			if (StringUtils.isEmpty(customerDataBean.getCustTaxNO())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_taxnum").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Tax Number."));
				}
				valid = false;
			} else if (StringUtils.isNotEmpty(customerDataBean.getCustTaxNO())) {
				if (!CommonValidate.isNumeric(customerDataBean.getCustTaxNO())) {
					if(flag){
					fieldName = CommonValidate.findComponentInRoot("cus_taxnum").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the vaild Tax Number."));
					}
					valid = false;
				}
			}
			if (StringUtils.isBlank(customerDataBean.getCustCity())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_city").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the City ."));
				}
				valid = false;
			} else if (StringUtils.isNotEmpty(customerDataBean.getCustCity())) {
				if (!CommonValidate.validateName(customerDataBean.getCustCity())) {
					if(flag){
					fieldName = CommonValidate.findComponentInRoot("cus_city").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the City ."));
					}
					valid = false;
				}
			}
			
			if (StringUtils.isBlank(customerDataBean.getCustState())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_state").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the State."));
				}
				valid = false;
			} else if (StringUtils.isNotEmpty(customerDataBean.getCustState())) {
				if (!CommonValidate.validateName(customerDataBean.getCustState())) {
					if(flag){
					fieldName = CommonValidate.findComponentInRoot("cus_state").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid  State."));
					}
					valid = false;
				}
			}
			/*if(StringUtils.isBlank(customerDataBean.getCustPhoneNumber())){
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_phno").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Person Incharge Telephone Number."));
				}
				valid = false;
			} else if (StringUtils.isNotEmpty(customerDataBean.getCustPhoneNumber())) {
				if (!CommonValidate.isNumeric(customerDataBean.getCustPhoneNumber())) {
					if(flag){
					fieldName = CommonValidate.findComponentInRoot("cus_phno").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Person Incharge Telephone Number."));
					}
					valid = false;
				}
			}*/
			if (customerDataBean.getCustCountry().equalsIgnoreCase("00110")) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_country").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Country"));
				}
				valid = false;
			} 
			if (customerDataBean.getCustStaffId().equalsIgnoreCase("000")) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("staf_id").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Staff ID."));
				}
				valid = false;
			} 
			/*if(StringUtils.isBlank(customerDataBean.getCustEmail())){
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_email").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Customer Email ID."));
				}
				valid = false;
			} else if (!StringUtils.isNotEmpty(customerDataBean.getCustEmail())) {
				if (!CommonValidate.validateEmail(customerDataBean.getCustEmail())) {
					if(flag){
					fieldName = CommonValidate.findComponentInRoot("cus_email").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Customer Email ID."));
					}
					valid = false;
				}
			}*/
			if(customerDataBean.getCustDate()==null){
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("cus_date").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Date."));
				}
				valid = false;
			} 
			
			
			
			return valid;
		}
		public String cancel() {
			return "";
		}

		public String delete() {
			String status="fail";
			System.out.println("Inside Delete method calling");
			System.out.println(customerDataBean.getCustCustomerName());
			LiusenController controller = null;
			if(customerDataBean.getCustCustomerName() != null){
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				status=controller.customerDelete(customerDataBean);
				valid = false;
				if(status.equalsIgnoreCase("Success")){
					custViewList = controller.searchByCustomerName(customerDataBean);
				}
				
				System.out.println(custViewList.size());
			}
			return status;
		}

		/**
		 * @return the editflag
		 */
		public boolean isEditflag() {
			return editflag;
		}

		/**
		 * @param editflag the editflag to set
		 */
		public void setEditflag(boolean editflag) {
			this.editflag = editflag;
		}
	
	
	
}
