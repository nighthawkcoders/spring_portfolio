package com.nighthawk.spring_portfolio.mvc.nutrition;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.util.*;

// same as APCalendar
// fetch edmame API here

public class FoodAPI {
    /** takes food name as input, gets int calories from API */          
    public static double findCalories(String food) {
        String calories = "";
        if(food != null && food != "") {
            try{
                URL url = new URL("https://api.nutritionix.com/v1_1/search/" + food + "?results=0:1&fields=*&appId=8c64d3f3&appKey=cf6e93bfdd30f35a3064a9ad0ae2c250");
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
                    int endCaloriesIndex = response.indexOf("nf_calories_from_fat");

                    calories = response.substring(caloriesIndex + 13, endCaloriesIndex -1);
                    //System.out.println("Calories in "+food+": " + calories);
                    
                } else {
                    System.out.println("Error in API request: " + conn.getResponseCode());
                }
                //conn.disconnect();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } 
        return Double.parseDouble(calories);

    }
        
    
    /** Tester method */
    public static void main(String[] args) {
        // Public access modifiers

        System.out.println(String.valueOf(FoodAPI.findCalories("cupcake")));
        //String cal = Integer.toString(FoodAPI.findCalories("potato"));
        //System.out.println("Calories: " + cal);
    }
}



// turn string into dictionary
/*Map<String, String> hashMap = new HashMap<String, String>();
String parts[] = response.split(",");
for (String part : parts) {
    String responseData[] = part.split(":");
            
    String key = responseData[0].trim();
    String value = responseData[1].trim();
            
    hashMap.put(key, value);
}
calories = hashMap.get("nf_calories");
*/

 





