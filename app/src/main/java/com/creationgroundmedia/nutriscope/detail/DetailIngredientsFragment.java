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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.creationgroundmedia.nutriscope.R;
import com.creationgroundmedia.nutriscope.data.NutriscopeContract;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by George Cohn III on 6/27/16.
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailIngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailIngredientsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // the fragment initialization parameters, e.g. ARG_ROWID
    private static final String ARG_ROWID = NutriscopeContract.ProductsEntry._ID;
    private static final String ARG_NAME = NutriscopeContract.ProductsEntry.COLUMN_NAME;
    private static final String ARG_UPC = NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID;
    private static final int URL_LOADER = 2;
    private static final String[] PROJECTION = new String[]{
            NutriscopeContract.ProductsEntry._ID,
            NutriscopeContract.ProductsEntry.COLUMN_ADDITIVES,
            NutriscopeContract.ProductsEntry.COLUMN_ALLERGENS,
            NutriscopeContract.ProductsEntry.COLUMN_INGREDIENTS,
            NutriscopeContract.ProductsEntry.COLUMN_INGREDIENTSIMAGE,
            NutriscopeContract.ProductsEntry.COLUMN_TRACES
    };
    // The following must agree with the PROJECTION above
    private static final int ID = 0;
    private static final int ADDITIVES = 1;
    private static final int ALLERGENS = 2;
    private static final int INGREDIENTS = 3;
    private static final int INGREDIENTSIMAGE = 4;
    private static final int TRACES = 5;

    private static final String LOG_TAG = DetailIngredientsFragment.class.getSimpleName();

    private long mRowId;

    private String mName;
    private String mUpc;
    private Context mContext;
    private View mView;
    private Loader<Cursor> mCursorLoader;

    public DetailIngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param rowId The Id of the content provider entry
     * @param name The product name
     * @param upc The product UPC
     * @return A new instance of fragment DetailIngredientsFragment.
     */
    public static DetailIngredientsFragment newInstance(
            long rowId, String name, String upc) {
        DetailIngredientsFragment fragment = new DetailIngredientsFragment();
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
        mView = inflater.inflate(R.layout.fragment_detail_ingredients, container, false);

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
        final ImageView labelPhoto = (ImageView) mView.findViewById(R.id.labelPhoto);
        TextView ingredientsView = (TextView) mView.findViewById(R.id.ingredients);
        TextView allergensView = (TextView) mView.findViewById(R.id.possibleAllergens);
        TextView tracesView = (TextView) mView.findViewById(R.id.traces);
        TextView additivesView = (TextView) mView.findViewById(R.id.additives);
        final ProgressBar labelPhotoProgressBar = (ProgressBar) mView.findViewById(R.id.labelPhotoProgressBar);

        labelPhoto.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        labelPhoto.setAdjustViewBounds(true);
        labelPhoto.setPadding(4, 4, 4, 4);
        labelPhotoProgressBar.setVisibility(View.VISIBLE);
        String photoUrl = data.getString(INGREDIENTSIMAGE);
        if (photoUrl != null) {
            Picasso.with(mContext)
                    .load(data.getString(INGREDIENTSIMAGE))
                    .into(labelPhoto, new Callback() {
                        @Override
                        public void onSuccess() {
                            labelPhotoProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            labelPhoto.setImageResource(R.drawable.ic_open_food_facts_logo);
                            labelPhotoProgressBar.setVisibility(View.GONE);
                        }
                    });
        } else {
            labelPhotoProgressBar.setVisibility(View.GONE);
        }

        ingredientsView.setText(delineateAllergens(data.getString(INGREDIENTS)),
                TextView.BufferType.SPANNABLE);
        allergensView.setText(data.getString(ALLERGENS));
        additivesView.setText(data.getString(ADDITIVES));
        tracesView.setText(data.getString(TRACES));
    }

    private SpannableString delineateAllergens(String ingredients) {
        /**
         * @param ingredients a string of comma separated ingredients,
         *                    with possible allergen ingredients delimited by '_'
         * @return            a SpannableString with the delimiters removed and the
         *                    passible allergen ingredients highlighted by  the primary dark color
         */

         /** look for substrings delimited by '_' and paint them the theme's primary dark,
         * removing the delimiters
         */
        if (ingredients == null)
            return SpannableString.valueOf("");

        SpannableStringBuilder builder = new SpannableStringBuilder();

        for (int startIndex = 0, endIndex = 0;
             endIndex >= 0 && startIndex < ingredients.length();
             startIndex++) {
            /**
             * find the start of the next delimited substring,
             * and deliver everything up to it as normal text
             */
            endIndex = ingredients.indexOf('_', startIndex);
            if (startIndex < endIndex) {
                String s = ingredients.substring(startIndex, endIndex);
                builder.append(s);
            }
            startIndex = endIndex + 1;

            if (endIndex >= 0 && startIndex < ingredients.length()) {
                /**
                 * throw a color span on the delimited text and deliver it
                 */
                endIndex = ingredients.indexOf('_', startIndex);
                if (startIndex < endIndex) {
                    SpannableString ss = new SpannableString(
                            ingredients.substring(startIndex, endIndex));
                    ss.setSpan(new ForegroundColorSpan(
                                    ContextCompat.getColor(mContext, R.color.colorPrimaryDark)),
                            0, ss.length() - 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.append(ss);
                }
                startIndex = endIndex;
            }
        }

        return SpannableString.valueOf(builder);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
/* Nothing to be done here*/
    }
}
