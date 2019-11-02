package app.leo.matchmanagement.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.models.Organization;

@Repository
public interface MatchRepository extends JpaRepository<Match,Long> {

    Match getMatchById(long id);

    @Query(value = "select * from matches m where m.start_joining_date <= ?1 and m.announce_date >= ?1 and m.id in ?2 ",nativeQuery = true)
    List<Match> getMatchesByStartJoiningDateBeforeAndAnnouceDateAfterAndIdIn(Date currentDate, Collection<Long> matchIdList);

    @Query(value = "select * from matches m where m.announce_date <= ?1 and m.id in ?2",nativeQuery = true)
    List<Match> getMatchesByAnnounceDateEndDateAfterAndIdIn(Date currentDate,Collection<Long> matchIdList);

    @Override
    <S extends Match> Page<S> findAll(Example<S> example, Pageable pageable);

    @Query(value = "SELECT * FROM matches m where m.end_joining_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY) AND m.organization_id in (:idList)/*#pageable*/"
            ,countQuery = "SELECT COUNT(m.id) FROM matches m where m.end_joining_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)"
            ,nativeQuery = true)
    Page<Match> getLastChanceMatches(Pageable pageable,List<Long> idList);

    Page<Match> findAllByOrganizationIdIn(List<Long> organizationIdList, Pageable pageable);

    Match findTopByOrganizationOrderByIdDesc(Organization organization);
}
