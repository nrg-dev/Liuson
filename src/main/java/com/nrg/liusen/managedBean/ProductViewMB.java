package com.nrg.liusen.managedBean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.ProductDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.shared.ImagePath;
import com.nrg.liusen.shared.Product;
import com.nrg.liusen.shared.RawMaterial;
import com.nrg.liusen.util.CommonValidate;
/**
 * 
 * @author Robert Arjun
 * @Date 26-10-2015
 * @copyright NRG This class works as a ManagedBean
 *
 */
@ManagedBean(name = "productViewMB")
@RequestScoped
public class ProductViewMB {

	private boolean flag=true;
	
	ProductDataBean productDataBean = new ProductDataBean();
	private List<ProductDataBean> productViewList=null;
	private List<Product> prodList=null;
	private List<String> vendorList=null;
	private List<RawMaterial> prodRawList=null;
	private List<ImagePath> designList=null;
	private boolean deleteflag=false;
	private boolean editflag=false;
	private boolean limitviewflag=false;
	
	
	
	

	public boolean isDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(boolean deleteflag) {
		this.deleteflag = deleteflag;
	}

	public boolean isLimitviewflag() {
		return limitviewflag;
	}

	public void setLimitviewflag(boolean limitviewflag) {
		this.limitviewflag = limitviewflag;
	}

	/**
	 * @return the designList
	 */
	public List<ImagePath> getDesignList() {
		return designList;
	}

