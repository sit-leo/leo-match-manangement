package app.leo.matchmanagement.services;

import app.leo.matchmanagement.adapters.MatchAdapter;
import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchAdapter matchAdapter;

    public Match getMatchByMatchId(long id) {
        return this.matchRepository.getMatchById(id);
    }

    public List<Match> getCurrentMatchByApplicantId() {
        List<Long> matchId = matchAdapter.getMatchIdByApplicantId();
        Date currentDate = new Date(119,00,02);
        List<Match> matches = matchRepository.getMatchesByStartDateBeforeAndApplicantRankingEndDateAfterAndIdIn(currentDate,currentDate,matchId);
        return matches;
    }

    public List<Match> getEndedMatchByApplicantId(){
        List<Long> matchId = matchAdapter.getMatchIdByApplicantId();
        Date currentDate = new Date(119,00,02);
        List<Match> matches = matchRepository.getMatchesByApplicantRankingEndDateBeforeAndIdIn(currentDate,matchId);
        return matches;
    }

    public List<Match> getCurrentMatchByRecruiterId() {
        List<Long> matchId = matchAdapter.getMatchIdByRecruiterId();
        Date currentDate = new Date(119,00,02);
        List<Match> matches = matchRepository.getMatchesByStartDateBeforeAndApplicantRankingEndDateAfterAndIdIn(currentDate,currentDate,matchId);
        return matches;
    }

    public List<Match> getEndedMatchByRecruiterId(){
        List<Long> matchId = matchAdapter.getMatchIdByRecruiterId();
        Date currentDate = new Date(119,00,02);
        List<Match> matches = matchRepository.getMatchesByApplicantRankingEndDateBeforeAndIdIn(currentDate,matchId);
        return matches;
    }
}
