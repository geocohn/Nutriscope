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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.creationgroundmedia.nutriscope.data.NutriscopeContract.ProductsEntry;

/**
 * Created by geo on 7/4/16.
 */

public class NutriscopeDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "nutriscope.db";

    public NutriscopeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + ProductsEntry.TABLE_NAME + " (" +
                ProductsEntry._ID + " INTEGER PRIMARY KEY," +
                ProductsEntry.COLUMN_ADDITIVES + " TEXT, " +
                ProductsEntry.COLUMN_ALLERGENS + " TEXT, " +
                ProductsEntry.COLUMN_BRANDS + " TEXT, " +
                ProductsEntry.COLUMN_CARBOHYDRATES + " TEXT, " +
                ProductsEntry.COLUMN_CATEGORIES + " TEXT, " +
                ProductsEntry.COLUMN_CITY + " TEXT, " +
                ProductsEntry.COLUMN_IMAGE + " TEXT, " + // imageUrl
                ProductsEntry.COLUMN_IMAGESMALL + " TEXT, " + // imageSmallUrl
                ProductsEntry.COLUMN_IMAGETHUMB + " TEXT, " + // imageThumbUrl
                ProductsEntry.COLUMN_INGREDIENTS + " TEXT, " +
                ProductsEntry.COLUMN_INGREDIENTSIMAGE + " TEXT, " + // imageIngredientsUrl
                ProductsEntry.COLUMN_LABELS + " TEXT, " +
                ProductsEntry.COLUMN_NAME + " TEXT, " +
                ProductsEntry.COLUMN_PACKAGING + " TEXT, " +
                ProductsEntry.COLUMN_PRODUCTID + " TEXT, " +
                ProductsEntry.COLUMN_QUANTITY + " TEXT, " +
                ProductsEntry.COLUMN_ENERGY + " TEXT, " +
                ProductsEntry.COLUMN_FAT + " TEXT, " +
                ProductsEntry.COLUMN_FATLEVEL + " TEXT, " +
                ProductsEntry.COLUMN_FIBER + " TEXT, " +
                ProductsEntry.COLUMN_PROTEINS + " TEXT, " +
                ProductsEntry.COLUMN_SALT + " TEXT, " +
                ProductsEntry.COLUMN_SALTLEVEL + " TEXT, " +
                ProductsEntry.COLUMN_SATURATEDFATS + " TEXT, " +
                ProductsEntry.COLUMN_SATURATEDFATSLEVEL + " TEXT, " +
                ProductsEntry.COLUMN_SODIUM + " TEXT, " +
                ProductsEntry.COLUMN_STORES + " TEXT, " +
                ProductsEntry.COLUMN_SUGARS + " TEXT, " +
                ProductsEntry.COLUMN_SUGARSLEVEL + " TEXT, " +
                ProductsEntry.COLUMN_TRACES + " TEXT );";

        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProductsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}