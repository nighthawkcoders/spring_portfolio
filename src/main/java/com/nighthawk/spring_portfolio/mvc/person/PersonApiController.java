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

/**
 * This class provides RESTful API endpoints for managing Person entities.
 * It includes endpoints for creating, retrieving, updating, and deleting Person entities.
 */
@RestController
@RequestMapping("/api")
public class PersonApiController {
    /*
    #### RESTful API REFERENCE ####
    Resource: https://spring.io/guides/gs/rest-service/
    */

    /**
     * Repository for accessing Person entities in the database.
     */
    @Autowired
    private PersonJpaRepository repository;

    /**
     * Service for managing Person entities.
     */
    @Autowired
    private PersonDetailsService personDetailsService;

    /**
     * Retrieves a Person entity by current user of JWT token.
     * @return A ResponseEntity containing the Person entity if found, or a NOT_FOUND status if not found.
     */
    @GetMapping("/person")
    public ResponseEntity<Person> getPerson(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();  // Email is mapped/unmapped to username for Spring Security

        // Find a person by username
        Person person = repository.findByEmail(email);

        // Return the person if found
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Retrieves all the Person entities in the database, people
     * @return A ResponseEntity containing a list for Person entities 
     */
    @GetMapping("/people")
    public ResponseEntity<List<Person>> getPeople() {
        return new ResponseEntity<>( repository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    /**
     * Retrieves a Person entity by its ID.
     *
     * @param id The ID of the Person entity to retrieve.
     * @return A ResponseEntity containing the Person entity if found, or a NOT_FOUND status if not found.
     */
    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID
            return new ResponseEntity<>(person, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);       
    }

    /**
     * Delete a Person entity by its ID.
     *
     * @param id The ID of the Person entity to delete.
     * @return A ResponseEntity containing the Person entity if deleted, or a NOT_FOUND status if not found.
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
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
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

    /**
     * Create a new Person entity.
     * @param personDto
     * @return A ResponseEntity containing a success message if the Person entity is created, or a BAD_REQUEST status if not created.
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

    /**
     * Search for a Person entity by name or email.
     * @param map of a key-value (k,v), the key is "term" and the value is the search term. 
     * @return A ResponseEntity containing a list of Person entities that match the search term.
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

    /**
     * Adds stats to the Person table 
     * @param stat_map is a JSON object, example format:
        {"health":
            {"date": "2021-01-01",
            "measurements":
                {   
                    "weight": "150",
                    "height": "70",
                    "bmi": "21.52"
                }
            }
        }
    *  @return A ResponseEntity containing the Person entity with updated stats, or a NOT_FOUND status if not found.
    */
    @PostMapping(value = "/person/setStats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> personStats(Authentication authentication, @RequestBody final Map<String,Object> stat_map) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();  // Email is mapped/unmapped to username for Spring Security
    
        // Find a person by username
        Optional<Person> optional = Optional.ofNullable(repository.findByEmail(email));
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID
    
            // Get existing stats
            Map<String, Map<String, Object>> existingStats = person.getStats();
    
            // Iterate through each key in the incoming stats
            for (String key : stat_map.keySet()) {
                // Extract the stats for this key from the incoming stats
                Map<String, Object> incomingStats = (Map<String, Object>) stat_map.get(key);
    
                // Extract the date and attributes from the incoming stats
                String date = (String) incomingStats.get("date");
                Map<String, Object> attributeMap = new HashMap<>(incomingStats);
                attributeMap.remove("date");
    
                // If this key does not exist in the existing stats, add it
                if (!existingStats.containsKey(key)) {
                    existingStats.put(key, new HashMap<>());
                }
    
                // If this date already exists in the stats for this key, get the existing attributes for this date
                if (existingStats.get(key).containsKey(date)) {
                    Map<String, Object> existingAttributes = (Map<String, Object>) existingStats.get(key).get(date);
                    // Add the new attributes to the existing ones
                    existingAttributes.putAll(attributeMap);
                } else {
                    // If this date does not exist in the stats for this key, add the new date and attributes
                    existingStats.get(key).put(date, attributeMap);
                }
            }
    
            // Set the updated stats
            person.setStats(existingStats);
            repository.save(person);  // conclude by writing the stats updates
    
            // return Person with update Stats
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        // return Bad ID
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
    }
}
