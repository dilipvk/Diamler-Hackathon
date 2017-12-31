package com.secure.whatsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Avi on 7/30/2015.
 */
public class Description extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descriptionlayout);
        String filename = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            filename = extras.getString("selection");
        }
        int id = getResources().getIdentifier(filename, "drawable", "com.secure.whatsapp");
        ((ImageView)findViewById(R.id.imageView1)).setBackgroundResource(id);

    }
}
