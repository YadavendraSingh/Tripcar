package tripcar.yadu.com.tripcar.handler;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

/**
 * Created by yadavendras on 26-08-2015.
 */
public class ServiceHandler {

    private static final String TAG ="Hit Server" ;
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    public ServiceHandler() {

    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String url, int method,
                                  List<NameValuePair> params) {

        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // As the server might be down, we will retry it a couple
        // times.

        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");

            try {

                // http client
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpEntity httpEntity = null;
                HttpResponse httpResponse = null;

                // Checking http request method type
                if (method == POST) {
                    HttpPost httpPost = new HttpPost(url);
                    // adding post params
                    if (params != null) {
                        httpPost.setEntity(new UrlEncodedFormEntity(params));
                    }

                    httpResponse = httpClient.execute(httpPost);

                } else if (method == GET) {
                    // appending params to url
                    if (params != null) {
                        String paramString = URLEncodedUtils
                                .format(params, "utf-8");
                        url += "?" + paramString;
                    }
                    HttpGet httpGet = new HttpGet(url);

                    httpResponse = httpClient.execute(httpGet);

                }
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
                if(response!=null){
                    break;
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i + ":" + e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
            }

            // increase backoff exponentially
            backoff *= 2;
        }

        Log.e(TAG, "------->>>> Attempt No.  " + MAX_ATTEMPTS);

        return response;

    }
}
