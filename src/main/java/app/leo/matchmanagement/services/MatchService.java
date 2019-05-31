package app.leo.matchmanagement.services;

import app.leo.matchmanagement.adapters.MatchingAdapter;
import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchingAdapter matchingAdapter;

    private final Date currentDate =  java.sql.Date.valueOf(LocalDate.now(ZoneId.of("Asia/Bangkok")));

    public Match getMatchByMatchId(long id) {
        return this.matchRepository.getMatchById(id);
    }

    public List<Match> getCurrentMatchByUserId(String token) {
        List<Long> matchId = matchingAdapter.getMatchIdByUserId(token);
        return matchRepository.getMatchesByStartJoiningDateBeforeAndAnnouceDateAfterAndIdIn(currentDate,matchId);
    }

    public List<Match> getEndedMatchByUserId(String token){
        List<Long> matchId = matchingAdapter.getMatchIdByUserId(token);
        return matchRepository.getMatchesByAnnounceDateEndDateAfterAndIdIn(currentDate,matchId);
    }
}
