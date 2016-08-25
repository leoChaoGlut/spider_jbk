package com.yy.entity;

import java.util.HashMap;

/**
 * @author Leo
 * @datetime 2016年8月22日 下午3:32:12
 * @description 疾病包装类
 */
public class DiseaseWrapper {

    public static final int BASE_INFO = 10100;
    public static final int SYMPTOM = 10101;
    public static final int CAUSE = 10102;
    public static final int PREVENTION = 10103;
    public static final int CLINICAL_EXAMINATION = 10104;
    public static final int IDENTIFICATION = 10105;
    public static final int TREATMENT = 10106;
    public static final int NURSING = 10107;
    public static final int DIET = 10108;
    public static final int COMPLICATIONS = 10109;

    private static HashMap<Integer, String> constMap = new HashMap<>();

    static {
        constMap.put(BASE_INFO, "疾病名,别名,简介");
        constMap.put(SYMPTOM, "典型症状");
        constMap.put(CAUSE, "发病原因");
        constMap.put(PREVENTION, "预防知识");
        constMap.put(CLINICAL_EXAMINATION, "临床检查");
        constMap.put(IDENTIFICATION, "鉴别");
        constMap.put(TREATMENT, "治疗方法");
        constMap.put(NURSING, "护理");
        constMap.put(DIET, "饮食保健");
        constMap.put(COMPLICATIONS, "并发症");
    }


    private Disease disease;
    /**
     * 记录爬取过的疾病字段,防止重复爬取
     */
    private HashMap<Integer, Boolean> fieldRecordMap;

    public DiseaseWrapper() {
        disease = new Disease();
        fieldRecordMap = new HashMap<>();
        fieldRecordMap.put(BASE_INFO, false);
        fieldRecordMap.put(SYMPTOM, false);
        fieldRecordMap.put(CAUSE, false);
        fieldRecordMap.put(PREVENTION, false);
        fieldRecordMap.put(CLINICAL_EXAMINATION, false);
        fieldRecordMap.put(IDENTIFICATION, false);
        fieldRecordMap.put(TREATMENT, false);
        fieldRecordMap.put(NURSING, false);
        fieldRecordMap.put(DIET, false);
        fieldRecordMap.put(COMPLICATIONS, false);
    }

    /**
     * 将疾病的某一种信息爬完后,将该信息记录下来,防止不必要的重复爬取
     *
     * @param fieldCode
     */
    public void setFieldStatus(int fieldCode) {
        fieldRecordMap.put(fieldCode, true);
        System.out.println("Thread Id:" + Thread.currentThread().getId() + "," + disease.getName() + " - " + constMap.get(fieldCode) + " done.");
    }

    public static int getBaseInfo() {
        return BASE_INFO;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public HashMap<Integer, Boolean> getFieldRecordMap() {
        return fieldRecordMap;
    }

    public void setFieldRecordMap(HashMap<Integer, Boolean> fieldRecordMap) {
        this.fieldRecordMap = fieldRecordMap;
    }

    public boolean fieldHasBeenSpidered(int fieldCode) {
        return fieldRecordMap.get(fieldCode);
    }


}
