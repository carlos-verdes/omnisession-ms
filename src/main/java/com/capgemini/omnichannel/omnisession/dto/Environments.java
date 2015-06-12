package com.capgemini.omnichannel.omnisession.dto;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix="omnisession")
public class Environments {
	
	Map<String,Environment> environments;

	public Map<String, Environment> getEnvironments() {
		return environments;
	}

	public void setEnvironments(Map<String, Environment> environments) {
		this.environments = environments;
	}

	@Override
	public String toString() {
		return "Environments [environments=" + environments + "]";
	}
	
	
	

}
