package saha.swapnil.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import saha.swapnil.jpa.Constants;
import saha.swapnil.jpa.model.Person;
import saha.swapnil.jpa.repository.PersonRepository;

import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.regex.*;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public String validateNewPerson(Person person) {
        if(person.getId() != null) {
            return "New Person's id should be null.";
        }

        if(person.getName() == null) {
            return "Name can not be null.";
        }

        if(person.getEmail() == null) {
            return "Email can not be null.";
        }

        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        if(!pattern.matcher(person.getEmail()).matches()) {
            return "Email format not correct.";
        }

        if(personRepository.findByEmail(person.getEmail()) != null) {
            return "Email already exists.";
        }

        if(person.getUniversity() == null) {
            return "University can not be null.";
        }

        if(person.getBirthdate() == null) {
            return "Birthdate can not be null.";
        }

        if(person.getPersonType() != null && person.getPersonType() != Person.PersonType.NOT_DEFINED) {
            return "Person type should be null or NOT_DEFINED";
        }

        return Constants.NO_ERROR;
    }

    public ResponseEntity<?> saveNewPerson(Person person) {
        String error_res = validateNewPerson(person);
        if(error_res.equals(Constants.NO_ERROR)) {
            person.setPersonType(Person.PersonType.NOT_DEFINED);
            return new ResponseEntity<>(personRepository.save(person), HttpStatus.OK);
        }
        return new ResponseEntity<>(error_res, HttpStatus.BAD_REQUEST);
    }

    public Person findByName(String name) {
        return personRepository.findByName(name);
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }
}
