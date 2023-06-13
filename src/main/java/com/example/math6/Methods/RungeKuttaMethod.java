package com.example.math6.Methods;

import com.example.math6.DTO.Coord;
import java.util.ArrayList;
import java.util.function.BinaryOperator;


public class RungeKuttaMethod extends Method{
    public RungeKuttaMethod(BinaryOperator<Double> function, double leftBoundary, double rightBoundary, double y0) {
        super(function, leftBoundary, rightBoundary, y0);
    }

    @Override
    public ArrayList<Coord> calcWithStep(double step) {
        ArrayList<Coord> coords = new ArrayList<>();
        int countOfIterations = (int) ((rightBoundary - leftBoundary) / step);
        double y0 = yInit;
        double y = y0;
        double x = leftBoundary;
        coords.add(new Coord(x, y));
        for (int i = 0; i < countOfIterations; i++) {
            y = getKs(step, x, y0);
            x = x + step;
            coords.add(new Coord(x, y));
            y0 = y;
        }
        return coords;
    }

    private double getKs(double step, double x, double y0) {
        double y;
        double k1 = step * function.apply(x, y0);
        double k2 = step * function.apply(x + step / 2, y0 + k1 / 2);
        double k3 = step * function.apply(x + step / 2, y0 + k2 / 2);
        double k4 = step * function.apply(x + step, y0 + k3);

        y = y0 + 1d / 6d * (k1 + 2 * k2 + 2 * k3 + k4);
        return y;
    }

    @Override
    public ArrayList<Coord> calcWithAccuracy(double theirStep, double accuracy){
        ArrayList<Coord> coords = new ArrayList<>();
        boolean flag = true;
        double step = theirStep;
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
                y = getKs(step, x, y0);
                double yCheck = getKs(step / 2, x, y0);
                if (Math.abs(y - yCheck) / 3 > accuracy){
                    flag = true;
                    step = step / 2;
                    break;
                }
                x = x + step;
                coords.add(new Coord(x, y));
                y0 = y;
            }
        }
        return coords;
    }
}
