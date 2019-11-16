package com.nrg.liusen.managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.LimitDataBean;
import com.nrg.liusen.shared.ProductLimit;
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
@ManagedBean(name ="limitViewMB")
@RequestScoped
public class LimitViewMB {

	LimitDataBean limitDataBean=new LimitDataBean();
	private boolean flag=true;
	private boolean deleteflag=false;
	private boolean editflag=false;
	
	public boolean isDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(boolean deleteflag) {
		this.deleteflag = deleteflag;
	}
	private List<LimitDataBean> limitList=null;
	
	private List<ProductLimit> productLimitList=null;
	
	private List<String> projectList=null;
	
	private List<String> productList=null;
	
	private boolean valid1=true;
	private boolean valid2=true;
	
	
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

	/**
	 * @return the valid2
	 */
	public boolean isValid2() {
		return valid2;
	}

	/**
	 * @param valid2 the valid2 to set
	 */
	public void setValid2(boolean valid2) {
		this.valid2 = valid2;
	}

	/**
	 * @return the valid1
	 */
	public boolean isValid1() {
		return valid1;
	}

	/**
	 * @param valid1 the valid1 to set
	 */
	public void setValid1(boolean valid1) {
		this.valid1 = valid1;
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
	 * @return the limitList
	 */
	public List<LimitDataBean> getLimitList() {
		return limitList;
	}

	/**
	 * @param limitList the limitList to set
	 */
	public void setLimitList(List<LimitDataBean> limitList) {
		this.limitList = limitList;
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
	 * @return the limitLize
	 */
	public List<LimitDataBean> getLimitLize() {
		return limitList;
	}

	/**
	 * @param limitLize the limitLize to set
	 */
	public void setLimitLize(List<LimitDataBean> limitLize) {
		this.limitList = limitLize;
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
	public String limitViewLoad() {

		System.out.println("Inside Load the limitViewLoad View Load Page");
		setFlag(true);
		setValid1(true);
		setValid2(true);
		limitDataBean.setLimitProductName1("");
		limitDataBean.setLimProductCategory1("");
		limitDataBean.setLimProductCategory("");
		return "limViewLoad";

	}

	/**
	 * This method is used for show data tables depends on Product name
	 * 
	 * @return
	 */
	public String searchProductName() {
		LiusenController controller = null;
		System.out.println("Inside the searchProductName Method Calling");
		limitDataBean.setLimProductCategory1("");
		try {
			if (validate(true)) {
				System.out.println("After Validate inside  searchProductName method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager")){
					setDeleteflag(true);
					setEditflag(false);
				}else if(rolles.equalsIgnoreCase("Finance Manager") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Designer") || rolles.equalsIgnoreCase("Marketing Staff") ||  rolles.equalsIgnoreCase("Chief Designer") || rolles.equalsIgnoreCase("Marketing Manager")){
					setDeleteflag(true);
					setEditflag(true);
				}
				else{
					setDeleteflag(false);
					setEditflag(false);
				}
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				limitList=controller.getLimit(limitDataBean.getLimitProductName1());
			}else{
				setFlag(true);
			}
		} catch (Exception e) {
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
		if (StringUtils.isBlank(limitDataBean.getLimitProductName1())) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("limViewName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Product Name."));
			}
			valid = false;
		} else if (!StringUtils.isBlank(limitDataBean.getLimitProductName1())) {

			if (!CommonValidate.validateName(limitDataBean.getLimitProductName1())) {
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"limViewName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Enter the valid Product Name."));
				}
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * This method is used for show data tables depends on Category name
	 * 
	 * @return
	 */

