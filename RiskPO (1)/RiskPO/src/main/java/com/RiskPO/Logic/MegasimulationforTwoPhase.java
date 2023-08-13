package com.RiskPO.Logic;

import com.RiskPO.ImitateModels.TwoPhaseNetworkSimulation;

public class MegasimulationforTwoPhase {
    private double simulationTime; // Время работы симуляции в секундах
    private double packetLossRate; // Вероятность потерь пакетов
    private double transmissionDelay; // Задержка передачи в миллисекундах
    private double propagationDelay; // Задержка распространения в миллисекундах
    private double processingDelay;

    public MegasimulationforTwoPhase(double simulationTime, double packetLossRate, double transmissionDelay, double propagationDelay, double processingDelay) {
        this.simulationTime = simulationTime;
        this.packetLossRate = packetLossRate;
        this.transmissionDelay = transmissionDelay;
        this.propagationDelay = propagationDelay;
        this.processingDelay = processingDelay;
    }

    public void gettingSampleforPackets(int[] packetsResived, int[] packetsLost) throws InterruptedException {
        TwoPhaseNetworkSimulation model = new TwoPhaseNetworkSimulation(simulationTime, packetLossRate, transmissionDelay, propagationDelay, processingDelay);
        for (int i = 0; i < packetsResived.length; i++) {
            model.runSimulation();
            packetsResived[i] = model.getPacketsReceived();
            packetsLost[i] = model.getPacketsLost();
        }
    }
}
