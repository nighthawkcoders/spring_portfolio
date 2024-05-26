package com.nighthawk.spring_portfolio.mvc.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import lombok.Getter;

import java.util.*;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api")
public class PersonApiController {
    //     @Autowired
    // private JwtTokenUtil jwtGen;
    /*
    #### RESTful API ####
    Resource: https://spring.io/guides/gs/rest-service/
    */

    // Autowired enables Control to connect POJO Object through JPA
    @Autowired
    private PersonJpaRepository repository;

    @Autowired
    private PersonDetailsService personDetailsService;

    /*
    GET current Person 
     */
    @GetMapping("/person")
    public ResponseEntity<Person> getPerson(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        // Assuming you have a method in your repository to find a person by username
        Person person = repository.findByEmail(email);

        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /* 
    GET List of People
     */
    @GetMapping("/people")
    public ResponseEntity<List<Person>> getPeople() {
        return new ResponseEntity<>( repository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    /*
    GET individual Person using ID
     */
    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID
            return new ResponseEntity<>(person, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
    }

    /*
    DELETE individual Person using ID
     */
    @DeleteMapping("/person/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID
            repository.deleteById(id);  // value from findByID
            return new ResponseEntity<>(person, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }

    /* DTO (Data Transfer Object) to support POST request for postPerson method
       .. represents the data in the request body
     */
    @Getter 
    public static class PersonDto {
        private String email;
        private String password;
        private String name;
        private String dob;
    }

    /*
    POST Aa record by Requesting Parameters from URI
     */
    @PostMapping("/person")
    public ResponseEntity<Object> postPerson(@RequestBody PersonDto personDto) {
        // Validate dob input
        Date dob;
        try {
            dob = new SimpleDateFormat("MM-dd-yyyy").parse(personDto.getDob());
        } catch (Exception e) {
            return new ResponseEntity<>(personDto.getDob() + " error; try MM-dd-yyyy", HttpStatus.BAD_REQUEST);
        }
        // A person object WITHOUT ID will create a new record in the database
        Person person = new Person(personDto.getEmail(), personDto.getPassword(), personDto.getName(), dob, personDetailsService.findRole("USER"));
        personDetailsService.save(person);
        return new ResponseEntity<>(personDto.getEmail() + " is created successfully", HttpStatus.CREATED);
    }

    /*
    The personSearch API looks across database for partial match to term (k,v) passed by RequestEntity body
     */
    @PostMapping(value = "/people/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> personSearch(@RequestBody final Map<String,String> map) {
        // extract term from RequestEntity
        String term = (String) map.get("term");

        // JPA query to filter on term
        List<Person> list = repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);

        // return resulting list and status, error checking should be added
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /*
    The personStats API adds stats by Date to Person table 
    */
    @PostMapping(value = "/person/setStats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> personStats(@RequestBody final Map<String,Object> stat_map) {
        // find ID
        long id=Long.parseLong((String)stat_map.get("id"));  
        Optional<Person> optional = repository.findById((id));
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID

            // Extract Attributes from JSON
            Map<String, Object> attributeMap = new HashMap<>();
            for (Map.Entry<String,Object> entry : stat_map.entrySet())  {
                // Add all attribute other thaN "date" to the "attribute_map"
                if (!entry.getKey().equals("date") && !entry.getKey().equals("id"))
                    attributeMap.put(entry.getKey(), entry.getValue());
            }

            // Set Date and Attributes to SQL HashMap
            Map<String, Map<String, Object>> date_map = new HashMap<>();
            date_map.put( (String) stat_map.get("date"), attributeMap );
            person.setStats(date_map);  // BUG, needs to be customized to replace if existing or append if new
            repository.save(person);  // conclude by writing the stats updates

            // return Person with update Stats
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        // return Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }
}
