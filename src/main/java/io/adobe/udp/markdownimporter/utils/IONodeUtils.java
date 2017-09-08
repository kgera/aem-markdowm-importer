package io.adobe.udp.markdownimporter.utils;

import io.adobe.udp.markdownimporter.BranchRootInfo;
import io.adobe.udp.markdownimporter.FolderPageData;
import io.adobe.udp.markdownimporter.GithubData;
import io.adobe.udp.markdownimporter.PageData;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.resource.JcrResourceConstants;

import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;

public class IONodeUtils {
		
	public static String replaceDotsInPath(String path) {
		return path.replace(".", "_");
	}
	
	public static String trimName(String name) {
		if(name.startsWith("/")) {
			return name.substring(1);
		}
		return name;
		
	}
	
	public static String removeParams(String path) {
		if(path.contains("?")) {
			return path.substring(0, path.lastIndexOf("?"));
		}
		return path;
	}
	
	public static String getBranchPagePath(String branch, BranchRootInfo rootInfo) {
		String rootWithoutFirstSlash = removeFirstSlash(rootInfo.getRootPath()).replaceFirst(rootInfo.getCommonPrefix(), "");
		if(rootInfo.isFirst()) {
				return trimName(rootWithoutFirstSlash);
		} else {
			return branch + "/" + rootWithoutFirstSlash;
		}
	}
	
	public static String getBranchPageName(BranchRootInfo rootInfo) {
				return replaceDotsInPath(trimName(rootInfo.getBranchPageName()));
	}
	
	public static String getBranchPageName(String branch) {
		return replaceDotsInPath(trimName(branch).replace("/", "_"));
	}
	
	public static void populatePath(String path, Session session) {
		
	}
	
	public static void addPlaceHolderTemplate(String rootPath, String filePath, String githubFilePath, Set<String> files, Map<String, PageData> pages, FolderPageData pageData) {
		String parentPath = getParentPath(filePath);
		if(!rootPath.equals(filePath) && !rootPath.equals(parentPath)) {
			if(!files.contains(getParentPath(githubFilePath) + GithubConstants.MARKDOWN_EXTENSION) && !pages.containsKey(parentPath)) {
				pageData.setTitle(extractName(parentPath));
				pages.put(parentPath, pageData);
			}
			addPlaceHolderTemplate(rootPath, parentPath, getParentPath(githubFilePath), files, pages, pageData);
		}
	}
	

	public static String getBranchRootPath(String githubRoot, String branch) {
		return githubRoot + "/" + getBranchPageName(branch);
	}
	
	public static String removeFirstSlash(String path) {
		if(path.startsWith("/")) {
			return path.substring(1);
		}
		return path;
	}
	
	public static String getFileFolder(GithubData githubData, String filePath) {
		return githubData.getRawContentBranchRootUrl() + getFileFolder(filePath);
	}

	public static String stripFromExtension(String path) {
		String result = new String(path);
		if(path.toLowerCase().endsWith(GithubConstants.MARKDOWN_EXTENSION)) {
			result = path.substring(0, path.toLowerCase().lastIndexOf(GithubConstants.MARKDOWN_EXTENSION));
		}
		if (result.endsWith("/")) {
			return result.substring(0, result.lastIndexOf("/"));
		} 
		return result;
	}
	
	private static String getFileFolder(String filePath) {
		if(filePath.contains("/")) {
			return removeFirstSlash(filePath.substring(0, filePath.lastIndexOf("/") + 1));
		}
		return StringUtils.EMPTY;
	}
	
	
	public static String removeSlashAtEnd(String path) {
		if(path.endsWith("/")) {
			return path.substring(0, path.length() - 1);
		}
		return path;
	}
	
	public static String getBranchPageName(String rootPath, String branchName) {
		return rootPath + "/" + IONodeUtils.getBranchPageName(branchName);
	}
	
	private static String getParentPath(String path) {
		if(StringUtils.isBlank(path) || !path.contains("/")) {
			return StringUtils.EMPTY;
		}
		return path.substring(0, path.lastIndexOf("/"));
	}
	
	public static String extractName(String path) {
		if(StringUtils.isBlank(path)) {
			return StringUtils.EMPTY;
		}
		if(!path.contains("/")) {
			return path;
		}
		return path.substring(path.lastIndexOf("/") + 1);
	}
	
	public static String escapeBackslash(String path) {
		return path.replace("\\", "/");
	}
}