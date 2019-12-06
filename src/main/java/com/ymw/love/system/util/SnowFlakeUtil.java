package com.ymw.love.system.util;

import java.util.UUID;

public class SnowFlakeUtil {
    /**
     * @BelongsPackage: com.bluebooth.common.util
     * @author: Lee
     * @CreateDate: 2018/09/04/9:05
     * @version: 1.0
     * @Description: 通过nextId雪花算法获取唯一值
     */
        private  static final long twepoch = 1535990400000L;
        private static final long workerIdBits = 5L;
        private static final long datacenterIdBits = 5L;
        private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
        private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
        private static final long sequenceBits = 12L;
        private static final long workerIdShift = sequenceBits;
        private static final long datacenterIdShift = sequenceBits + workerIdBits;
        private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        private static final long sequenceMask = -1L ^ (-1L << sequenceBits);

        private static long workerId=0L;
        private static long datacenterId=3L;
        private static long sequence = 0L;
        private static long lastTimestamp = -1L;

        public SnowFlakeUtil(long workerId, long datacenterId) {
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
            }
            if (datacenterId > maxDatacenterId || datacenterId < 0) {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
            }
            this.workerId = workerId;
            this.datacenterId = datacenterId;
        }

        public static  long nextId() {
            long timestamp = timeGen();
            if (timestamp < lastTimestamp) {
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            }
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0L;
            }

            lastTimestamp = timestamp;

            return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
        }
        /**
         * 最高18位
         * @param quantity
         * @return
         */
        public static String nextId(int quantity) {
        	String t=nextId()+"";
        	return t.substring(0, quantity);
        }
        
        

        protected static long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        protected static long timeGen() {
            return System.currentTimeMillis();
        }
        
        
        
        public static void main(String[] args) {
        	//SnowFlakeUtil 
        System.out.println(getStochastic(8));
		}
        
        
        
        
        
        /**
         * 随机数 包含字母数字
         * 最长位32
         */ 
        public static String getStochastic(int number) {
        	  UUID uuid = UUID. randomUUID();
              String  u= uuid .toString().replace("-", "");
              return u.substring(0, number);
        }
        
        
        
        /**
         * 获取随机数
         *
         * @param min 最小数
         * @param max 最大数
         * @return int
         */
        public static int getRandom(int min, int max) {
            return (int) (Math.random() * (max - min) + min);
        }

        /**
         * 获取随机数从1开始,格式10000000-99999999
         *
         * @param number 随机数长度
         * @return int
         */
        public static int getRandom(int number) {
            int max = 9;
            int min = 1;
            for (int i = 1; i < number; i++) {
                min = min * 10;
                max = max * 10 + 9;
            }
            return getRandom(min, max);
        }
        
        
        
        
    }
