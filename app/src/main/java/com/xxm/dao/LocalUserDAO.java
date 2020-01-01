package com.xxm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;


public class LocalUserDAO implements UserDAO {

    private LocalDatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase instance;

    public LocalUserDAO(Context c) {
        context = c;
    }

    private void open() throws SQLException {
        dbHelper = new LocalDatabaseHelper(context);
        this.instance = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    @Override
    public boolean create(String userName, String password) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(LocalDatabaseHelper._ID,getId(userName));
        contentValue.put(LocalDatabaseHelper.COL_NAME_USERNAME, userName);
        contentValue.put(LocalDatabaseHelper.COL_NAME_PASSWORD, password);
        contentValue.put(LocalDatabaseHelper.COL_NAME_LAST_FAILED, "unknown");
        contentValue.put(LocalDatabaseHelper.COL_NAME_LAST_LOGIN, "unknown");
        contentValue.put(LocalDatabaseHelper.COL_NAME_FAILED_ATTEMPTS, 0);
        instance.insert(LocalDatabaseHelper.TABLE_NAME, null, contentValue);
        return true;
    }

    @Override
    public Map<String, Object> getUser(String userName, String password) {
        open();
        String query = "SELECT * FROM " + LocalDatabaseHelper.TABLE_NAME + " WHERE " + LocalDatabaseHelper.COL_NAME_USERNAME + " = "+ "'" +userName + "'";
        if (password != null){
            query = "SELECT * FROM " + LocalDatabaseHelper.TABLE_NAME + " WHERE " + LocalDatabaseHelper.COL_NAME_USERNAME + " = "+ "'" +userName + "' AND " + LocalDatabaseHelper.COL_NAME_PASSWORD + " = "+"'" +password + "'";
        }
        Cursor resultSet = instance.rawQuery(query,null);
        HashMap<String,Object> res = new HashMap<>();
        resultSet.moveToFirst();
        while(!resultSet.isAfterLast()){
            res.put(resultSet.getString(1),resultSet.getString(2));
            res.put(LocalDatabaseHelper.COL_NAME_LAST_FAILED,resultSet.getString(resultSet.getColumnIndex(LocalDatabaseHelper.COL_NAME_LAST_FAILED)));
            res.put(LocalDatabaseHelper.COL_NAME_LAST_LOGIN,resultSet.getString(resultSet.getColumnIndex(LocalDatabaseHelper.COL_NAME_LAST_LOGIN)));
            res.put(LocalDatabaseHelper.COL_NAME_FAILED_ATTEMPTS,resultSet.getInt(resultSet.getColumnIndex(LocalDatabaseHelper.COL_NAME_FAILED_ATTEMPTS)));
            resultSet.moveToNext();
        }
        resultSet.close();
        return res;
    }

    @Override
    public void updateFailedAttempts(String username, String password, boolean reset) {
        String query = "UPDATE " + LocalDatabaseHelper.TABLE_NAME +
                " SET " + LocalDatabaseHelper.COL_NAME_FAILED_ATTEMPTS + " = " + LocalDatabaseHelper.COL_NAME_FAILED_ATTEMPTS + "+ 1"+
                " WHERE " + LocalDatabaseHelper.COL_NAME_USERNAME + " = "+ "'" +username +
                "' AND " + LocalDatabaseHelper.COL_NAME_PASSWORD + " = "+"'" +password + "'";

        if (reset){
            query = "UPDATE " + LocalDatabaseHelper.TABLE_NAME +
                    " SET " + LocalDatabaseHelper.COL_NAME_FAILED_ATTEMPTS + "=" + 0 +
                    " WHERE " + LocalDatabaseHelper.COL_NAME_USERNAME + " = "+ "'" +username +
                    "' AND " + LocalDatabaseHelper.COL_NAME_PASSWORD + " = "+"'" +password + "'";

        }
        open();
        instance.execSQL(query);
        close();
    }

    @Override
    public void updateLastFailed(String time, String username, String password) {
        String query = "UPDATE " + LocalDatabaseHelper.TABLE_NAME +
                " SET " + LocalDatabaseHelper.COL_NAME_LAST_FAILED + "=" + "'"+time +"'" +
                " WHERE " + LocalDatabaseHelper.COL_NAME_USERNAME + " = "+ "'" +username +
                "' AND " + LocalDatabaseHelper.COL_NAME_PASSWORD + " = "+"'" +password + "'";

        open();
        instance.execSQL(query);
        close();
    }

    @Override
    public void updateLastLogin(String time, String username, String password) {
        String query = "UPDATE " + LocalDatabaseHelper.TABLE_NAME +
                " SET " + LocalDatabaseHelper.COL_NAME_LAST_LOGIN + "=" + "'"+time +"'"  +
                " WHERE " + LocalDatabaseHelper.COL_NAME_USERNAME + " = "+ "'" +username +
                "' AND " + LocalDatabaseHelper.COL_NAME_PASSWORD + " = "+"'" +password + "'";

        open();
        instance.execSQL(query);
        close();
    }


    private int getId(String username){
        return username.hashCode();
    }
}
