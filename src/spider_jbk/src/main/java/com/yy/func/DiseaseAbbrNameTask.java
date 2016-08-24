package com.yy.func;

import com.alibaba.fastjson.JSON;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 16-8-23.
 */
public class DiseaseAbbrNameTask implements Runnable {

    public static AtomicInteger doneCount = new AtomicInteger(0);

    private int beginIndex, endIndex;

    public DiseaseAbbrNameTask(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        for (int i = beginIndex; i < endIndex; i++) {
            try {
                Set<String> diseaseNameSet = SpiderV2.getAbbrDiseaseNameByPage(i);
                BufferedWriter br = new BufferedWriter(new FileWriter("/home/leo/diseaseTmp/" + i));
                br.write(JSON.toJSONString(diseaseNameSet));
                br.flush();
                br.close();
                int doneCount = DiseaseAbbrNameTask.doneCount.incrementAndGet();
                System.out.println("Page:" + i + " done. Total:" + doneCount);
//                TimeUnit.MICROSECONDS.sleep(new Random().nextInt(100));
            } catch (Exception e) {
//                System.err.println("Thread Id: " + Thread.currentThread().getId() + " -> " + e.getLocalizedMessage());
                i--;
            }
        }
    }
}
