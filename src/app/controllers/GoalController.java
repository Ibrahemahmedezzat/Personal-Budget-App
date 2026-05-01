package app.controllers;

import app.services.GoalService;

public class GoalController {

    GoalService s=new GoalService();

    public void create(int id,String name,double target){
        s.create(id,name,target);
    }
}