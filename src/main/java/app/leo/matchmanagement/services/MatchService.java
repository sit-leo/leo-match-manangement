package app.leo.matchmanagement.services;

import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public Match getMatchByMatchId(long id) {
        return this.matchRepository.getMatchById(id);
    }
}
