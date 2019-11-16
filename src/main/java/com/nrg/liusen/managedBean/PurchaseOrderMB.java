package com.nrg.liusen.managedBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

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
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="purchaseOrderMB")
@RequestScoped
public class PurchaseOrderMB {

	PurchaseOrederDataBean purchaseOrederDataBean=new PurchaseOrederDataBean();
	

	/**
	 * @return the purchaseOrederDataBean
	 */
	public PurchaseOrederDataBean getPurchaseOrederDataBean() {
		return purchaseOrederDataBean;
	}

	/**
	 * @param purchaseOrederDataBean the purchaseOrederDataBean to set
	 */
	public void setPurchaseOrederDataBean(
			PurchaseOrederDataBean purchaseOrederDataBean) {
		this.purchaseOrederDataBean = purchaseOrederDataBean;
	}
	
	/**
	 * purchasePageLoad Method is used for redirect to purchase registration form
	 * page
	 * 
	 * @return success go to vendor registration form page
	 */
	public String purchasePageLoad() 
	{
		System.out.println("Inside purchasePageLoad Method Calling");
		try
		{
			purchaseOrederDataBean.getPurchaseList().clear();
			purchaseOrederDataBean.setPurdate(null);
			purchaseOrederDataBean.setPurEstDate(null);
			purchaseOrederDataBean.setPurCategory("");
			purchaseOrederDataBean.setPurStaffId("");
			purchaseOrederDataBean.setPurProductName("");
			purchaseOrederDataBean.setPurprice("");
			purchaseOrederDataBean.setPurQuantity("");
			purchaseOrederDataBean.setPurCrossTotal("");
			purchaseOrederDataBean.setPurDiscAmmount("");
			purchaseOrederDataBean.setPurDiscType("");
			purchaseOrederDataBean.setPurStaffName("");
			purchaseOrederDataBean.setPurProject("");
			purchaseOrederDataBean.setPurVendor("");
			purchaseOrederDataBean.setTaxType("");
			purchaseOrederDataBean.setPurTotalPrice("");
			/*purchaseOrederDataBean.getPurchaseList().clear();
			*/vendorList=new ArrayList();
			productNameList=new ArrayList();
			projectList=new ArrayList();
			myValid=true;
			int j=1;
			for(int i=0;i<8;i++){
				PurchaseOrederDataBean pList=new PurchaseOrederDataBean();
				pList.setPurserialno(""+j);
				pList.setPurQuantity("");
				pList.setPurprice("");
				pList.setPurProductName("");
				pList.setPurNetAmount("");
				purchaseOrederDataBean.getPurchaseList().add(pList);
				j++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return "purchaseViewLoadPage";
		
	}
	
	public String Add(){
		System.out.println("Inside Serila number"+purchaseOrederDataBean.getPurchaseList().size());
		PurchaseOrederDataBean pList=new PurchaseOrederDataBean();
		pList.setPurserialno(""+(purchaseOrederDataBean.getPurchaseList().size()+1));
		pList.setPurQuantity("");
		pList.setPurprice("");
		pList.setPurProductName("");
		pList.setPurNetAmount("");
		purchaseOrederDataBean.getPurchaseList().add(pList);
		return "";
		
	}
	/**
	 * submit Method is used to get the values from UI to Controller
	 * 
	 * @return submitSuccess go to controller
	 * @return submitFailure redirect same page
	 */
	public String submit() {
		try {
			System.out.println("----------Inside submit Method Calling-----");
			if (validate(true)) {
				System.out.println("After Validation inside Submit method");

				return "";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
}

	private boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if(purchaseOrederDataBean.getPurdate()==null){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("purDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
				}
				valid = false;
		}
		if(purchaseOrederDataBean.getPurEstDate()==null){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("purEstDate").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Estimated Delivery Date."));
				}
				valid = false;
		}
		if(purchaseOrederDataBean.getPurCategory().equalsIgnoreCase("")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("pur_Category").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Category Name."));
				}
				valid = false;
		}
		
		if(purchaseOrederDataBean.getPurVendor().equalsIgnoreCase("")){
			if(flag){
				
				fieldName = CommonValidate.findComponentInRoot("purVendor").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Vendor Name."));
				}
				valid = false;
		}
		
		if(purchaseOrederDataBean.getPurStaffName().equalsIgnoreCase("")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("pur_emp").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Employee ID."));
				}
				valid = false;
		}
		/*if(purchaseOrederDataBean.getPurStaffId().equalsIgnoreCase("PUREMP00")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("pur_emp").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Employee Name."));
				}
				valid = false;
		}
		if(purchaseOrederDataBean.getPurchaseList().size()>0){
			System.out.println("1");
			for(int k=0;k<purchaseOrederDataBean.getPurchaseList().size();k++){
				System.out.println("1"+purchaseOrederDataBean.getPurchaseList().get(k).getPurserialno());
				System.out.println("-----"+purchaseOrederDataBean.getPurchaseList().get(k).getPurProductName());
				System.out.println("1"+purchaseOrederDataBean.getPurchaseList().get(k).getPurQuantity());}
		}*/
		
		if(!purchaseOrederDataBean.getPurDiscAmmount().equalsIgnoreCase("")  &&  purchaseOrederDataBean.getPurDiscType().equalsIgnoreCase(""))
	    {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("dataMeassage").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Select the Discount Type."));
			}
			valid = false;
	    }
		int c1=0;
		if(purchaseOrederDataBean.getPurchaseList().size()>0){
			for(int k=0;k<purchaseOrederDataBean.getPurchaseList().size();k++){

				
				
				if(purchaseOrederDataBean.getPurchaseList().get(k).getPurProductName().equalsIgnoreCase(""))
				{
					c1=c1+1;
				}
				else if(!purchaseOrederDataBean.getPurchaseList().get(k).getPurProductName().equalsIgnoreCase(""))
				{
					if(purchaseOrederDataBean.getPurchaseList().get(k).getPurQuantity().equalsIgnoreCase(""))
					{
						if(flag){
						fieldName = CommonValidate.findComponentInRoot("dataMeassage").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage("Please Enter the Quantity. "));
						}
						valid = false;
					}
					if(!purchaseOrederDataBean.getPurchaseList().get(k).getPurQuantity().matches("^\\d+(\\.\\d+)*$"))
					{
						if(flag){
						fieldName = CommonValidate.findComponentInRoot("dataMeassage").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(" Quantity should be in Numbers. "));
						}
						valid = false;
					}
				}
				if(c1==purchaseOrederDataBean.getPurchaseList().size())
				{
					if(flag){
					fieldName = CommonValidate.findComponentInRoot("dataMeassage").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose atleast One Product."));
					}
					valid = false;
				}
				
				
				
				
				
				
				
				
				
				
			if(purchaseOrederDataBean.getPurchaseList().get(k).getPurProductName().equalsIgnoreCase("P001")){
			if(StringUtils.isBlank(purchaseOrederDataBean.getPurchaseList().get(k).getPurQuantity())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("dataMeassage").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose atleast One Product"));
				}
				valid = false;
					}
				}
				
		}
		}
		
		return valid;
	}
	
	
	
	private boolean validate1(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		
					if(!purchaseOrederDataBean.getPurDiscAmmount().matches("^\\d+(\\.\\d+)*$"))
					{
						if(flag)
						{
						fieldName = CommonValidate.findComponentInRoot("dataMeassage").getClientId(fc);
						fc.addMessage(fieldName, new FacesMessage(" Discount should be in Numbers. "));
						}
						valid = false;
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
		purchaseOrederDataBean.setPurCrossTotal("");
		purchaseOrederDataBean.setPurdate(null);
		purchaseOrederDataBean.setPurDiscAmmount("");
		purchaseOrederDataBean.setPurDiscType("");
		purchaseOrederDataBean.setPurEstDate(null);
		purchaseOrederDataBean.setPurTotalPrice("");
		purchaseOrederDataBean.setPurVendor("");
		purchaseOrederDataBean.setPurCategory("");
		purchaseOrederDataBean.setPurStaffName("");
		purchaseOrederDataBean.setTaxType("");
		purchaseOrederDataBean.getPurchaseList().clear();
		int j=1;
		for(int i=0;i<8;i++){
			PurchaseOrederDataBean pList=new PurchaseOrederDataBean();
			pList.setPurserialno(""+j);
			pList.setPurQuantity("");
			pList.setPurprice("");
			pList.setPurProductName("");
			pList.setPurNetAmount("");
			purchaseOrederDataBean.getPurchaseList().add(pList);
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
	
	
	
	
	
	
	
	
	
	
	
	
	LiusenController controller = null;
	private boolean myValid = true; 
	private List<String> productNameList=null;
	private List<String> projectList=null;
	private List<String> vendorList=null;
	public boolean isMyValid() {
		return myValid;
	}
	public void setMyValid(boolean myValid) {
		this.myValid = myValid;
	}

	public List<String> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<String> projectList) {
		this.projectList = projectList;
	}
	public List<String> getVendorList() {
		return vendorList;
	}
	public void setVendorList(List<String> vendorList) {
		this.vendorList = vendorList;
	}
	public List<String> getProductNameList() {
		return productNameList;
	}
	public void setProductNameList(List<String> productNameList) {
		this.productNameList = productNameList;
	}
	
	
	
	
	/*kasthuri 27-11-2015 @@ retrieve vendorlist based on category*/	
	public void categoryChange(ValueChangeEvent c)
	{
		System.out.println("-->> cat name "+c.getNewValue().toString());
		purchaseOrederDataBean.setPurCategory(c.getNewValue().toString());
		try
		{
			productNameList=new ArrayList();
			projectList=new ArrayList();
			/*purchaseOrederDataBean.setPurProject("none");*/
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			List<String> vendorName=controller.vendorNameList(purchaseOrederDataBean);
			System.out.println("name"+vendorName);
			if(vendorName.size()>0)
			{
				vendorList=new ArrayList<String>();
				vendorList.addAll(vendorName);
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*kasthuri 30-11-2015 @@ set employee name*/	
	public void employeeChange(ValueChangeEvent e)
	{
		System.out.println("-->> emp name "+e.getNewValue().toString());
		purchaseOrederDataBean.setPurStaffName(e.getNewValue().toString());
		
	}
	
	/*kasthuri 27-11-2015 @@ retrieve productlist based on vendor*/
	public void  vendorChange(ValueChangeEvent v)
	{
		try
		{
			productNameList=new ArrayList();
			projectList=new ArrayList();
			System.out.println("-->> ven name "+v.getNewValue().toString());
			System.out.println("-->> cat type "+purchaseOrederDataBean.getPurCategory());
			purchaseOrederDataBean.setPurVendor(v.getNewValue().toString());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			List<String> productName=controller.productNameList(purchaseOrederDataBean);
			System.out.println("-->> name "+productName);
			if(productName.size()>0)
			{
				productNameList=new ArrayList<String>();
				productNameList.addAll(productName);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*kasthuri 30-11-15 @@ retrieve discount type*/
	public void discountChange(ValueChangeEvent d)
	{
		
		String value ; 
		FacesContext context = FacesContext.getCurrentInstance();  
	    value = (String) d.getNewValue() ;
	    System.out.println("-->> disc type "+value);
	    purchaseOrederDataBean.setPurDiscType(value);
	}
	
	/*kasthuri 30-11-15 @@ retrieve discount amount*/
	public void discountAmount(ValueChangeEvent d)
	{
		BigDecimal b1=BigDecimal.valueOf(0);
		BigDecimal b2=BigDecimal.valueOf(0);
		BigDecimal b3=BigDecimal.valueOf(0);
		try
	    {
			String value ; 
			value = d.getNewValue().toString();
			
		    System.out.println("-->> disc amount "+value);
		    purchaseOrederDataBean.setPurDiscAmmount(value);
		    if(validate1(true))
		    {
		    if(!purchaseOrederDataBean.getPurCrossTotal().equalsIgnoreCase("")  &&  !purchaseOrederDataBean.getPurDiscType().equalsIgnoreCase(""))
		    {
		    	
		    if(purchaseOrederDataBean.getPurDiscType().equalsIgnoreCase("IDR"))	
		    {
		    	b1=(new BigDecimal(value));
		    }
		    else if(purchaseOrederDataBean.getPurDiscType().equalsIgnoreCase("%"))
		    {
		    	
		    	b1=(new BigDecimal(purchaseOrederDataBean.getPurCrossTotal()).multiply(new BigDecimal(value))).divide(BigDecimal.valueOf(100));
		    }
		    }
		    if(!purchaseOrederDataBean.getTaxType().equalsIgnoreCase(""))
		    {
		    	if(purchaseOrederDataBean.getTaxType().equalsIgnoreCase("No Tax"))
			    {
			    	b2=BigDecimal.valueOf(0);
			    }
			    else if(purchaseOrederDataBean.getTaxType().equalsIgnoreCase("10% Tax"))
			    {
			    	b2=(new BigDecimal(purchaseOrederDataBean.getPurCrossTotal()).multiply(BigDecimal.valueOf(10))).divide(BigDecimal.valueOf(100));
			    }
		    }
		    b3=new BigDecimal(purchaseOrederDataBean.getPurCrossTotal()).subtract(b1).add(b2);
		    System.out.println("-->> b1 "+b1);
		    System.out.println("-->> b2 "+b2);
		    System.out.println("-->> b3 "+b3);
		    purchaseOrederDataBean.setPurTotalPrice(""+b3);
		    }
	    }
	    catch(NullPointerException n)
	    {
	    	n.printStackTrace();
	    }
		
	}
	
	/*kasthuri 30-11-15 @@ retrieve price of the product*/
	public void productPrice(ValueChangeEvent p)
	{
		try
		{
			String prod=p.getNewValue().toString();
			String serialNo="";
			System.out.println("-->> prod "+prod);
			serialNo=p.getComponent().getAttributes().get("serial").toString();
			purchaseOrederDataBean.setPurProductName(prod);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.productPrice(purchaseOrederDataBean);
			PurchaseOrederDataBean homeMB=new PurchaseOrederDataBean();
			homeMB.setPurprice(purchaseOrederDataBean.getPurprice());
			homeMB.setPurProductName(prod);
			homeMB.setPurserialno(serialNo);
			homeMB.setUnit(purchaseOrederDataBean.getUnit());
			homeMB.setProdF1("none");
			homeMB.setProdF2("1");
			purchaseOrederDataBean.getPurchaseList().set((Integer.parseInt(serialNo)-1), homeMB);
			System.out.println("-->> unit "+purchaseOrederDataBean.getUnit());
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*kasthuri 30-11-15 @@ retrieve nettotal of the product*/
	public void productQuan(ValueChangeEvent q)
	{
		try
		{
			String quan=q.getNewValue().toString();
			String serialNo="";
			String prod="";
			String price="";
			String unit="";
			System.out.println("-->> quan "+quan);
			BigDecimal net=BigDecimal.valueOf(0);
			BigDecimal cross=BigDecimal.valueOf(0);
			serialNo=q.getComponent().getAttributes().get("serial").toString();
			prod=q.getComponent().getAttributes().get("product").toString();
			price=q.getComponent().getAttributes().get("price").toString();
			unit=q.getComponent().getAttributes().get("unit").toString();
			net=new BigDecimal(quan).multiply(new BigDecimal(price));
			purchaseOrederDataBean.setPurQuantity(quan);
			PurchaseOrederDataBean homeMB=new PurchaseOrederDataBean();
			homeMB.setPurprice(price);
			homeMB.setPurProductName(prod);
			homeMB.setPurserialno(serialNo);
			homeMB.setPurQuantity(quan);
			homeMB.setUnit(unit);
			homeMB.setPurNetAmount(""+net);
			homeMB.setProdF1("none");
			homeMB.setProdF2("1");
			homeMB.setQuanF1("none");
			homeMB.setQuanF2("1");
			purchaseOrederDataBean.getPurchaseList().set((Integer.parseInt(serialNo)-1), homeMB);
			
			
			for(int i=0;i<purchaseOrederDataBean.getPurchaseList().size();i++)
			{
				if(!purchaseOrederDataBean.getPurchaseList().get(i).getPurProductName().equalsIgnoreCase(""))
				{
				System.out.println("-->> prod != null "+purchaseOrederDataBean.getPurchaseList().get(i).getPurProductName());	
				cross=cross.add(new BigDecimal(purchaseOrederDataBean.getPurchaseList().get(i).getPurNetAmount()));
				}
			}
			purchaseOrederDataBean.setPurCrossTotal(""+cross);
			purchaseOrederDataBean.setPurTotalPrice(""+cross);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*kasthuri 01-12-15 @@ purchase insertion*/
	public String purchaseSave()
	{
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			if(validate(true)) 
			{
			if(purchaseOrederDataBean.getPurDiscType().equalsIgnoreCase(""))	
			{
				purchaseOrederDataBean.setPurDiscType("IDR");
				purchaseOrederDataBean.setPurDiscAmmount("0");
			}
			else if(!purchaseOrederDataBean.getPurDiscType().equalsIgnoreCase("") && purchaseOrederDataBean.getPurDiscAmmount().equalsIgnoreCase("") )	
			{
				purchaseOrederDataBean.setPurDiscType("IDR");
				purchaseOrederDataBean.setPurDiscAmmount("0");
			}
			if(purchaseOrederDataBean.getTaxType().equalsIgnoreCase(""))	
			{
				purchaseOrederDataBean.setTaxType("No Tax");
			}
			ArrayList<PurchaseOrederDataBean> List=new ArrayList<PurchaseOrederDataBean>();
			for(int i=0;i<purchaseOrederDataBean.getPurchaseList().size();i++)
			{
				if(!purchaseOrederDataBean.getPurchaseList().get(i).getPurProductName().equalsIgnoreCase(""))
				{
					PurchaseOrederDataBean bean=new PurchaseOrederDataBean();
					bean.setPurserialno(purchaseOrederDataBean.getPurchaseList().get(i).getPurserialno());
					bean.setPurProductName(purchaseOrederDataBean.getPurchaseList().get(i).getPurProductName());
					bean.setUnitPrice(purchaseOrederDataBean.getPurchaseList().get(i).getPurprice());
					bean.setUnit(purchaseOrederDataBean.getPurchaseList().get(i).getUnit());
					bean.setPurQuantity(purchaseOrederDataBean.getPurchaseList().get(i).getPurQuantity());
					bean.setPurNetAmount(""+new BigDecimal(purchaseOrederDataBean.getPurchaseList().get(i).getPurQuantity()).multiply(new BigDecimal(purchaseOrederDataBean.getPurchaseList().get(i).getPurprice())));
					List.add(bean);
					purchaseOrederDataBean.setReferenceList(List);
				}
			}
			
			
			
			controller.purchaseInsertion1(purchaseOrederDataBean);
			controller.purchaseInsertion2(purchaseOrederDataBean);
			return "purchaseOderConfirm";
			}
			else
			{
				System.out.println("-->>else  ");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	/*kasthuri 01-12-15 @@ purchase insertion*/
	public String purchaseSaveConfirm()
	{
		try
		{
			System.out.println("-->>mb purchaseInsertion ");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			myValid=false;
			return "";
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	/*kasthuri 01-12-15 @@ clear the purchase order record*/
	public String edit()
	{
		System.out.println(" -->> serial no "+purchaseOrederDataBean.getPurserialno());
		myValid=true;
		String serialNo=purchaseOrederDataBean.getPurserialno();
		String netAmnt=purchaseOrederDataBean.getPurNetAmount();
		PurchaseOrederDataBean homeMB=new PurchaseOrederDataBean();
		homeMB.setPurprice("");
		homeMB.setPurProductName("");
		homeMB.setPurserialno(serialNo);
		homeMB.setPurQuantity("");
		homeMB.setUnit("");
		homeMB.setPurNetAmount("");
		homeMB.setProdF1("1");
		homeMB.setProdF2("none");
		homeMB.setQuanF1("1");
		homeMB.setQuanF2("none");
		purchaseOrederDataBean.getPurchaseList().set((Integer.parseInt(serialNo)-1), homeMB);
		BigDecimal b=BigDecimal.valueOf(0);
		b= new BigDecimal(purchaseOrederDataBean.getPurCrossTotal()).subtract(new BigDecimal(netAmnt));
		purchaseOrederDataBean.setPurCrossTotal(""+b);
		purchaseOrederDataBean.setPurTotalPrice(""+b);
		purchaseOrederDataBean.setTaxType("");
		purchaseOrederDataBean.setPurDiscAmmount("");
		purchaseOrederDataBean.setPurDiscType("");
		return "";
	}
	
	/*kasthuri 08-12-15 @@ retrieve tax type*/
	public void taxChange(ValueChangeEvent d)
	{
		String value ; 
		FacesContext context = FacesContext.getCurrentInstance();  
	    value = (String) d.getNewValue() ;
	    System.out.println("-->> tax type "+value);
	    purchaseOrederDataBean.setTaxType(value);
	    if(!purchaseOrederDataBean.getPurCrossTotal().equalsIgnoreCase(""))
	    {
	    		BigDecimal b1=BigDecimal.valueOf(0);
				BigDecimal b2=BigDecimal.valueOf(0);
				BigDecimal b3=BigDecimal.valueOf(0);
			    
			    System.out.println("-->> disc type "+purchaseOrederDataBean.getPurDiscType());
			    System.out.println("-->> disc amount "+purchaseOrederDataBean.getPurDiscAmmount());
			    System.out.println("-->> tax type "+purchaseOrederDataBean.getTaxType());
			    if(!purchaseOrederDataBean.getTaxType().equalsIgnoreCase(""))
			    {
		    	if(purchaseOrederDataBean.getTaxType().equalsIgnoreCase("No Tax"))	
			    {
			    	b2=BigDecimal.valueOf(0);
			    }
			    else if(purchaseOrederDataBean.getTaxType().equalsIgnoreCase("10% Tax"))
			    {
			    	b2=(new BigDecimal(purchaseOrederDataBean.getPurCrossTotal()).multiply(BigDecimal.valueOf(10))).divide(BigDecimal.valueOf(100));
			    }
			    }
			    if(!purchaseOrederDataBean.getPurDiscAmmount().equalsIgnoreCase("")  &&  !purchaseOrederDataBean.getPurDiscType().equalsIgnoreCase(""))
			    {
			    	
			    if(purchaseOrederDataBean.getPurDiscType().equalsIgnoreCase("IDR"))	
			    {
			    	b1=(new BigDecimal(purchaseOrederDataBean.getPurDiscAmmount()));
			    }
			    else if(purchaseOrederDataBean.getPurDiscType().equalsIgnoreCase("%"))
			    {
			    	b1=(new BigDecimal(purchaseOrederDataBean.getPurCrossTotal()).multiply(new BigDecimal(purchaseOrederDataBean.getPurDiscAmmount()))).divide(BigDecimal.valueOf(100));
			    }
			    }
			    
			    b3=new BigDecimal(purchaseOrederDataBean.getPurCrossTotal()).subtract(b1).add(b2);
			    System.out.println("-->> b1 "+b1);
			    System.out.println("-->> b2 "+b2);
			    System.out.println("-->> b3 "+b3);
			    purchaseOrederDataBean.setPurTotalPrice(""+b3);
			    
	    }
	}
}
