package app.leo.matchmanagement.dto;

import java.util.Date;

public class MatchDTO {

    private Long id;

    private String name;

    private String description;

    private Date startDate;

    private Date applicantRankingEndDate;

    private Date recruiterRankingEndDate;

    private Date summaryRankingEndDate;

    public MatchDTO() {
    }

    public MatchDTO(Long id, String name, Date startDate, Date applicantRankingEndDate, Date recruiterRankingEndDate, Date summaryRankingEndDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.applicantRankingEndDate = applicantRankingEndDate;
        this.recruiterRankingEndDate = recruiterRankingEndDate;
        this.summaryRankingEndDate = summaryRankingEndDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getApplicantRankingEndDate() {
        return applicantRankingEndDate;
    }

    public void setApplicantRankingEndDate(Date applicantRankingEndDate) {
        this.applicantRankingEndDate = applicantRankingEndDate;
    }

    public Date getRecruiterRankingEndDate() {
        return recruiterRankingEndDate;
    }

    public void setRecruiterRankingEndDate(Date recruiterRankingEndDate) {
        this.recruiterRankingEndDate = recruiterRankingEndDate;
    }

    public Date getSummaryRankingEndDate() {
        return summaryRankingEndDate;
    }

    public void setSummaryRankingEndDate(Date summaryRankingEndDate) {
        this.summaryRankingEndDate = summaryRankingEndDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
