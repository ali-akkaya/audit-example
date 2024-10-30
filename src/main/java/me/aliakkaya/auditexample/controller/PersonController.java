package me.aliakkaya.auditexample.controller;


import lombok.AllArgsConstructor;
import me.aliakkaya.auditexample.entity.Person;
import me.aliakkaya.auditexample.repository.IPersonRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final IPersonRepository personRepository;



    @PostMapping("/create")
    public Person createPerson(@RequestBody Person person){

        Person personToSave = new Person();
        personToSave.setName(person.getName());
        personToSave.setSurname(person.getSurname());

        return personRepository.save(personToSave);
    }

    @PutMapping("/update")
    public Person updatePerson(@RequestBody Person person){

        Person personToSave = personRepository.findById(person.getId()).orElseThrow();

        if(!person.getName().isEmpty())
            personToSave.setName(person.getName());

        if(!person.getSurname().isEmpty())
            personToSave.setSurname(person.getSurname());

        return personRepository.save(personToSave);
    }

}
