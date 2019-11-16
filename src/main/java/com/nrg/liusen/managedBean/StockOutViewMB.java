package com.nrg.liusen.managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.StockDataBean;
import com.nrg.liusen.util.CommonValidate;

public class StockOutViewMB {

	StockDataBean stockDataBean=new StockDataBean();
	private boolean flag=true;
	

	/**
	 * @return the stockDataBean
	 */
	public StockDataBean getStockDataBean() {
		return stockDataBean;
	}

	/**
	 * @param stockDataBean the stockDataBean to set
	 */
	public void setStockDataBean(StockDataBean stockDataBean) {
		this.stockDataBean = stockDataBean;
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
	public String stockOutPageLoad() {

		System.out.println("Inside Load the stockOutPageLoad Page");
		setFlag(true);
		stockDataBean.setStockProductName("");
		stockDataBean.setStockCategory("");
		return "outStockLoadPage";

	}
	
	/**
	 * This method is used for show data tables depends on Product Name
	 * 
	 * @return
	 */
	public String searchProduct()
	{		
		System.out.println("stock out view - >  mb ");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try 
		{
			if (validate(true)) 
			{
				System.out.println("After Validate ");
				ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller=(LiusenController) ctx.getBean("controller");
				controller.stockoutview(stockDataBean);
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
	
	private boolean validate(boolean flag1)
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if(stockDataBean.getStockCategory().equals("select"))
		{
			if (flag1)
			{
				fieldName = CommonValidate.findComponentInRoot("category").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose Category"));
			}
			valid = false;
		}
		else if(!stockDataBean.getStockCategory().equals("select"))
		{
			if(stockDataBean.getStockCategory().equals("Product Trading"))
			{
				if (stockDataBean.getStockProductName().equalsIgnoreCase("SOUT00"))
				{
					if (flag1)
					{
						fieldName = CommonValidate.findComponentInRoot("stockOutName").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Choose the Product Name."));
					}
					valid = false;
				} 
			}
			else if(!stockDataBean.getStockCategory().equals("Product Trading"))
			{
				if (stockDataBean.getStockProductName().equalsIgnoreCase("SOUT01"))
				{
					if (flag1)
					{
						fieldName = CommonValidate.findComponentInRoot("stockOutName").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Choose the Project Name."));
					}
					valid = false;
				} 
			}
		}
		return valid;
	}
	
	public void categoryChange(ValueChangeEvent v)
	{
		System.out.println("category - > "+v.getNewValue());
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			stockDataBean.setStockCategory(v.getNewValue().toString());
			controller.categoryOut(stockDataBean);
			setFlag(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
}
