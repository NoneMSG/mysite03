package com.jx372.mysite.vo;

public class AdminVo {
	private String title;
	private String welcome;
	private String profilepath;
	private String description;
	
	@Override
	public String toString() {
		return "AdminVo [title=" + title + ", welcome=" + welcome + ", profilepath=" + profilepath + ", description="
				+ description + "]";
	}
	public final String getTitle() {
		return title;
	}
	public final void setTitle(String title) {
		this.title = title;
	}
	public final String getWelcome() {
		return welcome;
	}
	public final void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	public final String getProfilepath() {
		return profilepath;
	}
	public final void setProfilepath(String profilepath) {
		this.profilepath = profilepath;
	}
	public final String getDescription() {
		return description;
	}
	public final void setDescription(String description) {
		this.description = description;
	}
	
}
