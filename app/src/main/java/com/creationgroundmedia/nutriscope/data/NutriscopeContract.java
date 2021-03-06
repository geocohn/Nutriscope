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

package com.creationgroundmedia.nutriscope.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by George Cohn III on 7/4/16.
 * Basic Content Provider Contract
 */

public class NutriscopeContract {

    // Authority
    static final String CONTENT_AUTHORITY = "com.creationgroundmedia.nutriscope";
    //Base URI
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //Paths
    static final String PATH_PRODUCTS = "products";
    static final String PATH_PRODUCTSEARCH = "productsearch";
    static final String PATH_UPCSEARCH = "upcsearch";
    private static final String PATH_UPC = "upc";
    static final String PATH_ROWID = "rowid";

    /* Inner class that defines the table contents of the products table */
    public static final class ProductsEntry implements BaseColumns {

        static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCTS).build();
        public static final Uri PRODUCTSEARCH_URI =
                CONTENT_URI.buildUpon().appendPath(PATH_PRODUCTSEARCH).build();
        public static final Uri UPCSEARCH_URI =
                CONTENT_URI.buildUpon().appendPath(PATH_UPCSEARCH).build();
        static final Uri UPC_URI =
                CONTENT_URI.buildUpon().appendPath(PATH_UPC).build();
        static final Uri ROWID_URI =
                CONTENT_URI.buildUpon().appendPath(PATH_ROWID).build();

        static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        static final String TABLE_NAME = "products";
        public static final String COLUMN_PRODUCTID = "productid";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_IMAGESMALL = "imagesmall";
        public static final String COLUMN_IMAGETHUMB = "imagethumb";
        public static final String COLUMN_CATEGORIES = "categories";
        public static final String COLUMN_LABELS = "labels";
        public static final String COLUMN_BRANDS = "brands";
        public static final String COLUMN_STORES = "stores";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_PACKAGING = "packaging";
        public static final String COLUMN_INGREDIENTSIMAGE = "ingredientsimage";
        public static final String COLUMN_INGREDIENTS = "ingredients";
        public static final String COLUMN_ALLERGENS = "allergens";
        public static final String COLUMN_TRACES = "traces";
        public static final String COLUMN_ADDITIVES = "additives";
        public static final String COLUMN_CARBOHYDRATES = "carbohydrates";
        public static final String COLUMN_FAT = "fats";
        public static final String COLUMN_FATLEVEL = "fatlevel";
        public static final String COLUMN_FIBER = "fiber";
        public static final String COLUMN_PROTEINS = "proteins";
        public static final String COLUMN_SATURATEDFATS = "saturatedfats";
        public static final String COLUMN_SATURATEDFATSLEVEL = "saturatedfatslevel";
        public static final String COLUMN_SALT = "salt";
        public static final String COLUMN_SALTLEVEL = "saltlevel";
        public static final String COLUMN_SODIUM = "sodium";
        public static final String COLUMN_SUGARS = "sugars";
        public static final String COLUMN_SUGARSLEVEL = "sugarslevel";
        public static final String COLUMN_ENERGY = "energy";

        public static Uri buildProductRowIdUri(long id) {
            return ContentUris.withAppendedId(ROWID_URI, id);
        }

        public static Uri buildProductSearchUri(String key) {
            return PRODUCTSEARCH_URI.buildUpon()
                    .appendPath(key)
                    .build();
        }

        public static Uri buildUpcSearchUri(String key) {
            return UPCSEARCH_URI.buildUpon()
                    .appendPath(key)
                    .build();
        }

        public static long getProductRowIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }

        public static String getProductSearchStringFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getUpcSearchStringFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

    }

}
