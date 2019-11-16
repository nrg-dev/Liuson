package com.nrg.liusen.managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.domain.StockOutManualDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="salesRemainderMB")
public class SalesRemainderMB
{
	SalesOrderDataBean salesOrderDataBean=new SalesOrderDataBean();
	StockOutManualDataBean stockOutManualDataBean=new StockOutManualDataBean();
	private boolean flag=false;
	private boolean reflag=false;
	private int salesapprovalGM;
	private int salesapprovalMM;
	private int salesdeliveryGM;
	private int salesdeliveryPM;
	private int stockoutmanual;
	private int salespaymentFM;
	private String mes;
	private int salespaymentdirector;
	private int stockoutmanualPM;
	public int getStockoutmanualPM() {
		return stockoutmanualPM;
	}
	public void setStockoutmanualPM(int stockoutmanualPM) {
		this.stockoutmanualPM = stockoutmanualPM;
	}
	public int getSalespaymentdirector() {
		return salespaymentdirector;
	}
	public void setSalespaymentdirector(int salespaymentdirector) {
		this.salespaymentdirector = salespaymentdirector;
	}
	public int getSalespaymentFM() {
		return salespaymentFM;
	}
	public void setSalespaymentFM(int salespaymentFM) {
		this.salespaymentFM = salespaymentFM;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public StockOutManualDataBean getStockOutManualDataBean() {
		return stockOutManualDataBean;
	}
	public void setStockOutManualDataBean(
			StockOutManualDataBean stockOutManualDataBean) {
		this.stockOutManualDataBean = stockOutManualDataBean;
	}
	public int getStockoutmanual() {
		return stockoutmanual;
	}
	public void setStockoutmanual(int stockoutmanual) {
		this.stockoutmanual = stockoutmanual;
	}
	public int getSalesdeliveryGM() {
		return salesdeliveryGM;
	}
	public void setSalesdeliveryGM(int salesdeliveryGM) {
		this.salesdeliveryGM = salesdeliveryGM;
	}
	public int getSalesdeliveryPM() {
		return salesdeliveryPM;
	}
	public void setSalesdeliveryPM(int salesdeliveryPM) {
		this.salesdeliveryPM = salesdeliveryPM;
	}
	public int getSalesapprovalGM() {
		return salesapprovalGM;
	}
	public void setSalesapprovalGM(int salesapprovalGM) {
		this.salesapprovalGM = salesapprovalGM;
	}
	public int getSalesapprovalMM() {
		return salesapprovalMM;
	}
	public void setSalesapprovalMM(int salesapprovalMM) {
		this.salesapprovalMM = salesapprovalMM;
	}
	
	public boolean isReflag() {
		return reflag;
	}

	public void setReflag(boolean reflag) {
		this.reflag = reflag;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public SalesOrderDataBean getSalesOrderDataBean() {
		return salesOrderDataBean;
	}

	public void setSalesOrderDataBean(SalesOrderDataBean salesOrderDataBean) {
		this.salesOrderDataBean = salesOrderDataBean;
	}
	
	public String remainders()
	{
		ApplicationContext ctx=null;
		LiusenController controller=null;
		salesOrderDataBean.setSalist(null);;
		salesOrderDataBean.setSalesList(null);
		stockOutManualDataBean.setStockOutList(null);
		stockOutManualDataBean.setStockOutList1(null);
		try
		{
			ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");			
			salesapprovalGM=controller.remSalesApprovalGM();
			salesapprovalMM=controller.remSalesApprovalMM();
			salesdeliveryGM=controller.remSalesDeliveryGM();
			salesdeliveryPM=controller.remSalesDeliveryPM();
			stockoutmanual=controller.remStockOutManual();
			stockoutmanualPM=controller.remStockOutManualPM();
			salespaymentdirector=controller.remSalesPaymnetdirector();
			salespaymentFM=controller.remSalesPaymentFM();
			System.out.println("loigin -- "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "";
	}	
	
	/*List of approval by MM for sales*/
	public String listofApprovalMM()
	{
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.approveMM(salesOrderDataBean);
			System.out.println("approval size -MM- > "+salesOrderDataBean.getSalist().size());
			salesOrderDataBean.setStatus("MM");
			flag=false;
			reflag=false;
		}
		catch(NullPointerException n)
		{
			System.out.println("inside null pointer exception");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";		
	}
	
	/*List of approval by GM for sales*/
	public String listofApprovalGM()
	{
		ApplicationContext ctx=null;
		LiusenController controller=null;
		salesOrderDataBean.setSalist(null);
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.approveGM(salesOrderDataBean);
			System.out.println("approval size -GM- > "+salesOrderDataBean.getSalist().size());
			salesOrderDataBean.setStatus("GM");
			flag=false;
			reflag=false;
		}
		catch(NullPointerException n)
		{
			System.out.println("inside null pointer exception");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";		
	}
	
	/*approved sales by MM*/
	public String approveMM()
	{
		System.out.println("approve mm-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			if(validate1(true))
			{
				controller.approvedSalesMM(salesOrderDataBean);
				if(salesOrderDataBean.getAction().equals("Approve"))
				{
					reflag=false;flag=true;
				}
				else if(salesOrderDataBean.getAction().equals("Reject"))
				{
					flag=false;reflag=true;
				}				
			}
			else
			{
				System.out.println("validte ");
			}				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			salesOrderDataBean.setMessage(e.getMessage());
		}
		
		return "";		
	}
	
	/*validation for approval*/
	private boolean validate1(boolean flag)
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (salesOrderDataBean.getAction().equals(""))
		{
			System.out.println("action -- > validate "+salesOrderDataBean.getAction());
			if (flag) 
			{
				fieldName = CommonValidate.findComponentInRoot("dell").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select one Action"));
			}
			valid = false;
		} 
		else if (!salesOrderDataBean.getAction().equals(""))
		{
			if (salesOrderDataBean.getAction().equals("Reject"))
			{
				System.out.println("action -- >  "+salesOrderDataBean.getAction());
				if (salesOrderDataBean.getReason().equals(""))
				{
					System.out.println("reason -- > validate "+salesOrderDataBean.getReason());
					if (flag) 
					{
						fieldName = CommonValidate.findComponentInRoot("dell").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the Reason"));
					}
					valid = false;
				} 
			} 
		}
		return valid;
	}
	
	/*approved sales by GM*/
	public String approveGM()
	{
		System.out.println("approve GM-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			if(validate1(true))
			{
				controller.approvedSalesGM(salesOrderDataBean);
				if(salesOrderDataBean.getAction().equals("Approve"))
				{
					reflag=false;flag=true;
				}
				else if(salesOrderDataBean.getAction().equals("Reject"))
				{
					flag=false;reflag=true;
				}				
			}
			else
			{
				System.out.println("validte ");
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			salesOrderDataBean.setMessage(e.getMessage());
		}		
		return "";		
	}
	
	/*dialog box for MM*/
	public String dialogMM()
	{
		System.out.println("inside dialog hide -MM->");
		flag=false;
		listofApprovalMM();
		return "";
	}
	
	/*dialog box for GM*/
	public String dialogGM()
	{
		System.out.println("inside dialog hide -GM->");
		flag=false;
		listofApprovalGM();
		return "";
	}
	
	/*View approval order by MM*/
	public String viewApprovalMM()
	{
		System.out.println("approve view by  MM-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.viewEachSales(salesOrderDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "approvalViewPageMM";
	}
	
	/*View approval order by GM*/
	public String viewApprovalGM()
	{
		System.out.println("approve view by  GM-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.viewEachSales(salesOrderDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "approvalViewPageMM";
	}
	
	/*back approval page from view*/
	public String viewApprovalBack()
	{
		flag=false;
		reflag=false;
		if(salesOrderDataBean.getStatus().equals("MM"))
		{
			return "approvalViewPageMM";
		}
		if(salesOrderDataBean.getStatus().equals("GM"))
		{
			return "approvalViewPageGM";
		}
		return "";
	}
	
	/*List of delivery by GM for sales*/
	public String listofDeliveryGM()
	{
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.deliveryGM(salesOrderDataBean);
			System.out.println("delivery size -GM- > "+salesOrderDataBean.getSalist().size());
			salesOrderDataBean.setStatus("GM");
			flag=false;
			reflag=false;
		}
		catch(NullPointerException n)
		{
			System.out.println("inside null pointer exception");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";		
	}
	
	/*List of delivery by PM for sales*/
	public String listofDeliveryPM()
	{
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.deliveryPM(salesOrderDataBean);
			System.out.println("delivery size -PM- > "+salesOrderDataBean.getSalist().size());
			salesOrderDataBean.setStatus("PM");
			flag=false;
			reflag=false;
		}
		catch(NullPointerException n)
		{
			System.out.println("inside null pointer exception");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";		
	}
	
	/*delivery sales by GM*/
	public String deliveryGM()
	{
		System.out.println("delivery GM-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			if(validate2(true))
			{
				controller.deliverdSalesGM(salesOrderDataBean);
				if(salesOrderDataBean.getAction().equals("Delivery"))
				{
					reflag=false;flag=true;
				}
				else if(salesOrderDataBean.getAction().equals("Reject"))
				{
					flag=false;reflag=true;
				}				
			}
			else
			{
				System.out.println("validte ");
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "";		
	}
	
	/*validation for delivery*/
	private boolean validate2(boolean flag)
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (salesOrderDataBean.getAction().equals(""))
		{
			System.out.println("action -- > validate "+salesOrderDataBean.getAction());
			if (flag) 
			{
				fieldName = CommonValidate.findComponentInRoot("dell").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select one Action"));
			}
			valid = false;
		} 
		else if (!salesOrderDataBean.getAction().equals(""))
		{
			if (salesOrderDataBean.getAction().equals("Reject"))
			{
				System.out.println("action -- >  "+salesOrderDataBean.getAction());
				if (salesOrderDataBean.getReason().equals(""))
				{
					System.out.println("reason -- > validate "+salesOrderDataBean.getReason());
					if (flag) 
					{
						fieldName = CommonValidate.findComponentInRoot("dell").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the Reason"));
					}
					valid = false;
				} 
			} 
		}		
		return valid;
	}
	
	/*delivery sales by PM*/
	public String deliveryPM()
	{
		System.out.println("delivery  PM-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			if(validate2(true))
			{
				controller.deliverdSalesPM(salesOrderDataBean);
				if(salesOrderDataBean.getAction().equals("Delivery"))
				{
					reflag=false;flag=true;
				}
				else if(salesOrderDataBean.getAction().equals("Reject"))
				{
					flag=false;reflag=true;
				}				
			}
			else
			{
				System.out.println("validte ");
			}				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			salesOrderDataBean.setMessage(e.getMessage());
		}		
		return "";		
	}
	
	/*dialog box for GM delivery*/
	public String dialogGMdel()
	{
		System.out.println("inside dialog hide -GM-delivery >");
		flag=false;
		listofDeliveryGM();
		return "";
	}
	
	/*dialog box for PM delivery*/
	public String dialogPMdel()
	{
		System.out.println("inside dialog hide -PM delivery->");
		flag=false;
		listofDeliveryPM();
		return "";
	}
	
	/*View delivery order by PM*/
	public String viewDeliveryGM()
	{
		System.out.println("delivery view by GM-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.viewEachSales(salesOrderDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "deliveryViewPage";
	}
	
	/*View delivery order by PM*/
	public String viewDeliveryPM()
	{
		System.out.println("delivery view by PM-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.viewEachSales(salesOrderDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "deliveryViewPage";
	}
	
	/*back delivery page from view*/
	public String viewDeliveryBack()
	{
		flag=false;
		reflag=false;
		if(salesOrderDataBean.getStatus().equals("GM"))
		{
			return "deliveryViewPageGM";
		}
		if(salesOrderDataBean.getStatus().equals("PM"))
		{
			return "deliveryViewPagePM";
		}
		return "";
	}
	
	/*Stock Out manual waiting list jan 11*/ 
	public String stockWaitingList()
	{
		System.out.println("Stock Out manual waiting list -- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.stockOutWaitingList(stockOutManualDataBean);			
			setFlag(false);reflag=false;
			stockOutManualDataBean.setStatus("GM");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String stockoutapprove()
	{
		System.out.println("Stock Out manual approval-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			if(validatestkout(true))
			{
				controller.stockoutapproval(stockOutManualDataBean);
				if(validate3(true))
				{
					if(stockOutManualDataBean.getAction().equals("Approve"))
					{
						reflag=false;flag=true;
					}
					else if(stockOutManualDataBean.getAction().equals("Reject"))
					{
						flag=false;reflag=true;
					}	
				}
				else
				{
					System.out.println("validate -- ");
				}
			}
			else
			{
				
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	private boolean validate3(boolean flag)
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (stockOutManualDataBean.getSoutQty().equals("greater"))
		{
			if (flag) 
			{
				fieldName = CommonValidate.findComponentInRoot("dell").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Less Stock in Warehouse"));
			}
			valid = false;
		} 		
		return valid;
	}
	
	public String stockwaitingView()
	{
		System.out.println("Stock Out manual approval view-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.stockOutManualView(stockOutManualDataBean);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "stockOutManualApproveView";
	}
	
	public String viewback()
	{		
		if(stockOutManualDataBean.getStatus().equals("GM"))
		{
			stockWaitingList();
			return "stockOutManualwaitingback";
		}
		if(stockOutManualDataBean.getStatus().equals("PM"))
		{
			stockWaitingListPM();
			return "stockOutManualwaitingbackPM";
		}
		return "";	
	}
	
	public String stockoutapprov()
	{
		stockWaitingList();
		return "";	
	}
	
	public String paymentFM()
	{
		System.out.println("sales payment approval FM-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		setFlag(false);
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.paymentFM(salesOrderDataBean);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String paymentApproveFM()
	{
		System.out.println("payment approval FM-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.paymentApproveFM(salesOrderDataBean);
			setFlag(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return "";
	}
	
	public String paymentDirector()
	{
		System.out.println("sales payment approval director-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		setFlag(false);
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.paymentDirector(salesOrderDataBean);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String paymentApproveDirector()
	{
		System.out.println("payment approval FM-- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.paymentApproveDirector(salesOrderDataBean);	
			setFlag(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return "";
	}
	
	private boolean validatestkout(boolean flag)
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (stockOutManualDataBean.getAction().equals(""))
		{
			System.out.println("action -- > validate "+salesOrderDataBean.getAction());
			if (flag) 
			{
				fieldName = CommonValidate.findComponentInRoot("dell").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select one Action"));
			}
			valid = false;
		} 
		else if (!stockOutManualDataBean.getAction().equals(""))
		{
			if (stockOutManualDataBean.getAction().equals("Reject"))
			{
				System.out.println("action -- >  "+salesOrderDataBean.getAction());
				if (stockOutManualDataBean.getReason().equals(""))
				{
					System.out.println("reason -- > validate "+salesOrderDataBean.getReason());
					if (flag) 
					{
						fieldName = CommonValidate.findComponentInRoot("dell").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the Reason"));
					}
					valid = false;
				} 
			} 
		}
		return valid;
	}
	
	public String stockWaitingListPM()
	{
		System.out.println("Stock Out manual waiting list -PM- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			controller.stockOutWaitingListPM(stockOutManualDataBean);			
			setFlag(false);reflag=false;
			stockOutManualDataBean.setStatus("PM");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String stockoutapprovePM()
	{
		System.out.println("Stock Out manual approval-PM- >. ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller =(LiusenController) ctx.getBean("controller");
			if(validatestkout(true))
			{
				controller.stockoutapprovalPM(stockOutManualDataBean);
				if(validate3(true))
				{
					if(stockOutManualDataBean.getAction().equals("Approve"))
					{
						reflag=false;flag=true;
					}
					else if(stockOutManualDataBean.getAction().equals("Reject"))
					{
						flag=false;reflag=true;
					}	
				}
				else
				{
					System.out.println("validate -- ");
				}
			}
			else
			{
				
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String stockoutreject()
	{
		stockWaitingListPM();
		return "";	
	}
}
