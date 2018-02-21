package com.example.milosb.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Milos B on 2018-02-21.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    protected static final String ACTIVITY_NAME = "ChatDatabaseHelper";

    private static String DATABASE_NAME = "Messages.db";
    private static int VERSION_NUM = 2;
    final static String KEY_ID = "Id";
    final static String KEY_MESSAGE = "Message";
    final static String TABLE_NAME = "ChatHistory";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("create table " + TABLE_NAME + "( " + KEY_ID + " integer primary key autoincrement, " + KEY_MESSAGE + " text);");

        Log.i(ACTIVITY_NAME, "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){  // newVersion > oldVersion

        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);

        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion= " + newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {  // newVersion < oldVersion

        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}
