package com.nighthawk.hacks.streamChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FruitBasket {
    private List<Fruit> fruits = new ArrayList<>();

    public FruitBasket addFruit(Fruit fruit) {
        this.fruits.add(fruit);
        return this;
    }

    public List<Fruit> filterByClimate(Climate climate) {
        return fruits.stream()
            .filter(fruit -> fruit.getClimate() == climate)
            .collect(Collectors.toList());
    }

    public List<Fruit> filterByState(State state) {
        return fruits.stream()
            .filter(fruit -> state.getClimates().contains(fruit.getClimate()))
            .collect(Collectors.toList());
    }

    public List<State> whereCanIGrow(String fruitName) {
        return Arrays.stream(State.values())
            .filter(state -> fruits.stream()
                .anyMatch(fruit -> fruit.getName().equals(fruitName) && state.getClimates().contains(fruit.getClimate())))
            .collect(Collectors.toList());
    }

    public List<Climate> whatClimateCanIGrow(String fruitName) {
        return Arrays.stream(Climate.values())
            .filter(climate -> fruits.stream()
                .anyMatch(fruit -> fruit.getName().equals(fruitName) && fruit.getClimate() == climate))
             .collect(Collectors.toList());
    }

    public String toString() {
        return fruits.toString();
    }

    public static void main(String[] args) {
        FruitBasket basket = new FruitBasket();
        // Add fruits to the collection using chaining
        basket
            .addFruit(new Fruit("Apple", Climate.TEMPERATE))
            .addFruit(new Fruit("Banana", Climate.TROPICAL))
            .addFruit(new Fruit("Cherry", Climate.TEMPERATE))
            .addFruit(new Fruit("Orange", Climate.SUBTROPICAL))
            .addFruit(new Fruit("Peach", Climate.SUBTROPICAL))
            .addFruit(new Fruit("Pear", Climate.TEMPERATE))
            .addFruit(new Fruit("Pineapple", Climate.TROPICAL))
            ;

        // Print Tropical fruits using a foreach loop
        System.out.println(Climate.TROPICAL + "  fruits are:");
        for (Fruit fruit : basket.filterByClimate(Climate.TROPICAL)) {
            System.out.println(fruit);
        }

        // Print Subtropical fruits using a foreach method and lambda expression
        System.out.println(State.CALIFORNIA + " fruits are: ");
        List<Fruit> subtropicalFruits = basket.filterByState(State.CALIFORNIA);
        subtropicalFruits.forEach(fruit -> System.out.println(fruit));
       
        // Print where Oranges grow with shorthand with method reference
        System.out.println("Where can I grow an Orange?");
        basket.whereCanIGrow("Orange").forEach(System.out::println);

        // Print climate that grow Apples with method reference
        System.out.println("What climate can I grow an Apple?");
        basket.whatClimateCanIGrow("Apple").forEach(System.out::println);
    }
}
