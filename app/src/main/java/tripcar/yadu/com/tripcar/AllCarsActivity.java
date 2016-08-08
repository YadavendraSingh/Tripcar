package tripcar.yadu.com.tripcar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tripcar.yadu.com.tripcar.adapter.UserCarAdapter;
import tripcar.yadu.com.tripcar.constant.CommonStrings;
import tripcar.yadu.com.tripcar.database.TripcarDatabase;
import tripcar.yadu.com.tripcar.gettersetter.UserCarGetterSetter;
import tripcar.yadu.com.tripcar.handler.ServiceHandler;

public class AllCarsActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    SharedPreferences sharedPreferences;

    String access_tocken;

    String id,make,model,comfort,seats,colour,car_type,number,user,updated_at,created_at;

    ArrayList<UserCarGetterSetter> userCarList = new ArrayList<>();

    RecyclerView car_recycleview;

    UserCarAdapter userCarAdapter;

    RelativeLayout no_car_layout;

    Button btn_add_car;

    TripcarDatabase db;

    ArrayList<UserCarGetterSetter> userCarGetterSetters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cars);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ytrip_icongreens);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new TripcarDatabase(getApplicationContext());

        car_recycleview = (RecyclerView) findViewById(R.id.user_car_recycle);

        no_car_layout = (RelativeLayout) findViewById(R.id.no_car_layout);

        btn_add_car = (Button) findViewById(R.id.btn_add_car);

        btn_add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),AddCarActivity.class);
                startActivity(in);

                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });

        sharedPreferences = getSharedPreferences(CommonStrings.MYPREFERENCE, Context.MODE_PRIVATE);
        access_tocken = sharedPreferences.getString(CommonStrings.USER_ACCESS_TOCKEN, "");

      //  getData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(),AddCarActivity.class);
                startActivity(in);

                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

              /*  Snackbar.make(view, "New car Will Be Added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        db.open();
        userCarGetterSetters = db.getAllCars();

        if(userCarGetterSetters.size()>0){


            userCarAdapter=new UserCarAdapter(getApplicationContext(),userCarGetterSetters);

            car_recycleview.setAdapter(userCarAdapter);

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

            car_recycleview.setLayoutManager(layoutManager);

            no_car_layout.setVisibility(View.INVISIBLE);

        }
        else{

            no_car_layout.setVisibility(View.VISIBLE);

        }

       // new GetContacts().execute();

    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, ArrayList<UserCarGetterSetter>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            // Progress dialog
            pDialog = new ProgressDialog(AllCarsActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Loading...");
            showDialog();

        }

        @Override
        protected ArrayList<UserCarGetterSetter> doInBackground(Void... arg0) {

            //data.clear();


            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            // String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("access_token",  access_tocken));

            String jsonStr = sh.makeServiceCall(CommonStrings.URL+CommonStrings.GET_ALL_CAR,ServiceHandler.POST,params);

            Log.d("Response: ", "> " + jsonStr);
            System.out.println("Server Respone---> "+jsonStr);

           // UserGetterSetter userGetterSetter= new UserGetterSetter();

            ArrayList<UserCarGetterSetter> carlist = new ArrayList<>();

            try {

                JSONObject jObj = new JSONObject(jsonStr);
                String status = jObj.getString("status");
                String message = jObj.getString("message");


                if (status.equals("103") && message.equals("Not An Authenticated User!")) {
                   // Snackbar.make(spinmake,"Not an authencated user",Snackbar.LENGTH_SHORT).show();
                }else if(status.equals("105")){

                    //setContentView(R.layout.temp_image_layout);

                    JSONArray jsonArray = jObj.getJSONArray("data");

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject= (JSONObject) jsonArray.get(i);

                        UserCarGetterSetter userCarGetterSetter = new UserCarGetterSetter();

                        userCarGetterSetter.setId(jsonObject.getString("_id"));
                        userCarGetterSetter.setMake(jsonObject.getString("make"));
                        userCarGetterSetter.setModel(jsonObject.getString("model"));
                        userCarGetterSetter.setComfort(jsonObject.getString("comfort"));
                        userCarGetterSetter.setColourc(jsonObject.getString("colour"));
                        userCarGetterSetter.setSeats(jsonObject.getString("seats"));
                        userCarGetterSetter.setCar_type(jsonObject.getString("car_type"));
                        userCarGetterSetter.setNumberp(jsonObject.getString("number"));
                        userCarGetterSetter.setUser(jsonObject.getString("user"));
                        userCarGetterSetter.setUpdated_at(jsonObject.getString("updated_at"));
                        userCarGetterSetter.setCreated_at(jsonObject.getString("created_at"));

                        carlist.add(userCarGetterSetter);

                    }

                   // Snackbar.make(spinmake,"Carr Added Successfully",Snackbar.LENGTH_SHORT).show();


                } else {
                   /* Toast.makeText(getActivity(),
                            "Category Data Not Found", Toast.LENGTH_LONG).show();*/
                }

            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
            }

            return carlist;
        }

        @Override
        protected void onPostExecute(ArrayList<UserCarGetterSetter> result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            hideDialog();

            if(result.size()>0){


                userCarAdapter=new UserCarAdapter(getApplicationContext(),result);

                car_recycleview.setAdapter(userCarAdapter);

                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

                car_recycleview.setLayoutManager(layoutManager);


              /*  editor.putString(CommonStrings.USER_ACCESS_TOCKEN, result.getUser_access_tocken());
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
                }*/
            }
            else{

                no_car_layout.setVisibility(View.VISIBLE);

            }


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


    public void getData(){

        ArrayList<UserCarGetterSetter> carList = new ArrayList<>();

        String make[] = {"Audi","BMW","Mercedes"};
        String model[] = {"Q7","X3","C220"};
        String comfort[] = {"Luxury","Comfort","Luxury"};
        String color[] = {"Black","White","Red"};
        String seat[] = {"4","3","4"};
        String cartype[] = {"SUV","SUV","Sedan"};
        String numberp[] = {"Dl GC 2368","Dl SK 0007","HR D 4584"};

        for (int i=0;i<3;i++){


            UserCarGetterSetter userCarGetterSetter = new UserCarGetterSetter();

           // userCarGetterSetter.setId(jsonObject.getString("_id"));
            userCarGetterSetter.setMake(make[i]);
            userCarGetterSetter.setModel(model[i]);
            userCarGetterSetter.setComfort(comfort[i]);
            userCarGetterSetter.setColourc(color[i]);
            userCarGetterSetter.setSeats(seat[i]);
            userCarGetterSetter.setCar_type(cartype[i]);
            userCarGetterSetter.setNumberp(numberp[i]);
           // userCarGetterSetter.setUser(jsonObject.getString("user"));
           // userCarGetterSetter.setUpdated_at(jsonObject.getString("updated_at"));
           // userCarGetterSetter.setCreated_at(jsonObject.getString("created_at"));

            carList.add(userCarGetterSetter);

        }

        userCarAdapter=new UserCarAdapter(getApplicationContext(),carList);

        car_recycleview.setAdapter(userCarAdapter);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        car_recycleview.setLayoutManager(layoutManager);

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
