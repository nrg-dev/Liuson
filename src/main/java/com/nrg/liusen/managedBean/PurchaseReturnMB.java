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
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="purchaseReturnMB")
@RequestScoped
public class PurchaseReturnMB {

	PurchaseOrederDataBean purchaseOrederDataBean=new PurchaseOrederDataBean();
	private boolean flag=false;
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

	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public PurchaseOrederDataBean getPurchaseOrederDataBean() {
		return purchaseOrederDataBean;
	}
	public void setPurchaseOrederDataBean(
			PurchaseOrederDataBean purchaseOrederDataBean) {
		this.purchaseOrederDataBean = purchaseOrederDataBean;
	}
	public String purchaseReturnVLoad() 
	{
		System.out.println("Inside Load the salesReturnVLoad Page");
		setFlag(false);
		flag1=false;		
		setMes("");
		purchaseOrederDataBean.setPurFromDate(null);
		purchaseOrederDataBean.setPurToDate(null);
		purchaseOrederDataBean.setPurCategory("");
		return "purchaseOrderReturn";
	}
	
	public String searchreturn() 
	{		
		System.out.println("Inside the searchDate Method Calling");
		setMes("");
		flag1=false;
		purchaseOrederDataBean.setPurchaseList(null);
		try 
		{
			if (validate(true)) 
			{
				System.out.println("After Validate inside  searchDate method");
				ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController controller=(LiusenController) ctx.getBean("controller");
				controller.purchaseReturn(purchaseOrederDataBean);
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
	
	private boolean validate(boolean flag1) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (purchaseOrederDataBean.getPurFromDate()==null) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("fdate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the From Date."));
			}
			valid = false;
		}  if (purchaseOrederDataBean.getPurToDate()==null) {

			
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"tdate").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Choose the To Date."));
				}
				valid = false;
			
		}
		 if (purchaseOrederDataBean.getPurCategory().equals("select")) {

				
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"category").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Choose the Category."));
				}
				valid = false;
			
		}
		return valid;
	}
	
	public String purchasereturn()
	{		
		System.out.println("return order form");
		setMes("");
		flag1=false;
		try 
		{
			ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			LiusenController controller=(LiusenController) ctx.getBean("controller");
			controller.purchaseReturnsearch(purchaseOrederDataBean);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "purchaseOrderReturnForm";
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
			purchaseOrederDataBean.setPurProductName(""+v.getComponent().getAttributes().get("product"));
			ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");	
			controller.returnCheckpur(purchaseOrederDataBean);
			purchaseOrederDataBean.setRetstatus("");
			if(v.getNewValue().equals(true))
			{
				System.out.println("inside true- -> ");
				PurchaseOrederDataBean list=new PurchaseOrederDataBean();
				list.setText("true");
				list.setPurProductName(""+v.getComponent().getAttributes().get("product"));
				list.setPurQuantity(""+v.getComponent().getAttributes().get("quan"));
				list.setPurserialno(""+v.getComponent().getAttributes().get("no"));
				list.setPurprice(""+v.getComponent().getAttributes().get("price"));
				purchaseOrederDataBean.getPurchaseViewList().set(i, list);
			}
			else if(!v.getNewValue().equals(true))
			{
				System.out.println("inside false- -> ");
				PurchaseOrederDataBean list=new PurchaseOrederDataBean();
				list.setText("false");
				list.setPurProductName(""+v.getComponent().getAttributes().get("product"));
				list.setPurQuantity(""+v.getComponent().getAttributes().get("quan"));
				list.setPurserialno(""+v.getComponent().getAttributes().get("no"));
				list.setPurprice(""+v.getComponent().getAttributes().get("price"));
				purchaseOrederDataBean.getPurchaseViewList().set(i, list);
			}
		}
		catch(LiusenException e)
		{
			System.out.println("catch -- >");
			PurchaseOrederDataBean list=new PurchaseOrederDataBean();
			list.setText("false");
			list.setTick("false");
			list.setPurProductName(""+v.getComponent().getAttributes().get("product"));
			list.setPurQuantity(""+v.getComponent().getAttributes().get("quan"));
			list.setPurserialno(""+v.getComponent().getAttributes().get("no"));
			list.setPurprice(""+v.getComponent().getAttributes().get("price"));
			purchaseOrederDataBean.getPurchaseViewList().set(i, list);
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
			purchaseOrederDataBean.setReturnqauntity(u.getNewValue().toString());
			purchaseOrederDataBean.setPurProductName(""+u.getComponent().getAttributes().get("product"));
			purchaseOrederDataBean.setDr(""+u.getComponent().getAttributes().get("dr"));
			if(purchaseOrederDataBean.getDr().equals("") || purchaseOrederDataBean.getDr().equals("null") || purchaseOrederDataBean.getDr().equals(null))
			{
				purchaseOrederDataBean.setDr("0");
			}
			System.out.println("dr mb - > "+purchaseOrederDataBean.getDr());
			LiusenController controller=null;		
			ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");	
			controller.quantityCheckReturn(purchaseOrederDataBean);
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
			purchaseOrederDataBean.setDr(uv.getNewValue().toString());
			purchaseOrederDataBean.setReturnqauntity(""+uv.getComponent().getAttributes().get("nr"));
			purchaseOrederDataBean.setPurProductName(""+uv.getComponent().getAttributes().get("product"));
			if(purchaseOrederDataBean.getReturnqauntity().equals("") || purchaseOrederDataBean.getReturnqauntity().equals("null") || purchaseOrederDataBean.getReturnqauntity().equals(null))
			{
				purchaseOrederDataBean.setReturnqauntity("0");
			}
			System.out.println("nr mb - > "+purchaseOrederDataBean.getReturnqauntity());
			LiusenController controller=null;		
			ApplicationContext ctx= FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");	
			controller.quantityCheckReturn(purchaseOrederDataBean);
		}
		catch(LiusenException e)
		{
			e.printStackTrace();
			setMes(e.getMessage());
		}
	}
	
	public String returnSubmit()
	{
		System.out.println("inside purchase return submit mb -- >");
		LiusenController controller=null;
		ApplicationContext ctx=null;
		flag1=false;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			int c=0;
			if(purchaseOrederDataBean.getPurchaseViewList().size()>0)
			{				
				for (int i = 0; i < purchaseOrederDataBean.getPurchaseViewList().size(); i++) 
				{
					if(purchaseOrederDataBean.getPurchaseViewList().get(i).getTick().equals("false"))
					{
						c++;
					}
					else
					{
						System.out.println("c -- 2- "+c);
					}
				}
				System.out.println("c-- > "+c);
				if(c==purchaseOrederDataBean.getPurchaseViewList().size())
				{
					throw new Exception("Please Return Atleast one Product");
				}
				else
				{
					for (int i = 0; i < purchaseOrederDataBean.getPurchaseViewList().size(); i++) 
					{
						int c1=0;int c2=0;
						System.out.println("quan -- > "+purchaseOrederDataBean.getPurchaseViewList().get(i).getReturnqauntity()+" dr - > "+purchaseOrederDataBean.getPurchaseViewList().get(i).getDr());
						if(purchaseOrederDataBean.getPurchaseViewList().get(i).getTick().equals("true"))
						{
							if(purchaseOrederDataBean.getPurchaseViewList().get(i).getText().equals("true"))
							{
								if(purchaseOrederDataBean.getPurchaseViewList().get(i).getReturnqauntity().equals(""))
								{
									c1++;								
								}
								if(purchaseOrederDataBean.getPurchaseViewList().get(i).getDr().equals(""))
								{
									c2++;
								}
								if(c1>0 && c2>0)
								{
									throw new Exception("Please Enter the Return Quantity");
								}
								if(purchaseOrederDataBean.getPurchaseViewList().get(i).getReturnqauntity().equals(""))
								{
									purchaseOrederDataBean.getPurchaseViewList().get(i).setReturnqauntity("0");
								}
								else if(purchaseOrederDataBean.getPurchaseViewList().get(i).getDr().equals(""))
								{
									purchaseOrederDataBean.getPurchaseViewList().get(i).setDr("0");
								}
							}													
							else
							{
								purchaseOrederDataBean.getPurchaseViewList().get(i).setReturnqauntity("0");
								purchaseOrederDataBean.getPurchaseViewList().get(i).setDr("0");
							}
						}
					}
					controller.returnSubmitPurcahse(purchaseOrederDataBean);
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
	
	public String searchreturnView() 
	{		
		setMes("");
		flag1=false;
		purchaseOrederDataBean.setPurchaseList(null);
		try 
		{
			if (validate1(true)) 
			{
				ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				LiusenController controller=(LiusenController) ctx.getBean("controller");
				controller.purchaseReturnView(purchaseOrederDataBean);
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
	
	private boolean validate1(boolean flag1) 
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (purchaseOrederDataBean.getPurFromDate()==null) 
		{
			if (flag1)
			{
				fieldName = CommonValidate.findComponentInRoot("fdate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the From Date."));
			}
			valid = false;
		} 
		if (purchaseOrederDataBean.getPurToDate()==null)
		{
			if (flag1)
			{
				fieldName = CommonValidate.findComponentInRoot("tdate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the To Date."));
			}
			valid = false;
		}		
		return valid;
	}
	
	public String purchaseReturnViewLoad() 
	{
		System.out.println("Inside Load the salesReturnViewLoad Page");
		setFlag(false);
		flag1=false;		
		setMes("");
		purchaseOrederDataBean.setPurFromDate(null);
		purchaseOrederDataBean.setPurToDate(null);
		purchaseOrederDataBean.setPurCategory("");
		return "purchaseReturnView";
	}
	
	public String purchasereturnView()
	{		
		System.out.println("return order form");
		setMes("");
		flag1=false;
		try 
		{
			ApplicationContext ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			LiusenController controller=(LiusenController) ctx.getBean("controller");
			controller.purchaseReturnViewRecord(purchaseOrederDataBean);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "purchaseReturnViewForm";
	}
	
	public String viewback()
	{
		return "purchaseReturnViewbac";
	}
}
