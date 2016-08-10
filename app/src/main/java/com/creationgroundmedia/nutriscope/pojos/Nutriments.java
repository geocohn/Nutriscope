package com.creationgroundmedia.nutriscope.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Nutriments {

    private String sodium;
    private String sugars;
    @JsonProperty("fat_unit")
    private String fatUnit;
    @JsonProperty("carbohydrates_unit")
    private String carbohydratesUnit;
    @JsonProperty("proteins_unit")
    private String proteinsUnit;
    @JsonProperty("nutrition-score-fr_100g")
    private int nutritionScoreFr100g;
    private String fat;
    @JsonProperty("proteins_serving")
    private String proteinsServing;
    @JsonProperty("sodium_serving")
    private String sodiumServing;
    private String proteins;
    private String salt;
    private int nutritionScoreFr;
    @JsonProperty("fat_serving")
    private String fatServing;
    @JsonProperty("sugars_unit")
    private String sugarsUnit;
    @JsonProperty("sugars_100g")
    private String sugars100g;
    @JsonProperty("sodium_unit")
    private String sodiumUnit;
    @JsonProperty("saturatedFat_serving")
    private String saturatedFatServing;
    @JsonProperty("sodium_100g")
    private String sodium100g;
    @JsonProperty("saturatedFat_unit")
    private String saturatedFatUnit;
    @JsonProperty("fiber_unit")
    private String fiberUnit;
    private String energy;
    @JsonProperty("energy_unit")
    private String energyUnit;
    @JsonProperty("sugars_serving")
    private String sugarsServing;
    @JsonProperty("carbohydrates_100g")
    private String carbohydrates100g;
    private int nutritionScoreUk;
    @JsonProperty("proteins_100g")
    private String proteins100g;
    @JsonProperty("fiber_serving")
    private String fiberServing;
    @JsonProperty("carbohydrates_serving")
    private String carbohydratesServing;
    @JsonProperty("energy_serving")
    private String energyServing;
    @JsonProperty("fat_100g")
    private String fat100g;
    @JsonProperty("saturatedFat_100g")
    private String saturatedFat100g;
    @JsonProperty("nutrition-score-uk_100g")
    private int nutritionScoreUk100g;
    private String fiber;
    @JsonProperty("salt_100g")
    private String salt100g;
    @JsonProperty("salt_serving")
    private String saltServing;
    @JsonProperty("fiber_100g")
    private String fiber100g;
    private String carbohydrates;
    @JsonProperty("energy_100g")
    private String energy100g;
    private String saturatedFat;


    public void setSodium(String sodium) {
        this.sodium = sodium;
    }
    public String getSodium() {
        return sodium;
    }


    public void setSugars(String sugars) {
        this.sugars = sugars;
    }
    public String getSugars() {
        return sugars;
    }


    public void setFatUnit(String fatUnit) {
        this.fatUnit = fatUnit;
    }
    public String getFatUnit() {
        return fatUnit;
    }


    public void setCarbohydratesUnit(String carbohydratesUnit) {
        this.carbohydratesUnit = carbohydratesUnit;
    }
    public String getCarbohydratesUnit() {
        return carbohydratesUnit;
    }


    public void setProteinsUnit(String proteinsUnit) {
        this.proteinsUnit = proteinsUnit;
    }
    public String getProteinsUnit() {
        return proteinsUnit;
    }


    public void setNutritionScoreFr100g(int nutritionScoreFr100g) {
        this.nutritionScoreFr100g = nutritionScoreFr100g;
    }
    public int getNutritionScoreFr100g() {
        return nutritionScoreFr100g;
    }


    public void setFat(String fat) {
        this.fat = fat;
    }
    public String getFat() {
        return fat;
    }


    public void setProteinsServing(String proteinsServing) {
        this.proteinsServing = proteinsServing;
    }
    public String getProteinsServing() {
        return proteinsServing;
    }


    public void setSodiumServing(String sodiumServing) {
        this.sodiumServing = sodiumServing;
    }
    public String getSodiumServing() {
        return sodiumServing;
    }


    public void setProteins(String proteins) {
        this.proteins = proteins;
    }
    public String getProteins() {
        return proteins;
    }


    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getSalt() {
        return salt;
    }


    public void setNutritionScoreFr(int nutritionScoreFr) {
        this.nutritionScoreFr = nutritionScoreFr;
    }
    public int getNutritionScoreFr() {
        return nutritionScoreFr;
    }


    public void setFatServing(String fatServing) {
        this.fatServing = fatServing;
    }
    public String getFatServing() {
        return fatServing;
    }


    public void setSugarsUnit(String sugarsUnit) {
        this.sugarsUnit = sugarsUnit;
    }
    public String getSugarsUnit() {
        return sugarsUnit;
    }


    public void setSugars100g(String sugars100g) {
        this.sugars100g = sugars100g;
    }
    public String getSugars100g() {
        return sugars100g;
    }


    public void setSodiumUnit(String sodiumUnit) {
        this.sodiumUnit = sodiumUnit;
    }
    public String getSodiumUnit() {
        return sodiumUnit;
    }


    public void setSaturatedFatServing(String saturatedFatServing) {
        this.saturatedFatServing = saturatedFatServing;
    }
    public String getSaturatedFatServing() {
        return saturatedFatServing;
    }


    public void setSodium100g(String sodium100g) {
        this.sodium100g = sodium100g;
    }
    public String getSodium100g() {
        return sodium100g;
    }


    public void setSaturatedFatUnit(String saturatedFatUnit) {
        this.saturatedFatUnit = saturatedFatUnit;
    }
    public String getSaturatedFatUnit() {
        return saturatedFatUnit;
    }


    public void setFiberUnit(String fiberUnit) {
        this.fiberUnit = fiberUnit;
    }
    public String getFiberUnit() {
        return fiberUnit;
    }


    public void setEnergy(String energy) {
        this.energy = energy;
    }
    public String getEnergy() {
        return energy;
    }


    public void setEnergyUnit(String energyUnit) {
        this.energyUnit = energyUnit;
    }
    public String getEnergyUnit() {
        return energyUnit;
    }


    public void setSugarsServing(String sugarsServing) {
        this.sugarsServing = sugarsServing;
    }
    public String getSugarsServing() {
        return sugarsServing;
    }


    public void setCarbohydrates100g(String carbohydrates100g) {
        this.carbohydrates100g = carbohydrates100g;
    }
    public String getCarbohydrates100g() {
        return carbohydrates100g;
    }


    public void setNutritionScoreUk(int nutritionScoreUk) {
        this.nutritionScoreUk = nutritionScoreUk;
    }
    public int getNutritionScoreUk() {
        return nutritionScoreUk;
    }


    public void setProteins100g(String proteins100g) {
        this.proteins100g = proteins100g;
    }
    public String getProteins100g() {
        return proteins100g;
    }


    public void setFiberServing(String fiberServing) {
        this.fiberServing = fiberServing;
    }
    public String getFiberServing() {
        return fiberServing;
    }


    public void setCarbohydratesServing(String carbohydratesServing) {
        this.carbohydratesServing = carbohydratesServing;
    }
    public String getCarbohydratesServing() {
        return carbohydratesServing;
    }


    public void setEnergyServing(String energyServing) {
        this.energyServing = energyServing;
    }
    public String getEnergyServing() {
        return energyServing;
    }


    public void setFat100g(String fat100g) {
        this.fat100g = fat100g;
    }
    public String getFat100g() {
        return fat100g;
    }


    public void setSaturatedFat100g(String saturatedFat100g) {
        this.saturatedFat100g = saturatedFat100g;
    }
    public String getSaturatedFat100g() {
        return saturatedFat100g;
    }


    public void setNutritionScoreUk100g(int nutritionScoreUk100g) {
        this.nutritionScoreUk100g = nutritionScoreUk100g;
    }
    public int getNutritionScoreUk100g() {
        return nutritionScoreUk100g;
    }


    public void setFiber(String fiber) {
        this.fiber = fiber;
    }
    public String getFiber() {
        return fiber;
    }


    public void setSalt100g(String salt100g) {
        this.salt100g = salt100g;
    }
    public String getSalt100g() {
        return salt100g;
    }


    public void setSaltServing(String saltServing) {
        this.saltServing = saltServing;
    }
    public String getSaltServing() {
        return saltServing;
    }


    public void setFiber100g(String fiber100g) {
        this.fiber100g = fiber100g;
    }
    public String getFiber100g() {
        return fiber100g;
    }


    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
    public String getCarbohydrates() {
        return carbohydrates;
    }


    public void setEnergy100g(String energy100g) {
        this.energy100g = energy100g;
    }
    public String getEnergy100g() {
        return energy100g;
    }


    public void setSaturatedFat(String saturatedFat) {
        this.saturatedFat = saturatedFat;
    }
    public String getSaturatedFat() {
        return saturatedFat;
    }

}