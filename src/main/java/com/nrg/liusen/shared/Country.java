package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the country database table.
 * 
 */
@Entity
@NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int country_ID;

	private String country_ISO;

	private String country_ISO3;

	private String country_Name;

	private String country_Nickname;

	private String country_NumCode;

	private String country_PhoneCode;

	//bi-directional many-to-one association to Customer
	@OneToMany(mappedBy="country")
	private List<Customer> customers;

	//bi-directional many-to-one association to Vendor
	@OneToMany(mappedBy="country")
	private List<Vendor> vendors;

	public Country() {
	}

	public int getCountry_ID() {
		return this.country_ID;
	}

	public void setCountry_ID(int country_ID) {
		this.country_ID = country_ID;
	}

	public String getCountry_ISO() {
		return this.country_ISO;
	}

	public void setCountry_ISO(String country_ISO) {
		this.country_ISO = country_ISO;
	}

	public String getCountry_ISO3() {
		return this.country_ISO3;
	}

	public void setCountry_ISO3(String country_ISO3) {
		this.country_ISO3 = country_ISO3;
	}

	public String getCountry_Name() {
		return this.country_Name;
	}

	public void setCountry_Name(String country_Name) {
		this.country_Name = country_Name;
	}

	public String getCountry_Nickname() {
		return this.country_Nickname;
	}

	public void setCountry_Nickname(String country_Nickname) {
		this.country_Nickname = country_Nickname;
	}

	public String getCountry_NumCode() {
		return this.country_NumCode;
	}

	public void setCountry_NumCode(String country_NumCode) {
		this.country_NumCode = country_NumCode;
	}

	public String getCountry_PhoneCode() {
		return this.country_PhoneCode;
	}

	public void setCountry_PhoneCode(String country_PhoneCode) {
		this.country_PhoneCode = country_PhoneCode;
	}

	public List<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public Customer addCustomer(Customer customer) {
		getCustomers().add(customer);
		customer.setCountry(this);

		return customer;
	}

	public Customer removeCustomer(Customer customer) {
		getCustomers().remove(customer);
		customer.setCountry(null);

		return customer;
	}

	public List<Vendor> getVendors() {
		return this.vendors;
	}

	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}

	public Vendor addVendor(Vendor vendor) {
		getVendors().add(vendor);
		vendor.setCountry(this);

		return vendor;
	}

	public Vendor removeVendor(Vendor vendor) {
		getVendors().remove(vendor);
		vendor.setCountry(null);

		return vendor;
	}

}