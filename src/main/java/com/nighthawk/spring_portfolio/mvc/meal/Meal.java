package com.nighthawk.spring_portfolio.mvc.meal;


public class Meal {
    private String name;
    private String description;
    private String day;
    private String meal;

    public Meal(String name, String description, String day, String meal) {
        this.name = name;
        this.description = description;
        this.day = day;
        this.meal = meal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }
    
    @Override
    public String toString() {
        return "Meal [name=" + name + ", description=" + description + ", day=" + day + ", meal=" + meal + "]";
    }

    public static void main(String[] args) {
        Meal meal1 = new Meal("Burger", "A beef patty served on a sesame seed bun.", "Monday", "Lunch");
        Meal meal2 = new Meal("Fried Rice", "Steamed rice stir-fried with vegetables and your choice of protein.", "Tuesday", "Dinner");

        System.out.println("Meal 1: " + meal1);
        System.out.println("Meal 2: " + meal2);
    }
}

    
