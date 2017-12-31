package com.secure.whatsapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.parse.ParseFile;
/**
 * Created by Avi on 7/30/2015.
 */
public class SellCar extends Activity {

    private SellCarObject meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                meal = new SellCarObject();
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                super.onCreate(savedInstanceState);

                setContentView(R.layout.activity_new_sell_car);
                FragmentManager manager = getFragmentManager();
                Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

                if (fragment == null) {
                    fragment = new NewMealFragment();
                    manager.beginTransaction().add(R.id.fragmentContainer, fragment)
                            .commit();
                }
            }
            public SellCarObject getCurrentMeal() {
                return meal;
            }

        }
