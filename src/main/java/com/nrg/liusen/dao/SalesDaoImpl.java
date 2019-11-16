package com.nrg.liusen.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.shared.Barcode;
import com.nrg.liusen.shared.Batch;
import com.nrg.liusen.shared.Employee;
import com.nrg.liusen.shared.Invoice;
import com.nrg.liusen.shared.Payment;
import com.nrg.liusen.shared.Product;
import com.nrg.liusen.shared.Projet;
import com.nrg.liusen.shared.Sale;
import com.nrg.liusen.shared.SalesApproval;
import com.nrg.liusen.shared.SalesRecord;
import com.nrg.liusen.shared.SalesReturn;
import com.nrg.liusen.shared.StockDamage;
import com.nrg.liusen.shared.StockoutManual;

/**
 * This Java Class will communicate with Database
 * @author Udhaya
 * @date 30-11-2015
 * @copyright NRG      
 */
@Repository
@Singleton
public class SalesDaoImpl implements SalesDao
{
	@PersistenceContext(name="liusen-pu")
	EntityManager entityManager;
	
	/*Retreive the customer name*/
	public void dropCustomer(SalesOrderDataBean salesOrderDataBean) 
	{
		Query v=null;
		/*v=entityManager.createQuery("select customerName from Customer where status='Active'");
		List<Customer> customer=(List<Customer>) v.getResultList();
		salesOrderDataBean.setCustomerNames(customer);
		System.out.println("customer -- >. "+customer);*/
		v=entityManager.createQuery("select projectName from Projet where status='Active'");
		List<String> projet=(List<String>) v.getResultList();
		salesOrderDataBean.setProjetnames(projet);
		System.out.println("projet -- >. "+projet);
		v=null;
		v=entityManager.createQuery("select employee_details_ID from Employee where status='Active'");
		List<Employee> employee=(List<Employee>) v.getResultList();
		salesOrderDataBean.setEmployeeId(employee);
		System.out.println("employee id -- >. "+employee);
	}
	
//	Category change event
	public void categoryChange(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("category dao --- > "+salesOrderDataBean.getSaleCategory());
		Query v=null;
		try
		{
			if(salesOrderDataBean.getSaleCategory().equals("SALC001"))
			{
				System.out.println("inside own product");
				v=entityManager.createQuery("select productName from Product where category=? and status='Active'");
				v.setParameter(1, "Product Trading");
				List<String> product=(List<String>)v.getResultList();
				salesOrderDataBean.setProduct(product);
				System.out.println("products -1- >> "+product);
			}
			else if(salesOrderDataBean.getSaleCategory().equals("SALC002"))
			{
				System.out.println("inside project");
				v=entityManager.createQuery("select productName from Product where category=? and status='Active'");
				v.setParameter(1, "Project");
				List<String> proproduct=(List<String>)v.getResultList();				
				salesOrderDataBean.setProduct(proproduct);
				System.out.println("products -- >> "+proproduct);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
//	product change event
	public void productChange(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("product dao -- >. "+salesOrderDataBean.getSaleProductName());
		Query v=null;
		try		
		{
			if(salesOrderDataBean.getSaleCategory().equalsIgnoreCase("SALC001"))
			{
				v=entityManager.createQuery("from Product where productName=?");
				v.setParameter(1, salesOrderDataBean.getSaleProductName());
				List<Product> pp=(List<Product>)v.getResultList();
				salesOrderDataBean.setSalePrice(pp.get(0).getActualPrice());
				System.out.println("price -- >. "+salesOrderDataBean.getSalePrice());
			}
			else if(salesOrderDataBean.getSaleCategory().equalsIgnoreCase("SALC002"))
			{
				v=entityManager.createQuery("from Product where productName=?");
				v.setParameter(1, salesOrderDataBean.getSaleProductName());
				List<Product> pp=(List<Product>)v.getResultList();
				salesOrderDataBean.setSalePrice(pp.get(0).getActualPrice());
				System.out.println("price -- >. "+salesOrderDataBean.getSalePrice());
			}
			else
			{
				System.out.println("Error");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
//	quantity change event
	public void quantityChange(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		Query v=null;
		try
		{
			v=entityManager.createQuery("from Batch where productName=?");
			v.setParameter(1, salesOrderDataBean.getSaleProductName());
			List<Batch> batch=(List<Batch>) v.getResultList();
			System.out.println("batch size -- >> "+batch.size());
			salesOrderDataBean.setBatch(batch);			
			if(batch.size()>0)
			{
				int batchId=0;
				batchId=batch.get(0).getBatch_ID();
				System.out.println("batch id -- >. "+batchId);
				v=null;
				v=entityManager.createQuery("from Barcode where batch_ID=?");
				v.setParameter(1, batchId);
				List<Barcode> barcode=(List<Barcode>) v.getResultList();
				System.out.println("barcode size -- >> "+barcode.size());
				salesOrderDataBean.setBarcode(barcode);
				if(batch.size()>0)
				{
					if(Integer.parseInt(salesOrderDataBean.getSaleQuantity())>Integer.parseInt(barcode.get(0).getStockIn()))
					{
						throw new LiusenException("Only "+barcode.get(0).getStockIn()+" Stock in Warehouse");
					}
				}				
			}
			else
			{
				throw new LiusenException("This Product not in Stock");
			}
		}
		finally
		{
			
		}
	}
	
	/*SalesOrderInsertion in sales table*/
	@Transactional(value="transactionManager")
	public String salesInsertion(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("sales order insertion in dao");
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		System.out.println("ct date -> "+date+" time - > "+time);
		Calendar now= Calendar.getInstance();
		String month=new SimpleDateFormat("MMM").format(now.getTime());
		Query v=null;int c=0;int cusid=0;
		try
		{
			v=entityManager.createQuery("from Sale");
			List<Sale> sales=(List<Sale>)v.getResultList();
			String orderno="";
			if(sales.size()>0)
			{
				c=sales.size()+1;
				System.out.println("size -- >"+c);
				if(c<=9)
				{
					orderno="SO000"+c+"/"+month+"/"+now.get(Calendar.YEAR);
					System.out.println("no 1- > "+orderno);
				}
				else if(c>9 && c<=99)
				{
					orderno="SO00"+c+"/"+month+"/"+now.get(Calendar.YEAR);
					System.out.println("no 2- > "+orderno);
				}
				else if(c>=99)
				{
					orderno="SO0"+c+"/"+month+"/"+now.get(Calendar.YEAR);
					System.out.println("no 3- > "+orderno);
				}
			}
			else
			{	
				c=1;
				orderno="SO0001"+"/"+month+"/"+now.get(Calendar.YEAR);
				System.out.println("no - > "+orderno);
			}
			salesOrderDataBean.setSaleSalesOrderNo(orderno);
			v=null;
			v=entityManager.createQuery("from Employee where employee_details_ID=? and status='Active'");
			v.setParameter(1, salesOrderDataBean.getSaleStaffId());
			List<Employee> emp=(List<Employee>) v.getResultList();
			int empid=emp.get(0).getEmployee_ID();
			System.out.println("employee id -- > "+empid);
			v=null;
			v=entityManager.createQuery("from Projet where projectName=? and status='Active'");
			v.setParameter(1, salesOrderDataBean.getSaleCustomerName());
			List<Projet> customer=(List<Projet>) v.getResultList();
			cusid=customer.get(0).getProject_ID();
			System.out.println("projet id -- > "+cusid);
			Sale ins=new Sale();
			System.out.println("sale category -- > "+salesOrderDataBean.getSaleCategory());
			if(salesOrderDataBean.getSaleCategory().equals("SALC001"))
			{
				ins.setSalesCategory("Product Trading");
				salesOrderDataBean.setSaleCategory("Product Trading");
				System.out.println("1 ");
			}
			else if(salesOrderDataBean.getSaleCategory().equals("SALC002"))
			{
				ins.setSalesCategory("Project");
				salesOrderDataBean.setSaleCategory("Project");
				System.out.println("2 ");
			}								
			ins.setCrossTotal(""+(new BigDecimal(salesOrderDataBean.getSaleCrossTotal()).setScale(0,BigDecimal.ROUND_HALF_UP)));
			ins.setPaymentStatus("pending");
			ins.setEmployee(entityManager.find(Employee.class,empid));
			ins.setProjet(entityManager.find(Projet.class,cusid));
			ins.setSalesOrderNumber(orderno);
			ins.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			ins.setSalesDate(date);
			ins.setSalesDeliveryDate(salesOrderDataBean.getSaleEstDate());
			ins.setSalesOrderDate(salesOrderDataBean.getSaleDate());
			ins.setSalesTime(time);	
			ins.setDiscountAmount(salesOrderDataBean.getDisamnt());
			ins.setDiscountType(salesOrderDataBean.getDisType());
			System.out.println("tax type -- > "+salesOrderDataBean.getSaleTaxType());
			if(salesOrderDataBean.getSaleTaxType().equals("PurDisco01"))
			{
				ins.setTaxType("NO Tax");
				System.out.println("1 tax");
				salesOrderDataBean.setSaleTaxType("NO Tax");
			}
			else if(salesOrderDataBean.getSaleTaxType().equals("PurDisco02"))
			{
				ins.setTaxType("10% Tax");
				System.out.println("2 tax");
				salesOrderDataBean.setSaleTaxType("10% Tax");
			}	
			ins.setTotalAmount(""+(new BigDecimal(salesOrderDataBean.getSaleTotalAmount()).setScale(0,BigDecimal.ROUND_HALF_UP)));
			ins.setSalesApprovalStatus("pending");
			ins.setSalesDeliveryStatus("insert");
			entityManager.persist(ins);	
			v=null;
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, orderno);
			List<Sale> sales1=(List<Sale>)v.getResultList();
			salesOrderDataBean.setSalesId(sales1.get(0).getSales_ID());
			System.out.println("sale id -- >"+sales1.get(0).getSales_ID());
			int salid=sales1.get(0).getSales_ID();
			SalesApproval approve=new SalesApproval();
			approve.setSales(entityManager.find(Sale.class,salid));
			approve.setApproval_status_MM("pending");
			approve.setApproval_status_GM("pending");
			approve.setApproval_login_GM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			approve.setApproval_login_MM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			approve.setApproved_date_GM(date);
			approve.setApproved_date_MM(date);
			approve.setApproved_reject_date_GM(""+date);
			approve.setApproved_reject_date_MM(""+date);
			approve.setApproved_reject_GM("pending");
			approve.setApproved_reject_MM("pending");
			approve.setApprovedDate(date);
			approve.setApprovalStatus("pending");
			approve.setApprovedLogin(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			approve.setApprovedRejectStatus("pending");
			approve.setApprovedTime(time);
			approve.setDelivery_date_GM(date);
			approve.setDelivery_date_PM(date);
			approve.setDelivery_login_GM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			approve.setDelivery_login_PM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			approve.setDelivery_reject_GM("pending");
			approve.setDelivery_reject_GMdate(date);
			approve.setDelivery_reject_PM("pending");
			approve.setDelivery_reject_PMdate(date);
			approve.setDelivery_status_GM("pending");
			approve.setDelivery_status_PM("pending");
			approve.setDeliveryDate(date);
			approve.setDeliveryLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			approve.setDeliveryRejectStatus("pending");
			approve.setDeliveryStatus("pending");
			approve.setDeliveryTime(time);
			entityManager.persist(approve);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	/*SalesOrderInsertion in sales record table*/
	@Transactional(value="transactionManager")
	public String salesRecordInsert(SalesOrderDataBean salesOrderDataBean, int i)
	{
		Query v=null;
		List<Barcode> barcode=null;
		int barcodeid=0;
		try
		{
			v=entityManager.createQuery("from Product where productName=? and status='Active'");
			v.setParameter(1, salesOrderDataBean.getSalesList().get(i).getSaleProductName());
			List<Product> pp=(List<Product>)v.getResultList();
			int productid=pp.get(0).getProduct_ID();
			v=null;			
			v=entityManager.createQuery("from Batch where productName=?");
			v.setParameter(1, salesOrderDataBean.getSalesList().get(i).getSaleProductName());
			List<Batch> batch=(List<Batch>) v.getResultList();
			System.out.println("batch size -- >> "+batch.size());
			SalesRecord sr=new SalesRecord();
			if(batch.size()>0)
			{
				int batchId=0;
				batchId=batch.get(0).getBatch_ID();
				System.out.println("batch id -- >. "+batchId);
				v=null;
				v=entityManager.createQuery("from Barcode where batch_ID=?");
				v.setParameter(1, batchId);
				barcode=(List<Barcode>) v.getResultList();
				System.out.println("barcode size -- >> "+barcode.size());	
				barcodeid=barcode.get(0).getBarcode_ID();
				sr.setBarcode(entityManager.find(Barcode.class,barcodeid));
			}	
			sr.setProduct(entityManager.find(Product.class, productid));
			sr.setSales_ID(salesOrderDataBean.getSalesId());
			sr.setSoldQuantity(salesOrderDataBean.getSalesList().get(i).getSaleQuantity());
			sr.setDamageReturnQuantity("0");
			sr.setDamageStatus("not damaged");
			sr.setStatus("Active");
			entityManager.persist(sr);					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	@Transactional(value="transactionManager")
	public void updateStock(SalesOrderDataBean salesOrderDataBean, int i)
	{
		Query v=null;
		BigDecimal stockin=BigDecimal.valueOf(0);
		BigDecimal stockout=BigDecimal.valueOf(0);
		try
		{
			v=entityManager.createQuery("from Batch where productName=? and status='Active'");
			v.setParameter(1, salesOrderDataBean.getSalesList().get(i).getSaleProductName());
			List<Batch> batch=(List<Batch>)v.getResultList();
			if(batch.size()>0)
			{
				v=null;
				v=entityManager.createQuery("from Barcode where batch_ID=? and status='Barcode Generated'");
				v.setParameter(1, batch.get(0).getBatch_ID());
				List<Barcode> barcode=(List<Barcode>)v.getResultList();
				System.out.println("barcode size -- > "+barcode.size());
				if(barcode.size()>0)
				{
					int barcodeId=barcode.get(0).getBarcode_ID();
					System.out.println("barcode id -- > "+barcodeId);
					Barcode update=entityManager.find(Barcode.class, barcodeId);
					stockin=new BigDecimal(barcode.get(0).getStockIn()).subtract(new BigDecimal(salesOrderDataBean.getSalesList().get(i).getSaleQuantity()));
					System.out.println("satock available -- > "+stockin);
					if(barcode.get(0).getStockOut()==null || barcode.get(0).getStockOut().equals("") || barcode.get(0).getStockOut().equals(null))
					{
						barcode.get(0).setStockOut("0");
						System.out.println("stock out table-- > "+barcode.get(0).getStockOut());
						stockout=new BigDecimal(salesOrderDataBean.getSalesList().get(i).getSaleQuantity());
					}
					else
					{
						stockout=new BigDecimal(barcode.get(0).getStockOut()).add(new BigDecimal(salesOrderDataBean.getSalesList().get(i).getSaleQuantity()));
						System.out.println("stock out -- > "+stockout);
					}	
					update.setStockIn(""+stockin);
					update.setStockOut(""+stockout);
					entityManager.merge(update);
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*View datatable for sales depend on date search*/
	public void dateSearchSales(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("date search ");
		Query v=null;
		salesOrderDataBean.setSalist(null);
		List<SalesOrderDataBean> salist=new ArrayList<SalesOrderDataBean>();
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderDate between ? and ? and (salesDeliveryStatus='insert' or salesDeliveryStatus='Delivered') and (salesApprovalStatus='pending' or salesApprovalStatus='Approved')");
			v.setParameter(1, salesOrderDataBean.getSalFromDate());
			v.setParameter(2, salesOrderDataBean.getSaleToDate());
			List<Sale> salelist=(List<Sale>) v.getResultList();
			if(salelist.size()>0)
			{
				System.out.println("list size -- >> "+salelist);
				for(Sale list:salelist)
				{
					SalesOrderDataBean salesrecord=new SalesOrderDataBean();
					salesrecord.setSaleDate(list.getSalesOrderDate());
					salesrecord.setSaleCustomerName(list.getProjet().getProjectName());
					salesrecord.setSaleTotalAmount(list.getCrossTotal());
					salesrecord.setSaleCategory(list.getSalesCategory());
					salesrecord.setSaleSalesOrderNo(list.getSalesOrderNumber());
					salesrecord.setSaleStaffId(list.getEmployee().getEmployeeName());
					salist.add(salesrecord);
					salesOrderDataBean.setSalist(salist);
					System.out.println("sales list if-- >. "+salist.size());
				}
			}
			else
			{
				salesOrderDataBean.setSalist(salist);
				System.out.println("sales list else -- >. "+salist.size());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*View datatable for sales depend on category search*/
	public void categorySearchSales(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("category search -- > "+salesOrderDataBean.getSaleCategory());
		Query v=null;
		salesOrderDataBean.setSalist(null);
		List<SalesOrderDataBean> salist=new ArrayList<SalesOrderDataBean>();
		try
		{
			v=entityManager.createQuery("from Sale where salesCategory=? and (salesDeliveryStatus='insert' or salesDeliveryStatus='Delivered') and (salesApprovalStatus='pending' or salesApprovalStatus='Approved')");
			v.setParameter(1, salesOrderDataBean.getSaleCategory());
			List<Sale> salelist=(List<Sale>) v.getResultList();
			if(salelist.size()>0)
			{
				System.out.println("list size -- >> "+salelist);
				for(Sale list:salelist)
				{
					SalesOrderDataBean salesrecord=new SalesOrderDataBean();
					salesrecord.setSaleDate(list.getSalesOrderDate());
					salesrecord.setSaleCustomerName(list.getProjet().getProjectName());
					salesrecord.setSaleTotalAmount(list.getCrossTotal());
					salesrecord.setSaleCategory(list.getSalesCategory());
					salesrecord.setSaleSalesOrderNo(list.getSalesOrderNumber());
					salesrecord.setSaleStaffId(list.getEmployee().getEmployeeName());
					salist.add(salesrecord);
					salesOrderDataBean.setSalist(salist);
					System.out.println("sales list if -- >. "+salist.size());
				}
			}
			else
			{
				salesOrderDataBean.setSalist(salist);
				System.out.println("sales list else -- >. "+salist.size());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	/*View each record sales*/
	public void viewEachSales(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		List<SalesOrderDataBean> srlist=new ArrayList<SalesOrderDataBean>();
		BigDecimal amount=BigDecimal.valueOf(0);
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=? ");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> view=(List<Sale>)v.getResultList();
			System.out.println("sales id -- > "+view.get(0).getSales_ID()+" size - > "+view.size());
			if(view.size()>0)
			{				
				salesOrderDataBean.setSaleCategory(view.get(0).getSalesCategory());
				salesOrderDataBean.setSaleCustomerName(view.get(0).getProjet().getProjectName());
				salesOrderDataBean.setSaleDate(view.get(0).getSalesOrderDate());
				salesOrderDataBean.setSaleEstDate(view.get(0).getSalesDeliveryDate());
				salesOrderDataBean.setSaleStaffId(view.get(0).getEmployee().getEmployee_details_ID());
				salesOrderDataBean.setSaleTaxType(view.get(0).getTaxType());
				salesOrderDataBean.setSaleTotalAmount(view.get(0).getTotalAmount());
				v=null;
				v=entityManager.createQuery("from SalesRecord where sales_ID=? ");
				v.setParameter(1, view.get(0).getSales_ID());
				List<SalesRecord> sarec=(List<SalesRecord>)v.getResultList();
				System.out.println("sales record size -- >> "+sarec);
				if(sarec.size()>0)
				{	
					int sno=1;
					for(SalesRecord sr:sarec)
					{
						SalesOrderDataBean srr=new SalesOrderDataBean();						
						srr.setSaleProductName(sr.getProduct().getProductName());
						srr.setSalePrice(sr.getProduct().getActualPrice());
						amount=new BigDecimal(sr.getProduct().getActualPrice()).multiply(new BigDecimal(sr.getSoldQuantity()));
						System.out.println("amount -- > "+amount);	
						srr.setSaleQuantity(sr.getSoldQuantity());
						srr.setSaleSerialNo(String.valueOf(sno));
						srr.setSaleNetAmount(""+amount);
						srlist.add(srr);
						salesOrderDataBean.setSalist(srlist);
						sno++;
					}
					System.out.println("list size -- > "+salesOrderDataBean.getSalist().size());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*edit sales records*/
	public void editSalesOrder(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		ArrayList<SalesOrderDataBean> srlist=new ArrayList<SalesOrderDataBean>();
		BigDecimal amount=BigDecimal.valueOf(0);
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=? ");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> view=(List<Sale>)v.getResultList();
			System.out.println("sales id -- > "+view.get(0).getSales_ID()+" size - > "+view.size());
			if(view.size()>0)
			{				
				salesOrderDataBean.setSaleCategory(view.get(0).getSalesCategory());
				salesOrderDataBean.setSaleCustomerName(view.get(0).getProjet().getProjectName());
				salesOrderDataBean.setSaleDate(view.get(0).getSalesOrderDate());
				salesOrderDataBean.setSaleEstDate(view.get(0).getSalesDeliveryDate());
				salesOrderDataBean.setSaleStaffId(view.get(0).getEmployee().getEmployee_details_ID());
				salesOrderDataBean.setSaleTaxType(view.get(0).getTaxType());
				salesOrderDataBean.setSaleTotalAmount(view.get(0).getTotalAmount());
				salesOrderDataBean.setSaleCrossTotal(view.get(0).getCrossTotal());
				salesOrderDataBean.setAppstatus(view.get(0).getSalesApprovalStatus());
				salesOrderDataBean.setDisamnt(view.get(0).getDiscountAmount());
				salesOrderDataBean.setDisType(view.get(0).getDiscountType());
				v=null;
				v=entityManager.createQuery("from SalesApproval where Sales_ID=? and (approval_status_GM='Approved' or approval_status_MM='Approved')");
				v.setParameter(1, view.get(0).getSales_ID());
				List<SalesApproval> saapp=(List<SalesApproval>)v.getResultList();
				salesOrderDataBean.setStatus(""+saapp.size());
				System.out.println("app size -- >"+saapp.size());
				v=null;
				v=entityManager.createQuery("from SalesRecord where sales_ID=? ");
				v.setParameter(1, view.get(0).getSales_ID());
				List<SalesRecord> sarec=(List<SalesRecord>)v.getResultList();
				System.out.println("sales record size -- >> "+sarec);
				if(sarec.size()>0)
				{	
					int sno=1;
					for(SalesRecord sr:sarec)
					{
						SalesOrderDataBean srr=new SalesOrderDataBean();						
						srr.setSaleProductName(sr.getProduct().getProductName());
						srr.setSalePrice(sr.getProduct().getActualPrice());
						amount=new BigDecimal(sr.getProduct().getActualPrice()).multiply(new BigDecimal(sr.getSoldQuantity()));
						System.out.println("amount -- > "+amount);	
						srr.setSaleQuantity(sr.getSoldQuantity());
						srr.setSaleSerialNo(String.valueOf(sno));
						srr.setQflag("none");
						srr.setQflag1("1");
						srr.setSaleNetAmount(""+amount);
						srlist.add(srr);
						salesOrderDataBean.setSalesList(srlist);
						sno++;
					}
					System.out.println("list size -- > "+salesOrderDataBean.getSalesList().size());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	/*quantity check for sales update*/
	public void updatequantitycheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		Query v=null;
		try
		{
			v=entityManager.createQuery("from Batch where productName=?");
			v.setParameter(1, salesOrderDataBean.getSaleProductName());
			List<Batch> batch=(List<Batch>) v.getResultList();
			System.out.println("batch size -- >> "+batch.size());
			salesOrderDataBean.setBatch(batch);			
			if(batch.size()>0)
			{
				int batchId=0;
				batchId=batch.get(0).getBatch_ID();
				System.out.println("batch id -- >. "+batchId);
				v=null;
				v=entityManager.createQuery("from Barcode where batch_ID=?");
				v.setParameter(1, batchId);
				List<Barcode> barcode=(List<Barcode>) v.getResultList();
				System.out.println("barcode size -- >> "+barcode.size());
				salesOrderDataBean.setBarcode(barcode);
				if(batch.size()>0)
				{
					if(Integer.parseInt(barcode.get(0).getStockIn())<Integer.parseInt(salesOrderDataBean.getSaleQuantity()))
					{
						throw new LiusenException("Only "+barcode.get(0).getStockIn()+" Stock in Warehouse");
					}
				}	
			}
						
		}
		finally
		{
			
		}
	}
	
//	sales update save
	@Transactional(value="transactionManager")
	public void updateSales(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("inside sale update dao ");
		Query v=null;BigDecimal stockin=BigDecimal.valueOf(0);BigDecimal stockout=BigDecimal.valueOf(0);
		BigDecimal amnt=BigDecimal.valueOf(0);BigDecimal total=BigDecimal.valueOf(0);
		BigDecimal salesquan=BigDecimal.valueOf(0);
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			if(sale.size()>0)
			{
				int saleid=sale.get(0).getSales_ID();
				System.out.println("sales id -- > "+saleid);
				v=null;
				v=entityManager.createQuery("from SalesRecord where sales_ID=?");
				v.setParameter(1, saleid);
				List<SalesRecord> salerec=(List<SalesRecord>) v.getResultList();
				if(salerec.size()>0)
				{
					for(int i=0;i<salerec.size();i++)					
					{
						int salereid=salerec.get(i).getSales_record_ID();
						System.out.println("sale size - > "+salesOrderDataBean.getSalesList().size()+" product - > "+salesOrderDataBean.getSalesList().get(i).getSaleProductName());
						salesquan=new BigDecimal(salerec.get(i).getSoldQuantity());
						SalesRecord saupdate=entityManager.find(SalesRecord.class, salereid);
						saupdate.setSoldQuantity(salesOrderDataBean.getSalesList().get(i).getSaleQuantity());
						entityManager.merge(saupdate);
						v=null;
						v=entityManager.createQuery("from Batch where productName=? and status='Active'");
						v.setParameter(1, salesOrderDataBean.getSalesList().get(i).getSaleProductName());
						List<Batch> batch=(List<Batch>)v.getResultList();
						if(batch.size()>0)
						{
							v=null;
							v=entityManager.createQuery("from Barcode where batch_ID=? and status='Barcode Generated'");
							v.setParameter(1, batch.get(0).getBatch_ID());
							List<Barcode> barcode=(List<Barcode>)v.getResultList();
							if(barcode.size()>0)
							{
								int barcodeId=barcode.get(0).getBarcode_ID();
								stockin=new BigDecimal(barcode.get(0).getStockIn()).add(salesquan);
								stockout=new BigDecimal(barcode.get(0).getStockOut()).subtract(salesquan);
								System.out.println("stock in --> "+stockin+" out -> "+stockout);
								Barcode update1=entityManager.find(Barcode.class, barcodeId);
								update1.setStockIn(""+stockin);
								update1.setStockOut(""+stockout);
								entityManager.merge(update1);
								Barcode update=entityManager.find(Barcode.class, barcodeId);
								stockin=new BigDecimal(barcode.get(0).getStockIn()).subtract(new BigDecimal(salesOrderDataBean.getSalesList().get(i).getSaleQuantity()));
								System.out.println("satock available -- > "+stockin);
								stockout=new BigDecimal(barcode.get(0).getStockOut()).add(new BigDecimal(salesOrderDataBean.getSalesList().get(i).getSaleQuantity()));
								System.out.println("stock out -- > "+stockout);
								update.setStockIn(""+stockin);
								update.setStockOut(""+stockout);
								entityManager.merge(update);
							}
						}
						
					}
					Sale saleupdate=entityManager.find(Sale.class, saleid);
					if(sale.get(0).getTaxType().equals("NO Tax"))
					{
						total=new BigDecimal(salesOrderDataBean.getSaleCrossTotal());
					}
					else if(sale.get(0).getTaxType().equals("10% Tax"))
					{
						total=new BigDecimal(salesOrderDataBean.getSaleCrossTotal()).add(new BigDecimal(salesOrderDataBean.getSaleCrossTotal()).multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
					}	
					try
					{
						if(sale.get(0).getDiscountType().equals(""))
						{
							amnt=total;
						}
						else if(sale.get(0).getDiscountType().equals("IDR"))
						{
							amnt=total.subtract(new BigDecimal(sale.get(0).getDiscountAmount()));
						}
						else if(sale.get(0).getDiscountType().equals("%"))
						{
							amnt=total.subtract(total.multiply(new BigDecimal(sale.get(0).getDiscountAmount())).divide(BigDecimal.valueOf(100)));
						}
					}
					catch(Exception e)
					{
						amnt=total;
					}
					saleupdate.setCrossTotal(""+(new BigDecimal(salesOrderDataBean.getSaleCrossTotal()).setScale(0,BigDecimal.ROUND_HALF_UP)));
					saleupdate.setTotalAmount(""+amnt.setScale(0,BigDecimal.ROUND_HALF_UP));
					entityManager.merge(saleupdate);					
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//	sales order delete check
	public void deleteCheck(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("inside sales delete check dao ");
		Query v=null;
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			if(sale.size()>0)
			{
				int saleid=sale.get(0).getSales_ID();
				System.out.println("sales id -- > "+saleid);
				v=null;
				v=entityManager.createQuery("from SalesApproval where Sales_ID=? and (approval_status_GM='Approved' or approval_status_MM='Approved')");
				v.setParameter(1, saleid);
				List<SalesApproval> saapp=(List<SalesApproval>)v.getResultList();
				salesOrderDataBean.setStatus(""+saapp.size());
				salesOrderDataBean.setAppstatus(sale.get(0).getSalesApprovalStatus());								
			}
		}
		finally
		{
			
		}
	}
	
//	sales order delete
	@Transactional(value="transactionManager")
	public void deleteSales(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		System.out.println("inside sales delete dao ");
		Query v=null;
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			if(sale.size()>0)
			{
				int saleid=sale.get(0).getSales_ID();
				System.out.println("sales id -- > "+saleid);
				v=null;
				v=entityManager.createQuery("from SalesRecord where sales_ID=?");
				v.setParameter(1, saleid);
				List<SalesRecord> salerec=(List<SalesRecord>) v.getResultList();
				if(salerec.size()>0)
				{
					for(int i=0;i<salerec.size();i++)					
					{
						int salereid=salerec.get(i).getSales_record_ID();
						SalesRecord sadel=entityManager.find(SalesRecord.class, salereid);
						sadel.setStatus("Removed");
						entityManager.merge(sadel);
						
						if(salesOrderDataBean.getSaleCategory().equals("Product Trading"))
						{
							int barcodeid=salerec.get(i).getBarcode().getBarcode_ID();
							System.out.println("barcode id - > "+barcodeid);
							Barcode delte=entityManager.find(Barcode.class, barcodeid);
							delte.setStockIn(""+(new BigDecimal(salerec.get(i).getBarcode().getStockIn()).add(new BigDecimal(salerec.get(i).getSoldQuantity()))));
							delte.setStockOut(""+(new BigDecimal(salerec.get(i).getBarcode().getStockOut()).subtract(new BigDecimal(salerec.get(i).getSoldQuantity()))));
							entityManager.merge(delte);
						}
					}
					Sale saledel=entityManager.find(Sale.class, saleid);									
					saledel.setSalesDeliveryStatus("Removed");					
					entityManager.merge(saledel);
				}					
			}
		}
		finally
		{
			
		}
	}
	
	/*sales order waiting approval for GM size*/
	public int remSalesApprovalGM()
	{
		System.out.println("GM Approval size dao -- >");
		Query v=null;
		int cc=0;
		try
		{
			v=entityManager.createQuery("from Sale where salesApprovalStatus='pending' and salesDeliveryStatus='insert'");
			List<Sale> sale=(List<Sale>) v.getResultList();
			if(sale.size()>0)
			{
				for (int i = 0; i <sale.size(); i++) 
				{
					v=null;
					v=entityManager.createQuery("from SalesApproval where approval_status_GM='pending' and approvalStatus='pending'");
					List<SalesApproval> saleappl=(List<SalesApproval>) v.getResultList();
					if(saleappl.size()>0)
					{
						cc=saleappl.size();						
					}
				}
				System.out.println("c for sale approval GM - > "+cc);
			}
		}
		finally
		{
			
		}
		return cc;
	}
	
	/*sales order waiting approval for MM size*/
	public int remSalesApprovalMM()
	{
		System.out.println("MM Approval size dao -- >");
		Query v=null;
		int cc=0;
		try
		{
			v=entityManager.createQuery("from Sale where salesApprovalStatus='pending' and salesDeliveryStatus='insert'");
			List<Sale> sale=(List<Sale>) v.getResultList();
			if(sale.size()>0)
			{
				for (int i = 0; i <sale.size(); i++) 
				{
					v=null;
					v=entityManager.createQuery("from SalesApproval where approval_status_MM='pending' and approvalStatus='pending'");
					List<SalesApproval> saleappl=(List<SalesApproval>) v.getResultList();
					if(saleappl.size()>0)
					{
						cc=saleappl.size();						
					}
				}
				System.out.println("c for sale approval MM - > "+cc);
			}
		}
		finally
		{
			
		}
		return cc;
	}

//	list of approval waiting for sales by MM
	public String approveMM(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("MM Approval dao -- >");
		Query v=null;
		List<SalesOrderDataBean> app=null;
		try
		{
			v=entityManager.createQuery("from Sale where salesApprovalStatus='pending' and salesDeliveryStatus='insert'");
			List<Sale> sale=(List<Sale>) v.getResultList();
			System.out.println("sale size - > "+sale.size());
			if(sale.size()>0)
			{
				for (int i = 0; i <sale.size(); i++) 
				{
					v=null;					
					v=entityManager.createQuery("from SalesApproval where approval_status_MM='pending' and approvalStatus='pending'");
					List<SalesApproval> saleappl=(List<SalesApproval>) v.getResultList();
					System.out.println(":sales approval size MM--> "+saleappl.size());
					if(saleappl.size()>0)
					{
						int count=1;
						app=new ArrayList<SalesOrderDataBean>();
						for (int j = 0; j <saleappl.size(); j++) 
						{							
							SalesOrderDataBean approlist=new SalesOrderDataBean();
							approlist.setSaleSalesOrderNo(saleappl.get(j).getSales().getSalesOrderNumber());
							approlist.setSaleDate(saleappl.get(j).getSales().getSalesDate());
							approlist.setSaleTotalAmount(saleappl.get(j).getSales().getTotalAmount());
							approlist.setSaleSerialNo(""+count);
							app.add(approlist);
							salesOrderDataBean.setSalist(app);
							count++;
						}						
					}
					else
					{
						salesOrderDataBean.setSalist(app);
					}
				}
			}
		}
		catch(NullPointerException n)
		{
			n.printStackTrace();
		}
		finally
		{
			
		}
		return null;	
	}
	
//	list of approval waiting for sales by GM
	public String approveGM(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("GM Approval dao -- >");
		Query v=null;
		List<SalesOrderDataBean> app=null;
		try
		{
			v=entityManager.createQuery("from Sale where salesApprovalStatus='pending' and salesDeliveryStatus='insert'");
			List<Sale> sale=(List<Sale>) v.getResultList();
			System.out.println("sale size - > "+sale.size());
			if(sale.size()>0)
			{
				for (int i = 0; i <sale.size(); i++) 
				{
					v=null;					
					app=new ArrayList<SalesOrderDataBean>();					
					v=entityManager.createQuery("from SalesApproval where approval_status_GM='pending' and approvalStatus='pending'");
					List<SalesApproval> saleappl=(List<SalesApproval>) v.getResultList();
					System.out.println(":sales approval size GM--> "+saleappl.size());
					if(saleappl.size()>0)
					{
						int count=1;
						for (int j = 0; j <saleappl.size(); j++) 
						{							
							SalesOrderDataBean approlist=new SalesOrderDataBean();
							approlist.setSaleSalesOrderNo(saleappl.get(j).getSales().getSalesOrderNumber());
							approlist.setSaleDate(saleappl.get(j).getSales().getSalesDate());
							approlist.setSaleTotalAmount(saleappl.get(j).getSales().getTotalAmount());
							approlist.setSaleSerialNo(""+count);
							app.add(approlist);
							salesOrderDataBean.setSalist(app);
							count++;
						}						
					}
					else
					{
						salesOrderDataBean.setSalist(app);
						System.out.println("else app size -- > "+app.size());
					}
				}
			}
		}
		catch(NullPointerException n)
		{
			n.printStackTrace();
		}
		finally
		{
			
		}
		return null;	
	}

	/*Approved the salesorder MM*/
	@Transactional(value="transactionManager")
	public String approvedSalesMM(SalesOrderDataBean salesOrderDataBean) 
	{
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		System.out.println("ct date -> "+date+" time - > "+time);
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			if(sale.size()>0)
			{
				int salid=sale.get(0).getSales_ID();
				System.out.println("sales id -- . "+salid);
				v=null;
				v=entityManager.createQuery("from SalesApproval where Sales_ID=?");
				v.setParameter(1, salid);
				List<SalesApproval> salappr=(List<SalesApproval>) v.getResultList();
				if(salappr.size()>0)
				{
					int salapprid=salappr.get(0).getSales_approval_ID();
					System.out.println("sale approve id -- >> "+salapprid);
					if(salesOrderDataBean.getAction().equals("Approve"))
					{
						System.out.println("approve -- >>.> ");
						SalesApproval approve=entityManager.find(SalesApproval.class, salapprid);
						approve.setApproval_login_MM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setApproval_status_MM("Approved");
						approve.setApproved_date_MM(date);
						approve.setApprovedDate(date);
						approve.setApprovedLogin(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setApprovedTime(time);
						entityManager.merge(approve);
						v=null;
						v=entityManager.createQuery("from SalesApproval where Sales_ID=? and approval_status_GM='Approved' and approval_status_MM='Approved'");
						v.setParameter(1, salid);
						List<SalesApproval> salapprove=(List<SalesApproval>) v.getResultList();
						if(salapprove.size()>0)
						{
							int appid=salapprove.get(0).getSales_approval_ID();
							SalesApproval appp1=entityManager.find(SalesApproval.class, appid);
							appp1.setApprovalStatus("Approved");
							entityManager.merge(appp1);
							int sal=salapprove.get(0).getSales().getSales_ID();
							Sale appp=entityManager.find(Sale.class, sal);
							appp.setSalesApprovalStatus("Approved");
							entityManager.merge(appp);
						}
					}
					else if(salesOrderDataBean.getAction().equals("Reject"))
					{
						System.out.println("reject -- >>.> ");
						SalesApproval approve=entityManager.find(SalesApproval.class, salapprid);
						approve.setApproved_reject_MM("Rejected");
						approve.setApproved_reject_date_MM(""+date);
						approve.setReject_reason_MM(salesOrderDataBean.getReason());
						approve.setApprovalStatus("Rejected");
						approve.setApprovedRejectStatus("RejectedbyMM");
						entityManager.merge(approve);
						v=null;
						v=entityManager.createQuery("from SalesApproval where Sales_ID=? ");
						v.setParameter(1, salid);
						List<SalesApproval> salapprove=(List<SalesApproval>) v.getResultList();
						if(salapprove.size()>0)
						{
							int sal=salapprove.get(0).getSales().getSales_ID();
							Sale appp=entityManager.find(Sale.class, sal);
							appp.setSalesApprovalStatus("Rejected");
							entityManager.merge(appp);
							v=null;
							v=entityManager.createQuery("from SalesRecord where sales_ID=?");
							v.setParameter(1, salid);
							List<SalesRecord> salerec=(List<SalesRecord>) v.getResultList();
							if(salerec.size()>0)
							{
								for(int i=0;i<salerec.size();i++)					
								{
									int salereid=salerec.get(i).getSales_record_ID();
									SalesRecord sadel=entityManager.find(SalesRecord.class, salereid);
									sadel.setStatus("Removed");
									entityManager.merge(sadel);
									
									if(sale.get(0).getSalesCategory().equals("Product Trading"))
									{
										int barcodeid=salerec.get(i).getBarcode().getBarcode_ID();
										System.out.println("barcode id - > "+barcodeid);
										Barcode delte=entityManager.find(Barcode.class, barcodeid);
										delte.setStockIn(""+(new BigDecimal(salerec.get(i).getBarcode().getStockIn()).add(new BigDecimal(salerec.get(i).getSoldQuantity()))));
										delte.setStockOut(""+(new BigDecimal(salerec.get(i).getBarcode().getStockOut()).subtract(new BigDecimal(salerec.get(i).getSoldQuantity()))));
										entityManager.merge(delte);
									}
								}
							}
						}
					}
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/*Approved the salesorder GM*/
	@Transactional(value="transactionManager")
	public String approvedSalesGM(SalesOrderDataBean salesOrderDataBean) 
	{
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		System.out.println("ct date -> "+date+" time - > "+time);
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			if(sale.size()>0)
			{
				int salid=sale.get(0).getSales_ID();
				System.out.println("sales id -- . "+salid);
				v=null;
				v=entityManager.createQuery("from SalesApproval where Sales_ID=?");
				v.setParameter(1, salid);
				List<SalesApproval> salappr=(List<SalesApproval>) v.getResultList();
				if(salappr.size()>0)
				{
					int salapprid=salappr.get(0).getSales_approval_ID();
					System.out.println("sale approve id -- >> "+salapprid);
					if(salesOrderDataBean.getAction().equals("Approve"))
					{
						System.out.println("approve -- >>.> ");
						SalesApproval approve=entityManager.find(SalesApproval.class, salapprid);
						approve.setApproval_login_GM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setApproval_status_GM("Approved");
						approve.setApproved_date_GM(date);
						approve.setApprovedDate(date);
						approve.setApprovedLogin(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setApprovedTime(time);
						entityManager.merge(approve);
						v=null;
						v=entityManager.createQuery("from SalesApproval where Sales_ID=? and approval_status_GM='Approved' and approval_status_MM='Approved'");
						v.setParameter(1, salid);
						List<SalesApproval> salapprove=(List<SalesApproval>) v.getResultList();
						if(salapprove.size()>0)
						{
							int appid=salapprove.get(0).getSales_approval_ID();
							SalesApproval appp1=entityManager.find(SalesApproval.class, appid);
							appp1.setApprovalStatus("Approved");
							entityManager.merge(appp1);
							int sal=salapprove.get(0).getSales().getSales_ID();
							Sale appp=entityManager.find(Sale.class, sal);
							appp.setSalesApprovalStatus("Approved");
							entityManager.merge(appp);
						}
					}
					else if(salesOrderDataBean.getAction().equals("Reject"))
					{
						System.out.println("reject -- >>.> ");
						SalesApproval approve=entityManager.find(SalesApproval.class, salapprid);
						approve.setApproved_reject_GM("Rejected");
						approve.setApproved_reject_date_GM(""+date);
						approve.setReject_reason_GM(salesOrderDataBean.getReason());
						approve.setApprovedRejectStatus("RejectedbyGM");		
						approve.setApprovalStatus("Rejected");
						entityManager.merge(approve);
						v=null;
						v=entityManager.createQuery("from SalesApproval where Sales_ID=?");
						v.setParameter(1, salid);
						List<SalesApproval> salapprove=(List<SalesApproval>) v.getResultList();
						if(salapprove.size()>0)
						{
							int sal=salapprove.get(0).getSales().getSales_ID();
							Sale appp=entityManager.find(Sale.class, sal);
							appp.setSalesApprovalStatus("Rejected");
							entityManager.merge(appp);
						}
					}
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}	

	/*sales order waiting delivery for GM size*/
	public int remSalesDeliveryGM()
	{
		System.out.println("GM Delivery size dao -- >");
		Query v=null;
		int cc=0;int count=0;
		try
		{
			v=entityManager.createQuery("from Sale where salesApprovalStatus='Approved' and salesDeliveryStatus='insert'");
			List<Sale> sale=(List<Sale>) v.getResultList();
			if(sale.size()>0)
			{
				for (int i = 0; i <sale.size(); i++) 
				{
					v=null;
					v=entityManager.createQuery("from SalesApproval where Sales_ID=? and  delivery_status_GM='pending' and deliveryStatus='pending'");
					v.setParameter(1, sale.get(i).getSales_ID());
					List<SalesApproval> saleappl=(List<SalesApproval>) v.getResultList();
					if(saleappl.size()>0)
					{
						cc=saleappl.size();	
						count=count+cc;
					}
				}
				System.out.println("c for sale Delivery GM - > "+count);
			}
		}
		finally
		{
			
		}
		return count;
	}
	
	/*sales order waiting delivery for PM size*/
	public int remSalesDeliveryPM()
	{
		System.out.println("PM Delivery size dao -- >");
		Query v=null;
		int cc=0;int count=0;
		try
		{
			v=entityManager.createQuery("from Sale where salesApprovalStatus='Approved' and salesDeliveryStatus='insert'");
			List<Sale> sale=(List<Sale>) v.getResultList();
			System.out.println("sales delivery size -- >"+sale.size());
			if(sale.size()>0)
			{
				for (int i = 0; i <sale.size(); i++) 
				{
					v=null;
					v=entityManager.createQuery("from SalesApproval where Sales_ID=? and delivery_status_PM='pending' and deliveryStatus='pending'");
					v.setParameter(1, sale.get(i).getSales_ID());
					List<SalesApproval> saleappl=(List<SalesApproval>) v.getResultList();
					if(saleappl.size()>0)
					{
						cc=saleappl.size();	
						count=count+cc;					
					}
				}
				System.out.println("c for sale Delivery PM - > "+count);
			}
		}
		finally
		{
			
		}
		return count;
	}
	
//	list of delivery waiting for sales by GM
	public String deliveryGM(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("GM delivery dao -- >");
		Query v=null;
		List<SalesOrderDataBean> app=new ArrayList<SalesOrderDataBean>();	
		try
		{
			v=entityManager.createQuery("from Sale where salesApprovalStatus='Approved' and salesDeliveryStatus='insert'");
			List<Sale> sale=(List<Sale>) v.getResultList();
			System.out.println("sale size - > "+sale.size());
			if(sale.size()>0)
			{
				int count=1;
				for (int i = 0; i <sale.size(); i++) 
				{
					v=null;					
					v=entityManager.createQuery("from SalesApproval where Sales_ID=? and  delivery_status_GM='pending' and deliveryStatus='pending'");
					v.setParameter(1, sale.get(i).getSales_ID());
					List<SalesApproval> saleappl=(List<SalesApproval>) v.getResultList();
					System.out.println(":sales delivery size GM--> "+saleappl.size());
					if(saleappl.size()>0)
					{						
						SalesOrderDataBean approlist=new SalesOrderDataBean();
						approlist.setSaleSalesOrderNo(saleappl.get(0).getSales().getSalesOrderNumber());
						approlist.setSaleDate(saleappl.get(0).getSales().getSalesDate());
						approlist.setSaleTotalAmount(saleappl.get(0).getSales().getTotalAmount());
						approlist.setSaleSerialNo(""+count);
						app.add(approlist);
						count++;												
					}
					else
						
					{
						salesOrderDataBean.setSalist(app);
						System.out.println("else app size -- > "+app.size());
					}
				}
				salesOrderDataBean.setSalist(app);				
			}
		}
		catch(NullPointerException n)
		{
			n.printStackTrace();
		}
		finally
		{
			
		}
		return null;	
	}
	
//	list of delivery waiting for sales by PM
	public String deliveryPM(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("PM Delivery dao -- >");
		Query v=null;
		salesOrderDataBean.setSalist(null);
		List<SalesOrderDataBean> app=new ArrayList<SalesOrderDataBean>();		
		try
		{
			v=entityManager.createQuery("from Sale where salesApprovalStatus='Approved' and salesDeliveryStatus='insert'");
			List<Sale> sale=(List<Sale>) v.getResultList();
			System.out.println("sale size - > "+sale.size());
			if(sale.size()>0)
			{
				int count=1;
				for (int i = 0; i <sale.size(); i++) 
				{
					v=null;					
					v=entityManager.createQuery("from SalesApproval where Sales_ID=? and delivery_status_PM='pending' and deliveryStatus='pending'");
					v.setParameter(1, sale.get(i).getSales_ID());
					List<SalesApproval> saleappl=(List<SalesApproval>) v.getResultList();
					System.out.println(":sales delivery size PM--> "+saleappl.size());
					if(saleappl.size()>0)
					{						
						SalesOrderDataBean approlist=new SalesOrderDataBean();
						approlist.setSaleSalesOrderNo(saleappl.get(0).getSales().getSalesOrderNumber());
						approlist.setSaleDate(saleappl.get(0).getSales().getSalesDate());
						approlist.setSaleTotalAmount(saleappl.get(0).getSales().getTotalAmount());
						approlist.setSaleSerialNo(""+count);
						app.add(approlist);
						count++;
					}
					else
					{
						salesOrderDataBean.setSalist(app);
						System.out.println("else app size -- > "+app.size());
					}
				}
				salesOrderDataBean.setSalist(app);				
			}
		}
		catch(NullPointerException n)
		{
			n.printStackTrace();
		}
		finally
		{
			
		}
		return null;	
	}

	/*deliverd the salesorder GM*/
	@Transactional(value="transactionManager")
	public String deliverdSalesGM(SalesOrderDataBean salesOrderDataBean) 
	{
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		System.out.println("ct date -> "+date+" time - > "+time);
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			if(sale.size()>0)
			{
				int salid=sale.get(0).getSales_ID();
				System.out.println("sales id -- . "+salid);
				v=null;
				v=entityManager.createQuery("from SalesApproval where Sales_ID=?");
				v.setParameter(1, salid);
				List<SalesApproval> salappr=(List<SalesApproval>) v.getResultList();
				if(salappr.size()>0)
				{
					int salapprid=salappr.get(0).getSales_approval_ID();
					System.out.println("sale approve id -- >> "+salapprid);
					if(salesOrderDataBean.getAction().equals("Delivery"))
					{
						System.out.println("delivery -- >>.> ");
						SalesApproval approve=entityManager.find(SalesApproval.class, salapprid);
						approve.setDelivery_login_GM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setDelivery_status_GM("Delivered");
						approve.setDelivery_date_GM(date);
						approve.setDeliveryDate(date);
						approve.setDeliveryLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setDeliveryTime(time);
						entityManager.merge(approve);
						v=null;
						v=entityManager.createQuery("from SalesApproval where Sales_ID=? and delivery_status_GM='Delivered' and delivery_status_PM='Delivered'");
						v.setParameter(1, salid);
						List<SalesApproval> salapprove=(List<SalesApproval>) v.getResultList();
						if(salapprove.size()>0)
						{
							int appid=salapprove.get(0).getSales_approval_ID();
							SalesApproval appp1=entityManager.find(SalesApproval.class, appid);
							appp1.setDeliveryStatus("Delivered");
							entityManager.merge(appp1);
							int sal=salapprove.get(0).getSales().getSales_ID();
							Sale appp=entityManager.find(Sale.class, sal);
							appp.setSalesDeliveryStatus("Delivered");
							entityManager.merge(appp);
						}
					}
					else if(salesOrderDataBean.getAction().equals("Reject"))
					{
						System.out.println("reject -- >>.> ");
						SalesApproval approve=entityManager.find(SalesApproval.class, salapprid);
						approve.setDelivery_reject_GM("Rejected");
						approve.setDelivery_reject_GMdate(date);
						approve.setReject_reason_GM(salesOrderDataBean.getReason());
						approve.setDeliveryStatus("Rejected");
						approve.setDeliveryRejectStatus("RejectedbyGM");
						entityManager.merge(approve);
						v=null;
						v=entityManager.createQuery("from SalesApproval where Sales_ID=? ");
						v.setParameter(1, salid);
						List<SalesApproval> salapprove=(List<SalesApproval>) v.getResultList();
						if(salapprove.size()>0)
						{
							int sal=salapprove.get(0).getSales().getSales_ID();
							Sale appp=entityManager.find(Sale.class, sal);
							appp.setSalesDeliveryStatus("Rejected");
							entityManager.merge(appp);
						}
					}
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/*delivered the salesorder PM*/
	@Transactional(value="transactionManager")
	public String deliverdSalesPM(SalesOrderDataBean salesOrderDataBean) 
	{
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		System.out.println("ct date -> "+date+" time - > "+time);
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			if(sale.size()>0)
			{
				int salid=sale.get(0).getSales_ID();
				System.out.println("sales id -- . "+salid);
				v=null;
				v=entityManager.createQuery("from SalesApproval where Sales_ID=?");
				v.setParameter(1, salid);
				List<SalesApproval> salappr=(List<SalesApproval>) v.getResultList();
				if(salappr.size()>0)
				{
					int salapprid=salappr.get(0).getSales_approval_ID();
					System.out.println("sale approve id -- >> "+salapprid);
					if(salesOrderDataBean.getAction().equals("Delivery"))
					{
						System.out.println("approve -- >>.> ");
						SalesApproval approve=entityManager.find(SalesApproval.class, salapprid);
						approve.setDelivery_login_PM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setDelivery_status_PM("Delivered");
						approve.setDelivery_date_PM(date);
						approve.setDeliveryDate(date);
						approve.setDeliveryLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setDeliveryTime(time);
						entityManager.merge(approve);
						v=null;
						v=entityManager.createQuery("from SalesApproval where Sales_ID=? and delivery_status_GM='Delivered' and delivery_status_PM='Delivered'");
						v.setParameter(1, salid);
						List<SalesApproval> salapprove=(List<SalesApproval>) v.getResultList();
						if(salapprove.size()>0)
						{
							int appid=salapprove.get(0).getSales_approval_ID();
							SalesApproval appp1=entityManager.find(SalesApproval.class, appid);
							appp1.setDeliveryStatus("Delivered");
							entityManager.merge(appp1);
							int sal=salapprove.get(0).getSales().getSales_ID();
							Sale appp=entityManager.find(Sale.class, sal);
							appp.setSalesDeliveryStatus("Delivered");
							entityManager.merge(appp);
						}
					}
					else if(salesOrderDataBean.getAction().equals("Reject"))
					{
						System.out.println("reject -- >>.> ");
						SalesApproval approve=entityManager.find(SalesApproval.class, salapprid);
						approve.setDelivery_reject_PM("Rejected");
						approve.setDelivery_reject_PMdate(date);
						approve.setReject_reason_PM(salesOrderDataBean.getReason());
						approve.setDeliveryStatus("Rejected");
						approve.setDeliveryRejectStatus("RejectedbyPM");
						entityManager.merge(approve);
						v=null;
						v=entityManager.createQuery("from SalesApproval where Sales_ID=? ");
						v.setParameter(1, salid);
						List<SalesApproval> salapprove=(List<SalesApproval>) v.getResultList();
						if(salapprove.size()>0)
						{
							int sal=salapprove.get(0).getSales().getSales_ID();
							Sale appp=entityManager.find(Sale.class, sal);
							appp.setSalesDeliveryStatus("Rejected");
							entityManager.merge(appp);
						}
					}
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}	

	public List<String> getinvoiceSales(SalesOrderDataBean salesOrderDataBean)
	{
		List<String> invoicelist=new ArrayList<String>();
		Query v=null;
		try
		{
			System.out.println(" inside "+salesOrderDataBean.getSaleCategory());
			v=entityManager.createQuery("from Sale where salesDeliveryStatus='Delivered' and salesCategory=?");
			v.setParameter(1, salesOrderDataBean.getSaleCategory());
			List<Sale> sale=(ArrayList<Sale>)v.getResultList();		
			System.out.println("sales size - > "+sale.size());			
			if(sale.size()==0)
			{
				System.out.println("sales empty - -");
			}		
			else
			{			
				for (Sale sales : sale)
				{
					String purchaselist2 =new String();
					System.out.println("order number------>"+sales.getSalesOrderNumber());
					purchaselist2=sales.getSalesOrderNumber();				
					invoicelist.add(purchaselist2);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return invoicelist;
	}
	
	public String invoiceViewSales(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		ArrayList<SalesOrderDataBean> litss=new ArrayList<SalesOrderDataBean>();
		try
		{
			System.out.println(" inside "+salesOrderDataBean.getSaleCategory());
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>)v.getResultList();
			int saleid=0;
			if(sale.size()>0)
			{
				saleid=sale.get(0).getSales_ID();
				System.out.println("sales id -- > "+saleid);							
				SalesOrderDataBean  list=new SalesOrderDataBean();
				list.setSaleDate(sale.get(0).getSalesOrderDate());
				list.setSaleSalesOrderNo(sale.get(0).getSalesOrderNumber());
				list.setSaleCustomerName(sale.get(0).getProjet().getProjectName());
				list.setSaleTotalAmount(sale.get(0).getTotalAmount());
				list.setSaleStaffId(sale.get(0).getEmployee().getEmployeeName());
				litss.add(list);
				salesOrderDataBean.setSalesList(litss);				
				System.out.println("size - >"+salesOrderDataBean.getSalesList().size());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";	
	}
	
	public String invoiceViewForm(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		List<SalesOrderDataBean> litss=new ArrayList<SalesOrderDataBean>();
		BigDecimal returnamnt=BigDecimal.valueOf(0),returnamnt1=BigDecimal.valueOf(0),total=BigDecimal.valueOf(0);
		BigDecimal amntz=BigDecimal.valueOf(0);
		try
		{
			System.out.println(" inside "+salesOrderDataBean.getSaleCategory());
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>)v.getResultList();
			int saleid=0;
			if(sale.size()>0)
			{
				saleid=sale.get(0).getSales_ID();
				System.out.println("sales id -- > "+saleid);	
				v=null;
				v=entityManager.createQuery("from SalesRecord where sales_ID=?");
				v.setParameter(1, saleid);
				List<SalesRecord> sarec=(List<SalesRecord>)v.getResultList();
				if(sarec.size()>0)
				{
					int c=1;
					for (int i = 0; i < sarec.size(); i++) 
					{
						v=null;
						v=entityManager.createQuery("from SalesReturn where sales_record_ID=?");
						v.setParameter(1, sarec.get(i).getSales_record_ID());
						List<SalesReturn> salret=(List<SalesReturn>)v.getResultList();
						if(salret.size()>0)
						{
							BigDecimal quan=BigDecimal.valueOf(0),amnt=BigDecimal.valueOf(0);	
							quan=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));
							amnt=new BigDecimal(salret.get(0).getSalesRecord().getProduct().getActualPrice()).multiply(quan);
							SalesOrderDataBean  list=new SalesOrderDataBean();
							list.setSaleSerialNo(""+c);
							list.setSaleProductName(sarec.get(i).getProduct().getProductName());
							list.setSaleQuantity(sarec.get(i).getSoldQuantity());
							list.setSalePrice(sarec.get(i).getProduct().getActualPrice());
							list.setSaleNetAmount(""+(new BigDecimal(sarec.get(i).getProduct().getActualPrice()).multiply(new BigDecimal(sarec.get(i).getSoldQuantity()))));
							list.setReturnqauntity(""+quan);
							list.setRetamount(""+amnt);								
							litss.add(list);
							salesOrderDataBean.setSalist(litss);		
							c++;
							returnamnt1=(new BigDecimal(sarec.get(i).getProduct().getActualPrice()).multiply(new BigDecimal(sarec.get(i).getSoldQuantity()))).subtract(amnt);
						}
						else
						{
							SalesOrderDataBean  list=new SalesOrderDataBean();
							list.setSaleSerialNo(""+c);
							list.setSaleProductName(sarec.get(i).getProduct().getProductName());
							list.setSaleQuantity(sarec.get(i).getSoldQuantity());
							list.setSalePrice(sarec.get(i).getProduct().getActualPrice());
							list.setSaleNetAmount(""+(new BigDecimal(sarec.get(i).getProduct().getActualPrice()).multiply(new BigDecimal(sarec.get(i).getSoldQuantity()))));
							list.setReturnqauntity("0");
							list.setRetamount("0");								
							litss.add(list);
							salesOrderDataBean.setSalist(litss);		
							c++;
							returnamnt1=new BigDecimal(sarec.get(i).getProduct().getActualPrice()).multiply(new BigDecimal(sarec.get(i).getSoldQuantity()));	
						}
						returnamnt=returnamnt.add(returnamnt1);
					}
				}
				if(sale.get(0).getTaxType().equals("NO Tax"))
				{
					total=returnamnt;
				}
				else if(sale.get(0).getTaxType().equals("10% Tax"))
				{
					total=returnamnt.add(returnamnt.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
				}
				try
				{
					if(sale.get(0).getDiscountType().equals(""))
					{
						amntz=total;
					}
					else if(sale.get(0).getDiscountType().equals("IDR"))
					{
						amntz=total.subtract(new BigDecimal(sale.get(0).getDiscountAmount()));
					}
					else if(sale.get(0).getDiscountType().equals("%"))
					{
						amntz=total.subtract(total.multiply(new BigDecimal(sale.get(0).getDiscountAmount())).divide(BigDecimal.valueOf(100)));
					}
				}
				catch(Exception e)
				{
					amntz=total;
				}
				salesOrderDataBean.setSaleCrossTotal(""+amntz.setScale(0,BigDecimal.ROUND_HALF_UP));
				salesOrderDataBean.setSaleDate(sale.get(0).getSalesOrderDate());
				salesOrderDataBean.setSaleSalesOrderNo(sale.get(0).getSalesOrderNumber());
				salesOrderDataBean.setSaleCustomerName(sale.get(0).getProjet().getProjectName());
				salesOrderDataBean.setSaleTotalAmount(sale.get(0).getTotalAmount());
				salesOrderDataBean.setSaleStaffId(sale.get(0).getEmployee().getEmployeeName());
				salesOrderDataBean.setSalesId(saleid);
				System.out.println("size - >"+salesOrderDataBean.getSalist().size());
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";		
	}
	
	@Transactional(value="transactionManager")
	public String invoiceinsert(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		try
		{
			v=entityManager.createQuery("from Invoice where sales_ID=?");
			v.setParameter(1, salesOrderDataBean.getSalesId());						
			List<Invoice> invoice=(List<Invoice>) v.getResultList();
			int invoiceid=0;
			if(invoice.size()>0)
			{
				invoiceid=invoice.get(0).getInvoice_ID();
				System.out.println("invoice id - > "+invoiceid);
				Invoice update=entityManager.find(Invoice.class,invoiceid);
				update.setInviceDate(date);
				update.setInvoiceStatus("Sales Invoice");	
				update.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				update.setLoginTime(time);
				update.setStatus("Invoice Generated");
				entityManager.merge(update);
			}
			else
			{
				Invoice insert=new Invoice();
				insert.setInviceDate(date);
				insert.setInvoiceStatus("Sales Invoice");
				insert.setSale(entityManager.find(Sale.class, salesOrderDataBean.getSalesId()));
				insert.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				insert.setLoginTime(time);
				insert.setStatus("Invoice Generated");
				entityManager.persist(insert);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public List<String> getpaymentSales(SalesOrderDataBean salesOrderDataBean)
	{
		List<String> list=new ArrayList<String>();
		Query v=null;
		try
		{
			v=entityManager.createQuery("from Invoice where invoiceStatus='Sales Invoice'");
			List<Invoice> invoice=(List<Invoice>) v.getResultList();
			if(invoice.size()>0)
			{
				for (int i = 0; i <invoice.size(); i++) 
				{
					v=null;
					v=entityManager.createQuery("from Payment where invoice_ID=?");
					v.setParameter(1, invoice.get(i).getInvoice_ID());
					List<Payment> pp=(List<Payment>) v.getResultList();
					if(pp.size()>0)
					{
						v=null;
						v=entityManager.createQuery("from Payment where invoice_ID=? and paymentStatus='pending'");
						v.setParameter(1, invoice.get(i).getInvoice_ID());
						List<Payment> payy=(List<Payment>) v.getResultList();
						if(payy.size()>0)
						{
							String sales=invoice.get(i).getSale().getSalesOrderNumber();
							list.add(sales);
						}	
					}
					else
					{
						String sales=invoice.get(i).getSale().getSalesOrderNumber();
						list.add(sales);
					}				
				}
			}			
			System.out.println("sales no - > "+list.size()+" -- "+list);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public String paymentViewSales(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		ArrayList<SalesOrderDataBean> litss=new ArrayList<SalesOrderDataBean>();
		try
		{
			BigDecimal amntt=BigDecimal.valueOf(0),total=BigDecimal.valueOf(0);
			BigDecimal returnamnt=BigDecimal.valueOf(0),amntz=BigDecimal.valueOf(0);
			System.out.println(" inside "+salesOrderDataBean.getSaleCategory());
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>)v.getResultList();
			int saleid=0;
			if(sale.size()>0)
			{
				saleid=sale.get(0).getSales_ID();
				System.out.println("sales id -- > "+saleid);						
				v=null;
				v=entityManager.createQuery("from SalesRecord where sales_ID=? ");
				v.setParameter(1, saleid);
				List<SalesRecord> sarec=(List<SalesRecord>)v.getResultList();
				System.out.println("sales record size -- >> "+sarec);
				if(sarec.size()>0)
				{	
					int sno=1;						
					for(SalesRecord sr:sarec)
					{						
						BigDecimal quan=BigDecimal.valueOf(0),amnt=BigDecimal.valueOf(0);								
						v=null;
						v=entityManager.createQuery("from SalesReturn where sales_record_ID=?");
						v.setParameter(1, sr.getSales_record_ID());
						List<SalesReturn> salret=(List<SalesReturn>)v.getResultList();
						if(salret.size()>0)
						{
							quan=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));
							amnt=new BigDecimal(salret.get(0).getSalesRecord().getProduct().getActualPrice()).multiply(quan);
							amntt=(new BigDecimal(salret.get(0).getSalesRecord().getProduct().getActualPrice()).multiply(new BigDecimal(salret.get(0).getSalesRecord().getSoldQuantity()))).subtract(amnt);
						}
						else
						{
							amnt=new BigDecimal(sr.getProduct().getActualPrice()).multiply(new BigDecimal(sr.getSoldQuantity()));
							amntt=amnt;
						}
						returnamnt=returnamnt.add(amntt);
					}
					System.out.println("total amnt -- > "+returnamnt);
				}
				if(sale.get(0).getTaxType().equals("NO Tax"))
				{
					total=returnamnt;
				}
				else if(sale.get(0).getTaxType().equals("10% Tax"))
				{
					total=returnamnt.add(returnamnt.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
				}
				try
				{
					if(sale.get(0).getDiscountType().equals(""))
					{
						amntz=total;
					}
					else if(sale.get(0).getDiscountType().equals("IDR"))
					{
						amntz=total.subtract(new BigDecimal(sale.get(0).getDiscountAmount()));
					}
					else if(sale.get(0).getDiscountType().equals("%"))
					{
						amntz=total.subtract(total.multiply(new BigDecimal(sale.get(0).getDiscountAmount())).divide(BigDecimal.valueOf(100)));
					}
				}
				catch(Exception e)
				{
					amntz=total;
				}
				System.out.println("after tax - > "+amntz);
				salesOrderDataBean.setSaleTotalAmount(""+amntz.setScale(0,BigDecimal.ROUND_HALF_UP));
				SalesOrderDataBean  list=new SalesOrderDataBean();
				list.setSaleDate(sale.get(0).getSalesOrderDate());
				list.setSaleSalesOrderNo(sale.get(0).getSalesOrderNumber());
				list.setSaleCustomerName(sale.get(0).getProjet().getProjectName());
				list.setSaleTotalAmount(""+amntz.setScale(0,BigDecimal.ROUND_HALF_UP));
				list.setSaleStaffId(sale.get(0).getEmployee().getEmployeeName());
				litss.add(list);
				salesOrderDataBean.setSalesList(litss);				
				System.out.println("size - >"+salesOrderDataBean.getSalesList().size());
				salesOrderDataBean.setSalesId(saleid);
				salesOrderDataBean.setRetamount(""+amntz.setScale(0,BigDecimal.ROUND_HALF_UP));
				salesOrderDataBean.setSaleCategory(sale.get(0).getSalesCategory());
			}						
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";	
	}
	
	@Transactional(value="transactionManager")
	public String paymentinSales(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		try
		{
			v=entityManager.createQuery("from Invoice where sales_ID=?");
			v.setParameter(1, salesOrderDataBean.getSalesId());			
			List<Invoice> inv=(List<Invoice>)v.getResultList();
			int invid=0;
			if(inv.size()>0)
			{
				invid=inv.get(0).getInvoice_ID();
				System.out.println("invoice id - > "+invid);
				v=entityManager.createQuery("from Payment where invoice_ID=?");
				v.setParameter(1, invid);
				List<Payment> pay=(List<Payment>) v.getResultList();
				int payid=0;
				if(pay.size()>0)
				{
					payid=pay.get(0).getPayment_ID();
					System.out.println("payment id - > "+payid);
					Payment payy=entityManager.find(Payment.class, payid);
					payy.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
					if(salesOrderDataBean.getSaleCategory().equals("Product Trading"))
					{
						payy.setTotalAmount(salesOrderDataBean.getRetamount());
					}	
					else if(!salesOrderDataBean.getSaleCategory().equals("Product Trading"))
					{
						payy.setTotalAmount(pay.get(0).getInvoice().getSale().getTotalAmount());
					}
					entityManager.merge(payy);
				}
				else
				{
					Payment payy=new Payment();
					payy.setInvoice(entityManager.find(Invoice.class, invid));
					payy.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
					if(salesOrderDataBean.getSaleCategory().equals("Product Trading"))
					{
						payy.setTotalAmount(salesOrderDataBean.getRetamount());
					}
					else if(!salesOrderDataBean.getSaleCategory().equals("Product Trading"))
					{
						payy.setTotalAmount(inv.get(0).getSale().getTotalAmount());
					}
					payy.setApprovalStatus("pending");
					payy.setPaidAmount("0");
					payy.setPayDate(date);
					payy.setPayTime(time);
					payy.setPaymentStatus("pending");
					payy.setPaymentType("no");
					payy.setBankName("no");
					payy.setChequeDate(date);
					payy.setChequeNumber("no");
					payy.setAccountNumber("no");
					payy.setStatus("pending");
					payy.setStatus_Director("pending");
					payy.setStatus_FM("pending");
					entityManager.persist(payy);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	
	public String payForm(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		try
		{
			System.out.println(" inside "+salesOrderDataBean.getSaleCategory());
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>)v.getResultList();
			int saleid=0;
			if(sale.size()>0)
			{
				saleid=sale.get(0).getSales_ID();
				System.out.println("sales id -- > "+saleid);	
				v=null;
				v=entityManager.createQuery("from Invoice where sales_ID=?");
				v.setParameter(1, salesOrderDataBean.getSalesId());
				List<Invoice> inv=(List<Invoice>)v.getResultList();
				int invid=0;
				if(inv.size()>0)
				{
					invid=inv.get(0).getInvoice_ID();
					System.out.println("invoice id - > "+invid);
					v=entityManager.createQuery("from Payment where invoice_ID=?");
					v.setParameter(1, invid);
					List<Payment> pay=(List<Payment>) v.getResultList();
					int payid=0;
					if(pay.size()>0)
					{
						payid=pay.get(0).getPayment_ID();
						System.out.println("payment id - > "+payid);
						salesOrderDataBean.setSaleDate(sale.get(0).getSalesOrderDate());
						salesOrderDataBean.setSaleSalesOrderNo(sale.get(0).getSalesOrderNumber());
						salesOrderDataBean.setSaleCustomerName(sale.get(0).getProjet().getProjectName());
						salesOrderDataBean.setSaleTotalAmount(pay.get(0).getTotalAmount());
						salesOrderDataBean.setPaidamount(pay.get(0).getPaidAmount());
						salesOrderDataBean.setBalanceamount(""+(new BigDecimal(pay.get(0).getTotalAmount()).subtract(new BigDecimal(pay.get(0).getPaidAmount()))));
						salesOrderDataBean.setBankname(pay.get(0).getBankName());
						salesOrderDataBean.setAccountno(pay.get(0).getAccountNumber());
						salesOrderDataBean.setChequedate(pay.get(0).getChequeDate());
						salesOrderDataBean.setChequeno(pay.get(0).getChequeNumber());
						salesOrderDataBean.setPayid(payid);
						salesOrderDataBean.setPaymenttype(pay.get(0).getPaymentType());
					}	
				}
			}					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";	
	}
	
	@Transactional(value="transactionManager")
	public String paysales(SalesOrderDataBean salesOrderDataBean)
	{
		BigDecimal paidd=BigDecimal.valueOf(0),total=BigDecimal.valueOf(0);
		paidd=new BigDecimal(salesOrderDataBean.getPaidamount()).add(new BigDecimal(salesOrderDataBean.getSalePrice()));
		System.out.println("paid - > "+paidd);
		total=new BigDecimal(salesOrderDataBean.getSaleTotalAmount());
		if(paidd.compareTo(total)==0)
		{
			System.out.println("equal -- ");
			Payment paid=entityManager.find(Payment.class, salesOrderDataBean.getPayid());
			paid.setPaymentType(salesOrderDataBean.getPaymenttype());
			paid.setBankName(salesOrderDataBean.getBankname());
			paid.setChequeDate(salesOrderDataBean.getChequedate());
			paid.setChequeNumber(salesOrderDataBean.getChequeno());
			paid.setAccountNumber(salesOrderDataBean.getAccountno());
			paid.setPaidAmount(""+paidd);
			paid.setPaymentStatus("paid");
			entityManager.merge(paid);
		}
		else 
		{
			System.out.println("not equal -- ");
			Payment paid=entityManager.find(Payment.class, salesOrderDataBean.getPayid());
			paid.setPaymentType(salesOrderDataBean.getPaymenttype());
			paid.setBankName(salesOrderDataBean.getBankname());
			paid.setChequeDate(salesOrderDataBean.getChequedate());
			paid.setChequeNumber(salesOrderDataBean.getChequeno());
			paid.setAccountNumber(salesOrderDataBean.getAccountno());
			paid.setPaidAmount(""+paidd);
			entityManager.merge(paid);
		}
		return "";
	}

	public int remSalesPaymnetdirector()
	{
		int c=0;
		Query v=null;
		v=entityManager.createQuery("from Invoice where (invoiceStatus='Stock Out Invoice' or invoiceStatus='Sales Invoice')");
		List<Invoice> in=(List<Invoice>) v.getResultList();
		if(in.size()>0)
		{			
			for (int i = 0; i <in.size(); i++)
			{
				v=null;
				v=entityManager.createQuery("from Payment where invoice_ID=? and paymentStatus='paid' and status_Director='pending'");
				v.setParameter(1, in.get(i).getInvoice_ID());
				List<Payment> pay=(List<Payment>)v.getResultList();
				if(pay.size()>0)
				{
					c++;
				}
				else
				{
					
				}
			}			
		}		
		System.out.println("sales payment waiting director - "+c);
		return c;
	}

	public int remSalesPaymentFM()
	{
		int c=0;
		Query v=null;
		v=entityManager.createQuery("from Invoice where (invoiceStatus='Stock Out Invoice' or invoiceStatus='Sales Invoice')");
		List<Invoice> in=(List<Invoice>) v.getResultList();
		if(in.size()>0)
		{			
			for (int i = 0; i <in.size(); i++)
			{
				v=null;
				v=entityManager.createQuery("from Payment where invoice_ID=? and paymentStatus='paid' and status_FM='pending'");
				v.setParameter(1, in.get(i).getInvoice_ID());
				List<Payment> pay=(List<Payment>)v.getResultList();
				if(pay.size()>0)
				{
					c++;
				}
				else
				{
					
				}
			}
		}
		System.out.println("sales payment waiting FM - "+c);
		return c;	
	}

	public String paymentFM(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;	
		List<SalesOrderDataBean> lisst=new ArrayList<SalesOrderDataBean>();
		try
		{
			v=entityManager.createQuery("from Invoice where invoiceStatus='Sales Invoice'");
			List<Invoice> in=(List<Invoice>) v.getResultList();
			if(in.size()>0)
			{			
				for (int i = 0; i <in.size(); i++)
				{
					v=null;
					v=entityManager.createQuery("from Payment where invoice_ID=? and paymentStatus='paid' and status_FM='pending'");
					v.setParameter(1, in.get(i).getInvoice_ID());
					List<Payment> pay=(List<Payment>)v.getResultList();
					if(pay.size()>0)
					{
						int c=1;
						SalesOrderDataBean list=new SalesOrderDataBean();
						list.setSaleSerialNo(""+c);						
						list.setSaleDate(pay.get(0).getInvoice().getSale().getSalesDate());
						list.setSaleSalesOrderNo(pay.get(0).getInvoice().getSale().getSalesOrderNumber());
						list.setSaleCategory(pay.get(0).getInvoice().getSale().getSalesCategory());
						list.setSaleTotalAmount(pay.get(0).getTotalAmount());							
						lisst.add(list);
						salesOrderDataBean.setSalist(lisst);
						c++;						
					}
					else
					{
						salesOrderDataBean.setSalist(lisst);
					}
				}
			}			
			System.out.println("payment approval FM size - > "+salesOrderDataBean.getSalist());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String paymentDirector(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;	
		List<SalesOrderDataBean> lisst=new ArrayList<SalesOrderDataBean>();
		try
		{
			v=entityManager.createQuery("from Invoice where invoiceStatus='Sales Invoice'");
			List<Invoice> in=(List<Invoice>) v.getResultList();
			if(in.size()>0)
			{			
				for (int i = 0; i <in.size(); i++)
				{
					v=null;
					v=entityManager.createQuery("from Payment where invoice_ID=? and paymentStatus='paid' and status_Director='pending'");
					v.setParameter(1, in.get(i).getInvoice_ID());
					List<Payment> pay=(List<Payment>)v.getResultList();
					if(pay.size()>0)
					{
						int c=1;
						SalesOrderDataBean list=new SalesOrderDataBean();
						list.setSaleSerialNo(""+c);
						list.setSaleDate(pay.get(0).getInvoice().getSale().getSalesDate());
						list.setSaleSalesOrderNo(pay.get(0).getInvoice().getSale().getSalesOrderNumber());
						list.setSaleCategory(pay.get(0).getInvoice().getSale().getSalesCategory());
						list.setSaleTotalAmount(pay.get(0).getTotalAmount());						
						lisst.add(list);
						salesOrderDataBean.setSalist(lisst);
						c++;						
					}
					else
					{
						salesOrderDataBean.setSalist(lisst);
					}
				}
			}			
			System.out.println("payment approval director size - > "+salesOrderDataBean.getSalist());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	@Transactional(value="transactionManager")
	public String paymentApproveFM(SalesOrderDataBean salesOrderDataBean) 
	{
		System.out.println("approve payment FM");
		Query v=null;			
		try
		{
			System.out.println("in sales table -- > ");
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			int saleid=0;
			if(sale.size()>0)
			{
				saleid=sale.get(0).getSales_ID();
				System.out.println("sale id -- > "+saleid);
				v=null;
				v=entityManager.createQuery("from Invoice where sales_ID=?");
				v.setParameter(1, saleid);
				List<Invoice> invoice=(List<Invoice>) v.getResultList();
				int invoiceid=0;	
				if(invoice.size()>0)
				{
					invoiceid=invoice.get(0).getInvoice_ID();
					System.out.println("invoice id - > "+invoiceid);
					v=null;
					v=entityManager.createQuery("from Payment where invoice_ID=?");
					v.setParameter(1, invoiceid);
					List<Payment> payy=(List<Payment>) v.getResultList();
					int payid=0;
					if(payy.size()>0)
					{
						payid=payy.get(0).getPayment_ID();
						System.out.println("payment id - . "+payid);
						Payment paid=entityManager.find(Payment.class, payid);
						paid.setStatus_FM("Approved");
						entityManager.merge(paid);
						if(payy.get(0).getStatus_Director().equals("Approved"))
						{
							Sale saleup=entityManager.find(Sale.class, saleid);
							saleup.setPaymentStatus("paid");
							entityManager.merge(saleup);
						}
						if(payy.get(0).getStatus_FM().equals("Approved") && payy.get(0).getStatus_Director().equals("Approved"))
						{
							Payment paid1=entityManager.find(Payment.class, payid);
							paid1.setStatus("Approved");
							entityManager.merge(paid1);
						}
					}
				}					
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Transactional(value="transactionManager")
	public String paymentApproveDirector(SalesOrderDataBean salesOrderDataBean) 
	{
		System.out.println("approve payment director ");
		Query v=null;			
		try
		{
			System.out.println("in sales table -- ");
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			int saleid=0;
			if(sale.size()>0)
			{
				saleid=sale.get(0).getSales_ID();
				System.out.println("sale id -- > "+saleid);
				v=null;
				v=entityManager.createQuery("from Invoice where sales_ID=?");
				v.setParameter(1, saleid);
				List<Invoice> invoice=(List<Invoice>) v.getResultList();
				int invoiceid=0;	
				if(invoice.size()>0)
				{
					invoiceid=invoice.get(0).getInvoice_ID();
					System.out.println("invoice id - > "+invoiceid);
					v=null;
					v=entityManager.createQuery("from Payment where invoice_ID=?");
					v.setParameter(1, invoiceid);
					List<Payment> payy=(List<Payment>) v.getResultList();
					int payid=0;
					if(payy.size()>0)
					{
						payid=payy.get(0).getPayment_ID();
						System.out.println("payment id - . "+payid);
						Payment paid=entityManager.find(Payment.class, payid);
						paid.setStatus_Director("Approved");
						entityManager.merge(paid);
						if(payy.get(0).getStatus_FM().equals("Approved"))
						{
							Sale saleup=entityManager.find(Sale.class, saleid);
							saleup.setPaymentStatus("paid");
							entityManager.merge(saleup);
						}
						if(payy.get(0).getStatus_FM().equals("Approved") && payy.get(0).getStatus_Director().equals("Approved"))
						{
							Payment paid1=entityManager.find(Payment.class, payid);
							paid1.setStatus("Approved");
							entityManager.merge(paid1);
						}
					}
				}					
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public List<String> paymentCategory(SalesOrderDataBean salesOrderDataBean)
	{
		List<String> payment=new ArrayList<String>();
		Query v=null;
		try
		{
			if(salesOrderDataBean.getSaleCategory().equals("Product Trading"))
			{
				System.out.println(" inside "+salesOrderDataBean.getSaleCategory());
				v=entityManager.createQuery("from Sale where salesDeliveryStatus='Delivered' and salesCategory='Product Trading'");
				List<Sale> sale=(ArrayList<Sale>)v.getResultList();		
				System.out.println("sales size - > "+sale.size());			
				if(sale.size()==0)
				{
					System.out.println("sales empty - -");
				}		
				else
				{			
					for (Sale sales : sale)
					{
						String purchaselist2 =new String();
						System.out.println("order number------>"+sales.getSalesOrderNumber());
						purchaselist2=sales.getSalesOrderNumber();				
						payment.add(purchaselist2);
					}
				}
			}
			else if(salesOrderDataBean.getSaleCategory().equals("Raw Material"))
			{
				System.out.println(" inside "+salesOrderDataBean.getSaleCategory());
				v=entityManager.createQuery("from StockoutManual where status='Active' and limitApprovalStatus='Approved' and category='Raw Material'");
				List<StockoutManual> sale=(ArrayList<StockoutManual>)v.getResultList();		
				System.out.println("sales size - > "+sale.size());			
				if(sale.size()==0)
				{
					System.out.println("sales empty - -");
				}		
				else
				{			
					for (StockoutManual sales : sale)
					{
						String purchaselist2 =new String();
						System.out.println("order number------>"+sales.getStockoutOrderNumber());
						purchaselist2=sales.getStockoutOrderNumber();				
						payment.add(purchaselist2);
					}
				}
			}
			else if(salesOrderDataBean.getSaleCategory().equals("Service"))
			{
				System.out.println(" inside "+salesOrderDataBean.getSaleCategory());
				v=entityManager.createQuery("from StockoutManual where status='Active' and limitApprovalStatus='Approved' and category='Service'");
				List<StockoutManual> sale=(ArrayList<StockoutManual>)v.getResultList();		
				System.out.println("sales size - > "+sale.size());			
				if(sale.size()==0)
				{
					System.out.println("sales empty - -");
				}		
				else
				{			
					for (StockoutManual sales : sale)
					{
						String purchaselist2 =new String();
						System.out.println("order number------>"+sales.getStockoutOrderNumber());
						purchaselist2=sales.getStockoutOrderNumber();				
						payment.add(purchaselist2);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return payment;
	}
	
	public String paymentStatusView1(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		ArrayList<SalesOrderDataBean> paylist=new ArrayList<SalesOrderDataBean>();
		try
		{
			System.out.println("category -1- > "+salesOrderDataBean.getSaleCategory());
			v=entityManager.createQuery("from Sale where salesOrderDate between ? and ? and salesDeliveryStatus='Delivered'");
			v.setParameter(1, salesOrderDataBean.getSalFromDate());
			v.setParameter(2, salesOrderDataBean.getSaleToDate());
			List<Sale> sale=(List<Sale>) v.getResultList();
			if(sale.size()>0)
			{
				System.out.println("sale size - >"+sale.size());
				for (int i = 0; i < sale.size(); i++) 
				{						
					v=null;
					v=entityManager.createQuery("from Invoice where sales_ID=?");
					v.setParameter(1, sale.get(i).getSales_ID());
					List<Invoice> inv=(List<Invoice>) v.getResultList();
					int invid=0;
					if(inv.size()>0)
					{
						invid=inv.get(0).getInvoice_ID();
						System.out.println("invoice id - > "+invid);
						v=null;
						v=entityManager.createQuery("from Payment where invoice_ID=?");
						v.setParameter(1, invid);
						List<Payment> payy=(List<Payment>) v.getResultList();
						if(payy.size()>0)
						{
							SalesOrderDataBean payl=new SalesOrderDataBean();
							payl.setSaleDate(payy.get(0).getInvoice().getSale().getSalesOrderDate());
							payl.setSaleSalesOrderNo(payy.get(0).getInvoice().getSale().getSalesOrderNumber());
							payl.setSaleCustomerName(payy.get(0).getInvoice().getSale().getProjet().getProjectName());
							payl.setSaleTotalAmount(payy.get(0).getTotalAmount());							
							if(Integer.parseInt(payy.get(0).getTotalAmount())>Integer.parseInt(payy.get(0).getPaidAmount()))
							{
								payl.setStatus("pending");
								payl.setBalanceamount(""+(new BigDecimal(payy.get(0).getTotalAmount()).subtract(new BigDecimal(payy.get(0).getPaidAmount()))));
							}
							if(Integer.parseInt(payy.get(0).getTotalAmount())==Integer.parseInt(payy.get(0).getPaidAmount()))
							{
								payl.setStatus("paid");
								payl.setBalanceamount("0");
							}	
							if(Integer.parseInt(payy.get(0).getTotalAmount())<=Integer.parseInt(payy.get(0).getPaidAmount()))
							{
								payl.setStatus("paid");
								payl.setBalanceamount("0");
							}	
							paylist.add(payl);
							salesOrderDataBean.setSalesList(paylist);
						}
						else
						{
							SalesOrderDataBean payl=new SalesOrderDataBean();
							payl.setSaleDate(inv.get(0).getSale().getSalesOrderDate());
							payl.setSaleSalesOrderNo(inv.get(0).getSale().getSalesOrderNumber());
							payl.setSaleCustomerName(inv.get(0).getSale().getProjet().getProjectName());
							payl.setSaleTotalAmount(inv.get(0).getSale().getTotalAmount());
							payl.setBalanceamount(inv.get(0).getSale().getTotalAmount());
							payl.setStatus("pending");															
							paylist.add(payl);
							salesOrderDataBean.setSalesList(paylist);
						}
					}
					else
					{
						SalesOrderDataBean payl=new SalesOrderDataBean();
						payl.setSaleDate(sale.get(i).getSalesOrderDate());
						payl.setSaleSalesOrderNo(sale.get(i).getSalesOrderNumber());
						payl.setSaleCustomerName(sale.get(i).getProjet().getProjectName());
						payl.setSaleTotalAmount(sale.get(i).getTotalAmount());
						payl.setBalanceamount(sale.get(i).getTotalAmount());
						payl.setStatus("pending");															
						paylist.add(payl);
						salesOrderDataBean.setSalesList(paylist);
					}
				}
			}			
			/*else if(!salesOrderDataBean.getSaleCategory().equals("Product Trading"))
			{
				System.out.println("category -- > "+salesOrderDataBean.getSaleCategory());
				v=null;
				v=entityManager.createQuery("from StockoutManual where stockoutOrderDate between ? and ? and category=? and limitApprovalStatus='Approved'");
				v.setParameter(1, salesOrderDataBean.getSalFromDate());
				System.out.println("from date - > "+salesOrderDataBean.getSalFromDate());
				v.setParameter(2, salesOrderDataBean.getSaleToDate());
				System.out.println("to date - > "+salesOrderDataBean.getSaleToDate());
				v.setParameter(3, salesOrderDataBean.getSaleCategory());
				System.out.println("category - > "+salesOrderDataBean.getSaleCategory());
				List<StockoutManual> sales=(List<StockoutManual>) v.getResultList();
				System.out.println("stockout size -> "+sales.size());
				if(sales.size()>0)
				{
					for (int i = 0; i < sales.size(); i++) 
					{						
						v=null;
						v=entityManager.createQuery("from Invoice where stock_out_ID=?");
						v.setParameter(1, sales.get(i).getStock_out_ID());
						List<Invoice> inv=(List<Invoice>) v.getResultList();
						int invid=0;
						if(inv.size()>0)
						{
							invid=inv.get(0).getInvoice_ID();
							System.out.println("invoice id - > "+invid);
							v=null;
							v=entityManager.createQuery("from Payment where invoice_ID=?");
							v.setParameter(1, invid);
							List<Payment> payy=(List<Payment>) v.getResultList();
							if(payy.size()>0)
							{
								SalesOrderDataBean payl=new SalesOrderDataBean();
								payl.setSaleDate(payy.get(0).getInvoice().getStockoutManual().getStockoutOrderDate());
								payl.setSaleSalesOrderNo(payy.get(0).getInvoice().getStockoutManual().getStockoutOrderNumber());
								payl.setSaleCustomerName(payy.get(0).getInvoice().getStockoutManual().getCustomer().getCustomerName());
								payl.setSaleTotalAmount(payy.get(0).getTotalAmount());
								payl.setBalanceamount(""+(new BigDecimal(payy.get(0).getTotalAmount()).subtract(new BigDecimal(payy.get(0).getPaidAmount()))));
								if(Integer.parseInt(payy.get(0).getTotalAmount())>Integer.parseInt(payy.get(0).getPaidAmount()))
								{
									payl.setStatus("pending");
								}
								if(Integer.parseInt(payy.get(0).getTotalAmount())==Integer.parseInt(payy.get(0).getPaidAmount()))
								{
									payl.setStatus("paid");
								}							
								paylist.add(payl);
								salesOrderDataBean.setSalesList(paylist);
							}
							else
							{
								SalesOrderDataBean payl=new SalesOrderDataBean();
								payl.setSaleDate(inv.get(0).getStockoutManual().getStockoutOrderDate());
								payl.setSaleSalesOrderNo(inv.get(0).getStockoutManual().getStockoutOrderNumber());
								payl.setSaleCustomerName(inv.get(0).getStockoutManual().getCustomer().getCustomerName());
								payl.setSaleTotalAmount(inv.get(0).getStockoutManual().getTotalAmount());
								payl.setBalanceamount(inv.get(0).getStockoutManual().getTotalAmount());
								payl.setStatus("pending");															
								paylist.add(payl);
								salesOrderDataBean.setSalesList(paylist);
							}
						}
						else
						{
							SalesOrderDataBean payl=new SalesOrderDataBean();
							payl.setSaleDate(sales.get(i).getStockoutOrderDate());
							payl.setSaleSalesOrderNo(sales.get(i).getStockoutOrderNumber());
							payl.setSaleCustomerName(sales.get(i).getCustomer().getCustomerName());
							payl.setSaleTotalAmount(sales.get(i).getTotalAmount());
							payl.setBalanceamount(sales.get(i).getTotalAmount());
							payl.setStatus("pending");															
							paylist.add(payl);
							salesOrderDataBean.setSalesList(paylist);
						}
					}
				}
			}*/
			System.out.println("sales payment size - > "+salesOrderDataBean.getSalesList());			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String salesPaymentView(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("sales payment view in dao -- ");
		Query v=null;
		List<SalesOrderDataBean> lll=new ArrayList<SalesOrderDataBean>();
		BigDecimal amntz=BigDecimal.valueOf(0);
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> paylist=(List<Sale>) v.getResultList();
			int salid=0;
			if(paylist.size()>0)
			{
				salid=paylist.get(0).getSales_ID();
				System.out.println("sales id - > "+salid);
				v=entityManager.createQuery("from SalesRecord where sales_ID=?");
				v.setParameter(1, salid);
				List<SalesRecord> salrec=(List<SalesRecord>) v.getResultList();
				System.out.println("sales record size -- > "+salrec.size());
				if(salrec.size()>0)
				{
					int c=1;
					BigDecimal returnamnt=BigDecimal.valueOf(0),returnamnt1=BigDecimal.valueOf(0),total=BigDecimal.valueOf(0);
					for (int i = 0; i < salrec.size(); i++)
					{						
						v=null;
						v=entityManager.createQuery("from SalesReturn where sales_record_ID=?");
						v.setParameter(1, salrec.get(i).getSales_record_ID());
						List<SalesReturn> salret=(List<SalesReturn>)v.getResultList();
						if(salret.size()>0)
						{
							BigDecimal quan=BigDecimal.valueOf(0),amnt=BigDecimal.valueOf(0);	
							quan=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));
							amnt=new BigDecimal(salret.get(0).getSalesRecord().getProduct().getActualPrice()).multiply(quan);
							SalesOrderDataBean list=new SalesOrderDataBean();
							list.setSaleSerialNo(""+c);
							list.setSaleProductName(salrec.get(i).getProduct().getProductName());
							list.setSaleQuantity(salrec.get(i).getSoldQuantity());
							list.setSalePrice(salrec.get(i).getProduct().getActualPrice());
							list.setSaleNetAmount(""+(new BigDecimal(salrec.get(i).getProduct().getActualPrice()).multiply(new BigDecimal(salrec.get(i).getSoldQuantity()))));
							list.setReturnqauntity(""+quan);
							list.setRetamount(""+amnt);
							lll.add(list);
							salesOrderDataBean.setSalist(lll);
							c++;
							returnamnt1=(new BigDecimal(salrec.get(i).getProduct().getActualPrice()).multiply(new BigDecimal(salrec.get(i).getSoldQuantity()))).subtract(amnt);
						}
						else
						{
							SalesOrderDataBean list=new SalesOrderDataBean();
							list.setSaleSerialNo(""+c);
							list.setSaleProductName(salrec.get(i).getProduct().getProductName());
							list.setSaleQuantity(salrec.get(i).getSoldQuantity());
							list.setSalePrice(salrec.get(i).getProduct().getActualPrice());
							list.setSaleNetAmount(""+(new BigDecimal(salrec.get(i).getProduct().getActualPrice()).multiply(new BigDecimal(salrec.get(i).getSoldQuantity()))));
							list.setReturnqauntity("0");
							list.setRetamount("0");							
							lll.add(list);
							salesOrderDataBean.setSalist(lll);
							c++;
							returnamnt1=(new BigDecimal(salrec.get(i).getProduct().getActualPrice()).multiply(new BigDecimal(salrec.get(i).getSoldQuantity())));
						}	
						returnamnt=returnamnt.add(returnamnt1);
					}
					if(paylist.get(0).getTaxType().equals("NO Tax"))
					{
						total=returnamnt;
					}
					else if(paylist.get(0).getTaxType().equals("10% Tax"))
					{
						total=returnamnt.add(returnamnt.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
					}
					try
					{
						if(paylist.get(0).getDiscountType().equals(""))
						{
							amntz=total;
						}
						else if(paylist.get(0).getDiscountType().equals("IDR"))
						{
							amntz=total.subtract(new BigDecimal(paylist.get(0).getDiscountAmount()));
						}
						else if(paylist.get(0).getDiscountType().equals("%"))
						{
							amntz=total.subtract(total.multiply(new BigDecimal(paylist.get(0).getDiscountAmount())).divide(BigDecimal.valueOf(100)));
						}
					}
					catch(Exception e)
					{
						amntz=total;
					}
					salesOrderDataBean.setSaleCrossTotal(""+amntz.setScale(0,BigDecimal.ROUND_HALF_UP));
					salesOrderDataBean.setSaleTotalAmount(paylist.get(0).getTotalAmount());
					salesOrderDataBean.setSaleCustomerName(paylist.get(0).getProjet().getProjectName());
					salesOrderDataBean.setSaleDate(paylist.get(0).getSalesOrderDate());
					salesOrderDataBean.setSaleSalesOrderNo(paylist.get(0).getSalesOrderNumber());
					salesOrderDataBean.setSaleTaxType(paylist.get(0).getTaxType());					
					salesOrderDataBean.setSaleStaffId(paylist.get(0).getEmployee().getEmployee_details_ID());
					salesOrderDataBean.setSaleCategory(paylist.get(0).getSalesCategory());
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String salesReport(SalesOrderDataBean salesOrderDataBean)
	{
		List<SalesOrderDataBean> lll=new ArrayList<SalesOrderDataBean>();
		Query v=null;
		BigDecimal total=BigDecimal.valueOf(0),total1=BigDecimal.valueOf(0);
		try
		{			
			v=entityManager.createQuery("from Sale where salesOrderDate between ? and ? and paymentStatus='paid'");
			v.setParameter(1, salesOrderDataBean.getSalFromDate());
			v.setParameter(2, salesOrderDataBean.getSaleToDate());
			List<Sale> sale=(ArrayList<Sale>)v.getResultList();		
			System.out.println("sales size - > "+sale.size());			
			if(sale.size()>0)
			{
				for (int i = 0; i < sale.size(); i++)
				{
					int  c=0;
					v=entityManager.createQuery("from SalesRecord where sales_ID=?");
					v.setParameter(1, sale.get(i).getSales_ID());
					List<SalesRecord> salrec=(List<SalesRecord>) v.getResultList();
					System.out.println("sales record size -- > "+salrec.size());
					if(salrec.size()>0)
					{						
						for (int j = 0; j < salrec.size(); j++)
						{						
							v=null;
							v=entityManager.createQuery("from SalesReturn where sales_record_ID=?");
							v.setParameter(1, salrec.get(j).getSales_record_ID());
							List<SalesReturn> salret=(List<SalesReturn>)v.getResultList();
							if(salret.size()>0)
							{
								c++;
							}
						}
					}
					SalesOrderDataBean list=new SalesOrderDataBean();
					list.setSaleDate(sale.get(i).getSalesOrderDate());
					list.setSaleSalesOrderNo(sale.get(i).getSalesOrderNumber());
					list.setSaleCustomerName(sale.get(i).getProjet().getProjectName());
					if(c>0)
					{
						v=null;
						v=entityManager.createQuery("from Invoice where sales_ID=?");
						v.setParameter(1, sale.get(i).getSales_ID());
						List<Invoice> inv=(List<Invoice>) v.getResultList();
						int invid=0;
						if(inv.size()>0)
						{
							invid=inv.get(0).getInvoice_ID();
							System.out.println("invoice id - > "+invid);
							v=null;
							v=entityManager.createQuery("from Payment where invoice_ID=?");
							v.setParameter(1, invid);
							List<Payment> payy=(List<Payment>) v.getResultList();
							if(payy.size()>0)
							{
								list.setSaleTotalAmount(payy.get(0).getTotalAmount());
								total1=new BigDecimal(payy.get(0).getTotalAmount());
							}
						}
					}
					else
					{
						list.setSaleTotalAmount(sale.get(i).getTotalAmount());
						total1=new BigDecimal(sale.get(i).getTotalAmount());
					}					
					list.setSaleStaffId(sale.get(i).getEmployee().getEmployeeName());
					lll.add(list);
					salesOrderDataBean.setSalist(lll);					
					total=total.add(total1);
				}
			}			
			salesOrderDataBean.setSaleCrossTotal(""+total);
			System.out.println("total amount - > "+total);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return "";
	}
	
	public String salesReturn(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		ArrayList<SalesOrderDataBean> litss=new ArrayList<SalesOrderDataBean>();
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>)v.getResultList();
			int saleid=0;
			if(sale.size()>0)
			{
				saleid=sale.get(0).getSales_ID();
				System.out.println("sales id -- > "+saleid);
				salesOrderDataBean.setSaleDate(sale.get(0).getSalesOrderDate());
				salesOrderDataBean.setSaleSalesOrderNo(sale.get(0).getSalesOrderNumber());
				salesOrderDataBean.setSaleCustomerName(sale.get(0).getProjet().getProjectName());
				salesOrderDataBean.setSaleTotalAmount(sale.get(0).getTotalAmount());
				salesOrderDataBean.setSaleStaffId(sale.get(0).getEmployee().getEmployeeName());	
				v=null;
				v=entityManager.createQuery("from SalesRecord where sales_ID=?");
				v.setParameter(1, saleid);
				List<SalesRecord> salrec=(List<SalesRecord>) v.getResultList();
				System.out.println("sales record size -- > "+salrec.size());
				if(salrec.size()>0)
				{
					int c=1;
					for (int i = 0; i < salrec.size(); i++)
					{
						SalesOrderDataBean list=new SalesOrderDataBean();
						list.setSaleSerialNo(""+c);
						list.setSaleProductName(salrec.get(i).getProduct().getProductName());
						list.setSaleQuantity(salrec.get(i).getSoldQuantity());
						list.setSalePrice(salrec.get(i).getProduct().getActualPrice());
						list.setTick("false");
						litss.add(list);
						salesOrderDataBean.setSalesList(litss);
						c++;
					}							
					System.out.println("size - >"+salesOrderDataBean.getSalesList().size());
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";	
	}
	
	public List<String> returnSales(SalesOrderDataBean salesOrderDataBean)
	{
		List<String> returnl=new ArrayList<String>();
		Query v=null;
		try
		{
			if(salesOrderDataBean.getSaleCategory().equals("Product Trading"))
			{
				System.out.println(" inside "+salesOrderDataBean.getSaleCategory());
				v=entityManager.createQuery("from Sale where paymentStatus='pending' and (salesDeliveryStatus='Delivered' or salesDeliveryStatus='insert') and salesCategory='Product Trading'");
				List<Sale> sale=(ArrayList<Sale>)v.getResultList();		
				System.out.println("sales size - > "+sale.size());			
				if(sale.size()>0)
				{							
					for (Sale sales : sale)
					{
						String purchaselist2 =new String();
						System.out.println("order number------>"+sales.getSalesOrderNumber());
						purchaselist2=sales.getSalesOrderNumber();				
						returnl.add(purchaselist2);
					}
				}
			}
			else if(salesOrderDataBean.getSaleCategory().equals("Raw Material"))
			{
				System.out.println(" inside "+salesOrderDataBean.getSaleCategory());
				v=entityManager.createQuery("from StockoutManual where status='Active' and limitStatus='insert' and category='Raw Material'");
				List<StockoutManual> sale=(ArrayList<StockoutManual>)v.getResultList();		
				System.out.println("stk 1 size - > "+sale.size());			
				if(sale.size()>0)
				{
					for (StockoutManual sales : sale)
					{
						String purchaselist2 =new String();
						System.out.println("order number-1----->"+sales.getStockoutOrderNumber());
						purchaselist2=sales.getStockoutOrderNumber();				
						returnl.add(purchaselist2);
					}
				}
			}
			else if(salesOrderDataBean.getSaleCategory().equals("Service"))
			{
				System.out.println(" inside "+salesOrderDataBean.getSaleCategory());
				v=entityManager.createQuery("from StockoutManual where status='Active' and limitStatus='insert' and category='Service'");
				List<StockoutManual> sale=(ArrayList<StockoutManual>)v.getResultList();		
				System.out.println("stk size - > "+sale.size());			
				if(sale.size()>0)
				{
					for (StockoutManual sales : sale)
					{
						String purchaselist2 =new String();
						System.out.println("order number-2----->"+sales.getStockoutOrderNumber());
						purchaselist2=sales.getStockoutOrderNumber();				
						returnl.add(purchaselist2);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnl;
	}
	
	@Transactional(value="transactionManager")	
	public String returnSubmit(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			int salesid=0;
			if(sale.size()>0)
			{
				salesid=sale.get(0).getSales_ID();
				System.out.println("sales id -- > "+salesid);
				salesOrderDataBean.setSalesId(salesid);
				salesOrderDataBean.setSaleTaxType(sale.get(0).getTaxType());
				salesOrderDataBean.setDisamnt(sale.get(0).getDiscountAmount());
				salesOrderDataBean.setDisType(sale.get(0).getDiscountType());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String returnSubmit1(SalesOrderDataBean salesOrderDataBean, int j)
	{
		Date dd=new Date();
		BigDecimal quan=BigDecimal.valueOf(0),quan1=BigDecimal.valueOf(0),quan2=BigDecimal.valueOf(0),quan3=BigDecimal.valueOf(0);
		Timestamp time=new Timestamp(dd.getTime());		
		Query v=null;
		v=null;
		v=entityManager.createQuery("from Product where productName=? and category='Product Trading'");
		v.setParameter(1, salesOrderDataBean.getSalesList().get(j).getSaleProductName());
		List<Product> product=(List<Product>)v.getResultList();
		v=null;
		v=entityManager.createQuery("from SalesRecord where sales_ID=? and product_ID=?");
		v.setParameter(1, salesOrderDataBean.getSalesId());
		v.setParameter(2, product.get(0).getProduct_ID());
		List<SalesRecord> salrec=(List<SalesRecord>)v.getResultList();
		if(salrec.size()>0)
		{
			salesOrderDataBean.setSalesrecid(salrec.get(0).getSales_record_ID());
			v=null;
			v=entityManager.createQuery("from SalesReturn where sales_record_ID=?");
			v.setParameter(1, salrec.get(0).getSales_record_ID());
			List<SalesReturn> salret=(List<SalesReturn>)v.getResultList();
			if(salret.size()>0)
			{
				SalesReturn salere=entityManager.find(SalesReturn.class, salret.get(0).getSales_return_ID());
				quan1=new BigDecimal(salesOrderDataBean.getSalesList().get(j).getReturnqauntity()).add(new BigDecimal(salret.get(0).getNormalReturn()));
				quan2=new BigDecimal(salesOrderDataBean.getSalesList().get(j).getDr()).add(new BigDecimal(salret.get(0).getDamageReturn()));
				quan3=quan1.add(quan2);
				salere.setNormalReturn(""+quan1);
				salere.setDamageReturn(""+quan2);
				salere.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				if(quan3.compareTo(new BigDecimal(salret.get(0).getSalesRecord().getSoldQuantity()))==0)
				{
					salere.setStatus("Return");
					salesOrderDataBean.setStatus("return");
				}
				else
				{
					salere.setStatus("partial");
					salesOrderDataBean.setStatus("partial");
				}
				salere.setSalesReturnDate(dd);
				salere.setSalesReturnTime(time);
				entityManager.merge(salere);
			}
			else
			{
				quan=new BigDecimal(salesOrderDataBean.getSalesList().get(j).getReturnqauntity()).add(new BigDecimal(salesOrderDataBean.getSalesList().get(j).getDr()));
				System.out.println("total quantity - > "+quan);
				String quann=""+quan;				
				SalesReturn salere=new SalesReturn();
				salere.setNormalReturn(salesOrderDataBean.getSalesList().get(j).getReturnqauntity());
				salere.setDamageReturn(salesOrderDataBean.getSalesList().get(j).getDr());
				salere.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				salere.setSalesRecord(entityManager.find(SalesRecord.class, salrec.get(0).getSales_record_ID()));
				if(Integer.parseInt(salrec.get(0).getSoldQuantity())==Integer.parseInt(quann))
				{
					salere.setStatus("Return");
					salesOrderDataBean.setStatus("return");
				}
				else
				{
					salere.setStatus("partial");
					salesOrderDataBean.setStatus("partial");
				}
				salere.setSalesReturnDate(dd);
				salere.setSalesReturnTime(time);
				entityManager.persist(salere);
			}
		}	
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String salesUpdateReturn(SalesOrderDataBean salesOrderDataBean)
	{
		BigDecimal nrq=BigDecimal.valueOf(0),totretamnt=BigDecimal.valueOf(0),totamnt=BigDecimal.valueOf(0);
		BigDecimal amnt=BigDecimal.valueOf(0),amnt1=BigDecimal.valueOf(0),total=BigDecimal.valueOf(0);
		Query v=null;
		v=entityManager.createQuery("from SalesRecord where sales_ID=?");
		v.setParameter(1, salesOrderDataBean.getSalesId());
		List<SalesRecord> salrec=(List<SalesRecord>)v.getResultList();
		if(salrec.size()>0)
		{
			int c=0;
			for (int i = 0; i < salrec.size(); i++) 
			{
				v=null;
				v=entityManager.createQuery("from SalesReturn where sales_record_ID=?");
				v.setParameter(1, salrec.get(i).getSales_record_ID());
				List<SalesReturn> salret=(List<SalesReturn>)v.getResultList();
				if(salret.size()>0)
				{
					nrq=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));
					if(nrq.compareTo(new BigDecimal(salret.get(0).getSalesRecord().getSoldQuantity()))==0)
					{
						c++;
					}
					totretamnt=(nrq.multiply(new BigDecimal(salret.get(0).getSalesRecord().getProduct().getActualPrice()))).add(totretamnt);
					System.out.println("total return  amount -- > "+totretamnt);
				}
				totamnt=(new BigDecimal(salrec.get(i).getSoldQuantity()).multiply(new BigDecimal(salrec.get(i).getProduct().getActualPrice()))).add(totamnt);
				System.out.println("total amount -- > "+totamnt);
			}
			amnt=totamnt.subtract(totretamnt);
			if(salesOrderDataBean.getSaleTaxType().equals("NO Tax"))
			{
				amnt1=amnt;
			}
			else if(salesOrderDataBean.getSaleTaxType().equals("10% Tax"))
			{
				amnt1=amnt.add(amnt.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
			}
			try
			{
				if(salesOrderDataBean.getDisType().equals(""))
				{
					total=amnt1;
				}
				else if(salesOrderDataBean.getDisType().equals("IDR"))
				{
					total=amnt1.subtract(new BigDecimal(salesOrderDataBean.getDisamnt()));
				}
				else if(salesOrderDataBean.getDisType().equals("%"))
				{
					total=amnt1.subtract(amnt1.multiply(new BigDecimal(salesOrderDataBean.getDisamnt())).divide(BigDecimal.valueOf(100)));
				}
			}
			catch(Exception e)
			{
				total=amnt1;
			}
			System.out.println("return amnt - > "+total);
			salesOrderDataBean.setRetamount(""+total.setScale(0,BigDecimal.ROUND_HALF_UP));
			if(c==salrec.size())
			{
				Sale upd=entityManager.find(Sale.class, salesOrderDataBean.getSalesId());
				upd.setSalesDeliveryStatus("Return");
				entityManager.merge(upd);
			}
		}		
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String stockUpdateReturnNr(SalesOrderDataBean salesOrderDataBean, int j)
	{
		System.out.println("stock update barcode in nr -- ");
		Query v=null;
		try
		{
			v=entityManager.createQuery("from Batch where productName=? and status='Active' and category='Product Trading'");
			v.setParameter(1, salesOrderDataBean.getSalesList().get(j).getSaleProductName());
			List<Batch> batch=(List<Batch>) v.getResultList();
			if(batch.size()>0)
			{
				v=null;
				v=entityManager.createQuery("from Barcode where batch_ID=? and status='Barcode Generated'");
				v.setParameter(1, batch.get(0).getBatch_ID());
				List<Barcode> barcode=(List<Barcode>) v.getResultList();
				if(barcode.size()>0)
				{
					Barcode stkup=entityManager.find(Barcode.class, barcode.get(0).getBarcode_ID());
					stkup.setStockIn(""+(new BigDecimal(barcode.get(0).getStockIn())).add(new BigDecimal(salesOrderDataBean.getSalesList().get(j).getReturnqauntity())));
					stkup.setStockOut(""+(new BigDecimal(barcode.get(0).getStockOut())).subtract(new BigDecimal(salesOrderDataBean.getSalesList().get(j).getReturnqauntity())));
					entityManager.merge(stkup);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return "";
	}

	@Transactional(value="transactionManager")
	public String stockUpdateReturnDr(SalesOrderDataBean salesOrderDataBean, int j)
	{
		System.out.println("stock update damge in dr -- ");
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		try
		{
			v=entityManager.createQuery("from Batch where productName=? and status='Active' and category='Product Trading'");
			v.setParameter(1, salesOrderDataBean.getSalesList().get(j).getSaleProductName());
			List<Batch> batch=(List<Batch>) v.getResultList();
			if(batch.size()>0)
			{
				StockDamage damup=new StockDamage();
				damup.setBatch(entityManager.find(Batch.class,batch.get(0).getBatch_ID()));
				damup.setDamageReturn(salesOrderDataBean.getSalesList().get(j).getDr());
				damup.setStatus("Damage");
				damup.setStock_ID(batch.get(0).getStock().getStock_ID());
				damup.setPurchase_ID(batch.get(0).getStock().getPurchase().getPurchase_ID());
				damup.setNormalReturn("0");
				damup.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				damup.setDamageDate(date);
				damup.setDaamgeTime(time);
				entityManager.persist(damup);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String paymentCheckReturn(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("payment check for return dao -- ");
		Query v=null;
		try
		{
			v=entityManager.createQuery("from Invoice where sales_ID=? and invoiceStatus='Sales Invoice'");
			v.setParameter(1, salesOrderDataBean.getSalesId());
			List<Invoice> inv=(List<Invoice>) v.getResultList();
			int invid=0;
			if(inv.size()>0)
			{
				invid=inv.get(0).getInvoice_ID();
				System.out.println("invoice id -- > "+invid);
				v=null;
				v=entityManager.createQuery("from Payment where invoice_ID=?");
				v.setParameter(1, invid);
				List<Payment> pay=(List<Payment>) v.getResultList();
				int payid=0;
				if(pay.size()>0)
				{
					payid=pay.get(0).getPayment_ID();
					System.out.println("paymnet id - -> "+payid);
					if(pay.get(0).getPaymentStatus().equals("pending"))
					{
						Payment payup1=entityManager.find(Payment.class, payid);
						payup1.setTotalAmount(salesOrderDataBean.getRetamount());
						entityManager.merge(payup1);	
						if(Integer.parseInt(pay.get(0).getTotalAmount())<=Integer.parseInt(salesOrderDataBean.getRetamount()))
						{	
							System.out.println("total amount less -- ");
							if(Integer.parseInt(pay.get(0).getPaidAmount())>Integer.parseInt(pay.get(0).getTotalAmount()))
							{
								Payment payup=entityManager.find(Payment.class, payid);
								payup.setPaymentStatus("paid");
								entityManager.merge(payup);	
							}													
						}
						else if(Integer.parseInt(pay.get(0).getTotalAmount())>Integer.parseInt(salesOrderDataBean.getRetamount()))
						{	
							System.out.println("total amount greater -- ");
							if(Integer.parseInt(pay.get(0).getPaidAmount())>Integer.parseInt(salesOrderDataBean.getRetamount()))
							{
								Payment payup=entityManager.find(Payment.class, payid);
								payup.setPaymentStatus("paid");
								entityManager.merge(payup);	
							}													
						}
					}
					else if(pay.get(0).getPaymentStatus().equals("paid"))
					{
						Payment payup=entityManager.find(Payment.class, payid);
						payup.setTotalAmount(salesOrderDataBean.getRetamount());
						entityManager.merge(payup);						
					}						
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String salesReturnSearch(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		List<SalesOrderDataBean> litss=new ArrayList<SalesOrderDataBean>();
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderDate between ? and ? and paymentStatus='pending' and salesDeliveryStatus='Delivered' and salesCategory='Product Trading'");
			v.setParameter(1, salesOrderDataBean.getSalFromDate());
			v.setParameter(2, salesOrderDataBean.getSaleToDate());
			List<Sale> sale=(List<Sale>)v.getResultList();
			if(sale.size()>0)
			{
				for (int i = 0; i < sale.size(); i++)
				{					
					SalesOrderDataBean list=new SalesOrderDataBean();
					list.setSaleDate(sale.get(i).getSalesOrderDate());
					list.setSaleSalesOrderNo(sale.get(i).getSalesOrderNumber());
					list.setSaleCustomerName(sale.get(i).getProjet().getProjectName());;
					list.setSaleTotalAmount(sale.get(i).getTotalAmount());
					list.setSaleStaffId(sale.get(i).getEmployee().getEmployeeName());					
					litss.add(list);
					salesOrderDataBean.setSalist(litss);
					
				}
				System.out.println("size - >"+salesOrderDataBean.getSalist().size());
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";	
	}
	
	public String quantityCheckReturn(SalesOrderDataBean salesOrderDataBean) throws LiusenException
	{
		System.out.println("quantity -- check dao - ");
		Query v=null;
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			int salesid=0;
			if(sale.size()>0)
			{
				salesid=sale.get(0).getSales_ID();
				System.out.println("sales id -- > "+salesid);
				v=null;
				v=entityManager.createQuery("from Product where productName=? and category='Product Trading'");
				v.setParameter(1, salesOrderDataBean.getSaleProductName());
				List<Product> product=(List<Product>)v.getResultList();
				v=null;
				v=entityManager.createQuery("from SalesRecord where sales_ID=? and product_ID=?");
				v.setParameter(1, salesid);
				v.setParameter(2, product.get(0).getProduct_ID());
				List<SalesRecord> salrec=(List<SalesRecord>)v.getResultList();
				int salerecid=0;
				if(salrec.size()>0)
				{
					salerecid=salrec.get(0).getSales_record_ID();
					System.out.println("sale record id -- > "+salerecid);
					v=null;
					v=entityManager.createQuery("from SalesReturn where sales_record_ID=?");
					v.setParameter(1, salerecid);
					List<SalesReturn> salret=(List<SalesReturn>)v.getResultList();
					if(salret.size()>0)
					{
						System.out.println("return table - -> ");
						BigDecimal quan=BigDecimal.valueOf(0),quan1=BigDecimal.valueOf(0);
						String qq="",qq1="";						
						qq1=salrec.get(0).getSoldQuantity();
						quan=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));							
						quan1=new BigDecimal(salrec.get(0).getSoldQuantity()).subtract(quan);
						quan=quan.add(new BigDecimal(salesOrderDataBean.getReturnqauntity())).add(new BigDecimal(salesOrderDataBean.getDr()));
						qq=""+quan;
						if(Integer.parseInt(qq)>Integer.parseInt(qq1))
						{
							throw new LiusenException("Only "+quan1+" Quantities in "+salesOrderDataBean.getSaleProductName());
						}											
					}
					else
					{
						System.out.println("not in return table - -> ");
						BigDecimal quan=BigDecimal.valueOf(0),quan1=BigDecimal.valueOf(0);
						String qq="",qq1="";
						qq1=salrec.get(0).getSoldQuantity();
						quan=quan.add(new BigDecimal(salesOrderDataBean.getReturnqauntity())).add(new BigDecimal(salesOrderDataBean.getDr()));
						qq=""+quan;
						if(Integer.parseInt(qq)>Integer.parseInt(qq1))
						{
							throw new LiusenException("Only "+qq1+" Quantities in "+salesOrderDataBean.getSaleProductName());
						}	
					}
				}
			}
		}
		finally
		{
			
		}
		return "";
	}
	
	public String returnCheck(SalesOrderDataBean salesOrderDataBean)throws LiusenException
	{
		System.out.println("return -- check dao - ");
		Query v=null;
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sale=(List<Sale>) v.getResultList();
			int salesid=0;
			if(sale.size()>0)
			{
				salesid=sale.get(0).getSales_ID();
				System.out.println("sales id -- > "+salesid);
				v=null;
				v=entityManager.createQuery("from Product where productName=? and category='Product Trading'");
				v.setParameter(1, salesOrderDataBean.getSaleProductName());
				List<Product> product=(List<Product>)v.getResultList();
				v=null;
				v=entityManager.createQuery("from SalesRecord where sales_ID=? and product_ID=?");
				v.setParameter(1, salesid);
				v.setParameter(2, product.get(0).getProduct_ID());
				List<SalesRecord> salrec=(List<SalesRecord>)v.getResultList();
				int salerecid=0;
				if(salrec.size()>0)
				{
					salerecid=salrec.get(0).getSales_record_ID();
					System.out.println("sale record id -- > "+salerecid);
					v=null;
					v=entityManager.createQuery("from SalesReturn where sales_record_ID=?");
					v.setParameter(1, salerecid);
					List<SalesReturn> salret=(List<SalesReturn>)v.getResultList();
					if(salret.size()>0)
					{
						BigDecimal quan=BigDecimal.valueOf(0),quan1=BigDecimal.valueOf(0);
						String qq1="",qq2="";
						System.out.println("return status - -> "+salesOrderDataBean.getRetstatus());
						qq1=salrec.get(0).getSoldQuantity();
						quan=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));							
						qq2=""+quan;
						if(Integer.parseInt(qq2)==Integer.parseInt(qq1))
						{
							System.out.println("returned -- ");
							throw new LiusenException("All Quantities are Returned");
						}
					}	
				}
			}
		}
		finally
		{
			
		}
		return "";	
	}

	public String viewEachSalesRecord(SalesOrderDataBean salesOrderDataBean)
	{
		Query v=null;
		List<SalesOrderDataBean> srlist=new ArrayList<SalesOrderDataBean>();
		BigDecimal amount=BigDecimal.valueOf(0);
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=? ");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> view=(List<Sale>)v.getResultList();
			System.out.println("sales id -- > "+view.get(0).getSales_ID()+" size - > "+view.size());
			if(view.size()>0)
			{				
				salesOrderDataBean.setSaleCategory(view.get(0).getSalesCategory());
				salesOrderDataBean.setSaleCustomerName(view.get(0).getProjet().getProjectName());
				salesOrderDataBean.setSaleDate(view.get(0).getSalesOrderDate());
				salesOrderDataBean.setSaleEstDate(view.get(0).getSalesDeliveryDate());
				salesOrderDataBean.setSaleStaffId(view.get(0).getEmployee().getEmployee_details_ID());
				salesOrderDataBean.setSaleTaxType(view.get(0).getTaxType());
				salesOrderDataBean.setSaleCrossTotal(view.get(0).getTotalAmount());
				try
				{
					if(view.get(0).getDiscountType().equals("") || view.get(0).getDiscountType().equals(null))
					{
						salesOrderDataBean.setDisamnt("0");
						salesOrderDataBean.setDisType("-");
					}
					else
					{
						salesOrderDataBean.setDisamnt(view.get(0).getDiscountAmount());
						salesOrderDataBean.setDisType(view.get(0).getDiscountType());
					}
				}
				catch(Exception e)
				{
					salesOrderDataBean.setDisamnt("0");
					salesOrderDataBean.setDisType("-");	
				}				
				v=null;
				v=entityManager.createQuery("from SalesRecord where sales_ID=? ");
				v.setParameter(1, view.get(0).getSales_ID());
				List<SalesRecord> sarec=(List<SalesRecord>)v.getResultList();
				System.out.println("sales record size -- >> "+sarec);
				if(sarec.size()>0)
				{	
					int sno=1;
					BigDecimal returnamnt=BigDecimal.valueOf(0),returnamnt1=BigDecimal.valueOf(0),total=BigDecimal.valueOf(0);
					for(SalesRecord sr:sarec)
					{											
						v=null;
						v=entityManager.createQuery("from SalesReturn where sales_record_ID=?");
						v.setParameter(1, sr.getSales_record_ID());
						List<SalesReturn> salret=(List<SalesReturn>)v.getResultList();
						if(salret.size()>0)
						{
							BigDecimal quan=BigDecimal.valueOf(0),amnt=BigDecimal.valueOf(0);	
							quan=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));
							amnt=new BigDecimal(salret.get(0).getSalesRecord().getProduct().getActualPrice()).multiply(quan);
							SalesOrderDataBean srr=new SalesOrderDataBean();						
							srr.setSaleProductName(salret.get(0).getSalesRecord().getProduct().getProductName());
							srr.setSalePrice(salret.get(0).getSalesRecord().getProduct().getActualPrice());
							amount=new BigDecimal(salret.get(0).getSalesRecord().getProduct().getActualPrice()).multiply(new BigDecimal(salret.get(0).getSalesRecord().getSoldQuantity()));
							System.out.println("amount -- > "+amount);	
							srr.setSaleQuantity(salret.get(0).getSalesRecord().getSoldQuantity());
							srr.setSaleSerialNo(String.valueOf(sno));
							srr.setSaleNetAmount(""+amount);
							srr.setReturnqauntity(""+quan);
							srr.setRetamount(""+amnt);
							srlist.add(srr);
							salesOrderDataBean.setSalist(srlist);
							sno++;
							returnamnt1=amount.subtract(amnt);
						}
						else
						{
							SalesOrderDataBean srr=new SalesOrderDataBean();						
							srr.setSaleProductName(sr.getProduct().getProductName());
							srr.setSalePrice(sr.getProduct().getActualPrice());
							amount=new BigDecimal(sr.getProduct().getActualPrice()).multiply(new BigDecimal(sr.getSoldQuantity()));
							System.out.println("amount -- > "+amount);	
							srr.setSaleQuantity(sr.getSoldQuantity());
							srr.setSaleSerialNo(String.valueOf(sno));
							srr.setSaleNetAmount(""+amount);
							srlist.add(srr);
							srr.setReturnqauntity("0");
							srr.setRetamount("0");
							salesOrderDataBean.setSalist(srlist);
							sno++;
							returnamnt1=amount;
						}
						returnamnt=returnamnt.add(returnamnt1);
					}
					System.out.println("list size -- > "+salesOrderDataBean.getSalist().size()+"amount zz - > "+returnamnt);
					if(view.get(0).getTaxType().equals("NO Tax"))
					{
						total=returnamnt;
					}
					else if(view.get(0).getTaxType().equals("10% Tax"))
					{
						total=returnamnt.add(returnamnt.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
					}
					if(view.get(0).getDiscountType().equals(""))
					{
						salesOrderDataBean.setSaleTotalAmount(""+total.setScale(0,BigDecimal.ROUND_HALF_UP));
					}
					else if(view.get(0).getDiscountType().equals("IDR"))
					{
						salesOrderDataBean.setSaleTotalAmount(""+(total.subtract(new BigDecimal(view.get(0).getDiscountAmount()))).setScale(0,BigDecimal.ROUND_HALF_UP));
					}
					else if(view.get(0).getDiscountType().equals("%"))
					{
						salesOrderDataBean.setSaleTotalAmount(""+(total.subtract(total.multiply(new BigDecimal(view.get(0).getDiscountAmount())).divide(BigDecimal.valueOf(100)))).setScale(0,BigDecimal.ROUND_HALF_UP));
					}
				}
			}
			System.out.println("total amount - > "+salesOrderDataBean.getSaleTotalAmount());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String salesReturnView(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("inside sales return view dao -- ");
		Query v=null;
		List<SalesOrderDataBean> list=new ArrayList<SalesOrderDataBean>();
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderDate between ? and ? and salesDeliveryStatus='Delivered' and salesCategory='Product Trading'");
			v.setParameter(1, salesOrderDataBean.getSalFromDate());
			v.setParameter(2, salesOrderDataBean.getSaleToDate());
			List<Sale> sales=(List<Sale>)v.getResultList();
			System.out.println("sales size - > "+sales.size());
			if(sales.size()>0)
			{
				for (int i = 0; i < sales.size(); i++) 
				{
					v=null;
					v=entityManager.createQuery("from SalesRecord where sales_ID=?");
					v.setParameter(1, sales.get(i).getSales_ID());
					List<SalesRecord> salrec=(List<SalesRecord>) v.getResultList();
					if(salrec.size()>0)
					{
						int c=0;
						for (int j = 0; j < salrec.size(); j++)
						{							
							v=null;
							v=entityManager.createQuery("from SalesReturn where sales_record_ID=?");
							v.setParameter(1, salrec.get(j).getSales_record_ID());
							List<SalesReturn> salret=(List<SalesReturn>) v.getResultList();
							if(salret.size()>0)
							{
								c++;
							}
							else
							{
								c=c;
							}
						}
						System.out.println("c-- > "+c);
						if(c>0)
						{
							System.out.println("inside add -- <> ");
							SalesOrderDataBean returns=new SalesOrderDataBean();
							returns.setSaleDate(sales.get(i).getSalesOrderDate());
							returns.setSaleSalesOrderNo(sales.get(i).getSalesOrderNumber());
							returns.setSaleTotalAmount(sales.get(i).getTotalAmount());
							returns.setSaleCustomerName(sales.get(i).getProjet().getProjectName());
							list.add(returns);
							salesOrderDataBean.setSalist(list);
						}
						else
						{
							System.out.println("inside  else add -- <> ");
							salesOrderDataBean.setSalist(list);
						}
					}
				}
				System.out.println("return list size -- > "+salesOrderDataBean.getSalist().size());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String returnSalesView(SalesOrderDataBean salesOrderDataBean)
	{
		System.out.println("inside sales return view dao -- ");
		Query v=null;
		ArrayList<SalesOrderDataBean> list=new ArrayList<SalesOrderDataBean>();
		try
		{
			v=entityManager.createQuery("from Sale where salesOrderNumber=?");
			v.setParameter(1, salesOrderDataBean.getSaleSalesOrderNo());
			List<Sale> sales=(List<Sale>)v.getResultList();
			System.out.println("sales size - > "+sales.size());
			if(sales.size()>0)
			{
				v=null;
				v=entityManager.createQuery("from SalesRecord where sales_ID=?");
				v.setParameter(1, sales.get(0).getSales_ID());
				List<SalesRecord> salrec=(List<SalesRecord>) v.getResultList();
				if(salrec.size()>0)
				{
					int c=1;
					for (int j = 0; j < salrec.size(); j++)
					{							
						v=null;
						v=entityManager.createQuery("from SalesReturn where sales_record_ID=?");
						v.setParameter(1, salrec.get(j).getSales_record_ID());
						List<SalesReturn> salret=(List<SalesReturn>) v.getResultList();
						if(salret.size()>0)
						{
							System.out.println("inside add -- <> ");
							SalesOrderDataBean returns=new SalesOrderDataBean();
							returns.setSaleProductName(salret.get(0).getSalesRecord().getProduct().getProductName());
							returns.setReturnqauntity(salret.get(0).getNormalReturn());
							returns.setSalePrice(salret.get(0).getSalesRecord().getProduct().getActualPrice());
							returns.setDr(salret.get(0).getDamageReturn());
							returns.setSaleQuantity(salret.get(0).getSalesRecord().getSoldQuantity());
							returns.setSaleSerialNo(""+c);
							list.add(returns);
							salesOrderDataBean.setSalesList(list);
							salesOrderDataBean.setSaleDate(sales.get(0).getSalesOrderDate());
							salesOrderDataBean.setSaleSalesOrderNo(sales.get(0).getSalesOrderNumber());
							salesOrderDataBean.setSaleTotalAmount(sales.get(0).getTotalAmount());
							salesOrderDataBean.setSaleCustomerName(sales.get(0).getProjet().getProjectName());
							salesOrderDataBean.setSaleStaffId(sales.get(0).getEmployee().getEmployeeName());
							salesOrderDataBean.setSaleTaxType(sales.get(0).getTaxType());
							salesOrderDataBean.setSaleCategory(sales.get(0).getSalesCategory());
							salesOrderDataBean.setSaleEstDate(sales.get(0).getSalesDeliveryDate());
							c++;
						}
					}					
				}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public List<String> getProjectNames() {
		List<String> projectList=null;
		Query q=null;
		System.out.println("Inside Dao Calling");
		try{
			projectList=new ArrayList<String>();
			q=entityManager.createQuery("from Projet where status='Active'");
			List<Projet> result=(List<Projet>)q.getResultList();
			if(result.size() > 0){
				for(int i=0;i<result.size();i++){
					String s=result.get(i).getProjectName();
					projectList.add(s);
				}
			}
			System.out.println("---------------"+projectList.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		return projectList;
	}
}