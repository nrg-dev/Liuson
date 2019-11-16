package com.nrg.liusen.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nrg.liusen.domain.EmployeeDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.shared.Designation;
import com.nrg.liusen.shared.Employee;
import com.nrg.liusen.shared.Payroll;
import com.nrg.liusen.shared.PayrollApproval;
import com.nrg.liusen.shared.Qualification;

@Repository
@Singleton
public class EmployeeDaoImpl implements EmployeeDao {

	@PersistenceContext(unitName="liusen-pu")
	private EntityManager entitymanager;
	
	@Override
	public List<String> getQualification() {
		Query q=null;
		 List<String> res=null;
	try{
		 q= entitymanager.createQuery("select qualification_Name from Qualification where qualification_Status='Active'");
		 res=(List<String>)q.getResultList();
		 System.out.println("Size"+res.size());
	}catch(Exception e){
		e.printStackTrace();
	}
		return res;
	}

	@Override
	public List<String> getDesignation() {
		Query q=null;
		List<String> result=null;
		try{
			q=entitymanager.createQuery("select designation_Name from  Designation where designation_Status='Active' ");
			result=(List<String>)q.getResultList();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	@Transactional(value="transactionManager")
	public String inserEmployee(EmployeeDataBean employeeDataBean) {
		System.out.println("Calling inserEmployee method ..."+employeeDataBean.getEmpEmployeeName());
		String status="Fail";
		Query insertEmp=null;
		
		int qual_ID=0;
		int desg_ID=0;
		Date date=new Date();
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
		Timestamp timestamp=new Timestamp(date.getTime());
		try{
			if(employeeDataBean.getEmpEmployeeName() != null){
				desg_ID =getDesignationList(employeeDataBean.getEmpDesignation());
				qual_ID=getQualificationList(employeeDataBean.getEmpQualification());
				if(desg_ID > 0 && qual_ID >0){
			
			insertEmp=entitymanager.createQuery("from Employee where (employee_details_ID = ? and status=?)");
			insertEmp.setParameter(1, employeeDataBean.getEmpEmployeeId());
			insertEmp.setParameter(2, "Active");
			List<Employee> empResult = (List<Employee>)insertEmp.getResultList();
			System.out.println("Size"+empResult.size());
			if(empResult.size() > 0){
				status="Exsist";
			}else{
				Employee employee = new Employee();
				employee.setDesignation(entitymanager.find(Designation.class, desg_ID));
				employee.setQualification(entitymanager.find(Qualification.class, qual_ID));
				employee.setEmployee_details_ID(employeeDataBean.getEmpEmployeeId());
				employee.setEmployeeName(employeeDataBean.getEmpEmployeeName());
				employee.setEmployeeDob(employeeDataBean.getEmpDob());
				employee.setEmployeeAddress(employeeDataBean.getEmpAddress());
				employee.setEmployeeBasicSalary(employeeDataBean.getEmpBasicSalary());
				employee.setEmployeeEmail(employeeDataBean.getEmpMaildId());
				employee.setEmployeeGender(employeeDataBean.getEmpGender());
				employee.setEmpPhone(employeeDataBean.getEmpPhoneNo());
				employee.setEmployeeEntryDate(employeeDataBean.getEmpEntryDate());
				employee.setEmployeeJointDate(employeeDataBean.getEmpJoinDate());
				employee.setEmployeeDescription(employeeDataBean.getEmpDescription());
				employee.setEmpDate(date);		
				employee.setEmpTime(timestamp);
				employee.setEmployeeLoginStatus(login);
				employee.setStatus("Active");
				employee.setEmployeeEditStatus("Not Edited");
				entitymanager.persist(employee);
				status="Success";
			}
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}

		private int getQualificationList(String empQualification) {
			int res1=0;
				Query getQual=null;
				System.out.println("Name"+empQualification);
				try{
					getQual=entitymanager.createQuery("from Qualification where (qualification_Name= ? and qualification_Status='Active')");
					getQual.setParameter(1, empQualification);
					List<Qualification> qualList=(List<Qualification>)getQual.getResultList();
					System.out.println("Size1"+qualList.size());
					if(qualList.size() > 0){
						res1=qualList.get(0).getQualificationID();
						System.out.println("res1"+res1);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				return res1;
	}

	private int getDesignationList(String name) {
		int res=0;
		Query getDesg=null;
		System.out.println("Name"+name);
		try{
			if(name != null){
			getDesg=entitymanager.createQuery("from Designation where (designation_Name=? and  designation_Status='Active')");
			getDesg.setParameter(1, name);
			List<Designation> DesgResult=(List<Designation>)getDesg.getResultList();
			if(DesgResult.size() > 0){
				res=DesgResult.get(0).getDesignationID();
				System.out.println("ID"+res);
			}
			}
		}catch(Exception e){
			e.printStackTrace();
			}
		return res;
	}

	@Override
	public List<String> getEmployeeID() {
		Query q=null;
		List<String> result=null;
		try{
			System.out.println("Inside getEmployeeID method Calling");
			q=entitymanager.createQuery("select employee_details_ID from Employee where status=?");
			q.setParameter(1, "Active");
			result=(List<String>)q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Employee> getemp(EmployeeDataBean employeeDataBean) {
		// TODO Auto-generated method stub
		System.out.println("Inside employee Info in DAO"+employeeDataBean.getEmpEmployeeId());
          List<Employee>emplist=null;
          if(employeeDataBean.getEmpEmployeeId()!=null)
          {
        	  try
        	  {
        		  Query q=null;
        		  q=entitymanager.createQuery("from Employee where (employee_details_ID=? and status=?)");
        		  q.setParameter(1, employeeDataBean.getEmpEmployeeId());
        		  q.setParameter(2, "Active");
        		  emplist=q.getResultList();
        		  System.out.println("emp size"+emplist.size());
        	  }
        	  catch(Exception e)
        	  {
        		  e.printStackTrace();
        	  }
        	  
        	  }
          
          return  emplist;
	}

	@Override
	public List<EmployeeDataBean> searchname(EmployeeDataBean employeeDataBean) {
		// TODO Auto-generated method stub
		List<EmployeeDataBean>emplist1=null;
		System.out.println("Inside searchempppppppName in DAO method calling");
		System.out.println("emp Name"+employeeDataBean.getEmpEmployeeName());
		if(employeeDataBean.getEmpEmployeeName() !=null)
		{
			try
			{
	
			Query q=null;
			emplist1=new ArrayList<EmployeeDataBean>();
			q=entitymanager.createQuery("from Employee where (employeeName Like ?  and status=?)"); //Like Search
			q.setParameter(1, "%"+employeeDataBean.getEmpEmployeeName()+"%");
			q.setParameter(2, "Active");
			List<Employee> emplist2=(List<Employee>)q.getResultList();
			System.out.println("Size"+emplist2.size());
			if(emplist2.size()>0)
			{
				System.out.println("emplist2 size");
				for( Employee em:emplist2)
				{
					EmployeeDataBean empobj=new EmployeeDataBean();
					empobj.setEmpEmployeeName(em.getEmployeeName());
					empobj.setEmpEmployeeId(em.getEmployee_details_ID());
					empobj.setEmpDesignation(em.getDesignation().getDesignation_Name());
					empobj.setEmpPhoneNo(em.getEmpPhone());
					empobj.setEmpEntryDate(em.getEmployeeEntryDate());
					emplist1.add(empobj);
					
				}
					
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
		return emplist1;
	}

	@Override
	public List<Employee> getempedit(String empEmployeeName,EmployeeDataBean employeeDataBean) {
		// TODO Auto-generated method stub
		try
		{
			Query q=null;
			q=entitymanager.createQuery("from Employee where(employee_details_ID=? and status=?)");
			q.setParameter(1,empEmployeeName);
			q.setParameter(2, "Active");
			List<Employee>empid1=(List<Employee>)q.getResultList();
			if(empid1.size()>0)
			{
			
			employeeDataBean.setEmpEmployeeId(empid1.get(0).getEmployee_details_ID());
			employeeDataBean.setEmpAddress(empid1.get(0).getEmployeeAddress());
			employeeDataBean.setEmpBasicSalary(empid1.get(0).getEmployeeBasicSalary());
			employeeDataBean.setEmpDescription(empid1.get(0).getEmployeeDescription());
			employeeDataBean.setEmpDob(empid1.get(0).getEmployeeDob());
			employeeDataBean.setEmpEmployeeName(empid1.get(0).getEmployeeName());
		    employeeDataBean.setEmpEntryDate(empid1.get(0).getEmployeeEntryDate());
			employeeDataBean.setEmpGender(empid1.get(0).getEmployeeGender());
            employeeDataBean.setEmpMaildId(empid1.get(0).getEmployeeEmail());
            employeeDataBean.setEmpDesignation(empid1.get(0).getDesignation().getDesignation_Name());
            employeeDataBean.setEmpQualification(empid1.get(0).getQualification().getQualification_Name());
            employeeDataBean.setEmpPhoneNo(empid1.get(0).getEmpPhone());
            employeeDataBean.setEmpJoinDate(empid1.get(0).getEmployeeJointDate());
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
		Date date=new Date();
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
					employee.setEmployeeEditStatus("Edited");
					employee.setEmployeeEditLogin(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
					employee.setEmployeeEditDate(date);
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
				if(employeeDataBean.getEmpEmployeeId()!=null)
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

	
		public List<String> empid()
		{
			System.out.println("empppppppiddddddd calling");
			Query q=null;
			q=entitymanager.createQuery("select employee_details_ID from Employee where status=?");		
			q.setParameter(1, "Active");
			List<String>emplist=(List<String>)q.getResultList();
			System.out.println("empppppppppp "+emplist.size());
			return emplist;
		}

		@Override
		public List<EmployeeDataBean> searchid(EmployeeDataBean employeeDataBean) {
			// TODO Auto-generated method stub
			List<EmployeeDataBean>empidlist=null;
			System.out.println("Inside searchemppiddddddddddddd in DAO method calling");
             System.out.println("emp"+employeeDataBean.getEmpEmployeeId());
             if(employeeDataBean.getEmpEmployeeName()!=null)
             {
            	 Query q=null;
               empidlist=new ArrayList<EmployeeDataBean>();
               q=entitymanager.createQuery(" from Employee where (employee_details_ID=?   and  status=?)");
               q.setParameter(1, employeeDataBean.getEmpEmployeeId());
               q.setParameter(2, "Active");
               List<Employee>emplist=q.getResultList();
               System.out.println("emp list size"+emplist.size());
               if(emplist.size()>0)
               {
               for(Employee emp:emplist)
               {
            	   EmployeeDataBean empdata=new EmployeeDataBean();
            	   empdata.setEmpEmployeeName(emp.getEmployeeName());
            	   empdata.setEmpEmployeeId(emp.getEmployee_details_ID());
            	   empdata.setEmpDesignation(emp.getDesignation().getDesignation_Name());
            	   empdata.setEmpMaildId(emp.getEmployeeEmail());
            	   empdata.setEmpPhoneNo(emp.getEmpPhone());
            	   empdata.setEmpEntryDate(emp.getEmployeeEntryDate());
            	   empidlist.add(empdata);
               }
               }
               
            	 
             }

			return empidlist;
		}
		
		@Override
		
		public String payroll(EmployeeDataBean employeeDataBean)
				throws LiusenException {
			
			Query q1=null;
			System.out.println("----eeeeaa---inside dao-----");
			try
			{
				
				List<Payroll>payroll_month=null;
				
				q1=entitymanager.createQuery("from Employee where employee_details_ID=?");
				q1.setParameter(1,employeeDataBean.getEmpEmployeeId());
				System.out.println("--------emp------"+employeeDataBean.getEmpEmployeeId());
				List<Employee> res=(List<Employee>)q1.getResultList();
				if(res.size()>0)
				{
					System.out.println("---emp==="+res.size());
					
					employeeDataBean.setEmpEmployeeId(res.get(0).getEmployee_details_ID());
					System.out.println("------employee-----"+employeeDataBean.getEmpEmployeeId());
					employeeDataBean.setEmpEmployeeName(res.get(0).getEmployeeName());
					employeeDataBean.setEmpDesignation(res.get(0).getDesignation().getDesignation_Name());
					employeeDataBean.setEmpBasicSalary(res.get(0).getEmployeeBasicSalary());
					employeeDataBean.setEmpPayTodayDate(res.get(0).getEmpDate());
					
				}
			
				q1=entitymanager.createQuery("from Payroll where payrollMonth=? and employee_ID=? and status='Active'");
				q1.setParameter(1,employeeDataBean.getEmpPayMonth());
				q1.setParameter(2,res.get(0).getEmployee_ID());
				System.out.println("-----pay------"+employeeDataBean.getEmpPayMonth()+"employee id"+employeeDataBean.getEmpEmployeeId());
				List<Payroll> result=(List<Payroll>)q1.getResultList();
				if(result.size()>0)
				{
					System.out.println("Inside if=====");
					employeeDataBean.setValidmonth("Exist");
					employeeDataBean.setEmpPayMonth(result.get(0).getPayrollMonth());				
					System.out.println("----pp----"+result.size());
					//throw new LiusenException("payroll is already generated");
				}
			}
			finally
			{
				
			}
				
			
			return null;	
		
			}
		
		@Override
		public String payroll1(EmployeeDataBean employeeDataBean) {
			Query q1=null;
			System.out.println("----eeeeaa---inside dao-----");
			try
			{
				
				List<Payroll>payroll_month=null;
				
				q1=entitymanager.createQuery("from Employee where employeeName=? ");
				q1.setParameter(1,employeeDataBean.getEmpEmployeeName());
				
				System.out.println("--------emp------"+employeeDataBean.getEmpEmployeeName());
				List<Employee> res=(List<Employee>)q1.getResultList();
				if(res.size()>0)
				{
					System.out.println("---emp==="+res.size());
					
					employeeDataBean.setEmpEmployeeId(res.get(0).getEmployee_details_ID());
					System.out.println("------employee-----"+employeeDataBean.getEmpEmployeeId());
					employeeDataBean.setEmpEmployeeName(res.get(0).getEmployeeName());
					employeeDataBean.setEmpDesignation(res.get(0).getDesignation().getDesignation_Name());
					employeeDataBean.setEmpBasicSalary(res.get(0).getEmployeeBasicSalary());
					employeeDataBean.setEmpPayTodayDate(res.get(0).getEmpDate());
					
				}
			
				q1=entitymanager.createQuery("from Payroll where payrollMonth=? and employee_ID=? and status=?");
				q1.setParameter(1,employeeDataBean.getEmpPayMonth());
				q1.setParameter(2,res.get(0).getEmployee_ID());
				q1.setParameter(3, "Active");
				System.out.println("-----pay------"+employeeDataBean.getEmpPayMonth()+"employee id"+res.get(0).getEmployee_ID());
				List<Payroll> result=(List<Payroll>)q1.getResultList();
				System.out.println("size of result ::: "+result.size());
				if(result.size()>0)
				{
					System.out.println("Inside if=====");
					employeeDataBean.setValidmonth("Exist");
					employeeDataBean.setEmpPayMonth(result.get(0).getPayrollMonth());				
					System.out.println("----pp----"+result.size());
					//throw new LiusenException("payroll is already generated");
				}
				else
				{
					employeeDataBean.setValidmonth("Not Exist");
					System.out.println("payroll is not generated for this month");
				}
			}
			/*finally
			{
				
			}*/
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return null;	
			
		}

		

		@Override
		@Transactional(value="transactionManager")
		public String insertpayroll(EmployeeDataBean employeeDataBean) {
			Query q2=null;
			String status="failed";
			
			try
			{
				
				Date date=new Date();
				String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
				Timestamp timestamp=new Timestamp(date.getTime());
				int employee_ID=0;
				System.out.println("----inside dao-------");
				q2=entitymanager.createQuery("from Employee where employee_details_ID=?");
				q2.setParameter(1, employeeDataBean.getEmpEmployeeId());
				List<Employee> result=(List<Employee>)q2.getResultList();
				

				if(result.size()>0){
					System.out.println("----size---");
					employee_ID=result.get(0).getEmployee_ID();
				     
			          Payroll pay=new Payroll();
			          pay.setEmployee(entitymanager.find(Employee.class,employee_ID));
				      pay.setLoanAdvance(employeeDataBean.getEmpPayLoanAdvance());
				      System.out.println("--pay----"+pay.getLoanAdvance());
				      pay.setOtAmoount(employeeDataBean.getEmpPayOTAmmount());
				      System.out.println("--pay----"+pay.getOtAmoount());
					  pay.setCommisionAmount(employeeDataBean.getEmpPayCommision());
					  System.out.println("--pay----"+pay.getCommisionAmount());
					  pay.setWorkingDays(Integer.parseInt(employeeDataBean.getEmpPayWorkDays()));
					  System.out.println("--pay----"+pay.getWorkingDays());
					  pay.setTotalSalary(employeeDataBean.getEmpPayTotalSalary());
					  System.out.println("--pay----"+pay.getTotalSalary());
				      pay.setPayDate(employeeDataBean.getEmpPayTodayDate());
				      System.out.println("--pay----"+pay.getPayDate());
				      pay.setPayTime(timestamp);
				      pay.setPayLoginStatus("payLoginStatus");
				      
				     pay.setPayrollMonth(employeeDataBean.getEmpPayMonth());
				     System.out.println("-----pay-11111-----"+pay.getPayrollMonth());
				     
				      pay.setPayLoginStatus(login);
				      pay.setApprovalStatus("waiting");
				      pay.setStatus("Active");
				      pay.setEditStatus("Not Edited");
	                  entitymanager.persist(pay);
	                 
	                  status="Success";
	                  System.out.println("----suc------"+status);
				    Query v=null;
					v=entitymanager.createQuery("from Payroll");
					List<Payroll> pay1=(List<Payroll>)v.getResultList();
					int payid=0;
					if(pay1.size()>0)
					{
						payid=pay1.get(pay1.size()-1).getPayroll_ID();
						System.out.println("payroll id -- > "+payid);
						
						PayrollApproval appr=new PayrollApproval();
						appr.setPayroll(entitymanager.find(Payroll.class, payid));
						appr.setAppDate(date);
						appr.setApproved_status_Director("pending");
						appr.setApproved_status_FM("pending");
						appr.setAppTime(timestamp);
						appr.setLogin_status_Director(login);
						appr.setLoginStatus(login);
						appr.setLogin_status_FM(login);
						appr.setReject_Director_status("pending");
						appr.setReject_FM_status("pending");
						appr.setRejectReason("pending");
						appr.setRejectStatus("pending");
						appr.setStatus("pending");
						appr.setStatus2("pending");
						entitymanager.persist(appr);
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
		public List<String> getEmployeeName() {
			Query q=null;
			List<String> res=null;
			try
			{
				System.out.println("Inside getEmployeeName method Calling");
				q=entitymanager.createQuery("select employeeName from Employee where status=?");
				q.setParameter(1, "Active");
				res=(List<String>) q.getResultList();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return res;
		}

		@Override
		public List<EmployeeDataBean> getPayrollList(EmployeeDataBean employeeDataBean) 
		{
			List<EmployeeDataBean> payrollList= new ArrayList<EmployeeDataBean>();
			Query payList=null;
			Query q=null;
			try
			{
				System.out.println("Inside getPayrollList method is calling");
				System.out.println("Employee name"+employeeDataBean.getEmpEmployeeName());
				if(employeeDataBean.getEmpEmployeeName() != null)
				{
					System.out.println("iiiinnnnnn");
					payList=entitymanager.createQuery("from Employee where employeeName=? and status=?");
					payList.setParameter(1, employeeDataBean.getEmpEmployeeName());
					payList.setParameter(2, "Active");
					List<Employee> res=(List<Employee>)payList.getResultList();
					System.out.println("Checking Size "+res.size());
					if(res.size()>0)
					{
						for (int i = 0; i < res.size(); i++) 
						{
							q=entitymanager.createQuery("from Payroll where employee_ID=? and status=?");
							q.setParameter(1, res.get(i).getEmployee_ID());
							q.setParameter(2, "Active");
							List<Payroll> result=(List<Payroll>)q.getResultList();
							System.out.println("Checking Size "+result.size());							
							if(result.size()>0)
							{
								
								for(Payroll payroll : result)
								{
									EmployeeDataBean emp=new EmployeeDataBean();
									emp.setEmpEmployeeName(payroll.getEmployee().getEmployeeName());
									emp.setEmpEmployeeId(payroll.getEmployee().getEmployee_details_ID());
									emp.setEmpPayTotalSalary(payroll.getTotalSalary());
									emp.setEmpDesignation(payroll.getEmployee().getDesignation().getDesignation_Name());
									emp.setEmpPayMonth(payroll.getPayrollMonth());
									payrollList.add(emp);
								}
							}
						}
					}
				
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return payrollList;
		}

		@Override
		public List<EmployeeDataBean> getPayrollList1(EmployeeDataBean employeeDataBean)
		{
			List<EmployeeDataBean> payrollList1=null;
			Query payList=null;
			Query q=null;
			try
			{
				System.out.println("Inside getPayrollList method is calling");
				System.out.println("Employee name"+employeeDataBean.getEmpEmployeeName());
				if(employeeDataBean.getEmpEmployeeName() != null)
				{
					System.out.println("iiiinnnnnn");
				payList=entitymanager.createQuery("from Employee where employee_details_ID=? and status=?");
				payList.setParameter(1, employeeDataBean.getEmpEmployeeId());
				payList.setParameter(2, "Active");
				List<Employee> res=(List<Employee>)payList.getResultList();
				System.out.println("Checking Size "+res.size());
				
				q=entitymanager.createQuery("from Payroll where employee_ID=? and status=?");
				q.setParameter(1, res.get(0).getEmployee_ID());
				q.setParameter(2, "Active");
				List<Payroll> result=(List<Payroll>)q.getResultList();
				System.out.println("Checking Size "+result.size());
				payrollList1= new ArrayList<EmployeeDataBean>();
				if(result.size()>0)
				{
					
					for(Payroll payroll : result)
					{
						EmployeeDataBean emp=new EmployeeDataBean();
						emp.setEmpEmployeeName(payroll.getEmployee().getEmployeeName());
						emp.setEmpEmployeeId(payroll.getEmployee().getEmployee_details_ID());
						emp.setEmpPayTotalSalary(payroll.getTotalSalary());
						emp.setEmpDesignation(payroll.getEmployee().getDesignation().getDesignation_Name());
						emp.setEmpPayMonth(payroll.getPayrollMonth());
						payrollList1.add(emp);
					}
				}
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return payrollList1;
		}

		@Override
		public List<Payroll> getPayrollListSingle(EmployeeDataBean employeeDataBean)
		{
			List<Payroll> paylist=null;
			List<Employee> emplist=null;
			try
			{
				System.out.println("Inside getPayrollListSingle");
				if(employeeDataBean.getEmpEmployeeId() != null)
				{
					System.out.println("id :::: "+employeeDataBean.getEmpEmployeeId());
					Query q=entitymanager.createQuery("from Employee where employee_details_ID=? and status=?");
					q.setParameter(1, employeeDataBean.getEmpEmployeeId());
					q.setParameter(2, "Active");
					emplist=(List<Employee>)q.getResultList();
					System.out.println("size of emplist : "+emplist.size());
					System.out.println("iiiddd"+emplist.get(0).getEmployee_ID());
					Query q1=entitymanager.createQuery("from Payroll where employee_ID=? and payrollMonth=? and status=?");
					q1.setParameter(1, emplist.get(0).getEmployee_ID());
					q1.setParameter(2, employeeDataBean.getEmpPayMonth());
					q1.setParameter(3, "Active");
					paylist=(List<Payroll>)q1.getResultList();
					System.out.println("paylist size"+paylist.size());
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return paylist;
		}

		@Override
		@Transactional(value="transactionManager")
		public String payrollDelete(EmployeeDataBean employeeDataBean)
		{
			System.out.println("Inside payrolltDelete method calling in DAO"+employeeDataBean.getEmpEmployeeId());
			String status="Fail";
			int payID=0;
			Date date=new Date();
			String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
			try
			{
			if(employeeDataBean.getEmpEmployeeId() != null)	
			{
				Query payList=entitymanager.createQuery("from Employee where employee_details_ID=? and status=?");
				payList.setParameter(1, employeeDataBean.getEmpEmployeeId());
				payList.setParameter(2, "Active");
				List<Employee> res=(List<Employee>)payList.getResultList();
				System.out.println("Checking Size "+res.size());
				
				Query q=entitymanager.createQuery("from Payroll where employee_ID=? and payrollMonth=? and status=?");
				q.setParameter(1, res.get(0).getEmployee_ID());
				q.setParameter(2, employeeDataBean.getEmpPayMonth());
				q.setParameter(3, "Active");
				List<Payroll> result=(List<Payroll>)q.getResultList();	
				System.out.println("result size"+result.size());
				if(result.size() > 0)
				{
					payID=result.get(0).getPayroll_ID();
					Payroll payroll=entitymanager.find(Payroll.class, payID);
					payroll.setEditStatus("Removed");
					payroll.setStatus("De-Activate");
					payroll.setEditDate(date);
					payroll.setEditLoginStatus(login);
					entitymanager.merge(payroll);
					status="Success";
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
		public List<Payroll> getPayrollInfo(EmployeeDataBean employeeDataBean)
		{
			List<Payroll> paylist=null;
			List<Employee> emplist=null;
			try
			{
				System.out.println("Inside getPayrollListSingle");
				if(employeeDataBean.getEmpEmployeeName() != null)
				{
					System.out.println("id :::: "+employeeDataBean.getEmpEmployeeName());
					Query q=entitymanager.createQuery("from Employee where employee_details_ID=? and status=?");
					q.setParameter(1, employeeDataBean.getEmpEmployeeId());
					q.setParameter(2, "Active");
					emplist=(List<Employee>)q.getResultList();
					System.out.println("size of emplist : "+emplist.size());
					System.out.println("iiiddd"+emplist.get(0).getEmployee_ID());
					Query q1=entitymanager.createQuery("from Payroll where employee_ID=? and payrollMonth=? and status=?");
					q1.setParameter(1, emplist.get(0).getEmployee_ID());
					q1.setParameter(2, employeeDataBean.getEmpPayMonth());
					q1.setParameter(3, "Active");
					paylist=(List<Payroll>)q1.getResultList();
					System.out.println("paylist size"+paylist.size());
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return paylist;
		}

		@Override
		@Transactional(value="transactionManager")
		public String editProduct(EmployeeDataBean employeeDataBean)
		{
			int payid=0;
			Date date=new Date();
			String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
			if(employeeDataBean.getEmpEmployeeId() != null)	
			{
				System.out.println("emp id "+employeeDataBean.getEmpEmployeeId());
				Query payList=entitymanager.createQuery("from Employee where employee_details_ID=? and status=?");
				payList.setParameter(1, employeeDataBean.getEmpEmployeeId());
				payList.setParameter(2, "Active");
				List<Employee> res=(List<Employee>)payList.getResultList();
				System.out.println("Checking Size "+res.size());
				
				Query q=entitymanager.createQuery("from Payroll where employee_ID=? and payrollMonth=? and status=?");
				q.setParameter(1, res.get(0).getEmployee_ID());
				q.setParameter(2, employeeDataBean.getEmpPayMonth());
				q.setParameter(3, "Active");
				List<Payroll> result=(List<Payroll>)q.getResultList();	
				System.out.println("result size"+result.size());
				
				if(result.size() >0)
				{
				payid=result.get(0).getPayroll_ID();
				Payroll pay=entitymanager.find(Payroll.class, payid);
				  pay.setLoanAdvance(employeeDataBean.getEmpPayLoanAdvance());
			      pay.setOtAmoount(employeeDataBean.getEmpPayOTAmmount());
			      pay.setCommisionAmount(employeeDataBean.getEmpPayCommision());
				  pay.setWorkingDays(Integer.parseInt(employeeDataBean.getEmpPayWorkDays()));
				  pay.setTotalSalary(employeeDataBean.getEmpPayTotalSalary());
			      pay.setPayDate(employeeDataBean.getEmpPayTodayDate());
			      pay.setEditLoginStatus(login);
			      pay.setEditDate(date);
			      pay.setEditStatus("Edited");
			      entitymanager.merge(pay);
			     
				}
			}
			
			return null;
		}
		
		public String payrollremainder(EmployeeDataBean employeeDataBean)
		{
			
			return "";
		}
}
