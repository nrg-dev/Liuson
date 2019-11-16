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
import javax.net.ssl.SSLEngineResult.Status;

import net.sf.jasperreports.web.servlets.Controller;

import org.apache.commons.lang.StringUtils;
import org.neo4j.cypher.internal.compiler.v2_1.perty.docbuilders.catchErrors;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.EmployeeDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="payrollMB")
@RequestScoped
public class PayrollMB {
	EmployeeDataBean employeeDataBean = new EmployeeDataBean();
	private boolean flag=true;
	private boolean panelflag=true;
	private boolean valid=true;
	private List<String> empNameList=null;
	private List<String> empIDList=null;
	
	
	public List<String> getEmpNameList() {
		return empNameList;
	}
	public void setEmpNameList(List<String> empNameList) {
		this.empNameList = empNameList;
	}
	public List<String> getEmpIDList() {
		return empIDList;
	}
	public void setEmpIDList(List<String> empIDList) {
		this.empIDList = empIDList;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	/**
	 * @return the panelflag
	 */
	public boolean isPanelflag() {
		return panelflag;
	}
	/**
	 * @param panelflag the panelflag to set
	 */
	public void setPanelflag(boolean panelflag) {
		this.panelflag = panelflag;
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
	
	public String payrollLoad() {

		System.out.println("Inside Load the payrollLoad Load Page");
		setFlag(true);
		setPanelflag(true);
		setValid(false);
		LiusenController controller = null;	
		try
		{
			System.out.println("Inside Construtor Calling");
			employeeDataBean.setEmpBasicSalary("");
			employeeDataBean.setEmpDesignation("");
			employeeDataBean.setEmpPayCommision("0");
			employeeDataBean.setEmpEmployeeName("");
			employeeDataBean.setEmpEmployeeId("");
			employeeDataBean.setEmpPayempId("");
			employeeDataBean.setEmpPayempName("");
			employeeDataBean.setEmpPayLoanAdvance("0");
			employeeDataBean.setEmpPayMonth(null);
			employeeDataBean.setEmpPayOTAmmount("0");
			employeeDataBean.setEmpPayTodayDate(null);
			employeeDataBean.setEmpDesignation("");
			employeeDataBean.setEmpPayTotalSalary("");
			employeeDataBean.setEmpPayWorkDays("");
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
		return "payrollLoadPage";

	}
public String searchPayEmpName() {
		
	System.out.println("Inside the searchPayEmpName Method Calling");
	FacesContext fc = FacesContext.getCurrentInstance();
	String status="failed";
	try {
		if (validate(true)) {
			System.out.println("After Validate inside  searchPayEmpName method");
			setFlag(false);
			setPanelflag(false);
			LiusenController controller = null;
			employeeDataBean.setValidmonth("");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			status=controller.payroll1(employeeDataBean);
			System.out.println("validation ::: "+employeeDataBean.getValidmonth());
			if (valida2(true)) 
			{
				employeeDataBean.setEmpPayOTAmmount("0");
				employeeDataBean.setEmpPayWorkDays("");
				employeeDataBean.setEmpPayLoanAdvance("0");
				employeeDataBean.setEmpPayTotalSalary("");
				employeeDataBean.setEmpPayCommision("0");
				employeeDataBean.setEmpPayTotalSalary(employeeDataBean.getEmpBasicSalary());
			}
			else
			{
				System.out.println("else==");					
				setFlag(true);
				setPanelflag(true);
			}				
		}else{
			setFlag(true);
			setPanelflag(true);
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
		
		String fieldName;
		boolean valid=true;
		FacesContext fc = FacesContext.getCurrentInstance();
		System.out.println("Inside validation");
		if(employeeDataBean.getEmpPayMonth()==null){
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("payMonth").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
			}
			valid = false;
		}
		if(employeeDataBean.getEmpPayMonth()==null){
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("payMonth1").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
			}
			valid = false;
		}
		if (employeeDataBean.getEmpEmployeeName().equalsIgnoreCase("010")) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("payEmpName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee Name."));
			}
			valid = false;
		}
		
		System.out.println("valid : "+valid);
		return valid;
	}

public String searchPayEmpID() {
		
		System.out.println("Inside the searchPayEmpID Method Calling");
		FacesContext fc = FacesContext.getCurrentInstance();
		String status="failed";
		try {
			if (validate1(true)) {
				System.out.println("After Validate inside  searchPayEmpID method");
				setFlag(false);
				setPanelflag(false);
				LiusenController controller = null;
				employeeDataBean.setValidmonth("");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				status=controller.payroll(employeeDataBean);
				System.out.println("validation ::: "+employeeDataBean.getValidmonth());
				if (valida(true)) 
				{
					employeeDataBean.setEmpPayOTAmmount("0");
					employeeDataBean.setEmpPayWorkDays("");
					employeeDataBean.setEmpPayLoanAdvance("0");
					employeeDataBean.setEmpPayTotalSalary("");
					employeeDataBean.setEmpPayCommision("0");
					employeeDataBean.setEmpPayTotalSalary(employeeDataBean.getEmpBasicSalary());
				}
				else
				{
					System.out.println("47777");					
					setFlag(true);
					setPanelflag(true);
				}				
			}else{
				setFlag(true);
				setPanelflag(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
private boolean valida(boolean flag2)
{
	boolean valid = true;
	String fieldName;
	FacesContext fc = FacesContext.getCurrentInstance();
	if(employeeDataBean.getValidmonth().equals("Exist"))
	{
		if (flag2) 
		{
			System.out.println("insideeeeeeeeeeee");
			fieldName = CommonValidate.findComponentInRoot("payEmpId").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("payroll is already generated"));
		}
		valid = false;
	}
	
	return valid;
}


private boolean valida2(boolean flag2)
{
	boolean valid = true;
	String fieldName;
	FacesContext fc = FacesContext.getCurrentInstance();
	if(employeeDataBean.getValidmonth().equals("Exist"))
	{
		if (flag2) 
		{
			System.out.println("insideeeeeeeeeeee");
			fieldName = CommonValidate.findComponentInRoot("payEmpName").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("payroll is already generated"));
		}
		valid = false;
	}
	
	return valid;
}
	/**
	 * This method is used to validate data
	 * 
	 * @param valid
	 * @return false show error message
	 * @return true not showing error message
	 */
	private boolean validate1(boolean flag2)
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		System.out.println("Inside validtion");
		if(employeeDataBean.getEmpPayMonth()==null)
		{
			if (flag2) 
			{
				fieldName = CommonValidate.findComponentInRoot("payMonth").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
			}
			valid = false;
		}
		if(employeeDataBean.getEmpPayMonth()==null){
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("payMonth1")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Date."));
			}
			valid = false;
		}
		if (employeeDataBean.getEmpEmployeeId().equalsIgnoreCase("000")) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("payEmpId").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee ID."));
			}
			valid = false;
		} 
		
		return valid;
	}
	
