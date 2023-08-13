package com.RiskPO.ImitateModels;

public class Packet {
        // packet data
        private byte[] data;
        // checksum for packet data
        private int checksum;

        public Packet(byte[] data) {
            this.data = data;
            this.checksum = calculateChecksum(data);
        }

        public byte[] getData() {
            return data;
        }

        public int getChecksum() {
            return checksum;
        }

        private int calculateChecksum(byte[] data) {
            // calculate checksum for data
            return 0;
        }
}
