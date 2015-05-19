package com.stone.java._socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchCrawler implements Runnable {

	private HashMap<String, ArrayList<String>> disallowListCaches = new HashMap<String, ArrayList<String>>();
	ArrayList<String> errorLists = new ArrayList<String>();// 错误信息
	ArrayList<String> results = new ArrayList<String>();// 搜索到的结果
	String startUrl;// 开始搜索的起点
	int maxUrl;// 最大处理的URL数
	String searchString;// 要搜索的字符串（英文）
	boolean caseSensitive = false;// 是否区分大小写
	boolean limitHost = false;// 是否在限制的主机内搜索

	public SearchCrawler() {
	}

	public SearchCrawler(String startUrl, int maxUrl, String searchString) {
		this.startUrl = startUrl;
		this.maxUrl = maxUrl;
		this.searchString = searchString;
	}

	public void run() {
		crawl(startUrl, maxUrl, searchString, limitHost, caseSensitive);
	}

	private ArrayList<String> crawl(String startUrl, int maxUrl,
			String searchString, boolean limitHost, boolean caseSensitive) {
		System.out.println("searchString=" + searchString);
		HashSet<String> crawledLists = new HashSet<String>();
		LinkedList<String> toCrawlLists = new LinkedList<String>();
		if (maxUrl < 1) {
			errorLists.add("Invalid Max URLs Value.");
			System.out.println("Invalid Max URL Value.");
		}
		if (searchString.length() < 1) {
			errorLists.add("Missing Search String.");
			System.out.println("Missing search String");
		}
		if (errorLists.size() > 0) {
			System.out.println("error!!!");
			return errorLists;
		}

		// 从开始URL中移除www
		startUrl = removeWwwFromUrl(startUrl);
		toCrawlLists.add(startUrl);
		while (toCrawlLists.size() > 0) {
			if (maxUrl != -1) {
				if (crawledLists.size() == maxUrl) {
					break;
				}
			}

			String url = toCrawlLists.iterator().next();
			toCrawlLists.remove(url);
			URL verifiedUrl = verifiedUrl(url);
			if (!isRobotAllowed(verifiedUrl)) {
				continue;
			}
			crawledLists.add(url);
			String pageContents = downloadPage(verifiedUrl);
			if(pageContents!=null && pageContents.length()>0){
				ArrayList<String> links = retrieveLinks(verifiedUrl,pageContents,crawledLists,limitHost);
			}
		}
		return null;
	}

	private ArrayList<String> retrieveLinks(URL verifiedUrl,
			String pageContents, HashSet<String> crawledLists,
			boolean limitHost) {
		Pattern p = Pattern.compile("<a//s+href//s*=//s*\"?(.*?)[\"|>]",Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(pageContents);
		ArrayList<String> linkList = new ArrayList<String>();
		while(m.find()){
			String link = m.group(1).trim();
			if(link.length()<1){
				continue;
			}
			if(link.charAt(0)=='#'){
				continue;
			}
			if(link.indexOf("mailto:")!=-1){
				continue;
			}
			if(link.indexOf("://")==-1){
				if(link.charAt(0)=='/'){
					link = "http://"+verifiedUrl.getHost()+":"+verifiedUrl.getPath()+link;
				}
			}else{
			}
		}
		return null;
	}

	private String downloadPage(URL verifiedUrl) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(verifiedUrl.openStream()));
			String line;
			StringBuffer pageBuffer = new StringBuffer();
			while((line=reader.readLine())!=null){
				pageBuffer.append(line);
			}
			return pageBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 描述:检测robot是否允许访问给出的URL 
	 * @param verifiedUrl
	 * @return
	 * @author     石涛
	 * date        2014-9-18
	 * --------------------------------------------------
	 * 修改人    	      修改日期       修改描述
	 * 石涛                    2014-9-18       创建
	 * --------------------------------------------------
	 * @Version  Ver1.0
	 */
	private boolean isRobotAllowed(URL verifiedUrl) {
		//获取URL的主机
		String host = verifiedUrl.getHost().toLowerCase();
		//获取主机不允许搜索的URL缓存
		ArrayList<String> disallowlist = disallowListCaches.get(host);
		if(disallowlist == null){
			disallowlist = new ArrayList<String>();
			try {
				URL robotFileUrl = new URL("http://"+host+"/robots.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(robotFileUrl.openStream()));
				String line;
				while((line = reader.readLine())!=null){
					//是否包含"Disallow"
					if(line.indexOf("Disallow:")==0){
						//获取不允许访问的路径
						String disallowPath = line.substring("Disallow:".length());
						int commentIndex = disallowPath.indexOf("#");
						if(commentIndex!=-1){
							//去掉注释
							disallowPath = disallowPath.substring(0,commentIndex);
						}
						disallowPath = disallowPath.trim();
						disallowlist.add(disallowPath);
					}
				}
				//缓存主机不允许访问的路径
				disallowListCaches.put(host, disallowlist);
			} catch (Exception e) {
				e.printStackTrace();
				return true;
			}
		}
		String file = verifiedUrl.getFile();
		for(int i =0;i<disallowlist.size();i++){
			String disallow = disallowlist.get(i);
			if(file.startsWith(disallow)){
				return false;
			}
		}
		return true;
	}

	private URL verifiedUrl(String url) {
		if (!url.toLowerCase().startsWith("http://"))
			return null;
		URL verifiedUrl = null;
		try {
			verifiedUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		return verifiedUrl;
	}

	/**
	 * 
	 * 描述: 从URL中去掉"www"
	 * 
	 * @param startUrl
	 * @return
	 * @author 石涛 date 2014-9-18
	 *         -------------------------------------------------- 修改人 修改日期 修改描述
	 *         石涛 2014-9-18 创建
	 *         --------------------------------------------------
	 * @Version Ver1.0
	 */
	private String removeWwwFromUrl(String startUrl) {
		int index = startUrl.toLowerCase().indexOf("://www.");
		if (index != -1) {
			return startUrl.substring(0, index + 3)
					+ startUrl.substring(index + 7);
		}
		return startUrl;
	}

	public HashMap<String, ArrayList<String>> getDisallowListCaches() {
		return disallowListCaches;
	}

	public void setDisallowListCaches(
			HashMap<String, ArrayList<String>> disallowListCaches) {
		this.disallowListCaches = disallowListCaches;
	}

	public ArrayList<String> getErrorLists() {
		return errorLists;
	}

	public void setErrorLists(ArrayList<String> errorLists) {
		this.errorLists = errorLists;
	}

	public ArrayList<String> getResults() {
		return results;
	}

	public void setResults(ArrayList<String> results) {
		this.results = results;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	public int getMaxUrl() {
		return maxUrl;
	}

	public void setMaxUrl(int maxUrl) {
		this.maxUrl = maxUrl;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public boolean isLimitHost() {
		return limitHost;
	}

	public void setLimitHost(boolean limitHost) {
		this.limitHost = limitHost;
	}
}
