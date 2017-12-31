package com.secure.whatsapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Avi on 7/30/2015.
 */
public class EmiCalculator extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emilayout);
        ((Button)findViewById(R.id.calculate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double loanAmount = Double.parseDouble(((EditText) findViewById(R.id.principal)).getText().toString());
                double rateOfInterest = Integer.parseInt(((EditText) findViewById(R.id.Intresetrate)).getText().toString());
                double numberOfMonths = Integer.valueOf(((EditText) findViewById(R.id.loanterm)).getText().toString());
                double finalValue = calcEmi(loanAmount,rateOfInterest,numberOfMonths);
                ((TextView)findViewById(R.id.emianswer)).setText(String.valueOf(finalValue));
                SharedPreferences.Editor edit = getSharedPreferences(MainActivity.PreferencesName, Context.MODE_PRIVATE).edit();
                edit.putString(MainActivity.EMI , String.valueOf(Math.round( finalValue)));
                edit.commit();
            }
        });
        ((Button)findViewById(R.id.reset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(R.id.principal)).setText("");
                ((EditText)findViewById(R.id.Intresetrate)).setText("");
                ((EditText) findViewById(R.id.loanterm)).setText("");
                ((TextView)findViewById(R.id.emianswer)).setText("");
            }
        });

    }
    public Double calcEmi(double p, double r, double n) {
        double R = (r / 12) / 100;
        double e = (p * R * (Math.pow((1 + R), n)) / ((Math.pow((1 + R), n)) - 1));
        return e;
    }
}
