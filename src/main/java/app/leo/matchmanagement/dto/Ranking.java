package app.leo.matchmanagement.dto;

public class Ranking {
    private long id;
    private int sequence;
    private long matchId;

    public Ranking() {
    }

    public Ranking(long id, int sequence, long matchId) {
        this.id = id;
        this.sequence = sequence;
        this.matchId = matchId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }
}
