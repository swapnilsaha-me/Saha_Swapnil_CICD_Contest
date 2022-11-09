package saha.swapnil.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import saha.swapnil.jpa.Constants;
import saha.swapnil.jpa.model.Person;
import saha.swapnil.jpa.model.Team;
import saha.swapnil.jpa.repository.TeamRepository;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PersonService personService;

    public Team save(Team team) {
        return teamRepository.save(team);
    }

//    public Team saveNewTeam(Team team, List<Long> members, Long coach) {
//
//        /*
//            1. New Team's id should be null.
//         */
//        if(team.getId() != null) {
//            return null;
//        }
//
//        /*
//            2. Team member's size should be exactly 3.
//            3. Coach's id can not be null.
//         */
//        if(members.size() != 3 || coach == null) {
//            return null;
//        }
//
//        /*
//            4. Member's id can not be null.
//            5. Member's id can not be equal to Coach's id.
//         */
//        for(int i = 0; i < members.size(); i++) {
//            if(members.get(i) == null) {
//                return null;
//            }
//            if(coach == members.get(i)) {
//                return null;
//            }
//        }
//
//        /*
//            6. Member's id should be distinct.
//         */
//        for(int i = 0; i < members.size(); i++) {
//            for(int j = i + 1; j < members.size(); j++) {
//                if(members.get(i) == members.get(j)) {
//                    return null;
//                }
//            }
//        }
//
//        /*
//            7. Coach can not be already a team member or manager.
//         */
//        Person person = personService.findById(coach);
//        if(person == null ||
//            person.getPersonType() == Person.PersonType.CONTESTANT ||
//            person.getPersonType() == Person.PersonType.MANAGER) {
//            return null;
//        }
//
//        /*
//            8. Member's type should be undefined, or can not be a coach or manager.
//         */
//        for(int i = 0; i < members.size(); i++) {
//            person = personService.findById(members.get(i));
//            if(person == null ||
//                person.getPersonType() == Person.PersonType.COACH ||
//                    person.getPersonType() == Person.PersonType.MANAGER) {
//                return null;
//            }
//        }
//
//        /*
//            9. Team member can have at most 24 years.
//         */
//        for(int i = 0; i < members.size(); i++) {
//            person = personService.findById(members.get(i));
//            if(person.getAge() >= Constants.CONTESTANT_AGE_LIMIT ||
//                    person.getAge() == Constants.INVALID_AGE) {
//                return null;
//            }
//        }
//
//        person = personService.findById(coach);
//        person.setPersonType(Person.PersonType.COACH);
//        personService.save(person);
//        team.setCoach(person);
//
//        team.setMembers(new HashSet<>());
//        for(int i = 0; i < members.size(); i++) {
//            person = personService.findById(members.get(i));
//            person.setPersonType(Person.PersonType.CONTESTANT);
//            personService.save(person);
//            team.getMembers().add(person);
//        }
//        team.setState(Team.State.NOT_DEFINED);
//        return save(team);
//    }

    public Team findById(Long teamID) {
        return teamRepository.findById(teamID).orElse(null);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public void deleteById(Long teamID) {
        teamRepository.deleteById(teamID);
    }

    public void deleteAll() {
        teamRepository.deleteAll();
    }

    public ResponseEntity<?> editTeam(Team team) {
        /*
            Team can not be null.
         */
        if(team == null) {
            return new ResponseEntity<>("Team - Team can not be null.",
                    HttpStatus.BAD_REQUEST);
        }
        /*
            TeamID can not be null.
         */
        if(team.getId() == null) {
            return new ResponseEntity<>("Team - Team id can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Team should exist.
         */
        Team teamDB = this.findById(team.getId());
        if(teamDB == null) {
            return new ResponseEntity<>("Team - Team should exist in the database.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Team should be editable.
         */
        if(teamDB.getWritable() == Boolean.FALSE) {
            return new ResponseEntity<>("Team - Team's registered contest is not editable.",
                    HttpStatus.BAD_REQUEST);
        }

        if(team.getMembers().size() != 0) {
            return new ResponseEntity<>("Team - Team members can not be edited.",
                    HttpStatus.BAD_REQUEST);
        }

        if(team.getCoach() != null) {
            return new ResponseEntity<>("Team - Team coach can not be edited.",
                    HttpStatus.BAD_REQUEST);
        }

        if(team.getClone_from() != null) {
            return new ResponseEntity<>("Team - Clone from can not be edited.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Validate the name.
         */
        if(team.getName() != null) {
            teamDB.setName(team.getName());
        }

        /*
            Validate the rank.
         */
        if(team.getRank() != null) {
            if(team.getRank() < 0) {
                return new ResponseEntity<>("Team - Team rank can not be negative.",
                        HttpStatus.BAD_REQUEST);
            }
            teamDB.setRank(team.getRank());
        }

        if(team.getState() != null) {
            teamDB.setState(team.getState());
        }

        return new ResponseEntity<>(this.save(teamDB), HttpStatus.OK);
    }
}
