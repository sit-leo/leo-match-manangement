package app.leo.matchmanagement.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class MatchDTO  {

	private Long id;

	@NotNull
	private String name;

	private String description;

	@NotNull
	private Date startJoiningDate;

	@NotNull
	private Date endJoiningDate;

	@NotNull
	private Date applicantRankingEndDate;

	@NotNull
	private Date recruiterRankingEndDate;

	@NotNull
	private Date announceDate;

	private int numOfRecruiter;

	private int numOfApplicant;

	private int popularity;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartJoiningDate() {
		return startJoiningDate;
	}

	public void setStartJoiningDate(Date startJoiningDate) {
		this.startJoiningDate = startJoiningDate;
	}

	public Date getEndJoiningDate() {
		return endJoiningDate;
	}

	public void setEndJoiningDate(Date endJoiningDate) {
		this.endJoiningDate = endJoiningDate;
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
