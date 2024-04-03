package com.example.restaurantApp.controller;

import com.example.restaurantApp.model.User;

public class LoginController {
    private static boolean loggedIn = false;
    private static boolean loggedInAsAdmin = false;

    private static User loggedAs= new User(0,null,null,null,null);

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public boolean isLoggedInAsAdmin() {
        return loggedInAsAdmin;
    }

    public void setLoggedIn(boolean loggedIn) {
        LoginController.loggedIn = loggedIn;
    }

    public void setLoggedInAsAdmin(boolean loggedInAsAdmin) {
        LoginController.loggedInAsAdmin = loggedInAsAdmin;
    }

    public  User getLoggedAs() {
        return loggedAs;
    }

    public void setLoggedAs(User loggedAs) {
        LoginController.loggedAs = loggedAs;
    }
}


