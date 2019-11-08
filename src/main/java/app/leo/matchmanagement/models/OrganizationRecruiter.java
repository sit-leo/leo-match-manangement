package app.leo.matchmanagement.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class OrganizationRecruiter {

    @Id
    @GeneratedValue
    private long id;
    @ElementCollection
    private List<Long> recruiterProfileIdList;

    @OneToOne
    private Organization organization   ;

    public OrganizationRecruiter() {
    }

    public OrganizationRecruiter(Organization organization,List<Long> recruiterProfileIdList) {
        this.recruiterProfileIdList = recruiterProfileIdList;
        this.organization = organization;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getRecruiterProfileIdList() {
        return recruiterProfileIdList;
    }

    public void setRecruiterProfileIdList(List<Long> recruiterProfileIdList) {
        this.recruiterProfileIdList = recruiterProfileIdList;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
