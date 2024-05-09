package com.nighthawk.spring_portfolio.hacks.calculator;

import java.util.function.BiFunction;

/**
 * Term class is a subclass of Token
 * Term is used to define the parts of a mathematical expression
 * - Operators.  define the operator token, precedence, and mathematical calculation
 * - Parenthesis.  used to group terms
 * - Value.  defines a string representation of a value in the expression
 * 
 * A Term can be an operator, parenthesis, space, or value 
 */
public class Term extends Token {
    private final String value;

    public Term(Character token) {
        super(token, 0);
        this.value = null;
    } 

    public Term(String value) {
        super();
        this.value = value;
    }

    public Term(Character token, int precedence, BiFunction<Double, Double, Double> calculation) {
        super(token, precedence, calculation);
        this.value = null;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        if (this.value == null) {
            return super.toString();
        }
        return this.value;
    }   
}