	/**
	 * @param designList the designList to set
	 */
	public void setDesignList(List<ImagePath> designList) {
		this.designList = designList;
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
	private List<String> productList = null;
	private List<ProductDataBean> desginViewList=null;
	private List<String> projectList = null;
	
	
	
	

	/**
	 * @return the desginViewList
	 */
	public List<ProductDataBean> getDesginViewList() {
		return desginViewList;
	}

	/**
	 * @param desginViewList the desginViewList to set
	 */
	public void setDesginViewList(List<ProductDataBean> desginViewList) {
		this.desginViewList = desginViewList;
	}

	public List<String> getProductList() {
		return productList;
	}

	public void setProductList(List<String> productList) {
		this.productList = productList;
	}

	/**
	 * @return the prodRawList
	 */
	public List<RawMaterial> getProdRawList() {
		return prodRawList;
	}

	/**
	 * @param prodRawList the prodRawList to set
	 */
	public void setProdRawList(List<RawMaterial> prodRawList) {
		this.prodRawList = prodRawList;
	}

	/**
	 * @return the prodList
	 */
	public List<Product> getProdList() {
		return prodList;
	}

	/**
	 * @param prodList the prodList to set
	 */
	public void setProdList(List<Product> prodList) {
		this.prodList = prodList;
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
	 * @return the productViewList
	 */
	public List<ProductDataBean> getProductViewList() {
		return productViewList;
	}

	/**
	 * @param productViewList the productViewList to set
	 */
	public void setProductViewList(List<ProductDataBean> productViewList) {
		this.productViewList = productViewList;
	}

	/**
	 * @return the productDataBean
	 */
	public ProductDataBean getProductDataBean() {
		return productDataBean;
	}

	/**
	 * @param productDataBean the productDataBean to set
	 */
	public void setProductDataBean(ProductDataBean productDataBean) {
		this.productDataBean = productDataBean;
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
	private boolean valid = true; 
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String productViewLoad() {
		valid = true; 
		myValid=true;
		System.out.println("Inside Load the Product View Load Page");
		productDataBean.setProdCategory1("");
		productDataBean.setProdProductName("");
		productDataBean.setProdProductName1("");
		setValidstock("");
		stockbox=false;
		flag=true;
		return "prodViewLoad";

	}

	/**
	 * This method is used for show data tables depends on Product name
	 * 
	 * @return
	 */
	public String searchProductName() {
		LiusenController controller = null;
		System.out.println("Inside the searchProductName Method Calling");
		setValidstock(null);
		productDataBean.setProdCategory1("");
		try {
			if (validate(true)) {
				System.out.println("After Validate inside  searchProductName method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager")){
					setDeleteflag(true);
					setEditflag(false);
				}else if(rolles.equalsIgnoreCase("Finance Manager") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Marketing Staff") || rolles.equalsIgnoreCase("Designer") || rolles.equalsIgnoreCase("Chief Designer") || rolles.equalsIgnoreCase("Marketing Manager")){
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
				productViewList=controller.getProductList(productDataBean);
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
		if (StringUtils.isBlank(productDataBean.getProdProductName1())) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot("prodViewProductName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the Product Name."));
			}
			valid = false;
		} else if (!StringUtils.isBlank(productDataBean.getProdProductName1())) {

			if (!CommonValidate.validateName(productDataBean.getProdProductName1())) {
				if (flag1) {
					fieldName = CommonValidate.findComponentInRoot(
							"prodViewProductName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Enter the valid Product Name."));
				}
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * This method is used for show data tables depends on City name
	 * 
	 * @return
	 */

	public String searchCategory() {
		System.out.println("Inside the searchCategory Method Calling");
		LiusenController controller = null;
		setValidstock(null);
		productDataBean.setProdProductName1("");
		try{
			if (validate1(true)) {
				System.out.println("After Validate1 inside  searchCategory method");
				String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
				if(rolles.equalsIgnoreCase("Project Manager")){
					setDeleteflag(true);
					setEditflag(false);
				}else if(rolles.equalsIgnoreCase("Finance Manager") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Marketing Staff") || rolles.equalsIgnoreCase("Designer") || rolles.equalsIgnoreCase("Chief Designer") || rolles.equalsIgnoreCase("Marketing Manager")){
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
				productViewList=controller.getProductListByCategory(productDataBean);
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
		if (productDataBean.getProdCategory1().equalsIgnoreCase("00")) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot("proViewCategoryName")
						.getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Choose the Category."));
			}
			valid = false;
		} /*else if (!StringUtils.isBlank(productDataBean.getProdCategory1())) {

			if (!CommonValidate.validateName(productDataBean.getProdCategory1())) {
				if (flag2) {
					fieldName = CommonValidate.findComponentInRoot(
							"proViewCategoryName").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage(
							"Please Enter the valid Category Name."));
				}
				valid = false;
			}
		}*/
		return valid;
	}
	
	public void viewByProductName() {
		System.out.println("Inside viewByProductName");
		LiusenController controller = null;
		try{
			System.out.println("Product Name"+productDataBean.getProdProductName());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			
			if(productDataBean.getProdCategory() != null){
				if(productDataBean.getProdCategory().equalsIgnoreCase("Raw Material") || productDataBean.getProdCategory().equalsIgnoreCase("Service")){
					System.out.println("Inside Raw Meterial");
					prodRawList=controller.getProductListSingle1(productDataBean.getProdProductName());
					if(prodRawList.size() > 0){
						for(RawMaterial rawMaterial: prodRawList){
							productDataBean.setProdActualPrice(rawMaterial.getActualPrice());
							productDataBean.setProdBrand(rawMaterial.getBrand());
							productDataBean.setProdCategory(rawMaterial.getCategory());
							productDataBean.setProdColor(rawMaterial.getColor());
							productDataBean.setProdDescription(rawMaterial.getDescription());
							productDataBean.setProdMarketPrice(rawMaterial.getMarketPrice());
							productDataBean.setProdProductCode(rawMaterial.getProductCode());
							productDataBean.setProdProductName(rawMaterial.getProductName());
							productDataBean.setProdSize(rawMaterial.getSize());
							productDataBean.setProRawCategory(rawMaterial.getSubcategory1_ID());
							productDataBean.setProRawCategory1(rawMaterial.getSubcategory2_ID());
							productDataBean.setProdUnit(rawMaterial.getUnit());
							productDataBean.setProdVendor(rawMaterial.getVendor().getFirmName());
						}
					}else{
						productDataBean.setProdActualPrice("");
						productDataBean.setProdBrand("");
						productDataBean.setProdCategory("");
						productDataBean.setProdColor("");
						productDataBean.setProdDescription("");
						productDataBean.setProdMarketPrice("");
						productDataBean.setProdProductCode("");
						productDataBean.setProdProductName("");
						productDataBean.setProdSize("");
						productDataBean.setProRawCategory("");
						productDataBean.setProRawCategory1("");
						productDataBean.setProdUnit("");
						productDataBean.setProdVendor("");
					}
				}else{
					System.out.println("Inside non Raw Meterial");
			prodList=controller.getProductListSingle(productDataBean.getProdProductName());
			System.out.println("Size"+prodList.size());
			if(prodList.size() > 0){
				for(Product product: prodList){
					productDataBean.setProdActualPrice(product.getActualPrice());
					productDataBean.setProdBrand(product.getBrand());
					productDataBean.setProdCategory(product.getCategory());
					productDataBean.setProdColor(product.getColor());
					productDataBean.setProdDescription(product.getDescription());
					productDataBean.setProdMarketPrice(product.getMarketPrice());
					productDataBean.setProdProductCode(product.getProductCode());
					productDataBean.setProdProductName(product.getProductName());
					productDataBean.setProdSize(product.getSize());
					productDataBean.setProdUnit(product.getUnit());
					productDataBean.setProdVendor(product.getVendor().getFirmName());
				}
			}else{
				productDataBean.setProdActualPrice("");
				productDataBean.setProdBrand("");
				productDataBean.setProdCategory("");
				productDataBean.setProdColor("");
				productDataBean.setProdDescription("");
				productDataBean.setProdMarketPrice("");
				productDataBean.setProdProductCode("");
				productDataBean.setProdProductName("");
				productDataBean.setProdSize("");
				productDataBean.setProRawCategory("");
				productDataBean.setProRawCategory1("");
				productDataBean.setProdUnit("");
				productDataBean.setProdVendor("");
			}
				}
			}
		}catch(Exception e){
			
		}
		
	}
	public String productEdit() {
		String status="productListNone";
		System.out.println("Inside Edit Method");
		 LiusenController controller = null;
		 if(!productDataBean.getProdProductName().equalsIgnoreCase("")){
			 try{
				 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				 controller = (LiusenController) ctx.getBean("controller");
				
				 if(productDataBean.getProdCategory() != null){
					 if(productDataBean.getProdCategory().equalsIgnoreCase("Raw Material") || productDataBean.getProdCategory().equalsIgnoreCase("Service")){
						 prodRawList=controller.getRawCategoryEdit(productDataBean);
						 
						 if(prodRawList.size()>0){
							 for(RawMaterial raw: prodRawList){
									productDataBean.setProdActualPrice(raw.getActualPrice());
									productDataBean.setProdBrand(raw.getBrand());
									productDataBean.setProdCategory(raw.getCategory());
									productDataBean.setProdColor(raw.getColor());
									productDataBean.setProdDescription(raw.getDescription());
									productDataBean.setProdMarketPrice(raw.getMarketPrice());
									productDataBean.setProdProductCode(raw.getProductCode());
									productDataBean.setProdProductName(raw.getProductName());
									productDataBean.setProdSize(raw.getSize());
									productDataBean.setProRawCategory(raw.getSubcategory1_ID());
									productDataBean.setProRawCategory1(raw.getSubcategory1_ID());
									productDataBean.setProdSerCategory1(raw.getSubcategory1_ID());
									productDataBean.setProdSerCategory2(raw.getSubcategory2_ID());
									productDataBean.setProdUnit(raw.getUnit());
									try{
									productDataBean.setProdVendor(raw.getVendor().getFirmName());
									}catch(NullPointerException e1){
										productDataBean.setProdVendor("");
									}
								}
							 
							 List<String> vendorName=controller.getVendorList();
								System.out.println("name"+vendorName);
								if(vendorName.size()>0){
									vendorList=new ArrayList<String>();
									vendorList.addAll(vendorName);
									HashSet<String> ll=new HashSet<String>(vendorList);
									vendorList.clear();
									vendorList.addAll(ll);
									Collections.sort(vendorList);
								}
								
							
						 status="productListtNotEmpty";
						 }
					 }else{
			
				 prodList=controller.getProductInfo(productDataBean);
				 System.out.println("prodList Size"+prodList.size());
				 if(prodList.size()>0){
					 for(Product product: prodList){
							productDataBean.setProdActualPrice(product.getActualPrice());
							productDataBean.setProdBrand(product.getBrand());
							productDataBean.setProdCategory(product.getCategory());
							productDataBean.setProdColor(product.getColor());
							productDataBean.setProdDescription(product.getDescription());
							productDataBean.setProdMarketPrice(product.getMarketPrice());
							productDataBean.setProdProductCode(product.getProductCode());
							productDataBean.setProdProductName(product.getProductName());
							productDataBean.setProdSize(product.getSize());
							productDataBean.setProRawCategory("");
							productDataBean.setProRawCategory1("");
							productDataBean.setProdUnit(product.getUnit());
							try{
							productDataBean.setProdVendor(product.getVendor().getFirmName());
							}catch(NullPointerException e1){
								productDataBean.setProdVendor("");
							}
						}
					 
					 List<String> vendorName=controller.getVendorList();
						System.out.println("name"+vendorName);
						if(vendorName.size()>0){
							vendorList=new ArrayList<String>();
							vendorList.addAll(vendorName);
							HashSet<String> ll=new HashSet<String>(vendorList);
							vendorList.clear();
							vendorList.addAll(ll);
							Collections.sort(vendorList);
						}
						
					
				 status="productListtNotEmpty";
				 }
				 }					
				 }
				 System.out.println("vendor -- > "+productDataBean.getProdVendor()+"vendor list -- > "+vendorList);
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
		 System.out.println("Status"+status);
		return status;
		
	}
	public String submit() {
		LiusenController controller = null;
		String outputStatus="Failure";
		try {
			System.out.println("----------Inside submit Method Calling-----");
			
		
			if (validateEdit(true)) {
				System.out.println("After Validation inside Submit method");
				
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				assigningValues(); // used for assigning rawcategory1 values depending on rawcategory
				String status=controller.editProduct(productDataBean); 
				System.out.println("Status"+status);
				if(status.equalsIgnoreCase("Merged")){
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
	private boolean validateEdit(boolean flag) {
		boolean valid = true;
		String fieldName;
		System.out.println("vendor -- > "+productDataBean.getProdVendor());
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
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Vendor ."));
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
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Vendor ."));
					}
					valid = false;
			}
		}else if(productDataBean.getProdCategory().equalsIgnoreCase("Service")){
			if((productDataBean.getProdVendor() == null) || (StringUtils.isEmpty(productDataBean.getProdVendor())) || (productDataBean.getProdVendor().equalsIgnoreCase(""))){
				if(flag){
					fieldName = CommonValidate.findComponentInRoot("productVendor").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Choose the Vendor ."));
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
	
	private boolean myValid = false;
	
	public boolean isMyValid() {
		return myValid;
	}

	public void setMyValid(boolean myValid) {
		this.myValid = myValid;
	}

	LiusenController controller = null;
	public String delete() {
		LiusenController controller = null;
		String outputStatus="Failure";
		try{
			System.out.println("Inside Delete method calling....");
			System.out.println("Product Name"+productDataBean.getProdProductName());
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			String Status=controller.productDelete(productDataBean);
			System.out.println("Status"+Status);
			myValid=false;
		}catch(Exception e){
			e.printStackTrace();
		}
		return outputStatus;
	}	
	String validate;
	public String uploadValidate;
	private ArrayList<UploadedImage> files = new ArrayList<UploadedImage>();
	ArrayList<String> list1=new ArrayList<String>();
	UploadedFile file;
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
    public ArrayList<String> getList1() {
		return list1;
	}
	public void setList1(ArrayList<String> list1) {
		this.list1 = list1;
	}
	public String getValidate() 
	{
		return validate;
	}
	public void setValidate(String validate)
	{
		this.validate = validate;
	}
	public String getUploadValidate() {
		return uploadValidate;
	}

	public void setUploadValidate(String uploadValidate) {
		this.uploadValidate = uploadValidate;
	}

	
	public ArrayList<UploadedImage> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<UploadedImage> files) {
		this.files = files;
	}

	public void paint1(OutputStream stream, Object object) throws IOException 
    {
    	System.out.println("----------inside paint----------");
    	stream.write(getFiles().get((Integer) object).getData());
        stream.close();
    }
	public String clearUploadData() {
    	setUploadValidate("");
        files.clear();
        list1.clear();
        setFile(null);
        return null;
    }
	public int getSize() {
        if (getFiles().size() > 0) {
            return getFiles().size();
        } else {
            return 0;
        }
    }
 
    public long getTimeStamp() {
        return System.currentTimeMillis();
    }
    public void listener(FileUploadEvent event) throws Exception 
	{
    	
    	try
    	{    
		setUploadValidate("");
		UploadedFile item = event.getUploadedFile();
        if(files.size()>=1)
        {
    	   throw new Exception("Only one Image can upload");
        }        
        UploadedImage file = new UploadedImage();
        file.setLength(item.getData().length);
        file.setName(item.getName());
        file.setData(item.getData());
        files.add(file);
        File fullFile  = new File(item.getName());   
        this.file = event.getUploadedFile();
        System.out.println("files----->"+getFiles());
        System.out.println("name----->"+item.getName());
        System.out.println("inputstream----->"+getFile().getInputStream());
    	}
    	catch(Exception e)
    	{
    		setUploadValidate(e.getMessage());
    	}
    }

	public String designReg()
	{
		try
		{
			valid = true;
			setValidate("");
			productList=new ArrayList<String>();
			productDataBean.setProdDescription("");
			productDataBean.setProdProductName("");
			productDataBean.setDate(null);
			setUploadValidate("");
			files.clear();
			productDataBean.setProdCategory1("Project");
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			productViewList=controller.getProductListByCategory(productDataBean);
			if(productViewList.size()>0)
			{
				for(int i=0;i<productViewList.size();i++)
				{
					productList.add(productViewList.get(i).getProdProductName());
				}
			}
			
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}		
		return "DesignRegisterPage";
	}
	
	private boolean validate2(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if(productDataBean.getProdProductName().equalsIgnoreCase(""))
		{
			if(flag)
			{
			fieldName = CommonValidate.findComponentInRoot("prod_name").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Choose the Product Name."));
			}
			valid = false;
		}
		if(productDataBean.getDate()==null)
		{
			if(flag)
			{
			fieldName = CommonValidate.findComponentInRoot("date").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
			}
			valid = false;
		}
		
		if(files.size()==0)
		{
			if(flag)
			{
			fieldName = CommonValidate.findComponentInRoot("upload").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Upload Photo."));
			}
			valid = false;
		}		
		return valid;
	}
	
	 public String designRegSubmit()
		{
			try
			{
				setValidate("");
				System.out.println("files.size() "+files.size());
				if(validate2(true))
			    {
					System.out.println("-->> validation true");
					productDataBean.setFile(file);
					controller.designRegSubmit(productDataBean);
					valid=false;
			    }
				else
				{
					System.out.println("-->> validation fails");
					valid=true;
				}
				
			}
			catch(Exception ei)
			{
				valid=true;
				setValidate(ei.getMessage());
				ei.printStackTrace();
				return "";
			}
			return "";
		}
	 
	 public String designView()
		{
			setValidate("");
			valid=true;
			myValid=true;
			productDataBean.setProdProductName("");
			productDataBean.setProdProductName1("");
			setFlag(true);
			setFile(null);
			files.clear();
			desginViewList=null;
			return "DesignViewPage";
		}
	 
	 public String searchProjectName()
		{
		 try
		{
			setValidate("");
		
			if(validate3(true))
		    {
				System.out.println("After validation completed"+productDataBean.getProdProductName1());
				setFlag(false);
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				desginViewList=controller.getDesignList(productDataBean);
			
				
		    }
			else
			{
				setFlag(true);
			}
			
		}
		catch(Exception ei)
		{
			valid=true;
			setValidate(ei.getMessage());
			ei.printStackTrace();
			
		}
			return "";
			
		}

	private boolean validate3(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if(productDataBean.getProdProductName1().equalsIgnoreCase(""))
		{
			if(flag)
			{
			fieldName = CommonValidate.findComponentInRoot("designViewProductName").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Choose the Project Name."));
			}
			valid = false;
		}
		return valid;
	}
	 public String onLoadMethod()
		{
		 System.out.println("Inside onLoadMethod method calling");
		 	ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			projectList=controller.getDesignProjectList();
			return "";
			
		}
	 public void paint2(OutputStream out, Object data) throws IOException
		{
			String s="C://product/";
			BufferedImage img = ImageIO.read(new File(s+""+productDataBean.getFileName()));
		    ImageIO.write(img,"png",out);
		}
	 public String viewByProject()
		{
		 System.out.println("Inside onLoadMethod method calling");
		 	ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			designList=controller.getDesignListView(productDataBean);
			System.out.println("size"+designList.size());
			if(designList.size() > 0){
				productDataBean.setDesignDate(designList.get(0).getDate());
				productDataBean.setFileName(designList.get(0).getFilePath());
				productDataBean.setDesignDesc(designList.get(0).getDescription());
			}
			return "";
			
		}
	 public String designEdit()
		{
		 System.out.println("Inside onLoadMethod method calling");
		 	ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			designList=controller.getDesignListView(productDataBean);
			System.out.println("size"+designList.size());
			if(designList.size() > 0){
				productDataBean.setDesignDate(designList.get(0).getDate());
				productDataBean.setFileName(designList.get(0).getFilePath());
				productDataBean.setDesignDesc(designList.get(0).getDescription());
				
				productList=new ArrayList<String>();
				productList=controller.getDesignProList();
				System.out.println("name "+productList);
			}
			return "success";
			
		}
	 public String designEditSubmit()
		{
			try
			{
				setValidate("");
				System.out.println("files.size() "+files.size());
				if(validate4(true))
			    {
					System.out.println("-->> validation true");
					productDataBean.setFile(file);
					ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
					controller = (LiusenController) ctx.getBean("controller");
					String s=controller.designEditSubmit(productDataBean);
					if(s.equalsIgnoreCase("Success")){
						valid=false;
					}else{
						valid=true;
					}
					
			    }
				else
				{
					System.out.println("-->> validation fails");
					valid=true;
				}
				
			}
			catch(Exception ei)
			{
				valid=true;
				setValidate(ei.getMessage());
				ei.printStackTrace();
				return "";
			}
			return "";
		}

	private boolean validate4(boolean flag) {
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if(productDataBean.getDesignProject().equalsIgnoreCase(""))
		{
			if(flag)
			{
			fieldName = CommonValidate.findComponentInRoot("prod_name").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Choose the Project Name."));
			}
			valid = false;
		}
		if(productDataBean.getDesignDate()==null)
		{
			if(flag)
			{
			fieldName = CommonValidate.findComponentInRoot("date").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
			}
			valid = false;
		}
		
		/*if(files.size()==0)
		{
			if(flag)
			{
			fieldName = CommonValidate.findComponentInRoot("upload").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Upload Photo."));
			}
			valid = false;
		}*/		
		return valid;
	}
	 public String designdelete()
		{
			try
			{
				setValidate("");
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (LiusenController) ctx.getBean("controller");
				String s=controller.designDelete(productDataBean);
				if(s.equalsIgnoreCase("Success")){
					myValid=false;
				}else{
					myValid=true;
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return "";
		}
	 
	 public String validstock;
	 public boolean stockbox=false;
	 public boolean isStockbox() {
		return stockbox;
	}

	public void setStockbox(boolean stockbox) {
		this.stockbox = stockbox;
	}

	public String getValidstock() {
		return validstock;
	}

	public void setValidstock(String validstock) {
		this.validstock = validstock;
	}

	public String openingStock()
	 {
		 System.out.println("add opening stock -- mb "+productDataBean.getOpeningStock());
		 ApplicationContext ctx=null;
		 LiusenController controller=null;
		 stockbox=false;
		 try
		 {			
			 ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			 controller=(LiusenController)ctx.getBean("controller");
			 controller.openingStock(productDataBean);
			 stockbox=true;
		 }
		 catch(LiusenException e)
		 {
			 e.printStackTrace();
			 setValidstock(e.getMessage());
		 }		
		return ""; 
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