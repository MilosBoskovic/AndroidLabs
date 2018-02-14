package com.example.milosb.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    Button loginButton = (Button) findViewById(R.id.login_button) ;
    //Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        SharedPreferences preferences = getSharedPreferences("email", Context.MODE_PRIVATE);
       final SharedPreferences.Editor edit = preferences.edit(); //edit the file


        preferences.getString("DefaultEmail", "email@domain.com");


        final EditText loginText = (EditText) findViewById(R.id.email);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.putString("DefaultEmail", loginText.getText().toString());
                edit.commit();

                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);

            }
        });

        //preferences.getString("DefaultEmail", "email@domain.com");
    }

    @Override
    protected  void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
