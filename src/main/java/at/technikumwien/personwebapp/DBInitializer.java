package at.technikumwien.personwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.List;

@Configuration // oder @Component, eine @Configuration ist eine Art von @Component
// wenn man die Klasse nicht annotiert, wird sie nicht gefunden.
@Profile("default")
public class DBInitializer {

    @Autowired // das wird mit Dependency Injection erzeugt
    private PersonRepository personRepository;

    // ApplicationReadyEvent -> wenn die App fertig geladen ist, wird diese Methode aufgerufen
    @EventListener(ApplicationReadyEvent.class)
    public void handleApplicationEvent(){ // könnte auch foo() heißen
        // das personRepo wurde automatisch mittels DI erzeugt
        // außerdem hat es schon viel Funktionalität (vom JpaRepository)
        personRepository.saveAll(
                List.of(
                        new Person(
                                null,
                                Sex.MALE,
                                "Markus",
                                "Mustermann",
                                LocalDate.of(1990, 1, 1),
                                false
                        ),
                        new Person(
                                null,
                                Sex.FEMALE,
                                "Martina",
                                "Musterfrau",
                                LocalDate.of(1995, 1, 1),
                                true
                        )
                )
        );
    }
}
