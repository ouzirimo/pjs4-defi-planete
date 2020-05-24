package model;

import java.util.LinkedList;
import java.util.Random;

public class User {
    private static final int VERSION = 1;
    private String login;
    private String email;
    private String password;
    private long level;
    private LinkedList <challengePivot> backpack;

    //j'aurais crée directement une classe backpack avec une liste de challengesDone et la liste des 4 à réaliser

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.backpack = new LinkedList<>();
    }

    public String getLogin() {
        return login;
    }

    /**
     * add a challenge to user's backpack
     * @param challenge
     */
    public void addChallenge(Challenge challenge){
        challengePivot object = new challengePivot(challenge);
        this.backpack.add(object);
    }

    /**
     *  set a challenge done
     * @param challenge
     */
    public void challengeDone(Challenge challenge){
        challengePivot bag = searchBackpack(challenge);
        if(bag != null) {
            bag.setDone(true);
        }
    }

    /**
     * search the challenge in the list of backpack
     * @param challenge
     * @return backpack linked to challenge
     */
    public challengePivot searchBackpack(Challenge challenge){
        for(challengePivot b : backpack){
            if (b.getChallenge().equals(challenge)){
                return b;
            }
        }
        return null;
    }

    /**
     * generate random challenge when one challenge is done or if it's a new user
     * verifiy with a condition before to call this method
     * @author dedeyyy
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
