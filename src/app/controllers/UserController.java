package app.controllers;

import app.services.*;
import app.models.User;

public class UserController {

    UserService s=new UserService();

    public void signup(String u,String p){
        s.signup(u,p);
    }

    public User login(String u,String p){
        return s.login(u,p);
    }
}