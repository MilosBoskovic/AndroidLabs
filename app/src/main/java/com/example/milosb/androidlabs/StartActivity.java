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

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent listIntent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(listIntent, 50);
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
