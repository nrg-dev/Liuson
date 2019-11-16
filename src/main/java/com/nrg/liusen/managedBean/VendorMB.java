package com.nrg.liusen.managedBean;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.primefaces.context.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.VendorDataBean;
import com.nrg.liusen.util.CommonValidate;
import com.nrg.liusen.util.TestCaseCheck;

/**
 * 
 * @author Robert Arjun
 * @date 16-10-2015
 * @copyright NRG 
 * This class works as a ManagedBean
 *
 */

@ManagedBean(name = "vendorMB")
@RequestScoped
public class VendorMB {

	
	VendorDataBean vendorDataBean=new VendorDataBean();
	private String status;
	private List<String> countryList = null;
	
	
	

	/**
	 * @return the countryList
	 */
	public List<String> getCountryList() {
		return countryList;
	}

	/**
	 * @param countryList the countryList to set
	 */
	public void setCountryList(List<String> countryList) {
		this.countryList = countryList;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the vendorDataBean
	 */
	public VendorDataBean getVendorDataBean() {
		return vendorDataBean;
	}

	/**
	 * @param vendorDataBean the vendorDataBean to set
	 */
	public void setVendorDataBean(VendorDataBean vendorDataBean) {
		this.vendorDataBean = vendorDataBean;
	}

	/**
	 * vendorPageLoad Method is used for redirect to vendor registration form
	 * page
	 * 
	 * @return success go to vendor registration form page
	 */
	public String vendorPageLoad() {
		System.out.println("----------Inside vendorPageLoad Method Calling-----");
		String outputStatus="nullLogin";
		System.out.println("Login Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user"));
		System.out.println("Roll Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll"));
		System.out.println("Login User Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));

		String LoginName=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		
		System.out.println("Name"+LoginName);
		if (LoginName == null) {
			System.out.println("Inside login null");
			return outputStatus;
		} else{
		try {
			valid = true;
			vendorDataBean.setVenAddress("");
			vendorDataBean.setVenCity("");
			vendorDataBean.setVenCountry("");
			vendorDataBean.setVenEmail("");
			vendorDataBean.setVenFaxNO("");
			vendorDataBean.setVenFirmName("");
			vendorDataBean.setVenFirmType("");
			vendorDataBean.setVenNature("");
			vendorDataBean.setVenNote("");
			vendorDataBean.setVenPerson("");
			vendorDataBean.setVenPhone("");
			vendorDataBean.setVenStandard("");
			vendorDataBean.setVenState("");
			vendorDataBean.setVenTaxNO("");
			vendorDataBean.setVenTelephone("");
			vendorDataBean.setVenPhone1("");
			vendorDataBean.setVenBankName("");
			vendorDataBean.setVenPerson1("");
			vendorDataBean.setVenTelephone1("");
			vendorDataBean.setVenAccountNo("");
			vendorDataBean.setVenWeb("");
			outputStatus="venRegPage";
			return outputStatus;
		} catch (Exception e) {
			System.out.println("----------Inside vendorPageLoad Method Exception Calling-----");
			e.getStackTrace();
			outputStatus="failure";
			return outputStatus;
		}
		}
	}

	/**
	 * submit Method is used to get the values from UI to Controller
	 * 
	 * @return submitSuccess go to controller
	 * @return submitFailure redirect same page
	 */
	
