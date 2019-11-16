package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the limit database table.
 * 
 */
@Entity
@NamedQuery(name="Limit.findAll", query="SELECT l FROM Limit l")
public class Limit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int limit_ID;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_ID")
	private Product product;
	public Limit() {
	}

	public int getLimit_ID() {
		return this.limit_ID;
	}

	public void setLimit_ID(int limit_ID) {
		this.limit_ID = limit_ID;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


}