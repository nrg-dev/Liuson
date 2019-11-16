package com.nrg.liusen.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.faces.context.FacesContext;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nrg.liusen.domain.ProfitLossDataBean;
import com.nrg.liusen.domain.PurchaseDataBean;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.domain.SalesOrderDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.shared.Barcode;
import com.nrg.liusen.shared.Batch;
import com.nrg.liusen.shared.Employee;
import com.nrg.liusen.shared.Invoice;
import com.nrg.liusen.shared.Payment;
import com.nrg.liusen.shared.Payroll;
import com.nrg.liusen.shared.Product;
import com.nrg.liusen.shared.Purchase;
import com.nrg.liusen.shared.PurchaseApproval;
import com.nrg.liusen.shared.PurchaseRecord;
import com.nrg.liusen.shared.PurchaseReturn;
import com.nrg.liusen.shared.RawMaterial;
import com.nrg.liusen.shared.Sale;
import com.nrg.liusen.shared.SalesRecord;
import com.nrg.liusen.shared.SalesReturn;
import com.nrg.liusen.shared.StockDamage;
import com.nrg.liusen.shared.Transaction;
import com.nrg.liusen.shared.Vendor;
import com.nrg.liusen.util.OrderNumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
@Singleton
public class PurchaseDaoImpl implements PurchaseDao
{
	
	final Logger logger = LoggerFactory.getLogger(PurchaseDaoImpl.class);

	@PersistenceContext(unitName="liusen-pu")
	private EntityManager entitymanager;
	
	@PersistenceContext(unitName="liusen-pu")
	private EntityManager entitymanager1;	
	
	SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
	
	
	
