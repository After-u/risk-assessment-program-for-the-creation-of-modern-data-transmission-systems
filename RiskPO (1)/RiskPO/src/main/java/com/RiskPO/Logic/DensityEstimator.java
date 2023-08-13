package com.RiskPO.Logic;

import java.util.List;

public class DensityEstimator {
    private List<Double> data;
    private double h;

    public DensityEstimator(List<Double> data) {
        this.data = data;
        this.h = calculateBandwidth(data);
    }

    public double estimate(double x) {
        double sum = 0.0;
        for (double xi : data) {
            sum += Kernel.gaussian((x - xi) / h, 1) / h;
        }
        return sum / data.size();
    }

    private double calculateBandwidth(List<Double> data) {
        double n = data.size();
        double stdDev = Math.sqrt(data.stream()
                .mapToDouble(x -> (x - mean(data)) * (x - mean(data)))
                .sum() / (n - 1));
        return 1.06 * stdDev * Math.pow(n, -0.2);
    }

    private double mean(List<Double> data) {
        return data.stream().mapToDouble(x -> x).average().orElse(0.0);
    }
}
