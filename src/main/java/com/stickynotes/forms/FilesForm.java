/**
 * 
 */
package com.stickynotes.forms;

import java.util.List;

/**
 * @author Varun
 *
 */
public class FilesForm {
	
	/**
	 * loggedInUser
	 */
	private String loggedInUser;
	
	/**
	 * filesToList
	 */
	private List<String> filesToList;
	
	/**
	 * fileToUpload
	 */
	private String fileToUpload;

	/**
	 * @return the loggedInUser
	 */
	public String getLoggedInUser() {
		return loggedInUser;
	}

	/**
	 * @param loggedInUser the loggedInUser to set
	 */
	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	/**
	 * @return the filesToList
	 */
	public List<String> getFilesToList() {
		return filesToList;
	}

	/**
	 * @param filesToList the filesToList to set
	 */
	public void setFilesToList(List<String> filesToList) {
		this.filesToList = filesToList;
	}

	/**
	 * @return the fileToUpload
	 */
	public String getFileToUpload() {
		return fileToUpload;
	}

	/**
	 * @param fileToUpload the fileToUpload to set
	 */
	public void setFileToUpload(String fileToUpload) {
		this.fileToUpload = fileToUpload;
	}
	
}
