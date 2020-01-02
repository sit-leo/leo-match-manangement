package app.leo.matchmanagement.controllers;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.leo.matchmanagement.dto.MatchDTO;
import app.leo.matchmanagement.dto.User;
import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.services.MatchService;

@RestController
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping(path = "matches/{matchId}")
    public ResponseEntity<MatchDTO> getMatchByMatchId(@PathVariable Long matchId) {
        ModelMapper modelMapper = new ModelMapper();
        MatchDTO matchResponse = modelMapper.map(this.matchService.getMatchByMatchId(matchId),MatchDTO.class);
        return new ResponseEntity<>(matchResponse, HttpStatus.OK);
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

    @PutMapping("/matches/{matchId}/join")
    public ResponseEntity<MatchDTO> updateNumberOfApplicantAndNumberOfRecruiterInMatch(@RequestAttribute("user") User user,
                                                                                    @PathVariable long matchId){
        Match match = matchService.getMatchByMatchId(matchId);
        match = matchService.updateNumberOfUser(user,match);
        ModelMapper mapper = new ModelMapper();
        MatchDTO matchDTO = mapper.map(match,MatchDTO.class);
        return new ResponseEntity<>(matchDTO,HttpStatus.OK);
    }
    @GetMapping(path = "/user/matches")
    public ResponseEntity<List<MatchDTO>> getMatchByUserId(
            @RequestParam String status,
            @RequestAttribute("user") User user,
            @RequestAttribute("token") String token
    ){
        if (status.equals("current")) {
            if (isOrganizer(user)) {
                List<Match> matches = matchService.getCurrentMatchByOrganizationId(user.getProfileId());
                return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
            }
            List<Match> matches =matchService.getCurrentMatchByUserId(token);
            return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
        } else if(status.equals("ended")){
            if (isOrganizer(user)) {
                List<Match> matches = matchService.getEndedMatchByOrganizationId(user.getProfileId());
                return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
            }
            List<Match> matches = matchService.getEndedMatchByUserId(token);
            return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
        }
        return  new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    private boolean isOrganizer(@RequestAttribute("user") User user) {
        return user.getRole().equalsIgnoreCase("organizer");
    }

    @GetMapping("/matches/lastest")
    public ResponseEntity<Page<Match>> getLatestMatchWithPageNumber(@RequestAttribute("user") User user,@RequestParam("page") int pageNumber
                                                                    ){
        Pageable pageable = PageRequest.of(pageNumber-1,6, Sort.by("create_at").descending());
        return new ResponseEntity<>(matchService.findAll(user,pageable),HttpStatus.OK);
    }

    @GetMapping("/matches/last-chance")
    public ResponseEntity<Page<Match>> getLastChanceMatchWithPageNumber(@RequestAttribute("user") User user,@RequestParam("page") int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1,3);
        return new ResponseEntity<>(matchService.getLastChanceMatches(user,pageable),HttpStatus.OK);
    }

    @GetMapping("/matches/popular")
    public ResponseEntity<Page<Match>> getPopularMatchesWithPageNumber(@RequestAttribute("user") User user ,@RequestParam("page") int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1, 3, Sort.by("popularity").descending());
        return new ResponseEntity<>(matchService.findAll(user,pageable),HttpStatus.OK);
    }

    @GetMapping("/now")
    public String getCurrentDate(){
        return matchService.getCurrentDate();
    }

}
