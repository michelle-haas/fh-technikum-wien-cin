package at.technikumwien.personwebapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

// alle Annotation, die man über die Klasse schreibt, betreffen dann alle Membervariablen
@Data // kurz für: @Getter @Setter @EqualsAndHashCode @ToString
@NoArgsConstructor // leerer Konstruktor
@AllArgsConstructor // Konstruktor, der alle Membervariablen übernimmt
@Entity // wenn man @Entity verwendet, muss unten ein @Id haben
@Table(name = "t_person") // Fehler ist ok
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Long, weil die id auch null sein könnte

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Sex sex;

    @Column(nullable = true, length = 50)
    private String firstName;

    @Column(nullable = true, length = 50)
    private String lastName;

    @Column(nullable = true)
    private LocalDate birthDate;

    @Column(nullable = true)
    private boolean active;

    @JsonIgnore // wird dann nicht in der JSON Response zurückgeschickt
    public String getName(){
        if(firstName == null || firstName.isBlank()){
            throw new IllegalArgumentException("first name is null or empty.");
        }

        if(lastName == null || lastName.isBlank()){
            throw new IllegalArgumentException("last name is null or empty.");
        }

        return firstName + " " + lastName;
    }
}
