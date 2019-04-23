package app.leo.matchmanagement.dto;

public class RecruiterRanking extends Ranking {

    private Position position;

    private ApplicantMatch applicantMatch;

    public RecruiterRanking() {
    }

    public RecruiterRanking(long id, int sequence, long matchId, Position position, ApplicantMatch applicantMatch) {
        super(id, sequence, matchId);
        this.position = position;
        this.applicantMatch = applicantMatch;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public ApplicantMatch getApplicantMatch() {
        return applicantMatch;
    }

    public void setApplicantMatch(ApplicantMatch applicantMatch) {
        this.applicantMatch = applicantMatch;
    }
}
