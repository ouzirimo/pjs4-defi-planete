package model;

public class UnDone implements ChallengeStatus {

    @Override
    public void setDone(ChallengePivot challenge) {
        challenge.setStatus(new InProgress());
    }

    @Override
    public void setUndone(ChallengePivot challenge) {

    }

    @Override
    public boolean isDone() {
        return false;
    }

}
