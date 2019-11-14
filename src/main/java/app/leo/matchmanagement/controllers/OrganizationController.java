package app.leo.matchmanagement.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import app.leo.matchmanagement.dto.*;
import app.leo.matchmanagement.exceptions.MatchIsNotEmptyException;
import app.leo.matchmanagement.exceptions.WrongOrganizationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.leo.matchmanagement.adapters.ProfileAdapter;
import app.leo.matchmanagement.exceptions.WrongRoleException;
import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.models.Organization;
import app.leo.matchmanagement.models.OrganizationApplicant;
import app.leo.matchmanagement.models.OrganizationRecruiter;
import app.leo.matchmanagement.services.MatchService;
import app.leo.matchmanagement.services.OrganizationService;

@RestController
public class OrganizationController {
    private static final List<?> EMPTY_PARTICIPANTS = new ArrayList<>();

    @Autowired
    private MatchService matchService;

    @Autowired
    private ProfileAdapter profileAdapter;

    @Autowired
    private OrganizationService organizationService;

    private ModelMapper modelMapper = new ModelMapper();
    private final String APPLICANT_ROLE = "applicant";
    private final String RECRUITER_ROLE = "recruiter";
    private final String ORGANIZER_ROLE = "organizer";

    @PostMapping("/match")
    public ResponseEntity<MatchDTO> createMatch(@Valid @RequestBody MatchDTO matchDTO, @RequestAttribute("user") User user) {
        if (user.getRole().equals(ORGANIZER_ROLE)) {
            Match mappedMatch = modelMapper.map(matchDTO, Match.class);
            Match savedMatch = matchService.saveMatch(mappedMatch, user.getProfileId());
            return new ResponseEntity<>(modelMapper.map(savedMatch, MatchDTO.class), HttpStatus.ACCEPTED);
        } else {
            throw new WrongRoleException("Your role can not create match");
        }
    }

    @GetMapping("organizations/match")
    public ResponseEntity<MatchDTO> getCurrentMatchInOrganization(
            @RequestAttribute("user") User user,
            @RequestAttribute("token") String token
    ) {
        Match match = organizationService.findTopByOrganization(user.getProfileId());
        return new ResponseEntity<>(modelMapper.map(match, MatchDTO.class), HttpStatus.OK);
    }

    @GetMapping("organization/applicants")
    public ResponseEntity<List<ApplicantInMemberList>> getApplicantMembersInOrganization(@RequestAttribute("user") User user, @RequestAttribute("token") String token) {
        Long[] ids = organizationService.getApplicantIdListByOrganizationId(user.getProfileId()).toArray(new Long[0]);

        if (ids.length == 0) {
            List<ApplicantInMemberList> empty = (List<ApplicantInMemberList>) EMPTY_PARTICIPANTS;
            return new ResponseEntity<>(empty, HttpStatus.OK);
        }

        List<ApplicantInMemberList> applicants = profileAdapter.getApplicantListByIdList(token, ids);
        return new ResponseEntity<>(applicants, HttpStatus.OK);
    }

    @GetMapping("organization/recruiters")
    public ResponseEntity<List<RecruiterInMemberList>> getRecruiterMembersInOrganization(@RequestAttribute("user") User user, @RequestAttribute("token") String token) {
        Long[] ids = organizationService.getRecruiterIdListByOrganizationId(user.getProfileId()).toArray(new Long[0]);

        if (ids.length == 0) {
            List<RecruiterInMemberList> empty = (List<RecruiterInMemberList>) EMPTY_PARTICIPANTS;
            return new ResponseEntity<>(empty, HttpStatus.OK);
        }

        List<RecruiterInMemberList> recruiters = profileAdapter.getRecruiterListByIdList(token, ids);
        return new ResponseEntity<>(recruiters, HttpStatus.OK);
    }

    @GetMapping("organization/matches/count")
    public ResponseEntity<Long> countMatchesByOrganizer(@RequestAttribute("user") User user, @RequestAttribute("token") String token) {
        Long numOfMatches = organizationService.countMatchesByOrganizer(user.getProfileId());
        return new ResponseEntity<>(numOfMatches, HttpStatus.OK);
    }

