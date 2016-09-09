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

package com.creationgroundmedia.nutriscope.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.util.Log;

import com.creationgroundmedia.nutriscope.R;
import com.creationgroundmedia.nutriscope.api.SimpleSearch;
import com.creationgroundmedia.nutriscope.api.UpcSearch;
import com.creationgroundmedia.nutriscope.data.NutriscopeContract;
import com.creationgroundmedia.nutriscope.pojos.ApiSearchResult;
import com.creationgroundmedia.nutriscope.pojos.Ingredients;
import com.creationgroundmedia.nutriscope.pojos.NutrientLevels;
import com.creationgroundmedia.nutriscope.pojos.Nutriments;
import com.creationgroundmedia.nutriscope.pojos.Product;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.text.TextUtils.concat;

/**
 * Created by George Cohn III on 6/27/16.
 * Does the search using the
 * {@link <a href="http://en.wiki.openfoodfacts.org/API">Open Food Facts API</a>}
 * and loads the result into the Content Provider.
 * It deletes all rows in the Content Provider first
 * so that the results consist of exactly the elements from the search.
 *
 * It also has code for API/Network status management static methpds
 */

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class SearchService extends IntentService {
    // IntentService can perform, e.g. ACTION_PRODUCTSEARCH
    private static final String ACTION_PRODUCTSEARCH = "com.creationgroundmedia.nutriscope.service.action.PRODUCTSEARCH";
    private static final String ACTION_UPCSEARCH = "com.creationgroundmedia.nutriscope.service.action.UPCSEARCH";

    private static final String PRODUCT_KEY = "com.creationgroundmedia.nutriscope.service.extra.PRODUCT_KEY";
    private static final String UPC_KEY = "com.creationgroundmedia.nutriscope.service.extra.UPC_KEY";
    private static final String LOG_TAG = SearchService.class.getSimpleName();
    private static CountDownLatch mGate;
    private ApiSearchResult mSearchResult;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({API_STATUS_OK,
            API_STATUS_SERVER_DOWN,
            API_STATUS_SERVER_INVALID,
            API_STATUS_UNKNOWN,
            API_STATUS_INVALID,
            API_STATUS_NO_MATCH,
            API_STATUS_SEARCHING})

    public @interface ApiStatus {}

    public static final int API_STATUS_OK = 0;
    public static final int API_STATUS_SEARCHING = 1;
    public static final int API_STATUS_SERVER_DOWN = 2;
    public static final int API_STATUS_SERVER_INVALID = 3;
    public static final int API_STATUS_UNKNOWN = 4;
    public static final int API_STATUS_INVALID = 5;
    public static final int API_STATUS_NO_MATCH = 6;

    /**
     * Returns true if the network is available or about to become available.
     *
     * @param context Context used to get the ConnectivityManager
     * @return true if the network is available
     */
    static public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /**
     *
     * @param context Context used to get the SharedPreferences
     * @return the location status integer type
     */
    @SuppressWarnings("ResourceType")
    static public @ApiStatus
    int getApiStatus(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(context.getString(R.string.pref_api_status_key), API_STATUS_UNKNOWN);
    }

    /**
     * Sets the location status.
     * @param context Context used to get the SharedPreferences
     * @param status Specific ApiStatus to set it to
     */
    static public void setApiStatus(Context context, @ApiStatus int status) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(context.getString(R.string.pref_api_status_key), status);
        spe.apply();
    }

    /**
     * Resets the location status.  (Sets it to SearchService.API_STATUS_UNKNOWN)
     * @param context Context used to get the SharedPreferences
     */
    static public void resetApiStatus(Context context) {
        setApiStatus(context, API_STATUS_UNKNOWN);
    }

    public static String getApiStatusString(Context context, @ApiStatus int apiStatus) {

        if (!isNetworkAvailable(context)) {
            return context.getString(R.string.api_status_nonetwork);
        }
        switch (apiStatus) {
            case API_STATUS_INVALID: {
                return context.getString(R.string.api_status_invalidsearch);
            }
            case API_STATUS_NO_MATCH: {
                return context.getString(R.string.api_status_nomatches);
            }
            case API_STATUS_OK:
            case API_STATUS_UNKNOWN:
            case API_STATUS_SEARCHING: {
                return "";
            }
            case API_STATUS_SERVER_DOWN:
            case API_STATUS_SERVER_INVALID: {
                return context.getString(R.string.api_status_badserver);
            }
        }
        return null;
    }

    public SearchService() {
        super("SearchService");
    }

    /**
     * Starts this service to perform action ACTION_PRODUCTSEARCH with the given parameters. If
     * the service is already performing a task this action will be queued.
     * @param context the context of the caller
     * @param productKey the key for the API's simple search
     *
     * @see IntentService
     */
    public static void startActionProductSearch(Context context, String productKey) {
        startActionProductSearch(context, productKey, 0);
    }

    /**
     * Starts this service to perform action ACTION_PRODUCTSEARCH with the given parameters. If
     * the service is already performing a task this action will be queued.
     * @param context the context of the caller
     * @param productKey the key for the API's simple search
     * @param timeout is needed by test modules, so that they don't finish before the results arrive
     */
    public static void startActionProductSearch(Context context, String productKey, int timeout) {
//        Log.d(LOG_TAG, "startActionProductSearch(context, " + productKey + ") called");
        Intent intent = new Intent(context, SearchService.class);
        intent.setAction(ACTION_PRODUCTSEARCH);
        intent.putExtra(PRODUCT_KEY, productKey);
        context.startService(intent);
        if (timeout == 0) {
            mGate = null;
        } else {
            mGate = new CountDownLatch(1);
            try {
                mGate.await(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts this service to perform action ACTION_UPCSEARCH with the given parameters. If
     * the service is already performing a task this action will be queued.
     * @param context the context of the caller
     * @param upcKey the key for the API's UPC lookup
     *
     * @see IntentService
     */
    public static void startActionUpcSearch(Context context, String upcKey) {
        startActionUpcSearch(context, upcKey, 0);
    }

    /**
     * Starts this service to perform action ACTION_UPCSEARCH with the given parameters. If
     * the service is already performing a task this action will be queued.
     * @param context the context of the caller
     * @param upcKey the key for the API's UPC lookup
     * @param timeout is needed by test modules, so that they don't finish before the results arrive
     */

    public static void startActionUpcSearch(Context context, String upcKey, int timeout) {
        Intent intent = new Intent(context, SearchService.class);
        intent.setAction(ACTION_UPCSEARCH);
        intent.putExtra(UPC_KEY, upcKey);
        context.startService(intent);
        if (timeout == 0) {
            mGate = null;
        } else {
            mGate = new CountDownLatch(1);
            try {
                mGate.await(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        Log.d(LOG_TAG, "OnHandleIntent called");
        if (intent != null) {
            final String action = intent.getAction();
//            Log.d(LOG_TAG, "action: " + action);
            if (ACTION_PRODUCTSEARCH.equals(action)) {
                final String productKey = intent.getStringExtra(PRODUCT_KEY);
                handleProductSearch(productKey);
            } else if (ACTION_UPCSEARCH.equals(action)) {
                final String upcKey = intent.getStringExtra(UPC_KEY);
                handleUpcSearch(upcKey);
            }
        }
        if (mGate != null) {
            /**
             * The countdown latch is only used in test cases,
             * otherwise the test finishes before anything gets to happen
             */
            mGate.countDown();
        }
    }


    private void handleProductSearch(String productKey) {
        int totalTiemsRead;
        int page;
        int count;
        Uri productUri = NutriscopeContract.ProductsEntry.buildProductSearchUri(productKey);
        int searchTimeout = Integer.parseInt(getString(R.string.search_timeout));


//        Log.d(LOG_TAG, "handleProductSearch productUri: " + productUri);

        // Clear the Content Provider database
        getContentResolver().delete(productUri, "1", null);

        SimpleSearch simpleSearch = new SimpleSearch();

        // results come in as a series of pages of JSON.
        // Iterate through the pages and load the data into the Content Provider
        page = 1;
        do {
            final CountDownLatch gate = new CountDownLatch(1);
            totalTiemsRead = 0;
            count = 0;
            mSearchResult = null;
            simpleSearch.search(productKey, page, new SimpleSearch.SearchResult() {
                @Override
                public void result(ApiSearchResult r) {
                    mSearchResult = r;
                    gate.countDown();
                }
            });

            try {
                gate.await(searchTimeout, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (mSearchResult != null) {
                count = Integer.parseInt(mSearchResult.getCount());
                totalTiemsRead = Integer.parseInt(mSearchResult.getPageSize()) * page;
                loadProvider(productUri, mSearchResult);
                page++;
            }
        } while (totalTiemsRead > 0 && totalTiemsRead < count && mSearchResult != null);

        setStatus(count);

        getContentResolver().notifyChange(productUri, null);
    }


    private void handleUpcSearch(String upcKey) {
        // Similar to HandleProductSearch() above, but the API for UPC lookup
        // doesn't allow the user to specify pages.
        // Therefore we can only deliver no more than a single page's worth,
        // even though there might be more, due to wildcard characters ('x') in the search string
        Uri upcSearchUri = NutriscopeContract.ProductsEntry.buildUpcSearchUri(upcKey);
        int searchTimeout = Integer.parseInt(getString(R.string.search_timeout));

        getContentResolver().delete(upcSearchUri, "1", null);

        UpcSearch upcSearch = new UpcSearch();
        final CountDownLatch gate = new CountDownLatch(1);

        mSearchResult = null;
        upcSearch.search(upcKey, new UpcSearch.SearchResult() {
            @Override
            public void result(ApiSearchResult r) {
                mSearchResult = r;
                gate.countDown();
            }
        });

        try {
            gate.await(searchTimeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mSearchResult != null) {
            loadProvider(upcSearchUri, mSearchResult);
        }

        setStatus(Integer.parseInt(mSearchResult.getCount()));

        getContentResolver().notifyChange(upcSearchUri, null);
    }

    private void setStatus(int itemsRead) {
        if (itemsRead == 0) {
            if (mSearchResult == null) {
                setApiStatus(getApplicationContext(), API_STATUS_SERVER_DOWN);
            } else {
                setApiStatus(getApplicationContext(), API_STATUS_NO_MATCH);
            }
        } else {
            setApiStatus(getApplicationContext(), API_STATUS_OK);
        }
    }

    /**
     * @param uri the Content Provider URI, based on the search type and search key
     * @param searchResult the pojo result of the API call
     * This is the workhorse of the service. It flattens and manicures the
     * data provided by the (somewhat) structured pojos.
     * That allows us to have a simple flat database instead of a complex relational database
     */
    private void loadProvider(Uri uri, ApiSearchResult searchResult) {
        int count = Integer.parseInt(searchResult.getCount());
        // Having empty pojos on hand reduces a lot of checking for null inside the loop
        Nutriments emptyNutriments = new Nutriments();
        NutrientLevels emptyNutrientLevels = new NutrientLevels();

        Vector<ContentValues> contentValuesVector = new Vector<>(count);
        List<Product> products = searchResult.getProducts();

        for (Product product : products) {
            Nutriments nutriments = product.getNutriments();
            if (nutriments == null) {
                nutriments = emptyNutriments;
            }
            NutrientLevels nutrientLevels = product.getNutrientLevels();
            if (nutrientLevels == null) {
                nutrientLevels = emptyNutrientLevels;
            }

            ContentValues productValues = new ContentValues();

            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID,
                    product.getId());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_ADDITIVES,
                    product.getAdditives());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_ALLERGENS,
                    product.getAllergens());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_BRANDS,
                    spacesAfterCommas(product.getBrands()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_CARBOHYDRATES,
                    quantityWithUnits(nutriments.getCarbohydrates100g(),
                            nutriments.getCarbohydratesUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_CATEGORIES,
                    spacesAfterCommas(product.getCategories()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_CITY,
                    String.valueOf(product.getOrigins()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_ENERGY,
                    quantityWithUnits(nutriments.getEnergy100g(),
                            nutriments.getEnergyUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_FATLEVEL,
                    nutrientLevels.getFat());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_FAT,
                    quantityWithUnits(nutriments.getFat100g(),
                            nutriments.getFatUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_FIBER,
                    quantityWithUnits(nutriments.getFiber100g(),
                            nutriments.getFiberUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_IMAGE,
                    product.getImageFrontUrl());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_IMAGESMALL,
                    product.getImageFrontSmallUrl());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_IMAGETHUMB,
                    product.getImageFrontThumbUrl());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_INGREDIENTS,
                    commaSeparateIngredients(product.getIngredients()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_INGREDIENTSIMAGE,
                    product.getImageIngredientsUrl());
             productValues.put(NutriscopeContract.ProductsEntry.COLUMN_LABELS,
                    spacesAfterCommas(product.getLabels()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_NAME,
                    product.getProductName());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_PACKAGING,
                    spacesAfterCommas(product.getPackaging()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_PROTEINS,
                    quantityWithUnits(nutriments.getProteins100g(),
                            nutriments.getProteinsUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_QUANTITY,
                    product.getQuantity());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SALT,
                    quantityWithUnits(nutriments.getSalt100g(), "g"));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SALTLEVEL,
                    nutrientLevels.getSalt());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SATURATEDFATS,
                    quantityWithUnits(nutriments.getSaturatedFat100g(),
                            nutriments.getSaturatedFatUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SATURATEDFATSLEVEL,
                    nutrientLevels.getSaturatedFat());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SODIUM,
                    quantityWithUnits(limitDigits(nutriments.getSodium100g(), 5),
                            nutriments.getSodiumUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_STORES,
                    spacesAfterCommas(product.getStores()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SUGARS,
                    quantityWithUnits(nutriments.getSugars100g(),
                            nutriments.getSugarsUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SUGARSLEVEL,
                    nutrientLevels.getSugars());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_TRACES,
                    spacesAfterCommas(product.getTraces()));

            contentValuesVector.add(productValues);
        }

        if (contentValuesVector.size() > 0) {
            ContentValues[] contentValuesArray = new ContentValues[contentValuesVector.size()];
            contentValuesVector.toArray(contentValuesArray);

            getContentResolver().bulkInsert(uri, contentValuesArray);
        }
    }

    // limitDigits was implemented only because
    // sodium was sometimes coming in out to several decimal places
    private String limitDigits(String value, int limit) {
        if (value == null || value.length() < limit)
            return value;
        return value.substring(0, limit);
    }

    private String quantityWithUnits(String quantity, String units) {
        return (String) concat(emptyIfNull(quantity), emptyIfNull(units));
    }

    private CharSequence emptyIfNull(String str) {
        if (str == null)
            return "";
        return str;
    }

    // Build an ingredients list string from a list of pojos
    private String commaSeparateIngredients(List<Ingredients> ingredients) {
        String ingredientString = null;

        if (ingredients == null)
            return "";

        /**
         * Sort by rank
         */
        Collections.sort(ingredients, new Comparator<Ingredients>() {
            @Override
            public int compare(Ingredients t1, Ingredients t2) {
                return t1.getRank() - t2.getRank();
            }
        });
        /**
         * Build a comma separated string
         */
        for (Ingredients ingredient : ingredients) {
            if (ingredient != null) {
                if (ingredientString == null) {
                    ingredientString = ingredient.getText();
                } else {
                    ingredientString += ", " + ingredient.getText();
                }
            }
        }
        return ingredientString;
    }

    private String spacesAfterCommas(String str) {
        if (str != null) {
            return(str.replace(",", ", "));
        }
        return null;
    }
}