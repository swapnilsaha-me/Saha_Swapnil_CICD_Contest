package saha.swapnil.jpa.controller;

import org.springframework.web.bind.annotation.*;
import saha.swapnil.jpa.StoreData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class MyController {
    @Autowired
    private StoreData storeData;

    @RequestMapping(value = "/populate", method = RequestMethod.POST)
    public ResponseEntity populate(){
        storeData.populate();
        return new ResponseEntity(HttpStatus.OK);
    }

    /*
    @RequestMapping(value = "/saveNewPerson", method = RequestMethod.POST)
    public ResponseEntity<Person> saveNewPerson(@RequestBody Person person) {
        return new ResponseEntity<>(personService.save(person), HttpStatus.OK);
    }

    @RequestMapping(value = "/findAllPerson", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> findAllPerson() {
        return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/saveNewTeam", method = RequestMethod.POST)
    public ResponseEntity<Team> saveNewTeam(@RequestBody Team team,
                                            @RequestParam(name = "members") List<Long> members,
                                            @RequestParam(name = "coach") Long coach) {
        Team ret_team = teamService.saveNewTeam(team, members, coach);
        if(ret_team != null) {
            return new ResponseEntity<>(ret_team, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/findAllTeam", method = RequestMethod.GET)
    public ResponseEntity<List<Team>> findAllTeam() {
        return new ResponseEntity<>(teamService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/saveNewContest", method = RequestMethod.POST)
    public ResponseEntity<Contest> saveNewContest(@RequestBody Contest contest,
                                                  @RequestParam(name = "managers") List<Long> managers) {
        Contest ret_contest = contestService.saveNewContest(contest, managers);
        if(ret_contest != null) {
            return new ResponseEntity<>(ret_contest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/findAllContest", method = RequestMethod.GET)
    public ResponseEntity<List<Contest>> findAllContest() {
        return new ResponseEntity<>(contestService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/contestRegistration", method = RequestMethod.POST)
    public ResponseEntity<Contest> contestRegistration(@RequestParam(name = "contestID") Long contestID,
                                                       @RequestParam(name = "teamID") Long teamID) {
        Contest ret_contest = contestService.contestRegistration(contestID, teamID);
        if(ret_contest != null) {
            return new ResponseEntity(ret_contest, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/setEditableContest", method = RequestMethod.POST)
    public ResponseEntity<Contest> setEditableContest(@RequestParam(name = "contestID") Long contestID) {
        Contest ret_contest = contestService.setEditOptionOfContest(contestID, true);
        if(ret_contest != null) {
            return new ResponseEntity<>(ret_contest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/setReadOnlyContest", method = RequestMethod.POST)
    public ResponseEntity<Contest> setReadOnlyContest(@RequestParam(name = "contestID") Long contestID) {
        Contest ret_contest = contestService.setEditOptionOfContest(contestID, false);
        if(ret_contest != null) {
            return new ResponseEntity<>(ret_contest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/editContest", method = RequestMethod.POST)
    public ResponseEntity<Contest> editContest(@RequestBody Contest contest) {
        Contest ret_contest = contestService.editContest(contest);
        if(ret_contest != null) {
            return new ResponseEntity<>(ret_contest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/editTeam", method = RequestMethod.POST)
    public ResponseEntity<Team> editTeam(@RequestBody Team team) {
        Team ret_team = teamService.editTeam(team);
        if(ret_team != null) {
            return new ResponseEntity<>(ret_team, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/promote", method = RequestMethod.POST)
    public ResponseEntity<Contest> promote(@RequestParam(name = "superContestID") Long superContestID,
                                           @RequestParam(name = "teamID") Long teamID) {
        Contest ret_contest = contestService.promote(superContestID, teamID);
        if(ret_contest != null) {
            return new ResponseEntity<>(ret_contest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
     */
}
