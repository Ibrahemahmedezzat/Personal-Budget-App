package app.ui;

import java.util.*;
import app.controllers.*;
import app.models.User;

public class LoginUI {

    Scanner sc=new Scanner(System.in);
    UserController c=new UserController();

    public void show(){

        while(true){
            System.out.println("\n1-Login\n2-Signup\n3-Exit");

            int ch=Integer.parseInt(sc.nextLine());

            if(ch==1){

                System.out.print("User: ");
                String u=sc.nextLine();

                System.out.print("Pass: ");
                String p=sc.nextLine();

                User user=c.login(u,p);

                if(user!=null){
                    System.out.println("Login Success ✅");
                    new MainMenu(user).show();
                }else System.out.println("Wrong ❌");

            }else if(ch==2){

                System.out.print("User: ");
                String u=sc.nextLine();

                System.out.print("Pass: ");
                String p=sc.nextLine();

                c.signup(u,p);

            }else return;
        }
    }
}