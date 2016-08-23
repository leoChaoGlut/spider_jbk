package com.yy.func;

import com.yy.entity.Disease;
import com.yy.entity.DiseaseWrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 16-8-23.
 */
public class SpiderV2 {

    public static AtomicInteger doneCount = new AtomicInteger(0);

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

    private static final String URL_PREFIX_DISEASE = "http://jbk.39.net/bw_t1_p";
    private static final String URL_SUFFIX_DISEASE = "#ps";

    private DiseaseWrapper diseaseWrapper;

    public SpiderV2(boolean createDiseaseWrapper) {
        if (createDiseaseWrapper) {
            this.diseaseWrapper = new DiseaseWrapper();
        }
    }

    public static Set<String> getAbbrDiseaseNameByPage(int page) throws Exception {
        HashSet<String> diseaseNameSet = new HashSet<>();
        Document doc = Jsoup.connect(URL_PREFIX_DISEASE + page + URL_SUFFIX_DISEASE).get();
        Elements diseaseElems = doc.select("div.res_list>dl>dt>h3>a");
        if (diseaseElems != null) {
            for (Element diseaseElem : diseaseElems) {
                String abbrDiseaseName = diseaseElem.attr("href");
                abbrDiseaseName = abbrDiseaseName.substring(0, abbrDiseaseName.length() - 1);
                abbrDiseaseName = abbrDiseaseName.substring(abbrDiseaseName.lastIndexOf("/") + 1);
                diseaseNameSet.add(abbrDiseaseName);
            }
            return diseaseNameSet;
        } else {
            throw new Exception("diseaseElems is null");
        }
    }

    public int getPageCount() {
        String url = "http://jbk.39.net/bw_t1_p1#ps";
        int pageCount = -1;
        try {
            Document doc = Jsoup.connect(url).get();
            Element pageCountElem = doc.select("span.res_page").first();
            String pageCountStr = pageCountElem.ownText();
            pageCount = Integer.valueOf(pageCountStr.substring(pageCountStr.indexOf("/") + 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageCount;
    }

    public Disease getDisease(String abbrDiseaseName) {
        try {
            if (!diseaseWrapper.fieldHasBeenSpidered(DiseaseWrapper.BASE_INFO)) {
                getBaseInfo(abbrDiseaseName);
                diseaseWrapper.setFieldStatus(DiseaseWrapper.BASE_INFO);
            }

            Disease disease = diseaseWrapper.getDisease();

            if (!diseaseWrapper.fieldHasBeenSpidered(DiseaseWrapper.SYMPTOM)) {
                String symptom = getSymptom(abbrDiseaseName);
                disease.setSymptom(symptom);
                diseaseWrapper.setFieldStatus(DiseaseWrapper.SYMPTOM);
            }
            if (!diseaseWrapper.fieldHasBeenSpidered(DiseaseWrapper.CAUSE)) {
                String cause = getCause(abbrDiseaseName);
                disease.setCause(cause);
                diseaseWrapper.setFieldStatus(DiseaseWrapper.CAUSE);
            }
            if (!diseaseWrapper.fieldHasBeenSpidered(DiseaseWrapper.PREVENTION)) {
                String prevention = getPrevention(abbrDiseaseName);
                disease.setPrevention(prevention);
                diseaseWrapper.setFieldStatus(DiseaseWrapper.PREVENTION);
            }
            if (!diseaseWrapper.fieldHasBeenSpidered(DiseaseWrapper.CLINICAL_EXAMINATION)) {
                String clinicalExamination = getClinicalExamination(abbrDiseaseName);
                disease.setClinicalExamination(clinicalExamination);
                diseaseWrapper.setFieldStatus(DiseaseWrapper.CLINICAL_EXAMINATION);
            }
            if (!diseaseWrapper.fieldHasBeenSpidered(DiseaseWrapper.IDENTIFICATION)) {
                String identification = getIdentification(abbrDiseaseName);
                disease.setIdentification(identification);
                diseaseWrapper.setFieldStatus(DiseaseWrapper.IDENTIFICATION);
            }
            if (!diseaseWrapper.fieldHasBeenSpidered(DiseaseWrapper.TREATMENT)) {
                String treatment = getTreatment(abbrDiseaseName);
                disease.setTreatment(treatment);
                diseaseWrapper.setFieldStatus(DiseaseWrapper.TREATMENT);
            }
            if (!diseaseWrapper.fieldHasBeenSpidered(DiseaseWrapper.NURSING)) {
                String nursing = getNursing(abbrDiseaseName);
                disease.setNursing(nursing);
                diseaseWrapper.setFieldStatus(DiseaseWrapper.NURSING);
            }
            if (!diseaseWrapper.fieldHasBeenSpidered(DiseaseWrapper.DIET)) {
                String diet = getDiet(abbrDiseaseName);
                disease.setDiet(diet);
                diseaseWrapper.setFieldStatus(DiseaseWrapper.DIET);
            }
            if (!diseaseWrapper.fieldHasBeenSpidered(DiseaseWrapper.COMPLICATIONS)) {
                String complications = getComplications(abbrDiseaseName);
                disease.setComplications(complications);
                diseaseWrapper.setFieldStatus(DiseaseWrapper.COMPLICATIONS);
            }

            return disease;
        } catch (Exception e) {
            System.err.println("Thread Id:" + Thread.currentThread().getId() + " -> " + e.getLocalizedMessage());
            return getDisease(abbrDiseaseName);
        }
    }

    private String getDiseaseInfo(String abbrDiseaseName, String infoName) throws Exception {
        int interval = new Random().nextInt(500) + 1000;
//        TimeUnit.MILLISECONDS.sleep(interval);
        Document doc = Jsoup.connect(URL_PREFIX_JBK + abbrDiseaseName + infoName).get();
        String str = doc.select("div.art-box").first().text();
        System.out.println(abbrDiseaseName + " - " + infoName + " - " + interval);
        return str;
    }

    private void getBaseInfo(String abbrDiseaseName) throws Exception {
        Document doc = Jsoup.connect(URL_PREFIX_JBK + abbrDiseaseName + INTRO).get();
        Element titleElem = doc.select("div.tit").first();
        String diseaseName = titleElem.select("a>h1").first().text();
        diseaseWrapper.getDisease().setName(diseaseName);
        Elements aliasElems = titleElem.select("h2.alias");
        if (!aliasElems.isEmpty()) {
            String diseaseAlias = aliasElems.first().text();
            diseaseWrapper.getDisease().setAlias(diseaseAlias.substring(1, diseaseAlias.length() - 1));
        }
        String diseaseIntro = doc.select("dl.intro>dd").first().text();
        diseaseWrapper.getDisease().setIntro(diseaseIntro);
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
