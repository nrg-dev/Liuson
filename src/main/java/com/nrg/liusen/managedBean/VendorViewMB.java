package com.nrg.liusen.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.VendorDataBean;
import com.nrg.liusen.shared.Vendor;
import com.nrg.liusen.util.CommonValidate;

/**
 * 
 * @author Robert Arjun
 * @Date 23-10-2015
 * @copyright NRG This class works as a ManagedBean
 *
 */
@ManagedBean(name = "vendorViewMB")
@RequestScoped
public class VendorViewMB {
	private VendorDataBean vendorDataBean = new VendorDataBean();
	
	private boolean flag=true;
	private boolean editflag=false;
	private boolean deleteflag=false;
	
	public boolean isEditflag() {
		return editflag;
	}

	public void setEditflag(boolean editflag) {
		this.editflag = editflag;
	}

	public boolean isDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(boolean deleteflag) {
		this.deleteflag = deleteflag;
	}
	private String status;
	
	private List<VendorDataBean> vendorViewList=null;
	private List<Vendor> vendorInfoList=null;
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
	 * @return the vendorViewList
	 */
	public List<VendorDataBean> getVendorViewList() {
		return vendorViewList;
	}

	/**
	 * @param vendorViewList the vendorViewList to set
	 */
	public void setVendorViewList(List<VendorDataBean> vendorViewList) {
		this.vendorViewList = vendorViewList;
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
	 * @return the vendorDataBean
	 */
	public VendorDataBean getVendorDataBean() {
		return vendorDataBean;
	}

	/**
	 * @param vendorDataBean
	 *            the vendorDataBean to set
	 */
	public void setVendorDataBean(VendorDataBean vendorDataBean) {
		this.vendorDataBean = vendorDataBean;
	}

	public String vendorViewLoad() {

		System.out.println("Inside Load the Vendor View Load Page");
		valid = true;
		vendorDataBean.setVenFirmName("");
		vendorDataBean.setVenFirmName1("");
		vendorDataBean.setVenCity("");
		setFlag(true);
		return "venViewLoad";

	}

	/**
	 * This method is used for show data tables depends on firm name
	 * 
	 * @return
	 */
	public String searchFirmName() {
		String outputStatus="Fail";
		LiusenController controller = null;
		System.out.println("Inside the searchFirmName Method Calling");
		
			if (validate(true)) {
				try{
				System.out.println("After Validate inside  searchFirmName method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager") || rolles.equalsIgnoreCase("Finance Manager")){
					setDeleteflag(true);
					setEditflag(false);
				}
				else if(rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff")){
					setDeleteflag(true);
					setEditflag(true);
				}
				else{
					setDeleteflag(false);
					setEditflag(false);	
				}
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				vendorViewList = controller.searchByVendorName(vendorDataBean);
				System.out.println("Size in MB"+vendorViewList.size());
				/*for(VendorDataBean ven : vendorViewList){
					
				}*/
				outputStatus="Success";
				}catch(Exception e){
					e.printStackTrace();
					System.out.println(System.err);
				}
			}else{
				setFlag(true);
			}
		return "";
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
		if (StringUtils.isBlank(vendorDataBean.getVenFirmName1())) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("venViewName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Firm Name."));
			}
			valid = false;
		} else if (!StringUtils.isBlank(vendorDataBean.getVenFirmName1())) {

			if (!CommonValidate.validateName(vendorDataBean.getVenFirmName1())) {
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"venViewName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Enter the valid Firm Name."));
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
		try{
			if (validate1(true)) {
				System.out.println("After Validate1 inside  searchCity method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager") || rolles.equalsIgnoreCase("Finance Manager")){
					setDeleteflag(true);
					setEditflag(false);
				}else if(rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Admin Project")){
					setDeleteflag(true);
					setEditflag(true);
				}
				else{
					setDeleteflag(false);
					setEditflag(false);	
				}
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				vendorViewList = controller.searchByCityName(vendorDataBean);
				System.out.println("Size in MB"+vendorViewList.size());
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
		if (StringUtils.isBlank(vendorDataBean.getVenCity())) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("venCityName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the City Name."));
			}
			valid = false;
		} else if (!StringUtils.isBlank(vendorDataBean.getVenCity())) {

			if (!CommonValidate.validateName(vendorDataBean.getVenCity())) {
				if (flag2) {
					fieldName = CommonValidate.findComponentInRoot(
							"venCityName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Enter the valid City Name."));
				}
				valid = false;
			}
		}
		return valid;
	}
	
	 public void vendorViewByName() {
		 System.out.println("Inside"+vendorDataBean.getVenFirmName());
		 LiusenController controller = null;
		 if(!vendorDataBean.getVenFirmName().equalsIgnoreCase("")){
			 try{
				 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				 controller = (LiusenController) ctx.getBean("controller");
				 vendorInfoList=controller.getVendorInfo(vendorDataBean.getVenFirmName());
				 System.out.println("vendorInfoList Size"+vendorInfoList.size());
				 if(vendorInfoList.size()>0){
				 for(Vendor ven:vendorInfoList){
					vendorDataBean.setVenAddress(ven.getAddress());
					vendorDataBean.setVenCity(ven.getCity());
					vendorDataBean.setVenCountry(ven.getCountry().getCountry_Name());
					vendorDataBean.setVenEmail(ven.getEmail());
					vendorDataBean.setVenFaxNO(ven.getFaxNumber());
					vendorDataBean.setVenFirmName(ven.getFirmName());
					vendorDataBean.setVenFirmType(ven.getFirmType());
					vendorDataBean.setVenNature(ven.getNatureBusiness());
					vendorDataBean.setVenNote(ven.getNote());
					vendorDataBean.setVenPerson(ven.getPersonIncharge());
					vendorDataBean.setVenPhone(ven.getPhoneNumber());
					vendorDataBean.setVenStandard(ven.getStandards());
					vendorDataBean.setVenState(ven.getState());
					vendorDataBean.setVenWeb(ven.getWebAddress());
					vendorDataBean.setVenTaxNO(ven.getTaxNumber());
					vendorDataBean.setVenTelephone(ven.getTelephoneNumber());
					vendorDataBean.setVenAccountNo(ven.getAccountNo());
					vendorDataBean.setVenBankName(ven.getBankName());
					vendorDataBean.setVenTelephone1(ven.getTelephoneNo2());
					vendorDataBean.setVenPerson1(ven.getPersonIncharge2());
					vendorDataBean.setVenPhone1(ven.getPhoneNumber2());
				 }
				 }
			 }catch(NullPointerException e1){
				System.out.println("Inside Null Pointer Exception");
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
	 }

	/**
	 * @return the vendorInfoList
	 */
	public List<Vendor> getVendorInfoList() {
		return vendorInfoList;
	}

	/**
	 * @param vendorInfoList the vendorInfoList to set
	 */
	public void setVendorInfoList(List<Vendor> vendorInfoList) {
		this.vendorInfoList = vendorInfoList;
	}
	
	
	
	public String vendorEdit() {
		String status="getVendorListNone";
		System.out.println("Inside Edit Method");
		 LiusenController controller = null;
		 if(!vendorDataBean.getVenFirmName().equalsIgnoreCase("")){
			 try{
				 valid = true; 
				 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				 controller = (LiusenController) ctx.getBean("controller");
				 vendorInfoList=controller.getVendorInfo(vendorDataBean.getVenFirmName());
				 System.out.println("vendorInfoList Size"+vendorInfoList.size());
				 if(vendorInfoList.size()>0){
				 for(Vendor ven:vendorInfoList){
					vendorDataBean.setVenAddress(ven.getAddress());
					vendorDataBean.setVenCity(ven.getCity());
					vendorDataBean.setVenCountry(ven.getCountry().getCountry_Name());
					vendorDataBean.setVenEmail(ven.getEmail());
					vendorDataBean.setVenFaxNO(ven.getFaxNumber());
					vendorDataBean.setVenFirmName(ven.getFirmName());
					vendorDataBean.setVenFirmType(ven.getFirmType());
					vendorDataBean.setVenNature(ven.getNatureBusiness());
					vendorDataBean.setVenNote(ven.getNote());
					vendorDataBean.setVenPerson(ven.getPersonIncharge());
					vendorDataBean.setVenPhone(ven.getPhoneNumber());
					vendorDataBean.setVenStandard(ven.getStandards());
					vendorDataBean.setVenState(ven.getState());
					vendorDataBean.setVenWeb(ven.getWebAddress());
					vendorDataBean.setVenTaxNO(ven.getTaxNumber());
					vendorDataBean.setVenTelephone(ven.getTelephoneNumber());
					vendorDataBean.setFirmName(ven.getFirmName());
					vendorDataBean.setVenBankName(ven.getBankName());
					vendorDataBean.setVenAccountNo(ven.getAccountNo());
					vendorDataBean.setVenPerson1(ven.getPersonIncharge2());
					vendorDataBean.setVenPhone1(ven.getPhoneNumber2());
					vendorDataBean.setVenTelephone1(ven.getTelephoneNo2());
					vendorDataBean.setPhone(ven.getPhoneNumber());
				 }
				
					List<String> countryName=controller.getCountryList();
					System.out.println("name"+countryName);
					if(countryName.size()>0){
						countryList=new ArrayList<String>();
						/*setCountryList(countryName);*/
						countryList.addAll(countryName);
						/*TestCaseCheck.testing();*/
					}
					
				 status="vendorListNotEmpty";
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
	private boolean valid = true; 
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String save() {
		System.out.println("Inside Save method Calling");
		LiusenController controller = null;
		if (validateEdit(true)) {
			try {
				System.out.println("After Validation inside Save method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				status = controller.editVendor(vendorDataBean);
				System.out.println("Status" + status);
				if(status.equalsIgnoreCase("Success")){
				valid = false;
				}
			} catch (Exception e) {
				
				System.out.println("Inside Exception");
			}
			
		}
		return "";
	}
	private boolean validateEdit(boolean flag) {
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
	/*	if (StringUtils.isBlank(vendorDataBean.getVenState())) {
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
//		if (StringUtils.isBlank(vendorDataBean.getVenStandard())) {
//			if(flag){
//			fieldName = CommonValidate.findComponentInRoot("venStandards").getClientId(fc);
//			fc.addMessage(fieldName, new FacesMessage("Please Enter the Standard."));
//			}
//			valid = false;
//		} else if (StringUtils.isNotEmpty(vendorDataBean.getVenStandard())) {
//			if (!CommonValidate.validateName(vendorDataBean.getVenStandard())) {
//				if(flag){
//				fieldName = CommonValidate.findComponentInRoot("venStandards").getClientId(fc);
//				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Standard."));
//				}
//				valid = false;
//			}
//		}
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
	public String cancel() {
		return "";
		
	}
	public String delete() {
		String status="fail";
		System.out.println("Inside Delete method calling");
		System.out.println(vendorDataBean.getVenFirmName());
		LiusenController controller = null;
		if(vendorDataBean.getVenFirmName() != null){
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			status=controller.deleteVendor(vendorDataBean);
			valid = false;
			if(status.equalsIgnoreCase("Success")){
				vendorViewList = controller.searchByVendorName(vendorDataBean);
			}
			
			System.out.println(vendorViewList.size());
		}
		return status;
	}
}
