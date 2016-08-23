package com.yy.entity;

import java.util.HashMap;

/**
 * Created by leo on 16-8-23.
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


    private Disease disease;
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

    public DiseaseWrapper(Disease disease, HashMap<Integer, Boolean> fieldRecordMap) {
        this.disease = disease;
        this.fieldRecordMap = fieldRecordMap;
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

    public void setFieldStatus(int fieldCode) {
        fieldRecordMap.put(fieldCode, true);
        System.out.println(disease.getName() + " - " + fieldCode + " done.");
    }

}