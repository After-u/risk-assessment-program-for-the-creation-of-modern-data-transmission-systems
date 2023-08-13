package com.RiskPO.Logic;

import com.RiskPO.ImitateModels.MultiChannelFourPhaseSystemSimulator;

import java.util.Map;

import java.util.*;

public class MegasimulationforFourPhase {
    private int numberOfChannels;
    private double serviceRate;
    private int simulationTime;
    private int maxQueueSize;


    public MegasimulationforFourPhase(int numberOfChannels, double serviceRate, int simulationTime, int maxQueueSize) {
        this.numberOfChannels = numberOfChannels;
        this.serviceRate = serviceRate;
        this.simulationTime = simulationTime;
        this.maxQueueSize = maxQueueSize;
    }

    public void gettingSampleforPacketsServiced(int[] resivedPackets, int[] lostPackets){
        MultiChannelFourPhaseSystemSimulator model = new MultiChannelFourPhaseSystemSimulator(numberOfChannels,serviceRate,simulationTime,maxQueueSize);
        for (int i=0; i< resivedPackets.length; i++){
            Map<String, Integer> resultsOfSimulate = model.simulate();
            resivedPackets[i] = resultsOfSimulate.get("packetsserviced");
            lostPackets[i] = resultsOfSimulate.get("packetsLost");
        }
    }
}
