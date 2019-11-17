package app.leo.matchmanagement.dto;

import java.util.List;

public class OrgIdWrapper {

    private List<IdWithNumberOfApplicantAndRecruiter> idList;

    public OrgIdWrapper(List<IdWithNumberOfApplicantAndRecruiter> idList) {
        this.idList = idList;
    }

    public List<IdWithNumberOfApplicantAndRecruiter> getIdList() {
        return idList;
    }

    public void setIdList(List<IdWithNumberOfApplicantAndRecruiter> idList) {
        this.idList = idList;
    }
}
