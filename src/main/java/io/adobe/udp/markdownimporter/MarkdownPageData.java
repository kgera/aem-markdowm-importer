package io.adobe.udp.markdownimporter;

import io.adobe.udp.markdownimporter.utils.GithubConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.sling.jcr.resource.JcrResourceConstants;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.NameConstants;

public class MarkdownPageData implements PageData {
	
	private String resourceType;
	private String template;
	private String designPath;
	private List<String> yamlProperties;
	private String title;
	private List<HashMap<String, String>> components;
	private Map<String, File> images;
	private String githubUrl;
	private String branch;
	
	public MarkdownPageData(String resourceType, String template, String designPath) {
		this.resourceType = resourceType;
		this.template = template;
		this.designPath = designPath;
		this.components = new ArrayList<HashMap<String, String>>();
		this.yamlProperties = new ArrayList<String>();
		this.images = new HashMap<String, File>();
	}	
	
	public MarkdownPageData(List<String> yamlProperties, String title, List<HashMap<String, String>> components) {
		this.yamlProperties = yamlProperties;
		this.title = title;
		this.components = components;
	}
	
	public List<String> getYamlProperties() {
		return yamlProperties;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<HashMap<String, String>> getComponents() {
		return components;
	}
	
	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}
	
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	public Map<String, File> getImages() {
		return images;
	}

	public Map<String, Object> toContent() {
		Map<String, Object> markdownContent = new TreeMap<String, Object>();
		int i = 0;
		for(HashMap<String, String> component : components) {
			markdownContent.put("element_" + i++ , component);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("markdownrenderer", markdownContent);
		result.put(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, resourceType);
		result.put(NameConstants.PN_DESIGN_PATH, designPath);
		result.put(NameConstants.PN_TEMPLATE, template);
		result.put(GithubConstants.BRANCH, branch);
		result.put("imported", true);
		result.put("githubUrl", githubUrl);
		result.put(JcrConstants.JCR_TITLE, title);
		return result;
	}

}