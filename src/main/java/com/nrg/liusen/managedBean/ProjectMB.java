package com.nrg.liusen.managedBean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.nrg.liusen.controler.LiusenController;
import com.nrg.liusen.domain.ProjectDataBean;
import com.nrg.liusen.exception.LiusenException;
import com.nrg.liusen.util.CommonValidate;
/**
 * 
 * @author Robert Arjun
 * @Date 27-10-2015
 * @copyright NRG This class works as a ManagedBean
 *
 */
@ManagedBean(name ="projectMB")
@RequestScoped
public class ProjectMB {

	ProjectDataBean projectDataBean = new ProjectDataBean();
	public boolean valid=false;
	public String message;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return the projectDataBean
	 */
	public ProjectDataBean getProjectDataBean() {
		return projectDataBean;
	}

	/**
	 * @param projectDataBean the projectDataBean to set
	 */
	public void setProjectDataBean(ProjectDataBean projectDataBean) {
		this.projectDataBean = projectDataBean;
	}
	/**
	 * productPageLoad Method is used for redirect to product registration form
	 * page
	 * 
	 * @return success go to product registration form page
	 */
	public String projectPageLoad() {
		System.out.println("Inside projectPageLoad Method Calling");
		valid=false;
		setMessage("");
		projectDataBean.setProjDate(null);
		projectDataBean.setProjAddress("");
		projectDataBean.setProjAdminProject("");
		projectDataBean.setProjClientName("");
		projectDataBean.setProjContactNO("");
		projectDataBean.setProjDescription("");
		projectDataBean.setProjLocation("");
		projectDataBean.setProjMarketing("");
		projectDataBean.setProjProjectManager("");
		projectDataBean.setProjProjectName("");
		projectDataBean.setProjProjectValue("");
		projectDataBean.setIamgepath("");
		projectDataBean.setProjDate1(null);
		projectDataBean.setProjDate2(null);
		setUploadValidate("");
        files.clear();
        list1.clear();
        setFile(null);
		ApplicationContext ctx=(FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()));
		LiusenController controller=(LiusenController) ctx.getBean("controller");
		controller.dropprojectValues(projectDataBean);
		return "loadProjectPage";
		
	}
	/**
	 * submit Method is used to get the values from UI to Controller
	 * 
	 * @return submitSuccess go to controller
	 * @return submitFailure redirect same page
	 */
	public String submit()
	{
		try
		{
			System.out.println("----------Inside submit Method Calling-----");
			if (validate(true)) 
			{
				System.out.println("After Validation inside Submit method");
				ApplicationContext ctx=(FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()));
				LiusenController controller=(LiusenController) ctx.getBean("controller");
				projectDataBean.setFile(file);
				controller.insertProjectValues(projectDataBean);
				setValid(true);
				return "";
			}
			return "";
		}
		catch (LiusenException e)
		{
			e.printStackTrace();
			setMessage(e.getMessage());
			return "";
		}	
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}		
	}
	/**
	 * This method is used to validate data
	 * 
	 * @param valid
	 * @return false show error message
	 * @return true not showing error message
	 */
	private boolean validate(boolean flag) {
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
		if (StringUtils.isBlank(projectDataBean.getProjProjectName())) {
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
		/*if (files.isEmpty()) {
			if(flag){
			fieldName = CommonValidate.findComponentInRoot("image").getClientId(fc);
			fc.addMessage(fieldName, new FacesMessage("Please Choose the Image."));
			}
			valid = false;
		} */
		return valid;
	}

	/**
	 * reset Method is used to reset the input text box values
	 * 
	 * @return
	 */
	public String reset() {
		System.out.println("Inside Rest Method Calling");
		projectDataBean.setProjDate(null);
		setMessage("");
		projectDataBean.setProjAddress("");
		projectDataBean.setProjAdminProject("");
		projectDataBean.setProjClientName("");
		projectDataBean.setProjContactNO("");
		projectDataBean.setProjDescription("");
		projectDataBean.setProjLocation("");
		projectDataBean.setProjMarketing("");
		projectDataBean.setProjProjectManager("");
		projectDataBean.setProjProjectName("");
		projectDataBean.setProjProjectValue("");
		projectDataBean.setIamgepath("");
		projectDataBean.setProjDate1(null);
		projectDataBean.setProjDate2(null);
		setUploadValidate("");
        files.clear();
        list1.clear();
        setFile(null);
		valid=false;
		return "";
	}
	/**
	 * cancel Method is used for redirect to home page
	 * 
	 * @return
	 */

	public String cancel() {
		System.out.println("---- Inside Cancel Method Calling------");
		return "cancelSuccess";

	}
	
	public void projectImage(ValueChangeEvent v)
	{
		System.out.println("project - > "+v.getNewValue());
		setMessage("");
		projectDataBean.setProjProjectName(v.getNewValue().toString());
		ApplicationContext ctx=(FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()));
		LiusenController controller=(LiusenController) ctx.getBean("controller");
		controller.projectImage(projectDataBean);
	}
	
	public void paint2(OutputStream out, Object data) throws IOException
	{
		String s="C://product/";
		BufferedImage img = ImageIO.read(new File(s+""+projectDataBean.getIamgepath()));
	    ImageIO.write(img,"png",out);
	}
	
	public String uploadValidate;
	private ArrayList<UploadedImage> files = new ArrayList<UploadedImage>();
	ArrayList<String> list1=new ArrayList<String>();
	UploadedFile file;
	public String getUploadValidate() {
		return uploadValidate;
	}

	public void setUploadValidate(String uploadValidate) {
		this.uploadValidate = uploadValidate;
	}

	public ArrayList<UploadedImage> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<UploadedImage> files) {
		this.files = files;
	}

	public ArrayList<String> getList1() {
		return list1;
	}

	public void setList1(ArrayList<String> list1) {
		this.list1 = list1;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void paint1(OutputStream stream, Object object) throws IOException  
    {
    	System.out.println("----------inside paint----------");
    	stream.write(getFiles().get((Integer) object).getData());
    	System.out.println("file -- > "+getFiles().get((Integer) object).getData());
        stream.close();
    }
	public String clearUploadData() {
		System.out.println("clear the upload photo");
    	setUploadValidate("");
        files.clear();
        list1.clear();
        setFile(null);
        return null;
    }
	public int getSize() {
        if (getFiles().size() > 0) {
            return getFiles().size();
        } else {
            return 0;
        }
    }
 
    public long getTimeStamp() {
        return System.currentTimeMillis();
    }
    public void listener(FileUploadEvent event) throws Exception 
	{
    	/*files.clear();
        list1.clear();
        setFile(null);
    	*/try
    	{    
		setUploadValidate("");
		UploadedFile item = event.getUploadedFile();
        if(files.size()>=1)
        {
    	   throw new Exception("Only one Image can upload");
        }        
        UploadedImage file = new UploadedImage();
        file.setLength(item.getData().length);
        file.setName(item.getName());
        file.setData(item.getData());
        files.add(file);
        File fullFile  = new File(item.getName());   
        this.file = event.getUploadedFile();
        System.out.println("files----->"+getFiles());
        System.out.println("name----->"+item.getName());
        System.out.println("inputstream----->"+getFile().getInputStream());
    	}
    	catch(Exception e)
    	{
    		setUploadValidate(e.getMessage());
    	}
    }
}
