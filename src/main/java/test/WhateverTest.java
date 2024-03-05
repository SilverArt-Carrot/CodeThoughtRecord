package test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class WhateverTest {
    public static void main(String[] args) {
//        int[] nums = new int[]{1,1,1,2,2,2,2,3};
//        topKFrequent(nums, 2);
        // jdk1.7 concurrentHashMap -> default 16 segments -> segment extend reentrantLock
        // -> segment contains hashEntry -> hashEntry contains linked node
//        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
//        map.put(null, 1);
//        System.out.println(2 << 3);
//        LinkedList<Integer> list = new LinkedList<>();
//        ListIterator<Integer> integerListIterator = list.listIterator();
//
//        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        int[] nums2 = new int[]{1, 2, 3};
//        System.arraycopy(nums, 7, nums2, 0, 3);
//        System.out.println(Arrays.toString(nums2));
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
