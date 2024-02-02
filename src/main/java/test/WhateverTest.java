package test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class WhateverTest {
    public static void main(String[] args) {
//        int[] nums = new int[]{1,1,1,2,2,2,2,3};
//        topKFrequent(nums, 2);
        // jdk1.7 concurrentHashMap -> default 16 segments -> segment extend reentrantLock
        // -> segment contains hashEntry -> hashEntry contains linked node
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
//        map.put(null, 1);
    }

    public static void topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        TreeMap<Map.Entry<Integer, Integer>, Integer> treeMap = new TreeMap<>((e1, e2) -> e2.getValue() - e1.getValue());
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            treeMap.put(entry, 1);
        }

        for (Map.Entry<Integer, Integer> entry : treeMap.keySet()) {
            System.out.println(entry.getKey() + "----" + entry.getValue());
        }

    }
}
