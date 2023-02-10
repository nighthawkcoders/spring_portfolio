package com.nighthawk.spring_portfolio.mvc.nutrition;

public class Food {
    private String food;
    private double findCalories;

    // zero argument constructor
    public Food() {} 

    /* food getter/setters */
    public String getFood() {
        return food;
    }
    public void setFood(String food) {
        this.food = food;
        this.setFindCalories(food);
    }

    /* findCalories getter/setters */
    public double getFindCalories(String food) {
        return FoodAPI.findCalories(food);
    }
    private void setFindCalories(String Food) {  // this is private to avoid tampering
        this.findCalories = FoodAPI.findCalories(food);
    }

    /* findCaloriesToString formatted to be mapped to JSON */
    public String findCaloriesToString(){
        return ( "{ \"food\": "  +this.food+  ", " + "\"findCalories\": "  +this.findCalories+ " }" );
    }	

    /* standard toString placeholder until class is extended */
    public String toString() { 
        return findCaloriesToString(); 
    }

    /** Tester method */
    public static void main(String[] args) {
        Food food = new Food();
        food.setFood("apple");
        System.out.println(food);
    }
}
