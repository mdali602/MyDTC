package com.example.mdali.mydtc;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "metro_timing_new.sqlite";
    private static String DB_PATH = "/data/com.example.mdali.mydtc/databases/";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 4);
        this.myContext = context;
        Log.d("sandeep", "DataBaseHelper()");
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        Log.d("sandeep", "createDataBase()");
        if (dbExist) {
            Log.d("sandeep", "createDataBase() db Exist");
            return;
        }
        Log.d("sandeep", "createDataBase() db NOT*** Exist");
        getReadableDatabase();
        try {
            copyDataBase();
        } catch (IOException e) {
            throw new Error("Error copying database");
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 1);
        } catch (SQLiteException e) {
        }
        Log.d("sandeep", "checkDataBase() + CheckDB:" + checkDB);
        if (checkDB != null) {
            checkDB.close();
        }
        if (checkDB != null) {
            return true;
        }
        return false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = this.myContext.getAssets().open(DB_NAME);
        Log.d("sandeep", "copyDataBase()");
        OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[1024];
        while (true) {
            int length = myInput.read(buffer);
            if (length > 0) {
                myOutput.write(buffer, 0, length);
            } else {
                myOutput.flush();
                myOutput.close();
                myInput.close();
                return;
            }
        }
    }

    public void openDataBase() throws SQLException {
        this.myDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 268435472);
    }

    public synchronized void close() {
        if (this.myDataBase != null) {
            this.myDataBase.close();
        }
        super.close();
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("sandeep", "OnUpgrade DB is called : oldVersion " + oldVersion + " newVersion :" + newVersion);
    }

    public void deleteDB() {
        Log.d("sandeep", "deleteDB is called :val " + this.myContext.deleteDatabase(DB_PATH + DB_NAME));
    }
}
