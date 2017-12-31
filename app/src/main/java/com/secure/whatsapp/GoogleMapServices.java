package com.secure.whatsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.util.List;

public class GoogleMapServices extends Activity implements GoogleMap.OnMarkerClickListener {

    List<ParseObject>marker;
    // Google Map
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemap);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ServicesLocation");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> markers, ParseException e) {
                if (e == null) {
                    marker=markers;
                    DisplayMap(markers);
                } else {
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void DisplayMap(List<ParseObject> markers) {
        try {
            initilizeMap();

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setRotateGesturesEnabled(true);
            googleMap.getUiSettings().setZoomGesturesEnabled(true);

            int count=0;
            for (ParseObject i:markers) {
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(Double.valueOf(i.getString("latitude")), Double.valueOf(i.getString("longitiude"))))
                        .title(String.valueOf(count));
                count++;
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                googleMap.addMarker(marker);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        googleMap.setOnMarkerClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void Callservice(final int pos)
    {
        final Dialog custominfo = new Dialog(this);
        custominfo.setContentView(R.layout.custominfo);
        ((Button)custominfo.findViewById(R.id.savecustomoinfo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject saveinfo = new ParseObject("CallServices");
                saveinfo.put("Destination",marker.get(pos).getString("Name"));
                saveinfo.put("Name",((EditText)custominfo.findViewById(R.id.CustonName)).getText().toString());
                saveinfo.put("Contact",((EditText)custominfo.findViewById(R.id.CustonContact)).getText().toString());
                saveinfo.put("Address",((EditText)custominfo.findViewById(R.id.Custonaddress)).getText().toString());
                saveinfo.saveEventually();
                custominfo.dismiss();
            }
        });
        custominfo.show();
    }

    @Override
    public boolean onMarkerClick(Marker m) {
        final Dialog markerdialog=new Dialog(this);
        markerdialog.setContentView(R.layout.markerdialog);
        final int pos=Integer.valueOf(m.getTitle());
        markerdialog.setTitle(marker.get(pos).getString("Name"));
        ((TextView)markerdialog.findViewById(R.id.address)).setText("Contact:" + marker.get(pos).getString("contact") + "\nAddress:" + marker.get(pos).getString("Address"));
        ((TextView)markerdialog.findViewById(R.id.position)).setText(marker.get(pos).getString("latitude")+" "+marker.get(pos).getString("longitiude"));
        ((Button)markerdialog.findViewById(R.id.callservice)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerdialog.dismiss();
                Callservice(pos);
            }
        });
        markerdialog.show();
        return true;
    }
}
