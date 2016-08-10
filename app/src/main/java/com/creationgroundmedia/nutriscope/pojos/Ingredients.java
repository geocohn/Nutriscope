package com.creationgroundmedia.nutriscope.pojos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredients {

    private String text;
    private String id;
    private int rank;


    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }


    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }


    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getRank() {
        return rank;
    }

}