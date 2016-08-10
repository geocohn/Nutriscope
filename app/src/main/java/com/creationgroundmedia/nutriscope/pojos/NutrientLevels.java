package com.creationgroundmedia.nutriscope.pojos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NutrientLevels {

    private String salt;
    private String fat;
    private String sugars;
    @JsonProperty("saturated-fat")
    private String saturatedFat;


    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getSalt() {
        return salt;
    }


    public void setFat(String fat) {
        this.fat = fat;
    }
    public String getFat() {
        return fat;
    }


    public void setSugars(String sugars) {
        this.sugars = sugars;
    }
    public String getSugars() {
        return sugars;
    }


    public void setSaturatedFat(String saturatedFat) {
        this.saturatedFat = saturatedFat;
    }
    public String getSaturatedFat() {
        return saturatedFat;
    }

}