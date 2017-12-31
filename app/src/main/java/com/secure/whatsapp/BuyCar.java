package com.secure.whatsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Avi on 7/30/2015.
 */
public class BuyCar extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyheading);
        SetListener();
    }

    private void SetListener() {
        ((ImageView)findViewById(R.id.head1)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.head2)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.head3)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.head4)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.head5)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.head6)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.head7)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.head8)).setOnClickListener(this);
        ((ImageView)findViewById(R.id.head9)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, TabActivity.class);
        String SelectedHead="";
        int total=0;
        switch (v.getId())
        {
            case R.id.head1:
                SelectedHead="amg";
                total=5;
                break;
            case R.id.head2:
                SelectedHead="bharatbenz";
                total=2;break;
            case R.id.head3:
                SelectedHead="freightliner";
                total=2;break;
            case R.id.head4:
                SelectedHead="fuso";
                total=3;break;
            case R.id.head5:
                SelectedHead="maybach";
                   total=3;break;
            case R.id.head6:
                SelectedHead="mercedes";
                total=7;break;
            case R.id.head7:
                SelectedHead="mercedestruck";
                total=3;break;
            case R.id.head8:
                SelectedHead="setra";
                total=2;break;
            case R.id.head9:
                SelectedHead="westernstar";
                total=2;break;
        }
        intent.putExtra("total",String.valueOf(total));
        intent.putExtra("selection",SelectedHead);
        startActivity(intent);

    }
}
