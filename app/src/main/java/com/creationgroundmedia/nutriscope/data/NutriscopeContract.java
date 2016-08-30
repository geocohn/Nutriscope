package com.creationgroundmedia.nutriscope.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by geo on 7/4/16.
 */

public class NutriscopeContract {

    // Authority
    public static final String CONTENT_AUTHORITY = "com.creationgroundmedia.nutriscope";
    //Base URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //Paths
    public static final String PATH_PRODUCTS = "products";
    public static final String PATH_PRODUCTSEARCH = "productsearch";
    public static final String PATH_UPCSEARCH = "upcsearch";
    public static final String PATH_UPC = "upc";
    public static final String PATH_ROWID = "rowid";

    /* Inner class that defines the table contents of the products table */
    public static final class ProductsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRODUCTS).build();
        public static final Uri PRODUCTSEARCH_URI =
                CONTENT_URI.buildUpon().appendPath(PATH_PRODUCTSEARCH).build();
        public static final Uri UPCSEARCH_URI =
                CONTENT_URI.buildUpon().appendPath(PATH_UPCSEARCH).build();
        public static final Uri UPC_URI =
                CONTENT_URI.buildUpon().appendPath(PATH_UPC).build();
        public static final Uri ROWID_URI =
                CONTENT_URI.buildUpon().appendPath(PATH_ROWID).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public static final String TABLE_NAME = "products";
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

        public static Uri buildUpcUri(String key) {
            return UPC_URI.buildUpon()
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
