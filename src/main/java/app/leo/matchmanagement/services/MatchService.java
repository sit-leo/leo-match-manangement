package app.leo.matchmanagement.services;

import app.leo.matchmanagement.adapters.MatchingAdapter;
import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchingAdapter matchingAdapter;

    private final Date currentDate = new Date(119, Calendar.JANUARY,2);


    public Match getMatchByMatchId(long id) {
        return this.matchRepository.getMatchById(id);
    }

    public List<Match> getCurrentMatchByApplicantId(String token) {
        List<Long> matchId = matchingAdapter.getMatchIdByApplicantId(token );
        return matchRepository.getMatchesByStartDateBeforeAndApplicantRankingEndDateAfterAndIdIn(currentDate,currentDate,matchId);
    }

    public List<Match> getEndedMatchByApplicantId(String token){
        List<Long> matchId = matchingAdapter.getMatchIdByApplicantId(token );
        return matchRepository.getMatchesByApplicantRankingEndDateBeforeAndIdIn(currentDate,matchId);
    }

    public List<Match> getCurrentMatchByRecruiterId(String token) {
        List<Long> matchId = matchingAdapter.getMatchIdByRecruiterId(token);
        return matchRepository.getMatchesByStartDateBeforeAndApplicantRankingEndDateAfterAndIdIn(currentDate,currentDate,matchId);
    }

    public List<Match> getEndedMatchByRecruiterId(String token){
        List<Long> matchId = matchingAdapter.getMatchIdByRecruiterId(token);
        return matchRepository.getMatchesByApplicantRankingEndDateBeforeAndIdIn(currentDate,matchId);
    }
}
