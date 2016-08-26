package com.yy.test;

import com.alibaba.fastjson.JSON;
import com.yy.entity.Disease;
import com.yy.func.DiseaseAbbrNameTask;
import com.yy.func.DiseaseInfoTask;
import com.yy.func.Spider;
import com.yy.util.FileUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Leo
 * @datetime 2016年8月22日 下午2:57:15
 * @description
 */

public class LeoTest {

    @Test
    public void test02() throws Exception {
//        WritableWorkbook workbook = Workbook.createWorkbook(new File("C://disease.xls"));
//        WritableSheet sheet = workbook.createSheet("First Sheet", 0);
//
//        sheet.addCell(new Label(0, 0, "疾病、症状名"));
//        sheet.addCell(new Label(1, 0, "别名"));
//        sheet.addCell(new Label(2, 0, "简介"));
//        sheet.addCell(new Label(3, 0, "典型症状"));
//        sheet.addCell(new Label(4, 0, "发病原因"));
//        sheet.addCell(new Label(5, 0, "预防知识"));
//        sheet.addCell(new Label(6, 0, "临床检查"));
//        sheet.addCell(new Label(7, 0, "鉴别"));
//        sheet.addCell(new Label(8, 0, "治疗方法"));
//        sheet.addCell(new Label(9, 0, "护理"));
//        sheet.addCell(new Label(10, 0, "饮食保健"));
//        sheet.addCell(new Label(11, 0, "并发症"));
//
//        for (int i = 0; i < diseaseList.size(); i++) {
//            Disease disease = diseaseList.get(i);
//            sheet.addCell(new Label(0, count, disease.getName()));
//            sheet.addCell(new Label(1, count, disease.getAlias()));
//            sheet.addCell(new Label(2, count, disease.getIntro()));
//            sheet.addCell(new Label(3, count, disease.getSymptom()));
//            sheet.addCell(new Label(4, count, disease.getCause()));
//            sheet.addCell(new Label(5, count, disease.getPrevention()));
//            sheet.addCell(new Label(6, count, disease.getClinicalExamination()));
//            sheet.addCell(new Label(7, count, disease.getIdentification()));
//            sheet.addCell(new Label(8, count, disease.getTreatment()));
//            sheet.addCell(new Label(9, count, disease.getNursing()));
//            sheet.addCell(new Label(10, count, disease.getDiet()));
//            sheet.addCell(new Label(11, count, disease.getComplications()));
//        }
//        workbook.write();
//        workbook.close();
    }


    /**
     * get diseaseNameSet and write to disk.
     */
    @Test
    public void test05() {
        System.out.println("开始获取总页数");
        long begin = System.nanoTime();
        Spider spider = new Spider(false);
        int pageCount = spider.getPageCount();
        System.out.println("获取总页数完毕,共: " + pageCount + " 页,耗时:" + BigDecimal.valueOf(System.nanoTime() - begin, 9) + " s.");

        int threadCount = Runtime.getRuntime().availableProcessors() << 1;

        System.out.println("线程总数:" + threadCount);

        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        int beginIndex, endIndex;
        System.out.println("开始获取所有疾病拼音简称");
        begin = System.nanoTime();
        for (int i = 0; i < threadCount; i++) {
            beginIndex = pageCount / threadCount * i;
            endIndex = (i == threadCount - 1) ? pageCount : pageCount / threadCount * (i + 1);
            threadPool.submit(new DiseaseAbbrNameTask(beginIndex, endIndex));
        }
        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                System.out.println("获取完毕,耗时: " + BigDecimal.valueOf(System.nanoTime() - begin, 9) + " s.");
                System.exit(0);
            }
        }

    }

    /**
     * get disease info by abbr disease name.
     */
    @Test
    public void test06() {
        Spider spider = new Spider(true);
        Disease disease = spider.getDisease("sz");
        System.out.println(disease);
//        Disease disease = SpiderV2.getDisease("sz");
//        System.out.println(disease.toString());
    }

    /**
     * 读取疾病简称文件,爬取它的详细信息
     */
    @Test
    public void test07() {
        System.out.println("开始抓取详细疾病数据");
        DiseaseInfoTask.beginTime = System.nanoTime();
        long begin = System.nanoTime();
        int threadCount = Runtime.getRuntime().availableProcessors() << 1;
        System.out.println("线程总数:" + threadCount);
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
        int pageCount = 809;
        int beginPage, endPage;
        for (int i = 0; i < threadCount; i++) {
            beginPage = pageCount / threadCount * i;
            endPage = (i == threadCount - 1) ? pageCount : pageCount / threadCount * (i + 1);
            threadPool.submit(new DiseaseInfoTask(beginPage, endPage));
        }
        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                System.out.println("抓取结束,耗时:" + BigDecimal.valueOf(System.nanoTime() - begin, 9) + ",共: " + DiseaseInfoTask.doneCount.get() + " 条数据");
                System.exit(0);
            }
        }
    }

    /**
     * json文件导出excel
     */
    @Test
    public void test08() throws Exception {
        int pageCount = 809;
        WritableWorkbook workbook = Workbook.createWorkbook(new File("/home/leo/diseaseExcel/disease.xls"));
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

        int count = 1;

        for (int i = 0; i < pageCount; i++) {
            File folder = new File("/home/leo/diseaseDetails/" + i);
            if (folder.exists()) {
                File[] files = folder.listFiles();
                System.out.println("==== Page:" + i + "====");
                for (int j = 0; j < files.length; j++) {
//                    System.out.println(FileUtil.asString(files[j]));
                    Disease disease = JSON.parseObject(FileUtil.asString(files[j]), Disease.class);

                    sheet.addCell(new Label(0, count, disease.getName()));
                    sheet.addCell(new Label(1, count, disease.getAlias()));
                    sheet.addCell(new Label(2, count, disease.getIntro()));
                    sheet.addCell(new Label(3, count, disease.getSymptom()));
                    sheet.addCell(new Label(4, count, disease.getCause()));
                    sheet.addCell(new Label(5, count, disease.getPrevention()));
                    sheet.addCell(new Label(6, count, disease.getClinicalExamination()));
                    sheet.addCell(new Label(7, count, disease.getIdentification()));
                    sheet.addCell(new Label(8, count, disease.getTreatment()));
                    sheet.addCell(new Label(9, count, disease.getNursing()));
                    sheet.addCell(new Label(10, count, disease.getDiet()));
                    sheet.addCell(new Label(11, count, disease.getComplications()));

                    count++;

                }
                System.out.println("==== Page:" + i + "====");
            }
        }

        workbook.write();
        workbook.close();
    }
}
