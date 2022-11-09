package saha.swapnil.jpa.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer capacity;

    private Date date;

    @NotNull
    private String name;

    @NotNull
    private Boolean registration_allowed;

    private Date registration_from;

    private Date registration_to;

    @NotNull
    private Boolean writable = true;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Person> managers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Team> teams = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Contest preliminary_contest;
}
