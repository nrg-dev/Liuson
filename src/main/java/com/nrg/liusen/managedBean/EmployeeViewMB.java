package com.nrg.liusen.managedBean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.web.servlets.Controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.EmployeeDataBean;
import com.nrg.liusen.shared.Employee;
import com.nrg.liusen.shared.Vendor;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="employeeViewMB")
@RequestScoped
public class EmployeeViewMB {
	private boolean flag=true;
	EmployeeDataBean employeeDataBean=new EmployeeDataBean();
	
	private List<String> empIDList=null;
	private List<Employee>emplist=null;
	private List<EmployeeDataBean>empviewlist=null;
	private List<String> qualList=null;
	private List<String> designationList=null;
	private boolean  delflag=false;
	private boolean  editflag=false;
	
	
	public boolean isDelflag() {
		return delflag;
	}
	public void setDelflag(boolean delflag) {
		this.delflag = delflag;
	}
	public boolean isEditflag() {
		return editflag;
	}
	public void setEditflag(boolean editflag) {
		this.editflag = editflag;
	}
	public List<String> getQualList() {
		return qualList;
	}
	public void setQualList(List<String> qualList) {
		this.qualList = qualList;
	}
	public List<String> getDesignationList() {
		return designationList;
	}
	public void setDesignationList(List<String> designationList) {
		this.designationList = designationList;
	}
	public List<EmployeeDataBean> getEmpviewlist() {
		return empviewlist;
	}
	public void setEmpviewlist(List<EmployeeDataBean> empviewlist) {
		this.empviewlist = empviewlist;
	}
	public List<Employee> getEmplist() {
		return emplist;
	}
	public void setEmplist(List<Employee> emplist) {
		this.emplist = emplist;
	}
	/**
	 * @return the empIDList
	 */
	public List<String> getEmpIDList() {
		return empIDList;
	}
	/**
	 * @param empIDList the empIDList to set
	 */
	public void setEmpIDList(List<String> empIDList) {
		this.empIDList = empIDList;
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
	 * @return the employeeDataBean
	 */
	public EmployeeDataBean getEmployeeDataBean() {
		return employeeDataBean;
	}
	/**
	 * @param employeeDataBean the employeeDataBean to set
	 */
	public void setEmployeeDataBean(EmployeeDataBean employeeDataBean) {
		this.employeeDataBean = employeeDataBean;
	}
	public String employeeViewLoad() {

		System.out.println("Inside Load the employeeViewLoad Load Page");
		setFlag(true);
		setValid(true);
		employeeDataBean.setEmpEmployeeName("");
		employeeDataBean.setEmpEmployeeId("");
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		LiusenController	controller = (LiusenController) ctx.getBean("controller");
		System.out.println("empidddd list +++++++++++++++-///---");
	     empIDList=controller.empid();
		return "employeeViewLoadPage";

	}
	/**
	 * This method is used for show data tables depends on project name
	 * 
	 * @return
	 */
	public String searchEmployeeName() {
		
		System.out.println("Inside the searchEmployeeName Method Calling");
		try {
			if (validate(true)) {
				System.out.println("After Validate inside  searchEmployeeName method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager")){
					setDelflag(true);
					setEditflag(true);
				}else if(rolles.equalsIgnoreCase("Finance Manager") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Accounting Staff")){
					setDelflag(true);
					setEditflag(true);
				}
				else{
					setDelflag(false);
					setEditflag(false);	
				}
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController	controller = (LiusenController) ctx.getBean("controller");
				empviewlist=controller.searchname(employeeDataBean);
				System.out.println("Size in MB"+empviewlist.size());
				//setValid(false);
			
			}
			else
			{
				setFlag(true);
			}
	}
			catch(Exception e)
			{
				e.printStackTrace();
				
			}
		return "";		}
	

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
		if (StringUtils.isBlank(employeeDataBean.getEmpEmployeeName())) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("empViewName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Employee Name."));
			}
			valid = false;
		} else if (!StringUtils.isBlank(employeeDataBean.getEmpEmployeeName())) {

			if (!CommonValidate.validateName(employeeDataBean.getEmpEmployeeName())) {
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"empViewName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Enter the valid Employee Name."));
				}
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * This method is used for show data tables depends on Client name
	 * 
	 * @return
	 */

	public String searchEmployeeId() {
		System.out.println("Inside the searchEmployeeId Method Calling");
		LiusenController controller = null;
		try{
			if (validate1(true)) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				System.out.println(rolles);
				if(rolles.equalsIgnoreCase("Project Manager")){
					setDelflag(true);
					setEditflag(true);
				}else if(rolles.equalsIgnoreCase("Finance Manager") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Accounting Staff") ){
					setDelflag(true);
					setEditflag(true);
				}else{
					setDelflag(false);
					setEditflag(false);	
				}
				System.out.println("After Validate1 inside  searchEmployeeId method");
			   empviewlist=controller.searchid(employeeDataBean);
				setFlag(false);
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
		if (StringUtils.isBlank(employeeDataBean.getEmpEmployeeId())) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("empViewID")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Employee ID."));
			}
			valid = false;
		} else if (!StringUtils.isBlank(employeeDataBean.getEmpEmployeeId())) {

			if (!CommonValidate.validateName(employeeDataBean.getEmpEmployeeId())) {
				if (flag2) {
					fieldName = CommonValidate.findComponentInRoot(
							"empViewID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Enter the valid Employee ID."));
				}
				valid = false;
			}
		}
		return valid;
	}
	@PostConstruct
	public void init(){
		System.out.println("Inside Constructor Method Calling...");
		LiusenController controller = null;
		try{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			empIDList=controller.getEmployeeID();
			System.out.println(empIDList);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void empview()
	{
		 System.out.println("Inside"+employeeDataBean.getEmpEmployeeName());
		 LiusenController controller = null;
		 if(!employeeDataBean.getEmpEmployeeId().equalsIgnoreCase("")){
			 try{
				 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				 controller = (LiusenController) ctx.getBean("controller");
				emplist=controller.getemp(employeeDataBean);
				 if(emplist.size()>0){
					
						employeeDataBean.setEmpAddress(emplist.get(0).getEmployeeAddress());
						employeeDataBean.setEmpBasicSalary(emplist.get(0).getEmployeeBasicSalary());
						employeeDataBean.setEmpDescription(emplist.get(0).getEmployeeDescription());
						employeeDataBean.setEmpDesignation(emplist.get(0).getDesignation().getDesignation_Name());
						employeeDataBean.setEmpDob(emplist.get(0).getEmployeeDob());
						employeeDataBean.setEmpEmployeeId(emplist.get(0).getEmployee_details_ID());
						employeeDataBean.setEmpEmployeeName(emplist.get(0).getEmployeeName());
						employeeDataBean.setEmpEntryDate(emplist.get(0).getEmployeeEntryDate());
						employeeDataBean.setEmpGender(emplist.get(0).getEmployeeGender());
						employeeDataBean.setEmpJoinDate(emplist.get(0).getEmployeeJointDate());
						employeeDataBean.setEmpMaildId(emplist.get(0).getEmployeeEmail());
						employeeDataBean.setEmpPhoneNo(emplist.get(0).getEmpPhone());
					
						 }
				 
			 }
			 catch(Exception e){
					e.printStackTrace();
				}
		 }
	}
	
	public String empedit()
	{
		System.out.println("Inside Edit Method");
		LiusenController controller=null;
		 if(!employeeDataBean.getEmpEmployeeName().equalsIgnoreCase(""))
		 {
              try{
				 valid = true; 
				 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				 controller = (LiusenController) ctx.getBean("controller");
				 emplist=controller.getempedit(employeeDataBean.getEmpEmployeeId(),employeeDataBean);
			
					 qualList=controller.getQualification();
					 designationList=controller.getDesignation();
				
                }catch(Exception e)
                   {
				 e.printStackTrace();
              }
		 }
              else  if(!employeeDataBean.getEmpEmployeeId().equalsIgnoreCase(""))
     		 {
                   try{
     				 valid = true; 
     				 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
     				 controller = (LiusenController) ctx.getBean("controller");
     				 emplist=controller.getempedit(employeeDataBean.getEmpEmployeeId(),employeeDataBean);
     			
     					 qualList=controller.getQualification();
     					 designationList=controller.getDesignation();
     				
                     }catch(Exception e)
                        {
     				 e.printStackTrace();
                   }
		 }
		return "employeeedit";
		
	}
	
	private boolean valid =true;

	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	} 
	public String cancel()
	{
		return "Winner";
		
	}
	private String status;
	
	public String save()
	{
		System.out.println("Inside Save method Calling");
		LiusenController controller = null;
		if (validateEdit(true)) {
			try {
				System.out.println("After Validation inside Save method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				status = controller.getupdate(employeeDataBean);
				System.out.println("Status" + status);
				setValid(false);

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
		if (StringUtils.isEmpty(employeeDataBean.getEmpEmployeeName())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("empName").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter Employee Name."));
			}
			valid = false;
		}
		else if (StringUtils.isNotEmpty(employeeDataBean.getEmpEmployeeName())) {
			if (!CommonValidate.validateName(employeeDataBean.getEmpEmployeeName())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("empName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Employee Name."));
				}
				valid = false;
			}
		}
		if(employeeDataBean.getEmpDob()==null){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("empdob").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee DOB."));
				}
				valid = false;
		}
		if(employeeDataBean.getEmpEntryDate()==null){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("empentry").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee Entry Date."));
				}
				valid = false;
		}
		
		if(employeeDataBean.getEmpJoinDate()==null){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("empjoin").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee Joining Date."));
				}
				valid = false;
		}
		if (StringUtils.isEmpty(employeeDataBean.getEmpEmployeeId())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("empid").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter Employee ID."));
			}
			valid = false;
		}
		/*else if (StringUtils.isNotEmpty(employeeDataBean.getEmpEmployeeId())) {
			if (!CommonValidate.validateName(employeeDataBean.getEmpEmployeeId())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("empid").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Employee id."));
				}
				valid = false;
			}
		}*/
		
		if (StringUtils.isEmpty(employeeDataBean.getEmpAddress())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("empAddres").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter Employee Address."));
			}
			valid = false;
		}
		else if (StringUtils.isNotEmpty(employeeDataBean.getEmpAddress())) {
			if (!CommonValidate.validateName(employeeDataBean.getEmpAddress())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("empAddres").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Employee Addresss."));
				}
				valid = false;
			}
		}
		
		if (StringUtils.isEmpty(employeeDataBean.getEmpBasicSalary())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("empsalary").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter Employee Basic Salary."));
			}
			valid = false;
		}
		else if (!StringUtils.isEmpty(employeeDataBean.getEmpBasicSalary())) {
			if (!CommonValidate.validateNumber(employeeDataBean.getEmpBasicSalary())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("empsalary").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Employee Basic Salary."));
				}
				valid = false;
			}
		}
		
		if (employeeDataBean.getEmpDesignation().equals("emp")) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("empdesignation").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter Employee Designation ."));
			}
			valid = false;
		}
		
		if (StringUtils.isEmpty(employeeDataBean.getEmpGender())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("empgender").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter Employee Gender ."));
			}
			valid = false;
		}
		else if (StringUtils.isNotEmpty(employeeDataBean.getEmpGender())) {
			if (!CommonValidate.validateName(employeeDataBean.getEmpGender())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("empgender").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Employee Gender."));
				}
				valid = false;
			}
		}
		
		if (employeeDataBean.getEmpQualification().equals("emp1")) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("empquailfication").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter Employee Qualification ."));
			}
			valid = false;
		}
		
		if (StringUtils.isEmpty(employeeDataBean.getEmpPhoneNo())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("empphone").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter Employee Phone Number ."));
			}
			valid = false;
		}
		else if (StringUtils.isNotEmpty(employeeDataBean.getEmpPhoneNo())) {
			if (!CommonValidate.validatePhone(employeeDataBean.getEmpPhoneNo())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("empphone").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Employee Phone Number."));
				}
				valid = false;
			}
		}
		
		if (StringUtils.isEmpty(employeeDataBean.getEmpMaildId())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("empmail").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter Employee Mail ID ."));
			}
			valid = false;
		}
		
		else if (StringUtils.isNotEmpty(employeeDataBean.getEmpMaildId())) {
			if (!CommonValidate.validateEmail(employeeDataBean.getEmpMaildId())) {
				if(flag){
				fieldName = CommonValidate.findComponentInRoot("empmail").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Employee Mail ID ."));
				}
				valid = false;
			}
		}
		
		return valid;

					}
	
	
	
	
	public String delete()
	{
		System.out.println("inside delete method calling");
		System.out.println(employeeDataBean.getEmpEmployeeName());
		LiusenController controller = null;
		if(employeeDataBean.getEmpEmployeeName() != null){
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			status=controller.deleteemployee(employeeDataBean);
		    setValid(false);
			
		}
			
		return status;
		
	}
	
	
	
}