	public String searchCategory() {
		System.out.println("Inside the searchCategory Method Calling");
		LiusenController controller = null;
		limitDataBean.setLimitProductName1("");
		try{
			if (validate1(true)) {
				System.out.println("After Validate1 inside  searchCategory method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager")){
					setDeleteflag(true);
					setEditflag(false);
				}else if(rolles.equalsIgnoreCase("Finance Manager") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Designer") || rolles.equalsIgnoreCase("Marketing Staff") || rolles.equalsIgnoreCase("Chief Designer") || rolles.equalsIgnoreCase("Marketing Manager")){
					setDeleteflag(true);
					setEditflag(true);
				}
				else{
					setDeleteflag(false);
					setEditflag(false);
				}
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				limitList=controller.getLimitByCategory(limitDataBean.getLimProductCategory1());
			}else{
				setFlag(true);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";

	}

	private boolean validate1(boolean flag2) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (limitDataBean.getLimProductCategory1() ==  null || limitDataBean.getLimProductCategory1().equalsIgnoreCase("")) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("limCategory")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Category."));
			}
			valid = false;
		
		}
		return valid;
	}
	public void viewByProjectName() {
		LiusenController controller = null;
		try{
			if(limitDataBean.getLimitProjectName() !=null){
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				productLimitList=controller.getLimitListByProject(limitDataBean);
				System.out.println("size"+productLimitList.size());
				if(productLimitList.size() > 0){
					limitDataBean.setLimProductCategory(productLimitList.get(0).getRawMaterial().getCategory());
					limitDataBean.setLimitServiceName(productLimitList.get(0).getProduct().getProductName());
					limitDataBean.setLimitProjectName(productLimitList.get(0).getProduct().getProductName());
					limitDataBean.setLimitRawCategory1(productLimitList.get(0).getRawMaterial().getSubcategory1_ID());
					limitDataBean.setLimitRawCategoory2(productLimitList.get(0).getRawMaterial().getSubcategory2_ID());
					limitDataBean.setLimitProductName(productLimitList.get(0).getRawMaterial().getProductName());
					limitDataBean.setLimitServiceName(productLimitList.get(0).getRawMaterial().getProductName());
					limitDataBean.setLimitLimitSize(productLimitList.get(0).getLimitSize());
					limitDataBean.setLimitDescription(productLimitList.get(0).getDescription());
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @return the productLimitList
	 */
	public List<ProductLimit> getProductLimitList() {
		return productLimitList;
	}

	/**
	 * @param productLimitList the productLimitList to set
	 */
	public void setProductLimitList(List<ProductLimit> productLimitList) {
		this.productLimitList = productLimitList;
	}
	public String limitEdit() {
		String status="LimitEmpty";
		LiusenController controller = null;
		String category;String category1;String category2;
		try{
			if(limitDataBean.getLimitProjectName() !=null){
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				productLimitList=controller.limitListEditByProject(limitDataBean);
				System.out.println("size"+productLimitList.size());
				if(productLimitList.size() > 0){
					limitDataBean.setLimProductCategory(productLimitList.get(0).getRawMaterial().getCategory());
					limitDataBean.setLimitServiceName(productLimitList.get(0).getProduct().getProductName());
					limitDataBean.setLimitProjectName(productLimitList.get(0).getProduct().getProductName());
					limitDataBean.setLimitRawCategory1(productLimitList.get(0).getRawMaterial().getSubcategory1_ID());
					limitDataBean.setLimitRawCategoory2(productLimitList.get(0).getRawMaterial().getSubcategory2_ID());
					limitDataBean.setLimitProductName(productLimitList.get(0).getRawMaterial().getProductName());
					limitDataBean.setLimitServiceName(productLimitList.get(0).getRawMaterial().getProductName());
					limitDataBean.setLimitLimitSize(productLimitList.get(0).getLimitSize());
					limitDataBean.setLimitDescription(productLimitList.get(0).getDescription());
					productList=new ArrayList<String>();
					category2=productLimitList.get(0).getRawMaterial().getCategory();
					productList=controller.productNameList(category2);
					projectList= new ArrayList<String>();
					
					category=productLimitList.get(0).getProduct().getCategory();
					projectList=controller.getProjectServiceList(category);
					/*category1=productLimitList.get(0).getRawMaterial().getSubcategory1_ID();
					productList=controller.productNameList(category1);*/
					System.out.println("Inside MB name" + projectList +productLimitList.get(0).getRawMaterial().getProductName()+productList);

					status="LimitNotEmpty";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
		
	}
	/*public void categoryChange(ValueChangeEvent  event){
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
			
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
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
			limitDataBean.setLimitServiceName("");
			limitDataBean.setLimitRawCategory1("");
			limitDataBean.setLimitRawCategoory2("");
			limitDataBean.setLimitDescription("");
			limitDataBean.setLimitLimitSize("");
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
	public String submit() {
		LiusenController controller = null;
		String outputStatus="Failure";
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			System.out.println("----------Inside submit Method Calling-----");
			if (validatesave(true)) {
				System.out.println("After Validation inside Submit method");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				if(limitDataBean.getLimitProductName() != null){
					limitDataBean.setStatus("edit");
					String status=controller.EditLimit(limitDataBean);
				System.out.println("Status"+status);
				if(status.equalsIgnoreCase("Pass")){
					setValid1(false);
					outputStatus="Success";
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
	private boolean validatesave(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if(limitDataBean.getLimitProjectName() == null || limitDataBean.getLimitProjectName().equalsIgnoreCase("")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("limProject").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Project Name."));
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
				fieldName = CommonValidate.findComponentInRoot("limRaw").getClientId(fc);
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
			System.out.println("service -- > "+limitDataBean.getLimitServiceName());
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
		}
		*/
		return valid;
	}
	public String delete() {
		LiusenController controller = null;
		String outputStatus="Failure";
		try{
			System.out.println("Inside Delete method calling....");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			limitDataBean.setStatus("delete");
			String Status=controller.limitDelete(limitDataBean);
			System.out.println("Status"+Status);
			if(Status.equalsIgnoreCase("Pass")){
			valid2=false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return outputStatus;
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
