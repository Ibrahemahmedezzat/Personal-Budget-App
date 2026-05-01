package app.controllers;

import app.services.ReportService;

public class ReportController {

    ReportService s=new ReportService();

    public void show(int id){
        s.show(id);
    }
}