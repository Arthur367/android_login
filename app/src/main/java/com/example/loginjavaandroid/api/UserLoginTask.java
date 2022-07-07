package com.example.loginjavaandroid.api;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.loginjavaandroid.DasbBoard;
import com.example.loginjavaandroid.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class UserLoginTask extends AsyncTask<String, Void, Boolean> {

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success == true) {
            //Do whatever your app does after login


        } else {
            //Let user know login has failed
        }
    }

    @Override
    protected Boolean doInBackground(String... login) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                "http://demo2.nav.kobby.co.ke:2048/BC200/ODataV4/Company('MOBILETEST')/QYLTRLogins");
        String str = null;
        String username = login[0];
        String password = login[1];

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return false;
        }

        try {
            HttpResponse response = httpclient.execute(httppost);

            str = EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Whatever parsing you need to do on the response
        //This is an example if the webservice just passes back a String of "true or "false"

        if (str.trim().equals("true")) {

            return true;
        } else {
            return false;

        }
    }
}
