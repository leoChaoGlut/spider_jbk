package com.yy.test;

import com.alibaba.fastjson.JSON;
import com.yy.entity.Disease;
import com.yy.func.Spider;
import com.yy.func.SpiderV2;
import com.yy.func.Task;
import com.yy.func.TaskManager;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Leo
 * @datetime 2016年8月22日 下午2:57:15
 * @description
 */

public class LeoTest {

    @Test
    public void test00() throws Exception {
        Disease disease = new Spider().getDisease("sz");
        String json = JSON.toJSONString(disease);
        System.out.println(json);
    }

    @Test
    public void test01() throws Exception {
        Document doc = Jsoup.connect("http://jbk.39.net/bw_t1_p0#ps").get();
        Elements elements = doc.select("div.res_list>dl>dt>h3>a");
        System.out.println(elements);
    }

    @Test
    public void test02() throws Exception {
        TaskManager taskManager = TaskManager.getInstance();
        taskManager.init();
        List<String> diseaseNameList = taskManager.getDiseaseNameList();
        Spider spider = new Spider();
        for (String diseaseName : diseaseNameList) {
            Disease disease = spider.getDisease(diseaseName);
            if (disease != null) {
                // System.out.println(disease.toString());
                taskManager.addDisease(disease);
            }
        }
        CopyOnWriteArrayList<Disease> diseaseList = taskManager.getDiseaseList();
        WritableWorkbook workbook = Workbook.createWorkbook(new File("C://disease.xls"));
        WritableSheet sheet = workbook.createSheet("First Sheet", 0);

        sheet.addCell(new Label(0, 0, "疾病、症状名"));
        sheet.addCell(new Label(1, 0, "别名"));
        sheet.addCell(new Label(2, 0, "简介"));
        sheet.addCell(new Label(3, 0, "典型症状"));
        sheet.addCell(new Label(4, 0, "发病原因"));
        sheet.addCell(new Label(5, 0, "预防知识"));
        sheet.addCell(new Label(6, 0, "临床检查"));
        sheet.addCell(new Label(7, 0, "鉴别"));
        sheet.addCell(new Label(8, 0, "治疗方法"));
        sheet.addCell(new Label(9, 0, "护理"));
        sheet.addCell(new Label(10, 0, "饮食保健"));
        sheet.addCell(new Label(11, 0, "并发症"));

        for (int i = 0; i < diseaseList.size(); i++) {
            Disease disease = diseaseList.get(i);
            sheet.addCell(new Label(0, i + 1, disease.getName()));
            sheet.addCell(new Label(1, i + 1, disease.getAlias()));
            sheet.addCell(new Label(2, i + 1, disease.getIntro()));
            sheet.addCell(new Label(3, i + 1, disease.getSymptom()));
            sheet.addCell(new Label(4, i + 1, disease.getCause()));
            sheet.addCell(new Label(5, i + 1, disease.getPrevention()));
            sheet.addCell(new Label(6, i + 1, disease.getClinicalExamination()));
            sheet.addCell(new Label(7, i + 1, disease.getIdentification()));
            sheet.addCell(new Label(8, i + 1, disease.getTreatment()));
            sheet.addCell(new Label(9, i + 1, disease.getNursing()));
            sheet.addCell(new Label(10, i + 1, disease.getDiet()));
            sheet.addCell(new Label(11, i + 1, disease.getComplications()));
        }
        workbook.write();
        workbook.close();
    }

    @Test
    public void test03() {
        doGet();
    }

    private void doGet() {
        for (int i = 0; i < 80; i++) {
            try {
                getList(i);
            } catch (IOException e) {
                e.printStackTrace();
                doGet();
            }
        }
    }

    private void getList(int i) throws IOException {
        int beginIndex = 10 * i, endIndex = 10 * i + 10;
        TaskManager taskManager = TaskManager.getInstance();
        taskManager.init();
        Set<String> diseaseNameSet = taskManager.buildDiseaseNameSet(beginIndex, endIndex);
        System.out.println(diseaseNameSet.size());
        String jsonString = JSON.toJSONString(diseaseNameSet);
        BufferedWriter bw = new BufferedWriter(
                new FileWriter("C://disease/diseaseNames_" + beginIndex + "-" + endIndex + ".txt", true));
        bw.write(jsonString);
        bw.flush();
        bw.close();
    }

    @Test
    public void test04() {
        Medicine medicine = new Medicine();
        medicine.setYpmc(medicine.new FieldStruct("valuie", "title"));
        String jsonString = JSON.toJSONString(medicine);
        System.out.println(jsonString);

    }

    /**
     * get diseaseNameSet and write to disk.
     */
    @Test
    public void test05() {
        SpiderV2 spider = new SpiderV2(false);
        int pageCount = spider.getPageCount();
        System.out.println(pageCount);

        int threadCount = Runtime.getRuntime().availableProcessors() + 1;

        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        int beginIndex, endIndex;

        for (int i = 0; i < threadCount; i++) {
            beginIndex = pageCount / 3 * i;
            endIndex = (i == threadCount - 1) ? pageCount : pageCount / 3 * (i + 1);
            threadPool.submit(new Task(beginIndex, endIndex));
        }

        while (true) {
            if (threadPool.isTerminated()) {
                System.exit(0);
            }
        }

    }

    /**
     * get disease info by abbr disease name.
     */
    @Test
    public void test06() {
        SpiderV2 spider = new SpiderV2(true);
        Disease disease = spider.getDisease("sz");
        System.out.println(disease);
//        Disease disease = SpiderV2.getDisease("sz");
//        System.out.println(disease.toString());
    }
}
