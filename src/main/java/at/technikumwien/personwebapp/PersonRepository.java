package at.technikumwien.personwebapp;

import at.technikumwien.personwebapp.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    // wir müssen hier gar nichts reinprogrammieren
    // dadurch, dass wir von JpaRepo erben, bekommen wir schon Funktionalität
    // JpaRepository ist von Spring

    // wenn diese Methode aufgerufen wird, wird im Hintergrund eine SQL Query gemacht
    List<Person> findAllByActiveTrue(); // aufs Naming achten

    // Komplexes Beispiel:
    // List<Person> findAllByLastNameAndFirstNameOrderByLastNameAsc(String ln, String fn);
}
