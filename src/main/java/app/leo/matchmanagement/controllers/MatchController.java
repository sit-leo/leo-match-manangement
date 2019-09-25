package app.leo.matchmanagement.controllers;

import app.leo.matchmanagement.dto.MatchDTO;
import app.leo.matchmanagement.dto.User;
import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.services.MatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.of;

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

    private List<MatchDTO> mapMatchListToMatchResponseList(List<Match> matchList){
        ModelMapper modelMapper = new ModelMapper();
        List<MatchDTO> matchResponseList = new ArrayList<>();
        for(Match match: matchList) {
            MatchDTO matchDTO = modelMapper.map(match, MatchDTO.class);
            matchResponseList.add(matchDTO);
        }
        return  matchResponseList;
    }

    @GetMapping(path = "/user/matches")
    public ResponseEntity<List<MatchDTO>> getMatchByUserId(
            @RequestParam String status,
            @RequestAttribute("user") User user,
            @RequestAttribute("token") String token
    ){
        if (status.equals("current")) {
            List<Match> matches =matchService.getCurrentMatchByUserId(token);
            return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
        } else if(status.equals("ended")){
            List<Match> matches = matchService.getEndedMatchByUserId(token);
            return new ResponseEntity<>(mapMatchListToMatchResponseList(matches),HttpStatus.OK);
        }
        return  new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/matches/lastest")
    public ResponseEntity<Page<Match>> getLatestMatchWithPageNumber(@RequestParam("page") int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1,6, Sort.by("createAt").descending());
        return new ResponseEntity<>(matchService.findAll(pageable),HttpStatus.OK);
    }

    @GetMapping("/matches/last-chance")
    public ResponseEntity<Page<Match>> getLastChanceMatchWithPageNumber(@RequestParam("page") int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1,6);
        return new ResponseEntity<>(matchService.getLastChanceMatches(pageable),HttpStatus.OK);
    }

    @GetMapping("/matches/popular")
    public ResponseEntity<Page<Match>> getPopularMatchesWithPageNumber(@RequestParam("page") int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1, 6, Sort.by("popularity").ascending());
        return new ResponseEntity<>(matchService.findAll(pageable),HttpStatus.OK);
    }

}
