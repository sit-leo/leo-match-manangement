package app.leo.matchmanagement.dto;

public class UserMatch  {
    private long id;
    private long matchId;

    public UserMatch() {
    }

    public UserMatch(long id, long matchId) {
        this.id = id;
        this.matchId = matchId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }
}
