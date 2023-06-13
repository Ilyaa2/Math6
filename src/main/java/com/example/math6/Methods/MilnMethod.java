package com.example.math6.Methods;

import com.example.math6.DTO.Coord;
import com.example.math6.DTO.FunctionInfo;
import com.example.math6.util.Analysis;

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
    public ArrayList<Coord> calcWithAccuracy(double accuracy, double step) {
        var myStep = step;
        do {
            ArrayList<Coord> coords = calcWithStep(myStep);
            if (isAccurate(coords, accuracy)){
                return coords;
            } else{
                myStep = step / 2;
            }
        } while(true);

    }
    public boolean isAccurate(ArrayList<Coord> coords, double accuracy) {
        Analysis analysis = new Analysis(new FunctionInfo(leftBoundary, rightBoundary, getYInit(), 0, 0));

        for (Coord coord: coords){
            if (Math.abs(analysis.getExactValue(function, coord.getX()) - coord.getY()) > accuracy){
                return false;
            }
        }
        return true;
    }
}