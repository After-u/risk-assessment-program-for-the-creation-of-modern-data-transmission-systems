package com.RiskPO.ImitateModels;

import java.util.Random;

public class TwoPhaseNetworkSimulation {
    private double simulationTime; // Время работы симуляции в секундах
    private double packetLossRate; // Вероятность потерь пакетов
    private double transmissionDelay; // Задержка передачи в миллисекундах
    private double propagationDelay; // Задержка распространения в миллисекундах
    private double processingDelay; // Задержка обработки в миллисекундах

    private int packetsSent;
    private int packetsReceived;
    private int packetsLost;

    private Random rand;

    public double getSimulationTime() {
        return simulationTime;
    }

    public double getPacketLossRate() {
        return packetLossRate;
    }

    public double getTransmissionDelay() {
        return transmissionDelay;
    }

    public double getPropagationDelay() {
        return propagationDelay;
    }

    public double getProcessingDelay() {
        return processingDelay;
    }

    public int getPacketsSent() {
        return packetsSent;
    }

    public int getPacketsReceived() {
        return packetsReceived;
    }

    public int getPacketsLost() {
        return packetsLost;
    }

    public TwoPhaseNetworkSimulation(double simulationTime, double packetLossRate, double transmissionDelay,
                                     double propagationDelay, double processingDelay) {
        this.simulationTime = simulationTime;
        this.packetLossRate = packetLossRate;
        this.transmissionDelay = transmissionDelay;
        this.propagationDelay = propagationDelay;
        this.processingDelay = processingDelay;

        this.rand = new Random();
    }

    public void runSimulation() throws InterruptedException {
        packetsReceived=0;
        packetsLost=0;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < (simulationTime * 1000)) {
            boolean packetSentSuccessfully = true;
            if (rand.nextDouble() < packetLossRate) {
                packetsLost++;
                packetSentSuccessfully = false;
            } else {
                packetsReceived++;
            }
            packetsSent++;
            // Задержка передачи
            Thread.sleep((long) transmissionDelay);

            if (packetSentSuccessfully) {
                // Задержка распространения
                Thread.sleep((long) propagationDelay);

                // Задержка обработки
                Thread.sleep((long) processingDelay);
            }
        }
    }
}
