package com.nrg.liusen.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class PurchaseOrederDataBean {

	public Date purdate; 
	public Date purEstDate;
	public String purVendor;
	public String purserialno;
	public String purProductName;
	public String productId;
	public String purTotalPrice;
	public String purCrossTotal;
	public String purDiscAmmount;
	public String purDiscType;
	public String purNetAmount;
	ArrayList<PurchaseOrederDataBean> PurchaseList=new ArrayList<PurchaseOrederDataBean>();
	ArrayList<PurchaseOrederDataBean> PurchaseViewList=new ArrayList<PurchaseOrederDataBean>();
	ArrayList<PurchaseOrederDataBean> referenceList=new ArrayList<PurchaseOrederDataBean>();
	public String purQuantity;
	public String purQuantity1;
	public String purprice;
	public Date purFromDate;
	public Date purToDate;
	public String purCategory;
	public String purStaffId;
	public String purOrderNo;
	public String purProject;
	public String purVendorId;
	public String deliveryStatus;
	public String paymentStatus;
	public int purchaseid=0;
	public String paidamount;
	public String balamount;
	public String paymenttype;
	public String bankname;
	public String accno;
	public String chequeno;
	public Date chequedate;
	public int payid;
	public int payFM;
	public int payDir;
	public int getPayFM() {
		return payFM;
	}
	public void setPayFM(int payFM) {
		this.payFM = payFM;
	}
	public int getPayDir() {
		return payDir;
	}
	public void setPayDir(int payDir) {
		this.payDir = payDir;
	}
	public int getPayid() {
		return payid;
	}
	public void setPayid(int payid) {
		this.payid = payid;
	}
	public String getPaidamount() {
		return paidamount;
	}
	public void setPaidamount(String paidamount) {
		this.paidamount = paidamount;
	}
	public String getBalamount() {
		return balamount;
	}
	public void setBalamount(String balamount) {
		this.balamount = balamount;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getAccno() {
		return accno;
	}
	public void setAccno(String accno) {
		this.accno = accno;
	}
	public String getChequeno() {
		return chequeno;
	}
	public void setChequeno(String chequeno) {
		this.chequeno = chequeno;
	}
	public Date getChequedate() {
		return chequedate;
	}
	public void setChequedate(Date chequedate) {
		this.chequedate = chequedate;
	}
	public int getPurchaseid() {
		return purchaseid;
	}
	public void setPurchaseid(int purchaseid) {
		this.purchaseid = purchaseid;
	}
	public String getPurRefNum() {
		return purRefNum;
	}
	public void setPurRefNum(String purRefNum) {
		this.purRefNum = purRefNum;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCount1() {
		return count1;
	}
	public void setCount1(String count1) {
		this.count1 = count1;
	}
	public String getCount2() {
		return count2;
	}
	public void setCount2(String count2) {
		this.count2 = count2;
	}
	public String getCount3() {
		return count3;
	}
	public void setCount3(String count3) {
		this.count3 = count3;
	}
	public String getCount4() {
		return count4;
	}
	public void setCount4(String count4) {
		this.count4 = count4;
	}
	public String getPoStatus() {
		return poStatus;
	}
	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}
	public String getPoApprovalStatus() {
		return poApprovalStatus;
	}
	public void setPoApprovalStatus(String poApprovalStatus) {
		this.poApprovalStatus = poApprovalStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public ArrayList<PurchaseOrederDataBean> getPurchaseViewList() {
		return PurchaseViewList;
	}
	public void setPurchaseViewList(ArrayList<PurchaseOrederDataBean> purchaseViewList) {
		PurchaseViewList = purchaseViewList;
	}
	public String getPurStaffName() {
		return purStaffName;
	}
	public void setPurStaffName(String purStaffName) {
		this.purStaffName = purStaffName;
	}
	public String getPurVendorId() {
		return purVendorId;
	}
	public void setPurVendorId(String purVendorId) {
		this.purVendorId = purVendorId;
	}
	public String getPurProjType() {
		return purProjType;
	}
	public void setPurProjType(String purProjType) {
		this.purProjType = purProjType;
	}
	public String getPurProject() {
		return purProject;
	}
	public void setPurProject(String purProject) {
		this.purProject = purProject;
	}
	/**
	 * @return the purOrderNo
	 */
	public String getPurOrderNo() {
		return purOrderNo;
	}
	/**
	 * @param purOrderNo the purOrderNo to set
	 */
	public void setPurOrderNo(String purOrderNo) {
		this.purOrderNo = purOrderNo;
	}
	/**
	 * @return the purCategory
	 */
	public String getPurCategory() {
		return purCategory;
	}
	/**
	 * @param purCategory the purCategory to set
	 */
	public void setPurCategory(String purCategory) {
		this.purCategory = purCategory;
	}
	/**
	 * @return the purStaffId
	 */
	public String getPurStaffId() {
		return purStaffId;
	}
	/**
	 * @param purStaffId the purStaffId to set
	 */
	public void setPurStaffId(String purStaffId) {
		this.purStaffId = purStaffId;
	}
	/**
	 * @return the purFromDate
	 */
	public Date getPurFromDate() {
		return purFromDate;
	}
	/**
	 * @param purFromDate the purFromDate to set
	 */
	public void setPurFromDate(Date purFromDate) {
		this.purFromDate = purFromDate;
	}
	/**
	 * @return the purToDate
	 */
	public Date getPurToDate() {
		return purToDate;
	}
	/**
	 * @param purToDate the purToDate to set
	 */
	public void setPurToDate(Date purToDate) {
		this.purToDate = purToDate;
	}
	/**
	 * @return the purprice
	 */
	public String getPurprice() {
		return purprice;
	}
	/**
	 * @param purprice the purprice to set
	 */
	public void setPurprice(String purprice) {
		this.purprice = purprice;
	}
	/**
	 * @return the purQuantity
	 */
	public String getPurQuantity() {
		return purQuantity;
	}
	/**
	 * @param purQuantity the purQuantity to set
	 */
	public void setPurQuantity(String purQuantity) {
		this.purQuantity = purQuantity;
	}
	/**
	 * @return the purchaseList
	 */
	public ArrayList<PurchaseOrederDataBean> getPurchaseList() {
		return PurchaseList;
	}
	/**
	 * @param purchaseList the purchaseList to set
	 */
	public void setPurchaseList(ArrayList<PurchaseOrederDataBean> purchaseList) {
		PurchaseList = purchaseList;
	}
	/**
	 * @return the purNetAmount
	 */
	public String getPurNetAmount() {
		return purNetAmount;
	}
	/**
	 * @param purNetAmount the purNetAmount to set
	 */
	public void setPurNetAmount(String purNetAmount) {
		this.purNetAmount = purNetAmount;
	}
	/**
	 * @return the purdate
	 */
	public Date getPurdate() {
		return purdate;
	}
	/**
	 * @param purdate the purdate to set
	 */
	public void setPurdate(Date purdate) {
		this.purdate = purdate;
	}
	/**
	 * @return the purEstDate
	 */
	public Date getPurEstDate() {
		return purEstDate;
	}
	/**
	 * @param purEstDate the purEstDate to set
	 */
	public void setPurEstDate(Date purEstDate) {
		this.purEstDate = purEstDate;
	}
	/**
	 * @return the purVendor
	 */
	public String getPurVendor() {
		return purVendor;
	}
	/**
	 * @param purVendor the purVendor to set
	 */
	public void setPurVendor(String purVendor) {
		this.purVendor = purVendor;
	}
	/**
	 * @return the purserialno
	 */
	public String getPurserialno() {
		return purserialno;
	}
	/**
	 * @param purserialno the purserialno to set
	 */
	public void setPurserialno(String purserialno) {
		this.purserialno = purserialno;
	}
	/**
	 * @return the purProductName
	 */
	public String getPurProductName() {
		return purProductName;
	}
	/**
	 * @param purProductName the purProductName to set
	 */
	public void setPurProductName(String purProductName) {
		this.purProductName = purProductName;
	}
	/**
	 * @return the purTotalPrice
	 */
	public String getPurTotalPrice() {
		return purTotalPrice;
	}
	/**
	 * @param purTotalPrice the purTotalPrice to set
	 */
	public void setPurTotalPrice(String purTotalPrice) {
		this.purTotalPrice = purTotalPrice;
	}
	/**
	 * @return the purCrossTotal
	 */
	public String getPurCrossTotal() {
		return purCrossTotal;
	}
	/**
	 * @param purCrossTotal the purCrossTotal to set
	 */
	public void setPurCrossTotal(String purCrossTotal) {
		this.purCrossTotal = purCrossTotal;
	}
	/**
	 * @return the purDiscAmmount
	 */
	public String getPurDiscAmmount() {
		return purDiscAmmount;
	}
	/**
	 * @param purDiscAmmount the purDiscAmmount to set
	 */
	public void setPurDiscAmmount(String purDiscAmmount) {
		this.purDiscAmmount = purDiscAmmount;
	}
	/**
	 * @return the purDiscType
	 */
	public String getPurDiscType() {
		return purDiscType;
	}
	/**
	 * @param purDiscType the purDiscType to set
	 */
	public void setPurDiscType(String purDiscType) {
		this.purDiscType = purDiscType;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String taxType;
	public String purProjType;
	public String purStaffName;
	public String searchType;
	public String unitPrice;
	public String unit;
	public String status;
	public String poStatus;
	public String poApprovalStatus;
	public String count1;
	public String count2;
	public String count3;
	public String count4;
	public String reason;
	public String appType;
	public String purRefNum;
	public String prodF1;
	public String prodF2="none";
	public String quanF1;
	public String quanF2="none";
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	private List<String> projectList=null;
	public List<String> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<String> projectList) {
		this.projectList = projectList;
	}
	public String getProdF1() {
		return prodF1;
	}
	public void setProdF1(String prodF1) {
		this.prodF1 = prodF1;
	}
	public String getProdF2() {
		return prodF2;
	}
	public void setProdF2(String prodF2) {
		this.prodF2 = prodF2;
	}
	public String getQuanF1() {
		return quanF1;
	}
	public void setQuanF1(String quanF1) {
		this.quanF1 = quanF1;
	}
	public String getQuanF2() {
		return quanF2;
	}
	public void setQuanF2(String quanF2) {
		this.quanF2 = quanF2;
	}
	public String getPurQuantity1() {
		return purQuantity1;
	}
	public void setPurQuantity1(String purQuantity1) {
		this.purQuantity1 = purQuantity1;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public ArrayList<PurchaseOrederDataBean> getReferenceList() {
		return referenceList;
	}
	public void setReferenceList(ArrayList<PurchaseOrederDataBean> referenceList) {
		this.referenceList = referenceList;
	}

	public Date fromDate;
	public Date toDate;
	public Date date;
	public String particulars;
	public String sNo;
	public String clientName;
	public String message;
	public String credit;
	public String debit;
	private String validate;
	public String totalcredit;
	public String totaldebit;
	public String getTotalcredit() {
		return totalcredit;
	}
	public void setTotalcredit(String totalcredit) {
		this.totalcredit = totalcredit;
	}
	public String getTotaldebit() {
		return totaldebit;
	}
	public void setTotaldebit(String totaldebit) {
		this.totaldebit = totaldebit;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public String getsNo() {
		return sNo;
	}
	public void setsNo(String sNo) {
		this.sNo = sNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	
	List<PurchaseOrederDataBean> resulfinal=null;
	public List<PurchaseOrederDataBean> getResulfinal() {
		return resulfinal;
	}
	public void setResulfinal(List<PurchaseOrederDataBean> resulfinal) {
		this.resulfinal = resulfinal;
	}
	public int serialNo;
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	
	public String tick="false";
	public String text="false";
	public String returnqauntity;
	public String dr;
	public String retstatus;
	public String retamount;
	public int purchaserecid;
	public String getTick() {
		return tick;
	}
	public void setTick(String tick) {
		this.tick = tick;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getReturnqauntity() {
		return returnqauntity;
	}
	public void setReturnqauntity(String returnqauntity) {
		this.returnqauntity = returnqauntity;
	}
	public String getDr() {
		return dr;
	}
	public void setDr(String dr) {
		this.dr = dr;
	}
	public String getRetstatus() {
		return retstatus;
	}
	public void setRetstatus(String retstatus) {
		this.retstatus = retstatus;
	}
	public String getRetamount() {
		return retamount;
	}
	public void setRetamount(String retamount) {
		this.retamount = retamount;
	}
	public int getPurchaserecid() {
		return purchaserecid;
	}
	public void setPurchaserecid(int purchaserecid) {
		this.purchaserecid = purchaserecid;
	}
	
	List<PurchaseOrederDataBean> cashList=null;
	public List<PurchaseOrederDataBean> getCashList() {
		return cashList;
	}
	public void setCashList(List<PurchaseOrederDataBean> cashList) {
		this.cashList = cashList;
	}
 }

