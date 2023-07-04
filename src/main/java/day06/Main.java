package day06;

import java.util.*;

public class Main {
    /**
     * 栈、队列
     */
    public static void main(String[] args) {
        Main main = new Main();
        boolean valid = main.isValid("([)]");
        System.out.println(valid);

        String result = main.removeDuplicates("abbaca");
        System.out.println(result);
    }

    /**
     * 用栈实现队列
     *
     * 使用栈实现队列的下列操作：
     *
     * push(x) -- 将一个元素放入队列的尾部。
     * pop() -- 从队列首部移除元素。
     * peek() -- 返回队列首部的元素。
     * empty() -- 返回队列是否为空。
     */
    public static class MyQueue {
        private final Deque<Integer> stackIn = new ArrayDeque<>();
        private final Deque<Integer> stackOut = new ArrayDeque<>();

        public void push(int item) {
            stackIn.push(item);
        }

        public int pop() {
            dump();
            return stackOut.pop();
        }

        public int peek() {
            dump();
            return stackOut.peek();
        }

        public boolean isEmpty() {
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

    /**
     * 用队列实现栈
     *
     * 使用队列实现栈的下列操作：
     *
     * push(x) -- 元素 x 入栈
     * pop() -- 移除栈顶元素
     * top() -- 获取栈顶元素
     * empty() -- 返回栈是否为空
     * 注意:
     *
     * 你只能使用队列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 这些操作是合法的。
     * 你所使用的语言也许不支持队列。 你可以使用 list 或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
     * 你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用 pop 或者 top 操作）。
     */
    public static class MyStack {  // 不使用队列的size
        private Deque<Integer> queue1 = new ArrayDeque<>();
        private Deque<Integer> queue2 = new ArrayDeque<>(); // 起到了复制的作用，并且把元素倒了过来

        public void push(int x) {
            queue2.offer(x);
            while (!queue1.isEmpty()) {
                queue2.offer(queue1.pop());
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

    /**
     * 使用了size
     */
    public static class MyStack2 {
        private final Deque<Integer> queue = new ArrayDeque<>();

        // 每次push的时候把链表倒过来
        public void push(int x) {
            queue.offer(x);
            int size = queue.size();
            while (size-- > 1) {
                queue.offer(queue.pollFirst());
            }
        }

        public int pop() {
            return queue.pop();
        }

        public int top() {
            return queue.peekFirst();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }

    /**
     * 有效的括号
     *
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     *
     * 有效字符串需满足：
     *
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     * 示例 1:
     *
     * 输入: "()"
     * 输出: true
     * 示例 2:
     *
     * 输入: "()[]{}"
     * 输出: true
     * 示例 3:
     *
     * 输入: "(]"
     * 输出: false
     * 示例 4:
     *
     * 输入: "([)]"
     * 输出: false
     * 示例 5:
     *
     * 输入: "{[]}"
     * 输出: true
     */
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            if (c == ')' || c == ']' || c == '}') {
                Character last = stack.poll();
                if (last == null) {
                    return false;
                }
                if (c == ')') {
                    if (last != '(') {
                        return false;
                    }
                } else if (c == ']') {
                    if (last != '[') {
                        return false;
                    }
                } else {
                    if (last != '{') {
                        return false;
                    }
                }
            }
        }

        return stack.isEmpty();
    }

    /**
     * 代码随想录的
     */
    public boolean isValid2(String s) {
        Deque<Character> deque = new LinkedList<>();
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            //碰到左括号，就把相应的右括号入栈
            if (ch == '(') {
                deque.push(')');
            } else if (ch == '{') {
                deque.push('}');
            } else if (ch == '[') {
                deque.push(']');
            } else if (deque.isEmpty() || deque.peek() != ch) {
                return false;
            } else { // 如果是右括号判断是否和栈顶元素匹配
                deque.pop();
            }
        }
        // 最后判断栈中元素是否匹配
        return deque.isEmpty();
    }

    /**
     * 1047. 删除字符串中的所有相邻重复项
     *
     * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
     *
     * 在 S 上反复执行重复项删除操作，直到无法继续删除。
     *
     * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
     *
     * 示例：
     *
     * 输入："abbaca"
     * 输出："ca"
     * 解释：例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
     * 提示：
     *
     * 1 <= S.length <= 20000
     * S 仅由小写英文字母组成。
     */
    public String removeDuplicates(String s) {
        Deque<Character> stack = new LinkedList<>();

        for (char c : s.toCharArray()) {
            if (stack.isEmpty() || stack.peek() != c) {
                stack.push(c);
            } else {
                stack.poll();
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pollLast());
        }
        return sb.toString();
    }

    /**
     * 逆波兰表达式求值
     *
     * 根据 逆波兰表示法，求表达式的值。
     *
     * 有效的运算符包括 + ,  - ,  * ,  / 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
     *
     * 说明：
     *
     * 整数除法只保留整数部分。 给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
     *
     * 示例 1：
     *
     * 输入: ["2", "1", "+", "3", " * "]
     * 输出: 9
     * 解释: 该算式转化为常见的中缀算术表达式为：((2 + 1) * 3) = 9
     * 示例 2：
     *
     * 输入: ["4", "13", "5", "/", "+"]
     * 输出: 6
     * 解释: 该算式转化为常见的中缀算术表达式为：(4 + (13 / 5)) = 6
     * 示例 3：
     *
     * 输入: ["10", "6", "9", "3", "+", "-11", " * ", "/", " * ", "17", "+", "5", "+"]
     *
     * 输出: 22
     *
     * 解释:该算式转化为常见的中缀算术表达式为：
     */
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();

        for (String token : tokens) {
            if (token.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (token.equals("-")) {
                int first = stack.pop();
                int second = stack.pop();
                stack.push(second - first);
            } else if (token.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (token.equals("/")) {
                int first = stack.pop();
                int second = stack.pop();
                stack.push(second / first);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    /**
     * 滑动窗口最大值
     *
     * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
     *
     * 返回滑动窗口中的最大值。
     *
     * 进阶：
     *
     * 你能在线性时间复杂度内解决此题吗？
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        MyNewStack stack = new MyNewStack();
        for (int i = 0; i < k; i++) {
            stack.push(nums[i]);
        }
        int j = 0;
        result[j++] = stack.peek();
        for (int i = k; i < nums.length; i++) {
            stack.pop(nums[i - k]);
            stack.push(nums[i]);
            result[j++] = stack.peek();
        }
        return result;
    }

    public static class MyNewStack {
        private final Deque<Integer> stack = new LinkedList<>();

        public void push(int x) {
            while (!stack.isEmpty() && stack.peek() < x) {
                stack.pop();
            }
            stack.push(x);
        }

        public int peek() {
            return stack.isEmpty() ? 0 : stack.peekLast();
        }

        public void pop(int x) {
            if (!stack.isEmpty() && stack.peekLast() == x) {
                stack.pollLast();
            }
        }
    }

    /**
     * 前 K 个高频元素
     *
     * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
     *
     * 示例 1:
     *
     * 输入: nums = [1,1,1,2,2,3], k = 2
     * 输出: [1,2]
     * 示例 2:
     *
     * 输入: nums = [1], k = 1
     * 输出: [1]
     * 提示：
     *
     * 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
     * 你的算法的时间复杂度必须优于 $O(n \log n)$ , n 是数组的大小。
     * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的。
     * 你可以按任意顺序返回答案。
     */
    public int[] topKFrequent(int[] nums, int k) {
        // 大顶堆
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>(
                (entry1, entry2) -> entry2.getValue() - entry1.getValue()
        );

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            queue.offer(entry);
        }
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = queue.poll().getKey();
        }
        return result;
    }
}
