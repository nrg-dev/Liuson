package com.nrg.liusen.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.neo4j.cypher.internal.compiler.v2_1.docbuilders.internalDocBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nrg.liusen.domain.StockDataBean;
import com.nrg.liusen.domain.StockOutManualDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.shared.Barcode;
import com.nrg.liusen.shared.Batch;
import com.nrg.liusen.shared.Customer;
import com.nrg.liusen.shared.Employee;
import com.nrg.liusen.shared.Limit;
import com.nrg.liusen.shared.Product;
import com.nrg.liusen.shared.ProductLimit;
import com.nrg.liusen.shared.Projet;
import com.nrg.liusen.shared.Purchase;
import com.nrg.liusen.shared.PurchaseApproval;
import com.nrg.liusen.shared.PurchaseRecord;
import com.nrg.liusen.shared.RawMaterial;
import com.nrg.liusen.shared.Sale;
import com.nrg.liusen.shared.SalesApproval;
import com.nrg.liusen.shared.SalesRecord;
import com.nrg.liusen.shared.Stock;
import com.nrg.liusen.shared.StockDamage;
import com.nrg.liusen.shared.StockoutManual;
import com.nrg.liusen.shared.StockoutRecored;
import com.nrg.liusen.shared.Stockoutapproval;
import com.nrg.liusen.shared.Vendor;

@Repository
@Singleton
public class StockDaoImpl implements StockDao{

	@PersistenceContext(unitName="liusen-pu")
	private EntityManager entitymanager;
	
	
	
