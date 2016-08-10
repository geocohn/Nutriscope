package com.creationgroundmedia.nutriscope.api;

import com.creationgroundmedia.nutriscope.pojos.ApiSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by geo on 6/27/16.
 */

public class UpcSearchRetrofit {

    public interface HttpService {
        @GET("{code}")
        Call<ApiSearchResult> search(@Path("code") String code);
    }
}