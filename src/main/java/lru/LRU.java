package lru;

import java.util.HashMap;
import java.util.Map;

// LRU 和 LRUCache 是一样的
public class LRU {

    private final int capacity;

    private final Map<Integer, Node> map;

    private final DoubleList list;

    public LRU(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        list = new DoubleList();
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        list.remove(node);
        list.add(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) {
            node.value = value;
            list.remove(node);
            list.add(node);
        } else {
            if (list.size >= this.capacity) {
                Node poll = list.poll();
                map.remove(poll.key);
            }
            node = new Node(key, value);
            list.add(node);
            map.put(key, node);
        }
    }

    private static class Node {
        private int key;
        private int value;

        private Node pre;
        private Node next;

        public Node() { }

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public Node(int key, int value, Node pre, Node next) {
            this(key, value);
            this.pre = pre;
            this.next = next;
        }
    }

    private static class DoubleList {
        private final Node head;
        private final Node tail;
        private int size;

        public DoubleList() {
            this.head = new Node();
            this.tail = new Node();
            this.head.next = tail;
            this.tail.pre = head;
        }

        public void add(Node node) {
            Node pre = this.tail.pre;
            node.pre = pre;
            node.next = tail;

            pre.next = node;
            this.tail.pre = node;
            this.size++;
        }

        public void remove(Node node) {
            Node pre = node.pre;
            Node next = node.next;

            pre.next = next;
            next.pre = pre;

            node.pre = node.next = null;
            this.size--;
        }

        public Node poll() {
            Node poll = this.head.next;
            if (poll == tail) {
                return null;
            }

            Node next = poll.next;
            this.head.next = next;
            next.pre = this.head;

            poll.pre = poll.next = null;
            this.size--;
            return poll;
        }
    }
}
