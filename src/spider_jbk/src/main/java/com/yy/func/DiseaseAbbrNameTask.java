package com.yy.func;

import com.alibaba.fastjson.JSON;
import com.yy.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Leo
 * @datetime 2016年8月22日 下午4:32:12
 * @description 爬取疾病拼音首字母
 */
public class DiseaseAbbrNameTask implements Runnable {

    public static AtomicInteger doneCount = new AtomicInteger(0);

    public static String DISEASE_ABBR_NAME_PATH_PREFIX;

    static {
        InputStream is = null;
        Properties prop = new Properties();
        try {
            is = DiseaseInfoTask.class.getResourceAsStream("/setting.properties");
            prop.load(is);
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

    public DiseaseAbbrNameTask(int beginPage, int endPage) {
        this.beginPage = beginPage;
        this.endPage = endPage;
    }


    @Override
    public void run() {
        for (int i = beginPage; i < endPage; i++) {
            try {
                Set<String> diseaseNameSet = Spider.getAbbrDiseaseNameByPage(i);
                FileUtil.write(DISEASE_ABBR_NAME_PATH_PREFIX + i, JSON.toJSONString(diseaseNameSet));
                System.out.println("Page:" + i + " done. Total:" + DiseaseAbbrNameTask.doneCount.incrementAndGet());
//                TimeUnit.MICROSECONDS.sleep(new Random().nextInt(100));
            } catch (Exception e) {
//                System.err.println("Thread Id: " + Thread.currentThread().getId() + " -> " + e.getLocalizedMessage());
                i--;//异常后,重新爬取
            }
        }
    }
}
