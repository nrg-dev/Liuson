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

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.StockOutManualDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="stockOutManualMB")
@RequestScoped
public class StockOutManualMB {
	
	StockOutManualDataBean stockOutManualDataBean = new StockOutManualDataBean();

	private List<String> productNameList=null;
	
	private List<String> projectList=null;	
	
	private ArrayList<StockOutManualDataBean> list=new ArrayList<StockOutManualDataBean>();
	
	private List<String> projectNameList=null;
	
	private boolean flag=false;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public ArrayList<StockOutManualDataBean> getList() {
		return list;
	}

	public void setList(ArrayList<StockOutManualDataBean> list) {
		this.list = list;
	}

	public List<String> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<String> projectList) {
		this.projectList = projectList;
	}

	/**
	 * @return the productNameList
	 */
	public List<String> getProductNameList() {
		return productNameList;
	}

	/**
	 * @param productNameList the productNameList to set
	 */
	public void setProductNameList(List<String> productNameList) {
		this.productNameList = productNameList;
	}

	/**
	 * @return the stockOutManualDataBean
	 */
	public StockOutManualDataBean getStockOutManualDataBean() {
		return stockOutManualDataBean;
	}

	/**
	 * @param stockOutManualDataBean the stockOutManualDataBean to set
	 */
	public void setStockOutManualDataBean(
			StockOutManualDataBean stockOutManualDataBean) {
		this.stockOutManualDataBean = stockOutManualDataBean;
	}

	public String LoadPage()
	{
		LiusenController controller = null;
		projectNameList=null;
		System.out.println("Inside ");
		int j=1;
		try
		{
			projectNameList=new ArrayList<String>();
			stockOutManualDataBean.getStockOutList().clear();
			for(int i=0;i<8;i++)
			{
				StockOutManualDataBean sList=new StockOutManualDataBean();
				sList.setSoutSerialNo(""+j);
				sList.setSoutProductName1("");
				sList.setSoutUnitPrice("");
				sList.setSoutNetAmount("");
				sList.setSoutQty("");
				sList.setSoutLimit("");
				sList.setFlag1("none");
				sList.setFlag("1");
				sList.setPflag("1");
				sList.setPflag1("none");
				sList.setQflag("1");
				sList.setQflag1("none");
				stockOutManualDataBean.getStockOutList().add(sList);
				j++;
			}
			stockOutManualDataBean.setSoutCrossTotal("0");
			stockOutManualDataBean.setSoutCategory("");
			stockOutManualDataBean.setSoutTotalAmount("0");
			stockOutManualDataBean.setSoutTaxType("");
			stockOutManualDataBean.setSoutProjectName("");
			stockOutManualDataBean.setSoutEmployeeID("");
			stockOutManualDataBean.setSoutCustomerName("");
			stockOutManualDataBean.getStockOutList1().clear();
			stockOutManualDataBean.setSoutProjectnames("");

			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			projectNameList=controller.getProjectNames();
			controller.getProjectLists(stockOutManualDataBean);
			/*controller.customerList(stockOutManualDataBean);*/
			controller.employeeList(stockOutManualDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "StockoutManualPage";		
		
	}
	
	public String Add()
	{
		System.out.println("Inside Serila number"+stockOutManualDataBean.getStockOutList().size());
		StockOutManualDataBean sList=new StockOutManualDataBean();
		sList.setSoutSerialNo(""+(stockOutManualDataBean.getStockOutList().size()+1));
		sList.setSoutProductName1("");
		sList.setSoutUnitPrice("");
		sList.setSoutNetAmount("");
		sList.setSoutQty("");
		sList.setFlag1("none");
		sList.setFlag("1");
		sList.setPflag("1");
		sList.setPflag1("none");
		sList.setQflag("1");
		sList.setQflag1("none");
		stockOutManualDataBean.getStockOutList().add(sList);
		return "";
	}
		
	public void categoryChange(ValueChangeEvent event)
	{
		try
		{
			System.out.println("inside "+event.getNewValue());
			LiusenController controller = null;
			String category=event.getNewValue().toString();			
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			productNameList= new ArrayList<String>();
			productNameList=controller.getProjectDetails(category,stockOutManualDataBean);
			System.out.println("Inside MB name" + productNameList+"ss"+stockOutManualDataBean.getSoutCategory());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public void productChange(ValueChangeEvent event)
	{
		try
		{
			System.out.println("inside product - > mb - "+event.getNewValue());
			LiusenController controller = null;
			String product=event.getNewValue().toString();	
			String sno=event.getComponent().getAttributes().get("sno").toString();
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			stockOutManualDataBean.setSoutProductName(product);
			controller.getproductLimit(stockOutManualDataBean);
			System.out.println("limit mb -- > "+stockOutManualDataBean.getSoutLimit());			
			if(validate1(true,product))
			{
				StockOutManualDataBean list=new StockOutManualDataBean();
				list.setSoutProductName1(product);
				list.setSoutLimit(stockOutManualDataBean.getSoutLimit());
				list.setSoutSerialNo(sno);
				list.setFlag("none");
				list.setFlag1("1");
				list.setPflag("1");
				list.setPflag1("none");
				list.setQflag("1");
				list.setQflag1("none");
				stockOutManualDataBean.getStockOutList().set(Integer.parseInt(sno)-1, list);
			}
			else
			{
				StockOutManualDataBean list=new StockOutManualDataBean();
				list.setSoutProductName1("");
				list.setSoutLimit("");
				list.setSoutSerialNo(sno);
				list.setFlag1("none");
				list.setFlag("1");
				list.setPflag("1");
				list.setPflag1("none");
				list.setQflag("1");
				list.setQflag1("none");
				stockOutManualDataBean.getStockOutList().set(Integer.parseInt(sno)-1, list);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	private boolean validate1(boolean flag, String product)
	{
		boolean valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(stockOutManualDataBean.getSoutLimit().equals("0"))
		{
			if(flag)
			{
				fieldName=CommonValidate.findComponentInRoot("message").getClientId(fc);
				fc.addMessage(fieldName , new FacesMessage("No Limit for "+product +" Product"));
			}
			valid=false;
		}		
		return valid;
	}
	
	public void productPrice(ValueChangeEvent vv)
	{
		System.out.println("price - > "+vv.getNewValue());
		try
		{
			String sno=vv.getComponent().getAttributes().get("sno").toString();
			StockOutManualDataBean list=new StockOutManualDataBean();
			list.setSoutProductName1(vv.getComponent().getAttributes().get("product").toString());
			list.setSoutLimit(vv.getComponent().getAttributes().get("limit").toString());
			list.setSoutUnitPrice(vv.getNewValue().toString());
			list.setSoutSerialNo(sno);
			list.setFlag("none");
			list.setFlag1("1");
			list.setPflag1("1");
			list.setPflag("none");
			list.setQflag("1");
			list.setQflag1("none");
			stockOutManualDataBean.getStockOutList().set(Integer.parseInt(sno)-1, list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void quantityChange(ValueChangeEvent vu)
	{
		System.out.println("quantity - > "+vu.getNewValue());
		BigDecimal netamnt=BigDecimal.valueOf(0), total =BigDecimal.valueOf(0);
		LiusenController controller=null;
		ApplicationContext ctx=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");		
			String sno=vu.getComponent().getAttributes().get("sno").toString();
			String pp=vu.getComponent().getAttributes().get("product").toString();	
			stockOutManualDataBean.setSoutQty(vu.getNewValue().toString());
			stockOutManualDataBean.setSoutProductName(pp);
			stockOutManualDataBean.setSoutLimit(vu.getComponent().getAttributes().get("limit").toString());
			if(Integer.parseInt(stockOutManualDataBean.getSoutQty())<=Integer.parseInt(stockOutManualDataBean.getSoutLimit()))
			{
				System.out.println("quantity is less -- >");
				controller.quantityCheckStockOut(stockOutManualDataBean);
				if(valida(true))
				{
					StockOutManualDataBean list=new StockOutManualDataBean();
					list.setSoutProductName1(vu.getComponent().getAttributes().get("product").toString());
					list.setSoutLimit(vu.getComponent().getAttributes().get("limit").toString());
					list.setSoutUnitPrice(vu.getComponent().getAttributes().get("price").toString());
					list.setSoutSerialNo(sno);
					list.setFlag("none");
					list.setFlag1("1");
					list.setPflag1("1");
					list.setPflag("none");
					list.setQflag1("1");
					list.setQflag("none");
					list.setSoutQty(vu.getNewValue().toString());
					netamnt=new BigDecimal(vu.getNewValue().toString()).multiply(new BigDecimal(vu.getComponent().getAttributes().get("price").toString()));
					System.out.println("net amount - > "+netamnt);
					list.setSoutNetAmount(""+netamnt);
					stockOutManualDataBean.getStockOutList().set(Integer.parseInt(sno)-1, list);
					productNameList.remove(pp);
				}
				else
				{
					StockOutManualDataBean list=new StockOutManualDataBean();
					list.setSoutProductName1("");
					list.setSoutLimit("");
					list.setSoutUnitPrice("");
					list.setSoutSerialNo(sno);
					list.setFlag1("none");
					list.setFlag("1");
					list.setPflag("1");
					list.setPflag1("none");
					list.setQflag("1");
					list.setQflag1("none");
					list.setSoutQty("");
					list.setSoutNetAmount("");
					stockOutManualDataBean.getStockOutList().set(Integer.parseInt(sno)-1, list);
				}
			}
			else
			{ 
				System.out.println("quantity is greater");
				StockOutManualDataBean list=new StockOutManualDataBean();
				list.setSoutProductName1(vu.getComponent().getAttributes().get("product").toString());
				list.setSoutLimit(vu.getComponent().getAttributes().get("limit").toString());
				list.setSoutUnitPrice(vu.getComponent().getAttributes().get("price").toString());
				list.setSoutSerialNo(sno);
				list.setFlag("none");
				list.setFlag1("1");
				list.setPflag1("1");
				list.setPflag("none");
				list.setQflag1("1");
				list.setQflag("none");
				list.setSoutQty(vu.getNewValue().toString());
				netamnt=new BigDecimal(vu.getNewValue().toString()).multiply(new BigDecimal(vu.getComponent().getAttributes().get("price").toString()));
				System.out.println("net amount - > "+netamnt);
				list.setSoutNetAmount(""+netamnt);
				stockOutManualDataBean.getStockOutList().set(Integer.parseInt(sno)-1, list);
				productNameList.remove(pp);
			}			
			total=new BigDecimal(stockOutManualDataBean.getSoutCrossTotal()).add(netamnt);
			System.out.println("total -- > "+total);
			stockOutManualDataBean.setSoutCrossTotal(""+total);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean valida(boolean flag)
	{
		boolean valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(stockOutManualDataBean.getMessage().equals("Exception"))
		{
			if(flag)
			{
				fieldName=CommonValidate.findComponentInRoot("message").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(stockOutManualDataBean.getSoutProductName()+" has only "+stockOutManualDataBean.getSoutQty()+" Stock in Warehouse"));
			}
			valid=false;
		}
		if(stockOutManualDataBean.getMessage1().equals("no prodcut"))
		{
			if(flag)
			{
				fieldName=CommonValidate.findComponentInRoot("message").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(stockOutManualDataBean.getSoutProductName()+" not in Stock"));
			}
			valid=false;
		}
		return valid;
	}
	
	public String recordEdit()
	{
		System.out.println("inside edit record - > ");
		BigDecimal amnt=BigDecimal.valueOf(0);
		try
		{
			String sno=stockOutManualDataBean.getSoutSerialNo();
			String product=stockOutManualDataBean.getSoutProductName1();
			StockOutManualDataBean edit=new StockOutManualDataBean();
			edit.setSoutSerialNo(sno);
			edit.setSoutProductName1("");
			edit.setSoutLimit("");
			edit.setSoutUnitPrice("");
			edit.setFlag1("none");
			edit.setFlag("1");
			edit.setPflag("1");
			edit.setPflag1("none");
			edit.setQflag("1");
			edit.setQflag1("none");
			edit.setSoutNetAmount("");
			edit.setSoutQty("");
			stockOutManualDataBean.getStockOutList().set(Integer.parseInt(sno)-1 , edit);
			if(!stockOutManualDataBean.getSoutNetAmount().equals(""))
			{
				amnt=new BigDecimal(stockOutManualDataBean.getSoutCrossTotal()).subtract(new BigDecimal(stockOutManualDataBean.getSoutNetAmount()));
				System.out.println("after subtract amount - > "+amnt);
				productNameList.add(product);
			}
			else
			{
				amnt=new BigDecimal(stockOutManualDataBean.getSoutCrossTotal());
				System.out.println(" amount - > "+amnt);
			}
			stockOutManualDataBean.setSoutCrossTotal(""+amnt);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public void taxTypeChange(ValueChangeEvent v)
	{
		System.out.println("tax type -- >. "+v.getNewValue());
		BigDecimal taxamnt=BigDecimal.valueOf(0);
		BigDecimal totalamnt=BigDecimal.valueOf(0);
		if(v.getNewValue().equals("NO Tax"))
		{
			stockOutManualDataBean.setSoutTotalAmount(stockOutManualDataBean.getSoutCrossTotal());
		}
		else if(v.getNewValue().equals("10% Tax"))
		{
			taxamnt=(new BigDecimal(stockOutManualDataBean.getSoutCrossTotal()).multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
			System.out.println("tax amnt -- >. "+taxamnt);
			totalamnt=new BigDecimal(stockOutManualDataBean.getSoutCrossTotal()).add(taxamnt);
			System.out.println("total amount -- > "+totalamnt);
			stockOutManualDataBean.setSoutTotalAmount(""+totalamnt);
		}		
	}
	
	public String stockManualInsert()
	{
		System.out.println("inside manual stock out insert -- ");
		LiusenController controller=null;
		ApplicationContext ctx=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			if(validate(true))
			{
				controller.stockoutManual(stockOutManualDataBean);
				int cc=1;
				for (int i = 0; i <stockOutManualDataBean.getStockOutList().size(); i++) 
				{
					StockOutManualDataBean ss=new StockOutManualDataBean();
					if(!stockOutManualDataBean.getStockOutList().get(i).getSoutProductName1().equals(""))
					{
						ss.setSoutSerialNo(""+cc);
						System.out.println("no - > "+cc);
						ss.setSoutProductName1(stockOutManualDataBean.getStockOutList().get(i).getSoutProductName1());
						ss.setSoutUnitPrice(stockOutManualDataBean.getStockOutList().get(i).getSoutUnitPrice());
						ss.setSoutQty(stockOutManualDataBean.getStockOutList().get(i).getSoutQty());
						ss.setSoutNetAmount(stockOutManualDataBean.getStockOutList().get(i).getSoutNetAmount());
						list.add(ss);
						stockOutManualDataBean.setStockOutList1(list);
						cc++;
					}
					else
					{
						System.out.println("product empty - >");
					}
				}
				System.out.println("stock out size mb - > "+stockOutManualDataBean.getStockOutList1().size());
				return "stockOutManualConfirmation";
			}
			else
			{
				System.out.println("validate -- >");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	private boolean validate(boolean flag)
	{
		boolean valid=true;
		String fieldName;
		FacesContext fc=FacesContext.getCurrentInstance();
		if(stockOutManualDataBean.getSoutProjectName()==null || stockOutManualDataBean.getSoutProjectName().equals(""))
		{
			if(flag)
			{
				fieldName=CommonValidate.findComponentInRoot("proname").getClientId(fc);
				fc.addMessage(fieldName , new FacesMessage("Select one Product Name"));
			}
			valid=false;
		}
		if(stockOutManualDataBean.getSoutCategory()==null || stockOutManualDataBean.getSoutCategory().equals(""))
		{
			if(flag)
			{
				fieldName=CommonValidate.findComponentInRoot("category").getClientId(fc);
				fc.addMessage(fieldName , new FacesMessage("Select one Category"));
			}
			valid=false;
		}
	/*	if(stockOutManualDataBean.getSoutCustomerName()==null || stockOutManualDataBean.getSoutCustomerName().equals(""))
		{
			if(flag)
			{
				fieldName=CommonValidate.findComponentInRoot("cusno").getClientId(fc);
				fc.addMessage(fieldName , new FacesMessage("Select one Customer Phone Number"));
			}
			valid=false;
		}*/
		if(stockOutManualDataBean.getSoutProjectnames()==null || stockOutManualDataBean.getSoutProjectnames().equals(""))
		{
			if(flag)
			{
				fieldName=CommonValidate.findComponentInRoot("cusno").getClientId(fc);
				fc.addMessage(fieldName , new FacesMessage("Select one Project Name"));
			}
			valid=false;
		}
		if(stockOutManualDataBean.getSoutEmployeeID()==null || stockOutManualDataBean.getSoutEmployeeID().equals(""))
		{
			if(flag)
			{
				fieldName=CommonValidate.findComponentInRoot("empid").getClientId(fc);
				fc.addMessage(fieldName , new FacesMessage("Select one Employee ID"));
			}
			valid=false;
		}
		if(stockOutManualDataBean.getStockOutList().size()>0)
		{
			int count=0;
			for(int i=0;i<stockOutManualDataBean.getStockOutList().size();i++)
			{
				if(stockOutManualDataBean.getStockOutList().get(i).getSoutProductName1().equals(""))
				{
					count++;
				}
				else if(!stockOutManualDataBean.getStockOutList().get(i).getSoutProductName1().equals(""))
				{
					if(stockOutManualDataBean.getStockOutList().get(i).getSoutUnitPrice()==null || stockOutManualDataBean.getStockOutList().get(i).getSoutUnitPrice().equals(""))
					{
						if(flag)
						{
							fieldName=CommonValidate.findComponentInRoot("message").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Price"));
						}
						valid=false;
					}
					else if(stockOutManualDataBean.getStockOutList().get(i).getSoutQty()==null || stockOutManualDataBean.getStockOutList().get(i).getSoutQty().equals(""))
					{
						if(flag)
						{
							fieldName=CommonValidate.findComponentInRoot("message").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Quantity"));
						}
						valid=false;
					}
				}
				if(count==stockOutManualDataBean.getStockOutList().size())
				{
					if(flag)
					{
						fieldName=CommonValidate.findComponentInRoot("message").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Choose atleast one Product"));
					}
					valid=false;
				}
			}
		}		
		if(stockOutManualDataBean.getSoutTaxType()==null || stockOutManualDataBean.getSoutTaxType().equals(""))
		{
			if(flag)
			{
				fieldName=CommonValidate.findComponentInRoot("taxType").getClientId(fc);
				fc.addMessage(fieldName , new FacesMessage("Select one Tax Type"));
			}
			valid=false;
		}
		return valid;
	}
	
//	go to sstock out manual from invoice form
	public String viewback()
	{
		stockOutManualDataBean.setMessage("");
		LoadPage();
		return "stockOutmanualBack";
	}
	
	public String reset()
	{
		System.out.println("reset ");
		for(int i=0;i<stockOutManualDataBean.getStockOutList().size();i++)
		{
			if(!stockOutManualDataBean.getStockOutList().get(i).getSoutProductName1().equals(""))
			{
				StockOutManualDataBean sList=new StockOutManualDataBean();
				sList.setSoutSerialNo(""+(i+1));
				sList.setSoutProductName1("");
				sList.setSoutUnitPrice("");
				sList.setSoutNetAmount("");
				sList.setSoutQty("");
				sList.setSoutLimit("");
				sList.setFlag1("none");
				sList.setFlag("1");
				sList.setPflag("1");
				sList.setPflag1("none");
				sList.setQflag("1");
				sList.setQflag1("none");
				stockOutManualDataBean.getStockOutList().set(i , sList);
			}			
		}
		stockOutManualDataBean.setSoutCrossTotal("0");
		stockOutManualDataBean.setSoutCategory("");
		stockOutManualDataBean.setSoutTotalAmount("0");
		stockOutManualDataBean.setSoutTaxType("");
		stockOutManualDataBean.setSoutProjectName("");
		stockOutManualDataBean.setSoutEmployeeID("");
		stockOutManualDataBean.setSoutCustomerName("");
		stockOutManualDataBean.setSoutProjectnames("");
		return "";
	}
	
	public String loadstokoutview()
	{
		System.out.println("stock out manual view page");
		stockOutManualDataBean.setFdate(null);
		stockOutManualDataBean.setTdate(null);
		stockOutManualDataBean.setSoutCategory("");
		stockOutManualDataBean.setSoutProjectName("");
		setFlag(true);
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			controller.projectNames(stockOutManualDataBean);							
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "stockOutManualView";
	}
	
	public String dateSearch()
	{
		System.out.println("stock out manual view by date search");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		stockOutManualDataBean.setSoutCategory("");
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			if(dateValidate(true))
			{
				controller.dateSearchView(stockOutManualDataBean);
				setFlag(false);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	private boolean dateValidate(boolean date)
	{
		boolean valid=true;
		FacesContext fc=FacesContext.getCurrentInstance();
		String fieldName;
		if(stockOutManualDataBean.getFdate()==null)
		{
			if(date)
			{
				fieldName=CommonValidate.findComponentInRoot("fdate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Select From Date"));
			}
			valid=false;
		}
		if(stockOutManualDataBean.getTdate()==null)
		{
			if(date)
			{
				fieldName=CommonValidate.findComponentInRoot("tdate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Select To Date"));
			}
			valid=false;
		}
		return valid;
	}
	
	public String stockOutView()
	{
		System.out.println("stock out manual view records");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			controller.viewOutRecords(stockOutManualDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "stockOutManualViewForm";
	}
	
	public String stockviewback()
	{
		setFlag(true);
		stockOutManualDataBean.setFdate(null);
		stockOutManualDataBean.setTdate(null);
		stockOutManualDataBean.setSoutCategory("");
		return "stockOutManualViewback";
	}
	
	public String categoryserach()
	{
		System.out.println("stock out manual view by category search");
		ApplicationContext ctx=null;
		LiusenController controller=null;
		stockOutManualDataBean.setFdate(null);
		stockOutManualDataBean.setTdate(null);
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController) ctx.getBean("controller");
			if(categoryValidate(true))
			{
				controller.categorySearchView(stockOutManualDataBean);
				setFlag(false);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	private boolean categoryValidate(boolean cate)
	{
		boolean valid=true;
		FacesContext fc=FacesContext.getCurrentInstance();
		String fieldName;
		if(stockOutManualDataBean.getSoutCategory().equals(""))
		{
			if(cate)
			{
				fieldName=CommonValidate.findComponentInRoot("category").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Choose the Category Name"));
			}
			valid=false;
		}		
		return valid;
	}

	/**
	 * @return the projectNameList
	 */
	public List<String> getProjectNameList() {
		return projectNameList;
	}

	/**
	 * @param projectNameList the projectNameList to set
	 */
	public void setProjectNameList(List<String> projectNameList) {
		this.projectNameList = projectNameList;
	}
}