    @PostMapping("/create/organization")
    public ResponseEntity<OrganizationDTO> createOrganization(@RequestBody OrganizationDTO organization) {
        Organization createdOrg = organizationService.createOrganization(modelMapper.map(organization, Organization.class));
        OrganizationApplicant organizationApplicant = new OrganizationApplicant(createdOrg, new ArrayList<>());
        organizationService.createOrganizationApplicant(organizationApplicant);
        OrganizationRecruiter organizationRecruiter = new OrganizationRecruiter(createdOrg, new ArrayList<>());
        organizationService.createOrganizationRecruiter(organizationRecruiter);
        return new ResponseEntity<>(modelMapper.map(createdOrg, OrganizationDTO.class), HttpStatus.ACCEPTED);
    }

    @PostMapping("/organization/applicants")
    public ResponseEntity<OrganizationApplicant> addApplicantToOrganizationApplicant(@RequestAttribute("user") User user, @RequestBody IdWrapper applicantProfileIdList) {
        return new ResponseEntity<>(organizationService.addOrganizationApplicantList(user.getProfileId(), applicantProfileIdList.getIdList()), HttpStatus.OK);
    }

    @PostMapping("/organization/recruiters")
    public ResponseEntity<OrganizationRecruiter> updateOrganizationRecruiter(@RequestAttribute("user") User user, @RequestBody IdWrapper recruiterProfileIdList) {
        return new ResponseEntity<>(organizationService.addOrganizationRecruiterList(user.getProfileId(), recruiterProfileIdList.getIdList()), HttpStatus.OK);
    }

    @PutMapping("/match")
    public ResponseEntity<MatchDTO> updateMatch(@RequestAttribute("user") User user, @Valid @RequestBody MatchDTO matchDTO) {
        Match previousMatch = matchService.getMatchByMatchId(matchDTO.getId());
        if (user.getRole().equals("organizer")) {
            if (previousMatch.getOrganization().getOrganizationProfileId() != user.getProfileId()){
                throw new WrongOrganizationException("This is not your match");
            }
            if(previousMatch.getNumOfApplicant()!=0 || previousMatch.getNumOfRecruiter() !=0){
                throw new MatchIsNotEmptyException("Match is not empty. You can not edit the detail of match");
            }
            Match match = modelMapper.map(matchDTO, Match.class);
            Match savedMatch = matchService.saveMatch(match, user.getProfileId());
            return new ResponseEntity<>(modelMapper.map(savedMatch, MatchDTO.class), HttpStatus.ACCEPTED);
        } else {
            throw new WrongRoleException("Your role can not create match");
        }
    }

    @GetMapping("/organization/applicants/{orgProfileId}")
    public ResponseEntity<IdWrapper> getApplicantIdListByOrganizationProfileId(@PathVariable long orgProfileId) {
        IdWrapper response = new IdWrapper(organizationService.getApplicantIdListByOrganizationId(orgProfileId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/organization/recruiters/{orgProfileId}")
    public ResponseEntity<IdWrapper> getRecruiterIdListByOrganizationProfileId(@PathVariable long orgProfileId) {
        IdWrapper response = new IdWrapper(organizationService.getRecruiterIdListByOrganizationId(orgProfileId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{profileId}/organizations")
    public ResponseEntity<OrgIdWrapper> getAllOrganizaitonProfileIdOfUser(@RequestAttribute("user") User user, @PathVariable long profileId) {
        String role = user.getRole();
        List<IdWithNumberOfApplicantAndRecruiter> idList;
        switch (role) {
            case APPLICANT_ROLE:
                idList = organizationService.getOrganizationProfileIdListByApplicantId(profileId);
                break;
            case RECRUITER_ROLE:
                idList = organizationService.getOrganizationProfileIdListByRecruiterId(profileId);
                break;
            default:
                throw new WrongRoleException("You belong to one organization");
        }
        return new ResponseEntity<>(new OrgIdWrapper(idList), HttpStatus.OK);
    }
}