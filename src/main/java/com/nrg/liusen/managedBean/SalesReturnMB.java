package com.nrg.liusen.managedBean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="salesReturnMB")
@RequestScoped
public class SalesReturnMB {

	SalesOrderDataBean salesOrderDataBean=new SalesOrderDataBean();
	private boolean flag=true;
	private boolean flag1=false;
	private List<String> returnlist=null;
	private String mes;
	public boolean isFlag1() {
		return flag1;
	}
	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public List<String> getReturnlist() {
		return returnlist;
	}
	public void setReturnlist(List<String> returnlist) {
		this.returnlist = returnlist;
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
	
	public String salesReturnVLoad() {

		System.out.println("Inside Load the salesReturnVLoad Page");
		setFlag(false);
		flag1=false;
		salesOrderDataBean.setSalFromDate(null);
		salesOrderDataBean.setSaleToDate(null);
		salesOrderDataBean.setSaleCategory("");
		salesOrderDataBean.setSaleSalesOrderNo("");
		salesOrderDataBean.setReturnqauntity("");
		salesOrderDataBean.setDr("");
		salesOrderDataBean.setRetstatus("");
		setMes("");
		return "salesReturnLoadPage";

	}
	/**
	 * This method is used for show data tables depends on Date
	 * 
	 * @return
	 */
	public String searchreturn() 
	{		
		System.out.println("Inside the searchDate Method Calling");
		setMes("");
		flag1=false;
		salesOrderDataBean.setSalist(null);
		try 
		{
			if (validate(true)) 
			{
				System.out.println("After Validate inside  searchDate method");
				ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController controller=(LiusenController) ctx.getBean("controller");
				controller.salesReturnSearch(salesOrderDataBean);
				setFlag(true);
				setMes("");				
			}
			else
			{
				setFlag(false);
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
	private boolean validate(boolean flag1) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (salesOrderDataBean.getSalFromDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("saleRtrnFrom").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the From Date."));
			}
			valid = false;
		}  if (salesOrderDataBean.getSaleToDate()==null) {

			
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"saleRtrnTo").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Choose the  To Date."));
				}
				valid = false;
			
		}
		return valid;
	}
	
