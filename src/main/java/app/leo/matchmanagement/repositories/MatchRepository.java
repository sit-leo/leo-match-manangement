package app.leo.matchmanagement.repositories;

import app.leo.matchmanagement.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match,Long> {

    Match getMatchById(long id);

    @Query(value = "select * from matches m where m.start_joining_date <= ?1 and m.announce_date >= ?1 and m.id in ?2 ",nativeQuery = true)
    List<Match> getMatchesByStartJoiningDateBeforeAndAnnouceDateAfterAndIdIn(Date currentDate, Collection<Long> matchIdList);

    @Query(value = "select * from matches m where m.announce_date <= ?1 and m.id in ?2",nativeQuery = true)
    List<Match> getMatchesByAnnounceDateEndDateAfterAndIdIn(Date currentDate,Collection<Long> matchIdList);
}
