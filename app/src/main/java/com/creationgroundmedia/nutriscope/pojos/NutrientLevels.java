/*
 * Copyright 2016 George Cohn III
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.creationgroundmedia.nutriscope.pojos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by George Cohn III on 6/27/16.
 * Generated from API sample query result
 * using {@link <a href="http://www.jsonschema2pojo.org/">http://www.jsonschema2pojo.org/</a>}
 */

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