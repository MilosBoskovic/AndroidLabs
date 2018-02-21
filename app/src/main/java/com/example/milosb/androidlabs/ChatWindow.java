package com.example.milosb.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    ListView chatList;
    EditText chatText;
    Button sendButton;

    ArrayList<String> chatHistory = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatList = findViewById(R.id.chatList);
        chatText = findViewById(R.id.chatText);
        sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatHistory.add(chatText.getText().toString());
            }
        });
    }


    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter (Context ctx) {
            super(ctx, 0);
        }

        // returns the number of rows that will be in your listView
        public int getCount() {
            return chatHistory.size();
        }

        //returns the item to show in the list at the specified position
        public String getItem(int position) {
            return chatHistory.get(position);
        }

        //TODO: this returns the layout that will be positioned at the specified row in the list. Do this in step 9.
        public View getView(int position, View convertView, ViewGroup parent) {

            return null;
        }

        // This is the database id of the item at position. For now, we aren’t using SQL, so just return the number: position.
        public long getId(int position) {
            return position;
        }
    }
}
