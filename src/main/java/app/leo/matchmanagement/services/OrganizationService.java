package app.leo.matchmanagement.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.leo.matchmanagement.dto.IdWithNumberOfApplicantAndRecruiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.models.Organization;
import app.leo.matchmanagement.models.OrganizationApplicant;
import app.leo.matchmanagement.models.OrganizationRecruiter;
import app.leo.matchmanagement.repositories.MatchRepository;
import app.leo.matchmanagement.repositories.OrganizationApplicantRepository;
import app.leo.matchmanagement.repositories.OrganizationRecruiterRepository;
import app.leo.matchmanagement.repositories.OrganizationRepository;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationApplicantRepository organizationApplicantRepository;

    @Autowired
    private OrganizationRecruiterRepository organizationRecruiterRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MatchRepository matchRepository;

    public List<Long> getOrganizationIdListByApplicantId(long applicantId){
        List<OrganizationApplicant> organizations = organizationApplicantRepository.findDistinctByApplicantProfileIdListIn(applicantId);
        List<Long> result = new ArrayList<>();
        for(OrganizationApplicant organizationApplicant:organizations){
            result.add(organizationApplicant.getOrganization().getId());
        }
        return result;
    }

    public List<IdWithNumberOfApplicantAndRecruiter> getOrganizationProfileIdListByApplicantId(long applicantId){
        List<OrganizationApplicant> organizations = organizationApplicantRepository.findDistinctByApplicantProfileIdListIn(applicantId);
        List<IdWithNumberOfApplicantAndRecruiter> result = new ArrayList<>();
        for(OrganizationApplicant organizationApplicant:organizations){
            Organization organization = organizationApplicant.getOrganization();
            IdWithNumberOfApplicantAndRecruiter idWithNumberOfApplicantAndRecruiter = new IdWithNumberOfApplicantAndRecruiter();
            idWithNumberOfApplicantAndRecruiter.setId(organization.getOrganizationProfileId());
            idWithNumberOfApplicantAndRecruiter.setNumberOfApplicants(organization.getNumOfApplicants());
            idWithNumberOfApplicantAndRecruiter.setNumberOfRecruiters(organization.getNumOfRecruiters());
            result.add(idWithNumberOfApplicantAndRecruiter);
        }
        return result;
    }

    public List<Long> getOrganizationIdListByRecruiterId(long recruiterId){
        List<OrganizationRecruiter> organizations = organizationRecruiterRepository.findDistinctByRecruiterProfileIdListIn(recruiterId);
        List<Long> result = new ArrayList<>();
        for(OrganizationRecruiter organizationRecruiter:organizations){
            result.add(organizationRecruiter.getOrganization().getId());
        }
        return result;
    }

    public List<IdWithNumberOfApplicantAndRecruiter> getOrganizationProfileIdListByRecruiterId(long recruiterId){
        List<OrganizationRecruiter> organizations = organizationRecruiterRepository.findDistinctByRecruiterProfileIdListIn(recruiterId);
        List<IdWithNumberOfApplicantAndRecruiter> result = new ArrayList<>();
        for(OrganizationRecruiter organizationRecruiter:organizations){
            Organization organization = organizationRecruiter.getOrganization();
            IdWithNumberOfApplicantAndRecruiter idWithNumberOfApplicantAndRecruiter = new IdWithNumberOfApplicantAndRecruiter();
            idWithNumberOfApplicantAndRecruiter.setId(organization.getOrganizationProfileId());
            idWithNumberOfApplicantAndRecruiter.setNumberOfApplicants(organization.getNumOfApplicants());
            idWithNumberOfApplicantAndRecruiter.setNumberOfRecruiters(organization.getNumOfRecruiters());
            result.add(idWithNumberOfApplicantAndRecruiter);
        }
        return result;
    }
    public List<Long> getApplicantIdListByOrganizationId(long organizationProfileId){
        Organization organization = getByOrganizationProfileId(organizationProfileId);
        OrganizationApplicant organizationApplicant = organizationApplicantRepository.findByOrganizationId(organization.getId());
        return organizationApplicant.getApplicantProfileIdList();
    }

    public List<Long> getRecruiterIdListByOrganizationId(long organizationProfileId){
        Organization organization = getByOrganizationProfileId(organizationProfileId);
        OrganizationRecruiter organizationRecruiter = organizationRecruiterRepository.findByOrganizationId(organization.getId());
        return organizationRecruiter.getRecruiterProfileIdList();
    }

    public Organization createOrganization(Organization organization) {
        organization.setNumOfApplicants(0);
        organization.setNumOfRecruiters(0);
        return organizationRepository.save(organization);
    }

    public OrganizationApplicant createOrganizationApplicant(OrganizationApplicant organizationApplicant){
        return organizationApplicantRepository.save(organizationApplicant);
    }

    public OrganizationRecruiter createOrganizationRecruiter(OrganizationRecruiter organizationRecruiter){
        return organizationRecruiterRepository.save(organizationRecruiter);
    }

    public OrganizationApplicant updateOrganizationApplicantList(long organizationProfileId,List<Long> applicantIdList){
        Organization organization = getByOrganizationProfileId(organizationProfileId);
        OrganizationApplicant organizationApplicant = organizationApplicantRepository.findByOrganizationId(organization.getId());
        organizationApplicant.setApplicantProfileIdList(applicantIdList);
        System.out.println(organizationApplicant);
        return organizationApplicantRepository.save(organizationApplicant);
    }

    public Match findTopByOrganization(long profileId) {
        Organization organization = getByOrganizationProfileId(profileId);
        return matchRepository.findTopByOrganizationOrderByIdDesc(organization);
    }

    public Long countMatchesByOrganizer(long profileId) {
        Organization organization = getByOrganizationProfileId(profileId);
        return matchRepository.countByOrganizationId(organization.getId());
    }

    private Organization getByOrganizationProfileId(long profileId) {
        return organizationRepository.findByOrganizationProfileId(profileId);
    }

    public OrganizationApplicant addOrganizationApplicantList(long organizationProfileId, List<Long> idList) {
        Organization organization = getByOrganizationProfileId(organizationProfileId);
        OrganizationApplicant organizationApplicant = organizationApplicantRepository.findByOrganizationId(organization.getId());
        List<Long> newApplicantList = organizationApplicant.getApplicantProfileIdList();
        newApplicantList.addAll(idList);
        organizationApplicant.setApplicantProfileIdList(sortList(newApplicantList));
        organization.setNumOfApplicants(organizationApplicant.getApplicantProfileIdList().size());
        organizationRepository.save(organization);
        return organizationApplicantRepository.save(organizationApplicant);
    }

    private List<Long> sortList(List<Long> list){
        return list.stream().sorted().collect(Collectors.toList());
    }

    public OrganizationRecruiter addOrganizationRecruiterList(long organizationProfileId, List<Long> idList) {
        Organization organization = getByOrganizationProfileId(organizationProfileId);
        OrganizationRecruiter organizationRecruiter = organizationRecruiterRepository.findByOrganizationId(organization.getId());
        List<Long> newRecruiterIdList = organizationRecruiter.getRecruiterProfileIdList();
        newRecruiterIdList.addAll(idList);
        organizationRecruiter.setRecruiterProfileIdList(sortList(newRecruiterIdList));
        organization.setNumOfRecruiters(organizationRecruiter.getRecruiterProfileIdList().size());
        organizationRepository.save(organization);
        return organizationRecruiterRepository.save(organizationRecruiter);
    }
}
