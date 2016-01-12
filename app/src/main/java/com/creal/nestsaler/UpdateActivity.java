package com.creal.nestsaler;

import android.app.Activity;
import android.os.Bundle;

import com.creal.nestsaler.views.HeaderView;

public class UpdateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.center_soft_update);
        headerView.setTitleLeft();

    }
}