	public String returnorder()
	{		
		System.out.println("return order form");
		setMes("");
		flag1=false;
		try 
		{
			ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			LiusenController controller=(LiusenController) ctx.getBean("controller");
			controller.salesReturn(salesOrderDataBean);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "salesReturnForm";
	}
	
	public void categoryChange(ValueChangeEvent v)
	{
		System.out.println("category change - > "+v.getNewValue());		
		LiusenController controller=null;		
		ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller = (LiusenController) ctx.getBean("controller");
		salesOrderDataBean.setSaleCategory(v.getNewValue().toString());
		returnlist=controller.returnSales(salesOrderDataBean);
		System.out.println("return -- > "+returnlist.size());
	}
	
	public void check(ValueChangeEvent v)
	{
		System.out.println("tick -- > "+v.getNewValue());
		setMes("");
		LiusenController controller=null;		
		ApplicationContext ctx=null;
		int i=0;
		i=Integer.parseInt(""+v.getComponent().getAttributes().get("no"))-1;		
		try
		{
			salesOrderDataBean.setSaleProductName(""+v.getComponent().getAttributes().get("product"));
			ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");	
			controller.returnCheck(salesOrderDataBean);
			salesOrderDataBean.setRetstatus("");
			if(v.getNewValue().equals(true))
			{
				System.out.println("inside true- -> ");
				SalesOrderDataBean list=new SalesOrderDataBean();
				list.setText("true");
				list.setSaleProductName(""+v.getComponent().getAttributes().get("product"));
				list.setSaleQuantity(""+v.getComponent().getAttributes().get("quan"));
				list.setSaleSerialNo(""+v.getComponent().getAttributes().get("no"));
				list.setSalePrice(""+v.getComponent().getAttributes().get("price"));
				salesOrderDataBean.getSalesList().set(i, list);
			}
			else if(!v.getNewValue().equals(true))
			{
				System.out.println("inside false- -> ");
				SalesOrderDataBean list=new SalesOrderDataBean();
				list.setText("false");
				list.setSaleSerialNo(""+v.getComponent().getAttributes().get("no"));
				list.setSaleProductName(""+v.getComponent().getAttributes().get("product"));
				list.setSaleQuantity(""+v.getComponent().getAttributes().get("quan"));
				list.setSalePrice(""+v.getComponent().getAttributes().get("price"));
				salesOrderDataBean.getSalesList().set(i, list);
			}
		}
		catch(LiusenException e)
		{
			System.out.println("catch -- >");
			SalesOrderDataBean list=new SalesOrderDataBean();
			list.setText("false");
			list.setTick("false");
			list.setSaleProductName(""+v.getComponent().getAttributes().get("product"));
			list.setSaleQuantity(""+v.getComponent().getAttributes().get("quan"));
			list.setSaleSerialNo(""+v.getComponent().getAttributes().get("no"));
			list.setSalePrice(""+v.getComponent().getAttributes().get("price"));
			salesOrderDataBean.getSalesList().set(i, list);
			e.printStackTrace();
			setMes(e.getMessage());			
		}		
	}
	
	public void quantityCheck(ValueChangeEvent u)
	{
		System.out.println("quantity - nr - > "+u.getNewValue());
		setMes("");
		try
		{
			salesOrderDataBean.setReturnqauntity(u.getNewValue().toString());
			salesOrderDataBean.setSaleProductName(""+u.getComponent().getAttributes().get("product"));
			salesOrderDataBean.setDr(""+u.getComponent().getAttributes().get("dr"));
			if(salesOrderDataBean.getDr().equals("") || salesOrderDataBean.getDr().equals("null") || salesOrderDataBean.getDr().equals(null))
			{
				salesOrderDataBean.setDr("0");
			}
			System.out.println("dr mb - > "+salesOrderDataBean.getDr());
			LiusenController controller=null;		
			ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");	
			controller.quantityCheckReturn(salesOrderDataBean);
		}
		catch(LiusenException e)
		{
			e.printStackTrace();
			setMes(e.getMessage());
		}
	}
	
	public void quantityCheck1(ValueChangeEvent uv)
	{
		System.out.println("quantity -1 dr  - > "+uv.getNewValue());
		setMes("");
		try
		{
			salesOrderDataBean.setDr(uv.getNewValue().toString());
			salesOrderDataBean.setReturnqauntity(""+uv.getComponent().getAttributes().get("nr"));
			salesOrderDataBean.setSaleProductName(""+uv.getComponent().getAttributes().get("product"));
			if(salesOrderDataBean.getReturnqauntity().equals("") || salesOrderDataBean.getReturnqauntity().equals("null") || salesOrderDataBean.getReturnqauntity().equals(null))
			{
				salesOrderDataBean.setReturnqauntity("0");
			}
			System.out.println("nr mb - > "+salesOrderDataBean.getReturnqauntity());
			LiusenController controller=null;		
			ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");	
			controller.quantityCheckReturn(salesOrderDataBean);
		}
		catch(LiusenException e)
		{
			e.printStackTrace();
			setMes(e.getMessage());
		}
	}
	
	public String returnSubmit()
	{
		System.out.println("inside return submit mb -- >");
		LiusenController controller=null;
		ApplicationContext ctx=null;
		flag1=false;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			int c=0;
			if(salesOrderDataBean.getSalesList().size()>0)
			{				
				for (int i = 0; i < salesOrderDataBean.getSalesList().size(); i++) 
				{
					if(salesOrderDataBean.getSalesList().get(i).getTick().equals("false"))
					{
						c++;
					}
					else
					{
						System.out.println("c -- 2- "+c);
					}
				}
				System.out.println("c-- > "+c);
				if(c==salesOrderDataBean.getSalesList().size())
				{
					throw new Exception("Please Return Atleast one Product");
				}
				else
				{
					for (int i = 0; i < salesOrderDataBean.getSalesList().size(); i++) 
					{
						int c1=0;int c2=0;
						System.out.println("quan -- > "+salesOrderDataBean.getSalesList().get(i).getReturnqauntity()+" dr - > "+salesOrderDataBean.getSalesList().get(i).getDr());
						if(salesOrderDataBean.getSalesList().get(i).getTick().equals("true"))
						{
							if(salesOrderDataBean.getSalesList().get(i).getText().equals("true"))
							{
								if(salesOrderDataBean.getSalesList().get(i).getReturnqauntity().equals(""))
								{
									c1++;								
								}
								if(salesOrderDataBean.getSalesList().get(i).getDr().equals(""))
								{
									c2++;
								}
								if(c1>0 && c2>0)
								{
									throw new Exception("Please Enter the Return Quantity");
								}
								if(salesOrderDataBean.getSalesList().get(i).getReturnqauntity().equals(""))
								{
									salesOrderDataBean.getSalesList().get(i).setReturnqauntity("0");
								}
								else if(salesOrderDataBean.getSalesList().get(i).getDr().equals(""))
								{
									salesOrderDataBean.getSalesList().get(i).setDr("0");
								}
							}													
							else
							{
								salesOrderDataBean.getSalesList().get(i).setReturnqauntity("0");
								salesOrderDataBean.getSalesList().get(i).setDr("0");
							}
						}
					}
					controller.returnSubmit(salesOrderDataBean);
					flag1=true;
				}				
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			setMes(e.getMessage());
		}
		return "";
	}
}
