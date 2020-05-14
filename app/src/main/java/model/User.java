package model;

import java.util.LinkedList;

public class User {
    private static final int VERSION = 1;
    private String login;
    private String email;
    private String password;
    private long level;
    private LinkedList <challengePivot> backpack;

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
}
