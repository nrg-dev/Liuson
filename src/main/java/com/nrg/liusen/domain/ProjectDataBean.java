package com.nrg.liusen.domain;

import java.util.Date;
import java.util.List;

import org.richfaces.model.UploadedFile;

/**
 * 
 * @author Robert Arjun
 * @date 27-10-2015
 * @copyright NRG This class used to hold the data( POJO Class )
 */
public class ProjectDataBean {

	private String projProjectName;
	private Date projDate;
	private String projClientName;
	private String projContactNO;
	private String projAddress;
	private String projLocation;
	private String projProjectManager;
	private String projMarketing;
	private String projAdminProject;
	private String projProjectValue;
	private String projDescription;
	private String fileName;
	private Date projDate1;
	private Date projDate2;
	private List<String> projectmanager=null;
	private List<String> marketingmanager=null;
	private List<String> adminproject=null;
	private List<String> projects=null;
	private String iamgepath;
	private int projetid=0;
	UploadedFile file;
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public int getProjetid() {
		return projetid;
	}
	public void setProjetid(int projetid) {
		this.projetid = projetid;
	}
	public String getIamgepath() {
		return iamgepath;
	}
	public void setIamgepath(String iamgepath) {
		this.iamgepath = iamgepath;
	}
	public List<String> getProjects() {
		return projects;
	}
	public void setProjects(List<String> projects) {
		this.projects = projects;
	}
	public List<String> getProjectmanager() {
		return projectmanager;
	}
	public void setProjectmanager(List<String> projectmanager) {
		this.projectmanager = projectmanager;
	}
	public List<String> getMarketingmanager() {
		return marketingmanager;
	}
	public void setMarketingmanager(List<String> marketingmanager) {
		this.marketingmanager = marketingmanager;
	}
	public List<String> getAdminproject() {
		return adminproject;
	}
	public void setAdminproject(List<String> adminproject) {
		this.adminproject = adminproject;
	}
	public Date getProjDate1() {
		return projDate1;
	}
	public void setProjDate1(Date projDate1) {
		this.projDate1 = projDate1;
	}
	public Date getProjDate2() {
		return projDate2;
	}
	public void setProjDate2(Date projDate2) {
		this.projDate2 = projDate2;
	}
	/**
	 * @return the projProjectName
	 */
	public String getProjProjectName() {
		return projProjectName;
	}
	/**
	 * @param projProjectName the projProjectName to set
	 */
	public void setProjProjectName(String projProjectName) {
		this.projProjectName = projProjectName;
	}
	/**
	 * @return the projDate
	 */
	public Date getProjDate() {
		return projDate;
	}
	/**
	 * @param projDate the projDate to set
	 */
	public void setProjDate(Date projDate) {
		this.projDate = projDate;
	}
	/**
	 * @return the projClientName
	 */
	public String getProjClientName() {
		return projClientName;
	}
	/**
	 * @param projClientName the projClientName to set
	 */
	public void setProjClientName(String projClientName) {
		this.projClientName = projClientName;
	}
	/**
	 * @return the projContactNO
	 */
	public String getProjContactNO() {
		return projContactNO;
	}
	/**
	 * @param projContactNO the projContactNO to set
	 */
	public void setProjContactNO(String projContactNO) {
		this.projContactNO = projContactNO;
	}
	/**
	 * @return the projAddress
	 */
	public String getProjAddress() {
		return projAddress;
	}
	/**
	 * @param projAddress the projAddress to set
	 */
	public void setProjAddress(String projAddress) {
		this.projAddress = projAddress;
	}
	/**
	 * @return the projLocation
	 */
	public String getProjLocation() {
		return projLocation;
	}
	/**
	 * @param projLocation the projLocation to set
	 */
	public void setProjLocation(String projLocation) {
		this.projLocation = projLocation;
	}
	/**
	 * @return the projProjectManager
	 */
	public String getProjProjectManager() {
		return projProjectManager;
	}
	/**
	 * @param projProjectManager the projProjectManager to set
	 */
	public void setProjProjectManager(String projProjectManager) {
		this.projProjectManager = projProjectManager;
	}
	/**
	 * @return the projMarketing
	 */
	public String getProjMarketing() {
		return projMarketing;
	}
	/**
	 * @param projMarketing the projMarketing to set
	 */
	public void setProjMarketing(String projMarketing) {
		this.projMarketing = projMarketing;
	}
	/**
	 * @return the projAdminProject
	 */
	public String getProjAdminProject() {
		return projAdminProject;
	}
	/**
	 * @param projAdminProject the projAdminProject to set
	 */
	public void setProjAdminProject(String projAdminProject) {
		this.projAdminProject = projAdminProject;
	}
	/**
	 * @return the projProjectValue
	 */
	public String getProjProjectValue() {
		return projProjectValue;
	}
	/**
	 * @param projProjectValue the projProjectValue to set
	 */
	public void setProjProjectValue(String projProjectValue) {
		this.projProjectValue = projProjectValue;
	}
	/**
	 * @return the projDescription
	 */
	public String getProjDescription() {
		return projDescription;
	}
	/**
	 * @param projDescription the projDescription to set
	 */
	public void setProjDescription(String projDescription) {
		this.projDescription = projDescription;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
