package com.xxm.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.xxm.dao.LocalDatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class LoginManager extends CredentialManager {

    private Map<String, Object> expectedCredentials;

    private String datePattern = "yyyy-MM-dd'T'HH:mm:ss:SSS'Z'";



    public static final String KEY_USERNAME = "username";

    public static final String KEY_PASSWORD = "password";

    private Context context;


    public LoginManager(String userName, String password, Context context) {
        super(userName, password,context);
        this.context = context;
    }

    @Override
    public boolean isUserNameValid(){
        expectedCredentials = isUserNameRegistered(false);
        if(!expectedCredentials.containsKey(getUserName())){
            userNameErrorMessage = ErrorMessage.USER_NAME_NO_RECORD.getErrorMessage();
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean isPasswordValid(){
        for (String key : expectedCredentials.keySet()){
            if (key.equals(getUserName()) && expectedCredentials.get(key).equals(getPassword())){
                return true;
            }
        }
        getDao().updateFailedAttempts(getUserName(), (String) expectedCredentials.get(getUserName()),false);SimpleDateFormat format = new SimpleDateFormat(datePattern);
        getDao().updateLastFailed(format.format(new Date()),getUserName(),(String) expectedCredentials.get(getUserName()));
        passWordErrorMessage = ErrorMessage.PASS_WORD_INCORRECT.getErrorMessage();
        return false;
    }

    public void login(){
        SessionManager.setLoggedIn(context, getUserName(),true);
        SimpleDateFormat format = new SimpleDateFormat(datePattern, Locale.UK);
        getDao().updateFailedAttempts(getUserName(),getPassword(),true);
        getDao().updateLastFailed("",getUserName(),getPassword());
        getDao().updateLastLogin(format.format(new Date()),getUserName(),getPassword());
    }

    public void logout(){
        SimpleDateFormat format = new SimpleDateFormat(datePattern, Locale.UK);
        getDao().updateLastLogin(format.format(new Date()),getUserName(),getPassword());
        SessionManager.setLoggedIn(context, getUserName(),false);
    }

    public boolean isAccountLocked() {
        int numAttempts = (int) expectedCredentials.get(LocalDatabaseHelper.COL_NAME_FAILED_ATTEMPTS);
        if (numAttempts >= 3) {
            String lasFailedTime = (String) expectedCredentials.get(LocalDatabaseHelper.COL_NAME_LAST_FAILED);
            SimpleDateFormat format = new SimpleDateFormat(datePattern, Locale.UK);
            int startHours;
            try {
                Date start = format.parse(lasFailedTime);
                startHours = start.getHours();
            } catch (ParseException e) {
                startHours = 0;
            }
            if (Math.abs(new Date().getHours() - startHours) < 24) {
                return true;
            }
        }
        return false;
    }


    public boolean isLoggedIn(){
        return  SessionManager.getLoggedStatus(context);
    }
}
