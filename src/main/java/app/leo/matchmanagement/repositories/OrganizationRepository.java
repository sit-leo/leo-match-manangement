package app.leo.matchmanagement.repositories;

import app.leo.matchmanagement.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    Organization findByOrganizationProfileId(long profileId);
}
