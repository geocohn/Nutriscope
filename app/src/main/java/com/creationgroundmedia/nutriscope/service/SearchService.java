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
     *
     * @see IntentService
     */
    public static void startActionProductSearch(Context context, String productKey) {
        startActionProductSearch(context, productKey, 0);
    }

    public static void startActionProductSearch(Context context, String productKey, int timeout) {
        Log.d(LOG_TAG, "startActionProductSearch(context, " + productKey + ") called");
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
     *
     * @see IntentService
     */
    public static void startActionUpcSearch(Context context, String upcKey) {
        startActionUpcSearch(context, upcKey, 0);
    }

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
        Log.d(LOG_TAG, "OnHandleIntent called");
        if (intent != null) {
            final String action = intent.getAction();
            Log.d(LOG_TAG, "action: " + action);
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


        Log.d(LOG_TAG, "handleProductSearch productUri: " + productUri);

        getContentResolver().delete(productUri, "1", null);

        SimpleSearch simpleSearch = new SimpleSearch();
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

    private void loadProvider(Uri uri, ApiSearchResult searchResult) {
        int count = Integer.parseInt(searchResult.getCount());
        Vector<ContentValues> contentValuesVector = new Vector<>(count);
        List<Product> products = searchResult.getProducts();
        for (Product product : products) {
            ContentValues productValues = new ContentValues();

            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID,
                    product.getId());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_ADDITIVES,
                    product.getAdditives());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_ALLERGENS,
                    product.getAllergens());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_BRANDS,
                    spacesAfterCommas(product.getBrands()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_CATEGORIES,
                    spacesAfterCommas(product.getCategories()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_CITY,
                    String.valueOf(product.getOrigins()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_ENERGY,
                    (String) concat(product.getNutriments().getEnergy100g(),
                            product.getNutriments().getEnergyUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_FATLEVEL,
                    product.getNutrientLevels().getFat());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_FAT,
                    (String) concat(product.getNutriments().getFat100g(),
                            product.getNutriments().getFatUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_FIBER,
                    (String) concat(product.getNutriments().getFiber100g(),
                            product.getNutriments().getFiberUnit()));
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
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_QUANTITY,
                    product.getQuantity());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SALT,
                    (String) concat(product.getNutriments().getSalt100g(), "g"));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SALTLEVEL,
                    product.getNutrientLevels().getSalt());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SATURATEDFATS,
                    (String) concat(product.getNutriments().getSaturatedFat100g(),
                            product.getNutriments().getSaturatedFatUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SATURATEDFATSLEVEL,
                    product.getNutrientLevels().getSaturatedFat());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_STORES,
                    spacesAfterCommas(product.getStores()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SUGARS,
                    (String) concat(product.getNutriments().getSugars100g(),
                            product.getNutriments().getSugarsUnit()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SUGARSLEVEL,
                    product.getNutrientLevels().getSugars());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_TRACES,
                    product.getTraces());

            contentValuesVector.add(productValues);
        }

        if (contentValuesVector.size() > 0) {
            ContentValues[] contentValuesArray = new ContentValues[contentValuesVector.size()];
            contentValuesVector.toArray(contentValuesArray);

            getContentResolver().bulkInsert(uri, contentValuesArray);
        }
    }

    private String commaSeparateIngredients(List<Ingredients> ingredients) {
        String ingredientString = null;

        /**
         * Sort by rank
         */
        if (ingredients != null) {
            Collections.sort(ingredients, new Comparator<Ingredients>() {
                @Override
                public int compare(Ingredients t1, Ingredients t2) {
                    return t1.getRank() - t2.getRank();
                }
            });
        }
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
