package com.creationgroundmedia.nutriscope.api;

import com.creationgroundmedia.nutriscope.pojos.ApiSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by geo on 6/27/16.
 */

public class SimpleSearchRetrofit {

    public interface HttpService {
        @GET("cgi/search.pl?search_simple=1&action=process&json=1")
        Call<ApiSearchResult> search(@Query("search_terms") String key,
                                     @Query("page") String page);
    }
}