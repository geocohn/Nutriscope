package com.creationgroundmedia.nutriscope;

// todo: copyright all files
// todo: comment all files
// todo: implement paid and free flavors

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.creationgroundmedia.nutriscope.data.NutriscopeContract;
import com.creationgroundmedia.nutriscope.detail.DetailActivity;
import com.creationgroundmedia.nutriscope.scan.BarcodeCaptureActivity;
import com.creationgroundmedia.nutriscope.scan.camera.CameraSource;
import com.creationgroundmedia.nutriscope.service.SearchService;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int URL_LOADER = 0;

    private static final String[] PROJECTION = new String[] {
            NutriscopeContract.ProductsEntry._ID,
            NutriscopeContract.ProductsEntry.COLUMN_IMAGE,
            NutriscopeContract.ProductsEntry.COLUMN_IMAGETHUMB,
            NutriscopeContract.ProductsEntry.COLUMN_NAME,
            NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID
    };
    // The following must agree with the PROJECTION above
    private static final int ID = 0;
    private static final int IMAGE = 1;
    private static final int THUMB = 2;
    private static final int NAME = 3;
    private static final int PRODUCTID = 4;

    // The following must correspond with the sorting_modes string array resource.
    private static final String SORT_NAME =  NutriscopeContract.ProductsEntry.COLUMN_NAME + " COLLATE NOCASE ASC";
    private static final String SORT_UPC =  NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID + " ASC";

    private static final String[] sortOrders = {
            SORT_NAME,
            SORT_UPC
    };

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String SELECTED_POSITION = "selectedPosition";

    private Context mContext;
    private ProgressBar mMainProgressBar;
    private TextView mApiStatusView;
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private Loader<Cursor> mCursorLoader;
    private String mBarcodeStatus;
    private String mBarcodeValue;
    private String mSortOrder = SORT_NAME;
    private MenuItem mSearchMenu;
    private MenuItem mSortMenu;
    private MenuItem mScanMenu;
    private Uri mSearchUri = NutriscopeContract.ProductsEntry.PRODUCTSEARCH_URI;
    private int mSelectedPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        Log.d(LOG_TAG, "onCreate saveInstanceState = " + savedInstanceState);
        if (savedInstanceState != null) {
            mSelectedPosition = savedInstanceState.getInt(SELECTED_POSITION);
            Log.d(LOG_TAG, "restoring selected position to " + mSelectedPosition);
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.mipmap.ic_launcher);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SearchService.resetApiStatus(mContext);

        mCursorLoader = getSupportLoaderManager().initLoader(URL_LOADER, null, this);

        mMainProgressBar = (ProgressBar) findViewById(R.id.mainProgressBar);
        mApiStatusView = (TextView) findViewById(R.id.api_status);
        mRecyclerView = (RecyclerView) findViewById(R.id.product_list);
        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mSearchMenu = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // todo: implement stickiness in query string
                Log.d(LOG_TAG, "onQueryTextSubmit(" + query + ")");
                SearchService.setApiStatus(mContext, SearchService.API_STATUS_SEARCHING);
                updateScreenStatus();
                mSelectedPosition = 0;
                if (looksLikeUpc(query)) {
                    setupQueryUri(NutriscopeContract.ProductsEntry.UPCSEARCH_URI);
                    SearchService.startActionUpcSearch(mContext, query);
                } else {
                    setupQueryUri(NutriscopeContract.ProductsEntry.PRODUCTSEARCH_URI);
                    SearchService.startActionProductSearch(mContext, query);
                }
                /**
                 * calling clearFocus() not only dismisses the keyboard,
                 * if prevents onQueryTextSubmit() from being called twice for a single query
                 */
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mScanMenu = menu.findItem(R.id.menu_scan)
                .setVisible(
                        CameraSource.getIdForRequestedCamera(CameraSource.CAMERA_FACING_BACK)
                                != -1);


        mSortMenu = menu.findItem(R.id.action_sorting_spinner);
        Spinner sortingSpinner = (Spinner) MenuItemCompat.getActionView(mSortMenu);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.sorting_modes, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        sortingSpinner.setAdapter(spinnerAdapter);
        sortingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSortOrder = sortOrders[position];
                mCursorLoader = getSupportLoaderManager().restartLoader(URL_LOADER, null, MainActivity.this);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                mSortMenu.collapseActionView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        return true;
    }

    private void setupQueryUri(Uri uri) {
        if (mSearchUri != uri) {
            mSearchUri = uri;
            getSupportLoaderManager().restartLoader(URL_LOADER, null, this);
        }
    }

    private boolean looksLikeUpc(String query) {
        if (query.length() < 4 || query.length() > 15) {
            return false;
        } else {
            for (char c : query.toCharArray()) {
                if (c != 'x' && c != 'X' && (c < '0' || c > '9')) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        /**
         * Make sure menu actions don't stack up in the action bar
         */
        if (item == mScanMenu) {
            mSearchMenu.collapseActionView();
            mSortMenu.collapseActionView();
        } else if (item == mSearchMenu) {
            mScanMenu.collapseActionView();
            mSortMenu.collapseActionView();
        } else if (item == mSortMenu) {
            mScanMenu.collapseActionView();
            mSearchMenu.collapseActionView();
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void onScanClicked(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_scan) {
            // launch barcode activity.

            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, getAutoFocus());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, getUseFlash());
// todo: find out why the first scan crashes
            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
    }

    private boolean getUseFlash() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sp.getBoolean(mContext.getString(R.string.pref_useflash), true);
    }

    private boolean getAutoFocus() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sp.getBoolean(mContext.getString(R.string.pref_autofocus), true);
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    mBarcodeStatus = getString(R.string.barcode_success);
                    mBarcodeValue = barcode.displayValue;
                    mSearchMenu.expandActionView();
                    mSearchView.setIconified(false);
                    mSearchView.setQuery(mBarcodeValue, false);
                    Log.d(LOG_TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    mBarcodeStatus = getString(R.string.barcode_failure);
                    Log.d(LOG_TAG, "No barcode captured, intent data is null");
                }
            } else {
                mBarcodeStatus = String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new AutofitGridLayoutManager(this, 1000));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new SimpleProductCursorRecyclerViewAdapter(this, null));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_POSITION, mSelectedPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case URL_LOADER:
                return new CursorLoader(
                        this,                                   // context
                        mSearchUri,  // Table to query
                        PROJECTION,                             // Projection to return
                        null,
                        null,                                   // No selection arguments
                        mSortOrder
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        updateScreenStatus();
        SimpleProductCursorRecyclerViewAdapter adapter =
                (SimpleProductCursorRecyclerViewAdapter) mRecyclerView.getAdapter();
        adapter.changeCursor(data);
        Log.d(LOG_TAG, "onLoadFinished, smooth scroll to " + mSelectedPosition);
        mRecyclerView.smoothScrollToPosition(mSelectedPosition);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((SimpleProductCursorRecyclerViewAdapter)(mRecyclerView.getAdapter())).changeCursor(null);
    }

    public class AutofitGridLayoutManager extends GridLayoutManager {
        /**
         * the number of columns in the grid depends on how much width we've got to play with
         */
        private int mItemWidth;

        AutofitGridLayoutManager(Context context, int itemWidth) {
            super (context, 1);
            mItemWidth = itemWidth;
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            int spanCount = getWidth() / mItemWidth;
            if (spanCount < 1) {
                spanCount = 1;
            }
            setSpanCount(spanCount);
            super.onLayoutChildren(recycler, state);
        }
    }


    public class SimpleProductCursorRecyclerViewAdapter
            extends CursorRecyclerViewAdapter<SimpleProductCursorRecyclerViewAdapter.ViewHolder> {

        private Context mContext;
        private ViewHolder mSelectedHolder = null;

        SimpleProductCursorRecyclerViewAdapter(Context context, Cursor productCursor) {
            super(context, productCursor);
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return super.getItemCount();
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {
            final String productUpc = cursor.getString(PRODUCTID);
            final String productName = cursor.getString(NAME);
            final String productImage = cursor.getString(IMAGE);
            String thumb = cursor.getString(THUMB);
            viewHolder.mProductId.setText(productUpc);
            viewHolder.mName.setText(productName);
            viewHolder.posterProgressView.setVisibility(View.VISIBLE);
            if (thumb != null) {
                Picasso.with(mContext)
                        .load(thumb)
                        .into(viewHolder.posterImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                viewHolder.posterProgressView.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                viewHolder.posterImageView.setImageResource(R.drawable.ic_open_food_facts_logo);
                                viewHolder.posterProgressView.setVisibility(View.GONE);
                            }
                        });
            } else {
                viewHolder.posterImageView.setImageResource(R.drawable.ic_open_food_facts_logo);
                viewHolder.posterProgressView.setVisibility(View.GONE);
            }

            final long rowId = cursor.getLong(ID);

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(LOG_TAG, "Clicked item " + rowId);
                    mSelectedPosition = viewHolder.getLayoutPosition();
                    Log.d(LOG_TAG, "mSelectedPosition set to " + mSelectedPosition);
                    DetailActivity.launchInstance(
                            view.getContext(), rowId, productName, productUpc, productImage);
                }
            });
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View mView;
            TextView mName;
            TextView mProductId;
            ImageView posterImageView;
            ProgressBar posterProgressView;

            ViewHolder(View view) {
                super(view);
                mView = view;

                posterImageView = (ImageView) view.findViewById(R.id.posterView);
                posterImageView.setLayoutParams(new FrameLayout.LayoutParams(200, 200));
                posterImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                posterImageView.setAdjustViewBounds(true);
                posterImageView.setPadding(4, 4, 4, 4);
                posterProgressView = (ProgressBar) view.findViewById(R.id.posterProgressBar);

                mName = (TextView) view.findViewById(R.id.name);
                mProductId = (TextView) view.findViewById(R.id.product_id);
            }
        }
    }

    private void updateScreenStatus() {
        @SearchService.ApiStatus int apiStatus = SearchService.getApiStatus(mContext);
        mMainProgressBar.setVisibility(apiStatus == SearchService.API_STATUS_SEARCHING? View.VISIBLE : View.INVISIBLE);
        mApiStatusView.setText(SearchService.getApiStatusString(mContext, apiStatus));
    }
}
