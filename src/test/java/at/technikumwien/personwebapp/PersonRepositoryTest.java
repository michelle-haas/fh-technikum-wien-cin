package at.technikumwien.personwebapp;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest // auch hier ist die Basis JUnit
// es wird für den Test die ganze DB hochgefahren, dadurch können wir die DB ordentlich testen.
@ActiveProfiles("test")
@Transactional // am Ende einer Methode wird ein DB rollback gemacht, damit man für jeden Test die gleichen Daten hat
//@Commit // die Änderungen in einem Test werden commited
@Tag("integration-test") // name ist egal, sollte aber immer gleich sein
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository; // dependency injection

    @Test // Integration Test, testet die DB
    public void testSave(){
        var person = new Person(
                null,
                Sex.MALE,
                "Maxi",
                "Musterkind",
                LocalDate.of(2010, 1, 1),
                true);
        personRepository.save(person);

        assertNotNull(person.getId());
        assertEquals(3, personRepository.count());


    }

    @Test
    public void testFindAllByActiveTrue(){
        var persons = personRepository.findAllByActiveTrue();

        assertEquals(1, persons.size());
    }

    // add more tests here

}
