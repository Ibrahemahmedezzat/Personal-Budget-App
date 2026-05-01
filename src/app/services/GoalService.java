package app.services;

import app.data.FileDatabase;

public class GoalService {

    public void create(int id,String name,double target){

        FileDatabase.save("goals.txt",
            id+","+name+","+target+",0");

        System.out.println("🎯 Goal Created");
    }

    public void contribute(int id,String name,double amount){

        for(String[] g:FileDatabase.read("goals.txt")){

            if(Integer.parseInt(g[0])==id && g[1].equals(name)){

                double target=Double.parseDouble(g[2]);
                double saved=Double.parseDouble(g[3]);

                saved += amount;

                double percent = (saved/target)*100;

                if(percent >= 100){
                    System.out.println("🎉 Goal Achieved!");
                }else{
                    System.out.println("📈 Progress: " + percent + "%");
                }
            }
        }
    }
}