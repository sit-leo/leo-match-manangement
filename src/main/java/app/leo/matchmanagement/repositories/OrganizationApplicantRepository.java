package app.leo.matchmanagement.repositories;

import app.leo.matchmanagement.models.OrganizationApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationApplicantRepository extends JpaRepository<OrganizationApplicant,Long> {

    List<OrganizationApplicant> findDistinctByApplicantProfileIdListIn(long applicantId);

    OrganizationApplicant findByOrganizationId(long organizationId);
}
