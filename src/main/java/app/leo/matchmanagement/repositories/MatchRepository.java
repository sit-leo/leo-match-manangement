package app.leo.matchmanagement.repositories;

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
    List<Match> getMatchesByStartJoiningDateBeforeAndAnnouceDateAfterAndIdIn(String currentDate, List<Long> matchIdList);

    @Query(value = "select * from matches m where m.announce_date <= ?1 and m.id in ?2",nativeQuery = true)
    List<Match> getMatchesByAnnounceDateEndDateAfterAndIdIn(String currentDate, List<Long> matchIdList);

    @Override
    <S extends Match> Page<S> findAll(Example<S> example, Pageable pageable);

    @Query(value = "SELECT * FROM matches m where m.end_joining_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY) AND m.organization_id in (:idList)/*#pageable*/"
            ,countQuery = "SELECT COUNT(m.id) FROM matches m where m.end_joining_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY) AND m.organization_id in (:idList)"
            ,nativeQuery = true)
    Page<Match> getLastChanceMatches(Pageable pageable,List<Long> idList);


    @Query(value= "SELECT * FROM matches m where NOW() >= m.start_joining_date AND NOW() <= m.end_joining_date AND m.organization_id in (:idOrgList)/*#pageable*/ "
            ,countQuery = "SELECT COUNT(m.id) FROM matches m where NOW() >= m.start_joining_date AND NOW() <= m.end_joining_date AND m.organization_id in (:idOrgList)"
            ,nativeQuery = true)
    Page<Match> findAllByOrganizationIdIn(Pageable pageable,List<Long> idOrgList);

    Match findTopByOrganizationOrderByIdDesc(Organization organization);

    @Query(value = "select * from matches m where m.announce_date >= NOW() and m.organization_id = ?1 ",nativeQuery = true)
    List<Match> findByStartJoiningDateBeforeAndAnnouceDateAfterAndOrganizationId(Long profileId);

    @Query(value = "select * from matches m where m.announce_date < NOW() and m.organization_id = ?1",nativeQuery = true)
    List<Match> findEndedMatchesByAnnounceDateEndDateAfterAndOrganizationId(Long profileId);

	  long countByOrganizationId(long id);
}
