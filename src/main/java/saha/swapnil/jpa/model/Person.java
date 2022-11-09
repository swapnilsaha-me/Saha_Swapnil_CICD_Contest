package saha.swapnil.jpa.model;

import lombok.Data;
import saha.swapnil.jpa.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "must contain valid email address")
    private String email;

    @Column(nullable = false)
    private String university;

    @NotNull
    private Date birthdate;

    @NotNull
    private PersonType personType;

    public enum PersonType {
        CONTESTANT, COACH, MANAGER, NOT_DEFINED
    }

    public Integer getAge() {
        if(this.birthdate == null) {
            return null;
        }
        LocalDate currDate = LocalDate.now();
        LocalDate dob = LocalDate.from(this.birthdate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        if(currDate == null || dob == null) {
            return Constants.INVALID_AGE;
        }
        if(currDate.isBefore(dob)) {
            return Constants.INVALID_AGE;
        }
        return Period.between(dob, currDate).getYears();
    }

    public Person() {

    }

    public Person(Long id, String name, String email, String university, Date birthdate, PersonType personType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.university = university;
        this.birthdate = birthdate;
        this.personType = personType;
    }
}
