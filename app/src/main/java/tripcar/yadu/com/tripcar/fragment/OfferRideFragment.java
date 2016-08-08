package tripcar.yadu.com.tripcar.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

import mehdi.sakout.fancybuttons.FancyButton;
import tripcar.yadu.com.tripcar.AllCarsActivity;
import tripcar.yadu.com.tripcar.R;
import tripcar.yadu.com.tripcar.common.logger.Log;
import tripcar.yadu.com.tripcar.constant.CommonStrings;
import tripcar.yadu.com.tripcar.dialog.DateTime;
import tripcar.yadu.com.tripcar.dialog.DateTimePicker;
import tripcar.yadu.com.tripcar.dialog.SimpleDateTimePicker;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferRideFragment extends Fragment implements View.OnClickListener ,DateTimePicker.OnDateTimeSetListener{

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    //static TextView tvstart,tvdestination,tvdate;
    static FancyButton btnfrom,btnto,btndate,btnseat,btnoffer_ride;

    public static String TAG="Autocomplete";

    boolean flag_destination_selected=false;

    int day,month;

    Spinner spin_seat;

    private Dialog dialog;

    boolean click_flag = true;

    public OfferRideFragment() {
        // Required empty public constructor
    }

    ArrayList<String> list= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_offer_ride, container, false);

        btnfrom = (FancyButton) view.findViewById(R.id.btn_from);
        btnto = (FancyButton) view.findViewById(R.id.btn_to);
        btndate = (FancyButton) view.findViewById(R.id.btndate);
        btnseat = (FancyButton) view.findViewById(R.id.btnseat);

        btnoffer_ride = (FancyButton) view.findViewById(R.id.btn_offer_ride);

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

        btnfrom.setOnClickListener(this);
        btnto.setOnClickListener(this);
        btndate.setOnClickListener(this);
        btnseat.setOnClickListener(this);
        btnoffer_ride.setOnClickListener(this);

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

                SimpleDateTimePicker.make(
                        "Set Date & Time Title",
                        new Date(),
                        this,
                        getActivity().getSupportFragmentManager()
                ).show();

                break;

            case R.id.btnseat:

                spin_seat.performClick();

              //  showMyDialog();

                break;


            case R.id.btn_offer_ride:

              //  Intent in = new Intent(getActivity(), AddCarActivity.class);

                String departure_city = btnfrom.getText().toString();
                String arrival_city = btnto.getText().toString();

                if(!checkNetIsAvailable()){

                    Snackbar.make(btnfrom, CommonStrings.NO_INTERNET_CONNECTION, Snackbar.LENGTH_SHORT).show();

                }else if(departure_city.equals("") || departure_city.equalsIgnoreCase("Leaving from")){

                    Snackbar.make(btnfrom,"Please select the departure place",Snackbar.LENGTH_SHORT).show();

                }
                else if( arrival_city.equals("") || arrival_city.equalsIgnoreCase("Going to")){
                    Snackbar.make(btnfrom,"Please select the arrival place",Snackbar.LENGTH_SHORT).show();
                }
                else {

                    Intent in = new Intent(getActivity(), AllCarsActivity.class);

                    getActivity().startActivity(in);

                    getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);


                }


                break;

        }

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

                LatLng latlng = place.getLatLng();

               double lotitude = latlng.latitude;
                double longitude = latlng.longitude;
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

    @Override
    public void DateTimeSet(Date date) {

        // This is the DateTime class we created earlier to handle the conversion
        // of Date to String Format of Date String Format to Date object
        DateTime mDateTime = new DateTime(date);
        // Show in the LOGCAT the selected Date and Time
        Log.v("TEST_TAG", "Date and Time selected: " + mDateTime.getDateString());

        btndate.setText(mDateTime.getDateString());

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
                btnseat.setText(newVal+"");
               // dialog.cancel();
            }
        });

        // dialog.setTitle("About Android Dialog Box");

        dialog.show();
    }

}
