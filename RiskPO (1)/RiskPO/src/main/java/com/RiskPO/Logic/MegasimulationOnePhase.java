package com.RiskPO.Logic;

import com.RiskPO.ImitateModels.*;

import java.util.Map;

public class MegasimulationOnePhase {
    private int numberOfChannels;
    private double serviceRate;
    private int simulationTime;
    private int maxQueueSize;

    public MegasimulationOnePhase(int numberOfChannels, double serviceRate, int simulationTime, int maxQueueSize) {
        this.numberOfChannels = numberOfChannels;
        this.serviceRate = serviceRate;
        this.simulationTime = simulationTime;
        this.maxQueueSize = maxQueueSize;
    }

    public void gettingSampleforPackets(int[] packetsResived, int[] packetsLost) throws InterruptedException {
        NetworkOnePhase networkOnePhase = new NetworkOnePhase(numberOfChannels,serviceRate,simulationTime,maxQueueSize);
        for (int i = 0; i < packetsResived.length; i++) {
            Map<String, Integer> resultsOfSimulate = networkOnePhase.simulate();
            packetsResived[i] = resultsOfSimulate.get("packetsserviced");
            packetsLost[i] = resultsOfSimulate.get("packetsLost");
        }
}
}
