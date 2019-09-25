package app.leo.matchmanagement.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
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

    private String pictureUrl;

    private Date startJoiningDate;

    private Date endJoiningDate;

    private Date applicantRankingEndDate;

    private Date recruiterRankingEndDate;

    private Date announceDate;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(columnDefinition = "integer default 0")
    private int numOfRecruiter;

    @Column(columnDefinition = "integer default 0")
    private int numOfApplicant;

    @Column(columnDefinition = "integer default 0")
    private int popularity;

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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Date getEndJoiningDate() {
        return endJoiningDate;
    }

    public void setEndJoiningDate(Date endJoiningDate) {
        this.endJoiningDate = endJoiningDate;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public int getNumOfRecruiter() {
        return numOfRecruiter;
    }

    public void setNumOfRecruiter(int numOfRecruiter) {
        this.numOfRecruiter = numOfRecruiter;
    }

    public int getNumOfApplicant() {
        return numOfApplicant;
    }

    public void setNumOfApplicant(int numOfApplicant) {
        this.numOfApplicant = numOfApplicant;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
