package app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // لإدارة نتيجة الـ Dialog
import javafx.scene.control.TextInputDialog; // لإظهار نافذة الإدخال

import app.data.FileDatabase;
import app.models.User;

public class UserService {

    public void signup(String u, String p) {
        int id = (int) (Math.random() * 10000);
        FileDatabase.save("users.txt", id + "," + u + "," + p + ",0");
        System.out.println("Signup Done ✅");
    }

    public User login(String u, String p) {
        for (String[] d : FileDatabase.read("users.txt")) {
            if (d[1].equals(u) && d[2].equals(p)) {
                
                if (Double.parseDouble(d[3]) == 0) {
                    
                    TextInputDialog dialog = new TextInputDialog("0");
                    dialog.setTitle("Salary Configuration");
                    dialog.setHeaderText("Welcome, " + u + "!");
                    dialog.setContentText("Please enter your monthly salary:");

                    Optional<String> result = dialog.showAndWait();
                    double s = 0;
                    
                    if (result.isPresent()) {
                        try {
                            s = Double.parseDouble(result.get());
                        } catch (NumberFormatException e) {
                            s = 0; 
                        }
                    }

                    FileDatabase.save("users.txt", d[0] + "," + d[1] + "," + d[2] + "," + s);
                    return new User(Integer.parseInt(d[0]), d[1], d[2], s);
                }

                return new User(
                        Integer.parseInt(d[0]),
                        d[1],
                        d[2],
                        Double.parseDouble(d[3]));
            }
        }
        return null;
    }

    public void update(int userId, String cat, double amount) {
        List<String> lines = new ArrayList<>();
        for (String[] b : FileDatabase.read("budgets.txt")) {
            if (Integer.parseInt(b[0]) == userId && b[1].equals(cat)) {
                double limit = Double.parseDouble(b[2]);
                double spent = Double.parseDouble(b[3]) + amount;
                double percent = (spent / limit) * 100;

                if (percent >= 100) {
                    System.out.println("🚨 OVER BUDGET!");
                } else if (percent >= 80) {
                    System.out.println("⚠️ WARNING: Near Limit");
                }
                lines.add(userId + "," + cat + "," + limit + "," + spent);
            } else {
                lines.add(String.join(",", b));
            }
        }
        FileDatabase.overwrite("budgets.txt", lines);
    }
}