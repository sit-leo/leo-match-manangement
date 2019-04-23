package app.leo.matchmanagement.dto;

import java.util.List;

public class Position {

    private Long id;

    private String name;

    private int capacity;

    private long matchId;

    private List<RecruiterRanking> recruiterRankings;

    private RecruiterMatch recruiterMatch;

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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public List<RecruiterRanking> getRecruiterRankings() {
        return recruiterRankings;
    }

    public void setRecruiterRankings(List<RecruiterRanking> recruiterRankings) {
        this.recruiterRankings = recruiterRankings;
    }

    public RecruiterMatch getRecruiterMatch() {
        return recruiterMatch;
    }

    public void setRecruiterMatch(RecruiterMatch recruiterMatch) {
        this.recruiterMatch = recruiterMatch;
    }
}
