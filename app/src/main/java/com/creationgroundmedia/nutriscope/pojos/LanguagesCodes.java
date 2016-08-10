package com.creationgroundmedia.nutriscope.pojos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguagesCodes {

    private int en;


    public void setEn(int en) {
        this.en = en;
    }
    public int getEn() {
        return en;
    }

}