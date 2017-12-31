package com.secure.whatsapp;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseTwitterUtils;
import com.parse.PushService;
import com.parse.SaveCallback;

public class RegisterServer extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
      ParseObject.registerSubclass(FeedBackObject.class);
      ParseObject.registerSubclass(SellCarObject.class);
    Parse.initialize(this);
      PushService.setDefaultPushCallback(this, MainActivity.class);
        ParsePush.subscribeInBackground("", new SaveCallback() {
          @Override
          public void done(ParseException e) {
              if (e == null) {
                  Log.e("com.parse.push", "successfully subscribed to the broadcast channel.");
              } else {
                  Log.e("com.parse.push", "failed to subscribe for push", e);
              }
          }
      });Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
    ParseFacebookUtils.initialize(this);
    ParseTwitterUtils.initialize(getString(R.string.twitter_consumer_key),
        getString(R.string.twitter_consumer_secret));
  }
}
