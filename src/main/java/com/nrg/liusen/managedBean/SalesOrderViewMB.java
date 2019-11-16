package com.nrg.liusen.managedBean;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.util.CommonValidate;

public class SalesOrderViewMB {

	SalesOrderDataBean salesOrderDataBean=new SalesOrderDataBean();
	private boolean flag=true;
	private boolean deleteflag=false;
	private boolean editflag=false;
	
	public boolean isDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(boolean deleteflag) {
		this.deleteflag = deleteflag;
	}

	public boolean dialog=false;
	public boolean dialog1=false;
	public boolean isDialog1() {
		return dialog1;
	}

	public void setDialog1(boolean dialog1) {
		this.dialog1 = dialog1;
	}

	public boolean isDialog() {
		return dialog;
	}

	public void setDialog(boolean dialog) {
		this.dialog = dialog;
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
	 * @return the salesOrderDataBean
	 */
	public SalesOrderDataBean getSalesOrderDataBean() {
		return salesOrderDataBean;
	}

	/**
	 * @param salesOrderDataBean the salesOrderDataBean to set
	 */
	public void setSalesOrderDataBean(SalesOrderDataBean salesOrderDataBean) {
		this.salesOrderDataBean = salesOrderDataBean;
	}
	public String saleViewLoad() {

		System.out.println("Inside Load the sale View Load Page");
		salesOrderDataBean.setSaleCategory("");
		salesOrderDataBean.setSalFromDate(null);
		salesOrderDataBean.setSaleToDate(null);
		setFlag(true);
		dialog1=false;
		salesOrderDataBean.setMessage("");
		dialog=false;
		return "saleViewLoad";

	}

	/*View datatable for sales depend on date search*/
	public String searchDate()
	{		
		System.out.println("date search ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		salesOrderDataBean.setSaleCategory("");
		dialog=false;salesOrderDataBean.setMessage("");
		try 
		{			
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			if (validate(true)) 
			{
				System.out.println("After Validate inside  searchDate method");
				controller.dateSearchSales(salesOrderDataBean);	
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Marketing Staff") || rolles.equalsIgnoreCase("Finance Manager") ||  rolles.equalsIgnoreCase("Chief Designer")){
					setDeleteflag(true);
					setEditflag(true);
				}else{
					setDeleteflag(false);
					setEditflag(false);
				}
				setFlag(false);
			}
			else
			{
				setFlag(true);
			}
		} 
		catch (Exception e)
		{
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
	private boolean validate(boolean flag1)
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (salesOrderDataBean.getSalFromDate()==null)
		{
			if (flag1) 
			{
				fieldName = CommonValidate.findComponentInRoot("salViewFdate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the From Date."));
			}
			valid = false;
		} 
		if (salesOrderDataBean.getSaleToDate()==null)
		{				
			if (flag1)
			{
				fieldName = CommonValidate.findComponentInRoot("salViewTdate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the  To Date."));
			}
			valid = false;			
		}
		return valid;
	}

