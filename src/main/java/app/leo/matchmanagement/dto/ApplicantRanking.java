package app.leo.matchmanagement.dto;

public class ApplicantRanking extends Ranking{
    private ApplicantMatch applicantMatch;
    private Position position;

    public ApplicantRanking() {
    }

    public ApplicantRanking(long id, int sequence, long matchId, ApplicantMatch applicantMatch, Position position) {
        super(id, sequence, matchId);
        this.applicantMatch = applicantMatch;
        this.position = position;
    }

    public ApplicantMatch getApplicantMatch() {
        return applicantMatch;
    }

    public void setApplicantMatch(ApplicantMatch applicantMatch) {
        this.applicantMatch = applicantMatch;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
