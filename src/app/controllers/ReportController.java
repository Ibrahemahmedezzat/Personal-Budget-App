package app.controllers;

import app.services.ReportService;

public class ReportController {

    ReportService s = new ReportService();

    public void show(int userId) {
        s.show(userId);
    }

    // 🔥 future upgrade (charts / analytics)
    public void getSummary(int userId) {
        s.summary(userId);
    }
}