	/*kasthuri @@ retrieve vendorlist*/	
	@Override
	public List<String> vendorNameList(PurchaseOrederDataBean o)
	{
		List<String>venList=new ArrayList<String>();
		List<String>projList=new ArrayList<String>();
		try
		{
			Query q=null,q1=null,q2=null;
			if(o.getPurCategory().equalsIgnoreCase("Raw Material") || o.getPurCategory().equalsIgnoreCase("Service"))
			{
				q=entitymanager.createQuery("from RawMaterial where category=? and status='Active'");
				q.setParameter(1, o.getPurCategory());
				List<RawMaterial> list1=(List<RawMaterial>)q.getResultList();
				if(list1.size()>0)
				{
					int vid=0;
					for(int i=0;i<list1.size();i++)
					{
						vid=list1.get(i).getVendor().getVendor_ID();
						System.out.println("-->> vid "+vid);
						q1=entitymanager.createQuery("from Vendor where vendor_ID=? and status='Active'");
						q1.setParameter(1, vid);
						ArrayList<Vendor> list2=(ArrayList<Vendor>)q1.getResultList();
						String vname="";
						for(int j=0;j<list2.size();j++)
						{
							vname=list2.get(j).getFirmName();
							System.out.println("-->> vname "+vname);
							venList.add(vname);
						}
						HashSet<String> hlist= new HashSet<String>(venList);
						venList.clear();
						venList.addAll(hlist);
					}
					
					
					/*q2=entitymanager.createQuery("from Product where status='Active' and (category='Project' or category='Service')");
					List<Product> list3=(List<Product>)q2.getResultList();
					if(list3.size()>0)
					{
						String projname="";
						for(int j=0;j<list3.size();j++)
						{
							projname=list3.get(j).getProductName();
							projList.add(projname);
							o.setProjectList(projList);
							System.out.println("-->>Proj List Size"+o.getProjectList().size());
							
						}
					}*/
					
				}
				System.out.println("-->> List Size"+venList.size());
			}
			else
			{
				q=entitymanager.createQuery("from Product where category=? and status='Active'");
				q.setParameter(1, o.getPurCategory());
				List<Product> list1=(List<Product>)q.getResultList();
				if(list1.size()>0)
				{
					int vid=0;
					for(int i=0;i<list1.size();i++)
					{
						vid=list1.get(i).getVendor().getVendor_ID();
						System.out.println("-->> vid "+vid);
						q1=entitymanager.createQuery("from Vendor where vendor_ID=? and status='Active'");
						q1.setParameter(1, vid);
						ArrayList<Vendor> list2=(ArrayList<Vendor>)q1.getResultList();
						String vname="";
						for(int j=0;j<list2.size();j++)
						{
							vname=list2.get(j).getFirmName();
							System.out.println("-->> vname "+vname);
							venList.add(vname);
						}
						HashSet<String> hlist= new HashSet<String>(venList);
						venList.clear();
						venList.addAll(hlist);
					}
				}
				System.out.println("-->> List Size"+venList.size());
			}	
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return venList;
	}	
	
	/*kasthuri @@ retrieve product list*/
	@Override
	public List<String> productNameList(PurchaseOrederDataBean o)
	{
		List<String>prodList=new ArrayList<String>();
		try
		{
			Query q=null,q1=null;
			q=entitymanager.createQuery("from Vendor where firmName=? and status='Active'");
			q.setParameter(1, o.getPurVendor());
			ArrayList<Vendor> list1=(ArrayList<Vendor>)q.getResultList();
			if(list1.size()>0)
			{
				int vid=0;
				vid=list1.get(0).getVendor_ID();
				System.out.println("-->> vid "+vid);
				o.setPurVendorId(""+vid);
				if(o.getPurCategory().equalsIgnoreCase("Raw Material") || o.getPurCategory().equalsIgnoreCase("Service"))
				{
					q1=entitymanager.createQuery("from RawMaterial where vendor_ID=? and category=? and status='Active' ");
					q1.setParameter(1,vid);
					q1.setParameter(2, o.getPurCategory());
					List<RawMaterial> list2=(ArrayList<RawMaterial>)q1.getResultList();
					String pname="";
					for(int i=0;i<list2.size();i++)
					{
						pname=list2.get(i).getProductName();
						prodList.add(pname);
					}
				}
				else
				{
				q1=entitymanager.createQuery("from Product where vendor_ID=? and category=? and status='Active'");
				q1.setParameter(1,vid);
				q1.setParameter(2, o.getPurCategory());
				List<Product> list2=(ArrayList<Product>)q1.getResultList();
				String pname="";
				for(int i=0;i<list2.size();i++)
				{
					pname=list2.get(i).getProductName();
					prodList.add(pname);
				}
				}
				HashSet<String> hlist= new HashSet<String>(prodList);
				prodList.clear();
				prodList.addAll(hlist);
			}
			System.out.println("-->> List Size"+prodList.size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return prodList;
	}	
	
	/*kasthuri @@ retrieve product price*/
	@Transactional(value="transactionManager")
	public String productPrice(PurchaseOrederDataBean p) 
	{
		try
		{
			List<Product> prodList=null;
			List<RawMaterial> rawList=null;
			Query q2=null;
			if(p.getPurCategory().equalsIgnoreCase("Raw Material") || p.getPurCategory().equalsIgnoreCase("Service") )
			{
				q2=entitymanager.createQuery("from RawMaterial where productName=? and status='Active'  ");
				q2.setParameter(1, p.getPurProductName());
				rawList=(List<RawMaterial>)q2.getResultList();
				int id=0;
				if(rawList.size()>0)
				{
					id=rawList.get(0).getRaw_ID();
					p.setProductId(""+id);
					p.setPurprice(rawList.get(0).getActualPrice());
					p.setUnit(rawList.get(0).getUnit());
					System.out.println("-->> price "+p.getPurprice());
				}
			}
			else
			{	
			q2=entitymanager.createQuery("from Product where productName=? and status='Active'  ");
			q2.setParameter(1, p.getPurProductName());
			prodList=(List<Product>)q2.getResultList();
			int id=0;
			if(prodList.size()>0)
			{
				id=prodList.get(0).getProduct_ID();
				p.setProductId(""+id);
				p.setPurprice(prodList.get(0).getActualPrice());
				p.setUnit(prodList.get(0).getUnit());
				System.out.println("-->> price "+p.getPurprice());
			}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}				
		return null;
	}
	
	/*kasthuri @@ purchase order insertion*/
	@Transactional(value="transactionManager")
	public String purchaseInsertion1(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
			Query q= null,q1=null,q2=null,q4=null;			
			String purRefNum="";
			Date d= new Date();
			Timestamp t=new Timestamp(d.getTime());
			String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
			System.out.println("-->> dao insertion ");
			q=entitymanager.createQuery("from Purchase ");
			List<Purchase> list1=(List<Purchase>)q.getResultList();
			if(list1.size()>0)
			{
				purRefNum=OrderNumber.getPurRefNum(list1.size());
			}
			else
			{
				purRefNum="PO0001";
				
			}
			int eid=0;	
			q1=entitymanager.createQuery("from Employee where employee_details_ID=? ");
			q1.setParameter(1, p.getPurStaffName());
			List<Employee> list2=(List<Employee>)q1.getResultList();
			if(list2.size()>0)
			{
			eid=list2.get(0).getEmployee_ID();
			}
			int vid=0;
			vid=Integer.parseInt(p.getPurVendorId());
			Purchase po=new Purchase();
			po.setCrossTotal(p.getPurCrossTotal());
			po.setDiscountAmount(p.getPurDiscAmmount());
			po.setDiscountType(p.getPurDiscType());
			po.setPurchaseDate(d);
			po.setPurchaseEstimatedDate(p.getPurEstDate());
			po.setPurchaseOrderDate(p.getPurdate());
			po.setStatus("ordered");
			po.setTotalAmount(""+new BigDecimal(p.getPurTotalPrice()).setScale(0,BigDecimal.ROUND_HALF_UP));
			po.setPurchaseOrderNumber(purRefNum);
			po.setEditStatus("none");
			po.setEditLoginStatus("none");
			po.setLoginStatus(login);
			po.setPurchaseTime(t);
			po.setPurchaseApprovalStatus("pending");
			po.setEmployee(entitymanager.find(Employee.class, eid));
			po.setVendor(entitymanager.find(Vendor.class, vid));
			po.setCategory(p.getPurCategory());
			po.setTypeofProject(p.getTaxType());
			po.setPaymentStatus("pending");
			po.setDeliveryStatus("pending");
			po.setStatus2("waiting");
			entitymanager.persist(po);
			
			int newid=0;
			q2=entitymanager.createQuery("from Purchase");
			List<Purchase> list3=(List<Purchase>)q2.getResultList();
			if(list3.size()>0)
			{
			p.setPurOrderNo(""+(list3.get(list3.size()-1).getPurchase_ID()));
			p.setPurRefNum(purRefNum);
			
			newid=list3.get(list3.size()-1).getPurchase_ID();
			}
			
			PurchaseApproval pa=new PurchaseApproval();
			pa.setPurchaseApprovalStatus("pending");
			pa.setStatus("pending");
			pa.setPurchase_approval_MMstatus("pending");
			pa.setPurchase_approval_GMstatus("pending");
			
			pa.setPurchaseDeliveryStatus("pending");
			pa.setStatus2("pending");
			pa.setPurchase_delivery_GMstatus("pending");
			pa.setPurchase_delivery_PMstatus("pending");
			
			pa.setPurchaseApprovalTime(t);
			pa.setPurchase(entitymanager.find(Purchase.class, newid));
			entitymanager.persist(pa);
			
			
			System.out.println("-->> purchase inserted");
		
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri @@ purchase order insertion*/
	@Transactional(value="transactionManager")
	public String purchaseInsertion2(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
			int id=Integer.parseInt(p.getPurOrderNo());
			
			List<Product> prodList=null;
			List<RawMaterial> rawList=null;
			int prodid=0;
			int rawid=0;
			Query q2=null;
			if(p.getPurCategory().equalsIgnoreCase("Raw Material") || p.getPurCategory().equalsIgnoreCase("Service"))
			{
				q2=entitymanager.createQuery("from RawMaterial where productName=? and status='Active'  ");
				q2.setParameter(1, p.getPurProductName());
				rawList=(List<RawMaterial>)q2.getResultList();
				if(rawList.size()>0)
				{
					rawid=rawList.get(0).getRaw_ID();
				}
			}
			else
			{	
				q2=entitymanager.createQuery("from Product where productName=? and status='Active'  ");
				q2.setParameter(1, p.getPurProductName());
				prodList=(List<Product>)q2.getResultList();
				if(prodList.size()>0)
				{
					prodid=prodList.get(0).getProduct_ID();
				}
			}
			System.out.println("-->> id "+id);
			System.out.println("-->> prodid "+prodid);
			
			
			PurchaseRecord po=new PurchaseRecord();
			po.setPurchase(entitymanager.find(Purchase.class, id));
			po.setProduct(entitymanager.find(Product.class, prodid));
			po.setRawMaterial(entitymanager.find(RawMaterial.class,rawid ));
			po.setQuantity(p.getPurQuantity());
			po.setUnitPrice(p.getPurprice());
			po.setStatus("ordered");
			po.setStatus2("ordered");
			entitymanager.persist(po);
			System.out.println("-->> purchase inserted");

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}

	ArrayList<PurchaseOrederDataBean> ViewList=null; 
	
	/*kasthuri @@ purchase order view*/
	@Transactional(value="transactionManager")
	public String purchaseView(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
			ViewList=new ArrayList<PurchaseOrederDataBean>();
			Query q2=null,q1=null;
			if(p.getSearchType().equalsIgnoreCase("viewByDate"))
			{
				q1=entitymanager.createQuery("from Purchase where purchaseOrderDate between ? and ? and (status='ordered')");
				q1.setParameter(1, p.getPurFromDate());
				q1.setParameter(2, p.getPurToDate());
				ArrayList<Purchase> list1=(ArrayList<Purchase>)q1.getResultList();
				if(list1.size()>0)
				{
					for(int i=0;i<list1.size();i++)
					{
						PurchaseOrederDataBean po=new PurchaseOrederDataBean();
						po.setPurdate(list1.get(i).getPurchaseOrderDate());
						po.setPurOrderNo(list1.get(i).getPurchaseOrderNumber());
						po.setPurVendor(list1.get(i).getVendor().getFirmName());
						po.setPurTotalPrice(list1.get(i).getTotalAmount());
						po.setDeliveryStatus(list1.get(i).getDeliveryStatus());
						po.setPaymentStatus(list1.get(i).getPaymentStatus());
						po.setPurCategory(list1.get(i).getCategory());
						ViewList.add(po);
						p.setPurchaseViewList(ViewList);
						
					}
				}
				else
				{
					p.setPurchaseViewList(ViewList);
				}
			}
			else if(p.getSearchType().equalsIgnoreCase("viewByCategory"))
			{
				q1=entitymanager.createQuery("from Purchase where category=? and (status='ordered')");
				q1.setParameter(1, p.getPurCategory());
				ArrayList<Purchase> list1=(ArrayList<Purchase>)q1.getResultList();
				if(list1.size()>0)
				{
					for(int i=0;i<list1.size();i++)
					{
						PurchaseOrederDataBean po=new PurchaseOrederDataBean();
						po.setPurdate(list1.get(i).getPurchaseOrderDate());
						po.setPurOrderNo(list1.get(i).getPurchaseOrderNumber());
						po.setPurVendor(list1.get(i).getVendor().getFirmName());
						po.setPurTotalPrice(list1.get(i).getTotalAmount());
						po.setDeliveryStatus(list1.get(i).getDeliveryStatus());
						po.setPaymentStatus(list1.get(i).getPaymentStatus());
						po.setPurCategory(list1.get(i).getCategory());
						ViewList.add(po);
						p.setPurchaseViewList(ViewList);
						
					}
				}
				else
				{
					p.setPurchaseViewList(ViewList);
				}
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri @@ purchase order DetailedView*/
	@Transactional(value="transactionManager")
	public String purchaseOrderDetailedView(PurchaseOrederDataBean p) throws LiusenException
	{
		BigDecimal total=BigDecimal.valueOf(0),total1=BigDecimal.valueOf(0),total2=BigDecimal.valueOf(0);
		try
		{
			ViewList=new ArrayList<PurchaseOrederDataBean>();
			Query q2=null,q1=null,v=null;
			q1=entitymanager.createQuery("from Purchase where purchaseOrderNumber=? and (status='ordered')");
			q1.setParameter(1, p.getPurOrderNo());
			ArrayList<Purchase> list1=(ArrayList<Purchase>)q1.getResultList();
			if(list1.size()>0)
			{
				int pid=0;
				pid=list1.get(0).getPurchase_ID();
				p.setPurdate(list1.get(0).getPurchaseOrderDate());
				p.setPurEstDate(list1.get(0).getPurchaseEstimatedDate());
				p.setPurDiscType(list1.get(0).getDiscountType());
				p.setPurDiscAmmount(list1.get(0).getDiscountAmount());
				p.setPurVendor(list1.get(0).getVendor().getFirmName());
				p.setPurStaffId(list1.get(0).getEmployee().getEmployee_details_ID());
				p.setPurCategory(list1.get(0).getCategory());
				p.setPurCrossTotal(list1.get(0).getCrossTotal());
				p.setPurTotalPrice(list1.get(0).getTotalAmount());
				p.setTaxType(list1.get(0).getTypeofProject());
				q2=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
				q2.setParameter(1, pid);
				ArrayList<PurchaseRecord> list2=(ArrayList<PurchaseRecord>)q2.getResultList();
				if(list2.size()>0)
				{	
					int count=0;
					for(int i=0;i<list2.size();i++)
					{
						count=count+1;
						BigDecimal b=BigDecimal.valueOf(0);
						PurchaseOrederDataBean po=new PurchaseOrederDataBean();
						v=null;
						v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
						v.setParameter(1, list2.get(i).getPurchase_record_ID());
						List<PurchaseReturn> rer=(List<PurchaseReturn>) v.getResultList();
						if(rer.size()>0)
						{
							po.setReturnqauntity(""+(new BigDecimal(rer.get(0).getNormalReturn()).add(new BigDecimal(rer.get(0).getDamageReturn()))));
							po.setRetamount(""+(new BigDecimal(rer.get(0).getPurchaseRecord().getUnitPrice()).multiply(new BigDecimal(rer.get(0).getNormalReturn()).add(new BigDecimal(rer.get(0).getDamageReturn())))));
							total1=new BigDecimal(rer.get(0).getPurchaseRecord().getUnitPrice()).multiply(new BigDecimal(rer.get(0).getNormalReturn()).add(new BigDecimal(rer.get(0).getDamageReturn())));
						}
						else
						{
							po.setReturnqauntity("0");
							po.setRetamount("0");
							total1=BigDecimal.valueOf(0);
						}						
						if(p.getPurCategory().equalsIgnoreCase("Raw Material"))
						{
							po.setPurProductName(list2.get(i).getRawMaterial().getProductName());
							po.setUnit(list2.get(i).getRawMaterial().getUnit());
						} 
						else
						{
							po.setPurProductName(list2.get(i).getProduct().getProductName());
							po.setUnit(list2.get(i).getProduct().getUnit());
						}
						po.setPurQuantity(list2.get(i).getQuantity());
						po.setUnitPrice(list2.get(i).getUnitPrice());
						po.setPurserialno(""+count);
						b=new BigDecimal(list2.get(i).getQuantity()).multiply(new BigDecimal(list2.get(i).getUnitPrice()));
						po.setPurNetAmount(""+b);
						ViewList.add(po);
						p.setPurchaseList(ViewList);
						p.setStatus("success");
					}
					total=new BigDecimal(list1.get(0).getCrossTotal()).subtract(total1);
					System.out.println("amm 0 - > "+total+"ret -- > "+total1);
					BigDecimal ttt=BigDecimal.valueOf(0),ttt1=BigDecimal.valueOf(0);
					if(list1.get(0).getTypeofProject().equals("No Tax") ||  list1.get(0).getTypeofProject().equals(""))
					{
						ttt1=total;
					}
					else if(list1.get(0).getTypeofProject().equals("10% Tax"))
					{
						ttt1=total.add(total.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
					}
					System.out.println("amm 1 - > "+ttt1);
					if(list1.get(0).getDiscountType().equals(""))
					{
						ttt=ttt1;
					}
					else if(list1.get(0).getDiscountType().equals("IDR"))
					{
						ttt=ttt1.subtract(new BigDecimal(list1.get(0).getDiscountAmount()));
					}
					else if(list1.get(0).getDiscountType().equals("%"))
					{
						ttt=ttt1.subtract(ttt1.multiply(new BigDecimal(list1.get(0).getDiscountAmount())).divide(BigDecimal.valueOf(100)));
					}
					System.out.println("amm 2- > "+ttt);
					total2=ttt;
					p.setPurCrossTotal(""+total2.setScale(0,BigDecimal.ROUND_HALF_UP));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri @@ retrieve purchase order approval details*/
	@Transactional(value="transactionManager")
	public String purchaseEditDeleteCheck(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
			Query q2=null,q1=null;
			q1=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			q1.setParameter(1, p.getPurOrderNo());
			ArrayList<Purchase> list1=(ArrayList<Purchase>)q1.getResultList();
			if(list1.size()>0)
			{
				String s1="";
				String s2="";
				s1=list1.get(0).getStatus();
				s2=list1.get(0).getPurchaseApprovalStatus();
				p.setPoStatus(s1);
				p.setPoApprovalStatus(s2);
			}
			else
			{
				System.out.println("in else");
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri @@ purchase order delete*/
	@Transactional(value="transactionManager")
	public String purchaseDelete(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
			Query q1=null;
			q1=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			q1.setParameter(1, p.getPurOrderNo());
			ArrayList<Purchase> list1=(ArrayList<Purchase>)q1.getResultList();
			if(list1.size()>0)
			{
				int id=list1.get(0).getPurchase_ID();
				Purchase pp=entitymanager.find(Purchase.class, id);
				pp.setStatus("cancelled");
				entitymanager.merge(pp);
				System.out.println("po deleted");
				p.setStatus("deleted");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri @@ purchase order edit*/
	@Transactional(value="transactionManager")
	public String purchaseEditConfirm(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
			System.out.println("-->> dao-->> "+p.getProductId());
			Query q2=null,q1=null,q3=null;
			BigDecimal b1=BigDecimal.valueOf(0);
			BigDecimal b2=BigDecimal.valueOf(0);
			BigDecimal b3=BigDecimal.valueOf(0);
			BigDecimal b4=BigDecimal.valueOf(0);
			String discType="",discAmount="",taxType;
			q1=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			q1.setParameter(1, p.getPurOrderNo());
			ArrayList<Purchase> list1=(ArrayList<Purchase>)q1.getResultList();
			if(list1.size()>0)
			{
				int pid=0;
				pid=list1.get(0).getPurchase_ID();
				discType=list1.get(0).getDiscountType();
				discAmount=list1.get(0).getDiscountAmount();
				taxType=list1.get(0).getTypeofProject();
				if(p.getPurCategory().equalsIgnoreCase("Raw Material"))
				{
					q2=entitymanager.createQuery("from PurchaseRecord where purchase_ID=? and raw_ID=? and quantity=?");
					q2.setParameter(1, pid);
					q2.setParameter(2, p.getProductId());
					q2.setParameter(3, p.getPurQuantity());
					ArrayList<PurchaseRecord> list2=(ArrayList<PurchaseRecord>)q2.getResultList();
					if(list2.size()>0)
					{	
						System.out.println("-->> if 2");
						int rid=0;
						rid=list2.get(0).getPurchase_record_ID();
						PurchaseRecord r=entitymanager.find(PurchaseRecord.class, rid);
						r.setQuantity(p.getPurQuantity1());
						r.setUnitPrice(p.getPurprice());
						entitymanager.merge(r);		
					}
					else
					{
						System.out.println("-->> else 2");
					}
				}
				else
				{
					q2=entitymanager.createQuery("from PurchaseRecord where purchase_ID=? and product_ID=? and quantity=?");
					q2.setParameter(1, pid);
					q2.setParameter(2, p.getProductId());
					q2.setParameter(3, p.getPurQuantity());
					ArrayList<PurchaseRecord> list2=(ArrayList<PurchaseRecord>)q2.getResultList();
					if(list2.size()>0)
					{	
						System.out.println("-->> if 2");
						int rid=0;
						rid=list2.get(0).getPurchase_record_ID();
						PurchaseRecord r=entitymanager.find(PurchaseRecord.class, rid);
						r.setQuantity(p.getPurQuantity1());
						r.setUnitPrice(p.getPurprice());
						entitymanager.merge(r);		
					}
					else
					{
						System.out.println("-->> else 2");
					}
				}
				
				q3=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
				q3.setParameter(1, pid);
				ArrayList<PurchaseRecord> list3=(ArrayList<PurchaseRecord>)q3.getResultList();
				if(list3.size()>0)
				{	
					
					for(int i=0;i<list3.size();i++)
					{
						b1=b1.add(new BigDecimal(list3.get(i).getQuantity()).multiply(new BigDecimal(list3.get(i).getUnitPrice())));
					}
					
				}
				if(discType.equalsIgnoreCase("IDR"))	
			    {
			    	b2=(new BigDecimal(discAmount));
			    }
			    else if(discType.equalsIgnoreCase("%"))
			    {
			    	
			    	b2=(b1.multiply(new BigDecimal(discAmount))).divide(BigDecimal.valueOf(100));
			    }
				if(taxType.equalsIgnoreCase("No Tax"))
				{
					b3=BigDecimal.valueOf(0);
				}
				else if(taxType.equalsIgnoreCase("10% Tax"))
				{
					b3=b1.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100));
				}
				b4=b1.add(b3).subtract(b2);
				System.out.println("-->> gross tot "+b1);
				System.out.println("-->> final tot "+b4);
				
				Date d= new Date();
				String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
				Purchase pp=entitymanager.find(Purchase.class, pid);
				pp.setCrossTotal(""+b1);
				pp.setTotalAmount(""+b4.setScale(0,BigDecimal.ROUND_HALF_UP));
				pp.setEditDate(d);
				pp.setEditLoginStatus(login);
				pp.setEditStatus("edited");
				entitymanager.merge(pp);
				
			}
			else
			{
				System.out.println("-->> else ");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri @@ purchase order approval count*/
	@Transactional(value="transactionManager")
	public String purchaseApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
			Query q2=null,q1=null,q3=null,q4=null;
			int pid=0,c1=0,c2=0,c3=0,c4=0;
			q1=entitymanager.createQuery("from Purchase where status='ordered'");
			List<Purchase> list1=(List<Purchase>)q1.getResultList();
			if(list1.size()>0)
			{
				for(int i=0;i<list1.size();i++)
				{
					pid=list1.get(i).getPurchase_ID();
					q2=entitymanager.createQuery("from PurchaseApproval where  purchase_ID=? and purchaseApprovalStatus='pending' ");
					q2.setParameter(1, pid);
					List<PurchaseApproval> list2=(List<PurchaseApproval>)q2.getResultList();
					if(list2.size()>0)
					{
						if(list2.get(0).getPurchase_approval_GMstatus().equalsIgnoreCase("pending"))
						{
							c1=c1+1;
							p.setCount1(""+c1);
							
						}
						if(list2.get(0).getPurchase_approval_MMstatus().equalsIgnoreCase("pending"))
						{
							c2=c2+1;
							p.setCount2(""+c2);
							
						}
					}
					
					q3=entitymanager.createQuery("from PurchaseApproval where  purchase_ID=? and purchaseDeliveryStatus='pending' and purchaseApprovalStatus='Approved' ");
					q3.setParameter(1, pid);
					List<PurchaseApproval> list3=(List<PurchaseApproval>)q3.getResultList();
					if(list3.size()>0)
					{
						if(list3.get(0).getPurchase_delivery_GMstatus().equalsIgnoreCase("pending"))
						{
							c3=c3+1;
							p.setCount3(""+c3);
							
						}
						if(list3.get(0).getPurchase_delivery_PMstatus().equalsIgnoreCase("pending"))
						{
							c4=c4+1;
							p.setCount4(""+c4);
							
						}
					}
					
				}
				if(c1==0)
				{
					p.setCount1("0");
				}
				if(c2==0)
				{
					p.setCount2("0");
				}
				if(c3==0)
				{
					p.setCount3("0");
				}
				if(c4==0)
				{
					p.setCount4("0");
				}
				System.out.println("-->> counts c1/c2/c3/c4 "+c1+"/"+c2+"/"+c3+"/"+c4);
			}
			else
			{
				p.setCount1("0");
				p.setCount3("0");
				p.setCount2("0");
				p.setCount4("0");
			}
		}
		catch(NullPointerException n)
		{
			System.out.println("-->> NullPointerException ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	
	/*kasthuri @@ purchase order for approval*/
	@Transactional(value="transactionManager")
	public String poDetailsForApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
			ViewList=new ArrayList<PurchaseOrederDataBean>();
			Query q2=null,q1=null,q3=null,q4=null;
			int pid=0,c1=0,c2=0,c3=0,c4=0,s=0,count=0;
			q1=entitymanager.createQuery("from Purchase where status='ordered'");
			List<Purchase> list1=(List<Purchase>)q1.getResultList();
			if(list1.size()>0)
			{
				for(int i=0;i<list1.size();i++)
				{
					s=s+1;
					pid=list1.get(i).getPurchase_ID();
					q2=entitymanager.createQuery("from PurchaseApproval where  purchase_ID=? and purchaseApprovalStatus='pending' ");
					q2.setParameter(1, pid);
					List<PurchaseApproval> list2=(List<PurchaseApproval>)q2.getResultList();
					if(list2.size()>0)
					{
						if(p.getStatus().equalsIgnoreCase("GM"))
						{
						if(list2.get(0).getPurchase_approval_GMstatus().equalsIgnoreCase("pending"))
						{
							System.out.println("-->> in if GM");	
							count=count+1;
							PurchaseOrederDataBean bean=new PurchaseOrederDataBean();
							bean.setPurOrderNo(list2.get(0).getPurchase().getPurchaseOrderNumber());
							bean.setPurdate(list2.get(0).getPurchase().getPurchaseOrderDate());
							bean.setPurCrossTotal(list2.get(0).getPurchase().getCrossTotal());
							bean.setPurserialno(""+count);
							ViewList.add(bean);
							p.setPurchaseViewList(ViewList);
							System.out.println("-->> dao list size "+ViewList.size());
							
						}
						}
						if(p.getStatus().equalsIgnoreCase("MM"))
						{
						if(list2.get(0).getPurchase_approval_MMstatus().equalsIgnoreCase("pending"))
						{
							System.out.println("-->> in if MM");
							count=count+1;
							PurchaseOrederDataBean bean=new PurchaseOrederDataBean();
							bean.setPurOrderNo(list2.get(0).getPurchase().getPurchaseOrderNumber());
							bean.setPurdate(list2.get(0).getPurchase().getPurchaseOrderDate());
							bean.setPurCrossTotal(list2.get(0).getPurchase().getCrossTotal());
							bean.setPurserialno(""+count);
							ViewList.add(bean);
							p.setPurchaseViewList(ViewList);
							System.out.println("-->> dao list size "+ViewList.size());
							
						}
						}
					}
					
					
				}
				
			}
			else
			{
				
			}
		}
		catch(NullPointerException n)
		{
			System.out.println("-->> NullPointerException ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri @@ po GM approval insertion*/
	@Transactional(value="transactionManager")
	public String poGMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
			Date d= new Date();
			Timestamp t=new Timestamp(d.getTime());
			String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
			System.out.println("-->> dao "+p.getPurOrderNo());
			System.out.println("-->> date "+d);
			System.out.println("-->> login "+login);
			Query q2=null,q1=null;
			int pid=0,aid=0;
			q1=entitymanager.createQuery("from Purchase where purchaseOrderNumber=? and (status='ordered')");
			q1.setParameter(1, p.getPurOrderNo());
			ArrayList<Purchase> list1=(ArrayList<Purchase>)q1.getResultList();
			if(list1.size()>0)
			{
				pid=list1.get(0).getPurchase_ID();
				System.out.println("-->> pid "+pid);
				
			}
			q2=entitymanager.createQuery("from PurchaseApproval where purchase_ID=?");
			q2.setParameter(1, pid);
			ArrayList<PurchaseApproval> list2=(ArrayList<PurchaseApproval>)q2.getResultList();
			if(list1.size()>0)
			{
				aid=list2.get(0).getPurchase_approval_ID();
				System.out.println("-->> aid "+aid);
				
			}
			PurchaseApproval app=entitymanager.find(PurchaseApproval.class, aid);
			app.setPurchase_approval_dateGM(d);
			app.setPurchaseApprovalTime(t);
			app.setPurchase_approval_GMlogin(login);
			app.setPurchase_approval_GMstatus(p.getAppType());
			app.setPurchase_approval_rejectStatus(p.getReason());
			app.setPurchaseApprovalDate(d);
			if(p.getAppType().equalsIgnoreCase("Reject"))
			{
				app.setPurchaseApprovalStatus("Rejected");
				app.setStatus("Rejected");
			}
			if(p.getAppType().equalsIgnoreCase("Approve") && list2.get(0).getPurchase_approval_MMstatus().equalsIgnoreCase("Approve"))
			{
				app.setPurchaseApprovalStatus("Approved");
				app.setStatus("Approved");
				app.setStatus2("Approved");
			}
			entitymanager.merge(app);
			Purchase pp=entitymanager.find(Purchase.class, pid);
			pp.setPurchaseApprovalStatus(p.getAppType());
			pp.setDeliveryStatus(p.getAppType());
			entitymanager.merge(pp);
		}
		catch(NullPointerException n)
		{
			System.out.println("-->> NullPointerException ");
			n.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri @@ po MM approval insertion*/
	@Transactional(value="transactionManager")
	public String poMMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
				Date d= new Date();
				Timestamp t=new Timestamp(d.getTime());
				String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
				System.out.println("-->> dao "+p.getPurOrderNo());
				System.out.println("-->> date "+d);
				System.out.println("-->> login "+login);
				Query q2=null,q1=null;
				int pid=0,aid=0;
				q1=entitymanager.createQuery("from Purchase where purchaseOrderNumber=? and (status='ordered')");
				q1.setParameter(1, p.getPurOrderNo());
				ArrayList<Purchase> list1=(ArrayList<Purchase>)q1.getResultList();
				if(list1.size()>0)
				{
					pid=list1.get(0).getPurchase_ID();
					System.out.println("-->> pid "+pid);
					
				}
				q2=entitymanager.createQuery("from PurchaseApproval where purchase_ID=?");
				q2.setParameter(1, pid);
				ArrayList<PurchaseApproval> list2=(ArrayList<PurchaseApproval>)q2.getResultList();
				if(list1.size()>0)
				{
					aid=list2.get(0).getPurchase_approval_ID();
					System.out.println("-->> aid "+aid);
					
				}
				PurchaseApproval app=entitymanager.find(PurchaseApproval.class, aid);
				app.setPurchase_approval_dateMM(d);
				app.setPurchaseApprovalTime(t);
				app.setPurchase_approval_MMlogin(login);
				app.setPurchase_approval_MMstatus(p.getAppType());
				app.setPurchase_approval_rejectStatus(p.getReason());
				app.setPurchaseApprovalDate(d);
				if(p.getAppType().equalsIgnoreCase("Reject"))
				{
					app.setPurchaseApprovalStatus("Rejected");
					app.setStatus("Rejected");
				}
				
				if(p.getAppType().equalsIgnoreCase("Approve") && list2.get(0).getPurchase_approval_GMstatus().equalsIgnoreCase("Approve"))
				{
					app.setPurchaseApprovalStatus("Approved");
					app.setStatus("Approved");
					app.setStatus2("Approved");
				}
			
				entitymanager.merge(app);
				Purchase pp=entitymanager.find(Purchase.class, pid);
				pp.setPurchaseApprovalStatus(p.getAppType());
				pp.setDeliveryStatus(p.getAppType());
				entitymanager.merge(pp);
			
		}
		catch(NullPointerException n)
		{
			System.out.println("-->> NullPointerException ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}

	
	/*kasthuri @@ purchase order for approval*/
	@Transactional(value="transactionManager")
	public String poDetailsForDeliveryApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{

			ViewList=new ArrayList<PurchaseOrederDataBean>();
			Query q2=null,q1=null,q3=null,q4=null;
			int pid=0,count=0;
			q1=entitymanager.createQuery("from Purchase where status='ordered'");
			List<Purchase> list1=(List<Purchase>)q1.getResultList();
			if(list1.size()>0)
			{
				for(int i=0;i<list1.size();i++)
				{
					pid=list1.get(i).getPurchase_ID();
					q2=entitymanager.createQuery("from PurchaseApproval where  purchase_ID=? and purchaseApprovalStatus='Approved' and purchaseDeliveryStatus='pending'");
					q2.setParameter(1, pid);
					List<PurchaseApproval> list2=(List<PurchaseApproval>)q2.getResultList();
					if(list2.size()>0)
					{
						if(p.getStatus().equalsIgnoreCase("GM"))
						{
						if(list2.get(0).getPurchase_delivery_GMstatus().equalsIgnoreCase("pending"))
						{
							System.out.println("-->> in if GM");	
							count=count+1;
							PurchaseOrederDataBean bean=new PurchaseOrederDataBean();
							bean.setPurOrderNo(list2.get(0).getPurchase().getPurchaseOrderNumber());
							bean.setPurdate(list2.get(0).getPurchase().getPurchaseOrderDate());
							bean.setPurCrossTotal(list2.get(0).getPurchase().getCrossTotal());
							bean.setPurserialno(""+count);
							ViewList.add(bean);
							p.setPurchaseViewList(ViewList);
							System.out.println("-->> dao list size "+ViewList.size());
							
						}
						}
						if(p.getStatus().equalsIgnoreCase("PM"))
						{
						if(list2.get(0).getPurchase_delivery_PMstatus().equalsIgnoreCase("pending"))
						{
							System.out.println("-->> in if PM");
							count=count+1;
							PurchaseOrederDataBean bean=new PurchaseOrederDataBean();
							bean.setPurOrderNo(list2.get(0).getPurchase().getPurchaseOrderNumber());
							bean.setPurdate(list2.get(0).getPurchase().getPurchaseOrderDate());
							bean.setPurCrossTotal(list2.get(0).getPurchase().getCrossTotal());
							bean.setPurserialno(""+count);
							ViewList.add(bean);
							p.setPurchaseViewList(ViewList);
							System.out.println("-->> dao list size "+ViewList.size());
							
						}
						}
					}
					
					
				}
				
			}
			else
			{
				
			}
		
			
		}
		catch(NullPointerException n)
		{
			System.out.println("-->> NullPointerException ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri @@ po delivery GM approval insertion*/
	@Transactional(value="transactionManager")
	public String poDelGMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
				Date d= new Date();
				Timestamp t=new Timestamp(d.getTime());
				String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
				System.out.println("-->> dao "+p.getPurOrderNo());
				System.out.println("-->> date "+d);
				System.out.println("-->> login "+login);
				Query q2=null,q1=null;
				int pid=0,aid=0;
				q1=entitymanager.createQuery("from Purchase where purchaseOrderNumber=? and (status='ordered')");
				q1.setParameter(1, p.getPurOrderNo());
				ArrayList<Purchase> list1=(ArrayList<Purchase>)q1.getResultList();
				if(list1.size()>0)
				{
					pid=list1.get(0).getPurchase_ID();
					System.out.println("-->> pid "+pid);
					
				}
				q2=entitymanager.createQuery("from PurchaseApproval where purchase_ID=?");
				q2.setParameter(1, pid);
				ArrayList<PurchaseApproval> list2=(ArrayList<PurchaseApproval>)q2.getResultList();
				if(list1.size()>0)
				{
					aid=list2.get(0).getPurchase_approval_ID();
					System.out.println("-->> aid "+aid);
					
				}
				PurchaseApproval app=entitymanager.find(PurchaseApproval.class, aid);
				app.setPurchase_delivery_GMlogin(login);
				app.setPurchase_delivery_GMstatus(p.getAppType());
				app.setPurchaseDeliveryRejectstatus(p.getReason());
				app.setPurchase_delivery_dateGM(d);
				app.setPurchaseDeliveryTime(t);
				app.setPurchaseDeliveryDate(d);
				if(p.getAppType().equalsIgnoreCase("Reject"))
				{
					app.setPurchaseDeliveryStatus("Rejected");
					app.setStatus2("Rejected");
				}
				if(p.getAppType().equalsIgnoreCase("Approve") && list2.get(0).getPurchase_delivery_PMstatus().equalsIgnoreCase("Approve"))
				{
					app.setPurchaseDeliveryStatus("Approved");
					app.setStatus2("Approved");
				}
				entitymanager.merge(app);
				Purchase pp=entitymanager.find(Purchase.class, pid);
				pp.setDeliveryStatus(p.getAppType());
				entitymanager.merge(pp);
			
		}
		catch(NullPointerException n)
		{
			System.out.println("-->> NullPointerException ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	/*kasthuri @@ po delivery PM approval insertion*/
	@Transactional(value="transactionManager")
	public String poDelPMApproval(PurchaseOrederDataBean p) throws LiusenException
	{
		try
		{
				Date d= new Date();
				Timestamp t=new Timestamp(d.getTime());
				String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");
				System.out.println("-->> dao "+p.getPurOrderNo());
				System.out.println("-->> date "+d);
				System.out.println("-->> login "+login);
				Query q2=null,q1=null;
				int pid=0,aid=0;
				q1=entitymanager.createQuery("from Purchase where purchaseOrderNumber=? and (status='ordered')");
				q1.setParameter(1, p.getPurOrderNo());
				ArrayList<Purchase> list1=(ArrayList<Purchase>)q1.getResultList();
				if(list1.size()>0)
				{
					pid=list1.get(0).getPurchase_ID();
					System.out.println("-->> pid "+pid);
					
				}
				q2=entitymanager.createQuery("from PurchaseApproval where purchase_ID=?");
				q2.setParameter(1, pid);
				ArrayList<PurchaseApproval> list2=(ArrayList<PurchaseApproval>)q2.getResultList();
				if(list1.size()>0)
				{
					aid=list2.get(0).getPurchase_approval_ID();
					System.out.println("-->> aid "+aid);
					
				}
				PurchaseApproval app=entitymanager.find(PurchaseApproval.class, aid);
				app.setPurchase_delivery_PMlogin(login);
				app.setPurchase_delivery_PMstatus(p.getAppType());
				app.setPurchaseDeliveryRejectstatus(p.getReason());
				app.setPurchase_delivery_datePM(d);
				app.setPurchaseDeliveryDate(d);
				app.setPurchaseDeliveryTime(t);
				if(p.getAppType().equalsIgnoreCase("Reject"))
				{
					app.setPurchaseDeliveryStatus("Rejected");
					app.setStatus2("Rejected");
					
				}
				if(p.getAppType().equalsIgnoreCase("Approve") && list2.get(0).getPurchase_delivery_GMstatus().equalsIgnoreCase("Approve"))
				{
					app.setPurchaseDeliveryStatus("Approved");
					app.setStatus2("Approved");
					
				}
				entitymanager.merge(app);
				Purchase pp=entitymanager.find(Purchase.class, pid);
				pp.setPurchaseApprovalStatus(p.getAppType());
				entitymanager.merge(pp);
			
		}
		catch(NullPointerException n)
		{
			System.out.println("-->> NullPointerException ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
		
	public List<String> getinvoicePurchase()
	{
		List<String> invoicelist=new ArrayList<String>();
		Query v=null;
		v=entitymanager.createQuery("from Purchase where deliveryStatus='Approve' and status2='Stocked In' and status='ordered'");
		List<Purchase> purchase=(ArrayList<Purchase>)v.getResultList();		
		System.out.println("purchase size - > "+purchase.size());			
		if(purchase.size()==0)
		{
			System.out.println("purchase empty - -");
		}		
		else
		{			
			for (Purchase purchases : purchase)
			{
				String purchaselist2 =new String();
				System.out.println("order number------>"+purchases.getPurchaseOrderNumber());
				purchaselist2=purchases.getPurchaseOrderNumber();				
				invoicelist.add(purchaselist2);
			}
		}
		return invoicelist;
	}
	
	public String invoiceView(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		BigDecimal nrq=BigDecimal.valueOf(0),totretamnt=BigDecimal.valueOf(0),totamnt=BigDecimal.valueOf(0);
		BigDecimal amnt=BigDecimal.valueOf(0),amnt1=BigDecimal.valueOf(0);		
		ArrayList<PurchaseOrederDataBean> litss=new ArrayList<PurchaseOrederDataBean>();
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			v.setParameter(1, purchaseOrederDataBean.getPurOrderNo());
			List<Purchase> pur=(List<Purchase>)v.getResultList();
			int purid=0;
			if(pur.size()>0)
			{
				purid=pur.get(0).getPurchase_ID();
				System.out.println("purchase id -- > "+purid);	
				v=null;
				v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
				v.setParameter(1, purid);
				List<PurchaseRecord> salrec=(List<PurchaseRecord>)v.getResultList();
				if(salrec.size()>0)
				{
					int c=0;
					for (int i = 0; i < salrec.size(); i++) 
					{
						v=null;
						v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
						v.setParameter(1, salrec.get(i).getPurchase_record_ID());
						List<PurchaseReturn> salret=(List<PurchaseReturn>)v.getResultList();
						if(salret.size()>0)
						{
							nrq=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));
							if(nrq.compareTo(new BigDecimal(salret.get(0).getPurchaseRecord().getQuantity()))==0)
							{
								c++;
							}
							totretamnt=(nrq.multiply(new BigDecimal(salret.get(0).getPurchaseRecord().getUnitPrice()))).add(totretamnt);
							System.out.println("total return  amount -- > "+totretamnt);
						}
						totamnt=(new BigDecimal(salrec.get(i).getQuantity()).multiply(new BigDecimal(salrec.get(i).getUnitPrice()))).add(totamnt);
						System.out.println("total amount -- > "+totamnt);
					}
					amnt=totamnt.subtract(totretamnt);
					System.out.println("amm - > "+amnt);
					BigDecimal ttt=BigDecimal.valueOf(0),ttt1=BigDecimal.valueOf(0);
					if(pur.get(0).getTypeofProject().equals("No Tax") ||  pur.get(0).getTypeofProject().equals(""))
					{
						ttt1=amnt;
					}
					else if(pur.get(0).getTypeofProject().equals("10% Tax"))
					{
						ttt1=amnt.add(amnt.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
					}
					System.out.println("amm 1 - > "+ttt1);
					if(pur.get(0).getDiscountType().equals(""))
					{
						ttt=ttt1;
					}
					else if(pur.get(0).getDiscountType().equals("IDR"))
					{
						ttt=ttt1.subtract(new BigDecimal(pur.get(0).getDiscountAmount()));
					}
					else if(pur.get(0).getDiscountType().equals("%"))
					{
						ttt=ttt1.subtract(ttt1.multiply(new BigDecimal(pur.get(0).getDiscountAmount())).divide(BigDecimal.valueOf(100)));
					}
					System.out.println("amm 2- > "+ttt);
					amnt1=ttt;
					purchaseOrederDataBean.setRetamount(""+amnt1.setScale(0,BigDecimal.ROUND_HALF_UP));
				}
			}
			System.out.println("return amount -- > "+amnt1);
			PurchaseOrederDataBean  list=new PurchaseOrederDataBean();
			list.setPurdate(pur.get(0).getPurchaseOrderDate());
			list.setPurOrderNo(pur.get(0).getPurchaseOrderNumber());
			list.setPurVendor(pur.get(0).getVendor().getFirmName());
			list.setPurTotalPrice(""+amnt1.setScale(0,BigDecimal.ROUND_HALF_UP));
			list.setPurStaffName(pur.get(0).getEmployee().getEmployeeName());
			litss.add(list);
			purchaseOrederDataBean.setPurchaseList(litss);				
			System.out.println("size - >"+purchaseOrederDataBean.getPurchaseList().size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String invoiceViewForm(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		ArrayList<PurchaseOrederDataBean> litss=new ArrayList<PurchaseOrederDataBean>();
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			v.setParameter(1, purchaseOrederDataBean.getPurOrderNo());
			List<Purchase> pur=(List<Purchase>)v.getResultList();
			int purid=0;
			if(pur.size()>0)
			{
				purid=pur.get(0).getPurchase_ID();
				System.out.println("purchase id -- > "+purid);
				v=null;
				v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
				v.setParameter(1, purid);
				List<PurchaseRecord> purec=(List<PurchaseRecord>) v.getResultList();
				purchaseOrederDataBean.setPurCrossTotal(purchaseOrederDataBean.getRetamount());
				if(purec.size()>0)
				{
					int c=1;
					for (int i = 0; i < purec.size(); i++)
					{
						PurchaseOrederDataBean  list=new PurchaseOrederDataBean();
						list.setPurserialno(""+c);
						try
						{
							if(purec.get(i).getProduct().equals(""))
							{
								list.setPurProductName(purec.get(i).getRawMaterial().getProductName());
							}
						}
						catch(Exception e)
						{							
							list.setPurProductName(purec.get(i).getRawMaterial().getProductName());							
						}
						try
						{
							if(purec.get(i).getRawMaterial().equals(""))
							{
								list.setPurProductName(purec.get(i).getProduct().getProductName());
							}
						}
						catch(Exception e)
						{
							list.setPurProductName(purec.get(i).getProduct().getProductName());
						}
						v=null;
						v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
						v.setParameter(1, purec.get(i).getPurchase_record_ID());
						List<PurchaseReturn> rer=(List<PurchaseReturn>) v.getResultList();
						if(rer.size()>0)
						{
							list.setReturnqauntity(""+(new BigDecimal(rer.get(0).getNormalReturn()).add(new BigDecimal(rer.get(0).getDamageReturn()))));
							list.setRetamount(""+(new BigDecimal(rer.get(0).getPurchaseRecord().getUnitPrice()).multiply(new BigDecimal(rer.get(0).getNormalReturn()).add(new BigDecimal(rer.get(0).getDamageReturn())))));
						}
						else
						{
							list.setReturnqauntity("0");
							list.setRetamount("0");
						}
						list.setPurQuantity(purec.get(i).getQuantity());
						list.setPurprice(purec.get(i).getUnitPrice());
						list.setPurNetAmount(""+(new BigDecimal(purec.get(i).getQuantity()).multiply(new BigDecimal(purec.get(i).getUnitPrice()))));
						litss.add(list);
						purchaseOrederDataBean.setPurchaseViewList(litss);
						c++;
					}
				}			
				purchaseOrederDataBean.setPurdate(pur.get(0).getPurchaseOrderDate());
				purchaseOrederDataBean.setPurOrderNo(pur.get(0).getPurchaseOrderNumber());
				purchaseOrederDataBean.setPurVendor(pur.get(0).getVendor().getFirmName());
				purchaseOrederDataBean.setPurTotalPrice(pur.get(0).getTotalAmount());
				purchaseOrederDataBean.setPurEstDate(pur.get(0).getPurchaseEstimatedDate());
				purchaseOrederDataBean.setPurStaffName(pur.get(0).getEmployee().getEmployeeName());
				purchaseOrederDataBean.setPurchaseid(purid);
				System.out.println("size - >"+purchaseOrederDataBean.getPurchaseList().size());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";	
	}
	
	@Transactional(value="transactionManager")
	public String invoiceinsertpur(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		try
		{			
			v=entitymanager.createQuery("from Invoice where purchase_ID=?");
			v.setParameter(1, purchaseOrederDataBean.getPurchaseid());					
			List<Invoice> invoice=(List<Invoice>) v.getResultList();
			int invoiceid=0;
			if(invoice.size()>0)
			{
				invoiceid=invoice.get(0).getInvoice_ID();
				System.out.println("invoice id - > "+invoiceid);
				Invoice update=entitymanager.find(Invoice.class,invoiceid);
				update.setInviceDate(date);
				update.setInvoiceStatus("Purchase Invoice");
				update.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				update.setLoginTime(time);
				update.setStatus("Invoice Generated");
				entitymanager.merge(update);
			}
			else
			{
				Invoice insert=new Invoice();
				insert.setInviceDate(date);
				insert.setInvoiceStatus("Purchase Invoice");
				insert.setPurchase(entitymanager.find(Purchase.class, purchaseOrederDataBean.getPurchaseid()));
				insert.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				insert.setLoginTime(time);
				insert.setStatus("Invoice Generated");
				entitymanager.persist(insert);
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public List<String> purchasepayorders()
	{
		System.out.println("purchase payment orders dao ");
		List<String> orders=new ArrayList<String>();
		Query v=null;
		v=entitymanager.createQuery("from Invoice where invoiceStatus='Purchase Invoice'");
		List<Invoice> inv=(List<Invoice>) v.getResultList();
		if(inv.size()>0)
		{
			System.out.println("purchase payment order size - > "+inv.size());
			for (int i = 0; i < inv.size(); i++)
			{
				v=null;
				v=entitymanager.createQuery("from Payment where invoice_ID=?");
				v.setParameter(1, inv.get(i).getInvoice_ID());
				List<Payment> pp=(List<Payment>) v.getResultList();
				if(pp.size()>0)
				{
					v=null;
					v=entitymanager.createQuery("from Payment where invoice_ID=? and paymentStatus='pending'");
					v.setParameter(1, inv.get(i).getInvoice_ID());
					List<Payment> pp1=(List<Payment>) v.getResultList();
					if(pp1.size()>0)
					{
						String order=pp1.get(0).getInvoice().getPurchase().getPurchaseOrderNumber();
						orders.add(order);
					}
				}
				else
				{
					String order=inv.get(i).getPurchase().getPurchaseOrderNumber();
					orders.add(order);
				}				
			}
		}
		return orders;
	}
	
	public String paymentViewpurchase(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		BigDecimal nrq=BigDecimal.valueOf(0),totretamnt=BigDecimal.valueOf(0),totamnt=BigDecimal.valueOf(0);
		BigDecimal amnt=BigDecimal.valueOf(0),amnt1=BigDecimal.valueOf(0);		
		ArrayList<PurchaseOrederDataBean> listt=new ArrayList<PurchaseOrederDataBean>();
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			v.setParameter(1, purchaseOrederDataBean.getPurOrderNo());
			System.out.println("order no - > "+purchaseOrederDataBean.getPurOrderNo());
			List<Purchase> purchase=(List<Purchase>) v.getResultList();
			if(purchase.size()>0)
			{
				v=null;
				v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
				v.setParameter(1, purchase.get(0).getPurchase_ID());
				List<PurchaseRecord> salrec=(List<PurchaseRecord>)v.getResultList();
				if(salrec.size()>0)
				{
					int c=0;
					for (int i = 0; i < salrec.size(); i++) 
					{
						v=null;
						v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
						v.setParameter(1, salrec.get(i).getPurchase_record_ID());
						List<PurchaseReturn> salret=(List<PurchaseReturn>)v.getResultList();
						if(salret.size()>0)
						{
							nrq=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));
							if(nrq.compareTo(new BigDecimal(salret.get(0).getPurchaseRecord().getQuantity()))==0)
							{
								c++;
							}
							totretamnt=(nrq.multiply(new BigDecimal(salret.get(0).getPurchaseRecord().getUnitPrice()))).add(totretamnt);
							System.out.println("total return  amount -- > "+totretamnt);
						}
						totamnt=(new BigDecimal(salrec.get(i).getQuantity()).multiply(new BigDecimal(salrec.get(i).getUnitPrice()))).add(totamnt);
						System.out.println("total amount -- > "+totamnt);
					}
					amnt=totamnt.subtract(totretamnt);
					System.out.println("amm - > "+amnt);
					BigDecimal ttt=BigDecimal.valueOf(0),ttt1=BigDecimal.valueOf(0);
					if(purchase.get(0).getTypeofProject().equals("No Tax") ||  purchase.get(0).getTypeofProject().equals(""))
					{
						ttt1=amnt;
					}
					else if(purchase.get(0).getTypeofProject().equals("10% Tax"))
					{
						ttt1=amnt.add(amnt.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
					}
					System.out.println("amm 1 - > "+ttt1);
					if(purchase.get(0).getDiscountType().equals(""))
					{
						ttt=ttt1;
					}
					else if(purchase.get(0).getDiscountType().equals("IDR"))
					{
						ttt=ttt1.subtract(new BigDecimal(purchase.get(0).getDiscountAmount()));
					}
					else if(purchase.get(0).getDiscountType().equals("%"))
					{
						ttt=ttt1.subtract(ttt1.multiply(new BigDecimal(purchase.get(0).getDiscountAmount())).divide(BigDecimal.valueOf(100)));
					}
					System.out.println("amm 2- > "+ttt);
					amnt1=ttt;
					purchaseOrederDataBean.setRetamount(""+amnt1.setScale(0,BigDecimal.ROUND_HALF_UP));
				}
			}
			System.out.println("return amount -- > "+amnt1);
			PurchaseOrederDataBean list=new PurchaseOrederDataBean();
			list.setPurdate(purchase.get(0).getPurchaseOrderDate());
			list.setPurOrderNo(purchase.get(0).getPurchaseOrderNumber());
			list.setPurVendor(purchase.get(0).getVendor().getFirmName());
			list.setPurTotalPrice(""+amnt1.setScale(0,BigDecimal.ROUND_HALF_UP));
			listt.add(list);
			purchaseOrederDataBean.setPurchaseList(listt);
			purchaseOrederDataBean.setPurchaseid(purchase.get(0).getPurchase_ID());
			System.out.println("purchase id - > "+purchaseOrederDataBean.getPurchaseid());		
		}
		catch(Exception e)
		{			
			e.printStackTrace();
		}
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String paymentinpur(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());		
		try
		{
			v=entitymanager.createQuery("from Invoice where purchase_ID=? and invoiceStatus='Purchase Invoice'");
			v.setParameter(1, purchaseOrederDataBean.getPurchaseid());
			List<Invoice> inv=(List<Invoice>) v.getResultList();
			int invid=0;
			if(inv.size()>0)
			{
				invid=inv.get(0).getInvoice_ID();
				System.out.println("invoice id - > "+invid);
				v=null;
				v=entitymanager.createQuery("from Payment where invoice_ID=?");
				v.setParameter(1, invid);
				List<Payment> payy=(List<Payment>) v.getResultList();
				int payid=0;
				if(payy.size()>0)
				{
					payid=payy.get(0).getPayment_ID();
					System.out.println("payment id - > "+payid);
					Payment payy1=entitymanager.find(Payment.class, payid);
					payy1.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
					payy1.setTotalAmount(purchaseOrederDataBean.getRetamount());						
					entitymanager.merge(payy1);
				}
				else
				{
					Payment paym=new Payment();
					paym.setInvoice(entitymanager.find(Invoice.class, invid));
					paym.setTotalAmount(purchaseOrederDataBean.getRetamount());
					paym.setPaidAmount("0");
					paym.setApprovalStatus("pending");
					paym.setPayDate(date);
					paym.setPayTime(time);
					paym.setPaymentStatus("pending");
					paym.setPaymentType("no");
					paym.setBankName("no");
					paym.setChequeDate(date);
					paym.setChequeNumber("no");
					paym.setAccountNumber("no");
					paym.setStatus("pending");
					paym.setStatus_Director("pending");
					paym.setStatus_FM("pending");
					paym.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
					entitymanager.persist(paym);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String payPurchase(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from Invoice where purchase_ID=? and invoiceStatus='Purchase Invoice'");
			v.setParameter(1, purchaseOrederDataBean.getPurchaseid());
			List<Invoice> inv=(List<Invoice>) v.getResultList();
			int invid=0;
			if(inv.size()>0)
			{
				invid=inv.get(0).getInvoice_ID();
				System.out.println("invoice id - > "+invid);
				v=null;
				v=entitymanager.createQuery("from Payment where invoice_ID=?");
				v.setParameter(1, invid);
				List<Payment> payy=(List<Payment>) v.getResultList();
				if(payy.size()>0)
				{
					purchaseOrederDataBean.setPurdate(payy.get(0).getInvoice().getPurchase().getPurchaseOrderDate());
					purchaseOrederDataBean.setPurOrderNo(payy.get(0).getInvoice().getPurchase().getPurchaseOrderNumber());
					purchaseOrederDataBean.setPurVendor(payy.get(0).getInvoice().getPurchase().getVendor().getFirmName());
					purchaseOrederDataBean.setPurTotalPrice(payy.get(0).getTotalAmount());
					purchaseOrederDataBean.setPaidamount(payy.get(0).getPaidAmount());
					purchaseOrederDataBean.setBalamount(""+(new BigDecimal(payy.get(0).getTotalAmount()).subtract(new BigDecimal(payy.get(0).getPaidAmount()))));
					purchaseOrederDataBean.setPayid(payy.get(0).getPayment_ID());
					purchaseOrederDataBean.setPaymenttype(payy.get(0).getPaymentType());
					purchaseOrederDataBean.setBankname(payy.get(0).getBankName());
					purchaseOrederDataBean.setAccno(payy.get(0).getAccountNumber());
					purchaseOrederDataBean.setChequedate(payy.get(0).getChequeDate());
					purchaseOrederDataBean.setChequeno(payy.get(0).getChequeNumber());
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
	public String payPurchaseAmount(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		BigDecimal paidd=BigDecimal.valueOf(0),total=BigDecimal.valueOf(0);
		paidd=new BigDecimal(purchaseOrederDataBean.getPaidamount()).add(new BigDecimal(purchaseOrederDataBean.getPurprice()));
		System.out.println("paid - > "+paidd);
		total=new BigDecimal(purchaseOrederDataBean.getPurTotalPrice());
		if(paidd.compareTo(total)==0)
		{
			System.out.println("equal -- ");
			Payment paid=entitymanager.find(Payment.class, purchaseOrederDataBean.getPayid());
			paid.setPaymentType(purchaseOrederDataBean.getPaymenttype());
			paid.setBankName(purchaseOrederDataBean.getBankname());
			paid.setChequeDate(purchaseOrederDataBean.getChequedate());
			paid.setChequeNumber(purchaseOrederDataBean.getChequeno());
			paid.setAccountNumber(purchaseOrederDataBean.getAccno());
			paid.setPaidAmount(""+paidd);
			paid.setPaymentStatus("paid");
			entitymanager.merge(paid);
		}
		else 
		{
			System.out.println("not equal -- ");
			Payment paid=entitymanager.find(Payment.class, purchaseOrederDataBean.getPayid());
			paid.setPaymentType(purchaseOrederDataBean.getPaymenttype());
			paid.setBankName(purchaseOrederDataBean.getBankname());
			paid.setChequeDate(purchaseOrederDataBean.getChequedate());
			paid.setChequeNumber(purchaseOrederDataBean.getChequeno());
			paid.setAccountNumber(purchaseOrederDataBean.getAccno());
			paid.setPaidAmount(""+paidd);
			entitymanager.merge(paid);
		}
		return "";
	}
	
	public String purchasePayment(PurchaseOrederDataBean p)
	{
		int c=0;int c1=0;
		Query v=null;
		v=entitymanager.createQuery("from Invoice where invoiceStatus='Purchase Invoice'");
		List<Invoice> in=(List<Invoice>) v.getResultList();
		if(in.size()>0)
		{			
			for (int i = 0; i <in.size(); i++)
			{
				v=null;
				v=entitymanager.createQuery("from Payment where invoice_ID=? and paymentStatus='paid'");
				v.setParameter(1, in.get(i).getInvoice_ID());
				List<Payment> pay=(List<Payment>)v.getResultList();
				if(pay.size()>0)
				{
					if(pay.get(0).getStatus_Director().equals("pending"))
					{
						c++;
					}
					if(pay.get(0).getStatus_FM().equals("pending"))
					{
						c1++;
					}
				}
				else
				{
					
				}
			}	
			p.setPayDir(c);
			p.setPayFM(c1);
			System.out.println("purchase payment waiting  - director - "+c+" - FM - "+c1);
		}	
		return "";
	}
	
	public String paymentFM(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		ArrayList<PurchaseOrederDataBean> paylist=new ArrayList<PurchaseOrederDataBean>();
		try
		{
			v=entitymanager.createQuery("from Invoice where invoiceStatus='Purchase Invoice'");
			List<Invoice> in=(List<Invoice>) v.getResultList();
			if(in.size()>0)
			{			
				int c=1;
				for (int i = 0; i <in.size(); i++)
				{
					v=null;
					v=entitymanager.createQuery("from Payment where invoice_ID=? and paymentStatus='paid' and status_FM='pending'");
					v.setParameter(1, in.get(i).getInvoice_ID());
					List<Payment> pay=(List<Payment>)v.getResultList();
					if(pay.size()>0)
					{
						PurchaseOrederDataBean list=new PurchaseOrederDataBean();
						list.setPurserialno(""+c);
						list.setPurdate(pay.get(0).getInvoice().getPurchase().getPurchaseOrderDate());
						list.setPurOrderNo(pay.get(0).getInvoice().getPurchase().getPurchaseOrderNumber());
						list.setPurVendor(pay.get(0).getInvoice().getPurchase().getVendor().getFirmName());
						list.setPurTotalPrice(pay.get(0).getInvoice().getPurchase().getTotalAmount());
						paylist.add(list);
						purchaseOrederDataBean.setPurchaseList(paylist);
						c++;
					}
					else
					{
						purchaseOrederDataBean.setPurchaseList(paylist);
					}
				}	
				System.out.println("payment FM size - "+purchaseOrederDataBean.getPurchaseList().size());
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return "";	
	}

	public String paymentDirector(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		ArrayList<PurchaseOrederDataBean> paylist=new ArrayList<PurchaseOrederDataBean>();
		try
		{
			v=entitymanager.createQuery("from Invoice where invoiceStatus='Purchase Invoice'");
			List<Invoice> in=(List<Invoice>) v.getResultList();
			if(in.size()>0)
			{			
				int c=1;
				for (int i = 0; i <in.size(); i++)
				{
					v=null;
					v=entitymanager.createQuery("from Payment where invoice_ID=? and paymentStatus='paid' and status_Director='pending'");
					v.setParameter(1, in.get(i).getInvoice_ID());
					List<Payment> pay=(List<Payment>)v.getResultList();
					if(pay.size()>0)
					{
						PurchaseOrederDataBean list=new PurchaseOrederDataBean();
						list.setPurserialno(""+c);
						list.setPurdate(pay.get(0).getInvoice().getPurchase().getPurchaseOrderDate());
						list.setPurOrderNo(pay.get(0).getInvoice().getPurchase().getPurchaseOrderNumber());
						list.setPurVendor(pay.get(0).getInvoice().getPurchase().getVendor().getFirmName());
						list.setPurTotalPrice(pay.get(0).getInvoice().getPurchase().getTotalAmount());
						paylist.add(list);
						purchaseOrederDataBean.setPurchaseList(paylist);
						c++;
					}
					else
					{
						purchaseOrederDataBean.setPurchaseList(paylist);
					}
				}	
				System.out.println("payment director size - "+purchaseOrederDataBean.getPurchaseList().size());
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return "";		
	}
	
	@Transactional(value="transactionManager")
	public String paymentApproveFM(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			v.setParameter(1, purchaseOrederDataBean.getPurOrderNo());
			List<Purchase> pur=(List<Purchase>)v.getResultList();
			int purid=0;
			if(pur.size()>0)
			{
				purid=pur.get(0).getPurchase_ID();
				System.out.println("purchase id - > "+purid);
				v=null;
				v=entitymanager.createQuery("from Invoice where purchase_ID=?");
				v.setParameter(1, purid);
				List<Invoice> inv=(List<Invoice>)v.getResultList();
				int invid=0;
				if(inv.size()>0)
				{
					invid=inv.get(0).getInvoice_ID();
					System.out.println("invoice id - > "+invid);
					v=null;
					v=entitymanager.createQuery("from Payment where invoice_ID=?");
					v.setParameter(1, invid);
					List<Payment> pay=(List<Payment>)v.getResultList();
					int payid=0;
					if(pay.size()>0)
					{
						payid=pay.get(0).getPayment_ID();
						System.out.println("payment id - > "+payid);
						Payment payup=entitymanager.find(Payment.class, payid);
						payup.setStatus_FM("Approved");
						entitymanager.merge(payup);
						if(pay.get(0).getStatus_FM().equals("Approved") && pay.get(0).getStatus_Director().equals("Approved"))
						{
							Purchase purup=entitymanager.find(Purchase.class,purid);
							purup.setPaymentStatus("paid");
							entitymanager.merge(purup);
						}
						if(pay.get(0).getStatus_Director().equals("Approved"))
						{
							Payment ppp=entitymanager.find(Payment.class, payid);
							ppp.setStatus("Approved");
							entitymanager.merge(ppp);
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

	@Transactional(value="transactionManager")
	public String paymentApproveDirector(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			v.setParameter(1, purchaseOrederDataBean.getPurOrderNo());
			List<Purchase> pur=(List<Purchase>)v.getResultList();
			int purid=0;
			if(pur.size()>0)
			{
				purid=pur.get(0).getPurchase_ID();
				System.out.println("purchase id - > "+purid);
				v=null;
				v=entitymanager.createQuery("from Invoice where purchase_ID=?");
				v.setParameter(1, purid);
				List<Invoice> inv=(List<Invoice>)v.getResultList();
				int invid=0;
				if(inv.size()>0)
				{
					invid=inv.get(0).getInvoice_ID();
					System.out.println("invoice id - > "+invid);
					v=null;
					v=entitymanager.createQuery("from Payment where invoice_ID=?");
					v.setParameter(1, invid);
					List<Payment> pay=(List<Payment>)v.getResultList();
					int payid=0;
					if(pay.size()>0)
					{
						payid=pay.get(0).getPayment_ID();
						System.out.println("payment id - > "+payid);
						Payment payup=entitymanager.find(Payment.class, payid);
						payup.setStatus_Director("Approved");
						entitymanager.merge(payup);
						if(pay.get(0).getStatus_FM().equals("Approved") && pay.get(0).getStatus_Director().equals("Approved"))
						{
							Purchase purup=entitymanager.find(Purchase.class,purid);
							purup.setPaymentStatus("paid");
							entitymanager.merge(purup);
						}
						if(pay.get(0).getStatus_FM().equals("Approved"))
						{
							Payment ppp=entitymanager.find(Payment.class, payid);
							ppp.setStatus("Approved");
							entitymanager.merge(ppp);
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
	
	public String paymentStatusView(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		ArrayList<PurchaseOrederDataBean> paylist=new ArrayList<PurchaseOrederDataBean>();
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderDate between ? and ? and purchaseApprovalStatus='Approve' and status='ordered'");
			v.setParameter(1, purchaseOrederDataBean.getPurFromDate());
			v.setParameter(2, purchaseOrederDataBean.getPurToDate());
			List<Purchase> purcahse=(List<Purchase>) v.getResultList();
			if(purcahse.size()>0)
			{
				System.out.println("purchase size - >"+purcahse.size());
				for (int i = 0; i < purcahse.size(); i++) 
				{
					v=null;
					v=entitymanager.createQuery("from Invoice where purchase_ID=?");
					v.setParameter(1, purcahse.get(i).getPurchase_ID());
					List<Invoice> inv=(List<Invoice>) v.getResultList();
					int invid=0;
					if(inv.size()>0)
					{
						invid=inv.get(0).getInvoice_ID();
						System.out.println("invoice id - > "+invid);
						v=null;
						v=entitymanager.createQuery("from Payment where invoice_ID=?");
						v.setParameter(1, invid);
						List<Payment> payy=(List<Payment>) v.getResultList();
						if(payy.size()>0)
						{
							PurchaseOrederDataBean payl=new PurchaseOrederDataBean();
							payl.setPurdate(payy.get(0).getInvoice().getPurchase().getPurchaseOrderDate());
							payl.setPurOrderNo(payy.get(0).getInvoice().getPurchase().getPurchaseOrderNumber());
							payl.setPurVendor(payy.get(0).getInvoice().getPurchase().getVendor().getFirmName());
							payl.setPurTotalPrice(payy.get(0).getTotalAmount());							
							if(Integer.parseInt(payy.get(0).getTotalAmount())>Integer.parseInt(payy.get(0).getPaidAmount()))
							{
								payl.setPaymentStatus("pending");
								payl.setBalamount(""+(new BigDecimal(payy.get(0).getTotalAmount()).subtract(new BigDecimal(payy.get(0).getPaidAmount()))));
							}
							else if(Integer.parseInt(payy.get(0).getTotalAmount())==Integer.parseInt(payy.get(0).getPaidAmount()))
							{
								payl.setPaymentStatus("paid");
								payl.setBalamount("0");
							}		
							if(Integer.parseInt(payy.get(0).getTotalAmount())<=Integer.parseInt(payy.get(0).getPaidAmount()))
							{
								payl.setPaymentStatus("paid");
								payl.setBalamount("0");
							}
							paylist.add(payl);
							purchaseOrederDataBean.setPurchaseList(paylist);
						}
						else
						{
							PurchaseOrederDataBean payl=new PurchaseOrederDataBean();
							payl.setPurdate(inv.get(0).getPurchase().getPurchaseOrderDate());
							payl.setPurOrderNo(inv.get(0).getPurchase().getPurchaseOrderNumber());
							payl.setPurVendor(inv.get(0).getPurchase().getVendor().getFirmName());
							payl.setPurTotalPrice(inv.get(0).getPurchase().getTotalAmount());
							payl.setBalamount(inv.get(0).getPurchase().getTotalAmount());
							payl.setPaymentStatus("pending");
							paylist.add(payl);
							purchaseOrederDataBean.setPurchaseList(paylist);
						}
					}
					else
					{
						PurchaseOrederDataBean payl=new PurchaseOrederDataBean();
						payl.setPurdate(purcahse.get(i).getPurchaseOrderDate());
						payl.setPurOrderNo(purcahse.get(i).getPurchaseOrderNumber());
						payl.setPurVendor(purcahse.get(i).getVendor().getFirmName());
						payl.setPurTotalPrice(purcahse.get(i).getTotalAmount());
						payl.setBalamount(purcahse.get(i).getTotalAmount());
						payl.setPaymentStatus("pending");
						paylist.add(payl);
						purchaseOrederDataBean.setPurchaseList(paylist);
					}
				}
				System.out.println("purchase payment size - > "+purchaseOrederDataBean.getPurchaseList());
			}
			else
			{
				purchaseOrederDataBean.setPurchaseList(paylist);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String purcPaymentView(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		System.out.println("purchase payment view in dao -- ");
		Query v=null;
		ArrayList<PurchaseOrederDataBean> lll=new ArrayList<PurchaseOrederDataBean>();
		BigDecimal nrq=BigDecimal.valueOf(0),totretamnt=BigDecimal.valueOf(0),totamnt=BigDecimal.valueOf(0);
		BigDecimal amnt=BigDecimal.valueOf(0),amnt1=BigDecimal.valueOf(0),mnt=BigDecimal.valueOf(0);				
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			v.setParameter(1, purchaseOrederDataBean.getPurOrderNo());
			List<Purchase> paylist=(List<Purchase>) v.getResultList();
			int purid=0;
			if(paylist.size()>0)
			{
				purid=paylist.get(0).getPurchase_ID();
				System.out.println("purchase id - > "+purid);
				v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
				v.setParameter(1, purid);
				List<PurchaseRecord> purrec=(List<PurchaseRecord>) v.getResultList();
				System.out.println("purchase record size -- > "+purrec.size());
				if(purrec.size()>0)
				{
					int c=1;
					for (int i = 0; i < purrec.size(); i++)
					{
						PurchaseOrederDataBean list=new PurchaseOrederDataBean();
						list.setPurserialno(""+c);
						v=null;
						v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
						v.setParameter(1, purrec.get(i).getPurchase_record_ID());
						List<PurchaseReturn> salret=(List<PurchaseReturn>)v.getResultList();
						if(salret.size()>0)
						{
							nrq=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));
							totretamnt=(nrq.multiply(new BigDecimal(salret.get(0).getPurchaseRecord().getUnitPrice())));
						}
						else
						{
							nrq=BigDecimal.valueOf(0);
							totretamnt=BigDecimal.valueOf(0);
						}
						mnt=mnt.add(totretamnt);
						totamnt=totamnt.add(new BigDecimal(purrec.get(i).getQuantity()).multiply(new BigDecimal(purrec.get(i).getUnitPrice())));
						list.setReturnqauntity(""+nrq);
						list.setRetamount(""+totretamnt);
						if(paylist.get(0).getCategory().equals("Product Trading"))
						{
							list.setPurProductName(purrec.get(i).getProduct().getProductName());
						}
						else if(!paylist.get(0).getCategory().equals("Product Trading"))
						{
							list.setPurProductName(purrec.get(i).getRawMaterial().getProductName());
						}
						list.setPurQuantity(purrec.get(i).getQuantity());
						list.setUnitPrice(purrec.get(i).getUnitPrice());
						list.setPurNetAmount(""+(new BigDecimal(purrec.get(i).getQuantity()).multiply(new BigDecimal(purrec.get(i).getUnitPrice()))));
						lll.add(list);
						purchaseOrederDataBean.setPurchaseViewList(lll);
						c++;
					}
					amnt=totamnt.subtract(mnt);
					System.out.println("amm - > "+amnt);
					BigDecimal ttt=BigDecimal.valueOf(0),ttt1=BigDecimal.valueOf(0);
					if(paylist.get(0).getTypeofProject().equals("No Tax") ||  paylist.get(0).getTypeofProject().equals(""))
					{
						ttt1=amnt;
					}
					else if(paylist.get(0).getTypeofProject().equals("10% Tax"))
					{
						ttt1=amnt.add(amnt.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
					}
					System.out.println("amm 1 - > "+ttt1);
					if(paylist.get(0).getDiscountType().equals(""))
					{
						ttt=ttt1;
					}
					else if(paylist.get(0).getDiscountType().equals("IDR"))
					{
						ttt=ttt1.subtract(new BigDecimal(paylist.get(0).getDiscountAmount()));
					}
					else if(paylist.get(0).getDiscountType().equals("%"))
					{
						ttt=ttt1.subtract(ttt1.multiply(new BigDecimal(paylist.get(0).getDiscountAmount())).divide(BigDecimal.valueOf(100)));
					}
					System.out.println("amm 2- > "+ttt);
					amnt1=ttt;
					purchaseOrederDataBean.setPurCrossTotal(""+amnt1.setScale(0,BigDecimal.ROUND_HALF_UP));
					purchaseOrederDataBean.setPurTotalPrice(paylist.get(0).getTotalAmount());
					purchaseOrederDataBean.setPurVendor(paylist.get(0).getVendor().getFirmName());
					purchaseOrederDataBean.setPurdate(paylist.get(0).getPurchaseOrderDate());
					purchaseOrederDataBean.setPurEstDate(paylist.get(0).getPurchaseEstimatedDate());
					purchaseOrederDataBean.setPurOrderNo(paylist.get(0).getPurchaseOrderNumber());
					purchaseOrederDataBean.setTaxType(paylist.get(0).getTypeofProject());
					if(paylist.get(0).getDiscountType().equals(""))
					{
						purchaseOrederDataBean.setPurDiscType("-");
						purchaseOrederDataBean.setPurDiscAmmount("0");
					}
					else
					{						
						purchaseOrederDataBean.setPurDiscType(paylist.get(0).getDiscountType());
						purchaseOrederDataBean.setPurDiscAmmount(paylist.get(0).getDiscountAmount());						
					}
					purchaseOrederDataBean.setPurStaffId(paylist.get(0).getEmployee().getEmployee_details_ID());
					purchaseOrederDataBean.setPurCategory(paylist.get(0).getCategory());
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
	public List<ProfitLossDataBean> searchProfitLoss(
			ProfitLossDataBean profitLossDataBean) {
		System.out.println("Inside Dao Method Calling");
		System.out.println(profitLossDataBean.getFromDate());
		System.out.println(profitLossDataBean.getToDate());
		List<ProfitLossDataBean> finalResult=null;
		Query q=null;Query q1=null;Query q2=null;Query q3=null;Query q4=null;Query q5=null;
		if(profitLossDataBean.getFromDate() !=null && profitLossDataBean.getToDate() != null){
			finalResult=new ArrayList<ProfitLossDataBean>();
			q=entitymanager.createQuery("from Sale where salesOrderDate between ? and ? and paymentStatus='paid'");
			q.setParameter(1, profitLossDataBean.getFromDate());
			q.setParameter(2, profitLossDataBean.getToDate());
			List<Sale> salesRes=(List<Sale>)q.getResultList();
			System.out.println("Size"+salesRes.size());
			int salesid=0;
			if(salesRes.size() > 0){
				profitLossDataBean.setSalesList(salesRes);
				for(int i=0;i<salesRes.size();i++){
					salesid=salesRes.get(i).getSales_ID();
					System.out.println("sale id -- > "+salesid);
					Query v=null;
					v=entitymanager.createQuery("from Invoice where sales_ID=?");
					v.setParameter(1, salesid);
					List<Invoice> invoice=(List<Invoice>) v.getResultList();
					int invoiceid=0;	
					if(invoice.size()>0)
					{
						invoiceid=invoice.get(0).getInvoice_ID();
						System.out.println("invoice id - > "+invoiceid);
						v=null;
						v=entitymanager.createQuery("from Payment where invoice_ID=?");
						v.setParameter(1, invoiceid);
						List<Payment> payy=(List<Payment>) v.getResultList();
						if(payy.size()>0)
						{
							ProfitLossDataBean 	profitLoss=new ProfitLossDataBean();
							profitLoss.setProfitDate(salesRes.get(i).getSalesOrderDate());
							profitLoss.setProfitClientName(salesRes.get(i).getSalesOrderNumber());
							profitLoss.setProfitReason("Sales");
							profitLoss.setProfitAmount(payy.get(0).getTotalAmount());
						finalResult.add(profitLoss);
						}
					}					
				}
			}		
		q2=entitymanager.createQuery("from Transaction where transactionDate between ? and ? and (status='Active' and transactionType='Income')");
		q2.setParameter(1, profitLossDataBean.getFromDate());
		q2.setParameter(2, profitLossDataBean.getToDate());
		List<Transaction> transRes=(List<Transaction>)q2.getResultList();
		System.out.println("Size"+transRes.size());
		if(transRes.size() > 0){
			
			profitLossDataBean.setTransactionList(transRes);
			for(int k=0;k<transRes.size();k++){
				ProfitLossDataBean 	profitLoss=new ProfitLossDataBean();
				profitLoss.setProfitDate(transRes.get(k).getTransactionDate());
				profitLoss.setProfitClientName(transRes.get(k).getTransactionNo());
				profitLoss.setProfitReason("Transaction");
				profitLoss.setProfitAmount(transRes.get(k).getTransactionAmount());
			finalResult.add(profitLoss);
			}
		}
		q3=entitymanager.createQuery("from Transaction where transactionDate between ? and ? and(status='Active' and transactionType='Expenses')");
		q3.setParameter(1, profitLossDataBean.getFromDate());
		q3.setParameter(2, profitLossDataBean.getToDate());
		List<Transaction> transEXRes=(List<Transaction>)q3.getResultList();
		System.out.println("Size"+transEXRes.size());
		if(transEXRes.size() > 0){
			profitLossDataBean.setTransactionList1(transEXRes);
			for(int m=0;m<transEXRes.size();m++){
				ProfitLossDataBean 	profitLoss=new ProfitLossDataBean();
				profitLoss.setLossDate(transEXRes.get(m).getTransactionDate());
				profitLoss.setLossClientName(transEXRes.get(m).getTransactionNo());
				profitLoss.setLossReason("Transaction");
				profitLoss.setLossAmount(transEXRes.get(m).getTransactionAmount());
			finalResult.add(profitLoss);
			}
		}
	q4=entitymanager.createQuery("from Purchase where purchaseOrderDate between ? and ? and(purchaseApprovalStatus='Approve' and paymentStatus='paid' and status='ordered')");
		q4.setParameter(1, profitLossDataBean.getFromDate());
		q4.setParameter(2, profitLossDataBean.getToDate());
		List<Purchase> PurRes=(List<Purchase>)q4.getResultList();
		System.out.println("Size -- purchase"+PurRes.size());
		if(PurRes.size() > 0){
			profitLossDataBean.setPurchaseList(PurRes);
			
			for(int l=0;l<PurRes.size();l++){
				Query v=null;
				v=entitymanager.createQuery("from Invoice where purchase_ID=?");
				v.setParameter(1, PurRes.get(l).getPurchase_ID());
				List<Invoice> invoice=(List<Invoice>) v.getResultList();
				int invoiceid=0;	
				if(invoice.size()>0)
				{
					invoiceid=invoice.get(0).getInvoice_ID();
					System.out.println("invoice id - > "+invoiceid);
					v=null;
					v=entitymanager.createQuery("from Payment where invoice_ID=?");
					v.setParameter(1, invoiceid);
					List<Payment> payy=(List<Payment>) v.getResultList();
					if(payy.size()>0)
					{
						ProfitLossDataBean 	profitLoss=new ProfitLossDataBean();
						profitLoss.setLossDate(PurRes.get(l).getPurchaseOrderDate());
						profitLoss.setLossClientName(PurRes.get(l).getPurchaseOrderNumber());
						profitLoss.setLossReason("Purchase");
						profitLoss.setLossAmount(payy.get(0).getTotalAmount());
					finalResult.add(profitLoss);
					}
				}
				
			}
		}
		q4=entitymanager.createQuery("from Payroll where payrollMonth between ? and ? and(status='Active')");
		q4.setParameter(1, profitLossDataBean.getFromDate());
		q4.setParameter(2, profitLossDataBean.getToDate());
		List<Payroll> PayrollRes=(List<Payroll>)q4.getResultList();
		System.out.println("Size"+PurRes.size());
		if(PayrollRes.size() > 0){
			profitLossDataBean.setPayList(PayrollRes);
			
			for(int n=0;n<PayrollRes.size();n++){
				ProfitLossDataBean 	profitLoss=new ProfitLossDataBean();
				profitLoss.setLossDate(PayrollRes.get(n).getPayrollMonth());
				profitLoss.setLossClientName(PayrollRes.get(n).getEmployee().getEmployee_details_ID());
				profitLoss.setLossReason("Payroll");
				profitLoss.setLossAmount(PayrollRes.get(n).getTotalSalary());
			finalResult.add(profitLoss);
			}
		}
		/*finalResult.add(profitLossDataBean);*/
	System.out.println(finalResult.size());
		BigDecimal big=BigDecimal.ZERO;
		BigDecimal bigq=BigDecimal.ZERO;
		if(finalResult.size() > 0){
			for(int a=0;a<finalResult.size();a++){
				System.out.println();
				if(finalResult.get(a).getProfitAmount() !=null){
				big=big.add(new BigDecimal(finalResult.get(a).getProfitAmount()));
				System.out.println("TotalAmount"+big.toString());
				profitLossDataBean.setTptalProfit(big.toString());
				}
				if(finalResult.get(a).getLossAmount() !=null){
				bigq=bigq.add(new BigDecimal(finalResult.get(a).getLossAmount()));
				System.out.println("TotalAmount"+bigq.toString());
				profitLossDataBean.setTotalLoss(bigq.toString());
				}
			}
		}
		}
		return finalResult;
	}
	
	public String purchaseOrderClose(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		ArrayList<PurchaseOrederDataBean> paylist=new ArrayList<PurchaseOrederDataBean>();
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderDate between ? and ? and deliveryStatus='Approve' and paymentStatus='paid' and status='ordered'");
			v.setParameter(1, purchaseOrederDataBean.getPurFromDate());
			v.setParameter(2, purchaseOrederDataBean.getPurToDate());
			List<Purchase> purcahse=(List<Purchase>) v.getResultList();
			if(purcahse.size()>0)
			{
				System.out.println("purchase size - >"+purcahse.size());
				for (int i = 0; i < purcahse.size(); i++) 
				{	
					BigDecimal nrq=BigDecimal.valueOf(0),totretamnt=BigDecimal.valueOf(0),totamnt=BigDecimal.valueOf(0);
					BigDecimal amnt=BigDecimal.valueOf(0),amnt1=BigDecimal.valueOf(0),mnt=BigDecimal.valueOf(0);						
					v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
					v.setParameter(1, purcahse.get(i).getPurchase_ID());
					List<PurchaseRecord> purrec=(List<PurchaseRecord>) v.getResultList();
					System.out.println("purchase record size -- > "+purrec.size());
					if(purrec.size()>0)
					{
						int c=1;
						for (int j = 0; j < purrec.size(); j++)
						{
							v=null;
							v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
							v.setParameter(1, purrec.get(j).getPurchase_record_ID());
							List<PurchaseReturn> salret=(List<PurchaseReturn>)v.getResultList();
							if(salret.size()>0)
							{
								nrq=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));
								totretamnt=(nrq.multiply(new BigDecimal(salret.get(0).getPurchaseRecord().getUnitPrice())));
							}
							else
							{
								nrq=BigDecimal.valueOf(0);
								totretamnt=BigDecimal.valueOf(0);
							}
							mnt=mnt.add(totretamnt);
							totamnt=totamnt.add(new BigDecimal(purrec.get(j).getQuantity()).multiply(new BigDecimal(purrec.get(j).getUnitPrice())));
						}
					}
					amnt=totamnt.subtract(mnt);
					System.out.println("amm - > "+amnt);
					BigDecimal ttt=BigDecimal.valueOf(0),ttt1=BigDecimal.valueOf(0);
					if(purcahse.get(0).getTypeofProject().equals("No Tax") ||  purcahse.get(0).getTypeofProject().equals(""))
					{
						ttt1=amnt;
					}
					else if(purcahse.get(0).getTypeofProject().equals("10% Tax"))
					{
						ttt1=amnt.add(amnt.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
					}
					System.out.println("amm 1 - > "+ttt1);
					if(purcahse.get(0).getDiscountType().equals(""))
					{
						ttt=ttt1;
					}
					else if(purcahse.get(0).getDiscountType().equals("IDR"))
					{
						ttt=ttt1.subtract(new BigDecimal(purcahse.get(0).getDiscountAmount()));
					}
					else if(purcahse.get(0).getDiscountType().equals("%"))
					{
						ttt=ttt1.subtract(ttt1.multiply(new BigDecimal(purcahse.get(0).getDiscountAmount())).divide(BigDecimal.valueOf(100)));
					}
					System.out.println("amm 2- > "+ttt);
					amnt1=ttt;
					PurchaseOrederDataBean payl=new PurchaseOrederDataBean();
					payl.setPurdate(purcahse.get(i).getPurchaseOrderDate());
					payl.setPurOrderNo(purcahse.get(i).getPurchaseOrderNumber());
					payl.setPurVendor(purcahse.get(i).getVendor().getFirmName());
					payl.setPurTotalPrice(""+amnt1.setScale(0,BigDecimal.ROUND_HALF_UP));
					payl.setStatus("Closed");
					paylist.add(payl);
					purchaseOrederDataBean.setPurchaseList(paylist);					
				}
				System.out.println("purchase close size - > "+purchaseOrederDataBean.getPurchaseList());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String purchaseReturn(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		ArrayList<PurchaseOrederDataBean> litss=new ArrayList<PurchaseOrederDataBean>();
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderDate between ? and ? and paymentStatus='pending' and status2='Stocked In' and category=? and status='ordered'");
			v.setParameter(1, purchaseOrederDataBean.getPurFromDate());
			v.setParameter(2, purchaseOrederDataBean.getPurToDate());
			v.setParameter(3, purchaseOrederDataBean.getPurCategory());
			List<Purchase> pur=(List<Purchase>)v.getResultList();
			if(pur.size()>0)
			{
				for (int i = 0; i < pur.size(); i++)
				{					
					PurchaseOrederDataBean list=new PurchaseOrederDataBean();
					list.setPurdate(pur.get(i).getPurchaseOrderDate());
					list.setPurOrderNo(pur.get(i).getPurchaseOrderNumber());
					list.setPurVendor(pur.get(i).getVendor().getFirmName());
					list.setPurTotalPrice(pur.get(i).getTotalAmount());
					list.setPurStaffName(pur.get(i).getEmployee().getEmployeeName());
					list.setPurCategory(pur.get(i).getCategory());
					litss.add(list);
					purchaseOrederDataBean.setPurchaseList(litss);					
				}
				System.out.println("size - >"+purchaseOrederDataBean.getPurchaseList().size());
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";	
	}
	
	public String purchaseReturnsearch(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		ArrayList<PurchaseOrederDataBean> litss=new ArrayList<PurchaseOrederDataBean>();
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			v.setParameter(1, purchaseOrederDataBean.getPurOrderNo());
			List<Purchase> pur=(List<Purchase>)v.getResultList();
			int purchaseid=0;
			if(pur.size()>0)
			{
				purchaseid=pur.get(0).getPurchase_ID();
				System.out.println("purchase id -- > "+purchaseid);
				purchaseOrederDataBean.setPurdate(pur.get(0).getPurchaseOrderDate());
				purchaseOrederDataBean.setPurOrderNo(pur.get(0).getPurchaseOrderNumber());
				purchaseOrederDataBean.setPurVendor(pur.get(0).getVendor().getFirmName());
				purchaseOrederDataBean.setPurTotalPrice(pur.get(0).getTotalAmount());
				purchaseOrederDataBean.setPurStaffName(pur.get(0).getEmployee().getEmployeeName());	
				purchaseOrederDataBean.setPurCategory(pur.get(0).getCategory());
				v=null;
				v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
				v.setParameter(1, purchaseid);
				List<PurchaseRecord> purreec=(List<PurchaseRecord>) v.getResultList();
				System.out.println("purchase record size -- > "+purreec.size());
				if(purreec.size()>0)
				{
					int c=1;
					for (int i = 0; i < purreec.size(); i++)
					{
						PurchaseOrederDataBean list=new PurchaseOrederDataBean();
						list.setPurserialno(""+c);
						if(purchaseOrederDataBean.getPurCategory().equals("Product Trading"))
						{
							list.setPurProductName(purreec.get(i).getProduct().getProductName());
							list.setPurprice(purreec.get(i).getProduct().getActualPrice());
						}
						else if(!purchaseOrederDataBean.getPurCategory().equals("Product Trading"))
						{
							list.setPurProductName(purreec.get(i).getRawMaterial().getProductName());
							list.setPurprice(purreec.get(i).getRawMaterial().getActualPrice());
						}
						list.setPurQuantity(purreec.get(i).getQuantity());						
						list.setTick("false");
						litss.add(list);
						purchaseOrederDataBean.setPurchaseViewList(litss);
						c++;
					}							
					System.out.println("size - >"+purchaseOrederDataBean.getPurchaseViewList().size());
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";	
	}
	
	public String returnCheckpur(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException
	{
		System.out.println("return -- check dao - ");
		Query v=null;
		List<PurchaseRecord> purrec=null;
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			v.setParameter(1, purchaseOrederDataBean.getPurOrderNo());
			List<Purchase> purchase=(List<Purchase>) v.getResultList();
			int purid=0;
			if(purchase.size()>0)
			{
				purid=purchase.get(0).getPurchase_ID();
				System.out.println("purchase id -- > "+purid);
				v=null;
				if(purchase.get(0).getCategory().equals("Product Trading"))
				{
					v=entitymanager.createQuery("from Product where productName=? and category='Product Trading'");
					v.setParameter(1, purchaseOrederDataBean.getPurProductName());
					List<Product> product=(List<Product>)v.getResultList();
					v=null;
					v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=? and product_ID=?");
					v.setParameter(1, purid);
					v.setParameter(2, product.get(0).getProduct_ID());
					purrec=(List<PurchaseRecord>)v.getResultList();
				}
				else if(!purchase.get(0).getCategory().equals("Product Trading"))
				{
					v=entitymanager.createQuery("from RawMaterial where productName=? and category=?");
					v.setParameter(1, purchaseOrederDataBean.getPurProductName());
					v.setParameter(2, purchase.get(0).getCategory());
					List<RawMaterial> product=(List<RawMaterial>)v.getResultList();
					v=null;
					v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=? and raw_ID=?");
					v.setParameter(1, purid);
					v.setParameter(2, product.get(0).getRaw_ID());
					purrec=(List<PurchaseRecord>)v.getResultList();
				}	
				int purrecid=0;
				if(purrec.size()>0)
				{
					purrecid=purrec.get(0).getPurchase_record_ID();
					System.out.println("purchase record id -- > "+purrecid);
					v=null;
					v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
					v.setParameter(1, purrecid);
					List<PurchaseReturn> purret=(List<PurchaseReturn>)v.getResultList();
					if(purret.size()>0)
					{
						BigDecimal quan=BigDecimal.valueOf(0),quan1=BigDecimal.valueOf(0);
						String qq1="",qq2="";
						qq1=purrec.get(0).getQuantity();
						quan=new BigDecimal(purret.get(0).getNormalReturn()).add(new BigDecimal(purret.get(0).getDamageReturn()));							
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
	
	public String quantityCheckReturn(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException
	{
		System.out.println("return -- check dao - ");
		Query v=null;
		List<PurchaseRecord> purrec=null;
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			v.setParameter(1, purchaseOrederDataBean.getPurOrderNo());
			List<Purchase> purchase=(List<Purchase>) v.getResultList();
			int purid=0;
			if(purchase.size()>0)
			{
				purid=purchase.get(0).getPurchase_ID();
				System.out.println("purchase id -- > "+purid);
				v=null;
				if(purchase.get(0).getCategory().equals("Product Trading"))
				{
					v=entitymanager.createQuery("from Product where productName=? and category='Product Trading'");
					v.setParameter(1, purchaseOrederDataBean.getPurProductName());
					List<Product> product=(List<Product>)v.getResultList();
					v=null;
					v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=? and product_ID=?");
					v.setParameter(1, purid);
					v.setParameter(2, product.get(0).getProduct_ID());
					purrec=(List<PurchaseRecord>)v.getResultList();
				}
				else if(!purchase.get(0).getCategory().equals("Product Trading"))
				{
					v=entitymanager.createQuery("from RawMaterial where productName=? and category=?");
					v.setParameter(1, purchaseOrederDataBean.getPurProductName());
					v.setParameter(2, purchase.get(0).getCategory());
					List<RawMaterial> product=(List<RawMaterial>)v.getResultList();
					v=null;
					v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=? and raw_ID=?");
					v.setParameter(1, purid);
					v.setParameter(2, product.get(0).getRaw_ID());
					purrec=(List<PurchaseRecord>)v.getResultList();
				}	
				int purrecid=0;
				if(purrec.size()>0)
				{
					purrecid=purrec.get(0).getPurchase_record_ID();
					System.out.println("purchase record id -- > "+purrecid);
					v=null;
					v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
					v.setParameter(1, purrecid);
					List<PurchaseReturn> purret=(List<PurchaseReturn>)v.getResultList();
					if(purret.size()>0)
					{
						BigDecimal quan=BigDecimal.valueOf(0),quan1=BigDecimal.valueOf(0);
						String qq1="",qq="";
						qq1=purrec.get(0).getQuantity();
						quan=new BigDecimal(purret.get(0).getNormalReturn()).add(new BigDecimal(purret.get(0).getDamageReturn()));
						quan1=new BigDecimal(purrec.get(0).getQuantity()).subtract(quan);
						quan=quan.add(new BigDecimal(purchaseOrederDataBean.getReturnqauntity())).add(new BigDecimal(purchaseOrederDataBean.getDr()));
						qq=""+quan;
						if(Integer.parseInt(qq)>Integer.parseInt(qq1))
						{
							throw new LiusenException("Only "+quan1+" Quantities in "+purchaseOrederDataBean.getPurProductName());
						}			
					}
					else
					{
						System.out.println("not in return table - -> ");
						BigDecimal quan=BigDecimal.valueOf(0),quan1=BigDecimal.valueOf(0);
						String qq="",qq1="";
						qq1=purrec.get(0).getQuantity();
						quan=quan.add(new BigDecimal(purchaseOrederDataBean.getReturnqauntity())).add(new BigDecimal(purchaseOrederDataBean.getDr()));
						qq=""+quan;
						if(Integer.parseInt(qq)>Integer.parseInt(qq1))
						{
							throw new LiusenException("Only "+qq1+" Quantities in "+purchaseOrederDataBean.getPurProductName());
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

	public String returnSubmitPurcahse(PurchaseOrederDataBean purchaseOrederDataBean) 
	{
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			v.setParameter(1, purchaseOrederDataBean.getPurOrderNo());
			List<Purchase> pur=(List<Purchase>) v.getResultList();
			int purid=0;
			if(pur.size()>0)
			{
				purid=pur.get(0).getPurchase_ID();
				System.out.println("purchase id -- > "+purid);
				purchaseOrederDataBean.setPurchaseid(purid);
				purchaseOrederDataBean.setTaxType(pur.get(0).getTypeofProject());
				purchaseOrederDataBean.setPurDiscType(pur.get(0).getDiscountType());
				purchaseOrederDataBean.setPurDiscAmmount(pur.get(0).getDiscountAmount());
				purchaseOrederDataBean.setPurCategory(pur.get(0).getCategory());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	@Transactional(value="transactionManager")
	public String returnSubmitPurcahse1(PurchaseOrederDataBean purchaseOrederDataBean, int j) 
	{
		Date dd=new Date();
		BigDecimal quan=BigDecimal.valueOf(0),quan1=BigDecimal.valueOf(0),quan2=BigDecimal.valueOf(0),quan3=BigDecimal.valueOf(0);
		Timestamp time=new Timestamp(dd.getTime());		
		Query v=null;
		List<PurchaseRecord> purrec=null;
		if(purchaseOrederDataBean.getPurCategory().equals("Product Trading"))
		{
			v=entitymanager.createQuery("from Product where productName=? and category='Product Trading'");
			v.setParameter(1, purchaseOrederDataBean.getPurchaseViewList().get(j).getPurProductName());
			List<Product> product=(List<Product>)v.getResultList();
			purchaseOrederDataBean.setUnitPrice(product.get(0).getActualPrice());
			v=null;
			v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=? and product_ID=?");
			v.setParameter(1, purchaseOrederDataBean.getPurchaseid());
			v.setParameter(2, product.get(0).getProduct_ID());
			purrec=(List<PurchaseRecord>)v.getResultList();
		}
		else if(!purchaseOrederDataBean.getPurCategory().equals("Product Trading"))
		{
			v=entitymanager.createQuery("from RawMaterial where productName=? and category=?");
			v.setParameter(1, purchaseOrederDataBean.getPurchaseViewList().get(j).getPurProductName());
			v.setParameter(2, purchaseOrederDataBean.getPurCategory());
			List<RawMaterial> product=(List<RawMaterial>)v.getResultList();
			purchaseOrederDataBean.setUnitPrice(product.get(0).getActualPrice());
			v=null;
			v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=? and raw_ID=?");
			v.setParameter(1, purchaseOrederDataBean.getPurchaseid());
			v.setParameter(2, product.get(0).getRaw_ID());
			purrec=(List<PurchaseRecord>)v.getResultList();
		}		
		if(purrec.size()>0)
		{
			purchaseOrederDataBean.setPurchaserecid(purrec.get(0).getPurchase_record_ID());
			v=null;
			v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
			v.setParameter(1, purrec.get(0).getPurchase_record_ID());
			List<PurchaseReturn> purret=(List<PurchaseReturn>)v.getResultList();
			if(purret.size()>0)
			{
				PurchaseReturn purre=entitymanager.find(PurchaseReturn.class, purret.get(0).getPurchase_return_ID());
				quan1=new BigDecimal(purchaseOrederDataBean.getPurchaseViewList().get(j).getReturnqauntity()).add(new BigDecimal(purret.get(0).getNormalReturn()));
				quan2=new BigDecimal(purchaseOrederDataBean.getPurchaseViewList().get(j).getDr()).add(new BigDecimal(purret.get(0).getDamageReturn()));
				quan3=quan1.add(quan2);
				purre.setNormalReturn(""+quan1);
				purre.setDamageReturn(""+quan2);
				purre.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				if(quan3.compareTo(new BigDecimal(purret.get(0).getPurchaseRecord().getQuantity()))==0)
				{
					purre.setStatus("Return");
					purchaseOrederDataBean.setStatus("return");
				}
				else
				{
					purre.setStatus("partial");
					purchaseOrederDataBean.setStatus("partial");
				}
				purre.setPurchaseReturnDate(dd);
				purre.setPurchaseReturnTime(time);
				entitymanager.merge(purre);
			}
			else
			{
				quan=new BigDecimal(purchaseOrederDataBean.getPurchaseViewList().get(j).getReturnqauntity()).add(new BigDecimal(purchaseOrederDataBean.getPurchaseViewList().get(j).getDr()));
				System.out.println("total quantity - > "+quan);
				String quann=""+quan;				
				PurchaseReturn salere=new PurchaseReturn();
				salere.setNormalReturn(purchaseOrederDataBean.getPurchaseViewList().get(j).getReturnqauntity());
				salere.setDamageReturn(purchaseOrederDataBean.getPurchaseViewList().get(j).getDr());
				salere.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				salere.setPurchaseRecord(entitymanager.find(PurchaseRecord.class, purrec.get(0).getPurchase_record_ID()));
				if(Integer.parseInt(purrec.get(0).getQuantity())==Integer.parseInt(quann))
				{
					salere.setStatus("Return");
					purchaseOrederDataBean.setStatus("return");
				}
				else
				{
					salere.setStatus("partial");
					purchaseOrederDataBean.setStatus("partial");
				}
				salere.setPurchaseReturnDate(dd);
				salere.setPurchaseReturnTime(time);
				entitymanager.persist(salere);
			}
		}	
		return "";
	}

	@Transactional(value="transactionManager")
	public String purchaseUpdateReturn(PurchaseOrederDataBean purchaseOrederDataBean) 
	{
		BigDecimal nrq=BigDecimal.valueOf(0),totretamnt=BigDecimal.valueOf(0),totamnt=BigDecimal.valueOf(0);
		BigDecimal amnt=BigDecimal.valueOf(0),amnt1=BigDecimal.valueOf(0);
		Query v=null;
		v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
		v.setParameter(1, purchaseOrederDataBean.getPurchaseid());
		List<PurchaseRecord> salrec=(List<PurchaseRecord>)v.getResultList();
		if(salrec.size()>0)
		{
			int c=0;
			for (int i = 0; i < salrec.size(); i++) 
			{
				v=null;
				v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
				v.setParameter(1, salrec.get(i).getPurchase_record_ID());
				List<PurchaseReturn> salret=(List<PurchaseReturn>)v.getResultList();
				if(salret.size()>0)
				{
					nrq=new BigDecimal(salret.get(0).getNormalReturn()).add(new BigDecimal(salret.get(0).getDamageReturn()));
					if(nrq.compareTo(new BigDecimal(salret.get(0).getPurchaseRecord().getQuantity()))==0)
					{
						c++;
					}
					totretamnt=(nrq.multiply(new BigDecimal(salret.get(0).getPurchaseRecord().getUnitPrice()))).add(totretamnt);
					System.out.println("total return  amount -- > "+totretamnt);
				}
				totamnt=(new BigDecimal(salrec.get(i).getQuantity()).multiply(new BigDecimal(salrec.get(i).getUnitPrice()))).add(totamnt);
				System.out.println("total amount -- > "+totamnt);
			}
			amnt=totamnt.subtract(totretamnt);
			BigDecimal ttt=BigDecimal.valueOf(0),ttt1=BigDecimal.valueOf(0);
			if(purchaseOrederDataBean.getTaxType().equals("No Tax") ||  purchaseOrederDataBean.getTaxType().equals(""))
			{
				ttt1=amnt;
			}
			else if(purchaseOrederDataBean.getTaxType().equals("10% Tax"))
			{
				ttt1=amnt.add(amnt.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100)));
			}
			if(purchaseOrederDataBean.getPurDiscType().equals(""))
			{
				ttt=ttt1;
			}
			else if(purchaseOrederDataBean.getPurDiscType().equals("IDR"))
			{
				ttt=ttt1.subtract(new BigDecimal(purchaseOrederDataBean.getPurDiscAmmount()));
			}
			else if(purchaseOrederDataBean.getPurDiscType().equals("%"))
			{
				ttt=ttt1.subtract(ttt1.multiply(new BigDecimal(purchaseOrederDataBean.getPurDiscAmmount())).divide(BigDecimal.valueOf(100)));
			}
			amnt1=ttt;
			purchaseOrederDataBean.setRetamount(""+amnt1.setScale(0,BigDecimal.ROUND_HALF_UP));
			if(c==salrec.size())
			{
				Purchase upd=entitymanager.find(Purchase.class, purchaseOrederDataBean.getPurchaseid());
				upd.setStatus("Return");
				entitymanager.merge(upd);
			}
		}		
		return "";
	}

	@Transactional(value="transactionManager")
	public String stockUpdateReturnNr(PurchaseOrederDataBean purchaseOrederDataBean, int j)
	{
		System.out.println("stock update barcode in nr -- ");
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from Batch where productName=? and status='Active' and category=?");
			v.setParameter(1, purchaseOrederDataBean.getPurchaseViewList().get(j).getPurProductName());
			v.setParameter(2, purchaseOrederDataBean.getPurCategory());
			List<Batch> batch=(List<Batch>) v.getResultList();
			if(batch.size()>0)
			{
				v=null;
				v=entitymanager.createQuery("from Barcode where batch_ID=? and status='Barcode Generated'");
				v.setParameter(1, batch.get(0).getBatch_ID());
				List<Barcode> barcode=(List<Barcode>) v.getResultList();
				if(barcode.size()>0)
				{
					Barcode stkup=entitymanager.find(Barcode.class, barcode.get(0).getBarcode_ID());
					stkup.setStockIn(""+(new BigDecimal(barcode.get(0).getStockIn())).subtract(new BigDecimal(purchaseOrederDataBean.getPurchaseViewList().get(j).getReturnqauntity())));
					entitymanager.merge(stkup);
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
	public String stockUpdateReturnDr(PurchaseOrederDataBean purchaseOrederDataBean, int j)
	{
		System.out.println("stock update damge in dr -- ");
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		try
		{
			v=entitymanager.createQuery("from Batch where productName=? and status='Active' and category=?");
			v.setParameter(1, purchaseOrederDataBean.getPurchaseViewList().get(j).getPurProductName());
			v.setParameter(2, purchaseOrederDataBean.getPurCategory());
			List<Batch> batch=(List<Batch>) v.getResultList();
			if(batch.size()>0)
			{
				StockDamage damup=new StockDamage();
				damup.setBatch(entitymanager.find(Batch.class,batch.get(0).getBatch_ID()));
				damup.setDamageReturn(purchaseOrederDataBean.getPurchaseViewList().get(j).getDr());
				damup.setStatus("Damage");
				damup.setStock_ID(batch.get(0).getStock().getStock_ID());
				damup.setPurchase_ID(batch.get(0).getStock().getPurchase().getPurchase_ID());
				damup.setNormalReturn("0");
				damup.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
				damup.setDamageDate(date);
				damup.setDaamgeTime(time);
				entitymanager.persist(damup);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		return "";
	}

	@Transactional(value="transactionManager")
	public String paymentCheckReturn(PurchaseOrederDataBean purchaseOrederDataBean)
	{	
		System.out.println("payment check for return dao -- ");
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from Invoice where purchase_ID=? and invoiceStatus='Purchase Invoice'");
			v.setParameter(1, purchaseOrederDataBean.getPurchaseid());
			List<Invoice> inv=(List<Invoice>) v.getResultList();
			int invid=0;
			if(inv.size()>0)
			{
				invid=inv.get(0).getInvoice_ID();
				System.out.println("invoice id -- > "+invid);
				v=null;
				v=entitymanager.createQuery("from Payment where invoice_ID=?");
				v.setParameter(1, invid);
				List<Payment> pay=(List<Payment>) v.getResultList();
				int payid=0;
				if(pay.size()>0)
				{
					payid=pay.get(0).getPayment_ID();
					System.out.println("paymnet id - -> "+payid);
					if(pay.get(0).getPaymentStatus().equals("pending"))
					{
						Payment payup1=entitymanager.find(Payment.class, payid);
						payup1.setTotalAmount(purchaseOrederDataBean.getRetamount());
						entitymanager.merge(payup1);	
						v=null;
						v=entitymanager.createQuery("from Payment where invoice_ID=?");
						v.setParameter(1, invid);
						List<Payment> pay1=(List<Payment>) v.getResultList();
						int payid1=0;
						if(pay1.size()>0)
						{
							if(Integer.parseInt(pay1.get(0).getTotalAmount())<=Integer.parseInt(purchaseOrederDataBean.getRetamount()))
							{	
								System.out.println("total amount less -- ");
								if(Integer.parseInt(pay1.get(0).getPaidAmount())>Integer.parseInt(pay1.get(0).getTotalAmount()))
								{
									Payment payup=entitymanager.find(Payment.class, payid);
									payup.setTotalAmount(purchaseOrederDataBean.getRetamount());
									payup.setPaymentStatus("paid");
									entitymanager.merge(payup);	
								}		
								else
								{
									Payment payup=entitymanager.find(Payment.class, payid);
									payup.setTotalAmount(purchaseOrederDataBean.getRetamount());
									entitymanager.merge(payup);	
								}
							}
							else if(Integer.parseInt(pay1.get(0).getTotalAmount())>Integer.parseInt(purchaseOrederDataBean.getRetamount()))
							{	
								System.out.println("total amount greater -- ");
								if(Integer.parseInt(pay1.get(0).getPaidAmount())>Integer.parseInt(purchaseOrederDataBean.getRetamount()))
								{
									Payment payup=entitymanager.find(Payment.class, payid);
									payup.setPaymentStatus("paid");
									payup.setTotalAmount(purchaseOrederDataBean.getRetamount());
									entitymanager.merge(payup);	
								}
								else
								{
									Payment payup=entitymanager.find(Payment.class, payid);
									payup.setTotalAmount(purchaseOrederDataBean.getRetamount());
									entitymanager.merge(payup);	
								}
							}
						}						
					}
					else if(pay.get(0).getPaymentStatus().equals("paid"))
					{
						Payment payup=entitymanager.find(Payment.class, payid);
						payup.setTotalAmount(purchaseOrederDataBean.getRetamount());
						entitymanager.merge(payup);						
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

	public String purchaseReturnView(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		Query v=null;
		ArrayList<PurchaseOrederDataBean> litss=new ArrayList<PurchaseOrederDataBean>();
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderDate between ? and ? and status2='Stocked In' and (status='ordered' or status='Return')");
			v.setParameter(1, purchaseOrederDataBean.getPurFromDate());
			v.setParameter(2, purchaseOrederDataBean.getPurToDate());
			List<Purchase> pur=(List<Purchase>)v.getResultList();
			if(pur.size()>0)
			{

				for (int i = 0; i < pur.size(); i++) 
				{
					v=null;
					v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
					v.setParameter(1, pur.get(i).getPurchase_ID());
					List<PurchaseRecord> salrec=(List<PurchaseRecord>) v.getResultList();
					if(salrec.size()>0)
					{
						int c=0;
						for (int j = 0; j < salrec.size(); j++)
						{							
							v=null;
							v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
							v.setParameter(1, salrec.get(j).getPurchase_record_ID());
							List<PurchaseReturn> salret=(List<PurchaseReturn>) v.getResultList();
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
							PurchaseOrederDataBean list=new PurchaseOrederDataBean();
							list.setPurdate(pur.get(i).getPurchaseOrderDate());
							list.setPurOrderNo(pur.get(i).getPurchaseOrderNumber());
							list.setPurVendor(pur.get(i).getVendor().getFirmName());
							list.setPurTotalPrice(pur.get(i).getTotalAmount());
							litss.add(list);
							purchaseOrederDataBean.setPurchaseList(litss);	
						}
						else
						{
							System.out.println("inside  else add -- <> ");
							purchaseOrederDataBean.setPurchaseList(litss);	
						}
					}
				}
				System.out.println("size - >"+purchaseOrederDataBean.getPurchaseList().size());
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";	
	}
	
	public String purchaseReturnViewRecord(PurchaseOrederDataBean purchaseOrederDataBean)
	{
		System.out.println("inside purchase return view dao -- ");
		Query v=null;
		ArrayList<PurchaseOrederDataBean> list=new ArrayList<PurchaseOrederDataBean>();
		try
		{
			v=entitymanager.createQuery("from Purchase where purchaseOrderNumber=?");
			v.setParameter(1, purchaseOrederDataBean.getPurOrderNo());
			List<Purchase> sales=(List<Purchase>)v.getResultList();
			if(sales.size()>0)
			{
				v=null;
				v=entitymanager.createQuery("from PurchaseRecord where purchase_ID=?");
				v.setParameter(1, sales.get(0).getPurchase_ID());
				List<PurchaseRecord> salrec=(List<PurchaseRecord>) v.getResultList();
				if(salrec.size()>0)
				{
					int c=1;
					for (int j = 0; j < salrec.size(); j++)
					{							
						v=null;
						v=entitymanager.createQuery("from PurchaseReturn where purchase_record_ID=?");
						v.setParameter(1, salrec.get(j).getPurchase_record_ID());
						List<PurchaseReturn> salret=(List<PurchaseReturn>) v.getResultList();
						if(salret.size()>0)
						{
							System.out.println("inside add -- <> ");
							PurchaseOrederDataBean returns=new PurchaseOrederDataBean();
							if(sales.get(0).getCategory().equals("Product Trading"))
							{
								returns.setPurProductName(salret.get(0).getPurchaseRecord().getProduct().getProductName());
							}
							else if(!sales.get(0).getCategory().equals("Product Trading"))
							{
								returns.setPurProductName(salret.get(0).getPurchaseRecord().getRawMaterial().getProductName());
							}							
							returns.setReturnqauntity(salret.get(0).getNormalReturn());
							returns.setPurprice(salret.get(0).getPurchaseRecord().getUnitPrice());
							returns.setDr(salret.get(0).getDamageReturn());
							returns.setPurQuantity(salret.get(0).getPurchaseRecord().getQuantity());
							returns.setPurserialno(""+c);
							list.add(returns);
							purchaseOrederDataBean.setPurchaseViewList(list);
							purchaseOrederDataBean.setPurdate(sales.get(0).getPurchaseOrderDate());
							purchaseOrederDataBean.setPurOrderNo(sales.get(0).getPurchaseOrderNumber());
							purchaseOrederDataBean.setPurVendor(sales.get(0).getVendor().getFirmName());
							purchaseOrederDataBean.setPurTotalPrice(sales.get(0).getTotalAmount());
							purchaseOrederDataBean.setPurCategory(sales.get(0).getCategory());
							purchaseOrederDataBean.setPurEstDate(sales.get(0).getPurchaseEstimatedDate());
							purchaseOrederDataBean.setPurStaffName(sales.get(0).getEmployee().getEmployeeName());
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
}