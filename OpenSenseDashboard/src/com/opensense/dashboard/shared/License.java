package com.opensense.dashboard.shared;

import java.util.Date;

import org.json.JSONObject;

import com.google.gwt.user.client.rpc.IsSerializable;

public class License implements IsSerializable{

	private int id;
	private String shortName;
	private String fullName;
	private String version;
	private String referenceLink;
	private String description;
	private Date created;
	private boolean allowsRedistribution;
	private boolean allowsDerivatives;
	private boolean noncommercial;
	private boolean requiresAttribution;
	private boolean requiresShareAlike;
	private boolean requiresKeepOpen;
	private boolean requiresChangeNote;
	
	public License() {
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}


	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}


	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}


	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}


	/**
	 * @return the referenceLink
	 */
	public String getReferenceLink() {
		return referenceLink;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @return the allowsRedistribution
	 */
	public boolean allowsRedistribution() {
		return allowsRedistribution;
	}


	/**
	 * @return the allowsDerivatives
	 */
	public boolean allowsDerivatives() {
		return allowsDerivatives;
	}


	/**
	 * @return the noncommercial
	 */
	public boolean noncommercial() {
		return noncommercial;
	}


	/**
	 * @return the requiresAttribution
	 */
	public boolean requiresAttribution() {
		return requiresAttribution;
	}


	/**
	 * @return the requiresShareAlike
	 */
	public boolean requiresShareAlike() {
		return requiresShareAlike;
	}


	/**
	 * @return the requiresKeepOpen
	 */
	public boolean requiresKeepOpen() {
		return requiresKeepOpen;
	}


	/**
	 * @return the requiresChangeNote
	 */
	public boolean requiresChangeNote() {
		return requiresChangeNote;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}


	/**
	 * @param referenceLink the referenceLink to set
	 */
	public void setReferenceLink(String referenceLink) {
		this.referenceLink = referenceLink;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @param allowsRedistribution the allowsRedistribution to set
	 */
	public void setAllowsRedistribution(boolean allowsRedistribution) {
		this.allowsRedistribution = allowsRedistribution;
	}

	/**
	 * @param allowsDerivatives the allowsDerivatives to set
	 */
	public void setAllowsDerivatives(boolean allowsDerivatives) {
		this.allowsDerivatives = allowsDerivatives;
	}

	/**
	 * @param noncommercial the noncommercial to set
	 */
	public void setNoncommercial(boolean noncommercial) {
		this.noncommercial = noncommercial;
	}

	/**
	 * @param requiresAttribution the requiresAttribution to set
	 */
	public void setRequiresAttribution(boolean requiresAttribution) {
		this.requiresAttribution = requiresAttribution;
	}

	/**
	 * @param requiresShareAlike the requiresShareAlike to set
	 */
	public void setRequiresShareAlike(boolean requiresShareAlike) {
		this.requiresShareAlike = requiresShareAlike;
	}

	/**
	 * @param requiresKeepOpen the requiresKeepOpen to set
	 */
	public void setRequiresKeepOpen(boolean requiresKeepOpen) {
		this.requiresKeepOpen = requiresKeepOpen;
	}


	/**
	 * @param requiresChangeNote the requiresChangeNote to set
	 */
	public void setRequiresChangeNote(boolean requiresChangeNote) {
		this.requiresChangeNote = requiresChangeNote;
	}

}
