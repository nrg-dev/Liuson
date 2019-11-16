package com.nrg.liusen.managedBean;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.LoginAccess;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.util.Util;
/**
    
 */

@ManagedBean(name="loginMB")
public class LoginMB 
{	
	private String invusername;
	private String invpassword;
	private String rollname;
	private String login_username;
	
	/**
	 * @return the login_username
	 */
	public String getLogin_username() {
		return login_username;
	}
	/**
	 * @param login_username the login_username to set
	 */
	public void setLogin_username(String login_username) {
		this.login_username = login_username;
	}
	/**
	 * @return the rollname
	 */
	public String getRollname() {
		return rollname;
	}
	/**
	 * @param rollname the rollname to set
	 */
	public void setRollname(String rollname) {
		this.rollname = rollname;
	}

	private String validate;
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getInvusername() {
		return invusername;
	}
	public void setInvusername(String invusername) {
		this.invusername = invusername;
	}
	public String getInvpassword() {
		return invpassword;
	}
	public void setInvpassword(String invpassword) {
		this.invpassword = invpassword;
	}

	private static Logger logger = Logger.getLogger(LoginMB.class);
	
	public String userLogin() 
	{
		LiusenController controller = null;
		try
		{
			logger.info("inside userLogin mb");
			LoginAccess loginaccess = new LoginAccess();
			loginaccess.setUsername(invusername);
			loginaccess.setUserpassword(invpassword);
			logger.debug("user name---------->"+loginaccess.getUsername());
			
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");
			
			logger.info("before callss");
			
			controller.userLogin(loginaccess);
			setRollname(loginaccess.getUser_rolles());
			System.out.println("get roll name"+getRollname()+""+loginaccess.getUsername());
			 ExternalContext externalcontext = FacesContext.getCurrentInstance().getExternalContext();
			 Map<String,Object> sessionMap = externalcontext.getSessionMap();
			 sessionMap.put("roll", loginaccess.getUser_rolles());
			 sessionMap.put("user", loginaccess.getUsername());
			 sessionMap.put("login_user", loginaccess.getLogin_user());	
			 
			logger.info("ends successfully");
			return "success";
		}
		
		catch(LiusenException ie)
		{
			logger.error("inside exception");
			logger.error(""+ie.getMessage());
			setValidate(ie.getMessage());
			return "";	
		}		
		finally
		{
			
		}
	}

	
	
	
	

	public String logout()
	{		
		try
		 {
			 HttpSession session = Util.getSession();
		     session.invalidate();
		     HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();  
	         HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
	         Cookie[] cookies = request.getCookies();  
	         Cookie opentoken = null;  
	         for(Cookie c : cookies){  
	             if (c.getName().equals("opentoken"))
	             {  
	                 logger.debug("found the cookie: "+c.getName()+" domain:"+c.getDomain()+" exp:"+c.getMaxAge()); // log4j debug statement  
	                 opentoken = c;  
	                 opentoken.setMaxAge(0);  
	                 opentoken.setValue(""); // it is more elegant to clear the value but not necessary  
	                 response.addCookie(opentoken);  
	                 logger.debug("redirecting to "+request.getContextPath());  
	                 response.sendRedirect(request.getContextPath());  
	                 break;  
	             }  
	         }  		      
		     return "login";
		 }
		 catch(Exception e)
		 {
			 logger.error("inside  Eception :)");
			 return "failure";			 
		 }
	}	
	private int salesapprovalGM;
	private int salesapprovalMM;
	public int getSalesapprovalGM() {
		return salesapprovalGM;
	}
	public void setSalesapprovalGM(int salesapprovalGM) {
		this.salesapprovalGM = salesapprovalGM;
	}
	public int getSalesapprovalMM() {
		return salesapprovalMM;
	}
	public void setSalesapprovalMM(int salesapprovalMM) {
		this.salesapprovalMM = salesapprovalMM;
	}

	public String remainders()
	{
		ApplicationContext ctx=null;
		LiusenController controller=null;
		try
		{
			ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (LiusenController) ctx.getBean("controller");			
			salesapprovalGM=controller.remSalesApprovalGM();
			salesapprovalMM=controller.remSalesApprovalMM();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "";
	}	
	
}
