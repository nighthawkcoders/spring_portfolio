package com.nighthawk.spring_portfolio.mvc.meal;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealApiController {

    private List<Meal> meals = new ArrayList<>();

    @PostMapping
    public Meal addMeal(@RequestBody Meal meal) {
        meals.add(meal);
        return meal;
    }

    @GetMapping
    public List<Meal> getMeals() {
        return meals;
    }

    @DeleteMapping("/{name}")
    public List<Meal> deleteMeal(@PathVariable String name) {
        meals.removeIf(meal -> meal.getName().equals(name));
        return meals;
    }
}
