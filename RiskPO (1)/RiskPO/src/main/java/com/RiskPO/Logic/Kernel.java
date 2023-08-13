package com.RiskPO.Logic;

public class Kernel {
    public static double gaussian(double x, double h) {
        return Math.exp(-(x*x)/(2*h*h)) / Math.sqrt(2*Math.PI*h*h);
    }
}
