package model;

/**
 * Backpack class link challenge object to User object
 */
public class ChallengePivot {
    private Challenge challenge;
    private ChallengeStatus status;
    private boolean done;

    public ChallengePivot(Challenge challenge , ChallengeStatus status) {
        this.challenge = challenge;
        this.status = status;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    /*status methods */
    public void setDone(){
        this.status.setDone(this);
    }
    public void setUndone(){
        this.status.setUndone(this);
    }
    public void setStatus(ChallengeStatus state){
        this.status = state;
    }
    public boolean isDone() {
        return this.status.isDone();
    }


}
