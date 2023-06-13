package com.example.math6.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FunctionInfo {
    private double left;
    private double right;
    private double initY;
    private double step;
    private double accuracy;
}
