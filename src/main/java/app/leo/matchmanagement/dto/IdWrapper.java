package app.leo.matchmanagement.dto;

import java.util.List;

public class IdWrapper {

    private List<Long> idList;

    public IdWrapper() {
    }

    public IdWrapper(List<Long> idList) {
        this.idList = idList;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
