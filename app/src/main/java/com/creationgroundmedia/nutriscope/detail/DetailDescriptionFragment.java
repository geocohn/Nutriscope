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
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creationgroundmedia.nutriscope.R;
import com.creationgroundmedia.nutriscope.data.NutriscopeContract;

/**
 * Created by George Cohn III on 6/27/16.
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailDescriptionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // the fragment initialization parameters, e.g. ARG_ROWID
    private static final String ARG_ROWID = NutriscopeContract.ProductsEntry._ID;
    private static final String ARG_NAME = NutriscopeContract.ProductsEntry.COLUMN_NAME;
    private static final String ARG_UPC = NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID;
    private static final int URL_LOADER = 1;
    private static final String[] PROJECTION = new String[]{
            NutriscopeContract.ProductsEntry._ID,
            NutriscopeContract.ProductsEntry.COLUMN_CATEGORIES,
            NutriscopeContract.ProductsEntry.COLUMN_LABELS,
            NutriscopeContract.ProductsEntry.COLUMN_BRANDS,
            NutriscopeContract.ProductsEntry.COLUMN_STORES,
            NutriscopeContract.ProductsEntry.COLUMN_CITY,
            NutriscopeContract.ProductsEntry.COLUMN_QUANTITY,
            NutriscopeContract.ProductsEntry.COLUMN_PACKAGING
    };
    // The following must agree with the PROJECTION above
    private static final int ID = 0;
    private static final int CATEGORIES = 1;
    private static final int LABELS = 2;
    private static final int BRANDS = 3;
    private static final int STORES = 4;
    private static final int CITY = 5;
    private static final int QUANTITY = 6;
    private static final int PACKAGING = 7;
    private static final String LOG_TAG = DetailDescriptionFragment.class.getSimpleName();

    private long mRowId;

    private String mName;
    private String mUpc;
    private Context mContext;
    private View mView;
    private Loader<Cursor> mCursorLoader;

    public DetailDescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param rowId The Id of the content provider entry
     * @param name The product name
     * @param upc The product UPC
     * @return A new instance of fragment DetailDescriptionFragment.
     */
    public static DetailDescriptionFragment newInstance(
            long rowId, String name, String upc) {
        DetailDescriptionFragment fragment = new DetailDescriptionFragment();
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
        Log.d(LOG_TAG, "onCreate(), mRowId: " + mRowId + ", mNanme: " + mName + ", mUpc: " + mUpc);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(LOG_TAG, "onCreateView()");
        mView = inflater.inflate(R.layout.fragment_detail_description, container, false);
        mCursorLoader = getActivity().getSupportLoaderManager().initLoader(URL_LOADER, null, this);
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
        Log.d(LOG_TAG, "onCreateLoader(), id: " + id);
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
        Log.d(LOG_TAG, "onLoadFinished(" + loader + ", " + data + ")");
        if (data == null) {
            return;
        }
        data.moveToFirst();
        TextView categoriesView =  (TextView) mView.findViewById(R.id.categories);
        TextView labelsView = (TextView) mView.findViewById(R.id.labels);
        TextView brandsView = (TextView) mView.findViewById(R.id.brands);
        TextView storesView = (TextView) mView.findViewById(R.id.stores);
        TextView cityView = (TextView) mView.findViewById(R.id.city);
        TextView quantityView = (TextView) mView.findViewById(R.id.quantity);
        TextView packagingView = (TextView) mView.findViewById(R.id.packaging);

        categoriesView.setText(data.getString(CATEGORIES));
        labelsView.setText(data.getString(LABELS));
        brandsView.setText(data.getString(BRANDS));
        storesView.setText(data.getString(STORES));
        cityView.setText(data.getString(CITY));
        quantityView.setText(data.getString(QUANTITY));
        packagingView.setText(data.getString(PACKAGING));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
/* Nothing to be done here*/
    }
}
