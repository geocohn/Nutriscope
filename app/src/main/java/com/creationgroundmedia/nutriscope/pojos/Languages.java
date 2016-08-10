package com.creationgroundmedia.nutriscope.pojos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Languages {

    private int english;


    public void setEnglish(int english) {
        this.english = english;
    }
    public int getEnglish() {
        return english;
    }

}