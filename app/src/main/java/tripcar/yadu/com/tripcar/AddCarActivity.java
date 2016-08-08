package tripcar.yadu.com.tripcar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tripcar.yadu.com.tripcar.constant.CommonStrings;
import tripcar.yadu.com.tripcar.database.TripcarDatabase;
import tripcar.yadu.com.tripcar.gettersetter.UserCarGetterSetter;
import tripcar.yadu.com.tripcar.gettersetter.UserGetterSetter;
import tripcar.yadu.com.tripcar.handler.ServiceHandler;

public class AddCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    Spinner spinmake,spinmodel,spincomfort,spinnoseat,spincartype,spincolor;
    ArrayAdapter<String> datacartype,datacofort,datamodel,datacolor,dataseatno;

    ArrayList<String> listmake = new ArrayList<>();
    ArrayList<String> listmodel = new ArrayList<>();
    ArrayList<String> listcomfort = new ArrayList<>();
    ArrayList<String> listseat = new ArrayList<>();
    ArrayList<String> listtype = new ArrayList<>();
    ArrayList<String> listcolor = new ArrayList<>();

    String car_make="",car_model="",car_type="",car_color="",car_seat_no="",car_comfort="";
    boolean flag_make,flag_model,flag_type,flag_color,flag_seat_no,flag_comfort;

    ProgressDialog pDialog;

    SharedPreferences sharedPreferences;

    String access_tocken;

    Button btnsubmit;

    TripcarDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setLogo(R.drawable.ytrip_icongreens);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(CommonStrings.MYPREFERENCE, Context.MODE_PRIVATE);
        access_tocken = sharedPreferences.getString(CommonStrings.USER_ACCESS_TOCKEN, "");

        db = new TripcarDatabase(getApplicationContext());


        spinmake = (Spinner) findViewById(R.id.spincarmake);
        spinmodel = (Spinner) findViewById(R.id.spinmodel);
        spincomfort = (Spinner) findViewById(R.id.spincomfort);
        spinnoseat = (Spinner) findViewById(R.id.spinnoseats);
        spincartype = (Spinner) findViewById(R.id.spintype);
        spincolor = (Spinner) findViewById(R.id.spincolor);

        btnsubmit = (Button) findViewById(R.id.btnaddcar);

        spincomfort.setOnItemSelectedListener(this);
        spinmake.setOnItemSelectedListener(this);
        spinmodel.setOnItemSelectedListener(this);
        spincartype.setOnItemSelectedListener(this);
        spincolor.setOnItemSelectedListener(this);
        spinnoseat.setOnItemSelectedListener(this);

        flag_make=flag_model=flag_type=flag_color=flag_seat_no=flag_comfort=false;

        //Car Make

        String strArr[] = CommonStrings.carBrands;

        for(int i=0;i<strArr.length;i++){

            listmake.add(strArr[i]);

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_custom_item,listmake);

       // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinmake.setAdapter(new CustomSpinnerAdapter(this, R.layout.spinner_custom_item, strArr, "Select Car Make"));

        //Car Model

        String strmodel[] = CommonStrings.carModel;

        for(int i=0;i<strArr.length;i++){

            listmodel.add(strArr[i]);

        }

       // datamodel =  new ArrayAdapter<>(this, R.layout.spinner_custom_item,listmodel);

        spinmodel.setAdapter(new CustomSpinnerAdapter(this, R.layout.spinner_custom_item, strmodel, "Select Car Model"));


        //Car comfort

        strArr = CommonStrings.comfortLevel;

        for(int i=0;i<strArr.length;i++){

            listcomfort.add(strArr[i]);

        }


       // datacofort =  new ArrayAdapter<>(this, R.layout.spinner_custom_item,listcomfort);

        spincomfort.setAdapter(new CustomSpinnerAdapter(this, R.layout.spinner_custom_item, strArr, "Select Car Comfort"));


        //Car type

        strArr = CommonStrings.carType;

        for(int i=0;i<strArr.length;i++){

            listtype.add(strArr[i]);

        }


        //datacartype =  new ArrayAdapter<>(this, R.layout.spinner_custom_item,listtype);

        spincartype.setAdapter(new CustomSpinnerAdapter(this, R.layout.spinner_custom_item, strArr, "Select Car Type"));


        //Car color

        strArr = CommonStrings.carColor;

        for(int i=0;i<strArr.length;i++){

            listcolor.add(strArr[i]);

        }


       // datacolor =  new ArrayAdapter<>(this, R.layout.spinner_custom_item,listcolor);

        spincolor.setAdapter(new CustomSpinnerAdapter(this, R.layout.spinner_custom_item, strArr, "Select Car Color"));

        //Car number seat

        strArr = CommonStrings.seatsAvailable;

        for(int i=0;i<strArr.length;i++){

            listseat.add(strArr[i]);

        }


        //dataseatno =  new ArrayAdapter<>(this,R.layout.spinner_custom_item,listseat);

        spinnoseat.setAdapter(new CustomSpinnerAdapter(this, R.layout.spinner_custom_item, strArr, "Select Seat In Car"));

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new GetContacts().execute();

                if(car_make.equals("") || car_model.equals("") ||car_type.equals("") || car_color.equals("") || car_seat_no.equals("") || car_comfort.equals("")){


                }
                else{

                    db.open();

                    UserCarGetterSetter userCarGetterSetter = new UserCarGetterSetter();
                    userCarGetterSetter.setMake(car_make);
                    userCarGetterSetter.setModel(car_model);
                    userCarGetterSetter.setCar_type(car_type);
                    userCarGetterSetter.setComfort(car_comfort);
                    userCarGetterSetter.setColourc(car_color);
                    userCarGetterSetter.setSeats(car_seat_no);

                    db.insertCarData(userCarGetterSetter);

                    finish();

                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){

            case R.id.spincarmake:

              /*  if(position==0 && flag_make==false){
                    flag_make=true;
                }
                else{
                    car_make = listmake.get(position);
                    Toast.makeText(getApplicationContext(),"Position="+position+" "+listmake.get(position),Toast.LENGTH_LONG).show();
                }*/
                car_make = listmake.get(position);
               // Toast.makeText(getApplicationContext(),"Position="+position+" "+listmake.get(position),Toast.LENGTH_LONG).show();

                break;

            case R.id.spincomfort:

            /*    if(position==0 && flag_comfort==false){
                    flag_comfort=true;
                }
                else{
                    car_comfort = listcomfort.get(position);
                    Toast.makeText(getApplicationContext(),"Position="+position+" "+listcomfort.get(position),Toast.LENGTH_LONG).show();
                }*/

                car_comfort = listcomfort.get(position);
               // Toast.makeText(getApplicationContext(),"Position="+position+" "+listcomfort.get(position),Toast.LENGTH_LONG).show();

                break;

            case R.id.spinmodel:

                car_model = listmodel.get(position);

                break;

            case R.id.spintype:

                car_type = listtype.get(position);

                break;

            case R.id.spincolor:

                car_color = listcolor.get(position);

                break;

            case R.id.spinnoseats:

                car_seat_no = listseat.get(position);

                break;

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public class CustomSpinnerAdapter extends ArrayAdapter<String>{

        Context context;
        String[] objects;
        String firstElement;
        boolean isFirstTime;

        public CustomSpinnerAdapter(Context context, int textViewResourceId, String[] objects, String defaultText) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.objects = objects;
            this.isFirstTime = true;
            setDefaultText(defaultText);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if(isFirstTime) {
                objects[0] = firstElement;
                isFirstTime = false;
            }
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            notifyDataSetChanged();
            return getCustomView(position, convertView, parent);
        }

        public void setDefaultText(String defaultText) {
            this.firstElement = objects[0];
            objects[0] = defaultText;
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.spinner_custom_item, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvspinnerhead);
            label.setText(objects[position]);

            return row;
        }

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

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, UserGetterSetter> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            // Progress dialog
            pDialog = new ProgressDialog(AddCarActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Loading...");
            showDialog();

        }

        @Override
        protected UserGetterSetter doInBackground(Void... arg0) {

            //data.clear();


            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            // String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("make", car_make));
            params.add(new BasicNameValuePair("model", car_model));
            params.add(new BasicNameValuePair("comfort", car_comfort));
            params.add(new BasicNameValuePair("seats", car_seat_no));
            params.add(new BasicNameValuePair("colour", car_color));
            params.add(new BasicNameValuePair("car_type", car_type));
            params.add(new BasicNameValuePair("number", "car number"));
            params.add(new BasicNameValuePair("access_token", access_tocken));

            String jsonStr = sh.makeServiceCall(CommonStrings.URL+CommonStrings.ADD_CAR_BASE,ServiceHandler.POST,params);

            Log.d("Response: ", "> " + jsonStr);
            System.out.println("Server Respone---> "+jsonStr);

            UserGetterSetter userGetterSetter= new UserGetterSetter();

            try {

                JSONObject jObj = new JSONObject(jsonStr);
                String status = jObj.getString("status");
                String message = jObj.getString("message");


                if (status.equals("103") && message.equals("Not An Authenticated User!")) {
                    Snackbar.make(spinmake,"Not an authencated user",Snackbar.LENGTH_SHORT).show();
                }else if(status.equals("104") && message.equals("Car added successfully!")){

                   /* //setContentView(R.layout.temp_image_layout);

                    JSONObject jsonObject = jObj.getJSONObject("data");

                    userGetterSetter.setFirst_name(jsonObject.getString("first_name"));
                    userGetterSetter.setLast_name(jsonObject.getString("last_name"));
                    userGetterSetter.setUser_access_tocken(jsonObject.getString("access_token"));
                    userGetterSetter.setPhone_number(jsonObject.getString("phone"));
                    userGetterSetter.setNew_user(jsonObject.getInt("new"));*/

                    Snackbar.make(spinmake,"Carr Added Successfully",Snackbar.LENGTH_SHORT).show();


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
            hideDialog();

            finish();

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

}
