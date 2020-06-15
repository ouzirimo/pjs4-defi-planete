package model;

public class Done implements ChallengeStatus {
    @Override
    public void setDone(ChallengePivot challenge) {

    }

    @Override
    public void setUndone(ChallengePivot challenge) {

    }

    @Override
    public boolean isDone() {
        return true;
    }
}
