package com.capgemini.omnichannel.omnisession.dto;


public class Environment {

	String url;
	String name;
	
	
	@Override
	public String toString() {
		return "Environment [url=" + url + ", name=" + name + "]";
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
