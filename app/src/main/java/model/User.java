package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class User {
    private static final int VERSION = 1;
    private String login;
    private String email;
    private long level;
    private HashMap<Integer, ChallengePivot> backpack;

    //j'aurais crée directement une classe backpack avec une liste de challengesDone et la liste des 4 à réaliser

    public User(String login, String email) {
        this.login = login;
        this.email = email;
        this.backpack = new HashMap<>();
    }

    public String getLogin() {
        return login;
    }

    /**
     * add a challenge to user's backpack
     * @param challenge
     */
   /* public void addChallenge(Challenge challenge){
        ChallengePivot object = new ChallengePivot(challenge); //create link between User and Challenge
        this.backpack.put(challenge.getId_challenge(),object); //add this link to User's list
    } */

    /**
     *  set a challenge done
     * @param challenge
     */
    /*public void challengeDone(Challenge challenge){
        ChallengePivot bag = backpack.get(challenge.getId_challenge());
        if(bag != null) {
            bag.setDone(true);
        }
    } */



    /**
     * generate random challenge when one challenge is done or if it's a new user
     * verifiy with a condition before to call this method
     */
    public void generateRandomChallenge(int nbMax){


        int idRandomChallenge = new Random().nextInt(nbMax + 1);
        /*
            Récup ensuite le challenge à partir de son id qui sera égal au nb tiré au hasard
         */
        //vérifier s'il n'existe pas déjà dans la backpack du user sinon recommancer (pas besoins à l'insciption d'un nouveau user)
        //seulement besoin lorsque il y a déjà eu des chaenge avant soit quand c pour remplacer un challenge abandonné ou réussit

    }
}
