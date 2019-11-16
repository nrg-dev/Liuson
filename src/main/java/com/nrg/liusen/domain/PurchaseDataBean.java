package com.nrg.liusen.domain;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
/*import java.util.Date;
import java.util.List;
*/
import com.nrg.liusen.shared.Employee;
public class PurchaseDataBean {
	public List<PurchaseDataBean>purlist=null;
	
		public List<PurchaseDataBean> getPurlist() {
		return purlist;
	}
	public void setPurlist(List<PurchaseDataBean> purlist) {
		this.purlist = purlist;
	}
		ArrayList<String> order=new ArrayList<String>();
		List<PurchaseDataBean>res=null;
		List<String> resulfinal1=null;
		public String vendor_phone_number;
		public ArrayList<String> productlist=null;	
		public String firmName;
		public String balanceammount;
		public String sellingPrice;	
		public Date orderDate;
		public Date targentDate;
		public String quantity;
		public int product_ID;
		public String product_name;
		public String totalPrice;
		public String orderNumber;
		public int counter=0;
		public String remaining;
		public String payableAmount;
		public String salesIdReference;
		Date deliveredDate;
		String delayreason;
		String batch;
		public int hid;
		public String marginPrice;
		public String customername;
		public String countryID;
		public Date salesorderdate;
		public String shipingaddress;
		public String phonenumber;
		public String email;
		public String employeeId;
		public String totalnumberofcount;
		public String totalnumberofcount1;
		public Date deliverydate;
		public String note;
		public String shipping_charge;
		public String crosstotal;
		public String shipping_charge1;
		public String crosstotal1;
		public int salesId;
		public String batchNumber;
		public String batchProductName;
		public String batchProductName1;
		public String valueChange;
		public String industry;
		public String status;
		public String status2;
		public String status3;
		public String status4;
		public String purchaseQuantity;
		ArrayList<String> firmname=null;
		ArrayList<String> phoneNumber=null;
		public String filenametemp;
		public String filePathfinal;
		public String filepathfinal1;
		public int count=0;
		public String duedate;
		public int serialNo;
		public String reason;
		private String clientName;
		public String totalAmount;
		public String netAmount;
		public String staffid;
		public String dis;
		public String product;
		public String tick="false";
		public String quanti;
		public String stat;
		public String statusa;
		public String openingStock;
		private String sellPice;
		private String sellPice1;
		
		
		public String getBalanceammount() {
			return balanceammount;
		}
		public void setBalanceammount(String balanceammount) {
			this.balanceammount = balanceammount;
		}
		public List<PurchaseDataBean> getRes() {
			return res;
		}
		public void setRes(List<PurchaseDataBean> res) {
			this.res = res;
		}
		public String getSellPice() {
			return sellPice;
		}
		public void setSellPice(String sellPice) {
			this.sellPice = sellPice;
		}
		public String getOpeningStock() {
			return openingStock;
		}
		public void setOpeningStock(String openingStock) {
			this.openingStock = openingStock;
		}
		public String getStatusa() {
			return statusa;
		}
		public void setStatusa(String statusa) {
			this.statusa = statusa;
		}
		ArrayList<Employee> employeeid=null;
		public String getStat() {
			return stat;
		}
		public void setStat(String stat) {
			this.stat = stat;
		}
			
		public String getSerialNo1() {
			return serialNo1;
		}
		public void setSerialNo1(String serialNo1) {
			this.serialNo1 = serialNo1;
		}
		public String f2="none";
		public String f3="none";
		public String f4="none";
		public String totalquanti;
		public String serialNo1;
		public String ordernum;
		public String priroty="false";
		public String tempFlag1="none";
		public String tempFlag2="none";
		public String nr;
		public String dr;
		public String bankname;
		public String accountno;
	public String paymenttype;
	public Date Chequedate;
	public String Chequeno;
		public int purchaseid;
		public String barcode;
		
