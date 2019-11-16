package com.nrg.liusen.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.LimitDataBean;
import com.nrg.liusen.shared.RawMaterial;
import com.nrg.liusen.util.CommonValidate;

/**
 * 
 * @author Robert Arjun
 * @date 27-10-2015
 * @copyright NRG 
 * This class works as a ManagedBean
 *
 */
@ManagedBean(name="limitMB")
public class LimitMB {

	LimitDataBean limitDataBean=new LimitDataBean();
	private List<String> productList=null;
	
	private List<String> projectList=null;
	
	private List<RawMaterial> categoryList=null;
	
	
	
	/**
	 * @return the categoryList
	 */
	public List<RawMaterial> getCategoryList() {
		return categoryList;
	}

	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List<RawMaterial> categoryList) {
		this.categoryList = categoryList;
	}
	private boolean valid=true;
	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return the projectList
	 */
	public List<String> getProjectList() {
		return projectList;
	}

	/**
	 * @param projectList the projectList to set
	 */
	public void setProjectList(List<String> projectList) {
		this.projectList = projectList;
	}

	/**
	 * @return the productList
	 */
	public List<String> getProductList() {
		return productList;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<String> productList) {
		this.productList = productList;
	}

	/**
	 * @return the limitDataBean
	 */
	public LimitDataBean getLimitDataBean() {
		return limitDataBean;
	}

	/**
	 * @param limitDataBean the limitDataBean to set
	 */
	public void setLimitDataBean(LimitDataBean limitDataBean) {
		this.limitDataBean = limitDataBean;
	}
	/**
	 * customerPageLoad Method is used for redirect to vendor registration form
	 * page
	 * 
	 * @return success go to Customer registration form page
	 */
	public String limitPageLoad() {
		System.out.println("Inside customerPageLoad Method Calling ");
		limitDataBean.setLimitDescription("");
		limitDataBean.setLimitLimitSize("");
		limitDataBean.setLimitProductName("");
		limitDataBean.setLimitRawCategoory2("");
		limitDataBean.setLimitRawCategory1("");
		limitDataBean.setLimitProjectName("");
		limitDataBean.setLimitServiceName("");
		limitDataBean.setLimProductCategory("");
		LiusenController controller = null;
		ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			projectList= new ArrayList<String>();
			projectList=controller.getProjectList();
		System.out.println("Inside MB name" + projectList);
		setValid(true);
		return "limitLoadPage";
		
	}
	
	/**
	 * submit Method is used to get the values from UI to Controller
	 * 
	 * @return submitSuccess go to controller
	 * @return submitFailure redirect same page
	 */
	public String submit() {
		LiusenController controller = null;
		String outputStatus="Failure";
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			System.out.println("----------Inside submit Method Calling-----");
			if (validate(true)) {
				System.out.println("After Validation inside Submit method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				if(limitDataBean.getLimitProductName() != null){
				String status=controller.insertLimit(limitDataBean);
				System.out.println("Status"+status);
				if(status.equalsIgnoreCase("Exsist")){
					System.out.println("Already Exsist");
					fieldName = CommonValidate.findComponentInRoot("limProject").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("This Product already set the Limit Size."));
				}else if(status.equalsIgnoreCase("Exsist1")){
					System.out.println("Already Exsist");
					fieldName = CommonValidate.findComponentInRoot("limService").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("This Product already set the Limit Size."));
				}
				else if(status.equalsIgnoreCase("Fail")){
					System.out.println("Network Error Please try again....");
				}
				else{
				outputStatus="Success";
				setValid(false);
				}
				}
			}
		}catch(Exception e){
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
	private boolean validate(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if(limitDataBean.getLimitProjectName() == null || limitDataBean.getLimitProjectName().equalsIgnoreCase("")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("limProject").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Product Name."));
				}
				valid = false;
		}
		
		if(limitDataBean.getLimProductCategory().equalsIgnoreCase("")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("limRadio").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Category."));
				}
				valid = false;
		}else if(limitDataBean.getLimProductCategory().equalsIgnoreCase("Raw Material")){
			if(limitDataBean.getLimitProductName() == null || limitDataBean.getLimitProductName().equalsIgnoreCase("")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("limService").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Raw Material."));
				}
				valid = false;
			}
			if(StringUtils.isBlank(limitDataBean.getLimitRawCategory1())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("limittSubCate1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Raw Material."));
					}
					valid = false;
			}
			if(StringUtils.isBlank(limitDataBean.getLimitRawCategoory2())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("limittSubCate2").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the the Raw Material."));
					}
					valid = false;
			}
			
		}else if(limitDataBean.getLimProductCategory().equalsIgnoreCase("Service")){
			if(limitDataBean.getLimitServiceName() == null || limitDataBean.getLimitServiceName().equalsIgnoreCase("")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("limService").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Service."));
				}
				valid = false;
			}
			if(StringUtils.isBlank(limitDataBean.getLimitRawCategory1())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("limitSerCate1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Service."));
					}
					valid = false;
			}
			if(StringUtils.isBlank(limitDataBean.getLimitRawCategoory2())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("limitSerCate2").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Service."));
					}
					valid = false;
			}
		}
		if(StringUtils.isBlank(limitDataBean.getLimitLimitSize())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("limitSize").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Limit Size."));
				}
				valid = false;
		}else if(!StringUtils.isBlank(limitDataBean.getLimitLimitSize())){
			if(!CommonValidate.isNumeric(limitDataBean.getLimitLimitSize())){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("limitSize").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Limit Size."));
					}
					valid = false;
			}
			
		}
		/*if(StringUtils.isBlank(limitDataBean.getLimitDescription())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("limitDesc").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Description."));
				}
				valid = false;
		}*/
		
		return valid;
	}
	/**
	 * reset Method is used to reset the input text box values
	 * 
	 * @return
	 */
	public String reset() {
		System.out.println("Inside Reset Method Calling");
		limitDataBean.setLimitDescription("");
		limitDataBean.setLimitLimitSize("");
		limitDataBean.setLimitProductName("");
		limitDataBean.setLimitRawCategoory2("");
		limitDataBean.setLimitRawCategory1("");
		limitDataBean.setLimitProjectName("");
		limitDataBean.setLimitServiceName("");
		limitDataBean.setLimProductCategory("");
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
	public void categoryChange(ValueChangeEvent  event){
		try{
			LiusenController controller = null;
			productList=new ArrayList<String>();
		System.out.println("Inside "+event.getNewValue());
		String category=event.getNewValue().toString();
		if(category != null){
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			productList=controller.productNameList(category);
			System.out.println("name"+productList);
			limitDataBean.setLimitProductName("");
			limitDataBean.setLimitRawCategory1("");
			limitDataBean.setLimitRawCategoory2("");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void productChange(ValueChangeEvent event) {
		try{
		System.out.println("inside "+event.getNewValue());
		LiusenController controller = null;
		String category=event.getNewValue().toString();
		if(category != null){
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			categoryList= new ArrayList<RawMaterial>();
			categoryList=controller.getSubCategoty(category);
			System.out.println("Inside MB name" + categoryList);
			if(categoryList.size() > 0){
				limitDataBean.setLimitRawCategory1(categoryList.get(0).getSubcategory1_ID());
				limitDataBean.setLimitRawCategoory2(categoryList.get(0).getSubcategory2_ID());
				System.out.println(categoryList.get(0).getSubcategory1_ID());
				System.out.println(categoryList.get(0).getSubcategory2_ID());

			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/*@PostConstruct
	public void init(){
		try{
		
			LiusenController controller = null;
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				projectList= new ArrayList<String>();
				projectList=controller.getProjectList();
			System.out.println("Inside MB name" + projectList);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
	}
*/
	
}