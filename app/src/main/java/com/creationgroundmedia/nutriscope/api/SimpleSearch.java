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

package com.creationgroundmedia.nutriscope.api;

import android.util.Log;

import com.creationgroundmedia.nutriscope.pojos.ApiSearchResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by George Cohn III on 7/4/16.
 * Wrapper for SimpleSearchRetrofit to run an Open Food Facts search and parse the JSON
 */

public class SimpleSearch {
    private static final String LOG_TAG = SimpleSearch.class.getSimpleName();
    private Retrofit mRetrofit;

    public interface SearchResult {
        void result(ApiSearchResult r);
    }

    public SimpleSearch() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://world.openfoodfacts.org/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public void search(String key, int page, final SearchResult sr) {

        SimpleSearchRetrofit.HttpService service = mRetrofit.create(SimpleSearchRetrofit.HttpService.class);

        Call<ApiSearchResult> call = service.search(key, Integer.toString(page));

//        Log.d(LOG_TAG, "HTTP: " + call.request().url());

        call.enqueue(new Callback<ApiSearchResult>() {
            @Override
            public void onResponse(Call<ApiSearchResult> call, Response<ApiSearchResult> response) {
//                Log.d(LOG_TAG, "Response status code: " + response.code());
                if (response.isSuccessful()) {
                    ApiSearchResult result = response.body();
//                    Log.d(LOG_TAG, "Count: " + result.getCount());
//                    Log.d(LOG_TAG, "PageSize: " + result.getPageSize());
                    sr.result(result);
                } else {
                    sr.result(null);
                }
            }

            @Override
            public void onFailure(Call<ApiSearchResult> call, Throwable t) {
//                Log.d(LOG_TAG, "onFailure");
//                Log.e(LOG_TAG, t.getMessage());
                sr.result(null);
            }
        });
    }
}
