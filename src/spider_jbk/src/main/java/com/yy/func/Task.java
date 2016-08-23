package com.yy.func;

import com.alibaba.fastjson.JSON;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Set;

/**
 * Created by leo on 16-8-23.
 */
public class Task implements Runnable {

    private int beginIndex, endIndex;

    public Task(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        for (int i = beginIndex; i < endIndex; i++) {
            try {
                Set<String> diseaseNameSet = SpiderV2.getAbbrDiseaseNameByPage(i);
                BufferedWriter br = new BufferedWriter(new FileWriter("/home/leo/disease/" + i));
                br.write(JSON.toJSONString(diseaseNameSet));
                br.flush();
                br.close();
                int doneCount = SpiderV2.doneCount.incrementAndGet();
                System.out.println("Page:" + i + " done. Total:" + doneCount);
            } catch (Exception e) {
                System.err.println("Thread Id: " + Thread.currentThread().getId() + " -> " + e.getLocalizedMessage());
                i--;
            }
        }
    }
}
