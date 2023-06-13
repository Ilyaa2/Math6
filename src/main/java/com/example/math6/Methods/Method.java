package com.example.math6.Methods;

import com.example.math6.DTO.Coord;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

@Getter
@AllArgsConstructor
public abstract class Method {
    protected final BinaryOperator<Double> function;
    protected final double leftBoundary;
    protected final double rightBoundary;
    protected final double yInit;

    public abstract ArrayList<Coord> calcWithStep(double step);
    public abstract ArrayList<Coord> calcWithAccuracy(double accuracy);
}
