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
        String productSearchKey = "chocolate";
        String upcSearchKey = "76223006xxxxx";
        long productId = 7622300623456L;
        Uri uri;

        uri = NutriscopeContract.ProductsEntry.buildProductRowIdUri(productId);
        assertEquals(NutriscopeContract.ProductsEntry.getProductRowIdFromUri(uri), productId);

        uri = NutriscopeContract.ProductsEntry.buildProductSearchUri(productSearchKey);
        assertEquals(NutriscopeContract.ProductsEntry.getProductSearchStringFromUri(uri), productSearchKey);

        uri = NutriscopeContract.ProductsEntry.buildUpcSearchUri(upcSearchKey);
        assertEquals(NutriscopeContract.ProductsEntry.getUpcSearchStringFromUri(uri), upcSearchKey);

    }

    @Test
    public void testSearchService() throws Exception {
        deleteDatabase();

        String productSearchKey = "msg";
        Context mContext = InstrumentationRegistry.getTargetContext();

        SearchService.startActionProductSearch(mContext, productSearchKey, 10);

        Cursor countCursor = mContext.getContentResolver().query(NutriscopeContract.ProductsEntry.buildProductSearchUri(productSearchKey),
                new String[] {"count(*) AS count"},
                null,
                null,
                null);

        countCursor.moveToFirst();
        int count = countCursor.getInt(0);

        mContext.getContentResolver()
        .acquireContentProviderClient(NutriscopeContract.ProductsEntry.buildProductSearchUri(productSearchKey))
        .getLocalContentProvider()
                .shutdown();

        Log.d(LOG_TAG, "testSearchService count: " + count);
        assertTrue(count != 0);
    }

    @Test
    public void testUpcService() throws Exception {
        deleteDatabase();

        String upcSearchKey = "76223006xxxxx";
        Context mContext = InstrumentationRegistry.getTargetContext();

        SearchService.startActionUpcSearch(mContext, upcSearchKey, 10);

        Cursor countCursor = mContext.getContentResolver().query(NutriscopeContract.ProductsEntry.buildProductSearchUri(upcSearchKey),
                new String[] {"count(*) AS count"},
                null,
                null,
                null);

        countCursor.moveToFirst();
        int count = countCursor.getInt(0);

        mContext.getContentResolver()
                .acquireContentProviderClient(NutriscopeContract.ProductsEntry.buildProductSearchUri(upcSearchKey))
                .getLocalContentProvider()
                .shutdown();

        Log.d(LOG_TAG, "testUpcService count: " + count);
        assertTrue(count != 0);
    }
}