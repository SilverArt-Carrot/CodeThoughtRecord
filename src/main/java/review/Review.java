package review;

import day02.ListNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Review {
    public static void main(String[] args) {

    }

    ///////////////////////////////////////数组///////////////////////////////////////

    // 二分
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] > target) {
                r = mid - 1;
            } else if (nums[mid] < target) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    // 移除元素
    public int removeElement(int[] nums, int val) {
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow++] = nums[fast];
            }
            fast++;
        }
        return slow;
    }

    // 有序数组的平方
    public int[] sortedSquares(int[] nums) {
        int[] result = new int[nums.length];

        int l = 0, r = nums.length - 1, i = nums.length - 1;
        while (l <= r) {
            int left = nums[l] * nums[l];
            int right = nums[r] * nums[r];

            if (left > right) {
                result[i--] = left;
                l++;
            } else {
                result[i--] = right;
                r--;
            }
        }
        return result;
    }

    // 长度最小的子数组
    public int minSubArrayLen(int s, int[] nums) {
        int left = 0, sum = 0, len = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            while (sum >= s) {
                len = Math.min(len, i - left + 1);
                sum -= nums[left++];
            }
        }
        return len == Integer.MAX_VALUE ? 0 : len;
    }

    // 螺旋矩阵
    public int[][] generateMatrix(int n) {
        int i = 0, j = 0;
        int num = 1;
        int edge = 0;
        int[][] matrix = new int[n][n];

        while (num <= n * n) {

            while (j < n - edge) {
                matrix[i][j++] = num++;
            }
            j--;
            i++;

            while (i < n - edge) {
                matrix[i++][j] = num++;
            }
            i--;
            j--;

            while (j >= edge) {
                matrix[i][j--] = num++;
            }
            j++;
            i--;

            while (i >= edge + 1) {
                matrix[i--][j] = num++;
            }
            i++;
            j++;

            edge++;
        }

        return matrix;
    }

    ///////////////////////////////////////链表///////////////////////////////////////

    // 移除链表元素
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        ListNode dummy = new ListNode(-1, head);  // 虚拟头节点
        ListNode pre = dummy;

        while (head != null) {
            if (head.val == val) {
                pre.next = head.next;
            } else {
                pre = head;
            }
            head = head.next;
        }
        return dummy.next;
    }

    // 设计链表
    static class MyLinkedList {

        int size;
        ListNode head;

        public MyLinkedList() {
            size = 0;
            head = new ListNode(-1);  // 虚拟头节点
        }

        public int get(int index) {
            if (index < 0 || index >= size) {
                return -1;
            }
            int i = 0;
            ListNode node = head;
            while (i <= index) {
                node = node.next;
                i++;
            }
            return node.val;
        }

        public void addAtHead(int val) {
            addAtIndex(0, val);
        }

        public void addAtTail(int val) {
            addAtIndex(size, val);
        }

        public void addAtIndex(int index, int val) {
            if (index > size) {
                return;
            }
            index = Math.max(index, 0);
            int i = 0;
            ListNode node = head;
            while (i < index) {
                node = node.next;
                i++;
            }
            node.next = new ListNode(val, node.next);
            size++;
        }

        public void deleteAtIndex(int index) {
            if (index < 0 || index >= size) {
                return;
            }
            int i = 0;
            ListNode node = head;
            while (i < index) {
                node = node.next;
                i++;
            }
            node.next = node.next.next;
            size--;
        }
    }

    // 反转链表
    public ListNode reverseList(ListNode head) {
        ListNode next = null;
        ListNode pre = null;
        while (head != null) {
            next = head.next;

            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    // 两两交换链表中的节点
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(-1, head);  // 虚拟头节点
        ListNode first = null;
        ListNode second = null;
        ListNode tmp = null;
        ListNode cur = dummy;

        while (cur.next != null && cur.next.next != null) {
            first = cur.next;
            second = cur.next.next;
            tmp = second.next;

            cur.next = second;
            second.next = first;
            first.next = tmp;

            cur = first;
        }

        return dummy.next;
    }

    // 删除链表的倒数第N个节点
    public ListNode removeNthFromEnd(ListNode head, int n){
        if (head == null || n <= 0) {
            return head;
        }
        ListNode dummy = new ListNode(-1, head);
        ListNode slow = dummy;
        ListNode fast = dummy;

        for (int i = 0; i <= n; i++) {
            if (fast != null) {
                fast = fast.next;
            } else {
                return dummy.next;
            }
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return dummy.next;
    }

    // 链表相交
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {  // set法，也可以使用代码随想录里的末尾对齐法，LeetCode里会更快
        if (headA == null || headB == null) {
            return null;
        }

        Set<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }

        while (headB != null) {
            if (set.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }

    // 环形链表II
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {  // 一个节点肯定无环
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) {
                ListNode node = head;
                while (node != slow) {
                    node = node.next;
                    slow = slow.next;
                }
                return node;
            }
        }
        return null;
    }

    // 有效的字母异位词
    public boolean isAnagram(String s, String t) {
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < t.length(); i++) {
            counts[t.charAt(i) - 'a']--;
        }

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] != 0) {
                return false;
            }
        }
        return true;
    }

    // 两个数组的交集
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return new int[0];
        }
        Set<Integer> set1 = new HashSet<>();
        for (int num : nums1) {
            set1.add(num);
        }

        Set<Integer> set2 = new HashSet<>();
        for (int num : nums2) {
            if (set1.contains(num)) {
                set2.add(num);
            }
        }

        int[] res = new int[set2.size()];
        int i = 0;
        for (Integer num : set2) {
            res[i++] = num;
        }
        return res;
    }

    // 快乐数
    // 如果不是快乐数，会无限循环，可以使用set的方式，
    // 也可以把它当成链表的，使用快慢指针来检测
    public boolean isHappy(int n) {
        int slow = n;
        int fast = getNext(n);

        while (slow != 1 && slow != fast) {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        }
        return slow == 1;
    }
    private int getNext(int n) {
        int total = 0;
        while (n > 0) {
            int t = n % 10;
            total += t * t;
            n /= 10;
        }
        return total;
    }

    // 两数之和
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    // 四数相加II
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num1 : nums1) {
            for (int num2 : nums2) {
                map.put(num1 + num2, map.getOrDefault(num1 + num2, 0) + 1);
            }
        }

        for (int num3 : nums3) {
            for (int num4 : nums4) {
                count += map.getOrDefault(-num3 - num4, 0);
            }
        }

        return count;
    }
}
