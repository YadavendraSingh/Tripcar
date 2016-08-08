package tripcar.yadu.com.tripcar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.ProfilePictureView;

import mehdi.sakout.fancybuttons.FancyButton;
import tripcar.yadu.com.tripcar.constant.CommonStrings;
import tripcar.yadu.com.tripcar.fragment.FindRideFragment;
import tripcar.yadu.com.tripcar.fragment.OfferRideFragment;
import tripcar.yadu.com.tripcar.smsverification.SmsVerificationActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener   {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    static TextView tvstart,tvdestination,tvdate;

   public static String TAG="Autocomplete";

    boolean flag_destination_selected=false;

    FancyButton btnfind,btnoffer;

    ProfilePictureView profilePictureView;
    TextView tvprofile_name,tvprogress;
    ProgressBar progressBar;

    SharedPreferences sharedPreferences;

    private int progressStatus = 0,profile_completeness=50;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());

       // profilePictureView = (ProfilePictureView) findViewById(R.id.profile_pic);

        btnfind = (FancyButton) findViewById(R.id.btn_find);
        btnoffer = (FancyButton) findViewById(R.id.btn_offer);

        btnfind.setOnClickListener(this);
        btnoffer.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Ride");

        toolbar.setLogo(R.drawable.ytrip_icongreens);

        setSupportActionBar(toolbar);

        FindRideFragment findRideFragment = new FindRideFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.offer_or_find_frame,findRideFragment).commit();

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                progressStatus = 0;

                boolean isVerified = sharedPreferences.getBoolean(CommonStrings.PHONE_NUMBER_VERIFIED, false);

                if(isVerified){
                    profile_completeness=70;
                }
                // Start the lengthy operation in a background thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(progressStatus < profile_completeness){
                            // Update the progress status
                            progressStatus +=1;

                            // Try to sleep the thread for 20 milliseconds
                            try{
                                Thread.sleep(20);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }

                            // Update the progress bar
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(progressStatus);
                                    // Show the progress on TextView
                                    tvprogress.setText("Profile Completeness "+progressStatus +"%");
                                    // If task execution completed
                                   /* if(progressStatus == 50){
                                        // Set a message of completion
                                        //tvprogress.setText("Operation completed.");
                                    }*/
                                }
                            });
                        }
                    }
                }).start(); // Start the operation
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                progressBar.setProgress(0);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        LinearLayout headerView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        profilePictureView = (ProfilePictureView) headerView.findViewById(R.id.profile_pic);
        tvprofile_name = (TextView) headerView.findViewById(R.id.tvprofile_name);
        progressBar = (ProgressBar) headerView.findViewById(R.id.progress_complete);
        tvprogress = (TextView) headerView.findViewById(R.id.tvprogress);

        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#FFEB3B"), PorterDuff.Mode.SRC_IN);


        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences = getSharedPreferences(CommonStrings.MYPREFERENCE, Context.MODE_PRIVATE);
        String profile_link = sharedPreferences.getString(CommonStrings.PROFILE_URL,"");
        String name = sharedPreferences.getString(CommonStrings.PROFILE_NAME, "");

       // Profile profile = Profile.getCurrentProfile();
        profilePictureView.setProfileId(profile_link);
        tvprofile_name.setText(name);

        navigationView.addHeaderView(headerView);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(in);

                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });

        }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_findride) {

            btnoffer.setBackgroundColor(getResources().getColor(R.color.grey));
            btnfind.setBackgroundColor(getResources().getColor(R.color.primary_color));

            FindRideFragment findRideFragment = new FindRideFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.offer_or_find_frame,findRideFragment).commit();


        } else if (id == R.id.nav_offerride) {

            btnfind.setBackgroundColor(getResources().getColor(R.color.grey));
            btnoffer.setBackgroundColor(getResources().getColor(R.color.primary_color));


            OfferRideFragment offerRideFragment = new OfferRideFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.offer_or_find_frame,offerRideFragment).commit();


        } else if (id == R.id.nav_login) {

            Intent in = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(in);

            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        } else if (id == R.id.nav_verify) {

            boolean isVerified = sharedPreferences.getBoolean(CommonStrings.PHONE_NUMBER_VERIFIED, false);

            if(!isVerified){
                Intent in = new Intent(getApplicationContext(),SmsVerificationActivity.class);
                startActivity(in);

                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

            }
            else{
                Snackbar.make(btnfind,"Phone number is already verified",Snackbar.LENGTH_SHORT).show();

            }


        } /*else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){

            case R.id.btn_find:

                btnoffer.setBackgroundColor(getResources().getColor(R.color.grey));
                btnfind.setBackgroundColor(getResources().getColor(R.color.primary_color));

                FindRideFragment findRideFragment = new FindRideFragment();

                getSupportFragmentManager().beginTransaction().replace(R.id.offer_or_find_frame,findRideFragment).commit();


                break;


            case R.id.btn_offer:

                btnfind.setBackgroundColor(getResources().getColor(R.color.grey));
                btnoffer.setBackgroundColor(getResources().getColor(R.color.primary_color));


                OfferRideFragment offerRideFragment = new OfferRideFragment();

                getSupportFragmentManager().beginTransaction().replace(R.id.offer_or_find_frame,offerRideFragment).commit();


                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}

