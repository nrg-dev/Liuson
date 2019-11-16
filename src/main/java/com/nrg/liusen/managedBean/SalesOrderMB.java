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
import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="salesMB")
@RequestScoped
public class SalesOrderMB {

	SalesOrderDataBean salesOrderDataBean=new SalesOrderDataBean();

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
	
	public boolean dialog=false;
	List<SalesOrderDataBean> list=new ArrayList<SalesOrderDataBean>();
	public List<SalesOrderDataBean> getList() {
		return list;
	}

	public void setList(List<SalesOrderDataBean> list) {
		this.list = list;
	}

	public boolean isDialog() {
		return dialog;
	}

	public void setDialog(boolean dialog) {
		this.dialog = dialog;
	}

	/**
	 * salesPageLoad Method is used for redirect to purchase registration form
	 * page
	 * 
	 * @return success go to Sales Order form page
	 */
	public String salesPageLoad() {
		System.out.println("Inside salesPageLoad Method Calling");
		salesOrderDataBean.setSaleDate(null);
		salesOrderDataBean.setSaleEstDate(null);
		salesOrderDataBean.setSaleCustomerName("");
		salesOrderDataBean.setSaleCrossTotal("0");
		salesOrderDataBean.setSaleTotalAmount("");
		salesOrderDataBean.setSaleTaxType("");
		salesOrderDataBean.setSaleCategory("");
		salesOrderDataBean.setSaleStaffId("");
		salesOrderDataBean.getSalesList().clear();
		salesOrderDataBean.setDisamnt("");
		salesOrderDataBean.setDisType("");
		/*salesOrderDataBean.getSalist().clear();
		*/list.clear();
		salesOrderDataBean.setMessage("");
		dialog=false;
		int j=1;
		for(int i=0;i<8;i++){
			SalesOrderDataBean sList=new SalesOrderDataBean();
			sList.setSaleSerialNo(""+j);
			sList.setSaleProductName("");
			sList.setSalePrice("");
			sList.setSaleNetAmount("");
			sList.setSaleQuantity("");
			sList.setPflag("1");
			sList.setPflag1("none");
			sList.setQflag("1");
			sList.setQflag1("none");
			salesOrderDataBean.getSalesList().add(sList);
			j++;
		}
		return "salesLoadPage";
	}
	public String Add(){
		System.out.println("Inside Serila number"+salesOrderDataBean.getSalesList().size());
		SalesOrderDataBean sList=new SalesOrderDataBean();
		sList.setSaleSerialNo(""+(salesOrderDataBean.getSalesList().size()+1));
		sList.setSaleProductName("");
		sList.setSalePrice("");
		sList.setSaleNetAmount("");
		sList.setSaleQuantity("");
		sList.setPflag("1");
		sList.setPflag1("none");
		sList.setQflag("1");
		sList.setQflag1("none");
		salesOrderDataBean.getSalesList().add(sList);
		salesOrderDataBean.setMessage("");
		return "";
		
	}
	
