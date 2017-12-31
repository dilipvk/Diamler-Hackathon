package com.secure.whatsapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Objects;

/**
 * Created by Avi on 7/30/2015.
 */
public class EarnCredits extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earncredits);
        ((Button)findViewById(R.id.OkRefferalCode)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(MainActivity.PreferencesName, Context.MODE_PRIVATE);
                String codealreadygiven = prefs.getString(MainActivity.USERCREDITS+"code", "");
                if(codealreadygiven.equals("yes"))
                {
                    Toast.makeText(getApplicationContext(),"Already Credited",Toast.LENGTH_LONG).show();
                    return;
                }
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("objectId", ((EditText)findViewById(R.id.Refferalcode)).getText().toString());
                query.countInBackground(new CountCallback(){
                    @Override
                    public void done(int count, ParseException e) {
                        // TODO Auto-generated method stub
                        if (e == null) {
                            if(count==0)
                                Toast.makeText(getApplicationContext(),"Wrong Refferal Code",Toast.LENGTH_LONG).show();
                            else {
                                Toast.makeText(getApplicationContext(), "1000 CREDITED", Toast.LENGTH_LONG).show();
                                SharedPreferences prefs = getSharedPreferences(MainActivity.PreferencesName, Context.MODE_PRIVATE);
                                String credits = prefs.getString(MainActivity.USERCREDITS, "0");
                                credits = String.valueOf(Integer.parseInt(credits) + 1000);
                                SharedPreferences.Editor edit = getSharedPreferences(MainActivity.PreferencesName, Context.MODE_PRIVATE).edit();
                                edit.putString(MainActivity.USERCREDITS + "code", "yes");
                                edit.putString(MainActivity.USERCREDITS, credits);
                                edit.commit();
                            }
                        }
                    }

                });
            }
        });
    }
}
