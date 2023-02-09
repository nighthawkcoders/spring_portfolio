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
    public ResponseEntity<JsonNode> findCalories(@PathVariable String food) throws JsonMappingException, JsonProcessingException {
        // Backend Food Object
        Food food_obj = new Food();
        food_obj.setFood(food);  // evaluates Leap Year
      
        // Turn Food Object into JSON
        ObjectMapper mapper = new ObjectMapper(); 
        JsonNode json = mapper.readTree(food_obj.findCaloriesToString()); // this requires exception handling
      
        return ResponseEntity.ok(json);  // JSON response, see ExceptionHandlerAdvice for throws
    }
}




/* 
if(foodName != null && foodName != "") {

    URL url = new URL("https://api.nutritionix.com/v1_1/search/" + foodName + "?results=0:1&fields=*&appId=8c64d3f3&appKey=cf6e93bfdd30f35a3064a9ad0ae2c250");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    // Check if the API request is successful
    if (conn.getResponseCode() == 200) {
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        // Read the response from the API
        while ((line = br.readLine()) != null) {
        sb.append(line);
        }
        // Parse the response to get the nutrition information
        String response = sb.toString();
        int caloriesIndex = response.indexOf("nf_calories");
        String calories = response.substring(caloriesIndex + 14, caloriesIndex + 18);
        System.out.println("Calories in an apple: " + calories);
    } else {
        System.out.println("Error in API request: " + conn.getResponseCode());
    }
    conn.disconnect();
} 
catch (Exception e) {
    System.out.println("Error: " + e.getMessage());
}

*/


