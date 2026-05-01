package app.services;

import app.data.FileDatabase;
import app.models.User;

public class UserService {

    public void signup(String u,String p){

        int id=(int)(Math.random()*10000);

        FileDatabase.save("users.txt",
                id+","+u+","+p+",0");

        System.out.println("Signup Done ✅");
    }

    public User login(String u,String p){

        for(String[] d:FileDatabase.read("users.txt")){

            if(d[1].equals(u) && d[2].equals(p)){

                if(Double.parseDouble(d[3]) == 0){
                    System.out.println("Enter Salary:");
                    double s = new java.util.Scanner(System.in).nextDouble();

                    updateSalary(Integer.parseInt(d[0]), u, p, s);

                    return new User(Integer.parseInt(d[0]),u,p,s);
                }

                return new User(
                        Integer.parseInt(d[0]),
                        d[1],
                        d[2],
                        Double.parseDouble(d[3])
                );
            }
        }
        return null;
    }

    private void updateSalary(int id,String u,String p,double salary){

        FileDatabase.save("users.txt",
                id+","+u+","+p+","+salary);
    }
}