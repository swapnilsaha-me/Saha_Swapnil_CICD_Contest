package saha.swapnil.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saha.swapnil.jpa.model.Person;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Long> {

    public Person findByName(String name);

    public Person findByEmail(String email);
}
