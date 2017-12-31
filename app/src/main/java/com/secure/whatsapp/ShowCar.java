package com.secure.whatsapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Avi on 7/30/2015.
 */
public class ShowCar extends Activity{
    String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showcar);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            filename = extras.getString("selection");
        }
        ((Button)findViewById(R.id.calltestDrive)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Callservice();
            }
        });

     }
    public void Callservice()
    {
        final Dialog custominfo = new Dialog(this);
        custominfo.setContentView(R.layout.custominfo);
        ((Button)custominfo.findViewById(R.id.savecustomoinfo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject saveinfo = new ParseObject("TestDrives");
                saveinfo.put("Name",((EditText)custominfo.findViewById(R.id.CustonName)).getText().toString());
                saveinfo.put("Contact",((EditText)custominfo.findViewById(R.id.CustonContact)).getText().toString());
                saveinfo.put("Address",((EditText)custominfo.findViewById(R.id.Custonaddress)).getText().toString());
                saveinfo.saveEventually();
                custominfo.dismiss();
            }
        });
        custominfo.show();
    }
}
