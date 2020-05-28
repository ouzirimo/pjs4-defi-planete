package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class User {
    private static final int VERSION = 1;
    private String login;
    private String email;
    private String password;
    private long level;
    private HashMap<Integer,challengePivot> backpack;

    //j'aurais crée directement une classe backpack avec une liste de challengesDone et la liste des 4 à réaliser

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.backpack = new HashMap<>();
    }

    public String getLogin() {
        return login;
    }

    /**
     * add a challenge to user's backpack
     * @param challenge
     * @author Noelle et Gaelle
     */
    public void addChallenge(Challenge challenge){
        challengePivot object = new challengePivot(challenge); //create link between User and Challenge
        this.backpack.put(challenge.getId_challenge(),object); //add this link to User's list
    }

    /**
     *  set a challenge done
     * @param challenge
     * @author Noelle et Gaelle
     */
    public void challengeDone(Challenge challenge){
        challengePivot bag = backpack.get(challenge.getId_challenge());
        if(bag != null) {
            bag.setDone(true);
        }
    }

    /**
     *  return true if challenge already exist in backpack
     * @param idChallenge
     * @author Noelle et Gaelle
     */
    public boolean containsChallenge(int idChallenge){
        return backpack.containsKey(idChallenge);
    }

}
