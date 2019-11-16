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
import com.nrg.liusen.domain.EmployeeDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="employeeMB")
@RequestScoped
public class EmployeeMB {

	EmployeeDataBean EmployeeDataBean= new EmployeeDataBean();
	private List<String> qualList=null;
	private List<String> designationList=null;
	

	/**
	 * @return the qualList
	 */
	public List<String> getQualList() {
		return qualList;
	}

	/**
	 * @param qualList the qualList to set
	 */
	public void setQualList(List<String> qualList) {
		this.qualList = qualList;
	}

	/**
	 * @return the designationList
	 */
	public List<String> getDesignationList() {
		return designationList;
	}

	/**
	 * @param designationList the designationList to set
	 */
	public void setDesignationList(List<String> designationList) {
		this.designationList = designationList;
	}

	/**
	 * @return the employeeDataBean
	 */
	public EmployeeDataBean getEmployeeDataBean() {
		return EmployeeDataBean;
	}

	/**
	 * @param employeeDataBean the employeeDataBean to set
	 */
	public void setEmployeeDataBean(EmployeeDataBean employeeDataBean) {
		EmployeeDataBean = employeeDataBean;
	}
	/**
	 * employee PageLoad Method is used for redirect to Employee registration form
	 * page
	 * 
	 * @return success go to Employee registration form page
	 */
	public String employeePageLoad() {
		try {
			System.out
					.println("----------Inside employeePageLoad Method Calling-----");
			valid = true;
			EmployeeDataBean.setEmpAddress("");
			EmployeeDataBean.setEmpBasicSalary("");
			EmployeeDataBean.setEmpDescription("");
			EmployeeDataBean.setEmpDesignation("");
			EmployeeDataBean.setEmpDob(null);
			EmployeeDataBean.setEmpEmployeeId("");
			EmployeeDataBean.setEmpEmployeeName("");
			EmployeeDataBean.setEmpEntryDate(null);
			EmployeeDataBean.setEmpGender("");
			EmployeeDataBean.setEmpJoinDate(null);
			EmployeeDataBean.setEmpMaildId("");
			EmployeeDataBean.setEmpPhoneNo("");
			EmployeeDataBean.setEmpQualification("");
		
			return "empRegLoad";
		} catch (Exception e) {
			System.out
					.println("----------Inside employeePageLoad Method Exception Calling-----");
			e.getStackTrace();
			return "failure";
		}
	}
	private boolean valid = true; 
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public String submit() 
	{
		String outstatus="Failure";
		LiusenController controller = null;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try 
		{
			System.out.println("----------Inside submit Method Calling-----");
			if(validate(true))
			{
				System.out.println("After Validation inside Submit method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				String status=controller.inserEmployee(EmployeeDataBean);
				System.out.println("status"+status);
				if(status.equalsIgnoreCase("Exsist"))
				{
					fieldName = CommonValidate.findComponentInRoot("emp_Id").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee ID."));
				}
				else if(status.equalsIgnoreCase("Success"))
				{
					outstatus="submitSuccess";
					valid = false;
				}
				else
				{
					System.out.println("Inside Else");
				}
				return "";
			}
			else
			{
				System.out.println("Inside validation");
				return "";
			}
		} 
		catch (Exception e)
		{
			System.out.println("----------Inside submit Method Exception Calling-----");
			e.printStackTrace();
			return "";
		}		
	}

	private boolean validate(boolean flag) {
		boolean valid=true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();

		if (StringUtils.isEmpty(EmployeeDataBean.getEmpEmployeeName())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("emp_Name").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee Name."));
			}
			valid = false;
		} else if(!StringUtils.isEmpty(EmployeeDataBean.getEmpEmployeeName())){
			if(!CommonValidate.validateName(EmployeeDataBean.getEmpEmployeeName())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("emp_Name").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Employee Name."));
					}
					valid = false;
			}
		}
		if (StringUtils.isEmpty(EmployeeDataBean.getEmpEmployeeId())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("emp_Id").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee ID."));
			}
			valid = false;
		} /*else if(!StringUtils.isEmpty(EmployeeDataBean.getEmpEmployeeId())){
			if(!CommonValidate.validateName(EmployeeDataBean.getEmpEmployeeId())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("emp_Id").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Employee ID."));
					}
					valid = false;
			}
		}*/
		if(EmployeeDataBean.getEmpDob()==null){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("emp_Dob").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee DOB."));
				}
				valid = false;
		}
		if (StringUtils.isEmpty(EmployeeDataBean.getEmpMaildId())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("emp_Mail").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Mail ID."));
			}
			valid = false;
		} else if(!StringUtils.isEmpty(EmployeeDataBean.getEmpMaildId())){
			if(!CommonValidate.validateEmail(EmployeeDataBean.getEmpMaildId())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("emp_Mail").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Mail ID."));
					}
					valid = false;
			}
		}
		if (StringUtils.isEmpty(EmployeeDataBean.getEmpGender())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("emp_Gender").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Choose the Gender."));
			}
			valid = false;
		} 
		
		if (StringUtils.isEmpty(EmployeeDataBean.getEmpPhoneNo())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("emp_Phone").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Phone Number."));
			}
			valid = false;
		} else if(!StringUtils.isEmpty(EmployeeDataBean.getEmpPhoneNo())){
			if(!CommonValidate.isNumeric(EmployeeDataBean.getEmpPhoneNo())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("emp_Phone").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Phone Number."));
					}
					valid = false;
			}
		}
		if (StringUtils.isEmpty(EmployeeDataBean.getEmpAddress())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("emp_Address").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Address."));
			}
			valid = false;
		} 
		if (StringUtils.isEmpty(EmployeeDataBean.getEmpQualification())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("emp_Qual").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Qualification."));
			}
			valid = false;
		} else if(!StringUtils.isEmpty(EmployeeDataBean.getEmpQualification())){
			if(!CommonValidate.validateName(EmployeeDataBean.getEmpQualification())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("emp_Qual").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Qualification."));
					}
					valid = false;
			}
		}
		if(EmployeeDataBean.getEmpEntryDate()==null){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("emp_EDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee Entry Date."));
				}
				valid = false;
		}
		if(EmployeeDataBean.getEmpJoinDate()==null){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("emp_JDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee Join Date."));
				}
				valid = false;
		}
		if (StringUtils.isEmpty(EmployeeDataBean.getEmpBasicSalary())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("emp_BSalary").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee Basic Salary."));
			}
			valid = false;
		} else if(!StringUtils.isEmpty(EmployeeDataBean.getEmpBasicSalary())){
			if(!CommonValidate.validateNumber(EmployeeDataBean.getEmpBasicSalary())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("emp_BSalary").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Employee Basic Salary."));
					}
					valid = false;
			}
		}
		if (StringUtils.isEmpty(EmployeeDataBean.getEmpDesignation())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("emp_Desgin").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee Designation."));
			}
			valid = false;
		} else if(!StringUtils.isEmpty(EmployeeDataBean.getEmpDesignation())){
			if(!CommonValidate.validateName(EmployeeDataBean.getEmpDesignation())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("emp_Desgin").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Employee Designation."));
					}
					valid = false;
			}
		}
		return valid;
	}
	public String cancel() {
		System.out.println("Inside Cancel Method Calling..");
		return "cancelSuccess";
		
	}
	public String reset() {
		System.out.println("Inside Reset Method Calling...");
		EmployeeDataBean.setEmpAddress("");
		EmployeeDataBean.setEmpBasicSalary("");
		EmployeeDataBean.setEmpDescription("");
		EmployeeDataBean.setEmpDesignation("");
		EmployeeDataBean.setEmpDob(null);
		EmployeeDataBean.setEmpEmployeeId("");
		EmployeeDataBean.setEmpEmployeeName("");
		EmployeeDataBean.setEmpEntryDate(null);
		EmployeeDataBean.setEmpGender("");
		EmployeeDataBean.setEmpJoinDate(null);
		EmployeeDataBean.setEmpMaildId("");
		EmployeeDataBean.setEmpPhoneNo("");
		EmployeeDataBean.setEmpQualification("");
		return "";
		
	}
	@PostConstruct
	public void init(){
		System.out.println("Constructor Calling ....");
		LiusenController controller = null;
		qualList = new ArrayList<String>();
		designationList= new ArrayList<String>();
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			qualList=controller.getQualification();
			designationList=controller.getDesignation();
			System.out.println("qualList"+qualList);
			System.out.println("designationList"+designationList);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
