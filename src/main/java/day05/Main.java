package day05;

import day02.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    /**
     * 双指针
     */
    public static void main(String[] args) {

    }

    /**
     * 27. 移除元素
     *
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     *
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并原地修改输入数组。
     *
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     *
     * 示例 1: 给定 nums = [3,2,2,3], val = 3, 函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。 你不需要考虑数组中超出新长度后面的元素。
     *
     * 示例 2: 给定 nums = [0,1,2,2,3,0,4,2], val = 2, 函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。
     *
     * 你不需要考虑数组中超出新长度后面的元素。
     */
    public static int learn01(int[] nums, int val) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow++] = nums[fast];
            }
        }
        return slow;
    }

    /**
     * 反转字符串
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
     *
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     *
     * 你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。
     *
     * 示例 1：
     * 输入：["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     *
     * 示例 2：
     * 输入：["H","a","n","n","a","h"]
     * 输出：["h","a","n","n","a","H"]
     *
     */
    public static void learn02(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char t = s[left];
            s[left] = s[right];
            s[right] = t;
            left++;
            right--;
        }
    }

    /**
     * 翻转字符串里的单词
     *
     * 给定一个字符串，逐个翻转字符串中的每个单词。
     *
     * 示例 1：
     * 输入: "the sky is blue"
     * 输出: "blue is sky the"
     *
     * 示例 2：
     * 输入: "  hello world!  "
     * 输出: "world! hello"
     * 解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
     *
     * 示例 3：
     * 输入: "a good   example"
     * 输出: "example good a"
     * 解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
     */
    public static String learn03(String s) {
        StringBuilder sb = new StringBuilder();
        char[] chars = s.toCharArray();
        int i = 0;
        int right = chars.length - 1;
        while (chars[i] == ' ') {
            i++;
        }
        while (chars[right] == ' ') {
            right--;
        }
        int left;
        while (right >= i) {
            left = right;
            while (left >= i && chars[left] != ' ') {
                left--;
            }
            sb.append(chars, left + 1, right - left);
            if (left >= i) {
                sb.append(" ");
            }
            while (left > i && chars[left] == ' ') {
                left--;
            }
            right = left;
        }
        return sb.toString();
    }

    /**
     * 反转链表
     *
     * 题意：反转一个单链表。
     *
     * 示例: 输入: 1->2->3->4->5->NULL 输出: 5->4->3->2->1->NULL
     *
     */
    public static ListNode learn04(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            ListNode next = cur.next;

            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 删除链表的倒数第N个节点
     */
    public static ListNode learn05(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        if (n <= 0) {
            return head;
        }
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode slow = dummy;
        ListNode fast = dummy;

        for (int i = 0; i < n; i++) {
            fast = fast.next;
            if (i >= n - 1 && fast == null) {
                return head;
            }
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return dummy.next;
    }

    /**
     * 链表相交
     *
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表没有交点，返回 null 。
     * 先求出两个链表的长度，让长的链表先移动差值，在判断两个链表节点是否有相等的
     * 我写的
     */
    public static ListNode learn06(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        int lenA = 0;
        int lenB = 0;
        ListNode curA = headA;
        ListNode curB = headB;

        while (curA != null) {
            lenA++;
            curA = curA.next;
        }

        while (curB != null) {
            lenB++;
            curB = curB.next;
        }

        curA = headA;
        curB = headB;
        int cnt = lenA - lenB;
        if (cnt > 0) {
            while (cnt > 0) {
                curA = curA.next;
                cnt--;
            }
        } else if (cnt < 0) {
            while (cnt != 0) {
                curB = curB.next;
                cnt++;
            }
        }
        while (curA != null && curB != null) {
            if (curA == curB) {
                return curA;
            }
            curA = curA.next;
            curB = curB.next;
        }
        return null;
    }

    /**
     * 两个链表是否有交点 chatGPT 写的
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 如果其中一个链表为空，则不相交
        if (headA == null || headB == null) {
            return null;
        }

        ListNode pA = headA;
        ListNode pB = headB;

        // 当两个指针指向同一个节点时，说明相交
        while (pA != pB) {
            // 如果指针pA已经到达链表末尾，则将它指向链表B的头节点
            pA = (pA == null) ? headB : pA.next;
            // 如果指针pB已经到达链表末尾，则将它指向链表A的头节点
            pB = (pB == null) ? headA : pB.next;
        }

        // 如果两个指针指向同一个节点，说明相交，返回相交节点
        return pA;
    }

    /**
     * 判断链表是否有环，charGPT 写的
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head.next;

        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * 返回链表入环的节点，若链表没有环则返回null
     */
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            // 快慢指针相遇则存在环
            if (slow == fast) {
                fast = head;
                while (fast != slow) {
                    fast = fast.next;
                    slow = slow.next;
                }
                return fast;
            }
        }
        return null;
    }

    /**
     * 返回链表环的长度，若无环，则返回0
     */
    public int cycleLength(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                int len = 1;
                fast = fast.next;
                while (fast != slow) {
                    fast = fast.next;
                    len++;
                }
                return len;
            }
        }
        return 0;
    }

    /**
     * 三数之和
     */
    public List<List<Integer>> threeNumSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum < 0) {
                    left++;
                } else if (sum > 0) {
                    right--;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                }
            }
        }
        return result;
    }

    /**
     * 四数之和 双指针 O4->O3
     */
    public List<List<Integer>> fourNumSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return result;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            if (nums[i] > 0 && nums[i] > target) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (nums[i] + nums[j] > 0 && nums[i] + nums[j] > target) {
                    break;
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
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        left++;
                        right--;
                    }
                }
            }
        }
        return result;
    }
}

