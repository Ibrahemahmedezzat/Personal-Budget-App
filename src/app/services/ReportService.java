package app.services;

import app.data.FileDatabase;
import java.util.*;

public class ReportService {

    public void show(int userId){

        System.out.println("\n📊 ===== REPORT =====");

        double total = 0;

        Map<String, Double> category = new HashMap<>();

        for(String[] t : FileDatabase.read("transactions.txt")){

            if(Integer.parseInt(t[0]) == userId){

                double amount = Double.parseDouble(t[1]);
                String cat = t[2];

                total += amount;

                category.put(cat,
                    category.getOrDefault(cat,0.0) + amount);
            }
        }

        System.out.println("💰 Total Spending: " + total);

        System.out.println("\n📂 By Category:");
        for(String c : category.keySet()){
            System.out.println(c + " → " + category.get(c));
        }

        System.out.println("====================\n");
    }
}