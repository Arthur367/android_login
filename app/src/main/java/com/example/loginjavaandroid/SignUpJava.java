package com.example.loginjavaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class SignUpJava extends AppCompatActivity {

    private Button loginBtn;
    private Button registerBtn;
    private TextView welcomBack;
    private TextView emailTextView;
    private TextView passTextView;
    private EditText emailInput;
    private EditText pass;
    private TextView loginTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_java);
        getSupportActionBar().hide();
        init();
    }
    private void init() {
        registerBtn = findViewById(R.id.sign_up_register);
        registerBtn.setOnClickListener((view) -> {
            this.onregisterPressed();
        });

        // Configure Welcom back. Log in component
        welcomBack = findViewById(R.id.welcome_back);

        // Configure Email Address component
        emailTextView = findViewById(R.id.sign_up_email_view);

        // Configure Password component
        passTextView = findViewById(R.id.sign_password_view);
        emailInput = findViewById(R.id.sign_email_input);
        pass = findViewById(R.id.sign_password_input);
        loginTxt = findViewById(R.id.sign_login_view);
        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpJava.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onregisterPressed() {
        String username = emailInput.getText().toString();
        String password = pass.getText().toString();
        new ExecuteTask().execute(username,password);
    }

    private void onloginPressed() {
    }

    private void onButtonTwoPressed() {
    }

    class ExecuteTask extends AsyncTask<String, Integer,String>{
        @Override
        protected String doInBackground(String... strings) {
            PostData(strings);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "SignUp Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), DasbBoard.class);
            startActivity(intent);
        }

        public void PostData(String[] value){
            try{
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://demo2.nav.kobby.co.ke:2047/BC200/WS/MOBILETEST/Codeunit/IntegrationTest");
                List<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("username",value[0]));
                list.add(new BasicNameValuePair("password", value[1]));
                post.setEntity(new UrlEncodedFormEntity(list));
                client.execute(post);

            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}