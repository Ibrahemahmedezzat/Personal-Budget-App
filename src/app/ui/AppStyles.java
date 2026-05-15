package app.ui;

/**
 * Central place for all CSS styles used across the application.
 */
public class AppStyles {

    // ── Colour palette ──────────────────────────────────────────────────────
    public static final String PRIMARY       = "#6C63FF";   // indigo-violet
    public static final String PRIMARY_DARK  = "#4B44CC";
    public static final String PRIMARY_LIGHT = "#EAE9FF";
    public static final String ACCENT        = "#FF6584";   // rose
    public static final String SUCCESS       = "#43D99E";   // mint
    public static final String WARNING       = "#FFB347";   // amber
    public static final String DANGER        = "#FF5757";   // red
    public static final String BG            = "#F4F6FB";   // very light grey-blue
    public static final String CARD          = "#FFFFFF";
    public static final String TEXT_DARK     = "#1E1E2F";
    public static final String TEXT_MUTED    = "#8E8EA0";
    public static final String BORDER        = "#E2E5F0";

    // ── Reusable style strings ───────────────────────────────────────────────

    public static final String SCENE_BG =
        "-fx-background-color: " + BG + ";";

    public static final String CARD_BOX =
        "-fx-background-color: " + CARD + ";" +
        "-fx-background-radius: 16;" +
        "-fx-effect: dropshadow(gaussian, rgba(108,99,255,0.10), 12, 0, 0, 4);";

    public static final String CARD_BOX_HOVER =
        "-fx-background-color: " + CARD + ";" +
        "-fx-background-radius: 16;" +
        "-fx-effect: dropshadow(gaussian, rgba(108,99,255,0.22), 18, 0, 0, 6);";

    public static final String BTN_PRIMARY =
        "-fx-background-color: " + PRIMARY + ";" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 10;" +
        "-fx-padding: 10 28 10 28;" +
        "-fx-cursor: hand;";

    public static final String BTN_PRIMARY_HOVER =
        "-fx-background-color: " + PRIMARY_DARK + ";" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 10;" +
        "-fx-padding: 10 28 10 28;" +
        "-fx-cursor: hand;";

    public static final String BTN_OUTLINE =
        "-fx-background-color: transparent;" +
        "-fx-border-color: " + PRIMARY + ";" +
        "-fx-border-radius: 10;" +
        "-fx-background-radius: 10;" +
        "-fx-text-fill: " + PRIMARY + ";" +
        "-fx-font-size: 13px;" +
        "-fx-font-weight: bold;" +
        "-fx-padding: 9 22 9 22;" +
        "-fx-cursor: hand;";

    public static final String BTN_DANGER =
        "-fx-background-color: " + DANGER + ";" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 13px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 10;" +
        "-fx-padding: 9 22 9 22;" +
        "-fx-cursor: hand;";

    public static final String BTN_SUCCESS =
        "-fx-background-color: " + SUCCESS + ";" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 13px;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 10;" +
        "-fx-padding: 9 22 9 22;" +
        "-fx-cursor: hand;";

    public static final String INPUT_FIELD =
        "-fx-background-color: " + BG + ";" +
        "-fx-border-color: " + BORDER + ";" +
        "-fx-border-radius: 10;" +
        "-fx-background-radius: 10;" +
        "-fx-padding: 10 14 10 14;" +
        "-fx-font-size: 14px;" +
        "-fx-text-fill: " + TEXT_DARK + ";";

    public static final String INPUT_FIELD_FOCUSED =
        "-fx-background-color: white;" +
        "-fx-border-color: " + PRIMARY + ";" +
        "-fx-border-radius: 10;" +
        "-fx-background-radius: 10;" +
        "-fx-padding: 10 14 10 14;" +
        "-fx-font-size: 14px;" +
        "-fx-text-fill: " + TEXT_DARK + ";";

    public static final String LABEL_TITLE =
        "-fx-font-size: 22px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: " + TEXT_DARK + ";";

    public static final String LABEL_SUBTITLE =
        "-fx-font-size: 14px;" +
        "-fx-text-fill: " + TEXT_MUTED + ";";

    public static final String LABEL_SECTION =
        "-fx-font-size: 16px;" +
        "-fx-font-weight: bold;" +
        "-fx-text-fill: " + TEXT_DARK + ";";

    public static final String SIDEBAR =
        "-fx-background-color: " + TEXT_DARK + ";" +
        "-fx-min-width: 210px;" +
        "-fx-pref-width: 210px;";

    public static final String SIDEBAR_BTN =
        "-fx-background-color: transparent;" +
        "-fx-text-fill: #B0B0C8;" +
        "-fx-font-size: 14px;" +
        "-fx-alignment: center-left;" +
        "-fx-padding: 12 20 12 20;" +
        "-fx-cursor: hand;" +
        "-fx-background-radius: 10;" +
        "-fx-min-width: 170px;";

    public static final String SIDEBAR_BTN_ACTIVE =
        "-fx-background-color: " + PRIMARY + ";" +
        "-fx-text-fill: white;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-alignment: center-left;" +
        "-fx-padding: 12 20 12 20;" +
        "-fx-cursor: hand;" +
        "-fx-background-radius: 10;" +
        "-fx-min-width: 170px;";

    // ── Stat-card colours ────────────────────────────────────────────────────
    public static final String STAT_PURPLE =
        "-fx-background-color: " + PRIMARY + ";" +
        "-fx-background-radius: 16;";

    public static final String STAT_GREEN =
        "-fx-background-color: " + SUCCESS + ";" +
        "-fx-background-radius: 16;";

    public static final String STAT_RED =
        "-fx-background-color: " + DANGER + ";" +
        "-fx-background-radius: 16;";

    public static final String STAT_ORANGE =
        "-fx-background-color: " + WARNING + ";" +
        "-fx-background-radius: 16;";

    // ── Helper to add hover effect to a button ───────────────────────────────
    public static void addHover(javafx.scene.control.Button btn,
                                String normal, String hover) {
        btn.setStyle(normal);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(normal));
    }
}
