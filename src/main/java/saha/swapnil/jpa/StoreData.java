package saha.swapnil.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import saha.swapnil.jpa.model.Contest;
import saha.swapnil.jpa.model.Person;
import saha.swapnil.jpa.model.Team;
import saha.swapnil.jpa.services.ContestService;
import saha.swapnil.jpa.services.PersonService;
import saha.swapnil.jpa.services.TeamService;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static saha.swapnil.jpa.model.Person.PersonType.*;

//Spring annotations, feel free to ignore it
@Repository
@Transactional
public class StoreData {

    @Autowired
    private PersonService personService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ContestService contestService;

    public void populate() {
        
    }

//    public void populate() {
//        /*
//            Person Creation
//         */
//        List<Person> persons;
//        try {
//            persons = createPersons();
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//
//        for (Person p : persons) {
//            personService.save(p);
//        }
//
//        /*
//            Team Creation
//         */
//        Team team1 = new Team();
//        team1.setName("Chasing_Corner_Case");
//        team1.getMembers().add(persons.get(0));
//        team1.getMembers().add(persons.get(1));
//        team1.getMembers().add(persons.get(2));
//        team1.setCoach(persons.get(12));
//        team1.setState(Team.State.ACCEPTED);
//        team1.setRank(10);
//        teamService.save(team1);
//
//        Team team2 = new Team();
//        team2.setName("DeadLock");
//        team2.getMembers().add(persons.get(3));
//        team2.getMembers().add(persons.get(4));
//        team2.getMembers().add(persons.get(5));
//        team2.setCoach(persons.get(13));
//        team2.setState(Team.State.ACCEPTED);
//        team2.setRank(15);
//        teamService.save(team2);
//
//        Team team3 = new Team();
//        team3.setName("Newbie");
//        team3.getMembers().add(persons.get(6));
//        team3.getMembers().add(persons.get(7));
//        team3.getMembers().add(persons.get(8));
//        team3.setCoach(persons.get(12));
//        team3.setState(Team.State.ACCEPTED);
//        team3.setRank(25);
//        teamService.save(team3);
//
//        Team team4 = new Team();
//        team4.setName("Fresher");
//        team4.getMembers().add(persons.get(9));
//        team4.getMembers().add(persons.get(10));
//        team4.getMembers().add(persons.get(11));
//        team4.setCoach(persons.get(13));
//        team4.setState(Team.State.ACCEPTED);
//        team4.setRank(35);
//        teamService.save(team4);
//
//
//        /*
//            Contest Creation
//         */
//        Contest contest1 = new Contest();
//        contest1.setName("NCPC");
//        contest1.setCapacity(5);
//        contest1.setRegistration_allowed(true);
//        contest1.getManagers().add(persons.get(14));
//        contest1.getManagers().add(persons.get(15));
//        contest1.getTeams().add(team1);
//        contest1.getTeams().add(team2);
//        contest1.getTeams().add(team3);
//        try {
//            contest1.setDate(getDate("01-01-2020"));
//            contest1.setRegistration_from(getDate("01-01-2019"));
//            contest1.setRegistration_to(getDate("12-12-2019"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        contestService.save(contest1);
//
//        Contest contest2 = new Contest();
//        contest2.setName("ACM ICPC");
//        contest2.setCapacity(5);
//        contest2.setRegistration_allowed(true);
//        contest2.getManagers().add(persons.get(14));
//        try {
//            contest2.setDate(getDate("01-01-2020"));
//            contest2.setRegistration_from(getDate("01-01-2019"));
//            contest2.setRegistration_to(getDate("12-12-2019"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        contest2.setPreliminary_contest(contest1);
//        contestService.save(contest2);
//
//        Contest contest3 = new Contest();
//        contest3.setName("Baylor IUPC");
//        contest3.setCapacity(2);
//        contest3.setRegistration_allowed(true);
//        contest3.getManagers().add(persons.get(14));
//        try {
//            contest3.setDate(getDate("01-01-2020"));
//            contest3.setRegistration_from(getDate("01-01-2019"));
//            contest3.setRegistration_to(getDate("12-12-2019"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        contestService.save(contest3);
//
//
//        Team team5 = team1.clone();
//        teamService.save(team5);
//    }
//
//    private List<Person> createPersons() throws ParseException {
//        List<Person> personList = new ArrayList<>();
//
//        Person person1 = new Person();
//        person1.setName("Swapnil");
//        person1.setEmail("swapnil@gmail.com");
//        person1.setUniversity("Baylor");
//        person1.setBirthdate(getDate("01-01-2000"));
//        person1.setPersonType(STUDENT);
//        personList.add(person1);
//
//        Person person2 = new Person();
//        person2.setName("Tasnim");
//        person2.setEmail("tasnim@gmail.com");
//        person2.setUniversity("Baylor");
//        person2.setBirthdate(getDate("01-01-2001"));
//        person2.setPersonType(STUDENT);
//        personList.add(person2);
//
//        Person person3 = new Person();
//        person3.setName("Maknun");
//        person3.setEmail("maknun@gmail.com");
//        person3.setUniversity("Baylor");
//        person3.setBirthdate(getDate("01-01-2002"));
//        person3.setPersonType(STUDENT);
//        personList.add(person3);
//
//        Person person4 = new Person();
//        person4.setName("Oshy");
//        person4.setEmail("oshy@gmail.com");
//        person4.setUniversity("Baylor");
//        person4.setBirthdate(getDate("01-01-1999"));
//        person4.setPersonType(STUDENT);
//        personList.add(person4);
//
//        Person person5 = new Person();
//        person5.setName("Tushar");
//        person5.setEmail("tushar@gmail.com");
//        person5.setUniversity("Baylor");
//        person5.setBirthdate(getDate("01-01-1999"));
//        person5.setPersonType(STUDENT);
//        personList.add(person5);
//
//        Person person6 = new Person();
//        person6.setName("Alamin");
//        person6.setEmail("alamin@gmail.com");
//        person6.setUniversity("Baylor");
//        person6.setBirthdate(getDate("01-01-2000"));
//        person6.setPersonType(STUDENT);
//        personList.add(person6);
//
//        Person person7 = new Person();
//        person7.setName("Anik");
//        person7.setEmail("anik@gmail.com");
//        person7.setUniversity("Baylor");
//        person7.setBirthdate(getDate("01-01-2005"));
//        person7.setPersonType(STUDENT);
//        personList.add(person7);
//
//        Person person8 = new Person();
//        person8.setName("Uzzal");
//        person8.setEmail("uzzal@gmail.com");
//        person8.setUniversity("Baylor");
//        person8.setBirthdate(getDate("01-01-2004"));
//        person8.setPersonType(STUDENT);
//        personList.add(person8);
//
//        Person person9 = new Person();
//        person9.setName("Hamza");
//        person9.setEmail("hamza@gmail.com");
//        person9.setUniversity("Baylor");
//        person9.setBirthdate(getDate("01-01-2006"));
//        person9.setPersonType(STUDENT);
//        personList.add(person9);
//
//        Person person10 = new Person();
//        person10.setName("Alice");
//        person10.setEmail("alice@gmail.com");
//        person10.setUniversity("Baylor");
//        person10.setBirthdate(getDate("01-01-2008"));
//        person10.setPersonType(STUDENT);
//        personList.add(person10);
//
//        Person person11 = new Person();
//        person11.setName("Bob");
//        person11.setEmail("bob@gmail.com");
//        person11.setUniversity("Baylor");
//        person11.setBirthdate(getDate("01-01-2009"));
//        person11.setPersonType(STUDENT);
//        personList.add(person11);
//
//        Person person12 = new Person();
//        person12.setName("Curl");
//        person12.setEmail("curl@gmail.com");
//        person12.setUniversity("Baylor");
//        person12.setBirthdate(getDate("01-01-2007"));
//        person12.setPersonType(STUDENT);
//        personList.add(person12);
//
//        Person person13 = new Person();
//        person13.setName("DMRH");
//        person13.setEmail("dmrh@gmail.com");
//        person13.setUniversity("Baylor");
//        person13.setBirthdate(getDate("01-01-1985"));
//        person13.setPersonType(COACH);
//        personList.add(person13);
//
//        Person person14 = new Person();
//        person14.setName("TJ");
//        person14.setEmail("tj@gmail.com");
//        person14.setUniversity("Baylor");
//        person14.setBirthdate(getDate("01-01-1985"));
//        person14.setPersonType(COACH);
//        personList.add(person14);
//
//        Person person15 = new Person();
//        person15.setName("DAWR");
//        person15.setEmail("dawr@gmail.com");
//        person15.setUniversity("Baylor");
//        person15.setBirthdate(getDate("01-01-1985"));
//        person15.setPersonType(MANAGER);
//        personList.add(person15);
//
//        Person person16 = new Person();
//        person16.setName("MHAK");
//        person16.setEmail("mhak@gmail.com");
//        person16.setUniversity("Baylor");
//        person16.setBirthdate(getDate("01-01-1985"));
//        person16.setPersonType(MANAGER);
//        personList.add(person16);
//
//
//        /*
//            For testing.
//         */
//        Person person17 = new Person();
//        person17.setName("A1");
//        person17.setEmail("A1@gmail.com");
//        person17.setUniversity("Baylor");
//        person17.setBirthdate(getDate("01-01-2000"));
//        person17.setPersonType(NOT_DEFINED);
//        personList.add(person17);
//
//        Person person18 = new Person();
//        person18.setName("B2");
//        person18.setEmail("B2@gmail.com");
//        person18.setUniversity("Baylor");
//        person18.setBirthdate(getDate("01-01-2000"));
//        person18.setPersonType(NOT_DEFINED);
//        personList.add(person18);
//
//        Person person19 = new Person();
//        person19.setName("C3");
//        person19.setEmail("C3@gmail.com");
//        person19.setUniversity("Baylor");
//        person19.setBirthdate(getDate("01-01-2000"));
//        person19.setPersonType(NOT_DEFINED);
//        personList.add(person19);
//
//        Person person20 = new Person();
//        person20.setName("D4");
//        person20.setEmail("D4@gmail.com");
//        person20.setUniversity("Baylor");
//        person20.setBirthdate(getDate("01-01-2000"));
//        person20.setPersonType(NOT_DEFINED);
//        personList.add(person20);
//
//        Person person21 = new Person();
//        person21.setName("E5");
//        person21.setEmail("E5@gmail.com");
//        person21.setUniversity("Baylor");
//        person21.setBirthdate(getDate("01-01-2000"));
//        person21.setPersonType(NOT_DEFINED);
//        personList.add(person21);
//
//        Person person22 = new Person();
//        person22.setName("F6");
//        person22.setEmail("F6@gmail.com");
//        person22.setUniversity("Baylor");
//        person22.setBirthdate(getDate("01-01-2000"));
//        person22.setPersonType(NOT_DEFINED);
//        personList.add(person22);
//
//        Person person23 = new Person();
//        person23.setName("G7");
//        person23.setEmail("G7@gmail.com");
//        person23.setUniversity("Baylor");
//        person23.setBirthdate(getDate("01-01-2000"));
//        person23.setPersonType(NOT_DEFINED);
//        personList.add(person23);
//
//        Person person24 = new Person();
//        person24.setName("H8");
//        person24.setEmail("H8@gmail.com");
//        person24.setUniversity("Baylor");
//        person24.setBirthdate(getDate("01-01-1995"));
//        person24.setPersonType(NOT_DEFINED);
//        personList.add(person24);
//
//        Person person25 = new Person();
//        person25.setName("I9");
//        person25.setEmail("I9@gmail.com");
//        person25.setUniversity("Baylor");
//        person25.setBirthdate(getDate("01-01-1995"));
//        person25.setPersonType(NOT_DEFINED);
//        personList.add(person25);
//
//        return personList;
//    }
//
//    private Date getDate(String date) throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
//        return dateFormat.parse(date);
//    }
}
