package com.nighthawk.spring_portfolio.mvc.nutrition;

// same as APCalendar
// fetch edmame API here

public class FoodAPI {
    /** takes food name as input, gets int calories from API */          
    public static int findCalories(String food) {
        // implementation not shown

        return 0;
    }
        
    
    /** Tester method */
    public static void main(String[] args) {
        // Public access modifiers
        System.out.println("isLeapYear: " + FoodAPI.findCalories("apple"));
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
