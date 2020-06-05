package at.technikumwien.personwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.List;

@Configuration
@Profile("test") // diese Klasse wird nur ausgeführt, wenn das Profile "test" ist
public class TestDBInitializer {
    // mit diesem TestDBInitializer spielen wir Testdaten in unsere Testdatenbank rein

    @Autowired
    private PersonRepository personRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void handleApplicationEvent(){
        personRepository.saveAll(
                List.of(
                        new Person(
                                null,
                                Sex.MALE,
                                "Markus",
                                "Mustermann",
                                LocalDate.of(1990, 1, 1),
                                true
                        ),
                        new Person(
                                null,
                                Sex.FEMALE,
                                "Martina",
                                "Musterfrau",
                                LocalDate.of(1995, 1, 1),
                                false
                        )
                )
        );
    }
}
