package app.leo.matchmanagement.repositories;

import app.leo.matchmanagement.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match,Long> {

    Match getMatchById(long id);

    List<Match> getMatchesByStartJoiningDateBeforeAndApplicantRankingEndDateAfterAndIdIn(Date currentDateBeforeStartDate, Date currentDateAfterApplicantRankingEndDate, Collection<Long> matchIdList);

    List<Match> getMatchesByApplicantRankingEndDateBeforeAndIdIn(Date currentDate,Collection<Long> matchIdList);
}
