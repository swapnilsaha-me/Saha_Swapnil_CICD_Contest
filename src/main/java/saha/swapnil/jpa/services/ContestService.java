package saha.swapnil.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import saha.swapnil.jpa.Constants;
import saha.swapnil.jpa.model.Contest;
import saha.swapnil.jpa.model.Person;
import saha.swapnil.jpa.model.Team;
import saha.swapnil.jpa.repository.ContestRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private TeamService teamService;

    public Contest save(Contest contest) {
        return contestRepository.save(contest);
    }

    public ResponseEntity<?> saveNewContest(Contest contest) {
        if(contest.getId() != null) {
            return new ResponseEntity<>("Contest - New Contest's id should be null.",
                    HttpStatus.BAD_REQUEST);
        }

        if(contest.getCapacity() == null) {
            return new ResponseEntity<>("Contest - Capacity can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        if(contest.getCapacity() < 0) {
            return new ResponseEntity<>("Contest - Capacity can not be negative.",
                    HttpStatus.BAD_REQUEST);
        }

        if(contest.getName() == null) {
            return new ResponseEntity<>("Contest - Name can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        if(contest.getRegistration_allowed() == null) {
            return new ResponseEntity<>("Contest - Registration allowed can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        if(contest.getWritable() == null) {
            contest.setWritable(true);
        }

        if(contest.getManagers().size() == 0) {
            return new ResponseEntity<>("Contest - There should be at least one manager.",
                    HttpStatus.BAD_REQUEST);
        }

        Set<Person> managers = contest.getManagers();
        for(Person manager : managers) {
            String ret_error = this.validatePersonObject(manager);
            if(!ret_error.equals(Constants.NO_ERROR)) {
                return new ResponseEntity<>("Contest - Manager -> " + ret_error,
                        HttpStatus.BAD_REQUEST);
            }
        }

        List<String> emails = new ArrayList<>();
        for(Person manager: managers) {
            if(manager.getId() != null) {
                manager = personService.findById(manager.getId());
            }
            emails.add(manager.getEmail());
            if(manager.getPersonType() == Person.PersonType.CONTESTANT) {
                return new ResponseEntity<>("Contest - Manager can not be a contestant.",
                        HttpStatus.BAD_REQUEST);
            }
            if(manager.getPersonType() == Person.PersonType.COACH) {
                return new ResponseEntity<>("Contest - Manager can not be a coach.",
                        HttpStatus.BAD_REQUEST);
            }
        }

        for(int i = 0; i < emails.size(); i++) {
            for(int j = i + 1; j < emails.size(); j++) {
                if(emails.get(i).equals(emails.get(j))) {
                    return new ResponseEntity<>("Contest - All manager should be distinct.",
                            HttpStatus.BAD_REQUEST);
                }
            }
        }

        if(contest.getTeams().size() != 0) {
            return new ResponseEntity<>("Contest - Team should be registered using contest registration api.",
                    HttpStatus.BAD_REQUEST);
        }

        if(contest.getPreliminary_contest() != null) {
            if(contest.getPreliminary_contest().getId() == null) {
                return new ResponseEntity<>("Contest - Preliminary contest id can not be null.",
                        HttpStatus.BAD_REQUEST);
            }
            if(this.findById(contest.getPreliminary_contest().getId()) == null) {
                return new ResponseEntity<>("Contest - Preliminary contest should exist in the database.",
                        HttpStatus.BAD_REQUEST);
            }
            contest.setPreliminary_contest(this.findById(contest.getPreliminary_contest().getId()));
        }

        contest.setManagers(new HashSet<>());
        for(Person manager: managers) {
            if(manager.getId() != null) {
                manager = personService.findById(manager.getId());
            }
            manager.setPersonType(Person.PersonType.MANAGER);
            personService.save(manager);
            contest.getManagers().add(manager);
        }
        return new ResponseEntity<>(this.save(contest), HttpStatus.OK);
    }

    public Contest findById(Long contestID) {
        return contestRepository.findById(contestID).orElse(null);
    }

    public Contest findByName(String contestName) {
        return contestRepository.findByName(contestName);
    }

    public List<Contest> findAll() {
        return contestRepository.findAll();
    }

    public void deleteAll() {
        contestRepository.deleteAll();
    }

    private String validatePersonObject(Person person) {

        if(person == null) {
            return "Person can not be null.";
        }

        if (person.getId() == null) {
            return personService.validateNewPerson(person);
        }

        if(personService.findById(person.getId()) == null) {
            return "A person should exist in the database.";
        }

        if(person.getName() != null) {
            return "Everything should be null except id for an existing person.";
        }

        if(person.getEmail() != null) {
            return "Everything should be null except id for an existing person.";
        }

        if(person.getUniversity() != null) {
            return "Everything should be null except id for an existing person.";
        }

        if(person.getBirthdate() != null) {
            return "Everything should be null except id for an existing person.";
        }

        if(person.getPersonType() != null) {
            return "Everything should be null except id for an existing person.";
        }

        if(person.getAge() != null) {
            return "Everything should be null except id for an existing person.";
        }

        return Constants.NO_ERROR;
    }

    public ResponseEntity<?> contestRegistration(Long contestID, Team team, boolean isPromote) {

        if(contestID == null) {
            return new ResponseEntity<>("Contest - Contest id can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        if(team == null) {
            return new ResponseEntity<>("Contest - Team can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        Contest contest = this.findById(contestID);
        if(contest == null) {
            return new ResponseEntity<>("Contest - Contest should exist in the database.",
                    HttpStatus.BAD_REQUEST);
        }

        if(contest.getWritable() == Boolean.FALSE) {
            return new ResponseEntity<>("Contest - Contest should be editable for team registration.",
                    HttpStatus.BAD_REQUEST);
        }

        if(contest.getRegistration_allowed() == Boolean.FALSE) {
            return new ResponseEntity<>("Contest - Registration for this contest is turned off.",
                    HttpStatus.BAD_REQUEST);
        }

        if(contest.getTeams().size() >= contest.getCapacity()) {
            return new ResponseEntity<>("Contest - No capacity for the new team registration.",
                    HttpStatus.BAD_REQUEST);
        }

        if(team.getId() != null) {
            return new ResponseEntity<>("Contest - Team id should be null.",
                    HttpStatus.BAD_REQUEST);
        }

        if(team.getName() == null) {
            return new ResponseEntity<>("Contest - Team name can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        if(team.getRank() != null) {
            if(team.getRank() <= 0) {
                return new ResponseEntity<>("Contest - Team rank should be positive.",
                        HttpStatus.BAD_REQUEST);
            }
        }

        if(team.getState() == null) {
            team.setState(Team.State.PENDING);
        }

        if(team.getState() != Team.State.PENDING && !isPromote) {
            return new ResponseEntity<>("Contest - Team state should be PENDING.",
                    HttpStatus.BAD_REQUEST);
        }

        if(team.getClone_from() != null && !isPromote) {
            return new ResponseEntity<>("Contest - Team can not be cloned in contest registration.",
                    HttpStatus.BAD_REQUEST);
        }

        team.setWritable(contest.getWritable());

        if(team.getCoach() == null) {
            new ResponseEntity<>("Contest - Team's coach can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        String ret_error = isPromote ? Constants.NO_ERROR : validatePersonObject(team.getCoach());
        if(!ret_error.equals(Constants.NO_ERROR)) {
            return new ResponseEntity<>("Contest - Coach -> " + ret_error,
                    HttpStatus.BAD_REQUEST);
        }

        if(team.getMembers().size() != 3) {
            return new ResponseEntity<>("Contest - Team should have exactly 3 members.",
                    HttpStatus.BAD_REQUEST);
        }

        for(Person member : team.getMembers()) {
            ret_error = isPromote ? Constants.NO_ERROR : validatePersonObject(member);
            if(!ret_error.equals(Constants.NO_ERROR)) {
                return new ResponseEntity<>("Contest - Contestant -> " + ret_error,
                        HttpStatus.BAD_REQUEST);
            }
        }

        List<String> emails = new ArrayList<>();
        Person coach = team.getCoach();
        if(coach.getId() != null) {
            coach = personService.findById(coach.getId());
        }
        emails.add(coach.getEmail());
        if(coach.getPersonType() == Person.PersonType.CONTESTANT) {
            return new ResponseEntity<>("Contest - Coach can not be a contestant.",
                    HttpStatus.BAD_REQUEST);
        }
        if(coach.getPersonType() == Person.PersonType.MANAGER) {
            return new ResponseEntity<>("Contest - Coach can not be a manager.",
                    HttpStatus.BAD_REQUEST);
        }

        for(Person member : team.getMembers()) {
            if(member.getId() != null) {
                member = personService.findById(member.getId());
            }
            emails.add(member.getEmail());
            if(member.getPersonType() == Person.PersonType.COACH) {
                return new ResponseEntity<>("Contest - Contestant can not be a coach.",
                        HttpStatus.BAD_REQUEST);
            }
            if(member.getPersonType() == Person.PersonType.MANAGER) {
                return new ResponseEntity<>("Contest - Contestant can not be a manager.",
                        HttpStatus.BAD_REQUEST);
            }
        }

        for(Person member : team.getMembers()) {
            if(member.getId() != null) {
                member = personService.findById(member.getId());
            }
            if(member.getAge() == Constants.INVALID_AGE) {
                return new ResponseEntity<>("Contest - Invalid date of birth given.",
                        HttpStatus.BAD_REQUEST);
            }
            if(member.getAge() > Constants.CONTESTANT_AGE_LIMIT) {
                return new ResponseEntity<>("Contest - Contestant's age can not be more than 24.",
                        HttpStatus.BAD_REQUEST);
            }
        }

        for(int i = 0; i < emails.size(); i++) {
            for(int j = i + 1; j < emails.size(); j++) {
                if(emails.get(i).equals(emails.get(j))) {
                    return new ResponseEntity<>("Contest - All contestants, and coach email should be distinct.",
                            HttpStatus.BAD_REQUEST);
                }
            }
        }

        Set<Team> allTeams = contest.getTeams();
        for(Person member : team.getMembers()) {
            if (member.getId() != null) {
                member = personService.findById(member.getId());
            }
            for(Team registered_team : allTeams) {
                Set<Person> registered_members = registered_team.getMembers();
                for(Person reg_member : registered_members) {
                    if(reg_member.getEmail().equals(member.getEmail())) {
                        return new ResponseEntity<>("Contest - Contestant already registered in another team.",
                                HttpStatus.BAD_REQUEST);
                    }
                }
            }
        }

        /*
            Data save.
         */
        if(!isPromote) {
            coach.setPersonType(Person.PersonType.COACH);
            if(coach.getId() == null) {
                personService.save(coach);
            }
            team.setCoach(coach);
            Set<Person> members = new HashSet<>();
            for(Person member : team.getMembers()) {
                if(member.getId() == null) {
                    member.setPersonType(Person.PersonType.CONTESTANT);
                    member = personService.save(member);
                } else {
                    member = personService.findById(member.getId());
                    member.setPersonType(Person.PersonType.CONTESTANT);
                    member = personService.save(member);
                }
                members.add(member);
            }
            team.setMembers(members);
        }
        teamService.save(team);
        contest.getTeams().add(team);
        return new ResponseEntity<>(this.save(contest), HttpStatus.OK);
    }

    public ResponseEntity<?> setEditOptionOfContest(Long contestID, boolean isWritable) {
        /*
            1. ID cannot be null.
         */
        if(contestID == null) {
            return new ResponseEntity<>("Contest - Contest id can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            2. There should exist a valid contest with this contestID.
         */
        Contest contest = this.findById(contestID);
        if(contest == null) {
            return new ResponseEntity<>("Contest - A contest should exist in the database.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            3. Set edit option of the contest and all teams.
         */
        contest.setWritable(isWritable);
        for(Team team: contest.getTeams()) {
            team.setWritable(isWritable);
            teamService.save(team);
        }

        return new ResponseEntity<>(this.save(contest), HttpStatus.OK);
    }

    public ResponseEntity<?> editContest(Contest contest) {

        /*
            Contest object can not be null.
         */
        if(contest == null) {
            return new ResponseEntity<>("Contest - Contest object can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Contest id can not be null.
         */
        if(contest.getId() == null) {
            return new ResponseEntity<>("Contest - Contest id can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Contest should exist.
         */
        Contest contestDB = this.findById(contest.getId());
        if(contestDB == null) {
            return new ResponseEntity<>("Contest - Contest should exist in the database.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Contest should be editable.
         */
        if(contestDB.getWritable() == Boolean.FALSE) {
            return new ResponseEntity<>("Contest - Contest should be set to editable.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Validate the capacity.
         */
        if(contest.getCapacity() != null) {
            if(contestDB.getCapacity() <= contest.getCapacity()) {
                contestDB.setCapacity(contest.getCapacity());
            } else {
                if(contestDB.getTeams().size() <= contest.getCapacity()) {
                    contestDB.setCapacity(contest.getCapacity());
                } else {
                    return new ResponseEntity<>("Contest - Contest capacity should be greater than " +
                            "or equal to the number of currently registered teams.",
                            HttpStatus.BAD_REQUEST);
                }
            }
        }

        /*
            Check managers.
         */
        if(contest.getManagers().size() != 0) {
            return new ResponseEntity<>("Contest - Managers can not be edited.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Check preliminary contest.
         */
        if(contest.getPreliminary_contest() != null) {
            return new ResponseEntity<>("Contest - Preliminary contest can not be edited.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Check teams.
         */
        if(contest.getTeams().size() != 0) {
            return new ResponseEntity<>("Contest - Teams can not be edited.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Update the date.
         */
        if(contest.getDate() != null) {
            contestDB.setDate(contest.getDate());
        }

        /*
            Update the name.
         */
        if(contest.getName() != null) {
            contestDB.setName(contest.getName());
        }

        /*
            Update the registration_allowed.
         */
        if(contest.getRegistration_allowed() != null) {
            contestDB.setRegistration_allowed(contest.getRegistration_allowed());
        }

        /*
            Update the registration_from.
         */
        if(contest.getRegistration_from() != null) {
            contestDB.setRegistration_from(contest.getRegistration_from());
        }

        /*
            Update the registration_to.
         */
        if(contest.getRegistration_to() != null) {
            contestDB.setRegistration_to(contest.getRegistration_to());
        }

        return new ResponseEntity<>(this.save(contestDB), HttpStatus.OK);
    }

    public ResponseEntity<?> promote(Long superContestID, Long teamID) {
        /*
            superContestID and teamID can not be null.
         */
        if (superContestID == null) {
            return new ResponseEntity<>("Contest - SuperContestId can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        if(teamID == null) {
            return new ResponseEntity<>("Contest - TeamId can not be null.",
                    HttpStatus.BAD_REQUEST);
        }

        Contest contest = this.findById(superContestID);
        Team team = teamService.findById(teamID);

        /*
            Contest and Team should exist.
         */
        if(contest == null) {
            return new ResponseEntity<>("Contest - Contest should exist in the database.",
                    HttpStatus.BAD_REQUEST);
        }

        if(team == null) {
            return new ResponseEntity<>("Contest - Team should exist in the database.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Preliminary contest can not be null.
         */
        if(contest.getPreliminary_contest() == null) {
            return new ResponseEntity<>("Contest - There should exist a preliminary contest.",
                    HttpStatus.BAD_REQUEST);
        }

        /*
            Team should exist in the preliminary contest.
         */
        boolean found = false;
        for(Team team1: contest.getPreliminary_contest().getTeams()) {
            if(team1.getId().equals(teamID)) {
                found = true;
                if(team1.getRank() == null) {
                    return new ResponseEntity<>("Contest - Team rank in the preliminary contest can not be null.",
                            HttpStatus.BAD_REQUEST);
                }
                if(team1.getRank() > Constants.RANK_TO_PROMOTE) {
                    return new ResponseEntity<>("Contest - Team rank should be in between 1 to 5.",
                            HttpStatus.BAD_REQUEST);
                }
                break;
            }
        }

        if(!found) {
            return new ResponseEntity<>("Contest - Team should exist in the preliminary contest.",
                    HttpStatus.BAD_REQUEST);
        }

        Team clone_team = team.clone();
        return this.contestRegistration(superContestID, clone_team, true);
//        teamService.save(clone_team);
//        Contest ret_contest = this.contestRegistration(superContestID, clone_team.getId());
//        if(ret_contest == null) {
//            teamService.deleteById(clone_team.getId());
//        }
//        return ret_contest;
    }
}
