package tripcar.yadu.com.tripcar;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tripcar.yadu.com.tripcar.constant.CommonStrings;
import tripcar.yadu.com.tripcar.gettersetter.ProfileGetterSetter;
import tripcar.yadu.com.tripcar.gettersetter.UserGetterSetter;
import tripcar.yadu.com.tripcar.handler.ServiceHandler;
import tripcar.yadu.com.tripcar.smsverification.SmsVerificationActivity;

public class LoginNewActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    LinearLayout lv_as_fblogin_btn;
    LoginButton loginButton;

    String email,name;

    ProgressDialog pDialog;

    SharedPreferences sharedPreferences;

    boolean isLoggedIn=false;

    AccessToken accessToken;

    private CallbackManager callbackManager;
  /*  private TextView textView;
    ProfilePictureView profilePictureView;
*/
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            displayMessage(profile);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };


    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    double latitude = 0.0;
    double longitude = 0.0;

    SharedPreferences.Editor editor;

    String gcm__registration_tocken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(CommonStrings.MYPREFERENCE, Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

        isLoggedIn = sharedPreferences.getBoolean(CommonStrings.IS_LOGGED_IN, false);

        gcm__registration_tocken = sharedPreferences.getString(CommonStrings.GCM_TOCKEN,"");

        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        displayLocation();

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

                accessToken=newToken;
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        setContentView(R.layout.activity_login_new);

        lv_as_fblogin_btn = (LinearLayout) findViewById(R.id.lv_as_fblogin_btn);

        loginButton = (LoginButton) findViewById(R.id.login_button);


        lv_as_fblogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
        /*textView = (TextView) findViewById(R.id.textView);

        profilePictureView = (ProfilePictureView) findViewById(R.id.profilepic);
*/

        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        loginButton.registerCallback(callbackManager, callback);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        profileTracker.startTracking();

    }

    private void displayMessage(final Profile profile){
        if(profile != null){

          final  ProfileGetterSetter profileGetterSetter = new ProfileGetterSetter();

            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object,GraphResponse response) {

                    JSONObject json = response.getJSONObject();
                    try {
                        if(json != null) {
                            String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
                            name = json.getString("name");
                            email = json.getString("email");

                            profileGetterSetter.setFb_email(email);

                            }

                        profileGetterSetter.setFb_id(profile.getId());
                        //   profileGetterSetter.setFb_email(email);
                        profileGetterSetter.setFirst_name(profile.getFirstName());
                        profileGetterSetter.setLast_name(profile.getLastName());
                        //check if permission needed for age
                        profileGetterSetter.setAge("26");
                        profileGetterSetter.setDevice_token(gcm__registration_tocken);
                        profileGetterSetter.setDevice_type("1");
                        profileGetterSetter.setFb_access_token(AccessToken.getCurrentAccessToken().getToken());
                        profileGetterSetter.setFb_friends("123");
                        profileGetterSetter.setApp_version("101");
                        profileGetterSetter.setLatitude(latitude + "");
                        profileGetterSetter.setLongitude(longitude + "");


                       /* if(!isLoggedIn){
                            new GetContacts().execute(profileGetterSetter);
                        }
                        else{

                            if(sharedPreferences.getString(CommonStrings.PHONE_NUMBER,"").equals("")){
                                Intent in = new Intent(LoginNewActivity.this, SmsVerificationActivity.class);
                                startActivity(in);
                                finish();
                            }
                            else{
                                Intent in = new Intent(LoginNewActivity.this, MainActivity.class);
                                startActivity(in);
                                finish();
                            }
                        }*/

                        Intent in = new Intent(LoginNewActivity.this, MainActivity.class);
                        startActivity(in);
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        }
                    }
                });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,email,picture,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();


            /*textView.setText(profile.getName()+profile.getProfilePictureUri(50, 50));
            profilePictureView.setProfileId(profile.getId());*/

            editor = sharedPreferences.edit();

            editor.putString(CommonStrings.PROFILE_URL, profile.getId());
            editor.putString(CommonStrings.PROFILE_NAME, profile.getName());

            editor.putBoolean(CommonStrings.IS_LOGGED_IN, true);
            editor.commit();


        }
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
       Profile profile = Profile.getCurrentProfile();
      //  displayMessage(profile);

        checkPlayServices();
    }

    @Override
    public void onConnected(Bundle bundle) {
// Once connected with google api, get the location
        displayLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.i("Tripcar", "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

    }


    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<ProfileGetterSetter, Void, UserGetterSetter> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            // Progress dialog
            pDialog = new ProgressDialog(LoginNewActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Loading...");
           showDialog();

        }

        @Override
        protected UserGetterSetter doInBackground(ProfileGetterSetter... arg0) {

            //data.clear();

            ProfileGetterSetter profileGetterSetter = arg0[0];

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            // String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("tag", "fb_login"));
            params.add(new BasicNameValuePair("fb_id", profileGetterSetter.getFb_id()));
            params.add(new BasicNameValuePair("fb_access_token", profileGetterSetter.getFb_access_token()));
            params.add(new BasicNameValuePair("age", profileGetterSetter.getAge()));
            params.add(new BasicNameValuePair("fb_friends", profileGetterSetter.getFb_friends()));
            params.add(new BasicNameValuePair("first_name", profileGetterSetter.getFirst_name()));
            params.add(new BasicNameValuePair("last_name", profileGetterSetter.getLast_name()));
            params.add(new BasicNameValuePair("fb_email", profileGetterSetter.getFb_email()));
            params.add(new BasicNameValuePair("device_type", profileGetterSetter.getDevice_type()));
            params.add(new BasicNameValuePair("device_token", profileGetterSetter.getDevice_token()));
            params.add(new BasicNameValuePair("app_version", profileGetterSetter.getApp_version()));
            params.add(new BasicNameValuePair("latitude", profileGetterSetter.getLatitude()));
            params.add(new BasicNameValuePair("longitude", profileGetterSetter.getLongitude()));


            String jsonStr = sh.makeServiceCall(CommonStrings.URL+CommonStrings.FB_LOGIN_BASE,ServiceHandler.POST,params);

            Log.d("Response: ", "> " + jsonStr);
            System.out.println("Server Respone---> "+jsonStr);

            UserGetterSetter userGetterSetter= new UserGetterSetter();

            try {

                JSONObject jObj = new JSONObject(jsonStr);
                String status = jObj.getString("status");
                String message = jObj.getString("message");


                if (status.equals("103") && message.equals("")) {

                }else if(status.equals("105")){

                    //setContentView(R.layout.temp_image_layout);

                    JSONObject jsonObject = jObj.getJSONObject("data");

                    userGetterSetter.setFirst_name(jsonObject.getString("first_name"));
                    userGetterSetter.setLast_name(jsonObject.getString("last_name"));
                    userGetterSetter.setUser_access_tocken(jsonObject.getString("access_token"));
                    userGetterSetter.setPhone_number(jsonObject.getString("phone"));
                    userGetterSetter.setNew_user(jsonObject.getInt("new"));


                } else {
                   /* Toast.makeText(getActivity(),
                            "Category Data Not Found", Toast.LENGTH_LONG).show();*/
                }

            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
            }

            return userGetterSetter;
        }

        @Override
        protected void onPostExecute(UserGetterSetter result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            if(result!=null){

                editor.putString(CommonStrings.USER_ACCESS_TOCKEN, result.getUser_access_tocken());
                editor.putBoolean(CommonStrings.IS_LOGGED_IN, true);
                editor.commit();


                if(result.getPhone_number()==null ){
                    Intent in = new Intent(LoginNewActivity.this, SmsVerificationActivity.class);
                    startActivity(in);
                    finish();
                }
                else{

                    Intent in = new Intent(LoginNewActivity.this, MainActivity.class);
                    startActivity(in);
                    finish();
                }
            }
            hideDialog();

        }

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    /**
     * Method to display the location on UI
     * */
    private void displayLocation() {

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

          //  lblLocation.setText(latitude + ", " + longitude);

        } else {

           //lblLocation.setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


}
