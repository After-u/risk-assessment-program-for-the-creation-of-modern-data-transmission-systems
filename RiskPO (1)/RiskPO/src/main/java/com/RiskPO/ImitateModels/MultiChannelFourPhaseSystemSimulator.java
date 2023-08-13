package com.RiskPO.ImitateModels;

import java.util.*;

public class MultiChannelFourPhaseSystemSimulator {
    private static final int NUMBER_OF_PHASES = 4;
    public int SIMULATION_TIME;///1000
    public double SERVICE_RATE;///0.6
    public int NUMBER_OF_CHANNELS;///2
    public int MAX_QUEUE_SIZE; // Максимальный размер очереди 10

    private final Queue<Customer> queue;
    private final List<Customer>[] channelList;
    private final Random random;
    private int customersNotServiced;

    public int getNUMBER_OF_PHASES() {
        return NUMBER_OF_PHASES;
    }

    public int getSIMULATION_TIME() {
        return SIMULATION_TIME;
    }

    public void setSIMULATION_TIME(int SIMULATION_TIME) {
        this.SIMULATION_TIME = SIMULATION_TIME;
    }

    public double getSERVICE_RATE() {
        return SERVICE_RATE;
    }

    public void setSERVICE_RATE(double SERVICE_RATE) {
        this.SERVICE_RATE = SERVICE_RATE;
    }

    public int getNUMBER_OF_CHANNELS() {
        return NUMBER_OF_CHANNELS;
    }

    public void setNUMBER_OF_CHANNELS(int NUMBER_OF_CHANNELS) {
        this.NUMBER_OF_CHANNELS = NUMBER_OF_CHANNELS;
    }

    public int getMAX_QUEUE_SIZE() {
        return MAX_QUEUE_SIZE;
    }

    public void setMAX_QUEUE_SIZE(int MAX_QUEUE_SIZE) {
        this.MAX_QUEUE_SIZE = MAX_QUEUE_SIZE;
    }

    public MultiChannelFourPhaseSystemSimulator(int NUMBER_OF_CHANNELS, double SERVICE_RATE, int SIMULATION_TIME, int MAX_QUEUE_SIZE) {
        this.NUMBER_OF_CHANNELS = NUMBER_OF_CHANNELS;
        this.SERVICE_RATE = SERVICE_RATE;
        this.SIMULATION_TIME = SIMULATION_TIME;
        this.MAX_QUEUE_SIZE = MAX_QUEUE_SIZE;

        queue = new LinkedList<>();
        channelList = new ArrayList[NUMBER_OF_CHANNELS];
        for (int i = 0; i < NUMBER_OF_CHANNELS; i++) {
            channelList[i] = new ArrayList<>();
        }
        random = new Random();
        customersNotServiced = 0;
    }

    public Map<String, Integer> simulate() {
        int totalCustomers = 0;
        int customersServiced = 0;
        int customersInQueue = 0;
        int customersNotServiced = 0;

        for (int time = 0; time < SIMULATION_TIME; time++) {
            // Generate a new customer with probability 0.1
            if (random.nextDouble() < 0.3) {
                if (queue.size() < MAX_QUEUE_SIZE) { // Проверяем, что очередь еще не заполнена
                    queue.add(new Customer(time));
                    totalCustomers++;
                    customersInQueue++;
                } else {
                    customersNotServiced++; // Увеличиваем счетчик клиентов, которые не могут быть обслужены
                }
            }

            // Check if there are customers in the queue
            while (!queue.isEmpty()) {
                boolean isAssigned = false;
                for (int i = 0; i < NUMBER_OF_CHANNELS; i++) {
                    if (channelList[i].isEmpty()) {
                        channelList[i].add(queue.poll());
                        customersInQueue--;
                        isAssigned = true;
                        break;
                    }
                }
                if (!isAssigned) {
                    break;
                }
            }

            for (int i = 0; i < NUMBER_OF_CHANNELS; i++) {
                // Check if there are customers in the channel
                if (!channelList[i].isEmpty()) {
                    Customer customer = channelList[i].get(0);

                    // Check if the customer has completed all phases of service
                    if (customer.getCurrentPhase() == NUMBER_OF_PHASES) {
                        channelList[i].remove(0);
                        customersServiced++;
                    } else {
                        // Check if the customer can proceed to the next phase
                        if (random.nextDouble() < SERVICE_RATE) {
                            customer.incrementCurrentPhase();
                        }
                    }
                }
            }
        }

        Map<String, Integer> resultsOfSimulate = new HashMap<>();
        resultsOfSimulate.put("totalpackets", totalCustomers);
        resultsOfSimulate.put("packetsserviced", customersServiced);
        resultsOfSimulate.put("packetsLost", customersNotServiced);
        resultsOfSimulate.put("packetsInQueue", customersInQueue);

        return resultsOfSimulate;
    }

    public double calculateStressResistance(int stressPeriod) {
        int totalCustomers = 0;
        int customersNotServiced = 0;

        for (int time = 0; time < stressPeriod; time++) {
            // Генерация новых клиентов с вероятностью 0.1
            if (random.nextDouble() < 0.3) {
                if (queue.size() < MAX_QUEUE_SIZE) {
                    queue.add(new Customer(time));
                    totalCustomers++;
                } else {
                    customersNotServiced++;
                }
            }

            while (!queue.isEmpty()) {
                boolean isAssigned = false;
                for (int i = 0; i < NUMBER_OF_CHANNELS; i++) {
                    if (channelList[i].isEmpty()) {
                        channelList[i].add(queue.poll());
                        isAssigned = true;
                        break;
                    }
                }
                if (!isAssigned) {
                    break;
                }
            }

            for (int i = 0; i < NUMBER_OF_CHANNELS; i++) {
                if (!channelList[i].isEmpty()) {
                    Customer customer = channelList[i].get(0);

                    if (customer.getCurrentPhase() == NUMBER_OF_PHASES) {
                        channelList[i].remove(0);
                    } else {
                        if (random.nextDouble() < SERVICE_RATE) {
                            customer.incrementCurrentPhase();
                        }
                    }
                }
            }
        }

        double stressResistance = 1 - ((double) customersNotServiced / totalCustomers);
        return stressResistance;
    }
}

class Customer {
    private final int arrivalTime;
    private int currentPhase;

    public Customer(int arrivalTime) {
        this.arrivalTime = arrivalTime;
        currentPhase = 1;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }

    public void incrementCurrentPhase() {
        currentPhase++;
    }
}