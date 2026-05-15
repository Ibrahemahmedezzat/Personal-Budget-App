package app.ui;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import app.models.User;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * NavigationService – Single Responsibility: owns ALL navigation logic.
 *
 * Keeps a Back-stack and a Forward-stack so the sidebar buttons,
 * Back-arrow and Forward-arrow all work consistently.
 *
 * SOLID notes
 * -----------
 * S – this class ONLY decides which screen to show and maintains history.
 * O – add new screens by adding a new navigate() call; nothing else changes.
 * D – callers depend on this abstraction, not on concrete screen classes.
 */
public class NavigationService {

    public enum Screen { DASHBOARD, TRANSACTIONS, BUDGETS, GOALS, REPORTS }

    private final StackPane contentArea;
    private final User      user;

    // History stacks for Back / Forward
    private final Deque<Screen> backStack    = new ArrayDeque<>();
    private final Deque<Screen> forwardStack = new ArrayDeque<>();
    private Screen current;

    // Listener so MainScreen can update the active sidebar button
    private NavigationListener listener;

    public interface NavigationListener {
        void onNavigate(Screen screen);
    }

    public NavigationService(StackPane contentArea, User user) {
        this.contentArea = contentArea;
        this.user = user;
    }

    public void setListener(NavigationListener l) { this.listener = l; }

    // ── Public API ────────────────────────────────────────────────────────────

    /** Navigate to a screen, push current to back-stack, clear forward. */
    public void navigate(Screen screen) {
        if (current != null) {
            backStack.push(current);
            forwardStack.clear();
        }
        doShow(screen);
    }

    /** Go back one step. */
    public boolean goBack() {
        if (backStack.isEmpty()) return false;
        forwardStack.push(current);
        doShow(backStack.pop());
        return true;
    }

    /** Go forward one step. */
    public boolean goForward() {
        if (forwardStack.isEmpty()) return false;
        backStack.push(current);
        doShow(forwardStack.pop());
        return true;
    }

    public boolean canGoBack()    { return !backStack.isEmpty(); }
    public boolean canGoForward() { return !forwardStack.isEmpty(); }
    public Screen  getCurrent()   { return current; }

    // ── Internal ──────────────────────────────────────────────────────────────

    private void doShow(Screen screen) {
        current = screen;
        Node node = buildScreen(screen);
        contentArea.getChildren().setAll(node);
        StackPane.setAlignment(node, Pos.TOP_LEFT);
        if (listener != null) listener.onNavigate(screen);
    }

    private Node buildScreen(Screen screen) {
        return switch (screen) {
            case DASHBOARD    -> new DashboardScreen(user).build();
            case TRANSACTIONS -> new TransactionScreen(user).build();
            case BUDGETS      -> new BudgetScreen(user).build();
            case GOALS        -> new GoalScreen(user).build();
            case REPORTS      -> new ReportScreen(user).build();
        };
    }
}
