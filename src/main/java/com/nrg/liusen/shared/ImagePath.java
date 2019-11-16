package com.nrg.liusen.shared;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the image_path database table.
 * 
 */
@Entity
@Table(name="image_path")
@NamedQuery(name="ImagePath.findAll", query="SELECT i FROM ImagePath i")
public class ImagePath implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="p_id")
	private int pId;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String description;

	@Column(name="file_path")
	private String filePath;

	private String status;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;

	public ImagePath() {
	}

	public int getPId() {
		return this.pId;
	}

	public void setPId(int pId) {
		this.pId = pId;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}