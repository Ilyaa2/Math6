package com.example.math6.Methods;

import com.example.math6.DTO.Coord;

import java.util.ArrayList;
import java.util.function.BinaryOperator;


public class MilnMethod extends Method {
    public MilnMethod(BinaryOperator<Double> function, double leftBoundary, double rightBoundary, double y0) {
        super(function, leftBoundary, rightBoundary, y0);
    }

    @Override
    public ArrayList<Coord> calcWithStep(double step) {
        RungeKuttaMethod method = new RungeKuttaMethod(function, leftBoundary, leftBoundary + 3 * step, yInit);
        ArrayList<Coord> coords = new ArrayList<>(method.calcWithStep(step));
        int countOfIterations = (int) ((rightBoundary - leftBoundary) / step);
        for (int i = 4; i < countOfIterations + step; i++) {
            double x = coords.get(i - 1).getX() + step;
            double yPredict = coords.get(i - 4).getY() + 4 * step * (2 * function.apply(coords.get(i - 3).getX(), coords.get(i - 3).getY()) - function.apply(coords.get(i - 2).getX(), coords.get(i - 2).getY()) + 2 * function.apply(coords.get(i - 1).getX(), coords.get(i - 1).getY())) / 3;
            double yCorrected = coords.get(i - 2).getY() + step * (function.apply(coords.get(i - 2).getX(), coords.get(i - 2).getY()) + 4 * function.apply(coords.get(i - 1).getX(), coords.get(i - 1).getY()) + function.apply(x, yPredict)) / 3;
            coords.add(new Coord(x, yCorrected));
        }
        return coords;
    }

    @Override
    public ArrayList<Coord> calcWithAccuracy(double stepik) {
        return calcWithStep(stepik);
    }

}
