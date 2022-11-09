package saha.swapnil.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saha.swapnil.jpa.model.Team;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TeamRepository extends JpaRepository<Team, Long> {

    public Team findByName(String teamName);

}
