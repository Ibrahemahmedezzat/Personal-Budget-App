package app.models;

public class User {
    public int id;
    public String name;
    public String pass;
    public double salary;

    public User(int id,String n,String p,double s){
        this.id=id;
        this.name=n;
        this.pass=p;
        this.salary=s;
    }
}