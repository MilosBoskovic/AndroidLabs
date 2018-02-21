package com.example.milosb.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    ListView chatList;
    EditText chatText;
    Button sendButton;

    ArrayList<String> chatHistory;// = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatList = (ListView) findViewById(R.id.chatList);
        chatText = (EditText) findViewById(R.id.chatText336);
        sendButton = (Button) findViewById(R.id.send_button);
        chatHistory = new ArrayList<>(5);

        //in this case, “this” is the ChatWindow, which is-A Context object
        final ChatAdapter messageAdapter = new ChatAdapter( this );
        chatList.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatHistory.add(chatText.getText().toString());

                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
                chatText.setText("");


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

        //this returns the layout that will be positioned at the specified row in the list
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            // This will recreate your View that you made in the resource file. If the position is
            // an even number (position%2 == 0), then inflate chat_row_incoming, else inflate
            // chat_row_outgoing

            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            //From the resulting view, get the TextView which holds the string message

            TextView message = (TextView) result.findViewById(R.id.chatText336);
            //message.setText(   getItem(position)  ); // get the string at position
            message.setText(getItem(position));
            return result;
        }

        // This is the database id of the item at position. For now, we aren’t using SQL, so just return the number: position.
        public long getId(int position) {
            return position;
        }
    }
}
