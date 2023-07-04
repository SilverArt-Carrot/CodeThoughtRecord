package day08;

import java.util.*;

public class Main {
    /**
     * 回溯
     * 回溯是递归的副产品，只要有递归就会有回溯。
     * 因为回溯的本质是穷举，穷举所有可能，然后选出我们想要的答案
     * 回溯法解决的问题都可以抽象为树形结构
     * 回溯法一般是在集合中递归搜索，集合的大小构成了树的宽度，递归的深度构成的树的深度。
     *
     * void backtracking(参数) {
     *     if (终止条件) {
     *         存放结果;
     *         return;
     *     }
     *
     *     for (选择：本层集合中元素（树中节点孩子的数量就是集合的大小）) {
     *         处理节点;
     *         backtracking(路径，选择列表); // 递归
     *         回溯，撤销处理结果
     *     }
     * }
     */
    public static void main(String[] args) {

    }

    /**
     * 组合
     *
     * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
     *
     * 示例: 输入: n = 4, k = 2 输出: [ [2,4], [3,4], [2,3], [1,2], [1,3], [1,4], ]
     *
     * n - (k - path.size()) 是看给下一层的，要看接下来的层数够不够用，+ 1是因为闭合区间
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new LinkedList<>();
        backTracking1(n, k, 1, result, path);
        return result;
    }
    private void backTracking1(int n, int k, int start, List<List<Integer>> result, Deque<Integer> path) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i <= n - (k - path.size()) + 1; i++) {
            path.offer(i);
            backTracking1(n, k, i + 1, result, path);
            path.pollLast();
        }
    }

    /**
     * 组合总和III
     *
     * 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
     *
     * 说明：
     *
     * 所有数字都是正整数。
     * 解集不能包含重复的组合。
     * 示例 1: 输入: k = 3, n = 7 输出: [[1,2,4]]
     *
     * 示例 2: 输入: k = 3, n = 9 输出: [[1,2,6], [1,3,5], [2,3,4]]
     *
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new LinkedList<>();
        backTracking2(n, k, 0, 1, result, path);
        return result;
    }
    private void backTracking2(int n, int k, int sum, int start, List<List<Integer>> result, Deque<Integer> path) {
        if (path.size() > k) {
            return;
        }
        if (path.size() == k && sum == n) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i <= 9 - (k - path.size()) + 1; i++) {
            if (i > n || i + sum > n) {
                break;
            }
            sum += i;
            path.add(i);
            backTracking2(n, k, sum, i + 1, result, path);
            sum -= i;
            path.pollLast();
        }
    }

    /**
     * 电话号码的字母组合
     *
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
     *
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * String[] nums = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
     *
     * 输入："23"
     * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
     * 说明：尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序。
     *
     */
    String[] numsArr = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return result;
        }
        StringBuilder sb = new StringBuilder();
        backTracking3(0, digits, sb, result);
        return result;
    }
    private void backTracking3(int start, String digits, StringBuilder sb, List<String> result) {
        if (sb.length() == digits.length()) {
            result.add(sb.toString());
            return;
        }
        // 要注意区分同一个集合中的组合，和不同集合中的组合
        String nums = numsArr[digits.charAt(start) - '0'];
        for (int i = 0; i < nums.length(); i++) {
            sb.append(nums.charAt(i));
            backTracking3(start + 1, digits, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * 组合总和
     *
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的数字可以无限制重复被选取。
     *
     * 说明：
     *
     * 所有数字（包括 target）都是正整数。
     * 解集不能包含重复的组合。
     * 示例 1：
     *
     * 输入：candidates = [2,3,6,7], target = 7,
     * 所求解集为： [ [7], [2,2,3] ]
     * 示例 2：
     *
     * 输入：candidates = [2,3,5], target = 8,
     * 所求解集为： [ [2,2,2,2], [2,3,3], [3,5] ]
     */
//    如果是一个集合来求组合的话，就需要startIndex，例如：77.组合，216.组合总和III。
//    如果是多个集合取组合，各个集合之间相互不影响，那么就不用startIndex，例如：17.电话号码的字母组合（可能排列也不用）
//    注意以上我只是说求组合的情况，如果是排列问题，又是另一套分析的套路，后面我再讲解排列的时候就重点介绍。
    private int sum1 = 0;
    private final List<List<Integer>> result1 = new ArrayList<>();
    private final LinkedList<Integer> path1 = new LinkedList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        backTracking4(candidates, target, 0);
        return result1;
    }
    private void backTracking4(int[] candidates, int target, int start) {
        if (sum1 == target) {
            result1.add(new ArrayList<>(path1));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] + sum1 > target) {
                break;
            }
            sum1 += candidates[i];
            path1.add(candidates[i]);
            backTracking4(candidates, target, i);
            sum1 -= candidates[i];
            path1.removeLast();
        }
    }

    /**
     * 组合总和II
     *
     * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的每个数字在每个组合中只能使用一次。
     *
     * 说明： 所有数字（包括目标数）都是正整数。解集不能包含重复的组合。
     *
     * 示例 1:
     * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
     * 所求解集为:
     * [
     *   [1, 7],
     *   [1, 2, 5],
     *   [2, 6],
     *   [1, 1, 6]
     * ]
     * 示例 2:
     * 输入: candidates = [2,5,2,1,2], target = 5,
     * 所求解集为:
     * [
     *   [1,2,2],
     *   [5]
     * ]
     */
    private int sum2 = 0;
    private final List<List<Integer>> result2 = new ArrayList<>();
    private final LinkedList<Integer> path2 = new LinkedList<>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        backTracking5(candidates, target, 0);
        return result2;
    }
    private void backTracking5(int[] candidates, int target, int start) {
        if (sum2 == target) {
            result2.add(new ArrayList<>(path2));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] + sum2 > target) {
                break;
            }
            // 看的代码随想录的去重，其实也可以用used数组，具体看
            // https://www.programmercarl.com/0040.%E7%BB%84%E5%90%88%E6%80%BB%E5%92%8CII.html#%E5%9B%9E%E6%BA%AF%E4%B8%89%E9%83%A8%E6%9B%B2
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }
            sum2 += candidates[i];
            path2.add(candidates[i]);
            backTracking5(candidates, target, i + 1);
            sum2 -= candidates[i];
            path2.removeLast();
        }
    }

    /**
     * 分割回文串
     *
     * 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
     *
     * 返回 s 所有可能的分割方案。
     *
     * 示例: 输入: "aab" 输出: [ ["aa","b"], ["a","a","b"] ]
     */
//    public List<List<String>> partition(String s) {
//
//    }
}
