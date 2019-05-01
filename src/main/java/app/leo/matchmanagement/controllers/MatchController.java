package app.leo.matchmanagement.controllers;

import app.leo.matchmanagement.dto.MatchDTO;
import app.leo.matchmanagement.dto.User;
import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.services.MatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping(path = "matches/{matchId:[\\d]}")
    public ResponseEntity<MatchDTO> getMatchByMatchId(@PathVariable Long matchId) {
        ModelMapper modelMapper = new ModelMapper();
        MatchDTO matchResponse = modelMapper.map(this.matchService.getMatchByMatchId(matchId),MatchDTO.class);
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
    }

    @GetMapping(path="/applicant/matches")
    public ResponseEntity<List<MatchDTO>> getMatchByApplicantId(
        @RequestParam String status,
        @RequestAttribute("token") String token
    ){
        List<MatchDTO> matchDTOList = new ArrayList<>();
        if (status.equals("current")) {
            List<Match> matches =matchService.getCurrentMatchByApplicantId(token);
            return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
        } else if(status.equals("ended")){
            List<Match> matches = matchService.getEndedMatchByApplicantId(token);
            return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    private List<MatchDTO> mapMatchListToMatchResponseList(List<Match> matchList){
        ModelMapper modelMapper = new ModelMapper();
        List<MatchDTO> matchResponseList = new ArrayList<>();
        for(Match match: matchList) {
            MatchDTO matchDTO = modelMapper.map(match, MatchDTO.class);
            matchResponseList.add(matchDTO);
        }
        return  matchResponseList;
    }

    @GetMapping(path ="/recruiter/matches")
    public ResponseEntity<List<MatchDTO>> getMatchByRecruiterId(
        @RequestParam String status,
        @RequestAttribute("token") String token
    ){
        if (status.equals("current")) {
            List<Match> matches =matchService.getCurrentMatchByRecruiterId(token);
            return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
        }else if(status.equals("ended")){
            List<Match> matches = matchService.getEndedMatchByApplicantId(token);
            return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/user/matches")
    public ResponseEntity<List<MatchDTO>> getMatchByUserId(
            @RequestParam String status,
            @RequestAttribute("user") User user,
            @RequestAttribute("token") String token
    ){
        String role = user.getRole();
        switch (role){
            case "applicant":
                if (status.equals("current")) {
                    List<Match> matches =matchService.getCurrentMatchByApplicantId(token);
                    return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
                } else if(status.equals("ended")){
                    List<Match> matches = matchService.getEndedMatchByApplicantId(token);
                    return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
                }
                break;
            case "recruiter":
                if (status.equals("current")) {
                    List<Match> matches =matchService.getCurrentMatchByRecruiterId(token);
                    return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
                }else if(status.equals("ended")){
                    List<Match> matches = matchService.getEndedMatchByApplicantId(token);
                    return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
                }
                break;
        }
        return  new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
}
