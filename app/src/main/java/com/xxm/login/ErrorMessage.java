package com.xxm.login;

public enum ErrorMessage {
    USER_NAME_INVALID_LENGTH("用户名长度6-18个字符"),
    USER_NAME_INVALID_PREFIX("用户名需以字母开头"),
    USER_NAME_INVALID_CHARACTER("用户名可使用字母，数字，下划线"),
    USER_NAME_NO_RECORD("该用户不存在 请注册"),
    USER_NAME_ALREADY_EXISTS("用户已存在 请登录"),

    PASS_WORD_INVALID_LENGTH("密码长度8-18个字符"),
    PASS_WORD_INVALID_TYPE("至少包含以下四类字符中的三类：大写字母、小写字母、数字，以及特殊符号仅限!@#$&()"),
    PASS_WORD_SAME_AS_USER_NAME("不得与用户名重复"),
    PASS_WORD_NOT_MATCH("一致性校失败"),
    PASS_WORD_INCORRECT("密码错误");

    private final String errorMessage;

    ErrorMessage(String message){
        this.errorMessage= message;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
