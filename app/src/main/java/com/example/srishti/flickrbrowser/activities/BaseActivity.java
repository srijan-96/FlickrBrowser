package com.example.srishti.flickrbrowser.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.srishti.flickrbrowser.R;

/**
 * Created by Srishti on 20-12-2017.
 */

public class BaseActivity extends AppCompatActivity {

    public static final String FLICKR_QUERY = "FLICKR QUERY";
    public static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";
    private Toolbar mToolbar;

    protected Toolbar activateToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }

    protected Toolbar activateToolbarWithHomeEnabled() {
        activateToolbar();
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return mToolbar;

    }

}

