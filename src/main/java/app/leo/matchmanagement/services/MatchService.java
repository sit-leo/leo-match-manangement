package app.leo.matchmanagement.services;

import app.leo.matchmanagement.adapters.MatchAdapter;
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
    private MatchAdapter matchAdapter;

    private final Date currentDate = new Date(119, Calendar.JANUARY,2);


    public Match getMatchByMatchId(long id) {
        return this.matchRepository.getMatchById(id);
    }

    public List<Match> getCurrentMatchByApplicantId() {
        List<Long> matchId = matchAdapter.getMatchIdByApplicantId();
        return matchRepository.getMatchesByStartDateBeforeAndApplicantRankingEndDateAfterAndIdIn(currentDate,currentDate,matchId);
    }

    public List<Match> getEndedMatchByApplicantId(){
        List<Long> matchId = matchAdapter.getMatchIdByApplicantId();
        return matchRepository.getMatchesByApplicantRankingEndDateBeforeAndIdIn(currentDate,matchId);
    }

    public List<Match> getCurrentMatchByRecruiterId() {
        List<Long> matchId = matchAdapter.getMatchIdByRecruiterId();
        return matchRepository.getMatchesByStartDateBeforeAndApplicantRankingEndDateAfterAndIdIn(currentDate,currentDate,matchId);
    }

    public List<Match> getEndedMatchByRecruiterId(){
        List<Long> matchId = matchAdapter.getMatchIdByRecruiterId();
        return matchRepository.getMatchesByApplicantRankingEndDateBeforeAndIdIn(currentDate,matchId);
    }
}
