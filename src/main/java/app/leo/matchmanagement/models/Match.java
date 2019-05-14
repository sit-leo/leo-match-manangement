package app.leo.matchmanagement.models;

import javax.persistence.*;
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

    private String description;

    private Date startJoiningDate;

    private Date applicantRankingEndDate;

    private Date recruiterRankingEndDate;

    private Date announceDate;

    public Match() {
    }

    public Match(@NotNull String name) {
        this.name = name;
    }

    public Match(@NotNull String name, String description, Date startJoiningDate, Date applicantRankingEndDate, Date recruiterRankingEndDate, Date announceDate) {
        this.name = name;
        this.description = description;
        this.startJoiningDate = startJoiningDate;
        this.applicantRankingEndDate = applicantRankingEndDate;
        this.recruiterRankingEndDate = recruiterRankingEndDate;
        this.announceDate = announceDate;
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

    public Date getStartJoiningDate() {
        return startJoiningDate;
    }

    public void setStartJoiningDate(Date startJoiningDate) {
        this.startJoiningDate = startJoiningDate;
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

    public Date getAnnounceDate() {
        return announceDate;
    }

    public void setAnnounceDate(Date announceDate) {
        this.announceDate = announceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
