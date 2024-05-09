package com.nighthawk.spring_portfolio.hacks.calculator;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.HashMap;

public class Terms {
    private Map<Character, Term> map;

    public Terms() {
        this.map = new HashMap<>();
    }

    public void put(Character token, int precedence, BiFunction<Double, Double, Double> calculation) {
        this.map.put(token, new Term(token, precedence, calculation));
    }

    public void put(Character token, int precedence) {
        this.map.put(token, new Term(token, precedence));
    }

    public void put(Character token) {
        this.map.put(token, new Term(token));
    }

    public Term get(Character token) {
        return this.map.get(token);
    }

    public int getPrecedence(Character token) {
        return this.map.get(token).getPrecedence();
    }

    public boolean contains(Character token) {
        return this.map.containsKey(token);
    }

    public String toString() {
        return this.map.toString();
    }

}
