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

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiSearchResult {

    @JsonProperty("page_size")
    private String pageSize;
    private String count;
    private int skip;
    private int page;
    private List<Product> products;


    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
    public String getPageSize() {
        return pageSize;
    }


    public void setCount(String count) {
        this.count = count;
    }
    public String getCount() {
        return count;
    }


    public void setSkip(int skip) {
        this.skip = skip;
    }
    public int getSkip() {
        return skip;
    }


    public void setPage(int page) {
        this.page = page;
    }
    public int getPage() {
        return page;
    }


    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public List<Product> getProducts() {
        return products;
    }

}