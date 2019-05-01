package app.leo.matchmanagement.dto;

public class RecruiterMatch extends UserMatch {
    private long recruiterId;

    public long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(long recruiterId) {
        this.recruiterId = recruiterId;
    }
}
