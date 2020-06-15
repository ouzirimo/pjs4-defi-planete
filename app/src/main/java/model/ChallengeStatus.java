package model;

public interface ChallengeStatus {
    public void setDone(ChallengePivot challenge);
    public void setUndone(ChallengePivot challenge);
    public boolean isDone();
}
