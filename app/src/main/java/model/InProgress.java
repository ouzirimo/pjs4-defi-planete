package model;

public class InProgress implements ChallengeStatus {
    @Override
    public void setDone(ChallengePivot challenge) {
        challenge.setStatus(new Done());
    }

    @Override
    public void setUndone(ChallengePivot challenge) {
        challenge.setStatus(new UnDone());
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
