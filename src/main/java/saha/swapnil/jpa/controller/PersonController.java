package saha.swapnil.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saha.swapnil.jpa.model.Person;
import saha.swapnil.jpa.services.PersonService;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/saveNewPerson", method = RequestMethod.POST)
    public ResponseEntity<?> saveNewPerson(@RequestBody Person person) {
        return personService.saveNewPerson(person);
    }

    @RequestMapping(value = "/findAllPerson", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> findAllPerson() {
        return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/personById", method = RequestMethod.GET)
    public ResponseEntity<Person> personById(@RequestParam(name = "id") Long personId) {
        return new ResponseEntity<>(personService.findById(personId), HttpStatus.OK);
    }
}
