package tripcar.yadu.com.tripcar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import tripcar.yadu.com.tripcar.constant.CommonStrings;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;

    SharedPreferences sharedPreferences;

    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/

      /*  ImageView rotate_image =(ImageView) findViewById(R.id.img_splash);
        RotateAnimation rotate = new RotateAnimation(30, 360, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2500);
        rotate_image.startAnimation(rotate);*/

        StartAnimations();


        sharedPreferences  =
                getSharedPreferences(CommonStrings.MYPREFERENCE, Context.MODE_PRIVATE);

        isLoggedIn = sharedPreferences.getBoolean(CommonStrings.IS_LOGGED_IN, false);

        startMainActivity();

      //  mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
       /* mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
              //  mRegistrationProgressBar.setVisibility(ProgressBar.GONE);

                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                  startMainActivity();

                } else {
                    startMainActivity();
                }
            }
        };
*/
        boolean isRegistered = sharedPreferences
                .getBoolean(getString(R.string.is_registered_on_gcm), false);

       /* if(!isRegistered){

            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }

        }
        else{
            new Handler().postDelayed(new Runnable() {

			*//*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 *//*

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity

                    if(isLoggedIn){
                        Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(i);

                        // close this activity
                        finish();
                    }
                    else{
                        Intent i = new Intent(SplashScreenActivity.this, LoginNewActivity.class);
                        startActivity(i);

                        // close this activity
                        finish();
                    }
                }
            }, SPLASH_TIME_OUT);
        }*/



    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.img_splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));*/
    }

    @Override
    protected void onPause() {
       // LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("Tripcar", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    public void startMainActivity(){
        // mInformationTextView.setText(getString(R.string.gcm_send_message));
        // Toast.makeText(getApplicationContext(),"Token retrieved and sent to server! You can now use gcmsender to send downstream messages to this app.",Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if(isLoggedIn){
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);

                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    // close this activity
                    finish();
                }
                else{
                    Intent i = new Intent(SplashScreenActivity.this, LoginNewActivity.class);
                    startActivity(i);

                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                    // close this activity
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }

}
