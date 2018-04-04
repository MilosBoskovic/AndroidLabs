package com.example.milosb.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle bundle = new Bundle();
        bundle.putString("ID", getIntent().getStringExtra("ID"));
        bundle.putString("MESSAGE", getIntent().getStringExtra("MESSAGE"));


        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MessageFragment mf = new MessageFragment();
        mf.setArguments(bundle);
        ft.addToBackStack(" nothing");
        ft.replace(R.id.fragmentMsgDetail, mf);
        ft.commit();

    }
}
