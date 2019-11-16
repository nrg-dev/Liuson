package com.nrg.liusen.dao;
import java.util.List;

import com.nrg.liusen.domain.LimitDataBean;
import com.nrg.liusen.domain.LoginAccess;
import com.nrg.liusen.domain.ProductDataBean;
import com.nrg.liusen.domain.ProfitLossDataBean;
import com.nrg.liusen.domain.PurchaseOrederDataBean;
import com.nrg.liusen.domain.VendorDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.shared.Product;
import com.nrg.liusen.shared.Vendor;
/**
 * This Java Class will communicate with Database
 * @author Robert Arjun 
 * @date 13-11-2015
 * @copyright NRG      
 */
public interface PurchaseDao
{

	public List<String> productNameList(PurchaseOrederDataBean o);
	public List<String> vendorNameList(PurchaseOrederDataBean o);
	public String productPrice(PurchaseOrederDataBean p);	
	public String purchaseInsertion1(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseInsertion2(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseView(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseDelete(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseEditConfirm(PurchaseOrederDataBean p) throws LiusenException;	
	public String purchaseApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseOrderDetailedView(PurchaseOrederDataBean p) throws LiusenException;
	public String purchaseEditDeleteCheck(PurchaseOrederDataBean p) throws LiusenException;	
	public String poDetailsForApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String poDetailsForDeliveryApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String poGMApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String poMMApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String poDelGMApproval(PurchaseOrederDataBean p) throws LiusenException;
	public String poDelPMApproval(PurchaseOrederDataBean p) throws LiusenException;
	public List<String> getinvoicePurchase();	
	public String invoiceView(PurchaseOrederDataBean purchaseOrederDataBean);
	public String invoiceViewForm(PurchaseOrederDataBean purchaseOrederDataBean);
	public String invoiceinsertpur(PurchaseOrederDataBean purchaseOrederDataBean);
	public List<String> purchasepayorders();	
	public String paymentViewpurchase(PurchaseOrederDataBean purchaseOrederDataBean);
	public String payPurchase(PurchaseOrederDataBean purchaseOrederDataBean);
	public String paymentinpur(PurchaseOrederDataBean purchaseOrederDataBean);
	public String payPurchaseAmount(PurchaseOrederDataBean purchaseOrederDataBean);
	public String purchasePayment(PurchaseOrederDataBean p);
	public String paymentFM(PurchaseOrederDataBean purchaseOrederDataBean);
	public String paymentDirector(PurchaseOrederDataBean purchaseOrederDataBean);
	public String paymentApproveFM(PurchaseOrederDataBean purchaseOrederDataBean);
	public String paymentApproveDirector(PurchaseOrederDataBean purchaseOrederDataBean);
	public String paymentStatusView(PurchaseOrederDataBean purchaseOrederDataBean);
	public String purcPaymentView(PurchaseOrederDataBean purchaseOrederDataBean);
	public List<ProfitLossDataBean> searchProfitLoss(ProfitLossDataBean profitLossDataBean);
	public String purchaseOrderClose(PurchaseOrederDataBean purchaseOrederDataBean);	
	public String purchaseReturn(PurchaseOrederDataBean purchaseOrederDataBean);
	public String purchaseReturnsearch(PurchaseOrederDataBean purchaseOrederDataBean);
	public String returnCheckpur(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException;	
	public String quantityCheckReturn(PurchaseOrederDataBean purchaseOrederDataBean)throws LiusenException;
	public String returnSubmitPurcahse(PurchaseOrederDataBean purchaseOrederDataBean);
	public String returnSubmitPurcahse1(PurchaseOrederDataBean purchaseOrederDataBean, int j);
	public String purchaseUpdateReturn(PurchaseOrederDataBean purchaseOrederDataBean);
	public String stockUpdateReturnNr(PurchaseOrederDataBean purchaseOrederDataBean, int j);
	public String stockUpdateReturnDr(PurchaseOrederDataBean purchaseOrederDataBean, int j);
	public String paymentCheckReturn(PurchaseOrederDataBean purchaseOrederDataBean);
	public String purchaseReturnView(PurchaseOrederDataBean purchaseOrederDataBean);
	public String purchaseReturnViewRecord(PurchaseOrederDataBean purchaseOrederDataBean);
	
}
