package app.leo.matchmanagement.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class OrganizationApplicant {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private Organization organization;

    @ElementCollection
    private List<Long> applicantProfileIdList;

    public OrganizationApplicant() {
    }

    public OrganizationApplicant(Organization organization, List<Long> applicantProfileIdList) {
        this.organization = organization;
        this.applicantProfileIdList = applicantProfileIdList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getApplicantProfileIdList() {
        return applicantProfileIdList;
    }

    public void setApplicantProfileIdList(List<Long> applicantProfileIdList) {
        this.applicantProfileIdList = applicantProfileIdList;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
