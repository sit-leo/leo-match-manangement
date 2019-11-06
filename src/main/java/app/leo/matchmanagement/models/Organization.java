package app.leo.matchmanagement.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Organization {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String description;
    private long organizationProfileId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getOrganizationProfileId() {
        return organizationProfileId;
    }

    public void setOrganizationProfileId(long organizationProfileId) {
        this.organizationProfileId = organizationProfileId;
    }
}
