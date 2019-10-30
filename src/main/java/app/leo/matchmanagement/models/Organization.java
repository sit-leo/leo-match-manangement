package app.leo.matchmanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Organization {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String description;
    private long organizeProfileId;

    @OneToMany
    @JsonIgnore
    private List<Match> matchList;

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

    public long getOrganizeProfileId() {
        return organizeProfileId;
    }

    public void setOrganizeProfileId(long organizeProfileId) {
        this.organizeProfileId = organizeProfileId;
    }

    public List<Match> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
    }
}
