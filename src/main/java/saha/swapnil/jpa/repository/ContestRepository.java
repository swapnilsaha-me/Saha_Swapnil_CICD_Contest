package saha.swapnil.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saha.swapnil.jpa.model.Contest;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ContestRepository extends JpaRepository<Contest, Long> {

    public Contest findByName(String contestName);

}
