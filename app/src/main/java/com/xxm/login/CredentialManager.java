package com.xxm.login;

import android.content.Context;
import com.xxm.dao.LocalUserDAO;
import com.xxm.dao.UserDAO;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class CredentialManager implements Serializable {

    String userNameErrorMessage;
    String passWordErrorMessage;

    private final UserDAO dao;
    private final String userName;
    private final String password;

    public CredentialManager(String userName, String password, Context context){
        this.userName = userName;
        this.password = password;
        this.dao = new LocalUserDAO(context);

    }

    public boolean isUserNameValid(){
        if (userName.length()>18 || userName.length()<6){
            this.userNameErrorMessage = ErrorMessage.USER_NAME_INVALID_LENGTH.getErrorMessage();
            return false;
        }
        if (!Character.isLetter(userName.charAt(0))){
            this.userNameErrorMessage= ErrorMessage.USER_NAME_INVALID_PREFIX.getErrorMessage();
            return false;
        }
        for (char c : userName.toCharArray()){
            if (!Character.isDigit(c) && !Character.isLetter(c) && c != '_') {
                this.userNameErrorMessage = ErrorMessage.USER_NAME_INVALID_CHARACTER.getErrorMessage();
                return false;
            }
        }
        return true;
    }

    public boolean isPasswordValid(){
        if (password.length()<8 || password.length()>18){
            this.passWordErrorMessage = ErrorMessage.PASS_WORD_INVALID_LENGTH.getErrorMessage();
            return false;
        }
        List<Character> allowedSpecialChars = Arrays.asList('!','@','#','$','&','(',')');
        int [] arr = new int[4];
        for (Character c : password.toCharArray()){
            if (Character.isDigit(c)){
                arr[0] = 1;
            }else if (Character.isUpperCase(c)){
                arr[1] = 1;
            }else if (Character.isLowerCase(c)){
                arr[2] = 1;
            }else if (allowedSpecialChars.contains(c)){
                arr[3] = 1;
            }else{
                this.passWordErrorMessage = ErrorMessage.PASS_WORD_INVALID_TYPE.getErrorMessage();
                return false;
            }
        }

        int sum = 0;
        for (int num : arr){
            sum+=num;
        }
        if (sum <3 ){
            this.passWordErrorMessage = ErrorMessage.PASS_WORD_INVALID_TYPE.getErrorMessage();
            return false;
        }

        if(userName.equals(password)){
            this.passWordErrorMessage = ErrorMessage.PASS_WORD_SAME_AS_USER_NAME.getErrorMessage();
            return false;
        }
        return true;
    }

    public String getUserNameErrorMessage() {
        return userNameErrorMessage;
    }

    public String getPassWordErrorMessage() {
        return passWordErrorMessage;
    }

    String getPassword(){
        return password;
    }

    String getUserName(){
        return userName;
    }

    Map<String, Object> isUserNameRegistered(boolean checkPassword){
        if (checkPassword){
            return dao.getUser(userName,password);
        }else{
            return dao.getUser(userName,null);
        }
    }

    UserDAO getDao() {
        return dao;
    }
}
