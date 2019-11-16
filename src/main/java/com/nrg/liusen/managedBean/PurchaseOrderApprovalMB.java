package com.nrg.liusen.managedBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.util.CommonValidate;

@ManagedBean(name="purchaseOrderApprovalMB")
@RequestScoped
public class PurchaseOrderApprovalMB {

	PurchaseOrederDataBean purchaseOrederDataBean=new PurchaseOrederDataBean();
	public boolean flag=false;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

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
	
		
	
	LiusenController controller = null;
	private boolean myValid = true; 
	public boolean isMyValid() {
		return myValid;
	}
	public void setMyValid(boolean myValid) {
		this.myValid = myValid;
	}

	
	
	/*kasthuri 04-12-15 @@ purchase order approval count*/
	public String purchaseApproval()
	{
	
		try
		{
			System.out.println("-->> mb");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.purchaseApproval(purchaseOrederDataBean);
			System.out.println("-->> mb counts c1/c2/c3/c4 "+purchaseOrederDataBean.count1+" / "+purchaseOrederDataBean.count2+" / "+purchaseOrederDataBean.count3+" / "+purchaseOrederDataBean.count4);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	
	
	
	
	/*kasthuri 04-12-15 @@ purchase order details for GM approval */
	public String poDetailsForGMApproval()
	{
	
		try
		{
			System.out.println("-->> mb");
			purchaseOrederDataBean.getPurchaseViewList().clear();
			purchaseOrederDataBean.setStatus("GM");
			myValid=true;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.poDetailsForApproval(purchaseOrederDataBean);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri 04-12-15 @@ purchase order details for MM approval */
	public String poDetailsForMMApproval()
	{		
		try
		{
			System.out.println("-->> mb");
			purchaseOrederDataBean.getPurchaseViewList().clear();
			purchaseOrederDataBean.setStatus("MM");
			myValid=true;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.poDetailsForApproval(purchaseOrederDataBean);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri 07-12-15 @@ validate */
	private boolean validate1(boolean flag3) 
	{
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		System.out.println("-->> type "+purchaseOrederDataBean.getAppType());
		System.out.println("-->> reason "+purchaseOrederDataBean.getReason());
		if ((purchaseOrederDataBean.getAppType().equalsIgnoreCase("")))
		{
			if (flag3) 
			{
				fieldName = CommonValidate.findComponentInRoot("purView").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Status."));

			}
			valid = false;
		}
		
		if ((purchaseOrederDataBean.getAppType().equalsIgnoreCase("Reject")) && purchaseOrederDataBean.getReason().equalsIgnoreCase(""))
		{
			if (flag3) 
			{
				fieldName = CommonValidate.findComponentInRoot("purView").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Reason for Rejection."));
			}
			valid = false;
		}
		
		return valid;
	}
	
	/*kasthuri 07-12-15 @@ po GM approval insertion*/
	public String poGMApproval()
	{
	
		try
		{
				System.out.println("-->> mb "+purchaseOrederDataBean.purOrderNo);
				purchaseOrederDataBean.setStatus("GM");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				if(validate1(true))
				{
					controller.poGMApproval(purchaseOrederDataBean);
					myValid=false;
				}
				else
				{
					System.out.println("-->> validation fails");
				}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	/*kasthuri 07-12-15 @@ po MM approval insertion*/
	public String poMMApproval()
	{
	
		try
		{
				System.out.println("-->> mb "+purchaseOrederDataBean.purOrderNo);
				purchaseOrederDataBean.setStatus("MM");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				if(validate1(true))
				{
					controller.poMMApproval(purchaseOrederDataBean);
					myValid=false;
				}
				else
				{
					System.out.println("-->> validation fails");
				}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
	
	/*kasthuri 07-11-2015 @@ approval type*/	
	public void approvalType(ValueChangeEvent c)
	{
		try
		{
			String value ; 
			String orderNo="";
			String serialNo="",tot="";
			String d;
			Date dd;
			FacesContext context = FacesContext.getCurrentInstance();  
		    value = (String) c.getNewValue() ;
		    orderNo=c.getComponent().getAttributes().get("orderNo").toString();
			serialNo=c.getComponent().getAttributes().get("serial").toString();
			tot=c.getComponent().getAttributes().get("tot").toString();
			d=c.getComponent().getAttributes().get("date").toString();
		    System.out.println("-->> type "+value);
			dd=df.parse(d);
		    PurchaseOrederDataBean homeMB=new PurchaseOrederDataBean();
			homeMB.setPurOrderNo(orderNo);
		    homeMB.setAppType(value);
		    homeMB.setPurCrossTotal(tot);
		    homeMB.setPurserialno(serialNo);
		    homeMB.setPurdate(dd);
		    purchaseOrederDataBean.getPurchaseViewList().set((Integer.parseInt(serialNo)-1), homeMB);
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*kasthuri 07-11-2015 @@ reason */	
	public void Reason(ValueChangeEvent c)
	{
		try
		{
			System.out.println("-->> reason "+c.getNewValue().toString());
			String orderNo="";
			String serialNo="",tot="",type="";
			String d;
			Date dd;
			orderNo=c.getComponent().getAttributes().get("orderNo").toString();
			serialNo=c.getComponent().getAttributes().get("serial").toString();
			tot=c.getComponent().getAttributes().get("tot").toString();
			type=c.getComponent().getAttributes().get("apptype").toString();
			d=c.getComponent().getAttributes().get("date").toString();
			dd=df.parse(d);
		    PurchaseOrederDataBean homeMB=new PurchaseOrederDataBean();
			homeMB.setPurOrderNo(orderNo);
		    homeMB.setAppType(type);
		    homeMB.setPurCrossTotal(tot);
		    homeMB.setPurserialno(serialNo);
		    homeMB.setPurdate(dd);
		    homeMB.setReason(c.getNewValue().toString());
		    purchaseOrederDataBean.getPurchaseViewList().set((Integer.parseInt(serialNo)-1), homeMB);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*kasthuri 08-12-15 @@ purchase order details for delivery GM approval */
	public String poDetailsForDelGMApproval()
	{
	
		try
		{
			System.out.println("-->> mb");
			purchaseOrederDataBean.getPurchaseViewList().clear();
			purchaseOrederDataBean.setStatus("GM");
			myValid=true;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.poDetailsForDeliveryApproval(purchaseOrederDataBean);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	/*kasthuri 08-12-15 @@ purchase order details for PM approval */
	public String poDetailsForDelPMApproval()
	{
	
		try
		{
			System.out.println("-->> mb");
			purchaseOrederDataBean.getPurchaseViewList().clear();
			purchaseOrederDataBean.setStatus("PM");
			myValid=true;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.poDetailsForDeliveryApproval(purchaseOrederDataBean);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri 08-12-15 @@ purchase order details for GM delivery approval */
	public String poDelGMApproval()
	{
	
		try
		{
			System.out.println("-->> mb");
			purchaseOrederDataBean.setStatus("GM");
			myValid=true;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			if(validate1(true))
			{
				controller.poDelGMApproval(purchaseOrederDataBean);
				myValid=false;
			}
			else
			{
				System.out.println("-->> validation fails");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri 08-12-15 @@ purchase order details for PM delivery approval */
	public String poDelPMApproval()
	{
	
		try
		{
			System.out.println("-->> mb");
			purchaseOrederDataBean.setStatus("PM");
			myValid=true;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			if(validate1(true))
			{
				controller.poDelPMApproval(purchaseOrederDataBean);
				myValid=false;
			}
			else
			{
				System.out.println("-->> validation fails");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri 02-12-15 @@ purchase order detailed view*/
	public String purchaseOrderDetailedView()
	{
	
		try
		{
			System.out.println("-->> mb po no "+purchaseOrederDataBean.purOrderNo);
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.purchaseOrderDetailedView(purchaseOrederDataBean);
			if(purchaseOrederDataBean.getStatus().equalsIgnoreCase("success"))
			{
				System.out.println("-->>if status : "+purchaseOrederDataBean.getStatus());
				return "poView";
			}
			else
			{
				System.out.println("-->>else status : "+purchaseOrederDataBean.getStatus());
			}
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	public String backPage()
	{
		System.out.println("-->> type "+purchaseOrederDataBean.purProjType);
		if(purchaseOrederDataBean.getPurProjType().equalsIgnoreCase("GMAPPROVAL"))
		{
			return "back1";
		}
		if(purchaseOrederDataBean.getPurProjType().equalsIgnoreCase("MMAPPROVAL"))
		{
			return "back2";
		}
		if(purchaseOrederDataBean.getPurProjType().equalsIgnoreCase("GMDELAPPROVAL"))
		{
			return "back3";
		}
		if(purchaseOrederDataBean.getPurProjType().equalsIgnoreCase("PMDELAPPROVAL"))
		{
			return "back4";
		}
		return"";
	}
	
	public String paymentFM()
	{
		System.out.println("purchase payment waiting by FM -- > ");
		setFlag(false);
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.paymentFM(purchaseOrederDataBean);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String paymentDirector()
	{
		System.out.println("purchase payment waiting by Director -- > ");
		setFlag(false);
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.paymentDirector(purchaseOrederDataBean);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String payApproveFM()
	{
		System.out.println("purchase payment approval by FM -- > ");
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.paymentApproveFM(purchaseOrederDataBean);
			setFlag(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String payApproveDirector()
	{
		System.out.println("purchase payment approval by Director -- > ");
		try
		{
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			controller.paymentApproveDirector(purchaseOrederDataBean);
			setFlag(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
