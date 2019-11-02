package app.leo.matchmanagement.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.leo.matchmanagement.adapters.ProfileAdapter;
import app.leo.matchmanagement.dto.ApplicantInMemberList;
import app.leo.matchmanagement.dto.IdWrapper;
import app.leo.matchmanagement.dto.MatchDTO;
import app.leo.matchmanagement.dto.OrganizationDTO;
import app.leo.matchmanagement.dto.RecruiterInMemberList;
import app.leo.matchmanagement.dto.User;
import app.leo.matchmanagement.exceptions.WrongRoleException;
import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.models.Organization;
import app.leo.matchmanagement.models.OrganizationApplicant;
import app.leo.matchmanagement.models.OrganizationRecruiter;
import app.leo.matchmanagement.services.MatchService;
import app.leo.matchmanagement.services.OrganizationService;

@RestController
public class OrganizationController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private ProfileAdapter profileAdapter;

    @Autowired
    private OrganizationService organizationService;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/match")
    public ResponseEntity<MatchDTO> createMatch(@Valid @RequestBody MatchDTO matchDTO, @RequestAttribute("user") User user) {
        if (user.getRole().equals("organizer")) {
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
        return new ResponseEntity<>(profileAdapter.getApplicantListByIdList(token, ids), HttpStatus.OK);
    }

    @GetMapping("organization/recruiters")
    public ResponseEntity<List<RecruiterInMemberList>> getRecruiterMembersInOrganization(@RequestAttribute("user") User user, @RequestAttribute("token") String token) {
        Long[] ids = organizationService.getRecruiterIdListByOrganizationId(user.getProfileId()).toArray(new Long[0]);
        return new ResponseEntity<>(profileAdapter.getRecruiterListByIdList(token, ids), HttpStatus.OK);
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

    @PutMapping("/organization/applicants")
    public ResponseEntity<OrganizationApplicant> updateOrganizationApplicant(@RequestAttribute("user") User user, @RequestBody IdWrapper applicantProfileIdList) {
      return new ResponseEntity<>(organizationService.updateOrganizationApplicantList(user.getProfileId(),applicantProfileIdList.getIdList()),HttpStatus.OK);
    }

    @PutMapping("/organization/recruiters")
    public ResponseEntity<OrganizationRecruiter> updateOrganizationRecruiter(@RequestAttribute("user") User user, @RequestBody IdWrapper recruiterProfileIdList) {
        return new ResponseEntity<>(organizationService.updateOrganizationRecruiterList(user.getProfileId(),recruiterProfileIdList.getIdList()),HttpStatus.OK);
    }

    @PutMapping("/match")
    public ResponseEntity<MatchDTO> updateMatch(@RequestAttribute("user") User user,@Valid @RequestBody MatchDTO matchDTO){
        if (user.getRole().equals("organizer")) {
            Match match = modelMapper.map(matchDTO, Match.class);
            Match savedMatch = matchService.saveMatch(match, user.getProfileId());
            return new ResponseEntity<>(modelMapper.map(savedMatch, MatchDTO.class), HttpStatus.ACCEPTED);
        } else {
            throw new WrongRoleException("Your role can not create match");
        }
    }
}
