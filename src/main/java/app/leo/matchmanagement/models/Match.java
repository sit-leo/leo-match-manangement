package app.leo.matchmanagement.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "matches")
public class Match implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private Date startDate;

    private Date applicantRankingEndDate;

    private Date recruiterRankingEndDate;

    private Date summaryRankingEndDate;

    public Match() {
    }

    public Match(@NotNull String name) {
        this.name = name;
    }

    public Match(@NotNull String name, Date startDate, Date applicantRankingEndDate, Date recruiterRankingEndDate, Date summaryRankingEndDate) {
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
}
