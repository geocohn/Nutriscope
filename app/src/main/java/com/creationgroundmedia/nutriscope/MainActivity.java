package com.creationgroundmedia.nutriscope;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import android.widget.TextView;

import com.creationgroundmedia.nutriscope.data.NutriscopeContract;
import com.creationgroundmedia.nutriscope.service.SearchService;

public class MainActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int URL_LOADER = 0;

    private static final String[] PROJECTION = new String[] {
            NutriscopeContract.ProductsEntry._ID,
            NutriscopeContract.ProductsEntry.COLUMN_IMAGETHUMB,
            NutriscopeContract.ProductsEntry.COLUMN_NAME,
            NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID
    };
    // The following must agree with the PROJECTION above
    private static final int ID = 0;
    private static final int THUMB = 1;
    private static final int NAME = 2;
    private static final int PRODUCTID = 3;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    private Context mContext;
    private RecyclerView mRecyclerView;
    private Loader<Cursor> mCursorLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mCursorLoader = getSupportLoaderManager().initLoader(URL_LOADER, null, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.product_list);
        assert mRecyclerView != null;
        setupRecyclerView((RecyclerView) mRecyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(LOG_TAG, "onQueryTextSubmit(" + query + ")");
                SearchService.startActionProductSearch(mContext, query);
                /**
                 * calling clearFocus() not only dismisses the keyboard,
                 * if prevents onQueryTextSubmit() from being called twice for a single query
                 */
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_bump) {
            getSupportLoaderManager().restartLoader(URL_LOADER, null, MainActivity.this);
            Log.d(LOG_TAG, "bump");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new AutofitGridLayoutManager(this, 1000));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new SimpleProductCursorRecyclerViewAdapter(this, null));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case URL_LOADER:
                return new CursorLoader(
                        this,                                   // context
                        NutriscopeContract.ProductsEntry.PRODUCTSEARCH_URI,  // Table to query
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
        SimpleProductCursorRecyclerViewAdapter adapter =
                (SimpleProductCursorRecyclerViewAdapter) mRecyclerView.getAdapter();
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((SimpleProductCursorRecyclerViewAdapter)(mRecyclerView.getAdapter())).changeCursor(null);
    }

    public class AutofitGridLayoutManager extends GridLayoutManager {
        /**
         * the number of columns in the grid depends on how much width we've got to play with
         */
        private int itemWidth;

        public AutofitGridLayoutManager(Context context, int itemWidth) {
            super (context, 1);
            this.itemWidth = itemWidth;
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            int spanCount = getWidth() / itemWidth;
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

        public SimpleProductCursorRecyclerViewAdapter(Context context, Cursor productCursor) {
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
            viewHolder.mProductId.setText(cursor.getString(PRODUCTID));
            viewHolder.mName.setText(cursor.getString(NAME));
            String thumb = cursor.getString(THUMB);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            View mView;
            TextView mName;
            TextView mProductId;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                mName = (TextView) view.findViewById(R.id.name);
                mProductId = (TextView) view.findViewById(R.id.product_id);
            }
        }
    }
}
