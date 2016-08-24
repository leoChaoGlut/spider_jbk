package com.yy.entity;

/**
 * @author Leo
 * @datetime 2016年8月22日 下午2:32:12
 * @description
 */
public class Disease {
	private String name;
	private String alias;
	private String intro;
	private String symptom;
	private String cause;
	private String prevention;
	private String clinicalExamination;
	private String identification;
	private String treatment;
	private String nursing;
	private String diet;
	private String complications;

	public Disease() {
	}

	public String getName() {
		return name;
	}

	public Disease setName(String name) {
		this.name = name;
		return this;
	}

	public String getAlias() {
		return alias;
	}

	public Disease setAlias(String alias) {
		this.alias = alias;
		return this;
	}

	public String getIntro() {
		return intro;
	}

	public Disease setIntro(String intro) {
		this.intro = intro;
		return this;
	}

	public String getSymptom() {
		return symptom;
	}

	public Disease setSymptom(String symptom) {
		this.symptom = symptom;
		return this;
	}

	public String getCause() {
		return cause;
	}

	public Disease setCause(String cause) {
		this.cause = cause;
		return this;
	}

	public String getPrevention() {
		return prevention;
	}

	public Disease setPrevention(String prevention) {
		this.prevention = prevention;
		return this;
	}

	public String getClinicalExamination() {
		return clinicalExamination;
	}

	public Disease setClinicalExamination(String clinicalExamination) {
		this.clinicalExamination = clinicalExamination;
		return this;
	}

	public String getIdentification() {
		return identification;
	}

	public Disease setIdentification(String identification) {
		this.identification = identification;
		return this;
	}

	public String getTreatment() {
		return treatment;
	}

	public Disease setTreatment(String treatment) {
		this.treatment = treatment;
		return this;
	}

	public String getNursing() {
		return nursing;
	}

	public Disease setNursing(String nursing) {
		this.nursing = nursing;
		return this;
	}

	public String getDiet() {
		return diet;
	}

	public Disease setDiet(String diet) {
		this.diet = diet;
		return this;
	}

	public String getComplications() {
		return complications;
	}

	public Disease setComplications(String complications) {
		this.complications = complications;
		return this;
	}

	@Override
	public String toString() {
		return "Disease [name=" + name + ", alias=" + alias + ", intro=" + intro + ", symptom=" + symptom + ", cause="
				+ cause + ", prevention=" + prevention + ", clinicalExamination=" + clinicalExamination
				+ ", identification=" + identification + ", treatment=" + treatment + ", nursing=" + nursing + ", diet="
				+ diet + ", complications=" + complications + "]";
	}

}
