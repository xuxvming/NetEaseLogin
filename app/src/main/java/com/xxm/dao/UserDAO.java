package com.xxm.dao;

import java.util.Map;

public interface UserDAO {

    /**
     * create a new record in the database
     * username is supposed to be unique but not treated as a primary key*/
    boolean create(String userName, String password);

    /**
     * get user from the database */
    Map<String, Object> getUser(String userName, String password);

    void updateFailedAttempts(String username, String password, boolean reset);

    void updateLastFailed(String time, String username, String password);

    void updateLastLogin(String time, String username, String password);
}
