package com.example.math6.Methods;

import com.example.math6.DTO.Coord;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class EulerMethod extends Method {
    private double stepik;
    public EulerMethod(BinaryOperator<Double> function, double leftBoundary, double rightBoundary, double y0) {
        super(function, leftBoundary, rightBoundary, y0);
    }

    @Override
    public ArrayList<Coord> calcWithStep(double step){
        ArrayList<Coord> coords = new ArrayList<>();
        int countOfIterations =  (int) ((rightBoundary - leftBoundary) / step);
        double y0 = yInit;
        double y = y0;
        double x = leftBoundary;
        coords.add(new Coord(x, y));
        for (int i = 0; i < countOfIterations; i++){
            y = y0 + step * function.apply(x, y0);
            x = x + step;
            coords.add(new Coord(x,y));
            y0 = y;
        }
        return coords;
    }



    @Override
    public ArrayList<Coord> calcWithAccuracy(double accuracy){
        ArrayList<Coord> coords = new ArrayList<>();
        double step = 1;
        boolean flag = true;
        while(flag) {
            flag = false;
            coords.clear();
            int countOfIterations = (int) ((rightBoundary - leftBoundary) / step);
            if (countOfIterations <= 1){
                flag = true;
                step = step / 2;
            }
            double y0 = yInit;
            double y = y0;
            double x = leftBoundary;
            coords.add(new Coord(x, y));
            for (int i = 0; i < countOfIterations; i++) {
                y = y0 + step * function.apply(x, y0);
                double yCheck = y0 + step * function.apply(x, y0) / 2;
                if (Math.abs(y - yCheck) > accuracy){
                    flag = true;
                    step = step / 2;
                    break;
                }
                x = x + step;
                coords.add(new Coord(x, y));
                y0 = y;
            }
        }
        stepik = step;
        return coords;
    }

    public double getStepik() {
        return stepik;
    }
}