public String submit() {
		
		System.out.println("Inside the submit Method Calling");
		FacesContext fc = FacesContext.getCurrentInstance();
		String status="Failed";
		try {
			if (validate2(true)) {
				System.out.println("After Validate inside  submit method"+employeeDataBean.getEmpPayMonth()+"  -- "+employeeDataBean.getEmpPayYear());
				LiusenController controller = null;
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				status =controller.insertpayroll(employeeDataBean);
				valid=true;
				setFlag(false);
				setPanelflag(false);
				System.out.println("---valid---"+valid);
			}
			else
			{
				valid=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
		private boolean validate2(boolean flag3) {
			boolean valid = true;
			String fieldName;
			FacesContext fc = FacesContext.getCurrentInstance();
			/*if(employeeDataBean.getEmpPayTodayDate()==null){
				if(flag3){
				fieldName = CommonValidate.findComponentInRoot("payGenDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
			}
				valid=false;
			}*/
			/*if(StringUtils.isBlank(employeeDataBean.getEmpPayempName())){
				if(flag3){
				fieldName = CommonValidate.findComponentInRoot("payGenEName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee Name."));
			}
				valid=false;
			}*/
			/*if(StringUtils.isBlank(employeeDataBean.getEmpPayempId())){
				if(flag3){
				fieldName = CommonValidate.findComponentInRoot("payGenEId").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee ID."));
			}
				valid=false;
			}*/
			/*if(StringUtils.isBlank(employeeDataBean.getEmpBasicSalary())){
				if(flag3){
				fieldName = CommonValidate.findComponentInRoot("payGenBasic").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Employee Basic Salary."));
			}
				valid=false;
			}
			if(employeeDataBean.getEmpPayMonth()==null){
				if(flag3){
				fieldName = CommonValidate.findComponentInRoot("payGenMonth").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Month and Year."));
			}
				valid=false;
			}*/
			if(StringUtils.isBlank(employeeDataBean.getEmpPayWorkDays())){
				if(flag3){
				fieldName = CommonValidate.findComponentInRoot("payGenWork").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Working Days."));
			}
				valid=false;
			}else if(!StringUtils.isBlank(employeeDataBean.getEmpPayWorkDays())){
				if(!CommonValidate.validateNumber(employeeDataBean.getEmpPayWorkDays())){
					if(flag3){
						fieldName = CommonValidate.findComponentInRoot("payGenWork").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please  Enter the valid Working Days."));
					}
						valid=false;
				}
			}
			if(!employeeDataBean.getEmpPayWorkDays().equals("")){
			if(Integer.parseInt(employeeDataBean.getEmpPayWorkDays()) > 31)
			{
				if(flag3){
					fieldName = CommonValidate.findComponentInRoot("payGenWork").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please  Enter the valid Working Days."));
				}
				valid=false;
			}
			}
			if(StringUtils.isBlank(employeeDataBean.getEmpPayCommision())){
				if(flag3){
				fieldName = CommonValidate.findComponentInRoot("payGenComm").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Commision Ammount."));
			}
				valid=false;
			}
			if(StringUtils.isBlank(employeeDataBean.getEmpPayOTAmmount())){
				if(flag3){
				fieldName = CommonValidate.findComponentInRoot("payGenOT").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Over Time Ammount."));
			}
				valid=false;
			}else if(!StringUtils.isBlank(employeeDataBean.getEmpPayOTAmmount())){
				if(!CommonValidate.validateNumber(employeeDataBean.getEmpPayOTAmmount())){
					if(flag3){
						fieldName = CommonValidate.findComponentInRoot("payGenOT").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Over Time Ammount."));
					}
						valid=false;
				}
			}
			if(StringUtils.isBlank(employeeDataBean.getEmpPayLoanAdvance())){
				if(flag3){
				fieldName = CommonValidate.findComponentInRoot("payGenLoan").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Loan Advance Ammount."));
			}
				valid=false;
			}else if(!StringUtils.isBlank(employeeDataBean.getEmpPayLoanAdvance())){
				if(!CommonValidate.validateNumber(employeeDataBean.getEmpPayLoanAdvance())){
					if(flag3){
						fieldName = CommonValidate.findComponentInRoot("payGenLoan").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Loan Advance Ammount."));
					}
						valid=false;
				}
			}
			return valid;
		}
		public String cancel() {
		
				System.out.println("----------Inside cancel Method Calling-----");

				return "cancelSuccess";
			} 
		public String reset() {
			employeeDataBean.setEmpBasicSalary("");
			employeeDataBean.setEmpDesignation("0");
			employeeDataBean.setEmpPayCommision("");
			employeeDataBean.setEmpEmployeeName("");
			employeeDataBean.setEmpEmployeeId("");
			employeeDataBean.setEmpPayempId("");
			employeeDataBean.setEmpPayempName("");
			employeeDataBean.setEmpPayLoanAdvance("0");
			employeeDataBean.setEmpPayMonth(null);
			employeeDataBean.setEmpPayOTAmmount("0");
			employeeDataBean.setEmpPayTodayDate(null);
			employeeDataBean.setEmpDesignation("");
			employeeDataBean.setEmpPayTotalSalary(null);
			setFlag(true);
			setPanelflag(true);
			return "";
			
		}
		public void calComision(ValueChangeEvent v)
		{
			BigDecimal  total=BigDecimal.valueOf(0);
			try
			{
				
			System.out.println("Inside calcomision value change");
			System.out.println("------------"+v.getNewValue());
			employeeDataBean.setEmpPayCommision(v.getNewValue().toString());
				/*if(!employeeDataBean.getEmpPayLoanAdvance().equals(""))
				{
					employeeDataBean.setEmpPayCommision(v.getNewValue().toString());
					System.out.println("--loan ---"+employeeDataBean.getEmpPayLoanAdvance());
				}
				
				else if(employeeDataBean.getEmpPayLoanAdvance().equals(""))
				{
					employeeDataBean.setEmpPayLoanAdvance("0");
					System.out.println("--loan1---"+employeeDataBean.getEmpPayLoanAdvance());
				}
//				if(!employeeDataBean.getEmpPayOTAmmount().equals(""));
//				{
//					System.out.println("--ot---"+employeeDataBean.getEmpPayOTAmmount());
//				
//				}
				 if(employeeDataBean.getEmpPayOTAmmount().equals("") && employeeDataBean.getEmpPayOTAmmount().equals(null));
				{
					System.out.println("--ot1--yry-"+employeeDataBean.getEmpPayOTAmmount());
					employeeDataBean.setEmpPayOTAmmount("0");
				}
					*/
					System.out.println("------employ--"+employeeDataBean.getEmpPayOTAmmount());
				
				String a =v.getNewValue().toString();

			  System.out.println("Basic salary========"+employeeDataBean.getEmpBasicSalary());
			  System.out.println("loan========"+employeeDataBean.getEmpPayLoanAdvance());
			  System.out.println("ot========"+employeeDataBean.getEmpPayOTAmmount());
				total=new BigDecimal(employeeDataBean.getEmpBasicSalary()).add(new BigDecimal(employeeDataBean.getEmpPayOTAmmount())).subtract(new BigDecimal(employeeDataBean.getEmpPayLoanAdvance())) .add(new BigDecimal(a));
				employeeDataBean.setEmpPayTotalSalary(""+total);
				System.out.println("------wwwwww------");
				employeeDataBean.setEmpPayCommision(a);
				System.out.println("commission - -> "+employeeDataBean.getEmpPayCommision());
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
			employeeDataBean.setEmpPayOTAmmount(v1.getNewValue().toString());
			System.out.println("----"+employeeDataBean.getEmpPayOTAmmount());
			System.out.println("commission -1-> "+employeeDataBean.getEmpPayCommision());
			/*if(!employeeDataBean.getEmpPayLoanAdvance().equals(""))
			{
				employeeDataBean.setEmpPayOTAmmount(v1.getNewValue().toString());
				System.out.println("-loan1 --"+employeeDataBean.getEmpPayLoanAdvance());
			}
			else if(employeeDataBean.getEmpPayLoanAdvance().equalsIgnoreCase(""))
			{
				employeeDataBean.setEmpPayLoanAdvance("0");
				System.out.println("-loan --"+employeeDataBean.getEmpPayLoanAdvance());
			}
			*/
			/*if(!employeeDataBean.getEmpPayCommision().equals(""));
			{
				System.out.println("--------commiso-----"+employeeDataBean.getEmpPayCommision());
			}*/
			/*if(employeeDataBean.getEmpPayCommision().equals(""));
			{
				employeeDataBean.setEmpPayCommision("0");
				System.out.println("----com ----"+employeeDataBean.getEmpPayCommision());
			}
			if(employeeDataBean.getEmpPayLoanAdvance().equalsIgnoreCase(""))
			{
				employeeDataBean.setEmpPayLoanAdvance("0");
				System.out.println("-loan --"+employeeDataBean.getEmpPayLoanAdvance());
			}*/
			String b=v1.getNewValue().toString();
			try
			{
			tot=new BigDecimal(employeeDataBean.getEmpBasicSalary()).add(new BigDecimal(b)).subtract(new BigDecimal(employeeDataBean.getEmpPayLoanAdvance())) .add(new BigDecimal(employeeDataBean.getEmpPayCommision()));
			System.out.println("----tot---"+employeeDataBean.getEmpBasicSalary());
			System.out.println("-----tot--"+employeeDataBean.getEmpPayLoanAdvance());
			System.out.println("-----tot-----"+employeeDataBean.getEmpPayCommision());
			}
		    catch(NumberFormatException e)
		    {
		    	e.printStackTrace();
		    }
			employeeDataBean.setEmpPayTotalSalary(""+tot);
			employeeDataBean.setEmpPayOTAmmount(b);
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
			employeeDataBean.setEmpPayLoanAdvance(v2.getNewValue().toString());
			
			/*if(!employeeDataBean.getEmpPayCommision().equals(""))
			{
				employeeDataBean.setEmpPayLoanAdvance(v2.getNewValue().toString());
				System.out.println("----com ----"+employeeDataBean.getEmpPayCommision());
			}
			else if(employeeDataBean.getEmpPayCommision().equals("")){
				employeeDataBean.setEmpPayCommision("0");
				System.out.println("----com 1----"+employeeDataBean.getEmpPayCommision());
			}
			if(!employeeDataBean.getEmpPayOTAmmount().equals(""));
			{
				System.out.println("----ot ----"+employeeDataBean.getEmpPayOTAmmount());
			}
			if(employeeDataBean.getEmpPayOTAmmount().equals(""));
			{
				employeeDataBean.setEmpPayOTAmmount("0");
				System.out.println("----ot 1----"+employeeDataBean.getEmpPayOTAmmount());
			}*/
			String c=v2.getNewValue().toString();
			
			tot1=new BigDecimal(employeeDataBean.getEmpBasicSalary()).add(new BigDecimal(employeeDataBean.getEmpPayOTAmmount())).subtract(new BigDecimal(c)) .add(new BigDecimal(employeeDataBean.getEmpPayCommision()));
			employeeDataBean.setEmpPayTotalSalary(""+tot1);
			employeeDataBean.setEmpPayLoanAdvance(c);
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
			
		
		public String payrollRemainder()
		{
			ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			LiusenController controller=(LiusenController) ctx.getBean("controller");
			controller.payrollremainder(employeeDataBean);
			return "";
			
		}
		
		public String search()
		{
			System.out.println("Inside the searchDate Method Calling");
			employeeDataBean.setPayrollList(null);
			try {
				if (validate3(true)) {
					System.out.println("After Validate inside  searchDate method");
					ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					LiusenController	controller = (LiusenController) ctx.getBean("controller");
					controller.searchpayroll(employeeDataBean);
					setFlag(true);

				}else{
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return "";
			
		}
		
	private boolean validate3(boolean flag1) {
			
			String fieldName;
			boolean valid=true;
			FacesContext fc = FacesContext.getCurrentInstance();
			System.out.println("Inside validation");
			if(employeeDataBean.getFromDate()==null){
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot("pay_from").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the From Date."));
				}
				valid = false;
			}
			if(employeeDataBean.getToDate()==null){
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot("pay_to").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the To Date."));
				}
				valid = false;
			}
			
			System.out.println("valid : "+valid);
			return valid;
		}
	
	public String payrollliabilityLoad()
	{
		System.out.println("Inside Load the payrollliabilityLoad Page");
		setFlag(false);
	employeeDataBean.setFromDate(null);
	employeeDataBean.setToDate(null);
		return "win";

			
	}
}
