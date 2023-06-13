package com.example.math6.util;

import com.example.math6.DTO.Coord;
import com.example.math6.DTO.Answer;
import com.example.math6.DTO.FunctionInfo;
import com.example.math6.Methods.EulerMethod;
import com.example.math6.Methods.MilnMethod;
import com.example.math6.Methods.RungeKuttaMethod;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class Analysis {
    private final FunctionInfo functionInfo;
    private double c1;
    private double c2;
    private double c3;

    private final ArrayList<BinaryOperator<Double>> taskFunctions = new ArrayList<>() {{
        add((x, y) -> y + (1 + x) * y * y); // y + (1 + x) * y^2
        add((x, y) -> y + x * x - (y + x)); // y + x^2 - (y + x)
        add((x, y) -> x + 10 * y); // x + 10 * y
        //add((x,y) -> 3*x*x + y + 5);
    }};
    private final ArrayList<BinaryOperator<Double>> answerFunctions = new ArrayList<>() {{
        add((x, c) -> -Math.exp(x) / (c + Math.exp(x) * x));
        add((x, c) -> 1/(c - x) + 1/2);
        //add((x,c) -> c*Math.exp(x) - 3*x*x - 6 * x - 11);
        add((x, c) -> c * Math.exp(10*x) - x / 10 - 0.01);
    }};

    public Analysis(FunctionInfo functionInfo) {
        this.functionInfo = functionInfo;
        double x = functionInfo.getLeft();
        double y = functionInfo.getInitY();
        //c1 = -Math.exp(x) * (y - Math.exp(x) * x);
        c1 = -Math.exp(x) * (y*x + 1) / y;
        c2 = 1 / (y - 0.5) + x;
        //c3 = y + 3*x*x + 6*x + 11 / Math.exp(x);
        c3 = (y + x / 10 + 0.01) / Math.exp(10*x);
    }

    public double getExactValue(BinaryOperator<Double> function, double x){
        if (function == taskFunctions.get(0)){
            return answerFunctions.get(0).apply(x, c1);
        } else if (function == taskFunctions.get(1)){
            return answerFunctions.get(1).apply(x, c2);
        }
        return answerFunctions.get(2).apply(x, c3);
    }

    //analyzeStep
    public ArrayList<Answer> analyze(int option) {
        var taskFunction = taskFunctions.get(option - 1);
        EulerMethod eulerMethod = new EulerMethod(taskFunction, functionInfo.getLeft(), functionInfo.getRight(), functionInfo.getInitY());
        MilnMethod milnMethod = new MilnMethod(taskFunction, functionInfo.getLeft(), functionInfo.getRight(), functionInfo.getInitY());
        RungeKuttaMethod rungeKuttaMethod = new RungeKuttaMethod(taskFunction, functionInfo.getLeft(), functionInfo.getRight(), functionInfo.getInitY());

        //ArrayList<Coord> coords1 = eulerMethod.calcWithStep(functionInfo.getAux());
        //ArrayList<Coord> coords2 = rungeKuttaMethod.calcWithStep(functionInfo.getAux());
        //ArrayList<Coord> coords3 = milnMethod.calcWithStep(functionInfo.getAux());

        ArrayList<Coord> coords1 = eulerMethod.calcWithAccuracy(functionInfo.getStep(), functionInfo.getAccuracy());
        ArrayList<Coord> coords2 = rungeKuttaMethod.calcWithAccuracy(functionInfo.getStep(), functionInfo.getAccuracy());
        ArrayList<Coord> coords3 = milnMethod.calcWithAccuracy(functionInfo.getStep(), functionInfo.getAccuracy());
        return extractY(coords1, coords2, coords3, option);
    }

    private ArrayList<Answer> extractY(ArrayList<Coord> coords1, ArrayList<Coord> coords2, ArrayList<Coord> coords3, int opt) {
        ArrayList<Answer> answers = new ArrayList<>();
        double myC;
        if (opt == 1) {
            myC = c1;
        } else if (opt == 2) {
            myC = c2;
        } else {
            myC = c3;
        }
        for (int i = 0; i < Math.min(coords3.size(), Math.min(coords1.size(), coords2.size())); i++) {
            Answer answer = new Answer(coords1.get(i).getX(), coords1.get(i).getY(), coords2.get(i).getY(), coords3.get(i).getY(), answerFunctions.get(opt - 1).apply(coords1.get(i).getX(), myC));
            answers.add(answer);
        }
        return answers;
    }

    public ArrayList<Coord> generateCoordsForBase(int option) {
        ArrayList<Coord> base = new ArrayList<>();
        double myC;
        if (option == 1) {
            myC = c1;
        } else if (option == 2) {
            myC = c2;
        } else {
            myC = c3;
        }
        for (double x = functionInfo.getLeft(); x <= functionInfo.getRight(); x += 0.1) {
            base.add(new Coord(x, answerFunctions.get(option - 1).apply(x, myC)));
        }
        return base;
    }

    public ArrayList<Coord> generateCoordsForApproxEuler(int option) {
        EulerMethod method = new EulerMethod(taskFunctions.get(option - 1), functionInfo.getLeft(), functionInfo.getRight(), functionInfo.getInitY());
        return method.calcWithStep(0.1);
    }

    public ArrayList<Coord> generateCoordsForApproxMiln(int option) {
        MilnMethod method = new MilnMethod(taskFunctions.get(option - 1), functionInfo.getLeft(), functionInfo.getRight(), functionInfo.getInitY());
        return method.calcWithStep(0.1);
    }

    public ArrayList<Coord> generateCoordsForApproxRunge(int option) {
        RungeKuttaMethod method = new RungeKuttaMethod(taskFunctions.get(option - 1), functionInfo.getLeft(), functionInfo.getRight(), functionInfo.getInitY());
        return method.calcWithStep(0.1);
    }

    /*
    public void analyzeAccuracy(int option) {
        var taskFunction = taskFunctions.get(option - 1);
        EulerMethod eulerMethod = new EulerMethod(taskFunction, functionInfo.getLeft(), functionInfo.getRight(), functionInfo.getAux());
        MilnMethod milnMethod = new MilnMethod(taskFunction, functionInfo.getLeft(), functionInfo.getRight(), functionInfo.getAux());
        RungeKuttaMethod rungeKuttaMethod = new RungeKuttaMethod(taskFunction, functionInfo.getLeft(), functionInfo.getRight(), functionInfo.getAux());

        ArrayList<Coord> coords1 = eulerMethod.calcWithStep(functionInfo.getAux());
        ArrayList<Coord> coords2 = milnMethod.calcWithStep(functionInfo.getAux());
        ArrayList<Coord> coords3 = rungeKuttaMethod.calcWithStep(functionInfo.getAux());
    }

     */
}