	private boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if(salesOrderDataBean.getSaleDate()==null){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("SalDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
				}
				valid = false;
		}
		if(salesOrderDataBean.getSaleEstDate()==null){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("salEstDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Estimated Delivery Date."));
				}
				valid = false;
		}
		if(salesOrderDataBean.getSaleCategory() == null || salesOrderDataBean.getSaleCategory().equalsIgnoreCase("")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("salCategory").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Category Name."));
				}
				valid = false;
		}		
		if(salesOrderDataBean.getSaleCustomerName() == null || salesOrderDataBean.getSaleCustomerName().equalsIgnoreCase("")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("salCust").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Project Name."));
				}
				valid = false;
		}
		if(salesOrderDataBean.getSaleStaffId() == null || salesOrderDataBean.getSaleStaffId().equalsIgnoreCase("")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("salemp").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Staff ID."));
				}
				valid = false;
		}
		System.out.println("Size"+salesOrderDataBean.getSalesList().size());
		
		if(salesOrderDataBean.getSalesList().size()>0)
		{
			int count=0;
			for(int k=0;k<salesOrderDataBean.getSalesList().size();k++)
			{
				if(salesOrderDataBean.getSalesList().get(k).getSaleProductName().equalsIgnoreCase(""))
				{
					count++;					
				}
				else if(!salesOrderDataBean.getSalesList().get(k).getSaleProductName().equalsIgnoreCase(""))
				{
					if(StringUtils.isBlank(salesOrderDataBean.getSalesList().get(k).getSaleQuantity()))
					{
						if(flag)
						{
							fieldName=CommonValidate.findComponentInRoot("dataMeassage").getClientId(fc);
							fc.addMessage(fieldName, new FacesMessage("Please Enter the Quantity"));
						}
						valid = false;
					}
				}
				if(count==salesOrderDataBean.getSalesList().size())
				{
					if(flag)
					{
						fieldName = CommonValidate.findComponentInRoot("dataMeassage").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Choose atleast One Product"));
					}
					valid = false;
				}				
				System.out.println("inside"+k+" count --> "+count);
			}
		}	
		if(!salesOrderDataBean.getSaleTaxType().equalsIgnoreCase("PurDisco01") && (!salesOrderDataBean.getSaleTaxType().equalsIgnoreCase("PurDisco02")))
		{
			if(flag)
			{
				fieldName = CommonValidate.findComponentInRoot("salTaxType").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose One Tax Type"));
			}
			valid = false;
		}
		System.out.println("dis type - > "+salesOrderDataBean.getDisType()+" amnt -- >"+salesOrderDataBean.getDisamnt());
		if(salesOrderDataBean.getDisType().equals("IDR") || salesOrderDataBean.getDisType().equals("%"))
		{
			if(salesOrderDataBean.getDisamnt().equals(""))
			{
				if(flag)
				{
					fieldName = CommonValidate.findComponentInRoot("disamnt").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter Discount Amount"));
				}
				valid = false;
			}
		}
		if(!salesOrderDataBean.getDisamnt().equals(""))
		{
			if(!salesOrderDataBean.getDisType().equals("IDR") && !salesOrderDataBean.getDisType().equals("%"))
			{
				if(flag)
				{
					fieldName = CommonValidate.findComponentInRoot("distype").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose Discount Type"));
				}
				valid = false;
			}
		}
		return valid;
	}
	/**
	 * reset Method is used to reset the input text box values
	 * 
	 * @return
	 */
	public String reset() {
		System.out.println("Inside Reset Method Calling");
		salesOrderDataBean.setSaleDate(null);
		salesOrderDataBean.setSaleEstDate(null);
		salesOrderDataBean.setSaleCustomerName("");
		salesOrderDataBean.setSaleCrossTotal("");
		salesOrderDataBean.setSaleTotalAmount("");
		salesOrderDataBean.setSaleTaxType("");
		salesOrderDataBean.setSaleCategory("");
		salesOrderDataBean.setSaleStaffId("");
		salesOrderDataBean.setMessage("");
		salesOrderDataBean.setDisamnt("");
		salesOrderDataBean.setDisType("");
		salesOrderDataBean.getSalesList().clear();
		int j=1;
		for(int i=0;i<8;i++){
			SalesOrderDataBean sList=new SalesOrderDataBean();
			sList.setSaleSerialNo(""+j);
			sList.setSaleProductName("");
			sList.setSalePrice("");
			sList.setSaleNetAmount("");
			sList.setSaleQuantity("");
			sList.setPflag("1");
			sList.setPflag1("none");
			sList.setQflag("1");
			sList.setQflag1("none");
			salesOrderDataBean.getSalesList().add(sList);
			j++;
		}
		return "";
		}
	/**
	 * cancel Method is used for redirect to home page
	 * 
	 * @return
	 */

	public String cancel() {
		System.out.println("---- Inside Cancel Method Calling------");
		return "cancelSuccess";

	}
	
