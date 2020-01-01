package com.xxm.login;

import android.content.Context;

import java.util.Map;

public class RegisterManager extends CredentialManager{

    public RegisterManager(String userName, String password, Context context) {
        super(userName, password, context);
    }

    @Override
    public boolean isUserNameValid(){

        if (!super.isUserNameValid()){
            return false;
        }else {
           // Map<String,Object> existingUsers = isUserNameRegistered(true)
            if (isUserNameRegistered(false).size() != 0) {
                userNameErrorMessage = ErrorMessage.USER_NAME_ALREADY_EXISTS.getErrorMessage();
                return false;
            }
            return true;
        }
    }

    public boolean isPasswordMatching(String password){
        if(!isPasswordValid()){
            return false;
        }else{
            if (!password.equals(getPassword())){
                passWordErrorMessage = ErrorMessage.PASS_WORD_NOT_MATCH.getErrorMessage();
                return false;
            }
            return true;
        }
    }

    public boolean register(){

       return getDao().create(getUserName(),getPassword());
    }

}
