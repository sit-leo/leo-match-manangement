package app.leo.matchmanagement.dto;

import java.util.List;

public class ApplicantMatch {
    private long id;
    private long matchId;
    private long applicantId;
    private List<ApplicantRanking> applicantRankingList;


    public ApplicantMatch() {
    }

    public ApplicantMatch(long id, long matchId, long applicantId, List<ApplicantRanking> applicantRankingList) {
        this.id = id;
        this.matchId = matchId;
        this.applicantId = applicantId;
        this.applicantRankingList = applicantRankingList;
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

    public long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(long applicantId) {
        this.applicantId = applicantId;
    }

    public List<ApplicantRanking> getApplicantRankingList() {
        return applicantRankingList;
    }

    public void setApplicantRankingList(List<ApplicantRanking> applicantRankingList) {
        this.applicantRankingList = applicantRankingList;
    }
}
