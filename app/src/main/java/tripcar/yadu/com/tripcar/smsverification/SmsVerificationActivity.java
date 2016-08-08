package tripcar.yadu.com.tripcar.smsverification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tripcar.yadu.com.tripcar.R;
import tripcar.yadu.com.tripcar.fragment.EnterMobileFragment;

public class SmsVerificationActivity extends AppCompatActivity {

   // private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sms_verification);

        EnterMobileFragment enterMobileFragment = new EnterMobileFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container_sms,enterMobileFragment).commit();


        // getSupportActionBar().setHomeButtonEnabled(true);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    /*@Override
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
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==android.R.id.home){

            finish();
            //slide from left to right
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        //slide from left to right
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

    }
}
