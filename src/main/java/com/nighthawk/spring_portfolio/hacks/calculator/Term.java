package com.nighthawk.spring_portfolio.hacks.calculator;

import java.util.function.BiFunction;

public class Term extends Token {
    private final String expression;

    public Term(Character token, int precedence, BiFunction<Double, Double, Double> calculation) {
        super(token, precedence, calculation);
        this.expression = null;
    }

    public Term(Character token, int precedence) {
        super(token, precedence);
        this.expression = null;
    }

    public Term(Character token) {
        super(token, 0);
        this.expression = null;
    } 

    public Term(String expression) {
        super();
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public String toString() {
        if (this.expression == null) {
            return super.toString();
        }
        return this.expression;
    }   
}