package com.yy.func;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yy.entity.Disease;

/**
 * @author Leo
 * @datetime 2016年8月22日 上午11:14:40
 * @description
 */

public class Spider {

    private final String INTRO = "/jbzs";
    private final String SYMPTOM = "/zztz";
    private final String CAUSE = "/blby";
    private final String PREVENTION = "/yfhl";
    private final String CLINICAL_EXAMINATION = "/jcjb";
    private final String IDENTIFICATION = "/jb";
    private final String TREATMENT = "/yyzl";
    private final String NURSING = "/hl";
    private final String DIET = "/ysbj";
    private final String COMPLICATIONS = "/bfbz";

    private final String URL_PREFIX_JBK = "http://jbk.39.net/";

    public Disease getDisease(String abbrDiseaseName) {
        try {
            Disease disease = new Disease();
            getBaseInfo(disease, abbrDiseaseName);

            String symptom = getSymptom(abbrDiseaseName);
            String cause = getCause(abbrDiseaseName);
            String prevention = getPrevention(abbrDiseaseName);
            String clinicalExamination = getClinicalExamination(abbrDiseaseName);
            String identification = getIdentification(abbrDiseaseName);
            String treatment = getTreatment(abbrDiseaseName);
            String nursing = getNursing(abbrDiseaseName);
            String diet = getDiet(abbrDiseaseName);
            String complications = getComplications(abbrDiseaseName);

            disease.setSymptom(symptom).setCause(cause).setPrevention(prevention)
                    .setClinicalExamination(clinicalExamination).setIdentification(identification)
                    .setTreatment(treatment).setNursing(nursing).setDiet(diet).setComplications(complications);

            return disease;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getDiseaseInfo(String abbrDiseaseName, String infoName) throws Exception {
        int interval = new Random().nextInt(500) + 1000;
        TimeUnit.MILLISECONDS.sleep(interval);
        Document doc = Jsoup.connect(URL_PREFIX_JBK + abbrDiseaseName + infoName).get();
        String str = doc.select("div.art-box").first().text();
        return str;
    }

    private void getBaseInfo(Disease disease, String abbrDiseaseName) throws Exception {
        Document doc = Jsoup.connect(URL_PREFIX_JBK + abbrDiseaseName + INTRO).get();
        Element titleElem = doc.select("div.tit").first();
        String diseaseName = titleElem.select("a>h1").first().text();
        disease.setName(diseaseName);
        Elements aliasElems = titleElem.select("h2.alias");
        if (!aliasElems.isEmpty()) {
            String diseaseAlias = aliasElems.first().text();
            disease.setAlias(diseaseAlias.substring(1, diseaseAlias.length() - 1));
        }
        String diseaseIntro = doc.select("dl.intro>dd").first().text();
        disease.setIntro(diseaseIntro);
    }

    private String getComplications(String abbrDiseaseName) throws Exception {
        return getDiseaseInfo(abbrDiseaseName, COMPLICATIONS);
    }

    private String getDiet(String abbrDiseaseName) throws Exception {
        return getDiseaseInfo(abbrDiseaseName, DIET);
    }

    private String getNursing(String abbrDiseaseName) throws Exception {
        return getDiseaseInfo(abbrDiseaseName, NURSING);
    }

    private String getTreatment(String abbrDiseaseName) throws Exception {
        return getDiseaseInfo(abbrDiseaseName, TREATMENT);
    }

    private String getIdentification(String abbrDiseaseName) throws Exception {
        return getDiseaseInfo(abbrDiseaseName, IDENTIFICATION);
    }

    private String getClinicalExamination(String abbrDiseaseName) throws Exception {
        return getDiseaseInfo(abbrDiseaseName, CLINICAL_EXAMINATION);
    }

    private String getPrevention(String abbrDiseaseName) throws Exception {
        return getDiseaseInfo(abbrDiseaseName, PREVENTION);
    }

    private String getCause(String abbrDiseaseName) throws Exception {
        return getDiseaseInfo(abbrDiseaseName, CAUSE);
    }

    private String getSymptom(String abbrDiseaseName) throws Exception {
        return getDiseaseInfo(abbrDiseaseName, SYMPTOM);
    }


}
