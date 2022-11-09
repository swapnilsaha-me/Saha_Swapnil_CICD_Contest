package saha.swapnil.jpa.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private Integer rank;

    private State state;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    @Size(min = 3, max = 3)
    private Set<Person> members = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "coach_id")
    @NotNull
    private Person coach;

    @ManyToOne
    private Team clone_from;

    private Boolean writable = true;

    public Team clone() {
        Team team = new Team();
        team.setName(this.getName());
        team.setRank(this.getRank());
        team.setState(this.getState());
        team.setCoach(this.getCoach());
        team.setState(this.getState());
        team.setClone_from(this);
        Set<Person> membersList = new HashSet<>();
        for(Person person : this.getMembers()) {
            membersList.add(person);
        }
        team.setMembers(membersList);
        return team;
    }

    public enum State {
        ACCEPTED, PENDING, CANCELED, NOT_DEFINED
    }

    public Team() {

    }

    public Team(Long id, String name, Integer rank, State state, Set<Person> members, Person coach, Team clone_from, Boolean writable) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.state = state;
        this.members = members;
        this.coach = coach;
        this.clone_from = clone_from;
        this.writable = writable;
    }
}
