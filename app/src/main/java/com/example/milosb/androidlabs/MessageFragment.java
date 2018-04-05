package com.example.milosb.androidlabs;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MessageFragment extends Fragment {
    protected static final String ACTIVITY_NAME = "MessageFragment Class";

    SQLiteDatabase db;
    ChatDatabaseHelper chatDb;

    TextView message;
    TextView id;
    Button delete;

    boolean tabletUse;

    View view;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        chatDb = new ChatDatabaseHelper(getActivity());
        db = chatDb.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.message_fragment, container, false);
        message = view.findViewById(R.id.show_msg);
        id = view.findViewById(R.id.show_id);
        View frameLayout = view.findViewById(R.id.frame_layout);

        if (frameLayout != null) tabletUse = true;
        else tabletUse = false;

        message.setText(getArguments().getString("MESSAGE"));
        id.setText(getArguments().getString("ID"));

        final Intent data = new Intent();

        data.putExtra("ID", id.getText().toString());
        delete = view.findViewById(R.id.btn_delete_msg);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long ID = Long.valueOf(data.getStringExtra("ID"));

                deleteMessage(ID);


                int duration = Toast.LENGTH_LONG;


                Toast toast = Toast.makeText(container.getContext(), "MESSAGE ERASED", duration);
                toast.show();

                Intent intent = new Intent(container.getContext() ,ChatWindow.class);

                startActivity(intent);


            }
        });
        return view;
    }

    public void deleteMessage(long ID) {

        db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + " = " + ID + ";", null);
    }

}
