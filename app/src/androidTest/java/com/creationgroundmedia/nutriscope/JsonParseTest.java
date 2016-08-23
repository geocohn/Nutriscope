package com.creationgroundmedia.nutriscope;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.creationgroundmedia.nutriscope.api.SimpleSearch;
import com.creationgroundmedia.nutriscope.api.SimpleSearchRetrofit;
import com.creationgroundmedia.nutriscope.api.UpcSearch;
import com.creationgroundmedia.nutriscope.api.UpcSearchRetrofit;
import com.creationgroundmedia.nutriscope.pojos.ApiSearchResult;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JsonParseTest {
    private static final String LOG_TAG = JsonParseTest.class.getSimpleName();
    private Boolean success;
    private ApiSearchResult searchResult;

    @Test
    public void testSimpleSearchRetrofit() throws Exception {
        final CountDownLatch gate = new CountDownLatch(1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://world.openfoodfacts.org/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        SimpleSearchRetrofit.HttpService service = retrofit.create(SimpleSearchRetrofit.HttpService.class);

        Call<ApiSearchResult> call = service.search("chocolate", Integer.toString(1));

        Log.d(LOG_TAG, "HTTP: " + call.request().url());

        call.enqueue(new Callback<ApiSearchResult>() {
            @Override
            public void onResponse(Call<ApiSearchResult> call, Response<ApiSearchResult> response) {
                Log.d(LOG_TAG, "Response status code: " + response.code());
                success = response.isSuccessful();
                if (success) {
                    ApiSearchResult result = response.body();
                    Log.d(LOG_TAG, "Count: " + result.getCount());
                    assertNotEquals(result.getCount(), "0");
                    assertNotEquals(result.getCount(), "1");
                }
                gate.countDown();
            }

            @Override
            public void onFailure(Call<ApiSearchResult> call, Throwable t) {
                success = false;
                Log.d(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, t.getMessage());
                gate.countDown();
            }
        });

        gate.await(5, TimeUnit.SECONDS);
        assertTrue(success);
    }

    @Test
    public void testUpcSearchRetrofit() throws Exception {
        final CountDownLatch gate = new CountDownLatch(1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://world.openfoodfacts.org/code/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        UpcSearchRetrofit.HttpService service = retrofit.create(UpcSearchRetrofit.HttpService.class);

        Call<ApiSearchResult> call = service.search("7622300631574.json");

        Log.d(LOG_TAG, "HTTP: " + call.request().url());

        call.enqueue(new Callback<ApiSearchResult>() {
            @Override
            public void onResponse(Call<ApiSearchResult> call, Response<ApiSearchResult> response) {
                Log.d(LOG_TAG, "Response status code: " + response.code());
                success = response.isSuccessful();
                if (success) {
                    ApiSearchResult result = response.body();
                    Log.d(LOG_TAG, "Count: " + result.getCount());
                    assertEquals(result.getCount(), "1");
                }
                gate.countDown();
            }

            @Override
            public void onFailure(Call<ApiSearchResult> call, Throwable t) {
                success = false;
                Log.d(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, t.getMessage());
                gate.countDown();
            }
        });

        gate.await(5, TimeUnit.SECONDS);
        assertTrue(success);
    }

    @Test
    public void testUpcWildcardSearchRetrofit() throws Exception {
        final CountDownLatch gate = new CountDownLatch(1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://world.openfoodfacts.org/code/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        UpcSearchRetrofit.HttpService service = retrofit.create(UpcSearchRetrofit.HttpService.class);

        Call<ApiSearchResult> call = service.search("76223006xxxxx.json");

        Log.d(LOG_TAG, "HTTP: " + call.request().url());

        call.enqueue(new Callback<ApiSearchResult>() {
            @Override
            public void onResponse(Call<ApiSearchResult> call, Response<ApiSearchResult> response) {
                Log.d(LOG_TAG, "Response status code: " + response.code());
                success = response.isSuccessful();
                if (success) {
                    ApiSearchResult result = response.body();
                    Log.d(LOG_TAG, "Count: " + result.getCount());
                    assertNotEquals(result.getCount(), "0");
                    assertNotEquals(result.getCount(), "1");
                }
                gate.countDown();
            }

            @Override
            public void onFailure(Call<ApiSearchResult> call, Throwable t) {
                success = false;
                Log.d(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, t.getMessage());
                gate.countDown();
            }
        });

        gate.await(5, TimeUnit.SECONDS);
        assertTrue(success);
    }


    @Test
    public void testSimpleSearch() throws Exception {
        final CountDownLatch gate = new CountDownLatch(1);

        SimpleSearch simpleSearch = new SimpleSearch();

        searchResult = null;
        simpleSearch.search("msg", 1, new SimpleSearch.SearchResult() {
            @Override
            public void result(ApiSearchResult r) {
                searchResult = r;
                gate.countDown();
            }
        });

        gate.await(5, TimeUnit.SECONDS);
        assertNotNull(searchResult);
        assertNotEquals(searchResult.getCount(), "0");
    }

    @Test
    public void testUpcSearch() throws Exception {
        final CountDownLatch gate = new CountDownLatch(1);

        UpcSearch upcSearch = new UpcSearch();

        searchResult = null;
        upcSearch.search("7622300631574", new UpcSearch.SearchResult() {
            @Override
            public void result(ApiSearchResult r) {
                searchResult = r;
                gate.countDown();
            }
        });

        gate.await(5, TimeUnit.SECONDS);
        assertNotNull(searchResult);
        assertNotEquals(searchResult.getCount(), "0");
    }

    @Test
    public void testUpcWildcardSearch() throws Exception {
        final CountDownLatch gate = new CountDownLatch(1);

        UpcSearch upcSearch = new UpcSearch();

        searchResult = null;
        upcSearch.search("76223006xxxxx", new UpcSearch.SearchResult() {
            @Override
            public void result(ApiSearchResult r) {
                searchResult = r;
                gate.countDown();
            }
        });

        gate.await(5, TimeUnit.SECONDS);
        assertNotNull(searchResult);
        assertNotEquals(searchResult.getCount(), "0");
    }
}
