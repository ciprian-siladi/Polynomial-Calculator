package com.example.polynomialcalculator.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {

    Polynomial p1 = new Polynomial("4x^5-3x^4+x^2-8.5x+1");
    Polynomial p2 = new Polynomial("3x^4-x^3+x^2+2x-1");

    @Test
    void add() {
        Polynomial sum = p1.add(p2);
        assertTrue(sum.getStringPolynomial().equals("+4X^5 -X^3 +2X^2 -6.5X"));
    }

    @Test
    void subtract() {
        Polynomial subtract = p1.subtract(p2);
        assertTrue(subtract.getStringPolynomial().equals("+4X^5 -6X^4 +X^3 -10.5X +2"));
    }

    @Test
    void multiply() {
        p1 = new Polynomial("-3.5x^2-X+1");
        p2 = new Polynomial("x-2");
        Polynomial multiply = p1.multiply(p2);
        assertTrue(multiply.getStringPolynomial().equals("-3.5X^3 +6X^2 +3X -2"));
    }

    @Test
    void divide() {
        p1 = new Polynomial("x^3-2x^2+6x-5");
        p2 = new Polynomial("x^2-1");
        ArrayList<Polynomial> division = p1.divide(p2);
        Polynomial quotient = division.get(0);
        Polynomial remainder = division.get(1);
        assertTrue(quotient.getStringPolynomial().equals("+X -2") && remainder.getStringPolynomial().equals("+7X -7"));
    }

    @Test
    void integrate() {
        p1 = new Polynomial("6x^5-3x^4+6x^2-7x+1");
        assertTrue(p1.integrate().getStringPolynomial().equals("+X^6 -0.6X^5 +2X^3 -3.5X^2 +X"));
    }

    @Test
    void derivative() {
        p1 = new Polynomial("6x^5-3x^4+6x^2-7x+1");
        assertTrue(p1.derivative().getStringPolynomial().equals("+30X^4 -12X^3 +12X -7"));
    }
}