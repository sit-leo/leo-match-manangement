package app.leo.matchmanagement.repositories;

import app.leo.matchmanagement.models.OrganizationRecruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRecruiterRepository extends JpaRepository<OrganizationRecruiter,Long> {

    List<OrganizationRecruiter> findDistinctByRecruiterProfileIdIn(long recruiterId);

    OrganizationRecruiter findByOrganizationId(long organizationId);
}
