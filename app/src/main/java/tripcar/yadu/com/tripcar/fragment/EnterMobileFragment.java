package tripcar.yadu.com.tripcar.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import tripcar.yadu.com.tripcar.R;
import tripcar.yadu.com.tripcar.constant.CommonStrings;
import tripcar.yadu.com.tripcar.handler.ServiceHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterMobileFragment extends Fragment {

    String mobile, fb_id, user_access_tocken;
    SharedPreferences sharedPreferences;

    FancyButton btnverify;
    EditText etmobnum;

    private ProgressBar progressBar;

    ProgressDialog pDialog;


    public EnterMobileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_enter_mobile, container, false);

        etmobnum = (EditText) view.findViewById(R.id.etmobnum_verify);
        btnverify = (FancyButton) view.findViewById(R.id.btn_verify_mob_num);

        sharedPreferences = getActivity().getSharedPreferences(CommonStrings.MYPREFERENCE, Context.MODE_PRIVATE);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        fb_id = sharedPreferences.getString(CommonStrings.PROFILE_URL,"");
        user_access_tocken = sharedPreferences.getString(CommonStrings.USER_ACCESS_TOCKEN,"");

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 mobile = etmobnum.getText().toString().trim();

                // validating mobile number
                // it should be of 10 digits length
                if (isValidPhoneNumber(mobile)) {

                    // request for sms
                    // progressBar.setVisibility(View.VISIBLE);

                    // saving the mobile number in shared preferences
                    //   pref.setMobileNumber(mobile);

                    // requesting for sms
                    //  requestForSMS(mobile);

                   // new GetContacts().execute();

                    pDialog = new ProgressDialog(getActivity());
                    pDialog.setCancelable(false);
                    pDialog.setMessage("Loading...");
                    // showDialog();
                    pDialog.show();


                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            hideDialog();

                            EnterOTPFragment enterMobileFragment = new EnterOTPFragment();

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_sms, enterMobileFragment).commit();

                        }
                    }, 2000);


                } else {
                    Toast.makeText(getActivity(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    /**
     * Regex to validate the mobile number
     * mobile number should be of 10 digits length
     *
     * @param mobile
     * @return
     */
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }


    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, String> {

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
        protected String doInBackground(Void... arg0) {

            String result=null;

            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            // String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("access_token", user_access_tocken));
            params.add(new BasicNameValuePair("phone", mobile));

            String jsonStr = sh.makeServiceCall(CommonStrings.URL+CommonStrings.MOBILE_REGISTER_BASE,ServiceHandler.POST,params);

            Log.d("Response: ", "> " + jsonStr);

            try {

                JSONObject jObj = new JSONObject(jsonStr);
                String status = jObj.getString("status");
                String message = jObj.getString("message");


                if (status.equals("104") && message.equals("Please check your phone and verify pin.")) {

                    result="Success";

                } else {

                    result = "Failure";
                }

            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            if(result.equals("Success")){

                hideDialog();

                EnterOTPFragment enterMobileFragment = new EnterOTPFragment();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_sms,enterMobileFragment).commit();

            }
            else{
                //temp

                hideDialog();

                EnterOTPFragment enterMobileFragment = new EnterOTPFragment();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_sms,enterMobileFragment).commit();

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

}
