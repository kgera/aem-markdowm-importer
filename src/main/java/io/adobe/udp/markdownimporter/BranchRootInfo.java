package io.adobe.udp.markdownimporter;

import io.adobe.udp.markdownimporter.utils.IONodeUtils;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class BranchRootInfo {
	
	public static final String COMMON_PREFIX = "commonPrefix";
	
	private String rootGithubFile;
	private String rootPath;
	private String rootPrefix;
	private String commonPrefix;
	private String branchPageName;
	private boolean first;
	
	public BranchRootInfo(String documentationRootPath, String rooPath, String rootPrefix, String commonPrefix,
			boolean first) {
		this.rootGithubFile = rooPath;
		this.rootPath = io.adobe.udp.markdownimporter.utils.IONodeUtils.stripFromExtension(rooPath);
		this.commonPrefix = commonPrefix;
		this.first = first;
		this.branchPageName = extractName();
	}
	
	public static BranchRootInfo createBranchRootInfo(String rootPage,
			List<String> configPages, String branch,
			Set<String> pages, boolean first, boolean hasPages) {
		String rootPath = branch;
		if(hasPages && pages.size() > 0) {
			rootPath = pages.iterator().next();
		}
		String commonPrefix = StringUtils.getCommonPrefix(pages.toArray(new String[pages.size()]));
		if(!hasPages) {
			return new BranchRootInfo(rootPage, branch, null, null,
					 first);
		}
		String rootPrefix = rootPath.contains("/") ? rootPath.substring(0, rootPath.lastIndexOf("/"))
				: rootPath;
		return new BranchRootInfo(rootPage, rootPath, rootPrefix, commonPrefix,
				 first);
	}


	private String extractName() {
		if(!rootPath.contains("/")) {
			return rootPath;
		}
		return rootPath.substring(rootPath.lastIndexOf("/") + 1);
	}

	public String getRootPath() {
		return rootPath;
	}

	public String getRootPrefix() {
		return rootPrefix;
	}

	public String getCommonPrefix() {
		return commonPrefix;
	}

	public boolean isFirst() {
		return first;
	}
	
	public String getBranchPathFirstElement() {
		String result = null;
		if(rootPath.contains("/")) {			
			result = rootPath.substring(rootPath.lastIndexOf("/") + 1);
		} else {
			result = rootPath;
		}
		return IONodeUtils.replaceDotsInPath(IONodeUtils.removeFirstSlash(result));
	}
	
	
	public String getInternalPath(String githubPath) {
		String internalPath = IONodeUtils.removeFirstSlash(githubPath);
		if(StringUtils.isNotBlank(rootPath)) {
			internalPath = IONodeUtils.removeFirstSlash(internalPath.replaceFirst(rootPath, ""));
		}
		if(StringUtils.isNoneBlank(rootPath) && rootPath.contains("/")) {
			internalPath = internalPath.replaceFirst(rootPath.substring(0, rootPath.lastIndexOf("/")), "");
		}
		if(StringUtils.isNotBlank(commonPrefix)) {
			internalPath = internalPath.replaceFirst(commonPrefix, "");
		}
		return internalPath;
	}

	public String getBranchPageName() {
		return branchPageName;
	}

	public String getRootGithubFile() {
		return rootGithubFile;
	}

}