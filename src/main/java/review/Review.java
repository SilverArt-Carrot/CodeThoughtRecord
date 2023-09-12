package review;

import day02.ListNode;

import java.util.*;

public class Review {
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

    /////////////////////////////////////哈希表/////////////////////////////////////

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

    // 赎金信
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] counts = new int[26];
        for (char c : ransomNote.toCharArray()) {
            counts[c - 'a']++;
        }
        for (char c : magazine.toCharArray()) {
            counts[c - 'a']--;
        }
        for (int count : counts) {
            if (count > 0) {
                return false;
            }
        }
        return true;
    }

    // 三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                return result;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                }
            }
        }
        return result;
    }

    // 四数之和
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 3; i++) {
            if (nums[i] > target && nums[i] >= 0) {  // target 可以是负数，注意【0，0，0，0】，target = 0这组数据
                return result;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (nums[i] + nums[j] > target && nums[j] >= 0) {  // nums[j] >= 0 则 nums[i] >= 0
                    continue;
                }
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                int left = j + 1;
                int right = nums.length - 1;
                while (left < right) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum < target) {
                        left++;
                    } else if (sum > target) {
                        right--;
                    } else {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        left++;
                        right--;
                    }
                }
            }
        }
        return result;
    }

    /////////////////////////////////////字符串/////////////////////////////////////

    // 反转字符串
    public void reverseString(char[] s) {
        int l = 0, r = s.length - 1;
        while (l < r) {
            char c = s[l];
            s[l] = s[r];
            s[r] = c;
            l++;
            r--;
        }
    }

    // 反转字符串II
    public String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i += 2 * k) {
            if (i + k < chars.length) {
                reverse(chars, i, i + k - 1);
            } else {
                reverse(chars, i, chars.length - 1);
            }
        }
        return new String(chars);
    }
    private void reverse(char[] chars, int l, int r) {
        while (l < r) {
            char c = chars[l];
            chars[l] = chars[r];
            chars[r] = c;
            l++;
            r--;
        }
    }

    // 替换空格
    public String replaceSpace(String s) {  // 可以用StringBuilder的，这样写我只是试试会不会快点
        char[] chars = s.toCharArray();
        int count = 0;
        for (char c : chars) {
            if (c == ' ') {
                count++;
            }
        }

        int length = chars.length + 2 * count;
        char[] newChars = new char[length];
        int index = length - 1;
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == ' ') {
                newChars[index--] = '0';
                newChars[index--] = '2';
                newChars[index--] = '%';
            } else {
                newChars[index--] = chars[i];
            }
        }
        return new String(newChars);
    }

    // 翻转字符串里的单词
    public String reverseWords(String s) {
        // 1.使用内置的函数
        // 2.先去除多余的空格，将整个字符串反转，最后再反转其中的单词
        // 3.使用双指针，从后往前搜索单词

        // 使用第三种方法
        char[] chars = s.trim().toCharArray();
        StringBuilder sb = new StringBuilder();

        int left = chars.length - 1, right = left;
        while (left >= 0) {
            while (left >= 0 && chars[left] != ' ') left--;
            sb.append(chars, left + 1, right - left);
            sb.append(' ');
            while (left >= 0 && chars[left] == ' ') left--;
            right = left;
        }

        return sb.toString().trim();
    }

    // 实现 strStr()
    // next数组统一减一操作
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        int[] next = getNext(needle.toCharArray());
        int j = -1;
        for (int i = 0; i < haystack.length(); i++) {
            while (j >= 0 && haystack.charAt(i) != needle.charAt(j + 1)) {
                j = next[j];
            }
            if (haystack.charAt(i) == needle.charAt(j + 1)) {
                j++;
            }
            if (j == needle.length() - 1) {
                return i - needle.length() + 1;
            }
        }
        return -1;
    }
    private int[] getNext(char[] chars) {
        int[] next = new int[chars.length];
        int j = -1;
        next[0] = j;
        for (int i = 1; i < chars.length; i++) {
            while (j >= 0 && chars[i] != chars[j + 1]) {
                // 逐渐理解了，在构造next数组的过程中，i为后缀的下一个，j+1为前缀的下一个
                // 当i与j+1不匹配时，取前缀j的最长相同前后缀的下一个与i继续匹配
                // 这个过程中你可以理解后缀被当成了主串，前缀被当成了模式串
                j = next[j];
            }
            if (chars[i] == chars[j + 1]) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
    // next数组统一不减一操作
    public int strStr2(String haystack, String needle) {
        haystack.indexOf("s");
        if (needle.length() == 0) {
            return 0;
        }
        int[] next = getNext2(needle.toCharArray());
        int j = 0;
        for (int i = 0; i < haystack.length(); i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = next[j - 1];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if (j == needle.length()) {
                return i - needle.length() + 1;
            }
        }
        return -1;
    }
    private int[] getNext2(char[] chars) {
        int[] next = new int[chars.length];
        int j = 0;
        next[0] = j;
        for (int i = 1; i < chars.length; i++) {
            while (j > 0 && chars[i] != chars[j]) {
                j = next[j - 1];
            }
            if (chars[i] == chars[j]) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    // 重复的子字符串
    /**
     * 假设 s 可由 子串 x 重复 n 次构成，即 s = nx
     * 则有 s+s = 2nx
     * 移除 s+s 开头和结尾的字符，变为 (s+s)[1:-1]，则破坏了开头和结尾的子串 x
     * 此时只剩 2n-2 个子串x
     * 若 s 在 (s+s)[1:-1] 中，则有 2n-2 >= n，即 n >= 2
     * 即 s 至少可由 x 重复 2 次构成
     * 否则，n < 2，n 为整数，只能取 1，说明 s 不能由其子串重复多次构成
     * class Solution:
     *     def repeatedSubstringPattern(self, s: str) -> bool:
     *         return s in (s+s)[1:-1]
     */
    public boolean repeatedSubstringPattern(String s) {
        // 当一个字符串由重复子串组成的，最长相等前后缀不包含的子串就是最小重复子串
        int[] next = getNext(s.toCharArray());
        int last = next[s.length() - 1];
        if (last != -1 && s.length() % (s.length() - (last + 1)) == 0) {
            return true;
        }
        return false;
    }

    /////////////////////////////////////双指针法/////////////////////////////////////
    // 题目都在上面了
    //【移除元素】【反转字符串】【替换空格】【翻转字符串里的单词】【反转链表】【删除链表的倒数第N个节点】【链表相交】【环形链表II】【三数之和】【四数之和】

    /////////////////////////////////////栈与队列/////////////////////////////////////

    // 用栈实现队列
    static class MyQueue {
        private final Deque<Integer> stackIn = new LinkedList<>();
        private final Deque<Integer> stackOut = new LinkedList<>();

        public MyQueue() {

        }

        public void push(int x) {
            stackIn.push(x);
        }

        public int pop() {
            dump();
            return stackOut.pop();
        }

        public int peek() {
            dump();
            return stackOut.peek();
        }

        public boolean empty() {
            return stackIn.isEmpty() && stackOut.isEmpty();
        }

        private void dump() {
            if (!stackOut.isEmpty()) {
                return;
            }
            while (!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
    }

    // 用队列实现栈
    public static class MyStack {  // 不使用队列的size
        private Deque<Integer> queue1 = new ArrayDeque<>();
        private Deque<Integer> queue2 = new ArrayDeque<>(); // 起到了复制的作用，并且把元素倒了过来

        public void push(int x) {
            queue2.offer(x);
            while (!queue1.isEmpty()) {
                queue2.offer(queue1.poll());
            }

            Deque<Integer> tmp = queue1;
            queue1 = queue2;
            queue2 = tmp;
        }

        public int pop() {
            return queue1.pop();
        }

        public int top() {
            return queue1.peek();
        }

        public boolean empty() {
            return queue1.isEmpty();
        }
    }
    public static class MyStack2 {
        private final Deque<Integer> queue = new ArrayDeque<>();

        // 每次push的时候把链表倒过来
        public void push(int x) {
            queue.offer(x);
            int size = queue.size();
            while (size-- > 1) {
                queue.offer(queue.poll());
            }
        }

        public int pop() {
            return queue.pop();
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }



    public static void main(String[] args) {
        LinkedList<Integer> deque = new LinkedList<>();
        deque.push(1);
        deque.push(2);
        deque.push(3);
        System.out.println(deque.peek());
        System.out.println(deque);

        System.out.println(deque.poll());
    }
}
