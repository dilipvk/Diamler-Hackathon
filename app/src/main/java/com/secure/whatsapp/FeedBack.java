package com.secure.whatsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Avi on 7/30/2015.
 */
public class FeedBack extends Activity {
    FeedBack activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        activity = this;
        ((Button)findViewById(R.id.button_register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = ((EditText) findViewById(R.id.FeedbackName)).getText().toString();
                String Contact = ((EditText) findViewById(R.id.FeedbackContact)).getText().toString();
                String Desccription = ((EditText) findViewById(R.id.FeedbackDescription)).getText().toString();
                String Address = ((EditText) findViewById(R.id.FeedbackEmailAddress)).getText().toString();
                if (Name.equals("") || Contact.equals("") || Desccription.equals("") || Address.equals(""))
                {       Toast.makeText(getApplicationContext(), "Fill Everything", Toast.LENGTH_LONG).show();
                    return;
                }
                FeedBackObject feedBackObject = new FeedBackObject();
                feedBackObject .setName(Name);
                feedBackObject .setContact(Contact);
                feedBackObject .setDescription(Desccription);
                feedBackObject .setAddress(Address);
                feedBackObject.setAuthor(ParseUser.getCurrentUser());
                feedBackObject.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(),"Mail Sent",Toast.LENGTH_LONG).show();
                            activity.setResult(Activity.RESULT_OK);
                            activity.finish();
                        } else {
                            Toast.makeText(
                                    getApplicationContext().getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
    }
}
