package com.nighthawk.spring_portfolio.hacks.calculator;

import java.util.function.BiFunction;

public class Token {
    private final Character token;
    private final int precedence;
    private final BiFunction<Double, Double, Double> calculation;

    public Token() {
        this.token = 0; 
        this.precedence = -1;
        this.calculation = null;
    }

    public Token(Character token, int precedence) {
        this.token = token;
        this.precedence = precedence;
        this.calculation = null;
    }

    public Token(Character token, int precedence,  BiFunction<Double, Double, Double> calculation) {
        this.token = token;
        this.precedence = precedence;
        this.calculation = calculation;
    }

    public Character getToken() {
        return token;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean isPrecedent(Token token) {
        return this.precedence >= token.getPrecedence();
    }

    public Double calculate(Double operand1, Double operand2) {
        return this.calculation.apply(operand1, operand2);
    }
    
}
