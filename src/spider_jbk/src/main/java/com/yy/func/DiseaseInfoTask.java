package com.yy.func;

import com.alibaba.fastjson.JSON;
import com.yy.entity.Disease;
import com.yy.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 16-8-23.
 */
public class DiseaseInfoTask implements Runnable {

    public static long beginTime;

    public static AtomicInteger doneCount = new AtomicInteger(0);

    private int beginPage, endPage;
    private String FILE_PATH_PREFIX = "/home/leo/diseaseDetails/";

    public DiseaseInfoTask(int beginPage, int endPage) {
        this.beginPage = beginPage;
        this.endPage = endPage;
    }

    @Override
    public void run() {
        for (int i = beginPage; i < endPage; i++) {
            String json = FileUtil.asString("/home/leo/disease/" + i);
            List<String> abbrDiseaseNamelist = JSON.parseArray(json, String.class);
            System.out.println("Page:" + i + " started.");
            for (String abbrDiseaseame : abbrDiseaseNamelist) {
                File file = new File(FILE_PATH_PREFIX + i + "/" + abbrDiseaseame);
                if (!file.exists()) {
                    SpiderV2 spider = new SpiderV2(true);
                    Disease disease = spider.getDisease(abbrDiseaseame);
                    FileUtil.write(file, disease.toString());
                    System.out.println("Thread Id:" + Thread.currentThread().getId() + "," + disease.getName() + " done, Total:" + DiseaseInfoTask.doneCount.incrementAndGet() + ", Spend: " + BigDecimal.valueOf(System.nanoTime() - DiseaseInfoTask.beginTime, 9) + " s");
                    System.out.println(disease.toString());
                }
            }
            System.out.println("Page:" + i + " finished.");
        }

    }
}
