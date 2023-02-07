package com.nighthawk.spring_portfolio.mvc.nutrition;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // annotation to create a RESTful web services
@RequestMapping("/api/nutData")  //prefix of API
public class NutritionApiController {
    private JSONObject body; //last run result

    // GET Covid 19 Stats
    @GetMapping("/food")   //added to end of prefix as endpoint
    public ResponseEntity<JSONObject> getNutrition(String foodName) {

        
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
    }
}/
\




/*import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NutritionApiController {

    public static void main(String[] args) {
        try {
            // API Key for Nutritionix API
            //String apiKey = "YOUR_API_KEY";
            // URL for Nutritionix API
            URL url = new URL("https://api.nutritionix.com/v1_1/search/apple?results=0:1&fields=*&appId=8c64d3f3&appKey=cf6e93bfdd30f35a3064a9ad0ae2c250");
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
    }
}
*/