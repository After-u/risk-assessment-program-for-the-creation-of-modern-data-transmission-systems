package com.RiskPO.ImitateModels;

    public class NetworkParameters {
        private double bandwidth; // пропускная способность сети в Mbps
        private double delay; // задержка передачи пакетов в миллисекундах
        private double jitter; // джиттер (разброс задержки) в миллисекундах

        public NetworkParameters(double bandwidth, double delay, double jitter) {
            this.bandwidth = bandwidth;
            this.delay = delay;
            this.jitter = jitter;
        }

        public double getBandwidth() {
            return bandwidth;
        }

        public double getDelay() {
            return delay;
        }

        public double getJitter() {
            return jitter;
        }
    }