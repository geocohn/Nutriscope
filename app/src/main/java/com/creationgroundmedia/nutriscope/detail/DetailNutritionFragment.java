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

package com.creationgroundmedia.nutriscope.detail;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.creationgroundmedia.nutriscope.R;
import com.creationgroundmedia.nutriscope.data.NutriscopeContract;

/**
 * Created by George Cohn III on 6/27/16.
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailNutritionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailNutritionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // the fragment initialization parameters, e.g. ARG_ROWID
    private static final String ARG_ROWID = NutriscopeContract.ProductsEntry._ID;
    private static final String ARG_NAME = NutriscopeContract.ProductsEntry.COLUMN_NAME;
    private static final String ARG_UPC = NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID;
    private static final int URL_LOADER = 3;
    private static final String[] PROJECTION = new String[]{
            NutriscopeContract.ProductsEntry._ID,
            NutriscopeContract.ProductsEntry.COLUMN_FATLEVEL,
            NutriscopeContract.ProductsEntry.COLUMN_SATURATEDFATSLEVEL,
            NutriscopeContract.ProductsEntry.COLUMN_SUGARSLEVEL,
            NutriscopeContract.ProductsEntry.COLUMN_SALTLEVEL,
            NutriscopeContract.ProductsEntry.COLUMN_ENERGY,
            NutriscopeContract.ProductsEntry.COLUMN_FAT,
            NutriscopeContract.ProductsEntry.COLUMN_SATURATEDFATS,
            NutriscopeContract.ProductsEntry.COLUMN_CARBOHYDRATES,
            NutriscopeContract.ProductsEntry.COLUMN_SUGARS,
            NutriscopeContract.ProductsEntry.COLUMN_PROTEINS,
            NutriscopeContract.ProductsEntry.COLUMN_SALT,
            NutriscopeContract.ProductsEntry.COLUMN_SODIUM,
            NutriscopeContract.ProductsEntry.COLUMN_FIBER
    };
    // The following must agree with the PROJECTION above
    private static final int ID = 0;
    private static final int FATLEVEL = 1;
    private static final int SATURATEDFATSLEVEL = 2;
    private static final int SUGARSLEVEL = 3;
    private static final int SALTLEVEL = 4;
    private static final int ENERGY = 5;
    private static final int FAT = 6;
    private static final int SATURATEDFATS = 7;
    private static final int CARBOHYDRATES = 8;
    private static final int SUGARS = 9;
    private static final int PROTEINS = 10;
    private static final int SALT = 11;
    private static final int SODIUM = 12;
    private static final int FIBER = 13;

    private static final String LOG_TAG = DetailNutritionFragment.class.getSimpleName();

    private long mRowId;

    private String mName;
    private String mUpc;
    private Context mContext;
    private View mView;
    private Loader<Cursor> mCursorLoader;

    public DetailNutritionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param rowId The Id of the content provider entry
     * @param name The product name
     * @param upc The product UPC
     * @return A new instance of fragment DetailNutritionFragment.
     */
    public static DetailNutritionFragment newInstance(
            long rowId, String name, String upc) {
        DetailNutritionFragment fragment = new DetailNutritionFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ROWID, rowId);
        args.putString(ARG_NAME, name);
        args.putString(ARG_UPC, upc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRowId = getArguments().getLong(ARG_ROWID);
            mName = getArguments().getString(ARG_NAME);
            mUpc = getArguments().getString(ARG_UPC);
        }
        mCursorLoader = getActivity().getSupportLoaderManager().initLoader(URL_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_detail_nutrition, container, false);

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case URL_LOADER:
                return new CursorLoader(
                        mContext,                                   // context
                        NutriscopeContract.ProductsEntry.buildProductRowIdUri(mRowId),  // Table to query
                        PROJECTION,                             // Projection to return
                        null,
                        null,                                   // No selection arguments
                        null                                    // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        Log.d(LOG_TAG, "onLoadFinished(" + loader + ", " + data + ")");
        if (data == null) {
            return;
        }
        data.moveToFirst();
        ImageView fatLevelIndicator = (ImageView) mView.findViewById(R.id.fatIndicator);
        TextView fatLevelText = (TextView) mView.findViewById(R.id.fatLevel);
        ImageView satFatLevelIndicator = (ImageView) mView.findViewById(R.id.satFatIndicator);
        TextView satFatLevelText = (TextView) mView.findViewById(R.id.satFatLevel);
        ImageView sugarLevelIndicator = (ImageView) mView.findViewById(R.id.sugarsIndicator);
        TextView sugarLevelText = (TextView) mView.findViewById(R.id.sugarsLevel);
        ImageView saltLevelIndicator = (ImageView) mView.findViewById(R.id.saltIndicator);
        TextView saltLevelText = (TextView) mView.findViewById(R.id.saltLevel);

        TextView energyJoules = (TextView) mView.findViewById(R.id.energyJoules);
        TextView fat = (TextView) mView.findViewById(R.id.totalFat);
        TextView satFat = (TextView) mView.findViewById(R.id.satFat);
        TextView carb = (TextView) mView.findViewById(R.id.carb);
        TextView sugars = (TextView) mView.findViewById(R.id.sugars);
        TextView proteins = (TextView) mView.findViewById(R.id.proteins);
        TextView salt = (TextView) mView.findViewById(R.id.salt);
        TextView sodium = (TextView) mView.findViewById(R.id.sodium);
        TextView fiber = (TextView) mView.findViewById(R.id.fiber);

        fatLevelIndicator.setColorFilter(getColorForLevel(data.getString(FATLEVEL)),
                PorterDuff.Mode.SRC_ATOP);
        fatLevelText.setText(getTextForLevel(data.getString(FAT),
                getString(R.string.level_fat),
                data.getString(FATLEVEL)));

        satFatLevelIndicator.setColorFilter(getColorForLevel(data.getString(SATURATEDFATSLEVEL)),
                PorterDuff.Mode.SRC_ATOP);
        satFatLevelText.setText(getTextForLevel(data.getString(SATURATEDFATS),
                getString(R.string.level_saturated_fat),
                data.getString(SATURATEDFATSLEVEL)));

        sugarLevelIndicator.setColorFilter(getColorForLevel(data.getString(SUGARSLEVEL)),
                PorterDuff.Mode.SRC_ATOP);
        sugarLevelText.setText(getTextForLevel(data.getString(SUGARS),
                getString(R.string.level_sugars),
                data.getString(SUGARSLEVEL)));

        saltLevelIndicator.setColorFilter(getColorForLevel(data.getString(SALTLEVEL)),
                PorterDuff.Mode.SRC_ATOP);
        saltLevelText.setText(getTextForLevel(data.getString(SALT),
                "Salt",
                data.getString(SALTLEVEL)));

        energyJoules.setText(data.getString(ENERGY));
        fat.setText(data.getString(FAT));
        satFat.setText(data.getString(SATURATEDFATS));
        carb.setText(data.getString(CARBOHYDRATES));
        sugars.setText(data.getString(SUGARS));
        proteins.setText(data.getString(PROTEINS));
        salt.setText(data.getString(SALT));
        sodium.setText(data.getString(SODIUM));
        fiber.setText(data.getString(FIBER));
    }

    private CharSequence getTextForLevel(String quantity, String nutrient, String level) {
        if (quantity == null || level == null)
            return String.format(getString(R.string.no_data_found_for), nutrient);
        return String.format(getString(R.string.quantity_nutrient_in_level_qualtity),
                quantity, nutrient, level);
    }

    private int getColorForLevel(String level) {
        if (level != null) {
            if (level.compareToIgnoreCase("low") == 0)
                return ContextCompat.getColor(mContext, R.color.colorLowLevel);
            if (level.compareToIgnoreCase("moderate") == 0)
                return ContextCompat.getColor(mContext, R.color.colorModerateLevel);
            if (level.compareToIgnoreCase("high") == 0)
                return ContextCompat.getColor(mContext, R.color.colorHighLevel);
        }
        return Color.WHITE;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Nothing to be done here
    }
}
