package com.example.milosb.androidlabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class TestToolbar extends AppCompatActivity {

    String sbMessage = "Chat Option selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "E-Mail functionality coming soon...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();

        switch (id){
            case R.id.action_camera:
                Log.d("Toolbar: ", "Camera option selected");

                final AlertDialog.Builder custom = new AlertDialog.Builder(TestToolbar.this);
                LayoutInflater inflater = TestToolbar.this.getLayoutInflater();
                View customDialogView = inflater.inflate(R.layout.custom_dialog, null);

                final EditText newMsg = customDialogView.findViewById(R.id.new_msg);

                custom.setView(customDialogView)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if(newMsg.getText().length() > 0){
                                    sbMessage = newMsg.getText().toString();
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                custom.show();
                break;

            case R.id.action_text:
                Log.d("Toolbar: ", "Chat option selected");
                Snackbar.make(findViewById(R.id.activity_test_toolbar), sbMessage, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

            case R.id.action_weather:
                Log.d("Toolbar: ", "Weather option selected");

                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle(R.string.go_back)

                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.menu_about:
                Log.d("Toolbar: ", "About option selected");
                int duration = Toast.LENGTH_LONG;


                Toast toast = Toast.makeText(this, "Version 1.0 by Milos Boskovic", duration);
                toast.show();
                break;

        }

        return true;
    }
}