	@Override
	public List<String> getPurchaseOrderNo() {
		List<String> orderList=null;
		Query q= null;	Query q1= null;
		String res1="";
		try{
			/*q=entitymanager.createQuery("select purchaseOrderNumber from Purchase where (status=? and deliveryStatus='Approve' and status2='waiting')");
			q.setParameter(1, "ordered");
			orderList=(List<String>)q.getResultList();
			System.out.println(orderList);
			
			if(orderList.size() > 0){
				q1=entitymanager.createQuery("from ");
			}*/
			q=entitymanager.createQuery("from PurchaseApproval where status2=?");
			q.setParameter(1, "Approved");
			List<PurchaseApproval> res= (List<PurchaseApproval>)q.getResultList();
			System.out.println("Result"+res);
			if(res.size() > 0){
				orderList=new ArrayList<String>();
				for(int i=0;i<res.size() ; i++){
					
					q1=entitymanager.createQuery("from Purchase where (purchase_ID=? and status2='waiting')");
					q1.setParameter(1, res.get(i).getPurchase().getPurchase_ID());
					List<Purchase> result =(List<Purchase>)q1.getResultList();
					
					if(result.size() > 0){
						res1=result.get(0).getPurchaseOrderNumber();
						System.out.println(res1);
						orderList.add(res1);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return orderList;
	}



	@Override
	public List<Purchase> getPurchaseOrderDetails(StockDataBean stockDataBean) {
		System.out.println("Inside getPurchaseOrderDetails method calling in Dao ");
		List<Purchase> orderList=null;
		Query order= null;
		
		try{
			if(stockDataBean.getStcokOrderNumber1() != null){
				order=entitymanager.createQuery("from Purchase where (purchaseOrderNumber=? and status=?)");
				order.setParameter(1, stockDataBean.getStcokOrderNumber1());
				order.setParameter(2, "ordered");
				orderList=(List<Purchase>)order.getResultList();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return orderList;
	}


	/**
	 * @author Robert Arjun
	 * @Date 03-12-2015
	 * @copyright NRG
	 * This Method is used for Insert Stcok Quantity in Batch, Barcode and Stock table
	 */

	@Override
	@Transactional(value="transactionManager")
	public String insertStock(StockDataBean stockDataBean) {
		String s="Failure";
		int purchase_ID = 0;
		int stock_ID = 0;
		String product_Name = "";
		int vendor_ID = 0;
		int batch_ID = 0;
		int bar_ID = 0;
		BigDecimal StocInQty = new BigDecimal("0");
		BigDecimal TotalStockQty= new BigDecimal("0");
		List<Purchase> purchase=null;
		List<Barcode> barList=null;
		int purchaseRecordIDSize=0;
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");// get the user roll
		Date date=new Date();
		Timestamp timestamp=new Timestamp(date.getTime());
		try{
			if(stockDataBean.getStcokOrderNumber() != null){
				purchase_ID=getPurchaseID(stockDataBean.getStcokOrderNumber());// this method is used for get purchase id 
				//If Purchase ID is greater than Zero insert to Stock table
				if(purchase_ID > 0){
				Stock stock = new Stock();
				stock.setPurchase(entitymanager.find(Purchase.class, purchase_ID));
				stock.setLoginStatus(login);
				stock.setOrderDate(stockDataBean.getStockOrderDate());
				stock.setStatus("Active");
				stock.setStockInDate(date);
				stock.setStockInTime(timestamp);
				entitymanager.persist(stock);
				}
				
				stock_ID=getStockId(purchase_ID); // get Stock Id
				 
				if(stock_ID > 0){
					purchase=getPurchaseOrderDetails(stockDataBean);
					if(purchase.size() > 0){
						purchaseRecordIDSize=getpurchaseRecordSize(purchase_ID);
						if(purchaseRecordIDSize > 0){
							if(purchase.get(0).getCategory().equalsIgnoreCase("Product Trading")){
							for(int i=0; i<purchaseRecordIDSize;i++){
								product_Name=purchase.get(0).getPurchaseRecords().get(i).getProduct().getProductName();
								vendor_ID=purchase.get(0).getVendor().getVendor_ID();
								batch_ID=getBatchIDByName(product_Name);
								//if Product ID is same Merge to Batch table
								if(batch_ID > 0)
								{
									System.out.println("Inside if in Batch");
									if(vendor_ID > 0){
										System.out.println("Inside if in Batch 1");
										Batch batch1= entitymanager.find(Batch.class, batch_ID);
										batch1.setLoginStatus(login);
										batch1.setStatus("Active");
										batch1.setStock(entitymanager.find(Stock.class, stock_ID));
										batch1.setUnit(purchase.get(0).getPurchaseRecords().get(i).getProduct().getUnit());
										batch1.setUnitPrice(purchase.get(0).getPurchaseRecords().get(i).getProduct().getActualPrice());
										batch1.setCategory(purchase.get(0).getCategory());
										batch1.setProductName(purchase.get(0).getPurchaseRecords().get(i).getProduct().getProductName());
										batch1.setVendor(entitymanager.find(Vendor.class, vendor_ID));
										entitymanager.merge(batch1);
								}
								}else{
									//if Product ID is not same Insert to Batch table
									System.out.println("Inside Else in Batch");
								if(vendor_ID > 0){
									System.out.println("Inside Else in Batch 1");
								Batch batch= new Batch();
								batch.setLoginStatus(login);
								batch.setStatus("Active");
								batch.setStock(entitymanager.find(Stock.class, stock_ID));
								batch.setUnit(purchase.get(0).getPurchaseRecords().get(i).getProduct().getUnit());
								batch.setUnitPrice(purchase.get(0).getPurchaseRecords().get(i).getProduct().getActualPrice());
								batch.setCategory(purchase.get(0).getCategory());
								batch.setProductName(purchase.get(0).getPurchaseRecords().get(i).getProduct().getProductName());
								batch.setVendor(entitymanager.find(Vendor.class, vendor_ID));
								entitymanager.persist(batch);
								entitymanager.flush();
								entitymanager.clear();
								}
								
								}
								batch_ID=getBatchIDByName(product_Name);
								/*bar_ID=getBarcodeID(batch_ID);*/
								barList=getBarcodeList(batch_ID);
								if(barList.size() > 0){
									//if Batch ID is  same Merge to Barcode table
									bar_ID=barList.get(0).getBarcode_ID();
									StocInQty=new BigDecimal(barList.get(0).getStockIn());
									if(bar_ID > 0){
										TotalStockQty=StocInQty.add(new BigDecimal(purchase.get(0).getPurchaseRecords().get(i).getQuantity()));
										Barcode barcode = entitymanager.find(Barcode.class, bar_ID);
										barcode.setBatch(entitymanager.find(Batch.class, batch_ID));
										barcode.setStatus("Barcode Generated");
										barcode.setStockIn(TotalStockQty.toString());
										entitymanager.merge(barcode);
										entitymanager.flush();
										entitymanager.clear();
									}
								}else{
									//if Batch ID is not same Insert to Barcode table
								if(batch_ID > 0){
									Barcode barcode = new Barcode();
									barcode.setBatch(entitymanager.find(Batch.class, batch_ID));
									barcode.setStatus("Barcode Generated");
									barcode.setStockOut("0");
									barcode.setStockIn(purchase.get(0).getPurchaseRecords().get(i).getQuantity());
									entitymanager.persist(barcode);
									entitymanager.flush();
									entitymanager.clear();
									s="Inserted";
								}
								}
								
							}
							Purchase purchase2= entitymanager.find(Purchase.class, purchase_ID);
							purchase2.setStatus2("Stocked In");
							entitymanager.merge(purchase2);
							
							
						}else if(purchase.get(0).getCategory().equalsIgnoreCase("Raw Material") || purchase.get(0).getCategory().equalsIgnoreCase("Service")){
								for(int j=0; j<purchaseRecordIDSize;j++){
									product_Name="";
									product_Name=purchase.get(0).getPurchaseRecords().get(j).getRawMaterial().getProductName();
									vendor_ID=purchase.get(0).getVendor().getVendor_ID();
									batch_ID=getBatchIDByName(product_Name);
									System.out.println("Name"+product_Name);
									
									System.out.println("Vendor"+vendor_ID);
									
									System.out.println("Batch"+batch_ID);
									//if Product ID is same Merge to Batch table
									if(batch_ID > 0)
									{
										System.out.println("Inside if in Batch");
										if(vendor_ID > 0){
											System.out.println("Inside if in Batch 1");
											Batch batch2= entitymanager.find(Batch.class, batch_ID);
											batch2.setLoginStatus(login);
											batch2.setStatus("Active");
											batch2.setStock(entitymanager.find(Stock.class, stock_ID));
											batch2.setUnit(purchase.get(0).getPurchaseRecords().get(j).getRawMaterial().getUnit());
											batch2.setUnitPrice(purchase.get(0).getPurchaseRecords().get(j).getRawMaterial().getActualPrice());
											batch2.setCategory(purchase.get(0).getCategory());
											batch2.setProductName(purchase.get(0).getPurchaseRecords().get(j).getRawMaterial().getProductName());
											batch2.setVendor(entitymanager.find(Vendor.class, vendor_ID));
											entitymanager.merge(batch2);
											entitymanager.flush();
											entitymanager.clear();
									}
									}else{
										//if Product ID is not same Insert to Batch table
										System.out.println("Inside Else in Batch");
									if(vendor_ID > 0){
										System.out.println("Inside Else in Batch 1");
										System.out.println(purchase.get(0).getPurchaseRecords().get(j).getRawMaterial().getUnit());
										System.out.println(purchase.get(0).getPurchaseRecords().get(j).getRawMaterial().getProductName());
									Batch batch3= new Batch();
									batch3.setLoginStatus(login);
									batch3.setStatus("Active");
									batch3.setStock(entitymanager.find(Stock.class, stock_ID));
									batch3.setUnit(purchase.get(0).getPurchaseRecords().get(j).getRawMaterial().getUnit());
									batch3.setUnitPrice(purchase.get(0).getPurchaseRecords().get(j).getRawMaterial().getActualPrice());
									batch3.setCategory(purchase.get(0).getCategory());
									batch3.setProductName(purchase.get(0).getPurchaseRecords().get(j).getRawMaterial().getProductName());
									batch3.setVendor(entitymanager.find(Vendor.class, vendor_ID));
									entitymanager.persist(batch3);
									entitymanager.flush();
									entitymanager.clear();
									}
									
									}
									batch_ID=getBatchIDByName(product_Name);
									System.out.println("------"+batch_ID);
									System.out.println("");
									/*bar_ID=getBarcodeID(batch_ID);*/
									barList=getBarcodeList(batch_ID);
									System.out.println("-----"+barList);
									if(barList.size() > 0){
										//if Batch ID is  same Merge to Barcode table
										bar_ID=barList.get(0).getBarcode_ID();
										StocInQty=new BigDecimal(barList.get(0).getStockIn());
										if(bar_ID > 0){
											TotalStockQty=StocInQty.add(new BigDecimal(purchase.get(0).getPurchaseRecords().get(j).getQuantity()));
											Barcode barcode = entitymanager.find(Barcode.class, bar_ID);
											barcode.setBatch(entitymanager.find(Batch.class, batch_ID));
											barcode.setStatus("Barcode Generated");
											barcode.setStockIn(TotalStockQty.toString());
											entitymanager.merge(barcode);
											entitymanager.flush();
											entitymanager.clear();
										}
									}else{
										//if Batch ID is not same Insert to Barcode table
									if(batch_ID > 0){
										Barcode barcode = new Barcode();
										barcode.setBatch(entitymanager.find(Batch.class, batch_ID));
										barcode.setStatus("Barcode Generated");
										barcode.setStockOut("0");
										barcode.setStockIn(purchase.get(0).getPurchaseRecords().get(j).getQuantity());
										entitymanager.persist(barcode);
										entitymanager.flush();
										entitymanager.clear();
										s="Inserted";
									}
									}
									
								
								
							}
								Purchase purchase2= entitymanager.find(Purchase.class, purchase_ID);
								purchase2.setStatus2("Stocked In");
								entitymanager.merge(purchase2);
						}else{
							System.out.println("Error");
						}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return s;
	}



	



	private int getpurchaseRecordSize(int purchase_ID) {
		Query q= null;
		int res=0;
		try{
			q=entitymanager.createQuery("from PurchaseRecord where purchase_ID= ?");
			q.setParameter(1, purchase_ID);
			List<PurchaseRecord> result=(List<PurchaseRecord>)q.getResultList();
			if(result.size() > 0){
				res=result.size();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}



	/**
	 * @author Robert Arjun
	 * @Date 03-12-2015
	 * @copyright NRG
	 * This Method is used for return BarcodeList
	 */

	private List<Barcode> getBarcodeList(int batch_ID) {
		 List<Barcode> res=null;
		Query q= null;
		try{
			q=entitymanager.createQuery("from Barcode where batch_ID= ?");
			q.setParameter(1, batch_ID);
			res=(List<Barcode>)q.getResultList();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}



	/**
	 * @author Robert Arjun
	 * @Date 03-12-2015
	 * @copyright NRG
	 * This Method is used for return BarcodeID
	 */

	private int getBarcodeID(int batch_ID) {
		int res=0;
		Query q= null;
		try{
			q=entitymanager.createQuery("from Barcode where batch_ID= ? and status='Barcode Generated'");
			q.setParameter(1, batch_ID);
			List<Barcode> result=(List<Barcode>)q.getResultList();
			if(result.size() > 0){
				res=result.get(0).getBarcode_ID();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}



	/**
	 * @author Robert Arjun
	 * @Date 03-12-2015
	 * @copyright NRG
	 * This Method is used for return BatchID 
	 */

	private int getBatchID(int product_ID) {
		int res=0;
		Query q= null;
		try{
			q=entitymanager.createQuery("from Batch where product_ID= ? and status='Active'");
			q.setParameter(1, product_ID);
			List<Batch> result=(List<Batch>)q.getResultList();
			if(result.size() > 0){
				res=result.get(0).getBatch_ID();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}



	/**
	 * @author Robert Arjun
	 * @Date 03-12-2015
	 * @copyright NRG
	 * This Method is used for  return  StockId
	 */

	private int getStockId(int purchase_ID) {
		int res=0;
		Query q= null;
		try{
			q=entitymanager.createQuery("from Stock where purchase_ID= ? and status='Active'");
			q.setParameter(1, purchase_ID);
			List<Stock> result=(List<Stock>)q.getResultList();
			if(result.size() > 0){
				res=result.get(0).getStock_ID();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}



	/**
	 * @author Robert Arjun
	 * @Date 03-12-2015
	 * @copyright NRG
	 * This Method is used for return PurchaseID
	 */

	private int getPurchaseID(String stcokOrderNumber) {
		int res=0;
		Query q= null;
		try{
			q=entitymanager.createQuery("from Purchase where purchaseOrderNumber= ? and status='ordered'");
			q.setParameter(1, stcokOrderNumber);
			List<Purchase> result=(List<Purchase>)q.getResultList();
			if(result.size() > 0){
				res=result.get(0).getPurchase_ID();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}



	@Override
	public List<String> getStockProductName() {
		List<String> productList=null;
		Query q=null;
		try{
			q= entitymanager.createQuery("select productName from Batch where status='Active'");
			productList=(List<String>)q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return productList;
	}



	@Override
	public List<String> getvendorName() {
		List<String> vendorList=null;
		String temp= "";
		Query q=null;
		try{
			q= entitymanager.createQuery("from Batch where status='Active'");
			List<Batch> result=(List<Batch>)q.getResultList();
			if(result.size() > 0){
				vendorList=new ArrayList<String>();
				for(int i=0; i< result.size(); i++){
					String s=result.get(i).getVendor().getFirmName();
					if(s.equalsIgnoreCase(temp)){
						
					}else{
					vendorList.add(s);
				}
					temp=s;
			}
				HashSet<String> ss=new HashSet<String>(vendorList);
				vendorList.clear();
				vendorList.addAll(ss);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return vendorList;
	}



	@Override
	public List<StockDataBean> getStockInList(StockDataBean stockDataBean)
	{		
		System.out.println("Inside getStockInList method Calling...");		
		List<StockDataBean> stock=null;
		List<RawMaterial> rawProduct=null;
		List<Product> nonrawproduct= null;
		int batch_ID=0;
		Query q= null;
		try
		{
			if(stockDataBean.getStockProductName() != null)
			{
				batch_ID=getBatchIDByName(stockDataBean.getStockProductName());
				if(batch_ID > 0)
				{
					q= entitymanager.createQuery("from Barcode where batch_ID= ? and status='Barcode Generated'");
					q.setParameter(1, batch_ID);
					List<Barcode> result= (List<Barcode>)q.getResultList();
					if(result.size()>0)
					{
						stock=new ArrayList<StockDataBean>();
						for(Barcode res : result)
						{
							StockDataBean stockdata=new StockDataBean();
							System.out.println("product -- "+res.getBatch().getProductName());
							if(res.getBatch().getCategory() != null)
							{
								System.out.println("categoryy -- ");
								if(res.getBatch().getCategory().equalsIgnoreCase("Raw Material") || res.getBatch().getCategory().equalsIgnoreCase("Service"))
								{
									System.out.println("category -- > "+res.getBatch().getCategory());
									rawProduct=getRawList(res.getBatch().getProductName());
									if(rawProduct.size() > 0)
									{
										stockdata.setStockProductName(rawProduct.get(0).getProductName());
										stockdata.setStockFirmName(rawProduct.get(0).getVendor().getFirmName());
										stockdata.setStockCategory(rawProduct.get(0).getCategory());
										stockdata.setStockInDate(res.getBatch().getStock().getStockInDate());
										stockdata.setStockinQty(res.getStockIn());
										stockdata.setStockUnitPrice(res.getBatch().getUnitPrice());
										stockdata.setStockUnit(res.getBatch().getUnit());
									}	
									else
									{
										stockdata.setStockInDate(res.getBatch().getStock().getStockInDate());
										stockdata.setStockinQty(res.getStockIn());
										stockdata.setStockUnitPrice(res.getBatch().getUnitPrice());
										stockdata.setStockUnit(res.getBatch().getUnit());
										stockdata.setStockProductName(res.getBatch().getProductName());
										stockdata.setStockFirmName(res.getBatch().getVendor().getFirmName());
										stockdata.setStockCategory(res.getBatch().getCategory());	
									}
								}
								else if(res.getBatch().getCategory().equalsIgnoreCase("Product Trading"))
								{
									System.out.println("category -- 1 > "+res.getBatch().getCategory());
									nonrawproduct=getProductList(res.getBatch().getProductName());
									if(nonrawproduct.size() > 0)
									{
										stockdata.setStockProductName(nonrawproduct.get(0).getProductName());
										stockdata.setStockFirmName(nonrawproduct.get(0).getVendor().getFirmName());
										stockdata.setStockCategory(nonrawproduct.get(0).getCategory());
										stockdata.setStockInDate(res.getBatch().getStock().getStockInDate());
										stockdata.setStockinQty(res.getStockIn());
										stockdata.setStockUnitPrice(res.getBatch().getUnitPrice());
										stockdata.setStockUnit(res.getBatch().getUnit());
									}
									else
									{
										stockdata.setStockInDate(res.getBatch().getStock().getStockInDate());
										stockdata.setStockinQty(res.getStockIn());
										stockdata.setStockUnitPrice(res.getBatch().getUnitPrice());
										stockdata.setStockUnit(res.getBatch().getUnit());
										stockdata.setStockProductName(res.getBatch().getProductName());
										stockdata.setStockFirmName(res.getBatch().getVendor().getFirmName());
										stockdata.setStockCategory(res.getBatch().getCategory());										
									}
								}
								else
								{
									
								}
								stock.add(stockdata);
								System.out.println("stock size 2 > "+stock.size());
							}
							else
							{
								System.out.println("no category - > ");
							}
							System.out.println("stock size -1 > "+stock.size());
						}
						System.out.println("stock size - > "+stock.size());
					}
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return stock;
	}
	private List<Product> getProductList(String productName) {
		System.out.println("Inside getProductList method calling");
		List<Product> res=null;
		Query q=null;
		try{
			q=entitymanager.createQuery("from Product where productName=? and status='Active')");
			q.setParameter(1, productName);
			res=(List<Product>)q.getResultList();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}



	private List<RawMaterial> getRawList(String productName) {
		System.out.println("Inside getRawList method calling..");
		List<RawMaterial> res=null;
		Query q=null;
		try{
			q=entitymanager.createQuery("from RawMaterial where productName=? and status='Active')");
			q.setParameter(1, productName);
			res=(List<RawMaterial>)q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}



	/**
	 * @author Robert Arjun
	 * @Date 03-12-2015
	 * @copyright NRG
	 * This Method is used for return BatchID depends on product name
	 */

	private int getBatchIDByName(String name) {
		int res=0;
		Query q= null;
		try{
			q=entitymanager.createQuery("from Batch where productName= ? and status='Active'");
			q.setParameter(1, name);
			List<Batch> result=(List<Batch>)q.getResultList();
			if(result.size() > 0){
				res=result.get(0).getBatch_ID();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}



	@Override
	public List<StockDataBean> getStockInListByVendor(
			StockDataBean stockDataBean) {
		System.out.println("Inside getStockInListByVendor method calling...");
		int ven_ID=0;
		int batch_ID = 0;
		Query q= null;
		List<StockDataBean> stock=new ArrayList<StockDataBean>();
		List<RawMaterial> rawProduct=null;
		List<Product> nonrawproduct= null;
		List<Batch> batchList=null;
		try
		{
			if(stockDataBean.getStockFirmName() != null)
			{
				ven_ID=getVendorID(stockDataBean.getStockFirmName());
				System.out.println("Vendor Id "+ven_ID);
				if(ven_ID > 0)
				{
					batchList=getBatchListByVendorID(ven_ID);
					if(batchList.size() > 0)
					{
						for(int i=0; i<batchList.size() ; i++)
						{
							q= entitymanager.createQuery("from Barcode where batch_ID= ? and status='Barcode Generated'");
							q.setParameter(1, batchList.get(i).getBatch_ID());
							List<Barcode> result= (List<Barcode>)q.getResultList();
							if(result.size()>0)
							{
								
								StockDataBean stockdata=new StockDataBean();
								System.out.println("product -- "+result.get(0).getBatch().getProductName());
								if(result.get(0).getBatch().getCategory() != null)
								{
									System.out.println("ctaegoryy -- ");
									if(result.get(0).getBatch().getCategory().equalsIgnoreCase("Raw Material") || result.get(0).getBatch().getCategory().equalsIgnoreCase("Service"))
									{
										System.out.println("category - 1 "+result.get(0).getBatch().getCategory());
										rawProduct=getRawList(result.get(0).getBatch().getProductName());
										if(rawProduct.size() > 0)
										{
											stockdata.setStockProductName(rawProduct.get(0).getProductName());
											stockdata.setStockFirmName(rawProduct.get(0).getVendor().getFirmName());
											stockdata.setStockCategory(rawProduct.get(0).getCategory());
											stockdata.setStockInDate(result.get(0).getBatch().getStock().getStockInDate());
											stockdata.setStockinQty(result.get(0).getStockIn());
											stockdata.setStockUnitPrice(result.get(0).getBatch().getUnitPrice());
											stockdata.setStockUnit(result.get(0).getBatch().getUnit());												
										}
										else
										{
											stockdata.setStockProductName(result.get(0).getBatch().getProductName());
											stockdata.setStockFirmName(result.get(0).getBatch().getVendor().getFirmName());
											stockdata.setStockCategory(result.get(0).getBatch().getCategory());
											stockdata.setStockInDate(result.get(0).getBatch().getStock().getStockInDate());
											stockdata.setStockinQty(result.get(0).getStockIn());
											stockdata.setStockUnitPrice(result.get(0).getBatch().getUnitPrice());
											stockdata.setStockUnit(result.get(0).getBatch().getUnit());		
										}
										
									}
									else if(result.get(0).getBatch().getCategory().equalsIgnoreCase("Product Trading"))
									{
										System.out.println("category - 2 "+result.get(0).getBatch().getCategory());
										nonrawproduct=getProductList(result.get(0).getBatch().getProductName());
										if(nonrawproduct.size() > 0)
										{
											stockdata.setStockProductName(nonrawproduct.get(0).getProductName());
											stockdata.setStockFirmName(nonrawproduct.get(0).getVendor().getFirmName());
											stockdata.setStockCategory(nonrawproduct.get(0).getCategory());
											stockdata.setStockInDate(result.get(0).getBatch().getStock().getStockInDate());
											stockdata.setStockinQty(result.get(0).getStockIn());
											stockdata.setStockUnitPrice(result.get(0).getBatch().getUnitPrice());
											stockdata.setStockUnit(result.get(0).getBatch().getUnit());												
										}
										else
										{
											stockdata.setStockProductName(result.get(0).getBatch().getProductName());
											stockdata.setStockFirmName(result.get(0).getBatch().getVendor().getFirmName());
											stockdata.setStockCategory(result.get(0).getBatch().getCategory());
											stockdata.setStockInDate(result.get(0).getBatch().getStock().getStockInDate());
											stockdata.setStockinQty(result.get(0).getStockIn());
											stockdata.setStockUnitPrice(result.get(0).getBatch().getUnitPrice());
											stockdata.setStockUnit(result.get(0).getBatch().getUnit());		
										}
									}
									else
									{
										
									}
									stock.add(stockdata);
									System.out.println("stock size - 1 "+stock.size());
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
		return stock;
	}



	private List<Batch> getBatchListByVendorID(int ven_ID) {
		List<Batch> res=null;Query q= null;
		try{
			q=entitymanager.createQuery("from Batch where vendor_ID= ? and status='Active'");
			q.setParameter(1, ven_ID);
			res=(List<Batch>)q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}



	private int getVendorID(String stockFirmName) {
		int res=0;
		Query q= null;
		try{
			q=entitymanager.createQuery("from Vendor where firmName=? and status=?");
			q.setParameter(1, stockFirmName);
			q.setParameter(2, "Active");
			List<Vendor> result= (List<Vendor>)q.getResultList();
			if(result.size() > 0){
				res=result.get(0).getVendor_ID();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}



	@Override
	@Transactional(value="transactionManager")
	public String addDamage(StockDataBean stockDataBean)  throws LiusenException{
		System.out.println("Inside addDamage method Calling...");
		int batch_ID=0;
		int barcode_ID=0;
		List<Barcode> barlist=null;
		Date date=new Date();
		Timestamp timestamp=new Timestamp(date.getTime());
		String login=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user");// get the user roll
		BigDecimal stockIn=BigDecimal.ZERO;
		BigDecimal stockout=BigDecimal.ZERO;
		BigDecimal temp=new BigDecimal("0");
		try
		{
			if(stockDataBean.getStockProductName() != null)
			{
				batch_ID=getBatchIDByName(stockDataBean.getStockProductName());
				if(batch_ID > 0)
				{
					barlist=getBarcodeList(batch_ID);
					if(barlist.size() > 0)
					{
						barcode_ID=barlist.get(0).getBarcode_ID();
						stockIn=new BigDecimal(barlist.get(0).getStockIn()).subtract(new BigDecimal(stockDataBean.getStockDamageQty()));
						stockout=new BigDecimal(barlist.get(0).getStockOut());
						if(Integer.parseInt(barlist.get(0).getStockIn())<Integer.parseInt(stockDataBean.getStockDamageQty()))
						{
							throw new LiusenException("Only "+barlist.get(0).getStockIn()+" Quantities in Stock");
						}
						else
						{
							StockDamage damage= new StockDamage();
							damage.setBatch(entitymanager.find(Batch.class, batch_ID));
							damage.setDaamgeTime(timestamp);
							damage.setDamageDate(date);
							damage.setLoginStatus(login);
							damage.setStatus("Damage");
							damage.setDamageReturn(stockDataBean.getStockDamageQty());
							damage.setPurchase_ID(barlist.get(0).getBatch().getStock().getPurchase().getPurchase_ID());
							damage.setStock_ID(barlist.get(0).getBatch().getStock().getStock_ID());
							damage.setNormalReturn("0");
							entitymanager.persist(damage);
							
							Barcode bar=entitymanager.find(Barcode.class, barcode_ID);
							bar.setStockIn(""+stockIn);
							entitymanager.merge(bar);
						}
					}
				}
			}
		}
		finally
		{
			
		}
		return null;
	}



	@Override
	public List<String> getSalesOrderList(StockOutManualDataBean stockOutManualDataBean) {
		System.out.println("Inside getSalesOrderList method Calling...");
		List<String> res= null;
		Query q= null;
		try{
			q= entitymanager.createQuery("Select salesOrderNumber from Sale where (salesCategory='Project' or salesCategory='Service')");
			res=(List<String>)q.getResultList();
			System.out.println(res);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	public List<String> getProjectDetails(String orderNo,StockOutManualDataBean stockOutManualDataBean)
	{		
		System.out.println("Inside getProjectDetails method calling");
		List<String> productNameList=null;
		Query v= null;
		try
		{
			if(orderNo.equals("Service"))
			{
				System.out.println("inside service -- > ");
				v=entitymanager.createQuery("select productName from RawMaterial where category='Service' and status='Active'");
				productNameList=v.getResultList();
				System.out.println("products service -- > "+productNameList);				
			}
			else if(orderNo.equals("Raw Material"))
			{
				System.out.println("inside raw material -- > ");
				v=entitymanager.createQuery("select productName from RawMaterial where category='Raw Material' and status='Active'");
				productNameList=v.getResultList();
				System.out.println("products raw  -- > "+productNameList);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return productNameList;							
	}


	/*Udhaya 19/12*/
//	retrive the project names from product
	public String getProjectLists(StockOutManualDataBean stockOutManualDataBean)
	{
		Query v=null;
		v=entitymanager.createQuery("select productName from Product where category='Project' and status='Active'");
		List<String> projects=(List<String>)v.getResultList();
		System.out.println("projects for stock sales - > "+projects);
		stockOutManualDataBean.setProjectList(projects);
		return null;
	}
	
//	retrive the customer phone number from customer
	public String customerList(StockOutManualDataBean stockOutManualDataBean)
	{
		Query v=null;
		v=entitymanager.createQuery("select customerPhoneNumber from Customer where status='Active'");
		List<String> customer=(List<String>)v.getResultList();
		System.out.println("customer no for stock sales - > "+customer);
		stockOutManualDataBean.setCustomerlist(customer);
		return null;
	}

//	retrive the employee id from employee
	public String employeeList(StockOutManualDataBean stockOutManualDataBean) 
	{
		Query v=null;
		v=entitymanager.createQuery("select employee_details_ID from Employee where status='Active'");
		List<String> employee=(List<String>)v.getResultList();
		System.out.println("employee id for stock sales - > "+employee);
		stockOutManualDataBean.setEmployeelist(employee);
		return null;
	}

//	retrive the products in dropdown based on category
	public String getproductLimit(StockOutManualDataBean stockOutManualDataBean) 
	{
		System.out.println("product - > "+stockOutManualDataBean.getSoutProductName());
		Query v=null;
		try
		{
			if(stockOutManualDataBean.getSoutCategory().equals("Service"))
			{
				System.out.println("inside service - >");
				v=entitymanager.createQuery("from RawMaterial where productName=? and category='Service' and status='Active'");
				v.setParameter(1, stockOutManualDataBean.getSoutProductName());
				List<RawMaterial> raw=(List<RawMaterial>)v.getResultList();
				int rawid=0;
				if(raw.size()>0)
				{
					rawid=raw.get(0).getRaw_ID();
					System.out.println("raw id -- > "+rawid);
					v=null;
					v=entitymanager.createQuery("from Product where productName=? and category='Project' and status='Active'");
					v.setParameter(1, stockOutManualDataBean.getSoutProjectName());
					List<Product> product=(List<Product>)v.getResultList();
					int productid=0;
					if(product.size()>0)
					{
						productid=product.get(0).getProduct_ID();
						System.out.println("product id -- > "+productid);
						v=null;
						v=entitymanager.createQuery("from ProductLimit where raw_id=? and product_ID=? and status='Active'");
						v.setParameter(1, rawid);
						v.setParameter(2, productid);
						List<ProductLimit> limit=(List<ProductLimit>)v.getResultList();
						if(limit.size()>0)
						{
							stockOutManualDataBean.setSoutLimit(limit.get(0).getLimitSize());
							System.out.println("limit - > "+limit.get(0).getLimitSize());
						}
						else
						{
							stockOutManualDataBean.setSoutLimit("0");
						}
					}					
				}
			}
			else if(stockOutManualDataBean.getSoutCategory().equals("Raw Material"))
			{
				System.out.println("inside raw - >");
				v=entitymanager.createQuery("from RawMaterial where productName=? and category='Raw Material' and status='Active'");
				v.setParameter(1, stockOutManualDataBean.getSoutProductName());
				List<RawMaterial> raw=(List<RawMaterial>)v.getResultList();
				int rawid=0;
				if(raw.size()>0)
				{
					rawid=raw.get(0).getRaw_ID();
					System.out.println("raw id -- > "+rawid);
					v=null;
					v=entitymanager.createQuery("from Product where productName=? and category='Project' and status='Active'");
					v.setParameter(1, stockOutManualDataBean.getSoutProjectName());
					List<Product> product=(List<Product>)v.getResultList();
					int productid=0;
					if(product.size()>0)
					{
						productid=product.get(0).getProduct_ID();
						System.out.println("product id -- > "+productid);
						v=null;
						v=entitymanager.createQuery("from ProductLimit where raw_id=? and product_ID=? and status='Active'");
						v.setParameter(1, rawid);
						v.setParameter(2, productid);
						List<ProductLimit> limit=(List<ProductLimit>)v.getResultList();
						if(limit.size()>0)
						{
							stockOutManualDataBean.setSoutLimit(limit.get(0).getLimitSize());
							System.out.println("limit - > "+limit.get(0).getLimitSize());
						}
						else
						{
							stockOutManualDataBean.setSoutLimit("0");
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
	
	/*quantity check for stock out manual*/
	public String quantityCheckStockOut(StockOutManualDataBean stockOutManualDataBean)
	{
		System.out.println("-- quantity check in stock manual dao -- ");
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from Batch where productName=? and status='Active'");
			v.setParameter(1, stockOutManualDataBean.getSoutProductName());
			List<Batch> batch=(List<Batch>) v.getResultList();
			int batchid=0;
			if(batch.size()>0)
			{
				stockOutManualDataBean.setMessage1("prodcut");
				batchid=batch.get(0).getBatch_ID();
				System.out.println("batch id -- > "+batchid);
				v=null;
				v=entitymanager.createQuery("from Barcode where batch_ID=? and status='Barcode Generated'");
				v.setParameter(1, batchid);
				List<Barcode> barcode=(List<Barcode>) v.getResultList();
				if(barcode.size()>0)
				{
					if(Integer.parseInt(barcode.get(0).getStockIn())<Integer.parseInt(stockOutManualDataBean.getSoutQty()))
					{
						stockOutManualDataBean.setMessage("Exception");
						stockOutManualDataBean.setSoutQty(barcode.get(0).getStockIn());
					}
					else
					{
						stockOutManualDataBean.setMessage("Exception1");					
					}
				}
				else
				{
					System.out.println("product not in stock");
				}
			}
			else
			{
				System.out.println("product not in stock 11 ");
				stockOutManualDataBean.setMessage1("no prodcut");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	@Transactional(value="transactionManager")
	public String stockoutManual(StockOutManualDataBean stockOutManualDataBean) 
	{		
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		System.out.println("ct date -> "+date+" time - > "+time);
		Calendar now= Calendar.getInstance();
		String month=new SimpleDateFormat("MMM").format(now.getTime());
		try
		{
			v=entitymanager.createQuery("from StockoutManual");
			List<StockoutManual> stkout=(List<StockoutManual>)v.getResultList();
			String orderno="";int cc=0;
			if(stkout.size()>0)
			{
				cc=stkout.size()+1;
				if(cc<=9)
				{
					orderno="STO000"+cc+"/"+month+"/"+now.get(Calendar.YEAR);
				}
				else if(cc>=9 && cc<=99)
				{
					orderno="STO00"+cc+"/"+month+"/"+now.get(Calendar.YEAR);
				}
				else if(cc>=99)
				{
					orderno="STO0"+cc+"/"+month+"/"+now.get(Calendar.YEAR);
				}
				System.out.println("order no -- > "+orderno );
			}
			else
			{
				orderno="STO0001"+"/"+month+"/"+now.get(Calendar.YEAR);
			}
			stockOutManualDataBean.setSoutOrederNo(orderno);
			v=null;
			v=entitymanager.createQuery("from Employee where employee_details_ID=? and status='Active'");
			v.setParameter(1, stockOutManualDataBean.getSoutEmployeeID());
			List<Employee> emp=(List<Employee>)v.getResultList();
			int empid=0;
			if(emp.size()>0)
			{
				empid=emp.get(0).getEmployee_ID();
				System.out.println("emp id -- > "+empid);
			}
			v=null;
			/*v=entitymanager.createQuery("from Customer where customerPhoneNumber=? and status='Active'");
			v.setParameter(1, stockOutManualDataBean.getSoutCustomerName());
			List<Customer> customer=(List<Customer>)v.getResultList();
			int cusid=0;
			if(customer.size()>0)
			{
				cusid=customer.get(0).getCustomer_ID();
				System.out.println("customer id -- > "+cusid);
			}*/
			v=entitymanager.createQuery("from Projet where projectName=? and status='Active'");
			v.setParameter(1, stockOutManualDataBean.getSoutProjectnames());
			List<Projet> proj=(List<Projet>)v.getResultList();
			int projectID=0;
			if(proj.size()>0)
			{
				projectID=proj.get(0).getProject_ID();
				System.out.println("project id -- > "+projectID);
			}
			v=null;
			v=entitymanager.createQuery("from Product where productName=? and status='Active'");
			v.setParameter(1, stockOutManualDataBean.getSoutProjectName());
			List<Product> product=(List<Product>)v.getResultList();
			int proid=0;
			if(product.size()>0)
			{
				proid=product.get(0).getProduct_ID();
				System.out.println("produt id -- > "+proid);
			}
			StockoutManual insert=new StockoutManual();
			insert.setCrossTotal(stockOutManualDataBean.getSoutCrossTotal());
			insert.setTotalAmount(""+(new BigDecimal(stockOutManualDataBean.getSoutTotalAmount()).setScale(0,BigDecimal.ROUND_HALF_UP)));
			insert.setTaxType(stockOutManualDataBean.getSoutTaxType());
			insert.setStockoutOrderNumber(orderno);
			insert.setStockoutOrderDate(date);
			insert.setStockoutDate(date);
			insert.setCategory(stockOutManualDataBean.getSoutCategory());
			insert.setLoginStatus(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			insert.setStockoutTime(time);
			insert.setStatus("Active");
			insert.setLimitApprovalStatus("pending");
			insert.setLimitStatus("insert");
			//insert.setCustomer(entitymanager.find(Customer.class, cusid));
			insert.setProjet(entitymanager.find(Projet.class, projectID));
			insert.setEmployee(entitymanager.find(Employee.class, empid));
			insert.setProduct(entitymanager.find(Product.class, proid));
			entitymanager.persist(insert);
			v=null;
			v=entitymanager.createQuery("from StockoutManual");
			List<StockoutManual> stoutmanl=(List<StockoutManual>) v.getResultList();
			int id=0;
			if(stoutmanl.size()>0)
			{
				id=stoutmanl.get(stoutmanl.size()-1).getStock_out_ID();
				System.out.println("stock out id - > "+id);
				stockOutManualDataBean.setStockoutid(id);
			}
			
			Stockoutapproval approve=new Stockoutapproval();
			approve.setStockoutManual(entitymanager.find(StockoutManual.class,id));
			approve.setApproval_status_PM("pending");
			approve.setApproval_status_GM("pending");
			approve.setApproval_login_GM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			approve.setApproval_login_PM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			approve.setApproved_date_GM(date);
			approve.setApproved_date_PM(date);
			approve.setApproved_reject_date_GM(""+date);
			approve.setApproved_reject_date_PM(""+date);
			approve.setApproved_reject_GM("pending");
			approve.setApproved_reject_PM("pending");
			approve.setApprovedDate(date);
			approve.setApprovalStatus("pending");
			approve.setApprovedLogin(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
			approve.setApprovedRejectStatus("pending");
			approve.setApprovedTime(time);
			entitymanager.persist(approve);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional(value="transactionManager")
	public String stockoutRecord(StockOutManualDataBean stockOutManualDataBean, int i)
	{
		System.out.println("insert into stock out record ");
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from Batch where productName=? and status='Active' and category=?");
			v.setParameter(1, stockOutManualDataBean.getStockOutList().get(i).getSoutProductName1());
			v.setParameter(2, stockOutManualDataBean.getSoutCategory());
			System.out.println("product - > "+stockOutManualDataBean.getStockOutList().get(i).getSoutProductName1()+" category - > "+stockOutManualDataBean.getSoutCategory());
			List<Batch> batch=(List<Batch>) v.getResultList();
			int batchid=0;
			if(batch.size()>0)
			{
				batchid=batch.get(0).getBatch_ID();
				System.out.println("batch id - > "+batchid);
				v=null;
				v=entitymanager.createQuery("from Barcode where batch_ID=? and status='Barcode Generated'");
				v.setParameter(1, batchid);
				List<Barcode> barcode=(List<Barcode>) v.getResultList();
				int barcodeid=0;
				if(barcode.size()>0)
				{
					barcodeid=barcode.get(0).getBarcode_ID();
					System.out.println("barcode id - > "+barcodeid);
					int stkid=stockOutManualDataBean.getStockoutid();
					System.out.println(" stk id - > "+stkid);
					StockoutRecored stkoutrec=new StockoutRecored();
					stkoutrec.setSoldQuantity(stockOutManualDataBean.getStockOutList().get(i).getSoutQty());
					stkoutrec.setNetAmount(stockOutManualDataBean.getStockOutList().get(i).getSoutNetAmount());
					stkoutrec.setUnitPrice(stockOutManualDataBean.getStockOutList().get(i).getSoutUnitPrice());
					stkoutrec.setStatus("solded");
					stkoutrec.setDamageQuantity("0");
					stkoutrec.setDamageStatus("not damaged");
					stkoutrec.setBarcode(entitymanager.find(Barcode.class, barcodeid));
					stkoutrec.setStockoutManual(entitymanager.find(StockoutManual.class, stkid));					
					entitymanager.persist(stkoutrec);
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
	public String stockOutupdate(StockOutManualDataBean stockOutManualDataBean)
	{
		System.out.println("update status in stock out manual");
		StockoutManual stkup=entitymanager.find(StockoutManual.class,stockOutManualDataBean.getStockoutid());
		stkup.setLimitApprovalStatus("Waiting");
		entitymanager.merge(stkup);
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String stockoutBarcode(StockOutManualDataBean stockOutManualDataBean, int i)
	{
		System.out.println("update stock in barcode");
		Query v=null;
		try 
		{
			v=entitymanager.createQuery("from Batch where productName=? and status='Active' and category=?");
			v.setParameter(1, stockOutManualDataBean.getStockOutList().get(i).getSoutProductName1());
			v.setParameter(2, stockOutManualDataBean.getSoutCategory());
			System.out.println("product - > "+stockOutManualDataBean.getStockOutList().get(i).getSoutProductName1()+" category - > "+stockOutManualDataBean.getSoutCategory());
			List<Batch> batch=(List<Batch>) v.getResultList();
			int batchid=0;
			if(batch.size()>0)
			{
				batchid=batch.get(0).getBatch_ID();
				System.out.println("batch id - > "+batchid);
				v=null;
				v=entitymanager.createQuery("from Barcode where batch_ID=? and status='Barcode Generated'");
				v.setParameter(1, batchid);
				List<Barcode> barcode=(List<Barcode>) v.getResultList();
				int barcodeid=0;
				if(barcode.size()>0)
				{
					barcodeid=barcode.get(0).getBarcode_ID();
					System.out.println("barcode id - > "+barcodeid);
					Barcode upstk=entitymanager.find(Barcode.class, barcodeid);
					upstk.setStockIn(""+(new BigDecimal(barcode.get(0).getStockIn()).subtract(new BigDecimal(stockOutManualDataBean.getStockOutList().get(i).getSoutQty()))));
					upstk.setStockOut(""+(new BigDecimal(barcode.get(0).getStockOut()).add(new BigDecimal(stockOutManualDataBean.getStockOutList().get(i).getSoutQty()))));
					entitymanager.merge(upstk);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	@Transactional(value="transactionManager")
	public String stockOutupdate1(StockOutManualDataBean stockOutManualDataBean)
	{
		System.out.println("update status in stock out manual 1");
		StockoutManual stkup=entitymanager.find(StockoutManual.class,stockOutManualDataBean.getStockoutid());
		stkup.setLimitApprovalStatus("Approved");
		entitymanager.merge(stkup);
		return "";
	}
	
	public int remStockOutManual()
	{
		int c=0;
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from StockoutManual where limitApprovalStatus='Waiting' and status='Active'");
			List<StockoutManual> waiting=(List<StockoutManual>) v.getResultList();
			if(waiting.size()>0)
			{
				for (int i = 0; i < waiting.size(); i++) 
				{
					v=null;
					v=entitymanager.createQuery("from Stockoutapproval where stock_out_ID=? and approval_status_GM='pending'");
					v.setParameter(1, waiting.get(i).getStock_out_ID());
					List<Stockoutapproval> stkoutapp=(List<Stockoutapproval>) v.getResultList();
					if(stkoutapp.size()>0)
					{
						c++;
					}
				}
				System.out.println("in stock out manual limit exceeding size is GM -- > "+c);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return c;
	}
	
	public String stockOutWaitingList(StockOutManualDataBean stockOutManualDataBean)
	{
		Query v=null;
		ArrayList<StockOutManualDataBean> list=new ArrayList<StockOutManualDataBean>();
		try
		{
			v=entitymanager.createQuery("from StockoutManual where limitApprovalStatus='Waiting' and status='Active'");
			List<StockoutManual> waiting=(List<StockoutManual>) v.getResultList();
			System.out.println("stock out waiting approval - > "+waiting.size());
			if(waiting.size()>0)
			{				
				int c=1;
				for (int i = 0; i < waiting.size(); i++) 
				{
					v=null;
					v=entitymanager.createQuery("from Stockoutapproval where stock_out_ID=? and approval_status_GM='pending'");
					v.setParameter(1, waiting.get(i).getStock_out_ID());
					List<Stockoutapproval> stkoutapp=(List<Stockoutapproval>) v.getResultList();
					if(stkoutapp.size()>0)
					{
						StockOutManualDataBean wait=new StockOutManualDataBean();
						wait.setSoutSerialNo(""+c);
						wait.setSoutDate(stkoutapp.get(0).getStockoutManual().getStockoutDate());
						wait.setSoutOrederNo(stkoutapp.get(0).getStockoutManual().getStockoutOrderNumber());
						wait.setSoutProjectName(stkoutapp.get(0).getStockoutManual().getProduct().getProductName());
						wait.setSoutTotalAmount(stkoutapp.get(0).getStockoutManual().getTotalAmount());
						wait.setSoutCategory(stkoutapp.get(0).getStockoutManual().getCategory());
						list.add(wait);
						stockOutManualDataBean.setStockOutList(list);
						c++;
					}
					else
					{
						stockOutManualDataBean.setStockOutList(list);
					}
				}
				System.out.println("list size - "+stockOutManualDataBean.getStockOutList().size());
			}
			else
			{
				stockOutManualDataBean.setStockOutList(list);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";	
	}
	
	@Transactional(value="transactionManager")
	public String stockoutapproval(StockOutManualDataBean stockOutManualDataBean)
	{
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		try
		{
			v=entitymanager.createQuery("from StockoutManual where stockoutOrderNumber=? and status='Active'");
			v.setParameter(1, stockOutManualDataBean.getSoutOrederNo());
			List<StockoutManual> stkman=(List<StockoutManual>)v.getResultList();
			int stkmanid=0;
			if(stkman.size()>0)
			{
				stkmanid=stkman.get(0).getStock_out_ID();
				System.out.println("stock out manual id - > "+stkmanid);		
				v=null;
				v=entitymanager.createQuery("from Stockoutapproval where stock_out_ID=?");
				v.setParameter(1, stkmanid);
				List<Stockoutapproval> stkoutappr=(List<Stockoutapproval>) v.getResultList();
				if(stkoutappr.size()>0)
				{
					int stkoutapprid=stkoutappr.get(0).getStockoutapproval_ID();
					System.out.println("stock out approve id -- >> "+stkoutapprid);
					if(stockOutManualDataBean.getAction().equals("Approve"))
					{
						System.out.println("approve -- >>.> ");
						v=entitymanager.createQuery("from StockoutRecored where stock_out_ID=? ");
						v.setParameter(1, stkmanid);
						List<StockoutRecored> stkoutrec=(List<StockoutRecored>) v.getResultList();
						System.out.println("stock outrecord size - > "+stkoutrec.size());
						if(stkoutrec.size()>0)
						{
							int c=0;
							for (int i = 0; i < stkoutrec.size(); i++) 
							{
								if(Integer.parseInt(stkoutrec.get(i).getBarcode().getStockIn())<Integer.parseInt(stkoutrec.get(i).getSoldQuantity()))
								{
									c++;
								}						
							}
							if(c>0)
							{
								System.out.println("quantity is greater - >");
								stockOutManualDataBean.setSoutQty("greater");
							}
							else
							{
								System.out.println("quantity is less - > update into barcode ");
								for (int i = 0; i < stkoutrec.size(); i++) 
								{
									Barcode stkupd=entitymanager.find(Barcode.class, stkoutrec.get(i).getBarcode().getBarcode_ID());
									stkupd.setStockIn(""+(new BigDecimal(stkoutrec.get(i).getBarcode().getStockIn()).subtract(new BigDecimal(stkoutrec.get(i).getSoldQuantity()))));
									stkupd.setStockOut(""+(new BigDecimal(stkoutrec.get(i).getBarcode().getStockOut()).add(new BigDecimal(stkoutrec.get(i).getSoldQuantity()))));
									entitymanager.merge(stkupd);
								}	
								stockOutManualDataBean.setSoutQty("less");
							}
						}
						Stockoutapproval approve=entitymanager.find(Stockoutapproval.class, stkoutapprid);
						approve.setApproval_login_GM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setApproval_status_GM("Approved");
						approve.setApproved_date_GM(date);
						approve.setApprovedDate(date);
						approve.setApprovedLogin(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setApprovedTime(time);
						entitymanager.merge(approve);
						v=null;
						v=entitymanager.createQuery("from Stockoutapproval where stock_out_ID=? and approval_status_GM='Approved' and approval_status_PM='Approved'");
						v.setParameter(1, stkmanid);
						List<Stockoutapproval> stkapprove=(List<Stockoutapproval>) v.getResultList();
						if(stkapprove.size()>0)
						{
							int appid=stkapprove.get(0).getStockoutapproval_ID();
							Stockoutapproval appp1=entitymanager.find(Stockoutapproval.class, appid);
							appp1.setApprovalStatus("Approved");
							entitymanager.merge(appp1);
							int sal=stkapprove.get(0).getStockoutManual().getStock_out_ID();
							StockoutManual appp=entitymanager.find(StockoutManual.class, sal);
							appp.setLimitApprovalStatus("Approved");
							entitymanager.merge(appp);
						}						
					}
					else if(stockOutManualDataBean.getAction().equals("Reject"))
					{
						System.out.println("reject -- >>.> ");
						Stockoutapproval approve=entitymanager.find(Stockoutapproval.class, stkoutapprid);
						approve.setApproved_reject_GM("Rejected");
						approve.setApproved_reject_date_GM(""+date);
						approve.setReject_reason_GM(stockOutManualDataBean.getReason());
						approve.setApprovedRejectStatus("RejectedbyGM");		
						approve.setApprovalStatus("Rejected");
						entitymanager.merge(approve);
						v=null;
						v=entitymanager.createQuery("from Stockoutapproval where stock_out_ID=?");
						v.setParameter(1, stkmanid);
						List<Stockoutapproval> salapprove=(List<Stockoutapproval>) v.getResultList();
						if(salapprove.size()>0)
						{
							int sal=salapprove.get(0).getStockoutManual().getStock_out_ID();
							StockoutManual appp=entitymanager.find(StockoutManual.class, sal);
							appp.setLimitApprovalStatus("Rejected");
							entitymanager.merge(appp);
						}
						stockOutManualDataBean.setSoutQty("less");
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
	
	public String stockOutManualView(StockOutManualDataBean stockOutManualDataBean)
	{
		Query v=null;
		ArrayList<StockOutManualDataBean> list=new ArrayList<StockOutManualDataBean>();
		try
		{
			v=entitymanager.createQuery("from StockoutManual where stockoutOrderNumber=? and status='Active'");
			v.setParameter(1, stockOutManualDataBean.getSoutOrederNo());
			System.out.println("order no - > "+stockOutManualDataBean.getSoutOrederNo());
			List<StockoutManual> waiting=(List<StockoutManual>) v.getResultList();
			System.out.println("stock out waiting approval - > "+waiting.size());
			int stkoutid=0;
			if(waiting.size()>0)
			{
				stkoutid=waiting.get(0).getStock_out_ID();
				System.out.println("id - > "+stkoutid);
				v=entitymanager.createQuery("from StockoutRecored where stock_out_ID=?");
				v.setParameter(1, stkoutid);
				List<StockoutRecored> stkoutrec=(List<StockoutRecored>) v.getResultList();
				System.out.println("stock out record approval - > "+stkoutrec.size());
				if(stkoutrec.size()>0)
				{
					int c=1;
					for (int i = 0; i < stkoutrec.size(); i++) 
					{
						StockOutManualDataBean wait=new StockOutManualDataBean();
						wait.setSoutSerialNo(""+c);
						wait.setSoutProductName1(stkoutrec.get(i).getBarcode().getBatch().getProductName());
						wait.setSoutNetAmount(stkoutrec.get(i).getNetAmount());
						wait.setSoutQty(stkoutrec.get(i).getSoldQuantity());
						wait.setSoutUnitPrice(stkoutrec.get(i).getUnitPrice());
						list.add(wait);
						stockOutManualDataBean.setStockOutList(list);
						c++;
					}
					stockOutManualDataBean.setSoutProjectName(waiting.get(0).getProduct().getProductName());
					stockOutManualDataBean.setSoutDate(waiting.get(0).getStockoutDate());
					stockOutManualDataBean.setSoutOrederNo(waiting.get(0).getStockoutOrderNumber());
					stockOutManualDataBean.setSoutTotalAmount(waiting.get(0).getTotalAmount());
					stockOutManualDataBean.setSoutEmployeeID(waiting.get(0).getEmployee().getEmployee_details_ID());
					stockOutManualDataBean.setSoutCustomerName(waiting.get(0).getProjet().getProjectName());
					stockOutManualDataBean.setSoutCategory(waiting.get(0).getCategory());
					stockOutManualDataBean.setSoutOrederNo(waiting.get(0).getStockoutOrderNumber());
					stockOutManualDataBean.setSoutTaxType(waiting.get(0).getTaxType());
					System.out.println("list size - "+stockOutManualDataBean.getStockOutList().size());	
				}				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String dateSearchView(StockOutManualDataBean stockOutManualDataBean)
	{
		System.out.println("date search in stock out view dao -- ");
		Query v=null;
		ArrayList<StockOutManualDataBean> list=new ArrayList<StockOutManualDataBean>();
		try
		{
			if(stockOutManualDataBean.getSoutProjectName().equals(""))
			{
				v=entitymanager.createQuery("from StockoutManual where stockoutDate between ? and ? and status='Active' and (limitApprovalStatus='Waiting' or limitApprovalStatus='Approved')");
				v.setParameter(1, stockOutManualDataBean.getFdate());
				v.setParameter(2, stockOutManualDataBean.getTdate());
				List<StockoutManual> stkview=(List<StockoutManual>) v.getResultList();
				System.out.println("stk out manual view size - > "+stkview.size());
				if(stkview.size()>0)
				{
					for (int i = 0; i < stkview.size(); i++) 
					{
						StockOutManualDataBean view=new StockOutManualDataBean();
						try{
						
						view.setSoutDate(stkview.get(i).getStockoutDate());
						view.setSoutOrederNo(stkview.get(i).getStockoutOrderNumber());
						view.setSoutEmployeeID(stkview.get(i).getEmployee().getEmployeeName());
						view.setSoutProjectnames(stkview.get(i).getProjet().getProjectName());
						view.setSoutCategory(stkview.get(i).getCategory());
						view.setSoutTotalAmount(stkview.get(i).getTotalAmount());
						list.add(view);
						stockOutManualDataBean.setStockOutList(list);
						}catch(NullPointerException n){
							view.setSoutDate(stkview.get(i).getStockoutDate());
							view.setSoutOrederNo(stkview.get(i).getStockoutOrderNumber());
							view.setSoutEmployeeID(stkview.get(i).getEmployee().getEmployeeName());
							view.setSoutProjectnames("");
							view.setSoutCategory(stkview.get(i).getCategory());
							view.setSoutTotalAmount(stkview.get(i).getTotalAmount());
							list.add(view);
							stockOutManualDataBean.setStockOutList(list);
						}
					}
					System.out.println("stock out record size - > "+stockOutManualDataBean.getStockOutList().size());
				}
				else
				{
					stockOutManualDataBean.setStockOutList(list);
				}
			}
			if(!stockOutManualDataBean.getSoutProjectName().equals(""))
			{
				v=entitymanager.createQuery("from Projet where projectName=? and status='Active'");
				v.setParameter(1, stockOutManualDataBean.getSoutProjectName());
				List<Projet> project=(List<Projet>)v.getResultList();
				v=entitymanager.createQuery("from StockoutManual where stockoutDate between ? and ? and project_ID=? and status='Active'");
				v.setParameter(1, stockOutManualDataBean.getFdate());
				v.setParameter(2, stockOutManualDataBean.getTdate());
				v.setParameter(3, project.get(0).getProject_ID());
				List<StockoutManual> stkview=(List<StockoutManual>) v.getResultList();
				System.out.println("stk out manual view size - > "+stkview.size());
				if(stkview.size()>0)
				{
					for (int i = 0; i < stkview.size(); i++) 
					{
						StockOutManualDataBean view=new StockOutManualDataBean();
						try{
						
						view.setSoutDate(stkview.get(i).getStockoutDate());
						view.setSoutOrederNo(stkview.get(i).getStockoutOrderNumber());
						view.setSoutEmployeeID(stkview.get(i).getEmployee().getEmployeeName());
						view.setSoutProjectnames(stkview.get(i).getProjet().getProjectName());
						view.setSoutCategory(stkview.get(i).getCategory());
						view.setSoutTotalAmount(stkview.get(i).getTotalAmount());
						list.add(view);
						stockOutManualDataBean.setStockOutList(list);
						}catch(NullPointerException n){
							view.setSoutDate(stkview.get(i).getStockoutDate());
							view.setSoutOrederNo(stkview.get(i).getStockoutOrderNumber());
							view.setSoutEmployeeID(stkview.get(i).getEmployee().getEmployeeName());
							view.setSoutProjectnames("");
							view.setSoutCategory(stkview.get(i).getCategory());
							view.setSoutTotalAmount(stkview.get(i).getTotalAmount());
							list.add(view);
							stockOutManualDataBean.setStockOutList(list);
						}
					}
					System.out.println("stock out record size - > "+stockOutManualDataBean.getStockOutList().size());
				}
				else
				{
					stockOutManualDataBean.setStockOutList(list);
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String viewOutRecords(StockOutManualDataBean stockOutManualDataBean)
	{
		System.out.println("in stock out view records in dao -- ");
		Query v=null;
		ArrayList<StockOutManualDataBean> list=new ArrayList<StockOutManualDataBean>();
		try
		{
			v=entitymanager.createQuery("from StockoutManual where stockoutOrderNumber=? and status='Active'");
			v.setParameter(1, stockOutManualDataBean.getSoutOrederNo());
			List<StockoutManual> stkview=(List<StockoutManual>) v.getResultList();
			int stkoutid=0;
			if(stkview.size()>0)
			{
				stkoutid=stkview.get(0).getStock_out_ID();
				System.out.println("id - > "+stkoutid);
				v=entitymanager.createQuery("from StockoutRecored where stock_out_ID=?");
				v.setParameter(1, stkoutid);
				List<StockoutRecored> stkoutrec=(List<StockoutRecored>) v.getResultList();
				System.out.println("stock out record size - > "+stkoutrec.size());
				if(stkoutrec.size()>0)
				{
					int c=1;
					for (int i = 0; i < stkoutrec.size(); i++) 
					{
						StockOutManualDataBean view=new StockOutManualDataBean();
						view.setSoutSerialNo(""+c);
						view.setSoutProductName1(stkoutrec.get(i).getBarcode().getBatch().getProductName());
						view.setSoutQty(stkoutrec.get(i).getSoldQuantity());
						view.setSoutUnitPrice(stkoutrec.get(i).getUnitPrice());
						view.setSoutNetAmount(stkoutrec.get(i).getNetAmount());
						list.add(view);
						stockOutManualDataBean.setStockOutList(list);
						c++;
					}
/*					stockOutManualDataBean.setSoutCustomerName(stkview.get(0).getCustomer().getCustomerPhoneNumber());
*/					stockOutManualDataBean.setSoutProjectnames(stkview.get(0).getProjet().getProjectName());
					stockOutManualDataBean.setSoutEmployeeID(stkview.get(0).getEmployee().getEmployeeName());
					stockOutManualDataBean.setSoutCategory(stkview.get(0).getCategory());
					stockOutManualDataBean.setSoutProjectName(stkview.get(0).getProduct().getProductName());
					stockOutManualDataBean.setSoutDate(stkview.get(0).getStockoutDate());
					stockOutManualDataBean.setSoutOrederNo(stkview.get(0).getStockoutOrderNumber());
					stockOutManualDataBean.setSoutTotalAmount(stkview.get(0).getTotalAmount());
					stockOutManualDataBean.setSoutTaxType(stkview.get(0).getTaxType());
				}
			}
			else
			{
				stockOutManualDataBean.setStockOutList(list);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String categoryOut(StockDataBean stockDataBean)
	{
		Query v=null;
		List<String> products=null;
		try
		{
			if(stockDataBean.getStockCategory().equals("Product Trading"))
			{
				System.out.println("in product trading");
				v=entitymanager.createQuery("select productName from Product where category=? and status='Active'");
				v.setParameter(1, stockDataBean.getStockCategory());
				products=v.getResultList();
				System.out.println("products - > "+products);
			}
			else if(!stockDataBean.getStockCategory().equals("Product Trading"))
			{
				System.out.println("in Raw Material");
				v=entitymanager.createQuery("select projectName from Projet where status='Active'");
				products=v.getResultList();
				System.out.println("products - > "+products);
			}
			stockDataBean.setProducts(products);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public String stockoutview(StockDataBean stockDataBean)
	{
		System.out.println("stock out view- > ");
		stockDataBean.setViewlist(null);
		Query v=null;
		List<StockDataBean> list=new ArrayList<StockDataBean>();
		try
		{
			System.out.println("product - > "+stockDataBean.getStockProductName()+" category -- > "+stockDataBean.getStockCategory());
			if(stockDataBean.getStockCategory().equals("Product Trading"))
			{
				v=entitymanager.createQuery("from Batch where productName=? and category=? and status='Active'");
				v.setParameter(1, stockDataBean.getStockProductName());
				v.setParameter(2, stockDataBean.getStockCategory());
				List<Batch> batch=(List<Batch>) v.getResultList();
				int batchid=0;
				if(batch.size()>0)
				{
					batchid=batch.get(0).getBatch_ID();
					System.out.println("batch id -- "+batchid);
					v=null;
					v=entitymanager.createQuery("from Barcode where batch_ID=? and status='Barcode Generated'");
					v.setParameter(1, batchid);
					List<Barcode> barcode=(List<Barcode>) v.getResultList();
					int barcodeid=0;
					if(barcode.size()>0)
					{
						barcodeid=barcode.get(0).getBarcode_ID();
						System.out.println("barcode id - > "+barcodeid);
						
							StockDataBean view=new StockDataBean();
							view.setStockProductName(barcode.get(0).getBatch().getProductName());
							view.setStockCategory(barcode.get(0).getBatch().getCategory());
							view.setStockUnitPrice(barcode.get(0).getBatch().getUnitPrice());
							view.setStockUnit(barcode.get(0).getBatch().getUnit());
							System.out.println("quan - > "+barcode.get(0).getStockOut());
							view.setStockOutQty(barcode.get(0).getStockOut());
							try
							{
								if(view.getStockOutQty().equals(""))
								{
									
								}
							}
							catch(Exception e)
							{
								view.setStockOutQty("0");
							}
							list.add(view);
							stockDataBean.setViewlist(list);
			}			
			
				}
			}
			else if(!stockDataBean.getStockCategory().equals("Product Trading"))
			{		
				v=null;
				v=entitymanager.createQuery("from Projet where projectName=? and status='Active'");
				v.setParameter(1, stockDataBean.getStockProductName());
				List<Projet> proj=(List<Projet>)v.getResultList();
				if(proj.size()>0)
				{
					v=null;
					v=entitymanager.createQuery("from StockoutManual where category=? and project_ID=? and status='Active' and limitApprovalStatus='Approved'");
					v.setParameter(1,stockDataBean.getStockCategory());
					v.setParameter(2, proj.get(0).getProject_ID());
					List<StockoutManual> stk=(List<StockoutManual>)v.getResultList();
					System.out.println("stock out size -- > "+stk.size());
					if(stk.size()>0)
					{
						for (int i = 0; i < stk.size(); i++)
						{
							BigDecimal quan=BigDecimal.valueOf(0);							
							v=null;
							v=entitymanager.createQuery("from StockoutRecored where stock_out_ID=?");
							v.setParameter(1, stk.get(i).getStock_out_ID());
							List<StockoutRecored> stkoutrec=(List<StockoutRecored>) v.getResultList();	
							System.out.println("stock out record size -- > "+stkoutrec.size());
							if(stkoutrec.size()>0)
							{
								for (int j = 0; j < stkoutrec.size(); j++) 
								{
									v=null;
									v=entitymanager.createQuery("from RawMaterial where productName=? and category=? and status='Active'");
									v.setParameter(1, stkoutrec.get(j).getBarcode().getBatch().getProductName());
									v.setParameter(2, stockDataBean.getStockCategory());
									List<RawMaterial> raw=(List<RawMaterial>) v.getResultList();
									int rawid=0;
									if(raw.size()>0)
									{
										rawid=raw.get(0).getRaw_ID();
										System.out.println("raw id - > "+rawid);
										v=null;
										v=entitymanager.createQuery("from ProductLimit where raw_id=? and product_ID=? and status='Active'");
										v.setParameter(1, rawid);
										v.setParameter(2, stk.get(i).getProduct().getProduct_ID());
										List<ProductLimit> limit=(List<ProductLimit>) v.getResultList();
										if(limit.size()>0)
										{
											stockDataBean.setLimit(limit.get(0).getLimitSize());
										}
									}
									StockDataBean view=new StockDataBean();
									view.setStockOrderDate(stk.get(i).getStockoutDate());
									view.setStcokOrderNumber(stk.get(i).getStockoutOrderNumber());
									view.setLimit(stockDataBean.getLimit());
									view.setStockProductName(stkoutrec.get(j).getBarcode().getBatch().getProductName());
									view.setStockCategory(stkoutrec.get(j).getBarcode().getBatch().getCategory());
									view.setStockUnitPrice(stkoutrec.get(j).getUnitPrice());
									view.setStockOutQty(stkoutrec.get(j).getSoldQuantity());
									quan=new BigDecimal(stockDataBean.getLimit()).subtract(new BigDecimal(stkoutrec.get(j).getSoldQuantity()));
									System.out.println("limit  - "+stockDataBean.getLimit()+" bal limit - > "+quan);
									view.setStockinQty(""+quan);
									list.add(view);
									stockDataBean.setViewlist(list);	
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
		return "";
	}
	
	public String categorySearchView(StockOutManualDataBean stockOutManualDataBean)
	{
		System.out.println("category search in stock out view dao -- ");
		Query v=null;
		ArrayList<StockOutManualDataBean> list=new ArrayList<StockOutManualDataBean>();
		try
		{
			v=entitymanager.createQuery("from StockoutManual where category=? and status='Active' and (limitApprovalStatus='Waiting' or limitApprovalStatus='Approved')");
			v.setParameter(1, stockOutManualDataBean.getSoutCategory());
			System.out.println("category - > "+stockOutManualDataBean.getSoutCategory());
			List<StockoutManual> stkview=(List<StockoutManual>) v.getResultList();
			System.out.println("stk out manual view size - > "+stkview.size());
			if(stkview.size()>0)
			{
				for (int i = 0; i < stkview.size(); i++) 
				{
					
					StockOutManualDataBean view=new StockOutManualDataBean();
					try{
					view.setSoutDate(stkview.get(i).getStockoutDate());
					view.setSoutOrederNo(stkview.get(i).getStockoutOrderNumber());
					view.setSoutEmployeeID(stkview.get(i).getEmployee().getEmployeeName());
					/*view.setSoutCustomerName(stkview.get(i).getCustomer().getCustomerPhoneNumber());*/
					view.setSoutProjectnames(stkview.get(i).getProjet().getProjectName());
					view.setSoutCategory(stkview.get(i).getCategory());
					view.setSoutTotalAmount(stkview.get(i).getTotalAmount());
					list.add(view);
					stockOutManualDataBean.setStockOutList(list);
					}catch(NullPointerException e){
						view.setSoutDate(stkview.get(i).getStockoutDate());
						view.setSoutOrederNo(stkview.get(i).getStockoutOrderNumber());
						view.setSoutEmployeeID(stkview.get(i).getEmployee().getEmployeeName());
						/*view.setSoutCustomerName(stkview.get(i).getCustomer().getCustomerPhoneNumber());*/
						view.setSoutProjectnames("");
						view.setSoutCategory(stkview.get(i).getCategory());
						view.setSoutTotalAmount(stkview.get(i).getTotalAmount());
						list.add(view);
						stockOutManualDataBean.setStockOutList(list);
					}
					 
				}
				System.out.println("stock out record size - > "+stockOutManualDataBean.getStockOutList().size());
			}
			else
			{
				stockOutManualDataBean.setStockOutList(list);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public List<String> stockDamgeProducts()
	{
		List<String> products=new ArrayList<String>();
		Query v=null;
		v=entitymanager.createQuery("from StockDamage where status='Damage'");
		List<StockDamage> dams=(List<StockDamage>)v.getResultList();
		if(dams.size()>0)
		{
			for (int i = 0; i < dams.size(); i++)
			{
				String product=dams.get(i).getBatch().getProductName();
				products.add(product);				
			}
			System.out.println(" pp - >"+products);
			HashSet<String> ss=new HashSet<String>(products);
			products.clear();
			products.addAll(ss);
			System.out.println("damage pp - >"+products);
		}
		return products;
	}
	
	public String damageproductsView(StockDataBean stockDataBean)
	{
		System.out.println("damage products view dao - > ");
		Query v=null;
		List<StockDataBean> damlist=new ArrayList<StockDataBean>();
		BigDecimal quantity=BigDecimal.valueOf(0);
		try
		{
			v=entitymanager.createQuery("from Batch where productName=? and status='Active'");
			v.setParameter(1, stockDataBean.getStockproduct());
			List<Batch> batch=(List<Batch>) v.getResultList();
			if(batch.size()>0)
			{
				v=entitymanager.createQuery("from StockDamage where batch_ID=?");
				v.setParameter(1, batch.get(0).getBatch_ID());
				List<StockDamage> dams=(List<StockDamage>)v.getResultList();
				System.out.println("damge size - > "+dams.size());
				if(dams.size()>0)
				{
					for (int i = 0; i < dams.size(); i++) 
					{
						quantity=quantity.add(new BigDecimal(dams.get(i).getDamageReturn()));
					}
					StockDataBean damgelist=new StockDataBean();
					damgelist.setStockProductName(batch.get(0).getProductName());
					damgelist.setStockFirmName(batch.get(0).getVendor().getFirmName());
					damgelist.setStockDamageQty(""+quantity);
					damgelist.setStockCategory(batch.get(0).getCategory());
					damgelist.setStatus("Damaged");
					damlist.add(damgelist);
					stockDataBean.setViewlist(damlist);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public String projectNames(StockOutManualDataBean stockOutManualDataBean)
	{
		Query v=null;
		List<String> projects=null;
		try
		{			
			v=entitymanager.createQuery("select projectName from Projet where status='Active'");
			projects=v.getResultList();
			System.out.println("projects - > "+projects);
			stockOutManualDataBean.setProjectList(projects);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public int remStockOutManualPM()
	{
		int c=0;
		Query v=null;
		try
		{
			v=entitymanager.createQuery("from StockoutManual where limitApprovalStatus='Waiting' and status='Active'");
			List<StockoutManual> waiting=(List<StockoutManual>) v.getResultList();
			if(waiting.size()>0)
			{
				for (int i = 0; i < waiting.size(); i++) 
				{
					v=null;
					v=entitymanager.createQuery("from Stockoutapproval where stock_out_ID=? and approval_status_PM='pending'");
					v.setParameter(1, waiting.get(i).getStock_out_ID());
					List<Stockoutapproval> stkoutapp=(List<Stockoutapproval>) v.getResultList();
					if(stkoutapp.size()>0)
					{
						c++;
					}
				}
				System.out.println("in stock out manual limit exceeding size in PM -- > "+c);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return c;
	}
	
	public String stockOutWaitingListPM(StockOutManualDataBean stockOutManualDataBean)
	{
		Query v=null;
		ArrayList<StockOutManualDataBean> list=new ArrayList<StockOutManualDataBean>();
		try
		{
			v=entitymanager.createQuery("from StockoutManual where limitApprovalStatus='Waiting' and status='Active'");
			List<StockoutManual> waiting=(List<StockoutManual>) v.getResultList();
			System.out.println("stock out waiting approval - > "+waiting.size());
			if(waiting.size()>0)
			{				
				int c=1;
				for (int i = 0; i < waiting.size(); i++) 
				{
					v=null;
					v=entitymanager.createQuery("from Stockoutapproval where stock_out_ID=? and approval_status_PM='pending'");
					v.setParameter(1, waiting.get(i).getStock_out_ID());
					List<Stockoutapproval> stkoutapp=(List<Stockoutapproval>) v.getResultList();
					if(stkoutapp.size()>0)
					{
						StockOutManualDataBean wait=new StockOutManualDataBean();
						wait.setSoutSerialNo(""+c);
						wait.setSoutDate(stkoutapp.get(0).getStockoutManual().getStockoutDate());
						wait.setSoutOrederNo(stkoutapp.get(0).getStockoutManual().getStockoutOrderNumber());
						wait.setSoutProjectName(stkoutapp.get(0).getStockoutManual().getProduct().getProductName());
						wait.setSoutTotalAmount(stkoutapp.get(0).getStockoutManual().getTotalAmount());
						wait.setSoutCategory(stkoutapp.get(0).getStockoutManual().getCategory());
						list.add(wait);
						stockOutManualDataBean.setStockOutList(list);
						c++;
					}
					else
					{
						stockOutManualDataBean.setStockOutList(list);
					}
				}
				System.out.println("list size - "+stockOutManualDataBean.getStockOutList().size());
			}
			else
			{
				stockOutManualDataBean.setStockOutList(list);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";	
	}
	
	@Transactional(value="transactionManager")
	public String stockoutapprovalPM(StockOutManualDataBean stockOutManualDataBean)
	{
		Query v=null;
		Date date=new Date();
		Timestamp time=new Timestamp(date.getTime());
		try
		{
			v=entitymanager.createQuery("from StockoutManual where stockoutOrderNumber=? and status='Active'");
			v.setParameter(1, stockOutManualDataBean.getSoutOrederNo());
			List<StockoutManual> stkman=(List<StockoutManual>)v.getResultList();
			int stkmanid=0;
			if(stkman.size()>0)
			{
				stkmanid=stkman.get(0).getStock_out_ID();
				System.out.println("stock out manual id - > "+stkmanid);				
				v=null;
				v=entitymanager.createQuery("from Stockoutapproval where stock_out_ID=?");
				v.setParameter(1, stkmanid);
				List<Stockoutapproval> stkoutappr=(List<Stockoutapproval>) v.getResultList();
				if(stkoutappr.size()>0)
				{
					int stkoutapprid=stkoutappr.get(0).getStockoutapproval_ID();
					System.out.println("stock out approve id -- >> "+stkoutapprid);
					if(stockOutManualDataBean.getAction().equals("Approve"))
					{
						System.out.println("approve -- >>.> ");
						v=entitymanager.createQuery("from StockoutRecored where stock_out_ID=? ");
						v.setParameter(1, stkmanid);
						List<StockoutRecored> stkoutrec=(List<StockoutRecored>) v.getResultList();
						System.out.println("stock outrecord size - > "+stkoutrec.size());
						if(stkoutrec.size()>0)
						{
							int c=0;
							for (int i = 0; i < stkoutrec.size(); i++) 
							{
								if(Integer.parseInt(stkoutrec.get(i).getBarcode().getStockIn())<Integer.parseInt(stkoutrec.get(i).getSoldQuantity()))
								{
									c++;
								}						
							}
							if(c>0)
							{
								System.out.println("quantity is greater - >");
								stockOutManualDataBean.setSoutQty("greater");
							}
							else
							{
								System.out.println("quantity is less - > update into barcode ");
								for (int i = 0; i < stkoutrec.size(); i++) 
								{
									Barcode stkupd=entitymanager.find(Barcode.class, stkoutrec.get(i).getBarcode().getBarcode_ID());
									stkupd.setStockIn(""+(new BigDecimal(stkoutrec.get(i).getBarcode().getStockIn()).subtract(new BigDecimal(stkoutrec.get(i).getSoldQuantity()))));
									stkupd.setStockOut(""+(new BigDecimal(stkoutrec.get(i).getBarcode().getStockOut()).add(new BigDecimal(stkoutrec.get(i).getSoldQuantity()))));
									entitymanager.merge(stkupd);
								}	
								stockOutManualDataBean.setSoutQty("less");
							}
						}
						Stockoutapproval approve=entitymanager.find(Stockoutapproval.class, stkoutapprid);
						approve.setApproval_login_PM(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setApproval_status_PM("Approved");
						approve.setApproved_date_PM(date);
						approve.setApprovedDate(date);
						approve.setApprovedLogin(""+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login_user"));
						approve.setApprovedTime(time);
						entitymanager.merge(approve);
						v=null;
						v=entitymanager.createQuery("from Stockoutapproval where stock_out_ID=? and approval_status_GM='Approved' and approval_status_PM='Approved'");
						v.setParameter(1, stkmanid);
						List<Stockoutapproval> stkapprove=(List<Stockoutapproval>) v.getResultList();
						if(stkapprove.size()>0)
						{
							int appid=stkapprove.get(0).getStockoutapproval_ID();
							Stockoutapproval appp1=entitymanager.find(Stockoutapproval.class, appid);
							appp1.setApprovalStatus("Approved");
							entitymanager.merge(appp1);
							int sal=stkapprove.get(0).getStockoutManual().getStock_out_ID();
							StockoutManual appp=entitymanager.find(StockoutManual.class, sal);
							appp.setLimitApprovalStatus("Approved");
							entitymanager.merge(appp);
						}
						
					}
					else if(stockOutManualDataBean.getAction().equals("Reject"))
					{
						System.out.println("reject -- >>.> ");
						Stockoutapproval approve=entitymanager.find(Stockoutapproval.class, stkoutapprid);
						approve.setApproved_reject_PM("Rejected");
						approve.setApproved_reject_date_PM(""+date);
						approve.setReject_reason_PM(stockOutManualDataBean.getReason());
						approve.setApprovedRejectStatus("RejectedbyPM");		
						approve.setApprovalStatus("Rejected");
						entitymanager.merge(approve);
						v=null;
						v=entitymanager.createQuery("from Stockoutapproval where stock_out_ID=?");
						v.setParameter(1, stkmanid);
						List<Stockoutapproval> salapprove=(List<Stockoutapproval>) v.getResultList();
						if(salapprove.size()>0)
						{
							int sal=salapprove.get(0).getStockoutManual().getStock_out_ID();
							StockoutManual appp=entitymanager.find(StockoutManual.class, sal);
							appp.setLimitApprovalStatus("Rejected");
							entitymanager.merge(appp);
						}
						stockOutManualDataBean.setSoutQty("less");
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
