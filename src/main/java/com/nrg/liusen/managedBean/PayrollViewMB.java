package com.nrg.liusen.managedBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.EmployeeDataBean;
import com.nrg.liusen.domain.ProductDataBean;
import com.nrg.liusen.shared.Payroll;
import com.nrg.liusen.shared.Product;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="payrollViewMB")
@RequestScoped
public class PayrollViewMB {
	
	List<String> empIDList=null;
	List<String> empNameList=null;
	List<Payroll> payList=null;
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

	private List<EmployeeDataBean> payrollViewList=null;
	
	
	public List<Payroll> getPayList() {
		return payList;
	}
	public void setPayList(List<Payroll> payList) {
		this.payList = payList;
	}
	public List<EmployeeDataBean> getPayrollViewList() {
		return payrollViewList;
	}
	public void setPayrollViewList(List<EmployeeDataBean> payrollViewList) {
		this.payrollViewList = payrollViewList;
	}
	public List<String> getEmpIDList() {
		return empIDList;
	}
	public void setEmpIDList(List<String> empIDList) {
		this.empIDList = empIDList;
	}
	public List<String> getEmpNameList() {
		return empNameList;
	}
	public void setEmpNameList(List<String> empNameList) {
		this.empNameList = empNameList;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	private boolean flag=true;
	private boolean valid=true;
	private boolean val=true;
	private boolean flag5=true;
	
	EmployeeDataBean EmployeeDataBean=new EmployeeDataBean();
	/**
	 * @return the flag
	 */
	
	public boolean isFlag() {
		return flag;
	}
	
	public boolean isFlag5() {
		return flag5;
	}
	public void setFlag5(boolean flag5) {
		this.flag5 = flag5;
	}
	public boolean isVal() {
		return val;
	}
	public void setVal(boolean val) {
		this.val = val;
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
		return EmployeeDataBean;
	}
	/**
	 * @param employeeDataBean the employeeDataBean to set
	 */
	public void setEmployeeDataBean(EmployeeDataBean employeeDataBean) {
		EmployeeDataBean = employeeDataBean;
	}
	public String payrollViewLoad() {

		System.out.println("Inside Load the payrollViewLoad Load Page");
		EmployeeDataBean.setEmpEmployeeId("");
		EmployeeDataBean.setEmpEmployeeName("");
		/*EmployeeDataBean.setEmpPayCommision("0");
		EmployeeDataBean.setEmpPayLoanAdvance("0");
		EmployeeDataBean.setEmpPayOTAmmount("0");*/
		setFlag(true);
		setVal(true);
		setFlag5(true);
		
		return "payrollViewLoadPage";

	}
	/**
	 * This method is used for show data tables depends on project name
	 * 
	 * @return
	 */
	public String searchEmployeeName() {
		
		System.out.println("Inside the searchEmployeeName Method Calling");
		try {
			LiusenController controller = null;
			if (validate(true)) {
				System.out.println("After Validate inside  searchEmployeeName method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Accounting Staff")){
					setEditflag(true);
					setDeleteflag(true);
				}else{
					setEditflag(false);
					setDeleteflag(false);	
				}
				setFlag(false);
				
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				payrollViewList=controller.getPayrollList(EmployeeDataBean);
				/*setPayrollViewList(EmployeeDataBean.getPayrollList());*/
			}else{
				setFlag(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (EmployeeDataBean.getEmpEmployeeName().equals("010")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("payViewEName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Employee Name."));
			}
			valid = false;
		} 
		return valid;
	}

	/**
	 * This method is used for show data tables depends on Client name
	 * 
	 * @return
	 */

	public String searchEmployeeId() 
	{
		System.out.println("Inside the searchEmployeeId Method Calling");
		try
		{
			LiusenController controller = null;
			if (validate1(true))
			{
				System.out.println("After Validate1 inside  searchEmployeeId method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Accounting Staff")){
					setDeleteflag(true);
					setEditflag(true);
				}else{
					setDeleteflag(false);
					setEditflag(false);	
				}
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				payrollViewList=controller.getPayrollList1(EmployeeDataBean);
				val=true;
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
		return "";

	}

	private boolean validate1(boolean flag2) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (EmployeeDataBean.getEmpEmployeeId().equals("000")) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("payViewEId")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Employee ID."));
			}
			valid = false;
		} 
		return valid;
	}
	
	@PostConstruct
	public void init()
	{
		LiusenController controller = null;	
		try
		{
			System.out.println("Inside Construtor Calling");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			
			List<String> empID=controller.getEmployeeID();
			if(empID.size() > 0)
			{
				empIDList=new ArrayList<String>();
				empIDList.addAll(empID);
			}
			
			List<String> empName=controller.getEmployeeName();
			if(empName.size() > 0)
			{
				empNameList=new ArrayList<String>();
				empNameList.addAll(empName);
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public String payrollView() 
	{
		LiusenController controller = null;
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			payList=controller.getPayrollListSingle(EmployeeDataBean);
			System.out.println("Size"+payList.size());
			if(payList.size() > 0)
			{
				EmployeeDataBean.setEmpBasicSalary(payList.get(0).getEmployee().getEmployeeBasicSalary());
				EmployeeDataBean.setEmpDesignation(payList.get(0).getEmployee().getDesignation().getDesignation_Name());
				EmployeeDataBean.setEmpEmployeeId(payList.get(0).getEmployee().getEmployee_details_ID());
				EmployeeDataBean.setEmpEmployeeName(payList.get(0).getEmployee().getEmployeeName());
				EmployeeDataBean.setEmpPayCommision(payList.get(0).getCommisionAmount());
				EmployeeDataBean.setEmpPayLoanAdvance(payList.get(0).getLoanAdvance());
				EmployeeDataBean.setEmpPayMonth(payList.get(0).getPayrollMonth());
				EmployeeDataBean.setEmpPayOTAmmount(payList.get(0).getOtAmoount());
				EmployeeDataBean.setEmpPayTotalSalary(payList.get(0).getTotalSalary());
				EmployeeDataBean.setEmpPayWorkDays(""+payList.get(0).getWorkingDays());
				System.out.println("out===");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public String delete() {
		LiusenController controller = null;
		String Status="Failure";
		try{
			System.out.println("Inside Delete method calling....");
			System.out.println("employee id"+EmployeeDataBean.getEmpEmployeeId());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			Status=controller.payrollDelete(EmployeeDataBean);
			System.out.println("Status"+Status);
			if(Status.equalsIgnoreCase("success")){
				setVal(false);
			}else{
				setVal(true);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String payrollEdit() 
	{
		/*String status="payrollListtNotEmpty";*/
		System.out.println("Inside payrollEdit method");
		LiusenController controller = null;
		setFlag(true);
		try
		{
		if(!EmployeeDataBean.getEmpEmployeeName().equalsIgnoreCase(""))	
		{
			 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			 controller = (LiusenController) ctx.getBean("controller");
			 payList=controller.getPayrollInfo(EmployeeDataBean);	
			 System.out.println("payList Size"+payList.size());
			 if(payList.size()>0)
			 {
				 for(Payroll payroll: payList)
				 {
					 System.out.println("Inside or loop");
					 	EmployeeDataBean.setEmpBasicSalary(payroll.getEmployee().getEmployeeBasicSalary());
						EmployeeDataBean.setEmpDesignation(payroll.getEmployee().getDesignation().getDesignation_Name());
						EmployeeDataBean.setEmpEmployeeId(payroll.getEmployee().getEmployee_details_ID());
						EmployeeDataBean.setEmpEmployeeName(payroll.getEmployee().getEmployeeName());
						EmployeeDataBean.setEmpPayCommision(payroll.getCommisionAmount());
						EmployeeDataBean.setEmpPayLoanAdvance(payroll.getLoanAdvance());
						EmployeeDataBean.setEmpPayMonth(payroll.getPayrollMonth());
						EmployeeDataBean.setEmpPayOTAmmount(payroll.getOtAmoount());
						EmployeeDataBean.setEmpPayTotalSalary(payroll.getTotalSalary());
						EmployeeDataBean.setEmpPayWorkDays(""+payroll.getWorkingDays()); 
						EmployeeDataBean.setEmpPayTodayDate(payroll.getPayDate());
						
				 }
				/* status="payrollListNotEmpty";*/
				 return "payrollUpdatePage";
			 }
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	
	
	
	
	
	
	public String submit()
	{
		setFlag5(false);
		LiusenController controller = null;
		try
		{
			System.out.println("days - > "+EmployeeDataBean.getEmpPayWorkDays());
			if (validateEdit(true))
			{
				System.out.println("After Validation inside Submit method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				String status=controller.editProduct(EmployeeDataBean); 
				setFlag5(false);
			}
			else
			{
				System.out.println(" validate -- ");
				setFlag5(true);
			}
		}
		catch(Exception e)
		{
		e.printStackTrace();	
		}
		return null;
		
	}
	
	public void calComision(ValueChangeEvent v)
	{
		BigDecimal  total=BigDecimal.valueOf(0);
		try
		{
			
		System.out.println("Inside calcomision value change");
		System.out.println("------------"+v.getNewValue());
		EmployeeDataBean.setEmpPayCommision(v.getNewValue().toString());
		System.out.println("------employ--"+EmployeeDataBean.getEmpPayOTAmmount());
		String a =v.getNewValue().toString();
		
		System.out.println("Basic salary========"+EmployeeDataBean.getEmpBasicSalary());
		System.out.println("loan========"+EmployeeDataBean.getEmpPayLoanAdvance());
		System.out.println("ot========"+EmployeeDataBean.getEmpPayOTAmmount());
		
		total=new BigDecimal(EmployeeDataBean.getEmpBasicSalary()).add(new BigDecimal(EmployeeDataBean.getEmpPayOTAmmount())).subtract(new BigDecimal(EmployeeDataBean.getEmpPayLoanAdvance())) .add(new BigDecimal(a));
		EmployeeDataBean.setEmpPayTotalSalary(""+total);
		System.out.println("------wwwwww------");
		EmployeeDataBean.setEmpPayCommision(a);
		System.out.println("commission - -> "+EmployeeDataBean.getEmpPayCommision());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
	}
	
	
	public void calAmt(ValueChangeEvent v1)
	{
		BigDecimal  tot=BigDecimal.valueOf(0);
		try
		{
		System.out.println("------------"+v1.getNewValue());
		EmployeeDataBean.setEmpPayOTAmmount(v1.getNewValue().toString());
		System.out.println("----"+EmployeeDataBean.getEmpPayOTAmmount());
		System.out.println("commission -1-> "+EmployeeDataBean.getEmpPayCommision());
		
		String b=v1.getNewValue().toString();
		try
		{
		tot=new BigDecimal(EmployeeDataBean.getEmpBasicSalary()).add(new BigDecimal(b)).subtract(new BigDecimal(EmployeeDataBean.getEmpPayLoanAdvance())) .add(new BigDecimal(EmployeeDataBean.getEmpPayCommision()));
		System.out.println("----tot---"+EmployeeDataBean.getEmpBasicSalary());
		System.out.println("-----tot--"+EmployeeDataBean.getEmpPayLoanAdvance());
		System.out.println("-----tot-----"+EmployeeDataBean.getEmpPayCommision());
		}
	    catch(NumberFormatException e)
	    {
	    	e.printStackTrace();
	    }
		EmployeeDataBean.setEmpPayTotalSalary(""+tot);
		EmployeeDataBean.setEmpPayOTAmmount(b);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void calLoan(ValueChangeEvent v2)
	{
		BigDecimal  tot1=BigDecimal.valueOf(0);
		try
		{
			
		
		System.out.println("------------"+v2.getNewValue());
		EmployeeDataBean.setEmpPayLoanAdvance(v2.getNewValue().toString());
		
		
		String c=v2.getNewValue().toString();
		
		tot1=new BigDecimal(EmployeeDataBean.getEmpBasicSalary()).add(new BigDecimal(EmployeeDataBean.getEmpPayOTAmmount())).subtract(new BigDecimal(c)) .add(new BigDecimal(EmployeeDataBean.getEmpPayCommision()));
		EmployeeDataBean.setEmpPayTotalSalary(""+tot1);
		EmployeeDataBean.setEmpPayLoanAdvance(c);
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	


	private boolean validateEdit(boolean flag3) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		
		
		
		if(StringUtils.isBlank(EmployeeDataBean.getEmpPayWorkDays())){
			if(flag3){
			fieldName = CommonValidate.findComponentInRoot("payGenWork").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the Working Days."));
		}
			valid=false;
		}
		else if(!StringUtils.isBlank(EmployeeDataBean.getEmpPayWorkDays())){
			if(!CommonValidate.validateNumber(EmployeeDataBean.getEmpPayWorkDays())){
				if(flag3){
					fieldName = CommonValidate.findComponentInRoot("payGenWork").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please  Enter the valid Working Days."));
				}
					valid=false;
			}
		}
		if(!EmployeeDataBean.getEmpPayWorkDays().equals(""))
		{
			System.out.println(" iii ");
			if(Integer.parseInt(EmployeeDataBean.getEmpPayWorkDays()) > 31)
			{
				if(flag3)
				{
					fieldName = CommonValidate.findComponentInRoot("payGenWork").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please  Enter the valid Working Days."));
				}
				valid=false;
			}
		}
		
		return valid;

	
}
	
	
	public String cancel() {
		
		System.out.println("----------Inside cancel Method Calling-----");

		return "cancelSuccess1";
	} 
	
}
