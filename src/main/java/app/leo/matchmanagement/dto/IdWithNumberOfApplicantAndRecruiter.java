package app.leo.matchmanagement.dto;

public class IdWithNumberOfApplicantAndRecruiter {

    private long id;
    private long numberOfApplicants;
    private long numberOfRecruiters;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumberOfApplicants() {
        return numberOfApplicants;
    }

    public void setNumberOfApplicants(long numberOfApplicants) {
        this.numberOfApplicants = numberOfApplicants;
    }

    public long getNumberOfRecruiters() {
        return numberOfRecruiters;
    }

    public void setNumberOfRecruiters(long numberOfRecruiters) {
        this.numberOfRecruiters = numberOfRecruiters;
    }
}
