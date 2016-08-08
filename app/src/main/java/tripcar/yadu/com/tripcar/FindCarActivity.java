package tripcar.yadu.com.tripcar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import tripcar.yadu.com.tripcar.adapter.FindCarAdapter;
import tripcar.yadu.com.tripcar.constant.CommonStrings;
import tripcar.yadu.com.tripcar.handler.ServiceHandler;
import tripcar.yadu.com.tripcar.models.FindCarData;

public class FindCarActivity extends AppCompatActivity {

    //FindCarData findCarData;

    RecyclerView carsView;

    FindCarAdapter findCarAdapter;

    ProgressDialog pDialog;

    String access_tocken,departure_city,arrival_city;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_car);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("No Matching Rides");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(CommonStrings.MYPREFERENCE, Context.MODE_PRIVATE);

        access_tocken = sharedPreferences.getString(CommonStrings.USER_ACCESS_TOCKEN, "");

        carsView = (RecyclerView) findViewById(R.id.find_car_recycle);

        Intent in=getIntent();
        departure_city=in.getStringExtra(CommonStrings.KEY_DEPARTURE_CITY);
        arrival_city=in.getStringExtra(CommonStrings.KEY_ARRIVAL_CITY);




       // new GetContacts().execute();


      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, FindCarData> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            // Progress dialog
            pDialog = new ProgressDialog(FindCarActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Loading...");
            // showDialog();
            pDialog.show();

        }

        @Override
        protected FindCarData doInBackground(Void... arg0) {

            FindCarData obj=null;

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            // String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("access_token", access_tocken));
            params.add(new BasicNameValuePair("departure_city", departure_city));
            params.add(new BasicNameValuePair("arrival_city", arrival_city));

            String jsonStr = sh.makeServiceCall(CommonStrings.URL+CommonStrings.MOBILE_REGISTER_BASE,ServiceHandler.POST,params);

            android.util.Log.d("Response: ", "> " + jsonStr);

            //loading from local json file

            jsonStr = loadJSONFromAsset();

            try {

                JSONObject jObj = new JSONObject(jsonStr);
                String status = jObj.getString("status");
                String message = jObj.getString("message");


                if (status.equals("103") && message.equals("Not An Authenticated User!")) {
                    // Snackbar.make(spinmake,"Not an authencated user",Snackbar.LENGTH_SHORT).show();
                }else{

                    Gson gson =new GsonBuilder().create();

                    obj = gson.fromJson(jsonStr, FindCarData.class);

                    if(obj.getStatus().equals("105")){

                        return obj;
                    }


                 /*if(status.equals("105")){
                    JSONArray jsonArray = jObj.getJSONArray("data");

                    for (int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        Gson gson =new GsonBuilder().create();

                        FindCarData obj  = gson.fromJson(jsonStr, FindCarData.class);
                    }

                } else {

                    result = "Failure";
                }*/
                }

            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
            }

            return obj;
        }

        @Override
        protected void onPostExecute(FindCarData result) {
            super.onPostExecute(result);

            hideDialog();

           // if(result.getStatus().equals("105")){

                if(result.getData().size()>0){

                    findCarAdapter = new FindCarAdapter(FindCarActivity.this,result.getData());

                    carsView.setAdapter(findCarAdapter);

                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

                    carsView.setLayoutManager(layoutManager);

                }
                else {

                    Snackbar.make(carsView,"No Cars Found on this route",Snackbar.LENGTH_SHORT).show();

                }

          /*  }
            else{
                Snackbar.make(carsView,"No Cars Found on this route",Snackbar.LENGTH_SHORT).show();
            }*/


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


    /*//Check Internet Connectivity
    public boolean checkNetIsAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }*/

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("findcar.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==android.R.id.home){

            // NavUtils.navigateUpFromSameTask(this);
            finish();

            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

    }

}
