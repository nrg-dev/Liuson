package com.nrg.liusen.domain;

import java.io.Serializable;
import java.util.Date;
import org.richfaces.model.UploadedFile;

/**
 * 
 * @author Robert Arjun
 * @date 23-10-2015
 * @copyright NRG This class used to hold the data( POJO Class )
 */

public class ProductDataBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prodCategory;
	private String prodProductName;
	private String prodProductCode;
	private String prodUnit;
	private String prodSize;
	private String prodBrand;
	private String prodColor;
	private String prodVendor;
	private String prodStandard;
	private String prodMarketPrice;
	private String prodActualPrice;
	private String prodDescription;
	private String proRawCategory;
	private String proRawCategory1;
	private String prodProductName1;
	private String prodCategory1;
	private String prodSerCategory1;
	private String prodSerCategory2;
	
	
	
	/**
	 * @return the prodSerCategory1
	 */
	public String getProdSerCategory1() {
		return prodSerCategory1;
	}
	/**
	 * @param prodSerCategory1 the prodSerCategory1 to set
	 */
	public void setProdSerCategory1(String prodSerCategory1) {
		this.prodSerCategory1 = prodSerCategory1;
	}
	/**
	 * @return the prodSerCategory2
	 */
	public String getProdSerCategory2() {
		return prodSerCategory2;
	}
	/**
	 * @param prodSerCategory2 the prodSerCategory2 to set
	 */
	public void setProdSerCategory2(String prodSerCategory2) {
		this.prodSerCategory2 = prodSerCategory2;
	}
	/**
	 * @return the prodCategory1
	 */
	public String getProdCategory1() {
		return prodCategory1;
	}
	/**
	 * @param prodCategory1 the prodCategory1 to set
	 */
	public void setProdCategory1(String prodCategory1) {
		this.prodCategory1 = prodCategory1;
	}
	/**
	 * @return the prodProductName1
	 */
	public String getProdProductName1() {
		return prodProductName1;
	}
	/**
	 * @param prodProductName1 the prodProductName1 to set
	 */
	public void setProdProductName1(String prodProductName1) {
		this.prodProductName1 = prodProductName1;
	}
	/**
	 * @return the prodCategory
	 */
	public String getProdCategory() {
		return prodCategory;
	}
	/**
	 * @param prodCategory the prodCategory to set
	 */
	public void setProdCategory(String prodCategory) {
		this.prodCategory = prodCategory;
	}
	/**
	 * @return the prodProductName
	 */
	public String getProdProductName() {
		return prodProductName;
	}
	/**
	 * @param prodProductName the prodProductName to set
	 */
	public void setProdProductName(String prodProductName) {
		this.prodProductName = prodProductName;
	}
	/**
	 * @return the prodProductCode
	 */
	public String getProdProductCode() {
		return prodProductCode;
	}
	/**
	 * @param prodProductCode the prodProductCode to set
	 */
	public void setProdProductCode(String prodProductCode) {
		this.prodProductCode = prodProductCode;
	}
	/**
	 * @return the prodUnit
	 */
	public String getProdUnit() {
		return prodUnit;
	}
	/**
	 * @param prodUnit the prodUnit to set
	 */
	public void setProdUnit(String prodUnit) {
		this.prodUnit = prodUnit;
	}
	/**
	 * @return the prodSize
	 */
	public String getProdSize() {
		return prodSize;
	}
	/**
	 * @param prodSize the prodSize to set
	 */
	public void setProdSize(String prodSize) {
		this.prodSize = prodSize;
	}
	/**
	 * @return the prodBrand
	 */
	public String getProdBrand() {
		return prodBrand;
	}
	/**
	 * @param prodBrand the prodBrand to set
	 */
	public void setProdBrand(String prodBrand) {
		this.prodBrand = prodBrand;
	}
	/**
	 * @return the prodColor
	 */
	public String getProdColor() {
		return prodColor;
	}
	/**
	 * @param prodColor the prodColor to set
	 */
	public void setProdColor(String prodColor) {
		this.prodColor = prodColor;
	}
	/**
	 * @return the prodVendor
	 */
	public String getProdVendor() {
		return prodVendor;
	}
	/**
	 * @param prodVendor the prodVendor to set
	 */
	public void setProdVendor(String prodVendor) {
		this.prodVendor = prodVendor;
	}
	/**
	 * @return the prodStandard
	 */
	public String getProdStandard() {
		return prodStandard;
	}
	/**
	 * @param prodStandard the prodStandard to set
	 */
	public void setProdStandard(String prodStandard) {
		this.prodStandard = prodStandard;
	}
	/**
	 * @return the prodMarketPrice
	 */
	public String getProdMarketPrice() {
		return prodMarketPrice;
	}
	/**
	 * @param prodMarketPrice the prodMarketPrice to set
	 */
	public void setProdMarketPrice(String prodMarketPrice) {
		this.prodMarketPrice = prodMarketPrice;
	}
	/**
	 * @return the prodActualPrice
	 */
	public String getProdActualPrice() {
		return prodActualPrice;
	}
	/**
	 * @param prodActualPrice the prodActualPrice to set
	 */
	public void setProdActualPrice(String prodActualPrice) {
		this.prodActualPrice = prodActualPrice;
	}
	/**
	 * @return the prodDescription
	 */
	public String getProdDescription() {
		return prodDescription;
	}
	/**
	 * @param prodDescription the prodDescription to set
	 */
	public void setProdDescription(String prodDescription) {
		this.prodDescription = prodDescription;
	}
	/**
	 * @return the proRawCategory
	 */
	public String getProRawCategory() {
		return proRawCategory;
	}
	/**
	 * @param proRawCategory the proRawCategory to set
	 */
	public void setProRawCategory(String proRawCategory) {
		this.proRawCategory = proRawCategory;
	}
	/**
	 * @return the proRawCategory1
	 */
	public String getProRawCategory1() {
		return proRawCategory1;
	}
	/**
	 * @param proRawCategory1 the proRawCategory1 to set
	 */
	public void setProRawCategory1(String proRawCategory1) {
		this.proRawCategory1 = proRawCategory1;
	}
	public Date date;
	UploadedFile file;
	
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	private String imagepath;
	public String getImagepath() {
		return this.imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private String designProject;
	private String designDesc;
	private Date designDate;
	private String fileName;



	public String getDesignProject() {
		return designProject;
	}
	public void setDesignProject(String designProject) {
		this.designProject = designProject;
	}
	public String getDesignDesc() {
		return designDesc;
	}
	public void setDesignDesc(String designDesc) {
		this.designDesc = designDesc;
	}
	public Date getDesignDate() {
		return designDate;
	}
	public void setDesignDate(Date designDate) {
		this.designDate = designDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String openingStock;
	public int purchaseid;
	public int stockid;
	public int batchid;

	public int getPurchaseid() {
		return purchaseid;
	}
	public void setPurchaseid(int purchaseid) {
		this.purchaseid = purchaseid;
	}
	public int getStockid() {
		return stockid;
	}
	public void setStockid(int stockid) {
		this.stockid = stockid;
	}
	public int getBatchid() {
		return batchid;
	}
	public void setBatchid(int batchid) {
		this.batchid = batchid;
	}
	public String getOpeningStock() {
		return openingStock;
	}
	public void setOpeningStock(String openingStock) {
		this.openingStock = openingStock;
	}
}
