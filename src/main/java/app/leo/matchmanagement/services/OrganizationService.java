package app.leo.matchmanagement.services;

import app.leo.matchmanagement.exceptions.WrongRoleException;
import app.leo.matchmanagement.models.Organization;
import app.leo.matchmanagement.models.OrganizationApplicant;
import app.leo.matchmanagement.models.OrganizationRecruiter;
import app.leo.matchmanagement.repositories.OrganizationApplicantRepository;
import app.leo.matchmanagement.repositories.OrganizationRecruiterRepository;
import app.leo.matchmanagement.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationApplicantRepository organizationApplicantRepository;

    @Autowired
    private OrganizationRecruiterRepository organizationRecruiterRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Long> getOrganizationIdListByApplicantId(long applicantId){
        List<OrganizationApplicant> organizations = organizationApplicantRepository.findDistinctByApplicantProfileIdListIn(applicantId);
        List<Long> result = new ArrayList<>();
        for(OrganizationApplicant organizationApplicant:organizations){
            result.add(organizationApplicant.getOrganization().getId());
        }
        return result;
    }

    public List<Long> getOrganizationIdListByRecruiterId(long recruiterId){
        List<OrganizationRecruiter> organizations = organizationRecruiterRepository.findDistinctByRecruiterProfileIdIn(recruiterId);
        List<Long> result = new ArrayList<>();
        for(OrganizationRecruiter organizationRecruiter:organizations){
            result.add(organizationRecruiter.getOrganization().getId());
        }
        return result;
    }

    public List<Long> getApplicantIdListByOrganizationId(long organizationProfileId){
        Organization organization = organizationRepository.findByOrganizeProfileId(organizationProfileId);
        OrganizationApplicant organizationApplicant = organizationApplicantRepository.findByOrganizationId(organization.getId());
        return organizationApplicant.getApplicantProfileIdList();
    }

    public List<Long> getRecruiterIdListByOrganizationId(long organizationProfileId){
        Organization organization = organizationRepository.findByOrganizeProfileId(organizationProfileId);
        OrganizationRecruiter organizationRecruiter = organizationRecruiterRepository.findByOrganizationId(organization.getId());
        return organizationRecruiter.getRecruiterProfileId();
    }
}
