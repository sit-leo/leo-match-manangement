package app.leo.matchmanagement.services;

import app.leo.matchmanagement.adapters.MatchingAdapter;
import app.leo.matchmanagement.dto.User;
import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Match> findAll(Pageable pagenable){
        return  matchRepository.findAll(pagenable);
    }

    public Page<Match> getLastChanceMatches(Pageable pageable){
        return matchRepository.getLastChanceMatches(pageable);
    }

    public Match updateNumberOfUser(User user,Match match){
        if(user.getRole().equals("recruiter")){
            int newNumberOfRecruiter = match.getNumOfRecruiter()+1;
            match.setNumOfRecruiter(newNumberOfRecruiter);
        }else if(user.getRole().equals("applicant")){
            int newNumberOfApplicant = match.getNumOfApplicant()+1;
            match.setNumOfApplicant(newNumberOfApplicant);
        }
        match.setPopularity(match.getNumOfApplicant()+match.getNumOfRecruiter());
        return matchRepository.save(match);

    }
}
