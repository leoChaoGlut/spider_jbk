package com.yy.func;

import com.alibaba.fastjson.JSON;
import com.yy.entity.Disease;
import com.yy.util.FileUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Leo
 * @datetime 2016年8月22日 下午5:32:12
 * @description 爬取疾病详细信息
 */
public class DiseaseInfoTask implements Runnable {

    public static long beginTime;

    public static AtomicInteger doneCount = new AtomicInteger(0);

    private int beginPage, endPage;

    public static String OUTPUT_FILE_PATH_PREFIX = "/home/leo/diseaseDetails/";
    public static String DISEASE_NAME_PATH = "/home/leo/disease/";

    public DiseaseInfoTask(int beginPage, int endPage) {
        this.beginPage = beginPage;
        this.endPage = endPage;
    }

    @Override
    public void run() {
        for (int i = beginPage; i < endPage; i++) {
            String json = FileUtil.asString(DISEASE_NAME_PATH + i);
            List<String> abbrDiseaseNamelist = JSON.parseArray(json, String.class);
            System.out.println("Page:" + i + " started.");
            for (String abbrDiseaseame : abbrDiseaseNamelist) {
                File file = new File(OUTPUT_FILE_PATH_PREFIX + i + "/" + abbrDiseaseame);
                if (!file.exists()) {
                    Spider spider = new Spider(true);
                    Disease disease = spider.getDisease(abbrDiseaseame);
                    FileUtil.write(file, JSON.toJSONString(disease));
                    System.out.println("Thread Id:" + Thread.currentThread().getId() + ", Page:" + i + ", Disease:" + disease.getName() + " done, Total:" + DiseaseInfoTask.doneCount.incrementAndGet() + ", Spend: " + BigDecimal.valueOf(System.nanoTime() - DiseaseInfoTask.beginTime, 9) + " s");
                    System.out.println(disease.toString());
                } else {
                    DiseaseInfoTask.doneCount.incrementAndGet();
                }
            }
            System.out.println("Page:" + i + " finished.");
        }

    }
}
