package app.leo.matchmanagement.dto;

import java.util.List;

public class ApplicantMatch extends UserMatch{

    private long applicantId;
    private List<ApplicantRanking> applicantRankingList;


    public ApplicantMatch() {
    }

    public ApplicantMatch(long id, long matchId, long applicantId, List<ApplicantRanking> applicantRankingList) {
        super(id,matchId);
        this.applicantId = applicantId;
        this.applicantRankingList = applicantRankingList;
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
