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
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.creationgroundmedia.nutriscope.R;
import com.creationgroundmedia.nutriscope.data.NutriscopeContract;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by George Cohn III on 6/27/16.
 * Product details are displayed in 3 sections:
 * 1. DESCRIPTION
 * 2. INGREDIENTS
 * 3. NUTRITION
 * Each section is displayed using a fragment.
 * For wide screens, all 3 are displayed on a single screen,
 * otherwise they show in a tabbed ViewPager
 */

public class DetailActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory.
     */

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private long mRowId;
    private String mProductName;
    private String mProductUpc;
    private String mImage;
    private ImageView mActionBarBackground;
    private Target mBgImage;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private FloatingActionButton mfAB;
    private boolean mUnifiedView;

    public static void launchInstance(Context context,
                                      long rowId,
                                      String name,
                                      String upc,
                                      String image) {
        context.startActivity(instanceIntent(context, rowId, name, upc, image));
    }

    public static Intent instanceIntent(Context context,
                                        long rowId,
                                        String name,
                                        String upc,
                                        String image) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(NutriscopeContract.ProductsEntry._ID, rowId);
        bundle.putString(NutriscopeContract.ProductsEntry.COLUMN_NAME, name);
        bundle.putString(NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID, upc);
        bundle.putString(NutriscopeContract.ProductsEntry.COLUMN_IMAGE, image);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mActionBarBackground = (ImageView) findViewById(R.id.toolbar_background);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        mRowId = extras.getLong(NutriscopeContract.ProductsEntry._ID);
        mProductName = extras.getString(NutriscopeContract.ProductsEntry.COLUMN_NAME);
        mProductUpc = extras.getString(NutriscopeContract.ProductsEntry.COLUMN_PRODUCTID);
        mImage = extras.getString(NutriscopeContract.ProductsEntry.COLUMN_IMAGE);

//        Log.d(LOG_TAG, "onCreate(" + mRowId + ", " + mProductName + ", " + mProductUpc + ", " + mImage + ")");
        actionBar.setTitle(mProductName);
        actionBar.setSubtitle(mProductUpc);

        // Display the product image in the action bar
        mBgImage = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                setupActionBarBackground(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(this).load(mImage).into(mBgImage);

        mUnifiedView = findViewById(R.id.unified_detail) != null;

        if (mUnifiedView) {
//            Log.d(LOG_TAG, "Unified frame");
            // Wide screens can accommodate all 3 fragments
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_description_container,
                            DetailDescriptionFragment.newInstance(mRowId, mProductName, mProductUpc))
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_ingredients_container,
                            DetailIngredientsFragment.newInstance(mRowId, mProductName, mProductUpc))
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_nutrition_container,
                            DetailNutritionFragment.newInstance(mRowId, mProductName, mProductUpc))
                    .commit();
        } else {
//            Log.d(LOG_TAG, "Tabbed frames");
            // Otherwise we're looking at a tabbed view
            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setOffscreenPageLimit(3);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        }

        mfAB = (FloatingActionButton) findViewById(R.id.fab);
        mfAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(mProductName, mProductUpc);
            }
        });
    }

    private void share(String productName, String productUpc) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://world.openfoodfacts.org/product/" + productUpc);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Open Food Facts: " + productName);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_message)));
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Doing this makes sure the screen back button behavior
        // is identical to the system back button's
        finish();
        return true;
    }

    private void setupActionBarBackground(Bitmap bitmap) {
        // Make a custom palette for the action bar based on the product image
        BitmapDrawable background =
                new BitmapDrawable(getApplicationContext().getResources(), bitmap);
        mActionBarBackground.setImageDrawable(background);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                mCollapsingToolbar.setContentScrimColor(palette.getMutedColor(
                        ContextCompat.getColor(
                                getApplicationContext(),
                                R.color.colorPrimary)));
                mCollapsingToolbar.setStatusBarScrimColor(palette.getDarkMutedColor(
                        ContextCompat.getColor(
                                getApplicationContext(),
                                R.color.colorPrimaryDark)));
                mfAB.setRippleColor(palette.getLightVibrantColor(
                        ContextCompat.getColor(
                                getApplicationContext(),
                                R.color.colorPrimary)));
                mfAB.setBackgroundTintList(ColorStateList.valueOf(palette.getVibrantColor(
                        ContextCompat.getColor(
                                getApplicationContext(),
                                R.color.colorAccent))));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0: {
                    return DetailDescriptionFragment.newInstance(
                            mRowId, mProductName, mProductUpc);
                }
                case 1: {
                    return DetailIngredientsFragment.newInstance(
                            mRowId, mProductName, mProductUpc);
                }
                case 2: {
                    return DetailNutritionFragment.newInstance(
                            mRowId, mProductName, mProductUpc);
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tab_name_description);
                case 1:
                    return getString(R.string.tab_name_ingredients);
                case 2:
                    return getString(R.string.tab_name_nutrition);
            }
            return null;
        }
    }
}