		public int getPurchaseid() {
			return purchaseid;
		}
		public void setPurchaseid(int purchaseid) {
			this.purchaseid = purchaseid;
		}
		public String getBarcode() {
			return barcode;
		}
		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}
		public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public Date getChequedate() {
		return Chequedate;
	}
	public void setChequedate(Date chequedate) {
		Chequedate = chequedate;
	}
	public String getChequeno() {
		return Chequeno;
	}
	public void setChequeno(String chequeno) {
		Chequeno = chequeno;
	}
		public String getNr() {
			return nr;
		}
		public void setNr(String nr) {
			this.nr = nr;
		}
		public String getDr() {
			return dr;
		}
		public void setDr(String dr) {
			this.dr = dr;
		}
		public String getOrdernum() {
			return ordernum;
		}
		public void setOrdernum(String ordernum) {
			this.ordernum = ordernum;
		}
		
		public String getPriroty() {
			return priroty;
		}
		public void setPriroty(String priroty) {
			this.priroty = priroty;
		}
		public String getTempFlag1() {
			return tempFlag1;
		}
		public void setTempFlag1(String tempFlag1) {
			this.tempFlag1 = tempFlag1;
		}
		public String getTempFlag2() {
			return tempFlag2;
		}
		public void setTempFlag2(String tempFlag2) {
			this.tempFlag2 = tempFlag2;
		}
		
		public String getTotalquanti() {
			return totalquanti;
		}
		public void setTotalquanti(String totalquanti) {
			this.totalquanti = totalquanti;
		}
		
		public String getF2() {
			return f2;
		}
		public void setF2(String f2) {
			this.f2 = f2;
		}
		public String getF3() {
			return f3;
		}
		public void setF3(String f3) {
			this.f3 = f3;
		}
		public String getF4() {
			return f4;
		}
		public void setF4(String f4) {
			this.f4 = f4;
		}
		public String getTick() {
			return tick;
		}
		public void setTick(String tick) {
			this.tick = tick;
		}
		public String getQuanti() {
			return quanti;
		}
		public void setQuanti(String quanti) {
			this.quanti = quanti;
		}
		public String getProduct() {
			return product;
		}
		public void setProduct(String product) {
			this.product = product;
		}
		
		public String getDis() {
			return dis;
		}
		public void setDis(String dis) {
			this.dis = dis;
		}
		public String getStaffid() {
			return staffid;
		}
		public void setStaffid(String staffid) {
			this.staffid = staffid;
		}
		public String getNetAmount() {
			return netAmount;
		}
		public void setNetAmount(String netAmount) {
			this.netAmount = netAmount;
		}
		public String getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(String totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(String employeeId) {
			this.employeeId = employeeId;
		}
		public ArrayList<Employee> getEmployeeid() {
			return employeeid;
		}
		public void setEmployeeid(ArrayList<Employee> employeeid) {
			this.employeeid = employeeid;
		}
		
	public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getClientName() {
			return clientName;
		}
		public void setClientName(String clientName) {
			this.clientName = clientName;
		}
	public int getSerialNo() {
			return serialNo;
		}
		public void setSerialNo(int serialNo) {
			this.serialNo = serialNo;
		}
	public Date fromDate;
		
		public Date getFromDate() {
			return fromDate;
		}
		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}
		public String getDuedate() {
			return duedate;
		}
		public void setDuedate(String duedate) {
			this.duedate = duedate;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public String getFilepathfinal1() 
		{
			return filepathfinal1;
		}
		public void setFilepathfinal1(String filepathfinal1) {
			this.filepathfinal1 = filepathfinal1;
		}
		public String getFilePathfinal()
		{
			return filePathfinal;
		}
		public void setFilePathfinal(String filePathfinal)
		{
			this.filePathfinal = filePathfinal;
		}
		public String getFilenametemp()
		{
			return filenametemp;
		}
		public void setFilenametemp(String filenametemp) 
		{
			this.filenametemp = filenametemp;
		}
		
		public ArrayList<String> getFirmname()
		{
			return firmname;
		}
		public void setFirmname(ArrayList<String> firmname) 
		{
			this.firmname = firmname;
		}
		public ArrayList<String> getPhoneNumber()
		{
			return phoneNumber;
		}
		public void setPhoneNumber(ArrayList<String> phoneNumber) 
		{
			this.phoneNumber = phoneNumber;
		}
		
		public String getPurchaseQuantity()
		{
			return purchaseQuantity;
		}
		public void setPurchaseQuantity(String purchaseQuantity) 
		{
			this.purchaseQuantity = purchaseQuantity;
		}
		public ArrayList<String> getOrder()
		{
			return order;
		}
		public void setOrder(ArrayList<String> order)
		{
			this.order = order;
		}
		public String getStatus2()
		{
			return status2;
		}
		public void setStatus2(String status2)
		{
			this.status2 = status2;
		}
		public String getStatus3() {
			return status3;
		}
		public void setStatus3(String status3) {
			this.status3 = status3;
		}
		public String getStatus4() {
			return status4;
		}
		public void setStatus4(String status4) {
			this.status4 = status4;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getIndustry() {
			return industry;
		}
		public void setIndustry(String industry) {
			this.industry = industry;
		}
	
		public String customerName;
		public String address;
		public String telephonenumber;
		public String quantity1;
		public String actualPrice;
		
		public String getQuantity1() 
		{
			return quantity1;
		}
		public void setQuantity1(String quantity1)
		{
			this.quantity1 = quantity1;
		}
		public int getHid() {
			return hid;
		}
		public void setHid(int hid) {
			this.hid = hid;
		}
		public List<String> getResulfinal1() {
			return resulfinal1;
		}
		public void setResulfinal1(List<String> resulfinal1) {
			this.resulfinal1 = resulfinal1;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getTelephonenumber() {
			return telephonenumber;
		}
		public void setTelephonenumber(String telephonenumber) {
			this.telephonenumber = telephonenumber;
		}
		
		public String getValueChange()
		{
			return valueChange;
		}
		public void setValueChange(String valueChange)
		{
			this.valueChange = valueChange;
		}
		
		public Date toDate;	
		
		public String getBatchNumber()
		{
			return batchNumber;
		}
		public void setBatchNumber(String batchNumber)
		{
			this.batchNumber = batchNumber;
		}
		
		public Date getToDate()
		{
			return toDate;
		}
		public void setToDate(Date toDate)
		{
			this.toDate = toDate;
		}
		
		
		public String getSalesIdReference() {
			return salesIdReference;
		}
		public void setSalesIdReference(String salesIdReference) {
			this.salesIdReference = salesIdReference;
		}
		
		public String getMarginPrice() {
			return marginPrice;
		}
		public void setMarginPrice(String marginPrice) {
			this.marginPrice = marginPrice;
		}
		public String getBatchProductName() {
			return batchProductName;
		}
		public void setBatchProductName(String batchProductName) {
			this.batchProductName = batchProductName;
		}
		public String getBatchProductName1() {
			return batchProductName1;
		}
		public void setBatchProductName1(String batchProductName1) {
			this.batchProductName1 = batchProductName1;
		}
		
		public int getSalesId() {
			return salesId;
		}
		public void setSalesId(int salesId) {
			this.salesId = salesId;
		}
		public String getCustomername()
		{
			return customername;
		}
		public void setCustomername(String customername)
		{
			this.customername = customername;
		}
		public String getCountryID() 
		{
			return countryID;
		}
		public void setCountryID(String countryID)
		{
			this.countryID = countryID;
		}
		public Date getSalesorderdate()
		{
			return salesorderdate;
		}
		public void setSalesorderdate(Date salesorderdate)
		{
			this.salesorderdate = salesorderdate;
		}
		public String getShipingaddress()
		{
			return shipingaddress;
		}
		public void setShipingaddress(String shipingaddress) 
		{
			this.shipingaddress = shipingaddress;
		}
		public String getPhonenumber() 
		{
			return phonenumber;
		}
		public void setPhonenumber(String phonenumber)
		{
			this.phonenumber = phonenumber;
		}
		public String getEmail() 
		{
			return email;
		}
		public void setEmail(String email) 
		{
			this.email = email;
		}
		public String getTotalnumberofcount()
		{
			return totalnumberofcount;
		}
		public void setTotalnumberofcount(String totalnumberofcount)
		{
			this.totalnumberofcount = totalnumberofcount;
		}
		public String getTotalnumberofcount1()
		{
			return totalnumberofcount1;
		}
		public void setTotalnumberofcount1(String totalnumberofcount1)
		{
			this.totalnumberofcount1 = totalnumberofcount1;
		}
		public Date getDeliverydate()
		{
			return deliverydate;
		}
		public void setDeliverydate(Date deliverydate)
		{
			this.deliverydate = deliverydate;
		}
		public String getNote()
		{
			return note;
		}
		public void setNote(String note) 
		{
			this.note = note;
		}	
		public String getShipping_charge()
		{
			return shipping_charge;
		}
		public void setShipping_charge(String shipping_charge)
		{
			this.shipping_charge = shipping_charge;
		}
		public String getCrosstotal() 
		{
			return crosstotal;
		}
		public void setCrosstotal(String crosstotal)
		{
			this.crosstotal = crosstotal;
		}
		public String getShipping_charge1() 
		{
			return shipping_charge1;
		}
		public void setShipping_charge1(String shipping_charge1)
		{
			this.shipping_charge1 = shipping_charge1;
		}
		public String getCrosstotal1() 
		{
			return crosstotal1;
		}
		public void setCrosstotal1(String crosstotal1)
		{
			this.crosstotal1 = crosstotal1;
		}


		public String getBatch()
		
		{
			return batch;
		}
		public void setBatch(String batch) 
		{
			this.batch = batch;
		}


		public Date getDeliveredDate()
		{
			return deliveredDate;
		}
		public void setDeliveredDate(Date deliveredDate)
		{
			this.deliveredDate = deliveredDate;
		}
		public String getDelayreason() 
		{
			return delayreason;
		}
		public void setDelayreason(String delayreason)
		{
			this.delayreason = delayreason;
		}



		public String getPayableAmount()
		{
			return payableAmount;
		}
		public void setPayableAmount(String payableAmount)
		{
			this.payableAmount = payableAmount;
		}
		public String getRemaining()
		{
				return remaining;
		}
		public void setRemaining(String remaining)
		{
			this.remaining = remaining;
		}
		





		public int getCounter() 
		{
			return counter;
		}



		public void setCounter(int counter)
		{
			this.counter = counter;
		}





		public String getOrderNumber()
		{
			return orderNumber;
		}



		public void setOrderNumber(String orderNumber)
		{
			this.orderNumber = orderNumber;
		}



		public String getTotalPrice()
		{
			return totalPrice;
		}



		public void setTotalPrice(String totalPrice)
		{
			this.totalPrice = totalPrice;
		}




		public int getProduct_ID()
		{
			return product_ID;
		}



		public void setProduct_ID(int product_ID) 
		{
			this.product_ID = product_ID;
		}



		public String getProduct_name()
		{
			return product_name;
		}



		public void setProduct_name(String product_name) 
		{
			this.product_name = product_name;
		}


		public Date getOrderDate()
		{
			return orderDate;
		}



		public void setOrderDate(Date orderDate)
		{
			this.orderDate = orderDate;
		}



		public Date getTargentDate()
		{
			return targentDate;
		}



		public void setTargentDate(Date targentDate)
		{
			this.targentDate = targentDate;
		}



		public String getQuantity()
		{
			return quantity;
		}



		public void setQuantity(String quantity) 
		{
			this.quantity = quantity;
		}



		

		public String getSellingPrice()
		{
			return sellingPrice;
		}



		public void setSellingPrice(String sellingPrice)
		{
			this.sellingPrice = sellingPrice;
		}



		public String getFirmName()
		{
			return firmName;
		}



		public void setFirmName(String firmName)
		{
			this.firmName = firmName;
		}

		public String getVendor_phone_number() 
		{
			return vendor_phone_number;
		}
		public void setVendor_phone_number(String vendor_phone_number) 
		{
			this.vendor_phone_number = vendor_phone_number;
		}
	
		public ArrayList<String> getProductlist() 
		{
			return productlist;
		}
		public void setProductlist(ArrayList<String> productlist) 
		{
			this.productlist = productlist;
		}
		
		
		/*udhaya 2.1.2015*/
		
		
		public String grosstotal;


		public String getGrosstotal() {
			return grosstotal;
		}
		public void setGrosstotal(String grosstotal) {
			this.grosstotal = grosstotal;
		};
		
		private String particulars;
		public String sNo;
		private String debit;
		private String credit;
		

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
		public String getDebit() {
			return debit;
		}
		public void setDebit(String debit) {
			this.debit = debit;
		}
		public String getCredit() {
			return credit;
		}
		public void setCredit(String credit) {
			this.credit = credit;
		}
		public String paymentDueDate;


		public String getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public String getSellPice1() {
		return sellPice1;
	}
	public void setSellPice1(String sellPice1) {
		this.sellPice1 = sellPice1;
	}
	
	
	}

	
	



