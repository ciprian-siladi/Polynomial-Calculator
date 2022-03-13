package com.example.polynomialcalculator.Model;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.polynomialcalculator.Controller.MainController.createAlert;
import static com.example.polynomialcalculator.Controller.MainController.divisionWithZero;

public class Polynomial {
    private ArrayList<Monomial> polynom = new ArrayList<>();

    public Polynomial() {
        this.polynom = new ArrayList<>();
    }

    public ArrayList<Monomial> getPolynom() {
        return polynom;
    }

    public Polynomial(String s){
        try{
            s = s.toUpperCase(Locale.ROOT);
            String patternString = "-?\\d*X\\^?\\d*|-?\\d+\\.\\d+X\\^?\\d*|X\\^?\\d*|-?\\d+\\.\\d+|-?\\d+";
            Pattern wrongInsert = Pattern.compile("[A-V]|[YZ_=\\[{\\]}|;:'\",<>/?!@#$%&*()`~]|\\+{2,}|-{2,}|\\.{2,}|\\^{2,}|X{2,}");
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = wrongInsert.matcher(s);
            if(matcher.find()) throw new RuntimeException();
            matcher = pattern.matcher(s);
            while(matcher.find()) {

                String currentGroup = matcher.group();
                //System.out.println(currentString);

                List<String> cp = List.of(currentGroup.split("X\\^?|X?\\^"));
                // cp stands for current Polynomial group

                System.out.println();
                Monomial monomial;

                if (cp.size() == 0) monomial = new Monomial(1, 1);
                else if (cp.size() == 1 && cp.get(0).equals("-")) monomial = new Monomial(1, -1);
                else if (cp.size() > 1 && cp.get(0).equals("-")) monomial = new Monomial(Integer.parseInt(cp.get(1)), -1);
                else if (cp.size() > 1 && !cp.get(0).equals("")) monomial = new Monomial(Integer.parseInt(cp.get(1)), Double.parseDouble(cp.get(0)));
                else if (cp.get(0).equals("")) monomial = new Monomial(Integer.parseInt(cp.get(1)), 1);
                else if (cp.get(0).equals(currentGroup)) monomial = new Monomial(0, Double.parseDouble(cp.get(0)));
                else monomial = new Monomial(1, Double.parseDouble(cp.get(0)));

                this.polynom.add(monomial);
            }
        } catch (Exception e){
            createAlert();
        }
    }

    public void displayPolynomial() {
        this.getPolynom().forEach(Monomial::displayMonomial);
        System.out.println("");
    }

    public void sortByPower() {
        Collections.sort(polynom, (o1, o2) -> {
            if (o1.getPower() > o2.getPower())
                return -1;
            else if (o1.getPower() == o2.getPower())
                return 0;
            else
                return 1;
        });
    }

    public void simplifyPolynomial(){
        Iterator<Monomial> monomialIterator = this.getPolynom().iterator();
        Monomial monomial1 = monomialIterator.next();
        while(monomialIterator.hasNext()){
            Monomial monomial2 = monomialIterator.next();
            if(monomial1.getPower() == monomial2.getPower()) {
                monomial1.setCoefficient(monomial1.getCoefficient().doubleValue() + monomial2.getCoefficient().doubleValue());
                monomialIterator.remove();
            } else {
                monomial1 = monomial2;
            }
        }
    }

    public String getStringPolynomial(){
        StringBuilder resultString = new StringBuilder();
        this.simplifyPolynomial();
        this.sortByPower();
        this.getPolynom().forEach(monomial -> {
            resultString.append(monomial.getStringMonomial());
        });

        return resultString.toString().trim();
    }

    public Monomial findPower(int power) {
        Monomial resultMonomial = null;

        Iterator<Monomial> monomialIterator = this.getPolynom().iterator();
        while (monomialIterator.hasNext()) {
            Monomial currentMonomial = monomialIterator.next();
            if (currentMonomial.getPower() == power)
                resultMonomial = currentMonomial;
        }

        return resultMonomial;
    }

    public Polynomial duplicatePolynomial(Polynomial p) {
        Polynomial duplicatePolynomial = new Polynomial();
        p.getPolynom().forEach(monomial ->
                duplicatePolynomial.getPolynom().add(new Monomial(monomial.getPower(), monomial.getCoefficient())));

        return duplicatePolynomial;
    }

    public Monomial findNotZeroCoeff() {
        for(Monomial monomial : this.getPolynom()){
            if(monomial.getCoefficient().doubleValue() != 0)
                return monomial;
        }
        return new Monomial();
    }
    // ADDITION
    public Polynomial add(Polynomial p) {
        Polynomial resultPolynomial = duplicatePolynomial(this);

        p.getPolynom().forEach(monomial -> {
            int currentPower = monomial.getPower();
            Number currentCoefficient = monomial.getCoefficient();

            Monomial foundMonomial = resultPolynomial.findPower(currentPower);
            if (foundMonomial == null) {
                resultPolynomial.getPolynom().add(monomial);
            } else {
                Number oldCoefficient = foundMonomial.getCoefficient();
                foundMonomial.setCoefficient(currentCoefficient.doubleValue() + oldCoefficient.doubleValue());
            }
        });

        resultPolynomial.sortByPower();
        return resultPolynomial;
    }

