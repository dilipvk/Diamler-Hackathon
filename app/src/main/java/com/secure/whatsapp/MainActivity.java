package com.secure.whatsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String USERCREDITS = "CREDITS";
    public static final String EMI="EMI";
    boolean PasswordSet ;
    Dialog AlternatenumberDialog ;
    boolean forgotpassword;
    ImageView PasswordDisplay[];
    ImageButton CombinationButton[];
    ImageButton ClearCombination ;
    static String PreferencesName = "daimlerLock";
    static String PreferencesCombination = PreferencesName+"Combination";
    static String AlternateNumber = "daimleralternatenumber";
    TextView CombinationTitle;
    int InputCount = 0;
    Integer UserColorInput[];
    Integer AppDataCombination[];
    private EditText Alternateet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            String mData = mBundle.getString("com.parse.Data");
            if(mData!=null)
                if(mData.contains("alert")) {
                mData = mData.substring(mData.indexOf("alert") + 8);
                open(mData.substring(0, mData.indexOf("\"")));
            }
        }
        ParseAnalytics.trackAppOpened(getIntent());
        IntializeCredits();
        StartAppLock();
     }

    private void IntializeCredits() {
        SharedPreferences prefs = getSharedPreferences(PreferencesName, Context.MODE_PRIVATE);
        String credits= prefs.getString(USERCREDITS,"");
        if(credits.equals(""))
        {
            SharedPreferences.Editor edit = getSharedPreferences(PreferencesName, Context.MODE_PRIVATE).edit();
            edit.putString(USERCREDITS, "0");
            edit.commit();
        }
        }

    public void open(String Message){
        if(Message.contains("CREDITED:"))
        {
            SharedPreferences prefs = getSharedPreferences(PreferencesName, Context.MODE_PRIVATE);
            String credits= prefs.getString(USERCREDITS,"0");
            credits = String.valueOf(Integer.parseInt(credits) + Integer.parseInt(Message.substring(Message.indexOf("CREDITED:") + 9)));
            SharedPreferences.Editor edit = getSharedPreferences(PreferencesName, Context.MODE_PRIVATE).edit();
            edit.putString(USERCREDITS, credits);
            edit.commit();
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(Message);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void StartAppLock() {
        setListeners();
        setRandomColors();
        getAppCombination();
        SetTitle();
    }

    private void SetTitle() {
        if(PasswordSet == true)
            CombinationTitle.setText("Combination Required");
        else
            CombinationTitle.setText("Set Combination");
    }

    private void setListeners() {
        ClearCombination = ((ImageButton)findViewById(R.id.clearCombination));
        CombinationTitle = (TextView)findViewById(R.id.CombinationTitle);
        UserColorInput = new Integer[4];
        AppDataCombination = new Integer[4];
        CombinationButton = new ImageButton[9];
        CombinationButton[0]=((ImageButton)findViewById(R.id.imgae1));
        CombinationButton[1]=((ImageButton)findViewById(R.id.imgae2));
        CombinationButton[2]=((ImageButton)findViewById(R.id.imgae3));
        CombinationButton[3]=((ImageButton)findViewById(R.id.imgae4));
        CombinationButton[4]=((ImageButton)findViewById(R.id.imgae5));
        CombinationButton[5]=((ImageButton)findViewById(R.id.imgae6));
        CombinationButton[6]=((ImageButton)findViewById(R.id.imgae7));
        CombinationButton[7]=((ImageButton)findViewById(R.id.imgae8));
        CombinationButton[8]=((ImageButton)findViewById(R.id.imgae9));
        PasswordDisplay = new ImageView[4];
        PasswordDisplay[0] = (ImageView) findViewById(R.id.password1);
        PasswordDisplay[1] = (ImageView) findViewById(R.id.password2);
        PasswordDisplay[2] = (ImageView) findViewById(R.id.password3);
        PasswordDisplay[3] = (ImageView) findViewById(R.id.password4);
        ((TextView)findViewById(R.id.forgotpassword)).setOnClickListener(this);
        SetCirclBackground();
        ClearCombination.setOnClickListener(this);
        ClearUserData();
    }

    private void SetCirclBackground() {
        for(int i = 0; i < 9; i++)
        {
            Drawable roundDrawable = getResources().getDrawable(R.drawable.circleview);
            CombinationButton[i].setBackground(roundDrawable);
        }
        for(int i = 0; i < 4; i++)
        {
            Drawable roundDrawable = getResources().getDrawable(R.drawable.circleview);
            PasswordDisplay[i].setBackground(roundDrawable);
        }
        Drawable roundDrawable = getResources().getDrawable(R.drawable.circleview);
        ClearCombination.setBackground(roundDrawable);
        ClearCombination.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
    }

    private void getAppCombination() {
        SharedPreferences prefs = getSharedPreferences(PreferencesName, Context.MODE_PRIVATE);
        String AppCombinationtemp = prefs.getString(PreferencesCombination,"");
        if(AppCombinationtemp.equals(""))
        {
            PasswordSet = false;
        }
        else
        {
            PasswordSet = true;
            String AppCombinationtemparray[] = AppCombinationtemp.split(",");
            for(int i=0; i < 4; i++)
                AppDataCombination[i] = Integer.parseInt(AppCombinationtemparray[i]);
        }
        Log.e("Password is set ", String.valueOf(PasswordSet));
    }

    private void setRandomColors() {
        Integer []colors=getRandomColors();
        for(int i=0; i<9; i++)
        {
            CombinationButton[i].getBackground().setColorFilter(colors[i], PorterDuff.Mode.SRC_ATOP);
            CombinationButton[i].setOnClickListener(this);
            CombinationButton[i].setTag(colors[i]);
        }
    }

    private Integer[] getRandomColors() {
        Integer colors[]=new Integer[9];
        colors[0] = getResources().getColor(R.color.pink);
        colors[1] = getResources().getColor(R.color.blue);
        colors[2] = getResources().getColor(R.color.orange);
        colors[3] = getResources().getColor(R.color.green);
        colors[4] = getResources().getColor(R.color.cyan);
        colors[5] = getResources().getColor(R.color.yellow);
        colors[6] = getResources().getColor(R.color.lavender);
        colors[7] = getResources().getColor(R.color.brown);
        colors[8] = getResources().getColor(R.color.purple);
        Collections.shuffle(Arrays.asList(colors));
        return colors;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imgae1:
            case R.id.imgae2:
            case R.id.imgae3:
            case R.id.imgae4:
            case R.id.imgae5:
            case R.id.imgae6:
            case R.id.imgae7:
            case R.id.imgae8:
            case R.id.imgae9:
                InputCount++;
                UserColorInput[InputCount - 1] = (int) ((ImageButton) v).getTag();
                PasswordDisplay[InputCount - 1].getBackground().setColorFilter(UserColorInput[InputCount - 1], PorterDuff.Mode.SRC_ATOP);
                if (InputCount == 4) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            VerifyCombination();
                        }
                    }, 100);
                }
                break;
            case R.id.clearCombination:
                ClearUserData();
                break;
            case R.id.Okalternate:
                SharedPreferences.Editor edit = getSharedPreferences(PreferencesName, Context.MODE_PRIVATE).edit();
                if(forgotpassword == true)
                {
                    SharedPreferences prefs = getSharedPreferences(PreferencesName, Context.MODE_PRIVATE);
                    String AppCombinationtemp = prefs.getString(AlternateNumber,"");
                    if (Alternateet.getText().toString().equals(AppCombinationtemp)) {
                        Toast.makeText(this,"Password reseted ",Toast.LENGTH_LONG).show();
                        AlternatenumberDialog.cancel();
                        edit.putString(PreferencesCombination , "");
                        edit.commit();
                        StartAppLock();
                        return;
                    }
                    else
                        Toast.makeText(this,"Password not reseted ",Toast.LENGTH_LONG).show();
                }
                else {
                    if (Alternateet.getText().toString().length() != 10) {
                        ((TextView) AlternatenumberDialog.findViewById(R.id.DialogError)).setText("Invalid Number");
                        return;
                    }
                    edit.putString(AlternateNumber, Alternateet.getText().toString());
                    edit.commit();
                    AlternatenumberDialog.cancel();
                    goNextActivity();
                }
                break;
            case R.id.forgotpassword:
                forgotpassword = true;
                AlternatenumberDialog = new Dialog(this);
                AlternatenumberDialog.setContentView(R.layout.alternatenumber);
                AlternatenumberDialog.setTitle("Answer it:");
                ((Button)AlternatenumberDialog.findViewById(R.id.Okalternate)).setOnClickListener(this);
                Alternateet = (EditText)AlternatenumberDialog.findViewById(R.id.alternatenumberet);
                AlternatenumberDialog.show();
                return;
        }
    }

    private void VerifyCombination() {
        if(PasswordSet == false)
        {
            saveCombination();
        }
        else
        {
            if(CheckCombination() == true )
                goNextActivity();
            else 
                {
                setRandomColors();
                CombinationTitle.setText("Combination Incorrect");
                ClearUserData();
                }
        }
    }

    private void ClearUserData() {
        InputCount = 0;
        for(ImageView i:PasswordDisplay)
            i.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
    }

    private void goNextActivity() {
        Intent myIntent = new Intent(MainActivity.this, LoginServer.class);
        startActivity(myIntent);
    }

    private boolean CheckCombination() {
        for(int i=0; i<4; i++)
            if(String.valueOf(UserColorInput[i]).equals(String.valueOf(AppDataCombination[i])) == false)
            {
                return false;
            }
        return true;
    }

    private void saveCombination() {
        String temp = new String();
        for(Integer i : UserColorInput)
        {
            temp+= String.valueOf(i);
            if(i!=3)
                temp+=",";
        }
        forgotpassword = false;
        AlternatenumberDialog = new Dialog(this);
        AlternatenumberDialog.setContentView(R.layout.alternatenumber);
        AlternatenumberDialog.setTitle("Security");
        ((Button)AlternatenumberDialog.findViewById(R.id.Okalternate)).setOnClickListener(this);
        Alternateet = (EditText)AlternatenumberDialog.findViewById(R.id.alternatenumberet);
        AlternatenumberDialog.show();
        SharedPreferences.Editor edit = getSharedPreferences(PreferencesName, Context.MODE_PRIVATE).edit();
        edit.putString(PreferencesCombination , temp);
        edit.commit();
    }
}
