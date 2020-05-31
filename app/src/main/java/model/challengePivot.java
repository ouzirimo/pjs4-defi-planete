package model;

/**
 * Backpack class link challenge object to User object
 * @author Gaëlle & Noëlle
 * @version 1.0
 */
public class challengePivot {
    private Challenge challenge;
    private boolean done;

    public challengePivot(Challenge challenge) {
        this.challenge = challenge;
    }

    public void setDone(boolean done){
        this.done=done;
    }
    public boolean isDone() {
        return done;
    }
    public Challenge getChallenge() {
        return challenge;
    }

}
