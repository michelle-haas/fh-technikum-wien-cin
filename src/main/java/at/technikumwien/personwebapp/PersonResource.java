package at.technikumwien.personwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// see http://localhost:8081/resources/persons
@RestController
@RequestMapping("/resources/persons")
public class PersonResource {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/{id}") // http://localhost:8081/resources/persons/1
    public Person retrieve(@PathVariable long id ) {
        return personRepository.findById(id).get(); // get() müssen wir aufrufen, weil ein 'optional' zurückkommt
    }

    @GetMapping
    public List<Person> retrieveAll(
            @RequestParam(name = "all", defaultValue = "false") boolean all)
    {
        if(all){
            return personRepository.findAll();
        }
        // Spring wandelt die Daten automatisch in ein JSON um
        return personRepository.findAllByActiveTrue();
    }
}