	/*View datatable for sales depend on category search*/
	public String searchCategory() 
	{
		System.out.println("inside category search ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		salesOrderDataBean.setSaleToDate(null);
		salesOrderDataBean.setSalFromDate(null);
		dialog=false;salesOrderDataBean.setMessage("");
		try 
		{			
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			if (validate1(true)) 
			{
				System.out.println("After Validate1 inside  searchCategory method");
				controller.categorySearchSales(salesOrderDataBean);	
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Marketing Staff") || rolles.equalsIgnoreCase("Finance Manager") ||  rolles.equalsIgnoreCase("Chief Designer")){
					setDeleteflag(true);
					setEditflag(true);
				}else if(rolles.equalsIgnoreCase("Marketing Manager")){
					setDeleteflag(true);
					setEditflag(false);
				}
				else{
					setDeleteflag(false);
					setEditflag(false);
				}
				
				setFlag(false);
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

	private boolean validate1(boolean flag2)
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (salesOrderDataBean.getSaleCategory().equalsIgnoreCase(""))
		{
			if (flag2)
			{
				fieldName = CommonValidate.findComponentInRoot("SaleViewCate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select one Category Name"));
			}
			valid = false;
		}		
		return valid;
	}
	
	/*View each sales record*/
	public String salesView()
	{
		System.out.println("view each record ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try 
		{			
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			System.out.println("order no -- > "+salesOrderDataBean.getSaleSalesOrderNo());
			controller.viewEachSalesRecord(salesOrderDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "salesViewForm";
	}
	
	/*Edit sales record*/
	public String salesEdit()
	{
		System.out.println("edit each record ");
		ApplicationContext ctx=null;
		LiusenController controller=null;dialog1=false;
		try 
		{			
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			System.out.println("order no -- > "+salesOrderDataBean.getSaleSalesOrderNo());
			controller.editSalesOrder(salesOrderDataBean);
			System.out.println("-->> list size "+salesOrderDataBean.getSalist().size());
			if (editvalidate(true))
			{
				return "salesOrderUpdateForm";
			}
			else
			{
				System.out.println("valaidate ");
			}		
			System.out.println("-->> list size -- "+salesOrderDataBean.getSalist().size());
			return "";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}		
	}
	
	/*validation for approval check*/
	public boolean editvalidate(boolean flag)
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if(salesOrderDataBean.getAppstatus().equals("Approved") || salesOrderDataBean.getStatus().equals("1"))
		{
			if (flag)
			{
				fieldName=CommonValidate.findComponentInRoot("salView").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(salesOrderDataBean.getSaleSalesOrderNo()+" is Already Approved"));
			}			
			valid=false;
		}
		return valid;
	}
	/*Edit the quantity*/
	public String edit() 
	{
		try
		{			
			System.out.println("inside quantity edit "+salesOrderDataBean.getSaleNetAmount()+"  -- "+salesOrderDataBean.getSaleCrossTotal());
			SalesOrderDataBean ss=new SalesOrderDataBean();
			ss.setSalePrice(salesOrderDataBean.getSalePrice());
			ss.setSaleSerialNo(salesOrderDataBean.getSaleSerialNo());
			ss.setQflag("1");
			ss.setQflag1("none");
			ss.setSaleQuantity("");
			ss.setSaleProductName(salesOrderDataBean.getSaleProductName());
			BigDecimal temp = BigDecimal.valueOf(0);
			if (!salesOrderDataBean.getSaleNetAmount().equalsIgnoreCase("")) 
			{
				temp = temp.add(new BigDecimal(salesOrderDataBean.getSaleCrossTotal()).subtract(new BigDecimal(salesOrderDataBean.getSaleNetAmount())));
			}
			else
			{
				temp = temp.add(new BigDecimal(salesOrderDataBean.getSaleCrossTotal()));
			}
			ss.setSaleNetAmount("");			
			salesOrderDataBean.getSalesList().set(Integer.parseInt(salesOrderDataBean.getSaleSerialNo())-1, ss);
			System.out.println("total -- >"+temp);
			salesOrderDataBean.setSaleCrossTotal("" + temp);			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			salesOrderDataBean.setMessage(e.getMessage());
		}
		return "";
	}
	
	/*Quantity Check for update*/
	public void quantityChange(ValueChangeEvent v)
	{
		System.out.println(" update quantity -- >. "+v.getNewValue());
		String no="";
		ApplicationContext ctx=null;
		LiusenController controller=null;
		BigDecimal netamount=BigDecimal.valueOf(0);
		BigDecimal total=BigDecimal.valueOf(0);
		try
		{
			no=v.getComponent().getAttributes().get("sno").toString();
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController)ctx.getBean("controller");
			salesOrderDataBean.setSaleQuantity(v.getNewValue().toString());
			salesOrderDataBean.setSaleProductName(v.getComponent().getAttributes().get("name").toString());
			if(salesOrderDataBean.getSaleCategory().equals("Product Trading"))
			{
				System.out.println("category -- >. "+salesOrderDataBean.getSaleCategory());
				controller.updatequantitycheck(salesOrderDataBean);
			}
			SalesOrderDataBean ss=new SalesOrderDataBean();
			ss.setSalePrice(v.getComponent().getAttributes().get("price").toString());
			ss.setSaleSerialNo(no);
			ss.setQflag("none");
			ss.setQflag1("1");
			ss.setSaleQuantity(v.getNewValue().toString());
			ss.setSaleProductName(v.getComponent().getAttributes().get("name").toString());
			System.out.println("price -- > "+new BigDecimal(salesOrderDataBean.getSalesList().get(Integer.parseInt(no)-1).getSalePrice())+" quantity -- >"+new BigDecimal(v.getNewValue().toString()));
			
			System.out.println("-->> price "+salesOrderDataBean.getSalesList().get(Integer.parseInt(no)-1).getSalePrice());
			System.out.println("-->> quan  "+v.getNewValue().toString());
			netamount=new BigDecimal(salesOrderDataBean.getSalesList().get(Integer.parseInt(no)-1).getSalePrice())
			.multiply(new BigDecimal(v.getNewValue().toString()));
			System.out.println("net amount -- > "+netamount);
			ss.setSaleNetAmount(""+netamount);
			salesOrderDataBean.getSalesList().set(Integer.parseInt(no)-1, ss);
			total=netamount.add(new BigDecimal(salesOrderDataBean.getSaleCrossTotal()));
			System.out.println("total -- > "+total);
			salesOrderDataBean.setSaleCrossTotal(""+total);			
		}
		catch(LiusenException e)
		{
			e.printStackTrace();		
			salesOrderDataBean.setMessage(e.getMessage());
			SalesOrderDataBean ss=new SalesOrderDataBean();
			ss.setSalePrice(v.getComponent().getAttributes().get("price").toString());
			ss.setSaleSerialNo(no);
			ss.setQflag("1");
			ss.setQflag1("none");
			ss.setSaleQuantity("");
			ss.setSaleProductName(v.getComponent().getAttributes().get("name").toString());
			ss.setSaleNetAmount("");
			salesOrderDataBean.getSalesList().set(Integer.parseInt(no)-1, ss);
		}
	}
	
	/*sales update insert*/
	public String salesUpdate()
	{
		System.out.println("update sales insert mb ");
		dialog=false;dialog1=false;
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try 
		{			
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			System.out.println("order no -- > "+salesOrderDataBean.getSaleSalesOrderNo());
			if(validate2(true))
			{
				System.out.println("inside update ");
				controller.updateSales(salesOrderDataBean);			
				dialog=true;
			}			
		}
		finally
		{
			
		}
		return "";
	}
	
//	validation for sales update
	private boolean validate2(boolean flag2)
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		for(int i=0;i<salesOrderDataBean.getSalesList().size();i++)
		{
			if (salesOrderDataBean.getSalesList().get(i).getSaleQuantity().equals(""))
			{
				if (flag2) 
				{
					fieldName = CommonValidate.findComponentInRoot("dataMeassage").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the Quantity"));
				}
				valid = false;
			} 
		}	
		return valid;
	}
	
//	go to view page from view form
	public String viewback()
	{
		salesOrderDataBean.setMessage("");
		flag=true;dialog1=false;
		salesOrderDataBean.setSaleToDate(null);
		salesOrderDataBean.setSalFromDate(null);
		salesOrderDataBean.setSaleCategory("");
		return "viewbackview";
	}
	
//	go to view page from edit form
	public String editback()
	{
		salesOrderDataBean.setMessage("");
		flag=true;dialog1=false;
		salesOrderDataBean.setSaleToDate(null);
		salesOrderDataBean.setSalFromDate(null);
		salesOrderDataBean.setSaleCategory("");
		return "editbackview";
	}
	
//	delete the sales record
	public void salesDelete()
	{
		System.out.println("delete sales order mb ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try 
		{			
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			System.out.println("order no -- > "+salesOrderDataBean.getSaleSalesOrderNo());
			controller.deleteCheck(salesOrderDataBean);	
			if(editvalidate(true))
			{
				controller.deleteSales(salesOrderDataBean);			
				System.out.println("dialog - > "+dialog1);
				dialog1=true;
				System.out.println("dialog - 1 -> "+dialog1);
			}
			else
			{
				System.out.println("validate -- >>");
			}
		}
		catch (LiusenException e) 
		{
			e.printStackTrace();
			dialog1=false;
		}
		finally
		{
			
		}	
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