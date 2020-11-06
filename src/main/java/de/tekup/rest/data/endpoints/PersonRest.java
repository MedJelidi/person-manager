package de.tekup.rest.data.endpoints;

import de.tekup.rest.data.models.AddressEntity;
import de.tekup.rest.data.models.PersonEntity;
import de.tekup.rest.data.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonRest {

    private final PersonService personService;

    @Autowired
    public PersonRest(PersonService personService) {
        super();
        this.personService = personService;
    }

    @GetMapping("/address")
    public List<AddressEntity> getAllAddress() {
        return this.personService.getAllAddressEntities();
    }

    @GetMapping
    public List<PersonEntity> getPersons() {
        return this.personService.getAllPersonEntities();
    }

    @GetMapping("/{id}")
    public PersonEntity getPerson(@PathVariable("id") long id) {
        return this.personService.getPersonEntityById(id);
    }

    @GetMapping("/getByOperator/{operator}")
    public List<PersonEntity> getAllPersonsByOperator(@PathVariable("operator") String operator) {
        return this.personService.getAllPersonByPhoneOperator(operator);
    }

    @PostMapping
    public PersonEntity addPerson(@RequestBody PersonEntity personEntity) {
        return this.personService.createPersonEntity(personEntity);
    }

    @DeleteMapping("/{id}")
    public PersonEntity deletePerson(@PathVariable("id") long id) {
        return this.personService.deletePersonEntity(id);
    }

    @PutMapping("/{id}")
    public PersonEntity updatePerson(@RequestBody PersonEntity personEntity,
                                     @PathVariable("id") long id) {
        return this.personService.modifyPersonEntity(id, personEntity);
    }


}
