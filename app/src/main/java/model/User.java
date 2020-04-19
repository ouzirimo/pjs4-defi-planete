package model;

public class User {

    private String user_name;
    private int user_mdp;

    public User(String n, int mdp){

        this.user_name = n;
        this.user_mdp = mdp;
    }

    public String getUser_name(){

        return user_name;
    }

    public int getUser_mdp() {
        return user_mdp;
    }
}
