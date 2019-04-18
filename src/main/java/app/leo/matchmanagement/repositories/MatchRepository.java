package app.leo.matchmanagement.repositories;

import app.leo.matchmanagement.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match,Long> {

    Match getMatchById(long id);

}
