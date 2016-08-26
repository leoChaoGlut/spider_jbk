package com.yy.func;

import com.alibaba.fastjson.JSON;
import com.yy.entity.Disease;
import com.yy.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Leo
 * @datetime 2016年8月22日 下午5:32:12
 * @description 爬取疾病详细信息
 */
public class DiseaseInfoTask implements Runnable {

    public static long beginTime;
    public static AtomicInteger doneCount = new AtomicInteger(0);

    public static String DISEASE_DETAILS_PATH_PREFIX;
    public static String DISEASE_ABBR_NAME_PATH_PREFIX;

    static {
        InputStream is = null;
        Properties prop = new Properties();
        try {
            is = DiseaseInfoTask.class.getResourceAsStream("setting.properties");
            prop.load(is);
            DISEASE_DETAILS_PATH_PREFIX = prop.getProperty("disease_details_path_prefix");
            DISEASE_ABBR_NAME_PATH_PREFIX = prop.getProperty("disease_abbr_name_path_prefix");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int beginPage, endPage;

    public DiseaseInfoTask(int beginPage, int endPage) {
        this.beginPage = beginPage;
        this.endPage = endPage;
    }

    @Override
    public void run() {
        for (int i = beginPage; i < endPage; i++) {
            String json = FileUtil.asString(DISEASE_ABBR_NAME_PATH_PREFIX + i);
            List<String> abbrDiseaseNamelist = JSON.parseArray(json, String.class);
            System.out.println("Page:" + i + " started.");
            for (String abbrDiseaseame : abbrDiseaseNamelist) {
                File file = new File(DISEASE_DETAILS_PATH_PREFIX + i + "/" + abbrDiseaseame);
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
