package day02;

import java.util.List;

/**
 * 链表相关
 */
public class Main {
    public static void main(String[] args) { }

    /**
     * 移除链表元素，使用虚拟头节点
     */
    public static ListNode learn01(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(-1, head);
        ListNode pre = dummy;
        ListNode cur = head;
        while (cur != null) {
            if (cur.val == val) {
                pre.next = cur.next;
            } else {
                pre = pre.next;
            }
            cur = cur.next;
        }
        return dummy.next;
    }

    /**
     * 移除链表元素，不使用虚拟头节点
     */
    public static ListNode learn02(ListNode head, int val) {
        // 如果头结点等于val，则移除
        while (head != null && head.val == val) {
            head = head.next;
        }
        if (head == null) {
            return null;
        }
        ListNode pre = head;
        ListNode cur = head.next;
        while (cur != null) {
            if (cur.val == val) {
                pre.next = cur.next;
            } else {
                pre = pre.next;
            }
            cur = cur.next;
        }
        return head;
    }

    /**
     * 反转链表
     */
    public static ListNode learn03(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;

            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 两两交换链表中的元素（本来是交换节点的，取巧了）
     */
    public static ListNode learn04(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode first = head;
        ListNode second = head.next;
        while (first != null) {
            if (second != null) {
                int val = first.val;
                first.val = second.val;
                second.val = val;
                first = second.next;
                if (first != null) {
                    second = first.next;
                }
            } else {
                break;
            }
        }
        return head;
    }

    /**
     * 删除链表的倒数第n个节点
     */
    public static ListNode learn05(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        if (n <= 0) {
            return head;
        }
        ListNode dummy = new ListNode(-1, head);
        ListNode slow = dummy;
        ListNode fast = head;
        while (n-- > 0) {
            fast = fast.next;
            if (n > 0 && fast == null) {
                return head;
            }
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }


    /**
     * 设计链表(单链表)
     * 使用虚拟头结点
     * get(index)：获取链表中第 index 个节点的值。如果索引无效，则返回-1。
     * addAtHead(val)：在链表的第一个元素之前添加一个值为 val 的节点。插入后，新节点将成为链表的第一个节点。
     * addAtTail(val)：将值为 val 的节点追加到链表的最后一个元素。
     * addAtIndex(index,val)：在链表中的第 index 个节点之前添加值为 val  的节点。如果 index 等于链表的长度，则该节点将附加到链表的末尾。如果 index 大于链表长度，则不会插入节点。如果index小于0，则在头部插入节点。
     * deleteAtIndex(index)：如果索引 index 有效，则删除链表中的第 index 个节点。
     */
    public static class MyLinkedList {
        // size存储链表元素的个数
        int size;
        // 虚拟头结点
        ListNode head;

        // 初始化链表
        public MyLinkedList() {
            size = 0;
            head = new ListNode(0);
        }

        //获取第index个节点的数值，注意index是从0开始的，第0个节点就是头结点
        public int get(int index) {
            if (index < 0 || index >= size) {
                return -1;
            }
            ListNode cur = head;
            for (int i = 0; i <= index; i++) {
                cur = cur.next;
            }
            return cur.val;
        }

        //在链表最前面插入一个节点，等价于在第0个元素前添加
        public void addAtHead(int val) {
            ListNode node = head.next;
            ListNode newNode = new ListNode(val);
            head.next = newNode;
            if (node != null) {
                newNode.next = node;
            }
            size++;
        }

        //在链表的最后插入一个节点，等价于在(末尾+1)个元素前添加
        public void addAtTail(int val) {
            ListNode node = head;
            while (node.next != null) {
                node = node.next;
            }
            node.next = new ListNode(val);
            size++;
        }

        // 在第 index 个节点之前插入一个新节点，例如index为0，那么新插入的节点为链表的新头节点。
        // 如果 index 等于链表的长度，则说明是新插入的节点为链表的尾结点
        // 如果 index 大于链表的长度，则返回空
        public void addAtIndex(int index, int val) {
            if (index > size) {
                return;
            }
            if (index == 0) {
                addAtHead(val);
                return;
            }
            if (index == size) {
                addAtTail(val);
                return;
            }
            ListNode prev = head;
            ListNode next = head.next;
            for (int i = 0; i < index; i++) {
                prev = prev.next;
                next = next.next;
            }
            ListNode node = new ListNode(val);
            prev.next = node;
            node.next = next;
            size++;
        }

        //删除第index个节点
        public void deleteAtIndex(int index) {
            if (index < 0 || index >= size) {
                return;
            }
            ListNode node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            node.next = node.next.next;
            size--;
        }
    }

    public static class MyLinkedList2 {
        // size存储链表元素的个数
        int size;
        // 虚拟头结点
        ListNode head;
        // 虚拟尾结点
        ListNode tail;

        // 初始化链表
        public MyLinkedList2() {
            size = 0;
            head = new ListNode(0);
            tail = new ListNode(0);
            head.next = tail;
            tail.prev = head;
        }

        /**
         * 可以判断一下从哪边开始遍历时间更短
         */
        public int get(int index) {
            if (index < 0 || index >= size) {
                return -1;
            }
            ListNode cur = head;
            for (int i = 0; i <= index; i++) {
                cur = cur.next;
            }
            return cur.val;
        }

        public void addAtHead(int val) {
            ListNode node = head.next;
            ListNode newNode = new ListNode(val);
            head.next = newNode;
            newNode.prev = head;

            newNode.next = node;
            node.prev = newNode;
            size++;
        }

        public void addAtTail(int val) {
            ListNode node = tail.prev;
            ListNode newNode = new ListNode(val);
            tail.prev = newNode;
            newNode.next = tail;

            newNode.prev = node;
            node.next = newNode;
            size++;
        }

        public void addAtIndex(int index, int val) {
            if (index < 0 || index > size) {
                return;
            }
            if (index == 0) {
                addAtHead(val);
                return;
            }
            if (index == size) {
                addAtTail(val);
                return;
            }
            ListNode prev = head;
            for (int i = 0; i < index; i++) {
                prev = prev.next;
            }
            ListNode next = prev.next;
            ListNode node = new ListNode(val);

            node.next = next;
            node.prev = prev;

            prev.next = node;
            next.prev = node;
            size++;
         }

        public void deleteAtIndex(int index) {
            if (index < 0 || index >= size) {
                return;
            }
            ListNode prev = head;
            for (int i = 0; i < index; i++) {
                prev = prev.next;
            }
            ListNode next = prev.next.next;
            prev.next = next;
            next.prev = prev;
            size--;
        }
    }
}
