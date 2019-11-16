package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the product_subcategory2 database table.
 * 
 */
@Entity
@Table(name="product_subcategory2")
@NamedQuery(name="ProductSubcategory2.findAll", query="SELECT p FROM ProductSubcategory2 p")
public class ProductSubcategory2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int subcategory2_ID;

	private String status;

	@Column(name="subcategory2_name")
	private String subcategory2Name;

	public ProductSubcategory2() {
	}

	public int getSubcategory2_ID() {
		return this.subcategory2_ID;
	}

	public void setSubcategory2_ID(int subcategory2_ID) {
		this.subcategory2_ID = subcategory2_ID;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubcategory2Name() {
		return this.subcategory2Name;
	}

	public void setSubcategory2Name(String subcategory2Name) {
		this.subcategory2Name = subcategory2Name;
	}

}