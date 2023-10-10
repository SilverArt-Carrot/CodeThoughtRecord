package lru;

import java.util.HashMap;
import java.util.Map;

// LRU 和 LRUCache 是一样的
public class LRUCache {
    private final Map<Integer, Node<Integer, Integer>> map = new HashMap<>();

    private final int capacity;

    private final DoubleLinkedList list = new DoubleLinkedList();

    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> pre;
        private Node<K, V> next;

        public Node() {
            this.pre = this.next = null;
        }

        public Node(K key, V value) {
            this();
            this.key = key;
            this.value = value;
        }
    }

    private static class DoubleLinkedList {
        private final Node<Integer, Integer> head;
        private final Node<Integer, Integer> tail;

        public DoubleLinkedList() {
            this.head = new Node<>();
            this.tail = new Node<>();
            head.next = tail;
            tail.pre = head;
        }

        public void add(Node<Integer, Integer> node) {
            node.next = tail;
            node.pre = tail.pre;
            tail.pre.next = node;
            tail.pre = node;
        }

        public void remove(Node<Integer, Integer> node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
            node.pre = node.next = null;
        }

        public Node<Integer, Integer> poll() {
            Node<Integer, Integer> node = this.head.next;
            this.head.next = node.next;
            node.next.pre = this.head;
            node.next = node.pre = null;
            return node;
        }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!map.containsKey(key))
            return -1;
        Node<Integer, Integer> node = map.get(key);
        list.remove(node);
        list.add(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node<Integer, Integer> node = map.get(key);
            node.value = value;
            list.remove(node);
            list.add(node);
        } else {
            if (map.size() == capacity) {
                Node<Integer, Integer> first = list.poll();
                map.remove(first.key);
            }
            Node<Integer, Integer> node = new Node<>(key, value);
            list.add(node);
            map.put(key, node);
        }
    }
}
