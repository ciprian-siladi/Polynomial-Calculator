package com.example.polynomialcalculator.Model;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

public class Monomial {
    private int power;
    private Number coefficient;

    public Monomial() {
        this.power = 0;
        this.coefficient = 0;
    }

    public Number getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Number coefficient) {
        this.coefficient = coefficient;
    }

    public Monomial(int power, Number coefficient) {
        this.power = power;
        this.coefficient = coefficient;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void displayMonomial() {
        if (coefficient.doubleValue() > 0)
            if (power != 0)
                System.out.print("+" + coefficient + "X^" + power + " ");
            else
                System.out.print("+" + coefficient);
        else
            if (power != 0)
                System.out.print(coefficient + "X^" + power + " ");
            else
                System.out.print(coefficient);
    }

    public String getStringMonomial() {
        String string = "";

        if(coefficient.doubleValue() == coefficient.intValue()){
            if (coefficient.intValue() > 0)
                if (power != 0)
                    if(power == 1){
                        if(coefficient.intValue() == 1) string = ("+X ");
                        else string = ("+" + coefficient.intValue() + "X ");
                    }
                    else{
                        if(coefficient.intValue() == 1) string = ("+X^" + power + " ");
                        else string = ("+" + coefficient.intValue() + "X^" + power + " ");
                    }
                else
                    string = ("+" + coefficient.intValue());
            else if(coefficient.intValue() < 0)
                if (power != 0)
                    if(power == 1){
                        if(coefficient.intValue() == -1) string = ("-X ");
                        else string = (coefficient.intValue() + "X ");
                    }
                    else{
                        if(coefficient.intValue() == -1) string = ("-X^" + power + " ");
                        else string = (coefficient.intValue() + "X^" + power + " ");
                    }

                else
                    string = (String.valueOf(coefficient.intValue()));
        }
        else {
            if (coefficient.doubleValue() > 0)
                if (power != 0)
                    if(power == 1){
                        if(coefficient.doubleValue() == 1.0) string = ("+X ");
                        else string = ("+" + coefficient.doubleValue() + "X ");
                    }
                    else {
                        if(coefficient.doubleValue() == 1.0) string = ("+X^" + power + " ");
                        else string = ("+" + coefficient.doubleValue() + "X^" + power + " ");
                    }
                else
                    string = ("+" + coefficient.doubleValue());
            else if(coefficient.doubleValue() < 0)
                if (power != 0)
                    if(power == 1){
                        if(coefficient.doubleValue() == -1.0) string = ("-X ");
                        else string = (coefficient.doubleValue() + "X ");
                    }
                    else {
                        if(coefficient.doubleValue() == -1.0) string = ("-X^" + power + " ");
                        else string = (coefficient.doubleValue() + "X^" + power + " ");
                    }
                else
                    string = (coefficient.toString());
        }
        return string;
    }
}
