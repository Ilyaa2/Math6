package com.example.math6;

import com.example.math6.DTO.Coord;
import com.example.math6.Methods.Method;
import com.example.math6.Methods.MilnMethod;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

@SpringBootApplication
public class Math6Application {

    public static void main(String[] args) {
        SpringApplication.run(Math6Application.class, args);
    }

    public static void launch(){
        BinaryOperator<Double> function = (x,y) -> y + (1 + x) * y * y;
        //step = 0.1
        Method eulerMethod = new MilnMethod(function, 1, 1.5, -1);
        ArrayList<Coord> coords = eulerMethod.calcWithAccuracy(0.1);
        for (Coord c: coords){
            System.out.println(c.getX() + "   :   " + c.getY());
        }

    }
}
