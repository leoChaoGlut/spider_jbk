package com.yy.test;

import com.alibaba.fastjson.JSON;
import com.yy.entity.Disease;
import com.yy.func.DiseaseInfoTask;
import com.yy.func.SpiderV2;
import com.yy.func.DiseaseAbbrNameTask;
import com.yy.util.FileUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
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
//            sheet.addCell(new Label(0, i + 1, disease.getName()));
//            sheet.addCell(new Label(1, i + 1, disease.getAlias()));
//            sheet.addCell(new Label(2, i + 1, disease.getIntro()));
//            sheet.addCell(new Label(3, i + 1, disease.getSymptom()));
//            sheet.addCell(new Label(4, i + 1, disease.getCause()));
//            sheet.addCell(new Label(5, i + 1, disease.getPrevention()));
//            sheet.addCell(new Label(6, i + 1, disease.getClinicalExamination()));
//            sheet.addCell(new Label(7, i + 1, disease.getIdentification()));
//            sheet.addCell(new Label(8, i + 1, disease.getTreatment()));
//            sheet.addCell(new Label(9, i + 1, disease.getNursing()));
//            sheet.addCell(new Label(10, i + 1, disease.getDiet()));
//            sheet.addCell(new Label(11, i + 1, disease.getComplications()));
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
        SpiderV2 spider = new SpiderV2(false);
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
        SpiderV2 spider = new SpiderV2(true);
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
        int pageCount = 100;
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
}
