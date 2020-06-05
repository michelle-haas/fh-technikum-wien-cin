package at.technikumwien.personwebapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller // kein RestController, sondern ein normaler
public class IndexController {
    // das ist unser Controller im MVC
    // Controller wird beim Hochfahren von Spring erzeugt
   @Autowired
   private PersonRepository personRepository;

    @RequestMapping // der Standard-GET-Request wird auf die Methode gemappt
    // @RequestMapping("/") -> hängt sich an root (eh standardmäßig, deswegen kann man das weglassen)
    public String index(
            @RequestParam(defaultValue = "false") boolean all,
            Model model){ // uns wird ein model übergeben, das brauchen wir nur mehr befüllen
        var persons = (all ? personRepository.findAll()
                : personRepository.findAllByActiveTrue());

        // Model befüllen
        model.addAttribute("persons", persons);

        if(all) {
            // zusätzliches Attribute
            model.addAttribute("all", true);
        }

        // jetzt leiten wir weiter
        return "index"; // so heißt die View
    }
}