    // SUBTRACT
    public Polynomial subtract(Polynomial p) {
        Polynomial resultPolynomial = duplicatePolynomial(this);

        p.getPolynom().forEach(monomial ->
        {
            int currentPower = monomial.getPower();
            Number currentCoefficient = monomial.getCoefficient();

            Monomial foundMonomial = resultPolynomial.findPower(currentPower);
            if (foundMonomial == null) {
                Monomial negativeMonomial = new Monomial(currentPower, -currentCoefficient.doubleValue());
                resultPolynomial.getPolynom().add(negativeMonomial);
            } else {
                Number oldCoefficient = foundMonomial.getCoefficient();
                foundMonomial.setCoefficient(oldCoefficient.doubleValue() - currentCoefficient.doubleValue());
            }
        });
        resultPolynomial.sortByPower();
        return resultPolynomial;
    }

    // MULTIPLICATION
    public Polynomial multiply(Polynomial p) {
        Polynomial resultPolynomial = new Polynomial();
        this.getPolynom().forEach(monomial1 -> {
            int power1 = monomial1.getPower();
            Number coefficient1 = monomial1.getCoefficient();

            p.getPolynom().forEach(monomial2 -> {
                int power2 = monomial2.getPower();
                Number coefficient2 = monomial2.getCoefficient();

                int resultPower = power1 + power2;
                Number resultCoefficient = coefficient1.doubleValue() * coefficient2.doubleValue();

                Monomial foundMonomial = resultPolynomial.findPower(resultPower);
                if (foundMonomial == null) {
                    resultPolynomial.getPolynom().add(new Monomial(resultPower, resultCoefficient));
                } else {
                    Number oldCoefficient = foundMonomial.getCoefficient();
                    foundMonomial.setCoefficient(oldCoefficient.doubleValue() + resultCoefficient.doubleValue());
                }
            });
        });

        resultPolynomial.sortByPower();
        return resultPolynomial;
    }

    // DIVISION
    public ArrayList<Polynomial> divide(Polynomial p) {
        p.sortByPower();
        this.simplifyPolynomial();
        if(p.getPolynom().get(0).getCoefficient().intValue() == 0){
            divisionWithZero();
        }

        Polynomial quotientPolynomial = new Polynomial();
        Polynomial remainderPolynomial = duplicatePolynomial(this);

        Monomial dividerFirstMonomial = p.getPolynom().get(0);
        Monomial remainderFirstMonomial = remainderPolynomial.getPolynom().get(0);

        Number quotientCoeff, dividerCoeff = dividerFirstMonomial.getCoefficient(),
                remainderCoeff = remainderFirstMonomial.getCoefficient();
        int quotientPower, dividerPower = dividerFirstMonomial.getPower(), remainderPower = remainderFirstMonomial.getPower();

        if(remainderPower < dividerPower){
            return null;
        }

        if(dividerPower == 0){
            this.getPolynom().forEach(monomial -> {
                monomial.setCoefficient(monomial.getCoefficient().doubleValue() / dividerCoeff.doubleValue());
            });
            quotientPolynomial = this;
            remainderPolynomial = new Polynomial("0");
        } else {
            while(remainderPower >= dividerPower) {
                quotientPower = remainderPower - dividerPower;
                quotientCoeff = remainderCoeff.doubleValue() / dividerCoeff.doubleValue();

                quotientPolynomial.getPolynom().add(new Monomial(quotientPower, quotientCoeff));

                Polynomial aux = new Polynomial();
                aux.getPolynom().add(new Monomial(quotientPower, quotientCoeff));

                remainderPolynomial = remainderPolynomial.subtract(aux.multiply(p));

                remainderFirstMonomial = remainderPolynomial.findNotZeroCoeff();
                remainderPower = remainderFirstMonomial.getPower();
                remainderCoeff = remainderFirstMonomial.getCoefficient();
            }
        }

        ArrayList<Polynomial> result = new ArrayList<>();
        result.add(quotientPolynomial);
        result.add(remainderPolynomial);
        return result;
    }

    // INTEGRATION
    public Polynomial integrate(){
        this.getPolynom().forEach(monomial -> {
            monomial.setCoefficient(monomial.getCoefficient().doubleValue() / (monomial.getPower() + 1));
            monomial.setPower(monomial.getPower() + 1);
        });
        return this;
    }
    // DERIVATIVE
    public Polynomial derivative(){
        this.getPolynom().forEach(monomial -> {
            monomial.setCoefficient(monomial.getCoefficient().doubleValue() * monomial.getPower());
            if(monomial.getPower() == 0) monomial.setCoefficient(0);
            else monomial.setPower(monomial.getPower() - 1);
        });
        return this;
    }
}
