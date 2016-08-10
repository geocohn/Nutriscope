package com.creationgroundmedia.nutriscope;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.creationgroundmedia.nutriscope.data.NutriscopeContract;
import com.creationgroundmedia.nutriscope.data.NutriscopeDbHelper;
import com.creationgroundmedia.nutriscope.service.SearchService;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.creationgroundmedia.nutriscope.data.NutriscopeContract.ProductsEntry.CONTENT_URI;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by geo on 7/12/16.
 */


@RunWith(AndroidJUnit4.class)

public class ProviderTest {
    private static final String LOG_TAG = ProviderTest.class.getSimpleName();
    private Boolean success;

    void deleteDatabase() {
        InstrumentationRegistry.getTargetContext().deleteDatabase(NutriscopeDbHelper.DATABASE_NAME);
    }

    @Test
    public void testProviderUris() throws Exception {
        String productSearchKey = "chocolate stuff";
        String upcSearchKey = "76223006xxxxx";
        long productId = 7622300623456L;
        Uri uri;

        uri = NutriscopeContract.ProductsEntry.buildProductUri(productId);
        assertEquals(NutriscopeContract.ProductsEntry.getProductFromUri(uri), productId);

        uri = NutriscopeContract.ProductsEntry.buildProductSearchUri(productSearchKey);
        assertEquals(NutriscopeContract.ProductsEntry.getProductSearchStringFromUri(uri), productSearchKey);

        uri = NutriscopeContract.ProductsEntry.buildUpcSearchUri(upcSearchKey);
        assertEquals(NutriscopeContract.ProductsEntry.getUpcSearchStringFromUri(uri), upcSearchKey);

    }

    @Test
    public void testSearchService() throws Exception {
        deleteDatabase();

        String productSearchKey = "chocolate stuff";
        Context mContext = InstrumentationRegistry.getTargetContext();

        SearchService.startActionProductSearch(mContext, productSearchKey, 5);

        Cursor countCursor = mContext.getContentResolver().query(NutriscopeContract.ProductsEntry.buildProductSearchUri(productSearchKey),
                new String[] {"count(*) AS count"},
                null,
                null,
                null);

        countCursor.moveToFirst();
        int count = countCursor.getInt(0);

        Log.d(LOG_TAG, "testSearchService count: " + count);
        assertTrue(count != 0);

    }
}