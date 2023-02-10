package com.nighthawk.spring_portfolio.mvc.nutrition;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController // annotation to create a RESTful web services
@RequestMapping("/api/nutrition")  //prefix of API
public class NutritionApiController {

    /** GET getCalories endpoint
     * ObjectMapper throws exceptions on bad JSON
     *  @throws JsonProcessingException
     *  @throws JsonMappingException
     */

    @GetMapping("/findCalories/{food}")
    public ResponseEntity<JsonNode> getFindCalories(@PathVariable String food) throws JsonMappingException, JsonProcessingException 
    {
        // Backend Food Object
        Food food_obj = new Food();
        food_obj.setFood(food);  // evaluates Leap Year
      
        // Turn Food Object into JSON
        ObjectMapper mapper = new ObjectMapper(); 
        JsonNode json = mapper.readTree(food_obj.findCaloriesToString()); // this requires exception handling
      
        return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
    }
}






