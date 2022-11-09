package saha.swapnil.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saha.swapnil.jpa.model.Team;
import saha.swapnil.jpa.services.TeamService;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/editTeam", method = RequestMethod.PUT)
    public ResponseEntity<?> editTeam(@RequestBody Team team) {
        return teamService.editTeam(team);
    }

    @RequestMapping(value = "/findAllTeam", method = RequestMethod.GET)
    public ResponseEntity<List<Team>> findAllTeam() {
        return new ResponseEntity<>(teamService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/teamById", method = RequestMethod.GET)
    public ResponseEntity<Team> teamById(@RequestParam(name = "id") Long teamId) {
        return new ResponseEntity<>(teamService.findById(teamId), HttpStatus.OK);
    }
}
