package app.leo.matchmanagement.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class OrganizationRecruiter {

    @Id
    @GeneratedValue
    private long id;
    @ElementCollection
    private List<Long> recruiterProfileId;

    @OneToOne
    private Organization organization   ;

    public OrganizationRecruiter() {
    }

    public OrganizationRecruiter(Organization organization,List<Long> recruiterProfileId) {
        this.recruiterProfileId = recruiterProfileId;
        this.organization = organization;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getRecruiterProfileId() {
        return recruiterProfileId;
    }

    public void setRecruiterProfileId(List<Long> recruiterProfileId) {
        this.recruiterProfileId = recruiterProfileId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
