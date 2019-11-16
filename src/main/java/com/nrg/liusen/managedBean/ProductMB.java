package com.nrg.liusen.managedBean;

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
import com.nrg.liusen.domain.ProductDataBean;
import com.nrg.liusen.util.CommonValidate;

/**
 * 
 * @author Robert Arjun
 * @Date 23-10-2015
 * @copyright NRG This class works as a ManagedBean
 *
 */
@ManagedBean(name = "productMB")
@RequestScoped
public class ProductMB {

	ProductDataBean productDataBean = new ProductDataBean();
	private boolean flag=true;
	private List<String> vendorList=null;
	private List<String> subCategoryList1=null;
	
	
	/**
	 * @return the subCategoryList1
	 */
	public List<String> getSubCategoryList1() {
		return subCategoryList1;
	}

	/**
	 * @param subCategoryList1 the subCategoryList1 to set
	 */
	public void setSubCategoryList1(List<String> subCategoryList1) {
		this.subCategoryList1 = subCategoryList1;
	}

	/**
	 * @return the vendorList
	 */
	public List<String> getVendorList() {
		return vendorList;
	}

	/**
	 * @param vendorList the vendorList to set
	 */
	public void setVendorList(List<String> vendorList) {
		this.vendorList = vendorList;
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
	 * @return the productDataBean
	 */
	public ProductDataBean getProductDataBean() {
		return productDataBean;
	}

	/**
	 * @param productDataBean
	 *            the productDataBean to set
	 */
	public void setProductDataBean(ProductDataBean productDataBean) {
		this.productDataBean = productDataBean;
	}

	/**
	 * productPageLoad Method is used for redirect to product registration form
	 * page
	 * 
	 * @return success go to product registration form page
	 */
	private boolean valid = true; 
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String productPageLoad() {
		LiusenController controller = null;
		try {
			valid = true;
			System.out.println("Inside productPageLoad method Load");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");			
			vendorList=new ArrayList<String>();
			vendorList=controller.getVendorList();
			productDataBean.setProdActualPrice("");
			productDataBean.setProdBrand("");
			productDataBean.setProdCategory("");
			productDataBean.setProdColor("");
			productDataBean.setProdDescription("");
			productDataBean.setProdMarketPrice("");
			productDataBean.setProdProductCode("");
			productDataBean.setProdProductName("");
			productDataBean.setProdSize("");
			productDataBean.setProdStandard("");
			productDataBean.setProdUnit("");
			productDataBean.setProdVendor("");
			productDataBean.setProRawCategory("");
			productDataBean.setProRawCategory1("");
			productDataBean.setProdSerCategory1("");
			productDataBean.setProdSerCategory2("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "productLoadPage";

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
		try {
			System.out.println("----------Inside submit Method Calling-----");
			
		
			if (validate(true)) {
				System.out.println("After Validation inside Submit method");
				String fieldName;
				FacesContext fc = FacesContext.getCurrentInstance();
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				assigningValues(); // used for assigning rawcategory1 values depending on rawcategory
				String status=controller.insertProduct(productDataBean); 
				System.out.println("Status"+status);
				if(status.equalsIgnoreCase("ExsistName")){
					fieldName = CommonValidate.findComponentInRoot("productProName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("This Product Name already exsist."));
				}else if(status.equalsIgnoreCase("ExsistCode")){
					fieldName = CommonValidate.findComponentInRoot("productProductCode").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("This Product Code already exsist."));
				}else{
					outputStatus="Sucsess";
					valid = false;
				}
			}
			
		} catch (Exception e) {
			System.out.println("----------Inside submit Method Exception Calling-----");

			e.printStackTrace();
			
		}
		System.out.println("outputStatus"+outputStatus);
		return "";
	}

	private void assigningValues() {
		try{
			if(productDataBean.getProRawCategory().equalsIgnoreCase("A")){
				productDataBean.setProRawCategory1("Material Alam");
			}else if(productDataBean.getProRawCategory().equalsIgnoreCase("B")){
				productDataBean.setProRawCategory1("Pondasi");
			}else if(productDataBean.getProRawCategory().equalsIgnoreCase("C")){
				productDataBean.setProRawCategory1("Dinding Panel & Acc");
			}else if(productDataBean.getProRawCategory().equalsIgnoreCase("D")){
				productDataBean.setProRawCategory1("Material Atap & Acc");
			}else if(productDataBean.getProRawCategory().equalsIgnoreCase("E")){
				productDataBean.setProRawCategory1("Material Kusen & Acc");
			}else if(productDataBean.getProRawCategory().equalsIgnoreCase("F")){
				productDataBean.setProRawCategory1("Instalasi Listrik & Acc");
			}else if(productDataBean.getProRawCategory().equalsIgnoreCase("G")){
				productDataBean.setProRawCategory1("Cat Dinding & Acc");
			}else if(productDataBean.getProRawCategory().equalsIgnoreCase("H")){
				productDataBean.setProRawCategory1("Dapur & Kamar Mandi");
			}else if(productDataBean.getProRawCategory().equalsIgnoreCase("I")){
				productDataBean.setProRawCategory1("Keramik");
			}
			
			if(productDataBean.getProdSerCategory1().equalsIgnoreCase("A")){
				productDataBean.setProdSerCategory2("Biaya Operational Site");
			}else if(productDataBean.getProdSerCategory1().equalsIgnoreCase("B")){
				productDataBean.setProdSerCategory2("Biaya Perjalanan Dinas");
			}else if(productDataBean.getProdSerCategory1().equalsIgnoreCase("C")){
				productDataBean.setProdSerCategory2("Upah");
			}else if(productDataBean.getProdSerCategory1().equalsIgnoreCase("D")){
				productDataBean.setProdSerCategory2("Biaya Expedisi");
			}
		}catch (Exception e) {
			System.out.println("Inside Exception");
		}
		
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
		
		if (StringUtils.isEmpty(productDataBean.getProdCategory())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("productCategory").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Choose the Category."));
			}
			valid = false;
		} else if(!StringUtils.isEmpty(productDataBean.getProdCategory())){
			if(productDataBean.getProdCategory().equalsIgnoreCase("00")){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("productCategory").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Category."));
					}
					valid = false;
			}else if(productDataBean.getProdCategory().equalsIgnoreCase("Product Trading")){
				if((productDataBean.getProdVendor() == null) || (StringUtils.isEmpty(productDataBean.getProdVendor())) || (productDataBean.getProdVendor().equalsIgnoreCase(""))){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("productVendor").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Vendor."));
					}
					valid = false;
			}
		}else if(productDataBean.getProdCategory().equalsIgnoreCase("Raw Material")){
			if(productDataBean.getProRawCategory() == null || productDataBean.getProRawCategory().equalsIgnoreCase("")){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("productSubCate1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Category 1."));
					}
					valid = false;
			}
			if((productDataBean.getProdVendor() == null) || (StringUtils.isEmpty(productDataBean.getProdVendor())) || (productDataBean.getProdVendor().equalsIgnoreCase(""))){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("productVendor").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Vendor."));
					}
					valid = false;
			}
		}else if(productDataBean.getProdCategory().equalsIgnoreCase("Service")){
			if((productDataBean.getProdVendor() == null) || (StringUtils.isEmpty(productDataBean.getProdVendor())) || (productDataBean.getProdVendor().equalsIgnoreCase(""))){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("productVendor").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Vendor."));
					}
					valid = false;
			}
			
			if(productDataBean.getProdSerCategory1() == null || productDataBean.getProdSerCategory1().equalsIgnoreCase("")){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("prodserviceSubCate1").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Category 1."));
					}
					valid = false;
			}
		}
		}
		if(StringUtils.isEmpty(productDataBean.getProdProductName())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productProName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Product Name."));
				}
				valid = false;
		}else if(!CommonValidate.validateName(productDataBean.getProdProductName())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productProName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the vaild Product Name."));
				}
				valid = false;
		}
		if(StringUtils.isEmpty(productDataBean.getProdProductCode())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productProductCode").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the  Product Code."));
				}
				valid = false;
		}
		if(StringUtils.isEmpty(productDataBean.getProdBrand())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productBrand").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the  Brand."));
				}
				valid = false;
		}else if(!CommonValidate.validateName(productDataBean.getProdBrand())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productBrand").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Brand."));
				}
				valid = false;
		}
		if(StringUtils.isEmpty(productDataBean.getProdSize())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productSize").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the  Size."));
				}
				valid = false;
		}else if(!CommonValidate.validateNumber(productDataBean.getProdSize())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productSize").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Size."));
				}
				valid = false;
		}
		/*if(StringUtils.isEmpty(productDataBean.getProdColor())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productColor").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the Type."));
				}
				valid = false;
		}else if(!CommonValidate.validateName(productDataBean.getProdColor())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productColor").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Enter the  valid Type."));
				}
				valid = false;
		}*/
		if(productDataBean.getProdUnit().equalsIgnoreCase("030")){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productUnit").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the  Unit."));
				}
				valid = false;
		}
		if(StringUtils.isEmpty(productDataBean.getProdActualPrice())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productActulPrice").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the Actual Cost."));
				}
				valid = false;
		}else if(!CommonValidate.validateNumber(productDataBean.getProdActualPrice())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productActulPrice").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the valid Actual Cost."));
				}
				valid = false;
		}
		if(StringUtils.isEmpty(productDataBean.getProdMarketPrice())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productMarketPrice").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the  Market Price."));
				}
				valid = false;
		}else if(!CommonValidate.validateNumber(productDataBean.getProdMarketPrice())){
			if(flag){
				fieldName = CommonValidate.findComponentInRoot("productMarketPrice").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Choose the valid Market Price."));
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
		System.out.println("Inside Rest Method Calling");
		productDataBean.setProdActualPrice("");
		productDataBean.setProdBrand("");
		productDataBean.setProdCategory("");
		productDataBean.setProdColor("");
		productDataBean.setProdDescription("");
		productDataBean.setProdMarketPrice("");
		productDataBean.setProdProductCode("");
		productDataBean.setProdProductName("");
		productDataBean.setProdSize("");
		productDataBean.setProdStandard("");
		productDataBean.setProdUnit("");
		productDataBean.setProdVendor("");
		productDataBean.setProRawCategory("");
		productDataBean.setProRawCategory1("");
		productDataBean.setProdSerCategory1("");
		productDataBean.setProdSerCategory2("");
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
	public void valuechange(ValueChangeEvent event){  
		System.out.println("here "+event.getNewValue());
		productDataBean.setProdCategory(event.getNewValue().toString());
		setFlag(false);
		flag=false;
		}
	
	/*@PostConstruct
	public void init(){

		LiusenController controller = null;
		try{
			
			System.out.println("Inside Construtor Calling");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			vendorList=new ArrayList<String>();
			vendorList=controller.getVendorList();
			System.out.println("name"+vendorName);
			if(vendorName.size()>0){
				vendorList=new ArrayList<String>();
				vendorList.addAll(vendorName);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
}
