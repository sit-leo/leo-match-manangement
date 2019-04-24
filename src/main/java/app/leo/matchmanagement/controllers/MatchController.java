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
        MatchDTO matchDTO = modelMapper.map(this.matchService.getMatchByMatchId(matchId),MatchDTO.class);
        return new ResponseEntity<>(matchDTO, HttpStatus.OK);
    }

    @GetMapping(path="/applicant/matches")
    public ResponseEntity<List<MatchDTO>> getMatchByApplicantId(@RequestParam String status, @RequestAttribute("user") User user) {
        System.out.println(user.getUserId());
        System.out.println(user.getRole());
        ModelMapper modelMapper = new ModelMapper();
        List<MatchDTO> matchDTOList = new ArrayList<>();
        if (status.equals("current")) {
            List<Match> matches =matchService.getCurrentMatchByApplicantId();
            for(Match match:matches) {
                MatchDTO matchDTO = modelMapper.map(match, MatchDTO.class);
                matchDTOList.add(matchDTO);
            }
            return new ResponseEntity<>(matchDTOList,HttpStatus.OK);
        } else if(status.equals("ended")){
            List<Match> matches = matchService.getEndedMatchByApplicantId();
            for(Match match:matches) {
                MatchDTO matchDTO = modelMapper.map(match, MatchDTO.class);
                matchDTOList.add(matchDTO);
            }
            return new ResponseEntity<>(matchDTOList,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @GetMapping(path ="/recruiter/matches")
    public ResponseEntity<List<MatchDTO>> getMatchByRecruiterId(@RequestParam String status){
        ModelMapper modelMapper = new ModelMapper();
        List<MatchDTO> matchDTOList = new ArrayList<>();
        if (status.equals("current")) {
            List<Match> matches =matchService.getCurrentMatchByRecruiterId();
            for(Match match:matches) {
                MatchDTO matchDTO = modelMapper.map(match, MatchDTO.class);
                matchDTOList.add(matchDTO);
            }
            return new ResponseEntity<>(matchDTOList,HttpStatus.OK);
        }else if(status.equals("ended")){
            List<Match> matches = matchService.getEndedMatchByApplicantId();
            for(Match match:matches) {
                MatchDTO matchDTO = modelMapper.map(match, MatchDTO.class);
                matchDTOList.add(matchDTO);
            }
            return new ResponseEntity<>(matchDTOList,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }
}
