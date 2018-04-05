package com.example.milosb.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        Button startButton = (Button) findViewById(R.id.button);
        Button chatButton = findViewById(R.id.chat_button);
        Button weatherButton = (Button) findViewById(R.id.weather_button);
        Button testToolbar = (Button) findViewById(R.id.start_toolbar);

        testToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, TestToolbar.class));
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent listIntent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(listIntent, 50);
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User Clicked Start Chat button");

                Intent chatIntent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(chatIntent);
            }
        });

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User Clicked Weather Forecast button");

                Intent weatherIntent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivity(weatherIntent);
            }
        });
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data) {
        if(resultCode == 50) {

            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");

        } else if (resultCode == Activity.RESULT_OK) {
            String messagePassed = data.getStringExtra("Response");

            CharSequence text = "ListItemsActivity passed: My information to share";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this , text, duration); //this is the ListActivity
            toast.show(); //display your message box
        }
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
