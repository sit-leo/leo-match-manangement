package app.leo.matchmanagement.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.leo.matchmanagement.adapters.MatchingAdapter;
import app.leo.matchmanagement.dto.User;
import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.models.Organization;
import app.leo.matchmanagement.repositories.MatchRepository;
import app.leo.matchmanagement.repositories.OrganizationRepository;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchingAdapter matchingAdapter;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    private final Date currentDate =  java.sql.Date.valueOf(LocalDate.now(ZoneId.of("Asia/Bangkok")));

    public Match getMatchByMatchId(long id) {
        return this.matchRepository.getMatchById(id);
    }

    public List<Match> getCurrentMatchByUserId(String token) {
        List<Long> matchId = matchingAdapter.getMatchIdByUserId(token);
        return matchRepository.getMatchesByStartJoiningDateBeforeAndAnnouceDateAfterAndIdIn(currentDate,matchId);
    }

    public List<Match> getEndedMatchByUserId(String token){
        List<Long> matchId = matchingAdapter.getMatchIdByUserId(token);
        return matchRepository.getMatchesByAnnounceDateEndDateAfterAndIdIn(currentDate,matchId);
    }

    public Page<Match> findAll(long profileId,Pageable pagenable,String role){
        List<Long> orgIdList = new ArrayList<>();
        if(role.equals("applicant")) {
            orgIdList = organizationService.getOrganizationIdListByApplicantId(profileId);
        }else if (role.equals("recruiter")){
            orgIdList =  organizationService.getOrganizationIdListByRecruiterId(profileId);
        }
        return  matchRepository.findAllByOrganizationIdIn(orgIdList,pagenable);
    }

    public Page<Match> getLastChanceMatches(long profileId,Pageable pageable,String role){
        List<Long> orgIdList = new ArrayList<>();
        if(role.equals("applicant")){
            orgIdList = organizationService.getOrganizationIdListByApplicantId(profileId);
        }else if(role.equals("recruiter")){
            orgIdList = organizationService.getOrganizationIdListByRecruiterId(profileId);
        }
        return matchRepository.getLastChanceMatches(pageable,orgIdList);
    }

    public Match updateNumberOfUser(User user,Match match){
        if(user.getRole().equals("recruiter")){
            int newNumberOfRecruiter = match.getNumOfRecruiter()+1;
            match.setNumOfRecruiter(newNumberOfRecruiter);
        }else if(user.getRole().equals("applicant")){
            int newNumberOfApplicant = match.getNumOfApplicant()+1;
            match.setNumOfApplicant(newNumberOfApplicant);
        }
        match.setPopularity(match.getNumOfApplicant()+match.getNumOfRecruiter());
        return matchRepository.save(match);

    }

    public Match saveMatch(Match match, long profileId){
        Organization organization = getOrganization(profileId);
        match.setOrganization(organization);
        return matchRepository.save(match);
    }

    public List<Match> getCurrentMatchByOrganizationId(long profileId) {
        Organization organization = getOrganization(profileId);
        return matchRepository.findByStartJoiningDateBeforeAndAnnouceDateAfterAndOrganizationId(currentDate, organization.getId());
    }

    public List<Match> getEndedMatchByOrganizationId(long profileId) {
        Organization organization = getOrganization(profileId);
        return matchRepository.findEndedMatchesByAnnounceDateEndDateAfterAndOrganizationId(currentDate, organization.getId());
    }

    private Organization getOrganization(long profileId) {
        return organizationRepository.findByOrganizationProfileId(profileId);
    }
}
