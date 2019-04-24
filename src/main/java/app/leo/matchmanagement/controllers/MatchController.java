package app.leo.matchmanagement.controllers;

import app.leo.matchmanagement.dto.User;
import app.leo.matchmanagement.models.Match;
import app.leo.matchmanagement.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping(path = "matches/{matchId:[\\d]}")
    public ResponseEntity<Match> getMatchByMatchId(@PathVariable Long matchId, @RequestAttribute("user") User user) {
        return new ResponseEntity<>(this.matchService.getMatchByMatchId(matchId), HttpStatus.OK);
    }
}
