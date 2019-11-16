package com.nrg.liusen.managedBean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.ProjectDataBean;
import com.nrg.liusen.shared.Projet;
import com.nrg.liusen.util.CommonValidate;
/**
 * 
 * @author Robert Arjun
 * @Date 27-10-2015
 * @copyright NRG This class works as a ManagedBean
 *
 */
@ManagedBean(name="projectViewMB")
@RequestScoped
public class ProjectViewMB {
	
ProjectDataBean projectDataBean=new ProjectDataBean();
private boolean flag=true;
private List<String> projectNameList=null;
private List<ProjectDataBean> projectViewList=null;
private List<ProjectDataBean> projectViewList1=null;
private List<Projet> projectList=null;
private boolean valid=false;
private boolean myvalid=true;
private boolean deleteflag=false;
private boolean editflag=false;


/**
 * @return the myvalid
 */
public boolean isMyvalid() {
	return myvalid;
}
public boolean isDeleteflag() {
	return deleteflag;
}
public void setDeleteflag(boolean deleteflag) {
	this.deleteflag = deleteflag;
}
/**
 * @param myvalid the myvalid to set
 */
public void setMyvalid(boolean myvalid) {
	this.myvalid = myvalid;
}
/**
 * @return the valid
 */
public boolean isValid() {
	return valid;
}
/**
 * @param valid the valid to set
 */
public void setValid(boolean valid) {
	this.valid = valid;
}
/**
 * @return the projectViewList1
 */
public List<ProjectDataBean> getProjectViewList1() {
	return projectViewList1;
}
/**
 * @param projectViewList1 the projectViewList1 to set
 */
public void setProjectViewList1(List<ProjectDataBean> projectViewList1) {
	this.projectViewList1 = projectViewList1;
}
/**
 * @return the projectDataBean
 */
public ProjectDataBean getprojectDataBean() {
	return projectDataBean;
}
/**
 * @param projectDataBean the projectDataBean to set
 */
public void setprojectDataBean(ProjectDataBean projectDataBean) {
	projectDataBean = projectDataBean;
}
/**
 * @return the flag
 */
public boolean isFlag() {
	return flag;
}
/**
 * @param flag the flag to set
 */
public void setFlag(boolean flag) {
	this.flag = flag;
}
public String projectViewLoad() {

	System.out.println("Inside Load the projectViewLoad Load Page");
	projectDataBean.setFileName(null);
	projectDataBean.setProjProjectName("");
	projectDataBean.setProjClientName("");
	setFlag(true);
	ApplicationContext ctx=null;
	LiusenController controller=null;
	try{
		ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		controller=(LiusenController)ctx.getBean("controller");
		projectNameList=controller.dropProject(projectDataBean);
		System.out.println(projectNameList);
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return "projectViewLoadPage";

}
/**
 * This method is used for show data tables depends on project name
 * 
 * @return
 */
public String searchProjectName() {
	
	System.out.println("Inside the searchProjectName Method Calling");
	ApplicationContext ctx=null;
	LiusenController controller=null;
	projectDataBean.setFileName(null);
	try {
		if (validate(true)) {
			System.out.println("After Validate inside  searchProjectName method");
			String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
			if(rolles.equalsIgnoreCase("Project Manager") || rolles.equalsIgnoreCase("Chief Designer")){
				setDeleteflag(true);
				setEditflag(false);
			}else if(rolles.equalsIgnoreCase("Finance Manager") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Marketing Staff") || rolles.equalsIgnoreCase("Marketing Manager") || rolles.equalsIgnoreCase("Designer")){
				setDeleteflag(true);
				setEditflag(true);
			}
			else{
				setDeleteflag(false);
				setEditflag(false);
			}
			setFlag(false);
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController)ctx.getBean("controller");
			projectViewList=controller.getProjectView(projectDataBean);
		}else{
			setFlag(true);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "";
}

/**
 * This method is used to validate data
 * 
 * @param valid
 * @return false show error message
 * @return true not showing error message
 */
private boolean validate(boolean flag1) {
	boolean valid = true;
	String fieldName;
	FacesContext fc = FacesContext.getCurrentInstance();
	if (StringUtils.isBlank(projectDataBean.getProjProjectName())) {
		if (flag1) {
			fieldName = CommonValidate.findComponentInRoot("projViewName")
					.getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage(
					"Please Choose the Project Name."));
		}
		valid = false;
	} /*else if (!StringUtils.isBlank(projectDataBean.getProjProjectName())) {

		if (!CommonValidate.validateName(projectDataBean.getProjProjectName())) {
			if (flag1) {
				fieldName = CommonValidate.findComponentInRoot(
						"projViewName").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the valid Project Name."));
			}
			valid = false;
		}
	}*/
	return valid;
}

/**
 * This method is used for show data tables depends on Client name
 * 
 * @return
 */

public String searchClient() {
	System.out.println("Inside the searchClient Method Calling");
	ApplicationContext ctx=null;
	LiusenController controller=null;
	projectDataBean.setFileName(null);
	setProjectViewList(null);
	try{
		if (validate1(true)) {
			System.out.println("After Validate1 inside  searchClient method");
			String rolles=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("roll");
			if(rolles.equalsIgnoreCase("Project Manager") || rolles.equalsIgnoreCase("Chief Designer")){
				setDeleteflag(true);
				setEditflag(false);
			}else if(rolles.equalsIgnoreCase("Finance Manager") || rolles.equalsIgnoreCase("Site Engineer") || rolles.equalsIgnoreCase("Inventory Staff") || rolles.equalsIgnoreCase("Admin Project") || rolles.equalsIgnoreCase("Accounting Staff") || rolles.equalsIgnoreCase("Finance Staff") || rolles.equalsIgnoreCase("Marketing Staff") || rolles.equalsIgnoreCase("Marketing Manager") || rolles.equalsIgnoreCase("Designer")){
				setDeleteflag(true);
				setEditflag(true);
			}
			else{
				setDeleteflag(false);
				setEditflag(false);
			}
			
			setFlag(false);
			ctx=FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller=(LiusenController)ctx.getBean("controller");
			projectViewList=controller.getProjectViewbyClient(projectDataBean);
		}else{
			setFlag(true);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";

}

private boolean validate1(boolean flag2) {
	boolean valid = true;
	String fieldName;
	FacesContext fc = FacesContext.getCurrentInstance();
	if (StringUtils.isBlank(projectDataBean.getProjClientName())) {
		if (flag2) {
			fieldName = CommonValidate.findComponentInRoot("projViewClient")
					.getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage(
					"Please Enter the Client Name."));
		}
		valid = false;
	} else if (!StringUtils.isBlank(projectDataBean.getProjClientName())) {

		if (!CommonValidate.validateName(projectDataBean.getProjClientName())) {
			if (flag2) {
				fieldName = CommonValidate.findComponentInRoot(
						"projViewClient").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage(
						"Please Enter the valid Client Name."));
			}
			valid = false;
		}
	}
	return valid;
}

/**
 * @return the projectNameList
 */
public List<String> getProjectNameList() {
	return projectNameList;
}
/**
 * @param projectNameList the projectNameList to set
 */
public void setProjectNameList(List<String> projectNameList) {
	this.projectNameList = projectNameList;
}
/**
 * @return the projectViewList
 */
public List<ProjectDataBean> getProjectViewList() {
	return projectViewList;
}
/**
 * @param projectViewList the projectViewList to set
 */
public void setProjectViewList(List<ProjectDataBean> projectViewList) {
	this.projectViewList = projectViewList;
}
public void paint2(OutputStream out, Object data) throws IOException
{
	String s="C://product/";
	System.out.println(projectDataBean.getFileName());
	BufferedImage img = ImageIO.read(new File(s+""+projectDataBean.getFileName()));
    ImageIO.write(img,"png",out);
}
public void projectViewByName() {
LiusenController controller = null;

if(!projectDataBean.getProjProjectName().equalsIgnoreCase("")){
	 try{
		 ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		 controller = (LiusenController) ctx.getBean("controller");
		 projectViewList1=new ArrayList<ProjectDataBean>();
		 projectList=controller.getProjectListView(projectDataBean,projectViewList1);
		 System.out.println(projectList.size()+"---"+projectViewList1.size());
		 if(projectList.size() > 0){
			 projectDataBean.setProjAddress(projectList.get(0).getAddress());
			 projectDataBean.setProjClientName(projectList.get(0).getClientName());
			 projectDataBean.setProjContactNO(projectList.get(0).getContactNumber());
			 projectDataBean.setProjDate(projectList.get(0).getProjectDate1());
			 projectDataBean.setProjDate1(projectList.get(0).getProjectDate2());
			 projectDataBean.setProjDate2(projectList.get(0).getProjectDate2());
			 projectDataBean.setProjProjectValue(projectList.get(0).getProjectValue());
			 projectDataBean.setProjLocation(projectList.get(0).getLocation());
			 projectDataBean.setProjDescription(projectList.get(0).getDescription());
			 }
	 }catch(Exception e){
	e.printStackTrace();
	 }
}
}
public String projectEdit(){
	System.out.println("Inside calling");
	if(projectDataBean.getProjProjectName() !=null){
		projectViewList1=null;
		
	ApplicationContext ctx=(FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()));
	LiusenController controller=(LiusenController) ctx.getBean("controller");
	 projectViewList1=new ArrayList<ProjectDataBean>();
	 projectList=controller.getProjectListView(projectDataBean,projectViewList1);
	 System.out.println(projectList.size()+"---"+projectViewList1.size());
	 if(projectList.size() > 0){
		 projectDataBean.setProjAddress(projectList.get(0).getAddress());
		 projectDataBean.setProjClientName(projectList.get(0).getClientName());
		 projectDataBean.setProjContactNO(projectList.get(0).getContactNumber());
		 projectDataBean.setProjDate(projectList.get(0).getProjectDate1());
		 projectDataBean.setProjDate1(projectList.get(0).getProjectDate2());
		 projectDataBean.setProjDate2(projectList.get(0).getProjectDate2());
		 projectDataBean.setProjProjectValue(projectList.get(0).getProjectValue());
		 projectDataBean.setProjLocation(projectList.get(0).getLocation());
		 projectDataBean.setProjDescription(projectList.get(0).getDescription());
		 }
	controller.dropprojectValues(projectDataBean);
	}
	setValid(false);
	return "editProject";
	
}
public String editSubmit(){
	
	if (validate2(true)) 
	{
		try
		{
			ApplicationContext ctx=(FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()));
			LiusenController controller=(LiusenController) ctx.getBean("controller");
			String s=controller.updateProject(projectDataBean);
			if(s.equalsIgnoreCase("Sucesss")){
			setValid(true);
			}else{
				setValid(false);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	return "";
	}
	

private boolean validate2(boolean flag) {
	boolean valid = true;
	String fieldName;
	FacesContext fc = FacesContext.getCurrentInstance();
	if (projectDataBean.getProjDate()==null) {
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("proDate").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Choose the Date."));
		}
		valid = false;
	}
	if (projectDataBean.getProjDate1()==null) {
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("date1").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Choose the Start Date."));
		}
		valid = false;
	}
	if (projectDataBean.getProjDate2()==null) {
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("date2").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Choose the Finish Date."));
		}
		valid = false;
	}
	if (projectDataBean.getProjProjectName().equalsIgnoreCase("project")) {
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("proName").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Enter the Project Name."));
		}
		valid = false;
	} 
	if (StringUtils.isBlank(projectDataBean.getProjClientName())) {
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("proClient").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Enter the Client Name."));
		}
		valid = false;
	} else if (StringUtils.isNotEmpty(projectDataBean.getProjClientName())) {
		if (!CommonValidate.validateName(projectDataBean.getProjClientName())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("proClient").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Client Name."));
			}
			valid = false;
		}
	}
	if(StringUtils.isBlank(projectDataBean.getProjContactNO())){
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("proContact").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Enter the Contact Number."));
		}
		valid = false;
	} else if (StringUtils.isNotEmpty(projectDataBean.getProjContactNO())) {
		if (!CommonValidate.isNumeric(projectDataBean.getProjContactNO())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("proContact").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Contact Number."));
			}
			valid = false;
		}
	}
	if (StringUtils.isEmpty(projectDataBean.getProjAddress())) {
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("proAddress").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Enter the Address."));
		}
		valid = false;
	} 
	if (StringUtils.isEmpty(projectDataBean.getProjLocation())) {
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("proLocation").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Enter the Location."));
		}
		valid = false;
	}/* else if (StringUtils.isNotEmpty(projectDataBean.getProjLocation())) {
		if (!CommonValidate.isNumeric(projectDataBean.getProjLocation())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("proLocation").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Location."));
			}
			valid = false;
		}
	}*/
	if (projectDataBean.getProjProjectManager().equalsIgnoreCase("11100")) {
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("proPM").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Choose the Project Manager."));
		}
		valid = false;
	}
	if(projectDataBean.getProjMarketing().equalsIgnoreCase("11200")){
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("proM").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Choose the Marketing Manager."));
		}
		valid = false;
	}
	if (projectDataBean.getProjAdminProject().equalsIgnoreCase("11300")) {
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("proAdmin").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Choose the Admin Project."));
		}
		valid = false;
	} 
	/*if (StringUtils.isBlank(projectDataBean.getProjProjectValue())) {
		if(flag){
		fieldName = CommonValidate.findComponentInRoot("proValue").getClientId(fc);
		fc.addMessage(fieldName, new FacesMessage("Please Enter the Project Value."));
		}
		valid = false;
	} else if (StringUtils.isNotEmpty(projectDataBean.getProjProjectValue())) {
		if (!CommonValidate.validateNumber(projectDataBean.getProjProjectValue())) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("proValue").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Enter the valid Project Value."));
			}
			valid = false;
		}
	}*/
	return valid;
}
public String cancel() {
	System.out.println("---- Inside Cancel Method Calling------");
	return "cancelSuccess";

}
public String delete(){
	
		try
		{
			ApplicationContext ctx=(FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()));
			LiusenController controller=(LiusenController) ctx.getBean("controller");
			String s=controller.deleteProject(projectDataBean);
			if(s.equalsIgnoreCase("Sucess")){
			setValid(true);
			}else{
				setValid(false);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	return "";
	}
/**
 * @return the editflag
 */
public boolean isEditflag() {
	return editflag;
}
/**
 * @param editflag the editflag to set
 */
public void setEditflag(boolean editflag) {
	this.editflag = editflag;
}
}