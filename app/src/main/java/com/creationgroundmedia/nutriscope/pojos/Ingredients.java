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

/**
 * Created by George Cohn III on 6/27/16.
 * Generated from API sample query result
 * using {@link <a href="http://www.jsonschema2pojo.org/">http://www.jsonschema2pojo.org/</a>}
 */

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