package com.lishengzn.dto;

import java.io.Serializable;

public class UserDto implements Serializable {
    private static final long serialVersionUID =1L;

    private String accountID;
    private String username;
    private String password;

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
