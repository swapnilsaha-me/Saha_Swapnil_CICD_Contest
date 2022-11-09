package saha.swapnil.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saha.swapnil.jpa.model.Contest;
import saha.swapnil.jpa.model.Team;
import saha.swapnil.jpa.services.ContestService;

import java.util.List;

@RestController
public class ContestController {

    @Autowired
    private ContestService contestService;

    @RequestMapping(value = "saveNewContest", method = RequestMethod.POST)
    public ResponseEntity<?> saveNewContest(@RequestBody Contest contest) {
        return contestService.saveNewContest(contest);
    }

    @RequestMapping(value = "/findAllContest", method = RequestMethod.GET)
    public ResponseEntity<List<Contest>> findAllContest() {
        return new ResponseEntity<>(contestService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/contestById", method = RequestMethod.GET)
    public ResponseEntity<Contest> contestById(@RequestParam(name = "id") Long contestID) {
        return new ResponseEntity<>(contestService.findById(contestID), HttpStatus.OK);
    }

    @RequestMapping(value = "/contestRegistration", method = RequestMethod.POST)
    public ResponseEntity<?> contestRegistration(@RequestParam("contestId") Long contestID, @RequestBody Team team) {
        return contestService.contestRegistration(contestID, team, false);
    }

    @RequestMapping(value = "/setEditable", method = RequestMethod.PUT)
    public ResponseEntity<?> setEditable(@RequestParam(name = "contestId") Long contestID) {
        return contestService.setEditOptionOfContest(contestID, true);
    }

    @RequestMapping(value = "/setReadOnly", method = RequestMethod.PUT)
    public ResponseEntity<?> setReadOnly(@RequestParam(name = "contestId") Long contestID) {
        return contestService.setEditOptionOfContest(contestID, false);
    }

    @RequestMapping(value = "/editContest", method = RequestMethod.PUT)
    public ResponseEntity<?> editContest(@RequestBody Contest contest) {
        return contestService.editContest(contest);
    }

    @RequestMapping(value = "/promote", method = RequestMethod.POST)
    public ResponseEntity<?> promote(@RequestParam(name = "superContestId") Long superContestID,
                                           @RequestParam(name = "teamId") Long teamID) {
        return contestService.promote(superContestID, teamID);
    }
}
