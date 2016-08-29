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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.creationgroundmedia.nutriscope.R;
import com.creationgroundmedia.nutriscope.data.NutriscopeContract;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
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
    // TODO: Rename and change types and number of parameters
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
        Log.d(LOG_TAG, "onLoadFinished(" + loader + ", " + data + ")");
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
        Picasso.with(mContext)
                .load(data.getString(INGREDIENTSIMAGE))
                .into(labelPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        labelPhotoProgressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError() {
                        labelPhoto.setImageResource(R.mipmap.ic_poster_placeholder);
                        labelPhotoProgressBar.setVisibility(View.GONE);
                    }
                });

        ingredientsView.setText(data.getString(INGREDIENTS));
        allergensView.setText(data.getString(ALLERGENS));
        additivesView.setText(data.getString(ADDITIVES));
        tracesView.setText(data.getString(TRACES));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
/* Nothing to be done here*/
    }
}
