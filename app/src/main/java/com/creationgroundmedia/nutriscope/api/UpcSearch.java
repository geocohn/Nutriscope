package com.creationgroundmedia.nutriscope.api;

import android.util.Log;

import com.creationgroundmedia.nutriscope.pojos.ApiSearchResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by geo on 7/4/16.
 */

public class UpcSearch {
    private static final String LOG_TAG = UpcSearch.class.getSimpleName();
    private Retrofit mRetrofit;

    public interface SearchResult {
        void result(ApiSearchResult r);
    }

    public UpcSearch() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://world.openfoodfacts.org/code/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public void search(String upc, final SearchResult sr) {

        UpcSearchRetrofit.HttpService service = mRetrofit.create(UpcSearchRetrofit.HttpService.class);

        Call<ApiSearchResult> call = service.search(upc + ".json");

        Log.d(LOG_TAG, "HTTP: " + call.request().url());

        call.enqueue(new Callback<ApiSearchResult>() {
            @Override
            public void onResponse(Call<ApiSearchResult> call, Response<ApiSearchResult> response) {
                Log.d(LOG_TAG, "Response status code: " + response.code());
                if (response.isSuccessful()) {
                    ApiSearchResult result = response.body();
                    Log.d(LOG_TAG, "Count: " + result.getCount());
                    Log.d(LOG_TAG, "PageSize: " + result.getPageSize());
                    sr.result(result);
                } else {
                    sr.result(null);
                }
            }

            @Override
            public void onFailure(Call<ApiSearchResult> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, t.getMessage());
                sr.result(null);
            }
        });
    }
}
