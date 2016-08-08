package tripcar.yadu.com.tripcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;


public class LoginActivity extends ActionBarActivity {

   // int gifarray[]={R.drawable.cityview,R.drawable.cargirl,R.drawable.restorent};
   // GifImageView gifImageView;

    RelativeLayout parent;

    static int count=0;

    private CallbackManager callbackManager;

    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();



        setContentView(R.layout.login_layout);

        parent = (RelativeLayout) findViewById(R.id.rel);

        loginButton = (LoginButton) findViewById(R.id.facebook_login_btn);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                boolean enableButtons = AccessToken.getCurrentAccessToken() != null;


                Profile profile = Profile.getCurrentProfile();
                if (enableButtons && profile != null) {
                    //profilePictureView.setProfileId(profile.getId());
                    Snackbar.make(parent, "User ID: "
                            + loginResult.getAccessToken().getUserId()
                            + "\n" +
                            "Auth Token: "
                            + loginResult.getAccessToken().getToken()+" "+profile.getFirstName(), Snackbar.LENGTH_LONG).show();


                   // greeting.setText(getString(R.string.hello_user, profile.getFirstName()));
                } else {
                   /* profilePictureView.setProfileId(null);
                    greeting.setText(null);
*/                }

            }

            @Override
            public void onCancel() {

                Snackbar.make(parent, "User Cancelled Login", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Snackbar.make(parent, "Error in Login happened", Snackbar.LENGTH_SHORT).show();
            }
        });
       /* gifImageView = (GifImageView) findViewById(R.id.gifview);

        gifImageView.setBackgroundResource(R.drawable.cityview);
*/


     /*   new Handler().postDelayed(new Runnable() {
            public void run() {

                if(count<gifarray.length){

                    gifImageView.setBackgroundResource(gifarray[count]);
                    count++;  //<<< increment counter here
                }
                else{
                    // reset counter here
                    count=0;
                }

            }
        }, 1000);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
