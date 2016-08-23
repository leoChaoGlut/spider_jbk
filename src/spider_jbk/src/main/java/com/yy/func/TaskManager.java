package com.yy.func;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yy.entity.Disease;
import com.yy.entity.SpiderRecord;

/**
 * @author Leo
 * @datetime 2016年8月22日 下午12:20:52
 * @description
 */

public class TaskManager {

	private boolean hasInited = false;
	private int pageCount = -1;
	private static final TaskManager taskManager = new TaskManager();
	private AtomicInteger successCount = new AtomicInteger();
	private Map<String, SpiderRecord> recordMap = new HashMap<>();
	private List<String> diseaseNameList = new ArrayList<>();
	private Set<String> diseaseNameSet = new HashSet<>();
	private CopyOnWriteArrayList<Disease> diseaseList = new CopyOnWriteArrayList<>();
	private final String URL_PREFIX_DISEASE = "http://jbk.39.net/bw_t1_p";
	private final String URL_SUFFIX_DISEASE = "#ps";

	public static TaskManager getInstance() {
		return taskManager;
	}

	public void init() {
		if (!hasInited) {
			pageCount = calcPageCount();
			System.out.println("Page Count:" + pageCount);
		}
	}

	public HashSet<String> buildDiseaseNameSet(int beginIndex, int endIndex) {
		HashSet<String> diseaseNameSet = new HashSet<>();
		try {
			endIndex = endIndex > pageCount ? pageCount : endIndex;
			for (int i = beginIndex; i < endIndex; i++) {
				buildDiseaseNameSetByPage(i, diseaseNameSet);
			}
			return diseaseNameSet;
			// hasInited = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void buildDiseaseNameSetByPage(int page, HashSet<String> diseaseNameSet) throws Exception {
		System.out.println("Page:" + page);
		Document doc = Jsoup.connect(URL_PREFIX_DISEASE + page + URL_SUFFIX_DISEASE).get();
		Elements diseaseElems = doc.select("div.res_list>dl>dt>h3>a");
		if (diseaseElems != null) {
			for (Element diseaseElem : diseaseElems) {
				String abbrDiseaseName = diseaseElem.attr("href");
				abbrDiseaseName = abbrDiseaseName.substring(0, abbrDiseaseName.length() - 1);
				abbrDiseaseName = abbrDiseaseName.substring(abbrDiseaseName.lastIndexOf("/") + 1);
				if (diseaseNameSet.contains(abbrDiseaseName)) {
					System.err.println("Duplicated Disease Name Occured!!!!!!!!!!");
					BufferedWriter bw = new BufferedWriter(new FileWriter("C://disease/log.txt", true));
					bw.append(abbrDiseaseName + "\n");
					bw.flush();
					bw.close();
				}
				diseaseNameSet.add(abbrDiseaseName);
				// System.out.println(abbrDiseaseName);
			}
		}
		TimeUnit.MILLISECONDS.sleep(1000);
	}

	private int calcPageCount() {
		String url = "http://jbk.39.net/bw_t1_p1#ps";
		int pageCount = -1;
		try {
			Document doc = Jsoup.connect(url).get();
			Element pageCountElem = doc.select("span.res_page").first();
			String pageCountStr = pageCountElem.ownText();
			pageCount = Integer.valueOf(pageCountStr.substring(pageCountStr.indexOf("/") + 1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pageCount;
	}

	public Map<String, SpiderRecord> getRecordMap() {
		return recordMap;
	}

	public List<String> getDiseaseNameList() {
		return diseaseNameList;
	}

	public CopyOnWriteArrayList<Disease> getDiseaseList() {
		return diseaseList;
	}

	public void addDisease(Disease disease) {
		diseaseList.add(disease);
	}

	public Set<String> getDiseaseNameSet() {
		return diseaseNameSet;
	}

}
