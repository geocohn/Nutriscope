package com.creationgroundmedia.nutriscope.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.creationgroundmedia.nutriscope.api.SimpleSearch;
import com.creationgroundmedia.nutriscope.api.UpcSearch;
import com.creationgroundmedia.nutriscope.data.NutriscopeContract;
import com.creationgroundmedia.nutriscope.pojos.ApiSearchResult;
import com.creationgroundmedia.nutriscope.pojos.Product;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class SearchService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_PRODUCTSEARCH = "com.creationgroundmedia.nutriscope.service.action.PRODUCTSEARCH";
    private static final String ACTION_UPCSEARCH = "com.creationgroundmedia.nutriscope.service.action.UPCSEARCH";

    private static final String PRODUCT_KEY = "com.creationgroundmedia.nutriscope.service.extra.PRODUCT_KEY";
    private static final String UPC_KEY = "com.creationgroundmedia.nutriscope.service.extra.UPC_KEY";
    private static final String LOG_TAG = SearchService.class.getSimpleName();
    private static CountDownLatch mGate;
    private ApiSearchResult mSearchResult;


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
            mGate.countDown();
        }
    }


    private void handleProductSearch(String productKey) {
        int readSoFar;
        int page;
        int count;
        Uri productUri = NutriscopeContract.ProductsEntry.buildProductSearchUri(productKey);

        Log.d(LOG_TAG, "handleProductSearch productUri: " + productUri);

        getContentResolver().delete(productUri, "1", null);

        SimpleSearch simpleSearch = new SimpleSearch();
        page = 1;
        do {
            final CountDownLatch gate = new CountDownLatch(1);
            readSoFar = 0;
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
                gate.await(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (mSearchResult != null) {
                count = Integer.parseInt(mSearchResult.getCount());
                readSoFar = Integer.parseInt(mSearchResult.getPageSize()) * page;
                loadProvider(productUri, mSearchResult);
                page++;
            }
        } while (readSoFar > 0 && readSoFar < count && mSearchResult != null);

        getContentResolver().notifyChange(productUri, null);
    }


    private void handleUpcSearch(String upcKey) {
        Uri upcSearchUri = NutriscopeContract.ProductsEntry.buildUpcSearchUri(upcKey);

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
            gate.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mSearchResult != null) {
            loadProvider(upcSearchUri, mSearchResult);
        }

        getContentResolver().notifyChange(upcSearchUri, null);
    }


    private void loadProvider(Uri uri, ApiSearchResult searchResult) {
        int count = Integer.parseInt(searchResult.getCount());
        Vector<ContentValues> contentValuesVector = new Vector<>(count);
        List<Product> products = searchResult.getProducts();
        for (Product product : products) {
            ContentValues productValues = new ContentValues();

            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID, product.getId());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_ADDITIVES, product.getAdditives());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_ALLERGENS, product.getAllergens());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_BRANDS, product.getBrands());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_CATEGORIES, product.getCategories());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_CITY, String.valueOf(product.getCitiesTags()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_ENERGY, product.getNutriments().getEnergy100g());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_FATS, product.getNutriments().getEnergy100g());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_IMAGE, product.getImageFrontUrl());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_IMAGESMALL, product.getImageFrontSmallUrl());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_IMAGETHUMB, product.getImageFrontThumbUrl());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_INGREDIENTS, String.valueOf(product.getIngredients()));
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_INGREDIENTSIMAGE, product.getImageIngredientsUrl());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_FATS, product.getNutriments().getFat100g());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_LABELS, product.getLabels());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_NAME, product.getProductName());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_PACKAGING, product.getPackaging());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_QUANTITY, product.getQuantity());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SALT, product.getNutriments().getSalt100g());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SATURATEDFATS, product.getNutriments().getSaturatedFat100g());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_STORES, product.getStores());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_SUGARS, product.getNutriments().getSugars100g());
            productValues.put(NutriscopeContract.ProductsEntry.COLUMN_TRACES, product.getTraces());

            contentValuesVector.add(productValues);
        }

        if (contentValuesVector.size() > 0) {
            ContentValues[] contentValuesArray = new ContentValues[contentValuesVector.size()];
            contentValuesVector.toArray(contentValuesArray);

            getContentResolver().bulkInsert(uri, contentValuesArray);
        }
    }
}
