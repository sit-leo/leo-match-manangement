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
import org.springframework.transaction.annotation.Transactional;

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

    private final String APPLICANT_ROLE = "applicant";
    private final String RECRUITER_ROLE = "recruiter";
    private final String ORGANIZER_ROLE = "organizer";

    private final Date currentDate =  java.sql.Date.valueOf(LocalDate.now(ZoneId.of("Asia/Bangkok")));

    public Match getMatchByMatchId(long id) {
        return this.matchRepository.getMatchById(id);
    }

    public List<Match> getCurrentMatchByUserId(String token) {
        List<Long> matchId = matchingAdapter.getMatchIdByUserId(token);
        return matchRepository.getMatchesByStartJoiningDateBeforeAndAnnouceDateAfterAndIdIn(currentDate.toString(),matchId);
    }

    public List<Match> getEndedMatchByUserId(String token){
        List<Long> matchId = matchingAdapter.getMatchIdByUserId(token);
        return matchRepository.getMatchesByAnnounceDateEndDateAfterAndIdIn(currentDate.toString(),matchId);
    }

    public Page<Match> findAll(User user,Pageable pagenable){
        List<Long> orgIdList = new ArrayList<>();
        String role = user.getRole();
        long profileId = user.getProfileId();
        if(role.equals(APPLICANT_ROLE)) {
            orgIdList = organizationService.getOrganizationIdListByApplicantId(profileId);
        }else if (role.equals(RECRUITER_ROLE)){
            orgIdList =  organizationService.getOrganizationIdListByRecruiterId(profileId);
        }else if(role.equals(ORGANIZER_ROLE)){
            orgIdList.add(organizationService.getByOrganizationProfileId(profileId).getId());
        }
        if (orgIdList.size() == 0) {
            return Page.empty();
        }
        return  matchRepository.findAllByOrganizationIdIn(pagenable,orgIdList);
    }

    public Page<Match> getLastChanceMatches(User user,Pageable pageable){
        List<Long> orgIdList = new ArrayList<>();
        String role = user.getRole();
        long profileId = user.getProfileId();
        if(role.equals(APPLICANT_ROLE)){
            orgIdList = organizationService.getOrganizationIdListByApplicantId(profileId);
        }else if(role.equals(RECRUITER_ROLE)){
            orgIdList = organizationService.getOrganizationIdListByRecruiterId(profileId);
        }else if(role.equals(ORGANIZER_ROLE)){
            orgIdList.add(organizationService.getByOrganizationProfileId(profileId).getId());
        }

        if (orgIdList.size() == 0) {
            return Page.empty();
        }
        return matchRepository.getLastChanceMatches(pageable, orgIdList);
    }

    public Match updateNumberOfUser(User user,Match match){
        if(user.getRole().equals(RECRUITER_ROLE)){
            int newNumberOfRecruiter = match.getNumOfRecruiter()+1;
            match.setNumOfRecruiter(newNumberOfRecruiter);
        }else if(user.getRole().equals(APPLICANT_ROLE)){
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
        return matchRepository.findByStartJoiningDateBeforeAndAnnouceDateAfterAndOrganizationId(currentDate.toString(), organization.getId());
    }

    public List<Match> getEndedMatchByOrganizationId(long profileId) {
        Organization organization = getOrganization(profileId);
        return matchRepository.findEndedMatchesByAnnounceDateEndDateAfterAndOrganizationId(currentDate.toString(), organization.getId());
    }

    private Organization getOrganization(long profileId) {
        return organizationRepository.findByOrganizationProfileId(profileId);
    }

    @Transactional
    public void deleteMatchById(long matchId) {
        matchRepository.deleteById(matchId);
    }

    public String getCurrentDate(){
        return currentDate.toString();
    }
}
