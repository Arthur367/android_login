package com.example.loginjavaandroid;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginjavaandroid.api.Client;
import com.example.loginjavaandroid.requests.LoginRequest;
import com.example.loginjavaandroid.responses.LoginResponse;
import com.example.loginjavaandroid.services.LoginService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.auth.AuthScope;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private Button buttonButton;
    private TextView welcomBackLogInTextView;
    private TextView passwordTextView;
    private EditText email;
    private EditText passwordInput;
    private TextView signNew,loginTxt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }
    private void init() {

        // Configure Button component
        buttonButton = this.findViewById(R.id.button_button);
        buttonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onButtonTwoPressed();
            }
        });

        // Configure Button component
        Button login = this.findViewById(R.id.login);
        login.setOnClickListener((view) -> {
            this.onloginPressed();
        });

        // Configure Don't have an accoun component
        Button register = this.findViewById(R.id.registerButton);
        register.setOnClickListener((view) -> {
            this.onregisterPressed();
        });

        // Configure Welcom back. Log in component
        welcomBackLogInTextView = this.findViewById(R.id.welcom_back_log_in_text_view);

        // Configure Email Address component
        TextView emailAddressTextView = this.findViewById(R.id.email_address_text_view);

        // Configure Password component
        passwordTextView = this.findViewById(R.id.password_text_view);
        email = this.findViewById(R.id.emailInput);
        passwordInput = this.findViewById(R.id.passwordInput);
        signNew = findViewById(R.id.sign_up_textView);
        signNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                Intent signIntent = new Intent(getApplicationContext(), SignUpJava.class);
                startActivity(signIntent);
            }
        });
    }

    private void onloginPressed() {
        String user = email.getText().toString();
        String pass = passwordInput.getText().toString();
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://demo2.nav.kobby.co.ke:2048/BC200/ODataV4/Company('MOBILETEST')/QYLTRLogins");

// Add authorization header
        httpGet.addHeader(BasicScheme.authenticate( new UsernamePasswordCredentials(user, pass), "UTF-8", false));

// Set up the header types needed to properly transfer JSON
        httpGet.setHeader("Content-Type", "application/json");
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                Toast.makeText(getApplicationContext(), "Successfull login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DasbBoard.class);
                startActivity(intent);
            } else {
//                Log.e(ParseJSON.class.toString(), "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        AsyncHttpClient client = new AsyncHttpClient();
//        client.setBasicAuth(user, pass);
//        client.get("http://demo2.nav.kobby.co.ke:2048/BC200/ODataV4/Company('MOBILETEST')/QYLTRLogins", null, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
//                String json = new String(bytes); // This is the json.
//                Log.w("statusCode", String.valueOf(statusCode));
//                Toast.makeText(getApplicationContext(), "Successfull login", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), DasbBoard.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
//            }
//        });

//        new ExecuteTask().execute(user,pass);
//        LoginService loginService =
//                Client.createService(LoginService.class, email.getText().toString(), passwordInput.getText().toString());
//        Call<LoginResponse> call = loginService.userLogin();
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), DasbBoard.class);
//                    startActivity(intent);
//                } else {
//                    // error response, no access to resource?
//                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                // something went completely south (like no internet connection)
//                Log.d("Error", t.getMessage());
//            }
//        });
    }

    public void onButtonTwoPressed() {

    }

    public void onregisterPressed() {

    }




//    class ExecuteTask extends AsyncTask<String, Integer,String>{
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String res = PostData(strings);
//            return res;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), DasbBoard.class);
//            startActivity(intent);
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            Toast.makeText(getApplicationContext(), values.toString(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    public String PostData(String[] value){
//        String s ="";
//        try {
//            HttpClient client = new DefaultHttpClient();
//            HttpPost post = new HttpPost("http://demo2.nav.kobby.co.ke:2048/BC200/ODataV4/Company('MOBILETEST')/QYLTRLogins");
//            List<NameValuePair> list = new ArrayList<>();
//            list.add(new BasicNameValuePair("username", value[0]));
//            list.add(new BasicNameValuePair("password", value[1]));
//            post.setEntity(new UrlEncodedFormEntity(list));
//            HttpResponse response = client.execute(post);
//            HttpEntity entity = response.getEntity();
//            s = readResponse(response);
//            Log.w("Response", entity.toString());
//        }catch(Exception e){}
//        return s;
//    }
//    public String readResponse(HttpResponse res) {
//        InputStream io = null;
//        String return_text = "";
//        try {
//            io = res.getEntity().getContent();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(io));
//            String line = "";
//            StringBuffer sb = new StringBuffer();
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//            return_text = sb.toString();
//        } catch (Exception e) {
//        }
//        return return_text;
//    }
}

