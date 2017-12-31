package com.secure.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by neokree on 16/12/14.
 */

public class galleryFragmentText extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final String drawablename=getArguments().getString("extra");
        int id = getResources().getIdentifier(drawablename, "drawable", "com.secure.whatsapp");
        View v = null;
        v = inflater.inflate(R.layout.imageview, container, false);
        LinearLayout home = (LinearLayout) v.findViewById(R.id.imageviewlayoutid);
        home.setBackgroundResource(id);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getActivity(),ShowCar.class);
                intent.putExtra("selection",drawablename);
                startActivity(intent);
            }
        });
        return v;
    }
}