	private boolean valid = true; 
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String submit() {
			String outputStatus="loginNull";
			System.out.println("----------Inside submit Method Calling-----"+outputStatus);
			
			LiusenController controller = null;
			System.out.println("Login Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user"));
			System.out.println("Roll Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll"));
			System.out.println("Login User Name "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			String fieldName;
			FacesContext fc = FacesContext.getCurrentInstance();
			String LoginName=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			System.out.println("Name"+LoginName);
			if (LoginName == null) {
				System.out.println("Inside login null");
				return "";
			} else if (validate(true)) {
				try {
					System.out.println("After Validation inside Submit method");
					ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (LiusenController) ctx.getBean("controller");
					status = controller.insertVendor(vendorDataBean);
					System.out.println("Status" + status);
					
					if(status.equalsIgnoreCase("Exsist")){
						fieldName = CommonValidate.findComponentInRoot("venName").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("This Firm Name already Registered."));
						outputStatus="";
					}else{
						
					outputStatus = "submitSuccess";
					valid = false;
					System.out.println("Out1" + outputStatus);
					}
	
				} catch (Exception e) {
					outputStatus = "";
					System.out.println("Inside Exception");
				}
				return "";
			} else {
				outputStatus = "";
				System.out.println("Out2" + outputStatus);
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

		if (StringUtils.isEmpty(vendorDataBean.getVenFirmName())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venName").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Firm Name."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenFirmName())) {
			if (!CommonValidate.validateName(vendorDataBean.getVenFirmName())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Firm Name."));
				}
				valid = false;
			}
		}
		if (StringUtils.isBlank(vendorDataBean.getVenFirmType())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venType").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Firm Type."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenFirmType())) {
			if (!CommonValidate.validateName(vendorDataBean.getVenFirmType())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venType").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Firm Type."));
				}
				valid = false;
			}
		}
		if(StringUtils.isBlank(vendorDataBean.getVenAddress())){
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venAddres").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Address."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenAddress())) {
			if (!CommonValidate.validateName(vendorDataBean.getVenAddress())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venAddres").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Address."));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(vendorDataBean.getVenTaxNO())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venTax").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Tax Number."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenTaxNO())) {
			if (!CommonValidate.isNumeric(vendorDataBean.getVenTaxNO())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venTax").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the vaild Tax Number."));
				}
				valid = false;
			}
		}
		if (StringUtils.isBlank(vendorDataBean.getVenCity())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venCity").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the City Name."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenCity())) {
			if (!CommonValidate.validateName(vendorDataBean.getVenCity())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venCity").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the City Name."));
				}
				valid = false;
			}
		}
		if(StringUtils.isBlank(vendorDataBean.getVenNature())){
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venNob").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Nature of Business."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenNature())) {
			if (!CommonValidate.validateName(vendorDataBean.getVenNature())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venNob").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Nature of Business."));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(vendorDataBean.getVenPerson())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venPI").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Person Incharge Name."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenPerson())) {
			if (!CommonValidate.validateName(vendorDataBean.getVenPerson())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venPI").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Person Incharge Name."));
				}
				valid = false;
			}
		}
		/*if (StringUtils.isBlank(vendorDataBean.getVenState())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venState").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Vendor State."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenState())) {
			if (!CommonValidate.validateName(vendorDataBean.getVenState())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venState").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Vendor State."));
				}
				valid = false;
			}
		}*/
		if(StringUtils.isBlank(vendorDataBean.getVenTelephone())){
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venTel").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Telephone Number."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenTelephone())) {
			if (!CommonValidate.isNumeric(vendorDataBean.getVenTelephone())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venTel").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Telephone Number."));
				}
				valid = false;
			}
		}
		if (vendorDataBean.getVenCountry().equalsIgnoreCase("ven00")) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venCountry").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Choose the Country Name."));
			}
			valid = false;
		} /*else if (StringUtils.isNotEmpty(vendorDataBean.getVenCountry())) {
			if (!CommonValidate.validateName(vendorDataBean.getVenCountry())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venCountry").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Vendor Country Name."));
				}
				valid = false;
			}
		}*/
		if(StringUtils.isBlank(vendorDataBean.getVenEmail())){
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venEmail").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Email ID."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenEmail())) {
			if (!CommonValidate.validateEmail(vendorDataBean.getVenEmail())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venEmail").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Email ID."));
				}
				valid = false;
			}
		}
		if (StringUtils.isEmpty(vendorDataBean.getVenPhone())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venPhone").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Phone Number."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenPhone())) {
			if (!CommonValidate.isNumeric(vendorDataBean.getVenPhone())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venPhone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Phone Number."));
				}
				valid = false;
			}
		}
		/*if (StringUtils.isBlank(vendorDataBean.getVenStandard())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venStandards").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Standard."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenStandard())) {
			if (!CommonValidate.validateName(vendorDataBean.getVenStandard())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venStandards").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Standard."));
				}
				valid = false;
			}
		}*/
		if(StringUtils.isBlank(vendorDataBean.getVenFaxNO())){
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venFax").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Fax Number."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenFaxNO())) {
			if (!CommonValidate.isNumeric(vendorDataBean.getVenFaxNO())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venFax").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Fax Number."));
				}
				valid = false;
			}
		}
		
		if(StringUtils.isBlank(vendorDataBean.getVenAccountNo())){
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venAccNo").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Account Number."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenAccountNo())) {
			if (!CommonValidate.isNumeric(vendorDataBean.getVenAccountNo())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venAccNo").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Account Number."));
				}
				valid = false;
			}
		}
		if(StringUtils.isBlank(vendorDataBean.getVenBankName())){
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("venBankN").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Bank Name."));
			}
			valid = false;
		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenBankName())) {
			if (!CommonValidate.validateName(vendorDataBean.getVenBankName())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("venBankN").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Bank Name.."));
				}
				valid = false;
			}
		}
		return valid;
	}

	
	/**
	 * reset Method is used to reset the input text box values
	 * 
	 * @return
	 */
	public String reset() {
		try {
			System.out.println("----------Inside reset Method Calling-----");
			vendorDataBean.setVenAddress("");
			vendorDataBean.setVenCity("");
			vendorDataBean.setVenCountry("");
			vendorDataBean.setVenEmail("");
			vendorDataBean.setVenFaxNO("");
			vendorDataBean.setVenFirmName("");
			vendorDataBean.setVenFirmType("");
			vendorDataBean.setVenNature("");
			vendorDataBean.setVenNote("");
			vendorDataBean.setVenPerson("");
			vendorDataBean.setVenPhone("");
			vendorDataBean.setVenStandard("");
			vendorDataBean.setVenState("");
			vendorDataBean.setVenTaxNO("");
			vendorDataBean.setVenTelephone("");
			vendorDataBean.setVenWeb("");
			vendorDataBean.setVenPhone1("");
			vendorDataBean.setVenBankName("");
			vendorDataBean.setVenPerson1("");
			vendorDataBean.setVenTelephone1("");
			vendorDataBean.setVenAccountNo("");
			return "";
		} catch (Exception e) {
			System.out
					.println("----------Inside reset Method Exception Calling-----");
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * cancel Method is used for redirect to home page
	 * 
	 * @return
	 */

	public String cancel() {
		try {
			System.out.println("----------Inside cancel Method Calling-----");

			return "cancelSuccess";
		} catch (Exception e) {
			System.out
					.println("----------Inside cancel Method Exception Calling-----");

			e.printStackTrace();
			return "";
		}
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
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void Checking(){
		System.out.println("Inside checking");
		 String str= "Junit is working fine";
	      assertEquals("Junit is working fine",str);
	}
}
