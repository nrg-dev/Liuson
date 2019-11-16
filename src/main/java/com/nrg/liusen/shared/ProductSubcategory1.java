package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the product_subcategory1 database table.
 * 
 */
@Entity
@Table(name="product_subcategory1")
@NamedQuery(name="ProductSubcategory1.findAll", query="SELECT p FROM ProductSubcategory1 p")
public class ProductSubcategory1 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int subcategory_ID;

	private String status;

	@Column(name="subcategory_name")
	private String subcategoryName;

	public ProductSubcategory1() {
	}

	public int getSubcategory_ID() {
		return this.subcategory_ID;
	}

	public void setSubcategory_ID(int subcategory_ID) {
		this.subcategory_ID = subcategory_ID;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubcategoryName() {
		return this.subcategoryName;
	}

	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}

}