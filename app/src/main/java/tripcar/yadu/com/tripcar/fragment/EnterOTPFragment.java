package tripcar.yadu.com.tripcar.fragment;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tripcar.yadu.com.tripcar.MainActivity;
import tripcar.yadu.com.tripcar.R;
import tripcar.yadu.com.tripcar.constant.CommonStrings;
import tripcar.yadu.com.tripcar.handler.ServiceHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterOTPFragment extends Fragment {

    ImageView imgmatchotp;
    EditText etotp;

    ProgressDialog pDialog;

    String otp,fb_id,access_tocken;
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    public EnterOTPFragment() {
        // Required empty public constructor
    }

    private SmsReceiver smsReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_enter_ot, container, false);

        imgmatchotp = (ImageView) view.findViewById(R.id.imgmatcotp);
        etotp = (EditText) view.findViewById(R.id.etotp_verify);

        sharedPreferences = getActivity().getSharedPreferences(CommonStrings.MYPREFERENCE, Context.MODE_PRIVATE);

        fb_id = sharedPreferences.getString(CommonStrings.PROFILE_URL, "");
        access_tocken = sharedPreferences.getString(CommonStrings.USER_ACCESS_TOCKEN, "");

        smsReceiver = new SmsReceiver();

        // Register receiver to receive the last sms
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        intentFilter.setPriority(999);

        getActivity().registerReceiver(smsReceiver, intentFilter);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                etotp.setText("564234");

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

                      /*  Intent in = new Intent(getActivity(), MainActivity.class);
                        getActivity().startActivity(in);
*/
                        editor = sharedPreferences.edit();

                        editor.putBoolean(CommonStrings.PHONE_NUMBER_VERIFIED, true);
                        editor.commit();

                        getActivity().finish();
                    }
                }, 2000);


            }
        }, 2000);


        imgmatchotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               otp = etotp.getText().toString().trim();

                if(otp.equals("")){
                    Snackbar.make(view,"Please fill OTP",Snackbar.LENGTH_SHORT).show();
                }
                else{
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

                           /* Intent in = new Intent(getActivity(), MainActivity.class);
                            getActivity().startActivity(in);

*/
                            editor = sharedPreferences.edit();

                            editor.putBoolean(CommonStrings.PHONE_NUMBER_VERIFIED, true);
                            editor.commit();


                            getActivity().finish();
                        }
                    }, 2000);
                }

            }
        });

        return view;
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
            params.add(new BasicNameValuePair("access_token", access_tocken));
            params.add(new BasicNameValuePair("otp", otp));

            String jsonStr = sh.makeServiceCall(CommonStrings.URL + CommonStrings.CHECK_OTP_BASE, ServiceHandler.POST, params);

            Log.d("Response: ", "> " + jsonStr);

            try {

                JSONObject jObj = new JSONObject(jsonStr);
                String status = jObj.getString("status");
                String message = jObj.getString("message");


                if (status.equals("104") && message.equals("Pin matched successfully")) {

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

                Intent in = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(in);

                getActivity().finish();
            }
            else{
                //temp

                hideDialog();

                Intent in = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(in);

                getActivity().finish();

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


    public class SmsReceiver extends BroadcastReceiver {
        private final String TAG = SmsReceiver.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {

            final Bundle bundle = intent.getExtras();
            try {
                if (bundle != null) {
                    Object[] pdusObj = (Object[]) bundle.get("pdus");
                    for (Object aPdusObj : pdusObj) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                        String senderAddress = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();

                        Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                        // if the SMS is not from our gateway, ignore the message
                        if (!senderAddress.toLowerCase().contains(CommonStrings.SMS_ORIGIN.toLowerCase())) {
                            Log.e(TAG, "SMS is not for our app!");
                            return;
                        }

                        // verification code from sms
                        String verificationCode = getVerificationCode(message);

                        etotp.setText(verificationCode);

                        new GetContacts().execute();

                        Log.e(TAG, "OTP received: " + verificationCode);

                    /*    Intent hhtpIntent = new Intent(context, HttpService.class);
                        hhtpIntent.putExtra("otp", verificationCode);
                        context.startService(hhtpIntent);*/
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        /**
         * Getting the OTP from sms message body
         * ':' is the separator of OTP from the message
         *
         * @param message
         * @return
         */
        private String getVerificationCode(String message) {
            String code = null;
            int index = message.indexOf(CommonStrings.OTP_DELIMITER);

            if (index != -1) {
                int start = index + 2;
                int length = 6;
                code = message.substring(start, start + length);
                return code;
            }

            return code;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        try {

            // Unregister the receiver

            getActivity().unregisterReceiver(this.smsReceiver);

        }catch(Exception e){}
    }
}
