package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the vendor database table.
 * 
 */
@Entity
@NamedQuery(name="Vendor.findAll", query="SELECT v FROM Vendor v")
public class Vendor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int vendor_ID;

	@Column(name="account_no")
	private String accountNo;

	private String address;

	@Column(name="bank_name")
	private String bankName;

	private String city;

	@Temporal(TemporalType.DATE)
	@Column(name="edit_date")
	private Date editDate;

	@Column(name="edit_login_status")
	private String editLoginStatus;

	@Column(name="edit_status")
	private String editStatus;

	private String email;

	@Column(name="fax_number")
	private String faxNumber;

	@Column(name="firm_name")
	private String firmName;

	@Column(name="firm_type")
	private String firmType;

	@Column(name="login_status")
	private String loginStatus;

	@Column(name="nature_business")
	private String natureBusiness;

	private String note;

	@Column(name="person_incharge")
	private String personIncharge;

	@Column(name="person_incharge2")
	private String personIncharge2;

	@Column(name="phone_number")
	private String phoneNumber;

	@Column(name="phone_number2")
	private String phoneNumber2;

	private String standards;

	private String state;

	private String status;

	@Column(name="tax_number")
	private String taxNumber;

	@Column(name="telephone_no2")
	private String telephoneNo2;

	@Column(name="telephone_number")
	private String telephoneNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="ven_reg_date")
	private Date venRegDate;

	@Column(name="ven_reg_time")
	private Timestamp venRegTime;

	@Column(name="web_address")
	private String webAddress;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="vendor")
	private List<Product> products;

	//bi-directional many-to-one association to Purchase
	@OneToMany(mappedBy="vendor")
	private List<Purchase> purchases;

	//bi-directional many-to-one association to RawMaterial
	@OneToMany(mappedBy="vendor")
	private List<RawMaterial> rawMaterials;

	//bi-directional many-to-one association to Country
	@ManyToOne
	@JoinColumn(name="country_ID")
	private Country country;

	//bi-directional many-to-one association to VendorProduct
	@OneToMany(mappedBy="vendor")
	private List<VendorProduct> vendorProducts;

	public Vendor() {
	}

	public int getVendor_ID() {
		return this.vendor_ID;
	}

	public void setVendor_ID(int vendor_ID) {
		this.vendor_ID = vendor_ID;
	}

	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getEditDate() {
		return this.editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getEditLoginStatus() {
		return this.editLoginStatus;
	}

	public void setEditLoginStatus(String editLoginStatus) {
		this.editLoginStatus = editLoginStatus;
	}

	public String getEditStatus() {
		return this.editStatus;
	}

	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getFirmName() {
		return this.firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getFirmType() {
		return this.firmType;
	}

	public void setFirmType(String firmType) {
		this.firmType = firmType;
	}

	public String getLoginStatus() {
		return this.loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getNatureBusiness() {
		return this.natureBusiness;
	}

	public void setNatureBusiness(String natureBusiness) {
		this.natureBusiness = natureBusiness;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPersonIncharge() {
		return this.personIncharge;
	}

	public void setPersonIncharge(String personIncharge) {
		this.personIncharge = personIncharge;
	}

	public String getPersonIncharge2() {
		return this.personIncharge2;
	}

	public void setPersonIncharge2(String personIncharge2) {
		this.personIncharge2 = personIncharge2;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber2() {
		return this.phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public String getStandards() {
		return this.standards;
	}

	public void setStandards(String standards) {
		this.standards = standards;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaxNumber() {
		return this.taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getTelephoneNo2() {
		return this.telephoneNo2;
	}

	public void setTelephoneNo2(String telephoneNo2) {
		this.telephoneNo2 = telephoneNo2;
	}

	public String getTelephoneNumber() {
		return this.telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public Date getVenRegDate() {
		return this.venRegDate;
	}

	public void setVenRegDate(Date venRegDate) {
		this.venRegDate = venRegDate;
	}

	public Timestamp getVenRegTime() {
		return this.venRegTime;
	}

	public void setVenRegTime(Timestamp venRegTime) {
		this.venRegTime = venRegTime;
	}

	public String getWebAddress() {
		return this.webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setVendor(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setVendor(null);

		return product;
	}

	public List<Purchase> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public Purchase addPurchas(Purchase purchas) {
		getPurchases().add(purchas);
		purchas.setVendor(this);

		return purchas;
	}

	public Purchase removePurchas(Purchase purchas) {
		getPurchases().remove(purchas);
		purchas.setVendor(null);

		return purchas;
	}

	public List<RawMaterial> getRawMaterials() {
		return this.rawMaterials;
	}

	public void setRawMaterials(List<RawMaterial> rawMaterials) {
		this.rawMaterials = rawMaterials;
	}

	public RawMaterial addRawMaterial(RawMaterial rawMaterial) {
		getRawMaterials().add(rawMaterial);
		rawMaterial.setVendor(this);

		return rawMaterial;
	}

	public RawMaterial removeRawMaterial(RawMaterial rawMaterial) {
		getRawMaterials().remove(rawMaterial);
		rawMaterial.setVendor(null);

		return rawMaterial;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<VendorProduct> getVendorProducts() {
		return this.vendorProducts;
	}

	public void setVendorProducts(List<VendorProduct> vendorProducts) {
		this.vendorProducts = vendorProducts;
	}

	public VendorProduct addVendorProduct(VendorProduct vendorProduct) {
		getVendorProducts().add(vendorProduct);
		vendorProduct.setVendor(this);

		return vendorProduct;
	}

	public VendorProduct removeVendorProduct(VendorProduct vendorProduct) {
		getVendorProducts().remove(vendorProduct);
		vendorProduct.setVendor(null);

		return vendorProduct;
	}

}