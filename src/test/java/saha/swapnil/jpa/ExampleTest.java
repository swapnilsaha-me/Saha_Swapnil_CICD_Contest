package saha.swapnil.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import saha.swapnil.jpa.model.Contest;
import saha.swapnil.jpa.model.Person;
import saha.swapnil.jpa.model.Team;
import saha.swapnil.jpa.services.ContestService;
import saha.swapnil.jpa.services.PersonService;
import saha.swapnil.jpa.services.TeamService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static saha.swapnil.jpa.model.Person.PersonType.*;
import static saha.swapnil.jpa.model.Person.PersonType.MANAGER;

@SpringBootTest
@RunWith( SpringRunner.class )
public class ExampleTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ContestService contestService;

    @Test
    public void demoTest1() {
        populate();
        Contest contest = contestService.findByName("ACM ICPC");
        printTeamNameRecursively(contest);
    }

    void printTeamNameRecursively(Contest contest) {
        if(contest == null) {
            return;
        }
        for(Team team : contest.getTeams()) {
            System.out.println(team.getName());
        }
        printTeamNameRecursively(contest.getPreliminary_contest());
    }

    @Test
    public void demoTest2() {
        populate();
        List<Person> personList = personService.findAll();
        HashMap<Integer, Integer> group = new HashMap<>();
        LocalDate today = LocalDate.now();
        for(Person person : personList) {
            Date birthDate = person.getBirthdate();
            LocalDate localBirthDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Integer year = Period.between(localBirthDate, today).getYears();
            if(person.getPersonType() == CONTESTANT) {
                if(group.get(year) == null) {
                    group.put(year, 1);
                } else {
                    group.put(year, group.get(year) + 1);
                }
            }
        }
        for(Integer key : group.keySet()) {
            System.out.println("Age " + key + " has " + group.get(key) + " students");
        }
    }

    @Test
    public void demoTest3() {
        populate();
        List<Contest> contestList = contestService.findAll();
        for(Contest contest : contestList) {
            System.out.println(contest.getName() + " - Total Teams = " + contest.getTeams().size() + ", Capacity = " + contest.getCapacity());
            assertThat(contest.getTeams().size()).isLessThanOrEqualTo(contest.getCapacity());
        }
    }

    public void populate() {
        /*
            Person Creation
         */
        List<Person> persons;
        try {
            persons = createPersons();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for (Person p : persons) {
            personService.save(p);
        }

        /*
            Team Creation
         */
        Team team1 = new Team();
        team1.setName("Chasing_Corner_Case");
        team1.getMembers().add(persons.get(0));
        team1.getMembers().add(persons.get(1));
        team1.getMembers().add(persons.get(2));
        team1.setCoach(persons.get(12));
        team1.setState(Team.State.ACCEPTED);
        team1.setRank(10);
        teamService.save(team1);

        Team team2 = new Team();
        team2.setName("DeadLock");
        team2.getMembers().add(persons.get(3));
        team2.getMembers().add(persons.get(4));
        team2.getMembers().add(persons.get(5));
        team2.setCoach(persons.get(13));
        team2.setState(Team.State.ACCEPTED);
        team2.setRank(15);
        teamService.save(team2);

        Team team3 = new Team();
        team3.setName("Newbie");
        team3.getMembers().add(persons.get(6));
        team3.getMembers().add(persons.get(7));
        team3.getMembers().add(persons.get(8));
        team3.setCoach(persons.get(12));
        team3.setState(Team.State.ACCEPTED);
        team3.setRank(25);
        teamService.save(team3);

        Team team4 = new Team();
        team4.setName("Fresher");
        team4.getMembers().add(persons.get(9));
        team4.getMembers().add(persons.get(10));
        team4.getMembers().add(persons.get(11));
        team4.setCoach(persons.get(13));
        team4.setState(Team.State.ACCEPTED);
        team4.setRank(35);
        teamService.save(team4);


        /*
            Contest Creation
         */
        Contest contest1 = new Contest();
        contest1.setName("NCPC");
        contest1.setCapacity(5);
        contest1.setRegistration_allowed(true);
        contest1.getManagers().add(persons.get(14));
        contest1.getManagers().add(persons.get(15));
        contest1.getTeams().add(team1);
        contest1.getTeams().add(team2);
        contest1.getTeams().add(team3);
        try {
            contest1.setDate(getDate("01-01-2020"));
            contest1.setRegistration_from(getDate("01-01-2019"));
            contest1.setRegistration_to(getDate("12-12-2019"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        contestService.save(contest1);

        Contest contest2 = new Contest();
        contest2.setName("ACM ICPC");
        contest2.setCapacity(5);
        contest2.setRegistration_allowed(true);
        contest2.getManagers().add(persons.get(14));
        contest2.getTeams().add(team4);
        try {
            contest2.setDate(getDate("01-01-2020"));
            contest2.setRegistration_from(getDate("01-01-2019"));
            contest2.setRegistration_to(getDate("12-12-2019"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        contest2.setPreliminary_contest(contest1);
        contestService.save(contest2);

        Team team5 = team1.clone();
        teamService.save(team5);
    }

    private List<Person> createPersons() throws ParseException {
        List<Person> personList = new ArrayList<>();

        Person person1 = new Person();
        person1.setName("Swapnil");
        person1.setEmail("swapnil@gmail.com");
        person1.setUniversity("Baylor");
        person1.setBirthdate(getDate("01-01-2000"));
        person1.setPersonType(CONTESTANT);
        personList.add(person1);

        Person person2 = new Person();
        person2.setName("Tasnim");
        person2.setEmail("tasnim@gmail.com");
        person2.setUniversity("Baylor");
        person2.setBirthdate(getDate("01-01-2001"));
        person2.setPersonType(CONTESTANT);
        personList.add(person2);

        Person person3 = new Person();
        person3.setName("Maknun");
        person3.setEmail("maknun@gmail.com");
        person3.setUniversity("Baylor");
        person3.setBirthdate(getDate("01-01-2002"));
        person3.setPersonType(CONTESTANT);
        personList.add(person3);

        Person person4 = new Person();
        person4.setName("Oshy");
        person4.setEmail("oshy@gmail.com");
        person4.setUniversity("Baylor");
        person4.setBirthdate(getDate("01-01-1999"));
        person4.setPersonType(CONTESTANT);
        personList.add(person4);

        Person person5 = new Person();
        person5.setName("Tushar");
        person5.setEmail("tushar@gmail.com");
        person5.setUniversity("Baylor");
        person5.setBirthdate(getDate("01-01-1999"));
        person5.setPersonType(CONTESTANT);
        personList.add(person5);

        Person person6 = new Person();
        person6.setName("Alamin");
        person6.setEmail("alamin@gmail.com");
        person6.setUniversity("Baylor");
        person6.setBirthdate(getDate("01-01-2000"));
        person6.setPersonType(CONTESTANT);
        personList.add(person6);

        Person person7 = new Person();
        person7.setName("Anik");
        person7.setEmail("anik@gmail.com");
        person7.setUniversity("Baylor");
        person7.setBirthdate(getDate("01-01-2005"));
        person7.setPersonType(CONTESTANT);
        personList.add(person7);

        Person person8 = new Person();
        person8.setName("Uzzal");
        person8.setEmail("uzzal@gmail.com");
        person8.setUniversity("Baylor");
        person8.setBirthdate(getDate("01-01-2004"));
        person8.setPersonType(CONTESTANT);
        personList.add(person8);

        Person person9 = new Person();
        person9.setName("Hamza");
        person9.setEmail("hamza@gmail.com");
        person9.setUniversity("Baylor");
        person9.setBirthdate(getDate("01-01-2006"));
        person9.setPersonType(CONTESTANT);
        personList.add(person9);

        Person person10 = new Person();
        person10.setName("Alice");
        person10.setEmail("alice@gmail.com");
        person10.setUniversity("Baylor");
        person10.setBirthdate(getDate("01-01-2008"));
        person10.setPersonType(CONTESTANT);
        personList.add(person10);

        Person person11 = new Person();
        person11.setName("Bob");
        person11.setEmail("bob@gmail.com");
        person11.setUniversity("Baylor");
        person11.setBirthdate(getDate("01-01-2009"));
        person11.setPersonType(CONTESTANT);
        personList.add(person11);

        Person person12 = new Person();
        person12.setName("Curl");
        person12.setEmail("curl@gmail.com");
        person12.setUniversity("Baylor");
        person12.setBirthdate(getDate("01-01-2007"));
        person12.setPersonType(CONTESTANT);
        personList.add(person12);

        Person person13 = new Person();
        person13.setName("DMRH");
        person13.setEmail("dmrh@gmail.com");
        person13.setUniversity("Baylor");
        person13.setBirthdate(getDate("01-01-1985"));
        person13.setPersonType(COACH);
        personList.add(person13);

        Person person14 = new Person();
        person14.setName("TJ");
        person14.setEmail("tj@gmail.com");
        person14.setUniversity("Baylor");
        person14.setBirthdate(getDate("01-01-1985"));
        person14.setPersonType(COACH);
        personList.add(person14);

        Person person15 = new Person();
        person15.setName("DAWR");
        person15.setEmail("dawr@gmail.com");
        person15.setUniversity("Baylor");
        person15.setBirthdate(getDate("01-01-1985"));
        person15.setPersonType(MANAGER);
        personList.add(person15);

        Person person16 = new Person();
        person16.setName("MHAK");
        person16.setEmail("mhak@gmail.com");
        person16.setUniversity("Baylor");
        person16.setBirthdate(getDate("01-01-1985"));
        person16.setPersonType(MANAGER);
        personList.add(person16);

        return personList;
    }

    private Date getDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        return dateFormat.parse(date);
    }
}
