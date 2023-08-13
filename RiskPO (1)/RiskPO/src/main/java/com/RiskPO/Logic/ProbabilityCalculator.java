package com.RiskPO.Logic;

public class ProbabilityCalculator {
    private DensityEstimator density;

    public ProbabilityCalculator(DensityEstimator density) {
        this.density = density;
    }

    public double probability(double x0) {
        double integral = 0.0;
        double step = 0.001; // можно изменить шаг интегрирования
        for (double x = x0; x < 10 * x0; x += step) {
            integral += density.estimate(x) * step;
        }

        return 1 - integral;
    }

    public double probabilityDown(double x0) {
        double integral = 0.0;
        double step = 0.001; // можно изменить шаг интегрирования
        for (double x = x0; x < 10 * x0; x += step) {
            integral += density.estimate(x) * step;
        }

        return integral;
    }
}
