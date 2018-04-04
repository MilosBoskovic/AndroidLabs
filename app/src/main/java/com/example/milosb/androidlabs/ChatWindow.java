package com.example.milosb.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    protected static final String ACTIVITY_NAME = "ChatWindow";

    ListView chatList;
    EditText chatText;
    Button sendButton;
    boolean tabletUse;

    ArrayList<String> chatHistory = new ArrayList<>();

    private static SQLiteDatabase chatDb;
    Cursor cursor;

    ChatAdapter messageAdapter; //= new ChatAdapter( this );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        if(findViewById(R.id.frame_layout) != null) tabletUse = true;
        else tabletUse = false;

        chatList = (ListView) findViewById(R.id.chatList);
        chatText = (EditText) findViewById(R.id.chatText336);
        sendButton = (Button) findViewById(R.id.send_button);



        //in this case, “this” is the ChatWindow, which is-A Context object
        //final ChatAdapter
        messageAdapter = new ChatAdapter( this );
        chatList.setAdapter(messageAdapter);

        ChatDatabaseHelper opener = new ChatDatabaseHelper(this);
        chatDb = opener.getWritableDatabase();
        final ContentValues cValues = new ContentValues();

        cursor = chatDb.query(ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                chatHistory.add(message);
                Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + message);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }


        Log.i(ACTIVITY_NAME, "Cursor's column count=" + cursor.getColumnCount());
        for (int i=0; i<cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, cursor.getColumnName(i));
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                chatHistory.add(chatText.getText().toString());
                cValues.put(ChatDatabaseHelper.KEY_MESSAGE, chatText.getText().toString());
                chatDb.insert(ChatDatabaseHelper.TABLE_NAME, null, cValues);


                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()

                chatText.setText("");
            }
        });


        final FragmentManager fm = getFragmentManager();

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("MESSAGE", messageAdapter.getItem(position));
                bundle.putString("ID", String.valueOf(id));

                cursor.moveToPosition((int)id);

                if(tabletUse) { // its a tablet


                    MessageFragment mf = new MessageFragment();

                    mf.setArguments(bundle);
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.addToBackStack(" nothing");
                    ft.replace(R.id.frame_layout, mf);
                    ft.commit();


                } else { // its a phone

                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    //startActivity(intent);
                    intent.putExtra("MESSAGE", messageAdapter.getItem(position));
                    intent.putExtra("ID", String.valueOf(id));
                    startActivityForResult(intent, 898, bundle);
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        chatDb.close();
    }

    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(ChatWindow.this, StartActivity.class));

        return;
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

            TextView message = (TextView) result.findViewById(R.id.message_text);

            message.setText(getItem(position));
            return result;
        }

        // This is the database id of the item at position. For now, we aren’t using SQL, so just return the number: position.
        public long getItemId(int position) {

            cursor.moveToPosition(position);
            int rowID = cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID);

            if(cursor.isAfterLast()) {
                cursor.moveToLast();
            }
            return cursor.getLong(rowID);
        }
    }
}
