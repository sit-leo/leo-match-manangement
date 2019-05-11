package app.leo.matchmanagement.dto;

public class UserMatch  {
    private long participantId;
    private long matchId;

    public UserMatch() {
    }

    public UserMatch(long participantId, long matchId) {
        this.participantId = participantId;
        this.matchId = matchId;
    }

    public long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(long participantId) {
        this.participantId = participantId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }
}