//	drop down customer names & employee id
	@PostConstruct
	public void init()
	{
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController)ctx.getBean("controller");
			controller.dropCustomer(salesOrderDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	/*Category change event*/
	public void categoryChange(ValueChangeEvent vv)
	{
		System.out.println("category -- >> "+vv.getNewValue());
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			salesOrderDataBean.setMessage("");
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController)ctx.getBean("controller");
			salesOrderDataBean.setSaleCategory(vv.getNewValue().toString());
			controller.categoryChange(salesOrderDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*Product change event*/
	public void productChange(ValueChangeEvent uv)
	{
		System.out.println("product -- >. "+uv.getNewValue());
		String no="";
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{			
			no=uv.getComponent().getAttributes().get("sno").toString();
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController)ctx.getBean("controller");
			salesOrderDataBean.setSaleProductName(uv.getNewValue().toString());
			controller.productChange(salesOrderDataBean);
			SalesOrderDataBean ss=new SalesOrderDataBean();
			ss.setSalePrice(salesOrderDataBean.getSalePrice());
			ss.setSaleSerialNo(no);
			ss.setPflag("none");
			ss.setPflag1("1");
			ss.setQflag("1");
			ss.setQflag1("none");
			ss.setSaleProductName(uv.getNewValue().toString());
			salesOrderDataBean.getSalesList().set(Integer.parseInt(no)-1, ss);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*Quantity change event*/
	public void quantityChange(ValueChangeEvent vu)
	{
		System.out.println("quantity -- >. "+vu.getNewValue());
		String no="";
		ApplicationContext ctx=null;
		LiusenController controller=null;
		BigDecimal netamount=BigDecimal.valueOf(0);
		BigDecimal total=BigDecimal.valueOf(0);
		try
		{
			no=vu.getComponent().getAttributes().get("sno").toString();
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController)ctx.getBean("controller");
			salesOrderDataBean.setSaleQuantity(vu.getNewValue().toString());
			salesOrderDataBean.setSaleProductName(vu.getComponent().getAttributes().get("name").toString());
			if(salesOrderDataBean.getSaleCategory().equals("SALC001"))
			{
				System.out.println("category -- >. "+salesOrderDataBean.getSaleCategory());
				controller.quantityChange(salesOrderDataBean);
			}
			SalesOrderDataBean ss=new SalesOrderDataBean();
			ss.setSalePrice(vu.getComponent().getAttributes().get("price").toString());
			ss.setSaleSerialNo(no);
			ss.setPflag("none");
			ss.setPflag1("1");
			ss.setQflag("none");
			ss.setQflag1("1");
			ss.setSaleQuantity(vu.getNewValue().toString());
			ss.setSaleProductName(vu.getComponent().getAttributes().get("name").toString());
			System.out.println("price -- > "+new BigDecimal(salesOrderDataBean.getSalesList().get(Integer.parseInt(no)-1).getSalePrice())+" quantity -- >"+new BigDecimal(vu.getNewValue().toString()));
			netamount=new BigDecimal(salesOrderDataBean.getSalesList().get(Integer.parseInt(no)-1).getSalePrice()).multiply(new BigDecimal(vu.getNewValue().toString()));
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
			ss.setSalePrice("");
			ss.setSaleSerialNo(no);
			ss.setPflag("1");
			ss.setPflag1("none");
			ss.setQflag("1");
			ss.setQflag1("none");
			ss.setSaleQuantity("");
			ss.setSaleProductName("");
			salesOrderDataBean.getSalesList().set(Integer.parseInt(no)-1, ss);
		}
	}
	
	/*private boolean validate1(boolean flag)
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();		
		if(salesOrderDataBean.getBatch().size()<0)
		{
			if(flag)
			{
				fieldName=CommonValidate.findComponentInRoot("dataMeassage").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("No Stock in Warehouse"));
			}
			valid = false;
		}
		else if(salesOrderDataBean.getBatch().size()>0)
		{
			if(Integer.parseInt(salesOrderDataBean.getSaleQuantity())>Integer.parseInt(salesOrderDataBean.getBarcode().get(0).getStockIn()))
			{
				if(flag)
				{
					fieldName=CommonValidate.findComponentInRoot("dataMeassage").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Only "+salesOrderDataBean.getBarcode().get(0).getStockIn()+" stock in Warehouse"));
				}
				valid = false;
			}	
		}
		return valid;
	}*/	
	
	/*Edit the data table record*/
	public String edit() 
	{
		try
		{			
			System.out.println("inside data edit ");
			SalesOrderDataBean ss=new SalesOrderDataBean();
			ss.setSalePrice("");
			ss.setSaleSerialNo(salesOrderDataBean.getSaleSerialNo());
			ss.setPflag("1");
			ss.setPflag1("none");
			ss.setQflag("1");
			ss.setQflag1("none");
			ss.setSaleQuantity("");
			ss.setSaleProductName("");
			BigDecimal temp = BigDecimal.valueOf(0);
			if (!salesOrderDataBean.getSaleNetAmount().equalsIgnoreCase("")) 
			{
				temp = temp.add(new BigDecimal(salesOrderDataBean.getSaleCrossTotal()).subtract(new BigDecimal(salesOrderDataBean.getSaleNetAmount())));
			}
			else
			{
				temp = temp.add(new BigDecimal(salesOrderDataBean.getSaleCrossTotal()));
			}
			salesOrderDataBean.getSalesList().set(Integer.parseInt(salesOrderDataBean.getSaleSerialNo())-1, ss);
			System.out.println("total -- >"+temp);
			salesOrderDataBean.setSaleCrossTotal("" + temp);			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "";
	}
	
//	Tax type calculation
	public void taxTypeChange(ValueChangeEvent v)
	{
		System.out.println("tax type -- >. "+v.getNewValue());
		BigDecimal taxamnt=BigDecimal.valueOf(0), cross=BigDecimal.valueOf(0);
		BigDecimal totalamnt=BigDecimal.valueOf(0),cross1=BigDecimal.valueOf(0);
		cross=new BigDecimal(salesOrderDataBean.getSaleCrossTotal());
		if(salesOrderDataBean.getDisType().equals(""))
		{
			cross1=cross;
		}
		else if(salesOrderDataBean.getDisType().equals("IDR"))
		{
			cross1=cross.subtract(new BigDecimal(salesOrderDataBean.getDisamnt()));
		}
		else if(salesOrderDataBean.getDisType().equals("%"))
		{
			BigDecimal amnt=BigDecimal.valueOf(0);
			amnt=(cross.multiply(new BigDecimal(salesOrderDataBean.getDisamnt()))).divide(BigDecimal.valueOf(100));
			cross1=cross.subtract(amnt);
		}
		if(v.getNewValue().equals("PurDisco01"))
		{
			salesOrderDataBean.setSaleTotalAmount(""+cross1);
		}
		else if(v.getNewValue().equals("PurDisco02"))
		{
			taxamnt=(cross1.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
			System.out.println("tax amnt -- >. "+taxamnt);
			totalamnt=cross1.add(taxamnt);
			System.out.println("total amount -- > "+totalamnt);
			salesOrderDataBean.setSaleTotalAmount(""+totalamnt);
		}
		
	}
	
	public void discountChange(ValueChangeEvent v)
	{
		System.out.println("discount amnt -- >. "+v.getNewValue());
		BigDecimal taxamnt=BigDecimal.valueOf(0), cross=BigDecimal.valueOf(0);
		BigDecimal totalamnt=BigDecimal.valueOf(0),cross1=BigDecimal.valueOf(0);
		cross=new BigDecimal(salesOrderDataBean.getSaleCrossTotal());
		if(salesOrderDataBean.getSaleTaxType().equals(""))
		{
			cross1=cross;
		}
		else if(salesOrderDataBean.getSaleTaxType().equals("PurDisco01"))
		{
			cross1=cross;
		}
		else if(salesOrderDataBean.getSaleTaxType().equals("PurDisco02"))
		{
			taxamnt=(cross.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
			System.out.println("tax amnt -- >. "+taxamnt);
			cross1=cross.add(taxamnt);
		}
		if(salesOrderDataBean.getDisType().equals("IDR"))
		{
			totalamnt=cross1.subtract(new BigDecimal(v.getNewValue().toString()));
			salesOrderDataBean.setSaleTotalAmount(""+totalamnt);
		}
		else if(salesOrderDataBean.getDisType().equals("%"))
		{
			BigDecimal amnt=BigDecimal.valueOf(0);
			amnt=(cross1.multiply(new BigDecimal(v.getNewValue().toString()))).divide(BigDecimal.valueOf(100));
			totalamnt=cross1.subtract(amnt);
			salesOrderDataBean.setSaleTotalAmount(""+totalamnt);
		}		
	}
	/*Sales Order Insert*/
	public String submit()
	{
		System.out.println(" sales order insert in mb ");
		dialog=false;
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{			
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController)ctx.getBean("controller");
			if (validate(true)) 
			{
				System.out.println("After Validation inside Submit method");
				controller.salesInsertion(salesOrderDataBean);
				System.out.println("size - > "+salesOrderDataBean.getSalesList().size());
				int cc=1;
				for (int i = 0; i <salesOrderDataBean.getSalesList().size(); i++) 
				{
					System.out.println("product -- > "+salesOrderDataBean.getSalesList().get(i).getSaleProductName());
					if(!salesOrderDataBean.getSalesList().get(i).getSaleProductName().equals(""))
					{
						SalesOrderDataBean ss=new SalesOrderDataBean();
						ss.setSaleSerialNo(""+cc);
						System.out.println("no - > "+cc);
						ss.setSaleProductName(salesOrderDataBean.getSalesList().get(i).getSaleProductName());
						System.out.println("product - > "+salesOrderDataBean.getSalesList().get(i).getSaleProductName());
						ss.setSalePrice(salesOrderDataBean.getSalesList().get(i).getSalePrice());
						System.out.println("price - > "+salesOrderDataBean.getSalesList().get(i).getSalePrice());
						ss.setSaleQuantity(salesOrderDataBean.getSalesList().get(i).getSaleQuantity());
						System.out.println("quantity - > "+salesOrderDataBean.getSalesList().get(i).getSaleQuantity());
						ss.setSaleNetAmount(salesOrderDataBean.getSalesList().get(i).getSaleNetAmount());
						System.out.println("net amount - > "+salesOrderDataBean.getSalesList().get(i).getSaleNetAmount());
						list.add(ss);
						salesOrderDataBean.setSalist(list);
						cc++;
					}
					else
					{
						System.out.println("product empty - >");
					}
				}
				System.out.println("invoice size -- > "+salesOrderDataBean.getSalist().size() +" c -- > "+cc);
				return "salesOrderConfirmation";
			}
			else
			{
				System.out.println("validate false -- > ");
			}
			return "";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
//	go to sales order from invoice form
	public String viewback()
	{
		salesOrderDataBean.setMessage("");
		salesPageLoad();
		return "salesorderback";
	}
}
