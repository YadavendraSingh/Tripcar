package tripcar.yadu.com.tripcar.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import tripcar.yadu.com.tripcar.FindCarActivity;
import tripcar.yadu.com.tripcar.R;
import tripcar.yadu.com.tripcar.common.logger.Log;
import tripcar.yadu.com.tripcar.constant.CommonStrings;
import tripcar.yadu.com.tripcar.handler.ServiceHandler;
import tripcar.yadu.com.tripcar.models.FindCarData;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindRideFragment extends Fragment implements View.OnClickListener{

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    // static TextView tvstart,tvdestination,tvdate;
    static FancyButton btnfrom,btnto,btndate,btnseat,btnfindride;

    public static String TAG="Autocomplete";

    boolean flag_destination_selected=false;

    Spinner spin_seat;

    ArrayList<String> list= new ArrayList<>();

    private int mYear;
    private int mMonth;
    private int mDay;

    static final int DATE_DIALOG_ID = 0;

    private Dialog dialog;

    ProgressDialog pDialog;
    String access_tocken,departure_city,arrival_city;
    SharedPreferences sharedPreferences;

    boolean click_flag = true;

    public FindRideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_find_ride, container, false);

        btnfrom = (FancyButton) view.findViewById(R.id.btn_from);
        btnto = (FancyButton) view.findViewById(R.id.btn_to);
        btndate = (FancyButton) view.findViewById(R.id.btndate);
        btnseat = (FancyButton) view.findViewById(R.id.btnseat);

        btnfindride = (FancyButton) view.findViewById(R.id.btn_find_ride);

        spin_seat = (Spinner) view.findViewById(R.id.spinseat);

        String strArr[] = CommonStrings.seatsAvailable;

        for(int i=0;i<strArr.length;i++){

            list.add((i+1)+"");

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_custom_item,list);

        spin_seat.setAdapter(dataAdapter);

        spin_seat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btnseat.setText(list.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sharedPreferences = getActivity().getSharedPreferences(CommonStrings.MYPREFERENCE, Context.MODE_PRIVATE);

        access_tocken = sharedPreferences.getString(CommonStrings.USER_ACCESS_TOCKEN, "");

        btnfrom.setOnClickListener(this);
        btnto.setOnClickListener(this);
        btndate.setOnClickListener(this);
        btnseat.setOnClickListener(this);
        btnfindride.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){

            case R.id.btn_from:

                if(click_flag){

                    click_flag = false;

                    openAutocompleteActivity();

                }

                break;

            case  R.id.btn_to:

                flag_destination_selected = true;

                if(click_flag){

                    click_flag = false;

                    openAutocompleteActivity();

                }

                break;

            case R.id.btndate:

               // Initialize a new date picker dialog fragment
                DialogFragment dFragment = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment.show(getActivity().getFragmentManager(), "Date Picker");

                break;

            case R.id.btnseat:

                spin_seat.performClick();
                //showMyDialog();

                break;

            case R.id.btn_find_ride:


                departure_city = btnfrom.getText().toString();
                arrival_city = btnto.getText().toString();

                if(!checkNetIsAvailable()){

                    Snackbar.make(btnfindride,CommonStrings.NO_INTERNET_CONNECTION,Snackbar.LENGTH_SHORT).show();

                }else if(departure_city.equals("") || departure_city.equalsIgnoreCase("Leaving from")){

                    Snackbar.make(btnfindride,"Please select the departure place",Snackbar.LENGTH_SHORT).show();

                }
                else if( arrival_city.equals("") || arrival_city.equalsIgnoreCase("Going to")){
                    Snackbar.make(btnfindride,"Please select the arrival place",Snackbar.LENGTH_SHORT).show();
                }
                else{

                    Intent in = new Intent(getActivity(),FindCarActivity.class);

                    in.putExtra(CommonStrings.KEY_DEPARTURE_CITY, departure_city);
                    in.putExtra(CommonStrings.KEY_ARRIVAL_CITY, arrival_city);

                    getActivity().startActivity(in);

                    //slide from left to right
                    getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                }

                break;

        }

    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(getActivity());
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e(TAG, message);
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {

            click_flag = true;

            if (resultCode == getActivity().RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i(TAG, "Place Selected: " + place.getName());

                // Format the place's details and display them in the TextView.


                if(flag_destination_selected){

                    flag_destination_selected = false;

                   /* flag_destination_selected = false;

                    tvdestination.setText(formatPlaceDetails(getResources(), place.getName(),
                            place.getId(), place.getAddress(), place.getPhoneNumber(),
                            place.getWebsiteUri()));

                    // Display attributions if required.
                    CharSequence attributions = place.getAttributions();
                    if (!TextUtils.isEmpty(attributions)) {
                        tvdestination.setText(Html.fromHtml(attributions.toString()));
                    } else {
                        tvdestination.setText("");
                    }*/

                    btnto.setText(place.getName().toString());

                }else{

                    /*tvstart.setText(formatPlaceDetails(getResources(), place.getName(),
                            place.getId(), place.getAddress(), place.getPhoneNumber(),
                            place.getWebsiteUri()));

                    // Display attributions if required.
                    CharSequence attributions = place.getAttributions();
                    if (!TextUtils.isEmpty(attributions)) {
                        tvstart.setText(Html.fromHtml(attributions.toString()));
                    } else {
                        tvstart.setText("");
                    }*/

                    btnfrom.setText(place.getName().toString());

                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Log.e(TAG, "Error: Status = " + status.toString());
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }

    /**
     * Helper method to format information about a place nicely.
     */
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }


    void showMyDialog(){
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_number_picker);

        NumberPicker np = (NumberPicker) dialog.findViewById(R.id.number_pick);
        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(4);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected number from picker
                btnseat.setText(newVal + "");
               // dialog.cancel();
            }
        });

        // dialog.setTitle("About Android Dialog Box");

        dialog.show();
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            /*
                Create a DatePickerDialog using Theme.

                    DatePickerDialog(Context context, int theme, DatePickerDialog.OnDateSetListener listener,
                        int year, int monthOfYear, int dayOfMonth)
             */

            // DatePickerDialog THEME_HOLO_LIGHT
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,this,year,month,day);


            // Return the DatePickerDialog
            return  dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            // Do something with the chosen date
            btndate.setText(
                    new StringBuilder()
                            // Month is 0 based so add 1
                            .append(day).append("-")
                            .append(month+1).append("-")
                            .append(year).append(" ").toString());

        }
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
            pDialog = new ProgressDialog(getActivity());
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

            if(result.getStatus().equals("105")){

                Intent in = new Intent(getActivity(),FindCarActivity.class);

                in.putExtra(CommonStrings.KEY_FIND_CAR, (Serializable) result);

                getActivity().startActivity(in);

                //slide from left to right
                getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

            }
            else{
                Intent in = new Intent(getActivity(),FindCarActivity.class);

                in.putExtra(CommonStrings.KEY_FIND_CAR, (Serializable) result);

                getActivity().startActivity(in);

                //slide from left to right
                getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
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


    //Check Internet Connectivity
    public boolean checkNetIsAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("findcar.json");
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
}