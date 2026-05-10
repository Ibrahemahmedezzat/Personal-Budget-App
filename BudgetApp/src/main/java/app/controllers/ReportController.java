package app.controllers;

import app.services.ReportService;

/**
 * Controller class responsible for handling report-related requests
 * and delegating them to the ReportService.
 * It acts as a bridge between the presentation layer and reporting logic.
 */
public class ReportController {

    /**
     * Service instance used to generate financial reports.
     */
    private ReportService reportService = new ReportService();

    /**
     * Displays a full financial report for a specific user.
     *
     * @param userId the ID of the user whose report will be displayed
     */
    public void show(int userId) {
        reportService.show(userId);
    }

    /**
     * Retrieves a summary report for a user.
     * This method is intended for future enhancements such as charts and analytics.
     *
     * @param userId the ID of the user
     */
    public void getSummary(int userId) {
        reportService.summary(userId);
    }
}